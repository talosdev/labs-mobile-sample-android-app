package com.labsmobile.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TwoFactorVerificationIndexActivity extends BaseActivityWithMenu {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tfv_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.button)
    public void onCheckButton() {
        startActivity(TwoFactorVerificationCheckActivity.newIntent(this));
    }


    @OnClick(R.id.request_button)
    public void onRequestButton() {
        startActivity(TwoFactorVerificationRequestActivity.newIntent(this));
    }

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, TwoFactorVerificationIndexActivity.class);
        return i;
    }
}
