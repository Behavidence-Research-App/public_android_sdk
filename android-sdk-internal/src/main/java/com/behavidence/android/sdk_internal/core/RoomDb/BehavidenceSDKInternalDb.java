package com.behavidence.android.sdk_internal.core.RoomDb;

import android.content.Context;
import android.util.Log;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.AdminRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.AssociationRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.AssociationRoomDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.OrganizationRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.ResearchRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.CustomLabelDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.CustomLabelEntity;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.LabelDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.LabelEntity;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.AppSessionDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.AppsDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Graphs.Room.AppTime;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Graphs.Room.GraphsDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Journals.Room.JournalsDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.TimeZoneInfoDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.App;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.AppSession;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Journals.Room.GeneralEntry;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.TimeZoneInfo;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Notifications.Room.NotificationRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Notifications.Room.NotificationRoomDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.SimilarityScores.Room.Mhssroom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.SimilarityScores.Room.MhssroomDao;

@Database(entities = {GeneralEntry.class, AppSession.class, App.class, TimeZoneInfo.class, Mhssroom.class,
        AdminRoom.class, AssociationRoom.class, OrganizationRoom.class, ResearchRoom.class, AppTime.class,
        NotificationRoom.class, CustomLabelEntity.class, LabelEntity.class

}, version = 3)
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
        public abstract GraphsDao graphsDao();
        public abstract AppsDao appCategoriesDao();
        public abstract TimeZoneInfoDao timeZoneInfoDao();
        public abstract JournalsDao journalsDao();
        public abstract MhssroomDao mhssroomDao();
        public abstract AssociationRoomDao associationRoomDao();
        public abstract NotificationRoomDao notificationRoomDao();

        public abstract CustomLabelDao customLabelDao();
        public abstract LabelDao labelDao();


}
