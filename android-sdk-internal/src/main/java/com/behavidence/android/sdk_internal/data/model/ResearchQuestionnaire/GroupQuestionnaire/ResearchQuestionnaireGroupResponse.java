package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire;

import com.google.gson.annotations.SerializedName;

   
public class ResearchQuestionnaireGroupResponse {

   @SerializedName("data")
   Data data;


    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }
    
}