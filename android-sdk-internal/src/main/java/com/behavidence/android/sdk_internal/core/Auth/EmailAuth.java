package com.behavidence.android.sdk_internal.core.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;

public interface EmailAuth extends BehavidenceAuth {
    @Nullable
    Boolean isUserRegistered();
    @Nullable
    String getAccessToken();
    @Nullable
    String getRefreshToken();
    long getAccessTokenTTL();
    long getRefreshTokenTTL();
}

class EmailAuthImpl implements EmailAuth{

    private final String accessToken;
    private final Boolean isRegistered;
    private final String refreshToken;
    private final long accessTokenTTL;
    private final long refreshTokenTTL;



    public EmailAuthImpl(@Nullable String accessToken, @NonNull String refreshToken, long accessTokenTTL, long refreshTokenTTL,Boolean isRegistered) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenTTL = accessTokenTTL;
        this.refreshTokenTTL = refreshTokenTTL;
        this.isRegistered=isRegistered;
    }

    @Nullable
    @Override
    public String getToken() {
        return getAccessToken();
    }

    @Nullable
    @Override
    public Boolean isUserRegistered() {
        return isRegistered;
    }

    @Nullable
    @Override
    public String getAccessToken() {
        if(Calendar.getInstance().getTimeInMillis()<accessTokenTTL)
            return accessToken;
        return null;
    }

    @Nullable
    @Override
    public String getRefreshToken() {
        if(Calendar.getInstance().getTimeInMillis()<refreshTokenTTL)
            return refreshToken;
        return null;
    }

    @Override
    public long getAccessTokenTTL() {
        return accessTokenTTL;
    }

    @Override
    public long getRefreshTokenTTL() {
        return refreshTokenTTL;
    }
}
