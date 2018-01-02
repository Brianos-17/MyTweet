package org.wit.mytweet.main;

import android.app.Application;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import org.wit.mytweet.db.DBManager;
import org.wit.mytweet.models.Portfolio;
import org.wit.mytweet.models.PortfolioSerializer;
import org.wit.mytweet.models.Tweet;
import org.wit.mytweet.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Brian on 14/10/2017.
 * Main App class, holds reference to portfolio that other classes use for persistence
 */

public class MyTweetApp extends Application {

    private static  final String FILENAME1 = "users.json";
    private static final String FILENAME2 = "tweets.json";
    public Portfolio portfolio;
    public String currentUserId;//variable introduced in order to associate tweets with specific users

//    public String currentUserId;//variable introduced in order to associate tweets with specific users
    public DBManager  dbManager = new DBManager(this);//Persist data in SQL
    /* Client used to interact with Google APIs. */
    public GoogleApiClient mGoogleApiClient;
    public GoogleSignInOptions mGoogleSignInOptions;
    public Location mCurrentLocation;

    public boolean signedIn = false;
    public String googleToken;
    public String googleName;
    public String googleMail;
    public String googlePhotoURL;
    public Bitmap googlePhoto;
    public int drawerID = 0;
    private static MyTweetApp mInstance;
    private RequestQueue mRequestQueue;
    public List <Tweet> tweetList = new ArrayList<>();
    public List <User> users = new ArrayList<>();
    public static final String TAG = MyTweetApp.class.getName();

    public void onCreate() {
        super.onCreate();
        Log.v("mytweet", "MyTweet App started");
        PortfolioSerializer serializer = new PortfolioSerializer(this, FILENAME1, FILENAME2);
        portfolio = new Portfolio(serializer);//passes the PortfolioSerializer and List of users to the portfolio class for persistence
        currentUserId = "";
        dbManager.open();
        mInstance = this;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        dbManager.close();
    }

    public static synchronized MyTweetApp getInstance() {
        return mInstance;
    }

    public <T> void add(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancel() {
        mRequestQueue.cancelAll(TAG);
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public void addUser(User user) {
        portfolio.users.add(user);
        Log.v("i/o", "User added: " + user);
        portfolio.saveUsers();
    }

    public void addTweet(Tweet tweet) {
        portfolio.tweetList.add(tweet);
        Log.v("i/o", "Tweet added: " + tweet);
        portfolio.saveTweets();
    }

    public void editTweet(String message, int tweetId) {
        for(Tweet tweet : portfolio.tweetList) {
            if(tweet.tweetId == tweetId) {
                tweet.message = message;
            }
        }

    }

    public boolean validUser(String email, String password) {
        for (User user : portfolio.users) {
            if((user.email.equals(email) && (user.password.equals(password)))) {
                Log.v("validuser", user.email + "successfully logged in");
                this.currentUserId = user.userId;//sets the global variable to current users id
                return true;
            }
        }
        return false;
    }
}
