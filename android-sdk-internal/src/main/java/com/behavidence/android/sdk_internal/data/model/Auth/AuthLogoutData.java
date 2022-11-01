package com.behavidence.android.sdk_internal.data.model.Auth;

import com.google.gson.annotations.SerializedName;

public class AuthLogoutData {

    @SerializedName("message")
    private String message;

    public AuthLogoutData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
