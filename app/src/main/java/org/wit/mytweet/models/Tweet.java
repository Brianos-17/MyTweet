package org.wit.mytweet.models;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Random;

public class Tweet implements Serializable {

    public String message;
    public String date;
    public int tweetId;
    public String userId;

    //variables fields included for persistence to and from JSON
    private static final String JSON_MESSAGE = "message";
    private static final String JSON_DATE = "date";
    private static final String JSON_USERID = "userId";
    private static final String JSON_TWEETID = "tweetId";

    public Tweet() {
        //Empty constructor used for DB
    }

    public Tweet(String message, String date, String userId) {
        this.message = message;
        this.date = date;
        this.userId = userId;
        this.tweetId = unsignedInt();
    }

    private int unsignedInt() {
        int id = 0;
        do {
            id = new Random().nextInt();
        } while (id <= 0); //Keeps generating a new number until it is a positive
        return id;
    }

    //Write a User Object to Json format
    public Tweet(JSONObject json) throws JSONException {
        message = json.getString(JSON_MESSAGE);
        date = json.getString(JSON_DATE);
        userId = json.getString(JSON_USERID);
        tweetId = json.getInt(JSON_TWEETID);
    }

    //Reads a json object as a User object
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_MESSAGE, message);
        json.put(JSON_DATE, date);
        json.put(JSON_USERID, userId);
        json.put(JSON_TWEETID, tweetId);
        return json;
    }
}
