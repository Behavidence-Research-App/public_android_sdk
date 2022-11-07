package com.behavidence.android.sdk_internal.domain.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.domain.model.Journal.interfaces.Journal;

import java.util.List;

public interface JournalClient {

    /***
     * Fetch all the saved Journals
     * @return List of all the saved journals
     */
    @NonNull
    List<Journal> getAllJournals();


    /***
     *
     * Fetch Journals between provided time range
     * @param startTimeInMilliUTC The start of the time range in UTC Milliseconds
     * @param endTimeInMilliUTC The end of the time range in UTC Milliseconds
     * @return List of journals in the time range
     */
    List<Journal> getJournalInTimeRange(@NonNull Long startTimeInMilliUTC, @NonNull Long endTimeInMilliUTC);

    /***
     *
     * Fetch Journal that it saved at a specified time
     * @return A single journal saved at provided time, null if there are no journals saved at that time
     */
    @Nullable
    Journal getJournal(@NonNull Long timeInMilliUTC);

    /***
     * Save Journal Entry
     * @param journalText The text for journal entry. The length should be less than 150
     * @param creationTimeInMilliUTC Time UTC Milliseconds at which the journal was created
     * @return true if journal is successfully saved
     */
    boolean saveJournal(@NonNull String journalText, @NonNull Long creationTimeInMilliUTC);
}
