package com.labsmobile.example.receiver;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.labsmobile.android.error.GenericError;
import com.labsmobile.android.model.OTPValidationRequest;
import com.labsmobile.android.service.ServiceCallback;
import com.labsmobile.example.R;

/**
 * Created by apapad on 27/03/16.
 */
public class DefaultOTPVerificationService extends OTPVerificationService {


    public DefaultOTPVerificationService() {
        super("OTP_Verification_Service");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        extractIntentParmas(intent);
        Log.d(TAG, "Handling an intent: " + code + " - " + phoneNumber);
        OTPValidationRequest request = new OTPValidationRequest(phoneNumber, code);


        otpService.validateCode(request, new ServiceCallback<Boolean>() {
            @Override
            public void onResponseOK(Boolean aBoolean) {
                if (aBoolean == null) {
                    Log.d(TAG, "A null response was received from the Verification service");
                }

                if (aBoolean.booleanValue()) {
                    Log.d(TAG, "SUCCESS");
                    //TODO
                    Toast.makeText(getBaseContext(), R.string.verificationOK, Toast.LENGTH_LONG).show();
                } else {
                    Log.d(TAG, "The number could not be verified");
                }

            }

            @Override
            public void onResponseNOK(GenericError genericError) {
                Log.e(TAG, "There was a problem with the request: " +
                        genericError.getStatusCode() + " - " + genericError.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, "An unexpected error occurred", throwable);
            }
        });
    }

}
