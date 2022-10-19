package com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.App;

@Dao
public interface AppsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insertList(List<App> apps);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insert(App app);

    @Query("SELECT * FROM App WHERE customCategorization!=:customCategory limit 400")
     List<App> getUnUploadedApps(int customCategory);

    @Query("SELECT * FROM App")
     List<App> getallApps();

}
