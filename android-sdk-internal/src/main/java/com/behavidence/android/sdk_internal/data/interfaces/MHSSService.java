package com.behavidence.android.sdk_internal.data.interfaces;

import com.behavidence.android.sdk_internal.data.model.MHSS.MHSSResponse;
import com.behavidence.android.sdk_internal.data.model.MHSS.ScoreLikeBody;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClientCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface MHSSService {

    MHSSResponse getCurrentMHSSSync();

    void getCurrentMHSS(BehavidenceClientCallback<MHSSResponse> callback);

    MHSSResponse getMHSSHistorySync();

    void getMHSSHistory(BehavidenceClientCallback<MHSSResponse> callback);

    void putScoreLike(ScoreLikeBody scoreLikeBody, BehavidenceClientCallback<Boolean> callback);

}
