package org.wit.mytweet.models;


import android.util.Log;

import java.util.ArrayList;

public class Portfolio {

    public ArrayList<User> users;
    public PortfolioSerializer serializer;

    public Portfolio(PortfolioSerializer serializer) {
        this.serializer = serializer;
        try {
            users = serializer.loadUsers();
        } catch (Exception e){
            Log.v("error", "Error loading users: "+ e.getMessage());
            users = new ArrayList<>();
        }
    }

    public boolean saveUsers() {
        try{
            serializer.saveUsers(users);
            Log.v("save", "Users saved to file");
            return true;
        } catch (Exception e) {
            Log.v("error", "Error saving users: " + e.getMessage());
            return false;
        }
    }
}
