package com.behavidence.android.sdk_internal.core.SdkFunctions.SimilarityScores;

import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.LabelEntity;
import com.behavidence.android.sdk_internal.core.SdkFunctions.SimilarityScores.Room.Mhssroom;

public class MappedMhss {

    private final Mhssroom mhssroom;
    private final LabelEntity label;

    public MappedMhss(Mhssroom mhssroom, LabelEntity label){
        this.mhssroom = mhssroom;
        this.label = label;
    }

    public int getScore(){
        return mhssroom.getScore();
    }

    public String getLabel(){
        return label.getLabel();
    }

    public boolean isLowMediumHigh(){
        return label.isShowLmh();
    }

    public boolean isReverse(){
        return label.isReverse();
    }

    public String getCustomUiLink(){
        return label.getCustomUiLink();
    }

    public int getMhssId(){
        return Integer.valueOf(mhssroom.getId());
    }

    public int getMhssPriority(){
        return label.getPriority();
    }

    @Override
    public String toString() {
        return "MappedMhss{" +
                "mhssroom=" + mhssroom +
                ", label=" + label +
                '}';
    }
}
