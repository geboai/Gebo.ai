package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesOrganizationsOrganization;
import ai.gebo.gitlab.client.model.APIEntitiesOrganizationsOrganizationUser;
import java.io.File;
import ai.gebo.gitlab.client.model.PostApiV4OrganizationsIdTransferUsersRequest;
import ai.gebo.gitlab.client.model.PostApiV4OrganizationsIdUsersRequest;
import ai.gebo.gitlab.client.model.PutApiV4OrganizationsIdUsersUserIdRequest;

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
public class OrganizationsApi {
    private ApiClient apiClient;

    public OrganizationsApi() {
        this(new ApiClient());
    }

    public OrganizationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Soft-delete an organization
     * This feature was introduced in GitLab 19.2.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the organization.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4OrganizationsIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4OrganizationsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/organizations/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Soft-delete an organization
     * This feature was introduced in GitLab 19.2.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the organization.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4OrganizationsId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4OrganizationsIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Soft-delete an organization
     * This feature was introduced in GitLab 19.2.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the organization.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4OrganizationsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4OrganizationsIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Soft-delete an organization
     * This feature was introduced in GitLab 19.2.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the organization.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4OrganizationsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteApiV4OrganizationsIdRequestCreation(id);
    }

    /**
     * Remove a user from an organization
     * Removes a user from an organization, including their group and project memberships in it. The last owner of an organization cannot be removed. This feature was introduced in GitLab 19.6.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param userId ID or username of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4OrganizationsIdUsersUserIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4OrganizationsIdUsersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling deleteApiV4OrganizationsIdUsersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/organizations/{id}/users/{user_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove a user from an organization
     * Removes a user from an organization, including their group and project memberships in it. The last owner of an organization cannot be removed. This feature was introduced in GitLab 19.6.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param userId ID or username of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4OrganizationsIdUsersUserId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4OrganizationsIdUsersUserIdRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Remove a user from an organization
     * Removes a user from an organization, including their group and project memberships in it. The last owner of an organization cannot be removed. This feature was introduced in GitLab 19.6.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param userId ID or username of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4OrganizationsIdUsersUserIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4OrganizationsIdUsersUserIdRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Remove a user from an organization
     * Removes a user from an organization, including their group and project memberships in it. The last owner of an organization cannot be removed. This feature was introduced in GitLab 19.6.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param userId ID or username of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4OrganizationsIdUsersUserIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String userId) throws RestClientResponseException {
        return deleteApiV4OrganizationsIdUsersUserIdRequestCreation(id, userId);
    }

    /**
     * List organization users
     * Lists the users of an organization. Uses keyset pagination. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param pagination Pagination method. Only &#x60;keyset&#x60; is supported.
     * @param cursor Cursor for obtaining the next set of records.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesOrganizationsOrganizationUser&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4OrganizationsIdUsersRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String pagination, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4OrganizationsIdUsers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pagination", pagination));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "cursor", cursor));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesOrganizationsOrganizationUser>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/organizations/{id}/users", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List organization users
     * Lists the users of an organization. Uses keyset pagination. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param pagination Pagination method. Only &#x60;keyset&#x60; is supported.
     * @param cursor Cursor for obtaining the next set of records.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesOrganizationsOrganizationUser&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesOrganizationsOrganizationUser> getApiV4OrganizationsIdUsers(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String pagination, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesOrganizationsOrganizationUser>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4OrganizationsIdUsersRequestCreation(id, pagination, cursor, orderBy, sort, perPage).body(localVarReturnType);
    }

    /**
     * List organization users
     * Lists the users of an organization. Uses keyset pagination. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param pagination Pagination method. Only &#x60;keyset&#x60; is supported.
     * @param cursor Cursor for obtaining the next set of records.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesOrganizationsOrganizationUser&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesOrganizationsOrganizationUser>> getApiV4OrganizationsIdUsersWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String pagination, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesOrganizationsOrganizationUser>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4OrganizationsIdUsersRequestCreation(id, pagination, cursor, orderBy, sort, perPage).toEntity(localVarReturnType);
    }

    /**
     * List organization users
     * Lists the users of an organization. Uses keyset pagination. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param pagination Pagination method. Only &#x60;keyset&#x60; is supported.
     * @param cursor Cursor for obtaining the next set of records.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4OrganizationsIdUsersWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String pagination, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4OrganizationsIdUsersRequestCreation(id, pagination, cursor, orderBy, sort, perPage);
    }

    /**
     * Get an organization user
     * Gets a single user of an organization. This feature was introduced in GitLab 19.7.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id The ID of the organization.
     * @param userId ID or username of the user.
     * @return APIEntitiesOrganizationsOrganizationUser
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4OrganizationsIdUsersUserIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4OrganizationsIdUsersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4OrganizationsIdUsersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesOrganizationsOrganizationUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/organizations/{id}/users/{user_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get an organization user
     * Gets a single user of an organization. This feature was introduced in GitLab 19.7.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id The ID of the organization.
     * @param userId ID or username of the user.
     * @return APIEntitiesOrganizationsOrganizationUser
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesOrganizationsOrganizationUser getApiV4OrganizationsIdUsersUserId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrganizationsOrganizationUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4OrganizationsIdUsersUserIdRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Get an organization user
     * Gets a single user of an organization. This feature was introduced in GitLab 19.7.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id The ID of the organization.
     * @param userId ID or username of the user.
     * @return ResponseEntity&lt;APIEntitiesOrganizationsOrganizationUser&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesOrganizationsOrganizationUser> getApiV4OrganizationsIdUsersUserIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrganizationsOrganizationUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4OrganizationsIdUsersUserIdRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Get an organization user
     * Gets a single user of an organization. This feature was introduced in GitLab 19.7.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id The ID of the organization.
     * @param userId ID or username of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4OrganizationsIdUsersUserIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String userId) throws RestClientResponseException {
        return getApiV4OrganizationsIdUsersUserIdRequestCreation(id, userId);
    }

    /**
     * Create an organization
     * Creates an organization. This feature was introduced in GitLab 17.5. This feature is behind the &#x60;allow_organization_creation&#x60; feature flag. In GitLab 18.3, the feature flag changed to &#x60;organization_switching&#x60;. In GitLab 19.4, the feature flag changed to &#x60;org_stage_experimental&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too many requests
     * @param name The name of the organization
     * @param path The path of the organization
     * @param description The description of the organization
     * @param visibility The visibility level of the organization
     * @param avatar The avatar image for the organization
     * @return APIEntitiesOrganizationsOrganization
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4OrganizationsRequestCreation(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable File avatar) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling postApiV4Organizations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling postApiV4Organizations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("name", name);
        formParams.add("path", path);
        if (description != null) {
            formParams.add("description", description);
        }
        if (visibility != null) {
            formParams.add("visibility", visibility);
        }
        if (avatar != null) {
            formParams.add("avatar", new FileSystemResource(avatar));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesOrganizationsOrganization> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/organizations", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an organization
     * Creates an organization. This feature was introduced in GitLab 17.5. This feature is behind the &#x60;allow_organization_creation&#x60; feature flag. In GitLab 18.3, the feature flag changed to &#x60;organization_switching&#x60;. In GitLab 19.4, the feature flag changed to &#x60;org_stage_experimental&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too many requests
     * @param name The name of the organization
     * @param path The path of the organization
     * @param description The description of the organization
     * @param visibility The visibility level of the organization
     * @param avatar The avatar image for the organization
     * @return APIEntitiesOrganizationsOrganization
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesOrganizationsOrganization postApiV4Organizations(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable File avatar) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrganizationsOrganization> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OrganizationsRequestCreation(name, path, description, visibility, avatar).body(localVarReturnType);
    }

    /**
     * Create an organization
     * Creates an organization. This feature was introduced in GitLab 17.5. This feature is behind the &#x60;allow_organization_creation&#x60; feature flag. In GitLab 18.3, the feature flag changed to &#x60;organization_switching&#x60;. In GitLab 19.4, the feature flag changed to &#x60;org_stage_experimental&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too many requests
     * @param name The name of the organization
     * @param path The path of the organization
     * @param description The description of the organization
     * @param visibility The visibility level of the organization
     * @param avatar The avatar image for the organization
     * @return ResponseEntity&lt;APIEntitiesOrganizationsOrganization&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesOrganizationsOrganization> postApiV4OrganizationsWithHttpInfo(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable File avatar) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrganizationsOrganization> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OrganizationsRequestCreation(name, path, description, visibility, avatar).toEntity(localVarReturnType);
    }

    /**
     * Create an organization
     * Creates an organization. This feature was introduced in GitLab 17.5. This feature is behind the &#x60;allow_organization_creation&#x60; feature flag. In GitLab 18.3, the feature flag changed to &#x60;organization_switching&#x60;. In GitLab 19.4, the feature flag changed to &#x60;org_stage_experimental&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too many requests
     * @param name The name of the organization
     * @param path The path of the organization
     * @param description The description of the organization
     * @param visibility The visibility level of the organization
     * @param avatar The avatar image for the organization
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4OrganizationsWithResponseSpec(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable File avatar) throws RestClientResponseException {
        return postApiV4OrganizationsRequestCreation(name, path, description, visibility, avatar);
    }

    /**
     * Restore a soft-deleted organization
     * Restores a soft-deleted organization. Administrators only. This feature was introduced in GitLab 19.6.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID of the organization.
     * @return APIEntitiesOrganizationsOrganization
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4OrganizationsIdRestoreRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4OrganizationsIdRestore", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesOrganizationsOrganization> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/organizations/{id}/restore", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Restore a soft-deleted organization
     * Restores a soft-deleted organization. Administrators only. This feature was introduced in GitLab 19.6.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID of the organization.
     * @return APIEntitiesOrganizationsOrganization
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesOrganizationsOrganization postApiV4OrganizationsIdRestore(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrganizationsOrganization> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OrganizationsIdRestoreRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Restore a soft-deleted organization
     * Restores a soft-deleted organization. Administrators only. This feature was introduced in GitLab 19.6.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID of the organization.
     * @return ResponseEntity&lt;APIEntitiesOrganizationsOrganization&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesOrganizationsOrganization> postApiV4OrganizationsIdRestoreWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrganizationsOrganization> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OrganizationsIdRestoreRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Restore a soft-deleted organization
     * Restores a soft-deleted organization. Administrators only. This feature was introduced in GitLab 19.6.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID of the organization.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4OrganizationsIdRestoreWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4OrganizationsIdRestoreRequestCreation(id);
    }

    /**
     * Transfer users to an organization
     * Transfers ownership of users to the organization, including their personal namespaces and projects. All users must currently belong to the same organization. This is a change of ownership, not organization membership. The transfer runs in the background. Administrators only. No user can be an internal user, such as the ghost user or a bot like &#x60;support_bot&#x60; or &#x60;admin_bot&#x60;. Service accounts and project access token bots can be transferred. This feature was introduced in GitLab 19.6.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID of the organization to transfer the users to.
     * @param postApiV4OrganizationsIdTransferUsersRequest The postApiV4OrganizationsIdTransferUsersRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4OrganizationsIdTransferUsersRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4OrganizationsIdTransferUsersRequest postApiV4OrganizationsIdTransferUsersRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4OrganizationsIdTransferUsers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4OrganizationsIdTransferUsersRequest' is set
        if (postApiV4OrganizationsIdTransferUsersRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4OrganizationsIdTransferUsersRequest' when calling postApiV4OrganizationsIdTransferUsers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/organizations/{id}/transfer_users", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4OrganizationsIdTransferUsersRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Transfer users to an organization
     * Transfers ownership of users to the organization, including their personal namespaces and projects. All users must currently belong to the same organization. This is a change of ownership, not organization membership. The transfer runs in the background. Administrators only. No user can be an internal user, such as the ghost user or a bot like &#x60;support_bot&#x60; or &#x60;admin_bot&#x60;. Service accounts and project access token bots can be transferred. This feature was introduced in GitLab 19.6.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID of the organization to transfer the users to.
     * @param postApiV4OrganizationsIdTransferUsersRequest The postApiV4OrganizationsIdTransferUsersRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4OrganizationsIdTransferUsers(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4OrganizationsIdTransferUsersRequest postApiV4OrganizationsIdTransferUsersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4OrganizationsIdTransferUsersRequestCreation(id, postApiV4OrganizationsIdTransferUsersRequest).body(localVarReturnType);
    }

    /**
     * Transfer users to an organization
     * Transfers ownership of users to the organization, including their personal namespaces and projects. All users must currently belong to the same organization. This is a change of ownership, not organization membership. The transfer runs in the background. Administrators only. No user can be an internal user, such as the ghost user or a bot like &#x60;support_bot&#x60; or &#x60;admin_bot&#x60;. Service accounts and project access token bots can be transferred. This feature was introduced in GitLab 19.6.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID of the organization to transfer the users to.
     * @param postApiV4OrganizationsIdTransferUsersRequest The postApiV4OrganizationsIdTransferUsersRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4OrganizationsIdTransferUsersWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4OrganizationsIdTransferUsersRequest postApiV4OrganizationsIdTransferUsersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OrganizationsIdTransferUsersRequestCreation(id, postApiV4OrganizationsIdTransferUsersRequest).toEntity(localVarReturnType);
    }

    /**
     * Transfer users to an organization
     * Transfers ownership of users to the organization, including their personal namespaces and projects. All users must currently belong to the same organization. This is a change of ownership, not organization membership. The transfer runs in the background. Administrators only. No user can be an internal user, such as the ghost user or a bot like &#x60;support_bot&#x60; or &#x60;admin_bot&#x60;. Service accounts and project access token bots can be transferred. This feature was introduced in GitLab 19.6.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID of the organization to transfer the users to.
     * @param postApiV4OrganizationsIdTransferUsersRequest The postApiV4OrganizationsIdTransferUsersRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4OrganizationsIdTransferUsersWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4OrganizationsIdTransferUsersRequest postApiV4OrganizationsIdTransferUsersRequest) throws RestClientResponseException {
        return postApiV4OrganizationsIdTransferUsersRequestCreation(id, postApiV4OrganizationsIdTransferUsersRequest);
    }

    /**
     * Add a user to an organization
     * Adds a user to an organization, optionally as an owner. This feature was introduced in GitLab 19.6.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param postApiV4OrganizationsIdUsersRequest The postApiV4OrganizationsIdUsersRequest parameter
     * @return APIEntitiesOrganizationsOrganizationUser
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4OrganizationsIdUsersRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4OrganizationsIdUsersRequest postApiV4OrganizationsIdUsersRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4OrganizationsIdUsers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesOrganizationsOrganizationUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/organizations/{id}/users", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4OrganizationsIdUsersRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a user to an organization
     * Adds a user to an organization, optionally as an owner. This feature was introduced in GitLab 19.6.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param postApiV4OrganizationsIdUsersRequest The postApiV4OrganizationsIdUsersRequest parameter
     * @return APIEntitiesOrganizationsOrganizationUser
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesOrganizationsOrganizationUser postApiV4OrganizationsIdUsers(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4OrganizationsIdUsersRequest postApiV4OrganizationsIdUsersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrganizationsOrganizationUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OrganizationsIdUsersRequestCreation(id, postApiV4OrganizationsIdUsersRequest).body(localVarReturnType);
    }

    /**
     * Add a user to an organization
     * Adds a user to an organization, optionally as an owner. This feature was introduced in GitLab 19.6.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param postApiV4OrganizationsIdUsersRequest The postApiV4OrganizationsIdUsersRequest parameter
     * @return ResponseEntity&lt;APIEntitiesOrganizationsOrganizationUser&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesOrganizationsOrganizationUser> postApiV4OrganizationsIdUsersWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4OrganizationsIdUsersRequest postApiV4OrganizationsIdUsersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrganizationsOrganizationUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OrganizationsIdUsersRequestCreation(id, postApiV4OrganizationsIdUsersRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a user to an organization
     * Adds a user to an organization, optionally as an owner. This feature was introduced in GitLab 19.6.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param postApiV4OrganizationsIdUsersRequest The postApiV4OrganizationsIdUsersRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4OrganizationsIdUsersWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4OrganizationsIdUsersRequest postApiV4OrganizationsIdUsersRequest) throws RestClientResponseException {
        return postApiV4OrganizationsIdUsersRequestCreation(id, postApiV4OrganizationsIdUsersRequest);
    }

    /**
     * Update an organization user
     * Changes the access level of a user in an organization, for example to promote them to owner. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param userId ID or username of the user.
     * @param putApiV4OrganizationsIdUsersUserIdRequest The putApiV4OrganizationsIdUsersUserIdRequest parameter
     * @return APIEntitiesOrganizationsOrganizationUser
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4OrganizationsIdUsersUserIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nonnull PutApiV4OrganizationsIdUsersUserIdRequest putApiV4OrganizationsIdUsersUserIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4OrganizationsIdUsersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling putApiV4OrganizationsIdUsersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4OrganizationsIdUsersUserIdRequest' is set
        if (putApiV4OrganizationsIdUsersUserIdRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4OrganizationsIdUsersUserIdRequest' when calling putApiV4OrganizationsIdUsersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesOrganizationsOrganizationUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/organizations/{id}/users/{user_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4OrganizationsIdUsersUserIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an organization user
     * Changes the access level of a user in an organization, for example to promote them to owner. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param userId ID or username of the user.
     * @param putApiV4OrganizationsIdUsersUserIdRequest The putApiV4OrganizationsIdUsersUserIdRequest parameter
     * @return APIEntitiesOrganizationsOrganizationUser
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesOrganizationsOrganizationUser putApiV4OrganizationsIdUsersUserId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nonnull PutApiV4OrganizationsIdUsersUserIdRequest putApiV4OrganizationsIdUsersUserIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrganizationsOrganizationUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4OrganizationsIdUsersUserIdRequestCreation(id, userId, putApiV4OrganizationsIdUsersUserIdRequest).body(localVarReturnType);
    }

    /**
     * Update an organization user
     * Changes the access level of a user in an organization, for example to promote them to owner. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param userId ID or username of the user.
     * @param putApiV4OrganizationsIdUsersUserIdRequest The putApiV4OrganizationsIdUsersUserIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesOrganizationsOrganizationUser&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesOrganizationsOrganizationUser> putApiV4OrganizationsIdUsersUserIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nonnull PutApiV4OrganizationsIdUsersUserIdRequest putApiV4OrganizationsIdUsersUserIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrganizationsOrganizationUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4OrganizationsIdUsersUserIdRequestCreation(id, userId, putApiV4OrganizationsIdUsersUserIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an organization user
     * Changes the access level of a user in an organization, for example to promote them to owner. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the organization.
     * @param userId ID or username of the user.
     * @param putApiV4OrganizationsIdUsersUserIdRequest The putApiV4OrganizationsIdUsersUserIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4OrganizationsIdUsersUserIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nonnull PutApiV4OrganizationsIdUsersUserIdRequest putApiV4OrganizationsIdUsersUserIdRequest) throws RestClientResponseException {
        return putApiV4OrganizationsIdUsersUserIdRequestCreation(id, userId, putApiV4OrganizationsIdUsersUserIdRequest);
    }
}
