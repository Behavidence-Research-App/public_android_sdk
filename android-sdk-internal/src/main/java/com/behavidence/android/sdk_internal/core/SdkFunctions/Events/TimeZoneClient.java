package com.behavidence.android.sdk_internal.core.SdkFunctions.Events;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.List;
import java.util.TimeZone;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.TimeZoneInfo;

public interface TimeZoneClient {


    /**
     *
     * @return TimeZoneClient for timezone related operation
     */
    @NonNull
    static TimeZoneClient getInstance(){
        return new TimeZoneClientImpl();
    }

    @NonNull
     TimeZoneInfo getTimeZoneForTime(@NonNull List<TimeZoneInfo> timeZoneInfos,long time);


    /**
     * log the current time zone required for the Behavidence SDK
     * must be called in Android Receiver receiving time zones updates
     * @param context Context
     */
     void logTimeZone(@NonNull Context context);

}

class TimeZoneClientImpl implements TimeZoneClient{


    @NonNull
    @Override
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

    @Override
    public void logTimeZone(@NonNull Context context) {
        TimeZoneIO.getInstance(context).logTimeZone();
    }
}
