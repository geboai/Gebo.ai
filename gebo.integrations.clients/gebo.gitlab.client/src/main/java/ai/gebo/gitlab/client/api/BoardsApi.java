package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBoard;
import ai.gebo.gitlab.client.model.APIEntitiesList;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdBoardsBoardIdListsRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdBoardsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdBoardsBoardIdListsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdBoardsRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdBoardsBoardIdListsListIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdBoardsBoardIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdBoardsBoardIdListsListIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdBoardsBoardIdRequest;

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
public class BoardsApi {
    private ApiClient apiClient;

    public BoardsApi() {
        this(new ApiClient());
    }

    public BoardsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a group issue board
     * Deletes a specified group issue board.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition failed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdBoardsBoardIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdBoardsBoardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling deleteApiV4GroupsIdBoardsBoardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);

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

        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/boards/{board_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a group issue board
     * Deletes a specified group issue board.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition failed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBoard deleteApiV4GroupsIdBoardsBoardId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdBoardsBoardIdRequestCreation(id, boardId).body(localVarReturnType);
    }

    /**
     * Delete a group issue board
     * Deletes a specified group issue board.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition failed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @return ResponseEntity&lt;APIEntitiesBoard&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBoard> deleteApiV4GroupsIdBoardsBoardIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdBoardsBoardIdRequestCreation(id, boardId).toEntity(localVarReturnType);
    }

    /**
     * Delete a group issue board
     * Deletes a specified group issue board.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition failed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdBoardsBoardIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        return deleteApiV4GroupsIdBoardsBoardIdRequestCreation(id, boardId);
    }

    /**
     * Delete a group issue board list
     * Deletes a specified group issue board list. Only for administrators and users with the Owner role for the group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdBoardsBoardIdListsListIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling deleteApiV4GroupsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'listId' is set
        if (listId == null) {
            throw new RestClientResponseException("Missing the required parameter 'listId' when calling deleteApiV4GroupsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);
        pathParams.put("list_id", listId);

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

        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/boards/{board_id}/lists/{list_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a group issue board list
     * Deletes a specified group issue board list. Only for administrators and users with the Owner role for the group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesList deleteApiV4GroupsIdBoardsBoardIdListsListId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId).body(localVarReturnType);
    }

    /**
     * Delete a group issue board list
     * Deletes a specified group issue board list. Only for administrators and users with the Owner role for the group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return ResponseEntity&lt;APIEntitiesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesList> deleteApiV4GroupsIdBoardsBoardIdListsListIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId).toEntity(localVarReturnType);
    }

    /**
     * Delete a group issue board list
     * Deletes a specified group issue board list. Only for administrators and users with the Owner role for the group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdBoardsBoardIdListsListIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        return deleteApiV4GroupsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId);
    }

    /**
     * Delete an issue board
     * Deletes a specified issue board in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdBoardsBoardIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdBoardsBoardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling deleteApiV4ProjectsIdBoardsBoardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);

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

        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/boards/{board_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an issue board
     * Deletes a specified issue board in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBoard deleteApiV4ProjectsIdBoardsBoardId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdBoardsBoardIdRequestCreation(id, boardId).body(localVarReturnType);
    }

    /**
     * Delete an issue board
     * Deletes a specified issue board in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @return ResponseEntity&lt;APIEntitiesBoard&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBoard> deleteApiV4ProjectsIdBoardsBoardIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdBoardsBoardIdRequestCreation(id, boardId).toEntity(localVarReturnType);
    }

    /**
     * Delete an issue board
     * Deletes a specified issue board in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdBoardsBoardIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdBoardsBoardIdRequestCreation(id, boardId);
    }

    /**
     * Delete a list from an issue board
     * Deletes a specified list from an issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdBoardsBoardIdListsListIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling deleteApiV4ProjectsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'listId' is set
        if (listId == null) {
            throw new RestClientResponseException("Missing the required parameter 'listId' when calling deleteApiV4ProjectsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);
        pathParams.put("list_id", listId);

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

        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/boards/{board_id}/lists/{list_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a list from an issue board
     * Deletes a specified list from an issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesList deleteApiV4ProjectsIdBoardsBoardIdListsListId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId).body(localVarReturnType);
    }

    /**
     * Delete a list from an issue board
     * Deletes a specified list from an issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return ResponseEntity&lt;APIEntitiesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesList> deleteApiV4ProjectsIdBoardsBoardIdListsListIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId).toEntity(localVarReturnType);
    }

    /**
     * Delete a list from an issue board
     * Deletes a specified list from an issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdBoardsBoardIdListsListIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId);
    }

    /**
     * List all group issue boards in a group
     * Lists all group issue boards for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBoard&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdBoardsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdBoards", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesBoard>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/boards", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group issue boards in a group
     * Lists all group issue boards for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBoard&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBoard> getApiV4GroupsIdBoards(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBoard>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBoardsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all group issue boards in a group
     * Lists all group issue boards for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBoard&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBoard>> getApiV4GroupsIdBoardsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBoard>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBoardsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group issue boards in a group
     * Lists all group issue boards for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdBoardsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdBoardsRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a group issue board
     * Retrieves a specified group issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdBoardsBoardIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdBoardsBoardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling getApiV4GroupsIdBoardsBoardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);

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

        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/boards/{board_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group issue board
     * Retrieves a specified group issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBoard getApiV4GroupsIdBoardsBoardId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBoardsBoardIdRequestCreation(id, boardId).body(localVarReturnType);
    }

    /**
     * Retrieve a group issue board
     * Retrieves a specified group issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @return ResponseEntity&lt;APIEntitiesBoard&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBoard> getApiV4GroupsIdBoardsBoardIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBoardsBoardIdRequestCreation(id, boardId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group issue board
     * Retrieves a specified group issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdBoardsBoardIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        return getApiV4GroupsIdBoardsBoardIdRequestCreation(id, boardId);
    }

    /**
     * List all group issue board lists
     * Lists all group issue board lists for a specified board. Does not include &#x60;open&#x60; and &#x60;closed&#x60; lists.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdBoardsBoardIdListsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdBoardsBoardIdLists", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling getApiV4GroupsIdBoardsBoardIdLists", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);

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

        ParameterizedTypeReference<List<APIEntitiesList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/boards/{board_id}/lists", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group issue board lists
     * Lists all group issue board lists for a specified board. Does not include &#x60;open&#x60; and &#x60;closed&#x60; lists.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesList> getApiV4GroupsIdBoardsBoardIdLists(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBoardsBoardIdListsRequestCreation(id, boardId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all group issue board lists
     * Lists all group issue board lists for a specified board. Does not include &#x60;open&#x60; and &#x60;closed&#x60; lists.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesList&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesList>> getApiV4GroupsIdBoardsBoardIdListsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBoardsBoardIdListsRequestCreation(id, boardId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group issue board lists
     * Lists all group issue board lists for a specified board. Does not include &#x60;open&#x60; and &#x60;closed&#x60; lists.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdBoardsBoardIdListsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdBoardsBoardIdListsRequestCreation(id, boardId, page, perPage);
    }

    /**
     * Retrieve a group issue board list
     * Retrieves a specified group issue board list.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdBoardsBoardIdListsListIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling getApiV4GroupsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'listId' is set
        if (listId == null) {
            throw new RestClientResponseException("Missing the required parameter 'listId' when calling getApiV4GroupsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);
        pathParams.put("list_id", listId);

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

        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/boards/{board_id}/lists/{list_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group issue board list
     * Retrieves a specified group issue board list.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesList getApiV4GroupsIdBoardsBoardIdListsListId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId).body(localVarReturnType);
    }

    /**
     * Retrieve a group issue board list
     * Retrieves a specified group issue board list.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return ResponseEntity&lt;APIEntitiesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesList> getApiV4GroupsIdBoardsBoardIdListsListIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group issue board list
     * Retrieves a specified group issue board list.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdBoardsBoardIdListsListIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        return getApiV4GroupsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId);
    }

    /**
     * List all project issue boards
     * Lists all issue boards in a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBoard&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdBoardsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdBoards", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesBoard>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/boards", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project issue boards
     * Lists all issue boards in a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBoard&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBoard> getApiV4ProjectsIdBoards(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBoard>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdBoardsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project issue boards
     * Lists all issue boards in a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBoard&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBoard>> getApiV4ProjectsIdBoardsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBoard>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdBoardsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project issue boards
     * Lists all issue boards in a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdBoardsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdBoardsRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve an issue board
     * Retrieves a specified issue board in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdBoardsBoardIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdBoardsBoardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling getApiV4ProjectsIdBoardsBoardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);

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

        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/boards/{board_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an issue board
     * Retrieves a specified issue board in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBoard getApiV4ProjectsIdBoardsBoardId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdBoardsBoardIdRequestCreation(id, boardId).body(localVarReturnType);
    }

    /**
     * Retrieve an issue board
     * Retrieves a specified issue board in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @return ResponseEntity&lt;APIEntitiesBoard&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBoard> getApiV4ProjectsIdBoardsBoardIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdBoardsBoardIdRequestCreation(id, boardId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an issue board
     * Retrieves a specified issue board in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdBoardsBoardIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        return getApiV4ProjectsIdBoardsBoardIdRequestCreation(id, boardId);
    }

    /**
     * List all board lists in an issue board
     * Lists all lists in a specified issue board. Does not include &#x60;open&#x60; and &#x60;closed&#x60; lists.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdBoardsBoardIdListsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdBoardsBoardIdLists", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling getApiV4ProjectsIdBoardsBoardIdLists", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);

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

        ParameterizedTypeReference<List<APIEntitiesList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/boards/{board_id}/lists", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all board lists in an issue board
     * Lists all lists in a specified issue board. Does not include &#x60;open&#x60; and &#x60;closed&#x60; lists.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesList> getApiV4ProjectsIdBoardsBoardIdLists(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdBoardsBoardIdListsRequestCreation(id, boardId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all board lists in an issue board
     * Lists all lists in a specified issue board. Does not include &#x60;open&#x60; and &#x60;closed&#x60; lists.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesList&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesList>> getApiV4ProjectsIdBoardsBoardIdListsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdBoardsBoardIdListsRequestCreation(id, boardId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all board lists in an issue board
     * Lists all lists in a specified issue board. Does not include &#x60;open&#x60; and &#x60;closed&#x60; lists.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdBoardsBoardIdListsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdBoardsBoardIdListsRequestCreation(id, boardId, page, perPage);
    }

    /**
     * Retrieve a board list
     * Retrieves a specified list from an issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdBoardsBoardIdListsListIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling getApiV4ProjectsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'listId' is set
        if (listId == null) {
            throw new RestClientResponseException("Missing the required parameter 'listId' when calling getApiV4ProjectsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);
        pathParams.put("list_id", listId);

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

        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/boards/{board_id}/lists/{list_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a board list
     * Retrieves a specified list from an issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesList getApiV4ProjectsIdBoardsBoardIdListsListId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId).body(localVarReturnType);
    }

    /**
     * Retrieve a board list
     * Retrieves a specified list from an issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return ResponseEntity&lt;APIEntitiesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesList> getApiV4ProjectsIdBoardsBoardIdListsListIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a board list
     * Retrieves a specified list from an issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdBoardsBoardIdListsListIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        return getApiV4ProjectsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId);
    }

    /**
     * Create a group issue board
     * Creates a group issue board for a specified group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdBoardsRequest The postApiV4GroupsIdBoardsRequest parameter
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdBoardsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdBoardsRequest postApiV4GroupsIdBoardsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdBoards", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdBoardsRequest' is set
        if (postApiV4GroupsIdBoardsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdBoardsRequest' when calling postApiV4GroupsIdBoards", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/boards", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdBoardsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group issue board
     * Creates a group issue board for a specified group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdBoardsRequest The postApiV4GroupsIdBoardsRequest parameter
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBoard postApiV4GroupsIdBoards(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdBoardsRequest postApiV4GroupsIdBoardsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdBoardsRequestCreation(id, postApiV4GroupsIdBoardsRequest).body(localVarReturnType);
    }

    /**
     * Create a group issue board
     * Creates a group issue board for a specified group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdBoardsRequest The postApiV4GroupsIdBoardsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBoard&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBoard> postApiV4GroupsIdBoardsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdBoardsRequest postApiV4GroupsIdBoardsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdBoardsRequestCreation(id, postApiV4GroupsIdBoardsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a group issue board
     * Creates a group issue board for a specified group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdBoardsRequest The postApiV4GroupsIdBoardsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdBoardsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdBoardsRequest postApiV4GroupsIdBoardsRequest) throws RestClientResponseException {
        return postApiV4GroupsIdBoardsRequestCreation(id, postApiV4GroupsIdBoardsRequest);
    }

    /**
     * Create a group issue board list
     * Creates a group issue board list for a specified board.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param postApiV4GroupsIdBoardsBoardIdListsRequest The postApiV4GroupsIdBoardsBoardIdListsRequest parameter
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdBoardsBoardIdListsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PostApiV4GroupsIdBoardsBoardIdListsRequest postApiV4GroupsIdBoardsBoardIdListsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdBoardsBoardIdLists", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling postApiV4GroupsIdBoardsBoardIdLists", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);

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

        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/boards/{board_id}/lists", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdBoardsBoardIdListsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group issue board list
     * Creates a group issue board list for a specified board.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param postApiV4GroupsIdBoardsBoardIdListsRequest The postApiV4GroupsIdBoardsBoardIdListsRequest parameter
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesList postApiV4GroupsIdBoardsBoardIdLists(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PostApiV4GroupsIdBoardsBoardIdListsRequest postApiV4GroupsIdBoardsBoardIdListsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdBoardsBoardIdListsRequestCreation(id, boardId, postApiV4GroupsIdBoardsBoardIdListsRequest).body(localVarReturnType);
    }

    /**
     * Create a group issue board list
     * Creates a group issue board list for a specified board.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param postApiV4GroupsIdBoardsBoardIdListsRequest The postApiV4GroupsIdBoardsBoardIdListsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesList> postApiV4GroupsIdBoardsBoardIdListsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PostApiV4GroupsIdBoardsBoardIdListsRequest postApiV4GroupsIdBoardsBoardIdListsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdBoardsBoardIdListsRequestCreation(id, boardId, postApiV4GroupsIdBoardsBoardIdListsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a group issue board list
     * Creates a group issue board list for a specified board.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param postApiV4GroupsIdBoardsBoardIdListsRequest The postApiV4GroupsIdBoardsBoardIdListsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdBoardsBoardIdListsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PostApiV4GroupsIdBoardsBoardIdListsRequest postApiV4GroupsIdBoardsBoardIdListsRequest) throws RestClientResponseException {
        return postApiV4GroupsIdBoardsBoardIdListsRequestCreation(id, boardId, postApiV4GroupsIdBoardsBoardIdListsRequest);
    }

    /**
     * Create an issue board
     * Creates an issue board in a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdBoardsRequest The postApiV4ProjectsIdBoardsRequest parameter
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdBoardsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdBoardsRequest postApiV4ProjectsIdBoardsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdBoards", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdBoardsRequest' is set
        if (postApiV4ProjectsIdBoardsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdBoardsRequest' when calling postApiV4ProjectsIdBoards", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/boards", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdBoardsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an issue board
     * Creates an issue board in a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdBoardsRequest The postApiV4ProjectsIdBoardsRequest parameter
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBoard postApiV4ProjectsIdBoards(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdBoardsRequest postApiV4ProjectsIdBoardsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdBoardsRequestCreation(id, postApiV4ProjectsIdBoardsRequest).body(localVarReturnType);
    }

    /**
     * Create an issue board
     * Creates an issue board in a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdBoardsRequest The postApiV4ProjectsIdBoardsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBoard&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBoard> postApiV4ProjectsIdBoardsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdBoardsRequest postApiV4ProjectsIdBoardsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdBoardsRequestCreation(id, postApiV4ProjectsIdBoardsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an issue board
     * Creates an issue board in a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdBoardsRequest The postApiV4ProjectsIdBoardsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdBoardsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdBoardsRequest postApiV4ProjectsIdBoardsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdBoardsRequestCreation(id, postApiV4ProjectsIdBoardsRequest);
    }

    /**
     * Create an issue board list
     * Creates an issue board list.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param postApiV4ProjectsIdBoardsBoardIdListsRequest The postApiV4ProjectsIdBoardsBoardIdListsRequest parameter
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdBoardsBoardIdListsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PostApiV4ProjectsIdBoardsBoardIdListsRequest postApiV4ProjectsIdBoardsBoardIdListsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdBoardsBoardIdLists", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling postApiV4ProjectsIdBoardsBoardIdLists", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);

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

        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/boards/{board_id}/lists", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdBoardsBoardIdListsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an issue board list
     * Creates an issue board list.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param postApiV4ProjectsIdBoardsBoardIdListsRequest The postApiV4ProjectsIdBoardsBoardIdListsRequest parameter
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesList postApiV4ProjectsIdBoardsBoardIdLists(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PostApiV4ProjectsIdBoardsBoardIdListsRequest postApiV4ProjectsIdBoardsBoardIdListsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdBoardsBoardIdListsRequestCreation(id, boardId, postApiV4ProjectsIdBoardsBoardIdListsRequest).body(localVarReturnType);
    }

    /**
     * Create an issue board list
     * Creates an issue board list.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param postApiV4ProjectsIdBoardsBoardIdListsRequest The postApiV4ProjectsIdBoardsBoardIdListsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesList> postApiV4ProjectsIdBoardsBoardIdListsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PostApiV4ProjectsIdBoardsBoardIdListsRequest postApiV4ProjectsIdBoardsBoardIdListsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdBoardsBoardIdListsRequestCreation(id, boardId, postApiV4ProjectsIdBoardsBoardIdListsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an issue board list
     * Creates an issue board list.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param postApiV4ProjectsIdBoardsBoardIdListsRequest The postApiV4ProjectsIdBoardsBoardIdListsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdBoardsBoardIdListsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PostApiV4ProjectsIdBoardsBoardIdListsRequest postApiV4ProjectsIdBoardsBoardIdListsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdBoardsBoardIdListsRequestCreation(id, boardId, postApiV4ProjectsIdBoardsBoardIdListsRequest);
    }

    /**
     * Update a group issue board
     * Updates a specified group issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param putApiV4GroupsIdBoardsBoardIdRequest The putApiV4GroupsIdBoardsBoardIdRequest parameter
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdBoardsBoardIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PutApiV4GroupsIdBoardsBoardIdRequest putApiV4GroupsIdBoardsBoardIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdBoardsBoardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling putApiV4GroupsIdBoardsBoardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);

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

        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/boards/{board_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdBoardsBoardIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a group issue board
     * Updates a specified group issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param putApiV4GroupsIdBoardsBoardIdRequest The putApiV4GroupsIdBoardsBoardIdRequest parameter
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBoard putApiV4GroupsIdBoardsBoardId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PutApiV4GroupsIdBoardsBoardIdRequest putApiV4GroupsIdBoardsBoardIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdBoardsBoardIdRequestCreation(id, boardId, putApiV4GroupsIdBoardsBoardIdRequest).body(localVarReturnType);
    }

    /**
     * Update a group issue board
     * Updates a specified group issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param putApiV4GroupsIdBoardsBoardIdRequest The putApiV4GroupsIdBoardsBoardIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBoard&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBoard> putApiV4GroupsIdBoardsBoardIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PutApiV4GroupsIdBoardsBoardIdRequest putApiV4GroupsIdBoardsBoardIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdBoardsBoardIdRequestCreation(id, boardId, putApiV4GroupsIdBoardsBoardIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a group issue board
     * Updates a specified group issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param putApiV4GroupsIdBoardsBoardIdRequest The putApiV4GroupsIdBoardsBoardIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdBoardsBoardIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PutApiV4GroupsIdBoardsBoardIdRequest putApiV4GroupsIdBoardsBoardIdRequest) throws RestClientResponseException {
        return putApiV4GroupsIdBoardsBoardIdRequestCreation(id, boardId, putApiV4GroupsIdBoardsBoardIdRequest);
    }

    /**
     * Update a group issue board list
     * Updates a specified group issue board list. This call is used to change list position.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @param putApiV4GroupsIdBoardsBoardIdListsListIdRequest The putApiV4GroupsIdBoardsBoardIdListsListIdRequest parameter
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdBoardsBoardIdListsListIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId, @jakarta.annotation.Nonnull PutApiV4GroupsIdBoardsBoardIdListsListIdRequest putApiV4GroupsIdBoardsBoardIdListsListIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling putApiV4GroupsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'listId' is set
        if (listId == null) {
            throw new RestClientResponseException("Missing the required parameter 'listId' when calling putApiV4GroupsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4GroupsIdBoardsBoardIdListsListIdRequest' is set
        if (putApiV4GroupsIdBoardsBoardIdListsListIdRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4GroupsIdBoardsBoardIdListsListIdRequest' when calling putApiV4GroupsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);
        pathParams.put("list_id", listId);

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

        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/boards/{board_id}/lists/{list_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdBoardsBoardIdListsListIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a group issue board list
     * Updates a specified group issue board list. This call is used to change list position.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @param putApiV4GroupsIdBoardsBoardIdListsListIdRequest The putApiV4GroupsIdBoardsBoardIdListsListIdRequest parameter
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesList putApiV4GroupsIdBoardsBoardIdListsListId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId, @jakarta.annotation.Nonnull PutApiV4GroupsIdBoardsBoardIdListsListIdRequest putApiV4GroupsIdBoardsBoardIdListsListIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId, putApiV4GroupsIdBoardsBoardIdListsListIdRequest).body(localVarReturnType);
    }

    /**
     * Update a group issue board list
     * Updates a specified group issue board list. This call is used to change list position.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @param putApiV4GroupsIdBoardsBoardIdListsListIdRequest The putApiV4GroupsIdBoardsBoardIdListsListIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesList> putApiV4GroupsIdBoardsBoardIdListsListIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId, @jakarta.annotation.Nonnull PutApiV4GroupsIdBoardsBoardIdListsListIdRequest putApiV4GroupsIdBoardsBoardIdListsListIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId, putApiV4GroupsIdBoardsBoardIdListsListIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a group issue board list
     * Updates a specified group issue board list. This call is used to change list position.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @param putApiV4GroupsIdBoardsBoardIdListsListIdRequest The putApiV4GroupsIdBoardsBoardIdListsListIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdBoardsBoardIdListsListIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId, @jakarta.annotation.Nonnull PutApiV4GroupsIdBoardsBoardIdListsListIdRequest putApiV4GroupsIdBoardsBoardIdListsListIdRequest) throws RestClientResponseException {
        return putApiV4GroupsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId, putApiV4GroupsIdBoardsBoardIdListsListIdRequest);
    }

    /**
     * Update an issue board
     * Updates a specified issue board in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param putApiV4ProjectsIdBoardsBoardIdRequest The putApiV4ProjectsIdBoardsBoardIdRequest parameter
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdBoardsBoardIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PutApiV4ProjectsIdBoardsBoardIdRequest putApiV4ProjectsIdBoardsBoardIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdBoardsBoardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling putApiV4ProjectsIdBoardsBoardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);

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

        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/boards/{board_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdBoardsBoardIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an issue board
     * Updates a specified issue board in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param putApiV4ProjectsIdBoardsBoardIdRequest The putApiV4ProjectsIdBoardsBoardIdRequest parameter
     * @return APIEntitiesBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBoard putApiV4ProjectsIdBoardsBoardId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PutApiV4ProjectsIdBoardsBoardIdRequest putApiV4ProjectsIdBoardsBoardIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdBoardsBoardIdRequestCreation(id, boardId, putApiV4ProjectsIdBoardsBoardIdRequest).body(localVarReturnType);
    }

    /**
     * Update an issue board
     * Updates a specified issue board in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param putApiV4ProjectsIdBoardsBoardIdRequest The putApiV4ProjectsIdBoardsBoardIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBoard&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBoard> putApiV4ProjectsIdBoardsBoardIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PutApiV4ProjectsIdBoardsBoardIdRequest putApiV4ProjectsIdBoardsBoardIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdBoardsBoardIdRequestCreation(id, boardId, putApiV4ProjectsIdBoardsBoardIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an issue board
     * Updates a specified issue board in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param putApiV4ProjectsIdBoardsBoardIdRequest The putApiV4ProjectsIdBoardsBoardIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdBoardsBoardIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable PutApiV4ProjectsIdBoardsBoardIdRequest putApiV4ProjectsIdBoardsBoardIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdBoardsBoardIdRequestCreation(id, boardId, putApiV4ProjectsIdBoardsBoardIdRequest);
    }

    /**
     * Update position of a board list
     * Updates the position of a specified list from an issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @param putApiV4ProjectsIdBoardsBoardIdListsListIdRequest The putApiV4ProjectsIdBoardsBoardIdListsListIdRequest parameter
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdBoardsBoardIdListsListIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdBoardsBoardIdListsListIdRequest putApiV4ProjectsIdBoardsBoardIdListsListIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling putApiV4ProjectsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'listId' is set
        if (listId == null) {
            throw new RestClientResponseException("Missing the required parameter 'listId' when calling putApiV4ProjectsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4ProjectsIdBoardsBoardIdListsListIdRequest' is set
        if (putApiV4ProjectsIdBoardsBoardIdListsListIdRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ProjectsIdBoardsBoardIdListsListIdRequest' when calling putApiV4ProjectsIdBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);
        pathParams.put("list_id", listId);

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

        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/boards/{board_id}/lists/{list_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdBoardsBoardIdListsListIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update position of a board list
     * Updates the position of a specified list from an issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @param putApiV4ProjectsIdBoardsBoardIdListsListIdRequest The putApiV4ProjectsIdBoardsBoardIdListsListIdRequest parameter
     * @return APIEntitiesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesList putApiV4ProjectsIdBoardsBoardIdListsListId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdBoardsBoardIdListsListIdRequest putApiV4ProjectsIdBoardsBoardIdListsListIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId, putApiV4ProjectsIdBoardsBoardIdListsListIdRequest).body(localVarReturnType);
    }

    /**
     * Update position of a board list
     * Updates the position of a specified list from an issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @param putApiV4ProjectsIdBoardsBoardIdListsListIdRequest The putApiV4ProjectsIdBoardsBoardIdListsListIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesList> putApiV4ProjectsIdBoardsBoardIdListsListIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdBoardsBoardIdListsListIdRequest putApiV4ProjectsIdBoardsBoardIdListsListIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId, putApiV4ProjectsIdBoardsBoardIdListsListIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update position of a board list
     * Updates the position of a specified list from an issue board.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param boardId ID of the board.
     * @param listId ID of the board&#39;s list.
     * @param putApiV4ProjectsIdBoardsBoardIdListsListIdRequest The putApiV4ProjectsIdBoardsBoardIdListsListIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdBoardsBoardIdListsListIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdBoardsBoardIdListsListIdRequest putApiV4ProjectsIdBoardsBoardIdListsListIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdBoardsBoardIdListsListIdRequestCreation(id, boardId, listId, putApiV4ProjectsIdBoardsBoardIdListsListIdRequest);
    }
}
