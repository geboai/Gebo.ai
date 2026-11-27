package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesGroupsRepositoryStorageMove;
import ai.gebo.gitlab.client.model.APIEntitiesProjectsRepositoryStorageMove;
import ai.gebo.gitlab.client.model.APIEntitiesSnippetsRepositoryStorageMove;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupRepositoryStorageMovesRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdRepositoryStorageMovesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectRepositoryStorageMovesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdRepositoryStorageMovesRequest;
import ai.gebo.gitlab.client.model.PostApiV4SnippetRepositoryStorageMovesRequest;
import ai.gebo.gitlab.client.model.PostApiV4SnippetsIdRepositoryStorageMovesRequest;

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
public class StorageMovesApi {
    private ApiClient apiClient;

    public StorageMovesApi() {
        this(new ApiClient());
    }

    public StorageMovesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all group repository storage moves
     * Lists all group repository storage moves for an instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGroupsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupRepositoryStorageMovesRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesGroupsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/group_repository_storage_moves", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group repository storage moves
     * Lists all group repository storage moves for an instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGroupsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGroupsRepositoryStorageMove> getApiV4GroupRepositoryStorageMoves(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroupsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupRepositoryStorageMovesRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all group repository storage moves
     * Lists all group repository storage moves for an instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGroupsRepositoryStorageMove&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGroupsRepositoryStorageMove>> getApiV4GroupRepositoryStorageMovesWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroupsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupRepositoryStorageMovesRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group repository storage moves
     * Lists all group repository storage moves for an instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupRepositoryStorageMovesWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupRepositoryStorageMovesRequestCreation(page, perPage);
    }

