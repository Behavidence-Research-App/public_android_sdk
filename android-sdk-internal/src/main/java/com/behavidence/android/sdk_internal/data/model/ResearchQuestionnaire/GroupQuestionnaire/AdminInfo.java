package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire;

import com.google.gson.annotations.SerializedName;

   
public class AdminInfo {

   @SerializedName("organizational_name")
   String organizationalName;

   @SerializedName("email")
   String email;

   @SerializedName("organization")
   String organization;

   @SerializedName("adminid")
   String adminid;


    public void setOrganizationalName(String organizationalName) {
        this.organizationalName = organizationalName;
    }
    public String getOrganizationalName() {
        return organizationalName;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    
    public void setOrganization(String organization) {
        this.organization = organization;
    }
    public String getOrganization() {
        return organization;
    }
    
    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }
    public String getAdminid() {
        return adminid;
    }
    
}