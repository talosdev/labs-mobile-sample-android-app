package com.labsmobile.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.labsmobile.android.model.OTPCheckRequest;
import com.labsmobile.android.service.OTPService;

import butterknife.OnClick;

/**
 * Created by apapad on 25/03/16.
 */
public class CheckVerificationFragment extends PhoneAndActionFragment {

    private OTPService otpService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        otpService = LabsMobileServiceProvider.provideOTPService();

        button.setText(R.string.check);
        return rootView;
    }

    @OnClick(R.id.button)
    public void onCheckButtonClick() {
        progressBar.setVisibility(View.VISIBLE);

        OTPCheckRequest request = new OTPCheckRequest(phoneNumberEditText.getText().toString());

        otpService.checkCode(request, new DefaultServiceCallback<Boolean>(getActivity()) {
            @Override
            public void onResponseOK(Boolean aBoolean) {
                progressBar.setVisibility(View.GONE);

                if (aBoolean == null) {
                    Toast.makeText(getActivity(),
                            R.string.tfv_check_noCode,
                            Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(getActivity(),
                            aBoolean.booleanValue() ? R.string.tfv_check_verified : R.string.tfv_check_not_verified,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
