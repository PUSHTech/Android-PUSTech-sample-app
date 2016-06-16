package com.pushtech.android.example.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.pushtech.android.example.R;
import com.pushtech.android.example.activities.PushTechWebViewActivity;
import com.pushtech.android.example.adapters.CampaignsAdapter;
import com.pushtech.sdk.DeliveryManager;
import com.pushtech.sdk.PushDelivery;
import com.pushtech.sdk.PushtechApp;
import com.squareup.picasso.Picasso;

import static com.pushtech.android.example.utils.Constants.INTENT_WEB_URL;

/**
 * Created by crm27 on 6/6/16.
 */

public class MessageCenterFragment extends Fragment implements CampaignsAdapter.OnCampaignClicked {
    private ListView list;
    private View emptyView;

    public MessageCenterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_message_center, container,
                false);
        initViews(rootView);
        setViews();


        return rootView;
    }


    private void initViews(View rootView) {
        list = (ListView) rootView.findViewById(R.id.fragment_message_center_list);
        emptyView = rootView.findViewById(R.id.fragment_message_center_empty_view);
    }

    private void setViews() {
        Cursor cursorCampaigns = PushtechApp.with(getActivity()).getDeliveriesManager().getDeliveryCursor(
                DeliveryManager.DELIVERIES_TYPE.PLATFORM);
        list.setAdapter(new CampaignsAdapter(getActivity(), cursorCampaigns, true, this));
        list.setEmptyView(emptyView);

    }


    @Override
    public void onImageClicked(final PushDelivery delivery) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(getResources().getText(R.string.dialog_close), null);
        AlertDialog dialog = builder.create();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.view_alert_full_image, null);
        dialog.setView(dialogLayout);
        ImageView image = (ImageView) dialogLayout.findViewById(R.id.view_alert_full_image_image);
        Picasso.with(getActivity()).load(delivery.getThumbnailUrl()).into(image);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

    }

    @Override
    public void onBodyClicked(PushDelivery delivery) {
        if (!TextUtils.isEmpty(delivery.getUrl())) {
            Intent intent = new Intent(getActivity(), PushTechWebViewActivity.class);
            intent.putExtra(INTENT_WEB_URL, delivery.getUrl());
            getActivity().startActivity(intent);
        } else {
            showDialogAccept(getString(R.string.dialog_alert_title), getString(R.string.dialog_no_lading_page));
        }
    }

    @Override
    public void onActionLeftClicked(PushDelivery delivery) {
        showDialogAccept(delivery.getCategoryActions().name(), delivery.getActionLeftString(getActivity()));
    }

    @Override
    public void onActionRightClicked(PushDelivery delivery) {
        showDialogAccept(delivery.getCategoryActions().name(), delivery.getActionRightString(getActivity()));

    }

    private void showDialogAccept(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(getString(R.string.dialog_accept), null);
        builder.create().show();
    }
}
