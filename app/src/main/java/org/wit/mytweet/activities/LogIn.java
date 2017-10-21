package org.wit.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;

public class LogIn extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticate(view);
            }
        });
    }

    public void authenticate(View view) {
        String email = ((TextView) findViewById(R.id.loginEmail)).getText().toString();
        String password = ((TextView) findViewById(R.id.loginPassword)).getText().toString();

        if(app.validUser(email, password)) {
            startActivity(new Intent(this, Home.class));
        } else
            Toast.makeText(this, "Email and Password do not match. \nPlease try again", Toast.LENGTH_SHORT).show();

    }
}
