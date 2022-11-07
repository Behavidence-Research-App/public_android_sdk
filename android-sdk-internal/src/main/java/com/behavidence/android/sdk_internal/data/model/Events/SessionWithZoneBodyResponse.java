package com.behavidence.android.sdk_internal.data.model.Events;

import com.google.gson.annotations.SerializedName;

public class SessionWithZoneBodyResponse {

    @SerializedName("data")
    SessionResponse response;

    public SessionResponse getResponse() {
        return response;
    }

    public void setResponse(SessionResponse response) {
        this.response = response;
    }
}
