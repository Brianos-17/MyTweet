package org.wit.mytweet.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.User;

public class SignUp extends Base{


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

        MyTweetApp app = (MyTweetApp) getApplication();

        if((firstName.isEmpty()) || (lastName.isEmpty()) || (email.isEmpty()) || (password.isEmpty())) {
            Toast.makeText(this, "You've left some blank spaces!", Toast.LENGTH_SHORT).show();
        } else {
            User newUser = new User(firstName, lastName, email, password);
            app.addUser(newUser);
            startActivity(new Intent(this, LogIn.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        app.portfolio.saveUsers(app.users);
        app.portfolio.loadUsers(app.users);
    }
}
