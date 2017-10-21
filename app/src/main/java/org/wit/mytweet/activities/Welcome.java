package org.wit.mytweet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.wit.mytweet.R;

public class Welcome extends Base implements View.OnClickListener {

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
        app.portfolio.loadUsers(app.users);
        app.portfolio.loadTweets(app.tweetList);
    }

    public void signupButtonPressed(View view) {
        startActivity(new Intent(this, SignUp.class));
    }

    public void loginButtonPressed(View view) {
        startActivity(new Intent(this, LogIn.class));
    }

    @Override
    public void onClick(View view) {
    }

}
