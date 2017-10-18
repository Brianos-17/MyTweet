package org.wit.mytweet.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.wit.mytweet.R;

import java.text.DateFormat;
import java.util.Date;

public class Add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        TextView tweetDate = (TextView) findViewById(R.id.tweetDate);
        //Code to get today's date in form of string and pass to tweetDate TextView
        String currentDate = DateFormat.getDateTimeInstance().format(new Date());
        tweetDate.setText(currentDate);
    }
}
