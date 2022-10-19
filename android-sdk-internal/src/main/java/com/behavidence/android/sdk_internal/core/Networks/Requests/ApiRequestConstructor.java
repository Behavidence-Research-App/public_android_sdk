package com.behavidence.android.sdk_internal.core.Networks.Requests;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.json.JSONObject;
import java.security.InvalidParameterException;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;


//need to be refactor
//static function, no need to wrap this function now
public interface ApiRequestConstructor {
      String BASIC_API=" https://g0u4ezlaa3.execute-api.us-east-1.amazonaws.com/prod";
     String AUTH_API=BASIC_API+"/auth";
     String USER_API=BASIC_API+"/data";
    MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    String DATA_TAG="data";


     static ApiRequestConstructor constructGetRequest(@NonNull Map<String,String> headers, @NonNull Map<String, String[]> queryParams, @NonNull String urlPath) {
        return new GETHttpApiRequestWrapper(headers,queryParams,urlPath);
    }

    static ApiRequestConstructor constructDeleteRequest(@NonNull Map<String,String> headers, @NonNull Map<String, String[]> queryParams, @NonNull String urlPath) {
        return new DeleteHttpApiRequestWrapper(headers,queryParams,urlPath);
    }



     static ApiRequestConstructor constructPostRequestForm(Map<String,String> headers, Map<String,String> formData, String urlPath){
      return new PostHttpApiRequestFrom(headers,formData,urlPath);
    }


     static ApiRequestConstructor constructPostRequestJSON(@NonNull JSONObject jsonObject, @Nullable Map<String,String> headers, @NonNull String urlPath) {
        return new PostHttpApiRequestWrapperJSON(jsonObject,headers,urlPath);
    }

    static ApiRequestConstructor constructPutRequestJSON(@NonNull JSONObject jsonObject, @Nullable Map<String,String> headers, @NonNull String urlPath) {
        return new PutHttpApiRequestWrapperJSON(jsonObject,headers,urlPath);
    }


    //    public static final String BASE_OAUTH2_URL="https://behavidence.auth.us-east-1.amazoncognito.com/oauth2/";
//    //redirect uri to redirect reqquest after successful login with federated identity
//    public static final String REDIRECT_URI_TO_APP="myapp://app.behavidence.auth";
//    //client id
//    public static final String CLIENT_ID="4hdldshpuop3ji8ji7sdb60qqc";
    Request getRequestObj();

}









abstract class HttpApiRequestWrapper implements ApiRequestConstructor {
    private final Map<String,String> headers;
    protected Request.Builder builder;
    protected final String urlPath;
    HttpApiRequestWrapper(Map<String,String> headers, @NonNull String urlPath){
        this.headers=headers;
        this.urlPath=urlPath;
        builder=new Request.Builder();
    }
    protected void addHeaders(){
        if(headers==null||headers.isEmpty())return;
        for (Map.Entry<String,String> entry : headers.entrySet())
            builder.addHeader(entry.getKey(),entry.getValue());
    }

}



      class PostHttpApiRequestFrom extends HttpApiRequestWrapper {

        private final Map<String,String> formData;

        PostHttpApiRequestFrom(@Nullable Map<String, String> headers, @NonNull Map<String,String> formData, @NonNull String urlPath) {
            super(headers, urlPath);
            this.formData=formData;
        }
        private void constructFormBody(){
            FormBody.Builder b = new FormBody.Builder();
            for (Map.Entry<String,String> entry : formData.entrySet())
                b.add(entry.getKey(),entry.getValue());
            builder.post(b.build());
        }

        @Override
        public Request getRequestObj(){
            addHeaders();
            constructFormBody();
            return builder.url(urlPath)
                    .build();
        }
    }

  class PostHttpApiRequestWrapperJSON extends HttpApiRequestWrapper {
    private final JSONObject jsonObject;
     PostHttpApiRequestWrapperJSON(@NonNull JSONObject jsonObject, @Nullable Map<String,String> headers, @NonNull String urlPath) {
        super(headers,urlPath);
        this.jsonObject = jsonObject;
    }
    @Override
    public Request getRequestObj() throws InvalidParameterException {
        addHeaders();
        return builder.url(urlPath)
                .post( RequestBody.create(jsonObject.toString(), JSON))
                .build();
    }
}

class PutHttpApiRequestWrapperJSON extends HttpApiRequestWrapper {
    private final JSONObject jsonObject;
    PutHttpApiRequestWrapperJSON(@NonNull JSONObject jsonObject, @Nullable Map<String,String> headers, @NonNull String urlPath) {
        super(headers,urlPath);
        this.jsonObject = jsonObject;
    }
    @Override
    public Request getRequestObj() throws InvalidParameterException {
        addHeaders();
        return builder.url(urlPath)
                .put( RequestBody.create(jsonObject.toString(), JSON))
                .build();
    }
}





class GETHttpApiRequestWrapper extends HttpApiRequestWrapper {
    private  final Map<String,String[]> queryParams;


    GETHttpApiRequestWrapper(@NonNull Map<String,String > headers, @NonNull Map<String, String[]> queryParams, @NonNull String urlPath) {
        super(headers,urlPath);
        this.queryParams = queryParams;

    }

    private HttpUrl integrateQueryParams(){
        HttpUrl.Builder builder=HttpUrl.parse(urlPath).newBuilder();;
        for(Map.Entry<String,String[]> entry : queryParams.entrySet()) {
            for(String val:entry.getValue())
                builder.addQueryParameter(entry.getKey(), val);
        }
        return builder.build();
    }
    @Override
    @NonNull
    public Request getRequestObj() {
        addHeaders();
        builder.url(integrateQueryParams());
        return builder
                .get()
                .build();
    }
}

class DeleteHttpApiRequestWrapper extends HttpApiRequestWrapper {
    private  final Map<String,String[]> queryParams;
    DeleteHttpApiRequestWrapper(@NonNull Map<String,String > headers, @NonNull Map<String, String[]> queryParams, @NonNull String urlPath) {
        super(headers,urlPath);
        this.queryParams = queryParams;
    }

    private HttpUrl integrateQueryParams(){
        HttpUrl.Builder builder=HttpUrl.parse(urlPath).newBuilder();;
        for(Map.Entry<String,String[]> entry : queryParams.entrySet()) {
            for(String val:entry.getValue())
                builder.addQueryParameter(entry.getKey(), val);
        }
        return builder.build();
    }
    @Override
    @NonNull
    public Request getRequestObj() {
        addHeaders();
        builder.url(integrateQueryParams());
        return builder
                .delete()
                .build();
    }
}