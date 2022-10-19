package com.behavidence.android.sdk_internal.core.SdkFunctions.SimilarityScores;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.core.RoomDb.BehavidenceSDKInternalDb;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.LabelDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.LabelEntity;
import com.behavidence.android.sdk_internal.core.SdkFunctions.SimilarityScores.Room.Mhssroom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface Mhss{

    void addMhssRoom(Mhssroom mhssroom);

    String getDateString();

    //Map<String, Mhssroom> getMhss();

    Map<String, MappedMhss> getMappedMhss(Context context);
}

class MHSSImpl implements Mhss{

    private final List<Mhssroom> mhssroom = new ArrayList<>();
    private final long timestamp;

    public MHSSImpl(Mhssroom mhss, long timestamp){
        this.mhssroom.add(mhss);
        this.timestamp = timestamp;
    }

    public MHSSImpl(List<Mhssroom> mhss){
        this.mhssroom.addAll(mhss);
        if(mhss.size() > 0)
            this.timestamp = mhss.get(0).getTimestamp();
        else this.timestamp = 0L;
    }

    public void addMhssRoom(Mhssroom mhssroom){
        this.mhssroom.add(mhssroom);
    }

    public String getDateString(){
        if(mhssroom.size() > 0)
            return mhssroom.get(0).getDate();
        else return "";
    }

    public Map<String, Mhssroom> getMhss(){
        Map<String, Mhssroom> map = new TreeMap<>();
        for(Mhssroom mhss: mhssroom){
            map.put(mhss.getId(), mhss);
        }

        return map;
    }

    public Map<String, MappedMhss> getMappedMhss(Context context){
        LabelDao labelDao = BehavidenceSDKInternalDb.getInstance(context).labelDao();

        List<LabelEntity> labels = labelDao.getLatestLabelsInOrder();
        Map<String, Mhssroom> map = getMhss();

        Map<String, MappedMhss> mappedMap = new HashMap<>();
        Map<String, MappedMhss> sortedMap = new LinkedHashMap<>();

        for(String key: map.keySet()){
            int intId = Integer.parseInt(key);
            LabelEntity labelEntity = new LabelEntity(intId, getKnownLabel(key), 1, false, false, 0, null);

            for(LabelEntity label: labels){
                if(label.getMhssId() == intId){
                    labelEntity = label;
                    break;
                }
            }

            mappedMap.put(key, new MappedMhss(map.get(key), labelEntity));
        }

        ArrayList<MappedMhss> list = new ArrayList<>();
        for (Map.Entry<String, MappedMhss> entry : mappedMap.entrySet()) {
            list.add(entry.getValue());
        }

        Collections.sort(list, (o1, o2) -> {
            int p1 = o1.getMhssPriority(), p2 = o2.getMhssPriority();
            return Integer.compare(p2, p1);
        });

        Log.d("MhssCheck", list.toString());

        for (MappedMhss mm : list) {
            for (Map.Entry<String, MappedMhss> entry : mappedMap.entrySet()) {
                if (entry.getValue().equals(mm)) {
                    sortedMap.put(entry.getKey(), mm);
                }
            }
        }

        return sortedMap;

    }

    private String getKnownLabel(String id){
        switch(id){
            case "0": return "Anxiety";
            case "1": return "ADHD";
            case "2": return "Depression";
            case "3": return "Stress";
            default: return "Unlabeled";
        }
    }


}

//public interface Mhss {
//    enum SCORES_LIKES {LIKE,NEUTRAL,DISLIKE}
//    enum  DIAGNOSTIC_TYPE{ANXIETY,DEPRESSION,ADHD,STRESS}
//
//    @NonNull
//    String getDateString();
//
//    @Nullable
//    Score getAnxiety();
//    @Nullable
//    Score getAdhd();
//    @Nullable
//    Score getStress();
//    @Nullable
//    Score getDepression();
//}
//
//class MHSSImpl implements Mhss{
//
//    private final Mhssroom mhssroom;
//    private final Score anxiety;
//    private final Score stress;
//    private final Score adhd;
//    private final Score depression;
//
//     MHSSImpl(@NonNull Mhssroom mhssroom) {
//        this.mhssroom = mhssroom;
//        anxiety=new ScoreImpl(mhssroom.getAnxietyScores(),mhssroom.getAnxietyLike(),DIAGNOSTIC_TYPE.ANXIETY);
//        adhd=new ScoreImpl(mhssroom.getAdhdScores(),mhssroom.getAdhdLike(),DIAGNOSTIC_TYPE.ADHD);
//        depression=new ScoreImpl(mhssroom.getDepScores(),mhssroom.getDepLike(),DIAGNOSTIC_TYPE.DEPRESSION);
//        stress=new ScoreImpl(mhssroom.getStressScores(),mhssroom.getStressLike(),DIAGNOSTIC_TYPE.STRESS);
//    }
//
//    Mhssroom getMhssroom(){
//         mhssroom.setAdhdLike(convertScoreLikeToInt(adhd.getScoreLike()));
//        mhssroom.setAnxietyLike(convertScoreLikeToInt(anxiety.getScoreLike()));
//        mhssroom.setDepLike(convertScoreLikeToInt(depression.getScoreLike()));
//        mhssroom.setStressLike(convertScoreLikeToInt(stress.getScoreLike()));
//         return mhssroom;
//    }
//
//    @NonNull
//    @Override
//    public String getDateString() {
//        return mhssroom.getDate();
//    }
//
//    @NonNull
//    @Override
//    public Score getAnxiety() {
//        return anxiety;
//    }
//
//    @NonNull
//    @Override
//    public Score getAdhd() {
//        return adhd;
//    }
//
//    @NonNull
//    @Override
//    public Score getStress() {
//        return stress;
//    }
//
//    @NonNull
//    @Override
//    public Score getDepression() {
//        return depression;
//    }
//}
