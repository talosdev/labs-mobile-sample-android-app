package com.labsmobile.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.labsmobile.android.model.BalanceQueryResponse;
import com.labsmobile.android.model.PriceQueryResponse;
import com.labsmobile.android.service.QueryService;
import com.labsmobile.example.util.BaseActivity;
import com.labsmobile.example.util.DefaultServiceCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QueriesActivity extends BaseActivity {


    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.countries)
    EditText countriesBox;

    @Bind(R.id.list_prices)
    ListView list;


    private QueryService queryService;
    private ArrayAdapter<PriceQueryResponse.CountryPriceInfo> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        queryService = LabsMobileServiceProvider.provideQueryService();

        ButterKnife.bind(this);

        adapter = new PriceArrayAdapter(this);
        list.setAdapter(adapter);
    }


    @OnClick(R.id.query_balance_button)
    public void onBalanceButtonClick() {
        queryService.queryBalance(new DefaultServiceCallback<BalanceQueryResponse>(QueriesActivity.this, null) {
            @Override
            public void onResponseOK(BalanceQueryResponse balanceQueryResponse) {
                Toast.makeText(QueriesActivity.this,
                        getResources().getString(R.string.balance_response, balanceQueryResponse.balance),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.query_prices_button)
    public void onPricesButtonClick() {
        progressBar.setVisibility(View.VISIBLE);
        adapter.clear();

        queryService.queryPrices(countriesBox.getText().toString(), new DefaultServiceCallback<PriceQueryResponse>(QueriesActivity.this, progressBar) {
            @Override
            public void onResponseOK(PriceQueryResponse priceQueryResponse) {
                progressBar.setVisibility(View.GONE);

                List<PriceQueryResponse.CountryPriceInfo> countryPrices =
                        priceQueryResponse.getCountryPrices();

                if (countryPrices != null) {
                    for (PriceQueryResponse.CountryPriceInfo info: countryPrices) {
                        adapter.add(info);
                    }
                }
            }
        });

    }

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, QueriesActivity.class);
        return i;
    }

    private class PriceArrayAdapter extends ArrayAdapter<PriceQueryResponse.CountryPriceInfo>{
        private Context context;

        public PriceArrayAdapter(Context context) {
            super(context, -1, new ArrayList<PriceQueryResponse.CountryPriceInfo>());
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);

            View rowView = inflater.inflate(R.layout.item_price, parent, false);

            TextView country = (TextView) rowView.findViewById(R.id.list_item_country);
            TextView prefix = (TextView) rowView.findViewById(R.id.list_item_prefix);
            TextView price = (TextView) rowView.findViewById(R.id.list_item_price);


            country.setText(getItem(position).getName());
            prefix.setText(getItem(position).getPrefix());
            price.setText(getItem(position).getPrice() + "");

            return rowView;

        }
    }
}
