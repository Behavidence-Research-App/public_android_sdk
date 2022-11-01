package com.behavidence.android.sdk_internal.domain.model.Research.interfaces;

import androidx.annotation.NonNull;

import java.util.List;

public interface QuestionGroup {
    @NonNull
    List<ResearchQuestion> getResearchQuestions();

    @NonNull
    String getQuestionsHeader();
}
