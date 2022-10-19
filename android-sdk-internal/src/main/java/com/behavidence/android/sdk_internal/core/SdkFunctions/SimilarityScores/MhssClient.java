package com.behavidence.android.sdk_internal.core.SdkFunctions.SimilarityScores;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.RoomDb.BehavidenceSDKInternalDb;
import com.behavidence.android.sdk_internal.core.SdkFunctions.SimilarityScores.Room.Mhssroom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.SimilarityScores.Room.MhssroomDao;
import com.behavidence.android.sdk_internal.core.Utils.AppUtils;
import com.behavidence.android.sdk_internal.core.Utils.Executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface MhssClient {

    /**
     * it will automatically get the Auth from local storage for performing network calls
     * default configuration will be used see @ApiConfig
     *
     * @param context Context
     * @return ScoresClient for events related functionalities
     */
    @NonNull
    static MhssClient getInstance(@NonNull Context context) {
        return new MhssClientImpl(context, ApiKey.getInstance(context), AuthClient.getInstance(context));
    }


    /**
     * put like or dislike mapped to the similarity score in the server
     *
     * @return Executor
     */
//    @NonNull
//    Executor<Void> putScoreLike(@NonNull Mhss mhss, @NonNull Score score);

    @NonNull
    Executor<Void> putScoreLike(@NonNull String mhssId, @NonNull Boolean like,@NonNull String date);


    /**
     * returning all the similarities scores present in the server for the given user
     *
     * @return Executor
     */
    @NonNull
    Executor<List<Mhss>> getSimilarityScoresFromServer();

    /**
     * @return latest similarity score of the given user calculated by the server
     */
    @NonNull
    Executor<Mhss> getSimilarityScoreFromServer();

    @NonNull
    List<Mhss> getLocalMhss(int limit);

    @NonNull
    List<Mhss> getLocalMhssDES(int limit);

    /**
     * @param date in pattren yyyy-mm-dd
     * @return Mhss
     */
    @NonNull
    Mhss getLocalMhss(@NonNull String date);

    @NonNull
    List<Mhss> getLocalMhss(@NonNull String startDate, @NonNull String endDate);

    void deleteLocalMhss();

}

class MhssClientImpl implements MhssClient {


    private final MhssroomDao mhssroomDao;
    private final ScoresApiRequest scoresApiRequest;


    public MhssClientImpl(@NonNull Context context, @NonNull ApiKey apiKey, @NonNull AuthClient authClient) {
        scoresApiRequest = new ScoresApiRequest(authClient, apiKey);
        mhssroomDao = BehavidenceSDKInternalDb.getInstance(context).mhssroomDao();
    }


//    @NonNull
//    @Override
//    public Executor<Void> putScoreLike(@NonNull Mhss mhss, @NonNull Score score) {
//        return new Executor<>(() -> {
//            scoresApiRequest.new PostLikesBuilder(score.getScoreType(), score.getScoreLike(), mhss.getDateString())
//                    .buildApiCaller()
//                    .callApiGetResponse();
//            mhssroomDao.insert(((MHSSImpl) mhss).getMhssroom());
//            return null;
//        });
//    }

    @NonNull
    @Override
    public Executor<Void> putScoreLike(@NonNull String mhssId, @NonNull Boolean like, @NonNull String date) {
        return new Executor<>(() -> {
            scoresApiRequest.new PostLikesBuilder(mhssId, like, date)
                    .buildApiCaller()
                    .callApiGetResponse();

            int likeVal = like ? 1 : -1;

            mhssroomDao.setLike(likeVal, mhssId, date);
            return null;
        });
    }

