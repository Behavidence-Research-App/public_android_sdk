package com.behavidence.android.sdk_internal.data.repository.Auth;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.behavidence.android.sdk_internal.Utils.AnonPasswordGenerator;
import com.behavidence.android.sdk_internal.Utils.LoadAPIKey;
import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthBody;
import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthResponse;
import com.behavidence.android.sdk_internal.data.repository.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthClient {

    public AuthClient(Context context) throws PackageManager.NameNotFoundException {

        String key = LoadAPIKey.getKey(context);
        String password = AnonPasswordGenerator.generatePassword(20);
        AuthRepoImpl client = RetrofitClient.getClient().create(AuthRepoImpl.class);

        client.createAnonProfile(key, new AnonymousAuthBody(password)).enqueue(new Callback<AnonymousAuthResponse>() {
            @Override
            public void onResponse(Call<AnonymousAuthResponse> call, Response<AnonymousAuthResponse> response) {
                Log.d("Response", response.body().getData().getAccessToken());
            }

            @Override
            public void onFailure(Call<AnonymousAuthResponse> call, Throwable t) {

            }
        });


    }

}
