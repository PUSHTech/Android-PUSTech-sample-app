package com.pushtech.android.example.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.i18n.phonenumbers.Phonenumber;
import com.pushtech.android.example.R;
import com.pushtech.sdk.CountryHelper;
import com.pushtech.sdk.DataCollectorManager;
import com.pushtech.sdk.NotValidCountryException;
import com.pushtech.sdk.Products;
import com.pushtech.sdk.PurchaseProduct;
import com.pushtech.sdk.PushDelivery;
import com.pushtech.sdk.PushtechApp;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;

/**
 * Created by crm27 on 6/6/16.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    private View sendUserData, sendMetrics,sendLocalPush;
    private EditText firstName, lastName, email, phoneNumber;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container,
                false);
        initViews(rootView);
        setListener();


        return rootView;
    }

    private void initViews(View rootView) {
        sendUserData = rootView.findViewById(R.id.fragment_home_send_user_data);
        sendMetrics = rootView.findViewById(R.id.fragment_home_send_metrics);
        sendLocalPush = rootView.findViewById(R.id.fragment_home_send_local_push);

        firstName = (EditText) rootView.findViewById(R.id.fragment_home_first_name);
        lastName = (EditText) rootView.findViewById(R.id.fragment_home_last_name);
        email = (EditText) rootView.findViewById(R.id.fragment_home_email);
        phoneNumber = (EditText) rootView.findViewById(R.id.fragment_home_phone);
    }


    private void setListener() {
        sendUserData.setOnClickListener(this);
        sendMetrics.setOnClickListener(this);
        sendLocalPush.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_home_send_user_data:
                sendUserData();
                cleanFields();

                break;
            case R.id.fragment_home_send_metrics:
                sendGenerateMetrics();
                break;
            case R.id.fragment_home_send_local_push:
                sendFakePush();
                break;
        }

    }

    private void sendFakePush() {
        PushDelivery delivery = new PushDelivery();
        delivery.setTitle("This a title");
        delivery.setText("This a dummy push to preview the SDK push notifications");
        delivery.setThumbnailUrl("https://www.pushtech.com/assets/pushvideo3-fd19f2d9f7a81f9835173aa82bfe663e.jpg");
        delivery.setCategoryActions(PushDelivery.CategoryActions.CATEGORY_LIKE_DISLIKE);
        delivery.setActionLeft(PushDelivery.Actions.LIKE);
        delivery.setActionRight(PushDelivery.Actions.DISLIKE);
        delivery.setReceivedDate(new Date());
        PushtechApp.with(getActivity()).getDeliveriesManager().showPushNotification(delivery);
    }

    private void cleanFields() {
        firstName.getText().clear();
        lastName.getText().clear();
        email.getText().clear();
        phoneNumber.getText().clear();
    }

    private void sendGenerateMetrics() {
        DataCollectorManager data = DataCollectorManager.getInstance(getActivity());
        data.setCustomEvent("metric", "max_air", "10.3", DataCollectorManager.ValueType.NUMBER);
        data.loginFacebook();
        data.loginGoogle();
        data.loginTwitter();
        PurchaseProduct product = new PurchaseProduct();
        product.setName("Iphone 6");
        product.setPrice((double) 843.45);
        product.setProductId("273023ida3892");
        product.setCurrency(Currency.getInstance("EUR"));

        Products products = new Products();
        products.setProducts(new ArrayList<PurchaseProduct>());
        products.getProducts().add(product);
        data.addProductToCart(product);

        product = new PurchaseProduct();
        product.setName("Macbook pro 13");
        product.setPrice((double) 1843.45);
        product.setProductId("232139a30asds3");
        product.setCurrency(Currency.getInstance("EUR"));
        products.getProducts().add(product);
        data.removeProductFromCart(product);

        data.purchaseProduct(products);


        data.setBirthdate(new Date());
        data.setCity("Barcelona");
        try {
            data.setCountry(CountryHelper.getCountryByIsoCode("ES"));
        } catch (NotValidCountryException e) {
            e.printStackTrace();
        }


        data.setGender(DataCollectorManager.Gender.MALE);
        data.setNumberFacebookFriends(5738);
        data.setNumberOfTwitterFollowers(10020323);


        data.contentView("HomeFragment");

        data.logoutFacebook();

        data.setFacebookID("207336719452056");

        data.logoutTwitter();
        data.setTwitterID("PUSHTechCloud");

        data.logoutGoogle();
        data.setGoogleID("+Pushtechnologies");
        data.sendMetrics();
        showSendMetricsDialog();
    }

    private void sendUserData() {

        DataCollectorManager metrics = DataCollectorManager.getInstance(getActivity());
        String firstNameTxt = firstName.getText().toString();
        if (!TextUtils.isEmpty(firstNameTxt)) {
            metrics.setFirstName(firstNameTxt);
        }
        String lastNameTxt = lastName.getText().toString();
        if (!TextUtils.isEmpty(lastNameTxt)) {
            metrics.setLastName(lastNameTxt);
        }
        String emailTxt = email.getText().toString();
        if (!TextUtils.isEmpty(emailTxt)) {
            metrics.setEmail(emailTxt);
        }
        Phonenumber.PhoneNumber phone = new Phonenumber.PhoneNumber();
        phone.setRawInput(phoneNumber.getText().toString());
        metrics.setPhoneNumber(phone);
        metrics.sendMetrics();
        showSendMetricsDialog();


    }

    private void showSendMetricsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.dialog_send_metrics_message));
        builder.setTitle(getResources().getString(R.string.dialog_send_metrics_title));
        builder.setPositiveButton(getString(R.string.accept), null);
        builder.create().show();
    }
}
