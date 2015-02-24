package com.meeple.meeple.Fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.meeple.meeple.API.Handler.ChangeHandler;
import com.meeple.meeple.API.Handler.ProfileHandler;
import com.meeple.meeple.API.httpClientUsage;
import com.meeple.meeple.Activity.MainPageActivity;
import com.meeple.meeple.Models.User;
import com.meeple.meeple.R;
import com.meeple.meeple.Utils.DialogMaker;

import org.json.JSONException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    ProfileHandler handler;
    ChangeHandler changeHandler;
    DialogMaker dialogMaker;
    User user;
    EditText passwordField;
    EditText passwordConfField;
    EditText emailField;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        handler = new ProfileHandler(this);
        changeHandler = new ChangeHandler(this);
        dialogMaker = new DialogMaker(getActivity());
        View rootview = inflater.inflate(R.layout.fragment_setting, container, false);
        passwordConfField = (EditText)rootview.findViewById(R.id.pwd_conf);
        passwordField = (EditText)rootview.findViewById(R.id.pwd);
        emailField = (EditText)rootview.findViewById(R.id.email);
        ((Button)rootview.findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveChanges();
            }
        });
        getInfos();
        return rootview;
    }

    private void getInfos()
    {
        try {
            httpClientUsage.getUser(((MainPageActivity)getActivity()).userId, handler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getInfosSuccess(User user)
    {
        this.user = user;
        if (user.get_mailUser() != null)
            emailField.setText(user.get_mailUser());
    }

    public void getInfosFailure(String error)
    {
        dialogMaker.getAlert("Error !", error).show();
    }

    public void saveChanges()
    {
        String password = passwordField.getText().toString();
        String password_conf = passwordConfField.getText().toString();
        String email = emailField.getText().toString();
        if (email == null)
        {
            saveChangesFailure("You need an email");
            return;
        }
        if (password != null) {
            if (!password.equals(password_conf)) {
                saveChangesFailure("Passwords differ");
                return;
            }
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                saveChangesFailure("Password encryption algorithm not found");
            }
            if (md != null) {
                md.update(password.getBytes());
                byte[] digest = md.digest();
                StringBuffer sb = new StringBuffer();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b & 0xff));
                }
            httpClientUsage.changeAccount(((MainPageActivity)getActivity()).userId, sb.toString(), email, changeHandler);
            } else {
                saveChangesFailure("Password encryption failure");
            }
        }
        else
            httpClientUsage.changeAccount(((MainPageActivity)getActivity()).userId, null, email, changeHandler);
    }

    public void saveChangesSuccess()
    {
        dialogMaker.getAlert("Success !", "Changes saved").show();
        getActivity().setTitle("Profile");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = new ProfileFragment();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    public void saveChangesFailure(String error)
    {
        dialogMaker.getAlert("Error !", error).show();
    }
}
