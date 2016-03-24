package com.labsmobile.example;

import android.content.Context;
import android.widget.Toast;

import com.labsmobile.android.error.GenericError;
import com.labsmobile.android.service.ServiceCallback;

/**
 * Created by apapad on 24/03/16.
 */
abstract class DefaultServiceCallback<T> implements ServiceCallback<T> {

    private QueriesActivity queriesActivity;
    private Context context;

    public DefaultServiceCallback(QueriesActivity queriesActivity, Context context) {
        this.queriesActivity = queriesActivity;
        this.context = context;
    }

    @Override
    public void onResponseNOK(GenericError genericError) {
        Toast.makeText(context,
                queriesActivity.getResources().getString(R.string.responseNOK, genericError.getStatusCode(), genericError.getMessage()),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(Throwable throwable) {
        Toast.makeText(context,
                queriesActivity.getResources().getString(R.string.responseError, throwable.getMessage()),
                Toast.LENGTH_LONG).show();
    }
}
