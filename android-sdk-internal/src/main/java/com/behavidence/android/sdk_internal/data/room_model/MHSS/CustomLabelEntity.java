package com.behavidence.android.sdk_internal.data.room_model.MHSS;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.behavidence.android.sdk_internal.data.room_model.Participation.AssociationRoom;
import com.behavidence.android.sdk_internal.data.room_model.Participation.ResearchRoom;


@Entity(
        foreignKeys = {
                @ForeignKey(entity = ResearchRoom.class,
                        parentColumns = {"adminId", "code"},
                        childColumns = {"researchFId", "researchFCode"},
                        onDelete = CASCADE),
                @ForeignKey(entity = AssociationRoom.class,
                        parentColumns = {"adminId", "code"},
                        childColumns = {"associationFId", "associationFCode"},
                        onDelete = CASCADE)}
)

public class CustomLabelEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String researchFId;
    private String researchFCode;
    private String associationFId;
    private String associationFCode;
    private long timestamp;


    @Ignore
    public CustomLabelEntity(){
        this.timestamp = System.currentTimeMillis();
    }

    public CustomLabelEntity(String researchFId, String researchFCode, String associationFId, String associationFCode, long timestamp) {
        this.researchFId = researchFId;
        this.researchFCode = researchFCode;
        this.associationFId = associationFId;
        this.associationFCode = associationFCode;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getResearchFId() {
        return researchFId;
    }

    public String getResearchFCode() {
        return researchFCode;
    }

    public String getAssociationFId() {
        return associationFId;
    }

    public String getAssociationFCode() {
        return associationFCode;
    }

    public void setResearchFId(String researchFId) {
        this.researchFId = researchFId;
    }

    public void setResearchFCode(String researchFCode) {
        this.researchFCode = researchFCode;
    }

    public void setAssociationFId(String associationFId) {
        this.associationFId = associationFId;
    }

    public void setAssociationFCode(String associationFCode) {
        this.associationFCode = associationFCode;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
