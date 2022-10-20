package com.behavidence.android.sdk_internal.data.model.Journal;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JournalBody {

    @SerializedName("events")
    ArrayList<JournalData> events = new ArrayList<>();

    public JournalBody(ArrayList<JournalData> data) {
        this.events = data;
    }

    public JournalBody(JournalData data){
        this.events.add(data);
    }

    public void addEvent(JournalData data){
        this.events.add(data);
    }

    public ArrayList<JournalData> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<JournalData> data) {
        this.events = data;
    }
}
