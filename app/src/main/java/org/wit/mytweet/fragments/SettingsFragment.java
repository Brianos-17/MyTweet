package org.wit.mytweet.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.activities.Base;
import org.wit.mytweet.models.User;

//Help for this class Retrieved from: https://wit-ictskills-2017.github.io/mobile-app-dev/topic06-b/book-a-settings/index.html#/MyRent-10 (Settings)
// and https://developer.android.com/guide/topics/ui/settings.html

public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener  {

    private SharedPreferences prefs;
    private Base activity;

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_settings);
    }

    //Binds the Base class to this fragment in order to allow for proper updating and saving of user details through the portfolio class
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Base) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.v("prefcheck", String.valueOf(key));
        Log.v("prefcheck", sharedPreferences.getString(key, ""));
        if (key.equals("username")) {
            String email = sharedPreferences.getString(key, "");
            for (User user : activity.app.portfolio.users) {
                if (user.userId.equals(activity.app.currentUserId)) {
                    user.email = email;
                    Log.v("prefcheck", "Changing user email to " + email);
                    activity.app.portfolio.saveUsers();
                    Toast.makeText(getActivity(), "Log in email has been changed to " + email, Toast.LENGTH_SHORT).show();
                }
            }
        } else if (key.equals("password")) {
            String password = sharedPreferences.getString(key, "");
            for (User user : activity.app.portfolio.users) {
                if (user.userId.equals(activity.app.currentUserId)) {
                    user.password = password;
                    Log.v("prefcheck", "Changing user password to" + password);
                    activity.app.portfolio.saveUsers();
                    Toast.makeText(getActivity(), "Password has been changed to " + password, Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}