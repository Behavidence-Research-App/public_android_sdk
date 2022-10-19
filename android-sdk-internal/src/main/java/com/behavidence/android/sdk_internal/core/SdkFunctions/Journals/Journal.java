package com.behavidence.android.sdk_internal.core.SdkFunctions.Journals;


import androidx.annotation.NonNull;

import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;


public class Journal {


        private final String journalTxt;
        private final long timeStampMillisecond;






    /**
     *
     * @param journalTxt string of journal. length must be less than 150
     * @param timeStampMillisecond time in milliseconds in UTC when journal is created
     * @throws InvalidArgumentException when length of text is invalid
     */
    public Journal(@NonNull String journalTxt, long timeStampMillisecond){
            if(journalTxt.length()<1||journalTxt.length()>150)
                throw new InvalidArgumentException("Invalid Journal length, length must be between 1 and 150");
            this.journalTxt=journalTxt;
            this.timeStampMillisecond=timeStampMillisecond;
        }

    /**
     *
     * @return getting text of the journal
     */
    public String getJournalTxt() {
            return journalTxt;
        }

    /**
     *
     * @return returning the time in milliseconds
     */
    public long getTimeStampMillisecond() {
            return timeStampMillisecond;
        }
}
