/*
 * Copyright (c) 2016, LabsMobile. All rights reserved.
 */

package com.labsmobile.example.util;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.labsmobile.android.error.GenericError;
import com.labsmobile.android.service.ServiceCallback;
import com.labsmobile.example.R;

/**
 * Abstract {@link ServiceCallback} implementation that handles the error cases
 * (by showing a Toast and hiding the progressBar) and leaves only the success case to be
 * handler by subclasses.
 *
 * @author talosdev for LabsMobile
 * @version 1.0
 */
public abstract class DefaultServiceCallback<T> implements ServiceCallback<T> {

    protected final Context context;
    private final ProgressBar progressBar;

    public DefaultServiceCallback(Context context, ProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;
    }

    @Override
    public void onResponseNOK(GenericError genericError) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        Toast.makeText(context,
                context.getResources().getString(R.string.responseNOK, genericError.getStatusCode(), genericError.getMessage()),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(Throwable throwable) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        Toast.makeText(context,
                context.getResources().getString(R.string.responseError, throwable.getMessage()),
                Toast.LENGTH_LONG).show();
    }
}
