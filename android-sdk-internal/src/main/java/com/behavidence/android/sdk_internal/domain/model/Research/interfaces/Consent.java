package com.behavidence.android.sdk_internal.domain.model.Research.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface Consent {

    @NonNull
    String getHtmlText();

    @Nullable
    String getText();

}
