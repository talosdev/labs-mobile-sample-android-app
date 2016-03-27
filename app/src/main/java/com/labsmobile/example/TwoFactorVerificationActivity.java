package com.labsmobile.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.labsmobile.android.service.OTPService;
import com.labsmobile.example.receiver.DefaultOTPBroadcastReceiver;
import com.labsmobile.example.receiver.DefaultOTPVerificationService;
import com.labsmobile.example.receiver.OTPBroadcastReceiver;

/**
 * Created by apapad on 25/03/16.
 */
public class TwoFactorVerificationActivity extends BaseActivity implements Navigator {

    protected OTPService otpService;

    private BroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tfv_execute);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        RequestCodeFragment f = RequestCodeFragment.newInstance();
        fragmentTransaction.add(R.id.fragment, f)
                .addToBackStack(null)
                .commit();

    }

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, TwoFactorVerificationActivity.class);
        return i;
    }

    @Override
    public void onCodeRequested(String phoneNumber) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");

        //TODO values
        receiver = new DefaultOTPBroadcastReceiver(
                getResources().getString(R.string.otp_message_sender),
                getResources().getString(R.string.otp_message),
                phoneNumber,
                DefaultOTPVerificationService.class,
                BuildConfig.LABS_MOBILE_USERNAME,
                BuildConfig.LABS_MOBILE_PASSWORD,
                "asd"
        );

        registerReceiver(receiver, filter);
        Log.d(OTPBroadcastReceiver.TAG, "Registered OTPBroadcastReceiver");

        //TODO enable this
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        ValidateCodeFragment f = ValidateCodeFragment.newInstance(phoneNumber);
//        fragmentTransaction.replace(R.id.fragment, f)
//                .addToBackStack(null)
//                .commit();
    }

    @Override
    public void onCodeVerification(String phoneNumber, String code) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        VerificationResultFragment f = VerificationResultFragment.newInstance(phoneNumber, code);
        fragmentTransaction.replace(R.id.fragment, f)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }
}
