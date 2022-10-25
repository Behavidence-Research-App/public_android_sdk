package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;

import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.data.interfaces.AuthService;
import com.behavidence.android.sdk_internal.data.interfaces.AuthSigninService;
import com.behavidence.android.sdk_internal.data.interfaces.JournalService;
import com.behavidence.android.sdk_internal.data.room_repository.BehavidenceSDKInternalDb;
import com.behavidence.android.sdk_internal.domain.interfaces.Auth;

class AuthClient extends ClientParent implements Auth {

    private final AuthSigninService _service;

    public AuthClient(Context context) {
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
            public void onFailure(Boolean response, String message) {
                callback.onFailure(response, message);
            }
        });
    }

}
