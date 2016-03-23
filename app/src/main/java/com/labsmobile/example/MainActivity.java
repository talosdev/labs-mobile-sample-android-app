package com.labsmobile.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.sms_services_button)
    Button smsButton;

    @Bind(R.id.query_services_button)
    Button queriesButton;

    @Bind(R.id.otp_services_button)
    Button otpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SMSActivity.newIntent(MainActivity.this));
            }
        });

        queriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(QueriesActivity.newIntent(MainActivity.this));
            }
        });

        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }


    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        return i;
    }
}
