package com.labsmobile.example;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

/**
 * TextWatcher that enables a button if all of the provided EditTexts are filled in, and disables it
 * if at least one of them is empty.
 *
 * Created by apapad on 24/03/16.
 */
class ButtonTogglingTextWatcher implements TextWatcher {

    EditText[] editTexts;
    Button button;

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
