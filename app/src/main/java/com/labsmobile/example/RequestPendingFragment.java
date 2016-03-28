package com.labsmobile.example;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.labsmobile.android.error.GenericError;
import com.labsmobile.android.model.OTPValidationRequest;
import com.labsmobile.android.service.ServiceCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apapad on 25/03/16.
 */
public class RequestPendingFragment extends BaseRequestFragment {

    @Bind(R.id.progressBar_manual)
    ProgressBar progressBarManual;


    @Bind(R.id.otp)
    TextView codeEditText;

    Navigator navigator;

    public RequestPendingFragment() {
    }

    public static RequestPendingFragment newInstance(String phoneNumber) {
        RequestPendingFragment f = new RequestPendingFragment();
        Bundle b = f.getBaseArgs(phoneNumber);

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
        View rootView = inflater.inflate(R.layout.fragment_verification_pending, container, false);

        ButterKnife.bind(this, rootView);

        otpService = LabsMobileServiceProvider.provideOTPService();

        return rootView;
    }

    @OnClick(R.id.button_ok)
    public void onOkButton() {

        progressBarManual.setVisibility(View.VISIBLE);

        final String code =  codeEditText.getText().toString();

        OTPValidationRequest request = new OTPValidationRequest(
                getArguments().getString(Constants.EXTRA_PHONE_NUMBER),
               code
        );

        otpService.validateCode(request, new ServiceCallback<Boolean>() {
            @Override
            public void onResponseOK(Boolean aBoolean) {
                progressBarManual.setVisibility(View.INVISIBLE);
                navigator.onNumberVerified(getArguments().getString(Constants.EXTRA_PHONE_NUMBER));
            }

            @Override
            public void onResponseNOK(GenericError genericError) {
                progressBarManual.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(),
                        R.string.responseNOK,
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable throwable) {
                progressBarManual.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(),
                        R.string.responseError,
                        Toast.LENGTH_LONG).show();
            }
        });


    }


}
