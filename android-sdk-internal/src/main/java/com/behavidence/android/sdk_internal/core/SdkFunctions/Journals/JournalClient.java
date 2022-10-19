package com.behavidence.android.sdk_internal.core.SdkFunctions.Journals;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.TimeZoneInfo;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.TimeZoneClient;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.TimeZoneIO;
import com.behavidence.android.sdk_internal.core.Utils.Executor;

public interface JournalClient {



    /**
     *
     * @param context Context
     * @return JournalClient
     */
    @NonNull
     static JournalClient getInstance(@NonNull Context context){
        return new JournalClientImpl(context,AuthClient.getInstance(context));

    }



    /**
     * upload all journals to the server saved in local storage
     * @return Executor
     */
    @NonNull
     Executor<Void> uploadJournals();

    /**
     * save Journal in local storage
     * @param journal Journal
     */
     void saveJournal(@NonNull Journal journal);

    /**
     * save list of Journal in local storage
     * @param journals Journal
     */
     void saveJournals(@NonNull List<Journal> journals);
    @NonNull
     List<Journal> loadJournals();

    /**
     *
     * @param from time in millisecond UTC inclusive
     * @param to time in millisecond UTC inclusive
     * @return list of journals having creation time in between to and from inclusive
     */
    @NonNull
     List<Journal> loadJournals(long from,long to);


    /**
     * load journal based on creation time in millisecond UTC
     * @param creationTime of the journals
     * @return Journal
     */
    @Nullable
     Journal loadJournal(long creationTime);

    void deleteAllJournals();

    void deleteJournal(long creationTime);

}
class JournalClientImpl implements JournalClient{


    private final JournalsApiRequest journalsApiRequest;
    private final JournalsIO journalsIO;
    private final TimeZoneIO timeZoneIO;
    private final TimeZoneClient timeZoneClient;

    protected JournalClientImpl(@NonNull Context context, @NonNull AuthClient authClient) {
        journalsApiRequest =new JournalsApiRequest(authClient,ApiKey.getInstance(context));
        journalsIO=JournalsIO.getInstance(context);
        timeZoneIO=TimeZoneIO.getInstance(context);
        timeZoneClient=TimeZoneClient.getInstance();
    }


    @NonNull
    @Override
    public Executor<Void> uploadJournals() {
        return new Executor<>(this::uploadAllJournals);
    }


    @Override
    public void saveJournal(@NonNull Journal journal) {
        List<Journal> journals=new ArrayList<>();
        journals.add(journal);
        saveJournals(journals);
    }

    @Override
    public void saveJournals(@NonNull List<Journal> journals) {
        journalsIO.saveJournals(journals);
    }

    @NonNull
    @Override
    public List<Journal> loadJournals() {
        return loadJournals(0, Calendar.getInstance().getTimeInMillis());
    }

    @NonNull
    @Override
    public List<Journal> loadJournals(long from, long to) {
        return journalsIO.loadJournalsFromDiskDES(from,to);
    }


    @Nullable
    @Override
    public Journal loadJournal(long creationTime) {
        List<Journal> journals=loadJournals(creationTime,creationTime);
        if(journals.size()>0)return journals.get(0);
        else return null;
    }

    @Override
    public void deleteAllJournals() {
        journalsIO.deleteAllJournals();
    }

    @Override
    public void deleteJournal(long creationTime) {
         journalsIO.deleteJournal(creationTime);
    }


    private Void uploadAllJournals()  {
        int MAX_UPLOAD_SIZE = 100;
        List<Journal> journals = journalsIO.loadUnUploadedJournalsASC();
        List<TimeZoneInfo> timeZoneInfos=timeZoneIO.loadTimeZonesDES(200);
        if (journals.size() < 1)
            return null;
        int trimStart = 0;
        int trimEnd = Math.min(journals.size(), MAX_UPLOAD_SIZE);
        while (trimStart < trimEnd) {
            journalsApiRequest.new SaveJournalsBuilder()
                    .setJournals(journals.subList(trimStart,trimEnd)).setTimeZoneClient(timeZoneClient)
                    .setTimeZoneInfos(timeZoneInfos).buildApiCaller().callApiGetResponse();
            journalsIO.markUploaded(journals.subList(trimStart,trimEnd));
                trimStart = trimEnd;
                trimEnd = Math.min(journals.size(), (trimEnd + MAX_UPLOAD_SIZE));
            //     Log.e("asmar", "" + trimStart + "  " + trimEnd);
        }//end of while
        return null;
    }



}
