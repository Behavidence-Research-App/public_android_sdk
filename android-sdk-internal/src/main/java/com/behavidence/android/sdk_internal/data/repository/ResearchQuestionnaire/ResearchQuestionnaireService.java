package com.behavidence.android.sdk_internal.data.repository.ResearchQuestionnaire;

import android.content.Context;
import android.content.pm.PackageManager;

import com.behavidence.android.sdk_internal.Utils.LoadAPIKey;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.ResearchQuestionnaireGroupResponse;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.Questionnaire.ResearchQuestionnaireResponse;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;
import com.behavidence.android.sdk_internal.data.repository.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResearchQuestionnaireService {

    private String apiKey;
    private String token;
    private ResearchQuestionnaireRepoImpl client;

    public ResearchQuestionnaireService(Context context) throws PackageManager.NameNotFoundException {

        apiKey = LoadAPIKey.getKey(context);
        client = RetrofitClient.getClient().create(ResearchQuestionnaireRepoImpl.class);
        token = "";

    }

    public ResearchQuestionnaireResponse getQuestionnaireSync(String code){
        try {
            return client.getQuestionnaire(apiKey, token, code).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getQuestionnaire(String code, BehavidenceResponseCallback<ResearchQuestionnaireResponse> callback){

        client.getQuestionnaire(apiKey, token, code).enqueue(new Callback<ResearchQuestionnaireResponse>() {
            @Override
            public void onResponse(Call<ResearchQuestionnaireResponse> call, Response<ResearchQuestionnaireResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResearchQuestionnaireResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });

    }

    public ResearchQuestionnaireGroupResponse getGroupQuestionnaireSync(String code){
        try {
            return client.getGroupQuestionnaire(apiKey, token, code).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getGroupQuestionnaire(String code, BehavidenceResponseCallback<ResearchQuestionnaireGroupResponse> callback){

        client.getGroupQuestionnaire(apiKey, token, code).enqueue(new Callback<ResearchQuestionnaireGroupResponse>() {
            @Override
            public void onResponse(Call<ResearchQuestionnaireGroupResponse> call, Response<ResearchQuestionnaireGroupResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResearchQuestionnaireGroupResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });

    }

}
