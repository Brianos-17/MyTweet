package org.wit.mytweet.fragments;


//Help for this class retrieved from lab: https://wit-ictskills-2017.github.io/mobile-app-dev/topic07-a/book-coffeemate-lab-02/index.html#/03

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View.OnClickListener;

import org.wit.mytweet.adapters.TweetListAdapter;

public class TweetFragment extends ListFragment implements OnClickListener {

    protected static TweetListAdapter listAdapter;

    public TweetFragment() {
    }

    public static TweetFragment newInstance() {
        TweetFragment fragment = new TweetFragment();
        return fragment;
    }


    @Override
    public void onAttach(Context context)
    {
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
        listAdapter = new TweetListAdapter(activity, this, app.coffeeList);
        setListAdapter (listAdapter);
    }
}
