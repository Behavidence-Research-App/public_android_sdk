package com.behavidence.android.sdk_internal.core.SdkFunctions.Association.AssociationManager;

import static com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor.USER_API;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;
import com.behavidence.android.sdk_internal.core.Networks.ApiRequestTemplate;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor;
import com.behavidence.android.sdk_internal.core.RoomDb.BehavidenceSDKInternalDb;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.AdminRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.AssociationRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.AssociationRoomDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.OrganizationRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.Association.Room.ResearchRoom;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.CustomLabelDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.CustomLabelEntity;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.LabelDao;
import com.behavidence.android.sdk_internal.core.SdkFunctions.CustomUILabels.Room.LabelEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Request;

public class CodeApiRequest {
    private static final String apiPath = USER_API + "/code";
    private static final String participateApiPath = apiPath + "/participate";
    protected final AuthClient authClient;
    protected final ApiKey apiKey;
    protected static final String QUESTION_ID_TAG = "qid";
    protected static final String QUESTION_TAG = "question";
    protected static final String QUESTIONS_TAG = "questions";
    protected static final String QUESTION_NO_TAG = "no";
    protected static final String OPTION_TAG = "options";
    protected static final String MULTIPLE_TAG = "multiple";

    CodeApiRequest(@NonNull AuthClient authClient, @NonNull ApiKey apiKey) {
        this.authClient = authClient;
        this.apiKey = apiKey;
    }

    class GetCodeInfoBuilder extends ApiRequestTemplate<CodeInformation> {

        private final String code;
        private final Boolean fetchGroup;
        private final Context context;

        public GetCodeInfoBuilder(@NonNull Context context, @NonNull String code) {
            super(apiKey, authClient);
            this.code = code;
            this.fetchGroup = null;
            this.context = context;
        }

        public GetCodeInfoBuilder(@NonNull Context context, @NonNull String code, @Nullable Boolean fetchGroup) {
            super(apiKey, authClient);
            this.code = code;
            this.fetchGroup = fetchGroup;
            this.context = context;
        }

        @NonNull
        private AdminRoom getAdminInfoFromJson(@NonNull JSONObject jsonObject) throws JSONException {
            JSONObject adminJson = jsonObject.getJSONObject("admin_info");
//            AdminRoom admin = new AdminRoom("NAN");

            AdminRoom admin = new AdminRoom(adminJson.getString("adminid"));
            admin.setOrganizationalName(adminJson.getString("organizational_name"));
            admin.setEmail(adminJson.getString("email"));
            if (adminJson.has("name"))
                admin.setName(adminJson.getString("name"));
            if (adminJson.has("middle_name"))
                admin.setMiddleName(adminJson.getString("middle_name"));
            if (adminJson.has("organization"))
                admin.setOrganization(adminJson.getString("organization"));
            return admin;
        }

        @Nullable
        private OrganizationRoom getOrgInfoFromJson(@NonNull JSONObject jsonObject) throws JSONException {
            if (!jsonObject.has("organization_info")) return null;

            JSONObject orgJsonObj = jsonObject.getJSONObject("organization_info");
            OrganizationRoom organization = new OrganizationRoom(orgJsonObj.getString("name"));
            if (orgJsonObj.has("website"))
                organization.setWebsite(orgJsonObj.getString("website"));
            return organization;
        }

        @Nullable
        private List<ResearchQuestion> getResearchQuestions(@NonNull JSONObject codeInfoJson) throws JSONException {
            if (!codeInfoJson.has("questions")) return null;

            JSONArray questionArray = codeInfoJson.getJSONArray("questions");
            ArrayList<ResearchQuestion> researchQuestions = new ArrayList<>();
            String[] options;
            for (int i = 0; i < questionArray.length(); i++) {
                options = new String[questionArray.getJSONObject(i).getJSONArray(OPTION_TAG).length()];
                for (int j = 0; j < options.length; j++)
                    options[j] = questionArray.getJSONObject(i).getJSONArray(OPTION_TAG).getString(j);

                researchQuestions.add(
                        new ResearchQuestionImpl(questionArray.getJSONObject(i).getString(QUESTION_ID_TAG),
                                questionArray.getJSONObject(i).getInt(QUESTION_NO_TAG)
                                , questionArray.getJSONObject(i).getString(QUESTION_TAG),
                                options,
                                questionArray.getJSONObject(i).getBoolean(MULTIPLE_TAG)));
            }
            return researchQuestions;

        }

