package com.behavidence.android.sdk_internal.core.Networks;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * callback to provide to aysncronous calls to get results
 * @param <T> Type which is returned
 */
public interface ApiCallBack<T> {

     /**
      * call when call succeeded. With the defined parameter
      * @param t
      */
     void success(@Nullable T t);

     /**
      * call when call fails with RuntimeException
      * @param e RuntimeException
      */
     void onFailure(@NonNull RuntimeException e);
}
