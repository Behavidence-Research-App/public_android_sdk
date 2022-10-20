package com.behavidence.android.sdk_internal.data.repository.MHSS;

import com.behavidence.android.sdk_internal.data.model.MHSS.MHSSResponse;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.ResearchQuestionnaireGroupResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MHSSRepoImpl {

    @GET("data/mhss/android")
    Call<MHSSResponse> getCurrentMHSS(@Header("x-api-key") String apiKey, @Header("token") String token);

    @GET("data/mhss/android?history=true")
    Call<MHSSResponse> getMHSSHistory(@Header("x-api-key") String apiKey, @Header("token") String token);


}
