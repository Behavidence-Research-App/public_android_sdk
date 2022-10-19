package com.behavidence.android.sdk_internal.core.SdkFunctions.PlayPayments;

import androidx.annotation.NonNull;

public interface SubscriptionInfo {
     enum SUBSCRIPTION_STATUS {
         SUBSCRIBED,
         GRACE,
         UN_SUBSCRIBED,
     }
    @NonNull
     SUBSCRIPTION_STATUS getStatus();

     long getValidTillMilli();

     long getExpiryTimeMilli();

    @NonNull
     String getPurchaseToken();

    @NonNull
     String getSubscriptionId();


}
class SubscriptionInfoImpl implements SubscriptionInfo {
    private final SUBSCRIPTION_STATUS status;
    private final long validTillMilli;
    private final long expiryTimeMilli;
    private final String purchaseToken;
    private final String subscriptionId;

    public SubscriptionInfoImpl(SUBSCRIPTION_STATUS status, long validTillMilli, long expiryTimeMilli, String purchaseToken, String subscriptionId) {
        this.status = status;
        this.validTillMilli = validTillMilli;
        this.expiryTimeMilli = expiryTimeMilli;
        this.purchaseToken = purchaseToken;
        this.subscriptionId = subscriptionId;
    }

    @NonNull
    public SUBSCRIPTION_STATUS getStatus() {
        return status;
    }

    public long getValidTillMilli() {
        return validTillMilli;
    }

    public long getExpiryTimeMilli() {
        return expiryTimeMilli;
    }

    @NonNull
    public String getPurchaseToken() {
        return purchaseToken;
    }

    @NonNull
    public String getSubscriptionId() {
        return subscriptionId;
    }
}
