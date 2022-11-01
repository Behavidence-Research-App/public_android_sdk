package com.behavidence.android.sdk_internal.data.model.Events;

import com.google.gson.annotations.SerializedName;

public class ZoneInfo {

    @SerializedName("id")
    private String id;
    @SerializedName("offset")
    private int offset;
    @SerializedName("change")
    private long change;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public long getChange() {
        return change;
    }

    public void setChange(long change) {
        this.change = change;
    }
}
