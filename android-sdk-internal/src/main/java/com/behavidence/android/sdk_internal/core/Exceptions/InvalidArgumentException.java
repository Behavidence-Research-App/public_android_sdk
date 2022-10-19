package com.behavidence.android.sdk_internal.core.Exceptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * when there is invalid argument from client side
 */
public class InvalidArgumentException extends BehavidenceClientException{

    public InvalidArgumentException(@NonNull String message, @Nullable String detail) {
        super(message, detail);
    }
    public InvalidArgumentException(@Nullable String message) {
        super(message==null?"No message":message, "Invalid argument provided");

    }
}
