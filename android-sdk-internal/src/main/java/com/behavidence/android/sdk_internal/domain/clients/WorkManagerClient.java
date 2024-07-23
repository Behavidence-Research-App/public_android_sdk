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
    private DopaOneClient_Impl _dopaOneClient;

    public WorkManagerClient(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        _eventsClient = new EventsClient_Impl(context);
        _journalClient = new JournalClient_Impl(context);
        _dopaOneClient = new DopaOneClient_Impl(context);
    }

    public static void initializeWorker(Context context){
        Constraints constraints = new Constraints
                .Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest saveRequest = new PeriodicWorkRequest
                .Builder(WorkManagerClient.class, 8, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(60, TimeUnit.SECONDS)
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

            Log.i("BehavidenceWorkManager", "Executed");

            _eventsClient.uploadAllSessions(getApplicationContext());
            _eventsClient.uploadAllApps();
            _journalClient.uploadAllJournals();
            _dopaOneClient.fetchRawEventsToSendAndPost(getApplicationContext());

            return Result.success();
        }catch (Exception e){
//            e.printStackTrace();
            Log.i("BehavidenceWorkManager", "Exception -" + e.getLocalizedMessage());

            return Result.retry();
        }

    }

    public static void manualWork(Context context){
        try {

            Log.i("BehavidenceWorkManager", "Executed");

            EventsClient_Impl _eventsClient = new EventsClient_Impl(context);
            JournalClient_Impl _journalClient = new JournalClient_Impl(context);
            DopaOneClient_Impl _dopaOneClient = new DopaOneClient_Impl(context);

            _eventsClient.uploadAllSessions(context);
            _eventsClient.uploadAllApps();
            _journalClient.uploadAllJournals();
            _dopaOneClient.fetchRawEventsToSendAndPost(context);

        }catch (Exception e){
            Log.i("BehavidenceWorkManager", "Exception -" + e.getLocalizedMessage());
        }
    }
}
