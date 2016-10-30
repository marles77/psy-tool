package org.neurony.marcin.psytool;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Marcin on 2016-04-22.
 */
public class NormView extends View {



    public NormView(Context context) {
        super(context);
    }

    public NormView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NormView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    int frWidth, frHeight, curveHeight;
    float touchX = 0; //touchY = 0;



    /*
    //calculating y on normal curve
    public double getY(double x1) {

        return Math.pow(Math.exp(-(((x1 - mean) * (x1 - mean)) / ((2 * variance)))), 1 / (stdDev * Math.sqrt(2 * Math.PI)));
    }
   */





    @Override
    protected void onDraw(Canvas canvas) {
        frWidth = getWidth();
        frHeight = getHeight();

        curveHeight = frHeight / 3;


        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(getResources().getColor(R.color.colorStoper1));
        p.setStrokeWidth(3);
        p.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawRect(0, 0, touchX, frHeight, p);




        super.onDraw(canvas);
    }
}
