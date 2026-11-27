package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAiActiveContextCodeEnabledNamespace;
import ai.gebo.gitlab.client.model.APIEntitiesAiActiveContextCollection;
import ai.gebo.gitlab.client.model.APIEntitiesAiActiveContextConnection;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4AdminActiveContextDeadQueueReplayRequest;
import ai.gebo.gitlab.client.model.PutApiV4AdminActiveContextCodeEnabledNamespacesRequest;
import ai.gebo.gitlab.client.model.PutApiV4AdminActiveContextCollectionsIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4AdminActiveContextConnectionsActivateRequest;
import ai.gebo.gitlab.client.model.PutApiV4AdminActiveContextConnectionsDeactivateRequest;

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
public class ActiveContextApi {
    private ApiClient apiClient;

    public ActiveContextApi() {
        this(new ApiClient());
    }

    public ActiveContextApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Clear the ActiveContext dead queue
     * Removes all items from the dead queue and returns the number of items cleared.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4AdminActiveContextDeadQueueRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/admin/active_context/dead_queue", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Clear the ActiveContext dead queue
     * Removes all items from the dead queue and returns the number of items cleared.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4AdminActiveContextDeadQueue() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4AdminActiveContextDeadQueueRequestCreation().body(localVarReturnType);
    }

    /**
     * Clear the ActiveContext dead queue
     * Removes all items from the dead queue and returns the number of items cleared.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4AdminActiveContextDeadQueueWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4AdminActiveContextDeadQueueRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Clear the ActiveContext dead queue
     * Removes all items from the dead queue and returns the number of items cleared.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4AdminActiveContextDeadQueueWithResponseSpec() throws RestClientResponseException {
        return deleteApiV4AdminActiveContextDeadQueueRequestCreation();
    }

    /**
     * Get all ActiveContext connections
     * Returns every ActiveContext connection configured for this instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @return List&lt;APIEntitiesAiActiveContextConnection&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminActiveContextConnectionsRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<List<APIEntitiesAiActiveContextConnection>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/active_context/connections", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get all ActiveContext connections
     * Returns every ActiveContext connection configured for this instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @return List&lt;APIEntitiesAiActiveContextConnection&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAiActiveContextConnection> getApiV4AdminActiveContextConnections() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAiActiveContextConnection>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminActiveContextConnectionsRequestCreation().body(localVarReturnType);
    }

    /**
     * Get all ActiveContext connections
     * Returns every ActiveContext connection configured for this instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @return ResponseEntity&lt;List&lt;APIEntitiesAiActiveContextConnection&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAiActiveContextConnection>> getApiV4AdminActiveContextConnectionsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAiActiveContextConnection>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminActiveContextConnectionsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get all ActiveContext connections
     * Returns every ActiveContext connection configured for this instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminActiveContextConnectionsWithResponseSpec() throws RestClientResponseException {
        return getApiV4AdminActiveContextConnectionsRequestCreation();
    }

    /**
     * Replay items from the dead queue into another queue
     * Enqueues a background task that replays dead queue items into the specified target queue.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4AdminActiveContextDeadQueueReplayRequest The postApiV4AdminActiveContextDeadQueueReplayRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AdminActiveContextDeadQueueReplayRequestCreation(@jakarta.annotation.Nonnull PostApiV4AdminActiveContextDeadQueueReplayRequest postApiV4AdminActiveContextDeadQueueReplayRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4AdminActiveContextDeadQueueReplayRequest' is set
        if (postApiV4AdminActiveContextDeadQueueReplayRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4AdminActiveContextDeadQueueReplayRequest' when calling postApiV4AdminActiveContextDeadQueueReplay", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/admin/active_context/dead_queue/replay", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AdminActiveContextDeadQueueReplayRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Replay items from the dead queue into another queue
     * Enqueues a background task that replays dead queue items into the specified target queue.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4AdminActiveContextDeadQueueReplayRequest The postApiV4AdminActiveContextDeadQueueReplayRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AdminActiveContextDeadQueueReplay(@jakarta.annotation.Nonnull PostApiV4AdminActiveContextDeadQueueReplayRequest postApiV4AdminActiveContextDeadQueueReplayRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AdminActiveContextDeadQueueReplayRequestCreation(postApiV4AdminActiveContextDeadQueueReplayRequest).body(localVarReturnType);
    }

    /**
     * Replay items from the dead queue into another queue
     * Enqueues a background task that replays dead queue items into the specified target queue.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4AdminActiveContextDeadQueueReplayRequest The postApiV4AdminActiveContextDeadQueueReplayRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AdminActiveContextDeadQueueReplayWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4AdminActiveContextDeadQueueReplayRequest postApiV4AdminActiveContextDeadQueueReplayRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AdminActiveContextDeadQueueReplayRequestCreation(postApiV4AdminActiveContextDeadQueueReplayRequest).toEntity(localVarReturnType);
    }

    /**
     * Replay items from the dead queue into another queue
     * Enqueues a background task that replays dead queue items into the specified target queue.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4AdminActiveContextDeadQueueReplayRequest The postApiV4AdminActiveContextDeadQueueReplayRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AdminActiveContextDeadQueueReplayWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4AdminActiveContextDeadQueueReplayRequest postApiV4AdminActiveContextDeadQueueReplayRequest) throws RestClientResponseException {
        return postApiV4AdminActiveContextDeadQueueReplayRequestCreation(postApiV4AdminActiveContextDeadQueueReplayRequest);
    }

    /**
     * Update enabled namespace state
     * Transitions the indexing state of an enabled namespace to pending or ready.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param putApiV4AdminActiveContextCodeEnabledNamespacesRequest The putApiV4AdminActiveContextCodeEnabledNamespacesRequest parameter
     * @return APIEntitiesAiActiveContextCodeEnabledNamespace
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminActiveContextCodeEnabledNamespacesRequestCreation(@jakarta.annotation.Nonnull PutApiV4AdminActiveContextCodeEnabledNamespacesRequest putApiV4AdminActiveContextCodeEnabledNamespacesRequest) throws RestClientResponseException {
        // verify the required parameter 'putApiV4AdminActiveContextCodeEnabledNamespacesRequest' is set
        if (putApiV4AdminActiveContextCodeEnabledNamespacesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4AdminActiveContextCodeEnabledNamespacesRequest' when calling putApiV4AdminActiveContextCodeEnabledNamespaces", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesAiActiveContextCodeEnabledNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/active_context/code/enabled_namespaces", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4AdminActiveContextCodeEnabledNamespacesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update enabled namespace state
     * Transitions the indexing state of an enabled namespace to pending or ready.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param putApiV4AdminActiveContextCodeEnabledNamespacesRequest The putApiV4AdminActiveContextCodeEnabledNamespacesRequest parameter
     * @return APIEntitiesAiActiveContextCodeEnabledNamespace
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiActiveContextCodeEnabledNamespace putApiV4AdminActiveContextCodeEnabledNamespaces(@jakarta.annotation.Nonnull PutApiV4AdminActiveContextCodeEnabledNamespacesRequest putApiV4AdminActiveContextCodeEnabledNamespacesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiActiveContextCodeEnabledNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminActiveContextCodeEnabledNamespacesRequestCreation(putApiV4AdminActiveContextCodeEnabledNamespacesRequest).body(localVarReturnType);
    }

    /**
     * Update enabled namespace state
     * Transitions the indexing state of an enabled namespace to pending or ready.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param putApiV4AdminActiveContextCodeEnabledNamespacesRequest The putApiV4AdminActiveContextCodeEnabledNamespacesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAiActiveContextCodeEnabledNamespace&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiActiveContextCodeEnabledNamespace> putApiV4AdminActiveContextCodeEnabledNamespacesWithHttpInfo(@jakarta.annotation.Nonnull PutApiV4AdminActiveContextCodeEnabledNamespacesRequest putApiV4AdminActiveContextCodeEnabledNamespacesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiActiveContextCodeEnabledNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminActiveContextCodeEnabledNamespacesRequestCreation(putApiV4AdminActiveContextCodeEnabledNamespacesRequest).toEntity(localVarReturnType);
    }

    /**
     * Update enabled namespace state
     * Transitions the indexing state of an enabled namespace to pending or ready.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param putApiV4AdminActiveContextCodeEnabledNamespacesRequest The putApiV4AdminActiveContextCodeEnabledNamespacesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminActiveContextCodeEnabledNamespacesWithResponseSpec(@jakarta.annotation.Nonnull PutApiV4AdminActiveContextCodeEnabledNamespacesRequest putApiV4AdminActiveContextCodeEnabledNamespacesRequest) throws RestClientResponseException {
        return putApiV4AdminActiveContextCodeEnabledNamespacesRequestCreation(putApiV4AdminActiveContextCodeEnabledNamespacesRequest);
    }

    /**
     * Update collection options
     * Updates the queue shard count and/or queue shard limit for the specified collection.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - 422 Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id Name or ID of the collection.
     * @param putApiV4AdminActiveContextCollectionsIdRequest The putApiV4AdminActiveContextCollectionsIdRequest parameter
     * @return APIEntitiesAiActiveContextCollection
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminActiveContextCollectionsIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4AdminActiveContextCollectionsIdRequest putApiV4AdminActiveContextCollectionsIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4AdminActiveContextCollectionsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesAiActiveContextCollection> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/active_context/collections/{id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4AdminActiveContextCollectionsIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update collection options
     * Updates the queue shard count and/or queue shard limit for the specified collection.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - 422 Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id Name or ID of the collection.
     * @param putApiV4AdminActiveContextCollectionsIdRequest The putApiV4AdminActiveContextCollectionsIdRequest parameter
     * @return APIEntitiesAiActiveContextCollection
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiActiveContextCollection putApiV4AdminActiveContextCollectionsId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4AdminActiveContextCollectionsIdRequest putApiV4AdminActiveContextCollectionsIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiActiveContextCollection> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminActiveContextCollectionsIdRequestCreation(id, putApiV4AdminActiveContextCollectionsIdRequest).body(localVarReturnType);
    }

    /**
     * Update collection options
     * Updates the queue shard count and/or queue shard limit for the specified collection.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - 422 Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id Name or ID of the collection.
     * @param putApiV4AdminActiveContextCollectionsIdRequest The putApiV4AdminActiveContextCollectionsIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAiActiveContextCollection&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiActiveContextCollection> putApiV4AdminActiveContextCollectionsIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4AdminActiveContextCollectionsIdRequest putApiV4AdminActiveContextCollectionsIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiActiveContextCollection> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminActiveContextCollectionsIdRequestCreation(id, putApiV4AdminActiveContextCollectionsIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update collection options
     * Updates the queue shard count and/or queue shard limit for the specified collection.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - 422 Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id Name or ID of the collection.
     * @param putApiV4AdminActiveContextCollectionsIdRequest The putApiV4AdminActiveContextCollectionsIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminActiveContextCollectionsIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4AdminActiveContextCollectionsIdRequest putApiV4AdminActiveContextCollectionsIdRequest) throws RestClientResponseException {
        return putApiV4AdminActiveContextCollectionsIdRequestCreation(id, putApiV4AdminActiveContextCollectionsIdRequest);
    }

    /**
     * Activate an ActiveContext connection
     * Activates the specified connection and deactivates the previously active connection, if any.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param putApiV4AdminActiveContextConnectionsActivateRequest The putApiV4AdminActiveContextConnectionsActivateRequest parameter
     * @return APIEntitiesAiActiveContextConnection
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminActiveContextConnectionsActivateRequestCreation(@jakarta.annotation.Nonnull PutApiV4AdminActiveContextConnectionsActivateRequest putApiV4AdminActiveContextConnectionsActivateRequest) throws RestClientResponseException {
        // verify the required parameter 'putApiV4AdminActiveContextConnectionsActivateRequest' is set
        if (putApiV4AdminActiveContextConnectionsActivateRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4AdminActiveContextConnectionsActivateRequest' when calling putApiV4AdminActiveContextConnectionsActivate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesAiActiveContextConnection> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/active_context/connections/activate", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4AdminActiveContextConnectionsActivateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Activate an ActiveContext connection
     * Activates the specified connection and deactivates the previously active connection, if any.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param putApiV4AdminActiveContextConnectionsActivateRequest The putApiV4AdminActiveContextConnectionsActivateRequest parameter
     * @return APIEntitiesAiActiveContextConnection
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiActiveContextConnection putApiV4AdminActiveContextConnectionsActivate(@jakarta.annotation.Nonnull PutApiV4AdminActiveContextConnectionsActivateRequest putApiV4AdminActiveContextConnectionsActivateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiActiveContextConnection> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminActiveContextConnectionsActivateRequestCreation(putApiV4AdminActiveContextConnectionsActivateRequest).body(localVarReturnType);
    }

    /**
     * Activate an ActiveContext connection
     * Activates the specified connection and deactivates the previously active connection, if any.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param putApiV4AdminActiveContextConnectionsActivateRequest The putApiV4AdminActiveContextConnectionsActivateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAiActiveContextConnection&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiActiveContextConnection> putApiV4AdminActiveContextConnectionsActivateWithHttpInfo(@jakarta.annotation.Nonnull PutApiV4AdminActiveContextConnectionsActivateRequest putApiV4AdminActiveContextConnectionsActivateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiActiveContextConnection> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminActiveContextConnectionsActivateRequestCreation(putApiV4AdminActiveContextConnectionsActivateRequest).toEntity(localVarReturnType);
    }

    /**
     * Activate an ActiveContext connection
     * Activates the specified connection and deactivates the previously active connection, if any.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param putApiV4AdminActiveContextConnectionsActivateRequest The putApiV4AdminActiveContextConnectionsActivateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminActiveContextConnectionsActivateWithResponseSpec(@jakarta.annotation.Nonnull PutApiV4AdminActiveContextConnectionsActivateRequest putApiV4AdminActiveContextConnectionsActivateRequest) throws RestClientResponseException {
        return putApiV4AdminActiveContextConnectionsActivateRequestCreation(putApiV4AdminActiveContextConnectionsActivateRequest);
    }

    /**
     * Deactivate an ActiveContext connection
     * Deactivates the specified connection. This asynchronously drops indexed data for the connection and deletes the connection record.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param putApiV4AdminActiveContextConnectionsDeactivateRequest The putApiV4AdminActiveContextConnectionsDeactivateRequest parameter
     * @return APIEntitiesAiActiveContextConnection
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminActiveContextConnectionsDeactivateRequestCreation(@jakarta.annotation.Nullable PutApiV4AdminActiveContextConnectionsDeactivateRequest putApiV4AdminActiveContextConnectionsDeactivateRequest) throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesAiActiveContextConnection> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/active_context/connections/deactivate", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4AdminActiveContextConnectionsDeactivateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Deactivate an ActiveContext connection
     * Deactivates the specified connection. This asynchronously drops indexed data for the connection and deletes the connection record.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param putApiV4AdminActiveContextConnectionsDeactivateRequest The putApiV4AdminActiveContextConnectionsDeactivateRequest parameter
     * @return APIEntitiesAiActiveContextConnection
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiActiveContextConnection putApiV4AdminActiveContextConnectionsDeactivate(@jakarta.annotation.Nullable PutApiV4AdminActiveContextConnectionsDeactivateRequest putApiV4AdminActiveContextConnectionsDeactivateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiActiveContextConnection> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminActiveContextConnectionsDeactivateRequestCreation(putApiV4AdminActiveContextConnectionsDeactivateRequest).body(localVarReturnType);
    }

    /**
     * Deactivate an ActiveContext connection
     * Deactivates the specified connection. This asynchronously drops indexed data for the connection and deletes the connection record.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param putApiV4AdminActiveContextConnectionsDeactivateRequest The putApiV4AdminActiveContextConnectionsDeactivateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAiActiveContextConnection&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiActiveContextConnection> putApiV4AdminActiveContextConnectionsDeactivateWithHttpInfo(@jakarta.annotation.Nullable PutApiV4AdminActiveContextConnectionsDeactivateRequest putApiV4AdminActiveContextConnectionsDeactivateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiActiveContextConnection> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminActiveContextConnectionsDeactivateRequestCreation(putApiV4AdminActiveContextConnectionsDeactivateRequest).toEntity(localVarReturnType);
    }

    /**
     * Deactivate an ActiveContext connection
     * Deactivates the specified connection. This asynchronously drops indexed data for the connection and deletes the connection record.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param putApiV4AdminActiveContextConnectionsDeactivateRequest The putApiV4AdminActiveContextConnectionsDeactivateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminActiveContextConnectionsDeactivateWithResponseSpec(@jakarta.annotation.Nullable PutApiV4AdminActiveContextConnectionsDeactivateRequest putApiV4AdminActiveContextConnectionsDeactivateRequest) throws RestClientResponseException {
        return putApiV4AdminActiveContextConnectionsDeactivateRequestCreation(putApiV4AdminActiveContextConnectionsDeactivateRequest);
    }
}
