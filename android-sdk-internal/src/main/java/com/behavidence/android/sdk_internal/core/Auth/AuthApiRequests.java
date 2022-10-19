package com.behavidence.android.sdk_internal.core.Auth;
import static com.behavidence.android.sdk_internal.core.Auth.AuthClient.BASE_OAUTH2_URL;
import static com.behavidence.android.sdk_internal.core.Auth.AuthClient.CLIENT_ID;
import static com.behavidence.android.sdk_internal.core.Auth.AuthClient.REDIRECT_URI_TO_APP;
import static com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor.AUTH_API;
import static com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor.DATA_TAG;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;
import com.behavidence.android.sdk_internal.core.Exceptions.ParseException;
import com.behavidence.android.sdk_internal.core.Networks.ApiRequestTemplate;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import okhttp3.Request;

class AuthApiRequests {
    private final ApiKey apiKey;
    public AuthApiRequests(@NonNull ApiKey apiKey) {
        this.apiKey=apiKey;
    }
    private static final String AGE_TAG="age";
    private static final String PARTICIPATE_TAG="participate";
    private static final String CODE_TAG="code";
    private static final String DIAGNOSTICS="diagnostics";
    private static final String AUTH_TYPE="type";
    private static final String PASSWORD_TAG="password";
    private static final String EMAIL_TAG="email";
    private static final String USER_ID="userid";
    private static final String REFRESH_TOKEN_TAG="refreshtoken";
    private static final String REGISTERED_TAG="registered";
    private static final String LOGIN_TYPE="logintype";
    private static final String REFRESH_TOKEN_TTL_TAG="refreshtokenttl";
    private static final String EMAIL_PATH ="/email";
    private static final String ANONYMOUS_PATH="/anon";



    private  JSONObject getSignUpParams(String age,  String[] diagnostic, boolean participate)throws JSONException {
        return new JSONObject().put(DIAGNOSTICS,new JSONArray(diagnostic))
                .put(AGE_TAG,age)
                .put(PARTICIPATE_TAG,participate);
    }



    private boolean isNoneSelectedWithDiagnostics(List<AuthConstants.DIAGNOSTICS> diagnostics){
        if(diagnostics.size()>1){
            for(AuthConstants.DIAGNOSTICS d:diagnostics){
                if(d.equals(AuthConstants.DIAGNOSTICS.NONE))return true;
            }

        }
        return false;
    }

    private  void checkDiagnostics( List<AuthConstants.DIAGNOSTICS> diagnostics){
        if(diagnostics==null)
            return;
        if(isNoneSelectedWithDiagnostics(diagnostics))
            throw new InvalidArgumentException("'none' cannot be selected with other diagnostics");
    }


    private String[] getEnumListToArray(List<AuthConstants.DIAGNOSTICS> diagnosticsList){
        String[] diagnosticArray=new String[diagnosticsList.size()];
        for(int i=0;i<diagnosticsList.size();i++)
            diagnosticArray[i]=diagnosticsList.get(i).getDiagnostics();
        return diagnosticArray;
    }

