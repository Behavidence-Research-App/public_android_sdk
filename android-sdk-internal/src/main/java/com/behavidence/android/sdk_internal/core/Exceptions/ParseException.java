package com.behavidence.android.sdk_internal.core.Exceptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 *
 * when there is parse error while parsing response
 * This exception does not occur normally
 */
public class ParseException extends BehavidenceClientException{
    public ParseException(@NonNull String message, @Nullable String detail) {
        super(message,"This excpetion does not occur normally. Please report:" +detail);

    }
    public ParseException(@Nullable String detail) {
        super("Exception while parsing:","This excpetion does not occur normally. Please report:" +detail);
    }
}
