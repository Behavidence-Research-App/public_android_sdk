package com.behavidence.android.sdk_internal.core.SdkFunctions.SimilarityScores;

import static com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor.DATA_TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;
import com.behavidence.android.sdk_internal.core.Exceptions.ParseException;
import com.behavidence.android.sdk_internal.core.Networks.ApiRequestTemplate;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor;
import com.behavidence.android.sdk_internal.core.SdkFunctions.SimilarityScores.Room.Mhssroom;
import com.behavidence.android.sdk_internal.core.Utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;


class ScoresApiRequest {

    private static final String UPDATE_DATE_TAG = "update_date";
    //    private static final String ANXIETY_CAL_TAG = "cal_anxiety";
//    private static final String DEPRESSION_CAL_TAG = "cal_depression";
//    private static final String ADHD_CAL_TAG = "cal_adhd";
//    private static final String STRESS_CAL_TAG = "cal_stress";
    private static final String ANXIETY_CAL_TAG = "0";
    private static final String ADHD_CAL_TAG = "1";
    private static final String DEPRESSION_CAL_TAG = "2";
    private static final String STRESS_CAL_TAG = "3";
    private static final String TYPE_TAG = "type";
    private static final String WRITE_TAG = "write";
    private static final String LIKE_TAG = "like";
    private static final String HISTORY_TAG = "history";
    private static final long TIME_TO_REQUEST = 60 * 60 * 1000;
    private static final String FILE_NAME = "SimilarityScorefile";
    private static final String LAST_TIME = "lasttime";
    private static final String CLOUD_NOTIFY = "cloudnotify";
    private static final String HISTORY_FETCHED = "historyfetcheds";
    private static final String GET_LIKES_PATH = "/scores/likes";
    private static final String GET_SCORES_PATH = "/mhss/android";
    private final AuthClient authClient;
    private final ApiKey apiKey;


    public ScoresApiRequest(@NonNull AuthClient authClient, @NonNull ApiKey apiKey) {
        this.authClient = authClient;
        this.apiKey = apiKey;
    }

    class GetScoresBuilder extends ApiRequestTemplate<List<Mhssroom>> {

        private final boolean fetchHistory;

        public GetScoresBuilder(boolean fetchHistory) {
            super(apiKey, authClient);
            this.fetchHistory = fetchHistory;
        }


        @Override
        protected Request buildRequest() {
            Map<String, String[]> map = new HashMap<>();
            map.put(HISTORY_TAG, new String[]{fetchHistory + ""});
            return ApiRequestConstructor.constructGetRequest(getHeaders(),
                    map, ApiRequestConstructor.USER_API + GET_SCORES_PATH).getRequestObj();
        }

        @Nullable
        @Override
        protected List<Mhssroom> buildResponse(@NonNull JSONObject jsonObject) {
            try {

                final int chunkSize = 2048;
                for (int i = 0; i < jsonObject.toString().length(); i += chunkSize) {
                    Log.d("ScoreApiRequestCheck", jsonObject.toString().substring(i, Math.min(jsonObject.toString().length(), i + chunkSize)));
                }

                List<Mhssroom> similarityScores = new ArrayList<>();
                JSONObject data = jsonObject.getJSONObject(DATA_TAG);
                JSONArray jsonArray = data.getJSONArray("scores");
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++)
                        similarityScores.addAll(parseJsonToMhss(jsonArray.getJSONObject(i)));
                }
                return similarityScores;
            } catch (JSONException e) {
                throw new ParseException(e.getMessage());
            }
        }