        @Nullable
        private List<List<ResearchQuestion>> getResearchQuestionGroup(@NonNull JSONObject codeInfoJson) throws JSONException {

            JSONArray questionGroup = codeInfoJson.getJSONArray("questions_group");
            ArrayList<List<ResearchQuestion>> researchQuestionsGroup = new ArrayList<>();

            for (int k = 0; k < questionGroup.length(); k++) {
                JSONArray questionArray = questionGroup.getJSONObject(k).getJSONArray("questions");
                ArrayList<ResearchQuestion> researchQuestions = new ArrayList<>();
                String[] options;
                for (int i = 0; i < questionArray.length(); i++) {
                    options = new String[questionArray.getJSONObject(i).getJSONArray(OPTION_TAG).length()];
                    for (int j = 0; j < options.length; j++)
                        options[j] = questionArray.getJSONObject(i).getJSONArray(OPTION_TAG).getString(j);

                    researchQuestions.add(
                            new ResearchQuestionImpl(questionArray.getJSONObject(i).getString(QUESTION_ID_TAG),
                                    questionArray.getJSONObject(i).getInt(QUESTION_NO_TAG)
                                    , questionArray.getJSONObject(i).getString(QUESTION_TAG),
                                    options,
                                    questionArray.getJSONObject(i).getBoolean(MULTIPLE_TAG)));
                }

                researchQuestionsGroup.add(researchQuestions);
            }
            return researchQuestionsGroup;

        }

        @Nullable
        private List<String> getQuestionHeaders(@NonNull JSONObject codeInfoJson) throws JSONException {

            JSONArray questionGroup = codeInfoJson.getJSONArray("questions_group");
            ArrayList<String> questionHeaders = new ArrayList<>();

            for (int k = 0; k < questionGroup.length(); k++) {
                String header = "";

                if (questionGroup.getJSONObject(k).has("header")) {
                    header = questionGroup.getJSONObject(k).getString("header");
                }

                questionHeaders.add(header);

            }
            return questionHeaders;

        }

        @NonNull
        private CodeType getCodeType(JSONObject codeInfoJson) throws JSONException {
            String type = codeInfoJson
                    .getString("type");
            switch (type) {
                case "r":
                    return CodeType.RESEARCH;

                case "ar":
                    return CodeType.ASSOCIATION_RESEARCH;

                case "a":
                    return CodeType.ASSOCIATION;

                default:
                    throw new InvalidParameterException("Invalid code type while parsing");

            }

        }

        @Nullable
        private String getStringFromCodeJson(@NonNull JSONObject codeJson, @NonNull String key) throws JSONException {
            if (codeJson.has(key))
                return codeJson.getString(key);
            return null;
        }


        @Nullable
        private Consent getConsent(@NonNull JSONObject codeJson) throws JSONException {
            if (!codeJson.has("consent"))
                return null;
            ConsentImpl consent = new ConsentImpl();
            if (codeJson.getJSONObject("consent").has("html_text"))
                consent.setHtmlTxt(codeJson.getJSONObject("consent").getString("html_text"));

            if (codeJson.getJSONObject("consent").has("text"))
                consent.setHtmlTxt(codeJson.getJSONObject("consent").getString("text"));
            return consent;
        }

        private long getAssociationTime(@NonNull JSONObject codeJson) throws JSONException {
            if (codeJson.has("association_time"))
                return codeJson.getLong("association_time");
            else
                return 0L;
        }


        @Override
        protected Request buildRequest() {
            Map<String, String[]> parameters;
            parameters = new HashMap<>();
            parameters.put("code", new String[]{code});

            if (fetchGroup != null && fetchGroup)
                parameters.put("fetch_group", new String[]{"true"});

            return ApiRequestConstructor.constructGetRequest(getHeaders(), parameters, apiPath).getRequestObj();
        }

