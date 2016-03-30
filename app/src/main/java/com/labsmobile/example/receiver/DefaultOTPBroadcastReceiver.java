package com.labsmobile.example.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.labsmobile.example.Constants;

/**
 * Default implementation of {@link OTPBroadcastReceiver} that extracts the code and starts a
 * Service to handle the verification process automatically.
 * Subclasses can customize the behaviour by sublassing the methods
 * {@lnk DefaultOTPBroadcastReceiver#shouldProcessMessage} and
 * {@link DefaultOTPBroadcastReceiver#getVerificationCode(String, String)}
 * <br>
 * Adapted from
 * http://www.androidhive.info/2015/08/android-adding-sms-verification-like-whatsapp-part-2/
 *
 * Created by apapad on 25/03/16.
 */
public class DefaultOTPBroadcastReceiver extends OTPBroadcastReceiver {

    private final String username;
    private final String password;
    private String sender;

    private String messageTemplate;

    private Class<? extends OTPVerificationService> serviceClass;
    private String env;
    private String phoneNumber;

    public DefaultOTPBroadcastReceiver(String sender, String messageTemplate, String phoneNumber,
                                       Class<? extends OTPVerificationService> verificationServiceClass,
                                       String username, String password, String env) {
        this.sender = sender;
        this.messageTemplate = messageTemplate;
        this.phoneNumber = phoneNumber;
        this.serviceClass = verificationServiceClass;
        this.username = username;
        this.password = password;
        this.env = env;
    }

    public DefaultOTPBroadcastReceiver(String sender, String messageTemplate, String phoneNumber,
                                       Class<? extends OTPVerificationService> verificationServiceClass,
                                       String username, String password) {
        this.sender = sender;
        this.messageTemplate = messageTemplate;
        this.phoneNumber = phoneNumber;
        this.serviceClass = verificationServiceClass;
        this.username = username;
        this.password = password;
        this.env = "";
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
                    if (shouldProcessMessage(senderAddress, message)) {
                        // verification code from sms
                        String verificationCode = getVerificationCode(sender, message);

                        Log.d(TAG, "OTP received: " + verificationCode);

                        // TODO, we need a bound service, and pass the otpService through it.
                        Intent otpIntent = new Intent(context, serviceClass);
                        otpIntent.putExtra(OTPVerificationService.EXTRA_CODE, verificationCode);
                        otpIntent.putExtra(OTPVerificationService.EXTRA_USERNAME, username);
                        otpIntent.putExtra(OTPVerificationService.EXTRA_PASSWORD, password);
                        otpIntent.putExtra(OTPVerificationService.EXTRA_ENV, env);
                        otpIntent.putExtra(OTPVerificationService.EXTRA_PHONE_NUMBER, phoneNumber);
                        context.startService(otpIntent);


                    }

                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Checks whether we should process this message. This implementation checks whether the
     * #senderAddress includes the #sender that was used to initialize this class. Both Strings are
     * converted to lowercase before the comparison.
     * @param senderAddress
     * @param message
     * @return
     */
    protected boolean shouldProcessMessage(String senderAddress, String message) {
        return senderAddress.toLowerCase().contains(sender.toLowerCase());
    }


    /**
     * Extracts the verification code from the message received.
     * This implementation looks in the #message String to find the substring of #messageTemplate up to
     * @{link Constants#OTP_TOKEN} and then reads @{link Constants#OTP_LENGTH} charactes starting
     * from that point.
     * @param sender
     * @param message
     * @return
     */
    protected String getVerificationCode(String sender, String message) {
        Log.d(TAG, "Extracting code from message: " + message);

        String before = message.substring(0, messageTemplate.indexOf(Constants.OTP_TOKEN));

        String code = message.substring(before.length(), before.length() + Constants.OTP_LENGTH);

        Log.d(TAG, "Extracted code from message: " + code);

        return code;
    }
}
