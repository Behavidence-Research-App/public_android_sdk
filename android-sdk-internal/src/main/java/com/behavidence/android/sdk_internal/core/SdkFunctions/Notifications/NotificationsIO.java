package com.behavidence.android.sdk_internal.core.SdkFunctions.Notifications;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.core.RoomDb.BehavidenceSDKInternalDb;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Notifications.Room.NotificationRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Notifications.Room.NotificationRoomDao;

import java.util.ArrayList;
import java.util.List;


interface NotificationsIO {

   static NotificationsIO getInstance(@NonNull Context context){
        return new NotificationIOImpl(context);
    }

   void saveLastTimeFetch(long lastTimeInMilli);
    void savePaginationNotificationTime(long lastTimeInMilli);
   long getPaginationNotificationTime();
   long loadLastTimeFetch();
   List<Notification> loadNotificationsFromRoom(long timeFrom, long timeTo, int limit);
   void saveNotificationsInRoom(@NonNull List<NotificationRoom> notifications);
   void deleteNotifications(@NonNull Notification notification);
}

 class NotificationIOImpl implements NotificationsIO{
     private static final String LAST_TIME_FETCHED="lastitmefetched";
     private static final String PAG_NOTIFICATIONS_TIME="morenotifications";
   private final NotificationRoomDao notificationRoomDao;
   private final SharedPreferences sharedPreferences;

     public NotificationIOImpl(@NonNull Context context) {
         context=context.getApplicationContext();
         this.notificationRoomDao = BehavidenceSDKInternalDb.getInstance(context).notificationRoomDao();
         this.sharedPreferences = context.getSharedPreferences(NotificationsClient.NOTIFICATION_FILE,Context.MODE_PRIVATE);
     }

     @Override
     public void saveLastTimeFetch(long lastTimeInMilli) {
          sharedPreferences.edit().putLong(LAST_TIME_FETCHED,lastTimeInMilli)
                  .apply();
     }
     @Override
     public void savePaginationNotificationTime(long lastTimeInMilli) {
         sharedPreferences.edit().putLong(PAG_NOTIFICATIONS_TIME,lastTimeInMilli)
                 .apply();
     }

     @Override
     public long getPaginationNotificationTime() {
         return sharedPreferences
                 .getLong(PAG_NOTIFICATIONS_TIME,0);
     }

     @Override
     public long loadLastTimeFetch() {
         return sharedPreferences
                 .getLong(LAST_TIME_FETCHED,0);
     }

     @Override
     public List<Notification> loadNotificationsFromRoom(long timeFrom, long timeTo,int limit) {
        List<Notification> notifications=new ArrayList<>();
       List<NotificationRoom> notificationRooms=  notificationRoomDao.getNotifications(timeFrom,timeTo,limit);
       for(NotificationRoom nr:notificationRooms)
           notifications.add(new Notification(nr));
        return notifications;
     }

     @Override
     public void saveNotificationsInRoom(@NonNull List<NotificationRoom> notifications) {
            if(!notifications.isEmpty())
              notificationRoomDao.insert(notifications);
     }

     @Override
     public void deleteNotifications(@NonNull Notification notification) {
         notificationRoomDao.deleteNotification(notification.getTimeInMilli());
     }

 }

