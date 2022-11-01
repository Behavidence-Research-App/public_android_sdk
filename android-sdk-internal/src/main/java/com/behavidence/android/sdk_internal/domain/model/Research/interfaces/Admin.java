package com.behavidence.android.sdk_internal.domain.model.Research.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface Admin {

    @NonNull
    String getAdminName();
    @NonNull
    String getAdminEmail();
    @Nullable
    String getAdminOrganization();

}
