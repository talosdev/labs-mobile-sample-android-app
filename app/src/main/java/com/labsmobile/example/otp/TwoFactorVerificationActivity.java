package com.labsmobile.example.otp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.labsmobile.android.service.OTPService;
import com.labsmobile.example.R;
import com.labsmobile.android.service.background.impl.DefaultOTPSMSReceiver;
import com.labsmobile.android.service.background.impl.DefaultOTPVerificationService;
import com.labsmobile.android.service.background.impl.DefaultOTPVerificationSuccessReceiver;
import com.labsmobile.android.service.background.OTPVerificationService;
import com.labsmobile.android.service.background.OTPVerificationSuccessReceiver;
import com.labsmobile.android.service.background.AutomaticVerificationSuccessCallback;
import com.labsmobile.example.util.BaseActivity;
import com.labsmobile.example.util.Constants;


/**
 * Created by apapad on 25/03/16.
 */
public class TwoFactorVerificationActivity extends BaseActivity implements Navigator, AutomaticVerificationSuccessCallback {

    protected OTPService otpService;

    private BroadcastReceiver receiver;
    private String TAG = "OTP";


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
                new VerificationCompleteReceiver(), new IntentFilter(OTPVerificationService.SUCCESS_INTENT));

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
                    //          .addToBackStack(null)
                    .commit();
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            RequestPendingFragment f = RequestPendingFragment.newInstance(phoneNumber, false);
            fragmentTransaction.replace(R.id.fragment, f)
                    //          .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onNumberVerified() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        VerificationSuccessFragment f = VerificationSuccessFragment.newInstance();
        fragmentTransaction.replace(R.id.fragment, f)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCodeRequested(String phoneNumber) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");


        SharedPreferences sharedpreferences = getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
        String username = sharedpreferences.getString(Constants.SHARED_PREFS_USERNAME, null);
        String password = sharedpreferences.getString(Constants.SHARED_PREFS_PASSWORD, null);
        String env = sharedpreferences.getString(Constants.SHARED_PREFS_ENV, null);

        if (username == null || password == null || env == null) {
            Log.e(TAG, "There was an error reading from SharedPreferences");
            Toast.makeText(this, R.string.error_shared_prefs, Toast.LENGTH_LONG).show();
        } else {

            // TODO move this
            receiver = new DefaultOTPSMSReceiver(
                    getResources().getString(R.string.otp_message_sender),
                    getResources().getString(R.string.otp_message),
                    phoneNumber,
                    DefaultOTPVerificationService.class,
                    username,
                    password,
                    env
            );

            registerReceiver(receiver, filter);
            Log.d(TAG, "Registered OTPSMSReceiver");

            IntentFilter successFilter = new IntentFilter();
            successFilter.addAction(OTPVerificationService.SUCCESS_INTENT);

            OTPVerificationSuccessReceiver successVerification = new DefaultOTPVerificationSuccessReceiver(this);
            LocalBroadcastManager.getInstance(this).registerReceiver(successVerification, successFilter);

            Log.d(TAG, "Registered OTPVerificationSuccessReceiver");


            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            RequestPendingFragment f = RequestPendingFragment.newInstance(phoneNumber, true);
            fragmentTransaction.replace(R.id.fragment, f)
                    //          .addToBackStack(null)
                    .commit();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    @Override
    public void onVerificationSuccess() {
        onNumberVerified();
    }


    class VerificationCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO
            Toast.makeText(TwoFactorVerificationActivity.this, "YEAAAH!", Toast.LENGTH_LONG).show();
        }
    }
}
