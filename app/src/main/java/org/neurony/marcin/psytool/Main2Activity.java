package org.neurony.marcin.psytool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.content.Intent;

public class Main2Activity extends AppCompatActivity {


    TextView textMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //calling variable tryb from other activities
        Intent callingIntent = getIntent();
        int tryb = callingIntent.getIntExtra("tryb1", 0);

        textMessage = (TextView) findViewById(R.id.textView);

        switch (tryb) {
            case 0:
                textMessage.setText(R.string.info_text);
                break;
            case 1:
                textMessage.setText(R.string.info_text1);
                break;
            case 2:
                textMessage.setText(R.string.info_text2);
                break;
            default:
                textMessage.setText(R.string.info_text);

        }
    }

}

//http://stackoverflow.com/questions/30524122/how-do-i-use-variable-of-one-activity-in-another-activity-in-android-studio