package org.wit.mytweet.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.wit.mytweet.R;
import org.wit.mytweet.adapters.TweetListAdapter;
import org.wit.mytweet.api.TweetAPI;
import org.wit.mytweet.api.VolleyListener;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.Tweet;

import java.util.List;


public class FollowFragment extends Fragment implements VolleyListener, View.OnClickListener {

    private static TweetListAdapter listAdapter;
    private ListView listView;
    public MyTweetApp app = MyTweetApp.getInstance();
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    public FollowFragment() {

    }

    public static FollowFragment newInstance() {
        return new FollowFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TweetAPI.attachListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        TweetAPI.detachListener();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        TweetAPI.attachListener(this);
        TweetAPI.getAll("/api/users/" + app.currentUserId + "/followedTweets", mSwipeRefreshLayout);
    }

    @Override
    public void onPause() {
        super.onPause();
        TweetAPI.detachListener();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, parent, false);
        listView = (ListView) v.findViewById(R.id.tweetList);
        mSwipeRefreshLayout =  (SwipeRefreshLayout) v.findViewById(R.id.refresh_layout);
        setSwipeRefreshLayout();
        TweetAPI.getAll("/api/users/" + app.currentUserId + "/followedTweets", mSwipeRefreshLayout);

        return v;
    }

    protected void setSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TweetAPI.getAll("/api/users/" + app.currentUserId + "/followedTweets", mSwipeRefreshLayout);
            }
        });
    }

    @Override
    public void setList(List list) {
        app.tweetList = list;
        updateUI();
    }

    @Override
    public void setTweet(Tweet tweet) {

    }

    public void updateUI() {
        listAdapter = new TweetListAdapter(getActivity(), this, app.tweetList);
        setListView(listView);
        listAdapter.notifyDataSetChanged();
    }

    public void setListView(ListView listview) {
        listview.setAdapter (listAdapter);
    }

    @Override
    public void onClick(View v) {

    }

}
