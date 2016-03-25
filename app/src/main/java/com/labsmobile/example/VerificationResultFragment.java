package com.labsmobile.example;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.labsmobile.android.error.GenericError;
import com.labsmobile.android.model.OTPValidationRequest;
import com.labsmobile.android.service.OTPService;
import com.labsmobile.android.service.ServiceCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by apapad on 25/03/16.
 */
public class VerificationResultFragment extends Fragment {


    private OTPService otpService;

    private Navigator navigator;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.result)
    TextView resultView;



    public VerificationResultFragment() {
    }


    public static VerificationResultFragment newInstance(String phoneNumber, String code) {
        VerificationResultFragment f = new VerificationResultFragment();
        Bundle b = new Bundle();
        b.putString(Constants.EXTRA_PHONE_NUMBER, phoneNumber);
        b.putString(Constants.EXTRA_CODE, code);

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
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);

        ButterKnife.bind(this, rootView);

        otpService = LabsMobileServiceProvider.provideOTPService();

        OTPValidationRequest request = new OTPValidationRequest(
                getArguments().getString(Constants.EXTRA_PHONE_NUMBER),
                getArguments().getString(Constants.EXTRA_CODE)
        );

        otpService.validateCode(request, new ServiceCallback<Boolean>() {
            @Override
            public void onResponseOK(Boolean aBoolean) {
                progressBar.setVisibility(View.GONE);
                resultView.setText(getResources().getString(aBoolean.booleanValue() ? R.string.verificationOK : R.string.verificationNOK));
            }

            @Override
            public void onResponseNOK(GenericError genericError) {
                resultView.setText(getResources().getString(R.string.responseNOK));
            }

            @Override
            public void onError(Throwable throwable) {
                resultView.setText(getResources().getString(R.string.responseError));
            }
        });


        return rootView;
    }

}
