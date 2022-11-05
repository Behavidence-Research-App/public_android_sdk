package com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse;

import com.google.gson.annotations.SerializedName;


public class MhssLabels {

    @SerializedName("id")
    long id;

    @SerializedName("label")
    String label;

    @SerializedName("priority")
    int priority;

    @SerializedName("show_low_medium_high")
    boolean showLowMediumHigh;

    @SerializedName("reverse")
    boolean reverse;

    @SerializedName("custom_ui_link")
    String customUILink;


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setShowLowMediumHigh(boolean showLowMediumHigh) {
        this.showLowMediumHigh = showLowMediumHigh;
    }

    public boolean getShowLowMediumHigh() {
        return showLowMediumHigh;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public boolean getReverse() {
        return reverse;
    }

    public String getCustomUILink() {
        return customUILink;
    }

    public void setCustomUILink(String customUILink) {
        this.customUILink = customUILink;
    }
}