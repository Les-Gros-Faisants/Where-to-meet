package com.meeple.meeple.API.Handler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.meeple.meeple.Activity.MainActivity;
import org.apache.http.Header;
import org.json.JSONException;
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
        Log.i("Response", response.toString());
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {

    }
}
