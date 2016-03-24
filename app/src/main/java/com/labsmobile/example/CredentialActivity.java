package com.labsmobile.example;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

        usernameEditText.addTextChangedListener(new UsernamePasswordTextWatcher(usernameEditText, passwordEditText, continueButton));
        passwordEditText.addTextChangedListener(new UsernamePasswordTextWatcher(usernameEditText, passwordEditText, continueButton));

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

    private static class UsernamePasswordTextWatcher implements TextWatcher {

        EditText usernameEditText;
        EditText passwordEditText;
        Button continueButton;

        public UsernamePasswordTextWatcher(EditText usernameEditText, EditText passwordEditText, Button continueButton) {
            this.usernameEditText = usernameEditText;
            this.passwordEditText = passwordEditText;
            this.continueButton = continueButton;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (usernameEditText.getText().length()==0 ||
                    passwordEditText.getText().length() == 0) {
                continueButton.setEnabled(false);
            } else {
                continueButton.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
