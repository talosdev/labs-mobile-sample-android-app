package com.labsmobile.example.otp;

/**
 * Handles the navigation for the {@link TwoFactorVerificationActivity}
 *
 * Created by apapad on 25/03/16.
 */
public interface Navigator {

    /**
     * Callback that is called when a verification has just taken place
     * (either manual or automatic) or when a verification check result
     * comes back and says that the number is already verified.
     */
    void onNumberVerified();



    /**
     * Called when a verification check comes back, that shows that the number
     * is not verified.
     * @param phoneNumber the phone number in question
     * @param pendingRequestExists whether there is a pending verification process in progress
     */
    void onNumberNotVerifiedResult(String phoneNumber, boolean pendingRequestExists);


    /**
     * Called when a code has been requested for a phone number
     * @param phoneNumber the phone number
     */
    void onCodeRequested(String phoneNumber);
}
