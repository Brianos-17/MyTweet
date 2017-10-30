package org.wit.mytweet.activities;

import android.os.Bundle;

import org.wit.mytweet.R;
import org.wit.mytweet.fragments.TweetFragment;

/**
 * Created by Brian on 30/10/2017.
 */

public class GlobalTimeline extends Base {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_timeline);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tweetFragment = TweetFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.fragment_layout_gt, tweetFragment).commit();
    }
}
