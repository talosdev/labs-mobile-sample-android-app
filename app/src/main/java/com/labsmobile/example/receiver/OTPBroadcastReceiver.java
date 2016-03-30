package com.labsmobile.example.receiver;

import android.content.BroadcastReceiver;

/**
 * Base class for a {@link BroadcastReceiver} that checks the SMSs received and extacts the verification
 * code.
 * Created by apapad on 27/03/16.
 */
public abstract class OTPBroadcastReceiver extends BroadcastReceiver{

    public static final String TAG = "OTP_BR";
}
