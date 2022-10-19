package com.behavidence.android.sdk_internal.core.SdkFunctions.Events;

import android.content.Context;
import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.BehavidenceSDK;
import com.behavidence.android.sdk_internal.core.RoomDb.BehavidenceSDKInternalDb;
import com.behavidence.android.sdk_internal.core.Utils.Executor;

public interface EventsClient {

    /**
     * it will automatically get the Auth from local storage for performing network calls
     * default configuration will be used see @ApiConfig
     * @param context Context
     * @return EventsClient for events related functionalities
     */
    @NonNull
    static EventsClient getInstance(@NonNull Context context){
        return new EventsClientImpl(context);
    }


    /**
     *uploads all the data to the server
     * @return Executor
     */
    @NonNull
    Executor<Void> uploadEvents();

    /**
     * required to be call when device is unlocked (Start screen of the app or App class) on api level 30 and above
     * else auto upload will not work properly on on api level 30 and above
     * @return Executor
     */
    @NonNull
    Executor<Void> refreshEvents();

}

class EventsClientImpl implements EventsClient{

    private final Context context;
    public EventsClientImpl(@NonNull Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public Executor<Void> uploadEvents() {
        return new Executor<>(() -> {
            refreshEvents().execute();
            AppSessionsClient.getInstance(context)
                    .uploadAppSessions().execute();
            AppsClient.getInstance(context)
                    .uploadAppIds().execute();
            return null;
        });

    }
    @NonNull
    @Override
    public Executor<Void> refreshEvents() {
        return new Executor<>(() -> {
           AppSessionsIO.getInstance(context,AppsIO.getInstance(context))
                    .refreshAppSessions();
           return null;
        });
    }

}
