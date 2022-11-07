package com.behavidence.android.sdk_internal.domain.clients;

public interface BehavidenceResearchCallback<T> {

    enum CodeType{RESEARCH, ASSOCIATION, RESEARCH_ASSOCIATION};

    /**
     * This function is called when the Code Information call is successful
     * @param response The intended response
     * @param codeType The type of code. It can be RESEARCH, ASSOCIATION, or RESEARCH_ASSOCIATION. This type should be checked before calling certain function on CodeInfo object
     */
    void onSuccess(T response, CodeType codeType);

    /**
     * This function is called when the Code Information call is failed
     * @param message Failure message
     */
    void onFailure(String message);

}
