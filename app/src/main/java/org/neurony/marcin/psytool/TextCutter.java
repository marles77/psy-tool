package org.neurony.marcin.psytool;

/**
 * Created by Marcin on 2016-03-17.
 */
public class TextCutter {
    int hrsI = 0;
    int minsI = 0;
    int secsI = 0;

    //transform string to integer
    public int toInt(String str){
      int setInt1 = 0;
        try {
            setInt1 = Integer.parseInt(str);
        } catch(Exception e){
            setInt1 = 9;
        }
      return setInt1;
    }
    //transform string to double
    public double toDbl(String str){
        double setDbl1 = 0;
        try{
            setDbl1 = Double.parseDouble(str);
        } catch(Exception e){
            setDbl1 = 9;
        }
        return setDbl1;
    }
    //cut the string
    public void cutStr(String str){
        String[] parts = str.split(":");
        String hrs = parts[0];
        String mins = parts[1];
        String secs = parts[2];

        hrsI = toInt(hrs);
        minsI = toInt(mins);
        secsI = toInt(secs);
    }
}

