package com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.ResearchQuestion;
import com.google.gson.annotations.SerializedName;


public class Questions implements ResearchQuestion {

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

    private List<Integer> selectedOptions = new ArrayList<>();


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

    @NonNull
    @Override
    public Boolean isMultipleChoice() {
        return multiple;
    }

    @NonNull
    @Override
    public String[] getSelectionOptions() {
        return options.toArray(new String[0]);
    }

    @Override
    public Boolean getMultipleSelect() {
        return multiple;
    }

    @Override
    public void selectOptionIndex(@NonNull Integer index, boolean answer) {
        if(index >= options.size()){
            Log.e("BehavidenceError", "Invalid Index");
            return;
        }
        if(answer && selectedOptions.contains(index)) return;
        if(!answer) selectedOptions.remove(index);
        else selectedOptions.add(index);
    }

    @Override
    public void selectOption(@NonNull String option, boolean answer) {
        if(!options.contains(option) ){
            Log.e("BehavidenceError", "Invalid option");
            return;
        }

        int optionIndex = options.indexOf(option);

        if(answer && selectedOptions.contains(optionIndex)) return;
        if(!answer) selectedOptions.remove(optionIndex);
        else selectedOptions.add(optionIndex);
    }

    @NonNull
    @Override
    public List<String> getSelectedOption() {

        List<String> selectedOption = new ArrayList<>();

        for(Integer index: selectedOptions)
            selectedOption.add(options.get(index));

        return selectedOption;
    }

    @Override
    public List<Integer> getSelectedOptionsIndex() {
        return selectedOptions;
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

    @Override
    public String toString() {
        return "Questions{" +
                "qid='" + qid + '\'' +
                ", no=" + no +
                ", question='" + question + '\'' +
                ", multiple=" + multiple +
                ", options=" + options +
                ", calReverse=" + calReverse +
                ", selectedOptions=" + selectedOptions +
                '}';
    }
}