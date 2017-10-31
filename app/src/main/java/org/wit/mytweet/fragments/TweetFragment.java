package org.wit.mytweet.fragments;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.activities.Base;
import org.wit.mytweet.activities.Edit;
import org.wit.mytweet.activities.GlobalTimeline;
import org.wit.mytweet.activities.Home;
import org.wit.mytweet.adapters.TweetListAdapter;
import org.wit.helpers.UserTweetFilter;
import org.wit.mytweet.models.Tweet;

import java.util.List;


//Help for this class retrieved from lab: https://wit-ictskills-2017.github.io/mobile-app-dev/topic07-a/book-coffeemate-lab-02/index.html#/03

public class TweetFragment extends ListFragment implements OnClickListener, AbsListView.MultiChoiceModeListener {

    private static TweetListAdapter listAdapter;
    private UserTweetFilter filteredList;
    private Base activity;
    private ListView listView;

    public TweetFragment() {
    }

    public static TweetFragment newInstance() {
        return new TweetFragment();
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

        //Toggles list view between global timeline of every tweet and personalised timeline for current user
        if(getActivity() instanceof GlobalTimeline) {
            listAdapter = new TweetListAdapter(activity, this, activity.app.portfolio.tweetList);
            setListAdapter(listAdapter);
        } else {
            //cycles through each tweet in the tweetList and pulls out the ones written by the current user
            filteredList = new UserTweetFilter();
            List<Tweet> newList = filteredList.filter(activity.app.currentUserId, activity.app.portfolio.tweetList);
            listAdapter = new TweetListAdapter(activity, this, newList);
            setListAdapter(listAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v  = super.onCreateView(inflater, parent, savedInstanceState);
        listView =(ListView) v.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() instanceof Tweet) {
            deleteTweet((Tweet) view.getTag());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((TweetListAdapter) getListAdapter()).notifyDataSetChanged();
    }

    //Method which comes from ListFragment and acts as onClick listener for List Items
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Bundle activityInfo = new Bundle();
        activityInfo.putInt("tweetID", v.getId());//ensures we have the id of the selected tweet
        Log.v("itemcheck", "Item pressed: " + v.getId());

        Intent goEdit = new Intent(getActivity(), Edit.class);
        goEdit.putExtras(activityInfo);
        getActivity().startActivity(goEdit);
    }

    /* ************ MultiChoiceModeListener methods (begin) *********** */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.menu_delete_multi_tweet, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.menu_delete_multi_tweet:
                deleteMultiTweets(actionMode);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {

    }
    /* ************ MultiChoiceModeListener methods (end) *********** */

    //Method for deleting single tweet
    public void deleteTweet(final Tweet tweet) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to delete this tweet?\n\n" + tweet.message);
        builder.setCancelable(true);//allow users click out of dialog box

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                activity.app.portfolio.tweetList.remove(tweet); // remove from our list
                listAdapter.tweetList.remove(tweet); // update adapters data
                listAdapter.notifyDataSetChanged(); // refresh adapter
                activity.app.portfolio.saveTweets();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //Method for deleting multiple tweets in action mode
    private void deleteMultiTweets(ActionMode actionMode){
        for(int i = listAdapter.getCount() -1; i >= 0; i --) {
            if(listView.isItemChecked(i)){
                Log.v("deletetweet", "Deleting tweet: " + listAdapter.getItemId(i));
                activity.app.portfolio.tweetList.remove(listAdapter.getItem(i));
                listAdapter.tweetList.remove(listAdapter.getItem(i));//updates the adapter too to provide instant feedback
                activity.app.portfolio.saveTweets();
            }
        }
        actionMode.finish();
        listAdapter.notifyDataSetChanged();
    }

    //Method to delete all tweets a user has
    public void deleteAllTweets() {
        for(int i = listAdapter.getCount() -1; i >= 0; i--){
            activity.app.portfolio.tweetList.remove(listAdapter.getItem(i));
            listAdapter.tweetList.remove(listAdapter.getItem(i));//updates the adapter too to provide instant feedback
            listAdapter.notifyDataSetChanged(); // refresh adapter
            activity.app.portfolio.saveTweets();
            Log.v("deletetweet", "Deleting all tweets");
        }
    }
}