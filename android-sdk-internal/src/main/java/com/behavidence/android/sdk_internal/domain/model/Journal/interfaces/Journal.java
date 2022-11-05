package com.behavidence.android.sdk_internal.domain.model.Journal.interfaces;

import androidx.annotation.NonNull;

public interface Journal {

    @NonNull
    Long getCreationTime();

    @NonNull
    String getJournalText();


}