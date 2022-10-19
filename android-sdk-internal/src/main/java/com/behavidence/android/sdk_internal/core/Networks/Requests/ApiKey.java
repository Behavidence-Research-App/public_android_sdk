package com.behavidence.android.sdk_internal.core.Networks.Requests;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;


@FunctionalInterface
public interface ApiKey {

    static ApiKey getInstance(@NonNull Context context){
       return ApiKeyImpl.getInstance(context);
   }
   @Nullable
    String getApiKey();
     static void saveApiKey(@NonNull Context context,@NonNull String apiKey){
        ApiKeyImpl.saveApiKey(context,apiKey);
    }
}

class ApiKeyImpl implements ApiKey{
    private static  ApiKeyImpl apiKeyImpl;
    private final String apiKey;
    private static final String API_FILE="internalfile";
    private static final String API_KEY="apikey";

    static synchronized ApiKeyImpl getInstance(@NonNull Context context){
        if(apiKeyImpl==null||apiKeyImpl.apiKey==null)
            apiKeyImpl=new ApiKeyImpl(context);
        return apiKeyImpl;
    }

    @Nullable
    @Override
    public String getApiKey() {
       return apiKey;
    }

    private ApiKeyImpl(@NonNull Context context) {
        this.apiKey=getEncSharedPref(context).getString(API_KEY,null);
    }
    private static SharedPreferences getEncSharedPref(@NonNull Context context){
        try {
            return EncryptedSharedPreferences.create(context,
                    API_FILE,
                    new MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                            .setUserAuthenticationRequired(false)
                            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                            .build(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        }catch (Exception e){
            return context.getSharedPreferences(API_FILE+"1",Context.MODE_PRIVATE);
        }
    }

    static synchronized void saveApiKey(@NonNull Context context,@NonNull String apiKey){
        getEncSharedPref(context).edit().putString(API_KEY,apiKey).apply();
    }

}