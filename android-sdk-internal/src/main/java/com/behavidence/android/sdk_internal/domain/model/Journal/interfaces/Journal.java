package com.behavidence.android.sdk_internal.domain.model.Journal.interfaces;

import androidx.annotation.NonNull;

public interface Journal {

    /***
     *
     * @return Time in UTC Milliseconds of the entry
     */
    @NonNull
    Long getCreationTime();

    /**
     *
     * @return Text of journal entry
     */
    @NonNull
    String getJournalText();


}