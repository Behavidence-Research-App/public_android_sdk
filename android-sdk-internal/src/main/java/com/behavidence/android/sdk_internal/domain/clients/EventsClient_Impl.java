package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;

import com.behavidence.android.sdk_internal.data.interfaces.EventsService;
import com.behavidence.android.sdk_internal.data.interfaces.MHSSService;
import com.behavidence.android.sdk_internal.data.model.Events.AppId;
import com.behavidence.android.sdk_internal.data.model.Events.Session;
import com.behavidence.android.sdk_internal.data.model.Events.SessionResponse;
import com.behavidence.android.sdk_internal.data.model.Events.ZoneInfo;
import com.behavidence.android.sdk_internal.domain.interfaces.EventsClient;

import java.util.List;

public class EventsClient_Impl extends ClientParent implements EventsClient {

    private EventsService _service;

    public EventsClient_Impl(Context context) {
        super(context);
        _service = services.events();
    }

    @Override
    public void postAppCategory(List<AppId> apps, BehavidenceCallback<Void> callback) {

        _service.postAppCategory(apps, new BehavidenceClientCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(String message) {
                callback.onFailure(message);
            }
        });

    }

    @Override
    public void postSession(List<Session> sessions, BehavidenceCallback<SessionResponse> callback) {

        _service.postSession(sessions, new BehavidenceClientCallback<SessionResponse>() {
            @Override
            public void onSuccess(SessionResponse response) {
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(String message) {
                callback.onFailure(message);
            }
        });

    }

    @Override
    public void postSession(List<Session> sessions, List<ZoneInfo> zones, BehavidenceCallback<SessionResponse> callback) {
        _service.postSession(sessions, zones, new BehavidenceClientCallback<SessionResponse>() {
            @Override
            public void onSuccess(SessionResponse response) {
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(String message) {
                callback.onFailure(message);
            }
        });
    }
}
