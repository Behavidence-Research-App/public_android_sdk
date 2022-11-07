package com.behavidence.android.sdk_internal.domain.model.Research.interfaces;

import androidx.annotation.NonNull;

import java.util.List;

public interface QuestionGroup {

    /**
     * Get all Research Questions in this group
     * @return List of questions
     */
    @NonNull
    List<ResearchQuestion> getResearchQuestions();

    /**
     * Question Group Header with information about the group
     * @return Header Text
     */
    @NonNull
    String getQuestionsHeader();
}
