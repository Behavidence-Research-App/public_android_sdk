package com.behavidence.android.sdk_internal.data.model.Auth;

import com.google.gson.annotations.SerializedName;

public class AuthLogoutBody {

    @SerializedName("token")
    public String token;

    public AuthLogoutBody(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
