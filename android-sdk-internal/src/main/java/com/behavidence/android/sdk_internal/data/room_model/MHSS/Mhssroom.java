package com.behavidence.android.sdk_internal.data.room_model.MHSS;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;


@Entity(primaryKeys = {"id", "date"})
public class Mhssroom {

    @Ignore
    public static final int LIKE = 1;
    @Ignore
    public static final int DISLIKE = -1;
    @Ignore
    public static final int NEUTRAL = 0;
    @Ignore
    public static final int NOT_INIT = -1;

    @NonNull
    private final String id;
    @NonNull
    private final String date;

    private final int score;
    private final long timestamp;
    private int like;

    public Mhssroom(@NonNull String id, @NonNull String date, int score, long timestamp) {
        this.id = id;
        this.date = date;
        this.score = score;
        this.timestamp = timestamp;
        this.like = NEUTRAL;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getScore() {
        return score;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "Mhssroom{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", score=" + score +
                ", timestamp=" + timestamp +
                ", like=" + like +
                '}';
    }
}

//@Entity
//public class Mhssroom {
//
//    @Ignore
//    public static final int LIKE=1;
//    @Ignore
//    public static final int DISLIKE=-1;
//    @Ignore
//    public static final int NEUTRAL=0;
//
//    @Ignore
//    public static final int NOT_INIT=-1;
//
//    @PrimaryKey
//    @NonNull
//    private final String dateStr;
//    private final long dateTimeStamp;
//    private  final int depScores;
//    private final int anxietyScores;
//    private final int adhdScores;
//    private final int stressScores;
//    private int depLike;
//    private int anxietyLike;
//    private int adhdLike;
//    private int stressLike;
//
//    public Mhssroom(@NonNull String dateStr, long dateTimeStamp, int depScores, int anxietyScores, int adhdScores, int stressScores, int depLike, int anxietyLike, int adhdLike, int stressLike) {
//        this.dateStr = dateStr;
//        this.dateTimeStamp = dateTimeStamp;
//        this.depScores = depScores;
//        this.anxietyScores = anxietyScores;
//        this.adhdScores = adhdScores;
//        this.stressScores = stressScores;
//        this.depLike = depLike;
//        this.anxietyLike = anxietyLike;
//        this.adhdLike = adhdLike;
//        this.stressLike = stressLike;
//    }
//
//    @Ignore
//    public Mhssroom(@NonNull String dateStr, long dateTimeStamp, int depScores, int anxietyScores, int adhdScores,int stressScores) {
//        this.dateStr = dateStr;
//        this.dateTimeStamp = dateTimeStamp;
//        this.depScores = depScores;
//        this.anxietyScores = anxietyScores;
//        this.adhdScores = adhdScores;
//        this.stressScores=stressScores;
//    }
//
//    @NonNull
//    public String getDateStr() {
//        return dateStr;
//    }
//
//    public long getDateTimeStamp() {
//        return dateTimeStamp;
//    }
//
//    public int getDepScores() {
//        return depScores;
//    }
//
//    public int getAnxietyScores() {
//        return anxietyScores;
//    }
//
//    public int getAdhdScores() {
//        return adhdScores;
//    }
//
//    public int getStressScores() {
//        return stressScores;
//    }
//
//    public int getDepLike() {
//        return depLike;
//    }
//
//    public int getAnxietyLike() {
//        return anxietyLike;
//    }
//
//    public int getAdhdLike() {
//        return adhdLike;
//    }
//
//    public int getStressLike() {
//        return stressLike;
//    }
//
//    @Ignore
//    public void setStressLike(int stressLike) {
//        this.stressLike = stressLike;
//    }
//
//    @Ignore
//    public void setDepLike(int depLike) {
//        this.depLike = depLike;
//    }
//
//    @Ignore
//    public void setAnxietyLike(int anxietyLike) {
//        this.anxietyLike = anxietyLike;
//    }
//
//    @Ignore
//    public void setAdhdLike(int adhdLike) {
//        this.adhdLike = adhdLike;
//    }
//}
