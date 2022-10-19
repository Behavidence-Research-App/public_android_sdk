package com.behavidence.android.sdk_internal.core.Exceptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * When there is unauthorized response form the server this exception will be generated
 */
public class AuthException extends BehavidenceClientException {
    public AuthException(@NonNull String message, @Nullable String detail){
        super(message,detail);
    }
}
