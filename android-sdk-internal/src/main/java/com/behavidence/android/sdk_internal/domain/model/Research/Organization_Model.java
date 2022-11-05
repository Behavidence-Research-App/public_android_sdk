package com.behavidence.android.sdk_internal.domain.model.Research;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Organization;

public class Organization_Model implements Organization {

    String organizationName;
    String organizationWebsite;

    public Organization_Model(String organizationName, String organizationWebsite) {
        this.organizationName = organizationName;
        this.organizationWebsite = organizationWebsite;
    }

    @NonNull
    @Override
    public String getOrganizationName() {
        return organizationName;
    }

    @Nullable
    @Override
    public String getOrganizationWebsite() {
        return organizationWebsite;
    }

    @Override
    public String toString() {
        return "Organization_Model{" +
                "organizationName='" + organizationName + '\'' +
                ", organizationWebsite='" + organizationWebsite + '\'' +
                '}';
    }
}
