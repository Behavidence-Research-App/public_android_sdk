package com.behavidence.android.sdk_internal.core.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.core.Networks.ApiCallBack;
import com.behavidence.android.sdk_internal.core.Networks.ApiCaller;

/**
 * Executor class for executing network calls synchronously or asynchronously
 *  see Command Pattern and Generics in Java for more details
 * @param <T> type which is returned after execution
 *           have to change new thread creation with network executor or kotlin co-routines
 */
public class Executor<T> {
  private final ApiCaller<T> apiCaller;
    public Executor(@NonNull ApiCaller<T> apiCaller) {
        this.apiCaller = apiCaller;
    }

    /**
     * execute on thread from which this is called
     * @return desired type which is provided for return
     */
    @Nullable
    public T execute(){
       return apiCaller.callApiGetResponse();
    }

    /**
     * execute on separate thread and calls the provided callback on result
     * @param apiCallBack callback for the response
     */
    public void executeAsync(@NonNull ApiCallBack<T> apiCallBack){
        new Thread(() -> {
            try {
                apiCallBack.success(execute());
            }catch (RuntimeException e){
                apiCallBack.onFailure(e);
            }
        }).start();

    }
}
