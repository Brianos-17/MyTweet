package org.wit.mytweet.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.api.TweetAPI;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.User;

public class SignUp extends FragmentActivity {

    public MyTweetApp app = MyTweetApp.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });
    }

    public void register(View view) {
        String firstName = ((TextView) findViewById(R.id.signupFirstName)).getText().toString();
        String lastName = ((TextView) findViewById(R.id.signupLastName)).getText().toString();
        String email = ((TextView) findViewById(R.id.signupEmail)).getText().toString();
        String password = ((TextView) findViewById(R.id.signupPassword)).getText().toString();

        if((firstName.isEmpty()) || (lastName.isEmpty()) || (email.isEmpty()) || (password.isEmpty())) {
            Toast.makeText(this, "You've left some blank spaces!", Toast.LENGTH_SHORT).show();
        } else {
            User newUser = new User(firstName, lastName, email, password);
            app.addUser(newUser);//Persists in JSON
//            app.dbManager.insertUser(newUser);//Persists in SQL
            TweetAPI.postUser("/api/users", newUser);//Persists in mlab
            startActivity(new Intent(this, Login.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
