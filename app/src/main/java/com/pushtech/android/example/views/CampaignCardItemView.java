package com.pushtech.android.example.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pushtech.android.example.R;
import com.pushtech.android.example.adapters.CampaignsAdapter;
import com.pushtech.android.example.utils.DatesHelper;
import com.pushtech.sdk.PushDelivery;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * Created by crm27 on 6/6/16.
 */

public class CampaignCardItemView extends CardView implements View.OnClickListener {
    private PushDelivery delivery;
    private ImageView image;
    private TextView title, text, url, leftAction, rightAction, time;
    private View imageContainer, actionsContainer, bodyContainer;
    private Context context;
    private CampaignsAdapter.OnCampaignClicked listener;

    public CampaignCardItemView(Context context) {
        this(context, null);

    }

    public CampaignCardItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CampaignCardItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }


    private void initViews() {
        LayoutInflater.from(getContext()).inflate(
                R.layout.view_campaign_card_item, this, true);
        image = (ImageView) findViewById(R.id.view_campaign_card_item_image);
        title = (TextView) findViewById(R.id.view_campaign_card_item_title);
        text = (TextView) findViewById(R.id.view_campaign_card_item_text);
        imageContainer = findViewById(R.id.view_campaign_card_item_image_container);
        actionsContainer = findViewById(R.id.view_campaign_card_item_actions_container);
        bodyContainer = findViewById(R.id.view_campaign_card_item_body_container);
        leftAction = (TextView) findViewById(R.id.view_campaign_card_item_action_left);
        rightAction = (TextView) findViewById(R.id.view_campaign_card_item_action_right);
        time = (TextView) findViewById(R.id.view_campaign_card_item_time);
    }

    private void setViews() {
        setCardElevation(getMaxCardElevation());
        Date date = this.delivery.getCreatedDate();
        if (date == null) {
            date = this.delivery.getReceivedDate();
        }
        time.setText(DatesHelper.getDateString(getContext(),
                date.getTime()));
        if (!this.delivery.getThumbnailUrl().isEmpty()) {
            imageContainer.setVisibility(VISIBLE);
            Picasso.with(this.context).load(this.delivery.getThumbnailUrl()).placeholder(R.drawable.placeholder).into(image);
        } else {
            imageContainer.setVisibility(GONE);
        }
        if (this.delivery.getCategoryActions() != null) {
            actionsContainer.setVisibility(VISIBLE);
            leftAction.setText(this.delivery.getActionLeftString(context));
            if (this.delivery.getActionRight() == null) {
                rightAction.setVisibility(GONE);
            } else {
                rightAction.setVisibility(VISIBLE);
                rightAction.setText(this.delivery.getActionRightString(context));
            }

        } else {
            actionsContainer.setVisibility(GONE);
        }
        title.setText(this.delivery.getTitle());
        text.setText(this.delivery.getText().trim());
    }

    public void setListener(CampaignsAdapter.OnCampaignClicked listener) {
        this.listener = listener;
        imageContainer.setOnClickListener(this);
        bodyContainer.setOnClickListener(this);
        leftAction.setOnClickListener(this);
        rightAction.setOnClickListener(this);
    }

    public void setContent(PushDelivery delivery) {
        this.delivery = delivery;
        setViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_campaign_card_item_image_container:
                this.listener.onImageClicked(this.delivery);
                break;
            case R.id.view_campaign_card_item_body_container:
                this.listener.onBodyClicked(this.delivery);
                break;
            case R.id.view_campaign_card_item_action_left:
                this.listener.onActionLeftClicked(this.delivery);
                break;
            case R.id.view_campaign_card_item_action_right:
                this.listener.onActionRightClicked(this.delivery);
                break;
        }
    }
}
