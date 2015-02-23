package com.meeple.meeple.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.meeple.meeple.API.Handler.SignUpHandler;
import com.meeple.meeple.API.httpClientUsage;
import com.meeple.meeple.R;
import com.meeple.meeple.Utils.DialogMaker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpActivity extends ActionBarActivity {
    private DialogMaker dialogMaker;
    private ProgressDialog progressDialog;
    private SignUpHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dialogMaker = new DialogMaker(this);
        handler = new SignUpHandler(this);
        Button signupButton = (Button) findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signUp();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void signUp() {
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait");
        EditText login = (EditText) findViewById(R.id.login);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        EditText password_confirmation = (EditText) findViewById(R.id.password_validation);
        if (password.getText().toString().equals(password_confirmation.getText().toString())) {
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                signUpFailure("Password encryption algorithm not found");
            }
            if (md != null) {
                md.update(password.getText().toString().getBytes());
                byte[] digest = md.digest();
                StringBuffer sb = new StringBuffer();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b & 0xff));
                }
                httpClientUsage.createUser(login.getText().toString(), email.getText().toString(), sb.toString(), handler);
            } else {
                signUpFailure("Password encryption failure");
            }
        } else
            signUpFailure("Passwords differ");
    }

    public void signUpSuccess() {
//        dialogMaker.getAlert("Success !", "You are signed up.");
        progressDialog.dismiss();
        final Intent intent = new Intent(this, MainActivity.class);
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setMessage("You are signed up.");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(intent);
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public void signUpFailure(String error) {
        progressDialog.dismiss();
        dialogMaker.getAlert("Error !", error);
    }
}
