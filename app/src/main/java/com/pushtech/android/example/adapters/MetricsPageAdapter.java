package com.pushtech.android.example.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pushtech.android.example.fragments.metrics.PurchaseMetricsFragment;
import com.pushtech.android.example.fragments.metrics.SocialMetricsFragment;
import com.pushtech.android.example.fragments.metrics.UserMetricsFragment;

/**
 * Created by crm27 on 28/6/16.
 */
public class MetricsPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MetricsPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new PurchaseMetricsFragment();
            case 1:
                return new SocialMetricsFragment();
            case 2:
                return new UserMetricsFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

