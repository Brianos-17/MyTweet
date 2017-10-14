package org.wit.mytweet.models;


import java.util.Date;

public class Tweet {

    public String message;
    public Date date;

    public Tweet(String message, Date date) {
        this.message = message;
        this.date = date;
    }
}
