package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;

import com.behavidence.android.sdk_internal.data.repository.ServiceRepository;
import com.behavidence.android.sdk_internal.data.room_repository.BehavidenceSDKInternalDb;

class ClientParent {

    protected ServiceRepository services;
    protected BehavidenceSDKInternalDb database;

    public ClientParent(Context context){
        services = ServiceRepository.getInstance(context);
        database = BehavidenceSDKInternalDb.getInstance(context);
    }

}
