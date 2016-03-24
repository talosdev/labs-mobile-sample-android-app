package com.labsmobile.example;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CredentialActivity extends AppCompatActivity {

    @Bind(R.id.continue_button)
    Button continueButton;

    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.credentials_username)
    EditText usernameEditText;

    @Bind(R.id.credentials_password)
    EditText passwordEditText;

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
                LabsMobileServiceProvider.init(usernameEditText.getText().toString(), passwordEditText.getText().toString());

                startActivity(MainActivity.newIntent(CredentialActivity.this));
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
