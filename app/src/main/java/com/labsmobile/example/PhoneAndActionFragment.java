package com.labsmobile.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by apapad on 25/03/16.
 */
public class PhoneAndActionFragment extends Fragment {


    @Bind(R.id.phone_number)
    EditText phoneNumberEditText;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.button)
    Button button;

    public PhoneAndActionFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_phone_and_action, container, false);

        ButterKnife.bind(this, rootView);

        ButtonTogglingTextWatcher buttonToggler = new ButtonTogglingTextWatcher(button, phoneNumberEditText);
        phoneNumberEditText.addTextChangedListener(buttonToggler);

        return rootView;
    }

}
