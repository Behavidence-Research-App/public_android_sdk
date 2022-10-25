package com.behavidence.android.sdk_internal.data.room_model.MHSS;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {
                @ForeignKey(entity = CustomLabelEntity.class,
                        parentColumns = {"id"} ,
                        childColumns = {"customLabelId"},
                        onDelete = CASCADE),
        }
)
public class LabelEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private int mhssId;
    private String label;
    private int priority;
    private boolean showLmh;
    private boolean reverse;
    private long customLabelId;
    private String customUiLink;

    public LabelEntity(int mhssId, String label, int priority, boolean showLmh, boolean reverse, long customLabelId, String customUiLink) {
        this.mhssId = mhssId;
        this.label = label;
        this.priority = priority;
        this.showLmh = showLmh;
        this.reverse = reverse;
        this.customLabelId = customLabelId;
        this.customUiLink = customUiLink;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMhssId() {
        return mhssId;
    }

    public void setMhssId(int mhssId) {
        this.mhssId = mhssId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isShowLmh() {
        return showLmh;
    }

    public void setShowLmh(boolean showLmh) {
        this.showLmh = showLmh;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public long getCustomLabelId() {
        return customLabelId;
    }

    public void setCustomLabelId(long customLabelId) {
        this.customLabelId = customLabelId;
    }

    public String getCustomUiLink() {
        return customUiLink;
    }

    public void setCustomUiLink(String customUiLink) {
        this.customUiLink = customUiLink;
    }

    @Override
    public String toString() {
        return "LabelEntity{" +
                "id=" + id +
                ", mhssId=" + mhssId +
                ", label='" + label + '\'' +
                ", priority=" + priority +
                ", showLmh=" + showLmh +
                ", reverse=" + reverse +
                ", customLabelId=" + customLabelId +
                ", customUiLink=" + customUiLink +
                '}';
    }
}
