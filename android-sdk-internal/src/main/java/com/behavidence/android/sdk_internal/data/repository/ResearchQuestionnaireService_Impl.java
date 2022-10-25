package com.behavidence.android.sdk_internal.data.repository;

import android.content.Context;
import android.content.pm.PackageManager;

import com.behavidence.android.sdk_internal.Utils.LoadAPIKey;
import com.behavidence.android.sdk_internal.data.interfaces.ResearchQuestionnaireService;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.ResearchQuestionnaireGroupResponse;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.Questionnaire.ResearchQuestionnaireResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ResearchQuestionnaireService_Impl extends ServiceParent implements ResearchQuestionnaireService {

    private ResearchQuestionnaireRepoImpl client;

    public ResearchQuestionnaireService_Impl(Context context){
        super(context);
        client = RetrofitClient.getClient().create(ResearchQuestionnaireRepoImpl.class);
    }

    public ResearchQuestionnaireResponse getQuestionnaireSync(String code){

        if(loadAuthTokenSync()) {
            try {
                return client.getQuestionnaire(apiKey, token, code).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void getQuestionnaire(String code, BehavidenceResponseCallback<ResearchQuestionnaireResponse> callback){

        loadAuthToken(token -> client.getQuestionnaire(apiKey, token, code).enqueue(new Callback<ResearchQuestionnaireResponse>() {
            @Override
            public void onResponse(Call<ResearchQuestionnaireResponse> call, Response<ResearchQuestionnaireResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResearchQuestionnaireResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        }));

    }

    public ResearchQuestionnaireGroupResponse getGroupQuestionnaireSync(String code){
        if(loadAuthTokenSync()) {
            try {
                return client.getGroupQuestionnaire(apiKey, token, code).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void getGroupQuestionnaire(String code, BehavidenceResponseCallback<ResearchQuestionnaireGroupResponse> callback){

        loadAuthToken(token -> client.getGroupQuestionnaire(apiKey, token, code).enqueue(new Callback<ResearchQuestionnaireGroupResponse>() {
            @Override
            public void onResponse(Call<ResearchQuestionnaireGroupResponse> call, Response<ResearchQuestionnaireGroupResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResearchQuestionnaireGroupResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        }));

    }

}
