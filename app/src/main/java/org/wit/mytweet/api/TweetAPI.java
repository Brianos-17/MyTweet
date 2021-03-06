package org.wit.mytweet.api;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.Tweet;
import org.wit.mytweet.models.User;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class TweetAPI {

    private static final String hostURL = "https://stormy-springs-79867.herokuapp.com";
    private static VolleyListener vListener;
    public static ProgressDialog  dialog;
    public static MyTweetApp app = MyTweetApp.getInstance();

    public static void attachListener(VolleyListener fragment) {
        vListener = fragment;
    }

    public static void detachListener() {
        vListener  = null;
    }

    public static void attachDialog(ProgressDialog mDialog) {
        dialog = mDialog;
    }

    private static void showDialog(String message) {
        dialog.setMessage(message);
        if (!dialog.isShowing())
            dialog.show();
    }
    private static void hideDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static void get(String url) {
        showDialog("Downloading your Tweet...");
        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, hostURL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Result handling
                        Tweet result = null;
                        Type objType = new TypeToken<Tweet>(){}.getType();
                        result = new Gson().fromJson(response, objType);
                        Log.v("personaltweets", result.toString());
                        vListener.setTweet(result);
                        hideDialog();
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
        app.add(stringRequest);
    }

    public static void getAll(String url, final SwipeRefreshLayout mSwipeRefreshLayout) {
        Log.v(TAG, "GETing All Tweets from " + url);
        showDialog("Downloading Tweets...");
        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, hostURL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Result handling
                        List<Tweet> result = null;
                        Type collectionType = new TypeToken<List<Tweet>>(){}.getType();
                        result = new Gson().fromJson(response, collectionType);
                        vListener.setList(result);
                        Log.v("GETcheck", "Here are all the tweets" + result.toString());
                        if(mSwipeRefreshLayout != null){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        hideDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                Log.v(TAG,"Something went wrong with GET ALL!");
                Log.v("GETcheck", "Nothing here");
                if(mSwipeRefreshLayout != null){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                error.printStackTrace();
            }
        });
        // Add the request to the queue
        app.add(stringRequest);
    }

    public static void getGooglePhoto(String url, final ImageView googlePhoto) {
        ImageRequest imgRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        app.googlePhoto = response;
                        googlePhoto.setImageBitmap(app.googlePhoto);
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888,

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Something went wrong!");
                        error.printStackTrace();
                    }
                });
        // Add the request to the queue
        app.add(imgRequest);
    }

    public static void postTweet(String url,Tweet aTweet) {
        Log.v(TAG, "POSTing to : " + url);
        Type objType = new TypeToken<Tweet>(){}.getType();
        String json = new Gson().toJson(aTweet, objType);
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest gsonRequest = new JsonObjectRequest( Request.Method.POST, hostURL + url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, "insert new Coffee " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { // Handle Error
                        Log.v(TAG, "Unable to insert new Coffee");
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");

                return headers;
            }
        };

        // Add the request to the queue
        app.add(gsonRequest);
    }

    public static void postUser(String url,User user) {
        Log.v(TAG, "POSTing to : " + url);
        Type objType = new TypeToken<User>(){}.getType();
        String json = new Gson().toJson(user, objType);
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest gsonRequest = new JsonObjectRequest( Request.Method.POST, hostURL + url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, "insert new User " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { // Handle Error
                        Log.v(TAG, "Unable to insert new User");
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");

                return headers;
            }
        };

        // Add the request to the queue
        app.add(gsonRequest);
    }

    public static void authenticate(String url, User user) {
        Log.v(TAG, "Authenticating with : " + url);
        Type objType = new TypeToken<User>(){}.getType();
        String json = new Gson().toJson(user, objType);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest gsonRequest = new JsonObjectRequest( Request.Method.POST, hostURL + url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            app.currentUserId = response.getString("_id");
                            Log.v("userID", "Current user id is " + app.currentUserId);
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { // Handle Error
                        Log.v(TAG, "Unable to authenticate User");
                    }
                }) {
        };

        // Add the request to the queue
        app.add(gsonRequest);
    }

    public static void put(String url,Tweet tweetToEdit) {
        Log.v(TAG, "PUTing to : " + url);
        showDialog("Updating your Tweet...");
        Type objType = new TypeToken<Tweet>(){}.getType();
        String json = new Gson().toJson(tweetToEdit, objType);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest gsonRequest = new JsonObjectRequest( Request.Method.PUT, hostURL + url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Result handling
                        Tweet result = null;
                        Type objType = new TypeToken<Tweet>(){}.getType();

                        try {
                            result = new Gson().fromJson(response.getString("data"), objType);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        vListener.setTweet(result);
                        hideDialog();
                        Log.v(TAG, "Updating a Coffee successful with :" + result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Handle Error
                        Log.v(TAG, "Unable to update Coffee with error : " + error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                //headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        // Add the request to the queue
        app.add(gsonRequest);
    }

    public static void delete(String url) {
        Log.v(TAG, "DELETEing from " + url);

        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, hostURL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Result handling
                        Log.v(TAG, "DELETE success " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                Log.v(TAG,"Something went wrong with DELETE!");
                error.printStackTrace();
            }
        });

        // Add the request to the queue
        app.add(stringRequest);
    }
}

