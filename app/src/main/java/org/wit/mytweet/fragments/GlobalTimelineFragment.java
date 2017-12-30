package org.wit.mytweet.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.wit.mytweet.R;
import org.wit.mytweet.activities.Base;
import org.wit.mytweet.adapters.TweetListAdapter;
import org.wit.mytweet.models.Tweet;

import java.util.List;


public class GlobalTimelineFragment extends ListFragment
        implements View.OnClickListener {

    private static TweetListAdapter listAdapter;
    private Base activity;
    private ListView listView;

    public GlobalTimelineFragment() {
        // Required empty public constructor
    }

    public static GlobalTimelineFragment newInstance() {
        return new GlobalTimelineFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Base) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Allows fragment to access menu
        listAdapter = new TweetListAdapter(activity, this, activity.app.dbManager.getAllTweets());
        setListAdapter(listAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v  = super.onCreateView(inflater, parent, savedInstanceState);
        listView = (ListView) v.findViewById(android.R.id.list);
        return v;
    }

    @Override
    public void onClick(View v) {

    }
}
