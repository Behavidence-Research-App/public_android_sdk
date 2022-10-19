package com.behavidence.android.sdk_internal.core.SdkFunctions.Notifications;

import android.content.Context;
import androidx.annotation.NonNull;
import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.RoomDb.BehavidenceSDKInternalDb;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Notifications.Room.NotificationRoom;
import com.behavidence.android.sdk_internal.core.Utils.Executor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public interface NotificationsClient {
  String NOTIFICATION_FILE="behavidenceNotifcations";
    //need to refactor to follow singleTon pattern
    static NotificationsClient getInstance(@NonNull Context context){
        return NotificationsClientImpl.getNotificationsClientImpl(context,AuthClient.getInstance(context),ApiKey.getInstance(context));
    }

    static void logout(@NonNull Context context){
        context.getSharedPreferences(NOTIFICATION_FILE,Context.MODE_PRIVATE)
                .edit().clear().apply();
        BehavidenceSDKInternalDb.getInstance(context)
                .notificationRoomDao().deleteAllNotifications();
    }

    @NonNull
    Executor<Notifications> getNotifications();
    @NonNull
    public Executor<Notifications> getNotifications(long notificationTimeFrom,long lastNotificationTimeTo);

    Executor<Void> deleteNotification(@NonNull Notification notification);

}

class NotificationsClientImpl implements NotificationsClient{


    private static NotificationsClientImpl notificationsClientImpl;
    private static final int NOTIFICATIONS_LIMIT=60;
    private final NotificationsIO notificationsIO;
    private final AuthClient authClient;
    private ApiKey apiKey;

    static synchronized NotificationsClientImpl getNotificationsClientImpl(@NonNull Context context,@NonNull AuthClient authClient,@NonNull ApiKey apiKey){
        if(notificationsClientImpl==null)
            notificationsClientImpl=new NotificationsClientImpl(authClient,context,apiKey);
        else
            notificationsClientImpl.apiKey=apiKey;
        return notificationsClientImpl;
    }

    private NotificationsClientImpl(@NonNull AuthClient authClient,@NonNull Context context,@NonNull ApiKey apiKey) {
        this.authClient=authClient;
        this.apiKey=apiKey;
        notificationsIO=NotificationsIO.getInstance(context);
    }

    @NonNull
    @Override
    public Executor<Notifications> getNotifications() {
        return new Executor<>(() -> getLatestNotifications(notificationsIO,authClient,apiKey));
    }
    @NonNull
    @Override
    public Executor<Notifications> getNotifications(long notificationTimeFrom,long lastNotificationTimeTo) {
        return new Executor<>(() -> getPaginatedNotifications(notificationsIO,authClient,apiKey,notificationTimeFrom,lastNotificationTimeTo));
    }

    @Override
    public Executor<Void> deleteNotification(@NonNull Notification notification) {
        return new Executor<>(() -> deleteNotification(authClient,apiKey,notificationsIO,notification));

    }
    private static synchronized Void deleteNotification(@NonNull AuthClient authClient,@NonNull ApiKey apiKey,@NonNull NotificationsIO notificationsIO,@NonNull Notification notification){
        new NotificationsApiRequest(authClient,apiKey).new
                DeleteNotifications(new String[]{notification.getTimeInMilli()+""}).buildApiCaller().callApiGetResponse();
        notificationsIO.deleteNotifications(notification);
        return null;
    }

    @NonNull
    private static  synchronized Notifications getLatestNotifications(@NonNull NotificationsIO notificationsIO,@NonNull AuthClient authClient,@NonNull ApiKey apiKey) {
        final long thirtySecInMilli = 30000;
        final long currentTimeInMilli = Calendar.getInstance().getTimeInMillis();
        long lastTimeFetchedTimeMilli =notificationsIO.loadLastTimeFetch();

        if ((lastTimeFetchedTimeMilli + thirtySecInMilli) < currentTimeInMilli) {
            final long thirtyDaysInMilli = 2592000000L;
            if (lastTimeFetchedTimeMilli + thirtyDaysInMilli < currentTimeInMilli || lastTimeFetchedTimeMilli == 0)
                lastTimeFetchedTimeMilli =currentTimeInMilli-thirtyDaysInMilli;

            syncNotificationsFromServer(false,notificationsIO, authClient,apiKey, lastTimeFetchedTimeMilli, currentTimeInMilli);
            notificationsIO.saveLastTimeFetch(currentTimeInMilli+1);
        }

        return getNotificationsFromLocalDb(notificationsIO, 0, Calendar.getInstance().getTimeInMillis());
    }
    private static synchronized Notifications getPaginatedNotifications(@NonNull NotificationsIO notificationsIO,@NonNull AuthClient authClient,@NonNull ApiKey apiKey,long timeFrom,long timeTo){
          syncNotificationsFromServer(true,notificationsIO,authClient,apiKey,timeFrom,timeTo);
          return getNotificationsFromLocalDb(notificationsIO,timeFrom,timeTo);
    }


    private static void syncNotificationsFromServer(boolean isPaginationCall,@NonNull NotificationsIO notificationsIO,@NonNull AuthClient authClient,@NonNull ApiKey apiKey,long timeFrom,long timeTo){

         Notifications notifications=new NotificationsApiRequest(authClient,apiKey).new GetNotifications(timeFrom,timeTo).buildApiCaller().callApiGetResponse();
        if(notifications!=null&&notifications.getNotifications()!=null){
            List<NotificationRoom> notificationRooms=new ArrayList<>();
            for(Notification notification:notifications.getNotifications()){
                notificationRooms.add(notification.getNotificationRoom());
            }
            notificationsIO.saveNotificationsInRoom(notificationRooms);
            final long lastNotificationTime=notifications.getNotifications().get(
                    notifications.getNotifications().size()-1).getTimeInMilli();
            if(notifications.hasMoreNotifications())
                notificationsIO.savePaginationNotificationTime(lastNotificationTime-1);
            else if(isPaginationCall)
                notificationsIO.savePaginationNotificationTime(0);
        }
    }

    private static Notifications getNotificationsFromLocalDb(NotificationsIO notificationsIO,long from,long to){
        List<Notification> notifications= notificationsIO.loadNotificationsFromRoom(from,to,NOTIFICATIONS_LIMIT);
        if(notifications.isEmpty())
            return new Notifications(null,false);
        return new Notifications(notifications,notificationsIO.getPaginationNotificationTime()>0);
    }


}

