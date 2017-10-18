package org.wit.mytweet.models;


import java.io.Serializable;
import java.util.Date;

public class Tweet implements Serializable {

    public String message;
    public Date date;

    public Tweet(String message, Date date) {
        this.message = message;
        this.date = date;
    }
}
