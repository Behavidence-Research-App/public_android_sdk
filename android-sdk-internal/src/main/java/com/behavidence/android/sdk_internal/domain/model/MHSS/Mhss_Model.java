package com.behavidence.android.sdk_internal.domain.model.MHSS;

import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.domain.model.MHSS.interfaces.Mhss;
import com.behavidence.android.sdk_internal.domain.model.MHSS.interfaces.Score;

import java.util.ArrayList;
import java.util.List;

public class Mhss_Model implements Mhss {

    private String date;
    private Long timeInMilli;
    private List<Score_Model> scores = new ArrayList<>();

    public Mhss_Model(String date, Long timeInMilli){
       this.date = date;
       this.timeInMilli = timeInMilli;
    }

    public void addScore(Score_Model score){
        scores.add(score);
    }

    public List<Score_Model> getScoreModels(){
        return scores;
    }

    @NonNull
    @Override
    public String getDate_yyyy_mm_dd() {
        return date;
    }

    @NonNull
    @Override
    public Long getTimeInMilli() {
        return timeInMilli;
    }

    @NonNull
    @Override
    public List<Score> getScores() {
        return new ArrayList<>(scores);
    }
}
