package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Admin;
import com.google.gson.annotations.SerializedName;


public class QuestionnaireAdminInfo implements Admin {

    @SerializedName("name")
    String name;

    @SerializedName("middle_name")
    String middleName;

    @SerializedName("organizational_name")
    String organizationalName;

    @SerializedName("email")
    String email;

    @SerializedName("organization")
    String organization;

    @SerializedName("address")
    String address;

    @SerializedName("adminid")
    String adminid;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    @NonNull
    @Override
    public String getAdminName() {
        return name;
    }

    @NonNull
    @Override
    public String getAdminEmail() {
        return email;
    }

    @Nullable
    @Override
    public String getAdminOrganization() {
        return organization;
    }

    @Override
    public String toString() {
        return "QuestionnaireAdminInfo{" +
                "name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", organizationalName='" + organizationalName + '\'' +
                ", email='" + email + '\'' +
                ", organization='" + organization + '\'' +
                ", address='" + address + '\'' +
                ", adminid='" + adminid + '\'' +
                '}';
    }
}