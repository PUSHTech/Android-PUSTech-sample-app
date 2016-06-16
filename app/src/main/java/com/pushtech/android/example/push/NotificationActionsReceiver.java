package com.pushtech.android.example.push;

import android.content.Context;
import android.content.Intent;

import com.pushtech.android.example.activities.HomeActivity;
import com.pushtech.sdk.PushDelivery;
import com.pushtech.sdk.Receiver.NotificationReceiver;

/**
 * Created by crm27 on 23/3/16.
 */
public class NotificationActionsReceiver extends NotificationReceiver {


    @Override
    public void actionCUSTOM(Context ctx, PushDelivery pushDelivery, String action) {
        openMainActivity(ctx, pushDelivery);

    }

    @Override
    public void actionOK(Context ctx, PushDelivery pushDelivery) {
        openMainActivity(ctx, pushDelivery);

    }

    @Override
    public void actionCANCEL(Context ctx, PushDelivery pushDelivery) {
        openMainActivity(ctx, pushDelivery);

    }

    @Override
    public void actionEDIT(Context ctx, PushDelivery pushDelivery) {
        openMainActivity(ctx, pushDelivery);

    }

    @Override
    public void actionSEND(Context ctx, PushDelivery pushDelivery) {
        openMainActivity(ctx, pushDelivery);

    }

    @Override
    public void actionBUY(Context ctx, PushDelivery pushDelivery) {
        openMainActivity(ctx, pushDelivery);

    }

    @Override
    public void actionSAVE(Context ctx, PushDelivery pushDelivery) {
        openMainActivity(ctx, pushDelivery);

    }

    @Override
    public void actionFIND(Context ctx, PushDelivery pushDelivery) {
        openMainActivity(ctx, pushDelivery);

    }

    @Override
    public void actionLIKE(Context ctx, PushDelivery pushDelivery) {
        openMainActivity(ctx, pushDelivery);

    }

    @Override
    public void actionDISLIKE(Context ctx, PushDelivery pushDelivery) {
        openMainActivity(ctx, pushDelivery);

    }

    @Override
    public void actionLAUNCH(Context ctx, PushDelivery pushDelivery) {
        openMainActivity(ctx, pushDelivery);

    }

    @Override
    public void actionREMIND(Context ctx, PushDelivery pushDelivery) {
        openMainActivity(ctx, pushDelivery);

    }

    @Override
    public void actionDELETE(Context ctx, PushDelivery pushDelivery) {
        openMainActivity(ctx, pushDelivery);

    }

    @Override
    public void actionFORBID(Context ctx, PushDelivery pushDelivery) {
        openMainActivity(ctx, pushDelivery);
    }

    private void openMainActivity(Context ctx, PushDelivery pushDelivery) {
        Intent intent = new Intent(ctx, HomeActivity.class);
        intent.putExtra("push_delivery_id", pushDelivery.getDeliveryId());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);

    }
}
