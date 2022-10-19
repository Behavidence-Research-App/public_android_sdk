package com.behavidence.android.sdk_internal.core.SdkFunctions.Events;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import com.behavidence.android.sdk_internal.BehavidenceSDK;
import com.behavidence.android.sdk_internal.core.Exceptions.PermissionException;
import com.behavidence.android.sdk_internal.core.RoomDb.BehavidenceSDKInternalDb;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.App;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room.AppSession;
import com.behavidence.android.sdk_internal.core.Utils.AppUtils;


interface AppSessionsIO {

    @NonNull
    static AppSessionsIO getInstance(@NonNull Context context,@NonNull AppsIO appsIO){
        return new AppSessionIOImpl(context,appsIO);
    }


    @NonNull
     List<AppSession> getAppSessions(long lastTimeUpload);

     void refreshAppSessions();

     long getLastTimeSessionUpload();

     void setLastTimeSessionUpload(long time);

     void deleteLastTimeSessionUpload();



}

class AppSessionIOImpl implements AppSessionsIO{

    public static final String LAST_TIME_DB_UPLOAD="behavidencelasttimeup";

    private final Context context;
    private final AppsIO appsIO;

    public AppSessionIOImpl(@NonNull Context context,@NonNull AppsIO appsIO) {
        this.context = context;
        this.appsIO=appsIO;
    }

    public Context getContext() {
        return context.getApplicationContext();
    }

    public AppsIO getAppsIO() {
        return appsIO;
    }

    public void deletePendingSessions(long beforeTime){
  BehavidenceSDKInternalDb.getInstance(context)
          .appSessionDao().deletePendingSessions(beforeTime);
    }

    @NonNull
    @Override
    public List<AppSession> getAppSessions(long lastTimeUpload) {
        List<AppSession> appSessions= BehavidenceSDKInternalDb.getInstance(context)
                .appSessionDao().getSessions(lastTimeUpload);
        if(appSessions==null)
            appSessions=new ArrayList<AppSession>();
        return appSessions;

    }


    @Override
    public void refreshAppSessions() {
        if(!BehavidenceSDK.isAccessPermissionEnabled(context))
            throw new PermissionException(PermissionException.PERMISSION_TYPE.USAGE_ACCESS,"Access Permission Denied. Please grant access permission");
        EventsLog.refreshAppSessions(this);
    }

    @Override
    public long getLastTimeSessionUpload(){
        long time= context.getSharedPreferences(LAST_TIME_DB_UPLOAD,Context.MODE_PRIVATE)
                .getLong(LAST_TIME_DB_UPLOAD,0);
        if(time==0)
            return AppUtils.getDayRange(-6)[0];
        return time;


    }

    @Override
    public void setLastTimeSessionUpload(long time){
        saveLastTimeUpload(context,time);

    }

    @Override
    public void deleteLastTimeSessionUpload() {
        deleteLastTimeUpload(context);
    }

    private void putAppSessions(@NonNull List<AppSession> appSessions){
     BehavidenceSDKInternalDb.getInstance(context)
             .appSessionDao().insert(appSessions);
    }
    private static synchronized void deleteLastTimeUpload(Context context){
        context.getSharedPreferences(LAST_TIME_DB_UPLOAD,Context.MODE_PRIVATE)
                .edit().clear().apply();
    }
    private static synchronized void saveLastTimeUpload(@NonNull Context context,long lastTime){
        //we will save time in utc to avoid confusion
        context.getSharedPreferences(LAST_TIME_DB_UPLOAD,Context.MODE_PRIVATE)
                .edit()
                .putLong(LAST_TIME_DB_UPLOAD,lastTime)
                .apply();
    }

    @NonNull
    private List<AppSession> getIncompleteSessions(){
        return BehavidenceSDKInternalDb.getInstance(context)
                .appSessionDao().getPendingSessions();
    }










    private static class EventsLog{


        public static final int MAX_TIME_LIMIT=900;
        public static final int MAX_START_LIMIT=1000*60*40;
        private static final String UPDATE_LAST_SESSION_TIME="lastSessionTime";


        private static HashSet<String> getAllApps(@NonNull  AppsIO appsIO){
            List<App> apps=appsIO.getAllApps();
            HashSet<String> packages=new HashSet<>();
            if(apps.size()<1)
                return packages;
            //adding all apps into hashset for performance purposes
            for(App app:apps)
                packages.add(app.getPackageName());
            return  packages;
        }





        private static synchronized long getLastTimeSessionQueried(@NonNull Context context){
            return context.getSharedPreferences(UPDATE_LAST_SESSION_TIME,Context.MODE_PRIVATE)
                    .getLong(UPDATE_LAST_SESSION_TIME, AppUtils.getDayRange(-6)[0]);
        }

        private static synchronized void saveLastTimeSessionQueried(@NonNull Context context,long time){
            context.getSharedPreferences(UPDATE_LAST_SESSION_TIME,Context.MODE_PRIVATE)
                    .edit().putLong(UPDATE_LAST_SESSION_TIME,time).apply();

        }



