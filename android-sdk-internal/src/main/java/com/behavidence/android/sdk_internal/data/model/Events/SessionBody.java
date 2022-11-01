package com.behavidence.android.sdk_internal.data.model.Events;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SessionBody {

    @SerializedName("events")
    protected List<Session> events;

    public SessionBody(List<Session> events){
        this.events = events;
    }

    public List<Session> getEvents() {
        return events;
    }

    public void setEvents(List<Session> events) {
        this.events = events;
    }
}
