package org.wit.mytweet.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import org.wit.mytweet.R;


public class Splash extends Activity {

    private static final int 	SPLASH_DURATION = 5000; // 2 seconds
    private boolean backButtonPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() { //ensures the splash screen wont show again if user presses back
                finish();
                if(!backButtonPressed){
                    Intent intent = new Intent(Splash.this, Welcome.class);
                    backButtonPressed = true;
                    Splash.this.startActivity(intent);
                }
            }
        }, SPLASH_DURATION);
    }
}
