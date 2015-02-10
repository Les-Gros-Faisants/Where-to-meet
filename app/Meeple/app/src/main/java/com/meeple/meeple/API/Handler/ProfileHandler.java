package com.meeple.meeple.API.Handler;

import android.app.Fragment;
import android.util.Log;
import com.meeple.meeple.Models.Event;
import com.meeple.meeple.Models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.meeple.meeple.Fragments.ProfileFragment;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by arkeopix on 2/5/15.
 */
public class ProfileHandler extends JsonHttpResponseHandler {
    private ProfileFragment _frag;

    public ProfileHandler() {}
    public ProfileHandler(ProfileFragment frag) {
        this._frag = frag;
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        try {
            if (statusCode == 200) {
                Log.i("Successfully got user: ", response.toString());

                JSONObject test = response.getJSONObject("events");
                Log.i("JSONObject: ", test.toString());
                Iterator<?> keys = test.keys();
                List<Event> eventList = new ArrayList<>();
                while (keys.hasNext()) {
                    String key = (String)keys.next();
                    if (test.get(key) instanceof JSONObject) {
                        Log.i("get: ", test.get(key).toString());
                        eventList.add(new Event(test.getInt("id_event"), test.getInt("event_organizer"),
                                test.getString("geolocation"), test.getString("desc_event"),
                                test.getString("event_name"), test.getString("event_date")));
                    }
                }
                User user = new User(response.getInt("id_user"), response.getString("user_pseudo"), response.getString("mail_user"), eventList);
                _frag.getInfosSucces(user);
            }
        }
        catch (JSONException e) {
            Log.e("something went wrong: ", e.getMessage());
            _frag.getInfosFailure(e.getMessage());
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
        Log.e("something went wrong", e.getMessage());
        _frag.getInfosFailure(e.getMessage());
    }
}
