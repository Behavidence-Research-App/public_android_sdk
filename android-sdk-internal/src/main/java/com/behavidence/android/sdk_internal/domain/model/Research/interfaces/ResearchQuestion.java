package com.behavidence.android.sdk_internal.domain.model.Research.interfaces;

import androidx.annotation.NonNull;

import java.util.List;

public interface ResearchQuestion {
    @NonNull
    String getQuestion();

    @NonNull
    Boolean isMultipleChoice();

    @NonNull
    String[] getSelectionOptions();

    Boolean getMultipleSelect();

    void selectOptionIndex(@NonNull Integer index, boolean answer);

//    void selectOptionIndexes(@NonNull List<Integer> indexes);

    void selectOption(@NonNull String option, boolean answer);

    @NonNull
    List<String> getSelectedOption();

    List<Integer> getSelectedOptionsIndex();

}