//        private Mhssroom parseJsonToMhss(JSONObject response) throws JSONException{
//           String scoreDate=response.getString(UPDATE_DATE_TAG);
//           long scoreTime= AppUtils.getDateRange(response.getString(UPDATE_DATE_TAG))[0];
//           int anxietyScore=Mhssroom.NOT_INIT;
//           int depressionScore=Mhssroom.NOT_INIT;
//           int adhdScore=Mhssroom.NOT_INIT;
//           int stressScores=Mhssroom.NOT_INIT;
//            //if anxiety score is not null then set anxiety scores
//            //parsing because we are getting string from server
//            if (response.has(ANXIETY_CAL_TAG))
//                anxietyScore=(Integer.parseInt(response.getString(ANXIETY_CAL_TAG)));
//
//            //getting depression scores
//            if (response.has(DEPRESSION_CAL_TAG))
//                depressionScore=(Integer.parseInt(response.getString(DEPRESSION_CAL_TAG)));
//
//            //setting adhd score
//            if (response.has(ADHD_CAL_TAG))
//                adhdScore=(Integer.parseInt(response.getString(ADHD_CAL_TAG)));
//
//            if (response.has(STRESS_CAL_TAG)&&!response.getString(STRESS_CAL_TAG).equals("null"))
//                stressScores=(Integer.parseInt(response.getString(STRESS_CAL_TAG)));
//
//
//            return new Mhssroom(scoreDate,scoreTime,depressionScore,anxietyScore,adhdScore,stressScores);
//        }
//
//
//    }

        private List<Mhssroom> parseJsonToMhss(JSONObject response) throws JSONException {
            String scoreDate = "2000-01-01";
            if (response.has("date"))
                scoreDate = response.getString("date");
            long scoreTime = AppUtils.getDateRange(scoreDate)[0];

            JSONArray scores = response.getJSONArray("scores");
            List<Mhssroom> mhss = new ArrayList<>();


            for (int i = 0; i < scores.length(); ++i) {

                JSONObject scoreObj = scores.getJSONObject(i);

                mhss.add(new Mhssroom(
                        scoreObj.getString("id"),
                        scoreDate,
                        scoreObj.getInt("score"),
                        scoreTime
                ));
            }


            return mhss;
        }


    }


    class PostLikesBuilder extends ApiRequestTemplate<Void> {

        private String id;
        private boolean like;
        private String date;

        public PostLikesBuilder(@NonNull String id, @NonNull Boolean like, @NonNull String date) {
            super(apiKey, authClient);
            this.id = id;
            this.like = like;
            this.date = date;
        }

        public PostLikesBuilder() {
            super(apiKey, authClient);
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setLike(boolean like) {
            this.like = like;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        protected Request buildRequest() {
            try {

                JSONObject body = new JSONObject();
                body.put("date", date);
                body.put("like", like);
                body.put("scoreid", id);

                return ApiRequestConstructor.constructPutRequestJSON(body,
                        getHeaders(),
                        ApiRequestConstructor.USER_API + GET_SCORES_PATH).getRequestObj();
            } catch (JSONException e) {
                throw new InvalidArgumentException(e.getMessage());
            }
        }

        @Nullable
        @Override
        protected Void buildResponse(@NonNull JSONObject jsonObject) {
            return null;
        }

    }

//    class PostLikesBuilder extends ApiRequestTemplate<Void> {
//
//        private Mhss.DIAGNOSTIC_TYPE type;
//        private Mhss.SCORES_LIKES like;
//        private String date;
//
//        public PostLikesBuilder(@NonNull Mhss.DIAGNOSTIC_TYPE type, @NonNull Mhss.SCORES_LIKES like, @NonNull String date) {
//            super(apiKey, authClient);
//            this.type = type;
//            this.like = like;
//            this.date = date;
//        }
//
//        public PostLikesBuilder() {
//            super(apiKey, authClient);
//        }
//
//        public PostLikesBuilder setType(Mhss.DIAGNOSTIC_TYPE type) {
//            this.type = type;
//            return this;
//        }
//
//        public PostLikesBuilder setLike(Mhss.SCORES_LIKES like) {
//            this.like = like;
//            return this;
//        }
//
//        public PostLikesBuilder setDate(String date) {
//            this.date = date;
//            return this;
//        }
//
//
//        private String scoresType(Mhss.DIAGNOSTIC_TYPE type) {
//            switch (type) {
//                case ADHD:
//                    return "adhd_like";
//
//                case ANXIETY:
//                    return "anxiety_like";
//
//
//                case DEPRESSION:
//                    return "depression_like";
//
//                case STRESS:
//                    return "stress_like";
//
//                default:
//                    return null;
//            }
//
//        }
//
//        private Boolean getLike(Mhss.SCORES_LIKES like) {
//            switch (like) {
//                case LIKE:
//                    return true;
//                case NEUTRAL:
//                    return null;
//
//                case DISLIKE:
//                    return false;
//
//                default:
//                    return null;
//            }
//
//        }
//
//
//        @Override
//        protected Request buildRequest() {
//            try {
//                return ApiRequestConstructor.constructPostRequestJSON(new JSONObject()
//                                .put(LIKE_TAG, getLike(like)).put("date", date).put(TYPE_TAG, scoresType(type)),
//                        getHeaders(),
//                        ApiRequestConstructor.USER_API + GET_LIKES_PATH).getRequestObj();
//            } catch (JSONException e) {
//                throw new InvalidArgumentException(e.getMessage());
//            }
//        }
//
//        @Nullable
//        @Override
//        protected Void buildResponse(@NonNull JSONObject jsonObject) {
//            return null;
//        }
//
//    }

}
