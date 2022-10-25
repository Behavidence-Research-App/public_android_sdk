package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;

import com.behavidence.android.sdk_internal.data.interfaces.JournalService;
import com.behavidence.android.sdk_internal.data.repository.ServiceRepository;
import com.behavidence.android.sdk_internal.domain.BehavidenceInterface;
import com.behavidence.android.sdk_internal.domain.interfaces.Auth;

import java.util.ArrayList;

public class BehavidenceClient {

    private static AuthClient authClient = null;

    public static void initialize(Context context){
        authClient = new AuthClient(context);
    }

    public static Auth Auth(){
        if(authClient == null)
            throw new NullPointerException("Client has not been initialized");
        return authClient;
    }

}
