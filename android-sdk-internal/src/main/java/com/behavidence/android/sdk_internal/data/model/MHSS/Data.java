package com.behavidence.android.sdk_internal.data.model.MHSS;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class Data {

   @SerializedName("scores")
   List<Scores> scores;


    public void setScores(List<Scores> scores) {
        this.scores = scores;
    }
    public List<Scores> getScores() {
        return scores;
    }
    
}