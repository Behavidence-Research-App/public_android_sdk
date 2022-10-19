package com.behavidence.android.sdk_internal.core.SdkFunctions.Graphs;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import androidx.annotation.NonNull;
import com.behavidence.android.sdk_internal.core.RoomDb.BehavidenceSDKInternalDb;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Graphs.Room.AppTime;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Graphs.Room.GraphsDao;
import com.behavidence.android.sdk_internal.core.Utils.AppUtils;
import com.behavidence.android.sdk_internal.core.Utils.Executor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public interface GraphClient {
    static GraphClient getInstance(@NonNull Context context){
        return new GraphClientImpl(context);
    }

    String NO_DATA="NoData";
    Executor<Void> refreshGraphData();

    List<AppTime> loadAll();

    List<AppTime> getAppsTimeRange(long min, long max);

    List<AppTime> getAppRange(long min, long max, String name);

    long loadSumTime(long min, long max);

    List<AppTime> isPresent(long time);

    Cursor getSortedAppList(long timeStart, long timeEnd);

   Cursor getSortedFreqList(long timeStart, long timeEnd);

   List<Long> getDistributionTimeList(long timeStart, long timeEnd);

    List<Long> getDistributionFreqList(long timeStart, long timeEnd);

    Cursor get1DistributionTime(long timeStart, long timeEnd, String name);

    Cursor get1DistributionFreq(long timeStart, long timeEnd, String name);

    long getTotalTime(long timeStart, long timeEnd);

    long getTotalTimeWApp(long timeStart, long timeEnd, String appName);

      long getTotalFreqWApp(long timeStart, long timeEnd, String appName);

      long getTotalTimeApp(long timeStart, String appName);

    long getTotalFreq(long timeStart, long timeEnd);
    long getTotalFreqApp(long timeStart, String appName);

    void deleteAll();
    void deleteRange(long time);


}

class GraphClientImpl implements GraphClient {
    private final Context context;
    private final GraphsDao graphsDao;

    public GraphClientImpl(@NonNull Context context) {
        this.context = context;
        this.graphsDao=BehavidenceSDKInternalDb.getInstance(context)
                .graphsDao();
    }


    @Override
    public Executor<Void> refreshGraphData() {
       return new Executor<>(() -> initRefresh(context));
    }

    @Override
    public List<AppTime> loadAll() {
        return graphsDao.loadAll();
    }

    @Override
    public List<AppTime> getAppsTimeRange(long min, long max) {
        return graphsDao.getAppsTimeRange(min,max);
    }

    @Override
    public List<AppTime> getAppRange(long min, long max, String name) {
        return graphsDao.getAppRange(min,max,name);
    }

    @Override
    public long loadSumTime(long min, long max) {
        return graphsDao.loadSumTime(min,max);
    }

    @Override
    public List<AppTime> isPresent(long time) {
        return graphsDao.isPresent(time);
    }

    @Override
    public Cursor getSortedAppList(long timeStart, long timeEnd) {
        return graphsDao.getSortedAppList(timeStart,timeEnd);
    }

    @Override
    public Cursor getSortedFreqList(long timeStart, long timeEnd) {
        return graphsDao.getSortedFreqList(timeStart,timeEnd);
    }

    @Override
    public List<Long> getDistributionTimeList(long timeStart, long timeEnd) {
        return graphsDao.getDistributionTimeList(timeStart,timeEnd);
    }

    @Override
    public List<Long> getDistributionFreqList(long timeStart, long timeEnd) {
        return graphsDao.getDistributionFreqList(timeStart,timeEnd);
    }

    @Override
    public Cursor get1DistributionTime(long timeStart, long timeEnd, String name) {
        return graphsDao.get1DistributionTime(timeStart,timeEnd,name);
    }

    @Override
    public Cursor get1DistributionFreq(long timeStart, long timeEnd, String name) {
        return graphsDao.get1DistributionFreq(timeStart,timeEnd,name);
    }

    @Override
    public long getTotalTime(long timeStart, long timeEnd) {
        return graphsDao.getTotalTime(timeStart,timeEnd);
    }

