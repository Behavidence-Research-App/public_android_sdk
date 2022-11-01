package com.behavidence.android.sdk_internal.data.model.MHSS;

import com.google.gson.annotations.SerializedName;

public class ScoreLikeBody {

    @SerializedName("date")
    private String date;
    @SerializedName("like")
    private boolean like;
    @SerializedName("scoreid")
    private String scoreid;

    public ScoreLikeBody(String date, boolean like, String scoreid) {
        this.date = date;
        this.like = like;
        this.scoreid = scoreid;
    }

    public String getDate() {
        return date;
    }

    public boolean getLike() {
        return like;
    }

    public String getScoreid() {
        return scoreid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public void setScoreid(String scoreid) {
        this.scoreid = scoreid;
    }

}
