package com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse;

import com.google.gson.annotations.SerializedName;


public class Association {

    @SerializedName("expire_sec")
    long expireSec;

    @SerializedName("created")
    long created;

    @SerializedName("last_updated")
    long updatedTime;

    @SerializedName("code")
    String code;

    @SerializedName("custom_label")
    CustomLabel customLabel;


    public void setExpireSec(long expireSec) {
        this.expireSec = expireSec;
    }

    public long getExpireSec() {
        return expireSec;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getCreated() {
        return created;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public CustomLabel getCustomLabel() {
        return customLabel;
    }

    public void setCustomLabel(CustomLabel customLabel) {
        this.customLabel = customLabel;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }
}