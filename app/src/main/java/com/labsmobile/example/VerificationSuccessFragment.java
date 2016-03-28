package com.labsmobile.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by apapad on 25/03/16.
 */
public class VerificationSuccessFragment extends BaseRequestFragment {





    public VerificationSuccessFragment() {
    }


    public static VerificationSuccessFragment newInstance(String phoneNumber) {
        VerificationSuccessFragment f = new VerificationSuccessFragment();
        Bundle b = f.getBaseArgs(phoneNumber);

        f.setArguments(b);
        return f;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_success, container, false);

        ButterKnife.bind(this, rootView);


        return rootView;
    }

}
