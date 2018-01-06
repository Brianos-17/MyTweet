package org.wit.mytweet.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import org.wit.mytweet.R;
import org.wit.mytweet.adapters.TweetListAdapter;
import org.wit.mytweet.api.TweetAPI;
import org.wit.mytweet.api.VolleyListener;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.Tweet;

import java.util.List;


public class GlobalTimelineFragment extends Fragment
        implements View.OnClickListener,
        AdapterView.OnItemClickListener,
        AbsListView.MultiChoiceModeListener,
        VolleyListener {

    protected static TweetListAdapter listAdapter;
    protected ListView listView;
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
        TweetAPI.attachListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_global_timeline, parent, false);
        listView = (ListView) v.findViewById(R.id.tweetList);
        mSwipeRefreshLayout =  (SwipeRefreshLayout) v.findViewById(R.id.gt_refresh_layout);
        setSwipeRefreshLayout();
//        ImageView deleteTweet = (ImageView) v.findViewById(R.id.deleteTweet);
//        deleteTweet.setVisibility(View.INVISIBLE);
        TweetAPI.getAll("/api/tweet", mSwipeRefreshLayout);
        return v;
    }

    protected void setSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TweetAPI.getAll("/api/tweet", mSwipeRefreshLayout);
            }
        });
    }

    @Override
    public void onClick(View v) {
        //Enter code to view tweet but not be able to edit
    }

    @Override
    public void onResume() {
        super.onResume();
        TweetAPI.attachListener(this);
        TweetAPI.getAll("/api/tweet", mSwipeRefreshLayout);
    }

    @Override
    public void onPause() {
        super.onPause();
        TweetAPI.detachListener();
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
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listview.setMultiChoiceModeListener(this);
        listview.setAdapter (listAdapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        TweetAPI.detachListener();
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
