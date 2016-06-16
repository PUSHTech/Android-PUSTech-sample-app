package com.pushtech.android.example.push;


import com.pushtech.sdk.GCMPushIntentService;
import com.pushtech.sdk.Receiver.GcmBroadcastReceiver;

/**
 *
 */
public final class ExampleGCMBroadcastReceiver extends GcmBroadcastReceiver {
    @Override
    protected Class<? extends GCMPushIntentService> getGcmServiceClass() {
        return ExampleGCMIntentService.class;
    }
}
