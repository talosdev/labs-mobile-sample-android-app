package com.labsmobile.example;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.labsmobile.android.service.OTPService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apapad on 25/03/16.
 */
public class ValidateCodeFragment extends Fragment {

    private OTPService otpService;

    private String phoneNumber;

    @Bind(R.id.otp)
    TextView codeEditText;

    Navigator navigator;

    public ValidateCodeFragment() {
    }

    public static ValidateCodeFragment newInstance(String phoneNumber) {
        ValidateCodeFragment f = new ValidateCodeFragment();
        Bundle b = new Bundle();
        b.putString(Constants.EXTRA_PHONE_NUMBER, phoneNumber);

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
        View rootView = inflater.inflate(R.layout.fragment_validate_code, container, false);

        ButterKnife.bind(this, rootView);

        otpService = LabsMobileServiceProvider.provideOTPService();

        return rootView;
    }

    @OnClick(R.id.button)
    public void onOkButton() {
        navigator.onCodeVerification(getArguments().getString(Constants.EXTRA_PHONE_NUMBER),
                codeEditText.getText().toString());
    }


}
