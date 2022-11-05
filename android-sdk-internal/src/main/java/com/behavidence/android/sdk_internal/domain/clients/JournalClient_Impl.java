package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.data.interfaces.JournalService;
import com.behavidence.android.sdk_internal.data.model.Journal.JournalData;
import com.behavidence.android.sdk_internal.data.room_model.Events.TimeZoneInfo;
import com.behavidence.android.sdk_internal.data.room_model.Journal.GeneralEntry;
import com.behavidence.android.sdk_internal.domain.interfaces.JournalClient;
import com.behavidence.android.sdk_internal.domain.model.Journal.interfaces.Journal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class JournalClient_Impl extends ClientParent implements JournalClient {

    private JournalService _service;
    private TimeZoneClient_Impl _timeZoneClient;

    public JournalClient_Impl(Context context) {
        super(context);
        _service = services.journal();
        _timeZoneClient = new TimeZoneClient_Impl(context);
    }

    public boolean submitJournalSync(String text, Long timeInMilli, String dateTime) {
        JournalData journal = new JournalData(dateTime, timeInMilli.toString(), text);
        List<JournalData> journalList = new ArrayList<>(Collections.singleton(journal));
        return _service.postJournalSync(journalList);
    }

    public void submitJournal(String text, Long timeInMilli, String dateTime, BehavidenceCallback<Void> callback) {
        JournalData journal = new JournalData(dateTime, timeInMilli.toString(), text);
        List<JournalData> journalList = new ArrayList<>(Collections.singleton(journal));
        _service.postJournal(journalList, new BehavidenceClientCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(String message) {
                callback.onFailure(message);
            }
        });
    }

    @NonNull
    @Override
    public List<Journal> getAllJournals() {
        return getJournalInTimeRange(0L, System.currentTimeMillis());
    }

    @Override
    public List<Journal> getJournalInTimeRange(@NonNull Long startTimeInMilliUTC, @NonNull Long endTimeInMilliUTC) {

        List<GeneralEntry> generalEntries = database.journalsDao().getJournals(startTimeInMilliUTC, endTimeInMilliUTC);
        List<Journal> journals = new ArrayList<>();

        if (generalEntries != null) journals.addAll(generalEntries);

        return journals;
    }

    @Override
    public Journal getJournal(@NonNull Long timeInMilliUTC) {
        List<Journal> journals = getJournalInTimeRange(timeInMilliUTC, timeInMilliUTC);
        if (journals.isEmpty()) return null;
        return journals.get(0);
    }

    @Override
    public boolean saveJournal(@NonNull String journalText, @NonNull Long creationTimeInMilliUTC) {
        if (journalText.length() < 1 || journalText.length() > 150) {
            Log.e("BehavidenceError", "Journal length should be between 1 to 150");
            return false;
        }

        database.journalsDao().insert(new GeneralEntry(journalText, creationTimeInMilliUTC, 0));
        return true;
    }

    private void markUploaded(List<Journal> journals){
        for(Journal journal: journals)
            database.journalsDao()
                    .insert(new GeneralEntry(journal.getJournalText(), journal.getCreationTime(), GeneralEntry.UPLOADED));
    }

    public void deleteAllJournals() {
        database.journalsDao().deleteAllJournals();
    }

    public Void uploadAllJournals() {

        int MAX_UPLOAD_SIZE = 100;

        final List<Journal> journals = loadUnUploadedJournalsASC();
        List<TimeZoneInfo> timeZoneInfos = _timeZoneClient.loadTimeZonesDES(200);

        if (journals.size() < 1)
            return null;

        int trimStart = 0;
        int trimEnd = Math.min(journals.size(), MAX_UPLOAD_SIZE);

        List<JournalData> journalData = new ArrayList<>();
        List<Journal> subJournals = journals.subList(trimStart, trimEnd);

        while (trimStart < trimEnd) {

            for(Journal journal: subJournals){
                journalData.add(new JournalData(
                        _timeZoneClient.getTimeZoneForTime(timeZoneInfos,journal.getCreationTime()).getTimeZoneId(),
                        journal.getCreationTime().toString(),
                        journal.getJournalText()
                ));
            }

            if(_service.postJournalSync(journalData))
                markUploaded(journals.subList(trimStart, trimEnd));

//            _service.postJournal(journalData, new BehavidenceClientCallback<Void>() {
//
//                int start;
//                int end;
//
//                @Override
//                public void onSuccess(Void response) {
//                    markUploaded(journals.subList(start, end));
//                }
//
//                @Override
//                public void onFailure(String message) {
//
//                }
//
//                public BehavidenceClientCallback<Void> init(int start, int end){
//                    this.start = start;
//                    this.end = end;
//                    return this;
//                }
//
//            }.init(trimStart, trimEnd));

            trimStart = trimEnd;
            trimEnd = Math.min(journals.size(), (trimEnd + MAX_UPLOAD_SIZE));
            subJournals = journals.subList(trimStart, trimEnd);
        }
        return null;
    }

    @NonNull
    public List<Journal> loadUnUploadedJournalsASC() {
        List<GeneralEntry> generalEntries = database.journalsDao().getPendingJournals(100);
        List<Journal> journals = new ArrayList<>();
        if (generalEntries != null) journals.addAll(generalEntries);
        return journals;
    }

}
