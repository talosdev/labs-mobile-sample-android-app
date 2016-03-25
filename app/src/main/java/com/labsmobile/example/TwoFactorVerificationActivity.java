package com.labsmobile.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.labsmobile.android.service.OTPService;

/**
 * Created by apapad on 25/03/16.
 */
public class TwoFactorVerificationActivity extends BaseActivity implements Navigator {

    protected OTPService otpService;


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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ValidateCodeFragment f = ValidateCodeFragment.newInstance(phoneNumber);
        fragmentTransaction.replace(R.id.fragment, f)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCodeVerification(String phoneNumber, String code) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        VerificationResultFragment f = VerificationResultFragment.newInstance(phoneNumber, code);
        fragmentTransaction.replace(R.id.fragment, f)
                .addToBackStack(null)
                .commit();
    }
}
