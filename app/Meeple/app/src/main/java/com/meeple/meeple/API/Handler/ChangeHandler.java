package com.meeple.meeple.API.Handler;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.meeple.meeple.Fragments.SettingFragment;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

/**
 * Created by arkeopix on 2/24/15.
 */
public class ChangeHandler extends JsonHttpResponseHandler {
    private SettingFragment _frag;

    public ChangeHandler() {}
    public ChangeHandler(SettingFragment frag) {
        this._frag = frag;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        try {
            if (statusCode == 200 && response.getString("ret").equals("OK")) {
                Log.i("Connection :", "OK");
                _frag.saveChangesSuccess();
            }
            else {
                Log.i("Connection :", "KO");
                _frag.saveChangesFailure("something went wrong with the server: " + statusCode);
            }
        }
        catch (JSONException e) {
                Log.e("error", e.getMessage());
                _frag.saveChangesFailure(e.getMessage());
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
        Log.e("Changes: ", "KO");
        _frag.saveChangesFailure(e.getMessage());
    }
}
