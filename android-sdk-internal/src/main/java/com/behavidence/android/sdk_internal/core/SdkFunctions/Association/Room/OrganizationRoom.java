package com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class OrganizationRoom {

    @PrimaryKey
    @NonNull
    private final String organizationName;
    private String website;

    @Ignore
    public OrganizationRoom(@NonNull String organizationName) {
        this.organizationName = organizationName;
    }

    public OrganizationRoom(@NonNull String organizationName, @Nullable String website) {
        this.organizationName = organizationName;
        this.website = website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @NonNull
    public String getOrganizationName() {
        return organizationName;
    }

    public String getWebsite() {
        return website;
    }

    @Ignore
    public  void printValues(@NonNull String key){
        Log.e(key,"oragnization: "+getOrganizationName()+"   website: "+website);

    }
}
