package com.meeple.meeple.API.Handler;

import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.meeple.meeple.Fragments.MainPageFragment;
import org.apache.http.Header;
import org.json.JSONObject;

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
        }
    }
}
