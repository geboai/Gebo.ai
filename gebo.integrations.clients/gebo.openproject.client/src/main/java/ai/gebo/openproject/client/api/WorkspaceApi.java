package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.GetWorkspace200Response;
import ai.gebo.openproject.client.model.WorkspaceCollectionModel;
import ai.gebo.openproject.client.model.WorkspacePatchModel;

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
public class WorkspaceApi {
    private ApiClient apiClient;

    public WorkspaceApi() {
        this(new ApiClient());
    }

    public WorkspaceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete workspace
     * Deletes a workspace identified by its id.
     * <p><b>204</b> - Returned if the workspace was successfully deleted.
     * <p><b>403</b> - Returned if the user has no permission to delete the workspace. Only instance admins are allowed to delete workspaces.
     * <p><b>404</b> - Returned if the workspace was not found by its id or the user has not the permissions to see it. The permission has any user that is either an instance admin or a member of the workspace.
     * @param id The workspace id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteWorkspaceRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteWorkspace", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete workspace
     * Deletes a workspace identified by its id.
     * <p><b>204</b> - Returned if the workspace was successfully deleted.
     * <p><b>403</b> - Returned if the user has no permission to delete the workspace. Only instance admins are allowed to delete workspaces.
     * <p><b>404</b> - Returned if the workspace was not found by its id or the user has not the permissions to see it. The permission has any user that is either an instance admin or a member of the workspace.
     * @param id The workspace id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteWorkspace(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteWorkspaceRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete workspace
     * Deletes a workspace identified by its id.
     * <p><b>204</b> - Returned if the workspace was successfully deleted.
     * <p><b>403</b> - Returned if the user has no permission to delete the workspace. Only instance admins are allowed to delete workspaces.
     * <p><b>404</b> - Returned if the workspace was not found by its id or the user has not the permissions to see it. The permission has any user that is either an instance admin or a member of the workspace.
     * @param id The workspace id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteWorkspaceWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteWorkspaceRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete workspace
     * Deletes a workspace identified by its id.
     * <p><b>204</b> - Returned if the workspace was successfully deleted.
     * <p><b>403</b> - Returned if the user has no permission to delete the workspace. Only instance admins are allowed to delete workspaces.
     * <p><b>404</b> - Returned if the workspace was not found by its id or the user has not the permissions to see it. The permission has any user that is either an instance admin or a member of the workspace.
     * @param id The workspace id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteWorkspaceWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return deleteWorkspaceRequestCreation(id);
    }

