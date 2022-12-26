package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CustomDateFormatter {

    // yyyy-MM-dd'T'HH:mm:ss.SSS'Z' (UTC Time) -> yyyy-MM-dd HH:mm:ss
    public static String UTCToCustomDateTime(String utcString){
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        transFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dt = null;
        try {
            dt = transFormat.parse(utcString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        utcString = dateFormatter.format(dt);

        return utcString;
    }

    // yyyy-MM-dd'T'HH:mm:ss.SSS'Z' (UTC Time) -> yyyy-MM-dd
    public static String UTCToCustomDate(String utcString){
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        transFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dt = null;
        try {
            dt = transFormat.parse(utcString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        utcString = dateFormatter.format(dt);

        return utcString;
    }
}
