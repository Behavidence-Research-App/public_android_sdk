package com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

import org.jetbrains.annotations.NotNull;



//RoomDb DB Entity class for app sessions
@Entity(primaryKeys = {"packageName", "startTime"})
public class AppSession {


    @NonNull
    private final String packageName;
    private final long startTime;
    private final long endTime;


    public AppSession(@NonNull String packageName, long startTime, long endTime) {
        this.packageName = packageName;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    @NonNull
    public String getPackageName() {
        return packageName;
    }

    public long getStartTime() {
        return startTime;
    }


    public long getEndTime() {
        return endTime;
    }


    @Ignore
    public void print(){
        Log.e("asmarevents","packageName: "+packageName+" startTime: "+startTime+" endTime: "+endTime);

    }
}
