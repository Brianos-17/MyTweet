package org.wit.mytweet.models;


import android.util.Log;

import org.wit.mytweet.main.MyTweetApp;

import java.util.ArrayList;
import java.util.List;

//Info for this class retrieved from lecture: https://wit-ictskills-2017.github.io/mobile-app-dev/topic05-a/talk-3-fileio-in-myrent/d-fileio-in-myrent.pdf

public class Portfolio {

    public List<Tweet> tweetList;
    public List<User> users;
    public PortfolioSerializer serializer;

    public Portfolio(PortfolioSerializer serializer) {
        this.serializer = serializer;
        try {
            tweetList = serializer.loadTweets();
            users = serializer.loadUsers();
        } catch (Exception e){
            Log.v("i/o", "Error loading users: "+ e.getMessage());
        }
    }

    public boolean saveUsers() {
        try{
            serializer.saveUsers(users);
            Log.v("i/o", "Users saved to file" + users);
            return true;
        } catch (Exception e) {
            Log.v("i/o", "Error saving users: " + e.getMessage());
            return false;
        }
    }

    public boolean saveTweets() {
        try{
            serializer.saveTweets(tweetList);
            Log.v("i/o", "Tweets saved to file" + tweetList);
            return true;
        } catch (Exception e) {
            Log.v("i/o", "Error saving tweets: " + e.getMessage());
            return false;
        }
    }

    public boolean loadUsers() {
        try{
            this.users = serializer.loadUsers();
            Log.v("i/o", "Users loaded" + users);
            return true;
        } catch (Exception e) {
            Log.v("i/o", "Error loading users: " + e.getMessage());
            return false;
        }
    }

    public boolean loadTweets() {
        try{
            this.tweetList = serializer.loadTweets();
            Log.v("i/o", "Tweets loaded" + tweetList);
            return true;
        } catch (Exception e) {
            Log.v("i/o", "Error loading tweets: " + e.getMessage());
            return false;
        }
    }
}