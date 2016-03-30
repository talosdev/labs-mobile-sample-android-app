package com.labsmobile.example.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by apapad on 29/03/16.
 */
public class DefaultOTPVerificationSuccessBroadcastReceiver extends OTPVerificationSuccessBroadcastReceiver {

    public static final String TAG = "OTP_VS_BR";

    VerificationSuccessCallback callback;

    public DefaultOTPVerificationSuccessBroadcastReceiver(VerificationSuccessCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "DefaultOTPVerificationSuccessBroadcastReceiver received intent");
        if (intent.getAction().equals(OTPVerificationService.VERIFICATION_SUCCESS)) {
            Log.d(TAG, "DefaultOTPVerificationSuccessBroadcastReceiver handling intent...");
            callback.onVerificationSuccess();
        }
    }
}
