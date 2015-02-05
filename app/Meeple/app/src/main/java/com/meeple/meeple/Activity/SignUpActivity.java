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

import com.meeple.meeple.API.Handler.NewUser;
import com.meeple.meeple.API.httpClientUsage;
import com.meeple.meeple.R;
import com.meeple.meeple.Utils.DialogMaker;

public class SignUpActivity extends ActionBarActivity {
    private NewUser handler;
    private DialogMaker dialogMaker;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        handler = new NewUser(this);
        dialogMaker = new DialogMaker(this);
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
        EditText login = (EditText) findViewById(R.id.login);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        EditText password_confirmation = (EditText) findViewById(R.id.password_validation);
        if (password.getText().toString().equals(password_confirmation.getText().toString()))
            httpClientUsage.createUser(login.getText().toString(), email.getText().toString(), password.getText().toString(), handler);
        else
            signUpFailure("Passwords differ");
    }

    public void signUpSuccess() {
        dialogMaker.getAlert("Success !", "You are signed up.");
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }

    public void signUpFailure(String error) {
        dialogMaker.getAlert("Error !", error);
    }
}
