package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesPersonalAccessToken;
import ai.gebo.gitlab.client.model.APIEntitiesPersonalAccessTokenWithToken;
import ai.gebo.gitlab.client.model.APIEntitiesServiceAccount;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4GroupsIdServiceAccountsUserIdRequest;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdServiceAccountsUserIdRequest;
import ai.gebo.gitlab.client.model.PatchApiV4ServiceAccountsUserIdRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdServiceAccountsRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdServiceAccountsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest;
import ai.gebo.gitlab.client.model.PostApiV4ServiceAccountsRequest;

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
public class ServiceAccountsApi {
    private ApiClient apiClient;

    public ServiceAccountsApi() {
        this(new ApiClient());
    }

    public ServiceAccountsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a group service account
     * Deletes a specified group service account. Available only for group Owners and administrators. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - Resource deleted
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Group not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @param hardDelete If &#x60;true&#x60;, contributions that would usually be [moved to a ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records) are deleted instead, along with groups owned solely by this service account.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdServiceAccountsUserIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdServiceAccountsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling deleteApiV4GroupsIdServiceAccountsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/service_accounts/{user_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a group service account
     * Deletes a specified group service account. Available only for group Owners and administrators. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - Resource deleted
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Group not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @param hardDelete If &#x60;true&#x60;, contributions that would usually be [moved to a ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records) are deleted instead, along with groups owned solely by this service account.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdServiceAccountsUserId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdServiceAccountsUserIdRequestCreation(id, userId, hardDelete).body(localVarReturnType);
    }

    /**
     * Delete a group service account
     * Deletes a specified group service account. Available only for group Owners and administrators. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - Resource deleted
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Group not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @param hardDelete If &#x60;true&#x60;, contributions that would usually be [moved to a ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records) are deleted instead, along with groups owned solely by this service account.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdServiceAccountsUserIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdServiceAccountsUserIdRequestCreation(id, userId, hardDelete).toEntity(localVarReturnType);
    }

    /**
     * Delete a group service account
     * Deletes a specified group service account. Available only for group Owners and administrators. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - Resource deleted
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Group not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @param hardDelete If &#x60;true&#x60;, contributions that would usually be [moved to a ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records) are deleted instead, along with groups owned solely by this service account.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdServiceAccountsUserIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        return deleteApiV4GroupsIdServiceAccountsUserIdRequestCreation(id, userId, hardDelete);
    }

    /**
     * Revoke a personal access token for a group service account
     * Revokes a specified personal access token for a group service account. This feature was introduced in GitLab 17.11.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling deleteApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling deleteApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("token_id", tokenId);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/service_accounts/{user_id}/personal_access_tokens/{token_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke a personal access token for a group service account
     * Revokes a specified personal access token for a group service account. This feature was introduced in GitLab 17.11.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRequestCreation(id, tokenId, userId).body(localVarReturnType);
    }

    /**
     * Revoke a personal access token for a group service account
     * Revokes a specified personal access token for a group service account. This feature was introduced in GitLab 17.11.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRequestCreation(id, tokenId, userId).toEntity(localVarReturnType);
    }

    /**
     * Revoke a personal access token for a group service account
     * Revokes a specified personal access token for a group service account. This feature was introduced in GitLab 17.11.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return deleteApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRequestCreation(id, tokenId, userId);
    }

    /**
     * Delete a project service account
     * Deletes a specified project service account. Available only for project Owners, Maintainers, and administrators.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Project not found
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @param hardDelete If &#x60;true&#x60;, contributions that would usually be [moved to a ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records) are deleted instead, along with groups owned solely by this service account.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdServiceAccountsUserIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdServiceAccountsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling deleteApiV4ProjectsIdServiceAccountsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/service_accounts/{user_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a project service account
     * Deletes a specified project service account. Available only for project Owners, Maintainers, and administrators.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Project not found
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @param hardDelete If &#x60;true&#x60;, contributions that would usually be [moved to a ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records) are deleted instead, along with groups owned solely by this service account.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdServiceAccountsUserId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdServiceAccountsUserIdRequestCreation(id, userId, hardDelete).body(localVarReturnType);
    }

    /**
     * Delete a project service account
     * Deletes a specified project service account. Available only for project Owners, Maintainers, and administrators.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Project not found
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @param hardDelete If &#x60;true&#x60;, contributions that would usually be [moved to a ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records) are deleted instead, along with groups owned solely by this service account.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdServiceAccountsUserIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdServiceAccountsUserIdRequestCreation(id, userId, hardDelete).toEntity(localVarReturnType);
    }

    /**
     * Delete a project service account
     * Deletes a specified project service account. Available only for project Owners, Maintainers, and administrators.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Project not found
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @param hardDelete If &#x60;true&#x60;, contributions that would usually be [moved to a ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records) are deleted instead, along with groups owned solely by this service account.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdServiceAccountsUserIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        return deleteApiV4ProjectsIdServiceAccountsUserIdRequestCreation(id, userId, hardDelete);
    }

    /**
     * Revoke a personal access token for a project service account
     * Revokes a specified personal access token for a project service account.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling deleteApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling deleteApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("token_id", tokenId);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/service_accounts/{user_id}/personal_access_tokens/{token_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke a personal access token for a project service account
     * Revokes a specified personal access token for a project service account.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRequestCreation(id, tokenId, userId).body(localVarReturnType);
    }

    /**
     * Revoke a personal access token for a project service account
     * Revokes a specified personal access token for a project service account.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRequestCreation(id, tokenId, userId).toEntity(localVarReturnType);
    }

    /**
     * Revoke a personal access token for a project service account
     * Revokes a specified personal access token for a project service account.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRequestCreation(id, tokenId, userId);
    }

    /**
     * List all group service accounts
     * Lists all service accounts in a specified group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 17.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Group not found
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return List&lt;APIEntitiesServiceAccount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdServiceAccountsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdServiceAccounts", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesServiceAccount>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/service_accounts", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group service accounts
     * Lists all service accounts in a specified group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 17.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Group not found
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return List&lt;APIEntitiesServiceAccount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesServiceAccount> getApiV4GroupsIdServiceAccounts(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesServiceAccount>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdServiceAccountsRequestCreation(id, page, perPage, orderBy, sort).body(localVarReturnType);
    }

    /**
     * List all group service accounts
     * Lists all service accounts in a specified group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 17.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Group not found
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return ResponseEntity&lt;List&lt;APIEntitiesServiceAccount&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesServiceAccount>> getApiV4GroupsIdServiceAccountsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesServiceAccount>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdServiceAccountsRequestCreation(id, page, perPage, orderBy, sort).toEntity(localVarReturnType);
    }

    /**
     * List all group service accounts
     * Lists all service accounts in a specified group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 17.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Group not found
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdServiceAccountsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getApiV4GroupsIdServiceAccountsRequestCreation(id, page, perPage, orderBy, sort);
    }

    /**
     * Get a single group service account
     * Gets a specified service account in a specified group. Returns &#x60;404 Not Found&#x60; if the service account does not exist in the group, and &#x60;400 Bad Request&#x60; if the user exists in the group but is not a service account.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdServiceAccountsUserIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdServiceAccountsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4GroupsIdServiceAccountsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/service_accounts/{user_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a single group service account
     * Gets a specified service account in a specified group. Returns &#x60;404 Not Found&#x60; if the service account does not exist in the group, and &#x60;400 Bad Request&#x60; if the user exists in the group but is not a service account.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesServiceAccount getApiV4GroupsIdServiceAccountsUserId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdServiceAccountsUserIdRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Get a single group service account
     * Gets a specified service account in a specified group. Returns &#x60;404 Not Found&#x60; if the service account does not exist in the group, and &#x60;400 Bad Request&#x60; if the user exists in the group but is not a service account.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @return ResponseEntity&lt;APIEntitiesServiceAccount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesServiceAccount> getApiV4GroupsIdServiceAccountsUserIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdServiceAccountsUserIdRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Get a single group service account
     * Gets a specified service account in a specified group. Returns &#x60;404 Not Found&#x60; if the service account does not exist in the group, and &#x60;400 Bad Request&#x60; if the user exists in the group but is not a service account.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdServiceAccountsUserIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return getApiV4GroupsIdServiceAccountsUserIdRequestCreation(id, userId);
    }

    /**
     * List all personal access tokens for a group service account
     * Lists all personal access tokens for a specified group service account. For tokens created with granular scopes, the response also includes a &#x60;granular_scopes&#x60; attribute. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/633317) in GitLab 19.6. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Group Not Found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
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
    private ResponseSpec getApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/service_accounts/{user_id}/personal_access_tokens", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all personal access tokens for a group service account
     * Lists all personal access tokens for a specified group service account. For tokens created with granular scopes, the response also includes a &#x60;granular_scopes&#x60; attribute. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/633317) in GitLab 19.6. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Group Not Found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
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
    public List<APIEntitiesPersonalAccessToken> getApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokens(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPersonalAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(id, userId, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort, page, perPage).body(localVarReturnType);
    }

    /**
     * List all personal access tokens for a group service account
     * Lists all personal access tokens for a specified group service account. For tokens created with granular scopes, the response also includes a &#x60;granular_scopes&#x60; attribute. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/633317) in GitLab 19.6. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Group Not Found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
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
    public ResponseEntity<List<APIEntitiesPersonalAccessToken>> getApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPersonalAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(id, userId, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all personal access tokens for a group service account
     * Lists all personal access tokens for a specified group service account. For tokens created with granular scopes, the response also includes a &#x60;granular_scopes&#x60; attribute. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/633317) in GitLab 19.6. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Group Not Found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
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
    public ResponseSpec getApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(id, userId, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort, page, perPage);
    }

    /**
     * List all project service accounts
     * Lists all service accounts in a specified project. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Project not found
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return List&lt;APIEntitiesServiceAccount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdServiceAccountsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdServiceAccounts", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesServiceAccount>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/service_accounts", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project service accounts
     * Lists all service accounts in a specified project. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Project not found
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return List&lt;APIEntitiesServiceAccount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesServiceAccount> getApiV4ProjectsIdServiceAccounts(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesServiceAccount>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdServiceAccountsRequestCreation(id, page, perPage, orderBy, sort).body(localVarReturnType);
    }

    /**
     * List all project service accounts
     * Lists all service accounts in a specified project. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Project not found
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return ResponseEntity&lt;List&lt;APIEntitiesServiceAccount&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesServiceAccount>> getApiV4ProjectsIdServiceAccountsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesServiceAccount>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdServiceAccountsRequestCreation(id, page, perPage, orderBy, sort).toEntity(localVarReturnType);
    }

    /**
     * List all project service accounts
     * Lists all service accounts in a specified project. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Project not found
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdServiceAccountsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getApiV4ProjectsIdServiceAccountsRequestCreation(id, page, perPage, orderBy, sort);
    }

    /**
     * Get a single project service account
     * Gets a specified service account in a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdServiceAccountsUserIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdServiceAccountsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4ProjectsIdServiceAccountsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/service_accounts/{user_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a single project service account
     * Gets a specified service account in a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesServiceAccount getApiV4ProjectsIdServiceAccountsUserId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdServiceAccountsUserIdRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Get a single project service account
     * Gets a specified service account in a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @return ResponseEntity&lt;APIEntitiesServiceAccount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesServiceAccount> getApiV4ProjectsIdServiceAccountsUserIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdServiceAccountsUserIdRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Get a single project service account
     * Gets a specified service account in a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdServiceAccountsUserIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return getApiV4ProjectsIdServiceAccountsUserIdRequestCreation(id, userId);
    }

    /**
     * List all personal access tokens for a project service account
     * Lists all personal access tokens for a specified project service account. For tokens created with granular scopes, the response also includes a &#x60;granular_scopes&#x60; attribute. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/633317) in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
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
    private ResponseSpec getApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/service_accounts/{user_id}/personal_access_tokens", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all personal access tokens for a project service account
     * Lists all personal access tokens for a specified project service account. For tokens created with granular scopes, the response also includes a &#x60;granular_scopes&#x60; attribute. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/633317) in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
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
    public List<APIEntitiesPersonalAccessToken> getApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokens(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPersonalAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(id, userId, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort, page, perPage).body(localVarReturnType);
    }

    /**
     * List all personal access tokens for a project service account
     * Lists all personal access tokens for a specified project service account. For tokens created with granular scopes, the response also includes a &#x60;granular_scopes&#x60; attribute. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/633317) in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
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
    public ResponseEntity<List<APIEntitiesPersonalAccessToken>> getApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPersonalAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(id, userId, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all personal access tokens for a project service account
     * Lists all personal access tokens for a specified project service account. For tokens created with granular scopes, the response also includes a &#x60;granular_scopes&#x60; attribute. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/633317) in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
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
    public ResponseSpec getApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(id, userId, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort, page, perPage);
    }

    /**
     * List all instance service accounts
     * Lists all instance service accounts. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 17.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return List&lt;APIEntitiesServiceAccount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ServiceAccountsRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesServiceAccount>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/service_accounts", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all instance service accounts
     * Lists all instance service accounts. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 17.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return List&lt;APIEntitiesServiceAccount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesServiceAccount> getApiV4ServiceAccounts(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesServiceAccount>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ServiceAccountsRequestCreation(page, perPage, orderBy, sort).body(localVarReturnType);
    }

    /**
     * List all instance service accounts
     * Lists all instance service accounts. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 17.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return ResponseEntity&lt;List&lt;APIEntitiesServiceAccount&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesServiceAccount>> getApiV4ServiceAccountsWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesServiceAccount>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ServiceAccountsRequestCreation(page, perPage, orderBy, sort).toEntity(localVarReturnType);
    }

    /**
     * List all instance service accounts
     * Lists all instance service accounts. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 17.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ServiceAccountsWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getApiV4ServiceAccountsRequestCreation(page, perPage, orderBy, sort);
    }

    /**
     * Update a group service account
     * Update a specified group service account. The username of a service account associated with a composite identity cannot be updated. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @param patchApiV4GroupsIdServiceAccountsUserIdRequest The patchApiV4GroupsIdServiceAccountsUserIdRequest parameter
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4GroupsIdServiceAccountsUserIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4GroupsIdServiceAccountsUserIdRequest patchApiV4GroupsIdServiceAccountsUserIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4GroupsIdServiceAccountsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling patchApiV4GroupsIdServiceAccountsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/service_accounts/{user_id}", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4GroupsIdServiceAccountsUserIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a group service account
     * Update a specified group service account. The username of a service account associated with a composite identity cannot be updated. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @param patchApiV4GroupsIdServiceAccountsUserIdRequest The patchApiV4GroupsIdServiceAccountsUserIdRequest parameter
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesServiceAccount patchApiV4GroupsIdServiceAccountsUserId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4GroupsIdServiceAccountsUserIdRequest patchApiV4GroupsIdServiceAccountsUserIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4GroupsIdServiceAccountsUserIdRequestCreation(id, userId, patchApiV4GroupsIdServiceAccountsUserIdRequest).body(localVarReturnType);
    }

    /**
     * Update a group service account
     * Update a specified group service account. The username of a service account associated with a composite identity cannot be updated. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @param patchApiV4GroupsIdServiceAccountsUserIdRequest The patchApiV4GroupsIdServiceAccountsUserIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesServiceAccount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesServiceAccount> patchApiV4GroupsIdServiceAccountsUserIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4GroupsIdServiceAccountsUserIdRequest patchApiV4GroupsIdServiceAccountsUserIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4GroupsIdServiceAccountsUserIdRequestCreation(id, userId, patchApiV4GroupsIdServiceAccountsUserIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a group service account
     * Update a specified group service account. The username of a service account associated with a composite identity cannot be updated. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @param patchApiV4GroupsIdServiceAccountsUserIdRequest The patchApiV4GroupsIdServiceAccountsUserIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4GroupsIdServiceAccountsUserIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4GroupsIdServiceAccountsUserIdRequest patchApiV4GroupsIdServiceAccountsUserIdRequest) throws RestClientResponseException {
        return patchApiV4GroupsIdServiceAccountsUserIdRequestCreation(id, userId, patchApiV4GroupsIdServiceAccountsUserIdRequest);
    }

    /**
     * Update a project service account
     * Update a specified project service account.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @param patchApiV4ProjectsIdServiceAccountsUserIdRequest The patchApiV4ProjectsIdServiceAccountsUserIdRequest parameter
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4ProjectsIdServiceAccountsUserIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4ProjectsIdServiceAccountsUserIdRequest patchApiV4ProjectsIdServiceAccountsUserIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4ProjectsIdServiceAccountsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling patchApiV4ProjectsIdServiceAccountsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/service_accounts/{user_id}", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4ProjectsIdServiceAccountsUserIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a project service account
     * Update a specified project service account.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @param patchApiV4ProjectsIdServiceAccountsUserIdRequest The patchApiV4ProjectsIdServiceAccountsUserIdRequest parameter
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesServiceAccount patchApiV4ProjectsIdServiceAccountsUserId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4ProjectsIdServiceAccountsUserIdRequest patchApiV4ProjectsIdServiceAccountsUserIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdServiceAccountsUserIdRequestCreation(id, userId, patchApiV4ProjectsIdServiceAccountsUserIdRequest).body(localVarReturnType);
    }

    /**
     * Update a project service account
     * Update a specified project service account.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @param patchApiV4ProjectsIdServiceAccountsUserIdRequest The patchApiV4ProjectsIdServiceAccountsUserIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesServiceAccount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesServiceAccount> patchApiV4ProjectsIdServiceAccountsUserIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4ProjectsIdServiceAccountsUserIdRequest patchApiV4ProjectsIdServiceAccountsUserIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdServiceAccountsUserIdRequestCreation(id, userId, patchApiV4ProjectsIdServiceAccountsUserIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a project service account
     * Update a specified project service account.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 User not found
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @param patchApiV4ProjectsIdServiceAccountsUserIdRequest The patchApiV4ProjectsIdServiceAccountsUserIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4ProjectsIdServiceAccountsUserIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4ProjectsIdServiceAccountsUserIdRequest patchApiV4ProjectsIdServiceAccountsUserIdRequest) throws RestClientResponseException {
        return patchApiV4ProjectsIdServiceAccountsUserIdRequestCreation(id, userId, patchApiV4ProjectsIdServiceAccountsUserIdRequest);
    }

    /**
     * Update an instance service account
     * Updates a specified instance service account. This feature was introduced in GitLab 18.2.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @param userId ID of the service account.
     * @param patchApiV4ServiceAccountsUserIdRequest The patchApiV4ServiceAccountsUserIdRequest parameter
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4ServiceAccountsUserIdRequestCreation(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4ServiceAccountsUserIdRequest patchApiV4ServiceAccountsUserIdRequest) throws RestClientResponseException {
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling patchApiV4ServiceAccountsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/service_accounts/{user_id}", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4ServiceAccountsUserIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an instance service account
     * Updates a specified instance service account. This feature was introduced in GitLab 18.2.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @param userId ID of the service account.
     * @param patchApiV4ServiceAccountsUserIdRequest The patchApiV4ServiceAccountsUserIdRequest parameter
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesServiceAccount patchApiV4ServiceAccountsUserId(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4ServiceAccountsUserIdRequest patchApiV4ServiceAccountsUserIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ServiceAccountsUserIdRequestCreation(userId, patchApiV4ServiceAccountsUserIdRequest).body(localVarReturnType);
    }

    /**
     * Update an instance service account
     * Updates a specified instance service account. This feature was introduced in GitLab 18.2.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @param userId ID of the service account.
     * @param patchApiV4ServiceAccountsUserIdRequest The patchApiV4ServiceAccountsUserIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesServiceAccount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesServiceAccount> patchApiV4ServiceAccountsUserIdWithHttpInfo(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4ServiceAccountsUserIdRequest patchApiV4ServiceAccountsUserIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ServiceAccountsUserIdRequestCreation(userId, patchApiV4ServiceAccountsUserIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an instance service account
     * Updates a specified instance service account. This feature was introduced in GitLab 18.2.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @param userId ID of the service account.
     * @param patchApiV4ServiceAccountsUserIdRequest The patchApiV4ServiceAccountsUserIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4ServiceAccountsUserIdWithResponseSpec(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PatchApiV4ServiceAccountsUserIdRequest patchApiV4ServiceAccountsUserIdRequest) throws RestClientResponseException {
        return patchApiV4ServiceAccountsUserIdRequestCreation(userId, patchApiV4ServiceAccountsUserIdRequest);
    }

    /**
     * Create a group service account
     * Creates a service account in a specified group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Group not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdServiceAccountsRequest The postApiV4GroupsIdServiceAccountsRequest parameter
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdServiceAccountsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4GroupsIdServiceAccountsRequest postApiV4GroupsIdServiceAccountsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdServiceAccounts", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/service_accounts", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdServiceAccountsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group service account
     * Creates a service account in a specified group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Group not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdServiceAccountsRequest The postApiV4GroupsIdServiceAccountsRequest parameter
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesServiceAccount postApiV4GroupsIdServiceAccounts(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4GroupsIdServiceAccountsRequest postApiV4GroupsIdServiceAccountsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdServiceAccountsRequestCreation(id, postApiV4GroupsIdServiceAccountsRequest).body(localVarReturnType);
    }

    /**
     * Create a group service account
     * Creates a service account in a specified group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Group not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdServiceAccountsRequest The postApiV4GroupsIdServiceAccountsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesServiceAccount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesServiceAccount> postApiV4GroupsIdServiceAccountsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4GroupsIdServiceAccountsRequest postApiV4GroupsIdServiceAccountsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdServiceAccountsRequestCreation(id, postApiV4GroupsIdServiceAccountsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a group service account
     * Creates a service account in a specified group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Group not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdServiceAccountsRequest The postApiV4GroupsIdServiceAccountsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdServiceAccountsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4GroupsIdServiceAccountsRequest postApiV4GroupsIdServiceAccountsRequest) throws RestClientResponseException {
        return postApiV4GroupsIdServiceAccountsRequestCreation(id, postApiV4GroupsIdServiceAccountsRequest);
    }

    /**
     * Create a personal access token for a group service account
     * Creates a personal access token for a specified group service account. Available only for group Owners and administrators. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. This feature was introduced in GitLab 16.1.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @param postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest The postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest' is set
        if (postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest' when calling postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/service_accounts/{user_id}/personal_access_tokens", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a personal access token for a group service account
     * Creates a personal access token for a specified group service account. Available only for group Owners and administrators. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. This feature was introduced in GitLab 16.1.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @param postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest The postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalAccessTokenWithToken postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokens(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(id, userId, postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest).body(localVarReturnType);
    }

    /**
     * Create a personal access token for a group service account
     * Creates a personal access token for a specified group service account. Available only for group Owners and administrators. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. This feature was introduced in GitLab 16.1.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @param postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest The postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPersonalAccessTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalAccessTokenWithToken> postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(id, userId, postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a personal access token for a group service account
     * Creates a personal access token for a specified group service account. Available only for group Owners and administrators. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. This feature was introduced in GitLab 16.1.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the service account.
     * @param postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest The postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest) throws RestClientResponseException {
        return postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(id, userId, postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest);
    }

    /**
     * Rotate a personal access token for a group service account
     * Rotates a specified personal access token for a group service account. This revokes the existing token and creates a token with the same name, description, and scopes. Rotating a token with granular scopes returns those scopes in a &#x60;granular_scopes&#x60; attribute on the new token. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632896) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @param postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest The postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PostApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("token_id", tokenId);
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

        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/service_accounts/{user_id}/personal_access_tokens/{token_id}/rotate", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rotate a personal access token for a group service account
     * Rotates a specified personal access token for a group service account. This revokes the existing token and creates a token with the same name, description, and scopes. Rotating a token with granular scopes returns those scopes in a &#x60;granular_scopes&#x60; attribute on the new token. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632896) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @param postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest The postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalAccessTokenWithToken postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PostApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequestCreation(id, tokenId, userId, postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest).body(localVarReturnType);
    }

    /**
     * Rotate a personal access token for a group service account
     * Rotates a specified personal access token for a group service account. This revokes the existing token and creates a token with the same name, description, and scopes. Rotating a token with granular scopes returns those scopes in a &#x60;granular_scopes&#x60; attribute on the new token. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632896) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @param postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest The postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPersonalAccessTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalAccessTokenWithToken> postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PostApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequestCreation(id, tokenId, userId, postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest).toEntity(localVarReturnType);
    }

    /**
     * Rotate a personal access token for a group service account
     * Rotates a specified personal access token for a group service account. This revokes the existing token and creates a token with the same name, description, and scopes. Rotating a token with granular scopes returns those scopes in a &#x60;granular_scopes&#x60; attribute on the new token. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632896) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @param postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest The postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PostApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        return postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequestCreation(id, tokenId, userId, postApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest);
    }

    /**
     * Create a project service account
     * Creates a service account in a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Project not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdServiceAccountsRequest The postApiV4ProjectsIdServiceAccountsRequest parameter
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdServiceAccountsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdServiceAccountsRequest postApiV4ProjectsIdServiceAccountsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdServiceAccounts", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/service_accounts", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdServiceAccountsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a project service account
     * Creates a service account in a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Project not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdServiceAccountsRequest The postApiV4ProjectsIdServiceAccountsRequest parameter
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesServiceAccount postApiV4ProjectsIdServiceAccounts(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdServiceAccountsRequest postApiV4ProjectsIdServiceAccountsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdServiceAccountsRequestCreation(id, postApiV4ProjectsIdServiceAccountsRequest).body(localVarReturnType);
    }

    /**
     * Create a project service account
     * Creates a service account in a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Project not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdServiceAccountsRequest The postApiV4ProjectsIdServiceAccountsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesServiceAccount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesServiceAccount> postApiV4ProjectsIdServiceAccountsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdServiceAccountsRequest postApiV4ProjectsIdServiceAccountsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdServiceAccountsRequestCreation(id, postApiV4ProjectsIdServiceAccountsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a project service account
     * Creates a service account in a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Project not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdServiceAccountsRequest The postApiV4ProjectsIdServiceAccountsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdServiceAccountsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdServiceAccountsRequest postApiV4ProjectsIdServiceAccountsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdServiceAccountsRequestCreation(id, postApiV4ProjectsIdServiceAccountsRequest);
    }

    /**
     * Create a personal access token for a project service account
     * Creates a personal access token for a specified project service account. Available only for project Owners, Maintainers, and administrators. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @param postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest The postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest' is set
        if (postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest' when calling postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/service_accounts/{user_id}/personal_access_tokens", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a personal access token for a project service account
     * Creates a personal access token for a specified project service account. Available only for project Owners, Maintainers, and administrators. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @param postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest The postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalAccessTokenWithToken postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokens(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(id, userId, postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest).body(localVarReturnType);
    }

    /**
     * Create a personal access token for a project service account
     * Creates a personal access token for a specified project service account. Available only for project Owners, Maintainers, and administrators. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @param postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest The postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPersonalAccessTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalAccessTokenWithToken> postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(id, userId, postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a personal access token for a project service account
     * Creates a personal access token for a specified project service account. Available only for project Owners, Maintainers, and administrators. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the service account.
     * @param postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest The postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequestCreation(id, userId, postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest);
    }

    /**
     * Rotate a personal access token for a project service account
     * Rotates a specified personal access token for a project service account. This revokes the existing token and creates a token with the same name, description, and scopes. Rotating a token with granular scopes returns those scopes in a &#x60;granular_scopes&#x60; attribute on the new token. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632896) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @param postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest The postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PostApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("token_id", tokenId);
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

        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/service_accounts/{user_id}/personal_access_tokens/{token_id}/rotate", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rotate a personal access token for a project service account
     * Rotates a specified personal access token for a project service account. This revokes the existing token and creates a token with the same name, description, and scopes. Rotating a token with granular scopes returns those scopes in a &#x60;granular_scopes&#x60; attribute on the new token. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632896) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @param postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest The postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalAccessTokenWithToken postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PostApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequestCreation(id, tokenId, userId, postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest).body(localVarReturnType);
    }

    /**
     * Rotate a personal access token for a project service account
     * Rotates a specified personal access token for a project service account. This revokes the existing token and creates a token with the same name, description, and scopes. Rotating a token with granular scopes returns those scopes in a &#x60;granular_scopes&#x60; attribute on the new token. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632896) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @param postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest The postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPersonalAccessTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalAccessTokenWithToken> postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PostApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequestCreation(id, tokenId, userId, postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest).toEntity(localVarReturnType);
    }

    /**
     * Rotate a personal access token for a project service account
     * Rotates a specified personal access token for a project service account. This revokes the existing token and creates a token with the same name, description, and scopes. Rotating a token with granular scopes returns those scopes in a &#x60;granular_scopes&#x60; attribute on the new token. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632896) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the personal access token.
     * @param userId ID of the service account.
     * @param postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest The postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PostApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequestCreation(id, tokenId, userId, postApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest);
    }

    /**
     * Create an instance service account
     * Creates an instance service account. If the email address is already in use by another user, &#x60;400 Bad request&#x60; is returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4ServiceAccountsRequest The postApiV4ServiceAccountsRequest parameter
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ServiceAccountsRequestCreation(@jakarta.annotation.Nullable PostApiV4ServiceAccountsRequest postApiV4ServiceAccountsRequest) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/service_accounts", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ServiceAccountsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an instance service account
     * Creates an instance service account. If the email address is already in use by another user, &#x60;400 Bad request&#x60; is returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4ServiceAccountsRequest The postApiV4ServiceAccountsRequest parameter
     * @return APIEntitiesServiceAccount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesServiceAccount postApiV4ServiceAccounts(@jakarta.annotation.Nullable PostApiV4ServiceAccountsRequest postApiV4ServiceAccountsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ServiceAccountsRequestCreation(postApiV4ServiceAccountsRequest).body(localVarReturnType);
    }

    /**
     * Create an instance service account
     * Creates an instance service account. If the email address is already in use by another user, &#x60;400 Bad request&#x60; is returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4ServiceAccountsRequest The postApiV4ServiceAccountsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesServiceAccount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesServiceAccount> postApiV4ServiceAccountsWithHttpInfo(@jakarta.annotation.Nullable PostApiV4ServiceAccountsRequest postApiV4ServiceAccountsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesServiceAccount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ServiceAccountsRequestCreation(postApiV4ServiceAccountsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an instance service account
     * Creates an instance service account. If the email address is already in use by another user, &#x60;400 Bad request&#x60; is returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4ServiceAccountsRequest The postApiV4ServiceAccountsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ServiceAccountsWithResponseSpec(@jakarta.annotation.Nullable PostApiV4ServiceAccountsRequest postApiV4ServiceAccountsRequest) throws RestClientResponseException {
        return postApiV4ServiceAccountsRequestCreation(postApiV4ServiceAccountsRequest);
    }
}
