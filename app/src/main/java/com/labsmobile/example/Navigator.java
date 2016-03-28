package com.labsmobile.example;

/**
 * Created by apapad on 25/03/16.
 */
public interface Navigator {

    void onPendingRequest(String phoneNumber);


    void onNumberVerified(String phoneNumber);

    void onNoPendingRequest(String phoneNumber);
}
