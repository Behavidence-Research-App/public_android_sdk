package com.behavidence.android.sdk_internal.core.Auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;

interface AuthIO {

    static AuthIO getInstance(@NonNull Context context){
        return new AuthIOImpl(context);
    }

     void saveAuth(@NonNull BehavidenceAuth auth);

    @Nullable
    BehavidenceAuth loadAuth();

    void invalidateAccessToken();

    void invalidateLocalTokens();


}

class AuthIOImpl implements AuthIO{

    enum LOGIN_TYPE {ANONYMOUS,EMAIL};

    //file name for saving credentials
    private static final String FILE_CREDENTIALS="behavidence_auth";
    private static final String ACCESS_TOKEN_TAG="accesstoken";
    private static final String PASSWORD_TAG="password";
    private static final String USER_ID="userid";
    private static final String REFRESH_TOKEN_TAG="refresh_token";
    private static final String REGISTERED_TAG="registered";
    private static final String LOGIN_TYPE_TAG="logintype";
    private static final String ACCESS_TOKEN_TTL="accessttl";
    private static final String REFRESH_TOKEN_TTL="refreshttl";

     private final Context context;

    public AuthIOImpl(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public void saveAuth(@NonNull BehavidenceAuth auth){
     if(auth instanceof EmailAuthImpl)
           saveEmailAuth((EmailAuthImpl) auth,context);
        else
         if(auth instanceof AnonymousAuthImp)
            saveAnonymousAuth((AnonymousAuthImp) auth,context);
        else
            throw new InvalidArgumentException("Auth is NULL or By implementing the interface by your own you have taken the responsibility to save tokens too.");
    }

    @Nullable
    @Override
    public BehavidenceAuth loadAuth() {
       return loadAuth(context);
    }

    @Override
    public void invalidateAccessToken() {
        invalidateAccToken(context);
    }

    @Override
    public void invalidateLocalTokens() {
        invalidateTokens(context);
    }

    private static synchronized void invalidateAccToken(@NonNull Context c){
        getEncSharedPref(c)
                .edit().remove(ACCESS_TOKEN_TAG).remove(ACCESS_TOKEN_TTL).apply();
    }
    private static synchronized void invalidateTokens(@NonNull Context c){
        getEncSharedPref(c)
                .edit().clear().apply();
    }

    private static SharedPreferences getEncSharedPref(@NonNull Context context){
        try {
            return EncryptedSharedPreferences.create(context,
                    FILE_CREDENTIALS,
                    new MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                            .setUserAuthenticationRequired(false)
                            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                            .build(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        }catch (Exception e){
            return context.getSharedPreferences(FILE_CREDENTIALS+"1",Context.MODE_PRIVATE);
        }

    }

    public static synchronized void saveEmailAuth(@NonNull EmailAuth  auth,@NonNull Context context){
        SharedPreferences.Editor editor=getEncSharedPref(context).edit();
        editor.clear();
        editor.putInt(LOGIN_TYPE_TAG,LOGIN_TYPE.EMAIL.ordinal());
            editor.putString(REFRESH_TOKEN_TAG, auth.getRefreshToken());
            editor.putLong(REFRESH_TOKEN_TTL,auth.getRefreshTokenTTL());
        if(auth.getAccessToken()!=null){
            editor.putString(ACCESS_TOKEN_TAG,auth.getAccessToken());
            editor.putLong(ACCESS_TOKEN_TTL,auth.getAccessTokenTTL());
        }
        editor.putInt(REGISTERED_TAG,constructIsRegisteredVal(auth.isUserRegistered()));
        editor.apply();
    }
    private static int constructIsRegisteredVal(@Nullable Boolean b){
        if(b==null)return -1;
        if(b) return 1;
        return 0;
    }
    private static Boolean getIsRegisteredVal(int val){
        if(val==-1)return null;
        return val==1;
    }

    public static synchronized void saveAnonymousAuth(@NonNull AnonymousAuth auth,@NonNull Context context){
        SharedPreferences.Editor editor=getEncSharedPref(context).edit();
        editor.clear();
        editor.putInt(LOGIN_TYPE_TAG,LOGIN_TYPE.ANONYMOUS.ordinal());
        editor.putString(USER_ID, auth.getId());
        editor.putString(PASSWORD_TAG,auth.getPassCode());
        if(auth.getAccessToken()!=null){
            editor.putString(ACCESS_TOKEN_TAG,auth.getAccessToken());
            editor.putLong(ACCESS_TOKEN_TTL,auth.getAccessTokenTTL());
        }
       editor.apply();
    }


    @Nullable
    public BehavidenceAuth loadAuth(@NonNull Context context){
           try {
               SharedPreferences sharedPreferences = getEncSharedPref(context);
               int type = sharedPreferences.getInt(LOGIN_TYPE_TAG, -1);
               if (type == LOGIN_TYPE.ANONYMOUS.ordinal())
                   return validate(loadAnonymousAuth(sharedPreferences));
               else if (type == LOGIN_TYPE.EMAIL.ordinal())
                   return validate(loadEmailAuth(sharedPreferences));
           }catch (Exception e){
               Log.e("tali",e.getMessage());

           }
        return null;
    }

    @NonNull
    public static  EmailAuthImpl loadEmailAuth(@NonNull SharedPreferences sharedPreferences){
      return new EmailAuthImpl(sharedPreferences.getString(ACCESS_TOKEN_TAG,null)
      ,sharedPreferences.getString(REFRESH_TOKEN_TAG,null),
              sharedPreferences.getLong(ACCESS_TOKEN_TTL,0)
              ,sharedPreferences.getLong(REFRESH_TOKEN_TTL,0),getIsRegisteredVal(sharedPreferences.getInt(REGISTERED_TAG,-1)));
    }

    @NonNull
    public AnonymousAuthImp loadAnonymousAuth(@NonNull SharedPreferences sharedPreferences){
        return new AnonymousAuthImp(sharedPreferences.getString(ACCESS_TOKEN_TAG,null),
                sharedPreferences.getString(USER_ID,null),
                sharedPreferences.getString(PASSWORD_TAG,null)
                ,sharedPreferences.getLong(ACCESS_TOKEN_TTL,0));
    }

    public EmailAuthImpl validate(@NonNull EmailAuthImpl auth){
        String exceptionStr="Invalid argument type: ";
        if(auth.getRefreshToken()==null)
            throw new InvalidArgumentException(exceptionStr+"refresh token is null");
        return auth;
    }
    public AnonymousAuthImp validate(@NonNull AnonymousAuthImp auth){

        String exceptionStr="Invalid argument type: ";
        if(auth.getId()==null)
            throw new InvalidArgumentException(exceptionStr+"id is null");
        else if(auth.getPassCode()==null)
            throw new InvalidArgumentException(exceptionStr+"passcode is null");
        return auth;
    }



}



