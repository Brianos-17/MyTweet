package org.wit.mytweet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wit.mytweet.R;
import org.wit.mytweet.models.Tweet;

public class TimelineItem {
    View view;

    public TimelineItem(Context context, ViewGroup parent, OnClickListener deleteListener, Tweet tweet) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.content_timeline_item, parent, false);
        view.setId(tweet.tweetId);
        updateTimeline(tweet);
    }

    private void updateTimeline(Tweet tweet) {
        if(tweet.message.length() > 20) {
            ((TextView) view.findViewById(R.id.tweetSubstring)).setText(tweet.message.substring(0, 20));
        } else {
            ((TextView) view.findViewById(R.id.tweetSubstring)).setText(tweet.message);
        }
        ((TextView) view.findViewById(R.id.tweetDate)).setText(tweet.date);
    }
}
