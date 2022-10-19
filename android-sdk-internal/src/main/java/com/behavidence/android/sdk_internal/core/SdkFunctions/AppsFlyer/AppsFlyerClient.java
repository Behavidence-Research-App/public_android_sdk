package com.behavidence.android.sdk_internal.core.SdkFunctions.AppsFlyer;


import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.Utils.Executor;

import java.util.Map;

public interface AppsFlyerClient {

    static AppsFlyerClient getInstance(@NonNull AuthClient authClient, @NonNull ApiKey apiKey) {
        return new AppsFlyerClientImpl(authClient, apiKey);
    }

    /**
     * Get Research code after submitting data from AppsFlyerSDK
     *
     * @return code
     */
    Executor<String> getCode(@NonNull Map<String, Object> params);
}

class AppsFlyerClientImpl implements AppsFlyerClient {

    private final AppsFlyerApiRequest appsFlyerApiRequest;

    public AppsFlyerClientImpl(@NonNull AuthClient authClient, @NonNull ApiKey apiKey){
        appsFlyerApiRequest = new AppsFlyerApiRequest(authClient, apiKey);
    }

    @Override
    public Executor<String> getCode(@NonNull Map<String, Object> params) {
        return new Executor<>(appsFlyerApiRequest. new GetCodeBuilder(params).buildApiCaller());
    }



}
