package com.behavidence.android.sdk_internal.data.room_repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.behavidence.android.sdk_internal.data.room_model.DopaOne.MedicationDataEntity;

import java.util.List;

@Dao
public interface MedicationDataDao {
    /**
     * Inserts new medication data into the 'MedicationDataEntity' table.
     *
     * @param data The [MedicationDataEntity] object representing the medication data to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMedicationData(MedicationDataEntity data);

    /**
     * Retrieves all medication data stored in the 'MedicationDataEntity' table.
     *
     * @return A list of [MedicationDataEntity] objects containing all medication data.
     */
    @Query("SELECT * FROM MedicationDataEntity")
    List<MedicationDataEntity> getAllMedicationData();

}
