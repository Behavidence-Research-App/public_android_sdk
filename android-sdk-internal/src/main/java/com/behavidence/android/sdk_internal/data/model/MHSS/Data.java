package com.behavidence.android.sdk_internal.data.model.MHSS;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class Data {

   @SerializedName("scores")
   List<ScoreList> scoreListList;


    public void setScores(List<ScoreList> scores) {
        this.scoreListList = scores;
    }
    public List<ScoreList> getScores() {
        return scoreListList;
    }
    
}