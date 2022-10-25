package com.behavidence.android.sdk_internal.data.interfaces;

import com.behavidence.android.sdk_internal.data.model.MHSS.MHSSResponse;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;

public interface MHSSService {

    MHSSResponse getCurrentMHSSSync();

    void getCurrentMHSS(BehavidenceResponseCallback<MHSSResponse> callback);

    MHSSResponse getMHSSHistorySync();

    void getMHSSHistory(BehavidenceResponseCallback<MHSSResponse> callback);

}
