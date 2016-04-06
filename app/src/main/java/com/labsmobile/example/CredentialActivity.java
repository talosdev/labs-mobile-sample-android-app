/*
 * Copyright (c) 2016, LabsMobile. All rights reserved.
 */

package com.labsmobile.example;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.labsmobile.example.util.ButtonTogglingTextWatcher;
import com.labsmobile.example.util.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activity that allows users to enter their LabsMobile credentials, so that they can test
 * the app.
 *
 * @author talosdev for LabsMobile
 * @version 1.0
 */
public class CredentialActivity extends AppCompatActivity {

    @SuppressWarnings("WeakerAccess")
    @Bind(R.id.continue_button)
    Button continueButton;

    @SuppressWarnings("WeakerAccess")
    @Bind(R.id.register_button)
    Button registerButton;

    @SuppressWarnings("WeakerAccess")
    @Bind(R.id.credentials_username)
    EditText usernameEditText;

    @SuppressWarnings("WeakerAccess")
    @Bind(R.id.credentials_password)
    EditText passwordEditText;

    @SuppressWarnings("WeakerAccess")
    @Bind(R.id.env)
    EditText envEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credential);

        ButterKnife.bind(this);

        ButtonTogglingTextWatcher buttonToggler = new ButtonTogglingTextWatcher(continueButton, usernameEditText, passwordEditText);
        usernameEditText.addTextChangedListener(buttonToggler);
        passwordEditText.addTextChangedListener(buttonToggler);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String env = envEditText.getText().toString();
                LabsMobileServiceProvider.init(username,
                        password,
                        env);

                startActivity(IndexActivity.newIntent(CredentialActivity.this));
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.LABS_MOBILE_REGISTRATION_URL));
                startActivity(browserIntent);
            }
        });

    }

}
