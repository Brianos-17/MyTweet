package org.wit.mytweet.api;

import org.wit.mytweet.models.Tweet;

import java.util.List;


public interface VolleyListener {
    void setList(List list);
    void setTweet(Tweet tweet);
}
