package com.behavidence.android.sdk_internal.data.model.Participation;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResearchBody extends ParticipationBody{


    @SerializedName("questions")
    private List<QuestionData> questions;

    public ResearchBody(String code, String date, String type, List<QuestionData> questions) {
        super(code, date, type);
        this.questions = questions;
    }


    public List<QuestionData> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionData> questions) {
        this.questions = questions;
    }
}
