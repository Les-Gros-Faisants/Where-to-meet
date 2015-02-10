package com.meeple.meeple.Fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.meeple.meeple.Models.Event;
import com.meeple.meeple.R;
import com.meeple.meeple.Utils.DialogMaker;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends Fragment {
    private DialogMaker dialogMaker;
    private static GoogleMap map;
    private static FragmentManager fragmentManager;
    private static MapFragment mapFragment;
    private Double lat;
    private Double lng;

    public MainPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dialogMaker = new DialogMaker(getActivity());
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
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    public void getEvents()
    {

    }

    public void getEventsSuccess(List<Event> list)
    {

    }

    public void getEventFailure(String error)
    {
        dialogMaker.getAlert("Error !", error).show();
    }
}
