package com.behavidence.android.sdk_internal.data.model.Auth;

import com.google.gson.annotations.SerializedName;


public class AnonymousAuthResponse {

    @SerializedName("data")
    AuthResponseData DataObject;

    public AuthResponseData getData() {
        return DataObject;
    }

    public void setData(AuthResponseData dataObject) {
        this.DataObject = dataObject;
    }
}

