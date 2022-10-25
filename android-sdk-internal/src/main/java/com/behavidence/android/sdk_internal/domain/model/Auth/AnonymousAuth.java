package com.behavidence.android.sdk_internal.domain.model.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.Calendar;

public class AnonymousAuth {

    private  String accessToken;
    private  String id;
    private  String passCode;
    private  long accessTokenTTL;

    AnonymousAuth(@Nullable String accessToken, @Nullable String id, @Nullable String passCode, long accessTokenTTL) {
        this.accessToken = accessToken;
        this.id = id;
        this.passCode = passCode;
        setSessionTTL(accessTokenTTL);
    }

    @NonNull
    AnonymousAuth setSessionToken(@Nullable String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @NonNull
    AnonymousAuth setId(@Nullable String id) {
        this.id = id;
        return this;
    }

    @NonNull
    AnonymousAuth setPassCode(@Nullable String passCode) {
        this.passCode = passCode;
        return this;
    }

    @NonNull
    AnonymousAuth setSessionTTL(long accessTokenTTL) {
        if(accessTokenTTL>-1)
            this.accessTokenTTL = accessTokenTTL;
        return this;
    }


    private boolean isAccessTokenExpired(){
        return Calendar.getInstance().getTimeInMillis()>accessTokenTTL;
    }

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getPassCode() {
        return passCode;
    }

    @Nullable
    public String getAccessToken() {
        if(isAccessTokenExpired())
            return null;
        return accessToken;
    }

    public long getAccessTokenTTL() {
        return accessTokenTTL;
    }

    @Nullable
    public String getToken() {
        if(isAccessTokenExpired())
            return null;
        return accessToken;
    }

}
