package com.behavidence.android.sdk_internal.core.SdkFunctions.Association.AssociationManager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.RoomDb.BehavidenceSDKInternalDb;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Pojos.AssociationDetail;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Pojos.ResearchDetail;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.AdminRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.AssociationRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.AssociationRoomDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.OrganizationRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.ResearchRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.CustomLabelDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.CustomLabelEntity;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.LabelDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.LabelEntity;
import com.behavidence.android.sdk_internal.core.Utils.Executor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public interface AssociationClient {

    static AssociationClient getInstance(@NonNull Context context) {
        return new AssociationClientImpl(context, AuthClient.getInstance(context), ApiKey.getInstance(context));
    }

    /**
     * making network call and getting all assocaitons and researches of the user and saving it in local DB
     */
    Executor<Void> syncAssociationResearches();


    @NonNull
    List<AssociationDetail> getAssociationsFromLocalDb();

    @NonNull
    List<ResearchDetail> getResearchesFromLocalDb();

    /**
     * update association from server as well as from local Database
     *
     * @param association AssociationDetail obj
     */
    Executor<Void> updateAssociation(@NonNull AssociationDetail association);

    /**
     * delete association from server as well as from local Database
     *
     * @param associationDetails List of association details object
     */
    @NonNull
    Executor<Void> deleteAssociations(@NonNull List<AssociationDetail> associationDetails);

    /**
     * delete association from server as well as from local Database
     *
     * @param associationDetails List of association details object
     */
    Executor<Void> deleteAssociation(@NonNull AssociationDetail associationDetails);


    Executor<Boolean> deleteResearch(@NonNull ResearchDetail researchDetails);

    Executor<Boolean> deleteResearchCode(@NonNull List<ResearchDetail> researchDetails);

    Executor<CodeInformation> getCodeInfo(@NonNull String code);

    Executor<CodeInformation> getCodeInfo(@NonNull String code, @Nullable Boolean fetchGroup);

    Executor<CodeInformation> participate(@NonNull CodeInformation codeInformation);

    void deleteAllLocalData();

}


class AssociationClientImpl implements AssociationClient {

    private final Context context;

    private final AssociationRoomDao associationRoomDao;
    private final AssociationApiRequest associationApiRequest;
    private final CodeApiRequest codeApiRequest;


    public AssociationClientImpl(@NonNull Context context, @NonNull AuthClient authClient, @NonNull ApiKey apiKey) {

        this.context = context;

        associationRoomDao = BehavidenceSDKInternalDb.getInstance(context).associationRoomDao();
        associationApiRequest = new AssociationApiRequest(apiKey, authClient);
        codeApiRequest = new CodeApiRequest(authClient, apiKey);

    }

    @Override
    public Executor<Void> syncAssociationResearches() {
        return new Executor<>(() -> {
            List<AssociationResearchObj> associationResearchObjs =
                    codeApiRequest.new GetAssosAndResBuilder()
                            .buildApiCaller().callApiGetResponse();
            saveInLocalDB(associationResearchObjs);
            return null;
        });

    }

    private void saveInLocalDB(@NonNull List<AssociationResearchObj> associationResearchObjList) {

        CustomLabelDao customLabelDao = BehavidenceSDKInternalDb.getInstance(context).customLabelDao();
        LabelDao labelDao = BehavidenceSDKInternalDb.getInstance(context).labelDao();

        for (AssociationResearchObj associationResearchObj : associationResearchObjList) {
            if (associationResearchObj.getOrganizationRoom() != null) {
                associationRoomDao.insertOrganization(associationResearchObj.getOrganizationRoom());
            }
            associationRoomDao.insertAdmin(associationResearchObj.getAdminRoom());
            if (associationResearchObj.getAssociationRoom() != null) {
                associationRoomDao.insertAssociation(associationResearchObj.getAssociationRoom());
            }
            if (associationResearchObj.getResearchRooms() != null) {
                for (ResearchRoom research : associationResearchObj.getResearchRooms()) {
                    associationRoomDao.insertResearch(research);
                }
            }

            List<CustomLabelEntity> customLabels = associationResearchObj.getCustomLabelEntities();
            List<List<LabelEntity>> labels = associationResearchObj.getLabelEntities();

            if(customLabels.size() > 0)
                for(int i = 0; i < customLabels.size(); ++i){

                    CustomLabelEntity entity = customLabels.get(i);
                    List<LabelEntity> lab = labels.get(i);

                    String fid = entity.getResearchFId() != null ? entity.getResearchFId() : entity.getAssociationFId();
                    String fcode = entity.getResearchFCode() != null ? entity.getResearchFCode() : entity.getAssociationFCode();

                    customLabelDao.deleteCustomLabel(fid, fcode);
                    long customLabelId = customLabelDao.insertCustomLabel(entity);

                    for(LabelEntity label: lab){
                        label.setCustomLabelId(customLabelId);
                        labelDao.insertLabel(label);
                    }

                }

        }
    }

