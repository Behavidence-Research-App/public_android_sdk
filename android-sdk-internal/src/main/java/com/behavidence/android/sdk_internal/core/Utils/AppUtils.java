package com.behavidence.android.sdk_internal.core.Utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;

public class AppUtils {
    public static String generatePassword (int lenght) {
        final char[] allAllowed = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~!@#%^&*()-_=+[{]},<.>?".toCharArray();
        //Use cryptographically secure random number generator

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < lenght; i++) {
            password.append(allAllowed[random.nextInt(allAllowed.length)]);
        }
        try {
            return password.toString();
        }catch (Exception e){return password.toString();}

    }

    public static long[] getDateRange(String date){
        long []range={0,0};

        String []d=date.split("-");
        if(d.length<3) return range;


        Calendar calendar=Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(d[2]));
        calendar.set(Calendar.MONTH,Integer.parseInt(d[1])-1);
        calendar.set(Calendar.YEAR,Integer.parseInt(d[0]));
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        range[0]=calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        range[1]=calendar.getTimeInMillis();
        return range;


    }

    public static long[] getDayRange(int backDays) {
        Calendar calendar=Calendar.getInstance();
        if(backDays<0)
            calendar.add(Calendar.DATE,backDays);

        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Calendar calendarend = Calendar.getInstance();
        if(backDays<0) {

            calendarend.add(Calendar.DATE, backDays);
            calendarend.set(Calendar.HOUR_OF_DAY, 23);
            calendarend.set(Calendar.MINUTE, 59);
            calendarend.set(Calendar.SECOND, 59);
            calendarend.set(Calendar.MILLISECOND, 999);
        }
        return new long[]{calendar.getTimeInMillis(),calendarend.getTimeInMillis()};

    }

    public static ArrayList<Long> getTimeDistribution(long startingTime, long stopingTime, int noOfmillisec){

        ArrayList<Long> arrayList=new ArrayList<>();
        long start= startingTime;
        long end=startingTime+noOfmillisec;


        while (end<=stopingTime){
            arrayList.add(start);
            arrayList.add(end);
            end++;
            start=end;
            end+=noOfmillisec;
        }
        if(start<=stopingTime)
        {
            arrayList.add(start);
            arrayList.add(stopingTime);
        }

        return arrayList;
    }


}
