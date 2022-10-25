package com.behavidence.android.sdk_internal.data.interfaces;

import com.behavidence.android.sdk_internal.data.model.Journal.JournalBody;
import com.behavidence.android.sdk_internal.data.model.Journal.JournalData;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface JournalService {

    boolean postJournalSync(List<JournalData> journalData);

    void postJournal(List<JournalData> journalData, BehavidenceResponseCallback<Void> callback);

}
