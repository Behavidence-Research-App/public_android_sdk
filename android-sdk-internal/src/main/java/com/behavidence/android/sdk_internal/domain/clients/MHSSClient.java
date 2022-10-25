package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;

import com.behavidence.android.sdk_internal.data.interfaces.JournalService;

public class MHSSClient extends ClientParent{

    private JournalService _service;

    public MHSSClient(Context context) {
        super(context);
        _service = services.journal();
    }

}
