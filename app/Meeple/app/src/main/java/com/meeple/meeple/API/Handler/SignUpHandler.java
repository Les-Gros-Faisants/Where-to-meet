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
            if (statusCode == 200 && response.getString("OK").equals("OK")) {
                Log.i("Account created with success, new id:", response.getString("new_id"));
                
            }
            else {

            }
        }
        catch (JSONException e) {

        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {

    }
}
