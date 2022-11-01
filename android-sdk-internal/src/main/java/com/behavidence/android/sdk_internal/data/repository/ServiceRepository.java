package com.behavidence.android.sdk_internal.data.repository;

import android.content.Context;

import com.behavidence.android.sdk_internal.data.interfaces.AuthService;
import com.behavidence.android.sdk_internal.data.interfaces.AuthSigninService;
import com.behavidence.android.sdk_internal.data.interfaces.EventsService;
import com.behavidence.android.sdk_internal.data.interfaces.JournalService;
import com.behavidence.android.sdk_internal.data.interfaces.MHSSService;
import com.behavidence.android.sdk_internal.data.interfaces.ParticipationService;
import com.behavidence.android.sdk_internal.data.interfaces.ResearchQuestionnaireService;
import com.behavidence.android.sdk_internal.data.model.Journal.JournalData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceRepository {

    private static ServiceRepository serviceRepository = null;

    private final ServiceParent _serviceParent;
    private final JournalService_Impl _journalService;
    private final MHSSService_Impl _mhssService;
    private final ParticipationService_Impl _participationService;
    private final ResearchQuestionnaireService_Impl _researchQuestionService;
    private final EventsService_Impl _eventsService;

    public synchronized static ServiceRepository getInstance(Context context){
        if(serviceRepository == null){
            serviceRepository = new ServiceRepository(context);
        }

        return serviceRepository;
    }

    private ServiceRepository(Context context){
        _serviceParent = new ServiceParent(context);
        _journalService = new JournalService_Impl(context);
        _mhssService = new MHSSService_Impl(context);
        _participationService = new ParticipationService_Impl(context);
        _researchQuestionService = new ResearchQuestionnaireService_Impl(context);
        _eventsService = new EventsService_Impl(context);
    }

    public AuthSigninService auth(){
        return _serviceParent;
    }

    public JournalService journal(){
        return _journalService;
    }

    public MHSSService mhss(){ return _mhssService; }

    public ParticipationService participation(){ return _participationService; }

    public ResearchQuestionnaireService researchQuestion(){ return _researchQuestionService; }

    public EventsService events(){ return _eventsService; }


}
