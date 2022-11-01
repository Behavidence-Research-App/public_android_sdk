package com.behavidence.android.sdk_internal.domain.model.Research.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface Organization {

    @NonNull
    String getOrganizationName();
    @Nullable
    String getOrganizationWebsite();

}
