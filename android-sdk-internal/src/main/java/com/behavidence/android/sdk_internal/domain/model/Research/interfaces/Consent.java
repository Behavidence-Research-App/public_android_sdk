package com.behavidence.android.sdk_internal.domain.model.Research.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface Consent {

    /**
     *
     * @return Consent Text in HTML format
     */
    @NonNull
    String getHtmlText();

    /**
     *
     * @return Consent Text
     */
    @Nullable
    String getText();

}
