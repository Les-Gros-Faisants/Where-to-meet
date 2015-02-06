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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.meeple.meeple.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventCreationFragment extends Fragment {
    private static GoogleMap map;
    private static Double latitude, longitude;
    private static FragmentManager fragmentManager;
    private static MapFragment mapFragment;

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
        fragmentManager = getChildFragmentManager();
        mapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_frame, mapFragment);
        fragmentTransaction.commit();
        return inflater.inflate(R.layout.fragment_event_creation, container, false);
    }

    public static void setUpMap() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            if (mapFragment != null)
            {
                Log.i("MAP", "FRAG NOT NULL");
                map = mapFragment.getMap();
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setUpMap();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (map != null) {
            super.onDestroyView();
//            map = null;
//            Fragment fragment = (getFragmentManager().findFragmentById(R.id.location_map));
//            FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
//            ft.remove(fragment);
//            ft.commit();
        }
    }
}
