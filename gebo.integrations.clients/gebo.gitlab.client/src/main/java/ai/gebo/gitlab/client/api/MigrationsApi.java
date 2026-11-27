package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.PostApiV4AdminMigrationsTimestampMarkRequest;

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
public class MigrationsApi {
    private ApiClient apiClient;

    public MigrationsApi() {
        this(new ApiClient());
    }

    public MigrationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all pending migrations
     * Lists all pending migrations for the instance.
     * <p><b>200</b> - 200 OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>400</b> - Bad Request
     * @param database Database name to query.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminMigrationsPendingRequestCreation(@jakarta.annotation.Nullable String database) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "database", database));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/migrations/pending", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all pending migrations
     * Lists all pending migrations for the instance.
     * <p><b>200</b> - 200 OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>400</b> - Bad Request
     * @param database Database name to query.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4AdminMigrationsPending(@jakarta.annotation.Nullable String database) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4AdminMigrationsPendingRequestCreation(database).body(localVarReturnType);
    }

    /**
     * List all pending migrations
     * Lists all pending migrations for the instance.
     * <p><b>200</b> - 200 OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>400</b> - Bad Request
     * @param database Database name to query.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4AdminMigrationsPendingWithHttpInfo(@jakarta.annotation.Nullable String database) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminMigrationsPendingRequestCreation(database).toEntity(localVarReturnType);
    }

    /**
     * List all pending migrations
     * Lists all pending migrations for the instance.
     * <p><b>200</b> - 200 OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>400</b> - Bad Request
     * @param database Database name to query.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminMigrationsPendingWithResponseSpec(@jakarta.annotation.Nullable String database) throws RestClientResponseException {
        return getApiV4AdminMigrationsPendingRequestCreation(database);
    }

    /**
     * Update status of a migration
     * Updates the status of a migration to indicate a successful execution. This prevent them from being executed by the &#x60;db:migrate&#x60; tasks. Use this API to skip failing migrations after you determine they are safe to skip.
     * <p><b>201</b> - 201 Created
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can mark only pending migrations
     * <p><b>400</b> - Bad Request
     * @param timestamp Version timestamp of the migration.
     * @param postApiV4AdminMigrationsTimestampMarkRequest The postApiV4AdminMigrationsTimestampMarkRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AdminMigrationsTimestampMarkRequestCreation(@jakarta.annotation.Nonnull Integer timestamp, @jakarta.annotation.Nullable PostApiV4AdminMigrationsTimestampMarkRequest postApiV4AdminMigrationsTimestampMarkRequest) throws RestClientResponseException {
        // verify the required parameter 'timestamp' is set
        if (timestamp == null) {
            throw new RestClientResponseException("Missing the required parameter 'timestamp' when calling postApiV4AdminMigrationsTimestampMark", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("timestamp", timestamp);

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
        return apiClient.invokeAPI("/api/v4/admin/migrations/{timestamp}/mark", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AdminMigrationsTimestampMarkRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update status of a migration
     * Updates the status of a migration to indicate a successful execution. This prevent them from being executed by the &#x60;db:migrate&#x60; tasks. Use this API to skip failing migrations after you determine they are safe to skip.
     * <p><b>201</b> - 201 Created
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can mark only pending migrations
     * <p><b>400</b> - Bad Request
     * @param timestamp Version timestamp of the migration.
     * @param postApiV4AdminMigrationsTimestampMarkRequest The postApiV4AdminMigrationsTimestampMarkRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AdminMigrationsTimestampMark(@jakarta.annotation.Nonnull Integer timestamp, @jakarta.annotation.Nullable PostApiV4AdminMigrationsTimestampMarkRequest postApiV4AdminMigrationsTimestampMarkRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AdminMigrationsTimestampMarkRequestCreation(timestamp, postApiV4AdminMigrationsTimestampMarkRequest).body(localVarReturnType);
    }

    /**
     * Update status of a migration
     * Updates the status of a migration to indicate a successful execution. This prevent them from being executed by the &#x60;db:migrate&#x60; tasks. Use this API to skip failing migrations after you determine they are safe to skip.
     * <p><b>201</b> - 201 Created
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can mark only pending migrations
     * <p><b>400</b> - Bad Request
     * @param timestamp Version timestamp of the migration.
     * @param postApiV4AdminMigrationsTimestampMarkRequest The postApiV4AdminMigrationsTimestampMarkRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AdminMigrationsTimestampMarkWithHttpInfo(@jakarta.annotation.Nonnull Integer timestamp, @jakarta.annotation.Nullable PostApiV4AdminMigrationsTimestampMarkRequest postApiV4AdminMigrationsTimestampMarkRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AdminMigrationsTimestampMarkRequestCreation(timestamp, postApiV4AdminMigrationsTimestampMarkRequest).toEntity(localVarReturnType);
    }

    /**
     * Update status of a migration
     * Updates the status of a migration to indicate a successful execution. This prevent them from being executed by the &#x60;db:migrate&#x60; tasks. Use this API to skip failing migrations after you determine they are safe to skip.
     * <p><b>201</b> - 201 Created
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>422</b> - You can mark only pending migrations
     * <p><b>400</b> - Bad Request
     * @param timestamp Version timestamp of the migration.
     * @param postApiV4AdminMigrationsTimestampMarkRequest The postApiV4AdminMigrationsTimestampMarkRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AdminMigrationsTimestampMarkWithResponseSpec(@jakarta.annotation.Nonnull Integer timestamp, @jakarta.annotation.Nullable PostApiV4AdminMigrationsTimestampMarkRequest postApiV4AdminMigrationsTimestampMarkRequest) throws RestClientResponseException {
        return postApiV4AdminMigrationsTimestampMarkRequestCreation(timestamp, postApiV4AdminMigrationsTimestampMarkRequest);
    }
}
