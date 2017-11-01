package org.wit.helpers;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;

/**
 * Class responsible for holding information for intents and permissions in order to access other app
 * Help for class retrieved from: https://wit-ictskills-2017.github.io/mobile-app-dev/topic05-a/book-a-myrent-06%20(Contact%20&%20Email)/index.html#/02
 */

public class IntentHelper {

    public static void navigateUp(Activity parent) {
        Intent upIntent = NavUtils.getParentActivityIntent(parent);
        NavUtils.navigateUpTo(parent, upIntent);
    }

    public static void selectContact(Activity parent, int id) {
        Intent selectContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        parent.startActivityForResult(selectContactIntent, id);
    }
}