    @Override
    public long getTotalTimeWApp(long timeStart, long timeEnd, String appName) {
        return graphsDao.getTotalTimeWApp(timeStart,timeEnd,appName);
    }

    @Override
    public long getTotalFreqWApp(long timeStart, long timeEnd, String appName) {
        return graphsDao.getTotalFreqWApp(timeStart,timeEnd,appName);
    }

    @Override
    public long getTotalTimeApp(long timeStart, String appName) {
        return graphsDao.getTotalTimeApp(timeStart,appName);
    }

    @Override
    public long getTotalFreq(long timeStart, long timeEnd) {
        return graphsDao.getTotalFreq(timeStart,timeEnd);
    }

    @Override
    public long getTotalFreqApp(long timeStart, String appName) {
        return graphsDao.getTotalFreqApp(timeStart,appName);
    }

    @Override
    public void deleteAll() {
        graphsDao.deleteAll();

    }

    @Override
    public void deleteRange(long time) {
        graphsDao.deleteRange(time);
    }

    synchronized static Void initRefresh(@NonNull Context context) {
        if(AppTime.isTimeZoneChanged(context)) {
            AppTime.deleteAllTime(context);
        }

      //  ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() );
        ArrayList<Long> timeList = AppUtils.getTimeDistribution(AppTime.getLastUpdateTime(context), Calendar.getInstance().getTimeInMillis(), 120 * 60 * 1000 - 1);
        for (int i = 0; i < timeList.size(); i += 2) {
          new UpdateDbThread(timeList.get(i), timeList.get(i + 1),context).run();
        }
      //  executor.shutdown();
//        try {
//            executor.awaitTermination(2, TimeUnit.MINUTES);
//            Log.e("tali","555");
//        } catch (InterruptedException e) {
//            Log.e("sdms","ssss");
//        } finally {
//
//        }
        AppTime.saveUpdateTime(context, timeList.get(timeList.size() - 2));
        return null;
    }

    public static class UpdateDbThread{

        private final long startTime;
        private final long endTime;
        private final Context context;


        UpdateDbThread(long startTime,long endTime,@NonNull Context context){
            this.startTime=startTime;
            this.endTime=endTime;
            this.context=context;
        }

        public void run() {
            Cursor cUsage=
                    BehavidenceSDKInternalDb.getInstance(context).graphsDao().getSortedAppsUsage(startTime,endTime);
            Cursor cFreq=BehavidenceSDKInternalDb.getInstance(context).graphsDao().getSortedAppsFreq(startTime,endTime);
            HashMap<String,AppTime> appTimeHashMap=new HashMap<>();
            while (cUsage.moveToNext()) {
                if(!appTimeHashMap.containsKey(cUsage.getString(1)))
                    appTimeHashMap.put(cUsage.getString(1),new AppTime(cUsage.getString(1)));
                appTimeHashMap.get(cUsage.getString(1)).totalTime=cUsage.getLong(0);
            }
            Log.e("tali11","bbb");
            while (cFreq.moveToNext()) {
                if(!appTimeHashMap.containsKey(cFreq.getString(1)))
                    appTimeHashMap.put(cFreq.getString(1),new AppTime(cFreq.getString(1)));
                appTimeHashMap.get(cFreq.getString(1)).frequency= (int) cFreq.getLong(0);
            }

            if(appTimeHashMap.size()>0){
                for (Map.Entry<String, AppTime> entry : appTimeHashMap.entrySet()) {
                    entry.getValue().timeInMillisecond = startTime;
                    enterInDataBase(entry.getValue(),context);
                }
            }else{
                AppTime appTime=new AppTime(NO_DATA);
                appTime.timeInMillisecond=startTime;
                enterInDataBase(appTime,context);
            }
            //end of if

        }//end of run
    }

     static   synchronized void enterInDataBase(AppTime appTime,@NonNull Context context){
        BehavidenceSDKInternalDb.getInstance(context).graphsDao().insert(appTime);
    }
}
