package com.behavidence.android.sdk_internal.data.interfaces;

import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationBody;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationDeletionReponse.ParticipationDeletionResponse;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse.ParticipationResponse;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;

public interface ParticipationService {

    boolean postParticipationSync(ParticipationBody participationBody);

    void postParticipation(ParticipationBody participationBody, BehavidenceResponseCallback<Void> callback);

    ParticipationResponse getParticipationSync();

    void getParticipation(BehavidenceResponseCallback<ParticipationResponse> callback);

    ParticipationDeletionResponse deleteResearchSync(String adminId, String researchCode);

    void deleteResearch(String adminId, String researchCode, BehavidenceResponseCallback<ParticipationDeletionResponse> callback);

    ParticipationDeletionResponse deleteAssociationSync(String adminId);

    void deleteAssociation(String adminId, BehavidenceResponseCallback<ParticipationDeletionResponse> callback);

}
