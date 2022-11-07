package com.behavidence.android.sdk_internal.domain.model.Research.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface Admin {

    /**
     *
     * @return Admin Name
     */
    @NonNull
    String getAdminName();

    /**
     *
     * @return Admin Email
     */
    @NonNull
    String getAdminEmail();

    /**
     *
     * @return Admin Organization Name
     */
    @Nullable
    String getAdminOrganization();

}
