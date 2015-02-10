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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.meeple.meeple.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventCreationFragment extends Fragment {
    private static GoogleMap map;
    private static FragmentManager fragmentManager;
    private static MapFragment mapFragment;
    private static Double lat = null;
    private static Double lng = null;

    public EventCreationFragment() {
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
        return inflater.inflate(R.layout.fragment_event_creation, container, false);
    }

    public static void setUpMap() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map != null) {
            if (mapFragment != null) {

                map.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .title("You are here"));
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (map != null) {
//            map = null;
//            Fragment fragment = (getFragmentManager().findFragmentById(R.id.location_map));
//            FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
//            ft.remove(fragment);
//            ft.commit();
        }
    }
}