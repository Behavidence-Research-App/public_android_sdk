package com.behavidence.android.sdk_internal.core.SdkFunctions.Association.AssociationManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.OrganizationRoom;

public interface Organization {
    @NonNull
     String getOrganizationName();

    @Nullable
     String getWebsite();


}

class OrganizationImpl implements Organization{

    private final OrganizationRoom organizationRoom;

    public OrganizationImpl(@Nullable OrganizationRoom organizationRoom) {
        this.organizationRoom = organizationRoom;
    }

    @NonNull
    @Override
    public String getOrganizationName() {
        if (organizationRoom != null) {
            return organizationRoom.getOrganizationName();
        }
        return null;
    }
    @Override
    @Nullable
    public String getWebsite() {
        if (organizationRoom != null) {
            return organizationRoom.getWebsite();
        }
        return null;
    }
}
