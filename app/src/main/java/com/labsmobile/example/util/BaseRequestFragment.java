package com.labsmobile.example.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ProgressBar;

import com.labsmobile.android.model.OTPRequest;
import com.labsmobile.android.service.OTPService;
import com.labsmobile.example.LabsMobileServiceProvider;
import com.labsmobile.example.R;
import com.labsmobile.example.otp.Navigator;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Base Fragment that can be used when a fragment includes a button
 * to request a code to be sent by SMS. The fragment handles the UI changes
 * (showing and hiding the progess bar) and handling the result (error messages
 * in case of failure, or navigation in case of success).
 *
 * Created by apapad on 25/03/16.
 */
public class BaseRequestFragment extends Fragment {

    protected OTPService otpService;

    @Bind(R.id.progressBar)
    protected ProgressBar progressBar;

    @Bind(R.id.button_request_code)
    protected Button button;

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
        OTPRequest request = new OTPRequest(phoneNumber,
                getResources().getString(R.string.otp_message),
                getResources().getString(R.string.otp_message_sender));

        otpService.sendCode(request, new DefaultServiceCallback<Boolean>(getActivity(), progressBar ) {
            @Override
            public void onResponseOK(Boolean aBoolean) {
                navigator.onCodeRequested(phoneNumber);
            }
        });

    }


}
