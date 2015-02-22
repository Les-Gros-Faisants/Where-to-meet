package com.meeple.meeple.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Vuilla_l on 22/02/2015.
 */
public class RobotoTextView extends TextView {
    public RobotoTextView(Context context) {
        super(context);
        setFont();
    }

    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/RobotoSlab-Regular.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}