package com.behavidence.android.sdk_internal.data.interfaces;

import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationBody;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationDeletionReponse.ParticipationDeletionResponse;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse.ParticipationResponse;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClientCallback;

public interface ParticipationService {

    boolean postParticipationSync(ParticipationBody participationBody);

    void postParticipation(ParticipationBody participationBody, BehavidenceClientCallback<Void> callback);

    ParticipationResponse getParticipationSync();

    void getParticipation(BehavidenceClientCallback<ParticipationResponse> callback);

    ParticipationDeletionResponse deleteResearchSync(String adminId, String researchCode);

    void deleteResearch(String adminId, String researchCode, BehavidenceClientCallback<ParticipationDeletionResponse> callback);

    ParticipationDeletionResponse deleteAssociationSync(String adminId);

    void deleteAssociation(String adminId, BehavidenceClientCallback<ParticipationDeletionResponse> callback);

}
