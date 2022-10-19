package com.behavidence.android.sdk_internal.core.Exceptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * shows the problem on the client side
 */
public class BehavidenceClientException extends RuntimeException{

    private  final String detail;
    public BehavidenceClientException(@NonNull String message,@Nullable String detail) {
        super(message);
        this.detail=detail;
    }

    @Nullable
    public String getDetail() {
        return detail;
    }
}
