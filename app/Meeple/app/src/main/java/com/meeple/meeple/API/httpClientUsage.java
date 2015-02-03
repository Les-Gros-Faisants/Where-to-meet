package com.meeple.meeple.API;
import org.json.*;
import org.apache.http.Header;
import com.loopj.android.http.*;
import android.util.Log;

/**
 * Created by arkeopix on 2/3/15.
 */
public class httpClientUsage {
    public void getUser(int userId) throws JSONException {
        String url = "users/" + userId;
        Log.i("url = ", url);
        httpClient.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("response to string", response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.i("response to string", response.toString());
            }
        });
    }
}
