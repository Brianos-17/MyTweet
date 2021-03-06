package org.wit.mytweet.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import org.wit.mytweet.R;
import org.wit.mytweet.adapters.TweetListAdapter;
import org.wit.mytweet.api.TweetAPI;
import org.wit.mytweet.api.VolleyListener;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.Tweet;

import java.util.List;


//Help for this class retrieved from lab: https://wit-ictskills-2017.github.io/mobile-app-dev/topic07-a/book-coffeemate-lab-02/index.html#/03

public class TweetFragment extends Fragment implements
        AdapterView.OnItemClickListener, View.OnClickListener,
        AbsListView.MultiChoiceModeListener, VolleyListener {

    private static TweetListAdapter listAdapter;
    private ListView listView;
    public MyTweetApp app = MyTweetApp.getInstance();
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    public TweetFragment() {
    }

    public static TweetFragment newInstance() {
        return new TweetFragment();
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
        View v = inflater.inflate(R.layout.fragment_home, parent, false);
        listView = (ListView) v.findViewById(R.id.tweetList);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
        mSwipeRefreshLayout =  (SwipeRefreshLayout) v.findViewById(R.id.refresh_layout);
        setSwipeRefreshLayout();
        TweetAPI.getAll("/api/users/" + app.currentUserId + "/tweets", mSwipeRefreshLayout);

        return v;
    }

    protected void setSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TweetAPI.getAll("/api/users/" + app.currentUserId + "/tweets", mSwipeRefreshLayout);
            }
        });
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
        TweetAPI.attachListener(this);
        TweetAPI.getAll("/api/users/" + app.currentUserId + "/tweets", mSwipeRefreshLayout);
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

    //Method which comes from ListFragment and acts as onClick listener for List Items
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Bundle activityInfo = new Bundle();
        activityInfo.putString("tweetID", (String) v.getTag());//ensures we have the id of the selected tweet

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = EditFragment.newInstance(activityInfo);
        ft.replace(R.id.fragment_layout, fragment);
        ft.addToBackStack(null);
        ft.commit();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(app);
        builder.setMessage("Are you sure you want to delete this tweet?\n\n" + tweet.message);
        builder.setCancelable(true);//allow users click out of dialog box

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                TweetAPI.delete("/api/tweet/" + tweet._id);
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

    //Method for deleting multiple tweets in action mode
    private void deleteMultiTweets(ActionMode actionMode){
        for(int i = listAdapter.getCount() -1; i >= 0; i --) {
            if(listView.isItemChecked(i)){
                Log.v("deletetweet", "Deleting tweet: " + (listAdapter.getItem(i))._id);
                TweetAPI.delete("/api/tweet/" + (listAdapter.getItem(i))._id);
                listAdapter.tweetList.remove(listAdapter.getItem(i));//updates the adapter too to provide instant feedback
            }
        }
        actionMode.finish();
        listAdapter.notifyDataSetChanged();
    }

    //Method to delete all tweets a user has
    public void deleteAllTweets() {
        for(int i = listAdapter.getCount() -1; i >= 0; i--){
            TweetAPI.delete("/api/tweet");
            listAdapter.tweetList.remove(listAdapter.getItem(i));//updates the adapter too to provide instant feedback
            listAdapter.notifyDataSetChanged(); // refresh adapter
            Log.v("deletetweet", "Deleting all tweets");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        TweetAPI.detachListener();
    }
}