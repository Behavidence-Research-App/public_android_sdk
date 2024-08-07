package com.behavidence.android.sdk_internal.data.interfaces;

import com.behavidence.android.sdk_internal.data.model.Events.AppCategoryResponse;
import com.behavidence.android.sdk_internal.data.model.Events.AppId;
import com.behavidence.android.sdk_internal.data.model.Events.Session;
import com.behavidence.android.sdk_internal.data.model.Events.SessionResponse;
import com.behavidence.android.sdk_internal.data.model.Events.SessionWithZoneBody;
import com.behavidence.android.sdk_internal.data.model.Events.SessionWithZoneBodyResponse;
import com.behavidence.android.sdk_internal.data.model.Events.ZoneInfo;
import com.behavidence.android.sdk_internal.data.model.Journal.JournalData;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClientCallback;

import java.util.List;

public interface EventsService {

    AppCategoryResponse postAppCategorySync(List<AppId> apps);

    void postAppCategory(List<AppId> apps, BehavidenceClientCallback<AppCategoryResponse> callback);

    SessionResponse postSessionSync(List<Session> sessions);

    void postSession(List<Session> sessions, BehavidenceClientCallback<SessionResponse> callback);

    SessionWithZoneBodyResponse postSessionSync(List<Session> sessions, List<ZoneInfo> zones);

    void postSession(List<Session> sessions, List<ZoneInfo> zones, BehavidenceClientCallback<SessionWithZoneBodyResponse> callback);

}
