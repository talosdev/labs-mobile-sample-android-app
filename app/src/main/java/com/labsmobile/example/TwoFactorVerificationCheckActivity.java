package com.labsmobile.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.labsmobile.android.model.OTPCheckRequest;

import butterknife.OnClick;

public class TwoFactorVerificationCheckActivity extends TwoFactorVerificationBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        button.setText(R.string.check);
    }

    @OnClick(R.id.button)
    public void onCheckButtonClick() {
        progressBar.setVisibility(View.VISIBLE);

        OTPCheckRequest request = new OTPCheckRequest(phoneNumberEditText.getText().toString(),
                envEditText.getText().toString());

        otpService.checkCode(request, new DefaultServiceCallback<Boolean>(TwoFactorVerificationCheckActivity.this) {
            @Override
            public void onResponseOK(Boolean aBoolean) {
                progressBar.setVisibility(View.GONE);

                if (aBoolean == null) {
                    Toast.makeText(TwoFactorVerificationCheckActivity.this,
                            R.string.tfv_check_noCode,
                            Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(TwoFactorVerificationCheckActivity.this,
                            aBoolean.booleanValue() ? R.string.tfv_check_verified : R.string.tfv_check_not_verified,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, TwoFactorVerificationCheckActivity.class);
        return i;
    }

}
