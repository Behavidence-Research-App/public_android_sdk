package com.behavidence.android.sdk_internal.core.SdkFunctions.Events;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.List;
import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.AppSession;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.TimeZoneInfo;
import com.behavidence.android.sdk_internal.core.Utils.Executor;


interface AppSessionsClient {

     @NonNull
    static AppSessionsClient getInstance(@NonNull Context context){
        return new AppSessionClientImpl(context,AuthClient.getInstance(context), ApiKey.getInstance(context));
    }


    @NonNull
    Executor<Void> uploadAppSessions();

}

class AppSessionClientImpl  implements AppSessionsClient {

    private final SessionsNetworkRequest sessionsNetworkRequest;
    private final AppSessionsIO appSessionsIO;
    private final TimeZoneClient timeZoneClient;
    private final TimeZoneIO timeZoneIO;

    public AppSessionClientImpl(@NonNull Context context, @NonNull AuthClient authClient, @NonNull ApiKey apiKey) {
         sessionsNetworkRequest =new SessionsNetworkRequest(authClient,apiKey);
         AppsIO appsIO = AppsIO.getInstance(context);
         appSessionsIO=AppSessionsIO.getInstance(context, appsIO);
         timeZoneClient=TimeZoneClient.getInstance();
         timeZoneIO=TimeZoneIO.getInstance(context);
    }

    @NonNull
    @Override
    public Executor<Void> uploadAppSessions() {
        return new Executor<>(this::uploadAllSessions);
    }


    private Void uploadAllSessions(){

        int MAX_UPLOAD_SIZE = 350;
        List<AppSession> appSessions = appSessionsIO.getAppSessions(appSessionsIO.getLastTimeSessionUpload());
        List<TimeZoneInfo> uploadPendingZones = timeZoneIO.loadUploadPendingZones(timeZoneIO.getLastTimeOfUpload());
        List<TimeZoneInfo> allZones = timeZoneIO.loadTimeZonesDES(100);
        boolean timeZonesUploaded = uploadPendingZones.size() > 0;
        if (appSessions.size() < 1)
            return null;
        int trimStart = 0;
        int trimEnd = Math.min(appSessions.size(), MAX_UPLOAD_SIZE);
        while (trimStart < trimEnd) {
            Long lastTimeUpload = sessionsNetworkRequest.new SessionsUploadBuilder().setAppSessions(appSessions.subList(trimStart,trimEnd))
                    .setTimeZonesToUpload(timeZonesUploaded?uploadPendingZones:null)
                    .setTimeZoneClient(timeZoneClient)
                    .setTimeZoneInfos(allZones)
                    .buildApiCaller().callApiGetResponse();
            if (lastTimeUpload != null) {
                appSessionsIO.setLastTimeSessionUpload(lastTimeUpload);
                appSessions = appSessionsIO.getAppSessions(lastTimeUpload);
                if (appSessions.size() < 1)
                    return null;
                trimStart = 0;
                trimEnd = Math.min(appSessions.size(), MAX_UPLOAD_SIZE);
            } else {
                //if time zone information has been fetched
                if (timeZonesUploaded) {
                    timeZoneIO.saveLastTimeOfUpload(uploadPendingZones.get(uploadPendingZones.size() - 1).getChangedTime());
                    timeZonesUploaded=false;
                }

                appSessionsIO.setLastTimeSessionUpload(appSessions.get(trimEnd - 1).getEndTime());
                trimStart = trimEnd;
                trimEnd = Math.min(appSessions.size(), (trimEnd + MAX_UPLOAD_SIZE));
            }
            //     Log.e("asmar", "" + trimStart + "  " + trimEnd);
        }//end of while

        return null;


    }

}
