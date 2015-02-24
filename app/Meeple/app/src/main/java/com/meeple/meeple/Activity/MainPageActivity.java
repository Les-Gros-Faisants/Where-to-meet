package com.meeple.meeple.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.meeple.meeple.Fragments.MainPageFragment;
import com.meeple.meeple.Fragments.ProfileFragment;
import com.meeple.meeple.Fragments.EventCreationFragment;
import com.meeple.meeple.Fragments.SettingFragment;
import com.meeple.meeple.R;
import com.meeple.meeple.Utils.DrawerItemClickListener;

public class MainPageActivity extends ActionBarActivity {
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String[] itemsList;
    private ActionBarDrawerToggle drawerToggle;
    private Fragment profileFragment;
    private Fragment mainPageFragment;
    private Fragment eventCreationFragment;
    private String username;
    public static FragmentManager fragmentManager;
    public int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        // Setting a drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        itemsList = getResources().getStringArray(R.array.items_list);
        drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, itemsList));
        drawerList.setOnItemClickListener(new DrawerItemClickListener(this));

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);

        fragmentManager = getFragmentManager();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // Get the username
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("USERNAME");
            userId = extras.getInt("USERNAME_ID");
        }
        // Setting the fragments
        this.setFragments();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new SettingFragment()).commit();
            setTitle("Settings");
            return true;
        }

        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private void setFragments() {
        this.profileFragment = new ProfileFragment();
        this.mainPageFragment = new MainPageFragment();
        this.eventCreationFragment = new EventCreationFragment();
        fragmentManager.beginTransaction().replace(R.id.content_frame, mainPageFragment).commit();
        setTitle(itemsList[0]);
        drawerList.setItemChecked(0, true);
    }

    public void selectItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = mainPageFragment;
                break;
            case 1:
                fragment = profileFragment;
                break;
            case 2:
                fragment = eventCreationFragment;
                break;
            case 3:
                fragment = null;
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            drawerList.setItemChecked(position, true);
            drawerList.setSelection(position);
            setTitle(itemsList[position]);
            drawerLayout.closeDrawer(drawerList);
        }
        else {
            Log.e("MainPageActivity", "SelectItem");
        }
    }
}