    public void saveLabelsInLocalDB(@NonNull CodeInformation codeInformation) {

        if (codeInformation.getLabels() != null && codeInformation.getLabels().size() > 0) {

            CustomLabelDao customLabelDao = BehavidenceSDKInternalDb.getInstance(context).customLabelDao();
            LabelDao labelDao = BehavidenceSDKInternalDb.getInstance(context).labelDao();

            Organization organization = codeInformation.getOrganization();
            Admin admin = codeInformation.getAdmin();
            CodeType codeType = codeInformation.getCodeType();
            long currentTime = System.currentTimeMillis();
            CustomLabelEntity customLabelEntity = new CustomLabelEntity();


            if(associationRoomDao.getOrganization(organization.getOrganizationName()) == null)
                associationRoomDao.insertOrganization(new OrganizationRoom(organization.getOrganizationName(), organization.getWebsite()));

            if(associationRoomDao.getAdmin(admin.getId()) == null)
                associationRoomDao.insertAdmin(new AdminRoom(admin.getId(),
                        admin.getName(),
                        admin.getMiddleName(),
                        admin.getOrganizationalName(),
                        admin.getEmail(),
                        admin.getAddress(),
                        admin.getOrganization()));

            if(codeType == CodeType.ASSOCIATION || codeType == CodeType.ASSOCIATION_RESEARCH) {
                if(associationRoomDao.getAssociation(admin.getId(), codeInformation.getCode()) == null)
                    associationRoomDao.insertAssociation(new AssociationRoom(admin.getId(),
                            currentTime,
                            codeInformation.getCode(),
                            codeInformation.getAssociationExpTimeInSec()));

                customLabelEntity.setAssociationFId(admin.getId());
                customLabelEntity.setAssociationFCode(codeInformation.getCode());
            }

            if(codeType == CodeType.RESEARCH || codeType == CodeType.ASSOCIATION_RESEARCH) {
                if(associationRoomDao.getResearch(admin.getId(), codeInformation.getCode()) == null)
                    associationRoomDao.insertResearch(new ResearchRoom(admin.getId(),
                            currentTime,
                            codeInformation.getCode(),
                            codeInformation.getName()));

                customLabelEntity.setResearchFId(admin.getId());
                customLabelEntity.setResearchFCode(codeInformation.getCode());
            }

            String fid = customLabelEntity.getResearchFId() != null ? customLabelEntity.getResearchFId() : customLabelEntity.getAssociationFId();
            String fcode = customLabelEntity.getResearchFCode() != null ? customLabelEntity.getResearchFCode() : customLabelEntity.getAssociationFCode();

            customLabelDao.deleteCustomLabel(fid, fcode);

            long customLabelId = customLabelDao.insertCustomLabel(customLabelEntity);

            for(LabelEntity label: codeInformation.getLabels()){
                label.setCustomLabelId(customLabelId);
                labelDao.insertLabel(label);
            }

        }
    }

    @Override
    @NonNull
    public List<AssociationDetail> getAssociationsFromLocalDb() {
        return associationRoomDao.getAssociationsWithAdminDetails();
    }

    @NonNull
    @Override
    public List<ResearchDetail> getResearchesFromLocalDb() {
        return associationRoomDao.getResearchWithAdminDetails();
    }


