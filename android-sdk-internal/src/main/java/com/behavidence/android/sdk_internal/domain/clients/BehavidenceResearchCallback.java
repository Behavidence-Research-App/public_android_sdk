package com.behavidence.android.sdk_internal.domain.clients;

public interface BehavidenceResearchCallback<T> {

    enum CodeType{RESEARCH, ASSOCIATION, RESEARCH_ASSOCIATION};

    void onSuccess(T response, CodeType codeType);

    void onFailure(String message);

}
