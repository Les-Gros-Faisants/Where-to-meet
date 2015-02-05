package com.meeple.meeple.API.Handler;

import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.meeple.meeple.Activity.MainActivity;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arkeopix on 2/5/15.
 */
public class NewUser extends JsonHttpResponseHandler {
    private MainActivity _act;

    public NewUser() {}
    public NewUser(MainActivity act) {
        this._act = act;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {

    }
}
