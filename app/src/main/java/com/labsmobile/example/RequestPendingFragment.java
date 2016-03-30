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

    @Bind(R.id.prompt_pending_new)
    View promptsNewCode;

    @Bind(R.id.prompt_pending_old)
    View promptsOldCode;


    @Bind(R.id.otp)
    TextView codeEditText;

    Navigator navigator;

    public RequestPendingFragment() {
    }

    /**
     * @param phoneNumber
     * @param codeJustRequested Whether a code has just been requested, or whether we are coming here after a check
     *                          that showed that a pending request already exists.
     * @return
     */
    public static RequestPendingFragment newInstance(String phoneNumber, boolean codeJustRequested) {
        RequestPendingFragment f = new RequestPendingFragment();
        Bundle b = f.getBaseArgs(phoneNumber);
        b.putBoolean(Constants.EXTRA_REQUESTED, codeJustRequested);

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

        if (getArguments().getBoolean(Constants.EXTRA_REQUESTED)) {
            promptsNewCode.setVisibility(View.VISIBLE);
        } else {
            promptsOldCode.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @OnClick(R.id.button_ok)
    public void onOkButton() {

        progressBarManual.setVisibility(View.VISIBLE);

        final String code = codeEditText.getText().toString();

        OTPValidationRequest request = new OTPValidationRequest(
                getArguments().getString(Constants.EXTRA_PHONE_NUMBER),
                code
        );

        otpService.validateCode(request, new ServiceCallback<Boolean>() {
            @Override
            public void onResponseOK(Boolean aBoolean) {
                progressBarManual.setVisibility(View.INVISIBLE);
                if (aBoolean.booleanValue()) {
                    //getArguments().getString(Constants.EXTRA_PHONE_NUMBER)
                    navigator.onNumberVerified();
                } else {
                    Toast.makeText(getActivity(),
                            R.string.code_invalid,
                            Toast.LENGTH_LONG).show();
                    codeEditText.setText("");
                }

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
