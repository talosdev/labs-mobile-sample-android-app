/*
 * Copyright (c) 2016, LabsMobile. All rights reserved.
 */

package com.labsmobile.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.labsmobile.android.model.SMSBuilder;
import com.labsmobile.android.model.SMSData;
import com.labsmobile.android.model.SMSResponse;
import com.labsmobile.android.service.SMSService;
import com.labsmobile.example.util.BaseActivity;
import com.labsmobile.example.util.ButtonTogglingTextWatcher;
import com.labsmobile.example.util.DefaultServiceCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SMSActivity extends BaseActivity {

    @Bind(R.id.phone_number)
    EditText phoneNumberEditText;

    @Bind(R.id.message_text)
    EditText messageEditText;

    @Bind(R.id.send_button)
    Button sendButton;

    @Bind(R.id.test_mode)
    CheckBox testModeCheckBox;

    @Bind(R.id.unicode_mode)
    CheckBox unicodeModeCheckBox;

    @Bind(R.id.long_mode)
    CheckBox longModeCheckBox;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;


    private SMSService smsService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        smsService = LabsMobileServiceProvider.provideSMSService();

        ButterKnife.bind(this);


        ButtonTogglingTextWatcher buttonToggler = new ButtonTogglingTextWatcher(sendButton, phoneNumberEditText, messageEditText);
        phoneNumberEditText.addTextChangedListener(buttonToggler);
        messageEditText.addTextChangedListener(buttonToggler);

    }

    @OnClick(R.id.send_button)
    public void onSendButtonClick() {
        progressBar.setVisibility(View.VISIBLE);

        SMSBuilder builder = new SMSBuilder();
        SMSData smsData = builder.
                setRecipient(phoneNumberEditText.getText().toString()).
                setMessage(messageEditText.getText().toString()).
                setTest(testModeCheckBox.isChecked()).
                setUcs2(unicodeModeCheckBox.isChecked()).
                setLongMessage(longModeCheckBox.isChecked()).
                setTpoa("LabsMobile").build();

        smsService.sendSMS(smsData, new DefaultServiceCallback<SMSResponse>(SMSActivity.this, progressBar) {
            @Override
            public void onResponseOK(SMSResponse smsResponse) {
                progressBar.setVisibility(View.GONE);
                phoneNumberEditText.getText().clear();
                messageEditText.getText().clear();
                Toast.makeText(context, R.string.send_sms_success, Toast.LENGTH_LONG).show();
            }
        });
    }



    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, SMSActivity.class);
        return i;
    }



}
