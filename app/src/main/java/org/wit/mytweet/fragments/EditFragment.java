package org.wit.mytweet.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.activities.Base;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.Tweet;

import java.text.DateFormat;
import java.util.Date;

import static org.wit.helpers.ContactHelper.getContact;
import static org.wit.helpers.ContactHelper.getEmail;
import static org.wit.helpers.ContactHelper.sendEmail;
import static org.wit.helpers.IntentHelper.selectContact;

public class EditFragment extends Fragment {
    public static MyTweetApp app;
    private TextView characterCount, tweetDate;
    private EditText editedTweet;
    private Button contactButton, emailButton, editTweet;
    private String emailAddress, tweetID;
    private Tweet tweetToEdit;
    private Intent data;
    private static final int REQUEST_CONTACT = 1;
    private OnFragmentInteractionListener mListener;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(Bundle tweetBundle) {
        EditFragment fragment = new EditFragment();
        fragment.setArguments(tweetBundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyTweetApp) getActivity().getApplication();
        if(getArguments() != null)
            tweetToEdit = app.dbManager.getTweet(getArguments().getInt("tweetID"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);

        characterCount = (TextView) v.findViewById(R.id.characterCount);
        editTweet = (Button) v.findViewById(R.id.sendTweet);
        editedTweet = (EditText) v.findViewById(R.id.newTweet);
        tweetDate = (TextView) v.findViewById(R.id.tweetDate);
        contactButton = (Button) v.findViewById(R.id.contactButton);
        emailButton = (Button) v.findViewById(R.id.emailButton);

        editedTweet.setText(tweetToEdit.message);
        characterCount.setText(String.valueOf(140 - tweetToEdit.message.length()));
        tweetDate.setText(tweetToEdit.date);


        //TextWatcher which counts down value of character count
        //Retrieved from: https://stackoverflow.com/questions/24110265/android-create-count-down-word-field-when-user-type-in-edittext
        editedTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                characterCount.setText(String.valueOf(140 - (editedTweet.getText().toString().length())));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void edit(View v);
    }

    public void edit(View v) {
        if(mListener != null) {
            String message = editTweet.getText().toString();
            if (message.length() > 0){
                tweetToEdit.message = message;
            } else {
                Toast.makeText(getActivity(), "You can't send a blank tweet!", Toast.LENGTH_SHORT).show();
            }
        }

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return;
        }
    }

    public void contactButtonPressed(View view) {
        selectContact(getActivity(), REQUEST_CONTACT);
    }

    public void emailButtonPressed(View view) {
        sendEmail(getActivity(), emailAddress, this.getString(R.string.latestTweetHeader), editedTweet.getText().toString());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONTACT:
                this.data = data;
                checkContactsReadPermission();
                break;
        }
    }

    //https://developer.android.com/training/permissions/requesting.html
    private void checkContactsReadPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            //We can request the permission.
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACT);
        } else {
            //We already have permission, so go head and read the contact
            readContact();
        }
    }

    //https://developer.android.com/training/permissions/requesting.html
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    readContact();
                }
            }
        }
    }

    private void readContact() {
        String name = getContact(getActivity(), data);
        emailAddress = getEmail(getActivity(), data);
        contactButton.setText(name + " : " + emailAddress);
    }
}
