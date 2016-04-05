/*
 * Copyright (c) 2016, LabsMobile. All rights reserved.
 */

package com.labsmobile.example.otp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.labsmobile.android.service.OTPService;
import com.labsmobile.android.service.background.AutomaticVerificationSuccessCallback;
import com.labsmobile.android.service.background.OTPVerificationService;
import com.labsmobile.android.service.background.OTPVerificationSuccessReceiver;
import com.labsmobile.android.service.background.impl.DefaultOTPVerificationSuccessReceiver;
import com.labsmobile.example.LabsMobileServiceProvider;
import com.labsmobile.example.R;
import com.labsmobile.example.util.BaseActivity;


/**
 * Activity that handles the OTP/two-factor-verification process.
 * <p/>
 * Created by apapad on 25/03/16.
 */
public class TwoFactorVerificationActivity extends BaseActivity implements Navigator, AutomaticVerificationSuccessCallback {

    protected OTPService otpService;

    private BroadcastReceiver receiver;
    private static final String TAG = "OTP";
    private OTPVerificationSuccessReceiver verificationSuccessReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tfv);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        CheckStatusFragment f = CheckStatusFragment.newInstance();
        fragmentTransaction.add(R.id.fragment, f)
                .addToBackStack(null)
                .commit();


        LocalBroadcastManager.getInstance(this).registerReceiver(
                new DefaultOTPVerificationSuccessReceiver(this), new IntentFilter(OTPVerificationService.SUCCESS_INTENT));

    }

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, TwoFactorVerificationActivity.class);
        return i;
    }


    @Override
    public void onNumberNotVerifiedResult(String phoneNumber, boolean pendingRequestExists) {
        if (!pendingRequestExists) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            NotVerifiedFragment f = NotVerifiedFragment.newInstance(phoneNumber);
            fragmentTransaction.replace(R.id.fragment, f)
                    .commit();
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            RequestPendingFragment f = RequestPendingFragment.newInstance(phoneNumber, false);
            fragmentTransaction.replace(R.id.fragment, f)
                    .commit();
        }
    }

    @Override
    public void onNumberVerified() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        VerificationSuccessFragment f = VerificationSuccessFragment.newInstance();
        fragmentTransaction.replace(R.id.fragment, f)
                .commit();
    }

    @Override
    public void onCodeRequested(String phoneNumber) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");


        receiver = LabsMobileServiceProvider.provideOTPSMSReceiver(getResources().getString(R.string.otp_message_sender),
                getResources().getString(R.string.otp_message),
                phoneNumber);

        registerReceiver(receiver, filter);
        Log.d(TAG, "Registered OTPSMSReceiver");

        IntentFilter successFilter = new IntentFilter();
        successFilter.addAction(OTPVerificationService.SUCCESS_INTENT);

        verificationSuccessReceiver =
                LabsMobileServiceProvider.provideOTPVerificationSuccessReceiver(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(verificationSuccessReceiver, successFilter);

        Log.d(TAG, "Registered OTPVerificationSuccessReceiver");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        RequestPendingFragment f = RequestPendingFragment.newInstance(phoneNumber, true);
        fragmentTransaction.replace(R.id.fragment, f)
                .commit();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
            LocalBroadcastManager.getInstance(this).unregisterReceiver(verificationSuccessReceiver);
        }
    }

    @Override
    public void onVerificationSuccess() {
        onNumberVerified();
    }

}
