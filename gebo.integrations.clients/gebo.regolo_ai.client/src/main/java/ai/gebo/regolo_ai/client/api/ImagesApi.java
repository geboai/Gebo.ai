package ai.gebo.regolo_ai.client.api;//jersey3

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.regolo_ai.client.invoker.ApiClient;
import ai.gebo.regolo_ai.client.invoker.ApiException;
import ai.gebo.regolo_ai.client.invoker.Configuration;
import ai.gebo.regolo_ai.client.invoker.Pair;
import ai.gebo.regolo_ai.client.model.ImagesGenerationsBody;
import ai.gebo.regolo_ai.client.model.ImagesGenerationsBody1;
import jakarta.ws.rs.core.GenericType;

@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-09-02T09:08:15.751524600+02:00[Europe/Rome]")
public class ImagesApi {
    private ApiClient apiClient;

    public ImagesApi() {
        this(Configuration.getDefaultApiClient());
    }

    public ImagesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Image Generation
     * 
     * @param body  (required)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object imageGenerationImagesGenerationsPost(ImagesGenerationsBody body) throws ApiException {
        Object localVarPostBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(400, "Missing the required parameter 'body' when calling imageGenerationImagesGenerationsPost");
        }
        // create path and map variables
        String localVarPath = "/images/generations";

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();



        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "APIKeyHeader" };

        GenericType<Object> localVarReturnType = new GenericType<Object>() {};
        return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Image Generation
     * 
     * @param body  (required)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object imageGenerationV1ImagesGenerationsPost(ImagesGenerationsBody1 body) throws ApiException {
        Object localVarPostBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(400, "Missing the required parameter 'body' when calling imageGenerationV1ImagesGenerationsPost");
        }
        // create path and map variables
        String localVarPath = "/v1/images/generations";

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();



        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "APIKeyHeader" };

        GenericType<Object> localVarReturnType = new GenericType<Object>() {};
        return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
}
