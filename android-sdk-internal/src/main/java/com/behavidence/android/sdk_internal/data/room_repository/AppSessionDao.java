package com.behavidence.android.sdk_internal.data.room_repository;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.behavidence.android.sdk_internal.data.room_model.Events.AppSession;

import java.util.List;

//public interface to perform different queries on tables
@Dao
interface AppSessionDao {

    //getting all pending session which are started but not ended yet
    @Query("SELECT * FROM AppSession WHERE endTime=-1")
    List<AppSession> getPendingSessions();

    //insertion and replace already existing element
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insert(List<AppSession> appSessions);

    @Query("Delete FROM AppSession")
       void deleteAllSessions();

    //deleting all pending session which are started but never completed
    @Query("DELETE FROM AppSession WHERE startTime<=:timeRange AND endTime=-1")
     void deletePendingSessions(long timeRange);

    @Query("SELECT * FROM AppSession")
     List<AppSession> getAllSessions();

    //getting all session time by end time because some session starts but not end in timeframe
    @Query("SELECT * FROM AppSession WHERE endTime>:time ORDER BY endTime ASC")
    List<AppSession> getSessions(long time);

}
