package com.wateryan.acropolis.seneca.core.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * Created on 8/15/2015.
 */
public abstract class AbstractValidator implements TextWatcher {
    private final TextView textView;

    public AbstractValidator(TextView textView) {
        this.textView = textView;
    }

    public abstract void validate(TextView textView, String text);

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Ignore
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Ignore
    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = this.textView.getText().toString();
        validate(textView, text);
    }
}
