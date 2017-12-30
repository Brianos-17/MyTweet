package org.wit.mytweet.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.wit.mytweet.fragments.TweetFragment;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.Portfolio;

public class Base extends AppCompatActivity {

    public static MyTweetApp app;
    protected Bundle activityInfo;
    protected TweetFragment tweetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyTweetApp) getApplication();
    }

    protected void goToActivity(Activity current, Class<? extends Activity> activityClass, Bundle bundle) {
        Intent newActivity = new Intent(current, activityClass);
        if(bundle != null){
            newActivity.putExtras(bundle);
        }
        current.startActivity(newActivity);
    }
}
