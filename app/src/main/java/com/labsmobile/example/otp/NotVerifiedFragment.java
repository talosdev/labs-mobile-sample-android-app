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
 * Fragment shown after a check is made and the result shows that the number is not
 * verified.
 *
 * Created by apapad on 28/03/16.
 */
public class NotVerifiedFragment extends BaseRequestFragment {


    public NotVerifiedFragment() {
    }

    public static NotVerifiedFragment newInstance(String phoneNumber) {
        NotVerifiedFragment f = new NotVerifiedFragment();
        Bundle b = f.getBaseArgs(phoneNumber);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_not_verified, container, false);

        ButterKnife.bind(this, rootView);

        button.setText(R.string.request);

        return rootView;
    }
}
