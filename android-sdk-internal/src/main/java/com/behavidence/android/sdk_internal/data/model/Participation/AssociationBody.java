package com.behavidence.android.sdk_internal.data.model.Participation;

import com.google.gson.annotations.SerializedName;

public class AssociationBody extends ParticipationBody {

    @SerializedName("expire_sec")
    private long expireSec;

    public AssociationBody(String code, String date, String type, long expireSec) {
        super(code, date, type);
        this.expireSec = expireSec;
    }

    public long getExpireSec() {
        return expireSec;
    }

    public void setExpireSec(long expireSec) {
        this.expireSec = expireSec;
    }
}
