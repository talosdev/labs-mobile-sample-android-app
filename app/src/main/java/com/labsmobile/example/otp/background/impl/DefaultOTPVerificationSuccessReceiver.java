package com.labsmobile.example.otp.background.impl;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.labsmobile.example.otp.background.AutomaticVerificationSuccessCallback;
import com.labsmobile.example.otp.background.OTPVerificationService;
import com.labsmobile.example.otp.background.OTPVerificationSuccessReceiver;

/**
 * Default implementation of {@link OTPVerificationSuccessReceiver} that uses a
 * {@link AutomaticVerificationSuccessCallback} to notify the application that the phone number has
 * been verified correctly.
 *
 * Created by apapad on 29/03/16.
 */
public class DefaultOTPVerificationSuccessReceiver extends OTPVerificationSuccessReceiver {

    public static final String TAG = "OTP_VS_BR";

    AutomaticVerificationSuccessCallback callback;

    public DefaultOTPVerificationSuccessReceiver(AutomaticVerificationSuccessCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "DefaultOTPVerificationSuccessReceiver received intent");
        if (intent.getAction().equals(OTPVerificationService.SUCCESS_INTENT)) {
            Log.d(TAG, "DefaultOTPVerificationSuccessReceiver handling intent...");
            callback.onVerificationSuccess();
        }
    }
}
