package com.behavidence.android.sdk_internal.data.repository;

import android.content.Context;
import android.content.pm.PackageManager;

import com.behavidence.android.sdk_internal.Utils.AnonPasswordGenerator;
import com.behavidence.android.sdk_internal.Utils.LoadAPIKey;
import com.behavidence.android.sdk_internal.data.interfaces.AuthService;
import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthBody;
import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthRefreshBody;
import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class AuthService_Impl implements AuthService {

    private String key;
    private AuthRepoImpl client;

    public AuthService_Impl(String key) {
        this.key = key;
        client = RetrofitClient.getClient().create(AuthRepoImpl.class);
    }

    public AnonymousAuthResponse createAnonymousProfileSync(String password) {
        try {
            return client.createAnonProfile(key, new AnonymousAuthBody(password)).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void createAnonymousProfile(String password, BehavidenceResponseCallback<AnonymousAuthResponse> callback) {

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

    public AnonymousAuthResponse refreshAnonymousProfileSync(String userId, String password) {
        try {
            return client.refreshAnonProfile(key, new AnonymousAuthRefreshBody(userId, password)).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void refreshAnonymousProfile(String userId, String password, BehavidenceResponseCallback<AnonymousAuthResponse> callback) {

        client.refreshAnonProfile(key, new AnonymousAuthRefreshBody(userId, password)).enqueue(new Callback<AnonymousAuthResponse>() {
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public AuthRepoImpl getClient() {
        return client;
    }

    public void setClient(AuthRepoImpl client) {
        this.client = client;
    }
}