    @NonNull
    @Override
    public Executor<List<Mhss>> getSimilarityScoresFromServer() {
        return new Executor<>(() -> {
            List<Mhssroom> mhssrooms = scoresApiRequest.new GetScoresBuilder(true).buildApiCaller().callApiGetResponse();
            if (mhssrooms.size() < 1) return new ArrayList<Mhss>();
            mhssroomDao.insert(mhssrooms);

            Map<Long, Mhss> mhssMap = new HashMap<>();
            for(Mhssroom mhss: mhssrooms) {
                Log.d("MhssClientCheck", "Works");
                if (mhssMap.containsKey(mhss.getTimestamp()))
                    mhssMap.get(mhss.getTimestamp()).addMhssRoom(mhss);
                else mhssMap.put(mhss.getTimestamp(), new MHSSImpl(mhss, mhss.getTimestamp()));
            }

            return new ArrayList<>(mhssMap.values());

//            List<Mhss> mhsses = new ArrayList<>();
//            for (Mhssroom mhssroom : mhssrooms)
//                mhsses.add(new MHSSImpl(mhssroom));
//            return mhsses;
        });
    }

    @NonNull
    @Override
    public Executor<Mhss> getSimilarityScoreFromServer() {
        return new Executor<>(() -> {
            List<Mhssroom> mhssrooms = scoresApiRequest.new GetScoresBuilder(false).buildApiCaller().callApiGetResponse();
            if (mhssrooms.size() < 1) return null;
            mhssroomDao.insert(mhssrooms);

            return new MHSSImpl(mhssroomDao.getMhss(1, false));
//            return new MHSSImpl(mhssrooms.get(0));
        });
    }

    @NonNull
    @Override
    public List<Mhss> getLocalMhss(int limit) {
        return getLocalMhssDES(limit, true);
    }

    @NonNull
    @Override
    public List<Mhss> getLocalMhssDES(int limit) {
        return getLocalMhssDES(limit, false);
    }

    @NonNull
    public List<Mhss> getLocalMhssDES(int limit, boolean asc) {
        if (limit < 1 || limit > 10000)
            throw new InvalidArgumentException("Invalid limit");

//        List<Mhssroom> mhssrooms = null;
//        if (asc)
//            mhssrooms = mhssroomDao.getMhss(limit);
//        else
//            mhssrooms = mhssroomDao.getMhssDES(limit);

        List<Mhssroom> mhssrooms = mhssroomDao.getMhss(limit, asc);

        if (mhssrooms.size() < 1) return new ArrayList<>();
        Map<Long, Mhss> mhssMap = new LinkedHashMap<>();


        for(Mhssroom mhss: mhssrooms)
            if(mhssMap.containsKey(mhss.getTimestamp()))
                mhssMap.get(mhss.getTimestamp()).addMhssRoom(mhss);
            else mhssMap.put(mhss.getTimestamp(), new MHSSImpl(mhss, mhss.getTimestamp()));

        return new ArrayList<>(mhssMap.values());
    }

    @NonNull
    @Override
    public Mhss getLocalMhss(@NonNull String date) {
        return new MHSSImpl(mhssroomDao.getMhss(date));
    }

    @NonNull
    @Override
    public List<Mhss> getLocalMhss(@NonNull String startDate, @NonNull String endDate) {
        try {
            long start = AppUtils.getDateRange(startDate)[0];
            long end = AppUtils.getDateRange(endDate)[1];
            if (end >= start)
                throw new InvalidArgumentException("End time should be less than start");
            List<Mhssroom> mhssrooms = mhssroomDao.getMhss(start, end);
            if (mhssrooms.size() < 1) return new ArrayList<Mhss>();
//            List<Mhss> mhsses = new ArrayList<>();
//            for (Mhssroom mhssroom : mhssrooms)
//                mhsses.add(new MHSSImpl(mhssroom));
//            return mhsses;

            Map<Long, Mhss> mhssMap = new HashMap<>();
            for(Mhssroom mhss: mhssrooms)
                if(mhssMap.containsKey(mhss.getTimestamp()))
                    mhssMap.get(mhss.getTimestamp()).addMhssRoom(mhss);
                else mhssMap.put(mhss.getTimestamp(), new MHSSImpl(mhss, mhss.getTimestamp()));

            return new ArrayList<>(mhssMap.values());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidArgumentException("Invalid dates");
        }

    }

    @Override
    public void deleteLocalMhss() {
        mhssroomDao.deleteAllMhss();
    }

}
