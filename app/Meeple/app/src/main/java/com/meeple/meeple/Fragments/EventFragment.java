package com.meeple.meeple.Fragments;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.meeple.meeple.API.Handler.EventHandler;
import com.meeple.meeple.API.httpClientUsage;
import com.meeple.meeple.Models.Event;
import com.meeple.meeple.Models.User;
import com.meeple.meeple.R;
import com.meeple.meeple.Utils.DialogMaker;

import java.util.List;

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
    private static ListView listView;
    private ProgressDialog progressDialog;

    public EventFragment() {
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
        handler = new EventHandler(this);
        Bundle bundle = getArguments();
        id = bundle.getInt("EVENT_ID");
        rootview = inflater.inflate(R.layout.fragment_event, container, false);

        listView = (ListView) rootview.findViewById(R.id.listView);

        // map setup
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
                getEvent();
            }
        }
    }

    /**
     * requests an object Event from the db
     */
    public static void getEvent() {
        httpClientUsage.getEvent(id, handler);
    }

    /**
     * callback of the getEvent request on success
     *
     * @param event object Event containing event's info
     */
    public void getEventSuccess(Event event) {
        ((TextView) rootview.findViewById(R.id.event_name)).setText(event.get_nameEvent());
        ((TextView) rootview.findViewById(R.id.event_description)).setText(event.get_descriptionEvent());
//        ((TextView) rootview.findViewById(R.id.event_tags)).setText(event.get_);
        String date = event.get_dateEvent().split("T")[0];
        ((TextView) rootview.findViewById(R.id.event_date)).setText(date);
        ((TextView) rootview.findViewById(R.id.event_creator)).setText(event.get_nOrganizer());
        map.addMarker(new MarkerOptions()
                .position(new LatLng(event.get_geolocation().get("lat"), event.get_geolocation().get("long")))
                .title(event.get_nameEvent()));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(event.get_geolocation().get("lat"), event.get_geolocation().get("long")), 12.0f));

        // listView creation
        final List<User> list = event.get_users();
        String[] values = new String[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            User user = list.get(i);
            String formatted = user.get_pseudoUser();
            values[i] = new String(formatted);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, values) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.BLACK);
                return view;
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = new ProfileFragment();
                Bundle args = new Bundle();
                args.putInt("USERNAME_ID", list.get(position).get_idUser());
                fragment.setArguments(args);
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });
        progressDialog.dismiss();
    }

    /**
     * callback of the getEvent request on failure
     *
     * @param error string describing the error
     */
    public void getEventFailure(String error) {
        dialogMaker.getAlert("Error !", error).show();
        progressDialog.dismiss();
    }
}
