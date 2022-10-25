package com.behavidence.android.sdk_internal.data.room_model.Journal;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.behavidence.android.sdk_internal.core.SdkFunctions.Journals.Journal;

import java.util.ArrayList;
import java.util.List;


@Entity
public class GeneralEntry {
    @Ignore
    public static final int UPLOADED=1;
    private final String entry;
    @PrimaryKey
    private final long timeInMillisecond;
    private final int uploaded;

    public GeneralEntry(String entry, long timeInMillisecond, int uploaded){
        this.entry=entry;
        this.timeInMillisecond=timeInMillisecond;
        this.uploaded=uploaded;
    }

    public String getEntry() {
        return entry;
    }

    public long getTimeInMillisecond() {
        return timeInMillisecond;
    }

    public int getUploaded() {
        return uploaded;
    }

    @Ignore
    public static GeneralEntry convertToRoomData(@NonNull Journal journal, int uploaded){
      return   new GeneralEntry(journal.getJournalTxt(), journal.getTimeStampMillisecond(), uploaded);
    }

    @Ignore
    public static Journal convertFromRoomData(@NonNull GeneralEntry entry){
        return new Journal(entry.getEntry(),entry.getTimeInMillisecond());
    }


    @Ignore
    public static List<GeneralEntry> convertToRoomData(@NonNull List<Journal> journals,int uploaded){
        List<GeneralEntry> generalEntries=new ArrayList<>();
        for(Journal journal:journals)
            generalEntries.add(convertToRoomData(journal,uploaded));
        return generalEntries;
    }

    @Ignore
    public static List<Journal> convertFromRoomData(@NonNull List<GeneralEntry> generalEntries){
        List<Journal> journals=new ArrayList<>();
        for(GeneralEntry generalEntry:generalEntries)
            journals.add(convertFromRoomData(generalEntry));
        return journals;
    }
}
