package org.wit.helpers;


import org.wit.mytweet.models.Tweet;
import java.util.ArrayList;
import java.util.List;

public class UserTweetFilter {

    public UserTweetFilter(){
    }

    public List<Tweet> filter(String currentUserId, List<Tweet> tweetList){
        List<Tweet> filteredTweets = new ArrayList<>();
        for(Tweet tweet: tweetList){
            if(tweet.user.equals(currentUserId)){
                filteredTweets.add(tweet);
            }
        }
        return filteredTweets;
    }
}
