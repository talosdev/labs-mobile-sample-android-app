package com.labsmobile.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TwoFactorVerificationCodeActivity extends BaseActivityWithMenu {

    private String phoneNumber;

    @Bind(R.id.otp)
    TextView codeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tfv_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        phoneNumber = getIntent().getStringExtra(Constants.EXTRA_PHONE_NUMBER);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.button)
    public void onOkButton() {
        startActivity(TwoFactorVerificationResultActivity.newIntent(this, phoneNumber, codeEditText.getText().toString()));
    }

    public static Intent newIntent(Context context, String phoneNumber) {
        Intent i = new Intent(context, TwoFactorVerificationCodeActivity.class);
        i.putExtra(Constants.EXTRA_PHONE_NUMBER, phoneNumber);
        return i;
    }
}
