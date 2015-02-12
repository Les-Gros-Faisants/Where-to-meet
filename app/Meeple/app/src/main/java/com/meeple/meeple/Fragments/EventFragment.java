package com.meeple.meeple.Fragments;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.meeple.meeple.API.Handler.EventHandler;
import com.meeple.meeple.API.httpClientUsage;
import com.meeple.meeple.Models.Event;
import com.meeple.meeple.R;
import com.meeple.meeple.Utils.DialogMaker;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {
    private static GoogleMap map;
    private static FragmentManager fragmentManager;
    private static MapFragment mapFragment;
    private static DialogMaker dialogMaker;
    private static EventHandler handler;
    private static Double lat = null;
    private static Double lng = null;
    private static int id;
    private static View rootview;

    public EventFragment() {
        // Required empty public constructor
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container == null) {
            return null;
        }
        dialogMaker = new DialogMaker(getActivity());
        handler = new EventHandler(this);
        Bundle bundle = getArguments();
        id = bundle.getInt("EVENT_ID");

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
        rootview = inflater.inflate(R.layout.fragment_event, container, false);;
        return rootview;
    }

    public static void setUpMap() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map != null) {
            if (mapFragment != null) {
                getEvent();
            }
        }
    }

    public static void getEvent()
    {
        httpClientUsage.getEvent(id, handler);
    }

    /**
     * callback of the getEvent request
     * @param event object Event containing event's info
     */
    public static void getEventSuccess(Event event)
    {
        Log.i("MAPTEST", "SUCCESS");
        ((TextView) rootview.findViewById(R.id.event_name)).setText(event.get_nameEvent());
        ((TextView) rootview.findViewById(R.id.event_description)).setText(event.get_descriptionEvent());
//        ((TextView) rootview.findViewById(R.id.event_tags)).setText(event.get_);
        String date = event.get_dateEvent().split("T")[0];
        ((TextView) rootview.findViewById(R.id.event_date)).setText(date);
        ((TextView) rootview.findViewById(R.id.event_creator)).setText(Integer.toString(event.get_idOrganizer()));
             map.addMarker(new MarkerOptions()
                     .position(new LatLng(Double.valueOf(event.get_geolocation().get("lat")), Double.valueOf(event.get_geolocation().get("long"))))
                     .title(event.get_nameEvent()));

    }

    public static void getEventFailure(String error)
    {
        dialogMaker.getAlert("Error !", error).show();
    }
}
