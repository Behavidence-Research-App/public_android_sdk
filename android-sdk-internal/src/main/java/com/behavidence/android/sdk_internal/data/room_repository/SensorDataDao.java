package com.behavidence.android.sdk_internal.data.room_repository;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.behavidence.android.sdk_internal.data.room_model.DopaOne.SensorDataEntity;

import java.util.List;

/**
 * `SensorDataDAO` is a Data Access Object (DAO) interface for performing database operations related to
 * sensor data entities. It defines methods for inserting and querying sensor data.
 */
@Dao
public interface SensorDataDao {

    // Insert new SensorEvent into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSensorData(SensorDataEntity data);

    // Query to load all events
    @Query("SELECT * FROM SensorDataEntity ORDER BY id ASC")
    List<SensorDataEntity> getAllSensorData();

    // Query to load all events with time greater than some value in milliseconds
    @Query("SELECT * FROM SensorDataEntity WHERE timeInMilliUtc > :timeMilli ORDER BY timeInMilliUtc ASC")
    List<SensorDataEntity> loadEventsAfterTime(Long timeMilli);

    // Query to load all events from the startTime to endTime in milliseconds
    @Query("SELECT * FROM SensorDataEntity WHERE timeInMilliUtc BETWEEN :startTimeMillis AND :endTimeMillis")
    List<SensorDataEntity> getSensorDataForDateRange(Long startTimeMillis, Long endTimeMillis);


}
