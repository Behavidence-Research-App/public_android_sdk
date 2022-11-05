package com.behavidence.android.sdk_internal.data.model.Events;

import com.google.gson.annotations.SerializedName;

import java.util.TimeZone;

public class Session {

    @SerializedName("start")
    private long start;
    @SerializedName("end")
    private long end;
    @SerializedName("id")
    private String id;
    @SerializedName("zone")
    private String zone;
    @SerializedName("startuser")
    private String startUser;
    @SerializedName("enduser")
    private String endUser;

    public Session(long start, long end, String id, String zone, String startUser, String endUser) {
        this.start = start;
        this.end = end;
        this.id = id;
        this.zone = zone;
        this.startUser = startUser;
        this.endUser = endUser;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getStartUser() {
        return startUser;
    }

    public void setStartUser(String startUser) {
        this.startUser = startUser;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }
}
