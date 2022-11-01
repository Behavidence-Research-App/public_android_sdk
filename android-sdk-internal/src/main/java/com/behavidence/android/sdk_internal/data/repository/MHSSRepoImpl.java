package com.behavidence.android.sdk_internal.data.repository;

import com.behavidence.android.sdk_internal.data.model.MHSS.MHSSResponse;
import com.behavidence.android.sdk_internal.data.model.MHSS.ScoreLikeBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;

interface MHSSRepoImpl {

    @GET("data/mhss/android")
    Call<MHSSResponse> getCurrentMHSS(@Header("x-api-key") String apiKey, @Header("token") String token);

    @GET("data/mhss/android?history=true")
    Call<MHSSResponse> getMHSSHistory(@Header("x-api-key") String apiKey, @Header("token") String token);

    @PUT("data/mhss/android")
    Call<Void> putScoreLike(@Header("x-api-key") String apiKey, @Header("token") String token, @Body ScoreLikeBody scoreLikeBody);


}
