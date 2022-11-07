package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;

import com.behavidence.android.sdk_internal.domain.interfaces.Auth;
import com.behavidence.android.sdk_internal.domain.interfaces.JournalClient;
import com.behavidence.android.sdk_internal.domain.interfaces.MHSSClient;
import com.behavidence.android.sdk_internal.domain.interfaces.ParticipationClient;
import com.behavidence.android.sdk_internal.domain.interfaces.Questionnaire;

public class BehavidenceClient {

//    private static AuthClient_Impl authClientImpl = null;
    private static ParticipationClient participationClient = null;
//    private static Questionnaire questionnaire = null;
    private static JournalClient journalClient = null;
    private static MHSSClient mhssClient = null;
    private static TimeZoneClient_Impl timeZoneClient = null;

    /**
     * Initialize the Behavidence Client (Important before calling other functions)
     * @param context
     */
    public static void initialize(Context context){
//        authClientImpl = new AuthClient_Impl(context);
        participationClient = new ParticipationClient_Impl(context);
//        questionnaire = new ResearchQuestionnaireClient_Impl(context);
        journalClient = new JournalClient_Impl(context);
        mhssClient = new MHSSClient_Impl(context);
        timeZoneClient = new TimeZoneClient_Impl(context);
        WorkManagerClient.initializeWorker(context);

        timeZoneClient.logTimeZone();
    }

//    public static Auth Auth(){
//        if(authClientImpl == null)
//            throw new NullPointerException("Client has not been initialized");
//        return authClientImpl;
//    }

    /**
     * Related to Research and Association
     * @return Participation Client
     */
    public static ParticipationClient Participation(){
        if(participationClient == null)
            throw new NullPointerException("Client has not been initialized");
        return participationClient;
    }

//    public static Questionnaire Questionnaire(){
//        if(questionnaire == null)
//            throw new NullPointerException("Client has not been initialized");
//        return questionnaire;
//    }

    /**
     * Related to Journals
     * @return Journal Client
     */
    public static JournalClient Journal(){
        if(journalClient == null)
            throw new NullPointerException("Client has not been initialized");
        return journalClient;
    }

    /**
     * Client for Similarity Score
     * @return MHSS Client
     */
    public static MHSSClient MHSS(){
        if(mhssClient == null)
            throw new NullPointerException("Client has not been initialized");
        return mhssClient;
    }

}
