package com.behavidence.android.sdk_internal.Utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class LoadAPIKey {

    public static String getKey(Context context) throws PackageManager.NameNotFoundException {
        ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);

        if(ai.metaData == null) invalidClientKeyException();

        Object value = ai.metaData.get("BEHAVIDENCE_CLIENT_KEY");

        if(value == null) invalidClientKeyException();

        return value.toString();
    }

    private static void invalidClientKeyException(){
        throw new NullPointerException("Client Key does not exists. \n Add <meta-data \nandroid:name=\"BEHAVIDENCE_CLIENT_KEY\"\nandroid:value=\"<KEY>\" /> in manifest.");
    }

}
