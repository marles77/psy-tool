package org.neurony.marcin.psytool;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
//import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.os.CountDownTimer;
//import android.text.TextWatcher;
import android.media.MediaPlayer;


public class TimerActivity extends AppCompatActivity {

    Button butnstart, butnreset;
    ImageButton butnalarm;
    TextView time;
    TextView textVol;
    PsyCountDownTimer cdtimer;
    EditText edit;
    SeekBar seekBar;
    TextCutter textCutter;
    Boolean isAlarm = true;
    MediaPlayer mp = null;
    private boolean timerHasStarted = false;
    private long startTime = 0;
    private final long interval = 1000;

    int hrs, hrsSet = 0;
    int mins, minsSet = 0;
    int secs, secsSet = 0;
    int timeSet = 0;
    int editLength = 8;
    int curPosition = 8;
    String editStr = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        butnstart = (StoperButton) findViewById(R.id.start1);
        butnreset = (Button) findViewById(R.id.reset1);
        butnalarm = (ImageButton) findViewById(R.id.alarm);
        time = (TextView) findViewById(R.id.timer);
        textVol = (TextView) findViewById(R.id.textVolume);
        edit = (EditText) findViewById(R.id.edit);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

                //end of timer edition
                edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        String input;
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            input = v.getText().toString();

                            editStr = input;
                            hideKeyboard(edit);
                            edit.setVisibility(View.INVISIBLE);
                            time.setVisibility(View.VISIBLE);
                            time.setText(editStr);

                            textCutter = new TextCutter();
                            textCutter.cutStr(editStr);
                            hrsSet = textCutter.hrsI;
                            minsSet = textCutter.minsI;
                            secsSet = textCutter.secsI;

