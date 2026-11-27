package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesPersonalAccessToken;
import ai.gebo.gitlab.client.model.APIEntitiesPersonalAccessTokenWithToken;
import ai.gebo.gitlab.client.model.APIEntitiesResourceAccessToken;
import ai.gebo.gitlab.client.model.APIEntitiesSshKeyWithUserId;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest;

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
public class GroupCredentialsInventoryApi {
    private ApiClient apiClient;

    public GroupCredentialsInventoryApi() {
        this(new ApiClient());
    }

    public GroupCredentialsInventoryApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Revoke a personal access token for an enterprise user
     * Revokes a specified personal access token for an enterprise user.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param patId ID of the personal access token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdManagePersonalAccessTokensPatIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer patId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdManagePersonalAccessTokensPatId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'patId' is set
        if (patId == null) {
            throw new RestClientResponseException("Missing the required parameter 'patId' when calling deleteApiV4GroupsIdManagePersonalAccessTokensPatId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pat_id", patId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/manage/personal_access_tokens/{pat_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke a personal access token for an enterprise user
     * Revokes a specified personal access token for an enterprise user.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param patId ID of the personal access token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdManagePersonalAccessTokensPatId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer patId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdManagePersonalAccessTokensPatIdRequestCreation(id, patId).body(localVarReturnType);
    }

    /**
     * Revoke a personal access token for an enterprise user
     * Revokes a specified personal access token for an enterprise user.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param patId ID of the personal access token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdManagePersonalAccessTokensPatIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer patId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdManagePersonalAccessTokensPatIdRequestCreation(id, patId).toEntity(localVarReturnType);
    }

    /**
     * Revoke a personal access token for an enterprise user
     * Revokes a specified personal access token for an enterprise user.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param patId ID of the personal access token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdManagePersonalAccessTokensPatIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer patId) throws RestClientResponseException {
        return deleteApiV4GroupsIdManagePersonalAccessTokensPatIdRequestCreation(id, patId);
    }

    /**
     * Revoke a group or project access token for a group
     * Revokes a specified group or project access token associated with a top-level group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param pratId ID of the resource access token.
     * @param expiresAt Date when the access token expires. If omitted, the new token expires one week after rotation when [token expiry is enforced](https://docs.gitlab.com/user/profile/personal_access_tokens/#access-token-expiration), and does not expire otherwise. When expiry is enforced, the date must be no more than one year after rotation.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdManageResourceAccessTokensPratIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pratId, @jakarta.annotation.Nullable LocalDate expiresAt) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdManageResourceAccessTokensPratId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pratId' is set
        if (pratId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pratId' when calling deleteApiV4GroupsIdManageResourceAccessTokensPratId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("prat_id", pratId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expires_at", expiresAt));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/manage/resource_access_tokens/{prat_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke a group or project access token for a group
     * Revokes a specified group or project access token associated with a top-level group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param pratId ID of the resource access token.
     * @param expiresAt Date when the access token expires. If omitted, the new token expires one week after rotation when [token expiry is enforced](https://docs.gitlab.com/user/profile/personal_access_tokens/#access-token-expiration), and does not expire otherwise. When expiry is enforced, the date must be no more than one year after rotation.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdManageResourceAccessTokensPratId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pratId, @jakarta.annotation.Nullable LocalDate expiresAt) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdManageResourceAccessTokensPratIdRequestCreation(id, pratId, expiresAt).body(localVarReturnType);
    }

    /**
     * Revoke a group or project access token for a group
     * Revokes a specified group or project access token associated with a top-level group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param pratId ID of the resource access token.
     * @param expiresAt Date when the access token expires. If omitted, the new token expires one week after rotation when [token expiry is enforced](https://docs.gitlab.com/user/profile/personal_access_tokens/#access-token-expiration), and does not expire otherwise. When expiry is enforced, the date must be no more than one year after rotation.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdManageResourceAccessTokensPratIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pratId, @jakarta.annotation.Nullable LocalDate expiresAt) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdManageResourceAccessTokensPratIdRequestCreation(id, pratId, expiresAt).toEntity(localVarReturnType);
    }

    /**
     * Revoke a group or project access token for a group
     * Revokes a specified group or project access token associated with a top-level group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param pratId ID of the resource access token.
     * @param expiresAt Date when the access token expires. If omitted, the new token expires one week after rotation when [token expiry is enforced](https://docs.gitlab.com/user/profile/personal_access_tokens/#access-token-expiration), and does not expire otherwise. When expiry is enforced, the date must be no more than one year after rotation.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdManageResourceAccessTokensPratIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pratId, @jakarta.annotation.Nullable LocalDate expiresAt) throws RestClientResponseException {
        return deleteApiV4GroupsIdManageResourceAccessTokensPratIdRequestCreation(id, pratId, expiresAt);
    }

    /**
     * Delete an SSH key for an enterprise user
     * Deletes a specified SSH public key for an enterprise user associated with the top-level group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param keyId ID of the SSH key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdManageSshKeysKeyIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdManageSshKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling deleteApiV4GroupsIdManageSshKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key_id", keyId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/manage/ssh_keys/{key_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an SSH key for an enterprise user
     * Deletes a specified SSH public key for an enterprise user associated with the top-level group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param keyId ID of the SSH key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdManageSshKeysKeyId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdManageSshKeysKeyIdRequestCreation(id, keyId).body(localVarReturnType);
    }

    /**
     * Delete an SSH key for an enterprise user
     * Deletes a specified SSH public key for an enterprise user associated with the top-level group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param keyId ID of the SSH key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdManageSshKeysKeyIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdManageSshKeysKeyIdRequestCreation(id, keyId).toEntity(localVarReturnType);
    }

    /**
     * Delete an SSH key for an enterprise user
     * Deletes a specified SSH public key for an enterprise user associated with the top-level group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param keyId ID of the SSH key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdManageSshKeysKeyIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        return deleteApiV4GroupsIdManageSshKeysKeyIdRequestCreation(id, keyId);
    }

    /**
     * List all personal access tokens for a group
     * Lists all personal access tokens associated with enterprise users in a top-level group. For tokens created with granular scopes, the response also includes a &#x60;granular_scopes&#x60; attribute. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/634390) in GitLab 19.6. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param revoked If &#x60;true&#x60;, only returns revoked tokens. If &#x60;false&#x60;, only returns tokens that are not revoked.
     * @param state Return tokens with the specified state. &#x60;all&#x60; returns tokens in any state.
     * @param createdBefore Return tokens created on or before the specified time.
     * @param createdAfter Return tokens created on or after the specified time.
     * @param lastUsedBefore Return tokens last used on or before the specified time.
     * @param lastUsedAfter Return tokens last used on or after the specified time.
     * @param expiresBefore Return tokens that expire before the specified date.
     * @param expiresAfter Return tokens that expire on or after the specified date.
     * @param search Return tokens that include the specified value in their name.
     * @param sort Sort results by the specified field and direction. Possible values are &#x60;created_asc&#x60;, &#x60;created_desc&#x60;, &#x60;expires_asc&#x60;, &#x60;expires_desc&#x60;, &#x60;last_used_asc&#x60;, &#x60;last_used_desc&#x60;, &#x60;name_asc&#x60;, and &#x60;name_desc&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesPersonalAccessToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdManagePersonalAccessTokensRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdManagePersonalAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "revoked", revoked));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "last_used_before", lastUsedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "last_used_after", lastUsedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expires_before", expiresBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expires_after", expiresAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
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

        ParameterizedTypeReference<List<APIEntitiesPersonalAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/manage/personal_access_tokens", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all personal access tokens for a group
     * Lists all personal access tokens associated with enterprise users in a top-level group. For tokens created with granular scopes, the response also includes a &#x60;granular_scopes&#x60; attribute. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/634390) in GitLab 19.6. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param revoked If &#x60;true&#x60;, only returns revoked tokens. If &#x60;false&#x60;, only returns tokens that are not revoked.
     * @param state Return tokens with the specified state. &#x60;all&#x60; returns tokens in any state.
     * @param createdBefore Return tokens created on or before the specified time.
     * @param createdAfter Return tokens created on or after the specified time.
     * @param lastUsedBefore Return tokens last used on or before the specified time.
     * @param lastUsedAfter Return tokens last used on or after the specified time.
     * @param expiresBefore Return tokens that expire before the specified date.
     * @param expiresAfter Return tokens that expire on or after the specified date.
     * @param search Return tokens that include the specified value in their name.
     * @param sort Sort results by the specified field and direction. Possible values are &#x60;created_asc&#x60;, &#x60;created_desc&#x60;, &#x60;expires_asc&#x60;, &#x60;expires_desc&#x60;, &#x60;last_used_asc&#x60;, &#x60;last_used_desc&#x60;, &#x60;name_asc&#x60;, and &#x60;name_desc&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesPersonalAccessToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesPersonalAccessToken> getApiV4GroupsIdManagePersonalAccessTokens(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPersonalAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdManagePersonalAccessTokensRequestCreation(id, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort, page, perPage).body(localVarReturnType);
    }

    /**
     * List all personal access tokens for a group
     * Lists all personal access tokens associated with enterprise users in a top-level group. For tokens created with granular scopes, the response also includes a &#x60;granular_scopes&#x60; attribute. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/634390) in GitLab 19.6. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param revoked If &#x60;true&#x60;, only returns revoked tokens. If &#x60;false&#x60;, only returns tokens that are not revoked.
     * @param state Return tokens with the specified state. &#x60;all&#x60; returns tokens in any state.
     * @param createdBefore Return tokens created on or before the specified time.
     * @param createdAfter Return tokens created on or after the specified time.
     * @param lastUsedBefore Return tokens last used on or before the specified time.
     * @param lastUsedAfter Return tokens last used on or after the specified time.
     * @param expiresBefore Return tokens that expire before the specified date.
     * @param expiresAfter Return tokens that expire on or after the specified date.
     * @param search Return tokens that include the specified value in their name.
     * @param sort Sort results by the specified field and direction. Possible values are &#x60;created_asc&#x60;, &#x60;created_desc&#x60;, &#x60;expires_asc&#x60;, &#x60;expires_desc&#x60;, &#x60;last_used_asc&#x60;, &#x60;last_used_desc&#x60;, &#x60;name_asc&#x60;, and &#x60;name_desc&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesPersonalAccessToken&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesPersonalAccessToken>> getApiV4GroupsIdManagePersonalAccessTokensWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPersonalAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdManagePersonalAccessTokensRequestCreation(id, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all personal access tokens for a group
     * Lists all personal access tokens associated with enterprise users in a top-level group. For tokens created with granular scopes, the response also includes a &#x60;granular_scopes&#x60; attribute. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/634390) in GitLab 19.6. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param revoked If &#x60;true&#x60;, only returns revoked tokens. If &#x60;false&#x60;, only returns tokens that are not revoked.
     * @param state Return tokens with the specified state. &#x60;all&#x60; returns tokens in any state.
     * @param createdBefore Return tokens created on or before the specified time.
     * @param createdAfter Return tokens created on or after the specified time.
     * @param lastUsedBefore Return tokens last used on or before the specified time.
     * @param lastUsedAfter Return tokens last used on or after the specified time.
     * @param expiresBefore Return tokens that expire before the specified date.
     * @param expiresAfter Return tokens that expire on or after the specified date.
     * @param search Return tokens that include the specified value in their name.
     * @param sort Sort results by the specified field and direction. Possible values are &#x60;created_asc&#x60;, &#x60;created_desc&#x60;, &#x60;expires_asc&#x60;, &#x60;expires_desc&#x60;, &#x60;last_used_asc&#x60;, &#x60;last_used_desc&#x60;, &#x60;name_asc&#x60;, and &#x60;name_desc&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdManagePersonalAccessTokensWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdManagePersonalAccessTokensRequestCreation(id, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort, page, perPage);
    }

    /**
     * List all group and project access tokens for a group
     * Lists all group and project access tokens associated with a top-level-group. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param revoked If &#x60;true&#x60;, only returns revoked tokens. If &#x60;false&#x60;, only returns tokens that are not revoked.
     * @param state Return tokens with the specified state. &#x60;all&#x60; returns tokens in any state.
     * @param createdBefore Return tokens created on or before the specified time.
     * @param createdAfter Return tokens created on or after the specified time.
     * @param lastUsedBefore Return tokens last used on or before the specified time.
     * @param lastUsedAfter Return tokens last used on or after the specified time.
     * @param expiresBefore Return tokens that expire before the specified date.
     * @param expiresAfter Return tokens that expire on or after the specified date.
     * @param search Return tokens that include the specified value in their name.
     * @param sort Sort results by the specified field and direction. Possible values are &#x60;created_asc&#x60;, &#x60;created_desc&#x60;, &#x60;expires_asc&#x60;, &#x60;expires_desc&#x60;, &#x60;last_used_asc&#x60;, &#x60;last_used_desc&#x60;, &#x60;name_asc&#x60;, and &#x60;name_desc&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceAccessToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdManageResourceAccessTokensRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdManageResourceAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "revoked", revoked));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "last_used_before", lastUsedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "last_used_after", lastUsedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expires_before", expiresBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expires_after", expiresAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
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

        ParameterizedTypeReference<List<APIEntitiesResourceAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/manage/resource_access_tokens", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group and project access tokens for a group
     * Lists all group and project access tokens associated with a top-level-group. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param revoked If &#x60;true&#x60;, only returns revoked tokens. If &#x60;false&#x60;, only returns tokens that are not revoked.
     * @param state Return tokens with the specified state. &#x60;all&#x60; returns tokens in any state.
     * @param createdBefore Return tokens created on or before the specified time.
     * @param createdAfter Return tokens created on or after the specified time.
     * @param lastUsedBefore Return tokens last used on or before the specified time.
     * @param lastUsedAfter Return tokens last used on or after the specified time.
     * @param expiresBefore Return tokens that expire before the specified date.
     * @param expiresAfter Return tokens that expire on or after the specified date.
     * @param search Return tokens that include the specified value in their name.
     * @param sort Sort results by the specified field and direction. Possible values are &#x60;created_asc&#x60;, &#x60;created_desc&#x60;, &#x60;expires_asc&#x60;, &#x60;expires_desc&#x60;, &#x60;last_used_asc&#x60;, &#x60;last_used_desc&#x60;, &#x60;name_asc&#x60;, and &#x60;name_desc&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesResourceAccessToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesResourceAccessToken> getApiV4GroupsIdManageResourceAccessTokens(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdManageResourceAccessTokensRequestCreation(id, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort, page, perPage).body(localVarReturnType);
    }

    /**
     * List all group and project access tokens for a group
     * Lists all group and project access tokens associated with a top-level-group. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param revoked If &#x60;true&#x60;, only returns revoked tokens. If &#x60;false&#x60;, only returns tokens that are not revoked.
     * @param state Return tokens with the specified state. &#x60;all&#x60; returns tokens in any state.
     * @param createdBefore Return tokens created on or before the specified time.
     * @param createdAfter Return tokens created on or after the specified time.
     * @param lastUsedBefore Return tokens last used on or before the specified time.
     * @param lastUsedAfter Return tokens last used on or after the specified time.
     * @param expiresBefore Return tokens that expire before the specified date.
     * @param expiresAfter Return tokens that expire on or after the specified date.
     * @param search Return tokens that include the specified value in their name.
     * @param sort Sort results by the specified field and direction. Possible values are &#x60;created_asc&#x60;, &#x60;created_desc&#x60;, &#x60;expires_asc&#x60;, &#x60;expires_desc&#x60;, &#x60;last_used_asc&#x60;, &#x60;last_used_desc&#x60;, &#x60;name_asc&#x60;, and &#x60;name_desc&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesResourceAccessToken&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesResourceAccessToken>> getApiV4GroupsIdManageResourceAccessTokensWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdManageResourceAccessTokensRequestCreation(id, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group and project access tokens for a group
     * Lists all group and project access tokens associated with a top-level-group. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param revoked If &#x60;true&#x60;, only returns revoked tokens. If &#x60;false&#x60;, only returns tokens that are not revoked.
     * @param state Return tokens with the specified state. &#x60;all&#x60; returns tokens in any state.
     * @param createdBefore Return tokens created on or before the specified time.
     * @param createdAfter Return tokens created on or after the specified time.
     * @param lastUsedBefore Return tokens last used on or before the specified time.
     * @param lastUsedAfter Return tokens last used on or after the specified time.
     * @param expiresBefore Return tokens that expire before the specified date.
     * @param expiresAfter Return tokens that expire on or after the specified date.
     * @param search Return tokens that include the specified value in their name.
     * @param sort Sort results by the specified field and direction. Possible values are &#x60;created_asc&#x60;, &#x60;created_desc&#x60;, &#x60;expires_asc&#x60;, &#x60;expires_desc&#x60;, &#x60;last_used_asc&#x60;, &#x60;last_used_desc&#x60;, &#x60;name_asc&#x60;, and &#x60;name_desc&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdManageResourceAccessTokensWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdManageResourceAccessTokensRequestCreation(id, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort, page, perPage);
    }

    /**
     * List all SSH keys for a group
     * Lists all SSH public keys associated with enterprise users in a top-level-group. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param createdBefore Return SSH keys created on or before the specified time.
     * @param createdAfter Return SSH keys created on or after the specified time.
     * @param expiresBefore Return SSH keys that expire on or before the specified time.
     * @param expiresAfter Return SSH keys that expire on or after the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSshKeyWithUserId&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdManageSshKeysRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime expiresBefore, @jakarta.annotation.Nullable OffsetDateTime expiresAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdManageSshKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expires_before", expiresBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expires_after", expiresAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesSshKeyWithUserId>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/manage/ssh_keys", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all SSH keys for a group
     * Lists all SSH public keys associated with enterprise users in a top-level-group. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param createdBefore Return SSH keys created on or before the specified time.
     * @param createdAfter Return SSH keys created on or after the specified time.
     * @param expiresBefore Return SSH keys that expire on or before the specified time.
     * @param expiresAfter Return SSH keys that expire on or after the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSshKeyWithUserId&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSshKeyWithUserId> getApiV4GroupsIdManageSshKeys(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime expiresBefore, @jakarta.annotation.Nullable OffsetDateTime expiresAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSshKeyWithUserId>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdManageSshKeysRequestCreation(id, createdBefore, createdAfter, expiresBefore, expiresAfter, page, perPage).body(localVarReturnType);
    }

    /**
     * List all SSH keys for a group
     * Lists all SSH public keys associated with enterprise users in a top-level-group. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param createdBefore Return SSH keys created on or before the specified time.
     * @param createdAfter Return SSH keys created on or after the specified time.
     * @param expiresBefore Return SSH keys that expire on or before the specified time.
     * @param expiresAfter Return SSH keys that expire on or after the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesSshKeyWithUserId&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSshKeyWithUserId>> getApiV4GroupsIdManageSshKeysWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime expiresBefore, @jakarta.annotation.Nullable OffsetDateTime expiresAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSshKeyWithUserId>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdManageSshKeysRequestCreation(id, createdBefore, createdAfter, expiresBefore, expiresAfter, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all SSH keys for a group
     * Lists all SSH public keys associated with enterprise users in a top-level-group. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param createdBefore Return SSH keys created on or before the specified time.
     * @param createdAfter Return SSH keys created on or after the specified time.
     * @param expiresBefore Return SSH keys that expire on or before the specified time.
     * @param expiresAfter Return SSH keys that expire on or after the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdManageSshKeysWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime expiresBefore, @jakarta.annotation.Nullable OffsetDateTime expiresAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdManageSshKeysRequestCreation(id, createdBefore, createdAfter, expiresBefore, expiresAfter, page, perPage);
    }

    /**
     * Rotate a personal access token for an enterprise user
     * Rotates a specified personal access token for an enterprise user associated with the top-level group. This revokes the previous token and creates a token that expires after one week. Rotating a token with granular scopes returns those scopes in a &#x60;granular_scopes&#x60; attribute on the new token. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632314) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param patId ID of the personal access token.
     * @param postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest The postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer patId, @jakarta.annotation.Nullable PostApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdManagePersonalAccessTokensPatIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'patId' is set
        if (patId == null) {
            throw new RestClientResponseException("Missing the required parameter 'patId' when calling postApiV4GroupsIdManagePersonalAccessTokensPatIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pat_id", patId);

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

        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/manage/personal_access_tokens/{pat_id}/rotate", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rotate a personal access token for an enterprise user
     * Rotates a specified personal access token for an enterprise user associated with the top-level group. This revokes the previous token and creates a token that expires after one week. Rotating a token with granular scopes returns those scopes in a &#x60;granular_scopes&#x60; attribute on the new token. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632314) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param patId ID of the personal access token.
     * @param postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest The postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalAccessTokenWithToken postApiV4GroupsIdManagePersonalAccessTokensPatIdRotate(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer patId, @jakarta.annotation.Nullable PostApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequestCreation(id, patId, postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest).body(localVarReturnType);
    }

    /**
     * Rotate a personal access token for an enterprise user
     * Rotates a specified personal access token for an enterprise user associated with the top-level group. This revokes the previous token and creates a token that expires after one week. Rotating a token with granular scopes returns those scopes in a &#x60;granular_scopes&#x60; attribute on the new token. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632314) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param patId ID of the personal access token.
     * @param postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest The postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPersonalAccessTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalAccessTokenWithToken> postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer patId, @jakarta.annotation.Nullable PostApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequestCreation(id, patId, postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest).toEntity(localVarReturnType);
    }

    /**
     * Rotate a personal access token for an enterprise user
     * Rotates a specified personal access token for an enterprise user associated with the top-level group. This revokes the previous token and creates a token that expires after one week. Rotating a token with granular scopes returns those scopes in a &#x60;granular_scopes&#x60; attribute on the new token. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632314) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param patId ID of the personal access token.
     * @param postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest The postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer patId, @jakarta.annotation.Nullable PostApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest) throws RestClientResponseException {
        return postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequestCreation(id, patId, postApiV4GroupsIdManagePersonalAccessTokensPatIdRotateRequest);
    }

    /**
     * Rotate a group or project access token for a group
     * Rotates a specified group or project access token associated with a top-level group. This revokes the previous token and creates a new token.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param pratId ID of the resource access token.
     * @param postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest The postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pratId, @jakarta.annotation.Nullable PostApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdManageResourceAccessTokensPratIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pratId' is set
        if (pratId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pratId' when calling postApiV4GroupsIdManageResourceAccessTokensPratIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("prat_id", pratId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/manage/resource_access_tokens/{prat_id}/rotate", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rotate a group or project access token for a group
     * Rotates a specified group or project access token associated with a top-level group. This revokes the previous token and creates a new token.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param pratId ID of the resource access token.
     * @param postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest The postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsIdManageResourceAccessTokensPratIdRotate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pratId, @jakarta.annotation.Nullable PostApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequestCreation(id, pratId, postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest).body(localVarReturnType);
    }

    /**
     * Rotate a group or project access token for a group
     * Rotates a specified group or project access token associated with a top-level group. This revokes the previous token and creates a new token.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param pratId ID of the resource access token.
     * @param postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest The postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsIdManageResourceAccessTokensPratIdRotateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pratId, @jakarta.annotation.Nullable PostApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequestCreation(id, pratId, postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest).toEntity(localVarReturnType);
    }

    /**
     * Rotate a group or project access token for a group
     * Rotates a specified group or project access token associated with a top-level group. This revokes the previous token and creates a new token.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param pratId ID of the resource access token.
     * @param postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest The postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdManageResourceAccessTokensPratIdRotateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pratId, @jakarta.annotation.Nullable PostApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest) throws RestClientResponseException {
        return postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequestCreation(id, pratId, postApiV4GroupsIdManageResourceAccessTokensPratIdRotateRequest);
    }
}
