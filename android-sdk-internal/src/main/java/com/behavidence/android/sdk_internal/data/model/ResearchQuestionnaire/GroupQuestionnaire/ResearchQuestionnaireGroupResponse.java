package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire;

import com.google.gson.annotations.SerializedName;


public class ResearchQuestionnaireGroupResponse {

    @SerializedName("data")
    CodeInformationResponse data;


    public void setData(CodeInformationResponse data) {
        this.data = data;
    }

    public CodeInformationResponse getData() {
        return data;
    }

}