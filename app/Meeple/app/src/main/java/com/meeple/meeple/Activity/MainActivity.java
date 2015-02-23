package com.meeple.meeple.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.meeple.meeple.API.Handler.LoginHandler;
import com.meeple.meeple.API.httpClientUsage;
import com.meeple.meeple.R;
import com.meeple.meeple.Utils.DialogMaker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends ActionBarActivity {
    private LoginHandler handler;
    private DialogMaker dialogMaker;
    private ProgressDialog progressDialog;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialogMaker = new DialogMaker(this);
        Button loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logUser();
            }
        });
        Button signupButton = (Button)findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signUp();
            }
        });
        handler = new LoginHandler(this);

//        httpClientUsage httpClient = new httpClientUsage();
//        LoginHandler login = new LoginHandler(this);
//        httpClient.logUser("mdplol", 2, login);

        //starts directly mainpage
//        Intent intent = new Intent(this, MainPageActivity.class);
//        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void logUser()
    {
        EditText login = (EditText) findViewById(R.id.login);
        EditText password = (EditText) findViewById(R.id.password);
        username = login.getText().toString();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logFailure();
        }
        if (md != null) {
            md.update(password.getText().toString().getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            try {
                httpClientUsage.logUser(sb.toString(), username, handler);
                progressDialog = ProgressDialog.show(this, "Loading", "Please wait");
            }
            catch (Exception e) {
                Log.e("Error:", e.getMessage());
            }
        } else {
            logFailure();
        }
    }

    public void logSuccess(int id)
    {
        progressDialog.dismiss();
        Intent intent = new Intent(this, MainPageActivity.class);
        intent.putExtra("USERNAME", username);
        intent.putExtra("USERNAME_ID", id);
        startActivity(intent);
    }

    public void logFailure()
    {
        progressDialog.dismiss();
        dialogMaker.getAlert("Error !", "Login failed").show();
    }

    public void signUp()
    {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}
