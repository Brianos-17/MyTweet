package org.wit.mytweet.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.wit.mytweet.R;

public class Home extends AppCompatActivity{

    private ImageButton addTweet, settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addTweet = (ImageButton) findViewById(R.id.addTweet);
        settingsButton = (ImageButton) findViewById(R.id.settingsButton);

        addTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTweetButtonPressed(view);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void addTweetButtonPressed(View view) {
        startActivity(new Intent(this, Add.class));
    }
}
