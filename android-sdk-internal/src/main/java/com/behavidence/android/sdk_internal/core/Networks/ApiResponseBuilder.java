package com.behavidence.android.sdk_internal.core.Networks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

public interface ApiResponseBuilder<T>{
    @Nullable
    T buildResponse(@NonNull JSONObject jsonObject);
}
