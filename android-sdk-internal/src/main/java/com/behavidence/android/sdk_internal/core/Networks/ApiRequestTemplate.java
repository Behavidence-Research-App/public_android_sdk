package com.behavidence.android.sdk_internal.core.Networks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Auth.BehavidenceAuth;
import com.behavidence.android.sdk_internal.core.Auth.EmailAuth;
import com.behavidence.android.sdk_internal.core.Exceptions.AuthException;
import com.behavidence.android.sdk_internal.core.Exceptions.BehavidenceClientException;
import com.behavidence.android.sdk_internal.core.Exceptions.BehavidenceServiceException;
import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;
import com.behavidence.android.sdk_internal.core.Networks.Config.ApiConfig;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public abstract class ApiRequestTemplate<T>{
      private final ApiKey apiKey;
      private final AuthClient authClient;

    public ApiRequestTemplate(@NonNull ApiKey apiKey, @Nullable AuthClient authClient) {
        this.apiKey = apiKey;
        this.authClient = authClient;
    }

    @NonNull
    public Map<String,String> getHeaders(){
        HashMap<String,String> map=new HashMap<>();
        if (apiKey.getApiKey() == null)
            throw new BehavidenceClientException("SDK not initialized. Api secret not found", "Make sure to initialize the SDK with Api secret and initiate the client object again");
        map.put("x-api-key",apiKey.getApiKey());
        if(authClient!=null){
            BehavidenceAuth auth=authClient.getAuth().execute();
            if(auth==null||auth.getToken()==null) throw new AuthException("access token expired.","Access token expired. Please refresh access token");
            if(auth instanceof EmailAuth&&(((EmailAuth) auth).isUserRegistered()==null|| !((EmailAuth) auth).isUserRegistered()))
                throw new AuthException("User is not registered.","Please call register api to register the user first");
            map.put("token",auth.getToken());
        }
        return map;
    }

    public ApiCaller<T> buildApiCaller(){
        return new ApiCallerImpl<>(this::buildRequest,this::buildResponse,this::handleException);
    }
    public ApiCaller<T> buildApiCaller(@NonNull ApiConfig apiConfig){
        return new ApiCallerImpl<>(this::buildRequest,this::buildResponse,this::handleException,apiConfig);
    }

    protected abstract Request buildRequest();
    @Nullable
    protected abstract T buildResponse(@NonNull JSONObject jsonObject);

    private void checkBehavidenceException(BehavidenceServiceException e){
        if(e.getStatusCode()==401)
            throw new AuthException(e.getMessage()!=null?e.getMessage():"unknown","Authorization exception");
    }
    protected void handleException(RuntimeException e){
        if(e instanceof  BehavidenceServiceException) {
            checkBehavidenceException((BehavidenceServiceException) e);
        }
        if(e  instanceof BehavidenceServiceException && (((BehavidenceServiceException) e).getStatusCode()==400))
            throw new InvalidArgumentException(e.getMessage());
            throw e;
    }
}
