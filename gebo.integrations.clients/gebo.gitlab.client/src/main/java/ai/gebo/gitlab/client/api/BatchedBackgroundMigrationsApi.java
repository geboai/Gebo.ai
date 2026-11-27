package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBatchedBackgroundMigration;
import ai.gebo.gitlab.client.model.PutApiV4AdminBatchedBackgroundMigrationsIdPauseRequest;
import ai.gebo.gitlab.client.model.PutApiV4AdminBatchedBackgroundMigrationsIdResumeRequest;

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
public class BatchedBackgroundMigrationsApi {
    private ApiClient apiClient;

    public BatchedBackgroundMigrationsApi() {
        this(new ApiClient());
    }

    public BatchedBackgroundMigrationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get the list of batched background migrations
     * Lists all batched background migrations with optional filters for database and job class name. Returns the 20 most recently created migrations.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param database Name of the database.
     * @param jobClassName Filter migrations by job class name.
     * @return List&lt;APIEntitiesBatchedBackgroundMigration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminBatchedBackgroundMigrationsRequestCreation(@jakarta.annotation.Nullable String database, @jakarta.annotation.Nullable String jobClassName) throws RestClientResponseException {
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

        ParameterizedTypeReference<List<APIEntitiesBatchedBackgroundMigration>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/batched_background_migrations", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get the list of batched background migrations
     * Lists all batched background migrations with optional filters for database and job class name. Returns the 20 most recently created migrations.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param database Name of the database.
     * @param jobClassName Filter migrations by job class name.
     * @return List&lt;APIEntitiesBatchedBackgroundMigration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBatchedBackgroundMigration> getApiV4AdminBatchedBackgroundMigrations(@jakarta.annotation.Nullable String database, @jakarta.annotation.Nullable String jobClassName) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBatchedBackgroundMigration>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminBatchedBackgroundMigrationsRequestCreation(database, jobClassName).body(localVarReturnType);
    }

