package com.pushtech.android.example.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.pushtech.android.example.views.CampaignCardItemView;
import com.pushtech.android.example.views.CampaignListItemView;
import com.pushtech.sdk.PushDeliveriesContentValuesOps;
import com.pushtech.sdk.PushDelivery;


/**
 * Created by cristianrodriguezmoya on 02/05/14.
 */
public class CampaignsAdapter extends CursorAdapter {
    private PushDeliveriesContentValuesOps converter = new PushDeliveriesContentValuesOps();
    private Context ctx;
    private OnCampaignClicked listener;
    private boolean isCardView;

    public interface OnCampaignClicked {
        void onImageClicked(PushDelivery delivery);

        void onBodyClicked(PushDelivery delivery);

        void onActionLeftClicked(PushDelivery delivery);

        void onActionRightClicked(PushDelivery delivery);
    }


    public CampaignsAdapter(Context context, Cursor c, boolean isCardView, OnCampaignClicked listener) {
        super(context, c);
        this.ctx = context;
        this.isCardView = isCardView;
        this.listener = listener;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        if (isCardView) {
            return new CampaignCardItemView(context);

        } else {
            return new CampaignListItemView(context);
        }
    }


    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final PushDelivery pushDelivery = converter.buildFromCursor(cursor);
        if (isCardView) {
            ((CampaignCardItemView) view).setContent(pushDelivery);
            ((CampaignCardItemView) view).setListener(listener);
        } else {
            ((CampaignListItemView) view).setContent(pushDelivery);
            ((CampaignListItemView) view).setListener(listener);

        }
    }
}
