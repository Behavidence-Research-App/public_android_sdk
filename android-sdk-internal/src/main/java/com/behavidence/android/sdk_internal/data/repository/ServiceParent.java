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


    boolean loadAuthTokenSync() {

        String userId = sharedPreferences.getString(USER_ID, "");
        String password = sharedPreferences.getString(PASSWORD_TAG, "");
//        token = loadAuthLocal();
        token = "eyJraWQiOiJMYytJdWgrMXhVeTVyR25wdWlXYWwwQUtpSlFIVTdTcmF6V1ZWQ1QzM3lRPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI3ZWU1Yjc4NS1jZTgxLTQ0ZDItOWM5OS04MTEwNmRhM2JkNDEiLCJldmVudF9pZCI6IjljOTBkYTJjLThhZGQtNGEzNS04YWUwLTBiNjhiNjE0ODEzOSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE2NjY5NzY3NzgsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX1lwalNTeVNpYiIsImV4cCI6MTY2Njk4MDM3OCwiaWF0IjoxNjY2OTc2Nzc4LCJqdGkiOiIzMDExZjE3Ni0zNzc2LTRiMjEtYmI1Ny0zNGE5NmIzOWJjMDUiLCJjbGllbnRfaWQiOiI0aGRsZHNocHVvcDNqaThqaTdzZGI2MHFxYyIsInVzZXJuYW1lIjoidThkODI5MDAxOTc3NzJjMjIzZTFjYjk4MGE0OWYyYjNhYWJjOGVmM2IifQ.QPP6U4D7jZraJYI7CYJAaF2yYLlCpsHOtPoJIpclRDxZ0utJnHWIQsAWOu054XSCYQUcoPj7VoacrDTr5bmsr2am5By5x4rSuShGzf9csdTsKLhAOpai9FM83UfXhrsgbU_KOFcmjUXEcxFXfyvW-AaBW1SmkxXoI97PYLHK2PA3e2Xkxt7yeXTX7nioGi8nnaLbYPP-ftyypc9rFukVlmRY25NYuWKi9DgsZn3tc4mCMS19Y8Zje9gjHd1x-veRqkzsypj7dCV57F9UDQ2gg3nVbMvrllCwjYFSl_F3e3LgLQ45vFqoaw-W6zoivK1LkE8_vHijDbMLmpoywl9sag";

        if (token.isEmpty()) {
            if (userId.isEmpty() || password.isEmpty()) {
                createAnonymousProfileSync();
                return token != null && !token.isEmpty();
//                Log.e(BEHAVIDENCE_ERROR, "User not logged in. Please sign in again");
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
//        token = loadAuthLocal();
        token = "eyJraWQiOiJMYytJdWgrMXhVeTVyR25wdWlXYWwwQUtpSlFIVTdTcmF6V1ZWQ1QzM3lRPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI3ZWU1Yjc4NS1jZTgxLTQ0ZDItOWM5OS04MTEwNmRhM2JkNDEiLCJldmVudF9pZCI6IjljOTBkYTJjLThhZGQtNGEzNS04YWUwLTBiNjhiNjE0ODEzOSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE2NjY5NzY3NzgsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX1lwalNTeVNpYiIsImV4cCI6MTY2Njk4MDM3OCwiaWF0IjoxNjY2OTc2Nzc4LCJqdGkiOiIzMDExZjE3Ni0zNzc2LTRiMjEtYmI1Ny0zNGE5NmIzOWJjMDUiLCJjbGllbnRfaWQiOiI0aGRsZHNocHVvcDNqaThqaTdzZGI2MHFxYyIsInVzZXJuYW1lIjoidThkODI5MDAxOTc3NzJjMjIzZTFjYjk4MGE0OWYyYjNhYWJjOGVmM2IifQ.QPP6U4D7jZraJYI7CYJAaF2yYLlCpsHOtPoJIpclRDxZ0utJnHWIQsAWOu054XSCYQUcoPj7VoacrDTr5bmsr2am5By5x4rSuShGzf9csdTsKLhAOpai9FM83UfXhrsgbU_KOFcmjUXEcxFXfyvW-AaBW1SmkxXoI97PYLHK2PA3e2Xkxt7yeXTX7nioGi8nnaLbYPP-ftyypc9rFukVlmRY25NYuWKi9DgsZn3tc4mCMS19Y8Zje9gjHd1x-veRqkzsypj7dCV57F9UDQ2gg3nVbMvrllCwjYFSl_F3e3LgLQ45vFqoaw-W6zoivK1LkE8_vHijDbMLmpoywl9sag";


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
//                Log.e(BEHAVIDENCE_ERROR, "User not logged in. Please sign in again");
            else {
                authService.refreshAnonymousProfile(userId, password, new BehavidenceResponseCallback<AnonymousAuthResponse>() {
                    @Override
                    public void onSuccess(AnonymousAuthResponse refreshResponse) {
                        if (refreshResponse == null) {
                            Log.e(BEHAVIDENCE_ERROR, "Failed to refresh authentication token. Please check internet connection.");
                            return;
                        }

                        token = refreshResponse.getData().getAccessToken();
                        saveAuth(refreshResponse.getData(), password);
                        callback.executeCallback(token);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(BEHAVIDENCE_ERROR, "Failed to refresh authentication token. Please check internet connection.");
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

        Log.d("ServiceParentCheck", "tokenTTL " + tokenTTL + " token " + token);

        if (currentTime > tokenTTL) return "";
        return token;

    }


    private void saveAuth(AuthResponseData auth, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
//        editor.putInt(LOGIN_TYPE_TAG, AuthIOImpl.LOGIN_TYPE.ANONYMOUS.ordinal());
        editor.putString(USER_ID, auth.getUserid());
        editor.putString(PASSWORD_TAG, password);

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
