package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBurndownEvent;
import ai.gebo.gitlab.client.model.APIEntitiesIssueBasic;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestBasic;
import ai.gebo.gitlab.client.model.APIEntitiesMilestone;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdMilestonesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMilestonesRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdMilestonesMilestoneIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdMilestonesMilestoneIdRequest;

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
public class MilestonesApi {
    private ApiClient apiClient;

    public MilestonesApi() {
        this(new ApiClient());
    }

    public MilestonesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a group milestone
     * Deletes the specified group milestone.
     * <p><b>204</b> - 204 No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdMilestonesMilestoneIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdMilestonesMilestoneId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'milestoneId' is set
        if (milestoneId == null) {
            throw new RestClientResponseException("Missing the required parameter 'milestoneId' when calling deleteApiV4GroupsIdMilestonesMilestoneId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("milestone_id", milestoneId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/milestones/{milestone_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a group milestone
     * Deletes the specified group milestone.
     * <p><b>204</b> - 204 No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdMilestonesMilestoneId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdMilestonesMilestoneIdRequestCreation(id, milestoneId).body(localVarReturnType);
    }

    /**
     * Delete a group milestone
     * Deletes the specified group milestone.
     * <p><b>204</b> - 204 No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdMilestonesMilestoneIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdMilestonesMilestoneIdRequestCreation(id, milestoneId).toEntity(localVarReturnType);
    }

    /**
     * Delete a group milestone
     * Deletes the specified group milestone.
     * <p><b>204</b> - 204 No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdMilestonesMilestoneIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        return deleteApiV4GroupsIdMilestonesMilestoneIdRequestCreation(id, milestoneId);
    }

    /**
     * Delete a project milestone
     * Deletes a specified project milestone. Only for users with the Planner, Reporter, Developer, Maintainer, or Owner role for the project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdMilestonesMilestoneIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdMilestonesMilestoneId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'milestoneId' is set
        if (milestoneId == null) {
            throw new RestClientResponseException("Missing the required parameter 'milestoneId' when calling deleteApiV4ProjectsIdMilestonesMilestoneId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("milestone_id", milestoneId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/milestones/{milestone_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a project milestone
     * Deletes a specified project milestone. Only for users with the Planner, Reporter, Developer, Maintainer, or Owner role for the project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdMilestonesMilestoneId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdMilestonesMilestoneIdRequestCreation(id, milestoneId).body(localVarReturnType);
    }

    /**
     * Delete a project milestone
     * Deletes a specified project milestone. Only for users with the Planner, Reporter, Developer, Maintainer, or Owner role for the project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdMilestonesMilestoneIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMilestonesMilestoneIdRequestCreation(id, milestoneId).toEntity(localVarReturnType);
    }

    /**
     * Delete a project milestone
     * Deletes a specified project milestone. Only for users with the Planner, Reporter, Developer, Maintainer, or Owner role for the project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdMilestonesMilestoneIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdMilestonesMilestoneIdRequestCreation(id, milestoneId);
    }

    /**
     * List all group milestones
     * Lists all group milestones for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param state Return \&quot;active\&quot;, \&quot;closed\&quot;, or \&quot;all\&quot; milestones
     * @param iids Return only the milestones having the given &#x60;iid&#x60;. Ignored if &#x60;include_ancestors&#x60; is &#x60;true&#x60;.
     * @param title The title of the milestones
     * @param search The search criteria for the title or description of the milestone
     * @param searchTitle The search criteria for the title of the milestone
     * @param includeParentMilestones If &#x60;true&#x60;, includes milestones from all parent groups. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/433298). Use &#x60;include_ancestors&#x60; instead. Mutually exclusive with &#x60;include_ancestors&#x60;.
     * @param includeAncestors Include milestones from all parent groups. Mutually exclusive with &#x60;include_parent_milestones&#x60;.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param containingDate Return milestones where start_date &lt;&#x3D; containing_date &lt;&#x3D; due_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param startDate Return milestones where due_date &gt;&#x3D; start_date. Requires end_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param endDate Return milestones where start_date &lt;&#x3D; end_date. Requires start_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param sort Return milestones sorted in the given order
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param includeDescendants If &#x60;true&#x60;, includes milestones for the group and its descendants.
     * @return List&lt;APIEntitiesMilestone&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdMilestonesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String searchTitle, @jakarta.annotation.Nullable Boolean includeParentMilestones, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime containingDate, @jakarta.annotation.Nullable OffsetDateTime startDate, @jakarta.annotation.Nullable OffsetDateTime endDate, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean includeDescendants) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdMilestones", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "iids", iids));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "title", title));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search_title", searchTitle));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_parent_milestones", includeParentMilestones));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_ancestors", includeAncestors));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "containing_date", containingDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start_date", startDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "end_date", endDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_descendants", includeDescendants));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesMilestone>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/milestones", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group milestones
     * Lists all group milestones for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param state Return \&quot;active\&quot;, \&quot;closed\&quot;, or \&quot;all\&quot; milestones
     * @param iids Return only the milestones having the given &#x60;iid&#x60;. Ignored if &#x60;include_ancestors&#x60; is &#x60;true&#x60;.
     * @param title The title of the milestones
     * @param search The search criteria for the title or description of the milestone
     * @param searchTitle The search criteria for the title of the milestone
     * @param includeParentMilestones If &#x60;true&#x60;, includes milestones from all parent groups. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/433298). Use &#x60;include_ancestors&#x60; instead. Mutually exclusive with &#x60;include_ancestors&#x60;.
     * @param includeAncestors Include milestones from all parent groups. Mutually exclusive with &#x60;include_parent_milestones&#x60;.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param containingDate Return milestones where start_date &lt;&#x3D; containing_date &lt;&#x3D; due_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param startDate Return milestones where due_date &gt;&#x3D; start_date. Requires end_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param endDate Return milestones where start_date &lt;&#x3D; end_date. Requires start_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param sort Return milestones sorted in the given order
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param includeDescendants If &#x60;true&#x60;, includes milestones for the group and its descendants.
     * @return List&lt;APIEntitiesMilestone&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMilestone> getApiV4GroupsIdMilestones(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String searchTitle, @jakarta.annotation.Nullable Boolean includeParentMilestones, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime containingDate, @jakarta.annotation.Nullable OffsetDateTime startDate, @jakarta.annotation.Nullable OffsetDateTime endDate, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean includeDescendants) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMilestone>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMilestonesRequestCreation(id, state, iids, title, search, searchTitle, includeParentMilestones, includeAncestors, updatedBefore, updatedAfter, containingDate, startDate, endDate, sort, page, perPage, includeDescendants).body(localVarReturnType);
    }

    /**
     * List all group milestones
     * Lists all group milestones for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param state Return \&quot;active\&quot;, \&quot;closed\&quot;, or \&quot;all\&quot; milestones
     * @param iids Return only the milestones having the given &#x60;iid&#x60;. Ignored if &#x60;include_ancestors&#x60; is &#x60;true&#x60;.
     * @param title The title of the milestones
     * @param search The search criteria for the title or description of the milestone
     * @param searchTitle The search criteria for the title of the milestone
     * @param includeParentMilestones If &#x60;true&#x60;, includes milestones from all parent groups. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/433298). Use &#x60;include_ancestors&#x60; instead. Mutually exclusive with &#x60;include_ancestors&#x60;.
     * @param includeAncestors Include milestones from all parent groups. Mutually exclusive with &#x60;include_parent_milestones&#x60;.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param containingDate Return milestones where start_date &lt;&#x3D; containing_date &lt;&#x3D; due_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param startDate Return milestones where due_date &gt;&#x3D; start_date. Requires end_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param endDate Return milestones where start_date &lt;&#x3D; end_date. Requires start_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param sort Return milestones sorted in the given order
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param includeDescendants If &#x60;true&#x60;, includes milestones for the group and its descendants.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMilestone&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMilestone>> getApiV4GroupsIdMilestonesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String searchTitle, @jakarta.annotation.Nullable Boolean includeParentMilestones, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime containingDate, @jakarta.annotation.Nullable OffsetDateTime startDate, @jakarta.annotation.Nullable OffsetDateTime endDate, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean includeDescendants) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMilestone>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMilestonesRequestCreation(id, state, iids, title, search, searchTitle, includeParentMilestones, includeAncestors, updatedBefore, updatedAfter, containingDate, startDate, endDate, sort, page, perPage, includeDescendants).toEntity(localVarReturnType);
    }

    /**
     * List all group milestones
     * Lists all group milestones for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param state Return \&quot;active\&quot;, \&quot;closed\&quot;, or \&quot;all\&quot; milestones
     * @param iids Return only the milestones having the given &#x60;iid&#x60;. Ignored if &#x60;include_ancestors&#x60; is &#x60;true&#x60;.
     * @param title The title of the milestones
     * @param search The search criteria for the title or description of the milestone
     * @param searchTitle The search criteria for the title of the milestone
     * @param includeParentMilestones If &#x60;true&#x60;, includes milestones from all parent groups. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/433298). Use &#x60;include_ancestors&#x60; instead. Mutually exclusive with &#x60;include_ancestors&#x60;.
     * @param includeAncestors Include milestones from all parent groups. Mutually exclusive with &#x60;include_parent_milestones&#x60;.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param containingDate Return milestones where start_date &lt;&#x3D; containing_date &lt;&#x3D; due_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param startDate Return milestones where due_date &gt;&#x3D; start_date. Requires end_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param endDate Return milestones where start_date &lt;&#x3D; end_date. Requires start_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param sort Return milestones sorted in the given order
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param includeDescendants If &#x60;true&#x60;, includes milestones for the group and its descendants.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdMilestonesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String searchTitle, @jakarta.annotation.Nullable Boolean includeParentMilestones, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime containingDate, @jakarta.annotation.Nullable OffsetDateTime startDate, @jakarta.annotation.Nullable OffsetDateTime endDate, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean includeDescendants) throws RestClientResponseException {
        return getApiV4GroupsIdMilestonesRequestCreation(id, state, iids, title, search, searchTitle, includeParentMilestones, includeAncestors, updatedBefore, updatedAfter, containingDate, startDate, endDate, sort, page, perPage, includeDescendants);
    }

    /**
     * Retrieve a group milestone
     * Retrieves a specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @return APIEntitiesMilestone
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdMilestonesMilestoneIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdMilestonesMilestoneId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'milestoneId' is set
        if (milestoneId == null) {
            throw new RestClientResponseException("Missing the required parameter 'milestoneId' when calling getApiV4GroupsIdMilestonesMilestoneId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("milestone_id", milestoneId);

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

        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/milestones/{milestone_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group milestone
     * Retrieves a specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @return APIEntitiesMilestone
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMilestone getApiV4GroupsIdMilestonesMilestoneId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMilestonesMilestoneIdRequestCreation(id, milestoneId).body(localVarReturnType);
    }

    /**
     * Retrieve a group milestone
     * Retrieves a specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @return ResponseEntity&lt;APIEntitiesMilestone&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMilestone> getApiV4GroupsIdMilestonesMilestoneIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMilestonesMilestoneIdRequestCreation(id, milestoneId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group milestone
     * Retrieves a specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdMilestonesMilestoneIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        return getApiV4GroupsIdMilestonesMilestoneIdRequestCreation(id, milestoneId);
    }

    /**
     * List all burndown chart events for a milestone
     * Lists all burndown chart events for a specified milestone.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @return List&lt;APIEntitiesBurndownEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdMilestonesMilestoneIdBurndownEventsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdMilestonesMilestoneIdBurndownEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'milestoneId' is set
        if (milestoneId == null) {
            throw new RestClientResponseException("Missing the required parameter 'milestoneId' when calling getApiV4GroupsIdMilestonesMilestoneIdBurndownEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("milestone_id", milestoneId);

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

        ParameterizedTypeReference<List<APIEntitiesBurndownEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/milestones/{milestone_id}/burndown_events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all burndown chart events for a milestone
     * Lists all burndown chart events for a specified milestone.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @return List&lt;APIEntitiesBurndownEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBurndownEvent> getApiV4GroupsIdMilestonesMilestoneIdBurndownEvents(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBurndownEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMilestonesMilestoneIdBurndownEventsRequestCreation(id, milestoneId).body(localVarReturnType);
    }

    /**
     * List all burndown chart events for a milestone
     * Lists all burndown chart events for a specified milestone.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBurndownEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBurndownEvent>> getApiV4GroupsIdMilestonesMilestoneIdBurndownEventsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBurndownEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMilestonesMilestoneIdBurndownEventsRequestCreation(id, milestoneId).toEntity(localVarReturnType);
    }

    /**
     * List all burndown chart events for a milestone
     * Lists all burndown chart events for a specified milestone.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdMilestonesMilestoneIdBurndownEventsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        return getApiV4GroupsIdMilestonesMilestoneIdBurndownEventsRequestCreation(id, milestoneId);
    }

    /**
     * List all issues assigned to a single milestone
     * Lists all issues assigned to a specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesIssueBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdMilestonesMilestoneIdIssuesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdMilestonesMilestoneIdIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'milestoneId' is set
        if (milestoneId == null) {
            throw new RestClientResponseException("Missing the required parameter 'milestoneId' when calling getApiV4GroupsIdMilestonesMilestoneIdIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("milestone_id", milestoneId);

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

        ParameterizedTypeReference<List<APIEntitiesIssueBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/milestones/{milestone_id}/issues", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all issues assigned to a single milestone
     * Lists all issues assigned to a specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesIssueBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesIssueBasic> getApiV4GroupsIdMilestonesMilestoneIdIssues(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIssueBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMilestonesMilestoneIdIssuesRequestCreation(id, milestoneId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all issues assigned to a single milestone
     * Lists all issues assigned to a specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesIssueBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesIssueBasic>> getApiV4GroupsIdMilestonesMilestoneIdIssuesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIssueBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMilestonesMilestoneIdIssuesRequestCreation(id, milestoneId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all issues assigned to a single milestone
     * Lists all issues assigned to a specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdMilestonesMilestoneIdIssuesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdMilestonesMilestoneIdIssuesRequestCreation(id, milestoneId, page, perPage);
    }

    /**
     * List all merge requests assigned to a single milestone
     * Lists all merge requests assigned to a specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdMilestonesMilestoneIdMergeRequestsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdMilestonesMilestoneIdMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'milestoneId' is set
        if (milestoneId == null) {
            throw new RestClientResponseException("Missing the required parameter 'milestoneId' when calling getApiV4GroupsIdMilestonesMilestoneIdMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("milestone_id", milestoneId);

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

        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/milestones/{milestone_id}/merge_requests", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all merge requests assigned to a single milestone
     * Lists all merge requests assigned to a specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeRequestBasic> getApiV4GroupsIdMilestonesMilestoneIdMergeRequests(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMilestonesMilestoneIdMergeRequestsRequestCreation(id, milestoneId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all merge requests assigned to a single milestone
     * Lists all merge requests assigned to a specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeRequestBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeRequestBasic>> getApiV4GroupsIdMilestonesMilestoneIdMergeRequestsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMilestonesMilestoneIdMergeRequestsRequestCreation(id, milestoneId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all merge requests assigned to a single milestone
     * Lists all merge requests assigned to a specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId ID of the group milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdMilestonesMilestoneIdMergeRequestsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdMilestonesMilestoneIdMergeRequestsRequestCreation(id, milestoneId, page, perPage);
    }

    /**
     * List all project milestones
     * Lists all milestones for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param state Return \&quot;active\&quot;, \&quot;closed\&quot;, or \&quot;all\&quot; milestones
     * @param iids Return only the milestones having the given &#x60;iid&#x60;. Ignored if &#x60;include_ancestors&#x60; is &#x60;true&#x60;.
     * @param title The title of the milestones
     * @param search The search criteria for the title or description of the milestone
     * @param searchTitle The search criteria for the title of the milestone
     * @param includeParentMilestones If &#x60;true&#x60;, includes milestones from all parent groups. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/433298). Use &#x60;include_ancestors&#x60; instead. Mutually exclusive with &#x60;include_ancestors&#x60;.
     * @param includeAncestors Include milestones from all parent groups. Mutually exclusive with &#x60;include_parent_milestones&#x60;.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param containingDate Return milestones where start_date &lt;&#x3D; containing_date &lt;&#x3D; due_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param startDate Return milestones where due_date &gt;&#x3D; start_date. Requires end_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param endDate Return milestones where start_date &lt;&#x3D; end_date. Requires start_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param sort Return milestones sorted in the given order
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMilestone&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMilestonesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String searchTitle, @jakarta.annotation.Nullable Boolean includeParentMilestones, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime containingDate, @jakarta.annotation.Nullable OffsetDateTime startDate, @jakarta.annotation.Nullable OffsetDateTime endDate, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMilestones", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "iids", iids));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "title", title));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search_title", searchTitle));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_parent_milestones", includeParentMilestones));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_ancestors", includeAncestors));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "containing_date", containingDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start_date", startDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "end_date", endDate));
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

        ParameterizedTypeReference<List<APIEntitiesMilestone>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/milestones", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project milestones
     * Lists all milestones for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param state Return \&quot;active\&quot;, \&quot;closed\&quot;, or \&quot;all\&quot; milestones
     * @param iids Return only the milestones having the given &#x60;iid&#x60;. Ignored if &#x60;include_ancestors&#x60; is &#x60;true&#x60;.
     * @param title The title of the milestones
     * @param search The search criteria for the title or description of the milestone
     * @param searchTitle The search criteria for the title of the milestone
     * @param includeParentMilestones If &#x60;true&#x60;, includes milestones from all parent groups. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/433298). Use &#x60;include_ancestors&#x60; instead. Mutually exclusive with &#x60;include_ancestors&#x60;.
     * @param includeAncestors Include milestones from all parent groups. Mutually exclusive with &#x60;include_parent_milestones&#x60;.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param containingDate Return milestones where start_date &lt;&#x3D; containing_date &lt;&#x3D; due_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param startDate Return milestones where due_date &gt;&#x3D; start_date. Requires end_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param endDate Return milestones where start_date &lt;&#x3D; end_date. Requires start_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param sort Return milestones sorted in the given order
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMilestone&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMilestone> getApiV4ProjectsIdMilestones(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String searchTitle, @jakarta.annotation.Nullable Boolean includeParentMilestones, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime containingDate, @jakarta.annotation.Nullable OffsetDateTime startDate, @jakarta.annotation.Nullable OffsetDateTime endDate, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMilestone>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMilestonesRequestCreation(id, state, iids, title, search, searchTitle, includeParentMilestones, includeAncestors, updatedBefore, updatedAfter, containingDate, startDate, endDate, sort, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project milestones
     * Lists all milestones for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param state Return \&quot;active\&quot;, \&quot;closed\&quot;, or \&quot;all\&quot; milestones
     * @param iids Return only the milestones having the given &#x60;iid&#x60;. Ignored if &#x60;include_ancestors&#x60; is &#x60;true&#x60;.
     * @param title The title of the milestones
     * @param search The search criteria for the title or description of the milestone
     * @param searchTitle The search criteria for the title of the milestone
     * @param includeParentMilestones If &#x60;true&#x60;, includes milestones from all parent groups. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/433298). Use &#x60;include_ancestors&#x60; instead. Mutually exclusive with &#x60;include_ancestors&#x60;.
     * @param includeAncestors Include milestones from all parent groups. Mutually exclusive with &#x60;include_parent_milestones&#x60;.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param containingDate Return milestones where start_date &lt;&#x3D; containing_date &lt;&#x3D; due_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param startDate Return milestones where due_date &gt;&#x3D; start_date. Requires end_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param endDate Return milestones where start_date &lt;&#x3D; end_date. Requires start_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param sort Return milestones sorted in the given order
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMilestone&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMilestone>> getApiV4ProjectsIdMilestonesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String searchTitle, @jakarta.annotation.Nullable Boolean includeParentMilestones, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime containingDate, @jakarta.annotation.Nullable OffsetDateTime startDate, @jakarta.annotation.Nullable OffsetDateTime endDate, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMilestone>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMilestonesRequestCreation(id, state, iids, title, search, searchTitle, includeParentMilestones, includeAncestors, updatedBefore, updatedAfter, containingDate, startDate, endDate, sort, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project milestones
     * Lists all milestones for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param state Return \&quot;active\&quot;, \&quot;closed\&quot;, or \&quot;all\&quot; milestones
     * @param iids Return only the milestones having the given &#x60;iid&#x60;. Ignored if &#x60;include_ancestors&#x60; is &#x60;true&#x60;.
     * @param title The title of the milestones
     * @param search The search criteria for the title or description of the milestone
     * @param searchTitle The search criteria for the title of the milestone
     * @param includeParentMilestones If &#x60;true&#x60;, includes milestones from all parent groups. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/433298). Use &#x60;include_ancestors&#x60; instead. Mutually exclusive with &#x60;include_ancestors&#x60;.
     * @param includeAncestors Include milestones from all parent groups. Mutually exclusive with &#x60;include_parent_milestones&#x60;.
     * @param updatedBefore Return milestones updated before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param updatedAfter Return milestones updated after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param containingDate Return milestones where start_date &lt;&#x3D; containing_date &lt;&#x3D; due_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param startDate Return milestones where due_date &gt;&#x3D; start_date. Requires end_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param endDate Return milestones where start_date &lt;&#x3D; end_date. Requires start_date. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param sort Return milestones sorted in the given order
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMilestonesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String searchTitle, @jakarta.annotation.Nullable Boolean includeParentMilestones, @jakarta.annotation.Nullable Boolean includeAncestors, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime containingDate, @jakarta.annotation.Nullable OffsetDateTime startDate, @jakarta.annotation.Nullable OffsetDateTime endDate, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMilestonesRequestCreation(id, state, iids, title, search, searchTitle, includeParentMilestones, includeAncestors, updatedBefore, updatedAfter, containingDate, startDate, endDate, sort, page, perPage);
    }

    /**
     * Retrieve a project milestone
     * Retrieves a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @return APIEntitiesMilestone
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMilestonesMilestoneIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMilestonesMilestoneId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'milestoneId' is set
        if (milestoneId == null) {
            throw new RestClientResponseException("Missing the required parameter 'milestoneId' when calling getApiV4ProjectsIdMilestonesMilestoneId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("milestone_id", milestoneId);

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

        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/milestones/{milestone_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a project milestone
     * Retrieves a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @return APIEntitiesMilestone
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMilestone getApiV4ProjectsIdMilestonesMilestoneId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMilestonesMilestoneIdRequestCreation(id, milestoneId).body(localVarReturnType);
    }

    /**
     * Retrieve a project milestone
     * Retrieves a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @return ResponseEntity&lt;APIEntitiesMilestone&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMilestone> getApiV4ProjectsIdMilestonesMilestoneIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMilestonesMilestoneIdRequestCreation(id, milestoneId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a project milestone
     * Retrieves a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMilestonesMilestoneIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        return getApiV4ProjectsIdMilestonesMilestoneIdRequestCreation(id, milestoneId);
    }

    /**
     * List all burndown chart events for a milestone
     * Lists all burndown chart events for a specified milestone.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @return List&lt;APIEntitiesBurndownEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMilestonesMilestoneIdBurndownEventsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMilestonesMilestoneIdBurndownEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'milestoneId' is set
        if (milestoneId == null) {
            throw new RestClientResponseException("Missing the required parameter 'milestoneId' when calling getApiV4ProjectsIdMilestonesMilestoneIdBurndownEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("milestone_id", milestoneId);

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

        ParameterizedTypeReference<List<APIEntitiesBurndownEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/milestones/{milestone_id}/burndown_events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all burndown chart events for a milestone
     * Lists all burndown chart events for a specified milestone.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @return List&lt;APIEntitiesBurndownEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBurndownEvent> getApiV4ProjectsIdMilestonesMilestoneIdBurndownEvents(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBurndownEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMilestonesMilestoneIdBurndownEventsRequestCreation(id, milestoneId).body(localVarReturnType);
    }

    /**
     * List all burndown chart events for a milestone
     * Lists all burndown chart events for a specified milestone.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBurndownEvent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBurndownEvent>> getApiV4ProjectsIdMilestonesMilestoneIdBurndownEventsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBurndownEvent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMilestonesMilestoneIdBurndownEventsRequestCreation(id, milestoneId).toEntity(localVarReturnType);
    }

    /**
     * List all burndown chart events for a milestone
     * Lists all burndown chart events for a specified milestone.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMilestonesMilestoneIdBurndownEventsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        return getApiV4ProjectsIdMilestonesMilestoneIdBurndownEventsRequestCreation(id, milestoneId);
    }

    /**
     * List all issues for a project milestone
     * Lists all issues assigned to a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesIssueBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMilestonesMilestoneIdIssuesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMilestonesMilestoneIdIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'milestoneId' is set
        if (milestoneId == null) {
            throw new RestClientResponseException("Missing the required parameter 'milestoneId' when calling getApiV4ProjectsIdMilestonesMilestoneIdIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("milestone_id", milestoneId);

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

        ParameterizedTypeReference<List<APIEntitiesIssueBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/milestones/{milestone_id}/issues", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all issues for a project milestone
     * Lists all issues assigned to a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesIssueBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesIssueBasic> getApiV4ProjectsIdMilestonesMilestoneIdIssues(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIssueBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMilestonesMilestoneIdIssuesRequestCreation(id, milestoneId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all issues for a project milestone
     * Lists all issues assigned to a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesIssueBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesIssueBasic>> getApiV4ProjectsIdMilestonesMilestoneIdIssuesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIssueBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMilestonesMilestoneIdIssuesRequestCreation(id, milestoneId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all issues for a project milestone
     * Lists all issues assigned to a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMilestonesMilestoneIdIssuesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMilestonesMilestoneIdIssuesRequestCreation(id, milestoneId, page, perPage);
    }

    /**
     * List all merge requests for a project milestone
     * Lists all merge requests assigned to a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMilestonesMilestoneIdMergeRequestsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMilestonesMilestoneIdMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'milestoneId' is set
        if (milestoneId == null) {
            throw new RestClientResponseException("Missing the required parameter 'milestoneId' when calling getApiV4ProjectsIdMilestonesMilestoneIdMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("milestone_id", milestoneId);

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

        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/milestones/{milestone_id}/merge_requests", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all merge requests for a project milestone
     * Lists all merge requests assigned to a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeRequestBasic> getApiV4ProjectsIdMilestonesMilestoneIdMergeRequests(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMilestonesMilestoneIdMergeRequestsRequestCreation(id, milestoneId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all merge requests for a project milestone
     * Lists all merge requests assigned to a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeRequestBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeRequestBasic>> getApiV4ProjectsIdMilestonesMilestoneIdMergeRequestsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMilestonesMilestoneIdMergeRequestsRequestCreation(id, milestoneId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all merge requests for a project milestone
     * Lists all merge requests assigned to a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMilestonesMilestoneIdMergeRequestsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMilestonesMilestoneIdMergeRequestsRequestCreation(id, milestoneId, page, perPage);
    }

    /**
     * Create a group milestone
     * Creates a milestone for the specified group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdMilestonesRequest The postApiV4GroupsIdMilestonesRequest parameter
     * @return APIEntitiesMilestone
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdMilestonesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdMilestonesRequest postApiV4GroupsIdMilestonesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdMilestones", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdMilestonesRequest' is set
        if (postApiV4GroupsIdMilestonesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdMilestonesRequest' when calling postApiV4GroupsIdMilestones", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/milestones", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdMilestonesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group milestone
     * Creates a milestone for the specified group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdMilestonesRequest The postApiV4GroupsIdMilestonesRequest parameter
     * @return APIEntitiesMilestone
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMilestone postApiV4GroupsIdMilestones(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdMilestonesRequest postApiV4GroupsIdMilestonesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdMilestonesRequestCreation(id, postApiV4GroupsIdMilestonesRequest).body(localVarReturnType);
    }

    /**
     * Create a group milestone
     * Creates a milestone for the specified group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdMilestonesRequest The postApiV4GroupsIdMilestonesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMilestone&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMilestone> postApiV4GroupsIdMilestonesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdMilestonesRequest postApiV4GroupsIdMilestonesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdMilestonesRequestCreation(id, postApiV4GroupsIdMilestonesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a group milestone
     * Creates a milestone for the specified group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdMilestonesRequest The postApiV4GroupsIdMilestonesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdMilestonesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdMilestonesRequest postApiV4GroupsIdMilestonesRequest) throws RestClientResponseException {
        return postApiV4GroupsIdMilestonesRequestCreation(id, postApiV4GroupsIdMilestonesRequest);
    }

    /**
     * Create a project milestone
     * Creates a project milestone.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMilestonesRequest The postApiV4ProjectsIdMilestonesRequest parameter
     * @return APIEntitiesMilestone
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMilestonesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMilestonesRequest postApiV4ProjectsIdMilestonesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMilestones", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMilestonesRequest' is set
        if (postApiV4ProjectsIdMilestonesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMilestonesRequest' when calling postApiV4ProjectsIdMilestones", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/milestones", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMilestonesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a project milestone
     * Creates a project milestone.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMilestonesRequest The postApiV4ProjectsIdMilestonesRequest parameter
     * @return APIEntitiesMilestone
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMilestone postApiV4ProjectsIdMilestones(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMilestonesRequest postApiV4ProjectsIdMilestonesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMilestonesRequestCreation(id, postApiV4ProjectsIdMilestonesRequest).body(localVarReturnType);
    }

    /**
     * Create a project milestone
     * Creates a project milestone.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMilestonesRequest The postApiV4ProjectsIdMilestonesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMilestone&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMilestone> postApiV4ProjectsIdMilestonesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMilestonesRequest postApiV4ProjectsIdMilestonesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMilestonesRequestCreation(id, postApiV4ProjectsIdMilestonesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a project milestone
     * Creates a project milestone.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMilestonesRequest The postApiV4ProjectsIdMilestonesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMilestonesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMilestonesRequest postApiV4ProjectsIdMilestonesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMilestonesRequestCreation(id, postApiV4ProjectsIdMilestonesRequest);
    }

    /**
     * Promote a project milestone to a group milestone
     * Promotes a specified project milestone to a group milestone. Only for users with the Planner, Reporter, Developer, Maintainer, or Owner role for the group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMilestonesMilestoneIdPromoteRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMilestonesMilestoneIdPromote", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'milestoneId' is set
        if (milestoneId == null) {
            throw new RestClientResponseException("Missing the required parameter 'milestoneId' when calling postApiV4ProjectsIdMilestonesMilestoneIdPromote", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("milestone_id", milestoneId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/milestones/{milestone_id}/promote", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Promote a project milestone to a group milestone
     * Promotes a specified project milestone to a group milestone. Only for users with the Planner, Reporter, Developer, Maintainer, or Owner role for the group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdMilestonesMilestoneIdPromote(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdMilestonesMilestoneIdPromoteRequestCreation(id, milestoneId).body(localVarReturnType);
    }

    /**
     * Promote a project milestone to a group milestone
     * Promotes a specified project milestone to a group milestone. Only for users with the Planner, Reporter, Developer, Maintainer, or Owner role for the group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdMilestonesMilestoneIdPromoteWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMilestonesMilestoneIdPromoteRequestCreation(id, milestoneId).toEntity(localVarReturnType);
    }

    /**
     * Promote a project milestone to a group milestone
     * Promotes a specified project milestone to a group milestone. Only for users with the Planner, Reporter, Developer, Maintainer, or Owner role for the group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId ID of the project milestone.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMilestonesMilestoneIdPromoteWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId) throws RestClientResponseException {
        return postApiV4ProjectsIdMilestonesMilestoneIdPromoteRequestCreation(id, milestoneId);
    }

    /**
     * Update a group milestone
     * Updates the specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId The milestone ID number
     * @param putApiV4GroupsIdMilestonesMilestoneIdRequest The putApiV4GroupsIdMilestonesMilestoneIdRequest parameter
     * @return APIEntitiesMilestone
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdMilestonesMilestoneIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable PutApiV4GroupsIdMilestonesMilestoneIdRequest putApiV4GroupsIdMilestonesMilestoneIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdMilestonesMilestoneId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'milestoneId' is set
        if (milestoneId == null) {
            throw new RestClientResponseException("Missing the required parameter 'milestoneId' when calling putApiV4GroupsIdMilestonesMilestoneId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("milestone_id", milestoneId);

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

        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/milestones/{milestone_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdMilestonesMilestoneIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a group milestone
     * Updates the specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId The milestone ID number
     * @param putApiV4GroupsIdMilestonesMilestoneIdRequest The putApiV4GroupsIdMilestonesMilestoneIdRequest parameter
     * @return APIEntitiesMilestone
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMilestone putApiV4GroupsIdMilestonesMilestoneId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable PutApiV4GroupsIdMilestonesMilestoneIdRequest putApiV4GroupsIdMilestonesMilestoneIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdMilestonesMilestoneIdRequestCreation(id, milestoneId, putApiV4GroupsIdMilestonesMilestoneIdRequest).body(localVarReturnType);
    }

    /**
     * Update a group milestone
     * Updates the specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId The milestone ID number
     * @param putApiV4GroupsIdMilestonesMilestoneIdRequest The putApiV4GroupsIdMilestonesMilestoneIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMilestone&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMilestone> putApiV4GroupsIdMilestonesMilestoneIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable PutApiV4GroupsIdMilestonesMilestoneIdRequest putApiV4GroupsIdMilestonesMilestoneIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdMilestonesMilestoneIdRequestCreation(id, milestoneId, putApiV4GroupsIdMilestonesMilestoneIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a group milestone
     * Updates the specified group milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param milestoneId The milestone ID number
     * @param putApiV4GroupsIdMilestonesMilestoneIdRequest The putApiV4GroupsIdMilestonesMilestoneIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdMilestonesMilestoneIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable PutApiV4GroupsIdMilestonesMilestoneIdRequest putApiV4GroupsIdMilestonesMilestoneIdRequest) throws RestClientResponseException {
        return putApiV4GroupsIdMilestonesMilestoneIdRequestCreation(id, milestoneId, putApiV4GroupsIdMilestonesMilestoneIdRequest);
    }

    /**
     * Update a project milestone
     * Updates a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId The milestone ID number
     * @param putApiV4ProjectsIdMilestonesMilestoneIdRequest The putApiV4ProjectsIdMilestonesMilestoneIdRequest parameter
     * @return APIEntitiesMilestone
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdMilestonesMilestoneIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMilestonesMilestoneIdRequest putApiV4ProjectsIdMilestonesMilestoneIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdMilestonesMilestoneId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'milestoneId' is set
        if (milestoneId == null) {
            throw new RestClientResponseException("Missing the required parameter 'milestoneId' when calling putApiV4ProjectsIdMilestonesMilestoneId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("milestone_id", milestoneId);

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

        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/milestones/{milestone_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdMilestonesMilestoneIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a project milestone
     * Updates a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId The milestone ID number
     * @param putApiV4ProjectsIdMilestonesMilestoneIdRequest The putApiV4ProjectsIdMilestonesMilestoneIdRequest parameter
     * @return APIEntitiesMilestone
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMilestone putApiV4ProjectsIdMilestonesMilestoneId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMilestonesMilestoneIdRequest putApiV4ProjectsIdMilestonesMilestoneIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMilestonesMilestoneIdRequestCreation(id, milestoneId, putApiV4ProjectsIdMilestonesMilestoneIdRequest).body(localVarReturnType);
    }

    /**
     * Update a project milestone
     * Updates a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId The milestone ID number
     * @param putApiV4ProjectsIdMilestonesMilestoneIdRequest The putApiV4ProjectsIdMilestonesMilestoneIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMilestone&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMilestone> putApiV4ProjectsIdMilestonesMilestoneIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMilestonesMilestoneIdRequest putApiV4ProjectsIdMilestonesMilestoneIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMilestone> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMilestonesMilestoneIdRequestCreation(id, milestoneId, putApiV4ProjectsIdMilestonesMilestoneIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a project milestone
     * Updates a specified project milestone.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param milestoneId The milestone ID number
     * @param putApiV4ProjectsIdMilestonesMilestoneIdRequest The putApiV4ProjectsIdMilestonesMilestoneIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdMilestonesMilestoneIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer milestoneId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMilestonesMilestoneIdRequest putApiV4ProjectsIdMilestonesMilestoneIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdMilestonesMilestoneIdRequestCreation(id, milestoneId, putApiV4ProjectsIdMilestonesMilestoneIdRequest);
    }
}
