package com.behavidence.android.sdk_internal.data.room_repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.behavidence.android.sdk_internal.data.room_model.MHSS.LabelEntity;

import java.util.List;

@Dao
public interface LabelDao {

    @Insert
    void insertLabel(LabelEntity labelEntity);

    @Delete
    void deleteLabel(LabelEntity labelEntity);

    @Query("SELECT * FROM LabelEntity")
    List<LabelEntity> getLabels();

    @Query("SELECT * FROM LabelEntity order by priority desc")
    List<LabelEntity> getLabelsInOrder();

    @Query("SELECT * FROM LabelEntity WHERE customLabelId IN (SELECT id FROM CustomLabelEntity ORDER BY timestamp DESC LIMIT 1)")
    List<LabelEntity> getLatestLabelsInOrder();

}
