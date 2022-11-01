package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Admin;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.CodeInfo;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Consent;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Organization;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.QuestionGroup;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CodeInformationResponse implements CodeInfo {

   @SerializedName("code_info")
   CodeInformation codeInfo;

   @SerializedName("admin_info")
   AdminInfo adminInfo;

   @SerializedName("organization_info")
   OrganizationInfo organizationInfo;

   Long associationTime = null;


    public void setCodeInfo(CodeInformation codeInfo) {
        this.codeInfo = codeInfo;
    }
    public CodeInformation getCodeInfo() {
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

    @Nullable
    @Override
    public Organization getOrganization() {
        return organizationInfo;
    }

    @NonNull
    @Override
    public Admin getAdmin() {
        return adminInfo;
    }

    @NonNull
    @Override
    public String getCode() {
        return codeInfo.code;
    }

    public String getType(){
        return codeInfo.type;
    }

    @Nullable
    @Override
    public Consent getConsent() {
        return codeInfo.getConsent();
    }

    @Override
    public void setAssociationTimeInSec(@NonNull Long associationTimeInSec) {
        this.associationTime = associationTimeInSec;
    }

    public Long getAssociationTimeInSec(){
        return associationTime;
    }

    @Override
    public List<QuestionGroup> getQuestionGroups() {
        return new ArrayList<>(codeInfo.questionsGroup);
    }
}