package com.behavidence.android.sdk_internal.data.room_model.Notification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class NotificationRoom {

    @PrimaryKey
    private final long  timeInMilli;
    @NonNull
    private final String title;
    @NonNull
    private final String subTitle;
    private final String htmlTxt;
    private final String txt;

    public NotificationRoom(long timeInMilli, @NonNull String title, @NonNull String subTitle, @Nullable String htmlTxt, @Nullable String txt) {
        this.timeInMilli = timeInMilli;
        this.title = title;
        this.subTitle = subTitle;
        this.htmlTxt = htmlTxt;
        this.txt = txt;
    }

    public long getTimeInMilli() {
        return timeInMilli;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getSubTitle() {
        return subTitle;
    }

    @Nullable
    public String getHtmlTxt() {
        return htmlTxt;
    }

    @Nullable
    public String getTxt() {
        return txt;
    }
}
