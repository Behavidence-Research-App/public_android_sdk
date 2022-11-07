package com.behavidence.android.sdk_internal.domain.model.MHSS.interfaces;

import androidx.annotation.NonNull;

import java.util.List;

public interface Mhss {

    /***
     *
     * @return Date of the Similarity Score
     */
    @NonNull
    String getDate_yyyy_mm_dd();

    /**
     *
     * @return Time of similarity score
     */
    @NonNull
    Long getTimeInMilli();

    /**
     *
     * @return List of scores in the current MHSS
     */
    @NonNull
    List<Score> getScores();

}
