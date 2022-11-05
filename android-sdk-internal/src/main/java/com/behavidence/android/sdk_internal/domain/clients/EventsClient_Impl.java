package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;
import android.util.Log;

import com.behavidence.android.sdk_internal.data.interfaces.EventsService;
import com.behavidence.android.sdk_internal.data.interfaces.MHSSService;
import com.behavidence.android.sdk_internal.data.model.Events.AppCategoryResponse;
import com.behavidence.android.sdk_internal.data.model.Events.AppId;
import com.behavidence.android.sdk_internal.data.model.Events.Session;
import com.behavidence.android.sdk_internal.data.model.Events.SessionResponse;
import com.behavidence.android.sdk_internal.data.model.Events.ZoneInfo;
import com.behavidence.android.sdk_internal.data.room_model.Events.App;
import com.behavidence.android.sdk_internal.data.room_model.Events.AppSession;
import com.behavidence.android.sdk_internal.data.room_model.Events.TimeZoneInfo;
import com.behavidence.android.sdk_internal.domain.interfaces.EventsClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

class EventsClient_Impl extends ClientParent implements EventsClient {

    private EventsService _service;
    private AppsIO appsIO;
    private AppSessionsIO appSessionsIO;
    private TimeZoneClient_Impl timeZoneClient;


    public EventsClient_Impl(Context context) {
        super(context);
        _service = services.events();
        appsIO = AppsIO.getInstance(context);
        appSessionsIO = AppSessionsIO.getInstance(context, appsIO);
        timeZoneClient = new TimeZoneClient_Impl(context);
    }

    @Override
    public boolean postAppCategorySync(List<AppId> apps) {
        AppCategoryResponse res = _service.postAppCategorySync(apps);
        return res != null && !res.getData().isEmpty();
    }

    @Override
    public SessionResponse postSessionSync(List<Session> sessions) {
        SessionResponse response = _service.postSessionSync(sessions);

        return response;
    }

    @Override
    public SessionResponse postSessionSync(List<Session> sessions, List<ZoneInfo> zones) {
        SessionResponse response = _service.postSessionSync(sessions, zones);
        return response;
    }

    private void refreshEvents() {
        appSessionsIO.refreshAppSessions();
    }

    public Void uploadAllSessions(Context context) {

        refreshEvents();

        int MAX_UPLOAD_SIZE = 350;

        List<AppSession> appSessions = appSessionsIO.getAppSessions(appSessionsIO.getLastTimeSessionUpload());
        List<TimeZoneInfo> uploadPendingZones = timeZoneClient.loadUploadPendingZones(timeZoneClient.getLastTimeOfUpload(context));
        List<TimeZoneInfo> allZones = timeZoneClient.loadTimeZonesDES(100);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

        List<Session> sessions = new ArrayList<>();
        List<ZoneInfo> zoneInfos = new ArrayList<>();

        for (AppSession session : appSessions) {

            TimeZoneInfo timeZoneInfo = timeZoneClient.getTimeZoneForTime(allZones, session.getEndTime());
            sdf.setTimeZone(TimeZone.getTimeZone(timeZoneInfo.getTimeZoneId()));

            sessions.add(new Session(
                    session.getStartTime(),
                    session.getEndTime(),
                    session.getPackageName(),
                    timeZoneInfo.getTimeZoneId(),
                    sdf.format(new Date(session.getStartTime())),
                    sdf.format(new Date(session.getEndTime()))
            ));
        }

        boolean timeZonesUploaded = uploadPendingZones.size() > 0;

        if (appSessions.size() < 1)
            return null;

        if (timeZonesUploaded) {
            for (TimeZoneInfo zone : allZones) {
                zoneInfos.add(new ZoneInfo(
                        zone.getTimeZoneId(),
                        zone.getTimeZoneOffset(),
                        zone.getChangedTime()
                ));
            }
        }

        int trimStart = 0;
        int trimEnd = Math.min(appSessions.size(), MAX_UPLOAD_SIZE);

        while (trimStart < trimEnd) {

            Long lastTimeUpload = null;

            if (zoneInfos.size() > 0) {
                SessionResponse response = postSessionSync(sessions, zoneInfos);
                if (response != null)
                    lastTimeUpload = response.getLastTimeUploaded();
            } else {
                SessionResponse response = postSessionSync(sessions);
                if (response != null)
                    lastTimeUpload = response.getLastTimeUploaded();
            }

            if (lastTimeUpload != null) {

                appSessionsIO.setLastTimeSessionUpload(lastTimeUpload);
                appSessions = appSessionsIO.getAppSessions(lastTimeUpload);

                if (appSessions.size() < 1)
                    return null;

                trimStart = 0;
                trimEnd = Math.min(appSessions.size(), MAX_UPLOAD_SIZE);

            } else {

                if (timeZonesUploaded) {
                    timeZoneClient.saveLastTimeOfUpload(context, uploadPendingZones.get(uploadPendingZones.size() - 1).getChangedTime());
                    timeZonesUploaded = false;
                }

                appSessionsIO.setLastTimeSessionUpload(appSessions.get(trimEnd - 1).getEndTime());
                trimStart = trimEnd;
                trimEnd = Math.min(appSessions.size(), (trimEnd + MAX_UPLOAD_SIZE));
            }
        }

        return null;


    }

    public void uploadAllApps() {
        List<App> apps = appsIO.getUnUploadedApps();
        List<AppId> appIds = new ArrayList<>();

        for(App app: apps) {
            appIds.add(new AppId(app.getPackageName()));
        }

        if(postAppCategorySync(appIds)) appsIO.markUploaded(apps);
    }

}
