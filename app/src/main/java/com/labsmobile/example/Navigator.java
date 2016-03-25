package com.labsmobile.example;

/**
 * Created by apapad on 25/03/16.
 */
public interface Navigator {

    void onCodeRequested(String phoneNumber);


    void onCodeVerification(String phoneNumber, String code);
}
