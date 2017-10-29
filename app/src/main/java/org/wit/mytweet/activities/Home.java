package org.wit.mytweet.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.fragments.TweetFragment;

public class Home extends Base{

    private ImageView addTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addTweet = (ImageView) findViewById(R.id.addTweet);
        addTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTweetButtonPressed(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuSettings:
                Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuClear:
                Toast.makeText(this, "Tweet Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuTweet:
                startActivity(new Intent(this, Add.class));
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        tweetFragment = TweetFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.fragment_layout, tweetFragment).commit();
    }

    public void addTweetButtonPressed(View view) {
        startActivity(new Intent(this, Add.class));
    }
}
