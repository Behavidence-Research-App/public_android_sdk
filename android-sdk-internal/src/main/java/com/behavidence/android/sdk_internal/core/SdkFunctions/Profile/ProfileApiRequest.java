package com.behavidence.android.sdk_internal.core.SdkFunctions.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;
import com.behavidence.android.sdk_internal.core.Networks.ApiRequestTemplate;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor;

import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.Request;

class ProfileNetworkRequest {
    private final AuthClient authClient;
    private static final String apiPath="/profile";
    private final ApiKey apiKey;
    public ProfileNetworkRequest(@NonNull AuthClient authClient, @NonNull ApiKey apiKey) {
        this.authClient = authClient;
        this.apiKey = apiKey;
    }


    class FCMTokenUploadBuilder extends ApiRequestTemplate<Void> {

        private final String fcmToken;
        public FCMTokenUploadBuilder(@NonNull String fcmToken) {
            super(apiKey, authClient);
            this.fcmToken=fcmToken;
        }

        @Override
        protected Request buildRequest() {
            try{
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("token",fcmToken);
                return ApiRequestConstructor.constructPostRequestJSON(
                        jsonObject,getHeaders(), ApiRequestConstructor.USER_API+apiPath).getRequestObj();

            }catch (JSONException jsonException){
                throw new InvalidArgumentException(jsonException.getMessage());
            }
        }

        @Nullable
        @Override
        protected Void buildResponse(@NonNull JSONObject jsonObject) {
            return null;
        }
    }

}
