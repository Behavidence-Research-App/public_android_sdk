package com.behavidence.android.sdk_internal.data.model.Events;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoryBody {

    @SerializedName("apps")
    private List<AppId> apps;

    public CategoryBody(List<AppId> apps){
        this.apps = apps;
    }

    public List<AppId> getApps() {
        return apps;
    }

    public void setApps(List<AppId> apps) {
        this.apps = apps;
    }
}
