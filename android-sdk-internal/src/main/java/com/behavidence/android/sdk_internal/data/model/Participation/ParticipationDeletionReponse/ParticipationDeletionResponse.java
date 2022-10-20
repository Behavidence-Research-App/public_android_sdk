package com.behavidence.android.sdk_internal.data.model.Participation.ParticipationDeletionReponse;

import com.google.gson.annotations.SerializedName;

public class ParticipationDeletionResponse {

    @SerializedName("data")
    Data data;

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

}
