package org.wit.helpers;
import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;

/**
 * Class responsible for holding information for intents and permissions in order to access other app
 * Help for class retrieved from: https://wit-ictskills-2017.github.io/mobile-app-dev/topic05-a/book-a-myrent-06%20(Contact%20&%20Email)/index.html#/02
 */

public class IntentHelper {

    public static void startActivity (Activity parent, Class classname) {
        Intent intent = new Intent(parent, classname);
        parent.startActivity(intent);
    }

    public static void startActivityWithData (Activity parent, Class classname, String extraID, Serializable extraData) {
        Intent intent = new Intent(parent, classname);
        intent.putExtra(extraID, extraData);
        parent.startActivity(intent);
    }

    public static void startActivityWithDataForResult (Activity parent, Class classname,
                                                       String extraID, Serializable extraData, int idForResult) {
        Intent intent = new Intent(parent, classname);
        intent.putExtra(extraID, extraData);
        parent.startActivityForResult(intent, idForResult);
    }

    public static void navigateUp(Activity parent) {
        Intent upIntent = NavUtils.getParentActivityIntent(parent);
        NavUtils.navigateUpTo(parent, upIntent);
    }
}