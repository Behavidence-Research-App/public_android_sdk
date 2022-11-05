package com.behavidence.android.sdk_internal.data.room_model.Journal;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.behavidence.android.sdk_internal.domain.model.Journal.interfaces.Journal;


@Entity
public class GeneralEntry implements Journal {

    public static final int UPLOADED = 1;

    @PrimaryKey
    private final long timeInMillisecond;
    private final String entry;
    private final int uploaded;

    public GeneralEntry(String entry, long timeInMillisecond, int uploaded) {
        this.entry = entry;
        this.timeInMillisecond = timeInMillisecond;
        this.uploaded = uploaded;
    }

    public int getUploaded() {
        return uploaded;
    }

    public long getTimeInMillisecond() {
        return timeInMillisecond;
    }

    public String getEntry() {
        return entry;
    }

    @NonNull
    @Override
    public Long getCreationTime() {
        return timeInMillisecond;
    }

    @NonNull
    @Override
    public String getJournalText() {
        return entry;
    }

    @Override
    public String toString() {
        return "GeneralEntry{" +
                "timeInMillisecond=" + timeInMillisecond +
                ", entry='" + entry + '\'' +
                ", uploaded=" + uploaded +
                '}';
    }
}
