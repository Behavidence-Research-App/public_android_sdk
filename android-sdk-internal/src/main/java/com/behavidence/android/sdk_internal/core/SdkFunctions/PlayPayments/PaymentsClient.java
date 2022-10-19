package com.behavidence.android.sdk_internal.core.SdkFunctions.PlayPayments;

import android.content.Context;
import androidx.annotation.NonNull;
import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.Utils.Executor;

public interface PaymentsClient {

    @NonNull
    static PaymentsClient getInstance(@NonNull Context context){
        return PaymentClientImpl.getPaymentClientImpl(AuthClient.getInstance(context),ApiKey.getInstance(context));
    }

    @NonNull
    Executor<SubscriptionInfo> getSubscriptionInfo();
    @NonNull
    Executor<Void> confirmSubscriptionInfo(@NonNull String purchaseToken,@NonNull String subscriptionId);
    @NonNull
    Executor<SubscriptionInfo> cancelSubscription();


}

class PaymentClientImpl implements PaymentsClient{

     private static PaymentClientImpl paymentClient;
     private final AuthClient authClient;
     private ApiKey apiKey;

    static synchronized PaymentClientImpl getPaymentClientImpl(@NonNull AuthClient authClient,@NonNull ApiKey apiKey){
         if(paymentClient==null)
              paymentClient=new PaymentClientImpl(authClient,apiKey);
         else
             paymentClient.apiKey=apiKey;
         return paymentClient;
    }

    public PaymentClientImpl(@NonNull AuthClient authClient, @NonNull ApiKey apiKey) {
        this.authClient = authClient;
        this.apiKey = apiKey;
    }

    @NonNull
    @Override
    public Executor<SubscriptionInfo> getSubscriptionInfo() {
        return new Executor<>(new PaymentApiRequest(authClient,apiKey)
                .new GetSubInfoBuilder()
                .buildApiCaller());
    }

    @NonNull
    @Override
    public Executor<Void> confirmSubscriptionInfo(@NonNull String purchaseToken,@NonNull String subscriptionId) {
        return  new Executor<>(new PaymentApiRequest(authClient,apiKey)
                .new ConfirmSubBuilder(purchaseToken,subscriptionId)
                .buildApiCaller());
    }

    @NonNull
    @Override
    public Executor<SubscriptionInfo> cancelSubscription() {
        return new Executor<>(new PaymentApiRequest(authClient,apiKey)
                .new CancelSubBuilder()
                .buildApiCaller());
    }
}
