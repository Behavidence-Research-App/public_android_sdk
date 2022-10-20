package com.behavidence.android.sdk_internal.data.model.Participation;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionData {

    @SerializedName("qid")
    private String questionId;
    @SerializedName("no")
    private long number;
    @SerializedName("options")
    private List<String> options;

    public QuestionData(String questionId, long number, List<String> options) {
        this.questionId = questionId;
        this.number = number;
        this.options = options;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
