package com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.ResearchRoom;

import java.util.List;

@Dao
public interface CustomLabelDao {

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
