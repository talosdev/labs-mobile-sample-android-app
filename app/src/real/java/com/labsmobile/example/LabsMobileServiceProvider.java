/*
 * Copyright (c) 2016, LabsMobile. All rights reserved.
 */

package com.labsmobile.example;

import com.labsmobile.android.service.OTPService;
import com.labsmobile.android.service.QueryService;
import com.labsmobile.android.service.SMSService;
import com.labsmobile.android.service.ServiceFactory;
import com.labsmobile.android.service.background.AutomaticVerificationSuccessCallback;
import com.labsmobile.android.service.background.OTPSMSReceiver;
import com.labsmobile.android.service.background.OTPVerificationSuccessReceiver;

/**
 * Factory that is used to obtain the LabsMobile services. It is actually a proxy to
 * {@link ServiceFactory}. The reason we need to use it is. because in this application, the
 * LabsMobile credentials are runtime parameters, provided by the user of the application, so we
 * use this class to store a {@link ServiceFactory} instance that has been initialized with these
 * credentials, instead of passing the credentials around between Activities, or storing them in
 * SharedPreferences. In a typical real-life application, however, this shouldn't be necessary;
 * the credentials would be fixed, so the library client can just reference the {@link ServiceFactory}
 * directly, using these fixed credentials (or, preferable, use some DI technique to access the
 * services offered by the factory).
 * <br/>
 * Before any of the <code>provideXXXService</code> methods are called,
 * a method to {@link LabsMobileServiceProvider#init} must be made.
 *
 * @author talosdev for LabsMobile
 * @version 1.0
*/
public class LabsMobileServiceProvider {

    private static ServiceFactory serviceFactory;
    private static boolean initialized;

    private LabsMobileServiceProvider() {
    }

    /**
     * Initialization method.
     * @param username The LabsMobile account username
     * @param password The LabsMobile account password
     * @param env The env to use (might be null)
     */
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
        return serviceFactory.createOTPService();
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
                    "Please make sure you have called the init() methods with valid " +
                    "LabsMobile credentials before calling any of the provideXXX() methods");
        }
    }
}
