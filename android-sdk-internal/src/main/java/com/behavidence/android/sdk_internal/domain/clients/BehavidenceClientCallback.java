package com.behavidence.android.sdk_internal.domain.clients;

public interface BehavidenceClientCallback<T> {

    void onSuccess(T response);

    void onFailure(String message);


}
