package com.meeple.meeple.API.Handler;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.meeple.meeple.Fragments.EventFragment;
import org.apache.http.Header;
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
            this._frag.getEventSuccess(new Event());
        }
    }
}
