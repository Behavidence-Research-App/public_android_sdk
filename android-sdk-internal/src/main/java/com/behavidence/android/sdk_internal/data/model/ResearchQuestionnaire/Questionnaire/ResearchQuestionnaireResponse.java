package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.Questionnaire;

import com.google.gson.annotations.SerializedName;

   
public class ResearchQuestionnaireResponse {

   @SerializedName("data")
   Data data;


    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }
    
}