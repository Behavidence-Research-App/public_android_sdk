package com.behavidence.android.sdk_internal.domain.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.domain.clients.BehavidenceCallback;
import com.behavidence.android.sdk_internal.domain.model.MHSS.interfaces.Mhss;

import java.util.List;

public interface MHSSClient {

    /***
     * Get the calculated Similarity Scores for a date
     * @param date_yyyy_mm_dd Date in String in format YYYY_MM_DD
     * @param callback Provide a callback to fetch the latest data from the server
     */
    @Nullable
    void getMhss(@NonNull String date_yyyy_mm_dd, BehavidenceCallback<Mhss> callback);

    /***
     * Get the calculated Similarity Scores in a date range
     * @param start_date_yyyy_mm_dd Start Date in String in format YYYY_MM_DD
     * @param end_date_yyyy_mm_dd End Date in String in format YYYY_MM_DD
     * @param callback Provide a callback to fetch the latest data from the server
     */
    @NonNull
    void getMhssInDateRange(@NonNull String start_date_yyyy_mm_dd, @NonNull String end_date_yyyy_mm_dd, BehavidenceCallback<List<Mhss>> callback);

    /***
     * Get the calculated Similarity Scores in a time range
     * @param startTimeInMilli Start time in UTC Milliseconds
     * @param endTimeInMilli End time in UTC Milliseconds
     * @param callback Provide a callback to fetch the latest data from the server
     */
    @NonNull
    void getMhssInTimeRange(@NonNull Long startTimeInMilli,@NonNull Long endTimeInMilli, BehavidenceCallback<List<Mhss>> callback);

    /***
     * Get all the calculated Similarity Scores
     * @param callback Provide a callback to fetch the latest data from server
     */
    @NonNull
    void getAllMhss(BehavidenceCallback<List<Mhss>> callback);

    /***
     * Update Similarity Score Like state to indicate if the score is positive/negative
     * @param mhss The mhss object for which the like state is updated
     */
    void updateLike(@NonNull Mhss mhss);

}
