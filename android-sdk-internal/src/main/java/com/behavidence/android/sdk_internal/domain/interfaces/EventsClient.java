package com.behavidence.android.sdk_internal.domain.interfaces;

import com.behavidence.android.sdk_internal.data.model.Events.AppCategoryResponse;
import com.behavidence.android.sdk_internal.data.model.Events.AppId;
import com.behavidence.android.sdk_internal.data.model.Events.CategoryBody;
import com.behavidence.android.sdk_internal.data.model.Events.Session;
import com.behavidence.android.sdk_internal.data.model.Events.SessionBody;
import com.behavidence.android.sdk_internal.data.model.Events.SessionResponse;
import com.behavidence.android.sdk_internal.data.model.Events.SessionWithZoneBody;
import com.behavidence.android.sdk_internal.data.model.Events.ZoneInfo;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceCallback;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClientCallback;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface EventsClient {

    boolean postAppCategorySync(List<AppId> apps);

    SessionResponse postSessionSync(List<Session> sessions);

    SessionResponse postSessionSync(List<Session> sessions, List<ZoneInfo> zones);

}
