package com.behavidence.android.sdk_internal.data.interfaces;

import com.behavidence.android.sdk_internal.Utils.AnonPasswordGenerator;
import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthBody;
import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthRefreshBody;
import com.behavidence.android.sdk_internal.data.model.Auth.AnonymousAuthResponse;
import com.behavidence.android.sdk_internal.data.repository.BehavidenceResponseCallback;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface AuthService {

    AnonymousAuthResponse createAnonymousProfileSync(String password);

    void createAnonymousProfile(String password, BehavidenceResponseCallback<AnonymousAuthResponse> callback);

    AnonymousAuthResponse refreshAnonymousProfileSync(String userId, String password);

    void refreshAnonymousProfile(String userId, String password, BehavidenceResponseCallback<AnonymousAuthResponse> callback);

}
