package com.behavidence.android.sdk_internal.data.repository;

import com.behavidence.android.sdk_internal.data.model.Participation.AssociationBody;
import com.behavidence.android.sdk_internal.data.model.Participation.AssociationResearchBody;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationDeletionReponse.ParticipationDeletionResponse;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse.ParticipationResponse;
import com.behavidence.android.sdk_internal.data.model.Participation.ResearchBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface ParticipationRepoImpl {

    @POST("data/code/participate")
    Call<Void> postAssociation(@Header("x-api-key") String apiKey, @Header("token") String token, @Body AssociationBody body);

    @POST("data/code/participate")
    Call<Void> postResearch(@Header("x-api-key") String apiKey, @Header("token") String token, @Body ResearchBody body);

    @POST("data/code/participate")
    Call<Void> postAssociationResearch(@Header("x-api-key") String apiKey, @Header("token") String token, @Body AssociationResearchBody body);

    @GET("data/code/participate")
    Call<ParticipationResponse> getAssociationResearch(@Header("x-api-key") String apiKey, @Header("token") String token);

    @DELETE("data/research")
    Call<ParticipationDeletionResponse> deleteResearch(@Header("x-api-key") String apiKey, @Header("token") String token,
                                                       @Query("adminid") String adminId, @Query("code") String code);

    @DELETE("data/association")
    Call<ParticipationDeletionResponse> deleteAssociation(@Header("x-api-key") String apiKey, @Header("token") String token,
                                               @Query("adminid") String adminId);

}