        private synchronized static void refreshAppSessions(@NonNull AppSessionIOImpl appSessionsIO){

            long startTime=getLastTimeSessionQueried(appSessionsIO.getContext());

            long endTime= Calendar.getInstance().getTimeInMillis();


            //getting all apps which have been saved before
            HashSet<String> validApp=getAllApps(appSessionsIO.getAppsIO());
            //apps which are strickly system apps or not openable apps
            HashSet<String> inValidApps=new HashSet<>();
            //apps which are not in database and need to be put
            HashSet<String> remainingCategories=new HashSet<>();
            //app session hashmap
            HashMap<String, Stack<AppSession>> stringStackHashMap=new HashMap<>();

            //hashmap to store open time of apps
            HashMap<String, Long> startEventsMap = new HashMap<>();

            //hashmap to store close time of app
            HashMap<String,Long> closeTimeMap=new HashMap<>();

            //getting pending app sessions
            getStartList(appSessionsIO,startEventsMap,startTime);

            //minimum api level is 21
            UsageStatsManager usageStatsManager;


            //checking for permissions
            try {
                    usageStatsManager = (UsageStatsManager) appSessionsIO.getContext().getSystemService(Context.USAGE_STATS_SERVICE);
                UsageEvents.Event currentEvent;
                UsageEvents usageEvents = usageStatsManager.queryEvents(startTime, endTime);
                PackageManager packageManager = appSessionsIO.getContext().getPackageManager();


                while (usageEvents.hasNextEvent()){
                    currentEvent = new UsageEvents.Event();
                    usageEvents.getNextEvent(currentEvent);

                    //only sessions of screen time of apps
                    if ((currentEvent.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED ||
                            currentEvent.getEventType() == UsageEvents.Event.ACTIVITY_PAUSED)) {


                        //if it is not a valid app means not apenable etc then leave it
                        if(inValidApps.contains(currentEvent.getPackageName()))
                            continue;
                            //if the app is openable and a valid app then continue getting the app information
                        else if(validApp.contains(currentEvent.getPackageName()));
                            //checking for the new app
                        else if(!openable(packageManager,currentEvent.getPackageName())) {
                            inValidApps.add(currentEvent.getPackageName());
                            continue;
                        }
                        else {
                            //the app which is not present in the database
                            remainingCategories.add(currentEvent.getPackageName());
                            //put in the valid app for performance purposes
                            validApp.add(currentEvent.getPackageName());
                        }

                        //if no package name is present then make the list and attach it with package name
                        if (!stringStackHashMap.containsKey(currentEvent.getPackageName()))
                            stringStackHashMap.put(currentEvent.getPackageName(), new Stack<>());


                        //if event is paused means it is close then we check if event has started in given time frame
                        if (currentEvent.getEventType() == UsageEvents.Event.ACTIVITY_PAUSED && startEventsMap.containsKey(currentEvent.getPackageName())) {
                            //putting in close hashmap

                            closeTimeMap.put(currentEvent.getPackageName(), currentEvent.getTimeStamp());
                            //removing from start event

                            //filling the values in hashmap
                            stringStackHashMap.get(currentEvent.getPackageName()).push(new AppSession(currentEvent.getPackageName(),startEventsMap.remove(currentEvent.getPackageName()),currentEvent.getTimeStamp()));

                        } else if (currentEvent.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED) {



                            if (closeTimeMap.containsKey(currentEvent.getPackageName()) && (currentEvent.getTimeStamp() - closeTimeMap.get(currentEvent.getPackageName()) <= MAX_TIME_LIMIT))
                                startEventsMap.put(currentEvent.getPackageName(), stringStackHashMap.get(currentEvent.getPackageName()).pop().getStartTime());
                            else
                                startEventsMap.put(currentEvent.getPackageName(), currentEvent.getTimeStamp());

                            closeTimeMap.remove(currentEvent.getPackageName());
                        }


                        //getting keyboard inputs
                    }



                }//end of while

                //saving all pending sessions which are started but pending
                for (Map.Entry<String, Long> entry : startEventsMap.entrySet())
                {
                    //if session is too long do not save it
                    //end time and start time of the session
                    if(endTime-entry.getValue()>MAX_START_LIMIT)
                        continue;
                    if(!stringStackHashMap.containsKey(entry.getKey()))
                        stringStackHashMap.put(entry.getKey(),new Stack<>());
                    stringStackHashMap.get(entry.getKey()).push(new AppSession(entry.getKey(), entry.getValue(), -1));
                }



                //saving all session time
                for (Map.Entry<String, Stack<AppSession>> entry : stringStackHashMap.entrySet())
                    appSessionsIO.putAppSessions(entry.getValue());




                saveApps(appSessionsIO.getAppsIO(),remainingCategories);
                //deleting all pending sessions of 30 minutes
                appSessionsIO.deletePendingSessions((endTime-MAX_START_LIMIT));
                saveLastTimeSessionQueried(appSessionsIO.getContext(),endTime);


            }catch (Exception e){

            }



        }

        private static void saveApps(@NonNull AppsIO appsIO,@NonNull HashSet<String> apps){
            List<App> appCategories=new ArrayList<>();
            for(String app:apps)
                appCategories.add(new App(app,0,0,null,0,null));
            appsIO.putApps(appCategories);
        }




        //function to get all events which are started but not ended yet
        private static void getStartList(@NonNull AppSessionIOImpl appSessionsIO,HashMap<String,Long> startHashMap,long startTime){
            //lets make dummy applist
            List<AppSession> appSessions= appSessionsIO.getIncompleteSessions();
            for(AppSession appSession:appSessions){
                //if the starttime of today - start time of pending session is less than specific time interval eg 30 min then leave that session
                if(startTime-appSession.getStartTime()<=MAX_START_LIMIT)
                    startHashMap.put(appSession.getPackageName(),appSession.getStartTime());
            }
        }


    }

    private static boolean openable(PackageManager packageManager, String packageName) {
        return packageManager.getLaunchIntentForPackage(packageName) != null;
    }


}


