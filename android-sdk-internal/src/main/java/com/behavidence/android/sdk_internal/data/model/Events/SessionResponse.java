package com.behavidence.android.sdk_internal.data.model.Events;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class SessionResponse {

    @SerializedName("lasttimeupload")
    private long lastTimeUploaded;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private JsonElement data;

    public long getLastTimeUploaded() {
        return lastTimeUploaded;
    }

    public void setLastTimeUploaded(long lastTimeUploaded) {
        this.lastTimeUploaded = lastTimeUploaded;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SessionResponse{" +
                "lastTimeUploaded=" + lastTimeUploaded +
                '}';
    }
}
