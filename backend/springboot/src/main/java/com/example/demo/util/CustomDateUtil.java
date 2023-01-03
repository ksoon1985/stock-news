package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CustomDateUtil {

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
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC") );
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
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        utcString = dateFormatter.format(dt);

        return utcString;
    }

    /**
     * 1주 전 ~ 현재 날짜 문자열 리스트
     */
    public static ArrayList<String> getRecent1WeekDateList(){

        // 1주 전 ~ 현재 날짜 리스트
        ArrayList<String> recent1WeekDateList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE,-6);

        for(int i=1;i<=7;i++){
            recent1WeekDateList.add(format.format(cal.getTime()));
            cal.add(Calendar.DATE,1);
        }
        return recent1WeekDateList;
    }
}
