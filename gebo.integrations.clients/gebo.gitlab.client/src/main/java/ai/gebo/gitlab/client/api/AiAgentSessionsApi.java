package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAiExternalAgentsSession;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdAiAgentSessionsSessionIdRequest;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdAiAgentSessionsRequest;

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
public class AiAgentSessionsApi {
    private ApiClient apiClient;

    public AiAgentSessionsApi() {
        this(new ApiClient());
    }

    public AiAgentSessionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List external agent sessions for a project.
     * Returns a paginated list of external agent sessions for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentType Filter by agent type.
     * @param status Filter by status.
     * @param createdAfter Filter sessions created after this time.
     * @param createdBefore Filter sessions created before this time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAiExternalAgentsSession&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdAiAgentSessionsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String agentType, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdAiAgentSessions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "agent_type", agentType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesAiExternalAgentsSession>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ai_agent/sessions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List external agent sessions for a project.
     * Returns a paginated list of external agent sessions for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentType Filter by agent type.
     * @param status Filter by status.
     * @param createdAfter Filter sessions created after this time.
     * @param createdBefore Filter sessions created before this time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAiExternalAgentsSession&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAiExternalAgentsSession> getApiV4ProjectsIdAiAgentSessions(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String agentType, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAiExternalAgentsSession>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAiAgentSessionsRequestCreation(id, agentType, status, createdAfter, createdBefore, page, perPage).body(localVarReturnType);
    }

    /**
     * List external agent sessions for a project.
     * Returns a paginated list of external agent sessions for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentType Filter by agent type.
     * @param status Filter by status.
     * @param createdAfter Filter sessions created after this time.
     * @param createdBefore Filter sessions created before this time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAiExternalAgentsSession&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAiExternalAgentsSession>> getApiV4ProjectsIdAiAgentSessionsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String agentType, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAiExternalAgentsSession>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAiAgentSessionsRequestCreation(id, agentType, status, createdAfter, createdBefore, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List external agent sessions for a project.
     * Returns a paginated list of external agent sessions for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentType Filter by agent type.
     * @param status Filter by status.
     * @param createdAfter Filter sessions created after this time.
     * @param createdBefore Filter sessions created before this time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdAiAgentSessionsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String agentType, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdAiAgentSessionsRequestCreation(id, agentType, status, createdAfter, createdBefore, page, perPage);
    }

    /**
     * Complete or fail an external agent session.
     * Marks a session as completed or failed. The authenticated user must be the creator of the session.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param sessionId The session ID.
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest The patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest parameter
     * @return APIEntitiesAiExternalAgentsSession
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4ProjectsIdAiAgentSessionsSessionIdRequestCreation(@jakarta.annotation.Nonnull Integer sessionId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdAiAgentSessionsSessionIdRequest patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest) throws RestClientResponseException {
        // verify the required parameter 'sessionId' is set
        if (sessionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'sessionId' when calling patchApiV4ProjectsIdAiAgentSessionsSessionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4ProjectsIdAiAgentSessionsSessionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest' is set
        if (patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest' when calling patchApiV4ProjectsIdAiAgentSessionsSessionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("session_id", sessionId);
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

        ParameterizedTypeReference<APIEntitiesAiExternalAgentsSession> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ai_agent/sessions/{session_id}", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Complete or fail an external agent session.
     * Marks a session as completed or failed. The authenticated user must be the creator of the session.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param sessionId The session ID.
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest The patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest parameter
     * @return APIEntitiesAiExternalAgentsSession
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiExternalAgentsSession patchApiV4ProjectsIdAiAgentSessionsSessionId(@jakarta.annotation.Nonnull Integer sessionId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdAiAgentSessionsSessionIdRequest patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiExternalAgentsSession> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdAiAgentSessionsSessionIdRequestCreation(sessionId, id, patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest).body(localVarReturnType);
    }

    /**
     * Complete or fail an external agent session.
     * Marks a session as completed or failed. The authenticated user must be the creator of the session.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param sessionId The session ID.
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest The patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAiExternalAgentsSession&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiExternalAgentsSession> patchApiV4ProjectsIdAiAgentSessionsSessionIdWithHttpInfo(@jakarta.annotation.Nonnull Integer sessionId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdAiAgentSessionsSessionIdRequest patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiExternalAgentsSession> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdAiAgentSessionsSessionIdRequestCreation(sessionId, id, patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Complete or fail an external agent session.
     * Marks a session as completed or failed. The authenticated user must be the creator of the session.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param sessionId The session ID.
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest The patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4ProjectsIdAiAgentSessionsSessionIdWithResponseSpec(@jakarta.annotation.Nonnull Integer sessionId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdAiAgentSessionsSessionIdRequest patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest) throws RestClientResponseException {
        return patchApiV4ProjectsIdAiAgentSessionsSessionIdRequestCreation(sessionId, id, patchApiV4ProjectsIdAiAgentSessionsSessionIdRequest);
    }

    /**
     * Create an external agent session.
     * Creates a new external agent session for the current user and project. Returns an existing record if an idempotency_key is provided and a matching session already exists.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAiAgentSessionsRequest The postApiV4ProjectsIdAiAgentSessionsRequest parameter
     * @return APIEntitiesAiExternalAgentsSession
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdAiAgentSessionsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAiAgentSessionsRequest postApiV4ProjectsIdAiAgentSessionsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdAiAgentSessions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdAiAgentSessionsRequest' is set
        if (postApiV4ProjectsIdAiAgentSessionsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdAiAgentSessionsRequest' when calling postApiV4ProjectsIdAiAgentSessions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesAiExternalAgentsSession> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ai_agent/sessions", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdAiAgentSessionsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an external agent session.
     * Creates a new external agent session for the current user and project. Returns an existing record if an idempotency_key is provided and a matching session already exists.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAiAgentSessionsRequest The postApiV4ProjectsIdAiAgentSessionsRequest parameter
     * @return APIEntitiesAiExternalAgentsSession
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiExternalAgentsSession postApiV4ProjectsIdAiAgentSessions(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAiAgentSessionsRequest postApiV4ProjectsIdAiAgentSessionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiExternalAgentsSession> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAiAgentSessionsRequestCreation(id, postApiV4ProjectsIdAiAgentSessionsRequest).body(localVarReturnType);
    }

    /**
     * Create an external agent session.
     * Creates a new external agent session for the current user and project. Returns an existing record if an idempotency_key is provided and a matching session already exists.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAiAgentSessionsRequest The postApiV4ProjectsIdAiAgentSessionsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAiExternalAgentsSession&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiExternalAgentsSession> postApiV4ProjectsIdAiAgentSessionsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAiAgentSessionsRequest postApiV4ProjectsIdAiAgentSessionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiExternalAgentsSession> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAiAgentSessionsRequestCreation(id, postApiV4ProjectsIdAiAgentSessionsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an external agent session.
     * Creates a new external agent session for the current user and project. Returns an existing record if an idempotency_key is provided and a matching session already exists.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAiAgentSessionsRequest The postApiV4ProjectsIdAiAgentSessionsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdAiAgentSessionsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAiAgentSessionsRequest postApiV4ProjectsIdAiAgentSessionsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdAiAgentSessionsRequestCreation(id, postApiV4ProjectsIdAiAgentSessionsRequest);
    }
}
