package org.wit.mytweet.main;

import android.app.Application;
import android.util.Log;
import org.wit.mytweet.models.Tweet;
import org.wit.mytweet.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian on 14/10/2017.

 */

public class MyTweetApp extends Application {

    public List<Tweet> tweetList = new ArrayList<>();
    public List<User> users = new ArrayList<>();

    public void onCreate() {
        super.onCreate();
        Log.v("mytweet", "MyTweet App started");
    }

    public boolean validUser(String email, String password) {
        for (User user : users) {
            if((user.email.equals(email) && (user.password.equals(password)))) {
                return true;
            }
        }
        return false;
    }
}
