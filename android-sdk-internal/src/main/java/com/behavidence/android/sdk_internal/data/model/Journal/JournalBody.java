package com.behavidence.android.sdk_internal.data.model.Journal;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class JournalBody {

    @SerializedName("events")
    List<JournalData> events = new ArrayList<>();

    public JournalBody(List<JournalData> data) {
        this.events = data;
    }

    public JournalBody(JournalData data){
        this.events.add(data);
    }

    public void addEvent(JournalData data){
        this.events.add(data);
    }

    public List<JournalData> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<JournalData> data) {
        this.events = data;
    }
}
