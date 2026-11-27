package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesResourceIterationEvent;
import ai.gebo.gitlab.client.model.APIEntitiesResourceLabelEvent;
import ai.gebo.gitlab.client.model.APIEntitiesResourceMilestoneEvent;
import ai.gebo.gitlab.client.model.APIEntitiesResourceStateEvent;
import ai.gebo.gitlab.client.model.APIEntitiesResourceWeightEvent;
import ai.gebo.gitlab.client.model.GetApiV4DependencyListExportsExportIdExportIdParameter;
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
public class ResourceEventsApi {
    private ApiClient apiClient;

    public ResourceEventsApi() {
        this(new ApiClient());
    }

    public ResourceEventsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all group epic label events
     * Lists all label events for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventableId The ID of the epic
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceLabelEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsEventableIdResourceLabelEventsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsEventableIdResourceLabelEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4GroupsIdEpicsEventableIdResourceLabelEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("eventable_id", eventableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesResourceLabelEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{eventable_id}/resource_label_events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group epic label events
     * Lists all label events for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventableId The ID of the epic
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceLabelEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesResourceLabelEvent> getApiV4GroupsIdEpicsEventableIdResourceLabelEvents(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceLabelEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEventableIdResourceLabelEventsRequestCreation(id, eventableId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all group epic label events
     * Lists all label events for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventableId The ID of the epic
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesResourceLabelEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesResourceLabelEvent>> getApiV4GroupsIdEpicsEventableIdResourceLabelEventsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceLabelEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEventableIdResourceLabelEventsRequestCreation(id, eventableId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group epic label events
     * Lists all label events for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventableId The ID of the epic
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsEventableIdResourceLabelEventsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsEventableIdResourceLabelEventsRequestCreation(id, eventableId, page, perPage);
    }

    /**
     * Retrieve an epic label event
     * Retrieves a label event for a specified group epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventId ID of the resource label event.
     * @param eventableId The ID of the epic
     * @return APIEntitiesResourceLabelEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsEventableIdResourceLabelEventsEventIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsEventableIdResourceLabelEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventId' is set
        if (eventId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventId' when calling getApiV4GroupsIdEpicsEventableIdResourceLabelEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4GroupsIdEpicsEventableIdResourceLabelEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("event_id", eventId);
        pathParams.put("eventable_id", eventableId);

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

        ParameterizedTypeReference<APIEntitiesResourceLabelEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{eventable_id}/resource_label_events/{event_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an epic label event
     * Retrieves a label event for a specified group epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventId ID of the resource label event.
     * @param eventableId The ID of the epic
     * @return APIEntitiesResourceLabelEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceLabelEvent getApiV4GroupsIdEpicsEventableIdResourceLabelEventsEventId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceLabelEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEventableIdResourceLabelEventsEventIdRequestCreation(id, eventId, eventableId).body(localVarReturnType);
    }

    /**
     * Retrieve an epic label event
     * Retrieves a label event for a specified group epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventId ID of the resource label event.
     * @param eventableId The ID of the epic
     * @return ResponseEntity&lt;APIEntitiesResourceLabelEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceLabelEvent> getApiV4GroupsIdEpicsEventableIdResourceLabelEventsEventIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceLabelEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEventableIdResourceLabelEventsEventIdRequestCreation(id, eventId, eventableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an epic label event
     * Retrieves a label event for a specified group epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventId ID of the resource label event.
     * @param eventableId The ID of the epic
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsEventableIdResourceLabelEventsEventIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsEventableIdResourceLabelEventsEventIdRequestCreation(id, eventId, eventableId);
    }

    /**
     * List all group epic state events
     * Lists all state events for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventableId The ID of the epic
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceStateEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsEventableIdResourceStateEventsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsEventableIdResourceStateEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4GroupsIdEpicsEventableIdResourceStateEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("eventable_id", eventableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesResourceStateEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{eventable_id}/resource_state_events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group epic state events
     * Lists all state events for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventableId The ID of the epic
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceStateEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesResourceStateEvent> getApiV4GroupsIdEpicsEventableIdResourceStateEvents(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceStateEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEventableIdResourceStateEventsRequestCreation(id, eventableId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all group epic state events
     * Lists all state events for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventableId The ID of the epic
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesResourceStateEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesResourceStateEvent>> getApiV4GroupsIdEpicsEventableIdResourceStateEventsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceStateEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEventableIdResourceStateEventsRequestCreation(id, eventableId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group epic state events
     * Lists all state events for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventableId The ID of the epic
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsEventableIdResourceStateEventsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsEventableIdResourceStateEventsRequestCreation(id, eventableId, page, perPage);
    }

    /**
     * Retrieve an epic state event
     * Retrieves a state event for a specified group epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventableId The ID of the epic
     * @param eventId ID of the resource state event.
     * @return APIEntitiesResourceStateEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsEventableIdResourceStateEventsEventIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nonnull Integer eventId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsEventableIdResourceStateEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4GroupsIdEpicsEventableIdResourceStateEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventId' is set
        if (eventId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventId' when calling getApiV4GroupsIdEpicsEventableIdResourceStateEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("eventable_id", eventableId);
        pathParams.put("event_id", eventId);

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

        ParameterizedTypeReference<APIEntitiesResourceStateEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{eventable_id}/resource_state_events/{event_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an epic state event
     * Retrieves a state event for a specified group epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventableId The ID of the epic
     * @param eventId ID of the resource state event.
     * @return APIEntitiesResourceStateEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceStateEvent getApiV4GroupsIdEpicsEventableIdResourceStateEventsEventId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nonnull Integer eventId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceStateEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEventableIdResourceStateEventsEventIdRequestCreation(id, eventableId, eventId).body(localVarReturnType);
    }

    /**
     * Retrieve an epic state event
     * Retrieves a state event for a specified group epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventableId The ID of the epic
     * @param eventId ID of the resource state event.
     * @return ResponseEntity&lt;APIEntitiesResourceStateEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceStateEvent> getApiV4GroupsIdEpicsEventableIdResourceStateEventsEventIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nonnull Integer eventId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceStateEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEventableIdResourceStateEventsEventIdRequestCreation(id, eventableId, eventId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an epic state event
     * Retrieves a state event for a specified group epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param eventableId The ID of the epic
     * @param eventId ID of the resource state event.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsEventableIdResourceStateEventsEventIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nonnull Integer eventId) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsEventableIdResourceStateEventsEventIdRequestCreation(id, eventableId, eventId);
    }

    /**
     * List all issue iteration events
     * Lists all iteration events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId ID of the eventable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceIterationEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesEventableIdResourceIterationEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdIssuesEventableIdResourceIterationEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("eventable_id", eventableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesResourceIterationEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{eventable_id}/resource_iteration_events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all issue iteration events
     * Lists all iteration events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId ID of the eventable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceIterationEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesResourceIterationEvent> getApiV4ProjectsIdIssuesEventableIdResourceIterationEvents(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceIterationEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsRequestCreation(id, eventableId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all issue iteration events
     * Lists all iteration events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId ID of the eventable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesResourceIterationEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesResourceIterationEvent>> getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceIterationEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsRequestCreation(id, eventableId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all issue iteration events
     * Lists all iteration events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId ID of the eventable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsRequestCreation(id, eventableId, page, perPage);
    }

    /**
     * Retrieve an issue iteration event
     * Retrieves an iteration event for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource iteration event.
     * @param eventableId ID of the eventable.
     * @return APIEntitiesResourceIterationEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsEventIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventId' is set
        if (eventId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventId' when calling getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("event_id", eventId);
        pathParams.put("eventable_id", eventableId);

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

        ParameterizedTypeReference<APIEntitiesResourceIterationEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{eventable_id}/resource_iteration_events/{event_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an issue iteration event
     * Retrieves an iteration event for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource iteration event.
     * @param eventableId ID of the eventable.
     * @return APIEntitiesResourceIterationEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceIterationEvent getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsEventId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceIterationEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsEventIdRequestCreation(id, eventId, eventableId).body(localVarReturnType);
    }

    /**
     * Retrieve an issue iteration event
     * Retrieves an iteration event for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource iteration event.
     * @param eventableId ID of the eventable.
     * @return ResponseEntity&lt;APIEntitiesResourceIterationEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceIterationEvent> getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsEventIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceIterationEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsEventIdRequestCreation(id, eventId, eventableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an issue iteration event
     * Retrieves an iteration event for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource iteration event.
     * @param eventableId ID of the eventable.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsEventIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesEventableIdResourceIterationEventsEventIdRequestCreation(id, eventId, eventableId);
    }

    /**
     * List all project issue label events
     * Lists all label events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the issue
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceLabelEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesEventableIdResourceLabelEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdIssuesEventableIdResourceLabelEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("eventable_id", eventableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesResourceLabelEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{eventable_id}/resource_label_events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project issue label events
     * Lists all label events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the issue
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceLabelEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesResourceLabelEvent> getApiV4ProjectsIdIssuesEventableIdResourceLabelEvents(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceLabelEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsRequestCreation(id, eventableId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project issue label events
     * Lists all label events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the issue
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesResourceLabelEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesResourceLabelEvent>> getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceLabelEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsRequestCreation(id, eventableId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project issue label events
     * Lists all label events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the issue
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsRequestCreation(id, eventableId, page, perPage);
    }

    /**
     * Retrieve an issue label event
     * Retrieves a label event for a specified project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource label event.
     * @param eventableId The IID of the issue
     * @return APIEntitiesResourceLabelEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsEventIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventId' is set
        if (eventId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventId' when calling getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("event_id", eventId);
        pathParams.put("eventable_id", eventableId);

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

        ParameterizedTypeReference<APIEntitiesResourceLabelEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{eventable_id}/resource_label_events/{event_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an issue label event
     * Retrieves a label event for a specified project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource label event.
     * @param eventableId The IID of the issue
     * @return APIEntitiesResourceLabelEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceLabelEvent getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsEventId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceLabelEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsEventIdRequestCreation(id, eventId, eventableId).body(localVarReturnType);
    }

    /**
     * Retrieve an issue label event
     * Retrieves a label event for a specified project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource label event.
     * @param eventableId The IID of the issue
     * @return ResponseEntity&lt;APIEntitiesResourceLabelEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceLabelEvent> getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsEventIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceLabelEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsEventIdRequestCreation(id, eventId, eventableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an issue label event
     * Retrieves a label event for a specified project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource label event.
     * @param eventableId The IID of the issue
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsEventIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesEventableIdResourceLabelEventsEventIdRequestCreation(id, eventId, eventableId);
    }

    /**
     * List all project issue milestone events
     * Lists all milestone events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId Internal ID of the issue or merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceMilestoneEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("eventable_id", eventableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesResourceMilestoneEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{eventable_id}/resource_milestone_events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project issue milestone events
     * Lists all milestone events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId Internal ID of the issue or merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceMilestoneEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesResourceMilestoneEvent> getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEvents(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceMilestoneEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsRequestCreation(id, eventableId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project issue milestone events
     * Lists all milestone events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId Internal ID of the issue or merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesResourceMilestoneEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesResourceMilestoneEvent>> getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceMilestoneEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsRequestCreation(id, eventableId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project issue milestone events
     * Lists all milestone events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId Internal ID of the issue or merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsRequestCreation(id, eventableId, page, perPage);
    }

    /**
     * Retrieve an issue milestone event
     * Retrieves a specified milestone event for a project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource milestone event.
     * @param eventableId Internal ID of the issue or merge request.
     * @return APIEntitiesResourceMilestoneEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsEventIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventId' is set
        if (eventId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventId' when calling getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("event_id", eventId);
        pathParams.put("eventable_id", eventableId);

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

        ParameterizedTypeReference<APIEntitiesResourceMilestoneEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{eventable_id}/resource_milestone_events/{event_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an issue milestone event
     * Retrieves a specified milestone event for a project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource milestone event.
     * @param eventableId Internal ID of the issue or merge request.
     * @return APIEntitiesResourceMilestoneEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceMilestoneEvent getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsEventId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceMilestoneEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsEventIdRequestCreation(id, eventId, eventableId).body(localVarReturnType);
    }

    /**
     * Retrieve an issue milestone event
     * Retrieves a specified milestone event for a project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource milestone event.
     * @param eventableId Internal ID of the issue or merge request.
     * @return ResponseEntity&lt;APIEntitiesResourceMilestoneEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceMilestoneEvent> getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsEventIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceMilestoneEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsEventIdRequestCreation(id, eventId, eventableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an issue milestone event
     * Retrieves a specified milestone event for a project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource milestone event.
     * @param eventableId Internal ID of the issue or merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsEventIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesEventableIdResourceMilestoneEventsEventIdRequestCreation(id, eventId, eventableId);
    }

    /**
     * List all project issue state events
     * Lists all state events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the issue
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceStateEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceStateEventsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesEventableIdResourceStateEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdIssuesEventableIdResourceStateEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("eventable_id", eventableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesResourceStateEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{eventable_id}/resource_state_events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project issue state events
     * Lists all state events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the issue
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceStateEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesResourceStateEvent> getApiV4ProjectsIdIssuesEventableIdResourceStateEvents(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceStateEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceStateEventsRequestCreation(id, eventableId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project issue state events
     * Lists all state events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the issue
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesResourceStateEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesResourceStateEvent>> getApiV4ProjectsIdIssuesEventableIdResourceStateEventsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceStateEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceStateEventsRequestCreation(id, eventableId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project issue state events
     * Lists all state events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the issue
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceStateEventsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesEventableIdResourceStateEventsRequestCreation(id, eventableId, page, perPage);
    }

    /**
     * Retrieve an issue state event
     * Retrieves a state event for a specified project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the issue
     * @param eventId ID of the resource state event.
     * @return APIEntitiesResourceStateEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceStateEventsEventIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nonnull Integer eventId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesEventableIdResourceStateEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdIssuesEventableIdResourceStateEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventId' is set
        if (eventId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventId' when calling getApiV4ProjectsIdIssuesEventableIdResourceStateEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("eventable_id", eventableId);
        pathParams.put("event_id", eventId);

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

        ParameterizedTypeReference<APIEntitiesResourceStateEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{eventable_id}/resource_state_events/{event_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an issue state event
     * Retrieves a state event for a specified project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the issue
     * @param eventId ID of the resource state event.
     * @return APIEntitiesResourceStateEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceStateEvent getApiV4ProjectsIdIssuesEventableIdResourceStateEventsEventId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nonnull Integer eventId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceStateEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceStateEventsEventIdRequestCreation(id, eventableId, eventId).body(localVarReturnType);
    }

    /**
     * Retrieve an issue state event
     * Retrieves a state event for a specified project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the issue
     * @param eventId ID of the resource state event.
     * @return ResponseEntity&lt;APIEntitiesResourceStateEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceStateEvent> getApiV4ProjectsIdIssuesEventableIdResourceStateEventsEventIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nonnull Integer eventId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceStateEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceStateEventsEventIdRequestCreation(id, eventableId, eventId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an issue state event
     * Retrieves a state event for a specified project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the issue
     * @param eventId ID of the resource state event.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceStateEventsEventIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nonnull Integer eventId) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesEventableIdResourceStateEventsEventIdRequestCreation(id, eventableId, eventId);
    }

    /**
     * List all project issue weight events
     * Lists all weight events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId ID of the eventable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceWeightEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesEventableIdResourceWeightEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdIssuesEventableIdResourceWeightEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("eventable_id", eventableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesResourceWeightEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{eventable_id}/resource_weight_events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project issue weight events
     * Lists all weight events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId ID of the eventable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceWeightEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesResourceWeightEvent> getApiV4ProjectsIdIssuesEventableIdResourceWeightEvents(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceWeightEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsRequestCreation(id, eventableId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project issue weight events
     * Lists all weight events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId ID of the eventable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesResourceWeightEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesResourceWeightEvent>> getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceWeightEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsRequestCreation(id, eventableId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project issue weight events
     * Lists all weight events for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId ID of the eventable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsRequestCreation(id, eventableId, page, perPage);
    }

    /**
     * Retrieve single issue weight event
     * Retrieves a single weight event for a specified project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource weight event.
     * @param eventableId ID of the eventable.
     * @return APIEntitiesResourceWeightEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsEventIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventId' is set
        if (eventId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventId' when calling getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("event_id", eventId);
        pathParams.put("eventable_id", eventableId);

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

        ParameterizedTypeReference<APIEntitiesResourceWeightEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{eventable_id}/resource_weight_events/{event_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve single issue weight event
     * Retrieves a single weight event for a specified project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource weight event.
     * @param eventableId ID of the eventable.
     * @return APIEntitiesResourceWeightEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceWeightEvent getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsEventId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceWeightEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsEventIdRequestCreation(id, eventId, eventableId).body(localVarReturnType);
    }

    /**
     * Retrieve single issue weight event
     * Retrieves a single weight event for a specified project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource weight event.
     * @param eventableId ID of the eventable.
     * @return ResponseEntity&lt;APIEntitiesResourceWeightEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceWeightEvent> getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsEventIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceWeightEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsEventIdRequestCreation(id, eventId, eventableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve single issue weight event
     * Retrieves a single weight event for a specified project issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource weight event.
     * @param eventableId ID of the eventable.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsEventIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesEventableIdResourceWeightEventsEventIdRequestCreation(id, eventId, eventableId);
    }

    /**
     * List all project merge request label events
     * Lists all label events for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the merge request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceLabelEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("eventable_id", eventableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesResourceLabelEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{eventable_id}/resource_label_events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project merge request label events
     * Lists all label events for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the merge request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceLabelEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesResourceLabelEvent> getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEvents(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceLabelEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsRequestCreation(id, eventableId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project merge request label events
     * Lists all label events for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the merge request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesResourceLabelEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesResourceLabelEvent>> getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceLabelEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsRequestCreation(id, eventableId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project merge request label events
     * Lists all label events for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the merge request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsRequestCreation(id, eventableId, page, perPage);
    }

    /**
     * Retrieve a merge request label event
     * Retrieves a label event for a specified project merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource label event.
     * @param eventableId The IID of the merge request
     * @return APIEntitiesResourceLabelEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsEventIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventId' is set
        if (eventId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventId' when calling getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("event_id", eventId);
        pathParams.put("eventable_id", eventableId);

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

        ParameterizedTypeReference<APIEntitiesResourceLabelEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{eventable_id}/resource_label_events/{event_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a merge request label event
     * Retrieves a label event for a specified project merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource label event.
     * @param eventableId The IID of the merge request
     * @return APIEntitiesResourceLabelEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceLabelEvent getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsEventId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceLabelEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsEventIdRequestCreation(id, eventId, eventableId).body(localVarReturnType);
    }

    /**
     * Retrieve a merge request label event
     * Retrieves a label event for a specified project merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource label event.
     * @param eventableId The IID of the merge request
     * @return ResponseEntity&lt;APIEntitiesResourceLabelEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceLabelEvent> getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsEventIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceLabelEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsEventIdRequestCreation(id, eventId, eventableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a merge request label event
     * Retrieves a label event for a specified project merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource label event.
     * @param eventableId The IID of the merge request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsEventIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceLabelEventsEventIdRequestCreation(id, eventId, eventableId);
    }

    /**
     * List all project merge request milestone events
     * Lists all milestone events for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId Internal ID of the issue or merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceMilestoneEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("eventable_id", eventableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesResourceMilestoneEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{eventable_id}/resource_milestone_events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project merge request milestone events
     * Lists all milestone events for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId Internal ID of the issue or merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceMilestoneEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesResourceMilestoneEvent> getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEvents(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceMilestoneEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsRequestCreation(id, eventableId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project merge request milestone events
     * Lists all milestone events for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId Internal ID of the issue or merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesResourceMilestoneEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesResourceMilestoneEvent>> getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceMilestoneEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsRequestCreation(id, eventableId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project merge request milestone events
     * Lists all milestone events for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId Internal ID of the issue or merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsRequestCreation(id, eventableId, page, perPage);
    }

    /**
     * Retrieve a merge request milestone event
     * Retrieves a specified milestone event for a project merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource milestone event.
     * @param eventableId Internal ID of the issue or merge request.
     * @return APIEntitiesResourceMilestoneEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsEventIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventId' is set
        if (eventId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventId' when calling getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("event_id", eventId);
        pathParams.put("eventable_id", eventableId);

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

        ParameterizedTypeReference<APIEntitiesResourceMilestoneEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{eventable_id}/resource_milestone_events/{event_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a merge request milestone event
     * Retrieves a specified milestone event for a project merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource milestone event.
     * @param eventableId Internal ID of the issue or merge request.
     * @return APIEntitiesResourceMilestoneEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceMilestoneEvent getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsEventId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceMilestoneEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsEventIdRequestCreation(id, eventId, eventableId).body(localVarReturnType);
    }

    /**
     * Retrieve a merge request milestone event
     * Retrieves a specified milestone event for a project merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource milestone event.
     * @param eventableId Internal ID of the issue or merge request.
     * @return ResponseEntity&lt;APIEntitiesResourceMilestoneEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceMilestoneEvent> getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsEventIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceMilestoneEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsEventIdRequestCreation(id, eventId, eventableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a merge request milestone event
     * Retrieves a specified milestone event for a project merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventId ID of the resource milestone event.
     * @param eventableId Internal ID of the issue or merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsEventIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String eventId, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter eventableId) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceMilestoneEventsEventIdRequestCreation(id, eventId, eventableId);
    }

    /**
     * List all project merge request state events
     * Lists all state events for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the merge request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceStateEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("eventable_id", eventableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesResourceStateEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{eventable_id}/resource_state_events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project merge request state events
     * Lists all state events for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the merge request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceStateEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesResourceStateEvent> getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEvents(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceStateEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsRequestCreation(id, eventableId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project merge request state events
     * Lists all state events for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the merge request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesResourceStateEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesResourceStateEvent>> getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceStateEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsRequestCreation(id, eventableId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project merge request state events
     * Lists all state events for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the merge request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsRequestCreation(id, eventableId, page, perPage);
    }

    /**
     * Retrieve a merge request state event
     * Retrieves a state event for a specified project merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the merge request
     * @param eventId ID of the resource state event.
     * @return APIEntitiesResourceStateEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsEventIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nonnull Integer eventId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventableId' is set
        if (eventableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventableId' when calling getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'eventId' is set
        if (eventId == null) {
            throw new RestClientResponseException("Missing the required parameter 'eventId' when calling getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("eventable_id", eventableId);
        pathParams.put("event_id", eventId);

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

        ParameterizedTypeReference<APIEntitiesResourceStateEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{eventable_id}/resource_state_events/{event_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a merge request state event
     * Retrieves a state event for a specified project merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the merge request
     * @param eventId ID of the resource state event.
     * @return APIEntitiesResourceStateEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceStateEvent getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsEventId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nonnull Integer eventId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceStateEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsEventIdRequestCreation(id, eventableId, eventId).body(localVarReturnType);
    }

    /**
     * Retrieve a merge request state event
     * Retrieves a state event for a specified project merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the merge request
     * @param eventId ID of the resource state event.
     * @return ResponseEntity&lt;APIEntitiesResourceStateEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceStateEvent> getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsEventIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nonnull Integer eventId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceStateEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsEventIdRequestCreation(id, eventableId, eventId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a merge request state event
     * Retrieves a state event for a specified project merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param eventableId The IID of the merge request
     * @param eventId ID of the resource state event.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsEventIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer eventableId, @jakarta.annotation.Nonnull Integer eventId) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsEventableIdResourceStateEventsEventIdRequestCreation(id, eventableId, eventId);
    }
}
