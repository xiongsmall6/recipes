package com.xiong.recipes.utils;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
    public static boolean isOneDay(Date date1 , Date date2){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }


    public static boolean match(String value){
        if(value==null){
            return false;
        }
        String regex = "^[\\da-zA-Z_-]+$";
        return value.matches(regex);
    }
}
