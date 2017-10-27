package org.wit.mytweet.models;


import android.util.Log;

import org.wit.mytweet.main.MyTweetApp;

import java.util.ArrayList;
import java.util.List;

//Info for this class retrieved from lecture: https://wit-ictskills-2017.github.io/mobile-app-dev/topic05-a/talk-3-fileio-in-myrent/d-fileio-in-myrent.pdf

public class Portfolio {

    public PortfolioSerializer serializer;

    public Portfolio(PortfolioSerializer serializer, List<User> users) {
        this.serializer = serializer;
        try {
            users = serializer.loadUsers(users);
            Log.v("i/o", "Users loaded on app start:" + users.toString());
        } catch (Exception e){
            Log.v("i/o", "Error loading users: "+ e.getMessage());
            users = new ArrayList<>();
        }
    }

    public boolean saveUsers(List<User> users) {
        try{
            serializer.saveUsers(users);
            Log.v("i/o", "Users saved to file" + users);
            return true;
        } catch (Exception e) {
            Log.v("i/o", "Error saving users: " + e.getMessage());
            return false;
        }
    }

    public boolean saveTweets(List<Tweet> tweetList) {
        try{
            serializer.saveTweets(tweetList);
            Log.v("i/o", "Tweets saved to file" + tweetList);
            return true;
        } catch (Exception e) {
            Log.v("i/o", "Error saving tweets: " + e.getMessage());
            return false;
        }
    }

    public boolean loadUsers(List<User> users) {
        try{
            serializer.loadUsers(users);
            Log.v("i/o", "Users loaded" + users);
            return true;
        } catch (Exception e) {
            Log.v("i/o", "Error loading users: " + e.getMessage());
            return false;
        }
    }

    public boolean loadTweets(List<Tweet> tweetList) {
        try{
            serializer.loadTweets(tweetList);
            Log.v("i/o", "Tweets loaded" + tweetList);
            return true;
        } catch (Exception e) {
            Log.v("i/o", "Error loading tweets: " + e.getMessage());
            return false;
        }
    }


}
