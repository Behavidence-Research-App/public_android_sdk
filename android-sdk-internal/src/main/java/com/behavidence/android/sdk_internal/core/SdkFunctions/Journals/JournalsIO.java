package com.behavidence.android.sdk_internal.core.SdkFunctions.Journals;

import android.content.Context;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import com.behavidence.android.sdk_internal.core.RoomDb.BehavidenceSDKInternalDb;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Journals.Room.GeneralEntry;


  interface JournalsIO {

     static JournalsIO getInstance(@NonNull Context context){
       return new JournalsIOImpl(context);
    }

     void markUploaded(@NonNull List<Journal> journals);
     void saveJournals(@NonNull List<Journal> journals);
   @NonNull
     List<Journal> loadJournalsFromDiskDES(long from,long to);

    @NonNull
     List<Journal> loadUnUploadedJournalsASC();

    void deleteAllJournals();

    void deleteJournal(long creationTime);

}
class JournalsIOImpl implements JournalsIO{

    private final Context context;
    public JournalsIOImpl(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public void markUploaded(@NonNull List<Journal> entries) {
        BehavidenceSDKInternalDb.getInstance(context).journalsDao().insert(GeneralEntry.convertToRoomData(entries,GeneralEntry.UPLOADED));
    }

    @Override
    public void saveJournals(@NonNull List<Journal> entries) {
        BehavidenceSDKInternalDb.getInstance(context).journalsDao().insert(GeneralEntry.convertToRoomData(entries,0));
    }

    @NonNull
    @Override
    public List<Journal> loadJournalsFromDiskDES(long from, long to) {

        List<GeneralEntry> generalEntries= BehavidenceSDKInternalDb.getInstance(context).journalsDao().getJournals(from,to);
        if(generalEntries==null) generalEntries=new ArrayList<>();
        return GeneralEntry.convertFromRoomData(generalEntries);
    }


    @NonNull
    public List<Journal> loadUnUploadedJournalsASC(){
        List<GeneralEntry> generalEntries= BehavidenceSDKInternalDb.getInstance(context).journalsDao().getPendingJournals(100);
        if(generalEntries==null) generalEntries=new ArrayList<>();
        return GeneralEntry.convertFromRoomData(generalEntries);
    }

    @Override
    public void deleteAllJournals() {
        BehavidenceSDKInternalDb.getInstance(context).journalsDao()
                .deleteAllJournals();
    }

    @Override
    public void deleteJournal(long creationTime) {
        BehavidenceSDKInternalDb.getInstance(context)
                .journalsDao().deleteJournal(creationTime);
    }
}
