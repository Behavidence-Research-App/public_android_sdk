package com.behavidence.android.sdk_internal.core.Exceptions;



/**
 * generated when there is a error from Server side
 */
public class BehavidenceServiceException extends RuntimeException {
    private final int statusCode;

    public BehavidenceServiceException(int code, String message) {
        super(message);
        this.statusCode=code;
    }
    public int getStatusCode(){
        return statusCode;
    }

}
