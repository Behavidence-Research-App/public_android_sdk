package com.behavidence.android.sdk_internal.core.Networks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ExceptionHandler {
    void handleException(@NonNull RuntimeException exception);
}
