package com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room;


import static androidx.room.ForeignKey.CASCADE;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

@Entity(primaryKeys = {"adminId", "code"},
        foreignKeys = @ForeignKey(entity = AdminRoom.class,
        parentColumns = "id",
        childColumns = "adminId",
        onDelete = CASCADE

)
)

public class AssociationRoom {

    @NonNull
    private final String adminId;
    private final long createdTimeStamp;
    private long expireSeconds;
    private long updatedTimeStamp;
    @NonNull
    private final String code;

    public AssociationRoom(@NonNull String adminId, long createdTimeStamp, long updatedTimeStamp, long expireSeconds, @NonNull String code) {
        this.adminId = adminId;
        this.createdTimeStamp = createdTimeStamp;
        this.updatedTimeStamp = updatedTimeStamp;
        this.code = code;
        this.expireSeconds=expireSeconds;
    }

    @Ignore
    public AssociationRoom(@NonNull String adminId, long createdTimeStamp, @NonNull String code, long expireTimeStamp) {
        this.adminId = adminId;
        this.createdTimeStamp = createdTimeStamp;
        this.code = code;
        this.expireSeconds=expireTimeStamp;
    }

    public long getExpireSeconds() {
        return expireSeconds;
    }

    @Ignore
    public void setExpireSeconds(long expireTimeStamp) {
        this.expireSeconds = expireTimeStamp;
    }

    @Ignore
    public void setUpdatedTimeStamp(long updatedTimeStamp) {
        this.updatedTimeStamp = updatedTimeStamp;
    }

    @NonNull
    public String getAdminId() {
        return adminId;
    }

    public long getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public long getUpdatedTimeStamp() {
        return updatedTimeStamp;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    @Ignore
    public  void printValues(@NonNull String key){
        Log.e(key,"adminid: "+getAdminId()+"   code: "+getCode()+"  created"+ createdTimeStamp+"  updated: "+updatedTimeStamp+"  expiry: "+expireSeconds);

    }
}