        @Nullable
        @Override
        protected CodeInformation buildResponse(@NonNull JSONObject jsonObject) {
            try {

                JSONObject dataJson = jsonObject.getJSONObject("data");
                JSONObject codeJson = dataJson.getJSONObject("code_info");

                OrganizationRoom organization = getOrgInfoFromJson(dataJson);
                AdminRoom admin = getAdminInfoFromJson(dataJson);

                List<ResearchQuestion> researchQuestions = getResearchQuestions(codeJson);
                CodeType codeType = getCodeType(codeJson);
                Consent consent = getConsent(codeJson);
                long associationTime = getAssociationTime(codeJson);
                String researchName = getStringFromCodeJson(codeJson, "name");
                String htmlHeader = getStringFromCodeJson(codeJson, "header");
                String customUIName = getStringFromCodeJson(codeJson, "custom_ui_name");

                List<LabelEntity> labels = getCustomLabels(codeJson);

                if (codeJson.has("questions_group")) {
                    List<List<ResearchQuestion>> questionGroup = getResearchQuestionGroup(codeJson);
                    List<String> questionHeaders = getQuestionHeaders(codeJson);

                    return new CodeInformationImpl(
                            new OrganizationImpl(organization),
                            new AdminImpl(admin),
                            codeType,
                            code,
                            consent,
                            associationTime,
                            htmlHeader,
                            researchName,
                            customUIName,
                            questionGroup,
                            questionHeaders,
                            labels
                    );

                } else {
                    return new CodeInformationImpl(
                            new OrganizationImpl(organization),
                            new AdminImpl(admin),
                            codeType,
                            code,
                            consent,
                            associationTime,
                            htmlHeader,
                            researchName,
                            researchQuestions,
                            labels
                    );
                }

            } catch (JSONException e) {
                throw new InvalidParameterException(e.getMessage());
            }
        }

