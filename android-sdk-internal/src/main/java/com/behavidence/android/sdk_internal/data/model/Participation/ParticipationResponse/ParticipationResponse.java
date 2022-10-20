package com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse;

import com.google.gson.annotations.SerializedName;

   
public class ParticipationResponse {

   @SerializedName("data")
   Data data;


    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }
    
}