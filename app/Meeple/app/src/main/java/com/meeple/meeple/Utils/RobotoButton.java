package com.meeple.meeple.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Vuilla_l on 23/02/2015.
 */
public class RobotoButton extends Button {

    public RobotoButton(Context context) {
        super(context);
        setFont();
    }

    public RobotoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public RobotoButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/RobotoSlab-Regular.ttf");
        setTypeface(font);
        setTextColor(0xffdbdbdb);
    }
}
