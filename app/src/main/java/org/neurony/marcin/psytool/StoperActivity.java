package org.neurony.marcin.psytool;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class StoperActivity extends AppCompatActivity {

    Button butnstart, butnreset;
    TextView time;
    long starttime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedtime = 0L;
    int t = 1;
    int secs = 0;
    int mins = 0;
    int milliseconds = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stoper);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        butnstart = (StoperButton) findViewById(R.id.start);
        butnreset = (Button) findViewById(R.id.reset);
        time = (TextView) findViewById(R.id.timer);

        butnstart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub

                if (t == 1) {
                    butnstart.setText(R.string.stop);
                    ((StoperButton) findViewById(R.id.start)).setStandby(false);
                    starttime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimer, 0);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                    t = 0;
                } else {
                    butnstart.setText(R.string.start);
                    ((StoperButton) findViewById(R.id.start)).setStandby(true);
                    time.setTextColor(Color.WHITE);
                    timeSwapBuff += timeInMilliseconds;
                    handler.removeCallbacks(updateTimer);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    t = 1;
                }
            }
        });
        butnreset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                starttime = 0L;
                timeInMilliseconds = 0L;
                timeSwapBuff = 0L;
                updatedtime = 0L;
                t = 1;
                secs = 0;
                mins = 0;
                milliseconds = 0;
                butnstart.setText(R.string.start);
                ((StoperButton) findViewById(R.id.start)).setStandby(true);
                handler.removeCallbacks(updateTimer);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                time.setText("00:00:00");
            }});
    }

    public Runnable updateTimer = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - starttime;
            updatedtime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedtime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) ((updatedtime % 1000)/10);
            time.setText(String.format("%02d", mins) + ":" + String.format("%02d", secs) + ":"
                    + String.format("%02d", milliseconds));
            time.setTextColor(Color.WHITE);
            handler.postDelayed(this, 0);
        }};
}

/*
http://stackoverflow.com/questions/4336060/android-how-to-add-a-custom-button-state
https://forums.xamarin.com/discussion/6019/custom-attributes-for-multi-state-button
 */