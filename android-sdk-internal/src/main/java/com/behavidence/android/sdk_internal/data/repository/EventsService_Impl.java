package com.behavidence.android.sdk_internal.data.repository;

import android.content.Context;
import android.util.Log;

import com.behavidence.android.sdk_internal.data.interfaces.EventsService;
import com.behavidence.android.sdk_internal.data.model.Events.AppCategoryResponse;
import com.behavidence.android.sdk_internal.data.model.Events.AppId;
import com.behavidence.android.sdk_internal.data.model.Events.CategoryBody;
import com.behavidence.android.sdk_internal.data.model.Events.Session;
import com.behavidence.android.sdk_internal.data.model.Events.SessionBody;
import com.behavidence.android.sdk_internal.data.model.Events.SessionResponse;
import com.behavidence.android.sdk_internal.data.model.Events.SessionWithZoneBody;
import com.behavidence.android.sdk_internal.data.model.Events.SessionWithZoneBodyResponse;
import com.behavidence.android.sdk_internal.data.model.Events.ZoneInfo;
import com.behavidence.android.sdk_internal.data.model.Journal.JournalBody;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClientCallback;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class EventsService_Impl extends ServiceParent implements EventsService {


    private EventsRepoImpl client;

    public EventsService_Impl(Context context){
        super(context);
        client = RetrofitClient.getClient().create(EventsRepoImpl.class);
    }

    @Override
    public AppCategoryResponse postAppCategorySync(List<AppId> apps) {
        if(loadAuthTokenSync()) {
            try {
                return client.postApps(apiKey, token, new CategoryBody(apps)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }

    @Override
    public void postAppCategory(List<AppId> apps, BehavidenceClientCallback<AppCategoryResponse> callback) {

        loadAuthToken(token -> client.postApps(apiKey, token, new CategoryBody(apps)).enqueue(new Callback<AppCategoryResponse>() {
            @Override
            public void onResponse(Call<AppCategoryResponse> call, Response<AppCategoryResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<AppCategoryResponse> call, Throwable t) {
                callback.onFailure("Failed to submit Apps");
            }
        }));
    }

    @Override
    public SessionResponse postSessionSync(List<Session> sessions) {
        Log.i("EventService", "Session Sync Called - " + sessions);
        if(loadAuthTokenSync()) {
            try {
                SessionResponse res = client.postSession(apiKey, token, new SessionBody(sessions)).execute().body();

                if(res != null && res.getData() != null){
                    JsonElement data = res.getData();
                    if(data.isJsonObject()){
                        JsonObject obj = data.getAsJsonObject();
                        if(obj.has("lasttimeupload")){
                            res.setLastTimeUploaded(obj.get("lasttimeupload").getAsLong());
                        }
                    }
                }
                Log.i("EventService", "Session Sync Success - " + res);
                return res;
            } catch (IOException e) {
                Log.e("EventService", "Session Sync Error - " + e.getMessage());
                return null;
            }
        }

        return null;
    }

    @Override
    public void postSession(List<Session> sessions, BehavidenceClientCallback<SessionResponse> callback) {
        loadAuthToken(token -> client.postSession(apiKey, token, new SessionBody(sessions)).enqueue(new Callback<SessionResponse>() {
            @Override
            public void onResponse(Call<SessionResponse> call, Response<SessionResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SessionResponse> call, Throwable t) {
                callback.onFailure("Failed to submit Sessions");
            }
        }));
    }

    @Override
    public SessionWithZoneBodyResponse postSessionSync(List<Session> sessions, List<ZoneInfo> zones) {
        if(loadAuthTokenSync()) {
            try {

                SessionWithZoneBody zone = new SessionWithZoneBody(sessions, zones);
                return client.postSession(apiKey, token, new SessionWithZoneBody(sessions, zones)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }

    @Override
    public void postSession(List<Session> sessions, List<ZoneInfo> zones, BehavidenceClientCallback<SessionWithZoneBodyResponse> callback) {
        loadAuthToken(token -> client.postSession(apiKey, token, new SessionWithZoneBody(sessions, zones)).enqueue(new Callback<SessionWithZoneBodyResponse>() {
            @Override
            public void onResponse(Call<SessionWithZoneBodyResponse> call, Response<SessionWithZoneBodyResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SessionWithZoneBodyResponse> call, Throwable t) {
                callback.onFailure("Failed to submit Sessions");
            }
        }));
    }
}
