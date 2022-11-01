package com.behavidence.android.sdk_internal.domain.model.MHSS;

import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.domain.model.MHSS.interfaces.Score;

public class Score_Model implements Score {


    private enum LikeState {DISLIKE, NEUTRAL, LIKE};

    private String id;
    private String scoring;
    private String title;
    private Integer score;
    private LikeState likeState = LikeState.NEUTRAL;

    private Integer priority;

    public Score_Model(String id, String scoring, String title, Integer score, Integer priority) {
        this.id = id;
        this.scoring = scoring;
        this.title = title;
        this.score = score;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    @NonNull
    @Override
    public String getScoring() {
        return scoring;
    }

    @NonNull
    @Override
    public String getScoreTitle() {
        return title;
    }

    @NonNull
    @Override
    public Integer getScoreInNumber() {
        return score;
    }

    @Override
    public void like() {
        likeState = LikeState.LIKE;
    }

    @Override
    public void disLike() {
        likeState = LikeState.DISLIKE;
    }

    @Override
    public void neutral() {
        likeState = LikeState.NEUTRAL;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getLikeState(){
        switch (likeState){
            case DISLIKE: return -1;
            case NEUTRAL: return 0;
            case LIKE: return 1;
            default: return 0;
        }
    }
}
