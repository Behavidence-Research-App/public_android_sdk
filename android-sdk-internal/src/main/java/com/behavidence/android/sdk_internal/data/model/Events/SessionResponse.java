package com.behavidence.android.sdk_internal.data.model.Events;

import com.google.gson.annotations.SerializedName;

public class SessionResponse {

    @SerializedName("lasttimeupload")
    private long lastTimeUploaded;

    public long getLastTimeUploaded() {
        return lastTimeUploaded;
    }

    public void setLastTimeUploaded(long lastTimeUploaded) {
        this.lastTimeUploaded = lastTimeUploaded;
    }
}
