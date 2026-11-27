package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesGroupLabel;
import ai.gebo.gitlab.client.model.APIEntitiesProjectLabel;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdLabelsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdLabelsRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdLabelsNameRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdLabelsRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdLabelsNameRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdLabelsPromoteRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdLabelsRequest;

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
public class LabelsApi {
    private ApiClient apiClient;

    public LabelsApi() {
        this(new ApiClient());
    }

    public LabelsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a group label
     * Deletes a specified group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the label to delete.
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdLabelsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdLabels", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling deleteApiV4GroupsIdLabels", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/labels", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a group label
     * Deletes a specified group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the label to delete.
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupLabel deleteApiV4GroupsIdLabels(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdLabelsRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Delete a group label
     * Deletes a specified group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the label to delete.
     * @return ResponseEntity&lt;APIEntitiesGroupLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupLabel> deleteApiV4GroupsIdLabelsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdLabelsRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Delete a group label
     * Deletes a specified group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the label to delete.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdLabelsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        return deleteApiV4GroupsIdLabelsRequestCreation(id, name);
    }

    /**
     * Delete a group label
     * Deletes a specified group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name or ID of the label to delete.
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdLabelsNameRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdLabelsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling deleteApiV4GroupsIdLabelsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);

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

        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/labels/{name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a group label
     * Deletes a specified group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name or ID of the label to delete.
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupLabel deleteApiV4GroupsIdLabelsName(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdLabelsNameRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Delete a group label
     * Deletes a specified group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name or ID of the label to delete.
     * @return ResponseEntity&lt;APIEntitiesGroupLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupLabel> deleteApiV4GroupsIdLabelsNameWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdLabelsNameRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Delete a group label
     * Deletes a specified group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name or ID of the label to delete.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdLabelsNameWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return deleteApiV4GroupsIdLabelsNameRequestCreation(id, name);
    }

    /**
     * Delete an existing label
     * Deprecated in GitLab 12.4. Use DELETE /projects/:id/labels/:name instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param labelId ID of the label to delete. Exactly one of &#x60;label_id&#x60;, &#x60;name&#x60; must be provided.
     * @param name Name of the label to delete. Exactly one of &#x60;label_id&#x60;, &#x60;name&#x60; must be provided.
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdLabelsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer labelId, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdLabels", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "label_id", labelId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/labels", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an existing label
     * Deprecated in GitLab 12.4. Use DELETE /projects/:id/labels/:name instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param labelId ID of the label to delete. Exactly one of &#x60;label_id&#x60;, &#x60;name&#x60; must be provided.
     * @param name Name of the label to delete. Exactly one of &#x60;label_id&#x60;, &#x60;name&#x60; must be provided.
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public APIEntitiesProjectLabel deleteApiV4ProjectsIdLabels(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer labelId, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdLabelsRequestCreation(id, labelId, name).body(localVarReturnType);
    }

    /**
     * Delete an existing label
     * Deprecated in GitLab 12.4. Use DELETE /projects/:id/labels/:name instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param labelId ID of the label to delete. Exactly one of &#x60;label_id&#x60;, &#x60;name&#x60; must be provided.
     * @param name Name of the label to delete. Exactly one of &#x60;label_id&#x60;, &#x60;name&#x60; must be provided.
     * @return ResponseEntity&lt;APIEntitiesProjectLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<APIEntitiesProjectLabel> deleteApiV4ProjectsIdLabelsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer labelId, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdLabelsRequestCreation(id, labelId, name).toEntity(localVarReturnType);
    }

    /**
     * Delete an existing label
     * Deprecated in GitLab 12.4. Use DELETE /projects/:id/labels/:name instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param labelId ID of the label to delete. Exactly one of &#x60;label_id&#x60;, &#x60;name&#x60; must be provided.
     * @param name Name of the label to delete. Exactly one of &#x60;label_id&#x60;, &#x60;name&#x60; must be provided.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec deleteApiV4ProjectsIdLabelsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer labelId, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        return deleteApiV4ProjectsIdLabelsRequestCreation(id, labelId, name);
    }

    /**
     * Delete a project label
     * Deletes a specified label from a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name or ID of the label to delete.
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdLabelsNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdLabelsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling deleteApiV4ProjectsIdLabelsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);

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

        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/labels/{name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a project label
     * Deletes a specified label from a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name or ID of the label to delete.
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectLabel deleteApiV4ProjectsIdLabelsName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdLabelsNameRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Delete a project label
     * Deletes a specified label from a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name or ID of the label to delete.
     * @return ResponseEntity&lt;APIEntitiesProjectLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectLabel> deleteApiV4ProjectsIdLabelsNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdLabelsNameRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Delete a project label
     * Deletes a specified label from a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name or ID of the label to delete.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdLabelsNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return deleteApiV4ProjectsIdLabelsNameRequestCreation(id, name);
    }

    /**
     * List all group labels
     * Lists all group labels for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param withCounts If &#x60;true&#x60;, includes issue and merge request counts.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes descendant groups.
     * @param onlyGroupLabels If &#x60;true&#x60;, returns only group labels. If &#x60;false&#x60;, also returns project labels.
     * @param search Keyword to filter labels by. Returns labels with an exact, case-insensitive name match first.
     * @param archived If &#x60;true&#x60;, returns only archived labels. If &#x60;false&#x60;, returns only non-archived labels. If omitted, returns all labels. Introduced in GitLab 18.10.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGroupLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdLabelsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean withCounts, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable Boolean onlyGroupLabels, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdLabels", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_counts", withCounts));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_ancestor_groups", includeAncestorGroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_descendant_groups", includeDescendantGroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "only_group_labels", onlyGroupLabels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "archived", archived));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesGroupLabel>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/labels", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group labels
     * Lists all group labels for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param withCounts If &#x60;true&#x60;, includes issue and merge request counts.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes descendant groups.
     * @param onlyGroupLabels If &#x60;true&#x60;, returns only group labels. If &#x60;false&#x60;, also returns project labels.
     * @param search Keyword to filter labels by. Returns labels with an exact, case-insensitive name match first.
     * @param archived If &#x60;true&#x60;, returns only archived labels. If &#x60;false&#x60;, returns only non-archived labels. If omitted, returns all labels. Introduced in GitLab 18.10.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGroupLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGroupLabel> getApiV4GroupsIdLabels(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean withCounts, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable Boolean onlyGroupLabels, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroupLabel>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdLabelsRequestCreation(id, withCounts, includeAncestorGroups, includeDescendantGroups, onlyGroupLabels, search, archived, page, perPage).body(localVarReturnType);
    }

    /**
     * List all group labels
     * Lists all group labels for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param withCounts If &#x60;true&#x60;, includes issue and merge request counts.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes descendant groups.
     * @param onlyGroupLabels If &#x60;true&#x60;, returns only group labels. If &#x60;false&#x60;, also returns project labels.
     * @param search Keyword to filter labels by. Returns labels with an exact, case-insensitive name match first.
     * @param archived If &#x60;true&#x60;, returns only archived labels. If &#x60;false&#x60;, returns only non-archived labels. If omitted, returns all labels. Introduced in GitLab 18.10.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGroupLabel&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGroupLabel>> getApiV4GroupsIdLabelsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean withCounts, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable Boolean onlyGroupLabels, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroupLabel>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdLabelsRequestCreation(id, withCounts, includeAncestorGroups, includeDescendantGroups, onlyGroupLabels, search, archived, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group labels
     * Lists all group labels for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param withCounts If &#x60;true&#x60;, includes issue and merge request counts.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes descendant groups.
     * @param onlyGroupLabels If &#x60;true&#x60;, returns only group labels. If &#x60;false&#x60;, also returns project labels.
     * @param search Keyword to filter labels by. Returns labels with an exact, case-insensitive name match first.
     * @param archived If &#x60;true&#x60;, returns only archived labels. If &#x60;false&#x60;, returns only non-archived labels. If omitted, returns all labels. Introduced in GitLab 18.10.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdLabelsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean withCounts, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable Boolean onlyGroupLabels, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdLabelsRequestCreation(id, withCounts, includeAncestorGroups, includeDescendantGroups, onlyGroupLabels, search, archived, page, perPage);
    }

    /**
     * Retrieve a group label
     * Retrieves a specified group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name ID or name of the label.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes descendant groups.
     * @param onlyGroupLabels If &#x60;true&#x60;, returns only group labels. If &#x60;false&#x60;, also returns project labels.
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdLabelsNameRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter name, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable Boolean onlyGroupLabels) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdLabelsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4GroupsIdLabelsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_ancestor_groups", includeAncestorGroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_descendant_groups", includeDescendantGroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "only_group_labels", onlyGroupLabels));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/labels/{name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group label
     * Retrieves a specified group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name ID or name of the label.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes descendant groups.
     * @param onlyGroupLabels If &#x60;true&#x60;, returns only group labels. If &#x60;false&#x60;, also returns project labels.
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupLabel getApiV4GroupsIdLabelsName(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter name, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable Boolean onlyGroupLabels) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdLabelsNameRequestCreation(id, name, includeAncestorGroups, includeDescendantGroups, onlyGroupLabels).body(localVarReturnType);
    }

    /**
     * Retrieve a group label
     * Retrieves a specified group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name ID or name of the label.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes descendant groups.
     * @param onlyGroupLabels If &#x60;true&#x60;, returns only group labels. If &#x60;false&#x60;, also returns project labels.
     * @return ResponseEntity&lt;APIEntitiesGroupLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupLabel> getApiV4GroupsIdLabelsNameWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter name, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable Boolean onlyGroupLabels) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdLabelsNameRequestCreation(id, name, includeAncestorGroups, includeDescendantGroups, onlyGroupLabels).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group label
     * Retrieves a specified group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name ID or name of the label.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes descendant groups.
     * @param onlyGroupLabels If &#x60;true&#x60;, returns only group labels. If &#x60;false&#x60;, also returns project labels.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdLabelsNameWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter name, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable Boolean onlyGroupLabels) throws RestClientResponseException {
        return getApiV4GroupsIdLabelsNameRequestCreation(id, name, includeAncestorGroups, includeDescendantGroups, onlyGroupLabels);
    }

    /**
     * List all project labels
     * Lists all labels for a specified project. By default, this request returns 20 results at a time because the API results are paginated.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param withCounts If &#x60;true&#x60;, includes issue and merge request counts.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @param search Keyword to filter labels by. Returns labels with an exact, case-insensitive name match first.
     * @param archived If &#x60;true&#x60;, returns only archived labels. If &#x60;false&#x60;, returns only non-archived labels. If omitted, returns all labels. Introduced in GitLab 18.10.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdLabelsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean withCounts, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdLabels", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_counts", withCounts));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_ancestor_groups", includeAncestorGroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "archived", archived));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesProjectLabel>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/labels", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project labels
     * Lists all labels for a specified project. By default, this request returns 20 results at a time because the API results are paginated.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param withCounts If &#x60;true&#x60;, includes issue and merge request counts.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @param search Keyword to filter labels by. Returns labels with an exact, case-insensitive name match first.
     * @param archived If &#x60;true&#x60;, returns only archived labels. If &#x60;false&#x60;, returns only non-archived labels. If omitted, returns all labels. Introduced in GitLab 18.10.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesProjectLabel> getApiV4ProjectsIdLabels(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean withCounts, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectLabel>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdLabelsRequestCreation(id, withCounts, includeAncestorGroups, search, archived, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project labels
     * Lists all labels for a specified project. By default, this request returns 20 results at a time because the API results are paginated.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param withCounts If &#x60;true&#x60;, includes issue and merge request counts.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @param search Keyword to filter labels by. Returns labels with an exact, case-insensitive name match first.
     * @param archived If &#x60;true&#x60;, returns only archived labels. If &#x60;false&#x60;, returns only non-archived labels. If omitted, returns all labels. Introduced in GitLab 18.10.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesProjectLabel&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesProjectLabel>> getApiV4ProjectsIdLabelsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean withCounts, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectLabel>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdLabelsRequestCreation(id, withCounts, includeAncestorGroups, search, archived, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project labels
     * Lists all labels for a specified project. By default, this request returns 20 results at a time because the API results are paginated.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param withCounts If &#x60;true&#x60;, includes issue and merge request counts.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @param search Keyword to filter labels by. Returns labels with an exact, case-insensitive name match first.
     * @param archived If &#x60;true&#x60;, returns only archived labels. If &#x60;false&#x60;, returns only non-archived labels. If omitted, returns all labels. Introduced in GitLab 18.10.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdLabelsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean withCounts, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdLabelsRequestCreation(id, withCounts, includeAncestorGroups, search, archived, page, perPage);
    }

    /**
     * Retrieve a project label
     * Retrieves a specified label for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name ID or name of the label.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdLabelsNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter name, @jakarta.annotation.Nullable Boolean includeAncestorGroups) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdLabelsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4ProjectsIdLabelsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_ancestor_groups", includeAncestorGroups));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/labels/{name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a project label
     * Retrieves a specified label for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name ID or name of the label.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectLabel getApiV4ProjectsIdLabelsName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter name, @jakarta.annotation.Nullable Boolean includeAncestorGroups) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdLabelsNameRequestCreation(id, name, includeAncestorGroups).body(localVarReturnType);
    }

    /**
     * Retrieve a project label
     * Retrieves a specified label for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name ID or name of the label.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @return ResponseEntity&lt;APIEntitiesProjectLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectLabel> getApiV4ProjectsIdLabelsNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter name, @jakarta.annotation.Nullable Boolean includeAncestorGroups) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdLabelsNameRequestCreation(id, name, includeAncestorGroups).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a project label
     * Retrieves a specified label for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name ID or name of the label.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes ancestor groups.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdLabelsNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter name, @jakarta.annotation.Nullable Boolean includeAncestorGroups) throws RestClientResponseException {
        return getApiV4ProjectsIdLabelsNameRequestCreation(id, name, includeAncestorGroups);
    }

    /**
     * Create a group label
     * Creates a group label.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdLabelsRequest The postApiV4GroupsIdLabelsRequest parameter
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdLabelsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdLabelsRequest postApiV4GroupsIdLabelsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdLabels", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdLabelsRequest' is set
        if (postApiV4GroupsIdLabelsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdLabelsRequest' when calling postApiV4GroupsIdLabels", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/labels", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdLabelsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group label
     * Creates a group label.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdLabelsRequest The postApiV4GroupsIdLabelsRequest parameter
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupLabel postApiV4GroupsIdLabels(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdLabelsRequest postApiV4GroupsIdLabelsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdLabelsRequestCreation(id, postApiV4GroupsIdLabelsRequest).body(localVarReturnType);
    }

    /**
     * Create a group label
     * Creates a group label.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdLabelsRequest The postApiV4GroupsIdLabelsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupLabel> postApiV4GroupsIdLabelsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdLabelsRequest postApiV4GroupsIdLabelsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdLabelsRequestCreation(id, postApiV4GroupsIdLabelsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a group label
     * Creates a group label.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdLabelsRequest The postApiV4GroupsIdLabelsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdLabelsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdLabelsRequest postApiV4GroupsIdLabelsRequest) throws RestClientResponseException {
        return postApiV4GroupsIdLabelsRequestCreation(id, postApiV4GroupsIdLabelsRequest);
    }

    /**
     * Create a project label
     * Creates a label for a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdLabelsRequest The postApiV4ProjectsIdLabelsRequest parameter
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdLabelsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdLabelsRequest postApiV4ProjectsIdLabelsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdLabels", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdLabelsRequest' is set
        if (postApiV4ProjectsIdLabelsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdLabelsRequest' when calling postApiV4ProjectsIdLabels", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/labels", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdLabelsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a project label
     * Creates a label for a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdLabelsRequest The postApiV4ProjectsIdLabelsRequest parameter
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectLabel postApiV4ProjectsIdLabels(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdLabelsRequest postApiV4ProjectsIdLabelsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdLabelsRequestCreation(id, postApiV4ProjectsIdLabelsRequest).body(localVarReturnType);
    }

    /**
     * Create a project label
     * Creates a label for a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdLabelsRequest The postApiV4ProjectsIdLabelsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectLabel> postApiV4ProjectsIdLabelsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdLabelsRequest postApiV4ProjectsIdLabelsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdLabelsRequestCreation(id, postApiV4ProjectsIdLabelsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a project label
     * Creates a label for a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdLabelsRequest The postApiV4ProjectsIdLabelsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdLabelsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdLabelsRequest postApiV4ProjectsIdLabelsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdLabelsRequestCreation(id, postApiV4ProjectsIdLabelsRequest);
    }

    /**
     * Update a group label
     * Updates an existing group label. At least one parameter is required to update the group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdLabelsRequest The putApiV4GroupsIdLabelsRequest parameter
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdLabelsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdLabelsRequest putApiV4GroupsIdLabelsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdLabels", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/labels", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdLabelsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a group label
     * Updates an existing group label. At least one parameter is required to update the group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdLabelsRequest The putApiV4GroupsIdLabelsRequest parameter
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupLabel putApiV4GroupsIdLabels(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdLabelsRequest putApiV4GroupsIdLabelsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdLabelsRequestCreation(id, putApiV4GroupsIdLabelsRequest).body(localVarReturnType);
    }

    /**
     * Update a group label
     * Updates an existing group label. At least one parameter is required to update the group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdLabelsRequest The putApiV4GroupsIdLabelsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupLabel> putApiV4GroupsIdLabelsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdLabelsRequest putApiV4GroupsIdLabelsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdLabelsRequestCreation(id, putApiV4GroupsIdLabelsRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a group label
     * Updates an existing group label. At least one parameter is required to update the group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdLabelsRequest The putApiV4GroupsIdLabelsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdLabelsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdLabelsRequest putApiV4GroupsIdLabelsRequest) throws RestClientResponseException {
        return putApiV4GroupsIdLabelsRequestCreation(id, putApiV4GroupsIdLabelsRequest);
    }

    /**
     * Update a group label
     * Updates a specified group label. At least one parameter is required to update the group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name or ID of the label to update.
     * @param putApiV4GroupsIdLabelsNameRequest The putApiV4GroupsIdLabelsNameRequest parameter
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdLabelsNameRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4GroupsIdLabelsNameRequest putApiV4GroupsIdLabelsNameRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdLabelsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling putApiV4GroupsIdLabelsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);

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

        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/labels/{name}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdLabelsNameRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a group label
     * Updates a specified group label. At least one parameter is required to update the group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name or ID of the label to update.
     * @param putApiV4GroupsIdLabelsNameRequest The putApiV4GroupsIdLabelsNameRequest parameter
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupLabel putApiV4GroupsIdLabelsName(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4GroupsIdLabelsNameRequest putApiV4GroupsIdLabelsNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdLabelsNameRequestCreation(id, name, putApiV4GroupsIdLabelsNameRequest).body(localVarReturnType);
    }

    /**
     * Update a group label
     * Updates a specified group label. At least one parameter is required to update the group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name or ID of the label to update.
     * @param putApiV4GroupsIdLabelsNameRequest The putApiV4GroupsIdLabelsNameRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupLabel> putApiV4GroupsIdLabelsNameWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4GroupsIdLabelsNameRequest putApiV4GroupsIdLabelsNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdLabelsNameRequestCreation(id, name, putApiV4GroupsIdLabelsNameRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a group label
     * Updates a specified group label. At least one parameter is required to update the group label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name or ID of the label to update.
     * @param putApiV4GroupsIdLabelsNameRequest The putApiV4GroupsIdLabelsNameRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdLabelsNameWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4GroupsIdLabelsNameRequest putApiV4GroupsIdLabelsNameRequest) throws RestClientResponseException {
        return putApiV4GroupsIdLabelsNameRequestCreation(id, name, putApiV4GroupsIdLabelsNameRequest);
    }

    /**
     * Update an existing label. At least one optional parameter is required.
     * Deprecated in GitLab 12.4. Use PUT /projects/:id/labels/:name instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdLabelsRequest The putApiV4ProjectsIdLabelsRequest parameter
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdLabelsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdLabelsRequest putApiV4ProjectsIdLabelsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdLabels", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/labels", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdLabelsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an existing label. At least one optional parameter is required.
     * Deprecated in GitLab 12.4. Use PUT /projects/:id/labels/:name instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdLabelsRequest The putApiV4ProjectsIdLabelsRequest parameter
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public APIEntitiesProjectLabel putApiV4ProjectsIdLabels(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdLabelsRequest putApiV4ProjectsIdLabelsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdLabelsRequestCreation(id, putApiV4ProjectsIdLabelsRequest).body(localVarReturnType);
    }

    /**
     * Update an existing label. At least one optional parameter is required.
     * Deprecated in GitLab 12.4. Use PUT /projects/:id/labels/:name instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdLabelsRequest The putApiV4ProjectsIdLabelsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<APIEntitiesProjectLabel> putApiV4ProjectsIdLabelsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdLabelsRequest putApiV4ProjectsIdLabelsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdLabelsRequestCreation(id, putApiV4ProjectsIdLabelsRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an existing label. At least one optional parameter is required.
     * Deprecated in GitLab 12.4. Use PUT /projects/:id/labels/:name instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdLabelsRequest The putApiV4ProjectsIdLabelsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec putApiV4ProjectsIdLabelsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdLabelsRequest putApiV4ProjectsIdLabelsRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdLabelsRequestCreation(id, putApiV4ProjectsIdLabelsRequest);
    }

    /**
     * Update a project label
     * Updates a specified label for a project with a different name or color. At least one parameter is required to update the label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name or ID of the label to update.
     * @param putApiV4ProjectsIdLabelsNameRequest The putApiV4ProjectsIdLabelsNameRequest parameter
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdLabelsNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4ProjectsIdLabelsNameRequest putApiV4ProjectsIdLabelsNameRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdLabelsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling putApiV4ProjectsIdLabelsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);

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

        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/labels/{name}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdLabelsNameRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a project label
     * Updates a specified label for a project with a different name or color. At least one parameter is required to update the label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name or ID of the label to update.
     * @param putApiV4ProjectsIdLabelsNameRequest The putApiV4ProjectsIdLabelsNameRequest parameter
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectLabel putApiV4ProjectsIdLabelsName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4ProjectsIdLabelsNameRequest putApiV4ProjectsIdLabelsNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdLabelsNameRequestCreation(id, name, putApiV4ProjectsIdLabelsNameRequest).body(localVarReturnType);
    }

    /**
     * Update a project label
     * Updates a specified label for a project with a different name or color. At least one parameter is required to update the label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name or ID of the label to update.
     * @param putApiV4ProjectsIdLabelsNameRequest The putApiV4ProjectsIdLabelsNameRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectLabel> putApiV4ProjectsIdLabelsNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4ProjectsIdLabelsNameRequest putApiV4ProjectsIdLabelsNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdLabelsNameRequestCreation(id, name, putApiV4ProjectsIdLabelsNameRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a project label
     * Updates a specified label for a project with a different name or color. At least one parameter is required to update the label.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name or ID of the label to update.
     * @param putApiV4ProjectsIdLabelsNameRequest The putApiV4ProjectsIdLabelsNameRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdLabelsNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4ProjectsIdLabelsNameRequest putApiV4ProjectsIdLabelsNameRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdLabelsNameRequestCreation(id, name, putApiV4ProjectsIdLabelsNameRequest);
    }

    /**
     * Promote a project label to a group label
     * Promotes a specified project label to a group label. The label keeps its ID.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name or ID of the label to promote.
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdLabelsNamePromoteRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdLabelsNamePromote", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling putApiV4ProjectsIdLabelsNamePromote", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);

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

        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/labels/{name}/promote", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Promote a project label to a group label
     * Promotes a specified project label to a group label. The label keeps its ID.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name or ID of the label to promote.
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupLabel putApiV4ProjectsIdLabelsNamePromote(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdLabelsNamePromoteRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Promote a project label to a group label
     * Promotes a specified project label to a group label. The label keeps its ID.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name or ID of the label to promote.
     * @return ResponseEntity&lt;APIEntitiesGroupLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupLabel> putApiV4ProjectsIdLabelsNamePromoteWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdLabelsNamePromoteRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Promote a project label to a group label
     * Promotes a specified project label to a group label. The label keeps its ID.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name or ID of the label to promote.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdLabelsNamePromoteWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return putApiV4ProjectsIdLabelsNamePromoteRequestCreation(id, name);
    }

    /**
     * Promote a label to a group label
     * Added in GitLab 12.3 and deprecated in GitLab 12.4. Use PUT /projects/:id/labels/:name/promote instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdLabelsPromoteRequest The putApiV4ProjectsIdLabelsPromoteRequest parameter
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdLabelsPromoteRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdLabelsPromoteRequest putApiV4ProjectsIdLabelsPromoteRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdLabelsPromote", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4ProjectsIdLabelsPromoteRequest' is set
        if (putApiV4ProjectsIdLabelsPromoteRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ProjectsIdLabelsPromoteRequest' when calling putApiV4ProjectsIdLabelsPromote", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/labels/promote", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdLabelsPromoteRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Promote a label to a group label
     * Added in GitLab 12.3 and deprecated in GitLab 12.4. Use PUT /projects/:id/labels/:name/promote instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdLabelsPromoteRequest The putApiV4ProjectsIdLabelsPromoteRequest parameter
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public APIEntitiesGroupLabel putApiV4ProjectsIdLabelsPromote(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdLabelsPromoteRequest putApiV4ProjectsIdLabelsPromoteRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdLabelsPromoteRequestCreation(id, putApiV4ProjectsIdLabelsPromoteRequest).body(localVarReturnType);
    }

    /**
     * Promote a label to a group label
     * Added in GitLab 12.3 and deprecated in GitLab 12.4. Use PUT /projects/:id/labels/:name/promote instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdLabelsPromoteRequest The putApiV4ProjectsIdLabelsPromoteRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<APIEntitiesGroupLabel> putApiV4ProjectsIdLabelsPromoteWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdLabelsPromoteRequest putApiV4ProjectsIdLabelsPromoteRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdLabelsPromoteRequestCreation(id, putApiV4ProjectsIdLabelsPromoteRequest).toEntity(localVarReturnType);
    }

    /**
     * Promote a label to a group label
     * Added in GitLab 12.3 and deprecated in GitLab 12.4. Use PUT /projects/:id/labels/:name/promote instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdLabelsPromoteRequest The putApiV4ProjectsIdLabelsPromoteRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec putApiV4ProjectsIdLabelsPromoteWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdLabelsPromoteRequest putApiV4ProjectsIdLabelsPromoteRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdLabelsPromoteRequestCreation(id, putApiV4ProjectsIdLabelsPromoteRequest);
    }
}
