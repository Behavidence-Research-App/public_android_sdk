package com.behavidence.android.sdk_internal.data.repository.Journal;

import android.content.Context;
import android.content.pm.PackageManager;

import com.behavidence.android.sdk_internal.Utils.LoadAPIKey;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.TimeZoneClient;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;
import com.behavidence.android.sdk_internal.data.model.Journal.JournalBody;
import com.behavidence.android.sdk_internal.data.model.Journal.JournalData;
import com.behavidence.android.sdk_internal.data.repository.RetrofitClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class JournalClient {

    private String apiKey;
    private String token;
    private JournalRepoImpl client;
    private TimeZoneClient timeZoneClient;

    public JournalClient(Context context) throws PackageManager.NameNotFoundException {

        apiKey = LoadAPIKey.getKey(context);
        client = RetrofitClient.getClient().create(JournalRepoImpl.class);
        token = "";
        timeZoneClient = TimeZoneClient.getInstance();

    }

    public boolean postJournalSync(JournalData journalData){
        try {
            client.postJournal(apiKey, token, new JournalBody(journalData)).execute();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void postJournal(JournalData journalData, BehavidenceResponseCallback<Void> callback){

        client.postJournal(apiKey, token, new JournalBody(journalData)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public boolean postJournalSync(List<JournalData> journalData){
        try {
            client.postJournal(apiKey, token, new JournalBody(journalData)).execute();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void postJournal(List<JournalData> journalData, BehavidenceResponseCallback<Void> callback){

        client.postJournal(apiKey, token, new JournalBody(journalData)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

}
