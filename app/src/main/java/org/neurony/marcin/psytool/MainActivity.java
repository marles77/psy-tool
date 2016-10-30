package org.neurony.marcin.psytool;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        ImageView mImageViewLogo = (ImageView)
                findViewById(R.id.imageview_animation_list_logo);
        ((AnimationDrawable) mImageViewLogo.getBackground()).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
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

    //stopwatch
    public void klik_1(View v){

        Intent stoper = new Intent(this, StoperActivity.class);
        startActivity( stoper);
    }
    //timer
    public void klik_2(View v){

        Intent timer = new Intent(this, TimerActivity.class);
        startActivity( timer);

    }
    //calculator
    public void klik_3(View v){
        Intent calc = new Intent(this, CalculatorActivity.class);
        startActivity( calc);

    }
    //normal curve
    public void klik_4(View v){
        Intent norm = new Intent(this, NormActivity.class);
        startActivity( norm);

    }
    //menu info
    public void showInfo(){
      Intent info = new Intent(this, Main2Activity.class);
        info.putExtra("tryb1", 0);
        startActivity(info);
    }
    //menu exit
    public void showExit(){
        finish();
        System.exit(0);
    }
}
