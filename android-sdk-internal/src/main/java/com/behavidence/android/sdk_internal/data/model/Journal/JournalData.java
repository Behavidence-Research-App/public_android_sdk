package com.behavidence.android.sdk_internal.data.model.Journal;

import com.google.gson.annotations.SerializedName;

public class JournalData {

    @SerializedName("datetime")
    String dateTime;
    @SerializedName("time_in_millisec")
    String timeInMilliSec;
    @SerializedName("text")
    String text;

    public JournalData(String dateTime, String timeInMilliSec, String text) {
        this.dateTime = dateTime;
        this.timeInMilliSec = timeInMilliSec;
        this.text = text;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTimeInMilliSec() {
        return timeInMilliSec;
    }

    public void setTimeInMilliSec(String timeInMilliSec) {
        this.timeInMilliSec = timeInMilliSec;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
