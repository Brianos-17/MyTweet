package org.wit.mytweet.fragments;

import android.app.ListFragment;

public class TweetFragment extends ListFragment {

    public TweetFragment() {
        //Empty constructor
    }

    public static TweetFragment newInstance() {
        TweetFragment fragment = new TweetFragment();
        return fragment;
    }


}
