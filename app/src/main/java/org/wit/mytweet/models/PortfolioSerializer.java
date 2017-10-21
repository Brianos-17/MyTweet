package org.wit.mytweet.models;


import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.wit.mytweet.main.MyTweetApp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

//Info for this class retrieved from lecture: https://wit-ictskills-2017.github.io/mobile-app-dev/topic05-a/talk-3-fileio-in-myrent/d-fileio-in-myrent.pdf

public class PortfolioSerializer {

    private Context mContext;
    private String mFilename1;
    private String mFilename2;

    public PortfolioSerializer(Context context, String filename1, String filename2) {
        mContext = context;
        mFilename1 = filename1;
        mFilename2 = filename2;
    }

    public void saveUsers(List<User> users) throws JSONException, IOException {
        JSONArray array = new JSONArray();
        Log.v("i/o", "PS Users: " + users.toString());
        for (User user: users) {
            array.put(user.toJson());
            Log.v("i/o", user.toString());
        }
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename1, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if(writer != null) {
                writer.close();
            }
        }
    }

    public void saveTweets(List<Tweet> tweetList) throws JSONException, IOException {
        JSONArray array = new JSONArray();
        for (Tweet tweet: tweetList) {
            array.put(tweet.toJson());
        }
        Writer writer = null;
        try{
            OutputStream out = mContext.openFileOutput(mFilename2, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if(writer != null) {
                writer.close();
            }
        }
    }

    public List<User> loadUsers(List<User> users) throws JSONException, IOException {
        BufferedReader reader = null;
        try {
            InputStream in = mContext.openFileInput(mFilename1);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            for (int i = 0; i < array.length(); i++) {
                    users.add(new User(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e){
            //
        }
        finally {
                if (reader != null) {
                    reader.close();
                }
            }
            return users;
        }

    public List<Tweet> loadTweets(List<Tweet> tweetList) throws JSONException, IOException {
        BufferedReader reader = null;
        try {
            InputStream in = mContext.openFileInput(mFilename2);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            for (int i = 0; i < array.length(); i++) {
                tweetList.add(new Tweet(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e){
            //
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
        return tweetList;
    }
}