    /**
     * Get workspace
     * Returns a single workspace identified by its id. As workspaces are the generic term for a number of resources like projects and portfolios, the returned entity will be either of those resources.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace was not found by its id or the user has not the permissions to see it. The permission has any user that is either an instance admin or a member of the workspace.
     * @param id The workspace id
     * @return GetWorkspace200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getWorkspaceRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getWorkspace", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<GetWorkspace200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get workspace
     * Returns a single workspace identified by its id. As workspaces are the generic term for a number of resources like projects and portfolios, the returned entity will be either of those resources.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace was not found by its id or the user has not the permissions to see it. The permission has any user that is either an instance admin or a member of the workspace.
     * @param id The workspace id
     * @return GetWorkspace200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GetWorkspace200Response getWorkspace(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<GetWorkspace200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWorkspaceRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get workspace
     * Returns a single workspace identified by its id. As workspaces are the generic term for a number of resources like projects and portfolios, the returned entity will be either of those resources.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace was not found by its id or the user has not the permissions to see it. The permission has any user that is either an instance admin or a member of the workspace.
     * @param id The workspace id
     * @return ResponseEntity&lt;GetWorkspace200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GetWorkspace200Response> getWorkspaceWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<GetWorkspace200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWorkspaceRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get workspace
     * Returns a single workspace identified by its id. As workspaces are the generic term for a number of resources like projects and portfolios, the returned entity will be either of those resources.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace was not found by its id or the user has not the permissions to see it. The permission has any user that is either an instance admin or a member of the workspace.
     * @param id The workspace id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getWorkspaceWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getWorkspaceRequestCreation(id);
    }

    /**
     * List workspace
     * Returns a collection of workspaces. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain workspaces the client is allowed to see.  Since workspaces are the generic term for a number of resources like projects and portfolios, the returned collection will contain a mix of those resources.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the workspace + ancestor: filters workspace by their ancestor. A workspace is not considered to be its own ancestor. + available_project_attributes: filters workspace based on the activated project attributes. + created_at: based on the time the workspace was created + favorited: based on the favorited property of the workspace + id: based on workspace&#39; id. + latest_activity_at: based on the time the last activity was registered on a workspace. + name_and_identifier: based on both the name and the identifier. + parent_id: filters workspace by their parent. + principal: based on members of the workspace. + project_phase_any: based on the project phases active in a workspace. + project_status_code: based on status code of the workspace + storage_id: filters workspace by linked storages + storage_url: filters workspace by linked storages identified by the host url + type_id: based on the types active in a workspace. + user_action: based on the actions the current user has in the workspace. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the workspace visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a workspace. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a workspace. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a workspace. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return WorkspaceCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkspaceRequestCreation(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "select", select));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<WorkspaceCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List workspace
     * Returns a collection of workspaces. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain workspaces the client is allowed to see.  Since workspaces are the generic term for a number of resources like projects and portfolios, the returned collection will contain a mix of those resources.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the workspace + ancestor: filters workspace by their ancestor. A workspace is not considered to be its own ancestor. + available_project_attributes: filters workspace based on the activated project attributes. + created_at: based on the time the workspace was created + favorited: based on the favorited property of the workspace + id: based on workspace&#39; id. + latest_activity_at: based on the time the last activity was registered on a workspace. + name_and_identifier: based on both the name and the identifier. + parent_id: filters workspace by their parent. + principal: based on members of the workspace. + project_phase_any: based on the project phases active in a workspace. + project_status_code: based on status code of the workspace + storage_id: filters workspace by linked storages + storage_url: filters workspace by linked storages identified by the host url + type_id: based on the types active in a workspace. + user_action: based on the actions the current user has in the workspace. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the workspace visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a workspace. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a workspace. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a workspace. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return WorkspaceCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public WorkspaceCollectionModel listWorkspace(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<WorkspaceCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkspaceRequestCreation(filters, sortBy, select).body(localVarReturnType);
    }

    /**
     * List workspace
     * Returns a collection of workspaces. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain workspaces the client is allowed to see.  Since workspaces are the generic term for a number of resources like projects and portfolios, the returned collection will contain a mix of those resources.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the workspace + ancestor: filters workspace by their ancestor. A workspace is not considered to be its own ancestor. + available_project_attributes: filters workspace based on the activated project attributes. + created_at: based on the time the workspace was created + favorited: based on the favorited property of the workspace + id: based on workspace&#39; id. + latest_activity_at: based on the time the last activity was registered on a workspace. + name_and_identifier: based on both the name and the identifier. + parent_id: filters workspace by their parent. + principal: based on members of the workspace. + project_phase_any: based on the project phases active in a workspace. + project_status_code: based on status code of the workspace + storage_id: filters workspace by linked storages + storage_url: filters workspace by linked storages identified by the host url + type_id: based on the types active in a workspace. + user_action: based on the actions the current user has in the workspace. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the workspace visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a workspace. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a workspace. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a workspace. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return ResponseEntity&lt;WorkspaceCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkspaceCollectionModel> listWorkspaceWithHttpInfo(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<WorkspaceCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkspaceRequestCreation(filters, sortBy, select).toEntity(localVarReturnType);
    }

    /**
     * List workspace
     * Returns a collection of workspaces. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain workspaces the client is allowed to see.  Since workspaces are the generic term for a number of resources like projects and portfolios, the returned collection will contain a mix of those resources.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the workspace + ancestor: filters workspace by their ancestor. A workspace is not considered to be its own ancestor. + available_project_attributes: filters workspace based on the activated project attributes. + created_at: based on the time the workspace was created + favorited: based on the favorited property of the workspace + id: based on workspace&#39; id. + latest_activity_at: based on the time the last activity was registered on a workspace. + name_and_identifier: based on both the name and the identifier. + parent_id: filters workspace by their parent. + principal: based on members of the workspace. + project_phase_any: based on the project phases active in a workspace. + project_status_code: based on status code of the workspace + storage_id: filters workspace by linked storages + storage_url: filters workspace by linked storages identified by the host url + type_id: based on the types active in a workspace. + user_action: based on the actions the current user has in the workspace. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the workspace visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a workspace. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a workspace. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a workspace. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkspaceWithResponseSpec(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        return listWorkspaceRequestCreation(filters, sortBy, select);
    }

    /**
     * Update workspace
     * Updates a single workspace identified by its id.  It can be used to archive/reactivate a workspace, sending only the &#x60;active&#x60; attribute.  It can be used to edit the workspace information and or the workspace attributes, which both require a different set of permissions.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace was not found by its id or the user has not the permissions to see it. The permission has any user that is either an instance admin or a member of the workspace.
     * @param id The workspace id
     * @param workspacePatchModel The workspacePatchModel parameter
     * @return GetWorkspace200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateWorkspaceRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable WorkspacePatchModel workspacePatchModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateWorkspace", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<GetWorkspace200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, workspacePatchModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update workspace
     * Updates a single workspace identified by its id.  It can be used to archive/reactivate a workspace, sending only the &#x60;active&#x60; attribute.  It can be used to edit the workspace information and or the workspace attributes, which both require a different set of permissions.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace was not found by its id or the user has not the permissions to see it. The permission has any user that is either an instance admin or a member of the workspace.
     * @param id The workspace id
     * @param workspacePatchModel The workspacePatchModel parameter
     * @return GetWorkspace200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GetWorkspace200Response updateWorkspace(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable WorkspacePatchModel workspacePatchModel) throws RestClientResponseException {
        ParameterizedTypeReference<GetWorkspace200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateWorkspaceRequestCreation(id, workspacePatchModel).body(localVarReturnType);
    }

    /**
     * Update workspace
     * Updates a single workspace identified by its id.  It can be used to archive/reactivate a workspace, sending only the &#x60;active&#x60; attribute.  It can be used to edit the workspace information and or the workspace attributes, which both require a different set of permissions.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace was not found by its id or the user has not the permissions to see it. The permission has any user that is either an instance admin or a member of the workspace.
     * @param id The workspace id
     * @param workspacePatchModel The workspacePatchModel parameter
     * @return ResponseEntity&lt;GetWorkspace200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GetWorkspace200Response> updateWorkspaceWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable WorkspacePatchModel workspacePatchModel) throws RestClientResponseException {
        ParameterizedTypeReference<GetWorkspace200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateWorkspaceRequestCreation(id, workspacePatchModel).toEntity(localVarReturnType);
    }

    /**
     * Update workspace
     * Updates a single workspace identified by its id.  It can be used to archive/reactivate a workspace, sending only the &#x60;active&#x60; attribute.  It can be used to edit the workspace information and or the workspace attributes, which both require a different set of permissions.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace was not found by its id or the user has not the permissions to see it. The permission has any user that is either an instance admin or a member of the workspace.
     * @param id The workspace id
     * @param workspacePatchModel The workspacePatchModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateWorkspaceWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable WorkspacePatchModel workspacePatchModel) throws RestClientResponseException {
        return updateWorkspaceRequestCreation(id, workspacePatchModel);
    }
}
