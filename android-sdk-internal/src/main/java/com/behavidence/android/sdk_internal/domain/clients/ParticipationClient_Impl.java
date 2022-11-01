package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.data.interfaces.ParticipationService;
import com.behavidence.android.sdk_internal.data.interfaces.ResearchQuestionnaireService;
import com.behavidence.android.sdk_internal.data.model.Participation.AssociationBody;
import com.behavidence.android.sdk_internal.data.model.Participation.AssociationResearchBody;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationBody;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationDeletionReponse.ParticipationDeletionResponse;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse.ParticipationResponse;
import com.behavidence.android.sdk_internal.data.model.Participation.QuestionData;
import com.behavidence.android.sdk_internal.data.model.Participation.ResearchBody;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.CodeInformation;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.CodeInformationResponse;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.Questions;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.QuestionsGroup;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.ResearchQuestionnaireGroupResponse;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceResearchCallback.CodeType;
import com.behavidence.android.sdk_internal.domain.interfaces.ParticipationClient;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.AssociationParticipation;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.CodeInfo;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Participation;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.ResearchParticipation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ParticipationClient_Impl extends ClientParent implements ParticipationClient {

    private ParticipationService _service;
    private ResearchQuestionnaireService _researchService;

    public ParticipationClient_Impl(Context context) {
        super(context);
        _service = services.participation();
        _researchService = services.researchQuestion();
    }

    @Nullable
    @Override
    public void getCodeInfo(@NonNull String code, BehavidenceResearchCallback<CodeInfo> callback) {
        _researchService.getGroupQuestionnaire(code, new BehavidenceClientCallback<ResearchQuestionnaireGroupResponse>() {
            @Override
            public void onSuccess(ResearchQuestionnaireGroupResponse response) {

                if(response != null && response.getData() != null){
                    CodeType type = getType(response.getData().getType());
                    callback.onSuccess(response.getData(), type);
                }
            }

            @Override
            public void onFailure(String message) {
                callback.onFailure(message);
            }
        });
    }

    @Override
    public void submit(@NonNull CodeInfo codeInfo) {
        if(codeInfo instanceof CodeInformationResponse){
            CodeInformationResponse codeInformationResponse = (CodeInformationResponse) codeInfo;
            CodeType type = getType(codeInformationResponse.getType());

            String code = codeInformationResponse.getCode();
            String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).format(new Date());
            String deviceType = "android";
            Long time = codeInformationResponse.getAssociationTimeInSec();
            List<QuestionData> questionData = new ArrayList<>();

            BehavidenceClientCallback<Void> callback = new BehavidenceClientCallback<Void>() {
                @Override
                public void onSuccess(Void response) {

                }

                @Override
                public void onFailure(String message) {

                }
            };

            if(type == CodeType.ASSOCIATION || type == CodeType.RESEARCH_ASSOCIATION) {
                if (time == null || time < 1) {
                    Log.e("BehavidenceError", "Invalid Association time");
                    return;
                }
            }

            if(type == CodeType.RESEARCH || type == CodeType.RESEARCH_ASSOCIATION){
                List<QuestionsGroup> questions = codeInformationResponse.getCodeInfo().getQuestionsGroup();
                for(QuestionsGroup group: questions){
                    for(Questions question: group.getQuestions())
                        questionData.add(new QuestionData(
                                question.getQid(),
                                question.getNo(),
                                question.getSelectedOption()
                        ));
                }
            }


            if(type == CodeType.ASSOCIATION) {
                AssociationBody associationBody = new AssociationBody(code, date, deviceType, time);
                _service.postParticipation(associationBody, callback);
            }else if(type == CodeType.RESEARCH){
                ResearchBody researchBody = new ResearchBody(code, date, deviceType, questionData);
                _service.postParticipation(researchBody, callback);
            }else if(type == CodeType.RESEARCH_ASSOCIATION){
                AssociationResearchBody associationResearchBody = new AssociationResearchBody(code, date, deviceType, time, questionData);
                _service.postParticipation(associationResearchBody, callback);
            }

        }
    }

    @NonNull
    @Override
    public List<Participation> getAllParticipation() {
        return null;
    }

    @NonNull
    @Override
    public List<ResearchParticipation> getAllResearchParticipation() {
        return null;
    }

    @NonNull
    @Override
    public List<AssociationParticipation> getAllAssociationParticipation() {
        return null;
    }

    @Override
    public void deleteAssociationParticipation(@NonNull AssociationParticipation associationParticipation) {

    }

    @Override
    public void deleteResearchParticipation(@NonNull ResearchParticipation researchParticipation) {

    }

    private CodeType getType(String type){
        switch (type){
            case "a": return CodeType.ASSOCIATION;
            case "r": return CodeType.RESEARCH;
            case "ar": return CodeType.RESEARCH_ASSOCIATION;
            default: return CodeType.ASSOCIATION;
        }
    }

}
