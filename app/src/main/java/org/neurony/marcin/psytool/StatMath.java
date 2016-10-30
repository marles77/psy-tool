package org.neurony.marcin.psytool;

/**
 * Created by Marcin on 2016-03-31.
 */


public class StatMath {
    //
    TextCutter textCutter;
    double tab[];
    double val[];
    double sumVal;
    double sS;

    private String[] cutStr(String str){

        String[] parts = str.split(";");


        return parts;
    }
    //calculating mean and SD
    public double[] mSd(String str1){
        tab = new double[2];
        tab[0] = 1; //mean
        tab[1] = 2; //SD
        textCutter = new TextCutter();

        String[] str2 = cutStr(str1);
        if (str2.length > 4) {
            val = new double[str2.length];
            sumVal = 0;
            sS = 0;

            for (int i = 0; i < str2.length; i++) {

                val[i] = textCutter.toDbl(str2[i]);
                sumVal = sumVal + val[i];
            }

            tab[0] = sumVal / val.length;

            for (int i = 0; i < val.length; i++) {

                sS = sS + (Math.pow((val[i] - tab[0]), 2)); //sum of squares

            }

            tab[1] = Math.sqrt(sS / (val.length-1)); //SD
        } else {tab[0] = 999; tab[1] = 999;}


        return tab;
    }
    //calculating d
    public double dEf(String str1){
        double d = 0;
        double pool = 1;
        double val[] = new double[4]; //0- M1, 1- SD1, 2- M2, 3- SD2
        textCutter = new TextCutter();

        String[] str2 = cutStr(str1);
        if (str2.length == 4) {
            for (int i = 0; i < str2.length; i++) {val[i] = textCutter.toDbl(str2[i]);}
            pool = (Math.pow(val[3], 2) + (Math.pow(val[1], 2)))/2;
            d = (val[2] - val[0]) / Math.sqrt(pool);
        } else d = 999;

        return d;
    }
    //calculating r
    public double rEf(String str1){
        double r = 0;
        double val[] = new double[3]; //0- Z, 1- N1, 2- N2
        textCutter = new TextCutter();
        String[] str2 = cutStr(str1);
        if (str2.length == 3) {
            for (int i = 0; i < str2.length; i++) {val[i] = textCutter.toDbl(str2[i]);}
            r = val[0] / Math.sqrt(val[1] + val[2]);
        } else r = 999;

        return r;
    }
    //calculating eta2
    public double etaEf(String str1){
        double eta = 0;
        double sqrT = 0;
        double val[] = new double[3]; //0- t, 1- N1, 2- N2
        textCutter = new TextCutter();
        String[] str2 = cutStr(str1);
        if (str2.length == 3) {
            for (int i = 0; i < str2.length; i++) {val[i] = textCutter.toDbl(str2[i]);}
            sqrT = Math.sqrt(val[0]);
            eta = sqrT / (sqrT + (val[1] + val[2] - 2));
        } else eta = 999;
        return eta;
    }
}
