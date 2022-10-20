package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire;

import com.google.gson.annotations.SerializedName;

   
public class OrganizationInfo {

   @SerializedName("name")
   String name;


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    
}