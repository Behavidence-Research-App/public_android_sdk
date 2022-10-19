package com.behavidence.android.sdk_internal.core.SdkFunctions.Events;

import android.content.Context;
import androidx.annotation.NonNull;

import java.util.List;

import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.App;
import com.behavidence.android.sdk_internal.core.Utils.Executor;

interface AppsClient {

     @NonNull
    static AppsClient getInstance(@NonNull Context context){
    return new AppsClientImpl(context,AuthClient.getInstance(context), ApiKey.getInstance(context));
    }
    @NonNull
    Executor<Void> uploadAppIds();

}

class AppsClientImpl  implements AppsClient {

    private final AppsIO appsIO;
    private  final AppsApiRequest appsApiRequest;
    public AppsClientImpl(@NonNull Context context,@NonNull AuthClient authClient, @NonNull ApiKey apiKey) {
      appsApiRequest =new AppsApiRequest(authClient,apiKey);
      appsIO= AppsIO.getInstance(context);
    }

    @NonNull
    @Override
    public Executor<Void> uploadAppIds() {
        return new Executor<>(() -> {
            List<App> apps=appsIO.getUnUploadedApps();
            appsApiRequest.new UploadAppsBuilder(apps)
                    .buildApiCaller().callApiGetResponse();
            appsIO.markUploaded(apps);
            return null;
        });
    }

}
