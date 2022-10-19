package com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.TimeZoneInfo;

@Dao
public interface TimeZoneInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TimeZoneInfo timeZoneInfo);


    @Query("SELECT * FROM TimeZoneInfo ORDER BY changedTime DESC limit :limit")
    List<TimeZoneInfo> loadTimeZoneDESC(int limit);

    @Query("SELECT * FROM TimeZoneInfo where changedTime>:timeInMilli ORDER BY changedTime")
    List<TimeZoneInfo> loadRemainingTime(long timeInMilli);

}
