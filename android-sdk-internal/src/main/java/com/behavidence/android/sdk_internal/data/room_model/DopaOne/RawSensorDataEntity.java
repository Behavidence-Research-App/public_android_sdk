package com.behavidence.android.sdk_internal.data.room_model.DopaOne;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RawSensorDataEntity {

    @PrimaryKey(autoGenerate = true)
    public long id = 0L;
    public long timestamp;
    public String sensor_type;
    public float x;
    public float y;
    public float z;

    public RawSensorDataEntity(long timestamp, String sensor_type, float x, float y, float z) {
        this.timestamp = timestamp;
        this.sensor_type = sensor_type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSensor_type() {
        return sensor_type;
    }

    public void setSensor_type(String sensor_type) {
        this.sensor_type = sensor_type;
    }

    public double getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
