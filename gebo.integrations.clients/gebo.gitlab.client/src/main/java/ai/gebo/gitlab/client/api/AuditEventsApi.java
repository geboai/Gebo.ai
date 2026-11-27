package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAuditEvent;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;

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
public class AuditEventsApi {
    private ApiClient apiClient;

    public AuditEventsApi() {
        this(new ApiClient());
    }

    public AuditEventsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all instance audit events
     * Lists all available instance audit events, limited to a maximum of 30 days for each query. Offset-based pagination was deprecated in GitLab 17.8 and is planned for removal in GitLab 20.0. Use keyset-based pagination instead. This change is a breaking change.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param entityType Return audit events for the specified entity type.
     * @param entityId Return audit events for the specified entity ID. Requires the &#x60;entity_type&#x60; attribute.
     * @param createdAfter Return audit events created on or after the specified time.
     * @param createdBefore Return audit events created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAuditEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AuditEventsRequestCreation(@jakarta.annotation.Nonnull String entityType, @jakarta.annotation.Nullable Integer entityId, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'entityType' is set
        if (entityType == null) {
            throw new RestClientResponseException("Missing the required parameter 'entityType' when calling getApiV4AuditEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "entity_type", entityType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "entity_id", entityId));
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

        ParameterizedTypeReference<List<APIEntitiesAuditEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/audit_events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all instance audit events
     * Lists all available instance audit events, limited to a maximum of 30 days for each query. Offset-based pagination was deprecated in GitLab 17.8 and is planned for removal in GitLab 20.0. Use keyset-based pagination instead. This change is a breaking change.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param entityType Return audit events for the specified entity type.
     * @param entityId Return audit events for the specified entity ID. Requires the &#x60;entity_type&#x60; attribute.
     * @param createdAfter Return audit events created on or after the specified time.
     * @param createdBefore Return audit events created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAuditEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAuditEvent> getApiV4AuditEvents(@jakarta.annotation.Nonnull String entityType, @jakarta.annotation.Nullable Integer entityId, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAuditEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AuditEventsRequestCreation(entityType, entityId, createdAfter, createdBefore, page, perPage).body(localVarReturnType);
    }

    /**
     * List all instance audit events
     * Lists all available instance audit events, limited to a maximum of 30 days for each query. Offset-based pagination was deprecated in GitLab 17.8 and is planned for removal in GitLab 20.0. Use keyset-based pagination instead. This change is a breaking change.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param entityType Return audit events for the specified entity type.
     * @param entityId Return audit events for the specified entity ID. Requires the &#x60;entity_type&#x60; attribute.
     * @param createdAfter Return audit events created on or after the specified time.
     * @param createdBefore Return audit events created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAuditEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAuditEvent>> getApiV4AuditEventsWithHttpInfo(@jakarta.annotation.Nonnull String entityType, @jakarta.annotation.Nullable Integer entityId, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAuditEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AuditEventsRequestCreation(entityType, entityId, createdAfter, createdBefore, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all instance audit events
     * Lists all available instance audit events, limited to a maximum of 30 days for each query. Offset-based pagination was deprecated in GitLab 17.8 and is planned for removal in GitLab 20.0. Use keyset-based pagination instead. This change is a breaking change.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param entityType Return audit events for the specified entity type.
     * @param entityId Return audit events for the specified entity ID. Requires the &#x60;entity_type&#x60; attribute.
     * @param createdAfter Return audit events created on or after the specified time.
     * @param createdBefore Return audit events created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AuditEventsWithResponseSpec(@jakarta.annotation.Nonnull String entityType, @jakarta.annotation.Nullable Integer entityId, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4AuditEventsRequestCreation(entityType, entityId, createdAfter, createdBefore, page, perPage);
    }

    /**
     * Retrieve an instance audit event
     * Retrieves a specified instance audit event.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the audit event.
     * @return APIEntitiesAuditEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AuditEventsIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4AuditEventsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesAuditEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/audit_events/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an instance audit event
     * Retrieves a specified instance audit event.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the audit event.
     * @return APIEntitiesAuditEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAuditEvent getApiV4AuditEventsId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAuditEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AuditEventsIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve an instance audit event
     * Retrieves a specified instance audit event.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the audit event.
     * @return ResponseEntity&lt;APIEntitiesAuditEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAuditEvent> getApiV4AuditEventsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAuditEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AuditEventsIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an instance audit event
     * Retrieves a specified instance audit event.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the audit event.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AuditEventsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4AuditEventsIdRequestCreation(id);
    }

    /**
     * List all group audit events
     * Lists all audit events for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param createdAfter Return group audit events created on or after the specified time.
     * @param createdBefore Return group audit events created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAuditEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdAuditEventsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdAuditEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesAuditEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/audit_events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group audit events
     * Lists all audit events for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param createdAfter Return group audit events created on or after the specified time.
     * @param createdBefore Return group audit events created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAuditEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAuditEvent> getApiV4GroupsIdAuditEvents(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAuditEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdAuditEventsRequestCreation(id, createdAfter, createdBefore, page, perPage).body(localVarReturnType);
    }

    /**
     * List all group audit events
     * Lists all audit events for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param createdAfter Return group audit events created on or after the specified time.
     * @param createdBefore Return group audit events created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAuditEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAuditEvent>> getApiV4GroupsIdAuditEventsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAuditEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdAuditEventsRequestCreation(id, createdAfter, createdBefore, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group audit events
     * Lists all audit events for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param createdAfter Return group audit events created on or after the specified time.
     * @param createdBefore Return group audit events created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdAuditEventsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdAuditEventsRequestCreation(id, createdAfter, createdBefore, page, perPage);
    }
}
