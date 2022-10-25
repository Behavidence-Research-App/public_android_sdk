package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;

import com.behavidence.android.sdk_internal.data.interfaces.JournalService;
import com.behavidence.android.sdk_internal.data.model.Journal.JournalData;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class JournalClient extends ClientParent {

    private JournalService _service;

    public JournalClient(Context context) {
        super(context);
        _service = services.journal();
    }

    public boolean submitJournalSync(String text, Long timeInMilli, String dateTime) {
        JournalData journal = new JournalData(dateTime, timeInMilli.toString(), text);
        List<JournalData> journalList = new ArrayList<>(Collections.singleton(journal));
        return _service.postJournalSync(journalList);
    }

    public void submitJournal(String text, Long timeInMilli, String dateTime, BehavidenceResponseCallback<Void> callback){
        JournalData journal = new JournalData(dateTime, timeInMilli.toString(), text);
        List<JournalData> journalList = new ArrayList<>(Collections.singleton(journal));
        _service.postJournal(journalList, callback);
    }

}
