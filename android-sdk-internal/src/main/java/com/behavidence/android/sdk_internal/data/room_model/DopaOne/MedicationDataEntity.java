package com.behavidence.android.sdk_internal.data.room_model.DopaOne;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MedicationDataEntity {
    public String medicationName;
    public String dosage;
    public String date;
    public String time;
    @PrimaryKey(autoGenerate = true)
    public long id = 0;

    public MedicationDataEntity(String medicationName, String dosage, String date, String time) {
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
