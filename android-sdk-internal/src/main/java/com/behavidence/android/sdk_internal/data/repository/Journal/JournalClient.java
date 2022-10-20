package com.behavidence.android.sdk_internal.data.repository.Journal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.behavidence.android.sdk_internal.Utils.AnonPasswordGenerator;
import com.behavidence.android.sdk_internal.Utils.LoadAPIKey;
import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthBody;
import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthResponse;
import com.behavidence.android.sdk_internal.data.repository.Auth.AuthRepoImpl;
import com.behavidence.android.sdk_internal.data.repository.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JournalClient {

    private String apiKey;
    private String token;
    private JournalRepoImpl client;

    public JournalClient(Context context) throws PackageManager.NameNotFoundException {

        apiKey = LoadAPIKey.getKey(context);
        client = RetrofitClient.getClient().create(JournalRepoImpl.class);

    }

    public boolean postJournal(){
        return false;
    }

}
