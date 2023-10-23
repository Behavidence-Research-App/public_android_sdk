package com.behavidence.android.sdk_internal.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.behavidence.android.sdk_internal.Utils.AnonPasswordGenerator;
import com.behavidence.android.sdk_internal.Utils.LoadAPIKey;
import com.behavidence.android.sdk_internal.data.interfaces.AuthSigninService;
import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthResponse;
import com.behavidence.android.sdk_internal.data.model.Auth.AuthLogoutResponse;
import com.behavidence.android.sdk_internal.data.model.Auth.AuthResponseData;
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClientCallback;

import java.util.Calendar;

class ServiceParent implements AuthSigninService {

    private static final String FILE_CREDENTIALS = "behavidence_auth";
    private static final String ACCESS_TOKEN_TAG = "accesstoken";
    private static final String PASSWORD_TAG = "password";
    private static final String USER_ID = "userid";

    private static final String USER_HASH = "userHash";
    private static final String REFRESH_TOKEN_TAG = "refresh_token";
    private static final String REGISTERED_TAG = "registered";
    private static final String LOGIN_TYPE_TAG = "logintype";
    private static final String ACCESS_TOKEN_TTL = "accessttl";
    private static final String REFRESH_TOKEN_TTL = "refreshttl";

    private static final String BEHAVIDENCE_ERROR = "BEHAVIDENCE_ERROR";

    private AuthService_Impl authService;
    private final SharedPreferences sharedPreferences;

    protected String apiKey;
    protected static String token;

    public ServiceParent(Context context){
        sharedPreferences = getEncSharedPref(context);

        try {
            apiKey = LoadAPIKey.getKey(context);
            authService = new AuthService_Impl(apiKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean createAnonymousProfileSync(){
        String password = AnonPasswordGenerator.generatePassword(20);
        AnonymousAuthResponse response = authService.createAnonymousProfileSync(password);

        if (response == null) {
            Log.e(BEHAVIDENCE_ERROR, "Failed to sign in. Please check internet connection.");
            return false;
        }

        token = response.getData().getAccessToken();
        saveAuth(response.getData(), password);
        return true;
    }

   public void createAnonymousProfile(BehavidenceClientCallback<Boolean> callback){
        String password = AnonPasswordGenerator.generatePassword(20);
        authService.createAnonymousProfile(password, new BehavidenceResponseCallback<AnonymousAuthResponse>() {
            @Override
            public void onSuccess(AnonymousAuthResponse response) {

                if (response == null) {
                    Log.e(BEHAVIDENCE_ERROR, "Failed to sign in. Please check internet connection.");
                    callback.onSuccess(false);
                    return;
                }

                token = response.getData().getAccessToken();
                saveAuth(response.getData(), password);
                callback.onSuccess(true);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure("Failed to create Anonymous Profile");
            }
        });
    }

    @Override
    public Boolean authLogoutSync() {
        if(loadAuthTokenSync()){
            AuthLogoutResponse resp = authService.logoutAuthSync(token);
            return resp != null && resp.getData() != null;
        }

        return false;
    }

    @Override
    public void authLogout(BehavidenceClientCallback<Boolean> callback) {

        loadAuthToken(new BehavidenceRefreshCallback() {
            @Override
            public void executeCallback(String token) {
            }
        });

        loadAuthToken(token -> authService.logoutAuth(token, new BehavidenceResponseCallback<AuthLogoutResponse>() {
            @Override
            public void onSuccess(AuthLogoutResponse response) {
                callback.onSuccess(true);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure("Failed to logout");
            }
        }));

    }

    @Override
    public String getUserId() {
        return sharedPreferences.getString(USER_HASH, "");
    }


    boolean loadAuthTokenSync() {

        String userId = sharedPreferences.getString(USER_ID, "");
        String password = sharedPreferences.getString(PASSWORD_TAG, "");
        token = loadAuthLocal();

        if (token.isEmpty()) {
            if (userId.isEmpty() || password.isEmpty()) {
                createAnonymousProfileSync();
                return token != null && !token.isEmpty();
            }
            else {
                AnonymousAuthResponse refreshResponse = authService.refreshAnonymousProfileSync(userId, password);
                if (refreshResponse == null) {
                    Log.e(BEHAVIDENCE_ERROR, "Failed to refresh authentication token. Please check internet connection.");
                    return false;
                }

                token = refreshResponse.getData().getAccessToken();
                saveAuth(refreshResponse.getData(), password);
            }
        }

        return true;

    }

    void loadAuthToken(BehavidenceRefreshCallback callback) {

        String userId = sharedPreferences.getString(USER_ID, "");
        String password = sharedPreferences.getString(PASSWORD_TAG, "");
        token = loadAuthLocal();

        if (token.isEmpty()) {
            if (userId.isEmpty() || password.isEmpty()){
                createAnonymousProfile(new BehavidenceClientCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean response) {
                        if (token == null || token.isEmpty()) {
                            return;
                        }
                        callback.executeCallback(token);

                    }

                    @Override
                    public void onFailure(String message) {
                    }
                });
            }

            else {
                authService.refreshAnonymousProfile(userId, password, new BehavidenceResponseCallback<AnonymousAuthResponse>() {
                    @Override
                    public void onSuccess(AnonymousAuthResponse refreshResponse) {
                        if (refreshResponse == null) {
                            Log.e(BEHAVIDENCE_ERROR, "Failed to refresh authentication token. Please check internet connection.");
                            callback.executeCallback(null);
                            return;
                        }

                        token = refreshResponse.getData().getAccessToken();
                        saveAuth(refreshResponse.getData(), password);
                        callback.executeCallback(token);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(BEHAVIDENCE_ERROR, "Failed to refresh authentication token. Please check internet connection.");
                        callback.executeCallback(null);
                    }
                });
            }
        }else{
            callback.executeCallback(token);
        }
    }

    private String loadAuthLocal() {

        long tokenTTL = sharedPreferences.getLong(ACCESS_TOKEN_TTL, 0);
        String token = sharedPreferences.getString(ACCESS_TOKEN_TAG, "");
        long currentTime = Calendar.getInstance().getTimeInMillis();

        if (currentTime > tokenTTL) return "";
        return token;

    }

    private void saveAuth(AuthResponseData auth, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();

        editor.putString(USER_ID, auth.getUserid());
        editor.putString(PASSWORD_TAG, password);
        editor.putString(USER_HASH, auth.getUserHash());

        if (auth.getAccessToken() != null) {
            long tokenTTL = Calendar.getInstance().getTimeInMillis() + ((auth.getExpiry() - 120) * 1000);

            editor.putString(ACCESS_TOKEN_TAG, auth.getAccessToken());
            editor.putLong(ACCESS_TOKEN_TTL, tokenTTL);
        }

        editor.apply();
    }

    private SharedPreferences getEncSharedPref(@NonNull Context context) {
        try {
            return EncryptedSharedPreferences.create(context,
                    FILE_CREDENTIALS,
                    new MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                            .setUserAuthenticationRequired(false)
                            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                            .build(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (Exception e) {
            return context.getSharedPreferences(FILE_CREDENTIALS + "1", Context.MODE_PRIVATE);
        }
    }

}
