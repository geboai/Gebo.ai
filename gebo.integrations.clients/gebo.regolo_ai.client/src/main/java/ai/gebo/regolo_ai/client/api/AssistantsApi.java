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
public class AssistantsApi {
    private ApiClient apiClient;

    public AssistantsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AssistantsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add Messages
     * Create a message.  API Reference - https://platform.openai.com/docs/api-reference/messages/createMessage
     * @param threadId  (required)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object addMessagesThreadsThreadIdMessagesPost(Object threadId) throws ApiException {
        Object localVarPostBody = null;
        // verify the required parameter 'threadId' is set
        if (threadId == null) {
            throw new ApiException(400, "Missing the required parameter 'threadId' when calling addMessagesThreadsThreadIdMessagesPost");
        }
        // create path and map variables
        String localVarPath = "/threads/{thread_id}/messages"
                .replaceAll("\\{" + "thread_id" + "\\}", apiClient.escapeString(threadId.toString()));

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
     * Add Messages
     * Create a message.  API Reference - https://platform.openai.com/docs/api-reference/messages/createMessage
     * @param threadId  (required)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object addMessagesV1ThreadsThreadIdMessagesPost(Object threadId) throws ApiException {
        Object localVarPostBody = null;
        // verify the required parameter 'threadId' is set
        if (threadId == null) {
            throw new ApiException(400, "Missing the required parameter 'threadId' when calling addMessagesV1ThreadsThreadIdMessagesPost");
        }
        // create path and map variables
        String localVarPath = "/v1/threads/{thread_id}/messages"
                .replaceAll("\\{" + "thread_id" + "\\}", apiClient.escapeString(threadId.toString()));

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
     * Create Assistant
     * Create assistant  API Reference docs - https://platform.openai.com/docs/api-reference/assistants/createAssistant
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object createAssistantAssistantsPost() throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/assistants";

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
     * Create Assistant
     * Create assistant  API Reference docs - https://platform.openai.com/docs/api-reference/assistants/createAssistant
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object createAssistantV1AssistantsPost() throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/v1/assistants";

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
     * Create Threads
     * Create a thread.  API Reference - https://platform.openai.com/docs/api-reference/threads/createThread
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object createThreadsThreadsPost() throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/threads";

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
     * Create Threads
     * Create a thread.  API Reference - https://platform.openai.com/docs/api-reference/threads/createThread
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object createThreadsV1ThreadsPost() throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/v1/threads";

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
     * Delete Assistant
     * Delete assistant  API Reference docs - https://platform.openai.com/docs/api-reference/assistants/createAssistant
     * @param assistantId  (required)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object deleteAssistantAssistantsAssistantIdDelete(Object assistantId) throws ApiException {
        Object localVarPostBody = null;
        // verify the required parameter 'assistantId' is set
        if (assistantId == null) {
            throw new ApiException(400, "Missing the required parameter 'assistantId' when calling deleteAssistantAssistantsAssistantIdDelete");
        }
        // create path and map variables
        String localVarPath = "/assistants/{assistant_id}"
                .replaceAll("\\{" + "assistant_id" + "\\}", apiClient.escapeString(assistantId.toString()));

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
        return apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Delete Assistant
     * Delete assistant  API Reference docs - https://platform.openai.com/docs/api-reference/assistants/createAssistant
     * @param assistantId  (required)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object deleteAssistantV1AssistantsAssistantIdDelete(Object assistantId) throws ApiException {
        Object localVarPostBody = null;
        // verify the required parameter 'assistantId' is set
        if (assistantId == null) {
            throw new ApiException(400, "Missing the required parameter 'assistantId' when calling deleteAssistantV1AssistantsAssistantIdDelete");
        }
        // create path and map variables
        String localVarPath = "/v1/assistants/{assistant_id}"
                .replaceAll("\\{" + "assistant_id" + "\\}", apiClient.escapeString(assistantId.toString()));

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
        return apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Get Assistants
     * Returns a list of assistants.  API Reference docs - https://platform.openai.com/docs/api-reference/assistants/listAssistants
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object getAssistantsAssistantsGet() throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/assistants";

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
        return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Get Assistants
     * Returns a list of assistants.  API Reference docs - https://platform.openai.com/docs/api-reference/assistants/listAssistants
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object getAssistantsV1AssistantsGet() throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/v1/assistants";

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
        return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Get Messages
     * Returns a list of messages for a given thread.  API Reference - https://platform.openai.com/docs/api-reference/messages/listMessages
     * @param threadId  (required)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object getMessagesThreadsThreadIdMessagesGet(Object threadId) throws ApiException {
        Object localVarPostBody = null;
        // verify the required parameter 'threadId' is set
        if (threadId == null) {
            throw new ApiException(400, "Missing the required parameter 'threadId' when calling getMessagesThreadsThreadIdMessagesGet");
        }
        // create path and map variables
        String localVarPath = "/threads/{thread_id}/messages"
                .replaceAll("\\{" + "thread_id" + "\\}", apiClient.escapeString(threadId.toString()));

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
        return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Get Messages
     * Returns a list of messages for a given thread.  API Reference - https://platform.openai.com/docs/api-reference/messages/listMessages
     * @param threadId  (required)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object getMessagesV1ThreadsThreadIdMessagesGet(Object threadId) throws ApiException {
        Object localVarPostBody = null;
        // verify the required parameter 'threadId' is set
        if (threadId == null) {
            throw new ApiException(400, "Missing the required parameter 'threadId' when calling getMessagesV1ThreadsThreadIdMessagesGet");
        }
        // create path and map variables
        String localVarPath = "/v1/threads/{thread_id}/messages"
                .replaceAll("\\{" + "thread_id" + "\\}", apiClient.escapeString(threadId.toString()));

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
        return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Get Thread
     * Retrieves a thread.  API Reference - https://platform.openai.com/docs/api-reference/threads/getThread
     * @param threadId  (required)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object getThreadThreadsThreadIdGet(Object threadId) throws ApiException {
        Object localVarPostBody = null;
        // verify the required parameter 'threadId' is set
        if (threadId == null) {
            throw new ApiException(400, "Missing the required parameter 'threadId' when calling getThreadThreadsThreadIdGet");
        }
        // create path and map variables
        String localVarPath = "/threads/{thread_id}"
                .replaceAll("\\{" + "thread_id" + "\\}", apiClient.escapeString(threadId.toString()));

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
        return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Get Thread
     * Retrieves a thread.  API Reference - https://platform.openai.com/docs/api-reference/threads/getThread
     * @param threadId  (required)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object getThreadV1ThreadsThreadIdGet(Object threadId) throws ApiException {
        Object localVarPostBody = null;
        // verify the required parameter 'threadId' is set
        if (threadId == null) {
            throw new ApiException(400, "Missing the required parameter 'threadId' when calling getThreadV1ThreadsThreadIdGet");
        }
        // create path and map variables
        String localVarPath = "/v1/threads/{thread_id}"
                .replaceAll("\\{" + "thread_id" + "\\}", apiClient.escapeString(threadId.toString()));

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
        return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Run Thread
     * Create a run.  API Reference: https://platform.openai.com/docs/api-reference/runs/createRun
     * @param threadId  (required)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object runThreadThreadsThreadIdRunsPost(Object threadId) throws ApiException {
        Object localVarPostBody = null;
        // verify the required parameter 'threadId' is set
        if (threadId == null) {
            throw new ApiException(400, "Missing the required parameter 'threadId' when calling runThreadThreadsThreadIdRunsPost");
        }
        // create path and map variables
        String localVarPath = "/threads/{thread_id}/runs"
                .replaceAll("\\{" + "thread_id" + "\\}", apiClient.escapeString(threadId.toString()));

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
     * Run Thread
     * Create a run.  API Reference: https://platform.openai.com/docs/api-reference/runs/createRun
     * @param threadId  (required)
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object runThreadV1ThreadsThreadIdRunsPost(Object threadId) throws ApiException {
        Object localVarPostBody = null;
        // verify the required parameter 'threadId' is set
        if (threadId == null) {
            throw new ApiException(400, "Missing the required parameter 'threadId' when calling runThreadV1ThreadsThreadIdRunsPost");
        }
        // create path and map variables
        String localVarPath = "/v1/threads/{thread_id}/runs"
                .replaceAll("\\{" + "thread_id" + "\\}", apiClient.escapeString(threadId.toString()));

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
}
