package com.labsmobile.example.otp.background;

import android.content.BroadcastReceiver;

/**
 * Base class for a {@link BroadcastReceiver} that receives and handles the notification
 * from the {@link OTPVerificationService} that the number has been verified automatically.
 *
 *
 * Created by apapad on 27/03/16.
 */
public abstract class OTPVerificationSuccessReceiver extends BroadcastReceiver{

    public static final String TAG = "OTP_BR";
}
