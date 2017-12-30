package org.wit.mytweet.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class User {

    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String userId;

    //variables fields included for persistence to and from JSON
    private static final String JSON_FIRSTNAME = "firstName";
    private static final String JSON_LASTNAME = "lastName";
    private static final String JSON_EMAIL = "email";
    private static final String JSON_PASSWORD = "password";
    private static final String JSON_USERID = "userId";

    public User() {
        //Empty constructor used for DB
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        UUID uuid = UUID.randomUUID();
        this.userId = uuid.toString();
    }

    //Write a User Object to Json format
    public User(JSONObject json) throws JSONException {
        firstName = json.getString(JSON_FIRSTNAME);
        lastName = json.getString(JSON_LASTNAME);
        email = json.getString(JSON_EMAIL);
        password = json.getString(JSON_PASSWORD);
        userId = json.getString(JSON_USERID);
    }

    //Reads a json object as a User object
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_FIRSTNAME, firstName);
        json.put(JSON_LASTNAME, lastName);
        json.put(JSON_EMAIL, email);
        json.put(JSON_PASSWORD, password);
        json.put(JSON_USERID, userId);
        return json;
    }
}