    /**
     * Retrieve a group repository storage move
     * Retrieves a specified group repository storage move. This feature was introduced in GitLab 13.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param repositoryStorageMoveId ID of the group repository storage move.
     * @return APIEntitiesGroupsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(@jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        // verify the required parameter 'repositoryStorageMoveId' is set
        if (repositoryStorageMoveId == null) {
            throw new RestClientResponseException("Missing the required parameter 'repositoryStorageMoveId' when calling getApiV4GroupRepositoryStorageMovesRepositoryStorageMoveId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("repository_storage_move_id", repositoryStorageMoveId);

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

        ParameterizedTypeReference<APIEntitiesGroupsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/group_repository_storage_moves/{repository_storage_move_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group repository storage move
     * Retrieves a specified group repository storage move. This feature was introduced in GitLab 13.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param repositoryStorageMoveId ID of the group repository storage move.
     * @return APIEntitiesGroupsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupsRepositoryStorageMove getApiV4GroupRepositoryStorageMovesRepositoryStorageMoveId(@jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(repositoryStorageMoveId).body(localVarReturnType);
    }

    /**
     * Retrieve a group repository storage move
     * Retrieves a specified group repository storage move. This feature was introduced in GitLab 13.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param repositoryStorageMoveId ID of the group repository storage move.
     * @return ResponseEntity&lt;APIEntitiesGroupsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupsRepositoryStorageMove> getApiV4GroupRepositoryStorageMovesRepositoryStorageMoveIdWithHttpInfo(@jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(repositoryStorageMoveId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group repository storage move
     * Retrieves a specified group repository storage move. This feature was introduced in GitLab 13.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param repositoryStorageMoveId ID of the group repository storage move.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupRepositoryStorageMovesRepositoryStorageMoveIdWithResponseSpec(@jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        return getApiV4GroupRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(repositoryStorageMoveId);
    }

    /**
     * List all repository storage moves for a group
     * Lists all repository storage moves for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGroupsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdRepositoryStorageMovesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdRepositoryStorageMoves", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesGroupsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/repository_storage_moves", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all repository storage moves for a group
     * Lists all repository storage moves for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGroupsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGroupsRepositoryStorageMove> getApiV4GroupsIdRepositoryStorageMoves(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroupsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdRepositoryStorageMovesRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all repository storage moves for a group
     * Lists all repository storage moves for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGroupsRepositoryStorageMove&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGroupsRepositoryStorageMove>> getApiV4GroupsIdRepositoryStorageMovesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroupsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdRepositoryStorageMovesRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all repository storage moves for a group
     * Lists all repository storage moves for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdRepositoryStorageMovesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdRepositoryStorageMovesRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a repository storage move for a group
     * Retrieves a specified repository storage move for a group. This feature was introduced in GitLab 13.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param repositoryStorageMoveId ID of the group repository storage move.
     * @return APIEntitiesGroupsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdRepositoryStorageMovesRepositoryStorageMoveId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'repositoryStorageMoveId' is set
        if (repositoryStorageMoveId == null) {
            throw new RestClientResponseException("Missing the required parameter 'repositoryStorageMoveId' when calling getApiV4GroupsIdRepositoryStorageMovesRepositoryStorageMoveId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("repository_storage_move_id", repositoryStorageMoveId);

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

        ParameterizedTypeReference<APIEntitiesGroupsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/repository_storage_moves/{repository_storage_move_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a repository storage move for a group
     * Retrieves a specified repository storage move for a group. This feature was introduced in GitLab 13.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param repositoryStorageMoveId ID of the group repository storage move.
     * @return APIEntitiesGroupsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupsRepositoryStorageMove getApiV4GroupsIdRepositoryStorageMovesRepositoryStorageMoveId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(id, repositoryStorageMoveId).body(localVarReturnType);
    }

    /**
     * Retrieve a repository storage move for a group
     * Retrieves a specified repository storage move for a group. This feature was introduced in GitLab 13.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param repositoryStorageMoveId ID of the group repository storage move.
     * @return ResponseEntity&lt;APIEntitiesGroupsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupsRepositoryStorageMove> getApiV4GroupsIdRepositoryStorageMovesRepositoryStorageMoveIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(id, repositoryStorageMoveId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a repository storage move for a group
     * Retrieves a specified repository storage move for a group. This feature was introduced in GitLab 13.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param repositoryStorageMoveId ID of the group repository storage move.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdRepositoryStorageMovesRepositoryStorageMoveIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        return getApiV4GroupsIdRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(id, repositoryStorageMoveId);
    }

    /**
     * List all project repository storage moves
     * Lists all project repository storage moves.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectRepositoryStorageMovesRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesProjectsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/project_repository_storage_moves", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project repository storage moves
     * Lists all project repository storage moves.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesProjectsRepositoryStorageMove> getApiV4ProjectRepositoryStorageMoves(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectRepositoryStorageMovesRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all project repository storage moves
     * Lists all project repository storage moves.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesProjectsRepositoryStorageMove&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesProjectsRepositoryStorageMove>> getApiV4ProjectRepositoryStorageMovesWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectRepositoryStorageMovesRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project repository storage moves
     * Lists all project repository storage moves.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectRepositoryStorageMovesWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectRepositoryStorageMovesRequestCreation(page, perPage);
    }

    /**
     * Retrieve a project repository storage move
     * Retrieves a specified project repository storage move.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param repositoryStorageMoveId ID of the project repository storage move.
     * @return APIEntitiesProjectsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(@jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        // verify the required parameter 'repositoryStorageMoveId' is set
        if (repositoryStorageMoveId == null) {
            throw new RestClientResponseException("Missing the required parameter 'repositoryStorageMoveId' when calling getApiV4ProjectRepositoryStorageMovesRepositoryStorageMoveId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("repository_storage_move_id", repositoryStorageMoveId);

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

        ParameterizedTypeReference<APIEntitiesProjectsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/project_repository_storage_moves/{repository_storage_move_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a project repository storage move
     * Retrieves a specified project repository storage move.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param repositoryStorageMoveId ID of the project repository storage move.
     * @return APIEntitiesProjectsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectsRepositoryStorageMove getApiV4ProjectRepositoryStorageMovesRepositoryStorageMoveId(@jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(repositoryStorageMoveId).body(localVarReturnType);
    }

    /**
     * Retrieve a project repository storage move
     * Retrieves a specified project repository storage move.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param repositoryStorageMoveId ID of the project repository storage move.
     * @return ResponseEntity&lt;APIEntitiesProjectsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectsRepositoryStorageMove> getApiV4ProjectRepositoryStorageMovesRepositoryStorageMoveIdWithHttpInfo(@jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(repositoryStorageMoveId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a project repository storage move
     * Retrieves a specified project repository storage move.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param repositoryStorageMoveId ID of the project repository storage move.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectRepositoryStorageMovesRepositoryStorageMoveIdWithResponseSpec(@jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        return getApiV4ProjectRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(repositoryStorageMoveId);
    }

    /**
     * List all repository storage moves for a project
     * Lists all repository storage moves for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryStorageMovesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryStorageMoves", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesProjectsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository_storage_moves", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all repository storage moves for a project
     * Lists all repository storage moves for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesProjectsRepositoryStorageMove> getApiV4ProjectsIdRepositoryStorageMoves(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryStorageMovesRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all repository storage moves for a project
     * Lists all repository storage moves for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesProjectsRepositoryStorageMove&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesProjectsRepositoryStorageMove>> getApiV4ProjectsIdRepositoryStorageMovesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryStorageMovesRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all repository storage moves for a project
     * Lists all repository storage moves for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryStorageMovesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryStorageMovesRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a repository storage move for a project
     * Retrieves a specified repository storage move for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param repositoryStorageMoveId ID of the project repository storage move.
     * @return APIEntitiesProjectsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryStorageMovesRepositoryStorageMoveId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'repositoryStorageMoveId' is set
        if (repositoryStorageMoveId == null) {
            throw new RestClientResponseException("Missing the required parameter 'repositoryStorageMoveId' when calling getApiV4ProjectsIdRepositoryStorageMovesRepositoryStorageMoveId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("repository_storage_move_id", repositoryStorageMoveId);

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

        ParameterizedTypeReference<APIEntitiesProjectsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository_storage_moves/{repository_storage_move_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a repository storage move for a project
     * Retrieves a specified repository storage move for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param repositoryStorageMoveId ID of the project repository storage move.
     * @return APIEntitiesProjectsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectsRepositoryStorageMove getApiV4ProjectsIdRepositoryStorageMovesRepositoryStorageMoveId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(id, repositoryStorageMoveId).body(localVarReturnType);
    }

    /**
     * Retrieve a repository storage move for a project
     * Retrieves a specified repository storage move for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param repositoryStorageMoveId ID of the project repository storage move.
     * @return ResponseEntity&lt;APIEntitiesProjectsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectsRepositoryStorageMove> getApiV4ProjectsIdRepositoryStorageMovesRepositoryStorageMoveIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(id, repositoryStorageMoveId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a repository storage move for a project
     * Retrieves a specified repository storage move for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param repositoryStorageMoveId ID of the project repository storage move.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryStorageMovesRepositoryStorageMoveIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(id, repositoryStorageMoveId);
    }

    /**
     * List all snippet repository storage moves
     * Lists all snippet repository storage moves. By default, &#x60;GET&#x60; requests return 20 results at a time because the API results are paginated.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSnippetsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SnippetRepositoryStorageMovesRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesSnippetsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/snippet_repository_storage_moves", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all snippet repository storage moves
     * Lists all snippet repository storage moves. By default, &#x60;GET&#x60; requests return 20 results at a time because the API results are paginated.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSnippetsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSnippetsRepositoryStorageMove> getApiV4SnippetRepositoryStorageMoves(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSnippetsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetRepositoryStorageMovesRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all snippet repository storage moves
     * Lists all snippet repository storage moves. By default, &#x60;GET&#x60; requests return 20 results at a time because the API results are paginated.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesSnippetsRepositoryStorageMove&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSnippetsRepositoryStorageMove>> getApiV4SnippetRepositoryStorageMovesWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSnippetsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetRepositoryStorageMovesRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all snippet repository storage moves
     * Lists all snippet repository storage moves. By default, &#x60;GET&#x60; requests return 20 results at a time because the API results are paginated.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SnippetRepositoryStorageMovesWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4SnippetRepositoryStorageMovesRequestCreation(page, perPage);
    }

    /**
     * Retrieve a snippet repository storage move
     * Retrieves a specified snippet repository storage move.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param repositoryStorageMoveId ID of the snippet repository storage move.
     * @return APIEntitiesSnippetsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SnippetRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(@jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        // verify the required parameter 'repositoryStorageMoveId' is set
        if (repositoryStorageMoveId == null) {
            throw new RestClientResponseException("Missing the required parameter 'repositoryStorageMoveId' when calling getApiV4SnippetRepositoryStorageMovesRepositoryStorageMoveId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("repository_storage_move_id", repositoryStorageMoveId);

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

        ParameterizedTypeReference<APIEntitiesSnippetsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/snippet_repository_storage_moves/{repository_storage_move_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a snippet repository storage move
     * Retrieves a specified snippet repository storage move.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param repositoryStorageMoveId ID of the snippet repository storage move.
     * @return APIEntitiesSnippetsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSnippetsRepositoryStorageMove getApiV4SnippetRepositoryStorageMovesRepositoryStorageMoveId(@jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSnippetsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(repositoryStorageMoveId).body(localVarReturnType);
    }

    /**
     * Retrieve a snippet repository storage move
     * Retrieves a specified snippet repository storage move.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param repositoryStorageMoveId ID of the snippet repository storage move.
     * @return ResponseEntity&lt;APIEntitiesSnippetsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSnippetsRepositoryStorageMove> getApiV4SnippetRepositoryStorageMovesRepositoryStorageMoveIdWithHttpInfo(@jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSnippetsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(repositoryStorageMoveId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a snippet repository storage move
     * Retrieves a specified snippet repository storage move.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param repositoryStorageMoveId ID of the snippet repository storage move.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SnippetRepositoryStorageMovesRepositoryStorageMoveIdWithResponseSpec(@jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        return getApiV4SnippetRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(repositoryStorageMoveId);
    }

    /**
     * List all repository storage moves for a snippet
     * Lists all repository storage moves for a specified snippet. By default, &#x60;GET&#x60; requests return 20 results at a time because the API results are paginated.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSnippetsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SnippetsIdRepositoryStorageMovesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4SnippetsIdRepositoryStorageMoves", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesSnippetsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/snippets/{id}/repository_storage_moves", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all repository storage moves for a snippet
     * Lists all repository storage moves for a specified snippet. By default, &#x60;GET&#x60; requests return 20 results at a time because the API results are paginated.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSnippetsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSnippetsRepositoryStorageMove> getApiV4SnippetsIdRepositoryStorageMoves(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSnippetsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsIdRepositoryStorageMovesRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all repository storage moves for a snippet
     * Lists all repository storage moves for a specified snippet. By default, &#x60;GET&#x60; requests return 20 results at a time because the API results are paginated.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesSnippetsRepositoryStorageMove&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSnippetsRepositoryStorageMove>> getApiV4SnippetsIdRepositoryStorageMovesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSnippetsRepositoryStorageMove>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsIdRepositoryStorageMovesRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all repository storage moves for a snippet
     * Lists all repository storage moves for a specified snippet. By default, &#x60;GET&#x60; requests return 20 results at a time because the API results are paginated.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SnippetsIdRepositoryStorageMovesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4SnippetsIdRepositoryStorageMovesRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a repository storage move for a snippet
     * Retrieves a repository storage move for a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @param repositoryStorageMoveId ID of the snippet repository storage move.
     * @return APIEntitiesSnippetsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SnippetsIdRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4SnippetsIdRepositoryStorageMovesRepositoryStorageMoveId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'repositoryStorageMoveId' is set
        if (repositoryStorageMoveId == null) {
            throw new RestClientResponseException("Missing the required parameter 'repositoryStorageMoveId' when calling getApiV4SnippetsIdRepositoryStorageMovesRepositoryStorageMoveId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("repository_storage_move_id", repositoryStorageMoveId);

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

        ParameterizedTypeReference<APIEntitiesSnippetsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/snippets/{id}/repository_storage_moves/{repository_storage_move_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a repository storage move for a snippet
     * Retrieves a repository storage move for a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @param repositoryStorageMoveId ID of the snippet repository storage move.
     * @return APIEntitiesSnippetsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSnippetsRepositoryStorageMove getApiV4SnippetsIdRepositoryStorageMovesRepositoryStorageMoveId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSnippetsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsIdRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(id, repositoryStorageMoveId).body(localVarReturnType);
    }

    /**
     * Retrieve a repository storage move for a snippet
     * Retrieves a repository storage move for a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @param repositoryStorageMoveId ID of the snippet repository storage move.
     * @return ResponseEntity&lt;APIEntitiesSnippetsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSnippetsRepositoryStorageMove> getApiV4SnippetsIdRepositoryStorageMovesRepositoryStorageMoveIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSnippetsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsIdRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(id, repositoryStorageMoveId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a repository storage move for a snippet
     * Retrieves a repository storage move for a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @param repositoryStorageMoveId ID of the snippet repository storage move.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SnippetsIdRepositoryStorageMovesRepositoryStorageMoveIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer repositoryStorageMoveId) throws RestClientResponseException {
        return getApiV4SnippetsIdRepositoryStorageMovesRepositoryStorageMoveIdRequestCreation(id, repositoryStorageMoveId);
    }

    /**
     * Create group repository storage moves for a storage shard
     * Creates repository storage moves for all groups on a specified storage shard.
     * <p><b>202</b> - 202 Accepted
     * <p><b>400</b> - Bad Request
     * @param postApiV4GroupRepositoryStorageMovesRequest The postApiV4GroupRepositoryStorageMovesRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupRepositoryStorageMovesRequestCreation(@jakarta.annotation.Nonnull PostApiV4GroupRepositoryStorageMovesRequest postApiV4GroupRepositoryStorageMovesRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4GroupRepositoryStorageMovesRequest' is set
        if (postApiV4GroupRepositoryStorageMovesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupRepositoryStorageMovesRequest' when calling postApiV4GroupRepositoryStorageMoves", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/group_repository_storage_moves", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupRepositoryStorageMovesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create group repository storage moves for a storage shard
     * Creates repository storage moves for all groups on a specified storage shard.
     * <p><b>202</b> - 202 Accepted
     * <p><b>400</b> - Bad Request
     * @param postApiV4GroupRepositoryStorageMovesRequest The postApiV4GroupRepositoryStorageMovesRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupRepositoryStorageMoves(@jakarta.annotation.Nonnull PostApiV4GroupRepositoryStorageMovesRequest postApiV4GroupRepositoryStorageMovesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupRepositoryStorageMovesRequestCreation(postApiV4GroupRepositoryStorageMovesRequest).body(localVarReturnType);
    }

    /**
     * Create group repository storage moves for a storage shard
     * Creates repository storage moves for all groups on a specified storage shard.
     * <p><b>202</b> - 202 Accepted
     * <p><b>400</b> - Bad Request
     * @param postApiV4GroupRepositoryStorageMovesRequest The postApiV4GroupRepositoryStorageMovesRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupRepositoryStorageMovesWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4GroupRepositoryStorageMovesRequest postApiV4GroupRepositoryStorageMovesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupRepositoryStorageMovesRequestCreation(postApiV4GroupRepositoryStorageMovesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create group repository storage moves for a storage shard
     * Creates repository storage moves for all groups on a specified storage shard.
     * <p><b>202</b> - 202 Accepted
     * <p><b>400</b> - Bad Request
     * @param postApiV4GroupRepositoryStorageMovesRequest The postApiV4GroupRepositoryStorageMovesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupRepositoryStorageMovesWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4GroupRepositoryStorageMovesRequest postApiV4GroupRepositoryStorageMovesRequest) throws RestClientResponseException {
        return postApiV4GroupRepositoryStorageMovesRequestCreation(postApiV4GroupRepositoryStorageMovesRequest);
    }

    /**
     * Create a group repository storage move
     * Creates a group repository storage move for a specified group. This operation moves only group wiki repositories and does not move repositories for projects in a group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdRepositoryStorageMovesRequest The postApiV4GroupsIdRepositoryStorageMovesRequest parameter
     * @return APIEntitiesGroupsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdRepositoryStorageMovesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdRepositoryStorageMovesRequest postApiV4GroupsIdRepositoryStorageMovesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdRepositoryStorageMoves", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroupsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/repository_storage_moves", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdRepositoryStorageMovesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group repository storage move
     * Creates a group repository storage move for a specified group. This operation moves only group wiki repositories and does not move repositories for projects in a group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdRepositoryStorageMovesRequest The postApiV4GroupsIdRepositoryStorageMovesRequest parameter
     * @return APIEntitiesGroupsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupsRepositoryStorageMove postApiV4GroupsIdRepositoryStorageMoves(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdRepositoryStorageMovesRequest postApiV4GroupsIdRepositoryStorageMovesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdRepositoryStorageMovesRequestCreation(id, postApiV4GroupsIdRepositoryStorageMovesRequest).body(localVarReturnType);
    }

    /**
     * Create a group repository storage move
     * Creates a group repository storage move for a specified group. This operation moves only group wiki repositories and does not move repositories for projects in a group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdRepositoryStorageMovesRequest The postApiV4GroupsIdRepositoryStorageMovesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupsRepositoryStorageMove> postApiV4GroupsIdRepositoryStorageMovesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdRepositoryStorageMovesRequest postApiV4GroupsIdRepositoryStorageMovesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdRepositoryStorageMovesRequestCreation(id, postApiV4GroupsIdRepositoryStorageMovesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a group repository storage move
     * Creates a group repository storage move for a specified group. This operation moves only group wiki repositories and does not move repositories for projects in a group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdRepositoryStorageMovesRequest The postApiV4GroupsIdRepositoryStorageMovesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdRepositoryStorageMovesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdRepositoryStorageMovesRequest postApiV4GroupsIdRepositoryStorageMovesRequest) throws RestClientResponseException {
        return postApiV4GroupsIdRepositoryStorageMovesRequestCreation(id, postApiV4GroupsIdRepositoryStorageMovesRequest);
    }

    /**
     * Create repository storage moves for all projects on a storage shard
     * Creates repository storage moves for each project repository stored on the source storage shard. This endpoint migrates all projects at once.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * @param postApiV4ProjectRepositoryStorageMovesRequest The postApiV4ProjectRepositoryStorageMovesRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectRepositoryStorageMovesRequestCreation(@jakarta.annotation.Nonnull PostApiV4ProjectRepositoryStorageMovesRequest postApiV4ProjectRepositoryStorageMovesRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4ProjectRepositoryStorageMovesRequest' is set
        if (postApiV4ProjectRepositoryStorageMovesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectRepositoryStorageMovesRequest' when calling postApiV4ProjectRepositoryStorageMoves", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/project_repository_storage_moves", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectRepositoryStorageMovesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create repository storage moves for all projects on a storage shard
     * Creates repository storage moves for each project repository stored on the source storage shard. This endpoint migrates all projects at once.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * @param postApiV4ProjectRepositoryStorageMovesRequest The postApiV4ProjectRepositoryStorageMovesRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectRepositoryStorageMoves(@jakarta.annotation.Nonnull PostApiV4ProjectRepositoryStorageMovesRequest postApiV4ProjectRepositoryStorageMovesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectRepositoryStorageMovesRequestCreation(postApiV4ProjectRepositoryStorageMovesRequest).body(localVarReturnType);
    }

    /**
     * Create repository storage moves for all projects on a storage shard
     * Creates repository storage moves for each project repository stored on the source storage shard. This endpoint migrates all projects at once.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * @param postApiV4ProjectRepositoryStorageMovesRequest The postApiV4ProjectRepositoryStorageMovesRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectRepositoryStorageMovesWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4ProjectRepositoryStorageMovesRequest postApiV4ProjectRepositoryStorageMovesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectRepositoryStorageMovesRequestCreation(postApiV4ProjectRepositoryStorageMovesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create repository storage moves for all projects on a storage shard
     * Creates repository storage moves for each project repository stored on the source storage shard. This endpoint migrates all projects at once.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * @param postApiV4ProjectRepositoryStorageMovesRequest The postApiV4ProjectRepositoryStorageMovesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectRepositoryStorageMovesWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4ProjectRepositoryStorageMovesRequest postApiV4ProjectRepositoryStorageMovesRequest) throws RestClientResponseException {
        return postApiV4ProjectRepositoryStorageMovesRequestCreation(postApiV4ProjectRepositoryStorageMovesRequest);
    }

    /**
     * Create a repository storage move for a project
     * Creates a repository storage move for a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryStorageMovesRequest The postApiV4ProjectsIdRepositoryStorageMovesRequest parameter
     * @return APIEntitiesProjectsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRepositoryStorageMovesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdRepositoryStorageMovesRequest postApiV4ProjectsIdRepositoryStorageMovesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRepositoryStorageMoves", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository_storage_moves", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdRepositoryStorageMovesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a repository storage move for a project
     * Creates a repository storage move for a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryStorageMovesRequest The postApiV4ProjectsIdRepositoryStorageMovesRequest parameter
     * @return APIEntitiesProjectsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectsRepositoryStorageMove postApiV4ProjectsIdRepositoryStorageMoves(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdRepositoryStorageMovesRequest postApiV4ProjectsIdRepositoryStorageMovesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryStorageMovesRequestCreation(id, postApiV4ProjectsIdRepositoryStorageMovesRequest).body(localVarReturnType);
    }

    /**
     * Create a repository storage move for a project
     * Creates a repository storage move for a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryStorageMovesRequest The postApiV4ProjectsIdRepositoryStorageMovesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectsRepositoryStorageMove> postApiV4ProjectsIdRepositoryStorageMovesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdRepositoryStorageMovesRequest postApiV4ProjectsIdRepositoryStorageMovesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryStorageMovesRequestCreation(id, postApiV4ProjectsIdRepositoryStorageMovesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a repository storage move for a project
     * Creates a repository storage move for a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryStorageMovesRequest The postApiV4ProjectsIdRepositoryStorageMovesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRepositoryStorageMovesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdRepositoryStorageMovesRequest postApiV4ProjectsIdRepositoryStorageMovesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdRepositoryStorageMovesRequestCreation(id, postApiV4ProjectsIdRepositoryStorageMovesRequest);
    }

    /**
     * Schedule repository storage moves for all snippets on a storage shard
     * Schedules repository storage moves for each snippet repository stored on the source storage shard. This endpoint migrates all snippets at once.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * @param postApiV4SnippetRepositoryStorageMovesRequest The postApiV4SnippetRepositoryStorageMovesRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4SnippetRepositoryStorageMovesRequestCreation(@jakarta.annotation.Nonnull PostApiV4SnippetRepositoryStorageMovesRequest postApiV4SnippetRepositoryStorageMovesRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4SnippetRepositoryStorageMovesRequest' is set
        if (postApiV4SnippetRepositoryStorageMovesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4SnippetRepositoryStorageMovesRequest' when calling postApiV4SnippetRepositoryStorageMoves", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/snippet_repository_storage_moves", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4SnippetRepositoryStorageMovesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Schedule repository storage moves for all snippets on a storage shard
     * Schedules repository storage moves for each snippet repository stored on the source storage shard. This endpoint migrates all snippets at once.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * @param postApiV4SnippetRepositoryStorageMovesRequest The postApiV4SnippetRepositoryStorageMovesRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4SnippetRepositoryStorageMoves(@jakarta.annotation.Nonnull PostApiV4SnippetRepositoryStorageMovesRequest postApiV4SnippetRepositoryStorageMovesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4SnippetRepositoryStorageMovesRequestCreation(postApiV4SnippetRepositoryStorageMovesRequest).body(localVarReturnType);
    }

    /**
     * Schedule repository storage moves for all snippets on a storage shard
     * Schedules repository storage moves for each snippet repository stored on the source storage shard. This endpoint migrates all snippets at once.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * @param postApiV4SnippetRepositoryStorageMovesRequest The postApiV4SnippetRepositoryStorageMovesRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4SnippetRepositoryStorageMovesWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4SnippetRepositoryStorageMovesRequest postApiV4SnippetRepositoryStorageMovesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4SnippetRepositoryStorageMovesRequestCreation(postApiV4SnippetRepositoryStorageMovesRequest).toEntity(localVarReturnType);
    }

    /**
     * Schedule repository storage moves for all snippets on a storage shard
     * Schedules repository storage moves for each snippet repository stored on the source storage shard. This endpoint migrates all snippets at once.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * @param postApiV4SnippetRepositoryStorageMovesRequest The postApiV4SnippetRepositoryStorageMovesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4SnippetRepositoryStorageMovesWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4SnippetRepositoryStorageMovesRequest postApiV4SnippetRepositoryStorageMovesRequest) throws RestClientResponseException {
        return postApiV4SnippetRepositoryStorageMovesRequestCreation(postApiV4SnippetRepositoryStorageMovesRequest);
    }

    /**
     * Schedule a repository storage move for a snippet
     * Schedules a repository storage move for a specified snippet.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @param postApiV4SnippetsIdRepositoryStorageMovesRequest The postApiV4SnippetsIdRepositoryStorageMovesRequest parameter
     * @return APIEntitiesSnippetsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4SnippetsIdRepositoryStorageMovesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4SnippetsIdRepositoryStorageMovesRequest postApiV4SnippetsIdRepositoryStorageMovesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4SnippetsIdRepositoryStorageMoves", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesSnippetsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/snippets/{id}/repository_storage_moves", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4SnippetsIdRepositoryStorageMovesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Schedule a repository storage move for a snippet
     * Schedules a repository storage move for a specified snippet.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @param postApiV4SnippetsIdRepositoryStorageMovesRequest The postApiV4SnippetsIdRepositoryStorageMovesRequest parameter
     * @return APIEntitiesSnippetsRepositoryStorageMove
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSnippetsRepositoryStorageMove postApiV4SnippetsIdRepositoryStorageMoves(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4SnippetsIdRepositoryStorageMovesRequest postApiV4SnippetsIdRepositoryStorageMovesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSnippetsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4SnippetsIdRepositoryStorageMovesRequestCreation(id, postApiV4SnippetsIdRepositoryStorageMovesRequest).body(localVarReturnType);
    }

    /**
     * Schedule a repository storage move for a snippet
     * Schedules a repository storage move for a specified snippet.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @param postApiV4SnippetsIdRepositoryStorageMovesRequest The postApiV4SnippetsIdRepositoryStorageMovesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesSnippetsRepositoryStorageMove&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSnippetsRepositoryStorageMove> postApiV4SnippetsIdRepositoryStorageMovesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4SnippetsIdRepositoryStorageMovesRequest postApiV4SnippetsIdRepositoryStorageMovesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSnippetsRepositoryStorageMove> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4SnippetsIdRepositoryStorageMovesRequestCreation(id, postApiV4SnippetsIdRepositoryStorageMovesRequest).toEntity(localVarReturnType);
    }

    /**
     * Schedule a repository storage move for a snippet
     * Schedules a repository storage move for a specified snippet.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @param postApiV4SnippetsIdRepositoryStorageMovesRequest The postApiV4SnippetsIdRepositoryStorageMovesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4SnippetsIdRepositoryStorageMovesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4SnippetsIdRepositoryStorageMovesRequest postApiV4SnippetsIdRepositoryStorageMovesRequest) throws RestClientResponseException {
        return postApiV4SnippetsIdRepositoryStorageMovesRequestCreation(id, postApiV4SnippetsIdRepositoryStorageMovesRequest);
    }
}
