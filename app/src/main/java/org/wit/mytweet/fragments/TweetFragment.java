package org.wit.mytweet.fragments;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import org.wit.mytweet.activities.Base;
import org.wit.mytweet.adapters.TweetListAdapter;
import org.wit.mytweet.adapters.UserTweetFilter;
import org.wit.mytweet.models.Tweet;

import java.util.List;


//Help for this class retrieved from lab: https://wit-ictskills-2017.github.io/mobile-app-dev/topic07-a/book-coffeemate-lab-02/index.html#/03

public class TweetFragment extends ListFragment implements OnClickListener {

    protected static TweetListAdapter listAdapter;
    protected UserTweetFilter filteredList;
    protected Base activity;

    public TweetFragment() {
    }

    public static TweetFragment newInstance() {
        return new TweetFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Base) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        filteredList = new UserTweetFilter();
        List<Tweet> newList = filteredList.filter(activity.app.currentUserId, activity.app.tweetList);
        listAdapter = new TweetListAdapter(activity, this, newList);
        setListAdapter (listAdapter);
    }

    @Override
    public void onClick(View view) {

    }
}
