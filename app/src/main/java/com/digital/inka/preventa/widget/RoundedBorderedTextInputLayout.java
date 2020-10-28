package com.digital.inka.preventa.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.digital.inka.R;
import com.google.android.material.textfield.TextInputLayout;


public class RoundedBorderedTextInputLayout extends TextInputLayout {
    private Context context;

    public RoundedBorderedTextInputLayout(Context context) {
        super(context);
        this.context = context;
    }

    public RoundedBorderedTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public RoundedBorderedTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        EditText editText = getEditText();
        if(editText != null) {
            editText.setBackground(ContextCompat.getDrawable(this.context, R.drawable.bg_ractangle_rounded));
        }
    }

    @Override
    public void setError(@Nullable final CharSequence error) {
        super.setError(error);

        EditText editText = getEditText();
        if(editText != null) {
            editText.setBackground(ContextCompat.getDrawable(this.context, R.drawable.bg_ractangle_rounded));
        }
    }
}
