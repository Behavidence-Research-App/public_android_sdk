package com.behavidence.android.sdk_internal.core.Auth;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.behavidence.android.sdk_internal.core.Exceptions.BehavidenceClientException;
import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;
import com.behavidence.android.sdk_internal.core.Networks.ApiCaller;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.RoomDb.BehavidenceSDKInternalDb;
import com.behavidence.android.sdk_internal.core.Utils.AppUtils;
import com.behavidence.android.sdk_internal.core.Utils.Executor;
import java.util.List;




public interface AuthClient {


     static final String BASE_OAUTH2_URL="https://behavidence.auth.us-east-1.amazoncognito.com/oauth2/";
    //redirect uri to redirect reqquest after successful login with federated identity
     static final String REDIRECT_URI_TO_APP="myapp://app.behavidence.auth";
    //client id
     static final String CLIENT_ID="4hdldshpuop3ji8ji7sdb60qqc";

    /**
     *
     * @param context Context
     * @return AuthClient
     */
     static  AuthClient getInstance(@NonNull Context context){
         return AuthClientImpl.getAuthClientImpl(context);
    }

    static void invalidateAuthClient(){
         AuthClientImpl.invalidate();
    }



    @NonNull
     Executor<BehavidenceAuth> getAuth();





    /**
     *
     * @param email
     * @param password
     * InvalidArgumentException
     * @return ApiClient
     */
    @NonNull
    public Executor<Void> emailSignUp(@NonNull String email, @NonNull String password);

    /**
     *
     * @param email
     * @param password
     * InvalidArgumentException
     * @return ApiClient
     */
    @NonNull
    public Executor<Void> emailSignIn(@NonNull String email, @NonNull String password);


    /**
     * Anonymous SignUp with parameters
     * See anonymousSignUp for more details
     * @param ageRange for age range. See AuthConstants.AGE_RANGE"
     * @param diagnostics for list of diagnostics user have, See AuthConstants.DIAGNOSTICS.
     * @param allowParticipation allow to participate in future researches to get notifications
     * @return AnonymousAuth
     */
    @NonNull
     Executor<Void> anonymousSignUp(@NonNull AuthConstants.AGE_RANGE ageRange, @NonNull List<AuthConstants.DIAGNOSTICS> diagnostics, boolean allowParticipation);


    /**
     * creates userid and passcode automatically and register it with Behavidence Auth Server
     * autoInitUpload(@NonNull Context context, @NonNull AnonymousAuth auth) saved this auth in local storage and manages auto upload of data for you
     * there is no recovery mechanism for userid or passcode so you should keep them safely with you in order to retain your identification
     * @return AnonymousAuth
     */
    @NonNull
     Executor<Void> anonymousSignUp();



    /**
     * invalidate the access token from the local storage
     */
    void inValidateLocalAccessToken();

    /**
     * Invalidate the Auth from the local storage
     * there is no recovery mechanism for userid or passcode so you should keep them safely with you in order to retain your identification
     * Otherwise you will loss your identity in the server
     *
     */
    void invalidateLocalAuth();

    /**
     *
     *
     */
    @NonNull
    Executor<Void> register(@NonNull List<AuthConstants.DIAGNOSTICS> diagnostics, @NonNull AuthConstants.AGE_RANGE ageRange);

    /**
     *
     * @param email
     * @return
     */
    @NonNull
    Executor<Void> sendForgetPasswordCode(@NonNull String email);

    /**
     *
     * @param email
     * @param newPassword
     * @param code
     * @return
     */
    @NonNull
    Executor<Void> updatePassword(@NonNull String email,@NonNull String newPassword,@NonNull String code);

    /**
     *
     * @return
     */
    @NonNull
    Executor<Void> logout();

    /**
     *
     * @param code
     * @return
     */
    @NonNull
    Executor<Void> socialSignIn(@NonNull String code);


    @Nullable
    Boolean checkIfUserRegisteredLocal();

    @NonNull
    Executor<Boolean> checkIfUserRegisteredOnline();

    boolean isAuthExpiredLocal();

    @NonNull
    String getOAuth2Url(@NonNull final String identityProvider);




}




class AuthClientImpl implements AuthClient{

    //memory leak, developer need to care about the the leak
    private static AuthClientImpl authClient;
    private final AuthApiRequests authApiRequests;
    private final AuthIO authIO;
    private final BehavidenceSDKInternalDb behavidenceSDKInternalDb;

    static synchronized void invalidate(){
        authClient=null;
    }

    static synchronized AuthClientImpl getAuthClientImpl(@NonNull Context context){
        if(authClient==null)
             authClient=new AuthClientImpl(context);
            return authClient;
    }

