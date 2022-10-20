package com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class Data {

   @SerializedName("result")
   List<Result> result;


    public void setResult(List<Result> result) {
        this.result = result;
    }
    public List<Result> getResult() {
        return result;
    }
    
}