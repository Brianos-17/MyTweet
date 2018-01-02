package org.wit.mytweet.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.wit.mytweet.R;
import org.wit.mytweet.adapters.TweetListAdapter;
import org.wit.mytweet.api.TweetAPI;
import org.wit.mytweet.main.MyTweetApp;


public class GlobalTimelineFragment extends ListFragment
        implements View.OnClickListener {

    private static TweetListAdapter listAdapter;
    private ListView listView;
    public MyTweetApp app = MyTweetApp.getInstance();
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    public GlobalTimelineFragment() {
        // Required empty public constructor
    }

    public static GlobalTimelineFragment newInstance() {
        return new GlobalTimelineFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Allows fragment to access menu
        listAdapter = new TweetListAdapter(getActivity(), this, app.dbManager.getAllTweets());
        setListAdapter(listAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v  = super.onCreateView(inflater, parent, savedInstanceState);
        listView = (ListView) v.findViewById(android.R.id.list);
//        mSwipeRefreshLayout =   (SwipeRefreshLayout) v.findViewById(R.id.coffee_swipe_refresh_layout);
//        setSwipeRefreshLayout();
//        TweetAPI.getAll("/api/tweet", mSwipeRefreshLayout);
        return v;
    }

    @Override
    public void onClick(View v) {

    }
}
