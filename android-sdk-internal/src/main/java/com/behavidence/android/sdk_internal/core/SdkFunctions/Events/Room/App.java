package com.behavidence.android.sdk_internal.core.SdkFunctions.Events.Room;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;


@Entity(primaryKeys = {"packageName"})
public class App{

    @Ignore
    public static final int CATEGORY_FITNESS=11;
    //already uploaded apps
   @Ignore
    public static final int UPLOADED=1;
    @NonNull
    private String packageName;
    private int categoryNo;
    private int customCategorization;
    private String categoryName;
    private double value;
    private String appName;

    @Ignore
    public App() {
        packageName="";
    }

    public App(@NonNull String packageName, int categoryNo, int customCategorization, String categoryName, double value, String appName) {
        this.packageName = packageName;
        this.categoryNo = categoryNo;
        this.customCategorization = customCategorization;
        this.categoryName = categoryName;
        this.value = value;
        this.appName = appName;
    }

    @NonNull
    public String getPackageName() {
        return packageName;
    }

    public int getCategoryNo() {
        return categoryNo;
    }

    public int getCustomCategorization() {
        return customCategorization;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public double getValue() {
        return value;
    }

    public String getAppName() {
        return appName;
    }

    @Ignore
    public App setPackageName(@NonNull String packageName) {
        this.packageName = packageName;
        return this;
    }

    @Ignore
    public App setCategoryNo(int categoryNo) {
        this.categoryNo = categoryNo;
        return this;
    }

   @Ignore
    public App setCustomCategorization(int customCategorization) {
        this.customCategorization = customCategorization;
        return this;
    }

    @Ignore
    public App setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    @Ignore
    public App setValue(double value) {
        this.value = value;
        return this;
    }

    @Ignore
    public App setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    @Ignore
    public static String getAppName(Context context,String packageName){
        ApplicationInfo applicationInformation;
        PackageManager packageManager=context.getPackageManager();
        try {
            applicationInformation = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        return (String)  packageManager.getApplicationLabel(applicationInformation);

    }


}
