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
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse.AdminInfo;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse.Android;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse.MhssLabels;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse.OrganizationInfo;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse.ParticipationResponse;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse.Researches;
import com.behavidence.android.sdk_internal.data.model.Participation.ParticipationResponse.Result;
import com.behavidence.android.sdk_internal.data.model.Participation.QuestionData;
import com.behavidence.android.sdk_internal.data.model.Participation.ResearchBody;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.CodeInformation;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.CodeInformationResponse;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.QuestionnaireAdminInfo;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.QuestionnaireMhssLabels;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.QuestionnaireOrganizationInfo;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.Questions;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.QuestionsGroup;
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.ResearchQuestionnaireGroupResponse;
import com.behavidence.android.sdk_internal.data.room_model.MHSS.CustomLabelEntity;
import com.behavidence.android.sdk_internal.data.room_model.MHSS.LabelEntity;
import com.behavidence.android.sdk_internal.data.room_model.Participation.AdminRoom;
import com.behavidence.android.sdk_internal.data.room_model.Participation.AssociationRoom;
import com.behavidence.android.sdk_internal.data.room_model.Participation.OrganizationRoom;
import com.behavidence.android.sdk_internal.data.room_model.Participation.ResearchRoom;
import com.behavidence.android.sdk_internal.data.room_repository.CustomLabelDao;
import com.behavidence.android.sdk_internal.data.room_repository.LabelDao;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceResearchCallback.CodeType;
import com.behavidence.android.sdk_internal.domain.interfaces.ParticipationClient;
import com.behavidence.android.sdk_internal.domain.model.Research.Admin_Model;
import com.behavidence.android.sdk_internal.domain.model.Research.AssociationParticipation_Model;
import com.behavidence.android.sdk_internal.domain.model.Research.Organization_Model;
import com.behavidence.android.sdk_internal.domain.model.Research.Participation_Model;
import com.behavidence.android.sdk_internal.domain.model.Research.ResearchParticipation_Model;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Admin;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.AssociationParticipation;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.CodeInfo;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Organization;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Participation;
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.ResearchParticipation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class ParticipationClient_Impl extends ClientParent implements ParticipationClient {

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

                if (response != null && response.getData() != null) {
                    CodeType type = getType(response.getData().getType());
                    callback.onSuccess(response.getData(), type);
                }else{
                    callback.onFailure("Failed to get the code");
                }
            }

            @Override
            public void onFailure(String message) {
                callback.onFailure(message);
            }
        });
    }

    @Override
    public void submit(@NonNull CodeInfo codeInfo, BehavidenceCallback<String> callback) {
        if (codeInfo instanceof CodeInformationResponse) {
            CodeInformationResponse codeInformationResponse = (CodeInformationResponse) codeInfo;
            CodeType type = getType(codeInformationResponse.getType());

            String adminId = codeInformationResponse.getAdminInfo().getAdminid();
            String code = codeInformationResponse.getCode();
            String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).format(new Date());
            String deviceType = "android";
            Long time = codeInformationResponse.getAssociationTimeInSec();
            List<QuestionData> questionData = new ArrayList<>();
            CustomLabelEntity customLabelEntity = new CustomLabelEntity();

            BehavidenceClientCallback<Void> clientCallback = new BehavidenceClientCallback<Void>() {
                @Override
                public void onSuccess(Void response) {

                    if (codeInformationResponse.getCodeInfo().getCustomLabel() != null && codeInformationResponse.getCodeInfo().getCustomLabel().getAndroid() != null)
                        saveLabelsInLocalDB(customLabelEntity, codeInformationResponse);
                    callback.onSuccess("Participated Successfully!");
                }

                @Override
                public void onFailure(String message) {
                    callback.onFailure(message);
                }
            };

            if (type == CodeType.ASSOCIATION || type == CodeType.RESEARCH_ASSOCIATION) {
                if (time == null || time < 1) {
                    Log.e("BehavidenceError", "Invalid Association time");
                    return;
                }
                customLabelEntity.setAssociationFId(adminId);
                customLabelEntity.setAssociationFCode(code);
            }

            if (type == CodeType.RESEARCH || type == CodeType.RESEARCH_ASSOCIATION) {
                List<QuestionsGroup> questions = codeInformationResponse.getCodeInfo().getQuestionsGroup();
                for (QuestionsGroup group : questions) {
                    for (Questions question : group.getQuestions())
                        questionData.add(new QuestionData(
                                question.getQid(),
                                question.getNo(),
                                question.getSelectedOption()
                        ));
                }
                customLabelEntity.setResearchFId(adminId);
                customLabelEntity.setResearchFCode(code);
            }

            if (type == CodeType.ASSOCIATION) {
                AssociationBody associationBody = new AssociationBody(code, date, deviceType, time);
                _service.postParticipation(associationBody, clientCallback);
            } else if (type == CodeType.RESEARCH) {
                ResearchBody researchBody = new ResearchBody(code, date, deviceType, questionData);
                _service.postParticipation(researchBody, clientCallback);
            } else if (type == CodeType.RESEARCH_ASSOCIATION) {
                AssociationResearchBody associationResearchBody = new AssociationResearchBody(code, date, deviceType, time, questionData);
                _service.postParticipation(associationResearchBody, clientCallback);
            }

        }
    }

    @NonNull
    @Override
    public void getAllParticipation(BehavidenceCallback<List<Participation>> callback) {
        _service.getParticipation(new BehavidenceClientCallback<ParticipationResponse>() {
            @Override
            public void onSuccess(ParticipationResponse response) {
                if (response != null && response.getData() != null && response.getData().getResult() != null)
                    saveParticipation(response.getData().getResult());

                callback.onSuccess(getParticipation());
            }

            @Override
            public void onFailure(String message) {
                List<Participation> participations = getParticipation();
                if (participations.size() < 1)
                    callback.onFailure("No participation found -" + message);
                else callback.onSuccess(participations);
            }
        });

    }

    @NonNull
    @Override
    public void getAllResearchParticipation(BehavidenceCallback<List<ResearchParticipation>> callback) {
        _service.getParticipation(new BehavidenceClientCallback<ParticipationResponse>() {
            @Override
            public void onSuccess(ParticipationResponse response) {
                if (response != null && response.getData() != null && response.getData().getResult() != null)
                    saveParticipation(response.getData().getResult());

                callback.onSuccess(getResearchParticipation());
            }

            @Override
            public void onFailure(String message) {
                List<ResearchParticipation> participations = getResearchParticipation();
                if (participations.size() < 1)
                    callback.onFailure("No participation found -" + message);
                else callback.onSuccess(participations);
            }
        });
    }

    @NonNull
    @Override
    public void getAllAssociationParticipation(BehavidenceCallback<List<AssociationParticipation>> callback) {
        _service.getParticipation(new BehavidenceClientCallback<ParticipationResponse>() {
            @Override
            public void onSuccess(ParticipationResponse response) {
                if (response != null && response.getData() != null && response.getData().getResult() != null)
                    saveParticipation(response.getData().getResult());

                callback.onSuccess(getAssociationParticipation());
            }

            @Override
            public void onFailure(String message) {
                List<AssociationParticipation> participations = getAssociationParticipation();
                if (participations.size() < 1)
                    callback.onFailure("No participation found -" + message);
                else callback.onSuccess(participations);
            }
        });
    }

    @Override
    public void deleteAssociationParticipation(@NonNull AssociationParticipation associationParticipation, BehavidenceCallback<String> callback) {


        if(associationParticipation instanceof AssociationParticipation_Model) {

            AssociationParticipation_Model associationParticipationModel = (AssociationParticipation_Model) associationParticipation;
            _service.deleteAssociation(associationParticipationModel.getAdminModel().getId(), new BehavidenceClientCallback<ParticipationDeletionResponse>() {
                @Override
                public void onSuccess(ParticipationDeletionResponse response) {
                    if(response != null && response.getData() != null)
                        callback.onSuccess(response.getData().getMessage());
                    else callback.onFailure("Failed to delete Association");
                }

                @Override
                public void onFailure(String message) {
                    callback.onFailure(message);
                }
            });
        }
    }

    @Override
    public void deleteResearchParticipation(@NonNull ResearchParticipation researchParticipation, BehavidenceCallback<String> callback) {

        if(researchParticipation instanceof ResearchParticipation_Model) {
            ResearchParticipation_Model researchParticipationModel = (ResearchParticipation_Model) researchParticipation;
            _service.deleteResearch(researchParticipationModel.getAdminModel().getId(), researchParticipationModel.getCode(), new BehavidenceClientCallback<ParticipationDeletionResponse>() {
                @Override
                public void onSuccess(ParticipationDeletionResponse response) {
                    if(response != null && response.getData() != null)
                        callback.onSuccess(response.getData().getMessage());
                    else callback.onFailure("Failed to delete Association");
                }

                @Override
                public void onFailure(String message) {
                    callback.onFailure(message);
                }
            });
        }
    }

    private CodeType getType(String type) {
        switch (type) {
            case "a":
                return CodeType.ASSOCIATION;
            case "r":
                return CodeType.RESEARCH;
            case "ar":
                return CodeType.RESEARCH_ASSOCIATION;
            default:
                return CodeType.ASSOCIATION;
        }
    }

    private void saveParticipation(@NonNull List<Result> results) {

        database.associationRoomDao().deleteAssociations();
        database.associationRoomDao().deleteResearches();

        for (Result result : results) {
            if (result.getOrganizationInfo() != null)
                database.associationRoomDao().insertOrganization(new OrganizationRoom(result.getOrganizationInfo().getName(),
                        result.getOrganizationInfo().getWebsite()));

            if (result.getAdminInfo() != null)
                database.associationRoomDao().insertAdmin(new AdminRoom(
                        result.getAdminInfo().getId(),
                        result.getAdminInfo().getName(),
                        result.getAdminInfo().getMiddleName(),
                        result.getAdminInfo().getOrganizationalName(),
                        result.getAdminInfo().getEmail(),
                        result.getAdminInfo().getAddress(),
                        result.getAdminInfo().getOrgnization()
                ));

            if (result.getAssociation() != null) {
                database.associationRoomDao().insertAssociation(new AssociationRoom(
                        result.getAdminInfo().getId(),
                        result.getAssociation().getCreated(),
                        result.getAssociation().getUpdatedTime(),
                        result.getAssociation().getExpireSec(),
                        result.getAssociation().getCode()
                ));

                if (result.getAssociation().getCustomLabel() != null && result.getAssociation().getCustomLabel().getAndroid() != null) {

                    String adminId = result.getAdminInfo().getId();
                    String code = result.getAssociation().getCode();
                    Android androidLabels = result.getAssociation().getCustomLabel().getAndroid();

                    database.customLabelDao().deleteCustomLabel(adminId, code);
                    long customLabelId = database.customLabelDao().insertCustomLabel(new CustomLabelEntity(
                            null,
                            null,
                            adminId,
                            code,
                            result.getAssociation().getCreated()
                    ));
                    for (MhssLabels labels : androidLabels.getMhssLabels()) {
                        database.labelDao().insertLabel(new LabelEntity((int) labels.getId(),
                                labels.getLabel(),
                                labels.getPriority(),
                                labels.getShowLowMediumHigh(), labels.getReverse(), customLabelId, labels.getCustomUILink()));
                    }

                }

            }

            if (result.getResearches() != null) {
                for (Researches research : result.getResearches()) {
                    database.associationRoomDao().insertResearch(new ResearchRoom(
                            result.getAdminInfo().getId(),
                            Long.parseLong(research.getCreationTime()),
                            research.getCode(),
                            research.getName()
                    ));

                    if (research.getCustomLabel() != null && research.getCustomLabel().getAndroid() != null) {

                        String adminId = result.getAdminInfo().getId();
                        String code = research.getCode();
                        Android androidLabels = research.getCustomLabel().getAndroid();

                        database.customLabelDao().deleteCustomLabel(adminId, code);
                        long customLabelId = database.customLabelDao().insertCustomLabel(new CustomLabelEntity(
                                adminId,
                                code,
                                null,
                                null,
                                Long.parseLong(research.getCreationTime())
                        ));
                        for (MhssLabels labels : androidLabels.getMhssLabels()) {
                            database.labelDao().insertLabel(new LabelEntity((int) labels.getId(),
                                    labels.getLabel(),
                                    labels.getPriority(),
                                    labels.getShowLowMediumHigh(), labels.getReverse(), customLabelId, labels.getCustomUILink()));
                        }

                    }

                }
            }

        }
    }

    public void saveLabelsInLocalDB(CustomLabelEntity customLabelEntity, CodeInformationResponse codeInformation) {

        QuestionnaireOrganizationInfo organization = codeInformation.getOrganizationInfo();
        QuestionnaireAdminInfo admin = codeInformation.getAdminInfo();
        CodeType codeType = getType(codeInformation.getType());
        long currentTime = System.currentTimeMillis();


        if (database.associationRoomDao().getOrganization(organization.getOrganizationName()) == null)
            database.associationRoomDao()
                    .insertOrganization(new OrganizationRoom(organization.getOrganizationName(), organization.getWebsite()));

        if (database.associationRoomDao().getAdmin(admin.getAdminid()) == null)
            database.associationRoomDao().insertAdmin(new AdminRoom(admin.getAdminid(),
                    admin.getAdminName(),
                    admin.getMiddleName(),
                    admin.getOrganizationalName(),
                    admin.getEmail(),
                    admin.getAddress(),
                    admin.getOrganization()));

        if (codeType == CodeType.ASSOCIATION || codeType == CodeType.RESEARCH_ASSOCIATION) {
            if (database.associationRoomDao().getAssociation(admin.getAdminid(), codeInformation.getCode()) == null)
                database.associationRoomDao().insertAssociation(new AssociationRoom(admin.getAdminid(),
                        currentTime,
                        codeInformation.getCode(),
                        codeInformation.getAssociationTimeInSec()));

            customLabelEntity.setAssociationFId(admin.getAdminid());
            customLabelEntity.setAssociationFCode(codeInformation.getCode());
        }

        if (codeType == CodeType.RESEARCH || codeType == CodeType.RESEARCH_ASSOCIATION) {
            if (database.associationRoomDao().getResearch(admin.getAdminid(), codeInformation.getCode()) == null)
                database.associationRoomDao().insertResearch(new ResearchRoom(admin.getAdminid(),
                        currentTime,
                        codeInformation.getCode(),
                        codeInformation.getCodeInfo().getName()));

            customLabelEntity.setResearchFId(admin.getAdminid());
            customLabelEntity.setResearchFCode(codeInformation.getCode());
        }

        String fid = customLabelEntity.getResearchFId() != null ? customLabelEntity.getResearchFId() : customLabelEntity.getAssociationFId();
        String fcode = customLabelEntity.getResearchFCode() != null ? customLabelEntity.getResearchFCode() : customLabelEntity.getAssociationFCode();

        database.customLabelDao().deleteCustomLabel(fid, fcode);

        long customLabelId = database.customLabelDao().insertCustomLabel(customLabelEntity);

        for (QuestionnaireMhssLabels labels : codeInformation.getCodeInfo().getCustomLabel().getAndroid().getMhssLabels()) {
            database.labelDao().insertLabel(new LabelEntity((int) labels.getId(),
                    labels.getLabel(),
                    labels.getPriority(),
                    labels.getShowLowMediumHigh(), labels.getReverse(), customLabelId, labels.getCustomUILink()));
        }
    }

    private List<Participation> getParticipation() {
        List<ResearchRoom> researchRooms = database.associationRoomDao().getResearches();
        List<AssociationRoom> associationRooms = database.associationRoomDao().getAssociations();

        List<Participation> participations = new ArrayList<>();

        for (ResearchRoom room : researchRooms) {
            Participation_Model participation = new Participation_Model(room.getCreatedTimeStamp(), room.getCode());

            AdminRoom admin = database.associationRoomDao().getAdmin(room.getAdminId());
            if (admin != null) {
                participation.setAdmin(new Admin_Model(admin.getId(), admin.getName(), admin.getEmail(), admin.getOrganization()));
                if (admin.getOrganization() != null) {
                    OrganizationRoom organization = database.associationRoomDao().getOrganization(admin.getOrganization());
                    if (organization != null) {
                        participation.setOrganization(new Organization_Model(organization.getOrganizationName(), organization.getWebsite()));
                    }
                }
            }

            participations.add(participation);
        }

        for (AssociationRoom room : associationRooms) {
            Participation_Model participation = new Participation_Model(room.getCreatedTimeStamp(), room.getCode());

            AdminRoom admin = database.associationRoomDao().getAdmin(room.getAdminId());
            if (admin != null) {
                participation.setAdmin(new Admin_Model(admin.getId(), admin.getName(), admin.getEmail(), admin.getOrganization()));
                if (admin.getOrganization() != null) {
                    OrganizationRoom organization = database.associationRoomDao().getOrganization(admin.getOrganization());
                    if (organization != null) {
                        participation.setOrganization(new Organization_Model(organization.getOrganizationName(), organization.getWebsite()));
                    }
                }
            }

            participations.add(participation);
        }

        return participations;

    }

    private List<ResearchParticipation> getResearchParticipation() {
        List<ResearchRoom> researchRooms = database.associationRoomDao().getResearches();

        List<ResearchParticipation> participations = new ArrayList<>();

        for (ResearchRoom room : researchRooms) {
            ResearchParticipation_Model participation = new ResearchParticipation_Model(room.getCreatedTimeStamp(), room.getCode(), room.getName());

            AdminRoom admin = database.associationRoomDao().getAdmin(room.getAdminId());
            if (admin != null) {
                participation.setAdmin(new Admin_Model(admin.getId(), admin.getName(), admin.getEmail(), admin.getOrganization()));
                if (admin.getOrganization() != null) {
                    OrganizationRoom organization = database.associationRoomDao().getOrganization(admin.getOrganization());
                    if (organization != null) {
                        participation.setOrganization(new Organization_Model(organization.getOrganizationName(), organization.getWebsite()));
                    }
                }
            }

            participations.add(participation);
        }

        return participations;

    }

    private List<AssociationParticipation> getAssociationParticipation() {
        List<AssociationRoom> associationRooms = database.associationRoomDao().getAssociations();

        List<AssociationParticipation> participations = new ArrayList<>();

        for (AssociationRoom room : associationRooms) {
            AssociationParticipation_Model participation = new AssociationParticipation_Model(room.getCreatedTimeStamp(), room.getCode(), room.getExpireSeconds());

            AdminRoom admin = database.associationRoomDao().getAdmin(room.getAdminId());
            if (admin != null) {
                participation.setAdmin(new Admin_Model(admin.getId(), admin.getName(), admin.getEmail(), admin.getOrganization()));
                if (admin.getOrganization() != null) {
                    OrganizationRoom organization = database.associationRoomDao().getOrganization(admin.getOrganization());
                    if (organization != null) {
                        participation.setOrganization(new Organization_Model(organization.getOrganizationName(), organization.getWebsite()));
                    }
                }
            }

            participations.add(participation);
        }

        return participations;

    }

}
