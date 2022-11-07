package com.behavidence.android.sdk_internal.domain.model.Research.interfaces;

import androidx.annotation.NonNull;

public interface AssociationParticipation  extends Participation {

    /**
     *
     * @return Association remaining time
     */
    @NonNull
    Long getTimeToLiveInSec();

}
