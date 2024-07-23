package com.behavidence.android.sdk_internal.data.room_repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.behavidence.android.sdk_internal.data.room_model.DopaOne.RawSensorDataEntity;

import java.util.List;

@Dao
public interface RawSensorDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRawSensorData(RawSensorDataEntity data);

    // Query to load all events
    @Query("SELECT * FROM RawSensorDataEntity ORDER BY id ASC")
    List<RawSensorDataEntity> getAllRawSensorData();

    // Query to load all events with time greater than some value in milliseconds
    @Query("SELECT * FROM RawSensorDataEntity WHERE timestamp > :timeMilli ORDER BY timestamp ASC")
    List<RawSensorDataEntity> loadEventsAfterTime(Long timeMilli);

    // Query to load all events from the startTime to endTime in milliseconds
    @Query("SELECT * FROM RawSensorDataEntity WHERE timestamp BETWEEN :startTimeMillis AND :endTimeMillis")
    List<RawSensorDataEntity> getSensorDataForDateRange(Long startTimeMillis, Long endTimeMillis);

}
