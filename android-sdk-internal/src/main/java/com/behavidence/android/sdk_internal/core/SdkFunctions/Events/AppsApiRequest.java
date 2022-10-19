package com.behavidence.android.sdk_internal.core.SdkFunctions.Events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Exceptions.ParseException;
import com.behavidence.android.sdk_internal.core.Networks.ApiRequestTemplate;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.App;

import okhttp3.Request;

class AppsApiRequest {
    private final AuthClient authClient;
    private final ApiKey apiKey;

    public AppsApiRequest(@NonNull AuthClient authClient, @NonNull ApiKey apiKey) {
        this.apiKey = apiKey;
        this.authClient=authClient;
    }

    class UploadAppsBuilder extends ApiRequestTemplate<Void> {

        private final List<App> apps;

        public UploadAppsBuilder(@NonNull List<App> apps) {
            super(apiKey,authClient);
            this.apps = apps;
        }


        private JSONArray getAppsJsonArray() throws JSONException {
            JSONArray jsonArray=new JSONArray();
            for(App app:apps)
                jsonArray.put(new JSONObject().put("id",app.getPackageName()));
            return jsonArray;
        }


        @Override
        protected Request buildRequest() {
            try{
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("apps",getAppsJsonArray());
                return ApiRequestConstructor.constructPostRequestJSON(jsonObject,getHeaders(), ApiRequestConstructor.USER_API+"/app-cat").getRequestObj();
            }catch (JSONException e){
                throw new ParseException(e.getMessage());
            }
        }

        @Nullable
        @Override
        protected Void buildResponse(@NonNull JSONObject jsonObject) {
            return null;
        }

    }

}
