package com.labsmobile.example;

import android.util.Log;

import com.labsmobile.android.model.OTPCheckRequest;
import com.labsmobile.android.model.OTPRequest;
import com.labsmobile.android.model.OTPValidationRequest;
import com.labsmobile.android.service.OTPService;
import com.labsmobile.android.service.QueryService;
import com.labsmobile.android.service.SMSService;
import com.labsmobile.android.service.ServiceCallback;
import com.labsmobile.android.service.ServiceFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apapad on 24/03/16.
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
        serviceFactory = ServiceFactory.newInstance(username, password, env);
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


    private static void checkInitialized() throws IllegalStateException {
        if (!initialized)  {
            throw new IllegalStateException("The Provider has not been initialized properly. " +
                    "Please make sure you have called the init() methos with valid " +
                    "LabsMobile credentials before calling any of the proviceXXX() methods");
        }
    }

    static class MockOTPService implements OTPService {

        Map<String, Boolean> verifications = new HashMap<>();

        @Override
        public void sendCode(OTPRequest otpRequest, ServiceCallback<Boolean> serviceCallback) {
            verifications.put(otpRequest.getPhoneNumber(), false);
            serviceCallback.onResponseOK(true);
        }

        @Override
        public void resendCode(OTPRequest otpRequest, ServiceCallback<Boolean> serviceCallback) {
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
