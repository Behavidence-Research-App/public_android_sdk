package com.behavidence.android.sdk_internal.core.Exceptions;


import androidx.annotation.NonNull;

/**
 * when the permission is not granted which is essential to run the SDK
 */
public class PermissionException extends  SecurityException{
    public enum PERMISSION_TYPE{
            USAGE_ACCESS
    };

    private final PERMISSION_TYPE permissionType;

   public PermissionException(@NonNull PERMISSION_TYPE type,@NonNull String message) {
        super(message);
        this.permissionType=type;
    }

    @NonNull
    public PERMISSION_TYPE getPermissionType() {
        return permissionType;
    }
}

