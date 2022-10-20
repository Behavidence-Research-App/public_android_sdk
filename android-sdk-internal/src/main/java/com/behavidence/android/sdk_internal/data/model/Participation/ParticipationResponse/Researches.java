package com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse;

import com.google.gson.annotations.SerializedName;

   
public class Researches {

   @SerializedName("creation_time")
   String creationTime;

   @SerializedName("name")
   String name;

   @SerializedName("code")
   String code;

   @SerializedName("custom_label")
   CustomLabel customLabel;


    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
    public String getCreationTime() {
        return creationTime;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
    
    public void setCustomLabel(CustomLabel customLabel) {
        this.customLabel = customLabel;
    }
    public CustomLabel getCustomLabel() {
        return customLabel;
    }
    
}