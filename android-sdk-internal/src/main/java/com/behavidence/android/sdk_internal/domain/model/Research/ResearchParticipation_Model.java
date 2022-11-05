package com.behavidence.android.sdk_internal.domain.model.Research;

import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.ResearchParticipation;

public class ResearchParticipation_Model extends Participation_Model implements ResearchParticipation {

    String researchName;

    public ResearchParticipation_Model(Long participationTime, String code, String researchName) {
        super(participationTime, code);
        this.researchName = researchName;
    }


    @Nullable
    @Override
    public String getResearchName() {
        return researchName;
    }

    @Override
    public String toString() {
        return "ResearchParticipation_Model{" +
                "admin=" + admin +
                ", organization=" + organization +
                ", participationTime=" + participationTime +
                ", code='" + code + '\'' +
                ", researchName='" + researchName + '\'' +
                '}';
    }
}
