package org.neurony.marcin.psytool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Marcin on 2016-05-08.
 */
public class NormViewScores extends View {

    public NormViewScores(Context context) {
        super(context);
    }

    public NormViewScores(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NormViewScores(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    StandardStat stStat = new StandardStat();
    int frWidth, frHeight, gapHeight, gapSd, gapSt, midWidth, k, num, font_size;
    Float touchX = (float)100, ssX = (float)100;
    String strSt;

    String actScore[]  = {"", "", "", "", "", "", ""};
    String lez[] = getResources().getStringArray(R.array.interpr_lez);
    String wan[] = getResources().getStringArray(R.array.interpr_wan);
    String name[] = getResources().getStringArray(R.array.name_s);

        @Override

    protected void onDraw(Canvas canvas) {
        frWidth = getWidth(); //screen width
        midWidth = frWidth / 2; //the middle of chart
        frHeight = getHeight();
        gapHeight = frHeight / 7;//8;
        gapSd = frWidth / 8;
        gapSt = gapSd / 2;
        strSt = "1";
        font_size = frWidth/40;
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setTextSize(font_size);

        //draw lines and labels for standard scales
        for (int i = 0; i < 7; i++) {
            p.setColor(Color.WHITE);
            p.setStrokeWidth(3);
            canvas.drawLine(0, i * gapHeight, frWidth, i * gapHeight, p);

            p.setColor(getResources().getColor(R.color.colorAccent));
            p.setStrokeWidth(1);
            try {
            canvas.drawText(name[i], 6, i * gapHeight + (font_size*2), p);
            } catch (ArrayIndexOutOfBoundsException e) {}
        }


        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(Color.WHITE);
        p.setStrokeWidth(1);
        num = -1;

        //draw standard scales beginning with middle points
        for (int j = 0; j < 7; j++) {
            if (j != 4) {
                canvas.drawLine(midWidth, j * gapHeight, midWidth, j * gapHeight + 5, p);
                if (j != 3) {
                    num++;
                    try {
                        canvas.drawText(stStat.stLabel[3][num], midWidth - ((font_size/4) * stStat.stLabel[3][num].length()), j * gapHeight + (font_size*2), p); ///******
                    } catch (ArrayIndexOutOfBoundsException e) {}
                }
            } else {
                canvas.drawText("5", midWidth - 3, j * gapHeight + (font_size*2), p);
            }
            //num = -1;
            for (int i = 1; i < 5; i++) {

                if (j < 3 || j > 4) {

                    canvas.drawLine(midWidth - (i * gapSd), j * gapHeight, midWidth - (i * gapSd), j * gapHeight + 5, p);
                    try {
                        canvas.drawText(stStat.stLabel[3-i][num], midWidth - (i * gapSd) - ((font_size/4) * stStat.stLabel[3-i][num].length()), j * gapHeight + (font_size*2), p);
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    canvas.drawLine(midWidth + (i * gapSd), j * gapHeight, midWidth + (i * gapSd), j * gapHeight + 5, p);
                    try {
                        canvas.drawText(stStat.stLabel[3+i][num], midWidth + (i * gapSd) - ((font_size/4) * stStat.stLabel[3+i][num].length()), j * gapHeight + (font_size*2), p);
                    } catch (ArrayIndexOutOfBoundsException e) {}
                } else
                if (j == 3) {
                    //sten scale

                    canvas.drawLine(midWidth - (i * gapSt), j * gapHeight, midWidth - (i * gapSt), j * gapHeight + 5, p);
                    strSt = "";
                    canvas.drawText(strSt + (6-i), midWidth - (i * gapSt) + (gapSt/2), j * gapHeight + (font_size*2), p);

                    canvas.drawLine(midWidth + (i * gapSt), j * gapHeight, midWidth + (i * gapSt), j * gapHeight + 5, p);
                    strSt = "";
                    canvas.drawText(strSt + (5+i), midWidth + (i * gapSt) - (gapSt/2), j * gapHeight + (font_size*2), p);
                    if (i == 4) {
                        canvas.drawText("1", (midWidth - (i * gapSt))/2, j * gapHeight + (font_size*2), p);
                        canvas.drawText("10", frWidth - (midWidth - (i * gapSt))/2, j * gapHeight + (font_size*2), p);
                    }
                } else
                if (j == 4) {
                    //stanine scale
                    k = i - 1;
                    canvas.drawLine(midWidth - (k * gapSt) - (gapSt / 2), j * gapHeight, midWidth - (k * gapSt) - (gapSt / 2), j * gapHeight + 5, p);
                    strSt = "";
                    if (i<4) {canvas.drawText(strSt + (5 - i), midWidth - (k * gapSt) - (gapSt), j * gapHeight + (font_size*2), p);}
                    else {canvas.drawText(strSt + (5 - i), (midWidth - (k * gapSt) - (gapSt / 2))/2, j * gapHeight + (font_size*2), p);}
                    canvas.drawLine(midWidth + (k * gapSt) + (gapSt / 2), j * gapHeight, midWidth + (k * gapSt) + (gapSt / 2), j * gapHeight + 5, p);
                    strSt = "";
                    if (i<4) {canvas.drawText(strSt + (5 + i), midWidth + (k * gapSt) + (gapSt), j * gapHeight + (font_size*2), p);}
                    else {canvas.drawText(strSt + (5 + i), frWidth - (midWidth - (k * gapSt) - (gapSt / 2))/2, j * gapHeight + (font_size*2), p);}


                }
            }
        }
            //draw current scores
            p.setColor(getResources().getColor(R.color.colorContrast));

            for (int i = 0; i<7; i++) {
                ssX = touchX - (actScore[i].length()*(font_size/4));
                if (ssX < 0) {ssX = (float)0;} else
                if ((ssX + actScore[i].length()*(font_size/4)) > frWidth) {ssX = (float)(frWidth - 9);}
            try {
                canvas.drawText(actScore[i], ssX, (i * gapHeight) + (font_size*3), p);
            } catch (ArrayIndexOutOfBoundsException e) {}
        }

            super.onDraw(canvas);
    }
}
