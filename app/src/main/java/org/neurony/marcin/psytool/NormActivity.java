package org.neurony.marcin.psytool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

public class NormActivity extends AppCompatActivity {

    float touchX = 0, touchY = 0, zScore;
    int frWidth, frHeight;
    String str = "";
    NormView nView;
    NormImageView iView;
    NormViewScores sView;
    EditText editText;
    TextView textWanlass;
    TextView textLezak;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_norm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nView = (NormView) findViewById(R.id.view);
        iView = (NormImageView) findViewById(R.id.imageView);
        sView = (NormViewScores) findViewById(R.id.scoresView);

        editText = (EditText) findViewById(R.id.editText);
        textWanlass = (TextView) findViewById(R.id.textWanlass);
        textLezak = (TextView) findViewById(R.id.textLezak);

        frWidth = nView.getWidth(); //screen width


                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_norm, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.info:
                showInfo();
                return true;
            case R.id.exit:
                showExit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //menu info
    public void showInfo(){
        Intent info = new Intent(this, Main2Activity.class);
        info.putExtra("tryb1", 2);
        startActivity(info);
    }
    //menu exit
    public void showExit(){
        finish();

    }

    //touch the screen
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_MOVE) {


            touchX = event.getX();
            touchY = event.getY();
            change(touchX);


            return true;
        } else {
            return false;
        }
    }

    //change of standard scores
    public void change(float x){
        StandardStat standscore = new StandardStat();
        frWidth = nView.getWidth(); //screen width
        frHeight = nView.getHeight(); //screen height

        zScore = roundF(obliczZ(x), 3);

        nView.touchX = x;
        sView.touchX = x;
        nView.curveHeight = iView.getHeight();
        sView.actScore[0] = str + roundF(zScore, 2);
        editText.setText(str + zScore);
        textLezak.setText(lezakInterp(zScore));
        textWanlass.setText(wanlasInterp(zScore));
        sView.actScore[1] = str + obliczCentyl(zScore);

        for (int i = 0; i<5; i++){
            try {
                sView.actScore[i + 2] = str + calcSS(zScore, standscore.ssParam[i][0], standscore.ssParam[i][1]);
            } catch (ArrayIndexOutOfBoundsException e) {}
        }

        nView.invalidate();
        sView.invalidate();
    }

    //calculating z score
    public float obliczZ(Float pozycja){

        return (pozycja - frWidth/2)/(frWidth/8);
    }

    //rounding float variable
    public static float roundF(float d, int decimalPlace) {
        return BigDecimal.valueOf(d).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    //calculate percentile
    public int obliczCentyl(float z){
        int centyl = 99;
        StandardStat standscore = new StandardStat();
        try{
            for (int i = 0; i < 98; i++){
                if (i < 50){
                    if (z <= (float)standscore.tabZ[i]){
                        centyl = i + 1;
                        break;
                    }
                } else if (i > 49){
                    if (z <= (float)standscore.tabZ[i+1]){
                        centyl = i + 1;
                        break;
                    }
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
        }
        return centyl;
    }

    //calculate SS
    public int calcSS(float z, double M, double SD){
        float resultS;

        resultS = (z * (float)SD) + (float)M;

        if (resultS < 1) resultS = 1;
        if (M < 5.5) {
            if (resultS > 9) resultS = 9;
        } else
        if (M < 10) {
            if (resultS > 10) resultS = 10;
        } else
        if (M < 11) {
            if (resultS > 20) resultS = 20;
        }

            return Math.round(resultS);

    }

    //interpretation of z score according to Lezak
    public String lezakInterp(float z){
        String str = "";
        StandardStat standscore = new StandardStat();
        try {
            for (int i = 0; i < 7; i++){
                if (z <= standscore.x_lezak[i]){
                    str = sView.lez[i];
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return str;
    }
    //interpretation of z score according to Wanlass
    public String wanlasInterp(float z){
        String str = "";
        StandardStat standscore = new StandardStat();
        try {
            for (int i = 0; i < 9; i++){
                if (z <= standscore.x_wanlas[i]){
                    str = sView.wan[i];
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return str;
    }

    //click +
    public void klik_plus(View v){
        if (touchX < sView.frWidth) {
            touchX++;
            change(touchX);
        }

    }

    //click -
    public void klik_minus(View v){
        if (touchX > 0) {
            touchX--;
            change(touchX);
        }
    }

    //click apply
    public void klik_apply(View v){
        try {
            float fl = Float.parseFloat(editText.getText().toString());
            if (fl < -4) {
                fl = -4;
            } else if (fl > 4) {
                fl = 4;
            }

            touchX = Math.round((fl * sView.gapSd) + frWidth / 2);

            change(touchX);
        }   catch (NumberFormatException e){editText.setText("0.0");}
    }
}
