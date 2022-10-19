package com.behavidence.android.sdk_internal.core.SdkFunctions.Association.AssociationManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.AdminRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.AssociationRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.OrganizationRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.ResearchRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.CustomLabelDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.CustomLabelEntity;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.LabelEntity;

import java.util.ArrayList;
import java.util.List;

class AssociationResearchObj {
   private final OrganizationRoom organizationRoom;
   private final AdminRoom adminRoom;
   private final AssociationRoom associationRoom;
   private final List<ResearchRoom> researchRooms;
   private final List<CustomLabelEntity> customLabelEntities;
   private final List<List<LabelEntity>> labelEntities;

    public AssociationResearchObj(@Nullable OrganizationRoom organizationRoom, @NonNull AdminRoom adminRoom, @Nullable AssociationRoom associationRoom, @Nullable List<ResearchRoom> researchRooms) {
        this.organizationRoom = organizationRoom;
        this.adminRoom = adminRoom;
        this.associationRoom = associationRoom;
        this.researchRooms = researchRooms;
        this.customLabelEntities = new ArrayList<>();
        this.labelEntities = new ArrayList<>();
    }

    public OrganizationRoom getOrganizationRoom() {
        return organizationRoom;
    }

    public AdminRoom getAdminRoom() {
        return adminRoom;
    }

    public AssociationRoom getAssociationRoom() {
        return associationRoom;
    }

    public List<ResearchRoom> getResearchRooms() {
        return researchRooms;
    }

    public List<CustomLabelEntity> getCustomLabelEntities() {
        return customLabelEntities;
    }

    public List<List<LabelEntity>> getLabelEntities() {
        return labelEntities;
    }

    public void addCustomLabel(CustomLabelEntity customLabelEntity) {
        this.customLabelEntities.add(customLabelEntity);
    }

    public void addLabelEntity(List<LabelEntity> label){
        this.labelEntities.add(label);
    }
}