    private  void checkPassword(@NonNull String password){
        if(password.length()<5||password.length()>16) throw new InvalidArgumentException("Password length is not valid");
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for(int i=0;i < password.length();i++) {
            ch = password.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag)
                return;
        }
        throw new InvalidArgumentException("Invalid Password. Password must contain upper & lower case letters and a digit");
    }

    /**
     * signIn Request
     * CredAuth will be the response returned to the user
     */
    class EmailSignInBuilder extends ApiRequestTemplate<EmailAuth> {
        private final String email;
        private final String password;

        public EmailSignInBuilder(@NonNull String email,@NonNull String password) {
            super(apiKey, null);
            this.email=email;
            checkPassword(password);
            this.password=password;
        }

        @Override
        public Request buildRequest() {
            try {
                ApiRequestConstructor apiRequestConstructor =  ApiRequestConstructor.constructPostRequestJSON(
                        new JSONObject().put(EMAIL_TAG,email)
                                .put(PASSWORD_TAG,password),getHeaders(),AUTH_API+EMAIL_PATH+"/signin");
               return apiRequestConstructor.getRequestObj();
            }catch (JSONException e){
                throw new ParseException(e.getMessage());
            }
        }

        @Nullable
        @Override
        public EmailAuth buildResponse(@NonNull JSONObject jsonObject) {
            try{
                JSONObject authJson=jsonObject.getJSONObject(DATA_TAG);
                return new EmailAuthImpl(authJson.getString("accesstoken")
                        ,authJson.getString("refreshtoken")
                        , Calendar.getInstance().getTimeInMillis()+(authJson.getLong("accesstokenttl")*1000)
                        ,Calendar.getInstance().getTimeInMillis()+(authJson.getLong("refreshtokenttl")*1000),
                        authJson.getBoolean("registered"));
            }catch (JSONException e){
                throw new ParseException(e.getMessage());
            }
        }
    }


    /**
     * Email SignUp Request
     */
    class EmailSignUpBuilder extends ApiRequestTemplate<Void> {

        private final String email;
        private final String password;

        public EmailSignUpBuilder(@NonNull String email, @NonNull String password) {
            super(apiKey,null);
            this.email = email;
            checkPassword(password);
            this.password = password;
        }


        @Override
        public Request buildRequest() {
            try {
                return ApiRequestConstructor.constructPostRequestJSON(
                        new JSONObject().put(EMAIL_TAG,email)
                                .put(PASSWORD_TAG,password),getHeaders(),AUTH_API+EMAIL_PATH+"/signup")
                 .getRequestObj();
            }catch (JSONException e){
                throw new ParseException(e.getMessage());
            }
        }

        @Nullable
        @Override
        public Void buildResponse(@NonNull JSONObject jsonObject) {
            return null;
        }

    }


    /**
     * Register request Credentials
     */
    class RegisterBuilder extends ApiRequestTemplate<Void> {
        private AuthConstants.AGE_RANGE ageRange;
        private boolean participationInResearch;
        private List<AuthConstants.DIAGNOSTICS> diagnostics;
        private String accessToken;


        public RegisterBuilder(){
            super(apiKey,null);
        }

        public RegisterBuilder(@NonNull AuthConstants.AGE_RANGE ageRange, boolean participationInResearch, @NonNull List<AuthConstants.DIAGNOSTICS> diagnostics, @NonNull String accessToken) {
            super(apiKey, null);
            this.ageRange = ageRange;
            this.participationInResearch = participationInResearch;
            this.diagnostics = diagnostics;
            this.accessToken = accessToken;
        }

        public RegisterBuilder setAgeRange(@NonNull AuthConstants.AGE_RANGE ageRange) {
            this.ageRange = ageRange;
            return this;
        }
        public RegisterBuilder setParticipationInResearch(boolean participationInResearch) {
            this.participationInResearch = participationInResearch;
            return this;
        }

        public RegisterBuilder setDiagnostics(@NonNull List<AuthConstants.DIAGNOSTICS> diagnostics) {
            this.diagnostics = diagnostics;
            return this;
        }

        public RegisterBuilder setAccessToken(@NonNull String accessToken) {
            this.accessToken = accessToken;
            return this;
        }


        @Override
        public Request buildRequest() {
            try {
                checkDiagnostics(diagnostics);
                Objects.requireNonNull(accessToken);
                Objects.requireNonNull(ageRange);
                JSONObject jsonObject=getSignUpParams(ageRange.getAgeRange(),getEnumListToArray(diagnostics),participationInResearch);
                jsonObject.put("token",accessToken);
             return ApiRequestConstructor.constructPostRequestJSON(jsonObject,getHeaders(),AUTH_API+"/register")
              .getRequestObj();
            }catch (JSONException e){throw new ParseException(e.getMessage());}
        }

        @Nullable
        @Override
        public Void buildResponse(@NonNull JSONObject jsonObject) {
            return null;
        }
    }


    /**
     * Registration request Anonymous sign up
     */
    class RegisterAnonBuilder extends ApiRequestTemplate<AnonymousAuth> {
        private AuthConstants.AGE_RANGE ageRange;
        private boolean participationInResearch;
        private List<AuthConstants.DIAGNOSTICS> diagnostics;
        private String password;
        public RegisterAnonBuilder(){
            super(apiKey,null);
        }

        public RegisterAnonBuilder setAgeRange(@Nullable AuthConstants.AGE_RANGE  ageRange) {
            this.ageRange = ageRange;
            return this;
        }
        public RegisterAnonBuilder setParticipationInResearch(boolean participationInResearch) {
            this.participationInResearch = participationInResearch;
            return this;
        }

        public RegisterAnonBuilder setDiagnostics(@Nullable List<AuthConstants.DIAGNOSTICS> diagnostics) {
            this.diagnostics = diagnostics;
            return this;
        }

        public RegisterAnonBuilder setPassword(@NonNull String password) {
            this.password = password;
            return this;
        }

        @Override
        public Request buildRequest() {
            try {
                JSONObject jsonObject=new JSONObject();
                checkDiagnostics(diagnostics);
                if(ageRange!=null&&diagnostics!=null)
                    jsonObject=getSignUpParams(ageRange.getAgeRange(),getEnumListToArray(diagnostics),participationInResearch);
                jsonObject.put(PASSWORD_TAG, password);
                return ApiRequestConstructor.constructPostRequestJSON(jsonObject,
                        getHeaders()
                        , AUTH_API+ANONYMOUS_PATH+"/register")
                        .getRequestObj();
            }catch (JSONException e){ throw new ParseException(e.getMessage());}
        }

        @Nullable
        @Override
        public AnonymousAuth buildResponse(@NonNull JSONObject jsonObject) {
            try{
                JSONObject anon=jsonObject.getJSONObject(DATA_TAG);
                return new AnonymousAuthImp(anon.getString("accesstoken")
                        ,anon.getString("userid"),password, Calendar.getInstance().getTimeInMillis()+((anon.getLong("expiry")-120)*1000));
            }catch (JSONException e){
                throw new InvalidArgumentException(e.getMessage());
            }
        }
    }


    class SocialLoginBuilder extends ApiRequestTemplate<EmailAuth> {

        private final String code;

        public SocialLoginBuilder(@NonNull String code) {
            super(apiKey,null);
            this.code = code;
        }





        @Override
        protected Request buildRequest() {
            Map<String,String> body=new HashMap<>();
            body.put(CODE_TAG, Objects.requireNonNull(code));
            body.put("grant_type", "authorization_code");
            body.put("client_id", CLIENT_ID);
            body.put("redirect_uri", REDIRECT_URI_TO_APP);
         return   ApiRequestConstructor.constructPostRequestForm(null,body,BASE_OAUTH2_URL+"token").getRequestObj();
        }

        @Nullable
        @Override
        protected EmailAuth buildResponse(@NonNull JSONObject jsonObject) {
            try{
                return new EmailAuthImpl(jsonObject.getString("access_token")
                        ,jsonObject.getString("refresh_token")
                        , Calendar.getInstance().getTimeInMillis()+(1000*60*55)
                        ,Calendar.getInstance().getTimeInMillis()+7344000000L, null);
            }catch (JSONException e){
                throw new ParseException(e.getMessage());
            }
        }

    }


    class UpdatePasswordBuilder extends ApiRequestTemplate<Void> {

        private  String newPassword;
        private  String confirmationCode;
        private  String email;

        public UpdatePasswordBuilder(@NonNull String newPassword, @NonNull String confirmationCode, @NonNull String email) {
            super(apiKey,null);
            this.newPassword = newPassword;
            this.confirmationCode = confirmationCode;
            this.email = email;
        }
        public UpdatePasswordBuilder() {
            super(apiKey,null);
        }
        @NonNull
        public UpdatePasswordBuilder setNewPassword(@NonNull String newPassword) {
            this.newPassword = newPassword;
            return this;
        }

        @NonNull
        public UpdatePasswordBuilder setConfirmationCode(@NonNull String confirmationCode) {
            this.confirmationCode = confirmationCode;
            return this;
        }

        @NonNull
        public UpdatePasswordBuilder setEmail(@NonNull String email) {
            this.email = email;
            return this;
        }




        @Override
        public Request buildRequest() {
            checkPassword(newPassword);
            JSONObject jsonObject;
            try{
                jsonObject=new JSONObject();
                jsonObject.put(CODE_TAG,Objects.requireNonNull(confirmationCode));
                jsonObject.put(EMAIL_TAG,Objects.requireNonNull(email));
                jsonObject.put(PASSWORD_TAG,Objects.requireNonNull(newPassword));
            }catch (JSONException e){throw new ParseException(e.getMessage());}
            return ApiRequestConstructor.constructPostRequestJSON(jsonObject,getHeaders(), AUTH_API+EMAIL_PATH+"/changepass")
                    .getRequestObj();
        }

        @Nullable
        @Override
        public Void buildResponse(@NonNull JSONObject jsonObject) {
            return null;
        }
    }

     class SendPasswordCodeBuilder extends ApiRequestTemplate<Void> {
        private final String email;
         public SendPasswordCodeBuilder(@NonNull String email) {
             super(apiKey,null);
             this.email = email;
         }


         @Override
         public Request buildRequest() {
             JSONObject jsonObject=new JSONObject();
             try{
                 jsonObject.put(EMAIL_TAG,Objects.requireNonNull(email));
             }catch (JSONException e){throw new ParseException(e.getMessage());}
             return ApiRequestConstructor.constructPostRequestJSON(jsonObject,getHeaders(), AUTH_API+EMAIL_PATH+"/forgetpass").getRequestObj();
         }

         @Nullable
         @Override
         public Void buildResponse(@NonNull JSONObject jsonObject) {
             return null;
         }
     }

     class EmailRefreshTokensBuilder extends ApiRequestTemplate<EmailAuth> {

        private final EmailAuth emailAuth;

         public EmailRefreshTokensBuilder(@NonNull EmailAuth emailAuth) {
             super(apiKey,null);
             this.emailAuth = emailAuth;
         }

         @Override
         public Request buildRequest() {
             try{
                 return  ApiRequestConstructor.constructPostRequestJSON(new JSONObject().put("token",Objects.requireNonNull(emailAuth.getRefreshToken())),getHeaders(),AUTH_API+"/refresh")
                         .getRequestObj();
             }catch (JSONException e){throw new ParseException(e.getMessage());}

         }

         @Nullable
         @Override
         public EmailAuth buildResponse(@NonNull JSONObject jsonObject) {
             try{
                 JSONObject authJson=jsonObject.getJSONObject(DATA_TAG);
                 return new EmailAuthImpl(authJson.getString("accesstoken")
                         ,emailAuth.getRefreshToken()
                         , Calendar.getInstance().getTimeInMillis()+(authJson.getLong("accesstokenttl")*1000)
                         ,emailAuth.getRefreshTokenTTL(),
                         emailAuth.isUserRegistered());
             }catch (JSONException e){
                 throw new ParseException(e.getMessage());
             }
         }
     }

     class AnonRefreshTokensBuilder extends ApiRequestTemplate<AnonymousAuth> {

        private final AnonymousAuth anonymousAuth;


         public AnonRefreshTokensBuilder(@NonNull AnonymousAuth anonymousAuth) {
             super(apiKey,null);
             this.anonymousAuth = anonymousAuth;
         }


         @Override
         public Request buildRequest() {
             try{
                 String url=AUTH_API+ANONYMOUS_PATH+"/refresh";
              return ApiRequestConstructor.constructPostRequestJSON(new JSONObject()
                                 .put(USER_ID,anonymousAuth.getId()).put(PASSWORD_TAG,anonymousAuth.getPassCode())
                         ,getHeaders()
                         ,url).getRequestObj();
             }catch (JSONException e){throw new ParseException(e.getMessage());}
         }

         @Nullable
         @Override
         public AnonymousAuth buildResponse(@NonNull JSONObject jsonObject) {
             try {
                 return new AnonymousAuthImp(jsonObject.getJSONObject(DATA_TAG).getString("accesstoken")
                         , anonymousAuth.getId(), anonymousAuth.getPassCode(), Calendar.getInstance().getTimeInMillis()+((jsonObject.getJSONObject(DATA_TAG).getLong("expiry")-120)*1000));
             }catch (JSONException e){throw new ParseException(e.getMessage());}
         }
     }

     class LogoutBuilder extends ApiRequestTemplate<Void> {

        private final EmailAuth emailAuth;
         public LogoutBuilder(@NonNull EmailAuth emailAuth) {
             super(apiKey,null);
             this.emailAuth = emailAuth;
         }

         @Override
         public Request buildRequest() {
             try{
                 return ApiRequestConstructor.constructPostRequestJSON(new JSONObject().put("token",emailAuth.getAccessToken()),getHeaders(), AUTH_API+"/logout").getRequestObj();
             }catch (JSONException e){throw new ParseException(e.getMessage());}
         }

         @Nullable
         @Override
         public Void buildResponse(@NonNull JSONObject jsonObject) {
             return null;
         }
     }


     //need to be revisted
     class IsRegisteredBuidler extends ApiRequestTemplate<Boolean> {

       private final EmailAuth emailAuth;

         public IsRegisteredBuidler(@NonNull EmailAuth emailAuth) {
             super(apiKey, null);
             this.emailAuth=emailAuth;
         }


         @Override
         protected Request buildRequest() {
             try{
               return   ApiRequestConstructor.constructPostRequestJSON(
                         new JSONObject().put("token",emailAuth.getAccessToken()),
                         getHeaders(),
                         AUTH_API+"/social/check"
                 ).getRequestObj();

             }catch (JSONException e){
                 throw new ParseException(e.getMessage());
             }
         }

         @Nullable
         @Override
         protected Boolean buildResponse(@NonNull JSONObject jsonObject) {
             try{
                 return jsonObject.getJSONObject(DATA_TAG).getBoolean("registered");
             }catch (JSONException e){
                 throw new ParseException(e.getMessage());
             }
         }

     }



}
