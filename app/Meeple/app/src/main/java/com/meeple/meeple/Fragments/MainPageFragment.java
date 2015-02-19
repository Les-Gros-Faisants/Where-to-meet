package com.meeple.meeple.Fragments;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.meeple.meeple.API.Handler.SearchEventHandler;
import com.meeple.meeple.API.httpClientUsage;
import com.meeple.meeple.Models.Event;
import com.meeple.meeple.Models.Tags;
import com.meeple.meeple.R;
import com.meeple.meeple.Utils.DialogMaker;
import com.meeple.meeple.Utils.InteractiveArrayAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends Fragment {
    private DialogMaker dialogMaker;
    private SearchEventHandler handler;
    private static GoogleMap map;
    private static FragmentManager fragmentManager;
    private static MapFragment mapFragment;
    private static Double lat;
    private static Double lng;
    private static Boolean located;
    private ExpandableListView listview;
    private List<Event> eventList;
    private List<Marker> markerList;

    private String tags;
    private String excluded_tags;
    private String radius;

    public MainPageFragment() {
        // Required empty public constructor
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        handler = new SearchEventHandler(this);
        dialogMaker = new DialogMaker(getActivity());
        View rootview = inflater.inflate(R.layout.fragment_main_page, container, false);
        tags = "";
        excluded_tags = "";
        radius = "1";

        //Listview setup
        listview = (ExpandableListView)rootview.findViewById(R.id.listView);
        getTags();

        // Button setup
        Button signupButton = (Button) rootview.findViewById(R.id.event_search_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getEvents();
            }
        });

        // map and location setup
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        Location location = null;
        if (provider != null) {
            location = locationManager.getLastKnownLocation(provider);
        }
        else
            dialogMaker.getAlert("Error !", "No provider found, impossible to locate you !").show();
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            lat = (location.getLatitude());
            lng = (location.getLongitude());
            located = true;
        } else
        {
            dialogMaker.getAlert("Error !", "Couldn't locate you !").show();
            lat = 44.339722;
            lng = 1.210278;
            located = false;
        }

        fragmentManager = getChildFragmentManager();
        mapFragment = MapFragment.newInstance();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                setUpMap();
            }
        });
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_frame, mapFragment);
        fragmentTransaction.commit();
        return rootview;
    }

    /**
     * called when the map is ready
     */
    public static void setUpMap() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map != null) {
            if (mapFragment != null) {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .title("You are here"));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 12.0f));
            }
        }
    }

    /**
     * get the events surrounding the user
     */
    public void getEvents()
    {
        if (!((EditText)getActivity().findViewById(R.id.event_tags)).getText().toString().equals(tags))
            tags = ((EditText)getActivity().findViewById(R.id.event_tags)).getText().toString();
        if (!((EditText)getActivity().findViewById(R.id.event_radius)).getText().toString().equals(radius))
            radius = ((EditText)getActivity().findViewById(R.id.event_radius)).getText().toString();
        try {
            int radiusInt = Integer.valueOf(radius);
        } catch (NumberFormatException e) {
            getEventFailure("Radius should be a number");
            return ;
        }
        httpClientUsage.searchEvents(Integer.parseInt(radius), lat, lng, tags, null, handler);
    }

    /**
     * callback of the getEvents request on success
     * @param list list of the requested events
     */
    public void getEventsSuccess(List<Event> list)
    {
        eventList = list;
        markerList = new ArrayList<>();
        for (Event event : list)
        {
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(event.get_geolocation().get("lat"), event.get_geolocation().get("long")))
                    .title(event.get_nameEvent())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            marker.showInfoWindow();
            markerList.add(list.indexOf(event), marker);
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
            {
                @Override
                public boolean onMarkerClick(Marker markerArg) {
                    if (eventList != null) {
                        int index = markerList.indexOf(markerArg);
                        if (index != -1) {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            Fragment fragment = new EventFragment();
                            Bundle args = new Bundle();
                            args.putInt("EVENT_ID", eventList.get(index).get_idEvent());
                            fragment.setArguments(args);
                            ft.replace(R.id.content_frame, fragment);
                            ft.commit();
                        }
                    }
                    return true;
                }

            });
        }
    }

    /**
     * callback of the getEvents request on failure
     * @param error string describing the error
     */
    public void getEventFailure(String error)
    {
        dialogMaker.getAlert("Event retrieval error !", error).show();
    }

    public void getTags()
    {

    }

    public void getTagsSuccess(List<Tags> list)
    {
        ArrayAdapter<Tags> adapter = new InteractiveArrayAdapter(getActivity(), list);
        listview.setAdapter(adapter);
    }

    public void getTagsFailure(String error)
    {
        dialogMaker.getAlert("Tag list error !", error).show();
    }

    /**
     * fixing crash when reloading a map
     */
    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
