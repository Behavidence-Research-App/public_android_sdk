package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.QuestionGroup;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.ResearchQuestion;
import com.google.gson.annotations.SerializedName;


public class QuestionsGroup implements QuestionGroup {

    @SerializedName("header")
    String header;

    @SerializedName("submit_index_answers")
    boolean submitIndexAnswers;

    @SerializedName("questions")
    List<Questions> questions;


    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public void setSubmitIndexAnswers(boolean submitIndexAnswers) {
        this.submitIndexAnswers = submitIndexAnswers;
    }

    public boolean getSubmitIndexAnswers() {
        return submitIndexAnswers;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    @NonNull
    @Override
    public List<ResearchQuestion> getResearchQuestions() {
        return new ArrayList<>(questions);
    }

    @NonNull
    @Override
    public String getQuestionsHeader() {
        return header;
    }
}