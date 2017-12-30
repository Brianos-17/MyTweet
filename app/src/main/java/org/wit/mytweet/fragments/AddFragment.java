package org.wit.mytweet.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.activities.Base;
import org.wit.mytweet.activities.Home;
import org.wit.mytweet.models.Tweet;

import java.text.DateFormat;
import java.util.Date;

import static org.wit.helpers.ContactHelper.getContact;
import static org.wit.helpers.ContactHelper.getEmail;
import static org.wit.helpers.ContactHelper.sendEmail;
import static org.wit.helpers.IntentHelper.selectContact;

public class AddFragment extends Fragment {
    private TextView characterCount, tweetDate;
    private EditText newTweet;
    private Button contactButton, emailButton, sendTweet;
    private Intent data;
    private String emailAddress;
    private static final int REQUEST_CONTACT = 1;
    private Base activity;

    public AddFragment() {
        //Empty Constructor
    }

    public static AddFragment newInstance() {
        return new AddFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);

        characterCount = (TextView) v.findViewById(R.id.characterCount);
        sendTweet = (Button) v.findViewById(R.id.sendTweet);
        newTweet = (EditText) v.findViewById(R.id.newTweet);
        tweetDate = (TextView) v.findViewById(R.id.tweetDate);
        contactButton = (Button) v.findViewById(R.id.contactButton);
        emailButton = (Button) v.findViewById(R.id.emailButton);

        characterCount.setText(String.valueOf(140));//Sets value of TextView to 140
        //Code to get today's date in form of string and pass to tweetDate TextView
        //Retrieved from: https://stackoverflow.com/questions/2271131/display-the-current-time-and-date-in-an-android-application
        String currentDate = DateFormat.getDateTimeInstance().format(new Date());
        tweetDate.setText(currentDate);

        sendTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTweet(view);
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

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void addNewTweet(View view) {
        String message = newTweet.getText().toString();
        String date = tweetDate.getText().toString();
        String userId = activity.app.currentUserId;
        if (message.length() > 0) {
            Tweet tweet = new Tweet(message, date, userId);
            activity.app.dbManager.insertTweet(tweet);
            Log.v("tweetcheck", "New Tweet added:" + message);
            Log.v("tweetcheck", "This tweet belongs to the user" + activity.app.currentUserId);
            Toast.makeText(getActivity(), "Tweet sent!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), Home.class);
            getActivity().startActivity(intent); // Brings user back to home class
        } else {
            Toast.makeText(getActivity(), "Oops, looks like you haven't said anything!", Toast.LENGTH_SHORT).show();
        }
    }

    public void contactButtonPressed(View view) {
        selectContact(getActivity(), REQUEST_CONTACT);
    }

    public void emailButtonPressed(View view) {
        sendEmail(getActivity(), emailAddress, this.getString(R.string.latestTweetHeader), newTweet.getText().toString());
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
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            //We can request the permission.
            ActivityCompat.requestPermissions(getActivity(),
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
        String name = getContact(getActivity(), data);
        emailAddress = getEmail(getActivity(), data);
        contactButton.setText(name + " : " + emailAddress);
    }
}
