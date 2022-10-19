package com.behavidence.android.sdk_internal.core.SdkFunctions.PlayPayments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.behavidence.android.sdk_internal.core.Auth.AuthClient;
import com.behavidence.android.sdk_internal.core.Exceptions.BehavidenceServiceException;
import com.behavidence.android.sdk_internal.core.Exceptions.InvalidArgumentException;
import com.behavidence.android.sdk_internal.core.Exceptions.PaymentException;
import com.behavidence.android.sdk_internal.core.Networks.ApiRequestTemplate;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiKey;
import com.behavidence.android.sdk_internal.core.Networks.Requests.ApiRequestConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import okhttp3.Request;

class PaymentApiRequest {

    private final AuthClient authClient;
    private static final String API_PATH=ApiRequestConstructor.USER_API+"/payments/subscription/android";
    private final ApiKey apiKey;
    public PaymentApiRequest(@NonNull AuthClient authClient, @NonNull ApiKey apiKey) {
        this.authClient = authClient;
        this.apiKey = apiKey;
    }

    private void handlePaymentException(RuntimeException e){
        if(e  instanceof BehavidenceServiceException && (((BehavidenceServiceException) e).getStatusCode()==402))
            throw new PaymentException("Invalid purchase token or purchase token is expired","Please check the purchase token");
    }

    private SubscriptionInfo.SUBSCRIPTION_STATUS getSubStatus(int status){
        switch (status){
            case 0:
                return SubscriptionInfo.SUBSCRIPTION_STATUS.SUBSCRIBED;
            case 1:
                return SubscriptionInfo.SUBSCRIPTION_STATUS.GRACE;
            case 2:
                return SubscriptionInfo.SUBSCRIPTION_STATUS.UN_SUBSCRIBED;
        }

        throw new PaymentException("Invalid status","Invalid status from the server");
    }


      class GetSubInfoBuilder extends ApiRequestTemplate<SubscriptionInfo> {
          public GetSubInfoBuilder() {
              super(apiKey, authClient);
          }

          @Override
          protected Request buildRequest() {
              return ApiRequestConstructor.constructGetRequest(getHeaders(),
                      new HashMap<>(), API_PATH).getRequestObj();
          }



          @Nullable
          @Override
          protected SubscriptionInfo buildResponse(@NonNull JSONObject jsonObject) {
             try{
                  JSONObject subInfo=jsonObject.getJSONObject("data");
                  return new SubscriptionInfoImpl(getSubStatus(subInfo.getInt("status"))
                     ,subInfo.getLong("validitytime_milli"),subInfo.getLong("expirytime_milli"),
                          subInfo.getString("purchase_token")
                          ,subInfo.getString("subscription_id"));
             }catch (JSONException jsonException){
                 throw new InvalidArgumentException(jsonException.getMessage());
             }
          }
          @Override
          protected void handleException(RuntimeException e) {
              handlePaymentException(e);
              super.handleException(e);
          }
      }


      class ConfirmSubBuilder extends ApiRequestTemplate<Void> {

           private final String purchaseToken;
           private final String subscriptionId;

          public ConfirmSubBuilder(@NonNull String purchaseToken,@NonNull String subscriptionId) {
              super(apiKey, authClient);
              this.purchaseToken = purchaseToken;
              this.subscriptionId = subscriptionId;
          }

          @Override
          protected Request buildRequest() {
              try{
                  JSONObject jsonObject=new JSONObject();
                  jsonObject.put("purchasetoken",purchaseToken);
                  jsonObject.put("subid",subscriptionId);
                  return ApiRequestConstructor.constructPostRequestJSON(
                          jsonObject,getHeaders(), API_PATH).getRequestObj();

              }catch (JSONException jsonException){
                  throw new InvalidArgumentException(jsonException.getMessage());
              }

          }
          @Nullable
          @Override
          protected Void buildResponse(@NonNull JSONObject jsonObject) {
            return null;
          }

          @Override
          protected void handleException(RuntimeException e) {
              handlePaymentException(e);
              super.handleException(e);
          }
      }

      class CancelSubBuilder extends ApiRequestTemplate<SubscriptionInfo> {
          public CancelSubBuilder() {
              super(apiKey, authClient);
          }

          @Override
          protected Request buildRequest() {
              return ApiRequestConstructor.constructDeleteRequest(getHeaders(),
                      new HashMap<>(), API_PATH).getRequestObj();
          }

          //need to make tags static
          @Nullable
          @Override
          protected SubscriptionInfo buildResponse(@NonNull JSONObject jsonObject) {
              try{
                  JSONObject subInfo=jsonObject.getJSONObject("data");
                  return new SubscriptionInfoImpl(getSubStatus(subInfo.getInt("status"))
                          ,subInfo.getLong("validitytime_milli"),subInfo.getLong("expirytime_milli"),
                          subInfo.getString("purchase_token")
                          ,subInfo.getString("subscription_id"));
              }catch (JSONException jsonException){
                  throw new InvalidArgumentException(jsonException.getMessage());
              }
          }

          @Override
          protected void handleException(RuntimeException e) {
              handlePaymentException(e);
              super.handleException(e);
          }
      }





}
