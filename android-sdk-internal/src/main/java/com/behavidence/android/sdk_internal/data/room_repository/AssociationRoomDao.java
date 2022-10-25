package com.behavidence.android.sdk_internal.data.room_repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


import com.behavidence.android.sdk_internal.data.room_model.Participation.AdminRoom;
import com.behavidence.android.sdk_internal.data.room_model.Participation.AssociationRoom;
import com.behavidence.android.sdk_internal.data.room_model.Participation.OrganizationRoom;
import com.behavidence.android.sdk_internal.data.room_model.Participation.ResearchRoom;
import com.behavidence.android.sdk_internal.data.room_model.Participation.join.AssociationDetail;
import com.behavidence.android.sdk_internal.data.room_model.Participation.join.ResearchDetail;

import java.util.List;

@Dao
interface AssociationRoomDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertAdmin(AdminRoom adminRoom);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertAssociation(AssociationRoom association);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertOrganization(OrganizationRoom organizationRoom);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertResearch(ResearchRoom research);

        @Query("DELETE FROM AdminRoom WHERE id=:id")
        void deleteAdmin( String id);

        @Query("DELETE FROM AssociationRoom WHERE adminId=:adminId AND code=:code")
        void deleteAssociation( String adminId,String code);

        @Query("DELETE FROM AssociationRoom")
        void deleteAssociations();

        @Query("DELETE FROM ResearchRoom")
        void deleteResearches();

        @Query("DELETE FROM AdminRoom")
        void deleteAdmins();

        @Query("DELETE FROM OrganizationRoom")
        void deleteOrganizations();

        @Query("DELETE FROM OrganizationRoom WHERE organizationName=:name")
        void deleteOrganization(String name);

        @Query("DELETE FROM ResearchRoom WHERE adminId=:adminId AND code=:code")
        void deleteResearch( String adminId,String code);

        @Query("SELECT * FROM AdminRoom")
        List<AdminRoom> getAdmins();

        @Query("SELECT * FROM AdminRoom WHERE id=:id")
        AdminRoom getAdmin(String id);

        @Query("SELECT * FROM AssociationRoom WHERE adminId=:adminId AND code=:code")
        AssociationRoom getAssociation(String adminId, String code);

        @Query("SELECT * FROM ResearchRoom WHERE adminId=:adminId AND code=:code")
        ResearchRoom getResearch(String adminId, String code);

        @Query("SELECT * FROM AssociationRoom")
        List<AssociationRoom> getAssociations();

        @Query("SELECT * FROM ResearchRoom")
        List<ResearchRoom> getResearches();

        @Query("SELECT * FROM OrganizationRoom WHERE organizationName=:organization")
        OrganizationRoom getOrganization(String organization);

        @Query("SELECT * FROM OrganizationRoom")
        List<OrganizationRoom> getOrganizations();

        @Transaction
        @Query("SELECT AssociationRoom.adminId , AssociationRoom.code, AssociationRoom.createdTimeStamp, AssociationRoom.expireSeconds,AssociationRoom.updatedTimeStamp " +
                ",AdminRoom.organizationalName , AdminRoom.organization, AdminRoom.email " +
                "From AdminRoom INNER JOIN AssociationRoom ON AdminRoom.id=AssociationRoom.adminId")
        List<AssociationDetail> getAssociationsWithAdminDetails();

        @Transaction
        @Query("SELECT ResearchRoom.adminId , ResearchRoom.code, ResearchRoom.createdTimeStamp, ResearchRoom.name" +
                ",AdminRoom.organizationalName , AdminRoom.organization, AdminRoom.email " +
                "From AdminRoom  INNER JOIN ResearchRoom ON AdminRoom.id=ResearchRoom.adminId")
        List<ResearchDetail> getResearchWithAdminDetails();









}


