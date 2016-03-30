package com.labsmobile.example.otp.background;

import android.os.IBinder;

import com.labsmobile.android.service.OTPService;

/**
 * Created by apapad on 27/03/16.
 */
public interface OTPBinder extends IBinder {

    void setOTPService(OTPService service);
}
