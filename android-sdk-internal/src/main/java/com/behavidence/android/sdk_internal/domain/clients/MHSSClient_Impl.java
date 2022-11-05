package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.Utils.AppUtils;
import com.behavidence.android.sdk_internal.data.interfaces.MHSSService;
import com.behavidence.android.sdk_internal.data.model.MHSS.MHSSResponse;
import com.behavidence.android.sdk_internal.data.model.MHSS.ScoreBody;
import com.behavidence.android.sdk_internal.data.model.MHSS.ScoreLikeBody;
import com.behavidence.android.sdk_internal.data.model.MHSS.ScoreList;
import com.behavidence.android.sdk_internal.data.room_model.MHSS.LabelEntity;
import com.behavidence.android.sdk_internal.data.room_model.MHSS.Mhssroom;
import com.behavidence.android.sdk_internal.domain.interfaces.MHSSClient;
import com.behavidence.android.sdk_internal.domain.model.MHSS.Mhss_Model;
import com.behavidence.android.sdk_internal.domain.model.MHSS.Score_Model;
import com.behavidence.android.sdk_internal.domain.model.MHSS.interfaces.Mhss;
import com.behavidence.android.sdk_internal.domain.model.MHSS.interfaces.Score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class MHSSClient_Impl extends ClientParent implements MHSSClient {

    private MHSSService _service;

    public MHSSClient_Impl(Context context) {
        super(context);
        _service = services.mhss();
    }

    private void getMhssHistory(BehavidenceClientCallback<Boolean> callback){
        _service.getMHSSHistory(new BehavidenceClientCallback<MHSSResponse>() {
            @Override
            public void onSuccess(MHSSResponse response) {

                if(response == null) return;

                List<ScoreList> scoreList = response.getData().getScores();
                List<Mhssroom> mhss = new ArrayList<>();
                String date = "2000-01-01";

                for(ScoreList list: scoreList){
                    date = list.getDate();
                    List<ScoreBody> scores = list.getScores();
                    for(ScoreBody score: scores){
                        mhss.add(new Mhssroom(
                                score.getId(),
                                date,
                                score.getScore(),
                                AppUtils.getDateRange(date)[0]
                        ));
                    }
                }

                database.mhssroomDao().insert(mhss);
                callback.onSuccess(true);

            }

            @Override
            public void onFailure(String message) {
                callback.onFailure("Failed to sync score");
            }
        });
    }


    @Nullable
    @Override
    public void getMhss(@NonNull String date, BehavidenceCallback<Mhss> callback) {
        getMhssHistory(new BehavidenceClientCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                List<Mhssroom> list = database.mhssroomDao().getMhss(date);
                if(list.isEmpty()) callback.onFailure("No Scores Found");
                else callback.onSuccess(getSortedMhss(list).get(0));
            }

            @Override
            public void onFailure(String message) {
                List<Mhssroom> list = database.mhssroomDao().getMhss(date);
                if(list.isEmpty()) callback.onFailure("No Scores Found - " + message);
                else callback.onSuccess(getSortedMhss(list).get(0));
            }
        });
    }

    @NonNull
    @Override
    public void getMhssInDateRange(@NonNull String start, @NonNull String end, BehavidenceCallback<List<Mhss>> callback) {
        getMhssHistory(new BehavidenceClientCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                List<Mhssroom> list = database.mhssroomDao().getMhss(start, end);
                if(list.isEmpty()) callback.onFailure("No Scores Found");
                else callback.onSuccess(getSortedMhss(list));
            }

            @Override
            public void onFailure(String message) {
                List<Mhssroom> list = database.mhssroomDao().getMhss(start, end);
                if(list.isEmpty()) callback.onFailure("No Scores Found - " + message);
                else callback.onSuccess(getSortedMhss(list));
            }
        });
    }

    @NonNull
    @Override
    public void getMhssInTimeRange(@NonNull Long startTimeInMilli, @NonNull Long endTimeInMilli, BehavidenceCallback<List<Mhss>> callback) {
        getMhssHistory(new BehavidenceClientCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                List<Mhssroom> list = database.mhssroomDao().getMhss(startTimeInMilli, endTimeInMilli);
                if(list.isEmpty()) callback.onFailure("No Scores Found");
                else callback.onSuccess(getSortedMhss(list));
            }

            @Override
            public void onFailure(String message) {
                List<Mhssroom> list = database.mhssroomDao().getMhss(startTimeInMilli, endTimeInMilli);
                if(list.isEmpty()) callback.onFailure("No Scores Found - " + message);
                else callback.onSuccess(getSortedMhss(list));
            }
        });
    }

    @NonNull
    @Override
    public void getAllMhss(BehavidenceCallback<List<Mhss>> callback) {
        getMhssHistory(new BehavidenceClientCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                List<Mhssroom> list = database.mhssroomDao().getMhss(Integer.MAX_VALUE, false);
                if(list.isEmpty()) callback.onFailure("No Scores Found");
                else callback.onSuccess(getSortedMhss(list));
            }

            @Override
            public void onFailure(String message) {
                List<Mhssroom> list = database.mhssroomDao().getMhss(Integer.MAX_VALUE, false);
                if(list.isEmpty()) callback.onFailure("No Scores Found - " + message);
                else callback.onSuccess(getSortedMhss(list));
            }
        });
    }

    @Override
    public void updateLike(@NonNull Mhss mhss) {
        for(Score score: mhss.getScores()){
            if(score instanceof Score_Model){
                Score_Model scoreModel = (Score_Model) score;
                int like = scoreModel.getLikeState();
                if(like == 0) {
                    database.mhssroomDao().setLike(like, scoreModel.getId(), mhss.getDate_yyyy_mm_dd());
                    continue;
                }
                services.mhss().putScoreLike(new ScoreLikeBody(mhss.getDate_yyyy_mm_dd(), like == 1, scoreModel.getId()),
                        new BehavidenceClientCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean response) {
                        database.mhssroomDao().setLike(like, scoreModel.getId(), mhss.getDate_yyyy_mm_dd());
                    }

                    @Override
                    public void onFailure(String message) {
                        Log.e("BehavidenceError", message);
                    }
                });
            }
        }
    }

    private List<Mhss> getSortedMhss(List<Mhssroom> mhssrooms){

        Map<String, LabelEntity> labelMap = getLatestLabels();

        if (mhssrooms.size() < 1) return new ArrayList<>();
        Map<Long, Mhss_Model> mhssMap = new LinkedHashMap<>();


        for(Mhssroom mhss: mhssrooms) {

            String scoring, title;
            int score = mhss.getScore();
            int priority = 0;

            if(labelMap.containsKey(mhss.getId())){
                LabelEntity label = labelMap.get(mhss.getId());

                if(label != null) {
                    title = label.getLabel();
                    if(label.isReverse())
                        score = 100 - score;
                    if(label.isShowLmh())
                        scoring = scoreThreshold(score);
                    else scoring = score+"";
                    priority = label.getPriority();
                }else{
                    title = "Unlabelled";
                    scoring = score + "";
                }

            }else {
                title = "Unlabelled";
                scoring = score + "";
            }

            Score_Model scoreModel = new Score_Model(mhss.getId(), scoring, title, score, priority);

            if (!mhssMap.containsKey(mhss.getTimestamp()))
                mhssMap.put(mhss.getTimestamp(), new Mhss_Model(mhss.getDate(), mhss.getTimestamp()));
            mhssMap.get(mhss.getTimestamp()).addScore(scoreModel);
        }

        for(Long key: mhssMap.keySet()){

            Mhss_Model mhss = mhssMap.get(key);
            Collections.sort(mhss.getScoreModels(), (o1, o2) -> {
                int p1 = o1.getPriority(), p2 = o2.getPriority();
                return Integer.compare(p2, p1);
            });

        }

        return new ArrayList<>(mhssMap.values());

    }

    private Map<String, LabelEntity> getLatestLabels(){
        Map<String, LabelEntity> labels = new HashMap<>();
        List<LabelEntity> labelEntities = database.labelDao().getLatestLabelsInOrder();

        labels.put("0", new LabelEntity(0, "Anxiety", 3, false, false, 0, ""));
        labels.put("1", new LabelEntity(1, "ADHD", 2, false, false, 0, ""));
        labels.put("2", new LabelEntity(2, "Depression", 1, false, false, 0, ""));
        labels.put("3", new LabelEntity(3, "Stress", 0, false, false, 0, ""));

        for(LabelEntity entity: labelEntities)
            labels.put(entity.getMhssId()+"", entity);

        return labels;
    }

    private String scoreThreshold(int score){

        if(score > 70) return "High";
        else if(score > 30) return "Medium";
        else return "Low";

    }

}
