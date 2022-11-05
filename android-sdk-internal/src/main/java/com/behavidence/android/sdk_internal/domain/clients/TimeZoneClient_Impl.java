package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;

import androidx.annotation.NonNull;


import com.behavidence.android.sdk_internal.data.room_model.Events.TimeZoneInfo;
import com.behavidence.android.sdk_internal.domain.interfaces.TimeZoneClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

class TimeZoneClient_Impl extends ClientParent implements TimeZoneClient {

    private static final String TIME_ZONE_FILE = "timezonesbheavidence";


    TimeZoneClient_Impl(Context context) {
        super(context);
    }


    @Override
    @NonNull
    public List<TimeZoneInfo> loadTimeZonesDES(int limit) {
        List<TimeZoneInfo> timeZoneInfos = database.timeZoneInfoDao().loadTimeZoneDESC(limit);
        if (timeZoneInfos == null) timeZoneInfos = new ArrayList<>();
        return timeZoneInfos;

    }

    @NonNull
    @Override
    public List<TimeZoneInfo> loadUploadPendingZones(long lastTimeOfUpload) {
        List<TimeZoneInfo> timeZoneInfos = database.timeZoneInfoDao().loadRemainingTime(lastTimeOfUpload);
        if (timeZoneInfos == null) timeZoneInfos = new ArrayList<>();
        return timeZoneInfos;
    }


    @Override
    public long getLastTimeOfUpload(Context context) {
        return context.getSharedPreferences(TIME_ZONE_FILE, Context.MODE_PRIVATE)
                .getLong(TIME_ZONE_FILE, 0);
    }

    @Override
    public void saveLastTimeOfUpload(Context context, long time) {
        if (time > -1)
            saveLastTimeOfUploadSync(context, time);


    }

    private static synchronized void saveLastTimeOfUploadSync(@NonNull Context context, long time) {
        context.getSharedPreferences(TIME_ZONE_FILE, Context.MODE_PRIVATE)
                .edit().putLong(TIME_ZONE_FILE, time).apply();
    }

    @Override
    public void logTimeZone() {
        int rawOffset = TimeZone.getDefault().getRawOffset() + TimeZone.getDefault().getDSTSavings();
        List<TimeZoneInfo> infoList = database.timeZoneInfoDao().loadTimeZoneDESC(1);
        if (infoList != null && infoList.size() > 0 && (infoList.get(0)
                .getTimeZoneOffset() == rawOffset && infoList.get(0).getTimeZoneId().equals(TimeZone.getDefault().getID())))
            return;
        database.timeZoneInfoDao().insert(new TimeZoneInfo(
                Calendar.getInstance().getTimeInMillis(),
                TimeZone.getDefault().getID(),
                rawOffset));

    }

    public TimeZoneInfo getTimeZoneForTime(@NonNull List<TimeZoneInfo> timeZoneInfos, long time) {
        if(timeZoneInfos.size()>1) {
            for (int i=0;i<timeZoneInfos.size();i++) {
                if (time < timeZoneInfos.get(i).getChangedTime()&&(i<(timeZoneInfos.size()-1)))
                    continue;
                return new TimeZoneInfo(0, timeZoneInfos.get(i).getTimeZoneId(), timeZoneInfos.get(i).getTimeZoneOffset());
            }
        }
        return new TimeZoneInfo(0, TimeZone.getDefault().getID(),TimeZone.getDefault().getRawOffset()+TimeZone.getDefault().getDSTSavings());
    }

}
