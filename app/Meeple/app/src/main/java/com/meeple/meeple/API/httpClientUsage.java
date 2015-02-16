package com.meeple.meeple.API;
import org.json.*;
import com.loopj.android.http.*;
import android.util.Log;

/**
 * Created by arkeopix on 2/3/15.
 */
public class httpClientUsage {
    public static void getUser(int userId, JsonHttpResponseHandler handler) throws JSONException {
        String url = "users/" + userId;

        httpClient.get(url, null, handler);
    }

    public static void getEvent(int id, JsonHttpResponseHandler handler) {
        String url = "events/" + id;

        httpClient.get(url, null, handler);
    }

    public static void logUser(String password, String username, JsonHttpResponseHandler handler) throws Exception {
        String url = "connect/" + username + "/" + password;

        httpClient.get(url, null, handler);
    }

    public static void createUser(String userName, String mail, String password, JsonHttpResponseHandler handler) {
        String url = "users/";

        RequestParams params = new RequestParams();
        params.put("passwd", password);
        params.put("username", userName);
        params.put("mail", mail);
        httpClient.put(url, params, handler);
    }

    public static void createEvent(Double lat, Double lng, String date, int idOrganizer, String eventName, String eventDesc, JsonHttpResponseHandler handler) {
        String url = "events/";
        RequestParams params = new RequestParams();
        params.put("id_organizer", idOrganizer);
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("date", date);
        params.put("event_name", eventName);
        params.put("event_desc", eventDesc);
        httpClient.put(url, params, handler);
    }
}
