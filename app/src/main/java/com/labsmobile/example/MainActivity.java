package com.labsmobile.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.labsmobile.example.otp.TwoFactorVerificationActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Main Activity that shows a list of navigation buttons.
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.sms_services_button)
    public void onSmsButton() {
        startActivity(SMSActivity.newIntent(MainActivity.this));
    }

    @OnClick(R.id.query_services_button)
    public void onQueriesButton() {
        startActivity(QueriesActivity.newIntent(MainActivity.this));
    }

    @OnClick(R.id.otp_services_button)
    public void onOTPButton() {
        startActivity(TwoFactorVerificationActivity.newIntent(MainActivity.this));
    }

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        return i;
    }
}
