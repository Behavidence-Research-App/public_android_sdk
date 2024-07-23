package com.behavidence.android.sdk_internal.data.repository;

import android.content.Context;

import com.behavidence.android.sdk_internal.data.interfaces.DopaOneService;
import com.behavidence.android.sdk_internal.data.model.DopaOne.RawData;
import com.behavidence.android.sdk_internal.data.model.DopaOne.RawDataBody;
import com.behavidence.android.sdk_internal.data.model.Events.SessionBody;
import com.behavidence.android.sdk_internal.data.room_model.DopaOne.RawSensorDataEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DopaOneService_Impl extends ServiceParent implements DopaOneService
{
    private DopaOneRepoImpl client;
    public DopaOneService_Impl(Context context) {
        super(context);
        client = RetrofitClient.getClient().create(DopaOneRepoImpl.class);
    }

    @Override
    public void postRawSensorData(List<RawSensorDataEntity> samples) {
        if(loadAuthTokenSync()) {

            List<RawData> list = new ArrayList<>();
            for(RawSensorDataEntity sdr: samples){
                list.add(new RawData(sdr.getTimestamp(), sdr.getSensor_type(), sdr.x, sdr.getY(), sdr.getZ()));
            }
            try {
                client.postRawData(apiKey, token, new RawDataBody(list)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        return;
    }
}
