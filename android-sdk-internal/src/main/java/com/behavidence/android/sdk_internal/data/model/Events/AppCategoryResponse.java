package com.behavidence.android.sdk_internal.data.model.Events;

import com.google.gson.annotations.SerializedName;

public class AppCategoryResponse {

    @SerializedName("data")
    String data;

    @SerializedName("message")
    String message;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AppCategoryResponse{" +
                "data='" + data + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
