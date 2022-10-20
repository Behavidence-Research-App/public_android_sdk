package com.behavidence.android.sdk_internal.data.model.MHSS;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class Scores {

   @SerializedName("date")
   Date date;

   @SerializedName("scores")
   List<Scores> scores;


    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate() {
        return date;
    }
    
    public void setScores(List<Scores> scores) {
        this.scores = scores;
    }
    public List<Scores> getScores() {
        return scores;
    }
    
}