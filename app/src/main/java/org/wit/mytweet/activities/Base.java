package org.wit.mytweet.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.wit.mytweet.fragments.TweetFragment;
import org.wit.mytweet.main.MyTweetApp;

public class Base extends AppCompatActivity {

    public MyTweetApp app;
    protected Bundle activityInfo;
    protected TweetFragment tweetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyTweetApp) getApplication();
    }
}
