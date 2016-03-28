package com.labsmobile.example;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ProgressBar;

import com.labsmobile.android.model.OTPRequest;
import com.labsmobile.android.service.OTPService;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apapad on 25/03/16.
 */
public class BaseRequestFragment extends Fragment {

    OTPService otpService;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.button_request_code)
    Button button;

    protected Navigator navigator;

    public BaseRequestFragment() {
        otpService = LabsMobileServiceProvider.provideOTPService();
    }


    protected Bundle getBaseArgs(String phoneNumber) {
        Bundle b = new Bundle();
        b.putString(Constants.EXTRA_PHONE_NUMBER, phoneNumber);
        return b;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (Navigator) context;
    }

    @OnClick(R.id.button_request_code)
    public void onRequestCode() {
        final String phoneNumber = getArguments().getString(Constants.EXTRA_PHONE_NUMBER);
        OTPRequest request = new OTPRequest(phoneNumber);

        otpService.sendCode(request, new DefaultServiceCallback<Boolean>(getActivity()) {
            @Override
            public void onResponseOK(Boolean aBoolean) {
                navigator.onPendingRequest(phoneNumber);
            }
        });

    }


}