     private AuthClientImpl(@NonNull Context context) {
        authIO=AuthIO.getInstance(context.getApplicationContext());
        authApiRequests =new AuthApiRequests(ApiKey.getInstance(context.getApplicationContext()));
         this.behavidenceSDKInternalDb=  BehavidenceSDKInternalDb.getInstance(context);
    }


    public void saveAuthToLocalStorage(@NonNull BehavidenceAuth auth) {

         authIO.saveAuth(auth);
    }

    @Nullable
    public BehavidenceAuth getLocalAuth() {
        try{
            return authIO.loadAuth();
        }catch (ClassCastException e){
            return null;
        }
    }

    @NonNull
    @Override
    public Executor<Void> emailSignUp(@NonNull String email, @NonNull String password) {
        return new Executor<>(authApiRequests.new EmailSignUpBuilder(email, password).buildApiCaller());
    }

    @NonNull
    @Override
    public Executor<Void> emailSignIn(@NonNull String email, @NonNull String password) {
        return new Executor<>(() -> {
         EmailAuth auth= authApiRequests.new EmailSignInBuilder(email,password).buildApiCaller().callApiGetResponse();
         saveAuthToLocalStorage(auth);
         return null;
        });
    }

    @NonNull
    @Override
    public Executor<BehavidenceAuth> getAuth() {
         return new Executor<>(() -> getBehavidenceAuth(this, authApiRequests));
    }

    private static synchronized BehavidenceAuth getBehavidenceAuth(@NonNull AuthClientImpl authClient,@NonNull AuthApiRequests authApiRequests){
       BehavidenceAuth behavidenceAuth = authClient.authIO.loadAuth();
        if (behavidenceAuth == null)
            throw new BehavidenceClientException("Credentials has been expired.Please sign in again", "BehavidenceAuth null from local storage");
        if (behavidenceAuth.getToken() == null) {
            if (behavidenceAuth instanceof EmailAuth) {
                ApiCaller<EmailAuth>  apiCaller= authApiRequests.new EmailRefreshTokensBuilder((EmailAuth) behavidenceAuth)
                        .buildApiCaller();
                behavidenceAuth = apiCaller.callApiGetResponse();
            } else if (behavidenceAuth instanceof AnonymousAuth) {
                ApiCaller<AnonymousAuth> authApiCallBuilder = authApiRequests.new AnonRefreshTokensBuilder((AnonymousAuth) behavidenceAuth)
                        .buildApiCaller();
                behavidenceAuth = authApiCallBuilder.callApiGetResponse();
            } else
                throw new BehavidenceClientException("Auth is not valid", "BehavidecneAuth is not inherited from SDK auths");
        }
        if (behavidenceAuth != null && behavidenceAuth.getToken() != null) {
           authClient.saveAuthToLocalStorage(behavidenceAuth);
            return behavidenceAuth;
        } else
            throw new BehavidenceClientException("Credentials Expired.Please signIn Again", "");
    }

    @NonNull
    @Override
    public Executor<Void> anonymousSignUp (@NonNull AuthConstants.AGE_RANGE ageRange, @NonNull List<AuthConstants.DIAGNOSTICS> diagnostics, boolean allowParticipation) {
        String password= AppUtils.generatePassword(20);
        return new Executor<>(() -> {
            AnonymousAuth auth= authApiRequests.new RegisterAnonBuilder().setPassword(password)
                    .setAgeRange(ageRange)
                    .setDiagnostics(diagnostics)
                    .setParticipationInResearch(allowParticipation)
                    .buildApiCaller().callApiGetResponse();
            saveAuthToLocalStorage(auth);
            return null;
        });
    }

    @NonNull
    @Override
    public Executor<Void> anonymousSignUp() {
        String password=AppUtils.generatePassword(20);
        return new Executor<>(() -> {
            AnonymousAuth auth= authApiRequests.new RegisterAnonBuilder().setPassword(password)
                    .buildApiCaller().callApiGetResponse();
            saveAuthToLocalStorage(auth);
            return null;
        });
    }


    @Override
    public void inValidateLocalAccessToken() {
        authIO.invalidateAccessToken();
    }

    @Override
    public void invalidateLocalAuth() {
        authIO.invalidateLocalTokens();
    }


