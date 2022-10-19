package com.behavidence.android.sdk_internal.core.SdkFunctions.Profile;

import android.content.Context;
import androidx.annotation.NonNull;
import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.Utils.Executor;


public interface UserProfileClient {
    @NonNull
    static UserProfileClient getInstance(@NonNull Context context){
        return UserProfileClientImpl.getUserProfileClientImpl(AuthClient.getInstance(context),ApiKey.getInstance(context));
    }

    @NonNull
    Executor<Void> uploadFCMToken(@NonNull String token);

}

class UserProfileClientImpl implements UserProfileClient{
    private static UserProfileClientImpl userProfileClient;
    private final AuthClient authClient;
    private ApiKey apiKey;

    @NonNull
    static synchronized UserProfileClientImpl getUserProfileClientImpl(@NonNull AuthClient authClient,@NonNull ApiKey apiKey){
        if(userProfileClient==null)
             userProfileClient=new UserProfileClientImpl(authClient,apiKey);
        else
            userProfileClient.apiKey=apiKey;
        return userProfileClient;
    }
    private UserProfileClientImpl(@NonNull AuthClient authClient, @NonNull ApiKey apiKey) {
        this.authClient = authClient;
        this.apiKey = apiKey;
    }


    @NonNull
    @Override
    public Executor<Void> uploadFCMToken(@NonNull String token) {
        return new Executor<>(new ProfileNetworkRequest(authClient,apiKey).new FCMTokenUploadBuilder(token).buildApiCaller());
    }
}