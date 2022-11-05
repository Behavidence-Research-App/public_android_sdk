package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;


public class WorkManagerClient extends Worker {

    private EventsClient_Impl _eventsClient;
    private JournalClient_Impl _journalClient;

    public WorkManagerClient(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        _eventsClient = new EventsClient_Impl(context);
        _journalClient = new JournalClient_Impl(context);
    }

    public static void initializeWorker(Context context){
        Constraints constraints = new Constraints
                .Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest saveRequest = new PeriodicWorkRequest
                .Builder(WorkManagerClient.class, 8, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(30, TimeUnit.SECONDS)
                .build();

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "UPLOAD_WORKER",
                ExistingPeriodicWorkPolicy.KEEP,
                saveRequest
        );
    }


    @NonNull
    @Override
    public Result doWork() {

        try {
            _eventsClient.uploadAllSessions(getApplicationContext());
            _eventsClient.uploadAllApps();
            _journalClient.uploadAllJournals();

            return Result.success();
        }catch (Exception e){
            return Result.retry();
        }

    }
}
