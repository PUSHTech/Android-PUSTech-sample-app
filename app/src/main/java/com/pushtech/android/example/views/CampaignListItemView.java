package com.pushtech.android.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.pushtech.android.example.R;
import com.pushtech.android.example.adapters.CampaignsAdapter;
import com.pushtech.sdk.PushDelivery;

/**
 * Created by crm27 on 6/6/16.
 */

public class CampaignListItemView extends LinearLayout {
    private PushDelivery delivery;
    private CampaignsAdapter.OnCampaignClicked listener;

    public CampaignListItemView(Context context) {
        this(context, null);

    }

    public CampaignListItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CampaignListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();

    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(
                R.layout.view_campaign_list_item, this, true);
    }

    private void setViews() {
    }


    public void setContent(PushDelivery delivery) {
        this.delivery = delivery;
        setViews();
    }

    public void setListener(CampaignsAdapter.OnCampaignClicked listener) {
        this.listener = listener;
    }
}
