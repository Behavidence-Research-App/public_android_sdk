package com.behavidence.android.sdk_internal.domain.model.Research.interfaces;

import androidx.annotation.NonNull;

import java.util.List;

public interface ResearchQuestion {

    /**
     *
     * @return Question text
     */
    @NonNull
    String getQuestion();

    /**
     *
     * @return True if this is a Multiple Choice question
     */
    @NonNull
    Boolean isMultipleChoice();

    /**
     *
     * @return All options for current question
     */
    @NonNull
    String[] getSelectionOptions();

    /**
     * Select the question in the list. This is 0-indexed
     * @param index The index of the selected question
     * @param answer True or false answer for the question
     */
    void selectOptionIndex(@NonNull Integer index, boolean answer);

//    void selectOptionIndexes(@NonNull List<Integer> indexes);

    /**
     * Select the question in the list by providing the option string
     * @param option The option String
     * @param answer True or false answer for the question
     */
    void selectOption(@NonNull String option, boolean answer);

    /**
     * Get all selected options
     * @return Option list
     */
    @NonNull
    List<String> getSelectedOption();

    /**
     * Get all index of selected options
     * @return Options index list
     */
    List<Integer> getSelectedOptionsIndex();

}
