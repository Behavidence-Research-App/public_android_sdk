package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class Android {

   @SerializedName("mhss_labels")
   List<MhssLabels> mhssLabels;


    public void setMhssLabels(List<MhssLabels> mhssLabels) {
        this.mhssLabels = mhssLabels;
    }
    public List<MhssLabels> getMhssLabels() {
        return mhssLabels;
    }
    
}