package com.behavidence.android.sdk_internal.data.repository.Auth;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class AuthClient {

    public AuthClient(Context context) throws PackageManager.NameNotFoundException {

        ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);

        if(ai.metaData == null) invalidClientException();

        Object value = ai.metaData.get("BEHAVIDENCE_CLIENT_KEY");

        if(value == null) invalidClientException();

        Log.d("BehavidenceKey", value.toString());
    }

    private void invalidClientException(){
        throw new NullPointerException("Client Key does not exists. \n Add <meta-data \nandroid:name=\"BEHAVIDENCE_CLIENT_KEY\"\nandroid:value=\"<KEY>\" /> in manifest.");
    }

}
