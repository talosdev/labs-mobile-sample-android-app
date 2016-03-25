package com.labsmobile.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.labsmobile.android.error.GenericError;
import com.labsmobile.android.model.OTPValidationRequest;
import com.labsmobile.android.service.OTPService;
import com.labsmobile.android.service.ServiceCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TwoFactorVerificationResultActivity extends BaseActivityWithMenu {

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.result)
    TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tfv_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra(Constants.EXTRA_PHONE_NUMBER);
        String code = intent.getStringExtra(Constants.EXTRA_CODE);

        OTPService otpService = LabsMobileServiceProvider.provideOTPService();

        OTPValidationRequest request = new OTPValidationRequest(phoneNumber, code);

        otpService.validateCode(request, new ServiceCallback<Boolean>() {
            @Override
            public void onResponseOK(Boolean aBoolean) {
                progressBar.setVisibility(View.GONE);
                resultView.setText(getResources().getString(aBoolean.booleanValue() ? R.string.verificationOK : R.string.verificationNOK));
            }

            @Override
            public void onResponseNOK(GenericError genericError) {
                resultView.setText(getResources().getString(R.string.responseNOK));
            }

            @Override
            public void onError(Throwable throwable) {
                resultView.setText(getResources().getString(R.string.responseError));
            }
        });
    }



    public static Intent newIntent(Context context, String phoneNumber, String code) {
        Intent i = new Intent(context, TwoFactorVerificationResultActivity.class);
        i.putExtra(Constants.EXTRA_PHONE_NUMBER, phoneNumber);
        i.putExtra(Constants.EXTRA_CODE, code);
        return i;
    }




}
