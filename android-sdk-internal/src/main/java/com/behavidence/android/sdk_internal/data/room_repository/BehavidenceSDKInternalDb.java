package com.behavidence.android.sdk_internal.data.room_repository;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.behavidence.android.sdk_internal.data.room_model.Events.App;
import com.behavidence.android.sdk_internal.data.room_model.Events.AppSession;
import com.behavidence.android.sdk_internal.data.room_model.Events.TimeZoneInfo;
import com.behavidence.android.sdk_internal.data.room_model.Journal.GeneralEntry;
import com.behavidence.android.sdk_internal.data.room_model.MHSS.CustomLabelEntity;
import com.behavidence.android.sdk_internal.data.room_model.MHSS.LabelEntity;
import com.behavidence.android.sdk_internal.data.room_model.MHSS.Mhssroom;
import com.behavidence.android.sdk_internal.data.room_model.Participation.AdminRoom;
import com.behavidence.android.sdk_internal.data.room_model.Participation.AssociationRoom;
import com.behavidence.android.sdk_internal.data.room_model.Participation.OrganizationRoom;
import com.behavidence.android.sdk_internal.data.room_model.Participation.ResearchRoom;


@Database(entities = {GeneralEntry.class, AppSession.class, App.class, TimeZoneInfo.class, Mhssroom.class,
        AdminRoom.class, AssociationRoom.class, OrganizationRoom.class, ResearchRoom.class,
        CustomLabelEntity.class, LabelEntity.class}, version = 3)
public abstract class BehavidenceSDKInternalDb extends RoomDatabase {
        public static final String DATABASE_NAME="behavidenceSDKDb";

        //initailizing again and again is not a good practise so making instance static
        private static BehavidenceSDKInternalDb INSTANCE=null;
        public static synchronized BehavidenceSDKInternalDb getInstance(Context context) {
            try {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context, BehavidenceSDKInternalDb.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
            }catch (Exception e){
                Log.e("exception ",e.getMessage());
            }
            return INSTANCE;

        }
        public abstract AppSessionDao appSessionDao();
        public abstract AppsDao appCategoriesDao();
        public abstract TimeZoneInfoDao timeZoneInfoDao();
        public abstract JournalsDao journalsDao();
        public abstract MhssroomDao mhssroomDao();
        public abstract AssociationRoomDao associationRoomDao();

        public abstract CustomLabelDao customLabelDao();
        public abstract LabelDao labelDao();


}
