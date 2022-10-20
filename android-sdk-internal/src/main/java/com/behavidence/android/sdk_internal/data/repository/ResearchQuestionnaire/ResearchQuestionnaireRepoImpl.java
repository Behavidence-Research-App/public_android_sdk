package com.behavidence.android.sdk_internal.data.repository.ResearchQuestionnaire;

import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.ResearchQuestionnaireGroupResponse;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.Questionnaire.ResearchQuestionnaireResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

interface ResearchQuestionnaireRepoImpl {

    @GET("data/code")
    Call<ResearchQuestionnaireResponse> getQuestionnaire(@Header("x-api-key") String apiKey, @Header("token") String token, @Query("code") String code);

    @GET("data/code?fetch_group=true")
    Call<ResearchQuestionnaireGroupResponse> getGroupQuestionnaire(@Header("x-api-key") String apiKey, @Header("token") String token, @Query("code") String code);

}
