package com.behavidence.android.sdk_internal.domain.model.Research.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public interface CodeInfo {

    @Nullable
    Organization getOrganization();

    @NonNull
    Admin getAdmin();

    @NonNull
    String getCode();

    @Nullable
    Consent getConsent();

    void setAssociationTimeInSec(@NonNull Long associationTimeInSec);

    List<QuestionGroup> getQuestionGroups();

}
