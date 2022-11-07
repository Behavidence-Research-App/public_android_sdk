package com.behavidence.android.sdk_internal.domain.model.Research.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface Participation {

    /**
     *
     * @return Admin Associated with this participation
     */
    @NonNull
    Admin getAdmin();

    /**
     *
     * @return Organization associated with this participation
     */
    @Nullable
    Organization getOrganization();

    /**
     *
     * @return Time of participation
     */
    @NonNull
    Long getParticipationTimeInMilliSec();

    /**
     *
     * @return Code for this participation
     */
    @NonNull
    String getCode();
}
