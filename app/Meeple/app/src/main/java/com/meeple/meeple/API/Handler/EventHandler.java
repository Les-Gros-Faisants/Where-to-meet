package com.meeple.meeple.API.Handler;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.meeple.meeple.Fragments.EventFragment;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import com.meeple.meeple.Models.Event;

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
                this._frag.getEventSuccess(new Event(response.getInt("id_event"),
                        response.getInt("id_organizer"),
                        response.getString("geolocation"),
                        response.getString("description"),
                        response.getString("event_name"),
                        response.getString("date_event")));

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
}
