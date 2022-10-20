package com.behavidence.android.sdk_internal.data.repository.Auth;

import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthBody;
import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

interface AuthRepoImpl {


    @POST("auth/anon/register")
    Call<AnonymousAuthResponse> createAnonProfile(@Header("x-api-key") String apiKey, @Body AnonymousAuthBody body);

    @POST("auth/anon/refresh")
    Call<AnonymousAuthResponse> refreshAnonProfile(@Header("x-api-key") String apiKey, @Body AnonymousAuthBody body);

}
