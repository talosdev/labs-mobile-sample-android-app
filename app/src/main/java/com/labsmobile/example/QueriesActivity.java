package com.labsmobile.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.labsmobile.android.model.BalanceQueryResponse;
import com.labsmobile.android.service.QueryService;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QueriesActivity extends BaseActivityWithMenu {

    @Bind(R.id.query_balance_button)
    Button queryBalanceButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);


        final QueryService queryService = LabsMobileServiceProvider.provideQueryService();

        queryBalanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                queryService.queryBalance(new DefaultServiceCallback<BalanceQueryResponse>(QueriesActivity.this, QueriesActivity.this) {
                    @Override
                    public void onResponseOK(BalanceQueryResponse balanceQueryResponse) {
                        Toast.makeText(QueriesActivity.this,
                                getResources().getString(R.string.balance_response, balanceQueryResponse.balance),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, QueriesActivity.class);
        return i;
    }

}
