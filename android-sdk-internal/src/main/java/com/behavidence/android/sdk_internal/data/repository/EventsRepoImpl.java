package com.behavidence.android.sdk_internal.data.repository;

import com.behavidence.android.sdk_internal.data.model.Events.AppCategoryResponse;
import com.behavidence.android.sdk_internal.data.model.Events.CategoryBody;
import com.behavidence.android.sdk_internal.data.model.Events.SessionBody;
import com.behavidence.android.sdk_internal.data.model.Events.SessionResponse;
import com.behavidence.android.sdk_internal.data.model.Events.SessionWithZoneBody;
import com.behavidence.android.sdk_internal.data.model.Journal.JournalBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

interface EventsRepoImpl {

    @POST("data/app-cat")
    Call<AppCategoryResponse> postApps(@Header("x-api-key") String apiKey, @Header("token") String token, @Body CategoryBody body);

    @POST("data/events")
    Call<SessionResponse> postSession(@Header("x-api-key") String apiKey, @Header("token") String token, @Body SessionBody body);

    @POST("data/events")
    Call<SessionResponse> postSession(@Header("x-api-key") String apiKey, @Header("token") String token, @Body SessionWithZoneBody body);

}
