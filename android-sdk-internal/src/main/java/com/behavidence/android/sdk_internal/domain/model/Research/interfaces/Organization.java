package com.behavidence.android.sdk_internal.domain.model.Research.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface Organization {

    /**
     *
     * @return Organization Name
     */
    @NonNull
    String getOrganizationName();

    /**
     *
     * @return Organization Website
     */
    @Nullable
    String getOrganizationWebsite();

}
