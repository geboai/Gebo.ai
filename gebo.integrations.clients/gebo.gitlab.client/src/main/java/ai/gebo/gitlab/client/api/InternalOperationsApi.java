package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAiDuoWorkflowsBasicCheckpoint;
import ai.gebo.gitlab.client.model.APIEntitiesAiDuoWorkflowsCheckpoint;
import ai.gebo.gitlab.client.model.APIEntitiesAiDuoWorkflowsEvent;
import ai.gebo.gitlab.client.model.APIEntitiesAiDuoWorkflowsWorkflow;
import ai.gebo.gitlab.client.model.APIEntitiesAiSuggestedReviewer;
import ai.gebo.gitlab.client.model.PatchApiV4AiDuoWorkflowsWorkflowsIdRequest;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsRevokeTokenRequest;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsWorkflowsIdEventsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest;
import ai.gebo.gitlab.client.model.PutApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient.ResponseSpec;
import org.springframework.web.client.RestClientResponseException;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class InternalOperationsApi {
    private ApiClient apiClient;

    public InternalOperationsApi() {
        this(new ApiClient());
    }

    public InternalOperationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get workflow details
     * Retrieves detailed information about a specific workflow by its ID.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @return APIEntitiesAiDuoWorkflowsWorkflow
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AiDuoWorkflowsWorkflowsIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4AiDuoWorkflowsWorkflowsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsWorkflow> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get workflow details
     * Retrieves detailed information about a specific workflow by its ID.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @return APIEntitiesAiDuoWorkflowsWorkflow
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiDuoWorkflowsWorkflow getApiV4AiDuoWorkflowsWorkflowsId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsWorkflow> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get workflow details
     * Retrieves detailed information about a specific workflow by its ID.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @return ResponseEntity&lt;APIEntitiesAiDuoWorkflowsWorkflow&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiDuoWorkflowsWorkflow> getApiV4AiDuoWorkflowsWorkflowsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsWorkflow> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get workflow details
     * Retrieves detailed information about a specific workflow by its ID.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AiDuoWorkflowsWorkflowsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4AiDuoWorkflowsWorkflowsIdRequestCreation(id);
    }

    /**
     * Get the AI Catalog item a workflow runs, with its sub-items
     * Returns the saved AI Catalog item this session runs and its resolved subagents. A subagent the user cannot use has an error instead of a definition. When a setting turns subagents off, sub_items is empty and sub_items_skipped_reason says which setting.
     * <p><b>200</b> - OK
     * <p><b>404</b> - The flow was not found or the user cannot read it
     * <p><b>422</b> - Workflow does not run a saved AI Catalog item
     * <p><b>400</b> - Bad Request
     * @param id The ID of the workflow
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AiDuoWorkflowsWorkflowsIdCatalogItemsRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4AiDuoWorkflowsWorkflowsIdCatalogItems", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{id}/catalog_items", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get the AI Catalog item a workflow runs, with its sub-items
     * Returns the saved AI Catalog item this session runs and its resolved subagents. A subagent the user cannot use has an error instead of a definition. When a setting turns subagents off, sub_items is empty and sub_items_skipped_reason says which setting.
     * <p><b>200</b> - OK
     * <p><b>404</b> - The flow was not found or the user cannot read it
     * <p><b>422</b> - Workflow does not run a saved AI Catalog item
     * <p><b>400</b> - Bad Request
     * @param id The ID of the workflow
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4AiDuoWorkflowsWorkflowsIdCatalogItems(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4AiDuoWorkflowsWorkflowsIdCatalogItemsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get the AI Catalog item a workflow runs, with its sub-items
     * Returns the saved AI Catalog item this session runs and its resolved subagents. A subagent the user cannot use has an error instead of a definition. When a setting turns subagents off, sub_items is empty and sub_items_skipped_reason says which setting.
     * <p><b>200</b> - OK
     * <p><b>404</b> - The flow was not found or the user cannot read it
     * <p><b>422</b> - Workflow does not run a saved AI Catalog item
     * <p><b>400</b> - Bad Request
     * @param id The ID of the workflow
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4AiDuoWorkflowsWorkflowsIdCatalogItemsWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsIdCatalogItemsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get the AI Catalog item a workflow runs, with its sub-items
     * Returns the saved AI Catalog item this session runs and its resolved subagents. A subagent the user cannot use has an error instead of a definition. When a setting turns subagents off, sub_items is empty and sub_items_skipped_reason says which setting.
     * <p><b>200</b> - OK
     * <p><b>404</b> - The flow was not found or the user cannot read it
     * <p><b>422</b> - Workflow does not run a saved AI Catalog item
     * <p><b>400</b> - Bad Request
     * @param id The ID of the workflow
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AiDuoWorkflowsWorkflowsIdCatalogItemsWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4AiDuoWorkflowsWorkflowsIdCatalogItemsRequestCreation(id);
    }

    /**
     * List all workflow checkpoints
     * Lists all checkpoints associated with a specific workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param acceptCompressed If &#x60;true&#x60;, returns compressed checkpoints.
     * @param checkpointNs Only return checkpoints belonging to this LangGraph checkpoint namespace (the flow&#39;s own top-level lineage if omitted or blank). Unset (the default): return checkpoints from every lineage, unfiltered, preserving prior behavior.
     * @param channels Comma-separated channels to return in each checkpoint&#39;s channel_values, at most 100, each 1 to 255 characters. Omit to return every channel.
     * @return List&lt;APIEntitiesAiDuoWorkflowsCheckpoint&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean acceptCompressed, @jakarta.annotation.Nullable String checkpointNs, @jakarta.annotation.Nullable List<String> channels) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4AiDuoWorkflowsWorkflowsIdCheckpoints", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "accept_compressed", acceptCompressed));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "checkpoint_ns", checkpointNs));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "channels", channels));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesAiDuoWorkflowsCheckpoint>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{id}/checkpoints", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all workflow checkpoints
     * Lists all checkpoints associated with a specific workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param acceptCompressed If &#x60;true&#x60;, returns compressed checkpoints.
     * @param checkpointNs Only return checkpoints belonging to this LangGraph checkpoint namespace (the flow&#39;s own top-level lineage if omitted or blank). Unset (the default): return checkpoints from every lineage, unfiltered, preserving prior behavior.
     * @param channels Comma-separated channels to return in each checkpoint&#39;s channel_values, at most 100, each 1 to 255 characters. Omit to return every channel.
     * @return List&lt;APIEntitiesAiDuoWorkflowsCheckpoint&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAiDuoWorkflowsCheckpoint> getApiV4AiDuoWorkflowsWorkflowsIdCheckpoints(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean acceptCompressed, @jakarta.annotation.Nullable String checkpointNs, @jakarta.annotation.Nullable List<String> channels) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAiDuoWorkflowsCheckpoint>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequestCreation(id, acceptCompressed, checkpointNs, channels).body(localVarReturnType);
    }

    /**
     * List all workflow checkpoints
     * Lists all checkpoints associated with a specific workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param acceptCompressed If &#x60;true&#x60;, returns compressed checkpoints.
     * @param checkpointNs Only return checkpoints belonging to this LangGraph checkpoint namespace (the flow&#39;s own top-level lineage if omitted or blank). Unset (the default): return checkpoints from every lineage, unfiltered, preserving prior behavior.
     * @param channels Comma-separated channels to return in each checkpoint&#39;s channel_values, at most 100, each 1 to 255 characters. Omit to return every channel.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAiDuoWorkflowsCheckpoint&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAiDuoWorkflowsCheckpoint>> getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean acceptCompressed, @jakarta.annotation.Nullable String checkpointNs, @jakarta.annotation.Nullable List<String> channels) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAiDuoWorkflowsCheckpoint>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequestCreation(id, acceptCompressed, checkpointNs, channels).toEntity(localVarReturnType);
    }

    /**
     * List all workflow checkpoints
     * Lists all checkpoints associated with a specific workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param acceptCompressed If &#x60;true&#x60;, returns compressed checkpoints.
     * @param checkpointNs Only return checkpoints belonging to this LangGraph checkpoint namespace (the flow&#39;s own top-level lineage if omitted or blank). Unset (the default): return checkpoints from every lineage, unfiltered, preserving prior behavior.
     * @param channels Comma-separated channels to return in each checkpoint&#39;s channel_values, at most 100, each 1 to 255 characters. Omit to return every channel.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean acceptCompressed, @jakarta.annotation.Nullable String checkpointNs, @jakarta.annotation.Nullable List<String> channels) throws RestClientResponseException {
        return getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequestCreation(id, acceptCompressed, checkpointNs, channels);
    }

    /**
     * Get a workflow checkpoint reconstructed from incremental blobs
     * Retrieves a full workflow checkpoint reconstructed from incremental data blobs.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param threadTs The langgraph thread_ts of the checkpoint
     * @param acceptCompressed If &#x60;true&#x60;, returns the compressed checkpoint.
     * @return APIEntitiesAiDuoWorkflowsCheckpoint
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsByThreadTsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String threadTs, @jakarta.annotation.Nullable Boolean acceptCompressed) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsByThreadTs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'threadTs' is set
        if (threadTs == null) {
            throw new RestClientResponseException("Missing the required parameter 'threadTs' when calling getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsByThreadTs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "thread_ts", threadTs));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "accept_compressed", acceptCompressed));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsCheckpoint> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{id}/checkpoints/by_thread_ts", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a workflow checkpoint reconstructed from incremental blobs
     * Retrieves a full workflow checkpoint reconstructed from incremental data blobs.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param threadTs The langgraph thread_ts of the checkpoint
     * @param acceptCompressed If &#x60;true&#x60;, returns the compressed checkpoint.
     * @return APIEntitiesAiDuoWorkflowsCheckpoint
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiDuoWorkflowsCheckpoint getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsByThreadTs(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String threadTs, @jakarta.annotation.Nullable Boolean acceptCompressed) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsCheckpoint> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsByThreadTsRequestCreation(id, threadTs, acceptCompressed).body(localVarReturnType);
    }

    /**
     * Get a workflow checkpoint reconstructed from incremental blobs
     * Retrieves a full workflow checkpoint reconstructed from incremental data blobs.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param threadTs The langgraph thread_ts of the checkpoint
     * @param acceptCompressed If &#x60;true&#x60;, returns the compressed checkpoint.
     * @return ResponseEntity&lt;APIEntitiesAiDuoWorkflowsCheckpoint&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiDuoWorkflowsCheckpoint> getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsByThreadTsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String threadTs, @jakarta.annotation.Nullable Boolean acceptCompressed) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsCheckpoint> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsByThreadTsRequestCreation(id, threadTs, acceptCompressed).toEntity(localVarReturnType);
    }

    /**
     * Get a workflow checkpoint reconstructed from incremental blobs
     * Retrieves a full workflow checkpoint reconstructed from incremental data blobs.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param threadTs The langgraph thread_ts of the checkpoint
     * @param acceptCompressed If &#x60;true&#x60;, returns the compressed checkpoint.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsByThreadTsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String threadTs, @jakarta.annotation.Nullable Boolean acceptCompressed) throws RestClientResponseException {
        return getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsByThreadTsRequestCreation(id, threadTs, acceptCompressed);
    }

    /**
     * Get details on a workflow checkpoint
     * Retrieves detailed information about a specific workflow checkpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param checkpointId ID of the checkpoint.
     * @param acceptCompressed If &#x60;true&#x60;, returns the compressed checkpoint.
     * @return APIEntitiesAiDuoWorkflowsCheckpoint
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsCheckpointIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer checkpointId, @jakarta.annotation.Nullable Boolean acceptCompressed) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsCheckpointId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'checkpointId' is set
        if (checkpointId == null) {
            throw new RestClientResponseException("Missing the required parameter 'checkpointId' when calling getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsCheckpointId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("checkpoint_id", checkpointId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "accept_compressed", acceptCompressed));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsCheckpoint> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{id}/checkpoints/{checkpoint_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get details on a workflow checkpoint
     * Retrieves detailed information about a specific workflow checkpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param checkpointId ID of the checkpoint.
     * @param acceptCompressed If &#x60;true&#x60;, returns the compressed checkpoint.
     * @return APIEntitiesAiDuoWorkflowsCheckpoint
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiDuoWorkflowsCheckpoint getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsCheckpointId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer checkpointId, @jakarta.annotation.Nullable Boolean acceptCompressed) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsCheckpoint> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsCheckpointIdRequestCreation(id, checkpointId, acceptCompressed).body(localVarReturnType);
    }

    /**
     * Get details on a workflow checkpoint
     * Retrieves detailed information about a specific workflow checkpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param checkpointId ID of the checkpoint.
     * @param acceptCompressed If &#x60;true&#x60;, returns the compressed checkpoint.
     * @return ResponseEntity&lt;APIEntitiesAiDuoWorkflowsCheckpoint&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiDuoWorkflowsCheckpoint> getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsCheckpointIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer checkpointId, @jakarta.annotation.Nullable Boolean acceptCompressed) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsCheckpoint> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsCheckpointIdRequestCreation(id, checkpointId, acceptCompressed).toEntity(localVarReturnType);
    }

    /**
     * Get details on a workflow checkpoint
     * Retrieves detailed information about a specific workflow checkpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param checkpointId ID of the checkpoint.
     * @param acceptCompressed If &#x60;true&#x60;, returns the compressed checkpoint.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsCheckpointIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer checkpointId, @jakarta.annotation.Nullable Boolean acceptCompressed) throws RestClientResponseException {
        return getApiV4AiDuoWorkflowsWorkflowsIdCheckpointsCheckpointIdRequestCreation(id, checkpointId, acceptCompressed);
    }

    /**
     * List all workflow events
     * Retrieves a list of all events associated with a specific workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @return List&lt;APIEntitiesAiDuoWorkflowsEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AiDuoWorkflowsWorkflowsIdEventsRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4AiDuoWorkflowsWorkflowsIdEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesAiDuoWorkflowsEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{id}/events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all workflow events
     * Retrieves a list of all events associated with a specific workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @return List&lt;APIEntitiesAiDuoWorkflowsEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAiDuoWorkflowsEvent> getApiV4AiDuoWorkflowsWorkflowsIdEvents(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAiDuoWorkflowsEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsIdEventsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List all workflow events
     * Retrieves a list of all events associated with a specific workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAiDuoWorkflowsEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAiDuoWorkflowsEvent>> getApiV4AiDuoWorkflowsWorkflowsIdEventsWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAiDuoWorkflowsEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsIdEventsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List all workflow events
     * Retrieves a list of all events associated with a specific workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AiDuoWorkflowsWorkflowsIdEventsWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4AiDuoWorkflowsWorkflowsIdEventsRequestCreation(id);
    }

    /**
     * Update the workflow status
     * Updates the execution status of a specified workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param patchApiV4AiDuoWorkflowsWorkflowsIdRequest The patchApiV4AiDuoWorkflowsWorkflowsIdRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4AiDuoWorkflowsWorkflowsIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PatchApiV4AiDuoWorkflowsWorkflowsIdRequest patchApiV4AiDuoWorkflowsWorkflowsIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4AiDuoWorkflowsWorkflowsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'patchApiV4AiDuoWorkflowsWorkflowsIdRequest' is set
        if (patchApiV4AiDuoWorkflowsWorkflowsIdRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'patchApiV4AiDuoWorkflowsWorkflowsIdRequest' when calling patchApiV4AiDuoWorkflowsWorkflowsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4AiDuoWorkflowsWorkflowsIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update the workflow status
     * Updates the execution status of a specified workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param patchApiV4AiDuoWorkflowsWorkflowsIdRequest The patchApiV4AiDuoWorkflowsWorkflowsIdRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void patchApiV4AiDuoWorkflowsWorkflowsId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PatchApiV4AiDuoWorkflowsWorkflowsIdRequest patchApiV4AiDuoWorkflowsWorkflowsIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        patchApiV4AiDuoWorkflowsWorkflowsIdRequestCreation(id, patchApiV4AiDuoWorkflowsWorkflowsIdRequest).body(localVarReturnType);
    }

    /**
     * Update the workflow status
     * Updates the execution status of a specified workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param patchApiV4AiDuoWorkflowsWorkflowsIdRequest The patchApiV4AiDuoWorkflowsWorkflowsIdRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> patchApiV4AiDuoWorkflowsWorkflowsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PatchApiV4AiDuoWorkflowsWorkflowsIdRequest patchApiV4AiDuoWorkflowsWorkflowsIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4AiDuoWorkflowsWorkflowsIdRequestCreation(id, patchApiV4AiDuoWorkflowsWorkflowsIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update the workflow status
     * Updates the execution status of a specified workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param patchApiV4AiDuoWorkflowsWorkflowsIdRequest The patchApiV4AiDuoWorkflowsWorkflowsIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4AiDuoWorkflowsWorkflowsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PatchApiV4AiDuoWorkflowsWorkflowsIdRequest patchApiV4AiDuoWorkflowsWorkflowsIdRequest) throws RestClientResponseException {
        return patchApiV4AiDuoWorkflowsWorkflowsIdRequestCreation(id, patchApiV4AiDuoWorkflowsWorkflowsIdRequest);
    }

    /**
     * Revoke ai_workflows token
     * Revokes an active Duo Workflows authentication token.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable Entity
     * @param postApiV4AiDuoWorkflowsRevokeTokenRequest The postApiV4AiDuoWorkflowsRevokeTokenRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsRevokeTokenRequestCreation(@jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsRevokeTokenRequest postApiV4AiDuoWorkflowsRevokeTokenRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4AiDuoWorkflowsRevokeTokenRequest' is set
        if (postApiV4AiDuoWorkflowsRevokeTokenRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4AiDuoWorkflowsRevokeTokenRequest' when calling postApiV4AiDuoWorkflowsRevokeToken", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/revoke_token", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AiDuoWorkflowsRevokeTokenRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke ai_workflows token
     * Revokes an active Duo Workflows authentication token.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable Entity
     * @param postApiV4AiDuoWorkflowsRevokeTokenRequest The postApiV4AiDuoWorkflowsRevokeTokenRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AiDuoWorkflowsRevokeToken(@jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsRevokeTokenRequest postApiV4AiDuoWorkflowsRevokeTokenRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AiDuoWorkflowsRevokeTokenRequestCreation(postApiV4AiDuoWorkflowsRevokeTokenRequest).body(localVarReturnType);
    }

    /**
     * Revoke ai_workflows token
     * Revokes an active Duo Workflows authentication token.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable Entity
     * @param postApiV4AiDuoWorkflowsRevokeTokenRequest The postApiV4AiDuoWorkflowsRevokeTokenRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AiDuoWorkflowsRevokeTokenWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsRevokeTokenRequest postApiV4AiDuoWorkflowsRevokeTokenRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsRevokeTokenRequestCreation(postApiV4AiDuoWorkflowsRevokeTokenRequest).toEntity(localVarReturnType);
    }

    /**
     * Revoke ai_workflows token
     * Revokes an active Duo Workflows authentication token.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable Entity
     * @param postApiV4AiDuoWorkflowsRevokeTokenRequest The postApiV4AiDuoWorkflowsRevokeTokenRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsRevokeTokenWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsRevokeTokenRequest postApiV4AiDuoWorkflowsRevokeTokenRequest) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsRevokeTokenRequestCreation(postApiV4AiDuoWorkflowsRevokeTokenRequest);
    }

    /**
     * Ingest AI audit events from Duo Workflow Service
     * 
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest The postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4AiDuoWorkflowsWorkflowsIdAuditEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest' is set
        if (postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest' when calling postApiV4AiDuoWorkflowsWorkflowsIdAuditEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{id}/audit_events", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Ingest AI audit events from Duo Workflow Service
     * 
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest The postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AiDuoWorkflowsWorkflowsIdAuditEvents(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequestCreation(id, postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest).body(localVarReturnType);
    }

    /**
     * Ingest AI audit events from Duo Workflow Service
     * 
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest The postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequestCreation(id, postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest).toEntity(localVarReturnType);
    }

    /**
     * Ingest AI audit events from Duo Workflow Service
     * 
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest The postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequestCreation(id, postApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest);
    }

    /**
     * Create multiple workflow checkpoint writes
     * Writes multiple checkpoint entries for a workflow in a single request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest The postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest' is set
        if (postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest' when calling postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{id}/checkpoint_writes_batch", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create multiple workflow checkpoint writes
     * Writes multiple checkpoint entries for a workflow in a single request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest The postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatch(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequestCreation(id, postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest).body(localVarReturnType);
    }

    /**
     * Create multiple workflow checkpoint writes
     * Writes multiple checkpoint entries for a workflow in a single request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest The postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequestCreation(id, postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest).toEntity(localVarReturnType);
    }

    /**
     * Create multiple workflow checkpoint writes
     * Writes multiple checkpoint entries for a workflow in a single request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest The postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequestCreation(id, postApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest);
    }

    /**
     * Create workflow checkpoint
     * Creates a new checkpoint state for an active workflow execution.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest The postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest parameter
     * @return APIEntitiesAiDuoWorkflowsBasicCheckpoint
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4AiDuoWorkflowsWorkflowsIdCheckpoints", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest' is set
        if (postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest' when calling postApiV4AiDuoWorkflowsWorkflowsIdCheckpoints", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsBasicCheckpoint> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{id}/checkpoints", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create workflow checkpoint
     * Creates a new checkpoint state for an active workflow execution.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest The postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest parameter
     * @return APIEntitiesAiDuoWorkflowsBasicCheckpoint
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiDuoWorkflowsBasicCheckpoint postApiV4AiDuoWorkflowsWorkflowsIdCheckpoints(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsBasicCheckpoint> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequestCreation(id, postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest).body(localVarReturnType);
    }

    /**
     * Create workflow checkpoint
     * Creates a new checkpoint state for an active workflow execution.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest The postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAiDuoWorkflowsBasicCheckpoint&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiDuoWorkflowsBasicCheckpoint> postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsBasicCheckpoint> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequestCreation(id, postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create workflow checkpoint
     * Creates a new checkpoint state for an active workflow execution.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest The postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequestCreation(id, postApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest);
    }

    /**
     * Create workflow event
     * Creates a new event for a specified workflow execution.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest The postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest parameter
     * @return APIEntitiesAiDuoWorkflowsEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsWorkflowsIdEventsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdEventsRequest postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4AiDuoWorkflowsWorkflowsIdEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest' is set
        if (postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest' when calling postApiV4AiDuoWorkflowsWorkflowsIdEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{id}/events", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create workflow event
     * Creates a new event for a specified workflow execution.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest The postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest parameter
     * @return APIEntitiesAiDuoWorkflowsEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiDuoWorkflowsEvent postApiV4AiDuoWorkflowsWorkflowsIdEvents(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdEventsRequest postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsIdEventsRequestCreation(id, postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest).body(localVarReturnType);
    }

    /**
     * Create workflow event
     * Creates a new event for a specified workflow execution.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest The postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAiDuoWorkflowsEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiDuoWorkflowsEvent> postApiV4AiDuoWorkflowsWorkflowsIdEventsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdEventsRequest postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsIdEventsRequestCreation(id, postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create workflow event
     * Creates a new event for a specified workflow execution.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest The postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsWorkflowsIdEventsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsIdEventsRequest postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsWorkflowsIdEventsRequestCreation(id, postApiV4AiDuoWorkflowsWorkflowsIdEventsRequest);
    }

    /**
     * Process Slack interaction events
     * Processes interaction events from Slack
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4IntegrationsSlackInteractionsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/integrations/slack/interactions", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Process Slack interaction events
     * Processes interaction events from Slack
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4IntegrationsSlackInteractions() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4IntegrationsSlackInteractionsRequestCreation().body(localVarReturnType);
    }

    /**
     * Process Slack interaction events
     * Processes interaction events from Slack
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4IntegrationsSlackInteractionsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4IntegrationsSlackInteractionsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Process Slack interaction events
     * Processes interaction events from Slack
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4IntegrationsSlackInteractionsWithResponseSpec() throws RestClientResponseException {
        return postApiV4IntegrationsSlackInteractionsRequestCreation();
    }

    /**
     * Get Slack interactive component options
     * Retrieves options for Slack interactive components
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4IntegrationsSlackOptionsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/integrations/slack/options", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get Slack interactive component options
     * Retrieves options for Slack interactive components
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4IntegrationsSlackOptions() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4IntegrationsSlackOptionsRequestCreation().body(localVarReturnType);
    }

    /**
     * Get Slack interactive component options
     * Retrieves options for Slack interactive components
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4IntegrationsSlackOptionsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4IntegrationsSlackOptionsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get Slack interactive component options
     * Retrieves options for Slack interactive components
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4IntegrationsSlackOptionsWithResponseSpec() throws RestClientResponseException {
        return postApiV4IntegrationsSlackOptionsRequestCreation();
    }

    /**
     * Set the AI-recommended reviewers for a merge request
     * Replaces the AI-recommended reviewers persisted for a specified merge request. Available only when the recommend reviewers flow is enabled for the project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest parameter
     * @return List&lt;APIEntitiesAiSuggestedReviewer&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest' is set
        if (postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesAiSuggestedReviewer>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/suggested_reviewers", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Set the AI-recommended reviewers for a merge request
     * Replaces the AI-recommended reviewers persisted for a specified merge request. Available only when the recommend reviewers flow is enabled for the project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest parameter
     * @return List&lt;APIEntitiesAiSuggestedReviewer&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAiSuggestedReviewer> postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewers(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAiSuggestedReviewer>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest).body(localVarReturnType);
    }

    /**
     * Set the AI-recommended reviewers for a merge request
     * Replaces the AI-recommended reviewers persisted for a specified merge request. Available only when the recommend reviewers flow is enabled for the project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest parameter
     * @return ResponseEntity&lt;List&lt;APIEntitiesAiSuggestedReviewer&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAiSuggestedReviewer>> postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAiSuggestedReviewer>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest).toEntity(localVarReturnType);
    }

    /**
     * Set the AI-recommended reviewers for a merge request
     * Replaces the AI-recommended reviewers persisted for a specified merge request. Available only when the recommend reviewers flow is enabled for the project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidSuggestedReviewersRequest);
    }

    /**
     * Update workflow event
     * Updates an existing event for a specified workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param eventId ID of the event.
     * @param putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest The putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest parameter
     * @return APIEntitiesAiDuoWorkflowsEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer eventId, @jakarta.annotation.Nonnull PutApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4AiDuoWorkflowsWorkflowsIdEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventId' is set
        if (eventId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventId' when calling putApiV4AiDuoWorkflowsWorkflowsIdEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest' is set
        if (putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest' when calling putApiV4AiDuoWorkflowsWorkflowsIdEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("event_id", eventId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{id}/events/{event_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update workflow event
     * Updates an existing event for a specified workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param eventId ID of the event.
     * @param putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest The putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest parameter
     * @return APIEntitiesAiDuoWorkflowsEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiDuoWorkflowsEvent putApiV4AiDuoWorkflowsWorkflowsIdEventsEventId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer eventId, @jakarta.annotation.Nonnull PutApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequestCreation(id, eventId, putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest).body(localVarReturnType);
    }

    /**
     * Update workflow event
     * Updates an existing event for a specified workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param eventId ID of the event.
     * @param putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest The putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAiDuoWorkflowsEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiDuoWorkflowsEvent> putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer eventId, @jakarta.annotation.Nonnull PutApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequestCreation(id, eventId, putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update workflow event
     * Updates an existing event for a specified workflow.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the workflow.
     * @param eventId ID of the event.
     * @param putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest The putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer eventId, @jakarta.annotation.Nonnull PutApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest) throws RestClientResponseException {
        return putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequestCreation(id, eventId, putApiV4AiDuoWorkflowsWorkflowsIdEventsEventIdRequest);
    }
}
