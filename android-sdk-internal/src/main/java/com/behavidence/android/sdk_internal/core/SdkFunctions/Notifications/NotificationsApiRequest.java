package com.behavidence.android.sdk_internal.core.SdkFunctions.Notifications;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Networks.ApiRequestTemplate;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Notifications.Room.NotificationRoom;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Request;

class NotificationsApiRequest {
    private final AuthClient authClient;
    private static final String API_PATH= ApiRequestConstructor.USER_API+"/notifications/android";
    private final ApiKey apiKey;
    public NotificationsApiRequest(@NonNull AuthClient authClient, @NonNull ApiKey apiKey) {
        this.authClient = authClient;
        this.apiKey = apiKey;
    }

    class GetNotifications extends ApiRequestTemplate<Notifications> {
        private final long from;
        private final long to;
        public GetNotifications(long from, long to) {
            super(apiKey,authClient);
            this.from=from;
            this.to=to;
        }

        @Nullable
        private List<Notification> parseNotificationsArray(@NonNull JSONArray notificationArrayJson) throws JSONException {
            if(notificationArrayJson.length()<1)
                return null;
            List<Notification> notifications=new ArrayList<>();
            for(int i=0;i<notificationArrayJson.length();i++)
                notifications.add(new Notification(
                        new NotificationRoom(
                                notificationArrayJson.getJSONObject(i)
                                        .getLong("time_in_milli"),
                                notificationArrayJson.getJSONObject(i)
                                        .getString("title"),
                                notificationArrayJson.getJSONObject(i)
                                        .getString("sub_title"),
                                notificationArrayJson.getJSONObject(i)
                                        .optString("htlm_body",null),
                                notificationArrayJson.getJSONObject(i)
                                        .optString("text",null)
                        )
                ));

            return notifications;
        }


        @Override
        protected Request buildRequest() {
            Map<String,String[]> parameters;
            parameters=new HashMap<>();
            if(from>to)
                throw new InvalidParameterException("Invalid time getting notifications");
            parameters.put("from",new String[]{from+""});
            parameters.put("to",new String[]{to+""});
            return ApiRequestConstructor.constructGetRequest(getHeaders(),
                    parameters, API_PATH).getRequestObj();
        }

        @Nullable
        @Override
        protected Notifications buildResponse(@NonNull JSONObject jsonObject) {
            try{
                List<Notification> notifications=parseNotificationsArray(jsonObject.getJSONObject("data").getJSONArray("notifications_array"));
                boolean hasMoreNotifications=jsonObject.getJSONObject("data").getBoolean("more_notifications_exists");
                return new Notifications(notifications,hasMoreNotifications);
            }catch (JSONException exception){
                throw new InvalidParameterException(exception.getMessage());
            }
        }
    }





    class DeleteNotifications extends ApiRequestTemplate<Void> {
        private final String []notificationTime;
        public DeleteNotifications(String []notificationTime) {
            super(apiKey,authClient);
            this.notificationTime=notificationTime;
        }

        @Override
        protected Request buildRequest() {
            Map<String,String[]> parameters;
            parameters=new HashMap<>();
            parameters.put("time",notificationTime);
            return ApiRequestConstructor.constructDeleteRequest(getHeaders(),
                    parameters, API_PATH).getRequestObj();
        }

        @Nullable
        @Override
        protected Void buildResponse(@NonNull JSONObject jsonObject) {
            return null;
        }
    }





}
