package com.behavidence.android.sdk_internal.core.SdkFunctions.Notifications;

import androidx.annotation.Nullable;

import java.util.List;

public class Notifications {
    private  final List<Notification> notifications;
    private final boolean hasMoreNotifications;
     Notifications(@Nullable List<Notification> notifications, boolean hasMoreNotifications) {
        this.notifications = notifications;
        this.hasMoreNotifications = hasMoreNotifications;
    }
    @Nullable
    public List<Notification> getNotifications() {
        return notifications;
    }

    public boolean hasMoreNotifications() {
        return hasMoreNotifications;
    }

}
