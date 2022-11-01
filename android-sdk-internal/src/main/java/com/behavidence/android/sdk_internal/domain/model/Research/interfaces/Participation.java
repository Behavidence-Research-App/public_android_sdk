package com.behavidence.android.sdk_internal.domain.model.Research.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface Participation {
    @NonNull
    Admin getAdmin();
    @Nullable
    Organization getOrganization();
    @NonNull
    Long getParticipationTimeInMilliSec();
    @NonNull
    String getCode();
}
