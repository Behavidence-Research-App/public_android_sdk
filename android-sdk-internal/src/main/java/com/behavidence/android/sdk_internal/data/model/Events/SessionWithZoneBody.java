package com.behavidence.android.sdk_internal.data.model.Events;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SessionWithZoneBody extends SessionBody{

    @SerializedName("zone_info")
    private List<ZoneInfo> zoneInfo;

    public SessionWithZoneBody(List<Session> sessions, List<ZoneInfo> zones){
        super(sessions);
        this.zoneInfo = zones;
    }

    public List<ZoneInfo> getZoneInfo() {
        return zoneInfo;
    }

    public void setZoneInfo(List<ZoneInfo> zoneInfo) {
        this.zoneInfo = zoneInfo;
    }

    @Override
    public String toString() {
        return "SessionWithZoneBody{" +
                "events=" + events +
                ", zoneInfo=" + zoneInfo +
                '}';
    }
}