    @NonNull
    @Override
    public Executor<Void> register(@NonNull List<AuthConstants.DIAGNOSTICS> diagnostics, @NonNull AuthConstants.AGE_RANGE ageRange) {
         return new Executor<>(() ->{
             try {
                 EmailAuth emailAuth = (EmailAuth) getAuth().execute();
                 authApiRequests.new RegisterBuilder().setAccessToken(emailAuth.getAccessToken())
                         .setDiagnostics(diagnostics).setAgeRange(ageRange).buildApiCaller().callApiGetResponse();
                 emailAuth=new EmailAuthImpl(emailAuth.getAccessToken(),emailAuth.getRefreshToken(),emailAuth.getAccessTokenTTL(),emailAuth.getRefreshTokenTTL(),true);
                 saveAuthToLocalStorage(emailAuth);
                 return null;
             }catch (ClassCastException e){
                 throw new InvalidArgumentException("You are already registered anonymously, Please sign in to email or social account for this api call",e.getMessage());
             }
         });
    }

    @NonNull
    @Override
    public Executor<Void> sendForgetPasswordCode(@NonNull String email) {
        return new Executor<Void>(authApiRequests.new SendPasswordCodeBuilder(email).buildApiCaller());
    }

    @NonNull
    @Override
    public Executor<Void> updatePassword(@NonNull String email, @NonNull String newPassword, @NonNull String code) {
        return new Executor<>(authApiRequests.new UpdatePasswordBuilder().setEmail(email)
        .setNewPassword(newPassword)
                .setConfirmationCode(code).buildApiCaller());
    }

    @NonNull
    @Override
    public Executor<Void> logout() {
        return new Executor<>(() -> {
            try {
                authApiRequests.new LogoutBuilder((EmailAuth) getAuth().execute())
                        .buildApiCaller().callApiGetResponse();
                invalidateLocalAuth();
             behavidenceSDKInternalDb.journalsDao()
                        .deleteAllJournals();
             behavidenceSDKInternalDb.mhssroomDao().deleteAllMhss();
             behavidenceSDKInternalDb.associationRoomDao().deleteAdmins();
             behavidenceSDKInternalDb.associationRoomDao().deleteAssociations();
             behavidenceSDKInternalDb.associationRoomDao().deleteOrganizations();
             behavidenceSDKInternalDb.associationRoomDao().deleteResearches();
                return null;
            }catch (ClassCastException e){
                throw new InvalidArgumentException("Current auth type is anonymous and cannot be logout",e.getMessage());
            }
        });
    }

    @NonNull
    @Override
    public Executor<Void> socialSignIn(@NonNull String code) {
        return new Executor<>(() -> {
           EmailAuth auth= authApiRequests.new SocialLoginBuilder(code).buildApiCaller().callApiGetResponse();
           saveAuthToLocalStorage(auth);
            return null;
        });
    }

    @Nullable
    @Override
    public Boolean checkIfUserRegisteredLocal() {
        BehavidenceAuth auth=getLocalAuth();
        if(auth!=null){
            if(auth instanceof AnonymousAuth) return  true;
            else if(auth instanceof EmailAuth)
                return ((EmailAuth) auth).isUserRegistered();
        }
        throw new BehavidenceClientException("User not signedUp","There is no credentials in the local storage");
    }

    @NonNull
    @Override
    public Executor<Boolean> checkIfUserRegisteredOnline() {
        return new Executor<>(() -> {
         try {
             Boolean isUserRegister = checkIfUserRegisteredLocal();
             if (isUserRegister != null && isUserRegister) return true;
             EmailAuth emailAuth= (EmailAuth) getAuth().execute();
             isUserRegister= authApiRequests.new IsRegisteredBuidler(emailAuth)
                     .buildApiCaller().callApiGetResponse();
             emailAuth=new EmailAuthImpl(emailAuth.getAccessToken(),emailAuth.getRefreshToken(),emailAuth.getAccessTokenTTL(),emailAuth.getRefreshTokenTTL(),isUserRegister);
             saveAuthToLocalStorage(emailAuth);
             return isUserRegister;
         }catch (ClassCastException e){
            throw new InvalidArgumentException("Current auth type is null",e.getMessage());
        }
        });
    }

    @Override
    public boolean isAuthExpiredLocal() {
        BehavidenceAuth auth=getLocalAuth();
        if(auth!=null){
            if(auth instanceof AnonymousAuth)return false;
            else if(auth instanceof EmailAuth)return ((EmailAuth) auth).getRefreshToken()==null;
        }
        return  true;
    }

    @NonNull
    @Override
    public String getOAuth2Url(@NonNull String identityProvider) {
         return BASE_OAUTH2_URL + "authorize?identity_provider=" + identityProvider + "&redirect_uri=" + REDIRECT_URI_TO_APP + "&response_type=CODE&client_id=" + CLIENT_ID + "&scope=email+profile+openid+aws.cognito.signin.user.admin";
    }


}
