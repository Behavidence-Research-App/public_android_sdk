package com.behavidence.android.sdk_internal.data.interfaces;

import android.util.Log;

import com.behavidence.android.sdk_internal.Utils.AnonPasswordGenerator;
import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthResponse;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClientCallback;

public interface AuthSigninService {

    boolean createAnonymousProfileSync();

    void createAnonymousProfile(BehavidenceClientCallback<Boolean> callback);

}
