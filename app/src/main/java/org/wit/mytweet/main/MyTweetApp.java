package org.wit.mytweet.main;

import android.app.Application;
import android.util.Log;

import org.wit.mytweet.models.Portfolio;
import org.wit.mytweet.models.PortfolioSerializer;
import org.wit.mytweet.models.Tweet;
import org.wit.mytweet.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Brian on 14/10/2017.
 * Main App class, holds lists of users and tweets that other classes reference
 */

public class MyTweetApp extends Application {

    private static  final String FILENAME = "users.json";
    public Portfolio portfolio;
    public List<Tweet> tweetList = new ArrayList<>();
    public List<User> users = new ArrayList<>();

    public void onCreate() {
        super.onCreate();
        PortfolioSerializer serializer = new PortfolioSerializer(this, FILENAME);
        portfolio = new Portfolio(serializer, users);
        Log.v("mytweet", "MyTweet App started");
    }

    public void addUser(User user) {
        users.add(user);
        Log.v("i/o", "Ad user: " + users);
    }

    public void addTweet(Tweet tweet) {
        tweetList.add(tweet);
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
