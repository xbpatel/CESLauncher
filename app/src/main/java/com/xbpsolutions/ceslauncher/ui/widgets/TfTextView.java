package com.xbpsolutions.ceslauncher.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;
import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.helper.Functions;


/**
 * Created by dhruvil on 27-07-2016.
 */

public class TfTextView extends android.support.v7.widget.AppCompatTextView {

    private Context _ctx;
    private boolean isBold;
    private int fTypeValue = 0;

    public TfTextView(Context context) {
        super(context);
        if (!isInEditMode()) {
            this._ctx = context;
            init();
        }
    }

    public TfTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TfTextView, 0, 0);
            try {
                isBold = a.getBoolean(R.styleable.TfTextView_isBold, false);

                if (a.hasValue(R.styleable.TfTextView_ftype)) {
                    fTypeValue = a.getInt(R.styleable.TfTextView_ftype, 0);
                }

            } finally {
                a.recycle();
            }

            this._ctx = context;
            init();
        }
    }

    private void init() {
        try {
            setTypeface(Functions.getFontType(getContext(), fTypeValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
