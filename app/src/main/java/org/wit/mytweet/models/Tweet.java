package org.wit.mytweet.models;


import java.io.Serializable;

public class Tweet implements Serializable {

    public String message;
    public String date;
    public static int autoId = 1;
    public int tweetId;

    public Tweet(String message, String date) {
        this.message = message;
        this.date = date;
        this.tweetId = autoId++;
    }
}
