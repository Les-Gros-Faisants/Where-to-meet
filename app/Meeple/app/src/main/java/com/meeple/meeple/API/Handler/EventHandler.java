package com.meeple.meeple.API.Handler;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.meeple.meeple.Fragments.EventFragment;
import com.meeple.meeple.Models.Tags;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import com.meeple.meeple.Models.Event;
import com.meeple.meeple.Models.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by arkeopix on 2/12/15.
 */
public class EventHandler extends JsonHttpResponseHandler {
    private EventFragment _frag;

    public EventHandler() {}
    public EventHandler(EventFragment frag) { this._frag = frag; }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
         if (statusCode == 200) {
            Log.i("Got: ", response.toString());
            try {
                List<User> users = new ArrayList<>();
                JSONObject usersObject = response.getJSONObject("users");
                Iterator<?> keys = usersObject.keys();
                while (keys.hasNext()) {
                    String key = (String)keys.next();
                    if (usersObject.get(key) instanceof JSONObject) {
                        Log.i("get: ", usersObject.get(key).toString());
                        users.add(new User(((JSONObject) usersObject.get(key)).getInt("id_user"),
                                ((JSONObject) usersObject.get(key)).getString("user_name")));
                    }
                }
                List<Tags> tags = new ArrayList<>();
                JSONObject tagObject = response.getJSONObject("tags");
                keys = tagObject.keys();
                while (keys.hasNext()) {
                    String key = (String)keys.next();
                    if (tagObject.get(key) instanceof JSONObject) {
                        Log.i("get: ", tagObject.get(key).toString());
                        tags.add(new Tags(((JSONObject) tagObject.get(key)).getInt("id_tag"),
                                ((JSONObject) tagObject.get(key)).getInt("id_aggressor"),
                                ((JSONObject) tagObject.get(key)).getInt("id_victim"),
                                ((JSONObject) tagObject.get(key)).getString("tag_name")));
                    }
                }

                this._frag.getEventSuccess(new Event(response.getInt("id_event"),
                        response.getString("name_organizer"),
                        response.getDouble("lat"),
                        response.getDouble("lng"),
                        response.getString("description"),
                        response.getString("event_name"),
                        response.getString("date_event"),
                        users,
                        tags));

            }
            catch (JSONException e) {
                Log.e("Something wnt wrong: ", e.getMessage());
                this._frag.getEventFailure(e.getMessage());
            }
        }
        else {
            this._frag.getEventFailure("Server returned: " + statusCode);
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
        Log.e("error: ", e.getMessage());
        this._frag.getEventFailure(e.getMessage());
    }
}
