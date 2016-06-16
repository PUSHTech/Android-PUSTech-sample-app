package com.pushtech.android.example.push;

import android.content.Intent;

import com.pushtech.sdk.BackendDelivery;
import com.pushtech.sdk.CampaignDelivery;
import com.pushtech.sdk.GCMPushIntentService;

/**
 *
 */
public final class ExampleGCMIntentService extends GCMPushIntentService {

    @Override
    public void onBackendDelivery(BackendDelivery delivery) {
        super.onBackendDelivery(delivery);
    }

    @Override
    public void onCampaignDelivery(CampaignDelivery delivery) {
        super.onCampaignDelivery(delivery);
    }

    @Override
    public void onGenericPush(Intent intent) {

    }
}