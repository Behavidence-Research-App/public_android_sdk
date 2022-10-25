package com.behavidence.android.sdk_internal.data.interfaces;

import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.ResearchQuestionnaireGroupResponse;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.Questionnaire.ResearchQuestionnaireResponse;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface ResearchQuestionnaireService {

    ResearchQuestionnaireResponse getQuestionnaireSync(String code);

    void getQuestionnaire(String code, BehavidenceResponseCallback<ResearchQuestionnaireResponse> callback);

    ResearchQuestionnaireGroupResponse getGroupQuestionnaireSync(String code);

    void getGroupQuestionnaire(String code, BehavidenceResponseCallback<ResearchQuestionnaireGroupResponse> callback);

}
