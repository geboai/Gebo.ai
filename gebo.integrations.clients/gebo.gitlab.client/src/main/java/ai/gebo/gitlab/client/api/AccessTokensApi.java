package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesImpersonationToken;
import ai.gebo.gitlab.client.model.APIEntitiesImpersonationTokenWithToken;
import ai.gebo.gitlab.client.model.APIEntitiesPersonalAccessToken;
import ai.gebo.gitlab.client.model.APIEntitiesPersonalAccessTokenAssociations;
import ai.gebo.gitlab.client.model.APIEntitiesPersonalAccessTokenWithToken;
import ai.gebo.gitlab.client.model.APIEntitiesResourceAccessToken;
import ai.gebo.gitlab.client.model.APIEntitiesResourceAccessTokenWithToken;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4AdminTokenRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdAccessTokensRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdAccessTokensSelfRotateRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdAccessTokensTokenIdRotateRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest;
import ai.gebo.gitlab.client.model.PostApiV4PersonalAccessTokensIdRotateRequest;
import ai.gebo.gitlab.client.model.PostApiV4PersonalAccessTokensSelfRotateRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdAccessTokensRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdAccessTokensSelfRotateRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdAccessTokensTokenIdRotateRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdServiceAccountsUserIdPersonalAccessTokensTokenIdRotateRequest;
import ai.gebo.gitlab.client.model.PostApiV4UserPersonalAccessTokensRequest;
import ai.gebo.gitlab.client.model.PostApiV4UsersUserIdImpersonationTokensRequest;
import ai.gebo.gitlab.client.model.PostApiV4UsersUserIdPersonalAccessTokensRequest;

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
public class AccessTokensApi {
    private ApiClient apiClient;

    public AccessTokensApi() {
        this(new ApiClient());
    }

    public AccessTokensApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Revoke a token.
     * This feature was introduced in GitLab 17.7.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable
     * <p><b>400</b> - Bad Request
     * @param token The token to revoke. To avoid exposing the token in the URL, send it in the JSON request body instead of as a query parameter.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4AdminTokenRequestCreation(@jakarta.annotation.Nullable String token) throws RestClientResponseException {
        // verify the required parameter 'token' is set
        if (token == null) {
            throw new RestClientResponseException("Missing the required parameter 'token' when calling deleteApiV4AdminToken", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "token", token));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/token", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke a token.
     * This feature was introduced in GitLab 17.7.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable
     * <p><b>400</b> - Bad Request
     * @param token The token to revoke. To avoid exposing the token in the URL, send it in the JSON request body instead of as a query parameter.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4AdminToken(@jakarta.annotation.Nullable String token) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4AdminTokenRequestCreation(token).body(localVarReturnType);
    }

