package com.meeple.meeple.API.Handler;

import android.app.Fragment;
import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arkeopix on 2/5/15.
 */
public class ProfileHandler extends JsonHttpResponseHandler {
    private Fragment _frag;

    public ProfileHandler() {}
    public ProfileHandler(Fragment frag) {
        this._frag = frag;
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {

    }
}
