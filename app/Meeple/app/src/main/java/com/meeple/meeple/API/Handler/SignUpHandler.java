package com.meeple.meeple.API.Handler;

import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.meeple.meeple.Activity.SignUpActivity;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arkeopix on 2/5/15.
 */
public class SignUpHandler extends JsonHttpResponseHandler {
    private SignUpActivity _act;

    public SignUpHandler() {}
    public SignUpHandler(SignUpActivity act) {
        this._act = act;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        try {
            if (statusCode == 200 && response.getString("ret").equals("OK")) {
                Log.i("Account created with success, new id:", response.getString("new_id"));
                _act.signUpSuccess();
            }
            else {
                Log.e("Account creation failed", ";(");
                _act.signUpFailure("Something went wrong");
            }
        }
        catch (JSONException e) {
            Log.e("error: ", e.getMessage());
            _act.signUpFailure(e.getMessage());
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
        _act.signUpFailure("Something went wrong: Connection to server failed");
    }
}
