/*
 * Copyright (c) 2016, LabsMobile. All rights reserved.
 */

package com.labsmobile.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.labsmobile.example.otp.TwoFactorVerificationActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Index Activity that shows a list of navigation options.
 *
 * author talosdev for LabsMobile
 * @version 1.0
 */
public class IndexActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.sms_services_button)
    public void onSmsButton() {
        startActivity(SMSActivity.newIntent(IndexActivity.this));
    }

    @OnClick(R.id.query_services_button)
    public void onQueriesButton() {
        startActivity(QueriesActivity.newIntent(IndexActivity.this));
    }

    @OnClick(R.id.otp_services_button)
    public void onOTPButton() {
        startActivity(TwoFactorVerificationActivity.newIntent(IndexActivity.this));
    }

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, IndexActivity.class);
        return i;
    }
}
