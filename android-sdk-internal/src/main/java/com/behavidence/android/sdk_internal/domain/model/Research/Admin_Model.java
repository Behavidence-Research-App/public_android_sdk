package com.behavidence.android.sdk_internal.domain.model.Research;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Admin;

public class Admin_Model implements Admin {

    String id;
    String adminName;
    String adminEmail;
    String adminOrganization;

    public Admin_Model(String adminId, String adminName, String adminEmail, String adminOrganization) {
        this.id = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminOrganization = adminOrganization;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public void setAdminOrganization(String adminOrganization) {
        this.adminOrganization = adminOrganization;
    }

    @NonNull
    @Override
    public String getAdminName() {
        return adminName;
    }

    @NonNull
    @Override
    public String getAdminEmail() {
        return adminEmail;
    }

    @Nullable
    @Override
    public String getAdminOrganization() {
        return adminOrganization;
    }

    @Override
    public String toString() {
        return "Admin_Model{" +
                "id='" + id + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminEmail='" + adminEmail + '\'' +
                ", adminOrganization='" + adminOrganization + '\'' +
                '}';
    }
}
