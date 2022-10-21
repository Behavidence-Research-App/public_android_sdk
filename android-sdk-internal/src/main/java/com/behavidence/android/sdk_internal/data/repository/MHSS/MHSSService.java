package com.behavidence.android.sdk_internal.data.repository.MHSS;

import android.content.Context;
import android.content.pm.PackageManager;

import com.behavidence.android.sdk_internal.Utils.LoadAPIKey;
import com.behavidence.android.sdk_internal.data.model.MHSS.MHSSResponse;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;
import com.behavidence.android.sdk_internal.data.repository.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MHSSService {

    private String apiKey;
    private String token;
    private MHSSRepoImpl client;

    public MHSSService(Context context) throws PackageManager.NameNotFoundException {

        apiKey = LoadAPIKey.getKey(context);
        client = RetrofitClient.getClient().create(MHSSRepoImpl.class);
        token = "";

    }

    public boolean getCurrentMHSSSync(){
        try {
            client.getCurrentMHSS(apiKey, token).execute();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void getCurrentMHSS(BehavidenceResponseCallback<MHSSResponse> callback){

        client.getCurrentMHSS(apiKey, token).enqueue(new Callback<MHSSResponse>() {
            @Override
            public void onResponse(Call<MHSSResponse> call, Response<MHSSResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MHSSResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public boolean getMHSSHistorySync(){
        try {
            client.getMHSSHistory(apiKey, token).execute();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void getMHSSHistory(BehavidenceResponseCallback<MHSSResponse> callback){

        client.getCurrentMHSS(apiKey, token).enqueue(new Callback<MHSSResponse>() {
            @Override
            public void onResponse(Call<MHSSResponse> call, Response<MHSSResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MHSSResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

}
