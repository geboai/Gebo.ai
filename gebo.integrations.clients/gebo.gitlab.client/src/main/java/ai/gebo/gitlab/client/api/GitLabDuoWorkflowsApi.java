package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAiDuoWorkflowsBasicCheckpoint;
import ai.gebo.gitlab.client.model.APIEntitiesAiDuoWorkflowsCheckpoint;
import ai.gebo.gitlab.client.model.APIEntitiesAiDuoWorkflowsEvent;
import ai.gebo.gitlab.client.model.APIEntitiesAiDuoWorkflowsSessionCapsule;
import ai.gebo.gitlab.client.model.APIEntitiesAiDuoWorkflowsWorkflow;
import ai.gebo.gitlab.client.model.APIEntitiesAiDuoWorkflowsWorkflowAgentPrivileges;
import ai.gebo.gitlab.client.model.APIEntitiesAiSuggestedReviewer;
import java.io.File;
import ai.gebo.gitlab.client.model.PatchApiV4AiDuoWorkflowsWorkflowsIdRequest;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsAgentWorkflowsRequest;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsDirectAccessRequest;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsRevokeTokenRequest;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsWorkflowsIdAuditEventsRequest;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsWorkflowsIdCheckpointWritesBatchRequest;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsWorkflowsIdCheckpointsRequest;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsWorkflowsIdEventsRequest;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsWorkflowsRequest;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest;
import ai.gebo.gitlab.client.model.PostApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest;
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
public class GitLabDuoWorkflowsApi {
    private ApiClient apiClient;

    public GitLabDuoWorkflowsApi() {
        this(new ApiClient());
    }

    public GitLabDuoWorkflowsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Fetch the Duo Code Review custom instructions for a merge request
     * Fetch all Duo Code Review custom instructions for a merge request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param projectId ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AiDuoWorkflowsCodeReviewCustomInstructionsRequestCreation(@jakarta.annotation.Nullable String projectId, @jakarta.annotation.Nullable Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getApiV4AiDuoWorkflowsCodeReviewCustomInstructions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4AiDuoWorkflowsCodeReviewCustomInstructions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "project_id", projectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merge_request_iid", mergeRequestIid));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/code_review/custom_instructions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Fetch the Duo Code Review custom instructions for a merge request
     * Fetch all Duo Code Review custom instructions for a merge request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param projectId ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4AiDuoWorkflowsCodeReviewCustomInstructions(@jakarta.annotation.Nullable String projectId, @jakarta.annotation.Nullable Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4AiDuoWorkflowsCodeReviewCustomInstructionsRequestCreation(projectId, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Fetch the Duo Code Review custom instructions for a merge request
     * Fetch all Duo Code Review custom instructions for a merge request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param projectId ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4AiDuoWorkflowsCodeReviewCustomInstructionsWithHttpInfo(@jakarta.annotation.Nullable String projectId, @jakarta.annotation.Nullable Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsCodeReviewCustomInstructionsRequestCreation(projectId, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Fetch the Duo Code Review custom instructions for a merge request
     * Fetch all Duo Code Review custom instructions for a merge request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param projectId ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AiDuoWorkflowsCodeReviewCustomInstructionsWithResponseSpec(@jakarta.annotation.Nullable String projectId, @jakarta.annotation.Nullable Integer mergeRequestIid) throws RestClientResponseException {
        return getApiV4AiDuoWorkflowsCodeReviewCustomInstructionsRequestCreation(projectId, mergeRequestIid);
    }

    /**
     * List Duo Agent Platform tools
     * Returns a list of all available Duo Agent Platform tools.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param workflowDefinition workflow type based on its capability
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AiDuoWorkflowsListToolsRequestCreation(@jakarta.annotation.Nullable String workflowDefinition) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflow_definition", workflowDefinition));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/list_tools", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List Duo Agent Platform tools
     * Returns a list of all available Duo Agent Platform tools.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param workflowDefinition workflow type based on its capability
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4AiDuoWorkflowsListTools(@jakarta.annotation.Nullable String workflowDefinition) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4AiDuoWorkflowsListToolsRequestCreation(workflowDefinition).body(localVarReturnType);
    }

    /**
     * List Duo Agent Platform tools
     * Returns a list of all available Duo Agent Platform tools.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param workflowDefinition workflow type based on its capability
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4AiDuoWorkflowsListToolsWithHttpInfo(@jakarta.annotation.Nullable String workflowDefinition) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsListToolsRequestCreation(workflowDefinition).toEntity(localVarReturnType);
    }

    /**
     * List Duo Agent Platform tools
     * Returns a list of all available Duo Agent Platform tools.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param workflowDefinition workflow type based on its capability
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AiDuoWorkflowsListToolsWithResponseSpec(@jakarta.annotation.Nullable String workflowDefinition) throws RestClientResponseException {
        return getApiV4AiDuoWorkflowsListToolsRequestCreation(workflowDefinition);
    }

    /**
     * List all agent privileges
     * Lists all available agent privileges with their IDs, names, descriptions, and whether each is enabled by default. This endpoint has no supported attributes.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return APIEntitiesAiDuoWorkflowsWorkflowAgentPrivileges
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AiDuoWorkflowsWorkflowsAgentPrivilegesRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsWorkflowAgentPrivileges> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/agent_privileges", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all agent privileges
     * Lists all available agent privileges with their IDs, names, descriptions, and whether each is enabled by default. This endpoint has no supported attributes.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return APIEntitiesAiDuoWorkflowsWorkflowAgentPrivileges
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiDuoWorkflowsWorkflowAgentPrivileges getApiV4AiDuoWorkflowsWorkflowsAgentPrivileges() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsWorkflowAgentPrivileges> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsAgentPrivilegesRequestCreation().body(localVarReturnType);
    }

    /**
     * List all agent privileges
     * Lists all available agent privileges with their IDs, names, descriptions, and whether each is enabled by default. This endpoint has no supported attributes.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseEntity&lt;APIEntitiesAiDuoWorkflowsWorkflowAgentPrivileges&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiDuoWorkflowsWorkflowAgentPrivileges> getApiV4AiDuoWorkflowsWorkflowsAgentPrivilegesWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsWorkflowAgentPrivileges> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsAgentPrivilegesRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all agent privileges
     * Lists all available agent privileges with their IDs, names, descriptions, and whether each is enabled by default. This endpoint has no supported attributes.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AiDuoWorkflowsWorkflowsAgentPrivilegesWithResponseSpec() throws RestClientResponseException {
        return getApiV4AiDuoWorkflowsWorkflowsAgentPrivilegesRequestCreation();
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
     * List session capsules
     * Lists the capsules of a session that have not expired, newest first.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the session
     * @param threadTs Return only capsules taken at this checkpoint thread
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAiDuoWorkflowsSessionCapsule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesRequestCreation(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nullable String threadTs, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new RestClientResponseException("Missing the required parameter 'workflowId' when calling getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("workflow_id", workflowId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "thread_ts", threadTs));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesAiDuoWorkflowsSessionCapsule>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{workflow_id}/capsules", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List session capsules
     * Lists the capsules of a session that have not expired, newest first.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the session
     * @param threadTs Return only capsules taken at this checkpoint thread
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAiDuoWorkflowsSessionCapsule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAiDuoWorkflowsSessionCapsule> getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsules(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nullable String threadTs, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAiDuoWorkflowsSessionCapsule>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesRequestCreation(workflowId, threadTs, page, perPage).body(localVarReturnType);
    }

    /**
     * List session capsules
     * Lists the capsules of a session that have not expired, newest first.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the session
     * @param threadTs Return only capsules taken at this checkpoint thread
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAiDuoWorkflowsSessionCapsule&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAiDuoWorkflowsSessionCapsule>> getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesWithHttpInfo(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nullable String threadTs, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAiDuoWorkflowsSessionCapsule>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesRequestCreation(workflowId, threadTs, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List session capsules
     * Lists the capsules of a session that have not expired, newest first.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the session
     * @param threadTs Return only capsules taken at this checkpoint thread
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesWithResponseSpec(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nullable String threadTs, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesRequestCreation(workflowId, threadTs, page, perPage);
    }

    /**
     * Download a session capsule
     * Downloads the capsule tar file. Expired capsules are not found.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the session
     * @param capsuleId The ID of the capsule, or &#x60;latest&#x60; for the newest capsule
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesCapsuleIdDownloadRequestCreation(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nonnull String capsuleId) throws RestClientResponseException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new RestClientResponseException("Missing the required parameter 'workflowId' when calling getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesCapsuleIdDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'capsuleId' is set
        if (capsuleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'capsuleId' when calling getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesCapsuleIdDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("workflow_id", workflowId);
        pathParams.put("capsule_id", capsuleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/x-tar"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{workflow_id}/capsules/{capsule_id}/download", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a session capsule
     * Downloads the capsule tar file. Expired capsules are not found.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the session
     * @param capsuleId The ID of the capsule, or &#x60;latest&#x60; for the newest capsule
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesCapsuleIdDownload(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nonnull String capsuleId) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesCapsuleIdDownloadRequestCreation(workflowId, capsuleId).body(localVarReturnType);
    }

    /**
     * Download a session capsule
     * Downloads the capsule tar file. Expired capsules are not found.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the session
     * @param capsuleId The ID of the capsule, or &#x60;latest&#x60; for the newest capsule
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesCapsuleIdDownloadWithHttpInfo(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nonnull String capsuleId) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesCapsuleIdDownloadRequestCreation(workflowId, capsuleId).toEntity(localVarReturnType);
    }

    /**
     * Download a session capsule
     * Downloads the capsule tar file. Expired capsules are not found.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the session
     * @param capsuleId The ID of the capsule, or &#x60;latest&#x60; for the newest capsule
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesCapsuleIdDownloadWithResponseSpec(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nonnull String capsuleId) throws RestClientResponseException {
        return getApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesCapsuleIdDownloadRequestCreation(workflowId, capsuleId);
    }

    /**
     * Get a session file with its contents
     * Returns a file the user attached to the session, with its contents base64 encoded.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @param fileId ID of the session file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileIdRequestCreation(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nonnull Integer fileId) throws RestClientResponseException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new RestClientResponseException("Missing the required parameter 'workflowId' when calling getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileId' is set
        if (fileId == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileId' when calling getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("workflow_id", workflowId);
        pathParams.put("file_id", fileId);

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
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{workflow_id}/session_files/{file_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a session file with its contents
     * Returns a file the user attached to the session, with its contents base64 encoded.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @param fileId ID of the session file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileId(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nonnull Integer fileId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileIdRequestCreation(workflowId, fileId).body(localVarReturnType);
    }

    /**
     * Get a session file with its contents
     * Returns a file the user attached to the session, with its contents base64 encoded.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @param fileId ID of the session file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileIdWithHttpInfo(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nonnull Integer fileId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileIdRequestCreation(workflowId, fileId).toEntity(localVarReturnType);
    }

    /**
     * Get a session file with its contents
     * Returns a file the user attached to the session, with its contents base64 encoded.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @param fileId ID of the session file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileIdWithResponseSpec(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nonnull Integer fileId) throws RestClientResponseException {
        return getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileIdRequestCreation(workflowId, fileId);
    }

    /**
     * Download a session file
     * Returns the raw contents of a file the user attached to the session.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @param fileId ID of the session file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileIdRawRequestCreation(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nonnull Integer fileId) throws RestClientResponseException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new RestClientResponseException("Missing the required parameter 'workflowId' when calling getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileIdRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileId' is set
        if (fileId == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileId' when calling getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileIdRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("workflow_id", workflowId);
        pathParams.put("file_id", fileId);

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
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{workflow_id}/session_files/{file_id}/raw", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a session file
     * Returns the raw contents of a file the user attached to the session.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @param fileId ID of the session file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileIdRaw(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nonnull Integer fileId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileIdRawRequestCreation(workflowId, fileId).body(localVarReturnType);
    }

    /**
     * Download a session file
     * Returns the raw contents of a file the user attached to the session.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @param fileId ID of the session file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileIdRawWithHttpInfo(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nonnull Integer fileId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileIdRawRequestCreation(workflowId, fileId).toEntity(localVarReturnType);
    }

    /**
     * Download a session file
     * Returns the raw contents of a file the user attached to the session.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @param fileId ID of the session file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileIdRawWithResponseSpec(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nonnull Integer fileId) throws RestClientResponseException {
        return getApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesFileIdRawRequestCreation(workflowId, fileId);
    }

    /**
     * Get workflow trace as JSONL
     * Returns the trace of a workflow session as JSON Lines (JSONL), with the &#x60;application/x-ndjson&#x60; content type. The first line holds the session goal, if set. The other lines are entries from the &#x60;ui_chat_log&#x60;, &#x60;plan&#x60;, and &#x60;status&#x60; channels. Each line is a JSON object with a &#x60;channel&#x60; key. A &#x60;ui_chat_log&#x60; line also carries the fields of the entry, such as &#x60;message_type&#x60;, &#x60;content&#x60;, and &#x60;status&#x60;. For workflows that use incremental checkpoint storage, the default trace covers all threads of the session, including messages from before any context compaction. Returns &#x60;403 Forbidden&#x60; if the user must complete identity verification before using GitLab Duo Agent Platform.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @param full If &#x60;true&#x60;, includes internal channels such as conversation history and handover. Restricted to the workflow owner. Non-owners receive a &#x60;403&#x60;.
     * @param thread Thread to return. Omit for the full trace across all threads. Use &#x60;latest&#x60; for the most recent thread, or a thread ID for a specific thread. Has no effect on workflows that do not use incremental checkpoint storage.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AiDuoWorkflowsWorkflowsWorkflowIdTraceJsonlRequestCreation(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable String thread) throws RestClientResponseException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new RestClientResponseException("Missing the required parameter 'workflowId' when calling getApiV4AiDuoWorkflowsWorkflowsWorkflowIdTraceJsonl", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("workflow_id", workflowId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "full", full));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "thread", thread));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{workflow_id}/trace.jsonl", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get workflow trace as JSONL
     * Returns the trace of a workflow session as JSON Lines (JSONL), with the &#x60;application/x-ndjson&#x60; content type. The first line holds the session goal, if set. The other lines are entries from the &#x60;ui_chat_log&#x60;, &#x60;plan&#x60;, and &#x60;status&#x60; channels. Each line is a JSON object with a &#x60;channel&#x60; key. A &#x60;ui_chat_log&#x60; line also carries the fields of the entry, such as &#x60;message_type&#x60;, &#x60;content&#x60;, and &#x60;status&#x60;. For workflows that use incremental checkpoint storage, the default trace covers all threads of the session, including messages from before any context compaction. Returns &#x60;403 Forbidden&#x60; if the user must complete identity verification before using GitLab Duo Agent Platform.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @param full If &#x60;true&#x60;, includes internal channels such as conversation history and handover. Restricted to the workflow owner. Non-owners receive a &#x60;403&#x60;.
     * @param thread Thread to return. Omit for the full trace across all threads. Use &#x60;latest&#x60; for the most recent thread, or a thread ID for a specific thread. Has no effect on workflows that do not use incremental checkpoint storage.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4AiDuoWorkflowsWorkflowsWorkflowIdTraceJsonl(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable String thread) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4AiDuoWorkflowsWorkflowsWorkflowIdTraceJsonlRequestCreation(workflowId, full, thread).body(localVarReturnType);
    }

    /**
     * Get workflow trace as JSONL
     * Returns the trace of a workflow session as JSON Lines (JSONL), with the &#x60;application/x-ndjson&#x60; content type. The first line holds the session goal, if set. The other lines are entries from the &#x60;ui_chat_log&#x60;, &#x60;plan&#x60;, and &#x60;status&#x60; channels. Each line is a JSON object with a &#x60;channel&#x60; key. A &#x60;ui_chat_log&#x60; line also carries the fields of the entry, such as &#x60;message_type&#x60;, &#x60;content&#x60;, and &#x60;status&#x60;. For workflows that use incremental checkpoint storage, the default trace covers all threads of the session, including messages from before any context compaction. Returns &#x60;403 Forbidden&#x60; if the user must complete identity verification before using GitLab Duo Agent Platform.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @param full If &#x60;true&#x60;, includes internal channels such as conversation history and handover. Restricted to the workflow owner. Non-owners receive a &#x60;403&#x60;.
     * @param thread Thread to return. Omit for the full trace across all threads. Use &#x60;latest&#x60; for the most recent thread, or a thread ID for a specific thread. Has no effect on workflows that do not use incremental checkpoint storage.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4AiDuoWorkflowsWorkflowsWorkflowIdTraceJsonlWithHttpInfo(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable String thread) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWorkflowsWorkflowIdTraceJsonlRequestCreation(workflowId, full, thread).toEntity(localVarReturnType);
    }

    /**
     * Get workflow trace as JSONL
     * Returns the trace of a workflow session as JSON Lines (JSONL), with the &#x60;application/x-ndjson&#x60; content type. The first line holds the session goal, if set. The other lines are entries from the &#x60;ui_chat_log&#x60;, &#x60;plan&#x60;, and &#x60;status&#x60; channels. Each line is a JSON object with a &#x60;channel&#x60; key. A &#x60;ui_chat_log&#x60; line also carries the fields of the entry, such as &#x60;message_type&#x60;, &#x60;content&#x60;, and &#x60;status&#x60;. For workflows that use incremental checkpoint storage, the default trace covers all threads of the session, including messages from before any context compaction. Returns &#x60;403 Forbidden&#x60; if the user must complete identity verification before using GitLab Duo Agent Platform.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @param full If &#x60;true&#x60;, includes internal channels such as conversation history and handover. Restricted to the workflow owner. Non-owners receive a &#x60;403&#x60;.
     * @param thread Thread to return. Omit for the full trace across all threads. Use &#x60;latest&#x60; for the most recent thread, or a thread ID for a specific thread. Has no effect on workflows that do not use incremental checkpoint storage.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AiDuoWorkflowsWorkflowsWorkflowIdTraceJsonlWithResponseSpec(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable String thread) throws RestClientResponseException {
        return getApiV4AiDuoWorkflowsWorkflowsWorkflowIdTraceJsonlRequestCreation(workflowId, full, thread);
    }

    /**
     * Get Duo Workflows WebSocket connection details
     * Retrieves WebSocket connection information to establish a session with Duo Workflows.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the existing workflow. When provided and the workflow has a stored model selection, that selection is reused to ensure provider stickiness.
     * @param environment Environment the session runs in. Used to resolve tool governance when the client connects before sending workflow_id. Only &#x60;ide&#x60;, &#x60;chat&#x60;, and &#x60;chat_partial&#x60; affect tool governance. Any other value falls back to &#x60;web&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AiDuoWorkflowsWsRequestCreation(@jakarta.annotation.Nullable String workflowId, @jakarta.annotation.Nullable String environment) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflow_id", workflowId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "environment", environment));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/ws", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get Duo Workflows WebSocket connection details
     * Retrieves WebSocket connection information to establish a session with Duo Workflows.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the existing workflow. When provided and the workflow has a stored model selection, that selection is reused to ensure provider stickiness.
     * @param environment Environment the session runs in. Used to resolve tool governance when the client connects before sending workflow_id. Only &#x60;ide&#x60;, &#x60;chat&#x60;, and &#x60;chat_partial&#x60; affect tool governance. Any other value falls back to &#x60;web&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4AiDuoWorkflowsWs(@jakarta.annotation.Nullable String workflowId, @jakarta.annotation.Nullable String environment) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4AiDuoWorkflowsWsRequestCreation(workflowId, environment).body(localVarReturnType);
    }

    /**
     * Get Duo Workflows WebSocket connection details
     * Retrieves WebSocket connection information to establish a session with Duo Workflows.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the existing workflow. When provided and the workflow has a stored model selection, that selection is reused to ensure provider stickiness.
     * @param environment Environment the session runs in. Used to resolve tool governance when the client connects before sending workflow_id. Only &#x60;ide&#x60;, &#x60;chat&#x60;, and &#x60;chat_partial&#x60; affect tool governance. Any other value falls back to &#x60;web&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4AiDuoWorkflowsWsWithHttpInfo(@jakarta.annotation.Nullable String workflowId, @jakarta.annotation.Nullable String environment) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AiDuoWorkflowsWsRequestCreation(workflowId, environment).toEntity(localVarReturnType);
    }

    /**
     * Get Duo Workflows WebSocket connection details
     * Retrieves WebSocket connection information to establish a session with Duo Workflows.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the existing workflow. When provided and the workflow has a stored model selection, that selection is reused to ensure provider stickiness.
     * @param environment Environment the session runs in. Used to resolve tool governance when the client connects before sending workflow_id. Only &#x60;ide&#x60;, &#x60;chat&#x60;, and &#x60;chat_partial&#x60; affect tool governance. Any other value falls back to &#x60;web&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AiDuoWorkflowsWsWithResponseSpec(@jakarta.annotation.Nullable String workflowId, @jakarta.annotation.Nullable String environment) throws RestClientResponseException {
        return getApiV4AiDuoWorkflowsWsRequestCreation(workflowId, environment);
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
     * Create workflow persistence (agent-initiated, limited privileges)
     * Accessible via ai_workflows scope token. Does not accept agent_privileges or pre_approved_agent_privileges.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param postApiV4AiDuoWorkflowsAgentWorkflowsRequest The postApiV4AiDuoWorkflowsAgentWorkflowsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsAgentWorkflowsRequestCreation(@jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsAgentWorkflowsRequest postApiV4AiDuoWorkflowsAgentWorkflowsRequest) throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/agent_workflows", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AiDuoWorkflowsAgentWorkflowsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create workflow persistence (agent-initiated, limited privileges)
     * Accessible via ai_workflows scope token. Does not accept agent_privileges or pre_approved_agent_privileges.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param postApiV4AiDuoWorkflowsAgentWorkflowsRequest The postApiV4AiDuoWorkflowsAgentWorkflowsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AiDuoWorkflowsAgentWorkflows(@jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsAgentWorkflowsRequest postApiV4AiDuoWorkflowsAgentWorkflowsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AiDuoWorkflowsAgentWorkflowsRequestCreation(postApiV4AiDuoWorkflowsAgentWorkflowsRequest).body(localVarReturnType);
    }

    /**
     * Create workflow persistence (agent-initiated, limited privileges)
     * Accessible via ai_workflows scope token. Does not accept agent_privileges or pre_approved_agent_privileges.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param postApiV4AiDuoWorkflowsAgentWorkflowsRequest The postApiV4AiDuoWorkflowsAgentWorkflowsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AiDuoWorkflowsAgentWorkflowsWithHttpInfo(@jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsAgentWorkflowsRequest postApiV4AiDuoWorkflowsAgentWorkflowsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsAgentWorkflowsRequestCreation(postApiV4AiDuoWorkflowsAgentWorkflowsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create workflow persistence (agent-initiated, limited privileges)
     * Accessible via ai_workflows scope token. Does not accept agent_privileges or pre_approved_agent_privileges.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param postApiV4AiDuoWorkflowsAgentWorkflowsRequest The postApiV4AiDuoWorkflowsAgentWorkflowsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsAgentWorkflowsWithResponseSpec(@jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsAgentWorkflowsRequest postApiV4AiDuoWorkflowsAgentWorkflowsRequest) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsAgentWorkflowsRequestCreation(postApiV4AiDuoWorkflowsAgentWorkflowsRequest);
    }

    /**
     * Add code review comments to a merge request
     * Adds review comments generated by Duo Workflows to a merge request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest The postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequestCreation(@jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest' is set
        if (postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest' when calling postApiV4AiDuoWorkflowsCodeReviewAddComments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/code_review/add_comments", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add code review comments to a merge request
     * Adds review comments generated by Duo Workflows to a merge request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest The postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AiDuoWorkflowsCodeReviewAddComments(@jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequestCreation(postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest).body(localVarReturnType);
    }

    /**
     * Add code review comments to a merge request
     * Adds review comments generated by Duo Workflows to a merge request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest The postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AiDuoWorkflowsCodeReviewAddCommentsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequestCreation(postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest).toEntity(localVarReturnType);
    }

    /**
     * Add code review comments to a merge request
     * Adds review comments generated by Duo Workflows to a merge request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest The postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsCodeReviewAddCommentsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequestCreation(postApiV4AiDuoWorkflowsCodeReviewAddCommentsRequest);
    }

    /**
     * Connection details for accessing Duo Agent Platform Service directly
     * Returns connection details for direct access to the Duo Agent Platform Service.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4AiDuoWorkflowsDirectAccessRequest The postApiV4AiDuoWorkflowsDirectAccessRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsDirectAccessRequestCreation(@jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsDirectAccessRequest postApiV4AiDuoWorkflowsDirectAccessRequest) throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/direct_access", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AiDuoWorkflowsDirectAccessRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Connection details for accessing Duo Agent Platform Service directly
     * Returns connection details for direct access to the Duo Agent Platform Service.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4AiDuoWorkflowsDirectAccessRequest The postApiV4AiDuoWorkflowsDirectAccessRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AiDuoWorkflowsDirectAccess(@jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsDirectAccessRequest postApiV4AiDuoWorkflowsDirectAccessRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AiDuoWorkflowsDirectAccessRequestCreation(postApiV4AiDuoWorkflowsDirectAccessRequest).body(localVarReturnType);
    }

    /**
     * Connection details for accessing Duo Agent Platform Service directly
     * Returns connection details for direct access to the Duo Agent Platform Service.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4AiDuoWorkflowsDirectAccessRequest The postApiV4AiDuoWorkflowsDirectAccessRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AiDuoWorkflowsDirectAccessWithHttpInfo(@jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsDirectAccessRequest postApiV4AiDuoWorkflowsDirectAccessRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsDirectAccessRequestCreation(postApiV4AiDuoWorkflowsDirectAccessRequest).toEntity(localVarReturnType);
    }

    /**
     * Connection details for accessing Duo Agent Platform Service directly
     * Returns connection details for direct access to the Duo Agent Platform Service.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4AiDuoWorkflowsDirectAccessRequest The postApiV4AiDuoWorkflowsDirectAccessRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsDirectAccessWithResponseSpec(@jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsDirectAccessRequest postApiV4AiDuoWorkflowsDirectAccessRequest) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsDirectAccessRequestCreation(postApiV4AiDuoWorkflowsDirectAccessRequest);
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
     * Submit risk assessment results for a merge request
     * Submits the risk level and reason for each area produced by the risk assessment flow.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest The postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequestCreation(@jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest' is set
        if (postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest' when calling postApiV4AiDuoWorkflowsToolsRiskAssessmentResults", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/tools/risk_assessment/results", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Submit risk assessment results for a merge request
     * Submits the risk level and reason for each area produced by the risk assessment flow.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest The postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AiDuoWorkflowsToolsRiskAssessmentResults(@jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequestCreation(postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest).body(localVarReturnType);
    }

    /**
     * Submit risk assessment results for a merge request
     * Submits the risk level and reason for each area produced by the risk assessment flow.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest The postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequestCreation(postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest).toEntity(localVarReturnType);
    }

    /**
     * Submit risk assessment results for a merge request
     * Submits the risk level and reason for each area produced by the risk assessment flow.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest The postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequestCreation(postApiV4AiDuoWorkflowsToolsRiskAssessmentResultsRequest);
    }

    /**
     * Create a flow
     * Creates and starts a flow. Returns &#x60;403 Forbidden&#x60; if the user must complete identity verification before using GitLab Duo Agent Platform. If &#x60;start_workflow&#x60; is &#x60;true&#x60; and the project has an empty repository, returns &#x60;422 Unprocessable entity&#x60; with &#x60;reason&#x60; set to &#x60;repository_empty&#x60;. If &#x60;start_workflow&#x60; is &#x60;true&#x60;, the flow must have a composite identity service account, either configured for the flow in the AI Catalog or behind the request&#39;s composite identity. This requirement is behind a feature flag named &#x60;enforce_composite_identity_for_api_started_workflows&#x60;, disabled by default.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4AiDuoWorkflowsWorkflowsRequest The postApiV4AiDuoWorkflowsWorkflowsRequest parameter
     * @return APIEntitiesAiDuoWorkflowsWorkflow
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsWorkflowsRequestCreation(@jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsWorkflowsRequest postApiV4AiDuoWorkflowsWorkflowsRequest) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsWorkflow> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AiDuoWorkflowsWorkflowsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a flow
     * Creates and starts a flow. Returns &#x60;403 Forbidden&#x60; if the user must complete identity verification before using GitLab Duo Agent Platform. If &#x60;start_workflow&#x60; is &#x60;true&#x60; and the project has an empty repository, returns &#x60;422 Unprocessable entity&#x60; with &#x60;reason&#x60; set to &#x60;repository_empty&#x60;. If &#x60;start_workflow&#x60; is &#x60;true&#x60;, the flow must have a composite identity service account, either configured for the flow in the AI Catalog or behind the request&#39;s composite identity. This requirement is behind a feature flag named &#x60;enforce_composite_identity_for_api_started_workflows&#x60;, disabled by default.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4AiDuoWorkflowsWorkflowsRequest The postApiV4AiDuoWorkflowsWorkflowsRequest parameter
     * @return APIEntitiesAiDuoWorkflowsWorkflow
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiDuoWorkflowsWorkflow postApiV4AiDuoWorkflowsWorkflows(@jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsWorkflowsRequest postApiV4AiDuoWorkflowsWorkflowsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsWorkflow> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsRequestCreation(postApiV4AiDuoWorkflowsWorkflowsRequest).body(localVarReturnType);
    }

    /**
     * Create a flow
     * Creates and starts a flow. Returns &#x60;403 Forbidden&#x60; if the user must complete identity verification before using GitLab Duo Agent Platform. If &#x60;start_workflow&#x60; is &#x60;true&#x60; and the project has an empty repository, returns &#x60;422 Unprocessable entity&#x60; with &#x60;reason&#x60; set to &#x60;repository_empty&#x60;. If &#x60;start_workflow&#x60; is &#x60;true&#x60;, the flow must have a composite identity service account, either configured for the flow in the AI Catalog or behind the request&#39;s composite identity. This requirement is behind a feature flag named &#x60;enforce_composite_identity_for_api_started_workflows&#x60;, disabled by default.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4AiDuoWorkflowsWorkflowsRequest The postApiV4AiDuoWorkflowsWorkflowsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAiDuoWorkflowsWorkflow&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiDuoWorkflowsWorkflow> postApiV4AiDuoWorkflowsWorkflowsWithHttpInfo(@jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsWorkflowsRequest postApiV4AiDuoWorkflowsWorkflowsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsWorkflow> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsRequestCreation(postApiV4AiDuoWorkflowsWorkflowsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a flow
     * Creates and starts a flow. Returns &#x60;403 Forbidden&#x60; if the user must complete identity verification before using GitLab Duo Agent Platform. If &#x60;start_workflow&#x60; is &#x60;true&#x60; and the project has an empty repository, returns &#x60;422 Unprocessable entity&#x60; with &#x60;reason&#x60; set to &#x60;repository_empty&#x60;. If &#x60;start_workflow&#x60; is &#x60;true&#x60;, the flow must have a composite identity service account, either configured for the flow in the AI Catalog or behind the request&#39;s composite identity. This requirement is behind a feature flag named &#x60;enforce_composite_identity_for_api_started_workflows&#x60;, disabled by default.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4AiDuoWorkflowsWorkflowsRequest The postApiV4AiDuoWorkflowsWorkflowsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsWorkflowsWithResponseSpec(@jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsWorkflowsRequest postApiV4AiDuoWorkflowsWorkflowsRequest) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsWorkflowsRequestCreation(postApiV4AiDuoWorkflowsWorkflowsRequest);
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
     * Upload a session capsule
     * Stores a tar of the session state that a CI job did not push, so the job that resumes the session can restore it.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param workflowId The ID of the session
     * @param _file The capsule tar file
     * @param threadTs The checkpoint thread the capsule was taken at
     * @return APIEntitiesAiDuoWorkflowsSessionCapsule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesRequestCreation(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String threadTs) throws RestClientResponseException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new RestClientResponseException("Missing the required parameter 'workflowId' when calling postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("workflow_id", workflowId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("file", new FileSystemResource(_file));
        if (threadTs != null) {
            formParams.add("thread_ts", threadTs);
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsSessionCapsule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{workflow_id}/capsules", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a session capsule
     * Stores a tar of the session state that a CI job did not push, so the job that resumes the session can restore it.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param workflowId The ID of the session
     * @param _file The capsule tar file
     * @param threadTs The checkpoint thread the capsule was taken at
     * @return APIEntitiesAiDuoWorkflowsSessionCapsule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiDuoWorkflowsSessionCapsule postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsules(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String threadTs) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsSessionCapsule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesRequestCreation(workflowId, _file, threadTs).body(localVarReturnType);
    }

    /**
     * Upload a session capsule
     * Stores a tar of the session state that a CI job did not push, so the job that resumes the session can restore it.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param workflowId The ID of the session
     * @param _file The capsule tar file
     * @param threadTs The checkpoint thread the capsule was taken at
     * @return ResponseEntity&lt;APIEntitiesAiDuoWorkflowsSessionCapsule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiDuoWorkflowsSessionCapsule> postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesWithHttpInfo(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String threadTs) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiDuoWorkflowsSessionCapsule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesRequestCreation(workflowId, _file, threadTs).toEntity(localVarReturnType);
    }

    /**
     * Upload a session capsule
     * Stores a tar of the session state that a CI job did not push, so the job that resumes the session can restore it.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param workflowId The ID of the session
     * @param _file The capsule tar file
     * @param threadTs The checkpoint thread the capsule was taken at
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesWithResponseSpec(@jakarta.annotation.Nonnull Integer workflowId, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String threadTs) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesRequestCreation(workflowId, _file, threadTs);
    }

    /**
     * Authorize a session capsule upload
     * Workhorse calls this endpoint before it accepts the capsule file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the session
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesAuthorizeRequestCreation(@jakarta.annotation.Nonnull Integer workflowId) throws RestClientResponseException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new RestClientResponseException("Missing the required parameter 'workflowId' when calling postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("workflow_id", workflowId);

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
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{workflow_id}/capsules/authorize", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Authorize a session capsule upload
     * Workhorse calls this endpoint before it accepts the capsule file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the session
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesAuthorize(@jakarta.annotation.Nonnull Integer workflowId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesAuthorizeRequestCreation(workflowId).body(localVarReturnType);
    }

    /**
     * Authorize a session capsule upload
     * Workhorse calls this endpoint before it accepts the capsule file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the session
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull Integer workflowId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesAuthorizeRequestCreation(workflowId).toEntity(localVarReturnType);
    }

    /**
     * Authorize a session capsule upload
     * Workhorse calls this endpoint before it accepts the capsule file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the session
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull Integer workflowId) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsWorkflowsWorkflowIdCapsulesAuthorizeRequestCreation(workflowId);
    }

    /**
     * Request Duo Workflows server-side execution connection details
     * Workhorse pre-authorization endpoint. Returns the connection details Workhorse needs to run the flow itself over gRPC and stream the actions it receives back to the caller, for callers that cannot execute actions of their own. Requests must arrive through Workhorse.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the workflow to execute
     * @param postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest The postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequestCreation(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest) throws RestClientResponseException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new RestClientResponseException("Missing the required parameter 'workflowId' when calling postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecute", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("workflow_id", workflowId);

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
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{workflow_id}/execute", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Request Duo Workflows server-side execution connection details
     * Workhorse pre-authorization endpoint. Returns the connection details Workhorse needs to run the flow itself over gRPC and stream the actions it receives back to the caller, for callers that cannot execute actions of their own. Requests must arrive through Workhorse.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the workflow to execute
     * @param postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest The postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecute(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequestCreation(workflowId, postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest).body(localVarReturnType);
    }

    /**
     * Request Duo Workflows server-side execution connection details
     * Workhorse pre-authorization endpoint. Returns the connection details Workhorse needs to run the flow itself over gRPC and stream the actions it receives back to the caller, for callers that cannot execute actions of their own. Requests must arrive through Workhorse.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the workflow to execute
     * @param postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest The postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteWithHttpInfo(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequestCreation(workflowId, postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest).toEntity(localVarReturnType);
    }

    /**
     * Request Duo Workflows server-side execution connection details
     * Workhorse pre-authorization endpoint. Returns the connection details Workhorse needs to run the flow itself over gRPC and stream the actions it receives back to the caller, for callers that cannot execute actions of their own. Requests must arrive through Workhorse.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param workflowId The ID of the workflow to execute
     * @param postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest The postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteWithResponseSpec(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nullable PostApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequestCreation(workflowId, postApiV4AiDuoWorkflowsWorkflowsWorkflowIdExecuteRequest);
    }

    /**
     * Restart a failed or stopped workflow as a new session
     * Creates and starts a new session with the flow, goal, noteable, source, privileges and trigger of the given workflow. The new session runs from the beginning. The original workflow is not modified and keeps its &#x60;failed&#x60; or &#x60;stopped&#x60; status. Only project-level workflows with a &#x60;failed&#x60; or &#x60;stopped&#x60; status can be restarted. Other workflows return &#x60;403 Forbidden&#x60;. Restarting a workflow while a restart of it is in progress returns &#x60;409 Conflict&#x60;. The response describes the new session, so &#x60;id&#x60; is the ID of the new session.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param workflowId The ID of the workflow to restart
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsWorkflowsWorkflowIdRestartRequestCreation(@jakarta.annotation.Nonnull String workflowId) throws RestClientResponseException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new RestClientResponseException("Missing the required parameter 'workflowId' when calling postApiV4AiDuoWorkflowsWorkflowsWorkflowIdRestart", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("workflow_id", workflowId);

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
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{workflow_id}/restart", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Restart a failed or stopped workflow as a new session
     * Creates and starts a new session with the flow, goal, noteable, source, privileges and trigger of the given workflow. The new session runs from the beginning. The original workflow is not modified and keeps its &#x60;failed&#x60; or &#x60;stopped&#x60; status. Only project-level workflows with a &#x60;failed&#x60; or &#x60;stopped&#x60; status can be restarted. Other workflows return &#x60;403 Forbidden&#x60;. Restarting a workflow while a restart of it is in progress returns &#x60;409 Conflict&#x60;. The response describes the new session, so &#x60;id&#x60; is the ID of the new session.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param workflowId The ID of the workflow to restart
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AiDuoWorkflowsWorkflowsWorkflowIdRestart(@jakarta.annotation.Nonnull String workflowId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AiDuoWorkflowsWorkflowsWorkflowIdRestartRequestCreation(workflowId).body(localVarReturnType);
    }

    /**
     * Restart a failed or stopped workflow as a new session
     * Creates and starts a new session with the flow, goal, noteable, source, privileges and trigger of the given workflow. The new session runs from the beginning. The original workflow is not modified and keeps its &#x60;failed&#x60; or &#x60;stopped&#x60; status. Only project-level workflows with a &#x60;failed&#x60; or &#x60;stopped&#x60; status can be restarted. Other workflows return &#x60;403 Forbidden&#x60;. Restarting a workflow while a restart of it is in progress returns &#x60;409 Conflict&#x60;. The response describes the new session, so &#x60;id&#x60; is the ID of the new session.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param workflowId The ID of the workflow to restart
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AiDuoWorkflowsWorkflowsWorkflowIdRestartWithHttpInfo(@jakarta.annotation.Nonnull String workflowId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsWorkflowIdRestartRequestCreation(workflowId).toEntity(localVarReturnType);
    }

    /**
     * Restart a failed or stopped workflow as a new session
     * Creates and starts a new session with the flow, goal, noteable, source, privileges and trigger of the given workflow. The new session runs from the beginning. The original workflow is not modified and keeps its &#x60;failed&#x60; or &#x60;stopped&#x60; status. Only project-level workflows with a &#x60;failed&#x60; or &#x60;stopped&#x60; status can be restarted. Other workflows return &#x60;403 Forbidden&#x60;. Restarting a workflow while a restart of it is in progress returns &#x60;409 Conflict&#x60;. The response describes the new session, so &#x60;id&#x60; is the ID of the new session.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param workflowId The ID of the workflow to restart
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsWorkflowsWorkflowIdRestartWithResponseSpec(@jakarta.annotation.Nonnull String workflowId) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsWorkflowsWorkflowIdRestartRequestCreation(workflowId);
    }

    /**
     * Resume a paused workflow
     * Resumes execution of a previously paused workflow.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param workflowId ID of the workflow to resume.
     * @param postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest The postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequestCreation(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest) throws RestClientResponseException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new RestClientResponseException("Missing the required parameter 'workflowId' when calling postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResume", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest' is set
        if (postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest' when calling postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResume", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("workflow_id", workflowId);

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
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{workflow_id}/resume", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Resume a paused workflow
     * Resumes execution of a previously paused workflow.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param workflowId ID of the workflow to resume.
     * @param postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest The postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResume(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequestCreation(workflowId, postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest).body(localVarReturnType);
    }

    /**
     * Resume a paused workflow
     * Resumes execution of a previously paused workflow.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param workflowId ID of the workflow to resume.
     * @param postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest The postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeWithHttpInfo(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequestCreation(workflowId, postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest).toEntity(localVarReturnType);
    }

    /**
     * Resume a paused workflow
     * Resumes execution of a previously paused workflow.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param workflowId ID of the workflow to resume.
     * @param postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest The postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeWithResponseSpec(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nonnull PostApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequestCreation(workflowId, postApiV4AiDuoWorkflowsWorkflowsWorkflowIdResumeRequest);
    }

    /**
     * Upload a session file
     * Uploads an image the user attached to the session.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param workflowId ID of the workflow.
     * @param _file The image file to upload
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesRequestCreation(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new RestClientResponseException("Missing the required parameter 'workflowId' when calling postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("workflow_id", workflowId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{workflow_id}/session_files", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a session file
     * Uploads an image the user attached to the session.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param workflowId ID of the workflow.
     * @param _file The image file to upload
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFiles(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesRequestCreation(workflowId, _file).body(localVarReturnType);
    }

    /**
     * Upload a session file
     * Uploads an image the user attached to the session.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param workflowId ID of the workflow.
     * @param _file The image file to upload
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesWithHttpInfo(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesRequestCreation(workflowId, _file).toEntity(localVarReturnType);
    }

    /**
     * Upload a session file
     * Uploads an image the user attached to the session.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation failed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param workflowId ID of the workflow.
     * @param _file The image file to upload
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesWithResponseSpec(@jakarta.annotation.Nonnull String workflowId, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesRequestCreation(workflowId, _file);
    }

    /**
     * Workhorse authorize a session file upload
     * Workhorse pre-authorization endpoint for uploading a session file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesAuthorizeRequestCreation(@jakarta.annotation.Nonnull String workflowId) throws RestClientResponseException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new RestClientResponseException("Missing the required parameter 'workflowId' when calling postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("workflow_id", workflowId);

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
        return apiClient.invokeAPI("/api/v4/ai/duo_workflows/workflows/{workflow_id}/session_files/authorize", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize a session file upload
     * Workhorse pre-authorization endpoint for uploading a session file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesAuthorize(@jakarta.annotation.Nonnull String workflowId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesAuthorizeRequestCreation(workflowId).body(localVarReturnType);
    }

    /**
     * Workhorse authorize a session file upload
     * Workhorse pre-authorization endpoint for uploading a session file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull String workflowId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesAuthorizeRequestCreation(workflowId).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize a session file upload
     * Workhorse pre-authorization endpoint for uploading a session file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param workflowId ID of the workflow.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull String workflowId) throws RestClientResponseException {
        return postApiV4AiDuoWorkflowsWorkflowsWorkflowIdSessionFilesAuthorizeRequestCreation(workflowId);
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
