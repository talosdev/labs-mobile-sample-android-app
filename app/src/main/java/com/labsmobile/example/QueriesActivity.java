package com.labsmobile.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.labsmobile.android.model.BalanceQueryResponse;
import com.labsmobile.android.model.PriceQueryResponse;
import com.labsmobile.android.service.QueryService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QueriesActivity extends BaseActivityWithMenu {

    @Bind(R.id.query_balance_button)
    Button queryBalanceButton;


    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.countries)
    EditText countriesBox;

    private QueryService queryService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);


        queryService = LabsMobileServiceProvider.provideQueryService();


        queryBalanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                queryService.queryBalance(new DefaultServiceCallback<BalanceQueryResponse>(QueriesActivity.this) {
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

    @OnClick(R.id.query_prices_button)
    public void onPricesButtonClick() {
        progressBar.setVisibility(View.VISIBLE);

        queryService.queryPrices(countriesBox.getText().toString(), new DefaultServiceCallback<PriceQueryResponse>(QueriesActivity.this) {
            @Override
            public void onResponseOK(PriceQueryResponse priceQueryResponse) {
                progressBar.setVisibility(View.GONE);
                List<PriceQueryResponse.CountryPriceInfo> countryPrices =
                        priceQueryResponse.getCountryPrices();

                Toast.makeText(QueriesActivity.this,
                        countryPrices.get(0).getName() + " - " + countryPrices.get(0).getPrice(),
                        Toast.LENGTH_LONG).show();

            }
        });

    }

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, QueriesActivity.class);
        return i;
    }

}
