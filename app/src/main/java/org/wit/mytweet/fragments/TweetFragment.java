package org.wit.mytweet.fragments;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import org.wit.mytweet.R;
import org.wit.mytweet.activities.Base;
import org.wit.mytweet.activities.Edit;
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
        //cycles through each tweet in the tweetList and pulls out the ones written by the current user
        filteredList = new UserTweetFilter();
        List<Tweet> newList = filteredList.filter(activity.app.currentUserId, activity.app.tweetList);

        listAdapter = new TweetListAdapter(activity, this, newList);
        setListAdapter(listAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() instanceof Tweet) {
            deleteTweet((Tweet) view.getTag());
        }
    }

    //Method for deleting single tweet
    public void deleteTweet(final Tweet tweet) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to delete this tweet?\n\n" + tweet.message);
        builder.setCancelable(true);//allow users click out of dialog box

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                activity.app.tweetList.remove(tweet); // remove from our list
                listAdapter.tweetList.remove(tweet); // update adapters data
                listAdapter.notifyDataSetChanged(); // refresh adapter
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}