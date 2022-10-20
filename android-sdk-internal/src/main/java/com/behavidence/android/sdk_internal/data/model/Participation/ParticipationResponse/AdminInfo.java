package com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse;

import com.google.gson.annotations.SerializedName;

   
public class AdminInfo {

   @SerializedName("id")
   String id;

   @SerializedName("organizational_name")
   String organizationalName;

   @SerializedName("email")
   String email;

   @SerializedName("orgnization")
   String orgnization;


    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    
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
    
    public void setOrgnization(String orgnization) {
        this.orgnization = orgnization;
    }
    public String getOrgnization() {
        return orgnization;
    }
    
}