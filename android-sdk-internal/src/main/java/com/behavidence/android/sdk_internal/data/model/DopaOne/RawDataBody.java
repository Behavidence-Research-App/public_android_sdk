package com.behavidence.android.sdk_internal.data.model.DopaOne;

import com.behavidence.android.sdk_internal.data.model.Events.Session;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RawDataBody {


    @SerializedName("items")
    protected List<RawData> items;

    public RawDataBody(List<RawData> items){
        this.items = items;
    }

    public List<RawData> getItems() {
        return items;
    }

    public void setItems(List<RawData> items) {
        this.items = items;
    }
}
