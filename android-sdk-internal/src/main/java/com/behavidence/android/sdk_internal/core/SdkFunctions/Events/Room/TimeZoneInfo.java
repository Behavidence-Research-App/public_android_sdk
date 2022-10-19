package com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity
public class TimeZoneInfo {

   @PrimaryKey
    private final long changedTime;
    private final String timeZoneId;
    private final int timeZoneOffset;

    public TimeZoneInfo(long changedTime, String timeZoneId, int timeZoneOffset) {
        this.changedTime = changedTime;
        this.timeZoneId = timeZoneId;
        this.timeZoneOffset = timeZoneOffset;
    }
    public long getChangedTime() {
        return changedTime;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public int getTimeZoneOffset() {
        return timeZoneOffset;
    }

   @Ignore
    public void print(String key){
        Log.e(key,"ChangeTime: "+changedTime+" "+" id: "+timeZoneId+" offset: "+timeZoneOffset);

    }

}
