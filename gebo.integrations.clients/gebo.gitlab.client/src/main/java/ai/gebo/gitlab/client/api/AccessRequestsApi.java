package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAccessRequester;
import ai.gebo.gitlab.client.model.APIEntitiesMember;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdAccessRequestsUserIdApproveRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdAccessRequestsUserIdApproveRequest;

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
public class AccessRequestsApi {
    private ApiClient apiClient;

    public AccessRequestsApi() {
        this(new ApiClient());
    }

    public AccessRequestsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Deny an access request
     * Denies an access request for a specified user in a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user requesting access.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdAccessRequestsUserIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdAccessRequestsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling deleteApiV4GroupsIdAccessRequestsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/access_requests/{user_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Deny an access request
     * Denies an access request for a specified user in a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user requesting access.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdAccessRequestsUserId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdAccessRequestsUserIdRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Deny an access request
     * Denies an access request for a specified user in a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user requesting access.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdAccessRequestsUserIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdAccessRequestsUserIdRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Deny an access request
     * Denies an access request for a specified user in a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user requesting access.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdAccessRequestsUserIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return deleteApiV4GroupsIdAccessRequestsUserIdRequestCreation(id, userId);
    }

    /**
     * Deny an access request
     * Denies an access request for a specified user in a project.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the user requesting access.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdAccessRequestsUserIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdAccessRequestsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling deleteApiV4ProjectsIdAccessRequestsUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/access_requests/{user_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Deny an access request
     * Denies an access request for a specified user in a project.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the user requesting access.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdAccessRequestsUserId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdAccessRequestsUserIdRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Deny an access request
     * Denies an access request for a specified user in a project.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the user requesting access.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdAccessRequestsUserIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdAccessRequestsUserIdRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Deny an access request
     * Denies an access request for a specified user in a project.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the user requesting access.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdAccessRequestsUserIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdAccessRequestsUserIdRequestCreation(id, userId);
    }

    /**
     * List all access requests for a group
     * Lists all access requests for a specified group that are viewable by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAccessRequester&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdAccessRequestsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdAccessRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesAccessRequester>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/access_requests", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all access requests for a group
     * Lists all access requests for a specified group that are viewable by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAccessRequester&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAccessRequester> getApiV4GroupsIdAccessRequests(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAccessRequester>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdAccessRequestsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all access requests for a group
     * Lists all access requests for a specified group that are viewable by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAccessRequester&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAccessRequester>> getApiV4GroupsIdAccessRequestsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAccessRequester>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdAccessRequestsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all access requests for a group
     * Lists all access requests for a specified group that are viewable by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdAccessRequestsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdAccessRequestsRequestCreation(id, page, perPage);
    }

    /**
     * List all access requests for a project
     * Lists all access requests for a specified project that are viewable by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAccessRequester&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdAccessRequestsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdAccessRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesAccessRequester>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/access_requests", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all access requests for a project
     * Lists all access requests for a specified project that are viewable by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAccessRequester&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAccessRequester> getApiV4ProjectsIdAccessRequests(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAccessRequester>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAccessRequestsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all access requests for a project
     * Lists all access requests for a specified project that are viewable by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAccessRequester&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAccessRequester>> getApiV4ProjectsIdAccessRequestsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAccessRequester>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAccessRequestsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all access requests for a project
     * Lists all access requests for a specified project that are viewable by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdAccessRequestsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdAccessRequestsRequestCreation(id, page, perPage);
    }

    /**
     * Request access to a group
     * Requests access to a specified group for the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesAccessRequester
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdAccessRequestsRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdAccessRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesAccessRequester> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/access_requests", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Request access to a group
     * Requests access to a specified group for the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesAccessRequester
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAccessRequester postApiV4GroupsIdAccessRequests(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAccessRequester> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdAccessRequestsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Request access to a group
     * Requests access to a specified group for the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;APIEntitiesAccessRequester&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAccessRequester> postApiV4GroupsIdAccessRequestsWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAccessRequester> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdAccessRequestsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Request access to a group
     * Requests access to a specified group for the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdAccessRequestsWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return postApiV4GroupsIdAccessRequestsRequestCreation(id);
    }

    /**
     * Request access to a project
     * Requests access to a specified project for the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesAccessRequester
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdAccessRequestsRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdAccessRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesAccessRequester> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/access_requests", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Request access to a project
     * Requests access to a specified project for the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesAccessRequester
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAccessRequester postApiV4ProjectsIdAccessRequests(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAccessRequester> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAccessRequestsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Request access to a project
     * Requests access to a specified project for the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesAccessRequester&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAccessRequester> postApiV4ProjectsIdAccessRequestsWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAccessRequester> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAccessRequestsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Request access to a project
     * Requests access to a specified project for the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdAccessRequestsWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return postApiV4ProjectsIdAccessRequestsRequestCreation(id);
    }

    /**
     * Approve an access request
     * Approves an access request for a specified user in a group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user requesting access.
     * @param putApiV4GroupsIdAccessRequestsUserIdApproveRequest The putApiV4GroupsIdAccessRequestsUserIdApproveRequest parameter
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdAccessRequestsUserIdApproveRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PutApiV4GroupsIdAccessRequestsUserIdApproveRequest putApiV4GroupsIdAccessRequestsUserIdApproveRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdAccessRequestsUserIdApprove", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling putApiV4GroupsIdAccessRequestsUserIdApprove", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/access_requests/{user_id}/approve", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdAccessRequestsUserIdApproveRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Approve an access request
     * Approves an access request for a specified user in a group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user requesting access.
     * @param putApiV4GroupsIdAccessRequestsUserIdApproveRequest The putApiV4GroupsIdAccessRequestsUserIdApproveRequest parameter
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMember putApiV4GroupsIdAccessRequestsUserIdApprove(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PutApiV4GroupsIdAccessRequestsUserIdApproveRequest putApiV4GroupsIdAccessRequestsUserIdApproveRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdAccessRequestsUserIdApproveRequestCreation(id, userId, putApiV4GroupsIdAccessRequestsUserIdApproveRequest).body(localVarReturnType);
    }

    /**
     * Approve an access request
     * Approves an access request for a specified user in a group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user requesting access.
     * @param putApiV4GroupsIdAccessRequestsUserIdApproveRequest The putApiV4GroupsIdAccessRequestsUserIdApproveRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMember> putApiV4GroupsIdAccessRequestsUserIdApproveWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PutApiV4GroupsIdAccessRequestsUserIdApproveRequest putApiV4GroupsIdAccessRequestsUserIdApproveRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdAccessRequestsUserIdApproveRequestCreation(id, userId, putApiV4GroupsIdAccessRequestsUserIdApproveRequest).toEntity(localVarReturnType);
    }

    /**
     * Approve an access request
     * Approves an access request for a specified user in a group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user requesting access.
     * @param putApiV4GroupsIdAccessRequestsUserIdApproveRequest The putApiV4GroupsIdAccessRequestsUserIdApproveRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdAccessRequestsUserIdApproveWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PutApiV4GroupsIdAccessRequestsUserIdApproveRequest putApiV4GroupsIdAccessRequestsUserIdApproveRequest) throws RestClientResponseException {
        return putApiV4GroupsIdAccessRequestsUserIdApproveRequestCreation(id, userId, putApiV4GroupsIdAccessRequestsUserIdApproveRequest);
    }

    /**
     * Approve an access request
     * Approves an access request for a specified user in a project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the user requesting access.
     * @param putApiV4ProjectsIdAccessRequestsUserIdApproveRequest The putApiV4ProjectsIdAccessRequestsUserIdApproveRequest parameter
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdAccessRequestsUserIdApproveRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PutApiV4ProjectsIdAccessRequestsUserIdApproveRequest putApiV4ProjectsIdAccessRequestsUserIdApproveRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdAccessRequestsUserIdApprove", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling putApiV4ProjectsIdAccessRequestsUserIdApprove", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/access_requests/{user_id}/approve", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdAccessRequestsUserIdApproveRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Approve an access request
     * Approves an access request for a specified user in a project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the user requesting access.
     * @param putApiV4ProjectsIdAccessRequestsUserIdApproveRequest The putApiV4ProjectsIdAccessRequestsUserIdApproveRequest parameter
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMember putApiV4ProjectsIdAccessRequestsUserIdApprove(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PutApiV4ProjectsIdAccessRequestsUserIdApproveRequest putApiV4ProjectsIdAccessRequestsUserIdApproveRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdAccessRequestsUserIdApproveRequestCreation(id, userId, putApiV4ProjectsIdAccessRequestsUserIdApproveRequest).body(localVarReturnType);
    }

    /**
     * Approve an access request
     * Approves an access request for a specified user in a project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the user requesting access.
     * @param putApiV4ProjectsIdAccessRequestsUserIdApproveRequest The putApiV4ProjectsIdAccessRequestsUserIdApproveRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMember> putApiV4ProjectsIdAccessRequestsUserIdApproveWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PutApiV4ProjectsIdAccessRequestsUserIdApproveRequest putApiV4ProjectsIdAccessRequestsUserIdApproveRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdAccessRequestsUserIdApproveRequestCreation(id, userId, putApiV4ProjectsIdAccessRequestsUserIdApproveRequest).toEntity(localVarReturnType);
    }

    /**
     * Approve an access request
     * Approves an access request for a specified user in a project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId ID of the user requesting access.
     * @param putApiV4ProjectsIdAccessRequestsUserIdApproveRequest The putApiV4ProjectsIdAccessRequestsUserIdApproveRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdAccessRequestsUserIdApproveWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable PutApiV4ProjectsIdAccessRequestsUserIdApproveRequest putApiV4ProjectsIdAccessRequestsUserIdApproveRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdAccessRequestsUserIdApproveRequestCreation(id, userId, putApiV4ProjectsIdAccessRequestsUserIdApproveRequest);
    }
}
