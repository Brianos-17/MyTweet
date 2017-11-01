package org.wit.helpers;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.content.ContentResolver;
import android.util.Log;

//Help for this class recieved from:
// https://wit-ictskills-2017.github.io/mobile-app-dev/topic05-a/book-a-myrent-06%20(Contact%20&%20Email)/index.html#/02
public class ContactHelper {

    public static String getEmail(Context context, Intent data) {
        String email = "no email";

        Uri contactUri = data.getData();
        ContentResolver cr = context.getContentResolver();

        Cursor cur = cr.query(contactUri, null, null, null, null);

        if (cur.getCount() > 0) {
            try {
                cur.moveToFirst();// true
                String contactId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                Log.v("contactHelper", "contactID for Brian: " + contactId);
                Cursor emails = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
                emails.moveToFirst();
                email = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                emails.close();
            } catch (Exception e) {
                Log.v("contactHelper", "Error " + e);
            }
        } return email;
    }

    public static String getContact(Context context, Intent data) {
        String contact = "unable to find contact";
        Uri contactUri = data.getData();
        String[] queryFields = new String[] { ContactsContract.Contacts.DISPLAY_NAME };
        Cursor c = context.getContentResolver().query(contactUri, queryFields, null, null, null);
        if (c.getCount() == 0) {
            c.close();
            return contact;
        }
        c.moveToFirst();
        contact = c.getString(0);
        c.close();

        return contact;
    }

    public static void sendEmail(Context context, String email, String subject, String body) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(emailIntent, "Sending Email"));
    }
}