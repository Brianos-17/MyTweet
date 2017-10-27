package org.wit.mytweet.models;


import org.json.JSONException;
import org.json.JSONObject;
import java.util.UUID;

import java.io.Serializable;

public class Tweet implements Serializable {

    public String message;
    public String date;
    public String tweetId;
    public String userId;

    //variables fields included for persistence to and from JSON
    private static final String JSON_MESSAGE = "message";
    private static final String JSON_DATE = "date";
    private static final String JSON_USERID = "userId";

    public Tweet(String message, String date, String userId) {
        this.message = message;
        this.date = date;
        this.userId = userId;
        UUID uuid = UUID.randomUUID();
        this.tweetId = uuid.toString();
    }

    //Write a User Object to Json format
    public Tweet(JSONObject json) throws JSONException {
        message = json.getString(JSON_MESSAGE);
        date = json.getString(JSON_DATE);
        userId = json.getString(JSON_USERID);
    }

    //Reads a json object as a User object
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_MESSAGE, message);
        json.put(JSON_DATE, date);
        json.put(JSON_USERID, userId);
        return json;
    }
}