        private List<LabelEntity> getCustomLabels(JSONObject codeJson) {

            List<LabelEntity> labels = new ArrayList<>();

            try {
                if (codeJson.has("custom_label")) {
                    JSONObject labelObject = codeJson.getJSONObject("custom_label");

                    if (labelObject.has("android")) {
                        JSONObject android = labelObject.getJSONObject("android");

                        if(android.has("mhss_labels")) {
                            JSONArray labelArray = android.getJSONArray("mhss_labels");

                            for (int i = 0; i < labelArray.length(); ++i) {
                                JSONObject obj = labelArray.getJSONObject(i);

                                int id = obj.getInt("id");
                                String label = obj.getString("label");
                                int priority = obj.getInt("priority");
                                boolean showLmh = obj.getBoolean("show_low_medium_high");
                                boolean reverse = obj.getBoolean("reverse");
                                String customUiLink = obj.optString("custom_ui_link", null);

                                LabelEntity entity = new LabelEntity(id, label, priority, showLmh, reverse, 0, customUiLink);
                                labels.add(entity);
                            }
                        }
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return labels;
        }
    }

    public class ParticipateBuilder extends ApiRequestTemplate<CodeInformation> {

        private final CodeInformation codeInformation;

        public ParticipateBuilder(@NonNull CodeInformation codeInformation) {
            super(apiKey, authClient);
            this.codeInformation = codeInformation;
        }

        private void validateAssociationParams() {
            if (codeInformation.getAssociationExpTimeInSec() < 1)
                throw new InvalidArgumentException("association not valid");
        }

        private void validateResearchParams() {
            for (ResearchQuestion researchQuestion : codeInformation.getResearchQuestions()) {
                if (researchQuestion.getSelectedAnswers().size() < 1)
                    throw new InvalidArgumentException("please answer all questions");
            }
        }

        private void putAssociationInfo(@NonNull JSONObject jsonObject) throws JSONException {
            jsonObject.put("expire_sec", codeInformation.getAssociationExpTimeInSec());
        }

        private void putResearchInfo(@NonNull JSONObject response) throws JSONException {
            JSONArray questionsArray = new JSONArray();
            JSONObject question;
            JSONArray answersArray;
            List<ResearchQuestion> researchQuestions = codeInformation.getResearchQuestions();
            for (int i = 0; i < researchQuestions.size(); i++) {
                question = new JSONObject();
                answersArray = new JSONArray();
                question.put(QUESTION_ID_TAG, researchQuestions.get(i).getGroupId());
                question.put(QUESTION_NO_TAG, researchQuestions.get(i).getQno());
                List<String> selectedAnswers = researchQuestions.get(i).getSelectedAnswers();
                for (String answer : selectedAnswers)
                    answersArray.put(answer);

                question.put(OPTION_TAG, answersArray);
                questionsArray.put(question);
            }

            response.put(QUESTIONS_TAG, questionsArray);

        }


        //path need to be decided
        @Override
        protected Request buildRequest() {
            try {

                JSONObject req = new JSONObject();
                req.put("code", codeInformation.getCode());
                req.put("date", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).format(new Date()));
                if (codeInformation.getCodeType() == CodeType.ASSOCIATION || codeInformation.getCodeType() == CodeType.ASSOCIATION_RESEARCH) {
                    validateAssociationParams();
                    putAssociationInfo(req);
                }
                if (codeInformation.getCodeType() == CodeType.RESEARCH || codeInformation.getCodeType() == CodeType.ASSOCIATION_RESEARCH) {
                    validateResearchParams();
                    putResearchInfo(req);
                }
                return ApiRequestConstructor.constructPostRequestJSON(req, getHeaders(), participateApiPath).getRequestObj();
            } catch (JSONException e) {
                throw new InvalidParameterException(e.getMessage());
            }
        }

        @Nullable
        @Override
        protected CodeInformation buildResponse(@NonNull JSONObject jsonObject) {
            try {
                String adminId = jsonObject.getJSONObject("data").getString("adminid");
                AdminRoom admin = new AdminRoom(adminId, codeInformation.getAdmin().getName(), codeInformation.getAdmin().getMiddleName(), codeInformation.getAdmin().getOrganizationalName(),
                        codeInformation.getAdmin().getEmail(), codeInformation.getAdmin().getAddress(), codeInformation.getAdmin().getOrganization());
                return new CodeInformationImpl(
                        codeInformation.getOrganization(),
                        new AdminImpl(admin),
                        codeInformation.getCodeType(),
                        codeInformation.getCode(),
                        codeInformation.getConsent(),
                        codeInformation.getAssociationExpTimeInSec(),
                        codeInformation.getHeader(),
                        codeInformation.getName(),
                        codeInformation.getResearchQuestions(),
                        null
                );

            } catch (JSONException e) {
                throw new InvalidParameterException(e.getMessage());
            }
        }
    }


    class GetAssosAndResBuilder extends ApiRequestTemplate<List<AssociationResearchObj>> {


        public GetAssosAndResBuilder() {
            super(apiKey, authClient);
        }


        @Nullable
        private OrganizationRoom getOrganizationFromJson(@NonNull JSONObject result) throws JSONException {
            if (!result.has("organization_info")) return null;
            JSONObject organizationJson = result.getJSONObject("organization_info");
            OrganizationRoom organization = new OrganizationRoom(organizationJson.getString("name"));
            if (organizationJson.has("website"))
                organization.setWebsite(organizationJson.getString("webiste"));
            return organization;
        }

        @NonNull
        private AdminRoom getAdminFromJson(@NonNull JSONObject result) throws JSONException {
            JSONObject adminJson = result.getJSONObject("admin_info");
            AdminRoom admin = new AdminRoom(adminJson.getString("id"));
            admin.setEmail(adminJson.getString("email"));
            admin.setOrganizationalName(adminJson.getString("organizational_name"));

            if (adminJson.has("name"))
                admin.setName(adminJson.getString("name"));

            if (adminJson.has("middle_name"))
                admin.setMiddleName(adminJson.getString("middle_name"));

            if (adminJson.has("orgnization"))
                admin.setOrganization(adminJson.getString("orgnization"));

            return admin;
        }


        @Nullable
        private AssociationRoom getAssociationFromJson(@NonNull JSONObject result) throws JSONException {
            if (!result.has("association")) return null;

            JSONObject associationJson = result.getJSONObject("association");
            AssociationRoom association = new AssociationRoom(result.getJSONObject("admin_info").getString("id"),
                    associationJson.getLong("created"),
                    associationJson.getString("code"),
                    associationJson.getLong("expire_sec")
            );
            if (associationJson.has("last_updated"))
                association.setUpdatedTimeStamp(associationJson.getLong("last_updated"));
            return association;
        }

        @Nullable
        private List<ResearchRoom> getResearchesFromJson(@NonNull JSONObject result) throws JSONException {

            if (!result.has("researches")) return null;
            JSONArray researchArrays = result.getJSONArray("researches");
            String adminId = result.getJSONObject("admin_info").getString("id");

            List<ResearchRoom> researches = new ArrayList<>();
            ResearchRoom research;
            JSONObject researchJson;

            for (int i = 0; i < researchArrays.length(); i++) {
                researchJson = researchArrays.getJSONObject(i);

                research = new ResearchRoom(
                        adminId, researchJson.getLong("creation_time"),
                        researchJson.getString("code"));
                if (researchJson.has("name"))
                    research.setName(researchJson.getString("name"));
                researches.add(research);
            }
            return researches;

        }

        private void getCustomLabels(JSONObject result, AssociationResearchObj assocObject){

            try {
                if (result.has("researches")) {
                    JSONArray researchArrays = result.getJSONArray("researches");
                    String adminId = result.getJSONObject("admin_info").getString("id");

                    for (int i = 0; i < researchArrays.length(); i++) {
                        JSONObject researchJson = researchArrays.getJSONObject(i);
                        List<LabelEntity> labelEntities = new ArrayList<>();

                        long creationTime = Long.parseLong(researchJson.getString("creation_time"));
                        String code = researchJson.getString("code");


                        if(researchJson.has("custom_label")){
                            JSONObject customLabel = researchJson.getJSONObject("custom_label");
                            if(customLabel.has("android")){
                                JSONObject android = customLabel.getJSONObject("android");
                                if(android.has("mhss_labels")){
                                    JSONArray labels = android.getJSONArray("mhss_labels");

                                    for(int j = 0; j < labels.length(); ++j) {
                                        JSONObject obj = labels.getJSONObject(j);

                                        int id = obj.getInt("id");
                                        String label = obj.getString("label");
                                        int priority = obj.getInt("priority");
                                        boolean showLmh = obj.getBoolean("show_low_medium_high");
                                        boolean reverse = obj.getBoolean("reverse");
                                        String customUiLink = obj.optString("custom_ui_link", null);

                                        LabelEntity entity = new LabelEntity(id, label, priority, showLmh, reverse, 0, customUiLink);
                                        labelEntities.add(entity);
                                    }

                                }
                            }
                        }

                        if(labelEntities.size() > 0){
                            assocObject.addCustomLabel(new CustomLabelEntity(
                                    adminId,
                                    code,
                                    null,
                                    null,
                                    creationTime));

                            assocObject.addLabelEntity(labelEntities);
                        }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //url need to be determined
        @Override
        protected Request buildRequest() {
            return ApiRequestConstructor.constructGetRequest(getHeaders(), new HashMap<>(), participateApiPath).getRequestObj();
        }

        @Nullable
        @Override
        protected List<AssociationResearchObj> buildResponse(@NonNull JSONObject jsonObject) {
            try {
                Log.d("BuildAssocResearchCheck", "Response " + jsonObject);

                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("result");
                List<AssociationResearchObj> associationResearchObjs = new ArrayList<>();
                JSONObject resultJsonObj;

                for (int i = 0; i < jsonArray.length(); i++) {
                    resultJsonObj = jsonArray.getJSONObject(i);

                    AssociationResearchObj associationResearchObj = new AssociationResearchObj(
                            getOrganizationFromJson(resultJsonObj),
                            getAdminFromJson(resultJsonObj),
                            getAssociationFromJson(resultJsonObj),
                            getResearchesFromJson(resultJsonObj)
                    );

                    getCustomLabels(resultJsonObj, associationResearchObj);

                    associationResearchObjs.add(associationResearchObj);
                }

                return associationResearchObjs;

            } catch (JSONException e) {
            }
            throw new InvalidParameterException("AssociationResearchDetails: Response does not parse correctly");
        }
    }


}
