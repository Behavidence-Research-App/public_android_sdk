package com.behavidence.android.sdk_internal.domain.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.domain.clients.BehavidenceCallback;
import com.behavidence.android.sdk_internal.domain.model.MHSS.interfaces.Mhss;

import java.util.List;

public interface MHSSClient {

    @Nullable
    void getMhss(@NonNull String date_yyyy_mm_dd, BehavidenceCallback<Mhss> callback);

    @NonNull
    void getMhssInDateRange(@NonNull String start_date_yyyy_mm_dd, @NonNull String end_date_yyyy_mm_dd, BehavidenceCallback<List<Mhss>> callback);

    @NonNull
    void getMhssInTimeRange(@NonNull Long startTimeInMilli,@NonNull Long endTimeInMilli, BehavidenceCallback<List<Mhss>> callback);
    @NonNull
    void getAllMhss(BehavidenceCallback<List<Mhss>> callback);

    void updateLike(@NonNull Mhss mhss);

}
