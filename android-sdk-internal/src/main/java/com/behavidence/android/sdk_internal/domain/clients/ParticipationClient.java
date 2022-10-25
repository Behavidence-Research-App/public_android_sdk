package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;

import com.behavidence.android.sdk_internal.data.interfaces.ParticipationService;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationBody;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationDeletionReponse.ParticipationDeletionResponse;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse.ParticipationResponse;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;
import com.behavidence.android.sdk_internal.domain.interfaces.Participation;

public class ParticipationClient extends ClientParent implements Participation {

    private ParticipationService _service;

    public ParticipationClient(Context context) {
        super(context);
        _service = services.participation();
    }

    @Override
    public boolean participateSync(ParticipationBody participationBody) {
        return false;
    }

    @Override
    public void participate(ParticipationBody participationBody, BehavidenceResponseCallback<Void> callback) {

    }

    @Override
    public ParticipationResponse checkParticipationSync() {
        return null;
    }

    @Override
    public void checkParticipation(BehavidenceResponseCallback<ParticipationResponse> callback) {

    }

    @Override
    public ParticipationDeletionResponse deleteResearchSync(String adminId, String researchCode) {
        return null;
    }

    @Override
    public void deleteResearch(String adminId, String researchCode, BehavidenceResponseCallback<ParticipationDeletionResponse> callback) {

    }

    @Override
    public ParticipationDeletionResponse deleteAssociationSync(String adminId) {
        return null;
    }

    @Override
    public void deleteAssociation(String adminId, BehavidenceResponseCallback<ParticipationDeletionResponse> callback) {

    }
}
