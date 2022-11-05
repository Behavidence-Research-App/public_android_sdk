package com.behavidence.android.sdk_internal.domain.model.Research;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Admin;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Organization;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Participation;

public class Participation_Model implements Participation {

    Admin_Model admin;
    Organization_Model organization;
    Long participationTime;
    String code;

    public Participation_Model(Long participationTime, String code) {
        this.participationTime = participationTime;
        this.code = code;
    }

    public Admin_Model getAdminModel(){
        return admin;
    }

    public void setAdmin(Admin_Model admin) {
        this.admin = admin;
    }

    public void setOrganization(Organization_Model organization) {
        this.organization = organization;
    }

    public void setParticipationTime(Long participationTime) {
        this.participationTime = participationTime;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NonNull
    @Override
    public Admin getAdmin() {
        return admin;
    }

    @Nullable
    @Override
    public Organization getOrganization() {
        return organization;
    }

    @NonNull
    @Override
    public Long getParticipationTimeInMilliSec() {
        return participationTime;
    }

    @NonNull
    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Participation_Model{" +
                "admin=" + admin +
                ", organization=" + organization +
                ", participationTime=" + participationTime +
                ", code='" + code + '\'' +
                '}';
    }
}
