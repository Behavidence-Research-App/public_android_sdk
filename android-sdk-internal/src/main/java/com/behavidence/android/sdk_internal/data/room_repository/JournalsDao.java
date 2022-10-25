package com.behavidence.android.sdk_internal.data.room_repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.behavidence.android.sdk_internal.data.room_model.Journal.GeneralEntry;

import java.util.List;

@Dao
interface JournalsDao {
    //getting all pending session which are started but not ended yet
    @Query("SELECT * FROM GeneralEntry WHERE uploaded=0 LIMIT:limit")
    List<GeneralEntry> getPendingJournals(int limit);

    @Query("SELECT * FROM GeneralEntry WHERE timeInMillisecond>=:from and timeInMillisecond<=:to")
    List<GeneralEntry> getJournals(long from,long to);

    //insertion and replace already existing element
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<GeneralEntry> generalEntries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GeneralEntry generalEntry);

    @Query("Delete FROM GeneralEntry")
    void deleteAllJournals();

    @Query("Delete FROM GeneralEntry where timeInMillisecond=:timeInMilli")
    void deleteJournal(long timeInMilli);



}
