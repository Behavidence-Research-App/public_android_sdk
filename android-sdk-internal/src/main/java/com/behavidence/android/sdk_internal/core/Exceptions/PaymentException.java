package com.behavidence.android.sdk_internal.core.Exceptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PaymentException extends BehavidenceClientException{
    public PaymentException(@NonNull String message, @Nullable String detail) {
        super(message, detail);
    }
}
