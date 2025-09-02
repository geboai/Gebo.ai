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
public class EmbeddingsApi {
    private ApiClient apiClient;

    public EmbeddingsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public EmbeddingsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Embeddings
     * Follows the exact same API spec as &#x60;OpenAI&#x27;s Embeddings API https://platform.openai.com/docs/api-reference/embeddings&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/embeddings  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;text-embedding-ada-002\&quot;,     \&quot;input\&quot;: \&quot;The quick brown fox jumps over the lazy dog\&quot; }&#x27; &#x60;&#x60;&#x60;
     * @param model  (optional)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object embeddingsEmbeddingsPost(Object model) throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/embeddings";

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
        return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Embeddings
     * Follows the exact same API spec as &#x60;OpenAI&#x27;s Embeddings API https://platform.openai.com/docs/api-reference/embeddings&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/embeddings  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;text-embedding-ada-002\&quot;,     \&quot;input\&quot;: \&quot;The quick brown fox jumps over the lazy dog\&quot; }&#x27; &#x60;&#x60;&#x60;
     * @param model  (required)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object embeddingsEnginesModelEmbeddingsPost(Object model) throws ApiException {
        Object localVarPostBody = null;
        // verify the required parameter 'model' is set
        if (model == null) {
            throw new ApiException(400, "Missing the required parameter 'model' when calling embeddingsEnginesModelEmbeddingsPost");
        }
        // create path and map variables
        String localVarPath = "/engines/{model}/embeddings"
                .replaceAll("\\{" + "model" + "\\}", apiClient.escapeString(model.toString()));

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();



        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "APIKeyHeader" };

        GenericType<Object> localVarReturnType = new GenericType<Object>() {};
        return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Embeddings
     * Follows the exact same API spec as &#x60;OpenAI&#x27;s Embeddings API https://platform.openai.com/docs/api-reference/embeddings&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/embeddings  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;text-embedding-ada-002\&quot;,     \&quot;input\&quot;: \&quot;The quick brown fox jumps over the lazy dog\&quot; }&#x27; &#x60;&#x60;&#x60;
     * @param model  (required)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object embeddingsOpenaiDeploymentsModelEmbeddingsPost(Object model) throws ApiException {
        Object localVarPostBody = null;
        // verify the required parameter 'model' is set
        if (model == null) {
            throw new ApiException(400, "Missing the required parameter 'model' when calling embeddingsOpenaiDeploymentsModelEmbeddingsPost");
        }
        // create path and map variables
        String localVarPath = "/openai/deployments/{model}/embeddings"
                .replaceAll("\\{" + "model" + "\\}", apiClient.escapeString(model.toString()));

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();



        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "APIKeyHeader" };

        GenericType<Object> localVarReturnType = new GenericType<Object>() {};
        return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Embeddings
     * Follows the exact same API spec as &#x60;OpenAI&#x27;s Embeddings API https://platform.openai.com/docs/api-reference/embeddings&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/embeddings  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;text-embedding-ada-002\&quot;,     \&quot;input\&quot;: \&quot;The quick brown fox jumps over the lazy dog\&quot; }&#x27; &#x60;&#x60;&#x60;
     * @param model  (optional)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object embeddingsV1EmbeddingsPost(Object model) throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/v1/embeddings";

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
        return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
}
