package com.behavidence.android.sdk_internal.domain.model.Research.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public interface CodeInfo {

    /**
     * Get Organization Information
     * @return Organization Object
     */
    @Nullable
    Organization getOrganization();

    /**
     * Get Admin Information
     * @return Admin Object
     */
    @NonNull
    Admin getAdmin();

    /**
     * Get code of the association or research
     * @return Code
     */
    @NonNull
    String getCode();

    /**
     * Consent for the association or research
     * @return Consent Object
     */
    @Nullable
    Consent getConsent();

    /**
     * Set Association Time Duration for an Association (Note: The Code entered should be of type ASSOCIATION or RESEARCH_ASSOCIATION)
     * @param associationTimeInSec Time in UTC Milliseconds for Association Duration
     */
    void setAssociationTimeInSec(@NonNull Long associationTimeInSec);

    /**
     * Get Research Questions for the entered Code (Note: The Code entered should be of type RESEARCH or RESEARCH_ASSOCIATION)
     * @return Group of different questions
     */
    List<QuestionGroup> getQuestionGroups();

}
