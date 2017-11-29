package org.wit.mytweet.activities;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.wit.mytweet.R;
import org.wit.mytweet.fragments.TweetFragment;


public class Home extends Base implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        ImageView addTweet = (ImageView) findViewById(R.id.addTweet);
//        ImageView viewTimeline = (ImageView) findViewById(R.id.viewTimeline);
//        addTweet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addTweetButtonPressed(view);
//            }
//        });
//        viewTimeline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewTimelineButtonPressed(view);
//            }
//        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        TweetFragment fragment = TweetFragment.newInstance();
        ft.replace(R.id.fragment_layout, fragment);
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tweetFragment = TweetFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.fragment_layout, tweetFragment).commit();
    }

    public void addTweetButtonPressed(View view) {
        startActivity(new Intent(this, Add.class));
    }

    public void viewTimelineButtonPressed(View view) {
        startActivity(new Intent(this, GlobalTimeline.class));
    }

    public void deleteAllTweets(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This will delete all of your current tweets?\nAre you sure you want to do this?\n");
        builder.setCancelable(true);//allow users click out of dialog box

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                tweetFragment.deleteAllTweets();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        // http://stackoverflow.com/questions/32944798/switch-between-fragments-with-onnavigationitemselected-in-new-navigation-drawer

        int id = item.getItemId();
        Fragment fragment;
        FragmentTransaction ft = getFragmentManager().beginTransaction();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
