package com.behavidence.android.sdk_internal.data.repository;

import android.content.Context;

import com.behavidence.android.sdk_internal.data.interfaces.EventsService;
import com.behavidence.android.sdk_internal.data.model.Events.AppId;
import com.behavidence.android.sdk_internal.data.model.Events.CategoryBody;
import com.behavidence.android.sdk_internal.data.model.Events.Session;
import com.behavidence.android.sdk_internal.data.model.Events.SessionBody;
import com.behavidence.android.sdk_internal.data.model.Events.SessionResponse;
import com.behavidence.android.sdk_internal.data.model.Events.SessionWithZoneBody;
import com.behavidence.android.sdk_internal.data.model.Events.ZoneInfo;
import com.behavidence.android.sdk_internal.data.model.Journal.JournalBody;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClientCallback;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsService_Impl extends ServiceParent implements EventsService {


    private EventsRepoImpl client;

    public EventsService_Impl(Context context){
        super(context);
        client = RetrofitClient.getClient().create(EventsRepoImpl.class);
    }

    @Override
    public boolean postAppCategorySync(List<AppId> apps) {
        if(loadAuthTokenSync()) {
            try {
                client.postApps(apiKey, token, new CategoryBody(apps)).execute();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    @Override
    public void postAppCategory(List<AppId> apps, BehavidenceClientCallback<Void> callback) {

        loadAuthToken(token -> client.postApps(apiKey, token, new CategoryBody(apps)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure("Failed to submit Apps");
            }
        }));
    }

    @Override
    public SessionResponse postSessionSync(List<Session> sessions) {
        if(loadAuthTokenSync()) {
            try {
                return client.postSession(apiKey, token, new SessionBody(sessions)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
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
    public SessionResponse postSessionSync(List<Session> sessions, List<ZoneInfo> zones) {
        if(loadAuthTokenSync()) {
            try {
                return client.postSession(apiKey, token, new SessionWithZoneBody(sessions, zones)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }

    @Override
    public void postSession(List<Session> sessions, List<ZoneInfo> zones, BehavidenceClientCallback<SessionResponse> callback) {
        loadAuthToken(token -> client.postSession(apiKey, token, new SessionWithZoneBody(sessions, zones)).enqueue(new Callback<SessionResponse>() {
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
}
