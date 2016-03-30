package com.labsmobile.example.otp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.labsmobile.android.model.OTPCheckRequest;
import com.labsmobile.android.service.OTPService;
import com.labsmobile.example.LabsMobileServiceProvider;
import com.labsmobile.example.R;
import com.labsmobile.example.util.ButtonTogglingTextWatcher;
import com.labsmobile.example.util.DefaultServiceCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apapad on 28/03/16.
 */
public class CheckStatusFragment extends Fragment {

    public static final String TAG = "OTP_STATUS";

    private OTPService otpService;

    @Bind(R.id.phone_number)
    EditText phoneNumberEditText;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.button)
    Button button;

    private Navigator navigator;

    public CheckStatusFragment() {
        otpService = LabsMobileServiceProvider.provideOTPService();
    }

    public static CheckStatusFragment newInstance() {
        CheckStatusFragment f = new CheckStatusFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_check, container, false);

        ButterKnife.bind(this, rootView);

        ButtonTogglingTextWatcher buttonToggler = new ButtonTogglingTextWatcher(button, phoneNumberEditText);
        phoneNumberEditText.addTextChangedListener(buttonToggler);

        return rootView;
    }

    @OnClick(R.id.button)
    public void onCheckButtonClick() {
        progressBar.setVisibility(View.VISIBLE);

        final String phoneNumber = phoneNumberEditText.getText().toString();

        OTPCheckRequest request = new OTPCheckRequest(phoneNumber);

        otpService.checkCode(request, new DefaultServiceCallback<Boolean>(getActivity(), progressBar) {
            @Override
            public void onResponseOK(Boolean aBoolean) {
                progressBar.setVisibility(View.GONE);

                if (aBoolean == null) {
                    Log.d(TAG, "Number not verified, and no pending process");
                    navigator.onNumberNotVerifiedResult(phoneNumber, false);

                } else {
                    if (aBoolean.booleanValue()) {
                        Log.d(TAG, "Number verified");
                        navigator.onNumberVerified();
                    } else {
                        Log.d(TAG, "Number not verified, but process in progress");
                        navigator.onNumberNotVerifiedResult(phoneNumber, true);
                    }
                }
            }

        });

    }
}
