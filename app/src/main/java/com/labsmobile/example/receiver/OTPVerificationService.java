package com.labsmobile.example.receiver;

import android.app.IntentService;
import android.content.Intent;

import com.labsmobile.android.service.OTPService;
import com.labsmobile.android.service.ServiceFactory;

/**
 * Created by apapad on 27/03/16.
 */
public abstract class OTPVerificationService extends IntentService {

    public static final String EXTRA_USERNAME = "com.labsmobile.USERNAME";
    public static final String EXTRA_PASSWORD = "com.labsmobile.PASSWORD";
    public static final String EXTRA_ENV = "com.labsmobile.ENV";
    public static final String EXTRA_CODE = "com.labsmobile.CODE";
    public static final String EXTRA_PHONE_NUMBER = "com.labsmobile.PHONE_NUMBER";

    protected static final String TAG = "OTP_VERIF_SERVICE";

    protected OTPService otpService;
    protected String phoneNumber;
    protected String code;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public OTPVerificationService(String name) {
        super(name);
    }


    protected void extractIntentParmas(Intent intent) {
        code = intent.getStringExtra(EXTRA_CODE);
        phoneNumber = intent.getStringExtra(EXTRA_PHONE_NUMBER);
        otpService = ServiceFactory.newInstance(intent.getStringExtra(EXTRA_USERNAME),
                intent.getStringExtra(EXTRA_PASSWORD),
                intent.getStringExtra(EXTRA_ENV)).createOTPService();
    }
}
