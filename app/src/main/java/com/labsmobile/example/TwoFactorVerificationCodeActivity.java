package com.labsmobile.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class TwoFactorVerificationCodeActivity extends BaseActivityWithMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tfv_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }


    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, TwoFactorVerificationCodeActivity.class);
        return i;
    }
}
