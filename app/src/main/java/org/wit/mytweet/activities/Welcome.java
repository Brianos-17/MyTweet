package org.wit.mytweet.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;

public class Welcome extends FragmentActivity implements View.OnClickListener {

    public MyTweetApp app = MyTweetApp.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupButtonPressed(view);
            }
        });
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonPressed(view);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        app.portfolio.loadUsers();
        app.portfolio.loadTweets();
    }

    public void signupButtonPressed(View view) {
        startActivity(new Intent(this, SignUp.class));
    }

    public void loginButtonPressed(View view) {
        startActivity(new Intent(this, Login.class));
    }

    @Override
    public void onClick(View view) {
    }

}