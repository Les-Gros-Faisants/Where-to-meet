package com.meeple.meeple.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

import com.meeple.meeple.API.Handler.LoginHandler;
import com.meeple.meeple.API.httpClientUsage;
import com.meeple.meeple.R;
import com.meeple.meeple.Utils.DialogMaker;

public class MainActivity extends ActionBarActivity {
    private LoginHandler handler;
    private DialogMaker dialogMaker;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login_button = (Button)findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logUser();
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
        httpClientUsage httpClient = new httpClientUsage();
        try {
            httpClient.logUser(password.getText().toString(), login.getText().toString(), handler);
            // fix in case of infinite loop
            progressDialog = ProgressDialog.show(this, "Loading", "Please wait");
        }
        catch (Exception e) {
            Log.e("Error:", e.getMessage());
        }
    }

    public void logSuccess()
    {
        progressDialog.dismiss();
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }

    public void logFailure()
    {
        progressDialog.dismiss();
        dialogMaker.getAlert("Error !", "Login failed").show();
    }
}
