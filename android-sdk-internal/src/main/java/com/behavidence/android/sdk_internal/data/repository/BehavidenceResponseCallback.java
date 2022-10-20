package com.behavidence.android.sdk_internal.data.repository;

public interface BehavidenceResponseCallback<T> {

    void onSuccess(T response);

    void onFailure(Throwable t);


}
