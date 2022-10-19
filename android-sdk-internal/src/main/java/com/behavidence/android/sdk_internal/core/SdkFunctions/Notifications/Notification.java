package com.behavidence.android.sdk_internal.core.SdkFunctions.Notifications;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.core.SdkFunctions.Notifications.Room.NotificationRoom;

public class Notification {
    private final NotificationRoom notificationRoom;

     Notification(@NonNull NotificationRoom notificationRoom) {
        this.notificationRoom=notificationRoom;
    }

    @NonNull
    NotificationRoom getNotificationRoom(){
        return notificationRoom;
    }
    @NonNull
    public String getTitle() {
        return notificationRoom.getTitle();
    }

    @NonNull
    public String getSubTitle() {
        return notificationRoom.getSubTitle();
    }

    @Nullable
    public String getHtmlTxt() {
        return notificationRoom.getHtmlTxt();
    }

    @Nullable
    public String getTxt() {
        return notificationRoom.getTxt();
    }

    public long getTimeInMilli() {
        return notificationRoom.getTimeInMilli();
    }
}
