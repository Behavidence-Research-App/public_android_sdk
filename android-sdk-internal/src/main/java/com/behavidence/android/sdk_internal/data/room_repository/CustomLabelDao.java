package com.behavidence.android.sdk_internal.data.room_repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.behavidence.android.sdk_internal.data.room_model.MHSS.CustomLabelEntity;

import java.util.List;

@Dao
interface CustomLabelDao {

    @Insert
    long insertCustomLabel(CustomLabelEntity customLabelEntity);

    @Delete
    void deleteCustomLabel(CustomLabelEntity customLabelEntity);

    @Query("DELETE FROM CustomLabelEntity WHERE (researchFId =:fid AND researchFCode=:fcode) OR (associationFId =:fid AND associationFCode=:fcode)")
    void deleteCustomLabel(String fid, String fcode);

    @Query("SELECT * FROM CustomLabelEntity")
    List<CustomLabelEntity> getCustomLabels();

    @Query("SELECT * FROM CustomLabelEntity order by timestamp desc")
    List<CustomLabelEntity> getCustomLabelInOrder();

}
