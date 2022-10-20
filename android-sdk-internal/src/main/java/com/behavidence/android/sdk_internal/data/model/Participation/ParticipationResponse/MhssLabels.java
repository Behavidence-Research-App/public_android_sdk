package com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse;

import com.google.gson.annotations.SerializedName;

   
public class MhssLabels {

   @SerializedName("id")
   int id;

   @SerializedName("label")
   String label;

   @SerializedName("priority")
   int priority;

   @SerializedName("show_low_medium_high")
   boolean showLowMediumHigh;

   @SerializedName("reverse")
   boolean reverse;


    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
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
    
}