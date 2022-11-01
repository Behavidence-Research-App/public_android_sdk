package com.behavidence.android.sdk_internal.data.model.Auth;

import com.google.gson.annotations.SerializedName;

public class AuthLogoutResponse {

    @SerializedName("data")
    AuthLogoutData DataObject;

    public AuthLogoutData getData() {
        return DataObject;
    }

    public void setData(AuthLogoutData dataObject) {
        this.DataObject = dataObject;
    }
}
