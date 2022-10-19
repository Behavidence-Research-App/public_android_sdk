package com.behavidence.android.sdk_internal.core.SdkFunctions.Events;

import static com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor.DATA_TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;
import com.behavidence.android.sdk_internal.core.Networks.ApiRequestTemplate;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.AppSession;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.TimeZoneInfo;

import okhttp3.Request;

class SessionsNetworkRequest {

    public static final String UPLOAD_EVENTS_PATH="/events";

    private final AuthClient authClient;
    private final ApiKey apiKey;

     SessionsNetworkRequest(@NonNull AuthClient authClient, @NonNull ApiKey apiKey) {
        this.apiKey = apiKey;
        this.authClient = authClient;
    }
    class SessionsUploadBuilder extends ApiRequestTemplate<Long> {

        private TimeZoneClient timeZoneClient;
        private List<AppSession> appSessions;
        private List<TimeZoneInfo> timeZoneInfos;
        private List<TimeZoneInfo> timeZonesToUpload;

        public SessionsUploadBuilder() {
            super(apiKey,authClient);
        }

        @NonNull
        public SessionsUploadBuilder setTimeZoneClient(@NonNull TimeZoneClient timeZoneClient) {
            this.timeZoneClient = timeZoneClient;
            return this;
        }

        @NonNull
        public SessionsUploadBuilder setAppSessions(@NonNull List<AppSession> appSessions) {
            this.appSessions = appSessions;
            return this;
        }

        @NonNull
        public SessionsUploadBuilder setTimeZoneInfos(@NonNull List<TimeZoneInfo> timeZoneInfos) {
            this.timeZoneInfos = timeZoneInfos;
            return this;
        }

        @NonNull
        public SessionsUploadBuilder setTimeZonesToUpload(@Nullable List<TimeZoneInfo> timeZonesToUpload) {
            this.timeZonesToUpload = timeZonesToUpload;
            return this;
        }


        private JSONArray generateTimeZoneJson() throws JSONException {
            JSONArray zonesArray = new JSONArray();
            JSONObject jsonObject;
            for (TimeZoneInfo timeZoneInfo : timeZoneInfos) {
                jsonObject = new JSONObject();
                jsonObject.put("id", timeZoneInfo.getTimeZoneId());
                jsonObject.put("offset", timeZoneInfo.getTimeZoneOffset());
                jsonObject.put("change", timeZoneInfo.getChangedTime());
                zonesArray.put(jsonObject);
            }
            return zonesArray;
        }
        @NonNull
        private  JSONObject generateEventJson() throws JSONException{
            if (appSessions == null || appSessions.size() < 1)
                throw new JSONException("Empty events");
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray=new JSONArray();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            JSONObject object;
            TimeZoneInfo timeZoneInfo;
            for (AppSession event : appSessions){
                object=new JSONObject();
                object.put("start",event.getStartTime());
                object.put("end",event.getEndTime());
                object.put("id",event.getPackageName());
                timeZoneInfo=timeZoneClient.getTimeZoneForTime(timeZoneInfos,event.getEndTime());
                object.put("zone", timeZoneInfo.getTimeZoneId());
                sdf.setTimeZone(TimeZone.getTimeZone(timeZoneInfo.getTimeZoneId()));
                object.put("startuser",sdf.format(new Date(event.getStartTime())));
                object.put("enduser",sdf.format(new Date(event.getEndTime())));
                jsonArray.put(object);
            }
            jsonObject.put("events",jsonArray);
            return jsonObject;
        }

        @Override
        protected Request buildRequest() {
            try {
                JSONObject jsonObject = generateEventJson();
                if (timeZonesToUpload != null && timeZonesToUpload.size() > 0)
                    jsonObject.put("zone_info", generateTimeZoneJson());
                return ApiRequestConstructor.constructPostRequestJSON(jsonObject,getHeaders(), ApiRequestConstructor.USER_API+UPLOAD_EVENTS_PATH).getRequestObj();
            }catch (JSONException e){throw  new InvalidArgumentException(e.getMessage());}

        }

        @Nullable
        @Override
        protected Long buildResponse(@NonNull JSONObject jsonObject) {
            try {
                if (jsonObject.get(DATA_TAG) instanceof JSONObject && jsonObject.getJSONObject(DATA_TAG).has("lasttimeupload"))
                    return jsonObject.getJSONObject(DATA_TAG).getLong("lasttimeupload");
                return null;
            }catch (JSONException e){throw new InvalidArgumentException(e.getMessage());}
        }
    }
}
