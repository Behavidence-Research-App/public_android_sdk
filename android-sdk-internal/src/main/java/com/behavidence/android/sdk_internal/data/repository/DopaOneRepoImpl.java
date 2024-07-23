package com.behavidence.android.sdk_internal.data.repository;

import com.behavidence.android.sdk_internal.data.model.DopaOne.RawDataBody;
import com.behavidence.android.sdk_internal.data.model.MHSS.MHSSResponse;
import com.behavidence.android.sdk_internal.data.model.MHSS.ScoreLikeBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface DopaOneRepoImpl {

    @POST("data/events/motion")
    Call<Void> postRawData(@Header("x-api-key") String apiKey, @Header("token") String token, @Body RawDataBody rawDataBody);
}
