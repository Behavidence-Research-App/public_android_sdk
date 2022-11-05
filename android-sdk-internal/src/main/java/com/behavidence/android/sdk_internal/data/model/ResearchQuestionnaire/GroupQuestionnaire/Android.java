package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class Android {

   @SerializedName("mhss_labels")
   List<QuestionnaireMhssLabels> mhssLabels;


    public void setMhssLabels(List<QuestionnaireMhssLabels> mhssLabels) {
        this.mhssLabels = mhssLabels;
    }
    public List<QuestionnaireMhssLabels> getMhssLabels() {
        return mhssLabels;
    }

    @Override
    public String toString() {
        return "Android{" +
                "mhssLabels=" + mhssLabels +
                '}';
    }
}