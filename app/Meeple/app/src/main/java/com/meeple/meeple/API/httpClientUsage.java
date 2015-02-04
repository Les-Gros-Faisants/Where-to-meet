package com.meeple.meeple.API;
import org.json.*;
import com.loopj.android.http.*;
import android.util.Log;

/**
 * Created by arkeopix on 2/3/15.
 */
public class httpClientUsage {
    public void getUser(int userId, JsonHttpResponseHandler handler) throws JSONException {
        String url = "users/" + userId;

        httpClient.get(url, null, handler);
    }

    public void logUser(String password, String username, JsonHttpResponseHandler handler) throws Exception {
        String url = "connect/" + username + "/" + password;

        httpClient.get(url, null, handler);
    }
}
