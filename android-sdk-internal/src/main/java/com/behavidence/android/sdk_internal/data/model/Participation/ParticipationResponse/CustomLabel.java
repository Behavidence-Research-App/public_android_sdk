package com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse;

import com.google.gson.annotations.SerializedName;

   
public class CustomLabel {

   @SerializedName("android")
   Android android;


    public void setAndroid(Android android) {
        this.android = android;
    }
    public Android getAndroid() {
        return android;
    }
    
}