    /**
     * Revoke a token.
     * This feature was introduced in GitLab 17.7.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable
     * <p><b>400</b> - Bad Request
     * @param token The token to revoke. To avoid exposing the token in the URL, send it in the JSON request body instead of as a query parameter.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4AdminTokenWithHttpInfo(@jakarta.annotation.Nullable String token) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4AdminTokenRequestCreation(token).toEntity(localVarReturnType);
    }

    /**
     * Revoke a token.
     * This feature was introduced in GitLab 17.7.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable
     * <p><b>400</b> - Bad Request
     * @param token The token to revoke. To avoid exposing the token in the URL, send it in the JSON request body instead of as a query parameter.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4AdminTokenWithResponseSpec(@jakarta.annotation.Nullable String token) throws RestClientResponseException {
        return deleteApiV4AdminTokenRequestCreation(token);
    }

    /**
     * Revoke a group access token
     * Revokes a specified group access token.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdAccessTokensTokenIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdAccessTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling deleteApiV4GroupsIdAccessTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("token_id", tokenId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/access_tokens/{token_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke a group access token
     * Revokes a specified group access token.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdAccessTokensTokenId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdAccessTokensTokenIdRequestCreation(id, tokenId).body(localVarReturnType);
    }

    /**
     * Revoke a group access token
     * Revokes a specified group access token.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdAccessTokensTokenIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdAccessTokensTokenIdRequestCreation(id, tokenId).toEntity(localVarReturnType);
    }

    /**
     * Revoke a group access token
     * Revokes a specified group access token.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdAccessTokensTokenIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        return deleteApiV4GroupsIdAccessTokensTokenIdRequestCreation(id, tokenId);
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
     * Revoke a personal access token
     * Revokes a specified personal access token. Administrators can revoke tokens for any user. Non-administrators can only revoke their own tokens.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID of the personal access token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4PersonalAccessTokensIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4PersonalAccessTokensId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/personal_access_tokens/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke a personal access token
     * Revokes a specified personal access token. Administrators can revoke tokens for any user. Non-administrators can only revoke their own tokens.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID of the personal access token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4PersonalAccessTokensId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4PersonalAccessTokensIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Revoke a personal access token
     * Revokes a specified personal access token. Administrators can revoke tokens for any user. Non-administrators can only revoke their own tokens.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID of the personal access token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4PersonalAccessTokensIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4PersonalAccessTokensIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Revoke a personal access token
     * Revokes a specified personal access token. Administrators can revoke tokens for any user. Non-administrators can only revoke their own tokens.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID of the personal access token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4PersonalAccessTokensIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteApiV4PersonalAccessTokensIdRequestCreation(id);
    }

    /**
     * Revoke a personal access token
     * Revokes a personal access token by passing it to the API in a header. Revokes the personal access token used to authenticate the request. A token with any scope can use this endpoint.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4PersonalAccessTokensSelfRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/personal_access_tokens/self", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke a personal access token
     * Revokes a personal access token by passing it to the API in a header. Revokes the personal access token used to authenticate the request. A token with any scope can use this endpoint.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4PersonalAccessTokensSelf() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4PersonalAccessTokensSelfRequestCreation().body(localVarReturnType);
    }

    /**
     * Revoke a personal access token
     * Revokes a personal access token by passing it to the API in a header. Revokes the personal access token used to authenticate the request. A token with any scope can use this endpoint.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4PersonalAccessTokensSelfWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4PersonalAccessTokensSelfRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Revoke a personal access token
     * Revokes a personal access token by passing it to the API in a header. Revokes the personal access token used to authenticate the request. A token with any scope can use this endpoint.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4PersonalAccessTokensSelfWithResponseSpec() throws RestClientResponseException {
        return deleteApiV4PersonalAccessTokensSelfRequestCreation();
    }

    /**
     * Revoke a project access token
     * Revokes a specified project access token.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdAccessTokensTokenIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdAccessTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling deleteApiV4ProjectsIdAccessTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("token_id", tokenId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/access_tokens/{token_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke a project access token
     * Revokes a specified project access token.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdAccessTokensTokenId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdAccessTokensTokenIdRequestCreation(id, tokenId).body(localVarReturnType);
    }

    /**
     * Revoke a project access token
     * Revokes a specified project access token.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdAccessTokensTokenIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdAccessTokensTokenIdRequestCreation(id, tokenId).toEntity(localVarReturnType);
    }

    /**
     * Revoke a project access token
     * Revokes a specified project access token.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdAccessTokensTokenIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdAccessTokensTokenIdRequestCreation(id, tokenId);
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
     * Revoke an impersonation token
     * Revokes an impersonation token for a specified user. Administrators only.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param impersonationTokenId ID of the impersonation token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4UsersUserIdImpersonationTokensImpersonationTokenIdRequestCreation(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull Integer impersonationTokenId) throws RestClientResponseException {
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling deleteApiV4UsersUserIdImpersonationTokensImpersonationTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'impersonationTokenId' is set
        if (impersonationTokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'impersonationTokenId' when calling deleteApiV4UsersUserIdImpersonationTokensImpersonationTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("user_id", userId);
        pathParams.put("impersonation_token_id", impersonationTokenId);

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
        return apiClient.invokeAPI("/api/v4/users/{user_id}/impersonation_tokens/{impersonation_token_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke an impersonation token
     * Revokes an impersonation token for a specified user. Administrators only.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param impersonationTokenId ID of the impersonation token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4UsersUserIdImpersonationTokensImpersonationTokenId(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull Integer impersonationTokenId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4UsersUserIdImpersonationTokensImpersonationTokenIdRequestCreation(userId, impersonationTokenId).body(localVarReturnType);
    }

    /**
     * Revoke an impersonation token
     * Revokes an impersonation token for a specified user. Administrators only.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param impersonationTokenId ID of the impersonation token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4UsersUserIdImpersonationTokensImpersonationTokenIdWithHttpInfo(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull Integer impersonationTokenId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UsersUserIdImpersonationTokensImpersonationTokenIdRequestCreation(userId, impersonationTokenId).toEntity(localVarReturnType);
    }

    /**
     * Revoke an impersonation token
     * Revokes an impersonation token for a specified user. Administrators only.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param impersonationTokenId ID of the impersonation token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4UsersUserIdImpersonationTokensImpersonationTokenIdWithResponseSpec(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull Integer impersonationTokenId) throws RestClientResponseException {
        return deleteApiV4UsersUserIdImpersonationTokensImpersonationTokenIdRequestCreation(userId, impersonationTokenId);
    }

    /**
     * List all group access tokens
     * Lists all group access tokens for a specified group. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/600347) in GitLab 19.2 and made generally available in GitLab 19.4.
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
     * @return List&lt;APIEntitiesResourceAccessToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdAccessTokensRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesResourceAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/access_tokens", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group access tokens
     * Lists all group access tokens for a specified group. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/600347) in GitLab 19.2 and made generally available in GitLab 19.4.
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
     * @return List&lt;APIEntitiesResourceAccessToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesResourceAccessToken> getApiV4GroupsIdAccessTokens(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdAccessTokensRequestCreation(id, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort).body(localVarReturnType);
    }

    /**
     * List all group access tokens
     * Lists all group access tokens for a specified group. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/600347) in GitLab 19.2 and made generally available in GitLab 19.4.
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
     * @return ResponseEntity&lt;List&lt;APIEntitiesResourceAccessToken&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesResourceAccessToken>> getApiV4GroupsIdAccessTokensWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdAccessTokensRequestCreation(id, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort).toEntity(localVarReturnType);
    }

    /**
     * List all group access tokens
     * Lists all group access tokens for a specified group. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/600347) in GitLab 19.2 and made generally available in GitLab 19.4.
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
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdAccessTokensWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getApiV4GroupsIdAccessTokensRequestCreation(id, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort);
    }

    /**
     * Retrieve details on a group access token
     * Retrieves details on a specified group access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the token.
     * @return APIEntitiesResourceAccessToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdAccessTokensTokenIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdAccessTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling getApiV4GroupsIdAccessTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("token_id", tokenId);

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

        ParameterizedTypeReference<APIEntitiesResourceAccessToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/access_tokens/{token_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve details on a group access token
     * Retrieves details on a specified group access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the token.
     * @return APIEntitiesResourceAccessToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceAccessToken getApiV4GroupsIdAccessTokensTokenId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdAccessTokensTokenIdRequestCreation(id, tokenId).body(localVarReturnType);
    }

    /**
     * Retrieve details on a group access token
     * Retrieves details on a specified group access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the token.
     * @return ResponseEntity&lt;APIEntitiesResourceAccessToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceAccessToken> getApiV4GroupsIdAccessTokensTokenIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdAccessTokensTokenIdRequestCreation(id, tokenId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve details on a group access token
     * Retrieves details on a specified group access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdAccessTokensTokenIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        return getApiV4GroupsIdAccessTokensTokenIdRequestCreation(id, tokenId);
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
     * List all personal access tokens
     * Lists all personal access tokens accessible by the authenticated user. For administrators, returns all personal access tokens in the instance. For non-administrators, returns all of their personal access tokens. The &#x60;granular_scopes&#x60; attribute is present only for tokens created with granular scopes. In each granular scope, &#x60;project_id&#x60; is set when the scope targets a project, and &#x60;group_id&#x60; when it targets a group. Both are &#x60;null&#x60; for scopes with &#x60;access&#x60; of &#x60;user&#x60;, &#x60;instance&#x60;, &#x60;all_memberships&#x60;, or &#x60;personal_projects&#x60;. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/589500) in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param userId Filter personal access tokens by user ID. Non-administrators can only filter their own tokens.
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
    private ResponseSpec getApiV4PersonalAccessTokensRequestCreation(@jakarta.annotation.Nullable Integer userId, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "user_id", userId));
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
        return apiClient.invokeAPI("/api/v4/personal_access_tokens", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all personal access tokens
     * Lists all personal access tokens accessible by the authenticated user. For administrators, returns all personal access tokens in the instance. For non-administrators, returns all of their personal access tokens. The &#x60;granular_scopes&#x60; attribute is present only for tokens created with granular scopes. In each granular scope, &#x60;project_id&#x60; is set when the scope targets a project, and &#x60;group_id&#x60; when it targets a group. Both are &#x60;null&#x60; for scopes with &#x60;access&#x60; of &#x60;user&#x60;, &#x60;instance&#x60;, &#x60;all_memberships&#x60;, or &#x60;personal_projects&#x60;. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/589500) in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param userId Filter personal access tokens by user ID. Non-administrators can only filter their own tokens.
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
    public List<APIEntitiesPersonalAccessToken> getApiV4PersonalAccessTokens(@jakarta.annotation.Nullable Integer userId, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPersonalAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PersonalAccessTokensRequestCreation(userId, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort, page, perPage).body(localVarReturnType);
    }

    /**
     * List all personal access tokens
     * Lists all personal access tokens accessible by the authenticated user. For administrators, returns all personal access tokens in the instance. For non-administrators, returns all of their personal access tokens. The &#x60;granular_scopes&#x60; attribute is present only for tokens created with granular scopes. In each granular scope, &#x60;project_id&#x60; is set when the scope targets a project, and &#x60;group_id&#x60; when it targets a group. Both are &#x60;null&#x60; for scopes with &#x60;access&#x60; of &#x60;user&#x60;, &#x60;instance&#x60;, &#x60;all_memberships&#x60;, or &#x60;personal_projects&#x60;. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/589500) in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param userId Filter personal access tokens by user ID. Non-administrators can only filter their own tokens.
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
    public ResponseEntity<List<APIEntitiesPersonalAccessToken>> getApiV4PersonalAccessTokensWithHttpInfo(@jakarta.annotation.Nullable Integer userId, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPersonalAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PersonalAccessTokensRequestCreation(userId, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all personal access tokens
     * Lists all personal access tokens accessible by the authenticated user. For administrators, returns all personal access tokens in the instance. For non-administrators, returns all of their personal access tokens. The &#x60;granular_scopes&#x60; attribute is present only for tokens created with granular scopes. In each granular scope, &#x60;project_id&#x60; is set when the scope targets a project, and &#x60;group_id&#x60; when it targets a group. Both are &#x60;null&#x60; for scopes with &#x60;access&#x60; of &#x60;user&#x60;, &#x60;instance&#x60;, &#x60;all_memberships&#x60;, or &#x60;personal_projects&#x60;. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/589500) in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param userId Filter personal access tokens by user ID. Non-administrators can only filter their own tokens.
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
    public ResponseSpec getApiV4PersonalAccessTokensWithResponseSpec(@jakarta.annotation.Nullable Integer userId, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4PersonalAccessTokensRequestCreation(userId, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort, page, perPage);
    }

    /**
     * Retrieve a personal access token
     * Retrieves details for a specified personal access token. Administrators can retrieve details on any token. Non-administrators can only retrieve details on their own tokens. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/589500) in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the personal access token.
     * @return APIEntitiesPersonalAccessToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PersonalAccessTokensIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4PersonalAccessTokensId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesPersonalAccessToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/personal_access_tokens/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a personal access token
     * Retrieves details for a specified personal access token. Administrators can retrieve details on any token. Non-administrators can only retrieve details on their own tokens. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/589500) in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the personal access token.
     * @return APIEntitiesPersonalAccessToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalAccessToken getApiV4PersonalAccessTokensId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PersonalAccessTokensIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve a personal access token
     * Retrieves details for a specified personal access token. Administrators can retrieve details on any token. Non-administrators can only retrieve details on their own tokens. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/589500) in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the personal access token.
     * @return ResponseEntity&lt;APIEntitiesPersonalAccessToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalAccessToken> getApiV4PersonalAccessTokensIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PersonalAccessTokensIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a personal access token
     * Retrieves details for a specified personal access token. Administrators can retrieve details on any token. Non-administrators can only retrieve details on their own tokens. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/589500) in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the personal access token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PersonalAccessTokensIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4PersonalAccessTokensIdRequestCreation(id);
    }

    /**
     * Retrieve a personal access token
     * Retrieves a specified personal access token by passing it to the API in a header. Returns the personal access token used to authenticate the request. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/629849) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @return APIEntitiesPersonalAccessToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PersonalAccessTokensSelfRequestCreation() throws RestClientResponseException {
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesPersonalAccessToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/personal_access_tokens/self", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a personal access token
     * Retrieves a specified personal access token by passing it to the API in a header. Returns the personal access token used to authenticate the request. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/629849) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @return APIEntitiesPersonalAccessToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalAccessToken getApiV4PersonalAccessTokensSelf() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PersonalAccessTokensSelfRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve a personal access token
     * Retrieves a specified personal access token by passing it to the API in a header. Returns the personal access token used to authenticate the request. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/629849) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @return ResponseEntity&lt;APIEntitiesPersonalAccessToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalAccessToken> getApiV4PersonalAccessTokensSelfWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PersonalAccessTokensSelfRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve a personal access token
     * Retrieves a specified personal access token by passing it to the API in a header. Returns the personal access token used to authenticate the request. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/629849) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PersonalAccessTokensSelfWithResponseSpec() throws RestClientResponseException {
        return getApiV4PersonalAccessTokensSelfRequestCreation();
    }

    /**
     * List all token associations
     * Lists all groups and projects accessible by the personal access token used to authenticate the request. Generally, this includes any groups or projects that the user is a member of. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param minAccessLevel Return groups and projects where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return APIEntitiesPersonalAccessTokenAssociations
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PersonalAccessTokensSelfAssociationsRequestCreation(@jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "min_access_level", minAccessLevel));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenAssociations> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/personal_access_tokens/self/associations", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all token associations
     * Lists all groups and projects accessible by the personal access token used to authenticate the request. Generally, this includes any groups or projects that the user is a member of. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param minAccessLevel Return groups and projects where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return APIEntitiesPersonalAccessTokenAssociations
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalAccessTokenAssociations getApiV4PersonalAccessTokensSelfAssociations(@jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenAssociations> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PersonalAccessTokensSelfAssociationsRequestCreation(minAccessLevel, page, perPage).body(localVarReturnType);
    }

    /**
     * List all token associations
     * Lists all groups and projects accessible by the personal access token used to authenticate the request. Generally, this includes any groups or projects that the user is a member of. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param minAccessLevel Return groups and projects where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;APIEntitiesPersonalAccessTokenAssociations&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalAccessTokenAssociations> getApiV4PersonalAccessTokensSelfAssociationsWithHttpInfo(@jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenAssociations> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PersonalAccessTokensSelfAssociationsRequestCreation(minAccessLevel, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all token associations
     * Lists all groups and projects accessible by the personal access token used to authenticate the request. Generally, this includes any groups or projects that the user is a member of. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param minAccessLevel Return groups and projects where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PersonalAccessTokensSelfAssociationsWithResponseSpec(@jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4PersonalAccessTokensSelfAssociationsRequestCreation(minAccessLevel, page, perPage);
    }

    /**
     * List all project access tokens
     * Lists all project access tokens for a specified project. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/600347) in GitLab 19.2 and made generally available in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
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
     * @return List&lt;APIEntitiesResourceAccessToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdAccessTokensRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesResourceAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/access_tokens", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project access tokens
     * Lists all project access tokens for a specified project. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/600347) in GitLab 19.2 and made generally available in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
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
     * @return List&lt;APIEntitiesResourceAccessToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesResourceAccessToken> getApiV4ProjectsIdAccessTokens(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAccessTokensRequestCreation(id, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort).body(localVarReturnType);
    }

    /**
     * List all project access tokens
     * Lists all project access tokens for a specified project. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/600347) in GitLab 19.2 and made generally available in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
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
     * @return ResponseEntity&lt;List&lt;APIEntitiesResourceAccessToken&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesResourceAccessToken>> getApiV4ProjectsIdAccessTokensWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesResourceAccessToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAccessTokensRequestCreation(id, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort).toEntity(localVarReturnType);
    }

    /**
     * List all project access tokens
     * Lists all project access tokens for a specified project. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/600347) in GitLab 19.2 and made generally available in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
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
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdAccessTokensWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getApiV4ProjectsIdAccessTokensRequestCreation(id, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort);
    }

    /**
     * Retrieve details on a project access token
     * Retrieves details on a specified project access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the token.
     * @return APIEntitiesResourceAccessToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdAccessTokensTokenIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdAccessTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling getApiV4ProjectsIdAccessTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("token_id", tokenId);

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

        ParameterizedTypeReference<APIEntitiesResourceAccessToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/access_tokens/{token_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve details on a project access token
     * Retrieves details on a specified project access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the token.
     * @return APIEntitiesResourceAccessToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceAccessToken getApiV4ProjectsIdAccessTokensTokenId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAccessTokensTokenIdRequestCreation(id, tokenId).body(localVarReturnType);
    }

    /**
     * Retrieve details on a project access token
     * Retrieves details on a specified project access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the token.
     * @return ResponseEntity&lt;APIEntitiesResourceAccessToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceAccessToken> getApiV4ProjectsIdAccessTokensTokenIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAccessTokensTokenIdRequestCreation(id, tokenId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve details on a project access token
     * Retrieves details on a specified project access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdAccessTokensTokenIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tokenId) throws RestClientResponseException {
        return getApiV4ProjectsIdAccessTokensTokenIdRequestCreation(id, tokenId);
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
     * List all impersonation tokens for a user
     * Lists all impersonation tokens for a specified user. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/242819) in GitLab 19.2 and made generally available in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632315) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
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
     * @return List&lt;APIEntitiesImpersonationToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersUserIdImpersonationTokensRequestCreation(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4UsersUserIdImpersonationTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("user_id", userId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
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

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesImpersonationToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{user_id}/impersonation_tokens", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all impersonation tokens for a user
     * Lists all impersonation tokens for a specified user. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/242819) in GitLab 19.2 and made generally available in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632315) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
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
     * @return List&lt;APIEntitiesImpersonationToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesImpersonationToken> getApiV4UsersUserIdImpersonationTokens(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesImpersonationToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersUserIdImpersonationTokensRequestCreation(userId, page, perPage, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort).body(localVarReturnType);
    }

    /**
     * List all impersonation tokens for a user
     * Lists all impersonation tokens for a specified user. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/242819) in GitLab 19.2 and made generally available in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632315) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
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
     * @return ResponseEntity&lt;List&lt;APIEntitiesImpersonationToken&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesImpersonationToken>> getApiV4UsersUserIdImpersonationTokensWithHttpInfo(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesImpersonationToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersUserIdImpersonationTokensRequestCreation(userId, page, perPage, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort).toEntity(localVarReturnType);
    }

    /**
     * List all impersonation tokens for a user
     * Lists all impersonation tokens for a specified user. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/242819) in GitLab 19.2 and made generally available in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632315) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
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
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersUserIdImpersonationTokensWithResponseSpec(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean revoked, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime lastUsedBefore, @jakarta.annotation.Nullable OffsetDateTime lastUsedAfter, @jakarta.annotation.Nullable LocalDate expiresBefore, @jakarta.annotation.Nullable LocalDate expiresAfter, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getApiV4UsersUserIdImpersonationTokensRequestCreation(userId, page, perPage, revoked, state, createdBefore, createdAfter, lastUsedBefore, lastUsedAfter, expiresBefore, expiresAfter, search, sort);
    }

    /**
     * Retrieve an impersonation token for a user
     * Retrieves an impersonation token for a specified user. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/242819) in GitLab 19.2 and made generally available in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632315) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param impersonationTokenId ID of the impersonation token.
     * @return APIEntitiesImpersonationToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersUserIdImpersonationTokensImpersonationTokenIdRequestCreation(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull Integer impersonationTokenId) throws RestClientResponseException {
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4UsersUserIdImpersonationTokensImpersonationTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'impersonationTokenId' is set
        if (impersonationTokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'impersonationTokenId' when calling getApiV4UsersUserIdImpersonationTokensImpersonationTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("user_id", userId);
        pathParams.put("impersonation_token_id", impersonationTokenId);

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

        ParameterizedTypeReference<APIEntitiesImpersonationToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{user_id}/impersonation_tokens/{impersonation_token_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an impersonation token for a user
     * Retrieves an impersonation token for a specified user. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/242819) in GitLab 19.2 and made generally available in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632315) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param impersonationTokenId ID of the impersonation token.
     * @return APIEntitiesImpersonationToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesImpersonationToken getApiV4UsersUserIdImpersonationTokensImpersonationTokenId(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull Integer impersonationTokenId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesImpersonationToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersUserIdImpersonationTokensImpersonationTokenIdRequestCreation(userId, impersonationTokenId).body(localVarReturnType);
    }

    /**
     * Retrieve an impersonation token for a user
     * Retrieves an impersonation token for a specified user. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/242819) in GitLab 19.2 and made generally available in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632315) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param impersonationTokenId ID of the impersonation token.
     * @return ResponseEntity&lt;APIEntitiesImpersonationToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesImpersonationToken> getApiV4UsersUserIdImpersonationTokensImpersonationTokenIdWithHttpInfo(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull Integer impersonationTokenId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesImpersonationToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersUserIdImpersonationTokensImpersonationTokenIdRequestCreation(userId, impersonationTokenId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an impersonation token for a user
     * Retrieves an impersonation token for a specified user. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/242819) in GitLab 19.2 and made generally available in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632315) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param impersonationTokenId ID of the impersonation token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersUserIdImpersonationTokensImpersonationTokenIdWithResponseSpec(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull Integer impersonationTokenId) throws RestClientResponseException {
        return getApiV4UsersUserIdImpersonationTokensImpersonationTokenIdRequestCreation(userId, impersonationTokenId);
    }

    /**
     * Get information about a token.
     * This feature was introduced in GitLab 17.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable
     * <p><b>400</b> - Bad Request
     * @param postApiV4AdminTokenRequest The postApiV4AdminTokenRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AdminTokenRequestCreation(@jakarta.annotation.Nonnull PostApiV4AdminTokenRequest postApiV4AdminTokenRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4AdminTokenRequest' is set
        if (postApiV4AdminTokenRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4AdminTokenRequest' when calling postApiV4AdminToken", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/admin/token", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AdminTokenRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get information about a token.
     * This feature was introduced in GitLab 17.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable
     * <p><b>400</b> - Bad Request
     * @param postApiV4AdminTokenRequest The postApiV4AdminTokenRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AdminToken(@jakarta.annotation.Nonnull PostApiV4AdminTokenRequest postApiV4AdminTokenRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AdminTokenRequestCreation(postApiV4AdminTokenRequest).body(localVarReturnType);
    }

    /**
     * Get information about a token.
     * This feature was introduced in GitLab 17.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable
     * <p><b>400</b> - Bad Request
     * @param postApiV4AdminTokenRequest The postApiV4AdminTokenRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AdminTokenWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4AdminTokenRequest postApiV4AdminTokenRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AdminTokenRequestCreation(postApiV4AdminTokenRequest).toEntity(localVarReturnType);
    }

    /**
     * Get information about a token.
     * This feature was introduced in GitLab 17.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable
     * <p><b>400</b> - Bad Request
     * @param postApiV4AdminTokenRequest The postApiV4AdminTokenRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AdminTokenWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4AdminTokenRequest postApiV4AdminTokenRequest) throws RestClientResponseException {
        return postApiV4AdminTokenRequestCreation(postApiV4AdminTokenRequest);
    }

    /**
     * Create a group access token
     * Creates a group access token for a specified group. You cannot create a token with an access level greater than your account. For example, a user with the Maintainer role cannot create a group access token with the Owner role. You must use a personal access token with this endpoint. You cannot authenticate with a group access token. You must have the Owner role for the group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdAccessTokensRequest The postApiV4GroupsIdAccessTokensRequest parameter
     * @return APIEntitiesResourceAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdAccessTokensRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdAccessTokensRequest postApiV4GroupsIdAccessTokensRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdAccessTokensRequest' is set
        if (postApiV4GroupsIdAccessTokensRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdAccessTokensRequest' when calling postApiV4GroupsIdAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/access_tokens", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdAccessTokensRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group access token
     * Creates a group access token for a specified group. You cannot create a token with an access level greater than your account. For example, a user with the Maintainer role cannot create a group access token with the Owner role. You must use a personal access token with this endpoint. You cannot authenticate with a group access token. You must have the Owner role for the group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdAccessTokensRequest The postApiV4GroupsIdAccessTokensRequest parameter
     * @return APIEntitiesResourceAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceAccessTokenWithToken postApiV4GroupsIdAccessTokens(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdAccessTokensRequest postApiV4GroupsIdAccessTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdAccessTokensRequestCreation(id, postApiV4GroupsIdAccessTokensRequest).body(localVarReturnType);
    }

    /**
     * Create a group access token
     * Creates a group access token for a specified group. You cannot create a token with an access level greater than your account. For example, a user with the Maintainer role cannot create a group access token with the Owner role. You must use a personal access token with this endpoint. You cannot authenticate with a group access token. You must have the Owner role for the group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdAccessTokensRequest The postApiV4GroupsIdAccessTokensRequest parameter
     * @return ResponseEntity&lt;APIEntitiesResourceAccessTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceAccessTokenWithToken> postApiV4GroupsIdAccessTokensWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdAccessTokensRequest postApiV4GroupsIdAccessTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdAccessTokensRequestCreation(id, postApiV4GroupsIdAccessTokensRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a group access token
     * Creates a group access token for a specified group. You cannot create a token with an access level greater than your account. For example, a user with the Maintainer role cannot create a group access token with the Owner role. You must use a personal access token with this endpoint. You cannot authenticate with a group access token. You must have the Owner role for the group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdAccessTokensRequest The postApiV4GroupsIdAccessTokensRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdAccessTokensWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdAccessTokensRequest postApiV4GroupsIdAccessTokensRequest) throws RestClientResponseException {
        return postApiV4GroupsIdAccessTokensRequestCreation(id, postApiV4GroupsIdAccessTokensRequest);
    }

    /**
     * Rotate a group access token
     * Rotates a group access token by passing it to the API in a header.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Method not allowed
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdAccessTokensSelfRotateRequest The postApiV4GroupsIdAccessTokensSelfRotateRequest parameter
     * @return APIEntitiesResourceAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdAccessTokensSelfRotateRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdAccessTokensSelfRotateRequest postApiV4GroupsIdAccessTokensSelfRotateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdAccessTokensSelfRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/access_tokens/self/rotate", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdAccessTokensSelfRotateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rotate a group access token
     * Rotates a group access token by passing it to the API in a header.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Method not allowed
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdAccessTokensSelfRotateRequest The postApiV4GroupsIdAccessTokensSelfRotateRequest parameter
     * @return APIEntitiesResourceAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceAccessTokenWithToken postApiV4GroupsIdAccessTokensSelfRotate(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdAccessTokensSelfRotateRequest postApiV4GroupsIdAccessTokensSelfRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdAccessTokensSelfRotateRequestCreation(id, postApiV4GroupsIdAccessTokensSelfRotateRequest).body(localVarReturnType);
    }

    /**
     * Rotate a group access token
     * Rotates a group access token by passing it to the API in a header.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Method not allowed
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdAccessTokensSelfRotateRequest The postApiV4GroupsIdAccessTokensSelfRotateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesResourceAccessTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceAccessTokenWithToken> postApiV4GroupsIdAccessTokensSelfRotateWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdAccessTokensSelfRotateRequest postApiV4GroupsIdAccessTokensSelfRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdAccessTokensSelfRotateRequestCreation(id, postApiV4GroupsIdAccessTokensSelfRotateRequest).toEntity(localVarReturnType);
    }

    /**
     * Rotate a group access token
     * Rotates a group access token by passing it to the API in a header.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Method not allowed
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdAccessTokensSelfRotateRequest The postApiV4GroupsIdAccessTokensSelfRotateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdAccessTokensSelfRotateWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdAccessTokensSelfRotateRequest postApiV4GroupsIdAccessTokensSelfRotateRequest) throws RestClientResponseException {
        return postApiV4GroupsIdAccessTokensSelfRotateRequestCreation(id, postApiV4GroupsIdAccessTokensSelfRotateRequest);
    }

    /**
     * Rotate a group access token
     * Rotates a group access token. This immediately revokes the previous token and creates a token. Generally, this endpoint rotates a specific group access token by authenticating with a personal access token. You can also use a group access token to rotate itself. If you attempt to use this endpoint to rotate a token that was previously revoked, any active tokens from the same token family are revoked. To rotate another group access token, you must use a personal access token with the &#x60;api&#x60; scope. To self-rotate a group access token, the token must have the &#x60;api&#x60; or &#x60;self_rotate&#x60; scope. This feature was introduced in GitLab 16.0.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the token, or the keyword &#x60;self&#x60; to rotate the token used to authenticate the request.
     * @param postApiV4GroupsIdAccessTokensTokenIdRotateRequest The postApiV4GroupsIdAccessTokensTokenIdRotateRequest parameter
     * @return APIEntitiesResourceAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdAccessTokensTokenIdRotateRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId, @jakarta.annotation.Nullable PostApiV4GroupsIdAccessTokensTokenIdRotateRequest postApiV4GroupsIdAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdAccessTokensTokenIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling postApiV4GroupsIdAccessTokensTokenIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("token_id", tokenId);

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

        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/access_tokens/{token_id}/rotate", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdAccessTokensTokenIdRotateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rotate a group access token
     * Rotates a group access token. This immediately revokes the previous token and creates a token. Generally, this endpoint rotates a specific group access token by authenticating with a personal access token. You can also use a group access token to rotate itself. If you attempt to use this endpoint to rotate a token that was previously revoked, any active tokens from the same token family are revoked. To rotate another group access token, you must use a personal access token with the &#x60;api&#x60; scope. To self-rotate a group access token, the token must have the &#x60;api&#x60; or &#x60;self_rotate&#x60; scope. This feature was introduced in GitLab 16.0.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the token, or the keyword &#x60;self&#x60; to rotate the token used to authenticate the request.
     * @param postApiV4GroupsIdAccessTokensTokenIdRotateRequest The postApiV4GroupsIdAccessTokensTokenIdRotateRequest parameter
     * @return APIEntitiesResourceAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceAccessTokenWithToken postApiV4GroupsIdAccessTokensTokenIdRotate(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId, @jakarta.annotation.Nullable PostApiV4GroupsIdAccessTokensTokenIdRotateRequest postApiV4GroupsIdAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdAccessTokensTokenIdRotateRequestCreation(id, tokenId, postApiV4GroupsIdAccessTokensTokenIdRotateRequest).body(localVarReturnType);
    }

    /**
     * Rotate a group access token
     * Rotates a group access token. This immediately revokes the previous token and creates a token. Generally, this endpoint rotates a specific group access token by authenticating with a personal access token. You can also use a group access token to rotate itself. If you attempt to use this endpoint to rotate a token that was previously revoked, any active tokens from the same token family are revoked. To rotate another group access token, you must use a personal access token with the &#x60;api&#x60; scope. To self-rotate a group access token, the token must have the &#x60;api&#x60; or &#x60;self_rotate&#x60; scope. This feature was introduced in GitLab 16.0.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the token, or the keyword &#x60;self&#x60; to rotate the token used to authenticate the request.
     * @param postApiV4GroupsIdAccessTokensTokenIdRotateRequest The postApiV4GroupsIdAccessTokensTokenIdRotateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesResourceAccessTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceAccessTokenWithToken> postApiV4GroupsIdAccessTokensTokenIdRotateWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId, @jakarta.annotation.Nullable PostApiV4GroupsIdAccessTokensTokenIdRotateRequest postApiV4GroupsIdAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdAccessTokensTokenIdRotateRequestCreation(id, tokenId, postApiV4GroupsIdAccessTokensTokenIdRotateRequest).toEntity(localVarReturnType);
    }

    /**
     * Rotate a group access token
     * Rotates a group access token. This immediately revokes the previous token and creates a token. Generally, this endpoint rotates a specific group access token by authenticating with a personal access token. You can also use a group access token to rotate itself. If you attempt to use this endpoint to rotate a token that was previously revoked, any active tokens from the same token family are revoked. To rotate another group access token, you must use a personal access token with the &#x60;api&#x60; scope. To self-rotate a group access token, the token must have the &#x60;api&#x60; or &#x60;self_rotate&#x60; scope. This feature was introduced in GitLab 16.0.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the token, or the keyword &#x60;self&#x60; to rotate the token used to authenticate the request.
     * @param postApiV4GroupsIdAccessTokensTokenIdRotateRequest The postApiV4GroupsIdAccessTokensTokenIdRotateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdAccessTokensTokenIdRotateWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId, @jakarta.annotation.Nullable PostApiV4GroupsIdAccessTokensTokenIdRotateRequest postApiV4GroupsIdAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        return postApiV4GroupsIdAccessTokensTokenIdRotateRequestCreation(id, tokenId, postApiV4GroupsIdAccessTokensTokenIdRotateRequest);
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
     * Rotate a personal access token
     * Rotates a specified personal access token. This revokes the previous token and creates a token that expires after one week. Administrators can revoke tokens for any user. Non-administrators can only revoke their own tokens. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/589500) in GitLab 19.2. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the personal access token.
     * @param postApiV4PersonalAccessTokensIdRotateRequest The postApiV4PersonalAccessTokensIdRotateRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4PersonalAccessTokensIdRotateRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4PersonalAccessTokensIdRotateRequest postApiV4PersonalAccessTokensIdRotateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4PersonalAccessTokensIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/personal_access_tokens/{id}/rotate", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4PersonalAccessTokensIdRotateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rotate a personal access token
     * Rotates a specified personal access token. This revokes the previous token and creates a token that expires after one week. Administrators can revoke tokens for any user. Non-administrators can only revoke their own tokens. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/589500) in GitLab 19.2. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the personal access token.
     * @param postApiV4PersonalAccessTokensIdRotateRequest The postApiV4PersonalAccessTokensIdRotateRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalAccessTokenWithToken postApiV4PersonalAccessTokensIdRotate(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4PersonalAccessTokensIdRotateRequest postApiV4PersonalAccessTokensIdRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4PersonalAccessTokensIdRotateRequestCreation(id, postApiV4PersonalAccessTokensIdRotateRequest).body(localVarReturnType);
    }

    /**
     * Rotate a personal access token
     * Rotates a specified personal access token. This revokes the previous token and creates a token that expires after one week. Administrators can revoke tokens for any user. Non-administrators can only revoke their own tokens. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/589500) in GitLab 19.2. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the personal access token.
     * @param postApiV4PersonalAccessTokensIdRotateRequest The postApiV4PersonalAccessTokensIdRotateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPersonalAccessTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalAccessTokenWithToken> postApiV4PersonalAccessTokensIdRotateWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4PersonalAccessTokensIdRotateRequest postApiV4PersonalAccessTokensIdRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4PersonalAccessTokensIdRotateRequestCreation(id, postApiV4PersonalAccessTokensIdRotateRequest).toEntity(localVarReturnType);
    }

    /**
     * Rotate a personal access token
     * Rotates a specified personal access token. This revokes the previous token and creates a token that expires after one week. Administrators can revoke tokens for any user. Non-administrators can only revoke their own tokens. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/589500) in GitLab 19.2. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the personal access token.
     * @param postApiV4PersonalAccessTokensIdRotateRequest The postApiV4PersonalAccessTokensIdRotateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4PersonalAccessTokensIdRotateWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4PersonalAccessTokensIdRotateRequest postApiV4PersonalAccessTokensIdRotateRequest) throws RestClientResponseException {
        return postApiV4PersonalAccessTokensIdRotateRequestCreation(id, postApiV4PersonalAccessTokensIdRotateRequest);
    }

    /**
     * Rotate a personal access token
     * Rotates a personal access token by passing it to the API in a header. Rotates the personal access token used to authenticate the request. The token must have the &#x60;api&#x60; or &#x60;self_rotate&#x60; scope. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632314) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Method not allowed
     * @param postApiV4PersonalAccessTokensSelfRotateRequest The postApiV4PersonalAccessTokensSelfRotateRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4PersonalAccessTokensSelfRotateRequestCreation(@jakarta.annotation.Nullable PostApiV4PersonalAccessTokensSelfRotateRequest postApiV4PersonalAccessTokensSelfRotateRequest) throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/personal_access_tokens/self/rotate", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4PersonalAccessTokensSelfRotateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rotate a personal access token
     * Rotates a personal access token by passing it to the API in a header. Rotates the personal access token used to authenticate the request. The token must have the &#x60;api&#x60; or &#x60;self_rotate&#x60; scope. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632314) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Method not allowed
     * @param postApiV4PersonalAccessTokensSelfRotateRequest The postApiV4PersonalAccessTokensSelfRotateRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalAccessTokenWithToken postApiV4PersonalAccessTokensSelfRotate(@jakarta.annotation.Nullable PostApiV4PersonalAccessTokensSelfRotateRequest postApiV4PersonalAccessTokensSelfRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4PersonalAccessTokensSelfRotateRequestCreation(postApiV4PersonalAccessTokensSelfRotateRequest).body(localVarReturnType);
    }

    /**
     * Rotate a personal access token
     * Rotates a personal access token by passing it to the API in a header. Rotates the personal access token used to authenticate the request. The token must have the &#x60;api&#x60; or &#x60;self_rotate&#x60; scope. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632314) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Method not allowed
     * @param postApiV4PersonalAccessTokensSelfRotateRequest The postApiV4PersonalAccessTokensSelfRotateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPersonalAccessTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalAccessTokenWithToken> postApiV4PersonalAccessTokensSelfRotateWithHttpInfo(@jakarta.annotation.Nullable PostApiV4PersonalAccessTokensSelfRotateRequest postApiV4PersonalAccessTokensSelfRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4PersonalAccessTokensSelfRotateRequestCreation(postApiV4PersonalAccessTokensSelfRotateRequest).toEntity(localVarReturnType);
    }

    /**
     * Rotate a personal access token
     * Rotates a personal access token by passing it to the API in a header. Rotates the personal access token used to authenticate the request. The token must have the &#x60;api&#x60; or &#x60;self_rotate&#x60; scope. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632314) in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Method not allowed
     * @param postApiV4PersonalAccessTokensSelfRotateRequest The postApiV4PersonalAccessTokensSelfRotateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4PersonalAccessTokensSelfRotateWithResponseSpec(@jakarta.annotation.Nullable PostApiV4PersonalAccessTokensSelfRotateRequest postApiV4PersonalAccessTokensSelfRotateRequest) throws RestClientResponseException {
        return postApiV4PersonalAccessTokensSelfRotateRequestCreation(postApiV4PersonalAccessTokensSelfRotateRequest);
    }

    /**
     * Create a project access token
     * Creates a project access token for a specified project. You cannot create a token with an access level greater than your account. For example, a user with the Maintainer role cannot create a project access token with the Owner role. You must use a personal access token with this endpoint. You cannot authenticate with a project access token.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAccessTokensRequest The postApiV4ProjectsIdAccessTokensRequest parameter
     * @return APIEntitiesResourceAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdAccessTokensRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAccessTokensRequest postApiV4ProjectsIdAccessTokensRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdAccessTokensRequest' is set
        if (postApiV4ProjectsIdAccessTokensRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdAccessTokensRequest' when calling postApiV4ProjectsIdAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/access_tokens", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdAccessTokensRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a project access token
     * Creates a project access token for a specified project. You cannot create a token with an access level greater than your account. For example, a user with the Maintainer role cannot create a project access token with the Owner role. You must use a personal access token with this endpoint. You cannot authenticate with a project access token.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAccessTokensRequest The postApiV4ProjectsIdAccessTokensRequest parameter
     * @return APIEntitiesResourceAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceAccessTokenWithToken postApiV4ProjectsIdAccessTokens(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAccessTokensRequest postApiV4ProjectsIdAccessTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAccessTokensRequestCreation(id, postApiV4ProjectsIdAccessTokensRequest).body(localVarReturnType);
    }

    /**
     * Create a project access token
     * Creates a project access token for a specified project. You cannot create a token with an access level greater than your account. For example, a user with the Maintainer role cannot create a project access token with the Owner role. You must use a personal access token with this endpoint. You cannot authenticate with a project access token.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAccessTokensRequest The postApiV4ProjectsIdAccessTokensRequest parameter
     * @return ResponseEntity&lt;APIEntitiesResourceAccessTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceAccessTokenWithToken> postApiV4ProjectsIdAccessTokensWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAccessTokensRequest postApiV4ProjectsIdAccessTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAccessTokensRequestCreation(id, postApiV4ProjectsIdAccessTokensRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a project access token
     * Creates a project access token for a specified project. You cannot create a token with an access level greater than your account. For example, a user with the Maintainer role cannot create a project access token with the Owner role. You must use a personal access token with this endpoint. You cannot authenticate with a project access token.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAccessTokensRequest The postApiV4ProjectsIdAccessTokensRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdAccessTokensWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAccessTokensRequest postApiV4ProjectsIdAccessTokensRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdAccessTokensRequestCreation(id, postApiV4ProjectsIdAccessTokensRequest);
    }

    /**
     * Rotate a project access token
     * Rotates a project access token by passing it to the API in a header.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Method not allowed
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAccessTokensSelfRotateRequest The postApiV4ProjectsIdAccessTokensSelfRotateRequest parameter
     * @return APIEntitiesResourceAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdAccessTokensSelfRotateRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4ProjectsIdAccessTokensSelfRotateRequest postApiV4ProjectsIdAccessTokensSelfRotateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdAccessTokensSelfRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/access_tokens/self/rotate", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdAccessTokensSelfRotateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rotate a project access token
     * Rotates a project access token by passing it to the API in a header.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Method not allowed
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAccessTokensSelfRotateRequest The postApiV4ProjectsIdAccessTokensSelfRotateRequest parameter
     * @return APIEntitiesResourceAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceAccessTokenWithToken postApiV4ProjectsIdAccessTokensSelfRotate(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4ProjectsIdAccessTokensSelfRotateRequest postApiV4ProjectsIdAccessTokensSelfRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAccessTokensSelfRotateRequestCreation(id, postApiV4ProjectsIdAccessTokensSelfRotateRequest).body(localVarReturnType);
    }

    /**
     * Rotate a project access token
     * Rotates a project access token by passing it to the API in a header.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Method not allowed
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAccessTokensSelfRotateRequest The postApiV4ProjectsIdAccessTokensSelfRotateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesResourceAccessTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceAccessTokenWithToken> postApiV4ProjectsIdAccessTokensSelfRotateWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4ProjectsIdAccessTokensSelfRotateRequest postApiV4ProjectsIdAccessTokensSelfRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAccessTokensSelfRotateRequestCreation(id, postApiV4ProjectsIdAccessTokensSelfRotateRequest).toEntity(localVarReturnType);
    }

    /**
     * Rotate a project access token
     * Rotates a project access token by passing it to the API in a header.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Method not allowed
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAccessTokensSelfRotateRequest The postApiV4ProjectsIdAccessTokensSelfRotateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdAccessTokensSelfRotateWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4ProjectsIdAccessTokensSelfRotateRequest postApiV4ProjectsIdAccessTokensSelfRotateRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdAccessTokensSelfRotateRequestCreation(id, postApiV4ProjectsIdAccessTokensSelfRotateRequest);
    }

    /**
     * Rotate a project access token
     * Rotates a project access token. This immediately revokes the previous token and creates a token. Generally, this endpoint rotates a specific project access token by authenticating with a personal access token. You can also use a project access token to rotate itself. If you attempt to use this endpoint to rotate a token that was previously revoked, any active tokens from the same token family are revoked. To rotate another project access token, you must use a personal access token with the &#x60;api&#x60; scope. To self-rotate a project access token, the token must have the &#x60;api&#x60; or &#x60;self_rotate&#x60; scope. This feature was introduced in GitLab 16.0.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the token, or the keyword &#x60;self&#x60; to rotate the token used to authenticate the request.
     * @param postApiV4ProjectsIdAccessTokensTokenIdRotateRequest The postApiV4ProjectsIdAccessTokensTokenIdRotateRequest parameter
     * @return APIEntitiesResourceAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdAccessTokensTokenIdRotateRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId, @jakarta.annotation.Nullable PostApiV4ProjectsIdAccessTokensTokenIdRotateRequest postApiV4ProjectsIdAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdAccessTokensTokenIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling postApiV4ProjectsIdAccessTokensTokenIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("token_id", tokenId);

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

        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/access_tokens/{token_id}/rotate", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdAccessTokensTokenIdRotateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rotate a project access token
     * Rotates a project access token. This immediately revokes the previous token and creates a token. Generally, this endpoint rotates a specific project access token by authenticating with a personal access token. You can also use a project access token to rotate itself. If you attempt to use this endpoint to rotate a token that was previously revoked, any active tokens from the same token family are revoked. To rotate another project access token, you must use a personal access token with the &#x60;api&#x60; scope. To self-rotate a project access token, the token must have the &#x60;api&#x60; or &#x60;self_rotate&#x60; scope. This feature was introduced in GitLab 16.0.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the token, or the keyword &#x60;self&#x60; to rotate the token used to authenticate the request.
     * @param postApiV4ProjectsIdAccessTokensTokenIdRotateRequest The postApiV4ProjectsIdAccessTokensTokenIdRotateRequest parameter
     * @return APIEntitiesResourceAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesResourceAccessTokenWithToken postApiV4ProjectsIdAccessTokensTokenIdRotate(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId, @jakarta.annotation.Nullable PostApiV4ProjectsIdAccessTokensTokenIdRotateRequest postApiV4ProjectsIdAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAccessTokensTokenIdRotateRequestCreation(id, tokenId, postApiV4ProjectsIdAccessTokensTokenIdRotateRequest).body(localVarReturnType);
    }

    /**
     * Rotate a project access token
     * Rotates a project access token. This immediately revokes the previous token and creates a token. Generally, this endpoint rotates a specific project access token by authenticating with a personal access token. You can also use a project access token to rotate itself. If you attempt to use this endpoint to rotate a token that was previously revoked, any active tokens from the same token family are revoked. To rotate another project access token, you must use a personal access token with the &#x60;api&#x60; scope. To self-rotate a project access token, the token must have the &#x60;api&#x60; or &#x60;self_rotate&#x60; scope. This feature was introduced in GitLab 16.0.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the token, or the keyword &#x60;self&#x60; to rotate the token used to authenticate the request.
     * @param postApiV4ProjectsIdAccessTokensTokenIdRotateRequest The postApiV4ProjectsIdAccessTokensTokenIdRotateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesResourceAccessTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesResourceAccessTokenWithToken> postApiV4ProjectsIdAccessTokensTokenIdRotateWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId, @jakarta.annotation.Nullable PostApiV4ProjectsIdAccessTokensTokenIdRotateRequest postApiV4ProjectsIdAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesResourceAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAccessTokensTokenIdRotateRequestCreation(id, tokenId, postApiV4ProjectsIdAccessTokensTokenIdRotateRequest).toEntity(localVarReturnType);
    }

    /**
     * Rotate a project access token
     * Rotates a project access token. This immediately revokes the previous token and creates a token. Generally, this endpoint rotates a specific project access token by authenticating with a personal access token. You can also use a project access token to rotate itself. If you attempt to use this endpoint to rotate a token that was previously revoked, any active tokens from the same token family are revoked. To rotate another project access token, you must use a personal access token with the &#x60;api&#x60; scope. To self-rotate a project access token, the token must have the &#x60;api&#x60; or &#x60;self_rotate&#x60; scope. This feature was introduced in GitLab 16.0.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the token, or the keyword &#x60;self&#x60; to rotate the token used to authenticate the request.
     * @param postApiV4ProjectsIdAccessTokensTokenIdRotateRequest The postApiV4ProjectsIdAccessTokensTokenIdRotateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdAccessTokensTokenIdRotateWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String tokenId, @jakarta.annotation.Nullable PostApiV4ProjectsIdAccessTokensTokenIdRotateRequest postApiV4ProjectsIdAccessTokensTokenIdRotateRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdAccessTokensTokenIdRotateRequestCreation(id, tokenId, postApiV4ProjectsIdAccessTokensTokenIdRotateRequest);
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
     * Create a personal access token
     * Creates a personal access token for the currently authenticated user. To create a fine-grained personal access token, use &#x60;granular_scopes&#x60; instead of &#x60;scopes&#x60;. For security purposes, a token created with &#x60;scopes&#x60; is limited to the &#x60;k8s_proxy&#x60; and &#x60;self_rotate&#x60; scope. With &#x60;selected_memberships&#x60;, you must have access to each group and project listed. Token values are included with the response, but cannot be retrieved later. The &#x60;granular_scopes&#x60; parameter was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/243428) in GitLab 19.2. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute in the response was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632314) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserPersonalAccessTokensRequest The postApiV4UserPersonalAccessTokensRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UserPersonalAccessTokensRequestCreation(@jakarta.annotation.Nonnull PostApiV4UserPersonalAccessTokensRequest postApiV4UserPersonalAccessTokensRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4UserPersonalAccessTokensRequest' is set
        if (postApiV4UserPersonalAccessTokensRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UserPersonalAccessTokensRequest' when calling postApiV4UserPersonalAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
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

        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/personal_access_tokens", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UserPersonalAccessTokensRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a personal access token
     * Creates a personal access token for the currently authenticated user. To create a fine-grained personal access token, use &#x60;granular_scopes&#x60; instead of &#x60;scopes&#x60;. For security purposes, a token created with &#x60;scopes&#x60; is limited to the &#x60;k8s_proxy&#x60; and &#x60;self_rotate&#x60; scope. With &#x60;selected_memberships&#x60;, you must have access to each group and project listed. Token values are included with the response, but cannot be retrieved later. The &#x60;granular_scopes&#x60; parameter was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/243428) in GitLab 19.2. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute in the response was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632314) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserPersonalAccessTokensRequest The postApiV4UserPersonalAccessTokensRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalAccessTokenWithToken postApiV4UserPersonalAccessTokens(@jakarta.annotation.Nonnull PostApiV4UserPersonalAccessTokensRequest postApiV4UserPersonalAccessTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserPersonalAccessTokensRequestCreation(postApiV4UserPersonalAccessTokensRequest).body(localVarReturnType);
    }

    /**
     * Create a personal access token
     * Creates a personal access token for the currently authenticated user. To create a fine-grained personal access token, use &#x60;granular_scopes&#x60; instead of &#x60;scopes&#x60;. For security purposes, a token created with &#x60;scopes&#x60; is limited to the &#x60;k8s_proxy&#x60; and &#x60;self_rotate&#x60; scope. With &#x60;selected_memberships&#x60;, you must have access to each group and project listed. Token values are included with the response, but cannot be retrieved later. The &#x60;granular_scopes&#x60; parameter was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/243428) in GitLab 19.2. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute in the response was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632314) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserPersonalAccessTokensRequest The postApiV4UserPersonalAccessTokensRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPersonalAccessTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalAccessTokenWithToken> postApiV4UserPersonalAccessTokensWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4UserPersonalAccessTokensRequest postApiV4UserPersonalAccessTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserPersonalAccessTokensRequestCreation(postApiV4UserPersonalAccessTokensRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a personal access token
     * Creates a personal access token for the currently authenticated user. To create a fine-grained personal access token, use &#x60;granular_scopes&#x60; instead of &#x60;scopes&#x60;. For security purposes, a token created with &#x60;scopes&#x60; is limited to the &#x60;k8s_proxy&#x60; and &#x60;self_rotate&#x60; scope. With &#x60;selected_memberships&#x60;, you must have access to each group and project listed. Token values are included with the response, but cannot be retrieved later. The &#x60;granular_scopes&#x60; parameter was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/243428) in GitLab 19.2. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute in the response was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632314) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserPersonalAccessTokensRequest The postApiV4UserPersonalAccessTokensRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UserPersonalAccessTokensWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4UserPersonalAccessTokensRequest postApiV4UserPersonalAccessTokensRequest) throws RestClientResponseException {
        return postApiV4UserPersonalAccessTokensRequestCreation(postApiV4UserPersonalAccessTokensRequest);
    }

    /**
     * Create an impersonation token
     * Creates an impersonation token for a specified user. These tokens are used to act on behalf of a user and can perform API calls as well as Git read and write actions. These tokens are not visible to the associated user on their profile settings page. To create a fine-grained impersonation token, use &#x60;granular_scopes&#x60; instead of &#x60;scopes&#x60;. The scopes in &#x60;granular_scopes&#x60; apply to the user the token is for. With &#x60;selected_memberships&#x60;, that user must have access to each group and project listed. Either &#x60;scopes&#x60; or &#x60;granular_scopes&#x60; is required. Token values are included with the response, but cannot be retrieved later. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/242819) in GitLab 19.2 and made generally available in GitLab 19.4. The &#x60;granular_scopes&#x60; parameter was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/243990) in GitLab 19.2, and the &#x60;granular_scopes&#x60; attribute in the response was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632315) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param postApiV4UsersUserIdImpersonationTokensRequest The postApiV4UsersUserIdImpersonationTokensRequest parameter
     * @return APIEntitiesImpersonationTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersUserIdImpersonationTokensRequestCreation(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4UsersUserIdImpersonationTokensRequest postApiV4UsersUserIdImpersonationTokensRequest) throws RestClientResponseException {
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling postApiV4UsersUserIdImpersonationTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4UsersUserIdImpersonationTokensRequest' is set
        if (postApiV4UsersUserIdImpersonationTokensRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UsersUserIdImpersonationTokensRequest' when calling postApiV4UsersUserIdImpersonationTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesImpersonationTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{user_id}/impersonation_tokens", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UsersUserIdImpersonationTokensRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an impersonation token
     * Creates an impersonation token for a specified user. These tokens are used to act on behalf of a user and can perform API calls as well as Git read and write actions. These tokens are not visible to the associated user on their profile settings page. To create a fine-grained impersonation token, use &#x60;granular_scopes&#x60; instead of &#x60;scopes&#x60;. The scopes in &#x60;granular_scopes&#x60; apply to the user the token is for. With &#x60;selected_memberships&#x60;, that user must have access to each group and project listed. Either &#x60;scopes&#x60; or &#x60;granular_scopes&#x60; is required. Token values are included with the response, but cannot be retrieved later. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/242819) in GitLab 19.2 and made generally available in GitLab 19.4. The &#x60;granular_scopes&#x60; parameter was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/243990) in GitLab 19.2, and the &#x60;granular_scopes&#x60; attribute in the response was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632315) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param postApiV4UsersUserIdImpersonationTokensRequest The postApiV4UsersUserIdImpersonationTokensRequest parameter
     * @return APIEntitiesImpersonationTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesImpersonationTokenWithToken postApiV4UsersUserIdImpersonationTokens(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4UsersUserIdImpersonationTokensRequest postApiV4UsersUserIdImpersonationTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesImpersonationTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersUserIdImpersonationTokensRequestCreation(userId, postApiV4UsersUserIdImpersonationTokensRequest).body(localVarReturnType);
    }

    /**
     * Create an impersonation token
     * Creates an impersonation token for a specified user. These tokens are used to act on behalf of a user and can perform API calls as well as Git read and write actions. These tokens are not visible to the associated user on their profile settings page. To create a fine-grained impersonation token, use &#x60;granular_scopes&#x60; instead of &#x60;scopes&#x60;. The scopes in &#x60;granular_scopes&#x60; apply to the user the token is for. With &#x60;selected_memberships&#x60;, that user must have access to each group and project listed. Either &#x60;scopes&#x60; or &#x60;granular_scopes&#x60; is required. Token values are included with the response, but cannot be retrieved later. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/242819) in GitLab 19.2 and made generally available in GitLab 19.4. The &#x60;granular_scopes&#x60; parameter was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/243990) in GitLab 19.2, and the &#x60;granular_scopes&#x60; attribute in the response was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632315) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param postApiV4UsersUserIdImpersonationTokensRequest The postApiV4UsersUserIdImpersonationTokensRequest parameter
     * @return ResponseEntity&lt;APIEntitiesImpersonationTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesImpersonationTokenWithToken> postApiV4UsersUserIdImpersonationTokensWithHttpInfo(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4UsersUserIdImpersonationTokensRequest postApiV4UsersUserIdImpersonationTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesImpersonationTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersUserIdImpersonationTokensRequestCreation(userId, postApiV4UsersUserIdImpersonationTokensRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an impersonation token
     * Creates an impersonation token for a specified user. These tokens are used to act on behalf of a user and can perform API calls as well as Git read and write actions. These tokens are not visible to the associated user on their profile settings page. To create a fine-grained impersonation token, use &#x60;granular_scopes&#x60; instead of &#x60;scopes&#x60;. The scopes in &#x60;granular_scopes&#x60; apply to the user the token is for. With &#x60;selected_memberships&#x60;, that user must have access to each group and project listed. Either &#x60;scopes&#x60; or &#x60;granular_scopes&#x60; is required. Token values are included with the response, but cannot be retrieved later. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/242819) in GitLab 19.2 and made generally available in GitLab 19.4. The &#x60;granular_scopes&#x60; parameter was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/243990) in GitLab 19.2, and the &#x60;granular_scopes&#x60; attribute in the response was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/632315) in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param postApiV4UsersUserIdImpersonationTokensRequest The postApiV4UsersUserIdImpersonationTokensRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersUserIdImpersonationTokensWithResponseSpec(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4UsersUserIdImpersonationTokensRequest postApiV4UsersUserIdImpersonationTokensRequest) throws RestClientResponseException {
        return postApiV4UsersUserIdImpersonationTokensRequestCreation(userId, postApiV4UsersUserIdImpersonationTokensRequest);
    }

    /**
     * Create a personal access token for a user
     * Creates a personal access token for a user. To create a fine-grained personal access token, use &#x60;granular_scopes&#x60; instead of &#x60;scopes&#x60;. The scopes in &#x60;granular_scopes&#x60; apply to the user the token is for. With &#x60;selected_memberships&#x60;, that user must have access to each group and project listed. Token values are included with the response, but cannot be retrieved later. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/630541) in GitLab 19.6.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param postApiV4UsersUserIdPersonalAccessTokensRequest The postApiV4UsersUserIdPersonalAccessTokensRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersUserIdPersonalAccessTokensRequestCreation(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4UsersUserIdPersonalAccessTokensRequest postApiV4UsersUserIdPersonalAccessTokensRequest) throws RestClientResponseException {
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling postApiV4UsersUserIdPersonalAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4UsersUserIdPersonalAccessTokensRequest' is set
        if (postApiV4UsersUserIdPersonalAccessTokensRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UsersUserIdPersonalAccessTokensRequest' when calling postApiV4UsersUserIdPersonalAccessTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{user_id}/personal_access_tokens", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UsersUserIdPersonalAccessTokensRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a personal access token for a user
     * Creates a personal access token for a user. To create a fine-grained personal access token, use &#x60;granular_scopes&#x60; instead of &#x60;scopes&#x60;. The scopes in &#x60;granular_scopes&#x60; apply to the user the token is for. With &#x60;selected_memberships&#x60;, that user must have access to each group and project listed. Token values are included with the response, but cannot be retrieved later. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/630541) in GitLab 19.6.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param postApiV4UsersUserIdPersonalAccessTokensRequest The postApiV4UsersUserIdPersonalAccessTokensRequest parameter
     * @return APIEntitiesPersonalAccessTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalAccessTokenWithToken postApiV4UsersUserIdPersonalAccessTokens(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4UsersUserIdPersonalAccessTokensRequest postApiV4UsersUserIdPersonalAccessTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersUserIdPersonalAccessTokensRequestCreation(userId, postApiV4UsersUserIdPersonalAccessTokensRequest).body(localVarReturnType);
    }

    /**
     * Create a personal access token for a user
     * Creates a personal access token for a user. To create a fine-grained personal access token, use &#x60;granular_scopes&#x60; instead of &#x60;scopes&#x60;. The scopes in &#x60;granular_scopes&#x60; apply to the user the token is for. With &#x60;selected_memberships&#x60;, that user must have access to each group and project listed. Token values are included with the response, but cannot be retrieved later. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/630541) in GitLab 19.6.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param postApiV4UsersUserIdPersonalAccessTokensRequest The postApiV4UsersUserIdPersonalAccessTokensRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPersonalAccessTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalAccessTokenWithToken> postApiV4UsersUserIdPersonalAccessTokensWithHttpInfo(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4UsersUserIdPersonalAccessTokensRequest postApiV4UsersUserIdPersonalAccessTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalAccessTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersUserIdPersonalAccessTokensRequestCreation(userId, postApiV4UsersUserIdPersonalAccessTokensRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a personal access token for a user
     * Creates a personal access token for a user. To create a fine-grained personal access token, use &#x60;granular_scopes&#x60; instead of &#x60;scopes&#x60;. The scopes in &#x60;granular_scopes&#x60; apply to the user the token is for. With &#x60;selected_memberships&#x60;, that user must have access to each group and project listed. Token values are included with the response, but cannot be retrieved later. Administrators only. The &#x60;last_used_ips&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/250307) in GitLab 19.4. The &#x60;granular_scopes&#x60; attribute was [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/630541) in GitLab 19.6.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param postApiV4UsersUserIdPersonalAccessTokensRequest The postApiV4UsersUserIdPersonalAccessTokensRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersUserIdPersonalAccessTokensWithResponseSpec(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4UsersUserIdPersonalAccessTokensRequest postApiV4UsersUserIdPersonalAccessTokensRequest) throws RestClientResponseException {
        return postApiV4UsersUserIdPersonalAccessTokensRequestCreation(userId, postApiV4UsersUserIdPersonalAccessTokensRequest);
    }
}
