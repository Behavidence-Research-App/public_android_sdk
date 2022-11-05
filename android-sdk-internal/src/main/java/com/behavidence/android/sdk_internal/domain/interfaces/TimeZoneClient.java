package com.behavidence.android.sdk_internal.domain.interfaces;

import android.content.Context;

import androidx.annotation.NonNull;


import com.behavidence.android.sdk_internal.data.room_model.Events.TimeZoneInfo;

import java.util.List;

public interface TimeZoneClient {

    @NonNull
    List<TimeZoneInfo> loadTimeZonesDES(int limit);

    @NonNull
    List<TimeZoneInfo> loadUploadPendingZones(long lastTimeOfUpload);

    long getLastTimeOfUpload(Context context);

    void saveLastTimeOfUpload(Context context, long time);

    void logTimeZone();

}
