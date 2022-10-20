package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire;

import com.google.gson.annotations.SerializedName;

   
public class Consent {

   @SerializedName("html_text")
   String htmlText;

   @SerializedName("explanation")
   String explanation;

   @SerializedName("header")
   String header;

   @SerializedName("website")
   String website;


    public void setHtmlText(String htmlText) {
        this.htmlText = htmlText;
    }
    public String getHtmlText() {
        return htmlText;
    }
    
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    public String getExplanation() {
        return explanation;
    }
    
    public void setHeader(String header) {
        this.header = header;
    }
    public String getHeader() {
        return header;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }
    public String getWebsite() {
        return website;
    }
    
}