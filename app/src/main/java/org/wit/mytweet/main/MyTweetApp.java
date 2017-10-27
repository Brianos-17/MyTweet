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

    private static  final String FILENAME1 = "users.json";
    private static final String FILENAME2 = "tweets.json";
    public Portfolio portfolio;
    public String currentUserId;//variable introduced in order to associate tweets with specific users

    public void onCreate() {
        super.onCreate();
        PortfolioSerializer serializer = new PortfolioSerializer(this, FILENAME1, FILENAME2);
        portfolio = new Portfolio(serializer);//passes the PortfolioSerializer and List of users to the portfolio class for persistence
        currentUserId = "";
        Log.v("mytweet", "MyTweet App started");
    }

    public void addUser(User user) {
        portfolio.users.add(user);
        Log.v("i/o", "User added: " + user);
    }

    public void addTweet(Tweet tweet) {
        portfolio.tweetList.add(tweet);
    }

    public void editTweet(String message, int tweetId) {
        for(Tweet tweet : portfolio.tweetList) {
            if(tweet.tweetId == tweetId) {
                tweet.message = message;
            }
        }

    }

    public boolean validUser(String email, String password) {
        for (User user : portfolio.users) {
            if((user.email.equals(email) && (user.password.equals(password)))) {
                Log.v("validuser", user.email + "successfully logged in");
                this.currentUserId = user.userId;//sets the global variable to current users id
                return true;
            }
        }
        return false;
    }
}
