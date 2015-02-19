package com.meeple.meeple.API.Handler;

import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.meeple.meeple.Fragments.MainPageFragment;
import com.meeple.meeple.Models.Event;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by arkeopix on 2/17/15.
 */
public class SearchEventHandler extends JsonHttpResponseHandler {
    private MainPageFragment _frag;

    public SearchEventHandler() {}
    public SearchEventHandler(MainPageFragment frag) { this._frag = frag; }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        if (statusCode == 200) {
            Log.i("Got response", response.toString());
            try {
                List<Event> events = new ArrayList<Event>();
                Iterator<?> keys = response.keys();
                while (keys.hasNext()) {
                    String key = (String)keys.next();
                    if (response.get(key) instanceof JSONObject) {
                        Log.i("got: ", response.get(key).toString());
                        events.add(new Event(((JSONObject) response.get(key)).getInt("id_event"),
                                ((JSONObject) response.get(key)).getString("event_name"),
                                ((JSONObject) response.get(key)).getDouble("lat"),
                                ((JSONObject) response.get(key)).getDouble("lng")));
                    }
                }

                this._frag.getEventsSuccess(events);
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
