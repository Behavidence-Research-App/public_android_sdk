package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class CodeInformation {

    @SerializedName("name")
    String name;

   @SerializedName("adminid")
   String adminid;

   @SerializedName("code")
   String code;

   @SerializedName("type")
   String type;

   @SerializedName("custom_ui_name")
   String customUiName;

   @SerializedName("questions_group")
   List<QuestionsGroup> questionsGroup;

   @SerializedName("custom_label")
   CustomLabel customLabel;

   @SerializedName("consent")
   CodeConsent consent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }
    public String getAdminid() {
        return adminid;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    
    public void setCustomUiName(String customUiName) {
        this.customUiName = customUiName;
    }
    public String getCustomUiName() {
        return customUiName;
    }
    
    public void setQuestionsGroup(List<QuestionsGroup> questionsGroup) {
        this.questionsGroup = questionsGroup;
    }
    public List<QuestionsGroup> getQuestionsGroup() {
        return questionsGroup;
    }
    
    public void setCustomLabel(CustomLabel customLabel) {
        this.customLabel = customLabel;
    }
    public CustomLabel getCustomLabel() {
        return customLabel;
    }
    
    public void setConsent(CodeConsent consent) {
        this.consent = consent;
    }
    public CodeConsent getConsent() {
        return consent;
    }

    @Override
    public String toString() {
        return "CodeInformation{" +
                "name='" + name + '\'' +
                ", adminid='" + adminid + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", customUiName='" + customUiName + '\'' +
                ", questionsGroup=" + questionsGroup +
                ", customLabel=" + customLabel +
                ", consent=" + consent +
                '}';
    }
}