package com.behavidence.android.sdk_internal.data.room_model.Participation;


import static androidx.room.ForeignKey.SET_NULL;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = OrganizationRoom.class,
        parentColumns = "organizationName",
        childColumns = "organization",
        onDelete = SET_NULL),
         indices = {@Index("name"),
        @Index(value = {"organization"})}
)
public class AdminRoom {

    @PrimaryKey
    @NonNull
    private final String id;
    private String name;
    private String middleName;
    private String organizationalName;
    private String email;
    private String address;
    private String organization;

    @Ignore
    public AdminRoom(@NonNull String id) {
        this.id = id;

    }

    public AdminRoom(@NonNull String id, @Nullable String name, @Nullable String middleName, @Nullable String organizationalName, @Nullable String email, @Nullable String address, @Nullable String organization) {
        this.id = id;
        this.name = name;
        this.middleName = middleName;
        this.organizationalName = organizationalName;
        this.email = email;
        this.address = address;
        this.organization = organization;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setOrganizationalName(String organizationalName) {
        this.organizationalName = organizationalName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getOrganizationalName() {
        return organizationalName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getOrganization() {
        return organization;
    }

    @Ignore
    public  void printValues(@NonNull String key){
        Log.e(key,"id: "+getId()+"   organization: "+organization+"    email"+email+"   name"+organizationalName);

    }

}
