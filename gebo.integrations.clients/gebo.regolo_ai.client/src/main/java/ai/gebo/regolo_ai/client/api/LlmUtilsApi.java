package ai.gebo.regolo_ai.client.api;//jersey3

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.regolo_ai.client.invoker.ApiClient;
import ai.gebo.regolo_ai.client.invoker.ApiException;
import ai.gebo.regolo_ai.client.invoker.Configuration;
import ai.gebo.regolo_ai.client.invoker.Pair;
import ai.gebo.regolo_ai.client.model.TokenCountRequest;
import ai.gebo.regolo_ai.client.model.TokenCountResponse;
import jakarta.ws.rs.core.GenericType;

@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-09-02T09:08:15.751524600+02:00[Europe/Rome]")
public class LlmUtilsApi {
    private ApiClient apiClient;

    public LlmUtilsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public LlmUtilsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Supported Openai Params
     * Returns supported openai params for a given litellm model name   e.g. &#x60;gpt-4&#x60; vs &#x60;gpt-3.5-turbo&#x60;   Example curl:  &#x60;&#x60;&#x60; curl -X GET --location &#x27;https://api.regolo.ai/utils/supported_openai_params?model&#x3D;gpt-3.5-turbo-16k&#x27;         --header &#x27;Authorization: Bearer sk-1234&#x27; &#x60;&#x60;&#x60;
     * @param model  (required)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object supportedOpenaiParamsUtilsSupportedOpenaiParamsGet(Object model) throws ApiException {
        Object localVarPostBody = null;
        // verify the required parameter 'model' is set
        if (model == null) {
            throw new ApiException(400, "Missing the required parameter 'model' when calling supportedOpenaiParamsUtilsSupportedOpenaiParamsGet");
        }
        // create path and map variables
        String localVarPath = "/utils/supported_openai_params";

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        localVarQueryParams.addAll(apiClient.parameterToPairs("", "model", model));


        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "APIKeyHeader" };

        GenericType<Object> localVarReturnType = new GenericType<Object>() {};
        return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Token Counter
     * 
     * @param body  (required)
     * @return TokenCountResponse
     * @throws ApiException if fails to make API call
     */
    public TokenCountResponse tokenCounterUtilsTokenCounterPost(TokenCountRequest body) throws ApiException {
        Object localVarPostBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(400, "Missing the required parameter 'body' when calling tokenCounterUtilsTokenCounterPost");
        }
        // create path and map variables
        String localVarPath = "/utils/token_counter";

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

        GenericType<TokenCountResponse> localVarReturnType = new GenericType<TokenCountResponse>() {};
        return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
}
