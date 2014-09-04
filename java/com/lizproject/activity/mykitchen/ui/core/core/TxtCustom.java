package com.lizproject.activity.mykitchen.ui.core.core;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Karla on 15/08/2014.
 */
public class TxtCustom extends TextView {

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public TxtCustom(Context context) {
        super(context);
    }

    public TxtCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TxtCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
