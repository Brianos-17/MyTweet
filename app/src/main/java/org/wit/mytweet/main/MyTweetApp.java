package org.wit.mytweet.main;

import android.app.Application;
import android.util.Log;
import org.wit.mytweet.models.Tweet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian on 14/10/2017.

 */

public class MyTweetApp extends Application {

    public List<Tweet> tweetList = new ArrayList<Tweet>();

    public void onCreate() {
        super.onCreate();
        Log.v("mytweet", "MyTweet App started");
    }
}