    /**
     * Get the list of batched background migrations
     * Lists all batched background migrations with optional filters for database and job class name. Returns the 20 most recently created migrations.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param database Name of the database.
     * @param jobClassName Filter migrations by job class name.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBatchedBackgroundMigration&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBatchedBackgroundMigration>> getApiV4AdminBatchedBackgroundMigrationsWithHttpInfo(@jakarta.annotation.Nullable String database, @jakarta.annotation.Nullable String jobClassName) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBatchedBackgroundMigration>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminBatchedBackgroundMigrationsRequestCreation(database, jobClassName).toEntity(localVarReturnType);
    }

    /**
     * Get the list of batched background migrations
     * Lists all batched background migrations with optional filters for database and job class name. Returns the 20 most recently created migrations.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param database Name of the database.
     * @param jobClassName Filter migrations by job class name.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminBatchedBackgroundMigrationsWithResponseSpec(@jakarta.annotation.Nullable String database, @jakarta.annotation.Nullable String jobClassName) throws RestClientResponseException {
        return getApiV4AdminBatchedBackgroundMigrationsRequestCreation(database, jobClassName);
    }

    /**
     * Retrieve a batched background migration
     * Fetches details of a specific batched migration, with optional filtering by database target.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background migration.
     * @param database Name of the database.
     * @return APIEntitiesBatchedBackgroundMigration
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminBatchedBackgroundMigrationsIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String database) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4AdminBatchedBackgroundMigrationsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesBatchedBackgroundMigration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/batched_background_migrations/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a batched background migration
     * Fetches details of a specific batched migration, with optional filtering by database target.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background migration.
     * @param database Name of the database.
     * @return APIEntitiesBatchedBackgroundMigration
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBatchedBackgroundMigration getApiV4AdminBatchedBackgroundMigrationsId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String database) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBatchedBackgroundMigration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminBatchedBackgroundMigrationsIdRequestCreation(id, database).body(localVarReturnType);
    }

    /**
     * Retrieve a batched background migration
     * Fetches details of a specific batched migration, with optional filtering by database target.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background migration.
     * @param database Name of the database.
     * @return ResponseEntity&lt;APIEntitiesBatchedBackgroundMigration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBatchedBackgroundMigration> getApiV4AdminBatchedBackgroundMigrationsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String database) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBatchedBackgroundMigration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminBatchedBackgroundMigrationsIdRequestCreation(id, database).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a batched background migration
     * Fetches details of a specific batched migration, with optional filtering by database target.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background migration.
     * @param database Name of the database.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminBatchedBackgroundMigrationsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String database) throws RestClientResponseException {
        return getApiV4AdminBatchedBackgroundMigrationsIdRequestCreation(id, database);
    }

    /**
     * Pause a batched background migration
     * Pauses an active batched background migration temporarily.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can pause only &#x60;active&#x60; batched background migrations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background migration.
     * @param putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest The putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest parameter
     * @return APIEntitiesBatchedBackgroundMigration
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminBatchedBackgroundMigrationsIdPauseRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundMigrationsIdPauseRequest putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4AdminBatchedBackgroundMigrationsIdPause", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesBatchedBackgroundMigration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/batched_background_migrations/{id}/pause", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Pause a batched background migration
     * Pauses an active batched background migration temporarily.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can pause only &#x60;active&#x60; batched background migrations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background migration.
     * @param putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest The putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest parameter
     * @return APIEntitiesBatchedBackgroundMigration
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBatchedBackgroundMigration putApiV4AdminBatchedBackgroundMigrationsIdPause(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundMigrationsIdPauseRequest putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBatchedBackgroundMigration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminBatchedBackgroundMigrationsIdPauseRequestCreation(id, putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest).body(localVarReturnType);
    }

    /**
     * Pause a batched background migration
     * Pauses an active batched background migration temporarily.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can pause only &#x60;active&#x60; batched background migrations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background migration.
     * @param putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest The putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBatchedBackgroundMigration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBatchedBackgroundMigration> putApiV4AdminBatchedBackgroundMigrationsIdPauseWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundMigrationsIdPauseRequest putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBatchedBackgroundMigration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminBatchedBackgroundMigrationsIdPauseRequestCreation(id, putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest).toEntity(localVarReturnType);
    }

    /**
     * Pause a batched background migration
     * Pauses an active batched background migration temporarily.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can pause only &#x60;active&#x60; batched background migrations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background migration.
     * @param putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest The putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminBatchedBackgroundMigrationsIdPauseWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundMigrationsIdPauseRequest putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest) throws RestClientResponseException {
        return putApiV4AdminBatchedBackgroundMigrationsIdPauseRequestCreation(id, putApiV4AdminBatchedBackgroundMigrationsIdPauseRequest);
    }

    /**
     * Resume a batched background migration
     * Resumes execution of a previously paused batched background migration.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can resume only &#x60;paused&#x60; batched background migrations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background migration.
     * @param putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest The putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest parameter
     * @return APIEntitiesBatchedBackgroundMigration
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminBatchedBackgroundMigrationsIdResumeRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundMigrationsIdResumeRequest putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4AdminBatchedBackgroundMigrationsIdResume", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesBatchedBackgroundMigration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/batched_background_migrations/{id}/resume", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Resume a batched background migration
     * Resumes execution of a previously paused batched background migration.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can resume only &#x60;paused&#x60; batched background migrations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background migration.
     * @param putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest The putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest parameter
     * @return APIEntitiesBatchedBackgroundMigration
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBatchedBackgroundMigration putApiV4AdminBatchedBackgroundMigrationsIdResume(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundMigrationsIdResumeRequest putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBatchedBackgroundMigration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminBatchedBackgroundMigrationsIdResumeRequestCreation(id, putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest).body(localVarReturnType);
    }

    /**
     * Resume a batched background migration
     * Resumes execution of a previously paused batched background migration.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can resume only &#x60;paused&#x60; batched background migrations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background migration.
     * @param putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest The putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBatchedBackgroundMigration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBatchedBackgroundMigration> putApiV4AdminBatchedBackgroundMigrationsIdResumeWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundMigrationsIdResumeRequest putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBatchedBackgroundMigration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminBatchedBackgroundMigrationsIdResumeRequestCreation(id, putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest).toEntity(localVarReturnType);
    }

    /**
     * Resume a batched background migration
     * Resumes execution of a previously paused batched background migration.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can resume only &#x60;paused&#x60; batched background migrations.
     * <p><b>400</b> - Bad Request
     * @param id ID of the batched background migration.
     * @param putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest The putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminBatchedBackgroundMigrationsIdResumeWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4AdminBatchedBackgroundMigrationsIdResumeRequest putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest) throws RestClientResponseException {
        return putApiV4AdminBatchedBackgroundMigrationsIdResumeRequestCreation(id, putApiV4AdminBatchedBackgroundMigrationsIdResumeRequest);
    }
}
