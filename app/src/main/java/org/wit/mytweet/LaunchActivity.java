package org.wit.mytweet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class LaunchActivity extends AppCompatActivity {

    private Button signupButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        signupButton = (Button) findViewById(R.id.signupButton);
        loginButton = (Button) findViewById(R.id.loginButton);
    }
}
