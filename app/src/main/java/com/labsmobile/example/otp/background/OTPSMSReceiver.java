package com.labsmobile.example.otp.background;

import android.content.BroadcastReceiver;

/**
 * Base class for a {@link BroadcastReceiver} that checks the SMSs received and extracts the verification
 * code.
 * Created by apapad on 27/03/16.
 */
public abstract class OTPSMSReceiver extends BroadcastReceiver{

    public static final String TAG = "OTP_BR";
}
