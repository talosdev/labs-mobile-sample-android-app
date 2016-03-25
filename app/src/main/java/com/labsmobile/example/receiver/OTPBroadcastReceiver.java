package com.labsmobile.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.labsmobile.example.Constants;

/**
 * Created by apapad on 25/03/16.
 */
public class OTPBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "OTP_BR";
    private String sender;

    private String messageTemplate;

    private Class<?> serviceClass;

    public OTPBroadcastReceiver(String sender, String messageTemplate) {
        this.sender = sender;
        this.messageTemplate = messageTemplate;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    String senderAddress = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    Log.d(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

                    // if the SMS is not from our gateway, ignore the message
                    if (!senderAddress.toLowerCase().contains(sender)) {
                        return;
                    }

                    // verification code from sms
                    String verificationCode = getVerificationCode(message);

                    Log.d(TAG, "OTP received: " + verificationCode);

                    Intent otpIntent = new Intent(context, serviceClass);
                    otpIntent.putExtra("otp", verificationCode);
                    context.startService(otpIntent);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private String getVerificationCode(String message) {
        Log.d(TAG, "Extracting code from message: " + message);

        String before = message.substring(0, messageTemplate.indexOf(Constants.OTP_TOKEN));

        String code = message.substring(before.length(), before.length() + Constants.OTP_TOKEN.length());

        Log.d(TAG, "Extracted code from message: " + code);

        return code;
    }
}
