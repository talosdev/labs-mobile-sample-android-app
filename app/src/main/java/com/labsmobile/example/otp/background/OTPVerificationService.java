package com.labsmobile.example.otp.background;

import android.app.IntentService;
import android.content.Intent;

import com.labsmobile.android.service.OTPService;
import com.labsmobile.android.service.ServiceFactory;

/**
 * Base class for the background service that handles the automatic verification upon the reception
 * of the verification code.
 * Offers utility methods for putting the extras to the Intent required to start this service, and
 * for extracting the values from the Intent.
 *
 * @see com.labsmobile.example.otp.background.impl.DefaultOTPVerificationService
 *
 * Created by apapad on 27/03/16.
 */
public abstract class OTPVerificationService extends IntentService {

    public static final String TAG = "OTP_VERIF_SERVICE";

    private static final String EXTRA_USERNAME = "com.labsmobile.USERNAME";
    private static final String EXTRA_PASSWORD = "com.labsmobile.PASSWORD";
    private static final String EXTRA_ENV = "com.labsmobile.ENV";
    private static final String EXTRA_CODE = "com.labsmobile.CODE";
    private static final String EXTRA_PHONE_NUMBER = "com.labsmobile.PHONE_NUMBER";

    public static final String SUCCESS_INTENT = "com.labsmobile.SUCCESS_INTENT";

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

    public static void putExtras(Intent intent,
                                   String username,
                                   String password,
                                   String env,
                                   String phoneNumber,
                                   String code) {

        intent.putExtra(OTPVerificationService.EXTRA_USERNAME, username);
        intent.putExtra(OTPVerificationService.EXTRA_PASSWORD, password);
        intent.putExtra(OTPVerificationService.EXTRA_ENV, env);
        intent.putExtra(OTPVerificationService.EXTRA_PHONE_NUMBER, phoneNumber);
        intent.putExtra(OTPVerificationService.EXTRA_CODE, code);
    }


    protected void extractIntentParams(Intent intent) {
        code = intent.getStringExtra(EXTRA_CODE);
        phoneNumber = intent.getStringExtra(EXTRA_PHONE_NUMBER);

        otpService = ServiceFactory.newInstance(intent.getStringExtra(EXTRA_USERNAME),
                intent.getStringExtra(EXTRA_PASSWORD),
                intent.getStringExtra(EXTRA_ENV)).createOTPService();
    }


}
