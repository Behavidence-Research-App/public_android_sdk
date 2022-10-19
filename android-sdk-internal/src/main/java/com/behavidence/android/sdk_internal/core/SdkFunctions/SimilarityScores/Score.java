package com.behavidence.android.sdk_internal.core.SdkFunctions.SimilarityScores;

import androidx.annotation.NonNull;
import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;
import com.behavidence.android.sdk_internal.core.SdkFunctions.SimilarityScores.Room.Mhssroom;

//public interface Score {
//
//    int getScoreVal();
//    @NonNull
//    Mhss.SCORES_LIKES getScoreLike();
//    void setScoreLike(@NonNull Mhss.SCORES_LIKES scoreLike);
//    Mhss.DIAGNOSTIC_TYPE getScoreType();
//}
//
//class ScoreImpl implements Score{
//
//    private final int scoresVal;
//    private  Mhss.SCORES_LIKES likes;
//    private  final Mhss.DIAGNOSTIC_TYPE diagnostic_type;
//
//    public ScoreImpl(int scoresVal, int like, Mhss.DIAGNOSTIC_TYPE diagnostic_type) {
//        this.scoresVal = scoresVal;
//        this.likes = convertIntToScoreLike(like);
//        this.diagnostic_type=diagnostic_type;
//    }
//
//    @Override
//    public int getScoreVal() {
//        return scoresVal;
//    }
//    static protected Mhss.SCORES_LIKES convertIntToScoreLike(int like){
//        switch (like){
//            case Mhssroom.LIKE:
//                 return Mhss.SCORES_LIKES.LIKE;
//
//                 case Mhssroom.DISLIKE:
//                     return Mhss.SCORES_LIKES.DISLIKE;
//
//            case Mhssroom.NEUTRAL:
//                return Mhss.SCORES_LIKES.NEUTRAL;
//
//            default:
//                throw new InvalidArgumentException("Invalid like val");
//
//        }
//    }
//
//    static protected int convertScoreLikeToInt(Mhss.SCORES_LIKES like){
//            if(like==Mhss.SCORES_LIKES.LIKE)
//                return   Mhssroom.LIKE;
//
//            if(like==Mhss.SCORES_LIKES.DISLIKE)
//                return Mhssroom.DISLIKE;
//
//            if(like==Mhss.SCORES_LIKES.NEUTRAL)
//                 return Mhssroom.NEUTRAL;
//
//            throw new InvalidArgumentException("Invalid like val");
//    }
//
//    @NonNull
//    @Override
//    public Mhss.SCORES_LIKES getScoreLike() {
//        return likes;
//    }
//
//    @Override
//    public void setScoreLike(@NonNull Mhss.SCORES_LIKES scoreLike) {
//
//        this.likes=scoreLike;
//    }
//
//    @Override
//    public Mhss.DIAGNOSTIC_TYPE getScoreType() {
//        return diagnostic_type;
//    }
//
//}
