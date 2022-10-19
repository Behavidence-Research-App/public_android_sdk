package com.behavidence.android.sdk_internal.core.SdkFunctions.Association.AssociationManager;

import android.util.Log;

import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;

import java.util.ArrayList;

import java.util.List;
import java.util.Set;

public interface ResearchQuestion{
    @NonNull
     String getGroupId();

     int getQno();

    @NonNull
     String getQuestion();

    @NonNull
     String[] getOptions();

    @NonNull
     Boolean getMultipleSelect();


    @NonNull
    List<String> getSelectedAnswers();

     void setAnswer(int answerIndex,boolean answer);

     void setSelectedAnswersMultiple(Set<Integer> indexes);

     void setAnswersMultiple(@NonNull List<Integer> indexes,@NonNull List<Boolean> booleans);

}

class ResearchQuestionImpl implements ResearchQuestion{

    private final String question;
    private final String []options;
    private final boolean multipleSelect;
    private final boolean []answers;
    private final String groupId;
    private final int qno;

    public ResearchQuestionImpl(@NonNull String groupId, int qno, @NonNull String question, @NonNull String[] options, boolean multipleSelect) {
        this.question = question;
        this.options = options;
        this.multipleSelect = multipleSelect;
        this.answers = new boolean[options.length];
        this.groupId=groupId;
        this.qno=qno;

    }


    @NonNull
    @Override
    public String getGroupId() {
        return groupId;
    }

    @NonNull
    @Override
    public int getQno() {
        return qno;
    }

    @NonNull
    @Override
    public String getQuestion() {
        return question;
    }

    @NonNull
    @Override
    public String[] getOptions() {
        return options;
    }

    @NonNull
    @Override
    public Boolean getMultipleSelect() {
        return multipleSelect;
    }

    @NonNull
    @Override
    public List<String> getSelectedAnswers() {
        List<String> answersList = new ArrayList<>();
        for (int i = 0; i < options.length; i++) {
            if (answers[i])
                answersList.add(options[i]);
        }
        return answersList;
    }

    public void printVal(@NonNull String key){
        Log.e(key,"gid"+groupId+" no"+qno+"question:"+question+" multiselect:"+multipleSelect+"  options\n");
        for (String option : options) Log.e(key, option);
    }

    @Override
    public void setAnswer(int answerIndex, boolean answer) {
        if(answerIndex<0||answerIndex>=options.length)return;
        this.answers[answerIndex]=answer;
        if(!this.multipleSelect&&answer)
        {
            for (int i=0;i<this.options.length;i++){
                if(i!=answerIndex)
                    this.answers[i]=false;
            }
        }
    }

    @Override
    public void setSelectedAnswersMultiple(@NonNull Set<Integer> indexes) {
        if(multipleSelect){
            for(int ansIndex=0;ansIndex<options.length;ansIndex++){
                setAnswer(ansIndex,indexes.contains(ansIndex));
            }
        }
    }


    @Override
    public void setAnswersMultiple(@NonNull List<Integer> indexes,@NonNull List<Boolean> booleans) {
        if(indexes.size()!=booleans.size()) throw new InvalidArgumentException("Invalid sizes ");
        if(multipleSelect) {
            for (int i = 0; i < booleans.size(); i++)
                setAnswer(i, booleans.get(i));
        }
    }
}
