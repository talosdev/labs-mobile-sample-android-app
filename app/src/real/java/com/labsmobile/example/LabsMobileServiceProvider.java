package com.labsmobile.example;

import com.labsmobile.android.service.OTPService;
import com.labsmobile.android.service.QueryService;
import com.labsmobile.android.service.SMSService;
import com.labsmobile.android.service.ServiceFactory;
import com.labsmobile.android.service.background.AutomaticVerificationSuccessCallback;
import com.labsmobile.android.service.background.OTPSMSReceiver;
import com.labsmobile.android.service.background.OTPVerificationSuccessReceiver;

/**
 * Factory/Provider that is used to obtain the LabsMobile services. It is actually a facade to the
 * {@link ServiceFactory}. Before any of the <code>provideXXXService</code> methods are called,
 * a method to {@link LabsMobileServiceProvider#init} must be made.
 *
 * Created by apapad on 24/03/16.
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
