package org.wit.mytweet.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import org.wit.mytweet.R;

import java.text.DateFormat;
import java.util.Date;

public class Add extends AppCompatActivity {

    private TextView characterCount;
    private EditText newTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        TextView tweetDate = (TextView) findViewById(R.id.tweetDate);
        characterCount = (TextView) findViewById(R.id.characterCount);
        characterCount.setText(String.valueOf(140));//Sets value of TextView to 140
        newTweet = (EditText) findViewById(R.id.newTweet);
        //Code to get today's date in form of string and pass to tweetDate TextView
        //Retrieved from: https://stackoverflow.com/questions/2271131/display-the-current-time-and-date-in-an-android-application
        String currentDate = DateFormat.getDateTimeInstance().format(new Date());
        tweetDate.setText(currentDate);
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
}
