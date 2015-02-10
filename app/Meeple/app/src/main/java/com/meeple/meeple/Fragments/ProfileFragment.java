package com.meeple.meeple.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meeple.meeple.API.Handler.ProfileHandler;
import com.meeple.meeple.API.httpClientUsage;
import com.meeple.meeple.Activity.MainPageActivity;
import com.meeple.meeple.Models.User;
import com.meeple.meeple.R;
import com.meeple.meeple.Utils.DialogMaker;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private ProfileHandler handler;
    private Integer id;
    private TextView name;
    private TextView email;
    private DialogMaker dialogMaker;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dialogMaker = new DialogMaker(getActivity());
        handler = new ProfileHandler(this);
//        id = getArguments().getInt("USERNAME_ID");
        id = ((MainPageActivity) getActivity()).userId;
        Log.i("TESTEST", id.toString());
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        name = (TextView) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);
        return view;
    }

    @Override
    public void	onViewCreated(View view, Bundle savedInstanceState)
    {
         getUserInfos();
    }

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

    public void getInfosSucces(User user)
    {
        email.setText(user.get_mailUser());
        name.setText(user.get_pseudoUser());
    }

    public void getInfosFailure(String error)
    {
        dialogMaker.getAlert("Error !", error).show();
    }
}
