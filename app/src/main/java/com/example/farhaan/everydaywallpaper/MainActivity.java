package com.example.farhaan.everydaywallpaper;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout drawer_layout;
    private String[] drawer_items, drawer_items_below;
    private Integer[] drawer_icons = {
            R.drawable.icon_wallpaper,
            R.drawable.icon_settings,
            R.drawable.icon_support,
    };
    private Integer[] drawer_icons_below = {
            R.drawable.icon_share,
            R.drawable.icon_contact,
            R.drawable.icon_about,
            R.drawable.icon_remove_ads
    };
    private ListView items_list, items_list_below;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    android.support.v7.app.ActionBar mActionBar;
    CustomList adapter, adapter_below;
    WallPapers wallPapers;
    Settings settings;
    String present_fragement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer_layout = (LinearLayout) findViewById(R.id.navigation_layout);
        drawer_items = getResources().getStringArray(R.array.drawer_items);
        drawer_items_below = getResources().getStringArray(R.array.drawer_items_below);
        //drawer_icons = getResources().getIntArray(R.array.drawer_icons);
        items_list = (ListView) findViewById(R.id.left_drawer);
        items_list_below = (ListView) findViewById(R.id.left_drawer_below);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        adapter = new CustomList(MainActivity.this, drawer_items, drawer_icons);
        adapter_below = new CustomList(MainActivity.this, drawer_items_below, drawer_icons_below);
        wallPapers = new WallPapers();
        present_fragement = "WALLPAPERS";
        //settings = new Settings();
        //present_fragement = "SETTINGS";

        drawer_layout.bringToFront();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        items_list.setAdapter(adapter);
        items_list_below.setAdapter(adapter_below);
        items_list.setOnItemClickListener(new DrawerItemClickListener());
        items_list_below.setOnItemClickListener(new LowerDrawerItemClickListener());
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, wallPapers).commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println("Selected position = " + position);
            selectItem(position);
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void selectItem(int position){
        switch (position){
            case 0:
                if(!present_fragement.equals("WALLPAPERS")) {
                    wallPapers = new WallPapers();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, wallPapers).commit();
                    present_fragement = "WALLPAPERS";
                }
                break;
            case 1:
                if(!present_fragement.equals("SETTINGS")) {
                    settings = new Settings();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, settings).commit();
                    present_fragement = "SETTINGS";
                }
                break;
            default:
                break;
        }
    }

    private class LowerDrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            System.out.println("Selected lower position " + position);
            selectLowerItem(position);
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void selectLowerItem(int position) {
        switch (position) {
            case 0:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Link for Farhaan's app: https://tinyurl.com/everydaywallpaper231570";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"Sharing");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            default:
                break;
        }
    }
}
