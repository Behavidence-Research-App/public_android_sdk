package com.behavidence.android.sdk_internal.domain.model.MHSS.interfaces;

import androidx.annotation.NonNull;

public interface Score {

    /**
     *
     * @return Scoring Label in String
     */
    @NonNull
    String getScoring();

    /**
     *
     * @return Score Title in String
     */
    @NonNull
    String getScoreTitle();

    /**
     *
     * @return The actual similarity score
     */
    @NonNull
    Integer getScoreInNumber();

    /**
     * Indicate that current score matches user's mental state
     */
    void like();

    /**
     * Indicate that current score does not match user's mental state
     */
    void disLike();

    /**
     * Reset like state for a score
     */
    void neutral();

}
