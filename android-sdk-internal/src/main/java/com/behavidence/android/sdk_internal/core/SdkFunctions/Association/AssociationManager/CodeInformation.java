package com.behavidence.android.sdk_internal.core.SdkFunctions.Association.AssociationManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.LabelEntity;

import java.util.List;

public interface CodeInformation {

    void setAssociationExpTimeInSec(long associationExpTimeInSec);

    long getAssociationExpTimeInSec();

    @NonNull
    public Admin getAdmin();

    @Nullable
    public Organization getOrganization();

    @Nullable
    String getName();

    @NonNull
    String getCode();

    @NonNull
    CodeType getCodeType();

    @Nullable
    String getHeader();

    @Nullable
    Consent getConsent();

    @Nullable
    public List<ResearchQuestion> getResearchQuestions();

    @Nullable
    public List<List<ResearchQuestion>> getQuestionGroup();

    @Nullable
    public List<String> getGroupHeaders();

    @Nullable
    public String getCustomUIName();

    @Nullable
    public List<LabelEntity> getLabels();

}

class CodeInformationImpl implements CodeInformation{
    private final Organization organization;
    private final Admin admin;
    private final CodeType codeType;
    private final String code;
    private final Consent consent;
    private  long associationExpTimeInSec;
    private final String header;
    private final String name;
    private final List<ResearchQuestion> researchQuestions;


    private final String customUIName;
    private final List<List<ResearchQuestion>> researchQuestionGroup;
    private final List<String> groupHeaders;
    private final List<LabelEntity> labels;

    public CodeInformationImpl(@Nullable Organization organization, @NonNull Admin admin,@NonNull CodeType codeType,@NonNull String code,@NonNull Consent consent, long associationExpTimeInSec,@Nullable String header,@Nullable String name, @Nullable List<ResearchQuestion> researchQuestions, List<LabelEntity> labels) {
        this.organization = organization;
        this.admin = admin;
        this.codeType = codeType;
        this.code = code;
        this.consent = consent;
        this.associationExpTimeInSec = associationExpTimeInSec;
        this.header = header;
        this.name = name;
        this.researchQuestions = researchQuestions;
        this.labels = labels;

        this.customUIName = null;
        this.researchQuestionGroup = null;
        this.groupHeaders = null;
    }

    public CodeInformationImpl(@Nullable Organization organization, @NonNull Admin admin,@NonNull CodeType codeType,@NonNull String code,@NonNull Consent consent, long associationExpTimeInSec,@Nullable String header,@Nullable String name, @Nullable String customUIName, @Nullable List<List<ResearchQuestion>> researchQuestions, List<String> headers, List<LabelEntity> labels) {
        this.organization = organization;
        this.admin = admin;
        this.codeType = codeType;
        this.code = code;
        this.consent = consent;
        this.associationExpTimeInSec = associationExpTimeInSec;
        this.header = header;
        this.name = name;
        this.researchQuestionGroup = researchQuestions;
        this.groupHeaders = headers;
        this.customUIName = customUIName;
        this.labels = labels;

        this.researchQuestions = null;
    }

    @Override
    public void setAssociationExpTimeInSec(long associationExpTimeInSec) {
        this.associationExpTimeInSec=associationExpTimeInSec;
    }

    @Override
    public long getAssociationExpTimeInSec() {
        return associationExpTimeInSec;
    }

    @NonNull
    @Override
    public Admin getAdmin() {
        return admin;
    }

    @Nullable
    @Override
    public Organization getOrganization() {
        return organization;
    }

    @Nullable
    @Override
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String getCode() {
        return code;
    }

    @NonNull
    @Override
    public CodeType getCodeType() {
        return codeType;
    }

    @Nullable
    @Override
    public String getHeader() {
        return header;
    }

    @Nullable
    @Override
    public Consent getConsent() {
        return consent;
    }

    @Nullable
    @Override
    public List<ResearchQuestion> getResearchQuestions() {
        return researchQuestions;
    }

    @Nullable
    @Override
    public List<List<ResearchQuestion>> getQuestionGroup() {
        return researchQuestionGroup;
    }

    @Nullable
    @Override
    public List<String> getGroupHeaders() {
        return groupHeaders;
    }

    @Nullable
    @Override
    public String getCustomUIName(){
        return customUIName;
    }


    @Nullable
    @Override
    public List<LabelEntity> getLabels() {
        return labels;
    }
}

