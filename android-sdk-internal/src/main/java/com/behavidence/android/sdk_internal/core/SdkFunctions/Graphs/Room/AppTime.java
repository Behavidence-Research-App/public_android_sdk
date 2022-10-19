package com.behavidence.android.sdk_internal.core.SdkFunctions.Graphs.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.behavidence.android.sdk_internal.core.RoomDb.BehavidenceSDKInternalDb;
import com.behavidence.android.sdk_internal.core.Utils.AppUtils;

import java.util.Calendar;
import java.util.TimeZone;

@Entity(primaryKeys = {"packageName", "timeInMillisecond"})
public class AppTime {
    @Ignore
    public static final String LAST_UPDATE="lastupdate";
    @Ignore
    public static final String TIME_ZONE="timezone";
    @Ignore
    private static final String TIME_OFFSET="timeoffset";


    @NonNull
    public String packageName;
    public long totalTime;
    public int frequency;
    public long timeInMillisecond;

    public AppTime(@NonNull String packageName, long totalTime, int frequency, long timeInMillisecond) {
        this.packageName = packageName;
        this.totalTime = totalTime;
        this.frequency = frequency;
        this.timeInMillisecond = timeInMillisecond;
    }

    @Ignore
    public AppTime(@NonNull String packageName) {
        this.packageName = packageName;
    }

    @Ignore
    public void setTotalTime(long timeInMillisecond){
        totalTime+=timeInMillisecond;
    }

    @Ignore
    public void setFrequency(int frequency){
        this.frequency+=frequency;
    }


    public void print(String TAG){
        Log.e(TAG,packageName+"  "+((totalTime/1000)/60)+" "+frequency);

    }

    @Ignore
    public static long getLastUpdateTime(@NonNull Context context){
        return context.getSharedPreferences(LAST_UPDATE,Context.MODE_PRIVATE).getLong(LAST_UPDATE, AppUtils.getDayRange(-6)[0]);
    }


    @SuppressLint("ApplySharedPref")
    @Ignore
    public static synchronized void saveUpdateTime(@NonNull Context context, long timeInMill){
        context.getSharedPreferences(LAST_UPDATE,Context.MODE_PRIVATE).edit().putLong(LAST_UPDATE,timeInMill)
                .commit();
    }

    @SuppressLint("ApplySharedPref")
    @Ignore
    public static boolean isTimeZoneChanged(Context context){

        String currTimeZone = Calendar.getInstance().getTimeZone().getID();
        int dayLightSavings= TimeZone.getDefault().getDSTSavings();

        return (!context.getSharedPreferences(LAST_UPDATE, Context.MODE_PRIVATE)
                .getString(TIME_ZONE, "-")
                .equals(currTimeZone)) || context.getSharedPreferences(LAST_UPDATE, Context.MODE_PRIVATE).getLong(TIME_OFFSET, -1) != dayLightSavings;
    }

    public static  void deleteAllTime(Context context){
        String currTimeZone = Calendar.getInstance().getTimeZone().getID();
        int dayLightSavings=TimeZone.getDefault().getDSTSavings();
        context.getSharedPreferences(LAST_UPDATE, Context.MODE_PRIVATE)
                .edit()
                .putString(TIME_ZONE, currTimeZone)
                .putLong(TIME_OFFSET,dayLightSavings)
                .remove(LAST_UPDATE)
                .apply();
        BehavidenceSDKInternalDb.getInstance(context)
                .graphsDao().deleteAll();
    }

}
