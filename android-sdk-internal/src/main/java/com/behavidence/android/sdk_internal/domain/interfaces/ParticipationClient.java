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

    /**
     * Fetch information for the entered research code
     * @param code The provided code
     * @param callback Provide callback for fetching data from server
     */
    @Nullable
    void getCodeInfo(@NonNull String code, BehavidenceResearchCallback<CodeInfo> callback);

    /**
     * Submit Code Information Object with Answers or Association time or both
     * @param codeInfo Code Information object with updated fields, provided by getCodeInfo() call
     * @param callback Provide callback to get the submission status message
     */
    void submit(@NonNull CodeInfo codeInfo, BehavidenceCallback<String> callback);

    /**
     * Get all participation information
     * @param callback Provide callback to fetch latest data
     */
    @NonNull
    void getAllParticipation(BehavidenceCallback<List<Participation>> callback);

    /**
     * Get all research participation
     * @param callback Provide callback to fetch latest data
     */
    @NonNull
    void getAllResearchParticipation(BehavidenceCallback<List<ResearchParticipation>> callback);

    /**
     * Get all association participation
     * @param callback Provide callback to fetch latest data
     */
    @NonNull
    void getAllAssociationParticipation(BehavidenceCallback<List<AssociationParticipation>> callback);

    /**
     * Delete an Association Participation
     * @param associationParticipation The Association object in which user is currently participating in
     * @param callback Provide callback to get the deletion status message
     */
    void deleteAssociationParticipation(@NonNull AssociationParticipation associationParticipation, BehavidenceCallback<String> callback);

    /**
     * Delete an Research Participation
     * @param researchParticipation The Research object in which user is currently participating in
     * @param callback Provide callback to get the deletion status message
     */
    void deleteResearchParticipation(@NonNull ResearchParticipation researchParticipation, BehavidenceCallback<String> callback);

}
