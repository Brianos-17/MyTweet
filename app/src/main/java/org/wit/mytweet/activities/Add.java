package org.wit.mytweet.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.Tweet;

import java.text.DateFormat;
import java.util.Date;

public class Add extends Base {

    private TextView characterCount, tweetDate;
    private EditText newTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button sendTweet = (Button) findViewById(R.id.sendTweet);
        tweetDate = (TextView) findViewById(R.id.tweetDate);
        characterCount = (TextView) findViewById(R.id.characterCount);
        newTweet = (EditText) findViewById(R.id.newTweet);

        characterCount.setText(String.valueOf(140));//Sets value of TextView to 140
        //Code to get today's date in form of string and pass to tweetDate TextView
        //Retrieved from: https://stackoverflow.com/questions/2271131/display-the-current-time-and-date-in-an-android-application
        String currentDate = DateFormat.getDateTimeInstance().format(new Date());
        tweetDate.setText(currentDate);

        sendTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                addNewTweet(view);
            }
        });

        //TextWatcher which counts down value of character count
        //Retrieved from: https://stackoverflow.com/questions/24110265/android-create-count-down-word-field-when-user-type-in-edittext
        newTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                characterCount.setText(String.valueOf(140 - (newTweet.getText().toString().length())));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void addNewTweet(View view) {
        String message = newTweet.getText().toString();
        String date = tweetDate.getText().toString();
        String userId = app.currentUserId;
        if(message.length() > 0) {
            Tweet tweet = new Tweet(message, date, userId);
            app.addTweet(tweet);
            Log.v("tweetcheck", "New Tweet added:" + message);
            Log.v("tweetcheck", "Tweet ID is " + tweet.tweetId);
            Log.v("tweetcheck", "This tweet belongs to the user" + app.currentUserId);
            goToActivity(this, Home.class, null);
        } else {
            Toast.makeText(this, "Oops, looks like you haven't said anything!", Toast.LENGTH_SHORT).show();
        }
    }
}
