package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesIteration;
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
public class IterationsApi {
    private ApiClient apiClient;

    public IterationsApi() {
        this(new ApiClient());
    }

    public IterationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all group iterations
     * Lists all iterations for a specified group. Iterations created by **Enable automatic scheduling** in iteration cadences return &#x60;null&#x60; for the &#x60;title&#x60; and &#x60;description&#x60; fields. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param state Filter iterations by state. Filtering by &#x60;started&#x60; is deprecated. Use &#x60;current&#x60; instead.
     * @param search Return only iterations with a title matching the provided string.
     * @param in Fields to search with the &#x60;search&#x60; query. Possible values are &#x60;title&#x60; and &#x60;cadence_title&#x60;.
     * @param includeAncestors If &#x60;true&#x60;, includes iterations for the parent group and its ancestors.
     * @param includeDescendants If &#x60;true&#x60;, includes iterations for the parent group and its descendants.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesIteration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdIterationsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable List<String> in, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable Boolean includeDescendants, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdIterations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "in", in));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_ancestors", includeAncestors));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_descendants", includeDescendants));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesIteration>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/iterations", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group iterations
     * Lists all iterations for a specified group. Iterations created by **Enable automatic scheduling** in iteration cadences return &#x60;null&#x60; for the &#x60;title&#x60; and &#x60;description&#x60; fields. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param state Filter iterations by state. Filtering by &#x60;started&#x60; is deprecated. Use &#x60;current&#x60; instead.
     * @param search Return only iterations with a title matching the provided string.
     * @param in Fields to search with the &#x60;search&#x60; query. Possible values are &#x60;title&#x60; and &#x60;cadence_title&#x60;.
     * @param includeAncestors If &#x60;true&#x60;, includes iterations for the parent group and its ancestors.
     * @param includeDescendants If &#x60;true&#x60;, includes iterations for the parent group and its descendants.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesIteration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesIteration> getApiV4GroupsIdIterations(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable List<String> in, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable Boolean includeDescendants, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIteration>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdIterationsRequestCreation(id, state, search, in, includeAncestors, includeDescendants, updatedBefore, updatedAfter, page, perPage).body(localVarReturnType);
    }

    /**
     * List all group iterations
     * Lists all iterations for a specified group. Iterations created by **Enable automatic scheduling** in iteration cadences return &#x60;null&#x60; for the &#x60;title&#x60; and &#x60;description&#x60; fields. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param state Filter iterations by state. Filtering by &#x60;started&#x60; is deprecated. Use &#x60;current&#x60; instead.
     * @param search Return only iterations with a title matching the provided string.
     * @param in Fields to search with the &#x60;search&#x60; query. Possible values are &#x60;title&#x60; and &#x60;cadence_title&#x60;.
     * @param includeAncestors If &#x60;true&#x60;, includes iterations for the parent group and its ancestors.
     * @param includeDescendants If &#x60;true&#x60;, includes iterations for the parent group and its descendants.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesIteration&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesIteration>> getApiV4GroupsIdIterationsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable List<String> in, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable Boolean includeDescendants, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIteration>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdIterationsRequestCreation(id, state, search, in, includeAncestors, includeDescendants, updatedBefore, updatedAfter, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group iterations
     * Lists all iterations for a specified group. Iterations created by **Enable automatic scheduling** in iteration cadences return &#x60;null&#x60; for the &#x60;title&#x60; and &#x60;description&#x60; fields. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param state Filter iterations by state. Filtering by &#x60;started&#x60; is deprecated. Use &#x60;current&#x60; instead.
     * @param search Return only iterations with a title matching the provided string.
     * @param in Fields to search with the &#x60;search&#x60; query. Possible values are &#x60;title&#x60; and &#x60;cadence_title&#x60;.
     * @param includeAncestors If &#x60;true&#x60;, includes iterations for the parent group and its ancestors.
     * @param includeDescendants If &#x60;true&#x60;, includes iterations for the parent group and its descendants.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdIterationsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable List<String> in, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable Boolean includeDescendants, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdIterationsRequestCreation(id, state, search, in, includeAncestors, includeDescendants, updatedBefore, updatedAfter, page, perPage);
    }

    /**
     * List all project iterations
     * Lists all iterations for a specified project. Iterations created by **Enable automatic scheduling** in iteration cadences return &#x60;null&#x60; for the &#x60;title&#x60; and &#x60;description&#x60; fields. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param state Filter iterations by state. Filtering by &#x60;started&#x60; is deprecated. Use &#x60;current&#x60; instead.
     * @param search Return only iterations with a title matching the provided string.
     * @param in Fields to search with the &#x60;search&#x60; query. Possible values are &#x60;title&#x60; and &#x60;cadence_title&#x60;.
     * @param includeAncestors If &#x60;true&#x60;, includes iterations for the parent group and its ancestors.
     * @param includeDescendants If &#x60;true&#x60;, includes iterations for the parent group and its descendants.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesIteration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIterationsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable List<String> in, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable Boolean includeDescendants, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIterations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "in", in));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_ancestors", includeAncestors));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_descendants", includeDescendants));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesIteration>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/iterations", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project iterations
     * Lists all iterations for a specified project. Iterations created by **Enable automatic scheduling** in iteration cadences return &#x60;null&#x60; for the &#x60;title&#x60; and &#x60;description&#x60; fields. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param state Filter iterations by state. Filtering by &#x60;started&#x60; is deprecated. Use &#x60;current&#x60; instead.
     * @param search Return only iterations with a title matching the provided string.
     * @param in Fields to search with the &#x60;search&#x60; query. Possible values are &#x60;title&#x60; and &#x60;cadence_title&#x60;.
     * @param includeAncestors If &#x60;true&#x60;, includes iterations for the parent group and its ancestors.
     * @param includeDescendants If &#x60;true&#x60;, includes iterations for the parent group and its descendants.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesIteration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesIteration> getApiV4ProjectsIdIterations(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable List<String> in, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable Boolean includeDescendants, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIteration>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIterationsRequestCreation(id, state, search, in, includeAncestors, includeDescendants, updatedBefore, updatedAfter, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project iterations
     * Lists all iterations for a specified project. Iterations created by **Enable automatic scheduling** in iteration cadences return &#x60;null&#x60; for the &#x60;title&#x60; and &#x60;description&#x60; fields. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param state Filter iterations by state. Filtering by &#x60;started&#x60; is deprecated. Use &#x60;current&#x60; instead.
     * @param search Return only iterations with a title matching the provided string.
     * @param in Fields to search with the &#x60;search&#x60; query. Possible values are &#x60;title&#x60; and &#x60;cadence_title&#x60;.
     * @param includeAncestors If &#x60;true&#x60;, includes iterations for the parent group and its ancestors.
     * @param includeDescendants If &#x60;true&#x60;, includes iterations for the parent group and its descendants.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesIteration&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesIteration>> getApiV4ProjectsIdIterationsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable List<String> in, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable Boolean includeDescendants, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIteration>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIterationsRequestCreation(id, state, search, in, includeAncestors, includeDescendants, updatedBefore, updatedAfter, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project iterations
     * Lists all iterations for a specified project. Iterations created by **Enable automatic scheduling** in iteration cadences return &#x60;null&#x60; for the &#x60;title&#x60; and &#x60;description&#x60; fields. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param state Filter iterations by state. Filtering by &#x60;started&#x60; is deprecated. Use &#x60;current&#x60; instead.
     * @param search Return only iterations with a title matching the provided string.
     * @param in Fields to search with the &#x60;search&#x60; query. Possible values are &#x60;title&#x60; and &#x60;cadence_title&#x60;.
     * @param includeAncestors If &#x60;true&#x60;, includes iterations for the parent group and its ancestors.
     * @param includeDescendants If &#x60;true&#x60;, includes iterations for the parent group and its descendants.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIterationsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable List<String> in, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable Boolean includeDescendants, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdIterationsRequestCreation(id, state, search, in, includeAncestors, includeDescendants, updatedBefore, updatedAfter, page, perPage);
    }
}
