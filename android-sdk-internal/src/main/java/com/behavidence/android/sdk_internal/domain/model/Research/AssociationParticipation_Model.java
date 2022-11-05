package com.behavidence.android.sdk_internal.domain.model.Research;

import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.AssociationParticipation;

public class AssociationParticipation_Model extends Participation_Model implements AssociationParticipation {

    Long timeToLive;

    public AssociationParticipation_Model(Long participationTime, String code, Long timeToLive) {
        super(participationTime, code);
        this.timeToLive = timeToLive;
    }

    @NonNull
    @Override
    public Long getTimeToLiveInSec() {
        return timeToLive;
    }

    @Override
    public String toString() {
        return "AssociationParticipation_Model{" +
                "timeToLive=" + timeToLive +
                ", admin=" + admin +
                ", organization=" + organization +
                ", participationTime=" + participationTime +
                ", code='" + code + '\'' +
                '}';
    }
}
