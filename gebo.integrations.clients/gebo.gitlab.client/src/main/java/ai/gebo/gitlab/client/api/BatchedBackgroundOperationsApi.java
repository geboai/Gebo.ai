package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBatchedBackgroundOperation;
import ai.gebo.gitlab.client.model.PutApiV4AdminBatchedBackgroundOperationsIdRestartRequest;
import ai.gebo.gitlab.client.model.PutApiV4AdminBatchedBackgroundOperationsIdStopRequest;

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
public class BatchedBackgroundOperationsApi {
    private ApiClient apiClient;

    public BatchedBackgroundOperationsApi() {
        this(new ApiClient());
    }

    public BatchedBackgroundOperationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get the list of batched background operations
     * This feature was introduced in GitLab 19.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param database Name of the database.
     * @param jobClassName Filter operations by job class name.
     * @return List&lt;APIEntitiesBatchedBackgroundOperation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminBatchedBackgroundOperationsRequestCreation(@jakarta.annotation.Nullable String database, @jakarta.annotation.Nullable String jobClassName) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "database", database));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "job_class_name", jobClassName));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesBatchedBackgroundOperation>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/batched_background_operations", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get the list of batched background operations
     * This feature was introduced in GitLab 19.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param database Name of the database.
     * @param jobClassName Filter operations by job class name.
     * @return List&lt;APIEntitiesBatchedBackgroundOperation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBatchedBackgroundOperation> getApiV4AdminBatchedBackgroundOperations(@jakarta.annotation.Nullable String database, @jakarta.annotation.Nullable String jobClassName) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBatchedBackgroundOperation>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminBatchedBackgroundOperationsRequestCreation(database, jobClassName).body(localVarReturnType);
    }

    /**
     * Get the list of batched background operations
     * This feature was introduced in GitLab 19.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param database Name of the database.
     * @param jobClassName Filter operations by job class name.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBatchedBackgroundOperation&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBatchedBackgroundOperation>> getApiV4AdminBatchedBackgroundOperationsWithHttpInfo(@jakarta.annotation.Nullable String database, @jakarta.annotation.Nullable String jobClassName) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBatchedBackgroundOperation>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminBatchedBackgroundOperationsRequestCreation(database, jobClassName).toEntity(localVarReturnType);
    }

    /**
     * Get the list of batched background operations
     * This feature was introduced in GitLab 19.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param database Name of the database.
     * @param jobClassName Filter operations by job class name.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminBatchedBackgroundOperationsWithResponseSpec(@jakarta.annotation.Nullable String database, @jakarta.annotation.Nullable String jobClassName) throws RestClientResponseException {
        return getApiV4AdminBatchedBackgroundOperationsRequestCreation(database, jobClassName);
    }

    /**
     * Retrieve a batched background operation
     * This feature was introduced in GitLab 19.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background operation.
     * @param database Name of the database.
     * @return APIEntitiesBatchedBackgroundOperation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminBatchedBackgroundOperationsIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String database) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4AdminBatchedBackgroundOperationsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "database", database));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesBatchedBackgroundOperation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/batched_background_operations/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a batched background operation
     * This feature was introduced in GitLab 19.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background operation.
     * @param database Name of the database.
     * @return APIEntitiesBatchedBackgroundOperation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBatchedBackgroundOperation getApiV4AdminBatchedBackgroundOperationsId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String database) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBatchedBackgroundOperation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminBatchedBackgroundOperationsIdRequestCreation(id, database).body(localVarReturnType);
    }

    /**
     * Retrieve a batched background operation
     * This feature was introduced in GitLab 19.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background operation.
     * @param database Name of the database.
     * @return ResponseEntity&lt;APIEntitiesBatchedBackgroundOperation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBatchedBackgroundOperation> getApiV4AdminBatchedBackgroundOperationsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String database) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBatchedBackgroundOperation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminBatchedBackgroundOperationsIdRequestCreation(id, database).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a batched background operation
     * This feature was introduced in GitLab 19.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background operation.
     * @param database Name of the database.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminBatchedBackgroundOperationsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String database) throws RestClientResponseException {
        return getApiV4AdminBatchedBackgroundOperationsIdRequestCreation(id, database);
    }

    /**
     * Restart a batched background operation
     * This feature was introduced in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can restart only &#x60;stopped&#x60; operations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background operation.
     * @param putApiV4AdminBatchedBackgroundOperationsIdRestartRequest The putApiV4AdminBatchedBackgroundOperationsIdRestartRequest parameter
     * @return APIEntitiesBatchedBackgroundOperation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminBatchedBackgroundOperationsIdRestartRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundOperationsIdRestartRequest putApiV4AdminBatchedBackgroundOperationsIdRestartRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4AdminBatchedBackgroundOperationsIdRestart", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesBatchedBackgroundOperation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/batched_background_operations/{id}/restart", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4AdminBatchedBackgroundOperationsIdRestartRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Restart a batched background operation
     * This feature was introduced in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can restart only &#x60;stopped&#x60; operations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background operation.
     * @param putApiV4AdminBatchedBackgroundOperationsIdRestartRequest The putApiV4AdminBatchedBackgroundOperationsIdRestartRequest parameter
     * @return APIEntitiesBatchedBackgroundOperation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBatchedBackgroundOperation putApiV4AdminBatchedBackgroundOperationsIdRestart(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundOperationsIdRestartRequest putApiV4AdminBatchedBackgroundOperationsIdRestartRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBatchedBackgroundOperation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminBatchedBackgroundOperationsIdRestartRequestCreation(id, putApiV4AdminBatchedBackgroundOperationsIdRestartRequest).body(localVarReturnType);
    }

    /**
     * Restart a batched background operation
     * This feature was introduced in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can restart only &#x60;stopped&#x60; operations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background operation.
     * @param putApiV4AdminBatchedBackgroundOperationsIdRestartRequest The putApiV4AdminBatchedBackgroundOperationsIdRestartRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBatchedBackgroundOperation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBatchedBackgroundOperation> putApiV4AdminBatchedBackgroundOperationsIdRestartWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundOperationsIdRestartRequest putApiV4AdminBatchedBackgroundOperationsIdRestartRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBatchedBackgroundOperation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminBatchedBackgroundOperationsIdRestartRequestCreation(id, putApiV4AdminBatchedBackgroundOperationsIdRestartRequest).toEntity(localVarReturnType);
    }

    /**
     * Restart a batched background operation
     * This feature was introduced in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can restart only &#x60;stopped&#x60; operations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background operation.
     * @param putApiV4AdminBatchedBackgroundOperationsIdRestartRequest The putApiV4AdminBatchedBackgroundOperationsIdRestartRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminBatchedBackgroundOperationsIdRestartWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundOperationsIdRestartRequest putApiV4AdminBatchedBackgroundOperationsIdRestartRequest) throws RestClientResponseException {
        return putApiV4AdminBatchedBackgroundOperationsIdRestartRequestCreation(id, putApiV4AdminBatchedBackgroundOperationsIdRestartRequest);
    }

    /**
     * Stop a batched background operation
     * This feature was introduced in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can stop only &#x60;queued&#x60;, &#x60;active&#x60; or &#x60;paused&#x60; operations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background operation.
     * @param putApiV4AdminBatchedBackgroundOperationsIdStopRequest The putApiV4AdminBatchedBackgroundOperationsIdStopRequest parameter
     * @return APIEntitiesBatchedBackgroundOperation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminBatchedBackgroundOperationsIdStopRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundOperationsIdStopRequest putApiV4AdminBatchedBackgroundOperationsIdStopRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4AdminBatchedBackgroundOperationsIdStop", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesBatchedBackgroundOperation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/batched_background_operations/{id}/stop", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4AdminBatchedBackgroundOperationsIdStopRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Stop a batched background operation
     * This feature was introduced in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can stop only &#x60;queued&#x60;, &#x60;active&#x60; or &#x60;paused&#x60; operations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background operation.
     * @param putApiV4AdminBatchedBackgroundOperationsIdStopRequest The putApiV4AdminBatchedBackgroundOperationsIdStopRequest parameter
     * @return APIEntitiesBatchedBackgroundOperation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBatchedBackgroundOperation putApiV4AdminBatchedBackgroundOperationsIdStop(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundOperationsIdStopRequest putApiV4AdminBatchedBackgroundOperationsIdStopRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBatchedBackgroundOperation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminBatchedBackgroundOperationsIdStopRequestCreation(id, putApiV4AdminBatchedBackgroundOperationsIdStopRequest).body(localVarReturnType);
    }

    /**
     * Stop a batched background operation
     * This feature was introduced in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can stop only &#x60;queued&#x60;, &#x60;active&#x60; or &#x60;paused&#x60; operations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background operation.
     * @param putApiV4AdminBatchedBackgroundOperationsIdStopRequest The putApiV4AdminBatchedBackgroundOperationsIdStopRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBatchedBackgroundOperation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBatchedBackgroundOperation> putApiV4AdminBatchedBackgroundOperationsIdStopWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundOperationsIdStopRequest putApiV4AdminBatchedBackgroundOperationsIdStopRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBatchedBackgroundOperation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminBatchedBackgroundOperationsIdStopRequestCreation(id, putApiV4AdminBatchedBackgroundOperationsIdStopRequest).toEntity(localVarReturnType);
    }

    /**
     * Stop a batched background operation
     * This feature was introduced in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can stop only &#x60;queued&#x60;, &#x60;active&#x60; or &#x60;paused&#x60; operations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background operation.
     * @param putApiV4AdminBatchedBackgroundOperationsIdStopRequest The putApiV4AdminBatchedBackgroundOperationsIdStopRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminBatchedBackgroundOperationsIdStopWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundOperationsIdStopRequest putApiV4AdminBatchedBackgroundOperationsIdStopRequest) throws RestClientResponseException {
        return putApiV4AdminBatchedBackgroundOperationsIdStopRequestCreation(id, putApiV4AdminBatchedBackgroundOperationsIdStopRequest);
    }
}
