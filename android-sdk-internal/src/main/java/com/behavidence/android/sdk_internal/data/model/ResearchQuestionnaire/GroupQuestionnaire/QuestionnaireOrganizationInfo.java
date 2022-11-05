package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Organization;
import com.google.gson.annotations.SerializedName;

   
public class QuestionnaireOrganizationInfo implements Organization {

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

    @NonNull
    @Override
    public String getOrganizationName() {
        return name;
    }

    @Nullable
    @Override
    public String getOrganizationWebsite() {
        return website;
    }

    @Override
    public String toString() {
        return "QuestionnaireOrganizationInfo{" +
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}