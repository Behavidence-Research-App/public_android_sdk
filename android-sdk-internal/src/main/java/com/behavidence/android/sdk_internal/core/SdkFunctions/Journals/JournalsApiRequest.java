package com.behavidence.android.sdk_internal.core.SdkFunctions.Journals;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Exceptions.AuthException;
import com.behavidence.android.sdk_internal.core.Exceptions.BehavidenceServiceException;
import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;
import com.behavidence.android.sdk_internal.core.Networks.ApiRequestTemplate;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.TimeZoneInfo;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.TimeZoneClient;

import okhttp3.Request;

class JournalsApiRequest {
    private final AuthClient authClient;
    private final ApiKey apiKey;

    public JournalsApiRequest(@NonNull AuthClient authClient, @NonNull ApiKey apiKey) {
        this.authClient = authClient;
        this.apiKey = apiKey;
    }

    private void checkAuthorization(BehavidenceServiceException e){
        if(e.getStatusCode()==401)
            throw new AuthException("Authorization Exception",e.getMessage());
    }


    class SaveJournalsBuilder extends ApiRequestTemplate<Void> {

        private List<Journal> journals;
        private List<TimeZoneInfo> timeZoneInfos;
        private TimeZoneClient timeZoneClient;

        public SaveJournalsBuilder() {
            super(apiKey,authClient);
        }

        public SaveJournalsBuilder setJournals(@NonNull List<Journal> journals) {
            this.journals = journals;
            return this;
        }

        public SaveJournalsBuilder setTimeZoneInfos(@NonNull List<TimeZoneInfo> timeZoneInfos) {
            this.timeZoneInfos = timeZoneInfos;
            return this;
        }

        public SaveJournalsBuilder setTimeZoneClient(@NonNull TimeZoneClient timeZoneClient) {
            this.timeZoneClient = timeZoneClient;
            return this;
        }


        private String checkTextSize(@NonNull String string){
            if(string.length()>150)
                throw new InvalidArgumentException("text length must be less than 150");
            return string;

        }


        @Override
        protected Request buildRequest() {
            try{
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray=new JSONArray();
                JSONObject object;
                for (int i=0;i<journals.size();i++) {
                    object=new JSONObject();
                    object.put("datetime", timeZoneClient.getTimeZoneForTime(timeZoneInfos,journals.get(i).getTimeStampMillisecond()).getTimeZoneId());
                    object.put("time_in_millisec",journals.get(i).getTimeStampMillisecond()+"");
                    object.put("text",checkTextSize(journals.get(i).getJournalTxt()));
                    jsonArray.put(object);
                }
                jsonObject.put("events",jsonArray);

                return ApiRequestConstructor.constructPostRequestJSON(jsonObject,getHeaders(), ApiRequestConstructor.USER_API+"/journals").getRequestObj();

            }catch (JSONException e){throw new InvalidArgumentException(e.getMessage());}
        }

        @Nullable
        @Override
        protected Void buildResponse(@NonNull JSONObject jsonObject) {
            return null;
        }

    }

}
