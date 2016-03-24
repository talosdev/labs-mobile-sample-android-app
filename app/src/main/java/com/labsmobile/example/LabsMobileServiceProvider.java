package com.labsmobile.example;

import com.labsmobile.android.service.OTPService;
import com.labsmobile.android.service.QueryService;
import com.labsmobile.android.service.SMSService;
import com.labsmobile.android.service.ServiceFactory;

/**
 * Created by apapad on 24/03/16.
 */
public class LabsMobileServiceProvider {

    private static LabsMobileServiceProvider provider;
    private static ServiceFactory serviceFactory;
    private static boolean initialized;

    private LabsMobileServiceProvider() {
    }

    public static void init(String username, String password) {
        serviceFactory = ServiceFactory.newInstance(username, password);
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
        return serviceFactory.createOTPService();
    }


    private static void checkInitialized() throws IllegalStateException {
        if (!initialized)  {
            throw new IllegalStateException("The Provider has not been initialized properly. " +
                    "Please make sure you have called the init() methos with valid " +
                    "LabsMobile credentials before calling any of the proviceXXX() methods");
        }
    }
}
