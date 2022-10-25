package com.behavidence.android.sdk_internal.domain.interfaces;

import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.ResearchQuestionnaireGroupResponse;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.Questionnaire.ResearchQuestionnaireResponse;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceCallback;

public interface Questionnaire {

    ResearchQuestionnaireResponse getQuestionnaireSync(String code);

    void getQuestionnaire(String code, BehavidenceCallback<ResearchQuestionnaireResponse> callback);

    ResearchQuestionnaireGroupResponse getGroupQuestionnaireSync(String code);

    void getGroupQuestionnaire(String code, BehavidenceCallback<ResearchQuestionnaireGroupResponse> callback);

}
