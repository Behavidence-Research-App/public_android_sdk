package com.behavidence.android.sdk_internal.domain.model.MHSS.interfaces;

import androidx.annotation.NonNull;

public interface Score {

    @NonNull
    String getScoring();

    @NonNull
    String getScoreTitle();


    @NonNull
    Integer getScoreInNumber();

    void like();
    void disLike();
    void neutral();

}
