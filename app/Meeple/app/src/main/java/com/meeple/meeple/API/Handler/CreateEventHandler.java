package com.meeple.meeple.API.Handler;

import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.meeple.meeple.Fragments.EventCreationFragment;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arkeopix on 2/16/15.
 */
public class CreateEventHandler extends JsonHttpResponseHandler {
    private EventCreationFragment _frag;

    public CreateEventHandler() {}
    public CreateEventHandler(EventCreationFragment frag) {
        this._frag = frag;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        try {
            if (statusCode == 200 && response.getString("ret").equals("OK")) {
                this._frag.eventCreationSuccess();
            }
        }
        catch (JSONException e) {
            Log.e("error: ", e.getMessage());
            this._frag.eventCreationFailure(e.getMessage());
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
        this._frag.eventCreationFailure(e.getMessage());
    }
}
