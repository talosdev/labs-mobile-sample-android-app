package com.labsmobile.example;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.labsmobile.android.model.OTPRequest;
import com.labsmobile.android.service.OTPService;

import butterknife.OnClick;

/**
 * Created by apapad on 25/03/16.
 */
public class RequestCodeFragment extends PhoneAndActionFragment {

    private Navigator navigator;

    private OTPService otpService;

    public RequestCodeFragment() {
    }


    public static RequestCodeFragment newInstance() {
        RequestCodeFragment f = new RequestCodeFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (Navigator) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        otpService = LabsMobileServiceProvider.provideOTPService();

        button.setText(R.string.request);
        return rootView;
    }

    @OnClick(R.id.button)
    public void onRequestButtonClick() {
        progressBar.setVisibility(View.VISIBLE);

        final String phoneNumber = phoneNumberEditText.getText().toString();

        final OTPRequest request = new OTPRequest(phoneNumber,
                getResources().getString(R.string.otp_message),
                getResources().getString(R.string.otp_message_sender));

        otpService.sendCode(request, new DefaultServiceCallback<Boolean>(getActivity()) {
            @Override
            public void onResponseOK(Boolean aBoolean) {
                progressBar.setVisibility(View.GONE);

                navigator.onCodeRequested(request.getPhoneNumber());
            }
        });

    }


}
