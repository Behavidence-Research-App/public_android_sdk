package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;

import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.data.interfaces.AuthSigninService;
import com.behavidence.android.sdk_internal.domain.interfaces.Auth;

class AuthClient_Impl extends ClientParent implements Auth {

    private final AuthSigninService _service;

    public AuthClient_Impl(Context context) {
        super(context);
        _service = services.auth();
    }

    public boolean createAnonymousProfileSync(){
        return _service.createAnonymousProfileSync();
    }

    public void createAnonymousProfile(@NonNull BehavidenceCallback<Boolean> callback){

        _service.createAnonymousProfile(new BehavidenceClientCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(String message) {
                callback.onFailure(message);
            }
        });
    }

    @Override
    public boolean logoutSync() {
        return _service.authLogoutSync();
    }

    @Override
    public void logout(@NonNull BehavidenceCallback<Boolean> callback) {
        _service.authLogout(new BehavidenceClientCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                callback.onSuccess(true);
            }

            @Override
            public void onFailure(String message) {
                callback.onFailure(message);
            }
        });
    }
}
