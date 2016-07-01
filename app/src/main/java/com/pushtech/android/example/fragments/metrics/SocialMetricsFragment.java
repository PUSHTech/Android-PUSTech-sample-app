package com.pushtech.android.example.fragments.metrics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.pushtech.android.example.R;
import com.pushtech.android.example.activities.HomeActivity;
import com.pushtech.sdk.DataCollectorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crm27 on 28/6/16.
 */
public class SocialMetricsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private DataCollectorManager dataCM;
    private AppCompatSpinner spinner;
    private EditText facebookId_ET, facebookFriends_ET,
            googleId_ET,
            twitterId_ET, twitterFollowers_ET;
    private View facebookContainer, googleContainer, twitterContainer;
    private View loginFacebookButton, logoutFacebookButton,
            loginGoogleButton, logoutGoogleButton,
            loginTwitterButton, logoutTwitterButton;
    private View sendFacebookButton, sendGoogleButton, sendTwitterButton;

    public SocialMetricsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_metrics_social, container,
                false);
        dataCM = DataCollectorManager.getInstance(getActivity());
        dataCM.contentView(this.getClass().getName());
        initViews(rootView);
        setListener();
        setViews();


        return rootView;
    }

    private void setViews() {
        List<String> list = new ArrayList<String>();
        list.add("Facebook");
        list.add("Google");
        list.add("Twitter");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }

    private void initViews(View rootView) {
        spinner = (AppCompatSpinner) rootView.findViewById(R.id.fragment_metrics_social_spinner);
        facebookId_ET = (EditText) rootView.findViewById(R.id.fragment_metrics_social_facebook_id);
        facebookFriends_ET = (EditText) rootView.findViewById(R.id.fragment_metrics_social_facebook_friends);
        googleId_ET = (EditText) rootView.findViewById(R.id.fragment_metrics_social_google_id);
        twitterId_ET = (EditText) rootView.findViewById(R.id.fragment_metrics_social_twitter_id);
        twitterFollowers_ET = (EditText) rootView.findViewById(R.id.fragment_metrics_social_twitter_followers);

        facebookContainer = rootView.findViewById(R.id.fragment_metrics_social_facebook_container);
        googleContainer = rootView.findViewById(R.id.fragment_metrics_social_google_container);
        twitterContainer = rootView.findViewById(R.id.fragment_metrics_social_twitter_container);

        loginFacebookButton = rootView.findViewById(R.id.fragment_metrics_social_facebook_login);
        logoutFacebookButton = rootView.findViewById(R.id.fragment_metrics_social_facebook_logout);

        loginGoogleButton = rootView.findViewById(R.id.fragment_metrics_social_google_login);
        logoutGoogleButton = rootView.findViewById(R.id.fragment_metrics_social_google_logout);

        loginTwitterButton = rootView.findViewById(R.id.fragment_metrics_social_twitter_login);
        logoutTwitterButton = rootView.findViewById(R.id.fragment_metrics_social_twitter_logout);

        sendFacebookButton = rootView.findViewById(R.id.fragment_metrics_social_facebook_send_metrics);
        sendGoogleButton = rootView.findViewById(R.id.fragment_metrics_social_google_send_metrics);
        sendTwitterButton = rootView.findViewById(R.id.fragment_metrics_social_twitter_send_metrics);

    }


    private void setListener() {
        loginFacebookButton.setOnClickListener(this);
        logoutFacebookButton.setOnClickListener(this);
        loginGoogleButton.setOnClickListener(this);
        logoutGoogleButton.setOnClickListener(this);
        loginTwitterButton.setOnClickListener(this);
        logoutTwitterButton.setOnClickListener(this);

        sendGoogleButton.setOnClickListener(this);
        sendFacebookButton.setOnClickListener(this);
        sendTwitterButton.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_metrics_social_facebook_login:
                dataCM.loginFacebook();
                break;
            case R.id.fragment_metrics_social_facebook_logout:
                dataCM.logoutFacebook();
                break;
            case R.id.fragment_metrics_social_facebook_send_metrics:
                String facebookId = facebookId_ET.getText().toString();
                String friends = facebookFriends_ET.getText().toString();
                if (!TextUtils.isEmpty(facebookId)) {
                    dataCM.setFacebookID(facebookId);
                }
                if (!TextUtils.isEmpty(friends)) {
                    int facebookFriends = Integer.parseInt(friends);
                    dataCM.setNumberFacebookFriends(facebookFriends);
                }
                break;
            case R.id.fragment_metrics_social_google_login:
                dataCM.loginGoogle();
                break;
            case R.id.fragment_metrics_social_google_logout:
                dataCM.logoutGoogle();
                break;
            case R.id.fragment_metrics_social_google_send_metrics:
                String googleId = googleId_ET.getText().toString();
                if (!TextUtils.isEmpty(googleId)) {
                    dataCM.setGoogleID(googleId);
                }

                break;
            case R.id.fragment_metrics_social_twitter_login:
                dataCM.loginTwitter();
                break;
            case R.id.fragment_metrics_social_twitter_logout:
                dataCM.logoutTwitter();
                break;
            case R.id.fragment_metrics_social_twitter_send_metrics:
                String twitterId = twitterId_ET.getText().toString();
                String f = twitterFollowers_ET.getText().toString();
                if (!TextUtils.isEmpty(twitterId)) {
                    dataCM.setTwitterID(twitterId);
                }
                if (!TextUtils.isEmpty(f)) {
                    int followers = Integer.parseInt(f);
                    dataCM.setNumberOfTwitterFollowers(followers);
                }
                break;

        }
        dataCM.sendMetrics();
        ((HomeActivity) getActivity()).showDialogAccept(getString(R.string.dialog_success),
                getString(R.string.dialog_social_metrics_send));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        facebookContainer.setVisibility(View.GONE);
        googleContainer.setVisibility(View.GONE);
        twitterContainer.setVisibility(View.GONE);
        switch (position) {
            case 0:
                facebookContainer.setVisibility(View.VISIBLE);
                break;
            case 1:
                googleContainer.setVisibility(View.VISIBLE);
                break;
            case 2:
                twitterContainer.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
