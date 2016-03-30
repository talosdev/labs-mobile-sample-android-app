package com.labsmobile.example;

/**
 * Created by apapad on 25/03/16.
 */
public interface Navigator {


    void onCheckResult(String phoneNumber, boolean pendingRequestExists);

    void onNumberVerified();

    void onCodeRequested(String phoneNumber);
}
