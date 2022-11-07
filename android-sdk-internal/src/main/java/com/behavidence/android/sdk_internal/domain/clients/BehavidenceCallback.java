package com.behavidence.android.sdk_internal.domain.clients;

public interface BehavidenceCallback<T> {

    /**
     * Provides result if the call is successful
     * @param response The intended response for a call
     */
    void onSuccess(T response);

    /**
     * In case of failure of a call, this function will be called
     * @param message The failure message
     */
    void onFailure(String message);


}
