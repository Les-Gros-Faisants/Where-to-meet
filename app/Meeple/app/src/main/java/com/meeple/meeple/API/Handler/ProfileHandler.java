package com.meeple.meeple.API.Handler;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.meeple.meeple.Fragments.ProfileFragment;
import com.meeple.meeple.Fragments.SettingFragment;
import com.meeple.meeple.Models.Event;
import com.meeple.meeple.Models.Tags;
import com.meeple.meeple.Models.User;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by arkeopix on 2/5/15.
 */
public class ProfileHandler extends JsonHttpResponseHandler {
    private ProfileFragment _frag = null;
    private SettingFragment _settingFrag = null;

    public ProfileHandler() {}
    public ProfileHandler(ProfileFragment frag) {
        this._frag = frag;
    }
    public ProfileHandler(SettingFragment frag) {
        this._settingFrag = frag;
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
                        eventList.add(new Event(((JSONObject) test.get(key)).getInt("id_event"),
                                ((JSONObject)test.get(key)).getInt("event_organizer"),
                                ((JSONObject)test.get(key)).getDouble("lat"),
                                ((JSONObject)test.get(key)).getDouble("lng"),
                                ((JSONObject)test.get(key)).getString("desc_event"),
                                ((JSONObject)test.get(key)).getString("event_name"),
                                ((JSONObject)test.get(key)).getString("event_date")));
                    }
                }
                test = response.getJSONObject("tags");
                Log.i("got: ", test.toString());
                keys = test.keys();
                List<Tags> tagList = new ArrayList<>();
                while(keys.hasNext()){
                    String key = (String)keys.next();
                    if (test.get(key) instanceof JSONObject) {
                        Log.i("get: ", test.get(key).toString());
                        tagList.add(new Tags(((JSONObject) test.get(key)).getString("tag_name"),
                                    ((JSONObject) test.get(key)).getInt("tag_occurence")));
                    }
                }
                User user = new User(response.getInt("id_user"), response.getString("user_pseudo"), response.getString("mail_user"), eventList, tagList);
                if (_frag != null)
                    _frag.getInfosSucces(user);
                else
                    _settingFrag.getInfosSuccess(user);
            }
        }
        catch (JSONException e) {
            Log.e("something went wrong: ", e.getMessage());
            if (_frag != null)
                _frag.getInfosFailure(e.getMessage());
            else
                _settingFrag.getInfosFailure(e.getMessage());
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
        Log.e("something went wrong", e.getMessage());
        if (_frag != null)
            _frag.getInfosFailure(e.getMessage());
        else
            _settingFrag.getInfosFailure(e.getMessage());
    }
}
