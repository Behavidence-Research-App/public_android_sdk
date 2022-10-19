package com.behavidence.android.sdk_internal.core.Auth;


import androidx.annotation.Nullable;

@FunctionalInterface
public interface BehavidenceAuth{

    /**
     * get access token to authenticate API calls
     * @return access token if not expired else return null
     */
    @Nullable
     String getToken();
}
