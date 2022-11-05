package com.behavidence.android.sdk_internal.domain.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.domain.model.Journal.interfaces.Journal;

import java.util.List;

public interface JournalClient {

    @NonNull
    List<Journal> getAllJournals();


    List<Journal> getJournalInTimeRange(@NonNull Long startTimeInMilliUTC, @NonNull Long endTimeInMilliUTC);

    @Nullable
    Journal getJournal(@NonNull Long timeInMilliUTC);

    boolean saveJournal(@NonNull String journalText, @NonNull Long creationTimeInMilliUTC);
}