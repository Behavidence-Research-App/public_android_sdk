package com.behavidence.android.sdk_internal.domain.interfaces;

import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.domain.clients.BehavidenceCallback;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClientCallback;

public interface Auth {

    boolean createAnonymousProfileSync();

    void createAnonymousProfile(@NonNull BehavidenceCallback<Boolean> callback);

    boolean logoutSync();

    void logout(@NonNull BehavidenceCallback<Boolean> callback);

    String getUserId();
}
