/*
 * Copyright (c) 2016, LabsMobile. All rights reserved.
 */

package com.labsmobile.example.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

/**
 * TextWatcher that enables a button if all of the provided EditTexts are filled in, and disables it
 * if at least one of them is empty.
 *
 * @author talosdev for LabsMobile
 * @version 1.0
*/
public class ButtonTogglingTextWatcher implements TextWatcher {

    private final EditText[] editTexts;
    private final Button button;

    public ButtonTogglingTextWatcher(Button button, EditText... editTexts) {
        this.editTexts = editTexts;
        this.button = button;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean allNonEmpty = true;
        for (EditText editText : editTexts) {
            if (editText.getText().length() == 0) {
                allNonEmpty = false;
                break;
            }
        }
        button.setEnabled(allNonEmpty);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
