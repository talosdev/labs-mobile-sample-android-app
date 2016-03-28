package com.labsmobile.example;

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
import com.labsmobile.example.receiver.DefaultOTPBroadcastReceiver;
import com.labsmobile.example.receiver.DefaultOTPVerificationService;
import com.labsmobile.example.receiver.OTPBroadcastReceiver;
import com.labsmobile.example.receiver.OTPVerificationService;

/**
 * Created by apapad on 25/03/16.
 */
public class TwoFactorVerificationActivity extends BaseActivity implements Navigator {

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
                new VerificationCompleteReceiver(), new IntentFilter(OTPVerificationService.VERIFICATION_SUCCESS));

    }

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, TwoFactorVerificationActivity.class);
        return i;
    }



    @Override
    public void onCheckResult(String phoneNumber, boolean pendingRequestExists) {
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
    public void onNumberVerified(String phoneNumber) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        VerificationSuccessFragment f = VerificationSuccessFragment.newInstance(phoneNumber);
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
            receiver = new DefaultOTPBroadcastReceiver(
                    getResources().getString(R.string.otp_message_sender),
                    getResources().getString(R.string.otp_message),
                    phoneNumber,
                    DefaultOTPVerificationService.class,
                    username,
                    password,
                    env
            );

            registerReceiver(receiver, filter);
            Log.d(OTPBroadcastReceiver.TAG, "Registered OTPBroadcastReceiver");

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


    class VerificationCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO
            Toast.makeText(TwoFactorVerificationActivity.this, "YEAAAH!", Toast.LENGTH_LONG).show();
        }
    }
}
