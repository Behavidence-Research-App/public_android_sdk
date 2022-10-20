package com.behavidence.android.sdk_internal.data.model.Participation.ParticipationDeletionReponse;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("message")
    String message;


    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

}
