package com.behavidence.android.sdk_internal.core.SdkFunctions.Association.AssociationManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.AdminRoom;


public interface Admin {

    @NonNull
     String getEmail();

    @Nullable
     String getAddress();

    @Nullable
     String getOrganization();

    @NonNull
    public String getId();

    @Nullable
    public String getName();
    @Nullable
    public String getMiddleName();

    @NonNull
    public String getOrganizationalName();


}
class AdminImpl implements Admin{

    private final AdminRoom adminRoom;

    public AdminImpl(@NonNull AdminRoom adminRoom) {
        this.adminRoom = adminRoom;
    }

    @NonNull
    @Override
    public String getEmail() {
        return adminRoom.getEmail();
    }

    @Nullable
    @Override
    public String getAddress() {
        return adminRoom.getEmail();
    }

    @Nullable
    @Override
    public String getOrganization() {
        return adminRoom.getOrganization();
    }

    @NonNull
    @Override
    public String getId() {
        return adminRoom.getId();
    }

    @Nullable
    @Override
    public String getName() {
        return adminRoom.getName();
    }

    @Nullable
    @Override
    public String getMiddleName() {
        return adminRoom.getMiddleName();
    }

    @NonNull
    @Override
    public String getOrganizationalName() {
        return adminRoom.getOrganizationalName();
    }
}
