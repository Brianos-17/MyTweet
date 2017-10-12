package org.wit.mytweet.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.models.User;

public class SignUp extends AppCompatActivity{

    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });
    }

    public void register(View view) {
        TextView firstName = (TextView) findViewById(R.id.signupFirstName);
        TextView lastName = (TextView) findViewById(R.id.signupLastName);
        TextView email = (TextView) findViewById(R.id.signupEmail);
        TextView password = (TextView) findViewById(R.id.signupPassword);

        if((firstName != null) && (lastName != null) && (email != null) && (password != null)) {
            User newUser = new User(firstName.getText().toString(), lastName.getText().toString(),
                    email.getText().toString(), password.getText().toString());
        } else {
            Toast.makeText(this, "You've left some blank spaces!", Toast.LENGTH_SHORT).show();
        }
    }
}
