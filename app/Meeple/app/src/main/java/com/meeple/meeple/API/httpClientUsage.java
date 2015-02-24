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

    public static void createEvent(Double lat, Double lng, int idOrganizer, String eventName, String eventDesc, int timeout, JsonHttpResponseHandler handler) {
        String url = "events/";

        RequestParams params = new RequestParams();
        params.put("id_organizer", idOrganizer);
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("event_name", eventName);
        params.put("event_desc", eventDesc);
        params.put("timeout", timeout);
        httpClient.put(url, params, handler);
    }

    public static void searchEvents(int radius, Double lat, Double lng, String tagWanted, String tagUnwanted, JsonHttpResponseHandler handler) {
        String url = "events/radius/" + lat + "/" + lng + "/" + radius;

        RequestParams params = new RequestParams();
        params.put("tag_wanted", tagWanted);
        params.put("tag_unwanted", tagUnwanted);
        httpClient.get(url, params, handler);
    }

    public static void addTag(String tagName, int idVictim, int idAggressor, JsonHttpResponseHandler handler) {
        String url = "tags/";

        RequestParams params = new RequestParams();
        params.put("tag_name", tagName);
        params.put("id_victim", idVictim);
        params.put("id_aggressor", idAggressor);
        httpClient.put(url, params, handler);
    }
    public static void changeAccount(int id, String passwd, String email, JsonHttpResponseHandler handler) {
        String url = "users/" + id;
        Log.i("build url: ", url);

        RequestParams params = new RequestParams();
        params.put("passwd", passwd != null ? passwd : "do not change");
        params.put("mail", email);
        httpClient.put(url, params, handler);
    }

}
