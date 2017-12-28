package org.wit.mytweet.activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import static org.wit.helpers.ContactHelper.getContact;
import static org.wit.helpers.ContactHelper.getEmail;
import static org.wit.helpers.ContactHelper.sendEmail;
import static org.wit.helpers.IntentHelper.selectContact;

public class Edit extends Base{

    private TextView characterCount, tweetDate;
    private EditText editedTweet;
    private Button editTweet, contactButton, emailButton;
    private Tweet tweetToEdit;
    private Intent data;
    private String emailAddress;
    private static final int REQUEST_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        activityInfo = getIntent().getExtras();
        tweetToEdit = findTweet(activityInfo.getInt("tweetID"));

        editTweet = (Button) findViewById(R.id.sendTweet);
        tweetDate = (TextView) findViewById(R.id.tweetDate);
        characterCount = (TextView) findViewById(R.id.characterCount);
        editedTweet = (EditText) findViewById(R.id.newTweet);
        contactButton = (Button) findViewById(R.id.contactButton);
        emailButton = (Button) findViewById(R.id.emailButton);

        editedTweet.setText(tweetToEdit.message);
        characterCount.setText(String.valueOf(140 - tweetToEdit.message.length()));
        tweetDate.setText(tweetToEdit.date);

        editTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                editTweet(view);
            }
        });
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactButtonPressed(view);
            }
        });
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailButtonPressed(view);
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
        if(message.length() > 0) {
            app.editTweet(message, tweetToEdit.tweetId);
            app.portfolio.saveTweets();
            Toast.makeText(this, "Tweet has been edited", Toast.LENGTH_SHORT).show();
            goToActivity(this, oldHome.class, null);
        } else {
            Toast.makeText(this, "Oops, looks like you haven't said anything!", Toast.LENGTH_SHORT).show();
        }
    }


    public void contactButtonPressed(View view) {
        selectContact(this, REQUEST_CONTACT);
    }

    public void emailButtonPressed(View view) {
        sendEmail(this, emailAddress, this.getString(R.string.latestTweetHeader), editedTweet.getText().toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONTACT:
                this.data = data;
                checkContactsReadPermission();
                break;
        }
    }

    //https://developer.android.com/training/permissions/requesting.html
    private void checkContactsReadPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            //We can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACT);
        } else {
            //We already have permission, so go head and read the contact
            readContact();
        }
    }

    //https://developer.android.com/training/permissions/requesting.html
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    readContact();
                }
            }
        }
    }

    private void readContact() {
        String name = getContact(this, data);
        emailAddress = getEmail(this, data);
        contactButton.setText(name + " : " + emailAddress);
    }
}
