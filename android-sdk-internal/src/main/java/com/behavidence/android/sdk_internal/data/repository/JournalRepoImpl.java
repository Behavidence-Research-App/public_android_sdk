package com.behavidence.android.sdk_internal.data.repository;

import com.behavidence.android.sdk_internal.data.model.Journal.JournalBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

interface JournalRepoImpl {

    @POST("data/journals")
    Call<Void> postJournal(@Header("x-api-key") String apiKey, @Header("token") String token, @Body JournalBody body);

}
