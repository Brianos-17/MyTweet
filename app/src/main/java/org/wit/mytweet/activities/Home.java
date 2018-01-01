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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.wit.mytweet.R;
import org.wit.mytweet.api.TweetAPI;
import org.wit.mytweet.api.VolleyListener;
import org.wit.mytweet.fragments.AddFragment;
import org.wit.mytweet.fragments.EditFragment;
import org.wit.mytweet.fragments.GlobalTimelineFragment;
import org.wit.mytweet.fragments.TweetFragment;
import org.wit.mytweet.main.MyTweetApp;

import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, EditFragment.OnFragmentInteractionListener,
        VolleyListener{

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
        googlePhoto = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.googlephoto1);
        TweetAPI.getGooglePhoto(app.googlePhotoURL,googlePhoto);

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

        } else if (id == R.id.nav_map) {
                startActivity(new Intent(this, Map.class));

        } else if (id == R.id.nav_share) {

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
                    Auth.GoogleSignInApi.signOut(app.mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
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

    @Override
    public void setList(List list) {
        Home.app.tweetList = list;
    }

    @Override
    public void updateUI(Fragment fragment) {
        if(fragment != null){
            ((TweetFragment)fragment).updateUI(fragment);
        }
    }
    // [END signOut]
}
