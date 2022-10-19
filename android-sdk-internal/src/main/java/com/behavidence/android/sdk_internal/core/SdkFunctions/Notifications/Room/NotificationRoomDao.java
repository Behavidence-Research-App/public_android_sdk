package com.behavidence.android.sdk_internal.core.SdkFunctions.Notifications.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface NotificationRoomDao {
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
