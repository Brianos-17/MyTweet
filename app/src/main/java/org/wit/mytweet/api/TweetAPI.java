package org.wit.mytweet.api;

import android.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.wit.mytweet.activities.Base;
import org.wit.mytweet.models.Tweet;

import java.lang.reflect.Type;
import java.util.List;

public class TweetAPI {

    private static final String hostURL = "https://safe-springs-59831.herokuapp.com";
    private static final String LocalhostURL = "http://192.168.0.13:3000";
    //private static List<Coffee> result = null;
    private static VolleyListener vListener;

    public static void attachListener(VolleyListener fragment)
    {
        //System.out.println("Attaching Fragment : " + fragment);
        vListener = fragment;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static void get(String url) {

        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, hostURL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Result handling
                        List<Tweet> result = null;
                        //System.out.println("COFFEE JSON DATA : " + response);
                        Type collectionType = new TypeToken<List<Tweet>>(){}.getType();

                        result = new Gson().fromJson(response, collectionType);
                        vListener.setList(result);
                        vListener.updateUI((Fragment)vListener);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();
            }
        });

// Add the request to the queue
        Base.app.add(stringRequest);
    }
}
