package org.wit.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.wit.mytweet.R;
import org.wit.mytweet.fragments.AddFragment;
import org.wit.mytweet.fragments.EditFragment;
import org.wit.mytweet.fragments.GlobalTimelineFragment;
import org.wit.mytweet.fragments.TweetFragment;
import org.wit.mytweet.main.MyTweetApp;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, EditFragment.OnFragmentInteractionListener {

    public static MyTweetApp app = MyTweetApp.getInstance();
    private ImageView googlePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //SetUp GooglePhoto and Email for Drawer here
        googlePhoto = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.googlephoto);
        CoffeeApi.getGooglePhoto(app.googlePhotoURL,googlePhoto);

        TextView googleName = (TextView)navigationView.getHeaderView(0).findViewById(R.id.googlename);
        googleName.setText(app.googleName);

        TextView googleMail = (TextView)navigationView.getHeaderView(0).findViewById(R.id.googlemail);
        googleMail.setText(app.googleMail);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        TweetFragment fragment = TweetFragment.newInstance();
        ft.replace(R.id.fragment_layout, fragment);
        ft.commit();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (id == R.id.nav_home) {
            fragment = TweetFragment.newInstance();
            ft.replace(R.id.fragment_layout, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_add) {
            fragment = AddFragment.newInstance();
            ft.replace(R.id.fragment_layout, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_gt) {
            fragment = GlobalTimelineFragment.newInstance();
            ft.replace(R.id.fragment_layout, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void edit(View v) {
        EditFragment editFrag = (EditFragment) getFragmentManager().findFragmentById(R.id.fragment_layout);
        if (editFrag != null) {
            editFrag.edit(v);
        }
    }

    // [START signOut]
    public void menuSignOut(MenuItem m) {

        //https://stackoverflow.com/questions/38039320/googleapiclient-is-not-connected-yet-on-logout-when-using-firebase-auth-with-g
        app.mGoogleApiClient.connect();
        app.mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {

                //FirebaseAuth.getInstance().signOut();
                if(app.mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(app.mGoogleApiClient).setResultCallback(new ResultCallback<ModernAsyncTask.Status>() {
                        @Override
                        public void onResult(@NonNull ModernAsyncTask.Status status) {
                            if (status.isSuccess()) {
                                Log.v("coffeemate", "User Logged out");
                                Intent intent = new Intent(Home.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.d("coffeemate", "Google API Client Connection Suspended");
            }
        });
    }
    // [END signOut]
}
