package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.GroupCollectionModel;
import ai.gebo.openproject.client.model.GroupModel;
import ai.gebo.openproject.client.model.GroupWriteModel;

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
public class GroupsApi {
    private ApiClient apiClient;

    public GroupsApi() {
        this(new ApiClient());
    }

    public GroupsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create group
     * Creates a new group applying the attributes provided in the body.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param groupWriteModel The groupWriteModel parameter
     * @return GroupModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createGroupRequestCreation(@jakarta.annotation.Nullable GroupWriteModel groupWriteModel) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<GroupModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/groups", HttpMethod.POST, pathParams, localVarQueryParams, groupWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create group
     * Creates a new group applying the attributes provided in the body.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param groupWriteModel The groupWriteModel parameter
     * @return GroupModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GroupModel createGroup(@jakarta.annotation.Nullable GroupWriteModel groupWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<GroupModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createGroupRequestCreation(groupWriteModel).body(localVarReturnType);
    }

    /**
     * Create group
     * Creates a new group applying the attributes provided in the body.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param groupWriteModel The groupWriteModel parameter
     * @return ResponseEntity&lt;GroupModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GroupModel> createGroupWithHttpInfo(@jakarta.annotation.Nullable GroupWriteModel groupWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<GroupModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createGroupRequestCreation(groupWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Create group
     * Creates a new group applying the attributes provided in the body.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param groupWriteModel The groupWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createGroupWithResponseSpec(@jakarta.annotation.Nullable GroupWriteModel groupWriteModel) throws RestClientResponseException {
        return createGroupRequestCreation(groupWriteModel);
    }

    /**
     * Delete group
     * Deletes the group.
     * <p><b>202</b> - Returned if the group was marked for deletion.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned, indicating the progress of deletion.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>404</b> - Returned if the group does not exist or the client does not have sufficient permissions to see it.  **Required permission:** Administrator  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Group id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteGroupRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/groups/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete group
     * Deletes the group.
     * <p><b>202</b> - Returned if the group was marked for deletion.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned, indicating the progress of deletion.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>404</b> - Returned if the group does not exist or the client does not have sufficient permissions to see it.  **Required permission:** Administrator  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Group id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteGroup(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteGroupRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete group
     * Deletes the group.
     * <p><b>202</b> - Returned if the group was marked for deletion.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned, indicating the progress of deletion.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>404</b> - Returned if the group does not exist or the client does not have sufficient permissions to see it.  **Required permission:** Administrator  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Group id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteGroupWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteGroupRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete group
     * Deletes the group.
     * <p><b>202</b> - Returned if the group was marked for deletion.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned, indicating the progress of deletion.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>404</b> - Returned if the group does not exist or the client does not have sufficient permissions to see it.  **Required permission:** Administrator  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Group id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteGroupWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteGroupRequestCreation(id);
    }

    /**
     * Get group
     * Fetches a group resource.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the group does not exist or if the API user does not have permission to view them.  **Required permission** If the user has the *manage members* permission in at least one project the user will be able to query all groups. If not, the user will be able to query all groups which are members in projects, he has the *view members* permission in.
     * @param id Group id
     * @return GroupModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getGroupRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<GroupModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/groups/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get group
     * Fetches a group resource.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the group does not exist or if the API user does not have permission to view them.  **Required permission** If the user has the *manage members* permission in at least one project the user will be able to query all groups. If not, the user will be able to query all groups which are members in projects, he has the *view members* permission in.
     * @param id Group id
     * @return GroupModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GroupModel getGroup(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<GroupModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getGroupRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get group
     * Fetches a group resource.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the group does not exist or if the API user does not have permission to view them.  **Required permission** If the user has the *manage members* permission in at least one project the user will be able to query all groups. If not, the user will be able to query all groups which are members in projects, he has the *view members* permission in.
     * @param id Group id
     * @return ResponseEntity&lt;GroupModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GroupModel> getGroupWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<GroupModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getGroupRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get group
     * Fetches a group resource.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the group does not exist or if the API user does not have permission to view them.  **Required permission** If the user has the *manage members* permission in at least one project the user will be able to query all groups. If not, the user will be able to query all groups which are members in projects, he has the *view members* permission in.
     * @param id Group id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getGroupWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getGroupRequestCreation(id);
    }

    /**
     * List groups
     * Returns a collection of groups. The client can choose to filter the groups similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain groups, for which the requesting client has sufficient permissions (*view_members*, *manage_members*).
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** View members or manage members in any project
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + created_at: Sort by group creation datetime  + updated_at: Sort by the time the group was updated last
     * @param select Comma separated list of properties to include.
     * @return GroupCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listGroupsRequestCreation(@jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "select", select));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<GroupCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/groups", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List groups
     * Returns a collection of groups. The client can choose to filter the groups similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain groups, for which the requesting client has sufficient permissions (*view_members*, *manage_members*).
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** View members or manage members in any project
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + created_at: Sort by group creation datetime  + updated_at: Sort by the time the group was updated last
     * @param select Comma separated list of properties to include.
     * @return GroupCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GroupCollectionModel listGroups(@jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<GroupCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listGroupsRequestCreation(sortBy, select).body(localVarReturnType);
    }

    /**
     * List groups
     * Returns a collection of groups. The client can choose to filter the groups similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain groups, for which the requesting client has sufficient permissions (*view_members*, *manage_members*).
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** View members or manage members in any project
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + created_at: Sort by group creation datetime  + updated_at: Sort by the time the group was updated last
     * @param select Comma separated list of properties to include.
     * @return ResponseEntity&lt;GroupCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GroupCollectionModel> listGroupsWithHttpInfo(@jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<GroupCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listGroupsRequestCreation(sortBy, select).toEntity(localVarReturnType);
    }

    /**
     * List groups
     * Returns a collection of groups. The client can choose to filter the groups similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain groups, for which the requesting client has sufficient permissions (*view_members*, *manage_members*).
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** View members or manage members in any project
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + created_at: Sort by group creation datetime  + updated_at: Sort by the time the group was updated last
     * @param select Comma separated list of properties to include.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listGroupsWithResponseSpec(@jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        return listGroupsRequestCreation(sortBy, select);
    }

    /**
     * Update group
     * Updates the given group by applying the attributes provided in the body.  Please note that the &#x60;members&#x60; array provided will override the existing set of members (similar to a PUT). A client thus has to provide the complete list of members the group is to have after the PATCH even if only one member is to be added.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>404</b> - Returned if the group does not exist or the client does not have sufficient permissions to see it.  **Required permission** If the user has the *manage members* permission in at least one project the user will be able to query all groups. If not, the user will be able to query all groups which are members in projects, he has the *view members* permission in.  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Group id
     * @param groupWriteModel The groupWriteModel parameter
     * @return GroupModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateGroupRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable GroupWriteModel groupWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<GroupModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/groups/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, groupWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update group
     * Updates the given group by applying the attributes provided in the body.  Please note that the &#x60;members&#x60; array provided will override the existing set of members (similar to a PUT). A client thus has to provide the complete list of members the group is to have after the PATCH even if only one member is to be added.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>404</b> - Returned if the group does not exist or the client does not have sufficient permissions to see it.  **Required permission** If the user has the *manage members* permission in at least one project the user will be able to query all groups. If not, the user will be able to query all groups which are members in projects, he has the *view members* permission in.  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Group id
     * @param groupWriteModel The groupWriteModel parameter
     * @return GroupModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GroupModel updateGroup(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable GroupWriteModel groupWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<GroupModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateGroupRequestCreation(id, groupWriteModel).body(localVarReturnType);
    }

    /**
     * Update group
     * Updates the given group by applying the attributes provided in the body.  Please note that the &#x60;members&#x60; array provided will override the existing set of members (similar to a PUT). A client thus has to provide the complete list of members the group is to have after the PATCH even if only one member is to be added.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>404</b> - Returned if the group does not exist or the client does not have sufficient permissions to see it.  **Required permission** If the user has the *manage members* permission in at least one project the user will be able to query all groups. If not, the user will be able to query all groups which are members in projects, he has the *view members* permission in.  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Group id
     * @param groupWriteModel The groupWriteModel parameter
     * @return ResponseEntity&lt;GroupModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GroupModel> updateGroupWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable GroupWriteModel groupWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<GroupModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateGroupRequestCreation(id, groupWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Update group
     * Updates the given group by applying the attributes provided in the body.  Please note that the &#x60;members&#x60; array provided will override the existing set of members (similar to a PUT). A client thus has to provide the complete list of members the group is to have after the PATCH even if only one member is to be added.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>404</b> - Returned if the group does not exist or the client does not have sufficient permissions to see it.  **Required permission** If the user has the *manage members* permission in at least one project the user will be able to query all groups. If not, the user will be able to query all groups which are members in projects, he has the *view members* permission in.  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Group id
     * @param groupWriteModel The groupWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateGroupWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable GroupWriteModel groupWriteModel) throws RestClientResponseException {
        return updateGroupRequestCreation(id, groupWriteModel);
    }
}
