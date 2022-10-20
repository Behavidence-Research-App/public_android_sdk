package com.behavidence.android.sdk_internal.data.repository.Journal;

import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JournalRepoImpl {

    @POST("data/journals")
    Call<Void> postJournal(@Header("x-api-key") String apiKey, @Header("token") String token, @Body AnonymousAuthBody body);

}
