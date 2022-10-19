package com.behavidence.android.sdk_internal.core.SdkFunctions.AppsFlyer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;
import com.behavidence.android.sdk_internal.core.Networks.ApiRequestTemplate;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.AssociationManager.CodeType;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import okhttp3.Request;


public class AppsFlyerApiRequest {

    private static String appsFlyerPostPath = "/analytics/install";

    protected final ApiKey apiKey;
    protected final AuthClient authClient;

    public AppsFlyerApiRequest(@NonNull AuthClient authClient, @NonNull ApiKey apiKey) {
        this.apiKey = apiKey;
        this.authClient = authClient;
    }

    class GetCodeBuilder extends ApiRequestTemplate<String> {

        private final Map<String, Object> params;

        public GetCodeBuilder(Map<String, Object> params) {
            super(apiKey, authClient);
            this.params = params;
        }

        @Override
        protected Request buildRequest() {

            JSONObject req = new JSONObject();
            try {
                if (params.containsKey("adgroup_id"))
                    req.put("adgroup_id", params.get("adgroup_id").toString());

                if (params.containsKey("adset"))
                    req.put("adset", params.get("adset").toString());

                if (params.containsKey("adset_id"))
                    req.put("adset_id", params.get("adset_id").toString());

                if (params.containsKey("af_siteid"))
                    req.put("af_siteid", params.get("af_siteid").toString());

                if (params.containsKey("agency"))
                    req.put("agency", params.get("agency").toString());

                if (params.containsKey("campaign"))
                    req.put("campaign", params.get("campaign").toString());

                if (params.containsKey("campaign_id"))
                    req.put("campaign_id", params.get("campaign_id").toString());

                if (params.containsKey("http_referrer"))
                    req.put("http_referrer", params.get("http_referrer").toString());

                if (params.containsKey("install_time"))
                    req.put("install_time", params.get("install_time").toString());

                if (params.containsKey("media_source"))
                    req.put("media_source", params.get("media_source").toString());

            } catch (JSONException e) {
                throw new InvalidParameterException(e.getMessage());
            }

            return ApiRequestConstructor.constructPostRequestJSON(req, getHeaders(), appsFlyerPostPath).getRequestObj();
        }

        @Nullable
        @Override
        protected String buildResponse(@NonNull JSONObject jsonObject) {
            try {
                return jsonObject.getJSONObject("data").getString("code");
            } catch (JSONException e) {
                throw new InvalidArgumentException(e.getMessage());
            }
        }
    }

}
