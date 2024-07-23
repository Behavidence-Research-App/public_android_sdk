package com.behavidence.android.sdk_internal.data.model.DopaOne;

import com.google.gson.annotations.SerializedName;

public class RawData {

    @SerializedName("timestamp")
    private long timestamp;
    @SerializedName("sensorType")
    private String sensorType;
    @SerializedName("x")
    private float x;
    @SerializedName("y")
    private float y;
    @SerializedName("z")
    private float z;

    public RawData(long timestamp, String sensorType, float x, float y, float z) {
        this.timestamp = timestamp;
        this.sensorType = sensorType;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public float getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(long z) {
        this.z = z;
    }
}
