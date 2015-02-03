package com.meeple.meeple.Utils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.meeple.meeple.Activity.MainPageActivity;

/**
 * Created by Vuilla_l on 03/02/2015.
 */
public class DrawerItemClickListener implements ListView.OnItemClickListener {
    private MainPageActivity activity;

    public DrawerItemClickListener(MainPageActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.activity.selectItem(position);
    }
}