package ai.gebo.regolo_ai.client.api;//jersey3

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.regolo_ai.client.invoker.ApiClient;
import ai.gebo.regolo_ai.client.invoker.ApiException;
import ai.gebo.regolo_ai.client.invoker.Configuration;
import ai.gebo.regolo_ai.client.invoker.Pair;
import jakarta.ws.rs.core.GenericType;

@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-09-02T09:08:15.751524600+02:00[Europe/Rome]")
public class ModelManagementApi {
    private ApiClient apiClient;

    public ModelManagementApi() {
        this(Configuration.getDefaultApiClient());
    }

    public ModelManagementApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Model Info V1
     * Provides more info about each model in /models  Parameters:     regolo_model_id: Optional[str] &#x3D; None      - When regolo_model_id is passed, it will return the info for that specific model     - When regolo_model_id is not passed, it will return the info for all models  Returns:     Returns a dictionary containing information about each model.  Example Response: &#x60;&#x60;&#x60;json {     \&quot;data\&quot;: [                 {                     \&quot;model_name\&quot;: \&quot;fake-openai-endpoint\&quot;,                     \&quot;litellm_params\&quot;: {                         \&quot;api_base\&quot;: \&quot;https://exampleopenaiendpoint-production.up.railway.app/\&quot;,                         \&quot;model\&quot;: \&quot;openai/fake\&quot;                     },                     \&quot;model_info\&quot;: {                         \&quot;id\&quot;: \&quot;112f74fab24a7a5245d2ced3536dd8f5f9192c57ee6e332af0f0512e08bed5af\&quot;,                         \&quot;db_model\&quot;: false                     }                 }             ] }  &#x60;&#x60;&#x60;
     * @param regoloModelId  (optional)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object modelInfoV1ModelInfoGet(Object regoloModelId) throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/model/info";

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        localVarQueryParams.addAll(apiClient.parameterToPairs("", "regolo_model_id", regoloModelId));


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
     * Model Info V1
     * Provides more info about each model in /models  Parameters:     regolo_model_id: Optional[str] &#x3D; None      - When regolo_model_id is passed, it will return the info for that specific model     - When regolo_model_id is not passed, it will return the info for all models  Returns:     Returns a dictionary containing information about each model.  Example Response: &#x60;&#x60;&#x60;json {     \&quot;data\&quot;: [                 {                     \&quot;model_name\&quot;: \&quot;fake-openai-endpoint\&quot;,                     \&quot;litellm_params\&quot;: {                         \&quot;api_base\&quot;: \&quot;https://exampleopenaiendpoint-production.up.railway.app/\&quot;,                         \&quot;model\&quot;: \&quot;openai/fake\&quot;                     },                     \&quot;model_info\&quot;: {                         \&quot;id\&quot;: \&quot;112f74fab24a7a5245d2ced3536dd8f5f9192c57ee6e332af0f0512e08bed5af\&quot;,                         \&quot;db_model\&quot;: false                     }                 }             ] }  &#x60;&#x60;&#x60;
     * @param regoloModelId  (optional)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object modelInfoV1V1ModelInfoGet(Object regoloModelId) throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/v1/model/info";

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        localVarQueryParams.addAll(apiClient.parameterToPairs("", "regolo_model_id", regoloModelId));


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
     * Model List
     * Use &#x60;/model/info&#x60; - to get detailed model information, example - pricing, mode, etc.  This is just for compatibility with openai projects like aider.
     * @param returnWildcardRoutes  (optional, default to false)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object modelListModelsGet(Object returnWildcardRoutes) throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/models";

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        localVarQueryParams.addAll(apiClient.parameterToPairs("", "return_wildcard_routes", returnWildcardRoutes));


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
     * Model List
     * Use &#x60;/model/info&#x60; - to get detailed model information, example - pricing, mode, etc.  This is just for compatibility with openai projects like aider.
     * @param returnWildcardRoutes  (optional, default to false)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object modelListV1ModelsGet(Object returnWildcardRoutes) throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/v1/models";

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        localVarQueryParams.addAll(apiClient.parameterToPairs("", "return_wildcard_routes", returnWildcardRoutes));


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
}
