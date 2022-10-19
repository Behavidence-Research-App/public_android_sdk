package com.behavidence.android.sdk_internal.core.SdkFunctions.Association.AssociationManager;

import static com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor.USER_API;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;
import com.behavidence.android.sdk_internal.core.Networks.ApiRequestTemplate;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class AssociationApiRequest {
    protected String apiPath = USER_API + "/association";
    protected String researchApiPath = USER_API + "/research";

    protected final ApiKey apiKey;
    protected final AuthClient authClient;

    public AssociationApiRequest(@NonNull ApiKey apiKey, @NonNull AuthClient authClient) {
        this.apiKey = apiKey;
        this.authClient = authClient;
    }

    class DeleteAssociationBuilder extends ApiRequestTemplate<Void> {
        private final String[] adminids;

        public DeleteAssociationBuilder(@NonNull String[] adminids) {
            super(apiKey, authClient);
            this.adminids = adminids;
        }

        //url needed to be determined
        @Override
        protected Request buildRequest() {
            Map<String, String[]> parameters;
            parameters = new HashMap<>();
            parameters.put("adminid", adminids);
            return ApiRequestConstructor.constructDeleteRequest(getHeaders(), parameters, apiPath).getRequestObj();

        }

        @Nullable
        @Override
        protected Void buildResponse(@NonNull JSONObject jsonObject) {
            return null;
        }
    }

    class DeleteResearchBuilder extends ApiRequestTemplate<String> {

        private final String[] adminIds;
        private final String[] code;

        public DeleteResearchBuilder(@NonNull String[] adminIds, @NonNull String[] code) {
            super(apiKey, authClient);
            this.adminIds = adminIds;
            this.code = code;
        }

        @Override
        protected Request buildRequest() {
            Map<String, String[]> parameters;
            parameters = new HashMap<>();
            parameters.put("adminid", adminIds);
            parameters.put("code", code);

            return ApiRequestConstructor.constructDeleteRequest(getHeaders(), parameters, researchApiPath).getRequestObj();

        }

        @Nullable
        @Override
        protected String buildResponse(@NonNull JSONObject jsonObject) {
            try {
                return jsonObject.getJSONObject("data").getString("message");
            } catch (JSONException e) {
                throw new InvalidArgumentException(e.getMessage());
            }
        }
    }

    class UpdateAssociationBuilder extends ApiRequestTemplate<Void> {
        private final String adminId;
        private final long expTimeInSec;


        public UpdateAssociationBuilder(@NonNull String adminId, long expTimeInSec) {
            super(apiKey, authClient);
            if (expTimeInSec < 0) throw new InvalidParameterException("invalid exp time");
            this.adminId = adminId;
            this.expTimeInSec = expTimeInSec;
        }

        //api path need to be determined
        @Override
        protected Request buildRequest() {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("adminid", adminId);
                jsonObject.put("expire_sec", expTimeInSec);
                return ApiRequestConstructor.constructPutRequestJSON(jsonObject, getHeaders(), apiPath).getRequestObj();
            } catch (JSONException e) {
                throw new InvalidParameterException(e.getMessage());
            }
        }

        @Nullable
        @Override
        protected Void buildResponse(@NonNull JSONObject jsonObject) {
            return null;
        }
    }


}
