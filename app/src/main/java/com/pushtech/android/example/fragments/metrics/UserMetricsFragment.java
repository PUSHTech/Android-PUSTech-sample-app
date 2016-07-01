package com.pushtech.android.example.fragments.metrics;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.i18n.phonenumbers.Phonenumber;
import com.pushtech.android.example.R;
import com.pushtech.android.example.activities.HomeActivity;
import com.pushtech.android.example.adapters.CountriesAdapter;
import com.pushtech.sdk.Country;
import com.pushtech.sdk.CountryHelper;
import com.pushtech.sdk.DataCollectorManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by crm27 on 28/6/16.
 */
public class UserMetricsFragment extends Fragment implements View.OnClickListener {
    private EditText firstName_ET, lastName_ET, email_ET, phone_ET, city_ET, userId_ET;
    private AppCompatSpinner gender_SP, country_SP;
    private View sendMetricsButton, birthdayButton, loginButton, registerButton, logoutButton;
    private Date birthdayDate;
    private DataCollectorManager dataCM;

    public UserMetricsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_metrics_user, container,
                false);
        initViews(rootView);
        setListener();
        initGenderSpinner();
        initCountrySpinner();
        dataCM = DataCollectorManager.getInstance(getActivity());
        dataCM.contentView(this.getClass().getName());
        return rootView;
    }


    private void initGenderSpinner() {
        List<String> list = new ArrayList<String>();
        list.add(getString(R.string.gender_male));
        list.add(getString(R.string.gender_female));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_SP.setAdapter(dataAdapter);
    }

    private void initCountrySpinner() {

        CountriesAdapter countriesAdapter = new CountriesAdapter(getActivity(), CountryHelper.getCountryList());
        country_SP.setAdapter(countriesAdapter);
    }

    private void initViews(View rootView) {
        firstName_ET = (EditText) rootView.findViewById(R.id.fragment_metrics_user_first_name);
        lastName_ET = (EditText) rootView.findViewById(R.id.fragment_metrics_user_last_name);
        email_ET = (EditText) rootView.findViewById(R.id.fragment_metrics_user_email);
        phone_ET = (EditText) rootView.findViewById(R.id.fragment_metrics_user_phone);
        city_ET = (EditText) rootView.findViewById(R.id.fragment_metrics_user_city);
        userId_ET = (EditText) rootView.findViewById(R.id.fragment_metrics_user_id);

        gender_SP = (AppCompatSpinner) rootView.findViewById(R.id.fragment_metrics_user_gender);
        country_SP = (AppCompatSpinner) rootView.findViewById(R.id.fragment_metrics_user_country);

        sendMetricsButton = rootView.findViewById(R.id.fragment_metrics_user_send_metrics);
        birthdayButton = rootView.findViewById(R.id.fragment_metrics_user_birthday);
        loginButton = rootView.findViewById(R.id.fragment_metrics_user_login_generic);
        logoutButton = rootView.findViewById(R.id.fragment_metrics_user_logout_generic);
        registerButton = rootView.findViewById(R.id.fragment_metrics_user_register_generic);

    }


    private void setListener() {
        sendMetricsButton.setOnClickListener(this);
        birthdayButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_metrics_user_send_metrics:
                sendMetrics();
                break;
            case R.id.fragment_metrics_user_birthday:
                openBirthdayDialog();
                return;
            case R.id.fragment_metrics_user_login_generic:
                dataCM.loginGeneric();
                break;
            case R.id.fragment_metrics_user_logout_generic:
                dataCM.logoutGeneric();
                break;
            case R.id.fragment_metrics_user_register_generic:
                dataCM.registerGeneric();
                break;
        }
        dataCM.sendMetrics();
        ((HomeActivity) getActivity()).showDialogAccept(getString(R.string.dialog_success),
                getString(R.string.dialog_user_metrics_send));

    }

    private void openBirthdayDialog() {
        Calendar calender = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                birthdayDate = new Date(year, monthOfYear, dayOfMonth);
            }
        }, calender.getTime().getYear(), calender.getTime().getMonth(), calender.getTime().getDay());
        datePickerDialog.show();
    }

    private void sendMetrics() {
        String firstName = firstName_ET.getText().toString();
        String lastName = lastName_ET.getText().toString();
        String email = email_ET.getText().toString();
        String phoneStr = phone_ET.getText().toString();
        String city = city_ET.getText().toString();
        Country country = (Country) country_SP.getSelectedItem();

        String gender = (String) gender_SP.getSelectedItem();

        if (!TextUtils.isEmpty(firstName)) {
            dataCM.setFirstName(firstName);
        }
        if (!TextUtils.isEmpty(lastName)) {
            dataCM.setLastName(lastName);
        }
        if (!TextUtils.isEmpty(email)) {
            dataCM.setEmail(email);
        }
        if (!TextUtils.isEmpty(phoneStr)) {
            Phonenumber.PhoneNumber phone = new Phonenumber.PhoneNumber();
            phone.setRawInput(phoneStr);
            dataCM.setPhoneNumber(phone);
        }
        if (!TextUtils.isEmpty(city)) {
            dataCM.setCity(city);
        }
        if (country != null) {

            dataCM.setCountry(country);
        }
        if (!TextUtils.isEmpty(gender)) {
            if (gender.equals(getString(R.string.gender_male))) {
                dataCM.setGender(DataCollectorManager.Gender.MALE);
            } else {
                dataCM.setGender(DataCollectorManager.Gender.FEMALE);
            }
        }
        if (birthdayDate != null) {
            dataCM.setBirthdate(birthdayDate);
        }
        clearFields();
    }

    private void clearFields() {
        firstName_ET.getText().clear();
        lastName_ET.getText().clear();
        email_ET.getText().clear();
        phone_ET.getText().clear();
        city_ET.getText().clear();
        userId_ET.getText().clear();
    }
}
