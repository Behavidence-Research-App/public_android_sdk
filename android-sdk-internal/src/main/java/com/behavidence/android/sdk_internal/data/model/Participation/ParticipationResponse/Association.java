package com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse;

import com.google.gson.annotations.SerializedName;

   
public class Association {

   @SerializedName("expire_sec")
   int expireSec;

   @SerializedName("created")
   int created;

   @SerializedName("code")
   String code;


    public void setExpireSec(int expireSec) {
        this.expireSec = expireSec;
    }
    public int getExpireSec() {
        return expireSec;
    }
    
    public void setCreated(int created) {
        this.created = created;
    }
    public int getCreated() {
        return created;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
    
}