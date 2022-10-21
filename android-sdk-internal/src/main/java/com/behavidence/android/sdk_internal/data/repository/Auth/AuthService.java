package com.behavidence.android.sdk_internal.data.repository.Auth;

import android.content.Context;
import android.content.pm.PackageManager;

import com.behavidence.android.sdk_internal.Utils.AnonPasswordGenerator;
import com.behavidence.android.sdk_internal.Utils.LoadAPIKey;
import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthBody;
import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthResponse;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;
import com.behavidence.android.sdk_internal.data.repository.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class AuthService {

    private String key;
    private AuthRepoImpl client;

    public AuthService(Context context) throws PackageManager.NameNotFoundException {

        key = LoadAPIKey.getKey(context);
        client = RetrofitClient.getClient().create(AuthRepoImpl.class);

    }

    public AnonymousAuthResponse createAnonymousProfileSync(){
        String password = AnonPasswordGenerator.generatePassword(20);
        try {
            return client.createAnonProfile(key, new AnonymousAuthBody(password)).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void createAnonymousProfile(BehavidenceResponseCallback<AnonymousAuthResponse> callback){
        String password = AnonPasswordGenerator.generatePassword(20);

        client.createAnonProfile(key, new AnonymousAuthBody(password)).enqueue(new Callback<AnonymousAuthResponse>() {
            @Override
            public void onResponse(Call<AnonymousAuthResponse> call, Response<AnonymousAuthResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<AnonymousAuthResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public AnonymousAuthResponse refreshAnonymousProfileSync(){
//        try {
//            return client.createAnonProfile(key, new AnonymousAuthBody(password)).execute().body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
        return null;
    }

    public void refreshAnonymousProfile(BehavidenceResponseCallback<AnonymousAuthResponse> callback){

//        client.createAnonProfile(key, new AnonymousAuthBody(password)).enqueue(new Callback<AnonymousAuthResponse>() {
//            @Override
//            public void onResponse(Call<AnonymousAuthResponse> call, Response<AnonymousAuthResponse> response) {
//                callback.onSuccess(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<AnonymousAuthResponse> call, Throwable t) {
//                callback.onFailure(t);
//            }
//        });
    }

}
