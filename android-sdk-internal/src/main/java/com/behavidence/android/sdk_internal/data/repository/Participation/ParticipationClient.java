package com.behavidence.android.sdk_internal.data.repository.Participation;

import android.content.Context;
import android.content.pm.PackageManager;

import com.behavidence.android.sdk_internal.Utils.LoadAPIKey;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse.ParticipationResponse;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;
import com.behavidence.android.sdk_internal.data.model.Participation.AssociationBody;
import com.behavidence.android.sdk_internal.data.model.Participation.AssociationResearchBody;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationBody;
import com.behavidence.android.sdk_internal.data.model.Participation.ResearchBody;
import com.behavidence.android.sdk_internal.data.repository.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticipationClient {

    private String apiKey;
    private String token;
    private ParticipationRepoImpl client;

    public ParticipationClient(Context context) throws PackageManager.NameNotFoundException {

        apiKey = LoadAPIKey.getKey(context);
        client = RetrofitClient.getClient().create(ParticipationRepoImpl.class);
        token = "eyJraWQiOiJMYytJdWgrMXhVeTVyR25wdWlXYWwwQUtpSlFIVTdTcmF6V1ZWQ1QzM3lRPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI3ZWU1Yjc4NS1jZTgxLTQ0ZDItOWM5OS04MTEwNmRhM2JkNDEiLCJldmVudF9pZCI6ImM1NjFlODY1LTI4ZjgtNDU3Yy1iYzM1LTg2NzZjODI2MTU3NyIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE2NjYyNzM3MDEsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX1lwalNTeVNpYiIsImV4cCI6MTY2NjI3NzMwMSwiaWF0IjoxNjY2MjczNzAxLCJqdGkiOiJhZjQzZjhlMS02MjI1LTQ4MjYtYjU0OS1jNTM0MzhmNmJjM2QiLCJjbGllbnRfaWQiOiI0aGRsZHNocHVvcDNqaThqaTdzZGI2MHFxYyIsInVzZXJuYW1lIjoidThkODI5MDAxOTc3NzJjMjIzZTFjYjk4MGE0OWYyYjNhYWJjOGVmM2IifQ.L0Af-Miwbp6XlZP-YaqeXEUTNkzln1Fq-9UojXpD212JVGvduQtG0cLFwUARQuZI7R2VGJ63IghoWWFHYYgUsNQe5KNe4SkYARWKlIYgZReoU61p1BzJgOJi4S8FDifRCb4ZDK7Jds8EZmHEsI03JcCqz06lguaLSejBW_V3oa7qQci4Wud9fxgrTV5IfohhMy-a7oV1SX9GzfT5EVIKuCKxppZysNzNumuM62BWUzCXxI_2jEjCKW3vYv_I0cT5XOyUYkrH6jx4r9SZlQIp9n_LHzYf5VxuES50yzG4M7TRm_xmmlgjzvsuGtHtRkhPJ93MIHC8smwQXPBQVi2WwA";

    }

    public boolean postParticipationSync(ParticipationBody participationBody){

        try {
            if(participationBody instanceof AssociationResearchBody)
                client.postAssociationResearch(apiKey, token, (AssociationResearchBody) participationBody).execute();
            else if(participationBody instanceof AssociationBody)
                client.postAssociation(apiKey, token, (AssociationBody) participationBody).execute();
            else if(participationBody instanceof ResearchBody)
                client.postResearch(apiKey, token, (ResearchBody) participationBody).execute();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

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
            client.postAssociationResearch(apiKey, token, (AssociationResearchBody) participationBody).enqueue(responseCallback);
        else if(participationBody instanceof AssociationBody)
            client.postAssociation(apiKey, token, (AssociationBody) participationBody).enqueue(responseCallback);
        else if(participationBody instanceof ResearchBody)
            client.postResearch(apiKey, token, (ResearchBody) participationBody).enqueue(responseCallback);

    }

    public ParticipationResponse getParticipationSync(){

        try {
            return client.getAssociationResearch(apiKey, token).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void getParticipation(BehavidenceResponseCallback<ParticipationResponse> callback){
        client.getAssociationResearch(apiKey, token).enqueue(new Callback<ParticipationResponse>() {
            @Override
            public void onResponse(Call<ParticipationResponse> call, Response<ParticipationResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ParticipationResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

}
