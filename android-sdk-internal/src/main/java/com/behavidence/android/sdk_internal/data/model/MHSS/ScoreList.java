package com.behavidence.android.sdk_internal.data.model.MHSS;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class ScoreList {

   @SerializedName("date")
   String date;

   @SerializedName("scores")
   List<ScoreBody> scores;


    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }
    
    public void setScores(List<ScoreBody> scores) {
        this.scores = scores;
    }
    public List<ScoreBody> getScores() {
        return scores;
    }
    
}