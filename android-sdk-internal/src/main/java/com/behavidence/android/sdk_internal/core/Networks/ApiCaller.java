package com.behavidence.android.sdk_internal.core.Networks;

import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.core.Exceptions.BehavidenceClientException;
import com.behavidence.android.sdk_internal.core.Exceptions.BehavidenceServiceException;
import com.behavidence.android.sdk_internal.core.Exceptions.ParseException;
import com.behavidence.android.sdk_internal.core.Networks.Config.ApiConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public interface ApiCaller<T>{
    T callApiGetResponse();
}

class ApiCallerImpl<T> implements ApiCaller<T>{
    private final ApiRequestBuilder apiRequestBuilder;
    private final ApiResponseBuilder<T> apiResponseBuilder;
    private final ExceptionHandler exceptionHandler;
    private final ApiConfig apiConfig;
    private String exceptionMessage=null;

    public ApiCallerImpl(@NonNull ApiRequestBuilder apiRequestBuilder, @NonNull ApiResponseBuilder<T> apiResponseBuilder, @NonNull ExceptionHandler exceptionHandler) {
        this.apiRequestBuilder = apiRequestBuilder;
        this.apiResponseBuilder = apiResponseBuilder;
        this.exceptionHandler = exceptionHandler;
        apiConfig=ApiConfig.getDefault();
    }
    public ApiCallerImpl(@NonNull ApiRequestBuilder apiRequestBuilder, @NonNull ApiResponseBuilder<T> apiResponseBuilder, @NonNull ExceptionHandler exceptionHandler, @NonNull ApiConfig apiConfig) {
        this.apiRequestBuilder = apiRequestBuilder;
        this.apiResponseBuilder = apiResponseBuilder;
        this.exceptionHandler = exceptionHandler;
       this.apiConfig=apiConfig;
    }

    @Override
    public T callApiGetResponse() {
        try{
           return apiResponseBuilder.buildResponse(makeApiCall(apiRequestBuilder.buildRequest()));
        }catch (RuntimeException e){
            exceptionHandler.handleException(e);
        }
        throw new BehavidenceClientException("Unknown exception","Exception not found");
    }

    private JSONObject makeApiCall(@NonNull Request request) throws BehavidenceServiceException, BehavidenceClientException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30,TimeUnit.SECONDS)
                .build();

        Response response ;
        int retry=0;
        do {
            try {
                response = client.newCall(request).execute();
                if(response.isSuccessful())
                    return new JSONObject(response.body().string());
                else{
                    throwException(response.code(),new JSONObject(response.body().string()));
                }
            } catch (BehavidenceServiceException e) {
                throw e;
            }catch (IOException|IllegalStateException|JSONException e){
                exceptionMessage=e.getMessage();
            }
            retry++;
            try {
                Thread.sleep(retry * 600L);
            }catch (Exception e){}
        }while (retry<apiConfig.getNoOfRetries());
        throw new BehavidenceClientException("No internet connection, Please check the connection and try again",exceptionMessage);

    }
    /**
     *construct message if there is no message from server
     * else get message from server
     * @param code response code
     * @param jsonObject which returned from server
     * @return String containing message
     */
    private void throwException(int code,JSONObject jsonObject){
        try {
            String message;
            if (!jsonObject.has("message") || jsonObject.get("message") == null)
                message = getMessage(code);
            else
                message = jsonObject.getString("message");

            throw new BehavidenceServiceException(code,message);
        }catch (JSONException e){
            throw new ParseException("Error during parsing response",e.getMessage());
        }
    }



    private static String getMessage(int code){
        switch (code){
            case 401:
                return "Credential expired.";

            case 400:
                return "Invalid request";

            case 403:
                return "You are not allowed to perform that action";

            case 404:
                return "Not found. Please try again";


            case 409:
                return "Already exists. Please try again";

            default:
                return "Internal Server Error";
        }
    }
}


