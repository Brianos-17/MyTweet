package org.wit.mytweet.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.fragments.TweetFragment;

import static org.wit.helpers.IntentHelper.navigateUp;

public class Home extends Base{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView addTweet = (ImageView) findViewById(R.id.addTweet);
        ImageView viewTimeline = (ImageView) findViewById(R.id.viewTimeline);
        addTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTweetButtonPressed(view);
            }
        });
        viewTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewTimelineButtonPressed(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSettings:
                startActivity(new Intent(this, Settings.class));
                break;
            case R.id.menuClear:
                deleteAllTweets();
                break;
            case R.id.menuTweet:
                startActivity(new Intent(this, Add.class));
                break;
        }
        return true;
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
}
