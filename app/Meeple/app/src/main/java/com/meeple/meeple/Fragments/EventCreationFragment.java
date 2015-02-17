package com.meeple.meeple.Fragments;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.meeple.meeple.API.Handler.CreateEventHandler;
import com.meeple.meeple.API.httpClientUsage;
import com.meeple.meeple.Activity.MainPageActivity;
import com.meeple.meeple.R;
import com.meeple.meeple.Utils.DialogMaker;

import java.lang.reflect.Field;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventCreationFragment extends Fragment {
    private DialogMaker dialogMaker;
    private static GoogleMap map;
    private static FragmentManager fragmentManager;
    private static MapFragment mapFragment;
    private static Double lat = null;
    private static Double lng = null;
    private static View rootview;
    private ProgressDialog progressDialog;
    private CreateEventHandler handler;

    public EventCreationFragment() {
        // Required empty public constructor
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        progressDialog = ProgressDialog.show(getActivity(), "Loading", "Please wait");
        dialogMaker = new DialogMaker(getActivity());
        rootview = inflater.inflate(R.layout.fragment_event_creation, container, false);
        handler = new CreateEventHandler(this);

        // Map and location setup
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        Location location = null;
        if (provider != null)
            location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            lat = (location.getLatitude());
            lng = (location.getLongitude());
        } else
        {
            lat = (double) 0;
            lng = (double) 0;
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

        // Button setup
        Button signupButton = (Button)rootview.findViewById(R.id.event_creation_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createEvent();
            }
        });
        progressDialog.dismiss();
        return rootview;
    }

    /**
     * called once the map is ready, will add the user's last known location
     */
    public static void setUpMap() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map != null) {
            if (mapFragment != null) {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .title("You are here"));
                //Move the camera to the user's location and zoom in!
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 12.0f));
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    /**
     * triggered by the event create button
     * will retrieve infos from fields and request add of the event on the db
     *
     */
public void createEvent()
{
    String name = ((EditText)getActivity().findViewById(R.id.event_name)).getText().toString();
    String desc = ((EditText)getActivity().findViewById(R.id.event_desc)).getText().toString();
    String tags = ((EditText)getActivity().findViewById(R.id.event_tags)).getText().toString();
    Integer timeout = Integer.valueOf(((EditText) getActivity().findViewById(R.id.event_timeout)).getText().toString());
    if (timeout < 1)
        timeout = 1;
    else if (timeout > 60 * 5)
        timeout = 60 * 5;
    timeout *= 60;
    if (name.equals("") || desc.equals("") || tags.equals(""))
        eventCreationFailure("One or serveral fields are empty");
    else if (lat == null || lat == 0.0 || lng == null || lng == 0.0)
        eventCreationFailure("You are not localized");
    else {
        httpClientUsage.createEvent(lat, lng, ((MainPageActivity) getActivity()).userId, name, desc, handler);
    }
}

    /**
     * callback of the event add request on success
     */
    public void eventCreationSuccess(int id)
    {
        dialogMaker.getAlert("Success !", "Event created").show();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putInt("EVENT_ID", id);
        fragment.setArguments(args);
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    /**
     * callback of the event add request on failure
     * @param error string describing the error
     */
    public void eventCreationFailure(String error)
    {
        dialogMaker.getAlert("Error !", error).show();
    }
}
