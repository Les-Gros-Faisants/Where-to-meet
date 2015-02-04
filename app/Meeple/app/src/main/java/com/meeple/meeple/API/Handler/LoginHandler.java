package com.meeple.meeple.API.Handler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.meeple.meeple.Activity.MainActivity;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;

/**
 * Created by arkeopix on 2/4/15.
 */
public class LoginHandler extends JsonHttpResponseHandler {
    private MainActivity _act;

    public LoginHandler(){}
    public LoginHandler(MainActivity act) {
        this._act = act;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        if (statusCode == 200) {
            Log.i("Connection :", "OK");
        }
        else {
            Log.i("Connection :", "KO");
        }
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        if (statusCode == 200) {
            Log.i("Connection :", "OK");
        }
        else {
            Log.i("Connection :", "KO");
        }
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String response) {
        if (statusCode == 200) {
            Log.i("Connection :", "OK");
        }
        else {
            Log.i("Connection :", "KO");
        }
    }


    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
        Log.i("Connection :", "KO");
    }
}
