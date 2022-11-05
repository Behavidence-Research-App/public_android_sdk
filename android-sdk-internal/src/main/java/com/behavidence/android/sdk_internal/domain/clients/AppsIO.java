package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;

import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.data.room_model.Events.App;

import java.util.ArrayList;
import java.util.List;

interface AppsIO {

    static AppsIO getInstance(@NonNull Context context) {
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

class AppsIOImpl extends ClientParent implements AppsIO {

    private final Context context;

    public AppsIOImpl(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public List<App> getAllApps() {
        List<App> apps = database.appCategoriesDao().getallApps();
        if (apps == null)
            apps = new ArrayList<>();
        return apps;

    }

    @NonNull
    @Override
    public List<App> getUnUploadedApps() {

        List<App> apps = database.appCategoriesDao().getUnUploadedApps(App.UPLOADED);
        if (apps == null)
            apps = new ArrayList<>();
        return apps;

    }

    @Override
    public void putApps(@NonNull List<App> apps) {
        database.appCategoriesDao().insertList(apps);
    }

    @Override
    public void putApp(@NonNull App app) {
        database.appCategoriesDao().insert(app);
    }

    @Override
    public void deleteALlApps() {
        database.appCategoriesDao().getallApps();
    }

    @Override
    public void markUploaded(@NonNull List<App> apps) {
        for (int i = 0; i < apps.size(); i++)
            apps.get(i).setCustomCategorization(App.UPLOADED);
        putApps(apps);
    }
}