package com.behavidence.android.sdk_internal.data.model.Auth;

import com.google.gson.annotations.SerializedName;

public class AnonymousAuthRefreshBody {

    @SerializedName("userid")
    public String userId;

    @SerializedName("password")
    public String password;

    public AnonymousAuthRefreshBody(String userId, String password){
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
