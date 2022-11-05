package com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse;

import com.google.gson.annotations.SerializedName;

   
public class OrganizationInfo {

   @SerializedName("name")
   String name;

   @SerializedName("website")
   String website;


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}