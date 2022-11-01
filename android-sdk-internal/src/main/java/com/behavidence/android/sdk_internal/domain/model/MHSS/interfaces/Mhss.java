package com.behavidence.android.sdk_internal.domain.model.MHSS.interfaces;

import androidx.annotation.NonNull;

import java.util.List;

public interface Mhss {

    @NonNull
    String getDate_yyyy_mm_dd();

    @NonNull
    Long getTimeInMilli();

    @NonNull
    List<Score> getScores();

}
