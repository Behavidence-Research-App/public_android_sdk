package com.behavidence.android.sdk_internal.core.SdkFunctions.Association.AssociationManager;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface  Consent {
    @Nullable
    public String getHtmlTxt();

    @Nullable
    public String getText();
}

 class ConsentImpl implements Consent{
    private String htmlTxt;
    private String text;
     ConsentImpl(){}
     @Override
    public String getHtmlTxt() {
        return htmlTxt;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setHtmlTxt(String htmlTxt) {
        this.htmlTxt = htmlTxt;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void printVal(@NonNull String key){
        Log.e(key,htmlTxt+"    :Normal text"+text);
    }
}
