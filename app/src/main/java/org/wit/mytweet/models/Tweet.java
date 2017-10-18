package org.wit.mytweet.models;


import java.io.Serializable;

public class Tweet implements Serializable {

    public String message;
    public String date;

    public Tweet(String message, String date) {
        this.message = message;
        this.date = date;
    }
}
