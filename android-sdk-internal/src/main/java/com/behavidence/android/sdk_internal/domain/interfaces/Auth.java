package com.behavidence.android.sdk_internal.domain.interfaces;

import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.domain.clients.BehavidenceCallback;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClientCallback;

public interface Auth {

    /**
     * Create an Anonymous user profile
     * @return true if successful or false if unsuccessful
     */
    boolean createAnonymousProfileSync();

    /**
     * Async create Anonymous user profile
     */
    void createAnonymousProfile(@NonNull BehavidenceCallback<Boolean> callback);

    /**
     * Logout from the user profile
     * @return
     */
    boolean logoutSync();

    /**
     * Async logout from the user profile
     * @return
     */
    void logout(@NonNull BehavidenceCallback<Boolean> callback);

    /**
     * Get the anonymous userid
     * @return UserId
     */
    String getUserId();
}
