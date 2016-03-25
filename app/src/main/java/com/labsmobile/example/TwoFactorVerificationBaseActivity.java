package com.labsmobile.example;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.labsmobile.android.service.OTPService;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by apapad on 25/03/16.
 */
public class TwoFactorVerificationBaseActivity extends BaseActivityWithMenu {
    protected OTPService otpService;
    @Bind(R.id.phone_number)
    EditText phoneNumberEditText;
    @Bind(R.id.env)
    EditText envEditText;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tfv);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        otpService = LabsMobileServiceProvider.provideOTPService();

        ButterKnife.bind(this);

        ButtonTogglingTextWatcher buttonToggler = new ButtonTogglingTextWatcher(button, phoneNumberEditText);
        phoneNumberEditText.addTextChangedListener(buttonToggler);
    }
}
