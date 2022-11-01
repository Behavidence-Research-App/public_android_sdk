package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;

import com.behavidence.android.sdk_internal.data.interfaces.ResearchQuestionnaireService;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.ResearchQuestionnaireGroupResponse;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.Questionnaire.ResearchQuestionnaireResponse;
import com.behavidence.android.sdk_internal.domain.interfaces.Questionnaire;

public class ResearchQuestionnaireClient_Impl extends ClientParent implements Questionnaire {

    private ResearchQuestionnaireService _service;

    public ResearchQuestionnaireClient_Impl(Context context) {
        super(context);
        _service = services.researchQuestion();
    }

    @Override
    public ResearchQuestionnaireResponse getQuestionnaireSync(String code) {
        return _service.getQuestionnaireSync(code);
    }

    @Override
    public void getQuestionnaire(String code, BehavidenceCallback<ResearchQuestionnaireResponse> callback) {

    }

    @Override
    public ResearchQuestionnaireGroupResponse getGroupQuestionnaireSync(String code) {
        return _service.getGroupQuestionnaireSync(code);
    }

    @Override
    public void getGroupQuestionnaire(String code, BehavidenceCallback<ResearchQuestionnaireGroupResponse> callback) {

        _service.getGroupQuestionnaire(code, new BehavidenceClientCallback<ResearchQuestionnaireGroupResponse>() {
            @Override
            public void onSuccess(ResearchQuestionnaireGroupResponse response) {
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(String message) {
                callback.onFailure(message);
            }
        });
    }
}
