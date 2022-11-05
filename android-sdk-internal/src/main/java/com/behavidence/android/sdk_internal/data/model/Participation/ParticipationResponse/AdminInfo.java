package com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse;

import com.google.gson.annotations.SerializedName;

   
public class AdminInfo {

   @SerializedName("id")
   String id;

   @SerializedName("name")
   String name;

   @SerializedName("middle_name")
   String middleName;

   @SerializedName("organizational_name")
   String organizationalName;

   @SerializedName("email")
   String email;

   @SerializedName("orgnization")
   String orgnization;

    @SerializedName("address")
    String address;


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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOrgnization(String orgnization) {
        this.orgnization = orgnization;
    }
    public String getOrgnization() {
        return orgnization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}