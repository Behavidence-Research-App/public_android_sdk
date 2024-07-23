package com.behavidence.android.sdk_internal.data.room_model.DopaOne;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SensorDataEntity {

    @PrimaryKey(autoGenerate = true)
    public long id = 0L;
    public long timeInMilliUtc;
    public String sensorType;
    public double sdx;
    public double sdy;
    public double sdz;
    public double sdyAndZ;
    public String localTime;
    public String timezone;

    public SensorDataEntity(long timeInMilliUtc, String sensorType, double sdx, double sdy, double sdz, double sdyAndZ, String localTime, String timezone) {
        this.timeInMilliUtc = timeInMilliUtc;
        this.sensorType = sensorType;
        this.sdx = sdx;
        this.sdy = sdy;
        this.sdz = sdz;
        this.sdyAndZ = sdyAndZ;
        this.localTime = localTime;
        this.timezone = timezone;
    }

    public long getTimeInMilliUtc() {
        return timeInMilliUtc;
    }

    public void setTimeInMilliUtc(long timeInMilliUtc) {
        this.timeInMilliUtc = timeInMilliUtc;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public double getSdx() {
        return sdx;
    }

    public void setSdx(double sdx) {
        this.sdx = sdx;
    }

    public double getSdy() {
        return sdy;
    }

    public void setSdy(double sdy) {
        this.sdy = sdy;
    }

    public double getSdz() {
        return sdz;
    }

    public void setSdz(double sdz) {
        this.sdz = sdz;
    }

    public double getSdyAndZ() {
        return sdyAndZ;
    }

    public void setSdyAndZ(double sdyAndZ) {
        this.sdyAndZ = sdyAndZ;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}


