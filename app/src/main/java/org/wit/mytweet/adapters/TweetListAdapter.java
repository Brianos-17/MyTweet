package org.wit.mytweet.adapters;

import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;

import org.wit.mytweet.models.Tweet;

import java.util.List;


public class TweetListAdapter extends ArrayAdapter<Tweet> {
    private Context context;
    private OnClickListener deleteListener;
    public List<Tweet> tweetList;

    public TweetListAdapter(Context xontext, OnClickListener deleteListener, List<Tweet> tweetList){

    }
}