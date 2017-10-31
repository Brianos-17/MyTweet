package org.wit.mytweet.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.wit.mytweet.R;

public class Settings extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView settingsUserName = (TextView) findViewById(R.id.settingsUserName);
        TextView settingsPassword = (TextView) findViewById(R.id.settingsPassword);

        settingsUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                userNamePressed(view);
            }
        });
        settingsPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordPressed(view);
            }
        });
    }
}
