package com.behavidence.android.sdk_internal.data.repository;

import android.content.Context;
import android.content.pm.PackageManager;

import com.behavidence.android.sdk_internal.data.interfaces.MHSSService;
import com.behavidence.android.sdk_internal.data.model.MHSS.MHSSResponse;

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

    public void getCurrentMHSS(BehavidenceResponseCallback<MHSSResponse> callback){
        loadAuthToken(token -> client.getCurrentMHSS(apiKey, token).enqueue(new Callback<MHSSResponse>() {
            @Override
            public void onResponse(Call<MHSSResponse> call, Response<MHSSResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MHSSResponse> call, Throwable t) {
                callback.onFailure(t);
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

    public void getMHSSHistory(BehavidenceResponseCallback<MHSSResponse> callback){
        loadAuthToken(token -> client.getMHSSHistory(apiKey, token).enqueue(new Callback<MHSSResponse>() {
            @Override
            public void onResponse(Call<MHSSResponse> call, Response<MHSSResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MHSSResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        }));
    }

}
