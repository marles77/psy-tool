package org.neurony.marcin.psytool;

import android.graphics.Canvas;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * Created by Marcin on 2016-05-03.
 */
public class NormImageView extends ImageView {



    public NormImageView(Context context) {
        super(context);
    }

    public NormImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NormImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
