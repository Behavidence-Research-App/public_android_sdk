package com.behavidence.android.sdk_internal;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;
import android.app.AppOpsManager;
import android.content.Context;
import android.os.Process;
import androidx.annotation.NonNull;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.EventsClient;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Events.TimeZoneClient;
import com.behavidence.android.sdk_internal.core.Utils.Executor;

public class BehavidenceSDK {




    /**
     *
     * @param context Context
     * @param apiSecret Api Secret got from Behavidence Developers
     */
    public static synchronized void init(@NonNull Context context,@NonNull String apiSecret){
       TimeZoneClient.getInstance().logTimeZone(context);
        ApiKey.saveApiKey(context,apiSecret);
    }

    /**
     * When you call autoInitUpload(context,auth)
     * Must call this function on opening of the app or when device is unlocked to refresh state of the SDK, on api level 30 and above
     * otherwise data will not be uploaded
     * @param c Context
     * @return Executor to execute either on main thread or worker thread. See Executor
     */
    @NonNull
    public static Executor<Void> refreshState(@NonNull Context c){
        return EventsClient.getInstance(c).refreshEvents();
    }

    /**
     *
     * @param context Context
     * @return true if permission is enabled
     */
    public static  boolean isAccessPermissionEnabled(@NonNull Context context){
            AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            if(appOps==null)
                return false;
            int mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS, Process.myUid(), context.getPackageName());
            return mode == MODE_ALLOWED;
        }

}



