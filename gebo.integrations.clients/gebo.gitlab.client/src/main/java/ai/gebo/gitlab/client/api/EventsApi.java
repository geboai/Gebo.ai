package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesEvent;
import java.time.LocalDate;
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
public class EventsApi {
    private ApiClient apiClient;

    public EventsApi() {
        this(new ApiClient());
    }

    public EventsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all events
     * Lists all events for the authenticated user. Does not return events associated with epics or merge requests. Returns bulk push events with limited commit details. Events about a wiki page include a &#x60;wiki_page&#x60; object. To return only those events, set &#x60;target_type&#x60; to &#x60;wiki&#x60;. Requires a token with the &#x60;read_user&#x60; or &#x60;api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param scope Include all events across the user&#39;s projects.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param action Filter events by the specified [action type](https://docs.gitlab.com/user/profile/contributions_calendar/#user-contribution-events).
     * @param targetType Filter events by target type.
     * @param before Return events created before the specified date.
     * @param after Return events created after the specified date.
     * @param sort Sort results in ascending or descending order by creation date.
     * @return List&lt;APIEntitiesEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4EventsRequestCreation(@jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable String targetType, @jakarta.annotation.Nullable LocalDate before, @jakarta.annotation.Nullable LocalDate after, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "action", action));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "target_type", targetType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "before", before));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "after", after));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all events
     * Lists all events for the authenticated user. Does not return events associated with epics or merge requests. Returns bulk push events with limited commit details. Events about a wiki page include a &#x60;wiki_page&#x60; object. To return only those events, set &#x60;target_type&#x60; to &#x60;wiki&#x60;. Requires a token with the &#x60;read_user&#x60; or &#x60;api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param scope Include all events across the user&#39;s projects.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param action Filter events by the specified [action type](https://docs.gitlab.com/user/profile/contributions_calendar/#user-contribution-events).
     * @param targetType Filter events by target type.
     * @param before Return events created before the specified date.
     * @param after Return events created after the specified date.
     * @param sort Sort results in ascending or descending order by creation date.
     * @return List&lt;APIEntitiesEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesEvent> getApiV4Events(@jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable String targetType, @jakarta.annotation.Nullable LocalDate before, @jakarta.annotation.Nullable LocalDate after, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4EventsRequestCreation(scope, page, perPage, action, targetType, before, after, sort).body(localVarReturnType);
    }

    /**
     * List all events
     * Lists all events for the authenticated user. Does not return events associated with epics or merge requests. Returns bulk push events with limited commit details. Events about a wiki page include a &#x60;wiki_page&#x60; object. To return only those events, set &#x60;target_type&#x60; to &#x60;wiki&#x60;. Requires a token with the &#x60;read_user&#x60; or &#x60;api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param scope Include all events across the user&#39;s projects.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param action Filter events by the specified [action type](https://docs.gitlab.com/user/profile/contributions_calendar/#user-contribution-events).
     * @param targetType Filter events by target type.
     * @param before Return events created before the specified date.
     * @param after Return events created after the specified date.
     * @param sort Sort results in ascending or descending order by creation date.
     * @return ResponseEntity&lt;List&lt;APIEntitiesEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesEvent>> getApiV4EventsWithHttpInfo(@jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable String targetType, @jakarta.annotation.Nullable LocalDate before, @jakarta.annotation.Nullable LocalDate after, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4EventsRequestCreation(scope, page, perPage, action, targetType, before, after, sort).toEntity(localVarReturnType);
    }

    /**
     * List all events
     * Lists all events for the authenticated user. Does not return events associated with epics or merge requests. Returns bulk push events with limited commit details. Events about a wiki page include a &#x60;wiki_page&#x60; object. To return only those events, set &#x60;target_type&#x60; to &#x60;wiki&#x60;. Requires a token with the &#x60;read_user&#x60; or &#x60;api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param scope Include all events across the user&#39;s projects.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param action Filter events by the specified [action type](https://docs.gitlab.com/user/profile/contributions_calendar/#user-contribution-events).
     * @param targetType Filter events by target type.
     * @param before Return events created before the specified date.
     * @param after Return events created after the specified date.
     * @param sort Sort results in ascending or descending order by creation date.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4EventsWithResponseSpec(@jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable String targetType, @jakarta.annotation.Nullable LocalDate before, @jakarta.annotation.Nullable LocalDate after, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getApiV4EventsRequestCreation(scope, page, perPage, action, targetType, before, after, sort);
    }

    /**
     * List all visible events for a project
     * Lists all visible events for a specified project. When a push exceeds the Push event activities limit, a single bulk push event is returned instead of individual commit events. Bulk push events have limited commit details: &#x60;commit_count&#x60; is &#x60;0&#x60;, &#x60;ref_count&#x60; shows the number of refs pushed, and individual commit attributes are &#x60;null&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param action Filter events by the specified [action type](https://docs.gitlab.com/user/profile/contributions_calendar/#user-contribution-events).
     * @param targetType Filter events by target type.
     * @param before Return events created before the specified date.
     * @param after Return events created after the specified date.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdEventsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable String targetType, @jakarta.annotation.Nullable LocalDate before, @jakarta.annotation.Nullable LocalDate after, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "action", action));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "target_type", targetType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "before", before));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "after", after));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all visible events for a project
     * Lists all visible events for a specified project. When a push exceeds the Push event activities limit, a single bulk push event is returned instead of individual commit events. Bulk push events have limited commit details: &#x60;commit_count&#x60; is &#x60;0&#x60;, &#x60;ref_count&#x60; shows the number of refs pushed, and individual commit attributes are &#x60;null&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param action Filter events by the specified [action type](https://docs.gitlab.com/user/profile/contributions_calendar/#user-contribution-events).
     * @param targetType Filter events by target type.
     * @param before Return events created before the specified date.
     * @param after Return events created after the specified date.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesEvent> getApiV4ProjectsIdEvents(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable String targetType, @jakarta.annotation.Nullable LocalDate before, @jakarta.annotation.Nullable LocalDate after, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdEventsRequestCreation(id, action, targetType, before, after, sort, page, perPage).body(localVarReturnType);
    }

    /**
     * List all visible events for a project
     * Lists all visible events for a specified project. When a push exceeds the Push event activities limit, a single bulk push event is returned instead of individual commit events. Bulk push events have limited commit details: &#x60;commit_count&#x60; is &#x60;0&#x60;, &#x60;ref_count&#x60; shows the number of refs pushed, and individual commit attributes are &#x60;null&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param action Filter events by the specified [action type](https://docs.gitlab.com/user/profile/contributions_calendar/#user-contribution-events).
     * @param targetType Filter events by target type.
     * @param before Return events created before the specified date.
     * @param after Return events created after the specified date.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesEvent>> getApiV4ProjectsIdEventsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable String targetType, @jakarta.annotation.Nullable LocalDate before, @jakarta.annotation.Nullable LocalDate after, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdEventsRequestCreation(id, action, targetType, before, after, sort, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all visible events for a project
     * Lists all visible events for a specified project. When a push exceeds the Push event activities limit, a single bulk push event is returned instead of individual commit events. Bulk push events have limited commit details: &#x60;commit_count&#x60; is &#x60;0&#x60;, &#x60;ref_count&#x60; shows the number of refs pushed, and individual commit attributes are &#x60;null&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param action Filter events by the specified [action type](https://docs.gitlab.com/user/profile/contributions_calendar/#user-contribution-events).
     * @param targetType Filter events by target type.
     * @param before Return events created before the specified date.
     * @param after Return events created after the specified date.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdEventsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable String targetType, @jakarta.annotation.Nullable LocalDate before, @jakarta.annotation.Nullable LocalDate after, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdEventsRequestCreation(id, action, targetType, before, after, sort, page, perPage);
    }

    /**
     * Retrieve contribution events for a user
     * Retrieves the contribution events for a specified user. Does not return events associated with epics or merge requests. Returns bulk push events with limited commit details. A token used to authenticate must have the &#x60;read_user&#x60; or &#x60;api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or username of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param action Filter events by the specified [action type](https://docs.gitlab.com/user/profile/contributions_calendar/#user-contribution-events).
     * @param targetType Filter events by target type.
     * @param before Return events created before the specified date.
     * @param after Return events created after the specified date.
     * @param sort Sort results in ascending or descending order by creation date.
     * @return List&lt;APIEntitiesEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersIdEventsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable String targetType, @jakarta.annotation.Nullable LocalDate before, @jakarta.annotation.Nullable LocalDate after, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4UsersIdEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "action", action));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "target_type", targetType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "before", before));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "after", after));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve contribution events for a user
     * Retrieves the contribution events for a specified user. Does not return events associated with epics or merge requests. Returns bulk push events with limited commit details. A token used to authenticate must have the &#x60;read_user&#x60; or &#x60;api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or username of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param action Filter events by the specified [action type](https://docs.gitlab.com/user/profile/contributions_calendar/#user-contribution-events).
     * @param targetType Filter events by target type.
     * @param before Return events created before the specified date.
     * @param after Return events created after the specified date.
     * @param sort Sort results in ascending or descending order by creation date.
     * @return List&lt;APIEntitiesEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesEvent> getApiV4UsersIdEvents(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable String targetType, @jakarta.annotation.Nullable LocalDate before, @jakarta.annotation.Nullable LocalDate after, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdEventsRequestCreation(id, page, perPage, action, targetType, before, after, sort).body(localVarReturnType);
    }

    /**
     * Retrieve contribution events for a user
     * Retrieves the contribution events for a specified user. Does not return events associated with epics or merge requests. Returns bulk push events with limited commit details. A token used to authenticate must have the &#x60;read_user&#x60; or &#x60;api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or username of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param action Filter events by the specified [action type](https://docs.gitlab.com/user/profile/contributions_calendar/#user-contribution-events).
     * @param targetType Filter events by target type.
     * @param before Return events created before the specified date.
     * @param after Return events created after the specified date.
     * @param sort Sort results in ascending or descending order by creation date.
     * @return ResponseEntity&lt;List&lt;APIEntitiesEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesEvent>> getApiV4UsersIdEventsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable String targetType, @jakarta.annotation.Nullable LocalDate before, @jakarta.annotation.Nullable LocalDate after, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdEventsRequestCreation(id, page, perPage, action, targetType, before, after, sort).toEntity(localVarReturnType);
    }

    /**
     * Retrieve contribution events for a user
     * Retrieves the contribution events for a specified user. Does not return events associated with epics or merge requests. Returns bulk push events with limited commit details. A token used to authenticate must have the &#x60;read_user&#x60; or &#x60;api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or username of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param action Filter events by the specified [action type](https://docs.gitlab.com/user/profile/contributions_calendar/#user-contribution-events).
     * @param targetType Filter events by target type.
     * @param before Return events created before the specified date.
     * @param after Return events created after the specified date.
     * @param sort Sort results in ascending or descending order by creation date.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersIdEventsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable String targetType, @jakarta.annotation.Nullable LocalDate before, @jakarta.annotation.Nullable LocalDate after, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getApiV4UsersIdEventsRequestCreation(id, page, perPage, action, targetType, before, after, sort);
    }
}
