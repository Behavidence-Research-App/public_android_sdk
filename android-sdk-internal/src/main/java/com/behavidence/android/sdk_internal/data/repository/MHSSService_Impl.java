package com.behavidence.android.sdk_internal.data.repository;

import android.content.Context;
import android.content.pm.PackageManager;

import com.behavidence.android.sdk_internal.data.interfaces.MHSSService;
import com.behavidence.android.sdk_internal.data.model.MHSS.MHSSResponse;
import com.behavidence.android.sdk_internal.data.model.MHSS.ScoreLikeBody;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClientCallback;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MHSSService_Impl extends ServiceParent implements MHSSService {

    private MHSSRepoImpl client;

    public MHSSService_Impl(Context context){
        super(context);
        client = RetrofitClient.getClient().create(MHSSRepoImpl.class);
    }

    public MHSSResponse getCurrentMHSSSync(){
        if(loadAuthTokenSync()) {
            try {
                return client.getCurrentMHSS(apiKey, token).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public void getCurrentMHSS(BehavidenceClientCallback<MHSSResponse> callback){
        loadAuthToken(token -> client.getCurrentMHSS(apiKey, token).enqueue(new Callback<MHSSResponse>() {
            @Override
            public void onResponse(Call<MHSSResponse> call, Response<MHSSResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MHSSResponse> call, Throwable t) {
                callback.onFailure("Failed to load current MHSS");
            }
        }));
    }

    public MHSSResponse getMHSSHistorySync(){
        if(loadAuthTokenSync()) {
            try {
                return client.getMHSSHistory(apiKey, token).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public void getMHSSHistory(BehavidenceClientCallback<MHSSResponse> callback){
        loadAuthToken(token -> client.getMHSSHistory(apiKey, token).enqueue(new Callback<MHSSResponse>() {
            @Override
            public void onResponse(Call<MHSSResponse> call, Response<MHSSResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MHSSResponse> call, Throwable t) {
                callback.onFailure("Failed to load MHSS History");
            }
        }));
    }

    public Boolean putScoreLikeSync(ScoreLikeBody scoreLikeBody){
        if(loadAuthTokenSync()) {
            try {
                client.putScoreLike(apiKey, token, scoreLikeBody).execute();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public void putScoreLike(ScoreLikeBody scoreLikeBody, BehavidenceClientCallback<Boolean> callback){
        loadAuthToken(token -> client.putScoreLike(apiKey, token, scoreLikeBody).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.onSuccess(true);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure("Failed to change score status");
            }
        }));
    }

}
