/*
 * Copyright (c) 2016, LabsMobile. All rights reserved.
 */

package com.labsmobile.example.otp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.labsmobile.example.util.BaseRequestFragment;
import com.labsmobile.example.R;

import butterknife.ButterKnife;

/**
 * Fragment that informs the user that their number is verified.

 * @author talosdev for LabsMobile
 * @version 1.0
 * @since 1.0
*/
public class VerificationSuccessFragment extends BaseRequestFragment {


    public VerificationSuccessFragment() {
    }


    public static VerificationSuccessFragment newInstance() {
        VerificationSuccessFragment f = new VerificationSuccessFragment();
        //Bundle b = f.getBaseArgs(phoneNumber);
        Bundle b = new Bundle();
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
