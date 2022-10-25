package com.behavidence.android.sdk_internal.data.room_repository;

import static com.behavidence.android.sdk_internal.core.SdkFunctions.Graphs.GraphClient.NO_DATA;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.behavidence.android.sdk_internal.data.room_model.Graphs.AppTime;

import java.util.List;

@Dao
interface GraphsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AppTime appTimeDao);



    @Query("SELECT * FROM AppTime")
    List<AppTime> loadAll();

    //it will count number of unlock events in given time
    @Query("SELECT *  FROM AppTime WHERE timeInMillisecond BETWEEN :min AND :max")
    List<AppTime> getAppsTimeRange(long min, long max);

    //it will return all unlock event(which contains time in millisecond)
    @Query("SELECT * FROM AppTime WHERE packageName LIKE :name AND timeInMillisecond BETWEEN :min AND :max")
    List<AppTime> getAppRange(long min, long max, String name);

    @Query("SELECT SUM(totalTime) AS 'total' FROM AppTime WHERE timeInMillisecond >= :min AND timeInMillisecond <=:max")
    long loadSumTime(long min, long max);

    //it will return the Unlock event object if event exists
    @Query("SELECT * FROM AppTime WHERE  timeInMillisecond = :time")
    List<AppTime> isPresent(long time);



    @Query("SELECT SUM(totalTime) AS 'Total',packageName FROM AppTime where packageName!='"+ NO_DATA +"'and timeInMillisecond>=:timeStart  AND  timeInMillisecond<=:timeEnd GROUP BY packageName order By Total DESC")
    Cursor getSortedAppList(long timeStart, long timeEnd);

    @Query("SELECT SUM(frequency) AS 'Total',packageName FROM AppTime where packageName!='"+ NO_DATA +"'and  timeInMillisecond>=:timeStart AND  timeInMillisecond<=:timeEnd  GROUP BY packageName order By Total DESC")
    Cursor getSortedFreqList(long timeStart, long timeEnd);

    @Query("SELECT SUM(totalTime) AS 'Total' FROM AppTime where timeInMillisecond>=:timeStart AND  timeInMillisecond<=:timeEnd  GROUP BY timeInMillisecond order By timeInMillisecond")
    List<Long> getDistributionTimeList(long timeStart, long timeEnd);

    @Query("SELECT SUM(frequency) AS 'Total' FROM AppTime where timeInMillisecond>=:timeStart AND  timeInMillisecond<=:timeEnd  GROUP BY timeInMillisecond order By timeInMillisecond")
    List<Long> getDistributionFreqList(long timeStart, long timeEnd);

    @Query("SELECT SUM(totalTime) AS 'Total',timeInMillisecond FROM AppTime where timeInMillisecond>=:timeStart AND  timeInMillisecond<=:timeEnd AND packageName LIKE:name  GROUP BY timeInMillisecond order By timeInMillisecond")
    Cursor get1DistributionTime(long timeStart, long timeEnd, String name);

    @Query("SELECT SUM(frequency) AS 'Total',timeInMillisecond FROM AppTime where timeInMillisecond>=:timeStart AND  timeInMillisecond<=:timeEnd AND packageName LIKE:name  GROUP BY timeInMillisecond order By timeInMillisecond")
    Cursor get1DistributionFreq(long timeStart, long timeEnd, String name);

    @Query("SELECT SUM(totalTime) AS 'Total' FROM AppTime where timeInMillisecond>=:timeStart AND  timeInMillisecond<=:timeEnd")
    long getTotalTime(long timeStart, long timeEnd);

    @Query("SELECT SUM(totalTime) AS 'Total' FROM AppTime where timeInMillisecond>=:timeStart AND  timeInMillisecond<=:timeEnd AND packageName like :appName")
    long getTotalTimeWApp(long timeStart, long timeEnd, String appName);

    @Query("SELECT SUM(frequency) AS 'Total' FROM AppTime where timeInMillisecond>=:timeStart AND  timeInMillisecond<=:timeEnd AND packageName like :appName")
    long getTotalFreqWApp(long timeStart, long timeEnd, String appName);



    @Query("SELECT SUM(totalTime) AS 'Total' FROM AppTime where timeInMillisecond=:timeStart  AND packageName like :appName")
    long getTotalTimeApp(long timeStart, String appName);

    @Query("SELECT SUM(frequency) AS 'Total' FROM AppTime where timeInMillisecond>=:timeStart AND  timeInMillisecond<=:timeEnd")
    long getTotalFreq(long timeStart, long timeEnd);

    @Query("SELECT SUM(frequency) AS 'Total' FROM AppTime where timeInMillisecond=:timeStart AND packageName like :appName")
    long getTotalFreqApp(long timeStart, String appName);

    @Query("Delete FROM AppTime")
    void deleteAll();

    @Query("Delete FROM AppTime WHERE timeInMillisecond >=:time")
    void deleteRange(long time);


    @Query("SELECT SUM(Total) ,packageName FROM (SELECT packageName, CASE WHEN endTime=-1 AND startTime>=:timeStart THEN (:timeEnd-startTime) WHEN startTime<:timeStart THEN (endTime-:timeStart) WHEN endTime>:timeEnd THEN (:timeEnd-startTime) WHEN endTime!=-1 THEN (endTime-startTime) ELSE 0 END AS 'Total' FROM AppSession WHERE startTime<=:timeEnd AND endTime>=:timeStart) GROUP BY packageName order By Total DESC ")
    Cursor getSortedAppsUsage(long timeStart, long timeEnd);
    //getting sum of frequencies  of all the apps group by package name in descending order
    @Query("SELECT COUNT(startTime) AS 'Total',packageName FROM AppSession WHERE startTime>=:timeStart AND startTime<=:timeEnd GROUP BY packageName order By Total DESC ")
    Cursor getSortedAppsFreq(long timeStart, long timeEnd);

    @Query("SELECT COUNT(DISTINCT packageName) AS 'Total' FROM AppSession WHERE startTime>=:timeStart AND startTime<=:timeEnd")
    int countApps(long timeStart, long timeEnd);

    @Query("SELECT COUNT(packageName) AS 'Total' FROM AppSession WHERE startTime>=:timeStart AND startTime<=:timeEnd AND (endTime-startTime)>=:sessionTime AND packageName IN (:cat)")
    int countSpecificApps(long timeStart, long timeEnd, String cat, int sessionTime);


}
