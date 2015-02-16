package com.meeple.meeple.Fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.meeple.meeple.API.Handler.ProfileHandler;
import com.meeple.meeple.API.httpClientUsage;
import com.meeple.meeple.Activity.MainPageActivity;
import com.meeple.meeple.Models.Event;
import com.meeple.meeple.Models.User;
import com.meeple.meeple.R;
import com.meeple.meeple.Utils.DialogMaker;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private ProfileHandler handler;
    private Integer id;
    private TextView name;
    private TextView email;
    private DialogMaker dialogMaker;
    private ListView listview = null;
    private ProgressDialog progressDialog;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = ProgressDialog.show(getActivity(), "Loading", "Please wait");
        dialogMaker = new DialogMaker(getActivity());
        handler = new ProfileHandler(this);
        Bundle args = getArguments();
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        if (args != null)
            id = args.getInt("USERNAME_ID");
        if (id == null) {
            id = ((MainPageActivity) getActivity()).userId;
            view.findViewById(R.id.title_tags).setVisibility(View.GONE);
            view.findViewById(R.id.tag_button).setVisibility(View.GONE);
            view.findViewById(R.id.tag_field).setVisibility(View.GONE);
            view.findViewById(R.id.tags).setVisibility(View.GONE);
        }
        Log.i("TESTEST", id.toString());
        name = (TextView) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);
        listview = (ListView) view.findViewById(R.id.listView);

        // Tag Button setup
        Button signupButton = (Button)view.findViewById(R.id.tag_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tagUser();
            }
        });

        return view;
    }

    @Override
    public void	onViewCreated(View view, Bundle savedInstanceState)
    {
         getUserInfos();
    }

    /**
     * requests user's informations
     */
    public void getUserInfos()
    {
        try
        {
            httpClientUsage.getUser(id, handler);
        } catch (Exception e)
        {
            Log.e("Profile error", e.getMessage());
        }
    }

    /**
     * callback of the getInfos request on success
     * will display users information and a clickable list of his previous events
     * @param user user's informations
     */
    public void getInfosSucces(User user)
    {
        email.setText(user.get_mailUser());
        name.setText(user.get_pseudoUser());

        final List<Event> list = user.get_events();
        String[] values = new String[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            Event event = list.get(i);
            String date = event.get_dateEvent().split("T")[0];
            String formatted = event.get_nameEvent() + "\n" + event.get_descriptionEvent() + "\n" + date;
            values[i] = new String(formatted);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, values){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.BLACK);
                return view;
            }
        };
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = new EventFragment();
                Bundle args = new Bundle();
                args.putInt("EVENT_ID", list.get(position).get_idEvent());
                fragment.setArguments(args);
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }

        });
        progressDialog.dismiss();

    }

    /**
     * calback of the getInfos request on failure
     * @param error string describing the error
     */
    public void getInfosFailure(String error)
    {
        dialogMaker.getAlert("Error !", error).show();
        progressDialog.dismiss();
    }

    /**
     * called by the Tag ! button
     */
    public void tagUser()
    {
        String tag = ((EditText)getActivity().findViewById(R.id.tag_field)).getText().toString();
    }

    /**
     * callback of the tagUser request on success
     */
    public void tagUserSuccess()
    {
        dialogMaker.getAlert("Success !", "User tagged !").show();
    }

    /**
     * callback of the tagUser request on failure
     * @param error string describing the error
     */
    public void tagUserFailure(String error)
    {
        dialogMaker.getAlert("Error !", error).show();
    }
}
