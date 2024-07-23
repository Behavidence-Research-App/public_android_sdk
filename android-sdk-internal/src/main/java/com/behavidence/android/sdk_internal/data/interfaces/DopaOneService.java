package com.behavidence.android.sdk_internal.data.interfaces;

import com.behavidence.android.sdk_internal.data.room_model.DopaOne.RawSensorDataEntity;

import java.util.List;

public interface DopaOneService {

    void postRawSensorData(List<RawSensorDataEntity> samples);

}
