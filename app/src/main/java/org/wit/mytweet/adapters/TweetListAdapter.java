package org.wit.mytweet.adapters;

import android.animation.TimeAnimator;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import org.wit.mytweet.R;
import org.wit.mytweet.models.Tweet;

import java.util.List;

//Help for this class retrieved from lab: https://wit-ictskills-2017.github.io/mobile-app-dev/topic07-a/book-coffeemate-lab-02/index.html#/03


public class TweetListAdapter extends ArrayAdapter<Tweet> {
    private Context context;
    private OnClickListener deleteListener;
    public List<Tweet> tweetList;

    public TweetListAdapter(Context context, OnClickListener deleteListener, List<Tweet> tweetList){
        super(context, R.layout.content_timeline_item, tweetList);
        this.context = context;
        this.deleteListener = deleteListener;
        this.tweetList = tweetList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TimelineItem item = new TimelineItem(context, parent, deleteListener, tweetList.get(position));
        return item.view;
    }

    public List<Tweet> getTweetList() {
        return  this.tweetList;
    }

    @Override
    public int getCount() {
        return tweetList.size();
    }

    @Override
    public Tweet getItem(int position) {
        return tweetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getPosition(Tweet tweet) {
        return tweetList.indexOf(tweet);
    }
}

