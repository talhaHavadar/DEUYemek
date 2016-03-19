package com.deu.talha.deuyemek.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.deu.talha.deuyemek.app.App;

/**
 * Created by talha on 10.02.2016.
 */
public class CanaroTextView extends TextView {


    public CanaroTextView(Context context) {
        super(context);
        setTypeface(App.CANARO_FONT);
    }

    public CanaroTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(App.CANARO_FONT);
    }

    public CanaroTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(App.CANARO_FONT);
    }

}
