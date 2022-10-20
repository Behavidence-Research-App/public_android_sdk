package com.behavidence.android.sdk_internal.data.model.Auth;

import com.google.gson.annotations.SerializedName;

public class AuthResponseData {

    @SerializedName("userid")
    private String userId;

    @SerializedName("accesstoken")
    private String accessToken;

    @SerializedName("expiry")
    private Long expiry;

    public AuthResponseData(String userId, String accessToken, Long expiry) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.expiry = expiry;
    }

    public String getUserid() {
        return userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getExpiry() {
        return expiry;
    }

    public void setUserid(String userid) {
        this.userId = userid;
    }

    public void setAccessToken(String accesstoken) {
        this.accessToken = accesstoken;
    }

    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }
}
