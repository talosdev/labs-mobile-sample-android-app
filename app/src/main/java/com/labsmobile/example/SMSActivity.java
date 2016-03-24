package com.labsmobile.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.labsmobile.android.model.SMSBuilder;
import com.labsmobile.android.model.SMSData;
import com.labsmobile.android.model.SMSResponse;
import com.labsmobile.android.service.SMSService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SMSActivity extends BaseActivityWithMenu {

    @Bind(R.id.phone_number)
    EditText phoneNumberEditText;

    @Bind(R.id.message_text)
    EditText messageEditText;

    @Bind(R.id.send_button)
    Button sendButton;


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

        SMSBuilder builder = new SMSBuilder();
        SMSData smsData = builder.
                setRecipient(phoneNumberEditText.getText().toString()).
                setMessage(messageEditText.getText().toString()).
                setTest(true).
                setTpoa("LabsMobile Sample App").createSMSData();

        smsService.sendSMS(smsData, new DefaultServiceCallback<SMSResponse>(SMSActivity.this) {
            @Override
            public void onResponseOK(SMSResponse smsResponse) {
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
