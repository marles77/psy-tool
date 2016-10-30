package org.neurony.marcin.psytool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
//import android.app.AlertDialog;
import android.widget.TextView;

import java.util.Arrays;

public class CalculatorActivity extends AppCompatActivity {

    EditText editDisplay;
    TextView textMessage;

    Character[] znak = {'/', '*', '+', '-', '(', ';'};
    int brackPair = 0; //number of brackets in the string
    boolean statOn = false; //statistic module activated
    int advFunc = 0; //advanced module activated (0 - no, 1 - ^/sqrt/, 2 - trigonometric)
    int statF = 0; //statistic function activated
    StatMath statMath;
    double result[];
    double resultEs = 0;
    double effect_size[][] = {
            {0.2, 0.5, 0.8},
            {0.1, 0.3, 0.5},
            {0.01, 0.06, 0.14}
    };


    Button but_del;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editDisplay = (EditText) findViewById(R.id.editDisplay);
        textMessage = (TextView) findViewById(R.id.textBelow);
        but_del = (Button) findViewById(R.id.But2_4);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        but_del.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                longKlik();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_calc, menu);
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
        info.putExtra("tryb1", 1);
        startActivity(info);
    }
    //menu exit
    public void showExit(){
        finish();

    }

    //click digit button
    public void klik_num(View v){
        Button b = (Button)v;
        String bText = "";
        char znakPrev, znakPrev2, znak1;
        int nDots = 0;


        if (editDisplay.length() > 0) {
            if (editDisplay.length() > 1){
                znakPrev2 = editDisplay.getText().charAt(editDisplay.length() - 2);
            } else znakPrev2 = '?';
            znakPrev = editDisplay.getText().charAt(editDisplay.length() - 1);
        } else {
            znakPrev = '?';
            znakPrev2 = '?';
        }

            //if '.' clicked
        if (v == b.findViewById(R.id.But5_2)) {
            if (editDisplay.length() == 0) {
                bText = "0.";
            } else {

                if (!Character.isDigit(znakPrev)) {
                    if (Arrays.asList(znak).contains(znakPrev)) {
                        bText = "0.";
                    } else if (znakPrev == ')') {
                        bText = "*0.";
                    } else bText = "";

                }
                else {
                    if (editDisplay.length() > 1){
                        nDots = 0;
                        //check if there is already decimal point
                        for(int i = 1; ; i++){
                            if (i > editDisplay.length()-1){
                                break;
                            } else {
                                znak1 = editDisplay.getText().charAt(editDisplay.length() - i);
                                if (Arrays.asList(znak).contains(znak1)) {
                                    nDots = 0;
                                    break;
                                } else if (znak1 == '.'){
                                    nDots++;
                                    break;
                                }
                            }
                        }
                        if (nDots > 0) {
                            bText = "";
                        } else bText = b.getText().toString();
                    } else bText = b.getText().toString();


                }
            }
            //if '0' clicked
        } else if (v == b.findViewById(R.id.But5_1)) {
            if (editDisplay.length() > 1) {
                if (znakPrev =='0' && Arrays.asList(znak).contains(znakPrev2)) {
                    bText = "";
                } else if (znakPrev ==')'){
                    bText = "*" + b.getText().toString();
                }
                else bText = b.getText().toString();
            } else if (editDisplay.length() > 0) {
                if (znakPrev =='0'){
                    bText = "";
                }
                else bText = b.getText().toString();
            }

              else bText = b.getText().toString();


        } else {
            if (editDisplay.length() > 1) {
                if (znakPrev =='0' && Arrays.asList(znak).contains(znakPrev2)) {
                    bText = "";
                } else if (znakPrev ==')'){
                    bText = "*" + b.getText().toString();
                }
                else bText = b.getText().toString();
            } else if (editDisplay.length() > 0) {

                    bText = b.getText().toString();
            } else
                bText = b.getText().toString();
        }

        editDisplay.getText().insert(editDisplay.getSelectionStart(), bText);
    }
    //click expression button (/ * - +)
    public void klik_exp(View v){
        Button b = (Button)v;
        int i = advFunc;
        if (statOn == false) {
            String bText = "";

            switch (i) {

                case 0:

                    if (editDisplay.length() == 0) {

                        if (v == b.findViewById(R.id.But1_3)) {
                            bText = b.getText().toString();
                        } else bText = "";
                    } else {
                        if (!Character.isDigit(editDisplay.getText().charAt(editDisplay.length() - 1))) {
                            if (editDisplay.getText().charAt(editDisplay.length() - 1) == '(' || editDisplay.getText().charAt(editDisplay.length() - 1) == ')') {
                                bText = b.getText().toString();
                            } else bText = "";

                        } else bText = b.getText().toString();
                    }
                    break;
                case 1:
                    if (v == b.findViewById(R.id.But1_3)) {
                        if (editDisplay.length() == 0) {
                            bText = b.getText().toString();
                        } else {
                            if (Arrays.asList(znak).contains(editDisplay.getText().charAt(editDisplay.length() - 1))){
                                bText = b.getText().toString();
                            } else {
                                bText = "*" + b.getText().toString();
                            }
                        }

                    } else {

                        if (editDisplay.length() == 0){
                            bText = "";
                        } else {
                            if (Character.isDigit(editDisplay.getText().charAt(editDisplay.length() - 1)) || editDisplay.getText().charAt(editDisplay.length() - 1) == ')') {
                                bText = b.getText().toString();
                            } else bText = "";
                        }
                    }
                    break;
                case 2:
                    if (editDisplay.length() == 0) {
                        bText = b.getText().toString();
                    } else {
                        if (Arrays.asList(znak).contains(editDisplay.getText().charAt(editDisplay.length() - 1))){
                            bText = b.getText().toString();
                        } else {
                            bText = "*" + b.getText().toString();
                        }
                    }
                    break;

            }
                editDisplay.getText().insert(editDisplay.getSelectionStart(), bText);
            //if STAT button is active
        } else {
            if (v == b.findViewById(R.id.But1_1)){
                textMessage.setText(R.string.text_below_message1);
                statF = 1;
            } else if (v == b.findViewById(R.id.But1_2)){
                textMessage.setText(R.string.text_below_message2);
                statF = 2;
            } else if (v == b.findViewById(R.id.But1_3)){
                textMessage.setText(R.string.text_below_message3);
                statF = 3;
            } else if (v == b.findViewById(R.id.But1_4)){
                textMessage.setText(R.string.text_below_message4);
                statF = 4;
            }
        }

    }


    //click delete button
    public void klik_del(View v){
        if (editDisplay.getSelectionEnd()>0) {
            editDisplay.getText().delete(editDisplay.getSelectionStart() - 1, editDisplay.getSelectionStart());
        }
    }

    //deleting all
    public void longKlik(){

        editDisplay.getText().clear();
        textMessage.setText(R.string.text_default);
    }

    //click adv (advanced functions) button
    public void klik_adv(View v){
        Button b = (Button)v;
        Button b1 = (Button) findViewById(R.id.But1_1);
        Button b2 = (Button) findViewById(R.id.But1_2);
        Button b3 = (Button) findViewById(R.id.But1_3);
        Button b4 = (Button) findViewById(R.id.But1_4);
        switch(advFunc){
            case 0:{
                advFunc = 1;
                b.setText("Adv1");
                b1.setText("^2");
                b2.setText("^");
                b3.setText("sqrt");
                b4.setText("");
                break;
            }
            case 1:{
                advFunc = 2;
                b.setText("Adv2");
                b1.setText("sin");
                b2.setText("cos");
                b3.setText("tan");
                b4.setText("");
                break;
            }
            case 2:{
                advFunc = 0;
                b.setText("Adv");
                b1.setText("/");
                b2.setText("*");
                b3.setText("-");
                b4.setText("+");
                break;
            }
        }

    }

    //click equals button
    public void klik_equals(View v){

        String str1 = "" + editDisplay.getText().toString();
        if (statF == 0) {
            brackPair = 0;
            for (int i = 0; i < editDisplay.length(); i++) {
                if (editDisplay.getText().charAt(i) == '(') {
                    brackPair++;
                } else if (editDisplay.getText().charAt(i) == ')') {
                    brackPair--;
                }
            }
            if (brackPair == 0) {
                double result = 0.0;
                Boolean error = false;
                try {
                    result = StringMath.eval(str1);
                    } catch (RuntimeException e){
                    error = true;
                    textMessage.setText(e.getMessage());
                    }
                    if (error == false) {
                        editDisplay.getText().clear();
                        result = Math.round(result * 1000000.0) / 1000000.0;
                        editDisplay.setText("" + result);
                        editDisplay.setSelection(editDisplay.length());
                    }

            } else textMessage.setText("')' is lacking");

        //if mean and SD
        } else if (statF == 1){
            //
            result = new double[2];
            result[0] = 999;
            result[1] = 999;
            try {
                    statMath = new StatMath();
                    result = statMath.mSd(str1);
                    if (result[0] != 999 && result[1] != 999) {
                        editDisplay.setText("M = " + String.format("%.2f", result[0]) + " SD = " + String.format("%.2f", result[1]));
                    } else textMessage.setText(R.string.text_below_error);
            }catch (NullPointerException e) {
                textMessage.setText(R.string.text_below_error);
            }
        //if cohen's d
        } else if (statF == 2){
            resultEs = 999;
            try {
                    statMath = new StatMath();
                    resultEs = statMath.dEf(str1);
                    if (resultEs != 999) {
                        editDisplay.setText("d = " + String.format("%.2f", resultEs));
                        textMessage.setText(es_check(0, resultEs));
                    } else textMessage.setText(R.string.text_below_error);
            } catch (NullPointerException e) {
                textMessage.setText(R.string.text_below_error);
            }

        //if r
        } else if (statF == 3){
            resultEs = 999;
            try {
                    statMath = new StatMath();
                    resultEs = statMath.rEf(str1);
                if (resultEs != 999) {
                    editDisplay.setText("r = " + String.format("%.2f", resultEs));
                    textMessage.setText(es_check(1, resultEs));
                } else textMessage.setText(R.string.text_below_error);
            } catch (NullPointerException e) {
                textMessage.setText(R.string.text_below_error);
            }

        //if eta2
        } else if (statF == 4){
            resultEs = 999;
            try {
                statMath = new StatMath();
                resultEs = statMath.etaEf(str1);
                if (resultEs != 999) {
                    editDisplay.setText("η2 = " + String.format("%.2f", resultEs));
                    textMessage.setText(es_check(2, resultEs));
                } else textMessage.setText(R.string.text_below_error);
            } catch (NullPointerException e) {
                textMessage.setText(R.string.text_below_error);
            }
        }
    }

    //click () button
    public void klik_brackets(View v){
       String str2 = "";
        if (statOn == false) {
            brackPair = 0;
            for (int i = 0; i < editDisplay.length(); i++) {
                if (editDisplay.getText().charAt(i) == '(') {
                    brackPair++;
                } else if (editDisplay.getText().charAt(i) == ')') {
                    brackPair--;
                }
            }

            if (editDisplay.length() == 0) {
                str2 = "(";

            } else {
                if (Character.isDigit(editDisplay.getText().charAt(editDisplay.length() - 1)) == true) {
                    if (brackPair > 0) {
                        str2 = ")";
                    } else {
                        str2 = "*(";
                    }

                } else {
                    if (editDisplay.getText().charAt(editDisplay.length() - 1) == ')') {
                        if (brackPair > 0){
                            str2 = ")";
                        } else str2 = "*(";
                    } else str2 = "(";

                }
            }
        } else str2 = ";";
       editDisplay.getText().insert(editDisplay.getSelectionStart(), str2);
    }

    //click stat button
    public void klik_stat(View v){
        Button b = (Button)v;
        Button b1 = (Button) findViewById(R.id.But1_1);
        Button b2 = (Button) findViewById(R.id.But1_2);
        Button b3 = (Button) findViewById(R.id.But1_3);
        Button b4 = (Button) findViewById(R.id.But1_4);
        Button b5 = (Button) findViewById(R.id.But5_3);

        if (statOn == false){
            statOn = true;
            b.setTextColor(Color.GREEN);
        } else {
            statOn = false;
            b.setTextColor(Color.WHITE);
            statF = 0;
        }

        if (statOn == true){
            b1.setText("M");
            b2.setText("d");
            b3.setText("r");
            b4.setText("η2");
            b5.setText(";");
        } else {
            b1.setText("/");
            b2.setText("*");
            b3.setText("-");
            b4.setText("+");
            b5.setText("( )");
        }
    }

    //click on display
    public void klik_editDisplay(View v){
        hideKeyboard(editDisplay);
    }

    //soft keyboard
    private void hideKeyboard(EditText editDisplay)
    {
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editDisplay.getWindowToken(), 0);
    }

    //display description of effect size
    public String es_check(int num, double wyn){
        String str = "";
        double wyn_abs = Math.abs(wyn);

        if (wyn_abs < effect_size[num][0]) {
            str= getResources().getString(R.string.no_es);
        } else
        if ((wyn_abs > effect_size[num][0]) && (wyn_abs < effect_size[num][1])) {
            str= getResources().getString(R.string.sm_es);
        } else
        if ((wyn_abs > effect_size[num][1]) && (wyn_abs < effect_size[num][2])) {
            str= getResources().getString(R.string.mod_es);
        } else
        if (wyn_abs > effect_size[num][2]) {
            str= getResources().getString(R.string.lar_es);
        }

        return str;
    }
}
