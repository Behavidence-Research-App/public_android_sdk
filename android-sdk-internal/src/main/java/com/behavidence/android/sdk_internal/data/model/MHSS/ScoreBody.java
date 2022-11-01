package com.behavidence.android.sdk_internal.data.model.MHSS;

import com.google.gson.annotations.SerializedName;

public class ScoreBody {

    @SerializedName("id")
    String id;

    @SerializedName("score")
    Integer score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
