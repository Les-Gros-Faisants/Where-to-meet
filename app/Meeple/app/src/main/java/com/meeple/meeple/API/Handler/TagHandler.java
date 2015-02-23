package com.meeple.meeple.API.Handler;

import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.meeple.meeple.Fragments.ProfileFragment;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arkeopix on 2/23/15.
 */
public class TagHandler extends JsonHttpResponseHandler {
    private ProfileFragment _frag;

    public TagHandler(){}
    public TagHandler(ProfileFragment frag) {
        this._frag = frag;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        try {
            if (statusCode == 200 && response.getString("ret").equals("OK")) {
                Log.i("Succesfuly added tag", "lol");
                _frag.tagUserSuccess();
            }
        } catch (JSONException e) {
            Log.i("error", e.getMessage());
            _frag.tagUserFailure(e.getMessage());
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
        Log.i("error", e.getMessage());
        _frag.tagUserFailure(e.getMessage());
    }
}
