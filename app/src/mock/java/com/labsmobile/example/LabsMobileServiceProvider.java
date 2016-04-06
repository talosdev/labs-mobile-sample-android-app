/*
 * Copyright (c) 2016, LabsMobile. All rights reserved.
 */

package com.labsmobile.example;

import android.util.Log;

import com.labsmobile.android.model.OTPCheckRequest;
import com.labsmobile.android.model.OTPSendCodeRequest;
import com.labsmobile.android.model.OTPValidationRequest;
import com.labsmobile.android.service.OTPService;
import com.labsmobile.android.service.QueryService;
import com.labsmobile.android.service.SMSService;
import com.labsmobile.android.service.ServiceCallback;
import com.labsmobile.android.service.ServiceFactory;
import com.labsmobile.android.service.background.AutomaticVerificationSuccessCallback;
import com.labsmobile.android.service.background.OTPSMSReceiver;
import com.labsmobile.android.service.background.OTPVerificationSuccessReceiver;

import java.util.HashMap;
import java.util.Map;

/**
 * Alternative mock implementation of the LabsMobileServiceProvider, that provides a mock
 * {@link OTPService}, that doesn't actually send SMSs. Can be used for testing on an emulator,
 * by sending an SMS through ADB. In the verification step, if the code starts with "VALID", the
 * verification is successful.
 * <br/>
 * Before any of the <code>provideXXXService</code> methods are called,
 * a method to {@link LabsMobileServiceProvider#init} must be made.
 *
 */
public class LabsMobileServiceProvider {

    private static LabsMobileServiceProvider provider;
    private static ServiceFactory serviceFactory;
    private static boolean initialized;
    private static OTPService otpService;




    private LabsMobileServiceProvider() {
    }

    public static void init(String username, String password, String env) {
        ServiceFactory.DEBUG = true;
        serviceFactory = ServiceFactory.getInstance(username, password, env);
        initialized = true;
    }

    public static QueryService provideQueryService() {
        checkInitialized();
        return serviceFactory.createQueryService();
    }
    public static SMSService provideSMSService() {
        checkInitialized();
        return serviceFactory.createSMSService();
    }
    public static OTPService provideOTPService() {
        checkInitialized();
        Log.d("MOCK", "Using mock OTPService");
        if (otpService == null) {
            otpService = new MockOTPService();
        }
        return otpService;

    }

    public static OTPVerificationSuccessReceiver provideOTPVerificationSuccessReceiver(AutomaticVerificationSuccessCallback callback) {
        checkInitialized();
        return serviceFactory.createOTPVerificationSuccessReceiver(callback);
    }


    public static OTPSMSReceiver provideOTPSMSReceiver(String sender, String messageTemplate, String phoneNumber) {
        checkInitialized();
        return serviceFactory.createOTPSMSReceiver(sender, messageTemplate, phoneNumber);
    }

    private static void checkInitialized() throws IllegalStateException {
        if (!initialized)  {
            throw new IllegalStateException("The Provider has not been initialized properly. " +
                    "Please make sure you have called the init() method with valid " +
                    "LabsMobile credentials before calling any of the proviceXXX() methods");
        }
    }

    static class MockOTPService implements OTPService {

        Map<String, Boolean> verifications = new HashMap<>();


        @Override
        public void sendCode(OTPSendCodeRequest otpSendCodeRequest, ServiceCallback<Boolean> serviceCallback) {
            verifications.put(otpSendCodeRequest.getPhoneNumber(), false);
            serviceCallback.onResponseOK(true);
        }

        @Override
        public void resendCode(OTPSendCodeRequest otpSendCodeRequest, ServiceCallback<Boolean> serviceCallback) {
            serviceCallback.onResponseOK(true);

        }

        @Override
        public void validateCode(OTPValidationRequest otpValidationRequest, ServiceCallback<Boolean> serviceCallback) {
            String code = otpValidationRequest.getCode();
            String phone = otpValidationRequest.getPhoneNumber();
            if (code.startsWith("VALID")) {
                verifications.put(phone, true);
                serviceCallback.onResponseOK(true);
            } else {
                serviceCallback.onResponseOK(false);
            }
        }

        @Override
        public void checkCode(OTPCheckRequest otpCheckRequest, ServiceCallback<Boolean> serviceCallback) {
            String phone = otpCheckRequest.getPhoneNumber();
            // returns true, false or null, in accordance to the API
            serviceCallback.onResponseOK(verifications.get(phone));
        }
    }
}