    //to check if we can make abstract class to fill values from room android
    @Override
    public Executor<Void> updateAssociation(@NonNull AssociationDetail associationDetail) {
        return new Executor<>(() -> {
            associationApiRequest.new UpdateAssociationBuilder(associationDetail.adminId, associationDetail.expireSeconds)
                    .buildApiCaller().callApiGetResponse();
            AssociationRoom associationRoom = new AssociationRoom(associationDetail.adminId, associationDetail.createdTimeStamp,
                    (Calendar.getInstance().getTimeInMillis() / 1000), associationDetail.expireSeconds, associationDetail.code);
            associationRoomDao.insertAssociation(associationRoom);
            return null;
        });
    }

    @Override
    public Executor<Void> deleteAssociation(@NonNull AssociationDetail associationDetail) {
        List<AssociationDetail> associationDetails = new ArrayList<>();
        associationDetails.add(associationDetail);
        return deleteAssociations(associationDetails);
    }

    @Override
    public Executor<Boolean> deleteResearch(@NonNull ResearchDetail researchDetails) {
        List<ResearchDetail> researchDetail = new ArrayList<>();
        researchDetail.add(researchDetails);
        return deleteResearchCode(researchDetail);
    }

    @Override
    public Executor<CodeInformation> getCodeInfo(@NonNull String code) {
        return new Executor<>(codeApiRequest.new GetCodeInfoBuilder(context, code).buildApiCaller());
    }

    @Override
    public Executor<CodeInformation> getCodeInfo(@NonNull String code, @Nullable Boolean fetchGroup) {
        return new Executor<>(codeApiRequest.new GetCodeInfoBuilder(context, code, fetchGroup).buildApiCaller());
    }

    @Override
    public Executor<CodeInformation> participate(@NonNull CodeInformation codeInformation) {
        saveLabelsInLocalDB(codeInformation);
        return new Executor<>(codeApiRequest.new ParticipateBuilder(codeInformation).buildApiCaller());
    }

    @Override
    public void deleteAllLocalData() {
        associationRoomDao.deleteAssociations();
        associationRoomDao.deleteResearches();
        associationRoomDao.deleteOrganizations();
        associationRoomDao.deleteAdmins();
    }

    @NonNull
    @Override
    public Executor<Void> deleteAssociations(@NonNull List<AssociationDetail> associationDetails) {
        return new Executor<>(() -> {
            associationApiRequest.new DeleteAssociationBuilder(getAdminIds(associationDetails))
                    .buildApiCaller().callApiGetResponse();
            deleteAssociationFromLocalDB(associationDetails);
            return null;
        });
    }

    @NonNull
    @Override
    public Executor<Boolean> deleteResearchCode(@NonNull List<ResearchDetail> researchDetails) {
        return new Executor<>(() -> {

            String response = associationApiRequest.new DeleteResearchBuilder(getResearchAdminIds(researchDetails),
                    getResearchCodes(researchDetails))
                    .buildApiCaller().callApiGetResponse();
            Log.d("AssociationClientSDK", response + " ");
            if (response.equals("research deleted successfully")) {
                deleteResearchFromLocalDB(researchDetails);
                return true;
            }
            return false;
        });
    }

    private String[] getAdminIds(@NonNull List<AssociationDetail> associationDetails) {
        String[] adminids = new String[associationDetails.size()];
        for (int i = 0; i < associationDetails.size(); i++)
            adminids[i] = associationDetails.get(i).adminId;
        return adminids;
    }

    private String[] getResearchAdminIds(@NonNull List<ResearchDetail> researchDetails) {
        String[] adminids = new String[researchDetails.size()];
        for (int i = 0; i < researchDetails.size(); i++)
            adminids[i] = researchDetails.get(i).adminId;
        return adminids;
    }

    private String[] getResearchCodes(@NonNull List<ResearchDetail> researchDetails) {
        String[] codes = new String[researchDetails.size()];
        for (int i = 0; i < researchDetails.size(); i++)
            codes[i] = researchDetails.get(i).code;
        return codes;
    }

    private void deleteAssociationFromLocalDB(@NonNull List<AssociationDetail> associationDetails) {
        for (AssociationDetail associationDetail : associationDetails)
            associationRoomDao.deleteAssociation(
                    associationDetail.adminId, associationDetail.code);
    }

    private void deleteResearchFromLocalDB(@NonNull List<ResearchDetail> researchDetails) {
        for (ResearchDetail researchDetail : researchDetails)
            associationRoomDao.deleteResearch(
                    researchDetail.adminId, researchDetail.code);
    }


}