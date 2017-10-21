package org.wit.mytweet.models;


import java.io.Serializable;

public class Tweet implements Serializable {

    public String message;
    public String date;
    private static int autoId = 1;
    public int tweetId;
    public String userId;

    public Tweet(String message, String date, String userId) {
        this.message = message;
        this.date = date;
        this.userId = userId;
        this.tweetId = autoId++;
    }
}
