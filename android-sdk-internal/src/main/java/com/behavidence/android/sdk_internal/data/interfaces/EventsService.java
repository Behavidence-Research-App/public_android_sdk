package com.behavidence.android.sdk_internal.data.interfaces;

import com.behavidence.android.sdk_internal.data.model.Events.AppId;
import com.behavidence.android.sdk_internal.data.model.Events.Session;
import com.behavidence.android.sdk_internal.data.model.Events.SessionResponse;
import com.behavidence.android.sdk_internal.data.model.Events.ZoneInfo;
import com.behavidence.android.sdk_internal.data.model.Journal.JournalData;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClientCallback;

import java.util.List;

public interface EventsService {

    boolean postAppCategorySync(List<AppId> apps);

    void postAppCategory(List<AppId> apps, BehavidenceClientCallback<Void> callback);

    SessionResponse postSessionSync(List<Session> sessions);

    void postSession(List<Session> sessions, BehavidenceClientCallback<SessionResponse> callback);

    SessionResponse postSessionSync(List<Session> sessions, List<ZoneInfo> zones);

    void postSession(List<Session> sessions, List<ZoneInfo> zones, BehavidenceClientCallback<SessionResponse> callback);

}
