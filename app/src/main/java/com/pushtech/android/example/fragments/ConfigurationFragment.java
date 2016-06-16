package com.pushtech.android.example.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.pushtech.android.example.R;
import com.pushtech.sdk.DataCollectorManager;
import com.pushtech.sdk.PushtechApp;

/**
 * Created by crm27 on 6/6/16.
 */
public class ConfigurationFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private TextView appId, sdkVersion;
    private SwitchCompat pushSubscribed;

    public ConfigurationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_configuration, container,
                false);
        initViews(rootView);
        setViews();
        setListener();
        initSwitchButton();


        return rootView;
    }


    private void initViews(View rootView) {
        appId = (TextView) rootView.findViewById(R.id.fragment_configuration_app_id);
        sdkVersion = (TextView) rootView.findViewById(R.id.fragment_configuration_pushtech_sdk_version);
        pushSubscribed = (SwitchCompat) rootView.findViewById(R.id.fragment_configuration_subscribe_push);
    }

    private void setViews() {
        appId.setText(PushtechApp.with(getActivity()).getAppId());
        sdkVersion.setText(PushtechApp.with(getActivity()).getSdkVersion());
    }


    private void setListener() {
        pushSubscribed.setOnCheckedChangeListener(this);
    }

    private void initSwitchButton() {
        pushSubscribed.setChecked(PushtechApp.with(getActivity()).isSubscribedPush());

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        DataCollectorManager.getInstance(getActivity()).unsubscribePushNotifications(!isChecked);
        DataCollectorManager.getInstance(getActivity()).sendMetrics();
    }
}
