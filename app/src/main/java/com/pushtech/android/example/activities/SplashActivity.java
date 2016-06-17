package com.pushtech.android.example.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.pushtech.android.example.R;
import com.pushtech.sdk.Callbacks.PushtechAppbuildAsyncCallback;
import com.pushtech.sdk.PushtechApp;
import com.pushtech.sdk.PushtechAppBuilder;
import com.pushtech.sdk.PushtechError;

import java.util.Calendar;


public class SplashActivity extends Activity implements PushtechAppbuildAsyncCallback {

    private static final Handler splashTimeOutHandler = new Handler();
    public static final int SPLASH_TIMEOUT = 5000;
    private long openDate = 0;
    private PushtechAppBuilder pushtechAppBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        openDate = Calendar.getInstance().getTimeInMillis();
        preparePushSDK();
        startSetup();

    }

    private void startSplashTimeOut(final long delayMillis) {
        splashTimeOutHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(HomeActivity.class);
            }
        }, delayMillis);
    }

    private void preparePushSDK() {
        pushtechAppBuilder = new PushtechAppBuilder(this);
    }

    private void startSetup() {
        pushtechAppBuilder
                .setNotificationIcon(R.drawable.ic_launcher)
                .setNotificationIntent(new Intent(this, HomeActivity.class))
                .setAppId(getString(R.string.app_id))
                .setAppSecret(getString(R.string.app_secret))
                .setGcmSenderId(getString(R.string.project_id))
                .build(this);
    }


    private void whaitUntilSplashTimeoutBeforeClosingSplash() {
        long elapsedTime = System.currentTimeMillis() - openDate;
        if (elapsedTime < SPLASH_TIMEOUT) {
            startSplashTimeOut(SPLASH_TIMEOUT - elapsedTime);
        } else {
            startSplashTimeOut(0);
        }
    }

    private void startActivity(Class activityClass) {
        Intent startActivityIntent = new Intent(this, activityClass);
        startActivity(startActivityIntent);
        finish();
    }

    @Override
    public void onPushtechAppBuild(PushtechApp pushtechApp) {
        whaitUntilSplashTimeoutBeforeClosingSplash();
    }

    @Override
    public void onError(PushtechError error) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startSetup();
            }
        }, 5000);

    }
}
