package org.wit.mytweet.models;


import android.util.Log;

import org.wit.mytweet.main.MyTweetApp;

import java.util.ArrayList;
import java.util.List;

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
}
