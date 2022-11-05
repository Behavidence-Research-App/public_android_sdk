package com.behavidence.android.sdk_internal.domain.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.ResearchQuestionnaireGroupResponse;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceCallback;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceResearchCallback;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.AssociationParticipation;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.CodeInfo;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Participation;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.ResearchParticipation;

import java.util.List;

public interface ParticipationClient {

    @Nullable
    void getCodeInfo(@NonNull String code, BehavidenceResearchCallback<CodeInfo> callback);

    void submit(@NonNull CodeInfo codeInfo, BehavidenceCallback<String> callback);

    @NonNull
    void getAllParticipation(BehavidenceCallback<List<Participation>> callback);

    @NonNull
    void getAllResearchParticipation(BehavidenceCallback<List<ResearchParticipation>> callback);

    @NonNull
    void getAllAssociationParticipation(BehavidenceCallback<List<AssociationParticipation>> callback);

    void deleteAssociationParticipation(@NonNull AssociationParticipation associationParticipation, BehavidenceCallback<String> callback);

    void deleteResearchParticipation(@NonNull ResearchParticipation researchParticipation, BehavidenceCallback<String> callback);

}
