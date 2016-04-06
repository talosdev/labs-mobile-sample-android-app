/*
 * Copyright (c) 2016, LabsMobile. All rights reserved.
 */

package com.labsmobile.example.otp;

/**
 * Handles the navigation for the {@link TwoFactorVerificationActivity}
 *
 * @author talosdev for LabsMobile
 * @version 1.0
 */
public interface Navigator {

    /**
     * Callback that is called when a verification has just taken place
     * (either manual or automatic) or when the result of a verification status check
     * comes back and says that the number is already verified.
     */
    void onNumberVerified();



    /**
     * Called when the result of a verification status check comes back, that shows that the number
     * is not verified.
     * @param phoneNumber the phone number in question
     * @param pendingRequestExists whether there is a pending verification process in progress
     */
    void onNumberNotVerifiedResult(String phoneNumber, boolean pendingRequestExists);


    /**
     * Called when a code has been requested for a phone number.
     * @param phoneNumber the phone number
     */
    void onCodeRequested(String phoneNumber);
}
