package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesTodo;

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
public class ToDosApi {
    private ApiClient apiClient;

    public ToDosApi() {
        this(new ApiClient());
    }

    public ToDosApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all to-do items
     * Lists all to-do items. When no filter is applied, it returns all pending to-do items for the current user. Different filters allow the user to refine the request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param action Filter to-do items by action.
     * @param authorId Filter to-do items by author ID.
     * @param projectId Filter to-do items by project ID.
     * @param groupId Filter to-do items by group ID.
     * @param state Filter to-do items by state.
     * @param type Filter to-do items by type.
     * @return List&lt;APIEntitiesTodo&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4TodosRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable Integer projectId, @jakarta.annotation.Nullable Integer groupId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String type) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "action", action));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_id", authorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "project_id", projectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "group_id", groupId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "type", type));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesTodo>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/todos", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all to-do items
     * Lists all to-do items. When no filter is applied, it returns all pending to-do items for the current user. Different filters allow the user to refine the request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param action Filter to-do items by action.
     * @param authorId Filter to-do items by author ID.
     * @param projectId Filter to-do items by project ID.
     * @param groupId Filter to-do items by group ID.
     * @param state Filter to-do items by state.
     * @param type Filter to-do items by type.
     * @return List&lt;APIEntitiesTodo&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesTodo> getApiV4Todos(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable Integer projectId, @jakarta.annotation.Nullable Integer groupId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String type) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTodo>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TodosRequestCreation(page, perPage, action, authorId, projectId, groupId, state, type).body(localVarReturnType);
    }

    /**
     * List all to-do items
     * Lists all to-do items. When no filter is applied, it returns all pending to-do items for the current user. Different filters allow the user to refine the request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param action Filter to-do items by action.
     * @param authorId Filter to-do items by author ID.
     * @param projectId Filter to-do items by project ID.
     * @param groupId Filter to-do items by group ID.
     * @param state Filter to-do items by state.
     * @param type Filter to-do items by type.
     * @return ResponseEntity&lt;List&lt;APIEntitiesTodo&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesTodo>> getApiV4TodosWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable Integer projectId, @jakarta.annotation.Nullable Integer groupId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String type) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTodo>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TodosRequestCreation(page, perPage, action, authorId, projectId, groupId, state, type).toEntity(localVarReturnType);
    }

    /**
     * List all to-do items
     * Lists all to-do items. When no filter is applied, it returns all pending to-do items for the current user. Different filters allow the user to refine the request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param action Filter to-do items by action.
     * @param authorId Filter to-do items by author ID.
     * @param projectId Filter to-do items by project ID.
     * @param groupId Filter to-do items by group ID.
     * @param state Filter to-do items by state.
     * @param type Filter to-do items by type.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4TodosWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String action, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable Integer projectId, @jakarta.annotation.Nullable Integer groupId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String type) throws RestClientResponseException {
        return getApiV4TodosRequestCreation(page, perPage, action, authorId, projectId, groupId, state, type);
    }

    /**
     * Create a to-do item for an issuable
     * Creates a to-do item for the current user on a specified issuable. If a to-do item already exists for the user on that issuable, returns a &#x60;304&#x60; status code.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @return APIEntitiesTodo
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdIssuesIssueIidTodoRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdIssuesIssueIidTodo", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling postApiV4ProjectsIdIssuesIssueIidTodo", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("issue_iid", issueIid);

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

        ParameterizedTypeReference<APIEntitiesTodo> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/todo", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a to-do item for an issuable
     * Creates a to-do item for the current user on a specified issuable. If a to-do item already exists for the user on that issuable, returns a &#x60;304&#x60; status code.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @return APIEntitiesTodo
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTodo postApiV4ProjectsIdIssuesIssueIidTodo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTodo> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidTodoRequestCreation(id, issueIid).body(localVarReturnType);
    }

    /**
     * Create a to-do item for an issuable
     * Creates a to-do item for the current user on a specified issuable. If a to-do item already exists for the user on that issuable, returns a &#x60;304&#x60; status code.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @return ResponseEntity&lt;APIEntitiesTodo&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTodo> postApiV4ProjectsIdIssuesIssueIidTodoWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTodo> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidTodoRequestCreation(id, issueIid).toEntity(localVarReturnType);
    }

    /**
     * Create a to-do item for an issuable
     * Creates a to-do item for the current user on a specified issuable. If a to-do item already exists for the user on that issuable, returns a &#x60;304&#x60; status code.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdIssuesIssueIidTodoWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        return postApiV4ProjectsIdIssuesIssueIidTodoRequestCreation(id, issueIid);
    }

    /**
     * Create a to-do item for an issuable
     * Creates a to-do item for the current user on a specified issuable. If a to-do item already exists for the user on that issuable, returns a &#x60;304&#x60; status code.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of an issuable
     * @return APIEntitiesTodo
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidTodoRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidTodo", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidTodo", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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

        ParameterizedTypeReference<APIEntitiesTodo> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/todo", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a to-do item for an issuable
     * Creates a to-do item for the current user on a specified issuable. If a to-do item already exists for the user on that issuable, returns a &#x60;304&#x60; status code.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of an issuable
     * @return APIEntitiesTodo
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTodo postApiV4ProjectsIdMergeRequestsMergeRequestIidTodo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTodo> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidTodoRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Create a to-do item for an issuable
     * Creates a to-do item for the current user on a specified issuable. If a to-do item already exists for the user on that issuable, returns a &#x60;304&#x60; status code.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of an issuable
     * @return ResponseEntity&lt;APIEntitiesTodo&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTodo> postApiV4ProjectsIdMergeRequestsMergeRequestIidTodoWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTodo> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidTodoRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Create a to-do item for an issuable
     * Creates a to-do item for the current user on a specified issuable. If a to-do item already exists for the user on that issuable, returns a &#x60;304&#x60; status code.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of an issuable
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidTodoWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidTodoRequestCreation(id, mergeRequestIid);
    }

    /**
     * Mark a to-do item as done
     * Marks a to-do item for the current user as done.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the to-do item.
     * @return APIEntitiesTodo
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4TodosIdMarkAsDoneRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4TodosIdMarkAsDone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesTodo> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/todos/{id}/mark_as_done", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Mark a to-do item as done
     * Marks a to-do item for the current user as done.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the to-do item.
     * @return APIEntitiesTodo
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTodo postApiV4TodosIdMarkAsDone(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTodo> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4TodosIdMarkAsDoneRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Mark a to-do item as done
     * Marks a to-do item for the current user as done.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the to-do item.
     * @return ResponseEntity&lt;APIEntitiesTodo&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTodo> postApiV4TodosIdMarkAsDoneWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTodo> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4TodosIdMarkAsDoneRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Mark a to-do item as done
     * Marks a to-do item for the current user as done.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the to-do item.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4TodosIdMarkAsDoneWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4TodosIdMarkAsDoneRequestCreation(id);
    }

    /**
     * Mark all to-do items as done
     * Marks all pending to-do items for the current user as done. It returns the HTTP status code &#x60;204&#x60; with an empty response.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4TodosMarkAsDoneRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/todos/mark_as_done", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Mark all to-do items as done
     * Marks all pending to-do items for the current user as done. It returns the HTTP status code &#x60;204&#x60; with an empty response.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4TodosMarkAsDone() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4TodosMarkAsDoneRequestCreation().body(localVarReturnType);
    }

    /**
     * Mark all to-do items as done
     * Marks all pending to-do items for the current user as done. It returns the HTTP status code &#x60;204&#x60; with an empty response.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4TodosMarkAsDoneWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4TodosMarkAsDoneRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Mark all to-do items as done
     * Marks all pending to-do items for the current user as done. It returns the HTTP status code &#x60;204&#x60; with an empty response.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4TodosMarkAsDoneWithResponseSpec() throws RestClientResponseException {
        return postApiV4TodosMarkAsDoneRequestCreation();
    }
}
