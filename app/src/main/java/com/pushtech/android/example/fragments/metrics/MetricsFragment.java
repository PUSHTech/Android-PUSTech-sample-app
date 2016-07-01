package com.pushtech.android.example.fragments.metrics;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pushtech.android.example.R;
import com.pushtech.android.example.adapters.MetricsPageAdapter;

/**
 * Created by crm27 on 6/6/16.
 */
public class MetricsFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    public MetricsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_metrics, container,
                false);
        initViews(rootView);
        setViews();
        setListener();


        return rootView;
    }

    private void setViews() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_purchase)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_social)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_user_attributes)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new MetricsPageAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void initViews(View rootView) {
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
    }


    private void setListener() {
        tabLayout.setOnTabSelectedListener(this);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}


