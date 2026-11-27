package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesUserPublic;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4GroupsIdEnterpriseUsersUserIdRequest;
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
public class EnterpriseUsersApi {
    private ApiClient apiClient;

    public EnterpriseUsersApi() {
        this(new ApiClient());
    }

    public EnterpriseUsersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete an enterprise user
     * Deletes a specified enterprise user of a top-level group. This feature was introduced in GitLab 18.3.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param hardDelete If &#x60;true&#x60;, deletes the user, their associated contributions, and any groups owned solely by the user. If &#x60;false&#x60;, deletes the user and moves their contributions to a [ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records).
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdEnterpriseUsersUserIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdEnterpriseUsersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling deleteApiV4GroupsIdEnterpriseUsersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "hard_delete", hardDelete));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/enterprise_users/{user_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an enterprise user
     * Deletes a specified enterprise user of a top-level group. This feature was introduced in GitLab 18.3.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param hardDelete If &#x60;true&#x60;, deletes the user, their associated contributions, and any groups owned solely by the user. If &#x60;false&#x60;, deletes the user and moves their contributions to a [ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records).
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdEnterpriseUsersUserId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdEnterpriseUsersUserIdRequestCreation(id, userId, hardDelete).body(localVarReturnType);
    }

    /**
     * Delete an enterprise user
     * Deletes a specified enterprise user of a top-level group. This feature was introduced in GitLab 18.3.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param hardDelete If &#x60;true&#x60;, deletes the user, their associated contributions, and any groups owned solely by the user. If &#x60;false&#x60;, deletes the user and moves their contributions to a [ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records).
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdEnterpriseUsersUserIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdEnterpriseUsersUserIdRequestCreation(id, userId, hardDelete).toEntity(localVarReturnType);
    }

    /**
     * Delete an enterprise user
     * Deletes a specified enterprise user of a top-level group. This feature was introduced in GitLab 18.3.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param hardDelete If &#x60;true&#x60;, deletes the user, their associated contributions, and any groups owned solely by the user. If &#x60;false&#x60;, deletes the user and moves their contributions to a [ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records).
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdEnterpriseUsersUserIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        return deleteApiV4GroupsIdEnterpriseUsersUserIdRequestCreation(id, userId, hardDelete);
    }

    /**
     * List all enterprise users
     * Lists all enterprise users for a specified top-level group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 17.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param username Return a user with the given username.
     * @param search Return users with a matching name, email, or username. Use partial values to increase results.
     * @param active If &#x60;true&#x60;, returns only active users.
     * @param blocked If &#x60;true&#x60;, returns only blocked users.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param twoFactor Filter users by two-factor authentication (2FA). Filter values are &#x60;enabled&#x60; or &#x60;disabled&#x60;. By default it returns all users.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesUserPublic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEnterpriseUsersRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable String twoFactor, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEnterpriseUsers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "username", username));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "active", active));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "blocked", blocked));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "two_factor", twoFactor));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesUserPublic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/enterprise_users", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all enterprise users
     * Lists all enterprise users for a specified top-level group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 17.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param username Return a user with the given username.
     * @param search Return users with a matching name, email, or username. Use partial values to increase results.
     * @param active If &#x60;true&#x60;, returns only active users.
     * @param blocked If &#x60;true&#x60;, returns only blocked users.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param twoFactor Filter users by two-factor authentication (2FA). Filter values are &#x60;enabled&#x60; or &#x60;disabled&#x60;. By default it returns all users.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesUserPublic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesUserPublic> getApiV4GroupsIdEnterpriseUsers(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable String twoFactor, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserPublic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEnterpriseUsersRequestCreation(id, username, search, active, blocked, createdAfter, createdBefore, twoFactor, page, perPage).body(localVarReturnType);
    }

    /**
     * List all enterprise users
     * Lists all enterprise users for a specified top-level group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 17.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param username Return a user with the given username.
     * @param search Return users with a matching name, email, or username. Use partial values to increase results.
     * @param active If &#x60;true&#x60;, returns only active users.
     * @param blocked If &#x60;true&#x60;, returns only blocked users.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param twoFactor Filter users by two-factor authentication (2FA). Filter values are &#x60;enabled&#x60; or &#x60;disabled&#x60;. By default it returns all users.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesUserPublic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesUserPublic>> getApiV4GroupsIdEnterpriseUsersWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable String twoFactor, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserPublic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEnterpriseUsersRequestCreation(id, username, search, active, blocked, createdAfter, createdBefore, twoFactor, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all enterprise users
     * Lists all enterprise users for a specified top-level group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 17.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param username Return a user with the given username.
     * @param search Return users with a matching name, email, or username. Use partial values to increase results.
     * @param active If &#x60;true&#x60;, returns only active users.
     * @param blocked If &#x60;true&#x60;, returns only blocked users.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param twoFactor Filter users by two-factor authentication (2FA). Filter values are &#x60;enabled&#x60; or &#x60;disabled&#x60;. By default it returns all users.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEnterpriseUsersWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable String twoFactor, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdEnterpriseUsersRequestCreation(id, username, search, active, blocked, createdAfter, createdBefore, twoFactor, page, perPage);
    }

    /**
     * Retrieve an enterprise user
     * Retrieves a specified enterprise user of a top-level group. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @return APIEntitiesUserPublic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEnterpriseUsersUserIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEnterpriseUsersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4GroupsIdEnterpriseUsersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<APIEntitiesUserPublic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/enterprise_users/{user_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an enterprise user
     * Retrieves a specified enterprise user of a top-level group. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @return APIEntitiesUserPublic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserPublic getApiV4GroupsIdEnterpriseUsersUserId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserPublic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEnterpriseUsersUserIdRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Retrieve an enterprise user
     * Retrieves a specified enterprise user of a top-level group. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @return ResponseEntity&lt;APIEntitiesUserPublic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserPublic> getApiV4GroupsIdEnterpriseUsersUserIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserPublic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEnterpriseUsersUserIdRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an enterprise user
     * Retrieves a specified enterprise user of a top-level group. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEnterpriseUsersUserIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return getApiV4GroupsIdEnterpriseUsersUserIdRequestCreation(id, userId);
    }

    /**
     * Update an enterprise user
     * Updates a specified enterprise user of a top-level group. This feature was introduced in GitLab 18.6.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param patchApiV4GroupsIdEnterpriseUsersUserIdRequest The patchApiV4GroupsIdEnterpriseUsersUserIdRequest parameter
     * @return APIEntitiesUserPublic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4GroupsIdEnterpriseUsersUserIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4GroupsIdEnterpriseUsersUserIdRequest patchApiV4GroupsIdEnterpriseUsersUserIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4GroupsIdEnterpriseUsersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling patchApiV4GroupsIdEnterpriseUsersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<APIEntitiesUserPublic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/enterprise_users/{user_id}", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4GroupsIdEnterpriseUsersUserIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an enterprise user
     * Updates a specified enterprise user of a top-level group. This feature was introduced in GitLab 18.6.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param patchApiV4GroupsIdEnterpriseUsersUserIdRequest The patchApiV4GroupsIdEnterpriseUsersUserIdRequest parameter
     * @return APIEntitiesUserPublic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserPublic patchApiV4GroupsIdEnterpriseUsersUserId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4GroupsIdEnterpriseUsersUserIdRequest patchApiV4GroupsIdEnterpriseUsersUserIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserPublic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4GroupsIdEnterpriseUsersUserIdRequestCreation(id, userId, patchApiV4GroupsIdEnterpriseUsersUserIdRequest).body(localVarReturnType);
    }

    /**
     * Update an enterprise user
     * Updates a specified enterprise user of a top-level group. This feature was introduced in GitLab 18.6.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param patchApiV4GroupsIdEnterpriseUsersUserIdRequest The patchApiV4GroupsIdEnterpriseUsersUserIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesUserPublic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserPublic> patchApiV4GroupsIdEnterpriseUsersUserIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4GroupsIdEnterpriseUsersUserIdRequest patchApiV4GroupsIdEnterpriseUsersUserIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserPublic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4GroupsIdEnterpriseUsersUserIdRequestCreation(id, userId, patchApiV4GroupsIdEnterpriseUsersUserIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an enterprise user
     * Updates a specified enterprise user of a top-level group. This feature was introduced in GitLab 18.6.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param patchApiV4GroupsIdEnterpriseUsersUserIdRequest The patchApiV4GroupsIdEnterpriseUsersUserIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4GroupsIdEnterpriseUsersUserIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4GroupsIdEnterpriseUsersUserIdRequest patchApiV4GroupsIdEnterpriseUsersUserIdRequest) throws RestClientResponseException {
        return patchApiV4GroupsIdEnterpriseUsersUserIdRequestCreation(id, userId, patchApiV4GroupsIdEnterpriseUsersUserIdRequest);
    }

    /**
     * Disable two-factor authentication for an enterprise user
     * Disables two-factor authentication (2FA) for a specified enterprise user of a top-level group. This feature was introduced in GitLab 17.9.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4GroupsIdEnterpriseUsersUserIdDisableTwoFactorRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4GroupsIdEnterpriseUsersUserIdDisableTwoFactor", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling patchApiV4GroupsIdEnterpriseUsersUserIdDisableTwoFactor", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/enterprise_users/{user_id}/disable_two_factor", HttpMethod.PATCH, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Disable two-factor authentication for an enterprise user
     * Disables two-factor authentication (2FA) for a specified enterprise user of a top-level group. This feature was introduced in GitLab 17.9.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void patchApiV4GroupsIdEnterpriseUsersUserIdDisableTwoFactor(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        patchApiV4GroupsIdEnterpriseUsersUserIdDisableTwoFactorRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Disable two-factor authentication for an enterprise user
     * Disables two-factor authentication (2FA) for a specified enterprise user of a top-level group. This feature was introduced in GitLab 17.9.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> patchApiV4GroupsIdEnterpriseUsersUserIdDisableTwoFactorWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4GroupsIdEnterpriseUsersUserIdDisableTwoFactorRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Disable two-factor authentication for an enterprise user
     * Disables two-factor authentication (2FA) for a specified enterprise user of a top-level group. This feature was introduced in GitLab 17.9.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4GroupsIdEnterpriseUsersUserIdDisableTwoFactorWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return patchApiV4GroupsIdEnterpriseUsersUserIdDisableTwoFactorRequestCreation(id, userId);
    }
}
