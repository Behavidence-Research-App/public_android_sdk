package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.Questionnaire;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class Questions {

   @SerializedName("qid")
   String qid;

   @SerializedName("no")
   int no;

   @SerializedName("question")
   String question;

   @SerializedName("multiple")
   boolean multiple;

   @SerializedName("options")
   List<String> options;

   @SerializedName("cal_reverse")
   boolean calReverse;


    public void setQid(String qid) {
        this.qid = qid;
    }
    public String getQid() {
        return qid;
    }
    
    public void setNo(int no) {
        this.no = no;
    }
    public int getNo() {
        return no;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getQuestion() {
        return question;
    }
    
    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }
    public boolean getMultiple() {
        return multiple;
    }
    
    public void setOptions(List<String> options) {
        this.options = options;
    }
    public List<String> getOptions() {
        return options;
    }
    
    public void setCalReverse(boolean calReverse) {
        this.calReverse = calReverse;
    }
    public boolean getCalReverse() {
        return calReverse;
    }
    
}