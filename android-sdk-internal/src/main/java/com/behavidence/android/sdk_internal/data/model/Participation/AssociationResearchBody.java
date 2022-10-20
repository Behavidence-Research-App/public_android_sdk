package com.behavidence.android.sdk_internal.data.model.Participation;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssociationResearchBody extends ParticipationBody{

    @SerializedName("expire_sec")
    private long expireSec;

    @SerializedName("questions")
    private List<QuestionData> questions;


    public AssociationResearchBody(String code, String date, String type, long expireSec, List<QuestionData> questions) {
        super(code, date, type);
        this.expireSec = expireSec;
        this.questions = questions;
    }

    public long getExpireSec() {
        return expireSec;
    }

    public void setExpireSec(long expireSec) {
        this.expireSec = expireSec;
    }

    public List<QuestionData> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionData> questions) {
        this.questions = questions;
    }
}
