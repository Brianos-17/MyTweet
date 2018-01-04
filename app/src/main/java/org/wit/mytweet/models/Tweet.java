package org.wit.mytweet.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Random;

public class Tweet  {

    public String message;
    public String date;
    public String user;
    public String _id;
    public Marker marker = new Marker();

    //variables fields included for persistence to and from JSON
    private static final String JSON_MESSAGE = "message";
    private static final String JSON_DATE = "date";
    private static final String JSON_USER = "user";
    private static final String JSON_ID = "tweetId";

    public Tweet() {
        //Empty constructor used for DB
    }

    public Tweet(String message, String date, String user) {
        this.message = message;
        this.date = date;
        this.user = user;
        this.marker.coords.latitude = 0;
        this.marker.coords.longitude = 0;
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
        user = json.getString(JSON_USER);
//        _id = json.getInt(JSON_ID);
    }

    //Reads a json object as a User object
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_MESSAGE, message);
        json.put(JSON_DATE, date);
        json.put(JSON_USER, user);
//        json.put(JSON_ID, _id);
        return json;
    }
}
