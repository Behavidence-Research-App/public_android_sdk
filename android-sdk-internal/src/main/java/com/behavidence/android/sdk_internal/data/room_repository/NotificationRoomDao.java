package com.behavidence.android.sdk_internal.data.room_repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.behavidence.android.sdk_internal.data.room_model.Notification.NotificationRoom;

import java.util.List;

@Dao
interface NotificationRoomDao {
    //insertion and replace already existing element
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<NotificationRoom> notificationRooms);

    @Query("Delete FROM NotificationRoom")
    void deleteAllNotifications();

    @Query("DELETE FROM NotificationRoom WHERE timeInMilli=:time")
    void deleteNotification(long time);

    //getting all session time by end time because some session starts but not end in timeframe
    @Query("SELECT * FROM NotificationRoom WHERE timeInMilli BETWEEN :timeFrom AND :timeTo ORDER BY timeInMilli DESC limit :limit")
    List<NotificationRoom> getNotifications(long timeFrom, long timeTo, int limit);

}