package org.wit.mytweet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.wit.mytweet.R;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signupButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupButtonPressed(view);
            }
        });
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }
}
