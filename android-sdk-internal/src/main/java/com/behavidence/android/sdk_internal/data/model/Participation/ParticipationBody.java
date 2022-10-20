package com.behavidence.android.sdk_internal.data.model.Participation;

import com.google.gson.annotations.SerializedName;

public class ParticipationBody {

    @SerializedName("code")
    private String code;
    @SerializedName("date")
    private String date;
    @SerializedName("type")
    private String type;

    public ParticipationBody(String code, String date, String type) {
        this.code = code;
        this.date = date;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

}
