package com.behavidence.android.sdk_internal.data.room_model.Participation;

import static androidx.room.ForeignKey.CASCADE;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;


@Entity(primaryKeys = {"adminId", "code"},
        foreignKeys = @ForeignKey(entity = AdminRoom.class,
        parentColumns = "id",
        childColumns = "adminId",
        onDelete = CASCADE)
)

public class ResearchRoom {
    @NonNull
    private final String adminId;
    private final long createdTimeStamp;
    @NonNull
    private final String code;

    @Nullable
    private String name;

    @Ignore
    public ResearchRoom(@NonNull String adminId, long createdTimeStamp, @NonNull String code) {
        this.adminId = adminId;
        this.createdTimeStamp = createdTimeStamp;
        this.code = code;
    }

    public ResearchRoom(@NonNull String adminId, long createdTimeStamp, @NonNull String code, @Nullable String name) {
        this.adminId = adminId;
        this.createdTimeStamp = createdTimeStamp;
        this.code = code;
        this.name = name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getAdminId() {
        return adminId;
    }

    public long getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Ignore
    public  void printValues(@NonNull String key){
        Log.e(key,"adminid: "+getAdminId()+"   code: "+getCode()+"  created"+ createdTimeStamp+"  name: "+getName());

    }


}
