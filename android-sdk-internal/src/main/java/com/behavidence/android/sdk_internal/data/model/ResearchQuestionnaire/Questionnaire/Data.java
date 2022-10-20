package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.Questionnaire;

import com.google.gson.annotations.SerializedName;

   
public class Data {

   @SerializedName("code_info")
   CodeInfo codeInfo;

   @SerializedName("admin_info")
   AdminInfo adminInfo;

   @SerializedName("organization_info")
   OrganizationInfo organizationInfo;


    public void setCodeInfo(CodeInfo codeInfo) {
        this.codeInfo = codeInfo;
    }
    public CodeInfo getCodeInfo() {
        return codeInfo;
    }
    
    public void setAdminInfo(AdminInfo adminInfo) {
        this.adminInfo = adminInfo;
    }
    public AdminInfo getAdminInfo() {
        return adminInfo;
    }
    
    public void setOrganizationInfo(OrganizationInfo organizationInfo) {
        this.organizationInfo = organizationInfo;
    }
    public OrganizationInfo getOrganizationInfo() {
        return organizationInfo;
    }
    
}