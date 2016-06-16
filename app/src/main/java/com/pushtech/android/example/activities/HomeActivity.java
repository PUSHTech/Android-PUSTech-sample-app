package com.pushtech.android.example.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.pushtech.android.example.R;
import com.pushtech.android.example.fragments.ConfigurationFragment;
import com.pushtech.android.example.fragments.HomeFragment;
import com.pushtech.android.example.fragments.MessageCenterFragment;
import com.pushtech.android.example.fragments.MetricsFragment;
import com.pushtech.android.example.utils.Constants;
import com.pushtech.sdk.DataCollectorManager;
import com.pushtech.sdk.PushDelivery;
import com.pushtech.sdk.PushtechApp;

import static com.pushtech.android.example.R.id.nav_view;

/**
 * Created by crm27 on 6/6/16.
 */

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Fragment fragment;
    private Toolbar toolbar;
    private NavigationView navigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendOpenCampaignMetric();
        getPushDelivery();
        initViews();
        setupToolbar();
        setupMenuDrawer();
        toolbar.setTitle(getString(R.string.nav_home) + " - " + getString(R.string.app_name));
        setupContent(new HomeFragment());


    }

    private String getIntentString(String name) {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null && !TextUtils.isEmpty(intent.getExtras().getString(name))) {
            return intent.getExtras().getString(name);
        } else {
            return null;
        }
    }

    private void getPushDelivery() {
        String id = getIntentString(Constants.INTENT_PUSHDELIVERY_ID);
        String action = getIntentString(Constants.INTENT_PUSH_ACTION);
        String category = getIntentString(Constants.INTENT_PUSH_CATEGORY);
        if (id != null) {
            PushDelivery delivery = PushtechApp.with(this).getDeliveriesManager().getPushDelivery(id);


        }
    }


    private void sendOpenCampaignMetric() {
        String campaign_id = getIntentString(Constants.INTENT_CAMPAIGN_ID);
        if (campaign_id != null) {
            DataCollectorManager.getInstance(this).setOpenPushCampaign(campaign_id);
        }
    }

    private void setupMenuDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.app_name,
                R.string.app_name) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                supportInvalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        mDrawerToggle.syncState();

    }

    private void setupContent(Fragment fr) {
        FragmentTransaction mTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragment = fr;
        mTransaction.replace(R.id.content_frame, fragment);
        mTransaction.commit();
        mDrawerLayout.closeDrawer(navigationView);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void initViews() {
        setContentView(R.layout.activity_home);
        navigationView = (NavigationView) findViewById(nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                if (!(fragment instanceof HomeFragment)) setupContent(new HomeFragment());
                toolbar.setTitle(getString(R.string.nav_home) + " - " + getString(R.string.app_name));
                break;
            case R.id.nav_message_center:
                if (!(fragment instanceof MessageCenterFragment))
                    setupContent(new MessageCenterFragment());
                toolbar.setTitle(getString(R.string.nav_message_center) + " - " + getString(R.string.app_name));

                break;
            case R.id.nav_metrics:
                if (!(fragment instanceof MetricsFragment)) setupContent(new MetricsFragment());
                break;
            case R.id.nav_configuration:
                if (!(fragment instanceof ConfigurationFragment))
                    setupContent(new ConfigurationFragment());
                toolbar.setTitle(getString(R.string.nav_configuration) + " - " + getString(R.string.app_name));

                break;
        }
        mDrawerLayout.closeDrawer(navigationView);
        return true;
    }

}
