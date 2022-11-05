package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire;

import com.google.gson.annotations.SerializedName;

   
public class QuestionnaireMhssLabels {

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

    @SerializedName("custom_ui_link")
    String customUILink;


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

    public boolean isShowLowMediumHigh() {
        return showLowMediumHigh;
    }

    public boolean isReverse() {
        return reverse;
    }

    public String getCustomUILink() {
        return customUILink;
    }

    public void setCustomUILink(String customUILink) {
        this.customUILink = customUILink;
    }

    @Override
    public String toString() {
        return "QuestionnaireMhssLabels{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", priority=" + priority +
                ", showLowMediumHigh=" + showLowMediumHigh +
                ", reverse=" + reverse +
                ", customUILink='" + customUILink + '\'' +
                '}';
    }
}