package com.behavidence.android.sdk_internal.data.room_model.Participation.join;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Admin;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Organization;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Participation;

public class ResearchDetail implements Participation {
    public String adminId;
    public String organization;
    public String code;
    public String organizationalName;
    public long createdTimeStamp;
    public String name;
    public String email;

    @NonNull
    @Override
    public Admin getAdmin() {
        return null;
    }

    @Nullable
    @Override
    public Organization getOrganization() {
        return null;
    }

    @NonNull
    @Override
    public Long getParticipationTimeInMilliSec() {
        return null;
    }

    @NonNull
    @Override
    public String getCode() {
        return null;
    }
}
