package com.behavidence.android.sdk_internal.data.repository;

import android.content.Context;

import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.TimeZoneClient;
import com.behavidence.android.sdk_internal.data.interfaces.JournalService;
import com.behavidence.android.sdk_internal.data.model.Journal.JournalBody;
import com.behavidence.android.sdk_internal.data.model.Journal.JournalData;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClientCallback;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class JournalService_Impl extends ServiceParent implements JournalService {

    private JournalRepoImpl client;

    public JournalService_Impl(Context context){
        super(context);

        client = RetrofitClient.getClient().create(JournalRepoImpl.class);
    }

    public boolean postJournalSync(List<JournalData> journalData){
        if(loadAuthTokenSync()) {
            try {
                client.postJournal(apiKey, token, new JournalBody(journalData)).execute();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    public void postJournal(List<JournalData> journalData, BehavidenceClientCallback<Void> callback){

        loadAuthToken(token -> client.postJournal(apiKey, token, new JournalBody(journalData)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure("Failed to submit Journals");
            }
        }));
    }

}
