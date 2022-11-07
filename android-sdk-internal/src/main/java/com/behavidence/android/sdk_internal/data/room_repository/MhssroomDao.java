package com.behavidence.android.sdk_internal.data.room_repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.behavidence.android.sdk_internal.data.room_model.MHSS.Mhssroom;

import java.util.List;

@Dao
public interface MhssroomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Mhssroom> mhssrooms);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Mhssroom mhssroom);


    @Query("SELECT * FROM MhssRoom WHERE timestamp IN (SELECT DISTINCT timestamp FROM MhssRoom ORDER BY timestamp DESC LIMIT :limit) ORDER BY " +
            "CASE WHEN :asc = 1 THEN timestamp END ASC," +
            "CASE WHEN :asc = 0 THEN timestamp END DESC")
    List<Mhssroom> getMhss(int limit, boolean asc);

    @Query("SELECT * FROM Mhssroom WHERE date=:date")
    List<Mhssroom> getMhss(String date);

    @Query("UPDATE Mhssroom SET `like` = :like WHERE id = :id AND date = :date")
    void setLike(int like, String id, String date);

    @Query("DELETE FROM Mhssroom")
    void deleteAllMhss();

    @Query("SELECT * FROM Mhssroom WHERE timestamp>=:start AND timestamp<=:end LIMIT 10000")
    List<Mhssroom> getMhss(long start, long end);

    @Query("SELECT * FROM Mhssroom WHERE date>=:start AND date<=:end LIMIT 10000")
    List<Mhssroom> getMhss(String start, String end);
//

//
//    @Query("SELECT * FROM Mhssroom limit :limit")
//    List<Mhssroom> getMhss(int limit);
//
//    @Query("SELECT * FROM Mhssroom order by dateTimeStamp DESC limit :limit")
//    List<Mhssroom> getMhssDES(int limit);
//
//
//    @Query("DELETE  FROM Mhssroom where dateTimeStamp>=:start and dateTimeStamp<=:end")
//    void deleteMhssRange(long start,long end);
//
//    @Query("DELETE  FROM Mhssroom where dateStr=:dateStr")
//    void deleteMhss(String dateStr);


}
