package org.wit.mytweet.activities;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.models.Tweet;

public class Edit extends Base{

    private TextView characterCount, tweetDate;
    private EditText editedTweet;
    private Button editTweet;
    private Tweet tweetToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        activityInfo = getIntent().getExtras();
        tweetToEdit = findTweet(activityInfo.getInt("tweetId"));

        editTweet = (Button) findViewById(R.id.editTweet);
        tweetDate = (TextView) findViewById(R.id.tweetDate);
        characterCount = (TextView) findViewById(R.id.characterCount);
        editedTweet = (EditText) findViewById(R.id.editedTweet);
        characterCount.setText(String.valueOf(140));
        tweetDate.setText(tweetToEdit.date);

        editTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                editTweet(view);
            }
        });

        editedTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                characterCount.setText(String.valueOf(140 - (editedTweet.getText().toString().length())));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        app.portfolio.saveTweets();
    }

    private Tweet findTweet(int tweetId){
        for (Tweet tweet: app.portfolio.tweetList){
            if (tweet.tweetId == tweetId) {
                Log.v("itemcheck", "In the Edit class Im working on tweetId: " +tweet.tweetId);
                return tweet;
            }
        }
        return null;
    }

    public void editTweet(View view) {
        String message = editedTweet.getText().toString();
        String date = tweetDate.getText().toString();
        if(message.length() > 0) {
            tweetToEdit.message = message;
            goToActivity(this, Home.class, null);
        } else {
            Toast.makeText(this, "Oops, looks like you haven't said anything!", Toast.LENGTH_SHORT).show();
        }
    }
}
