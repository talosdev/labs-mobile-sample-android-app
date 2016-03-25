package com.labsmobile.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.labsmobile.android.model.OTPRequest;

import butterknife.Bind;
import butterknife.OnClick;

public class TwoFactorVerificationRequestActivity extends TwoFactorVerificationBaseActivity {


    private static final String MESSAGE = "Sample app verification ode: %CODE%. Have a nice day!";

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        button.setText(R.string.request);


    }

    @OnClick(R.id.button)
    public void onRequestButtonClick() {
        progressBar.setVisibility(View.VISIBLE);

        final String phoneNumber = phoneNumberEditText.getText().toString();
        OTPRequest request = new OTPRequest(phoneNumber,
                getResources().getString(R.string.otp_message),
                getResources().getString(R.string.otp_message_sender));


        otpService.sendCode(request, new DefaultServiceCallback<Boolean>(TwoFactorVerificationRequestActivity.this) {
            @Override
            public void onResponseOK(Boolean aBoolean) {
                progressBar.setVisibility(View.GONE);

                startActivity(TwoFactorVerificationCodeActivity.newIntent(TwoFactorVerificationRequestActivity.this, phoneNumber));
            }
        });

    }



    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, TwoFactorVerificationRequestActivity.class);
        return i;
    }
}
