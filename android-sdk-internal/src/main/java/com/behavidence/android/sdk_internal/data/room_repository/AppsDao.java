package com.behavidence.android.sdk_internal.data.room_repository;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.behavidence.android.sdk_internal.data.room_model.Events.App;

import java.util.List;

@Dao
public interface AppsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insertList(List<App> apps);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insert(App app);

    @Query("SELECT * FROM App WHERE customCategorization!=:customCategory limit 400")
     List<App> getUnUploadedApps(int customCategory);

    @Query("SELECT * FROM App")
     List<App> getallApps();

}
