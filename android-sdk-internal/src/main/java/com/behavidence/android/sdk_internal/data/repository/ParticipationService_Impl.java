package com.behavidence.android.sdk_internal.data.repository;

import android.content.Context;
import android.content.pm.PackageManager;

import com.behavidence.android.sdk_internal.Utils.LoadAPIKey;
import com.behavidence.android.sdk_internal.data.interfaces.ParticipationService;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationDeletionReponse.ParticipationDeletionResponse;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse.ParticipationResponse;
import com.behavidence.android.sdk_internal.data.model.Participation.AssociationBody;
import com.behavidence.android.sdk_internal.data.model.Participation.AssociationResearchBody;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationBody;
import com.behavidence.android.sdk_internal.data.model.Participation.ResearchBody;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ParticipationService_Impl extends ServiceParent implements ParticipationService {

    private ParticipationRepoImpl client;

    public ParticipationService_Impl(Context context){
        super(context);
        client = RetrofitClient.getClient().create(ParticipationRepoImpl.class);
    }

    public boolean postParticipationSync(ParticipationBody participationBody){

        if(loadAuthTokenSync()) {

            try {
                if (participationBody instanceof AssociationResearchBody)
                    client.postAssociationResearch(apiKey, token, (AssociationResearchBody) participationBody).execute();
                else if (participationBody instanceof AssociationBody)
                    client.postAssociation(apiKey, token, (AssociationBody) participationBody).execute();
                else if (participationBody instanceof ResearchBody)
                    client.postResearch(apiKey, token, (ResearchBody) participationBody).execute();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }else return false;

    }

    public void postParticipation(ParticipationBody participationBody, BehavidenceResponseCallback<Void> callback){

        Callback<Void> responseCallback = new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(t);
            }
        };

        if(participationBody instanceof AssociationResearchBody)
            loadAuthToken(token -> client.postAssociationResearch(apiKey, token, (AssociationResearchBody) participationBody).enqueue(responseCallback));
        else if(participationBody instanceof AssociationBody)
            loadAuthToken(token -> client.postAssociation(apiKey, token, (AssociationBody) participationBody).enqueue(responseCallback));
        else if(participationBody instanceof ResearchBody)
            loadAuthToken(token -> client.postResearch(apiKey, token, (ResearchBody) participationBody).enqueue(responseCallback));


    }

    public ParticipationResponse getParticipationSync(){

        if(loadAuthTokenSync()) {
            try {
                return client.getAssociationResearch(apiKey, token).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void getParticipation(BehavidenceResponseCallback<ParticipationResponse> callback){

        loadAuthToken(token -> client.getAssociationResearch(apiKey, token).enqueue(new Callback<ParticipationResponse>() {
            @Override
            public void onResponse(Call<ParticipationResponse> call, Response<ParticipationResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ParticipationResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        }));


    }

    public ParticipationDeletionResponse deleteResearchSync(String adminId, String researchCode){

        if(loadAuthTokenSync()) {
            try {
                return client.deleteResearch(apiKey, token, adminId, researchCode).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void deleteResearch(String adminId, String researchCode, BehavidenceResponseCallback<ParticipationDeletionResponse> callback){

        loadAuthToken(token -> client.deleteResearch(apiKey, token, adminId, researchCode).enqueue(new Callback<ParticipationDeletionResponse>() {
            @Override
            public void onResponse(Call<ParticipationDeletionResponse> call, Response<ParticipationDeletionResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ParticipationDeletionResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        }));
    }

    public ParticipationDeletionResponse deleteAssociationSync(String adminId){
        if(loadAuthTokenSync()) {
            try {
                return client.deleteAssociation(apiKey, token, adminId).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void deleteAssociation(String adminId, BehavidenceResponseCallback<ParticipationDeletionResponse> callback){

        loadAuthToken(token -> client.deleteAssociation(apiKey, token, adminId).enqueue(new Callback<ParticipationDeletionResponse>() {
            @Override
            public void onResponse(Call<ParticipationDeletionResponse> call, Response<ParticipationDeletionResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ParticipationDeletionResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        }));

    }

}