                            return true; // consume.
                        }
                        return false; // pass on to other listeners.
                    }
                });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        time.setText(R.string.start_time);
        time.setTextColor(Color.WHITE);

        //volume
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        /*am.setStreamVolume(AudioManager.STREAM_MUSIC,
                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);*/

        seekBar.setMax(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        seekBar.setProgress(seekBar.getMax());

    } //end of onCreate

    private void hideKeyboard(EditText edit)
    {
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }

    private void showKeyboard(EditText edit){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
    }

    //buttons onClick
    public void klik_alarm(View v) {

        if (isAlarm== true){
            butnalarm.setImageResource(R.drawable.glosnik2);
            isAlarm= false;
        } else {
            butnalarm.setImageResource(R.drawable.glosnik1);
            isAlarm= true;
        }

    }
    //click START
    public void klik_start(View v) {


        if (timerHasStarted == false) {
            timeSet = (hrsSet * 3600) + (minsSet * 60) + secsSet;
            if (timeSet>0) {
                startTime = timeSet * 1000;

                cdtimer = new PsyCountDownTimer(startTime, interval);
                cdtimer.start();
                timerHasStarted = true;
                butnstart.setText(R.string.stop);
                ((StoperButton) findViewById(R.id.start1)).setStandby(false);
                //textVol.setText("" + seekBar.getProgress());
            }
        } else {
            cdtimer.cancel();
            timerHasStarted = false;
            hrsSet = hrs;
            minsSet = mins;
            secsSet = secs;
            butnstart.setText(R.string.start);
            ((StoperButton) findViewById(R.id.start1)).setStandby(true);
        }

    }
    //click hours
    public void klik_h1(View v) {
        //hours + 1
        if (timerHasStarted == false) {
            if (hrsSet < 99) {
                hrsSet++;

            } else {
                hrsSet = 0;
            }
            time.setText("" + String.format("%02d", hrsSet) + ":" + String.format("%02d", minsSet) + ":"
                    + String.format("%02d", secsSet));
            time.setTextColor(Color.WHITE);
        }
    }
    public void klik_h2(View v) {
        //hours - 1
        if (timerHasStarted == false) {
            if (hrsSet > 0) {
                hrsSet--;
                time.setText("" + String.format("%02d", hrsSet) + ":" + String.format("%02d", minsSet) + ":"
                        + String.format("%02d", secsSet));
                time.setTextColor(Color.WHITE);
            }
    }
    }
    //click minutes
    public void klik_m1(View v) {
        //mins + 1
        if (timerHasStarted == false) {
            if (minsSet < 59) {
                minsSet++;

            } else {
                minsSet = 0;
            }
            time.setText("" + String.format("%02d", hrsSet) + ":" + String.format("%02d", minsSet) + ":"
                    + String.format("%02d", secsSet));
            time.setTextColor(Color.WHITE);
        }
    }
    public void klik_m2(View v) {
        //mins - 1
        if (timerHasStarted == false) {
            if (minsSet > 0) {
                minsSet--;

            } else {
                minsSet = 59;
            }
            time.setText("" + String.format("%02d", hrsSet) + ":" + String.format("%02d", minsSet) + ":"
                    + String.format("%02d", secsSet));
            time.setTextColor(Color.WHITE);
        }
    }
    //click seconds
    public void klik_s1(View v) {
        //secs + 1
        if (timerHasStarted == false) {
            if (secsSet < 59) {
                secsSet++;

            } else secsSet = 0;
            time.setText("" + String.format("%02d", hrsSet) + ":" + String.format("%02d", minsSet) + ":"
                    + String.format("%02d", secsSet));
            time.setTextColor(Color.WHITE);
        }
    }
    public void klik_s2(View v) {
        //secs - 1
        if (timerHasStarted == false) {
            if (secsSet > 0) {
                secsSet--;

            } else {
                secsSet = 59;
            }
            time.setText("" + String.format("%02d", hrsSet) + ":" + String.format("%02d", minsSet) + ":"
                    + String.format("%02d", secsSet));
            time.setTextColor(Color.WHITE);
        }
    }
    //click reset
    public void klik_reset(View v) {
        if (timerHasStarted == true) {
            cdtimer.cancel();
            timerHasStarted = false;
        }
        hrsSet = 0;
        minsSet = 0;
        secsSet = 0;
        butnstart.setText(R.string.start);
        ((StoperButton) findViewById(R.id.start1)).setStandby(true);
        time.setText(R.string.start_time);
        time.setTextColor(Color.WHITE);
    }
    //
    public void klik_time(View v) {
        if (timerHasStarted == false) {
            time.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            edit.setTextColor(Color.GREEN);

            edit.setText(time.getText());
            editLength = edit.getText().length();
            edit.setSelection(3, 5);
            showKeyboard(edit);
        }
    }
    //
    public void klik_edit(View v){
        //if all digits are present in the display
        if (edit.getText().length() == 8) {
            curPosition = edit.getSelectionStart();
            if (curPosition < 3) {
                edit.setSelection(0, 2);

            } else if (curPosition < 6) {
                edit.setSelection(3, 5);

            } else if (curPosition < 9) {
                edit.setSelection(editLength - 2, editLength);
            }
            edit.setCursorVisible(false);
        } else {  //if different number of digits in the display
            editStr = "" + edit.getText();
            textCutter = new TextCutter();
            textCutter.cutStr(editStr);
            hrsSet = textCutter.hrsI;
            minsSet = textCutter.minsI;
            secsSet = textCutter.secsI;
            edit.setText("" + String.format("%02d", hrsSet) + ":" + String.format("%02d", minsSet) + ":"
                    + String.format("%02d", secsSet));
        }
    }

    //internal class CountDownTimer
    public class PsyCountDownTimer extends CountDownTimer {
        public PsyCountDownTimer(long startTime, long interval) {
             super(startTime, interval);
        }

        @Override
        public void onFinish() {

            time.setTextColor(Color.RED);
            time.setText(R.string.start_time);
            timerHasStarted = false;
            butnstart.setText(R.string.start);
            hrsSet = 0;
            minsSet = 0;
            secsSet = 0;
            ((StoperButton) findViewById(R.id.start1)).setStandby(true);
            if (isAlarm == true) {

                managerOfSound();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (timerHasStarted == false) {
                cdtimer.cancel();
            } else {

                    secs = (int) millisUntilFinished / 1000;
                    mins = secs / 60;
                    hrs = mins / 60;
                    secs = secs % 60;
                    mins = mins % 60;
                    time.setText("" + String.format("%02d", hrs) + ":" + String.format("%02d", mins) + ":"
                            + String.format("%02d", secs));

            }
        }
    } //end of ContDownTimer

    //alarm sound
    protected void managerOfSound() {
        if (mp != null) {
            mp.reset();
            mp.release();
        }

        AudioManager am1 = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am1.setStreamVolume(AudioManager.STREAM_MUSIC, seekBar.getProgress(), 0);

            mp = MediaPlayer.create(this, R.raw.alarm);
            //mp.setVolume(2, 2);
            mp.start();
    }

    //soft keyboard delete button click
    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {

            editStr = "" + edit.getText();
            textCutter = new TextCutter();
            textCutter.cutStr(editStr);
            hrsSet = textCutter.hrsI;
            minsSet = textCutter.minsI;
            secsSet = textCutter.secsI;

            curPosition = edit.getSelectionStart();
            if (curPosition<3) {
                hrsSet = 0;

            } else if (curPosition<6){
                minsSet = 0;

            } else if (curPosition<9) {
                secsSet = 0;
            }

            edit.setText("" + String.format("%02d", hrsSet) + ":" + String.format("%02d", minsSet) + ":"
                    + String.format("%02d", secsSet));

        }
        return super.dispatchKeyEvent(event);
    }

} //end of TimerActivity


