package com.behavidence.android.sdk_internal.core.SdkFunctions.Events;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.behavidence.android.sdk_internal.core.RoomDb.BehavidenceSDKInternalDb;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.TimeZoneInfo;

public interface TimeZoneIO {

     static TimeZoneIO getInstance(@NonNull Context context){
        return new TimeZoneIOImpl(context);
    }

    @NonNull
     List<TimeZoneInfo> loadTimeZonesDES(int limit);

    @NonNull
     List<TimeZoneInfo> loadUploadPendingZones(long lastTimeOfUpload);

     long getLastTimeOfUpload();

     void saveLastTimeOfUpload(long time);

     void logTimeZone();


}

class TimeZoneIOImpl implements TimeZoneIO{

    private static final String TIME_ZONE_FILE="timezonesbheavidence";
    private  final Context context;

    TimeZoneIOImpl(Context context){ this.context=context;}


    @Override
    @NonNull
    public List<TimeZoneInfo> loadTimeZonesDES(int limit){
        List<TimeZoneInfo> timeZoneInfos= BehavidenceSDKInternalDb.getInstance(context)
                .timeZoneInfoDao().loadTimeZoneDESC(limit);
        if(timeZoneInfos==null) timeZoneInfos=new ArrayList<>();
        return timeZoneInfos;

    }

    @NonNull
    @Override
    public List<TimeZoneInfo> loadUploadPendingZones(long lastTimeOfUpload){
        List<TimeZoneInfo> timeZoneInfos= BehavidenceSDKInternalDb.getInstance(context)
                .timeZoneInfoDao().loadRemainingTime(lastTimeOfUpload);
        if(timeZoneInfos==null) timeZoneInfos=new ArrayList<>();
        return timeZoneInfos;
    }



    @Override
    public long getLastTimeOfUpload(){
        return context.getSharedPreferences(TIME_ZONE_FILE,Context.MODE_PRIVATE)
                .getLong(TIME_ZONE_FILE,0);
    }

    @Override
    public void saveLastTimeOfUpload(long time){
        if(time>-1)
            saveLastTimeOfUploadSync(context,time);


    }
    private static synchronized void saveLastTimeOfUploadSync(@NonNull Context context,long time){
        context.getSharedPreferences(TIME_ZONE_FILE,Context.MODE_PRIVATE)
                .edit().putLong(TIME_ZONE_FILE,time).apply();
    }

    @Override
    public void logTimeZone(){
        int rawOffset= TimeZone.getDefault().getRawOffset()+TimeZone.getDefault().getDSTSavings();
        List<TimeZoneInfo> infoList= BehavidenceSDKInternalDb.getInstance(context)
                .timeZoneInfoDao().loadTimeZoneDESC(1);
        if(infoList!=null&&infoList.size()>0&&(infoList.get(0)
                .getTimeZoneOffset()==rawOffset&&infoList.get(0).getTimeZoneId().equals(TimeZone.getDefault().getID())))
            return;
        BehavidenceSDKInternalDb.getInstance(context).timeZoneInfoDao().insert(new TimeZoneInfo(
                Calendar.getInstance().getTimeInMillis(),
                TimeZone.getDefault().getID(),
                rawOffset));

    }
}
