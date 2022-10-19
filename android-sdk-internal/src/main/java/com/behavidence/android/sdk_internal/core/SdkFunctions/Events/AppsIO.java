package com.behavidence.android.sdk_internal.core.SdkFunctions.Events;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import com.behavidence.android.sdk_internal.core.RoomDb.BehavidenceSDKInternalDb;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.App;

 interface AppsIO {

     static AppsIO getInstance(@NonNull Context context){
        return new AppsIOImpl(context);
    }


    @NonNull
     List<App> getAllApps();

    @NonNull
     List<App> getUnUploadedApps();

     void putApps(@NonNull List<App> apps);

     void putApp(@NonNull App app);

     void deleteALlApps();

     void markUploaded(@NonNull List<App> apps);
}

class AppsIOImpl implements AppsIO{

    private final Context context;

    public AppsIOImpl(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public List<App> getAllApps() {
       List<App> apps= BehavidenceSDKInternalDb.getInstance(context)
                .appCategoriesDao().getallApps();
       if(apps==null)
            apps= new ArrayList<>();
       return apps;

    }

    @NonNull
    @Override
    public List<App> getUnUploadedApps() {

        List<App> apps= BehavidenceSDKInternalDb.getInstance(context)
                .appCategoriesDao().getUnUploadedApps(App.UPLOADED);

        if(apps==null)
            apps= new ArrayList<>();
        return apps;

    }

    @Override
    public void putApps(@NonNull List<App> apps) {
        BehavidenceSDKInternalDb.getInstance(context)
                .appCategoriesDao().insertList(apps);
    }

    @Override
    public void putApp(@NonNull App app) {
        BehavidenceSDKInternalDb.getInstance(context)
                .appCategoriesDao().insert(app);
    }

    @Override
    public void deleteALlApps() {
        BehavidenceSDKInternalDb.getInstance(context)
                .appCategoriesDao().getallApps();
    }

    @Override
    public void markUploaded(@NonNull List<App> apps){
        for(int i=0;i<apps.size();i++)
            apps.get(i).setCustomCategorization(App.UPLOADED);
        putApps(apps);
    }
}
