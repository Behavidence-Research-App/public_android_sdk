package com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class Result {

   @SerializedName("adminid")
   String adminid;

   @SerializedName("association")
   Association association;

   @SerializedName("researches")
   List<Researches> researches;

   @SerializedName("organization_info")
   OrganizationInfo organizationInfo;

   @SerializedName("admin_info")
   AdminInfo adminInfo;


    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }
    public String getAdminid() {
        return adminid;
    }
    
    public void setAssociation(Association association) {
        this.association = association;
    }
    public Association getAssociation() {
        return association;
    }
    
    public void setResearches(List<Researches> researches) {
        this.researches = researches;
    }
    public List<Researches> getResearches() {
        return researches;
    }
    
    public void setOrganizationInfo(OrganizationInfo organizationInfo) {
        this.organizationInfo = organizationInfo;
    }
    public OrganizationInfo getOrganizationInfo() {
        return organizationInfo;
    }
    
    public void setAdminInfo(AdminInfo adminInfo) {
        this.adminInfo = adminInfo;
    }
    public AdminInfo getAdminInfo() {
        return adminInfo;
    }
    
}