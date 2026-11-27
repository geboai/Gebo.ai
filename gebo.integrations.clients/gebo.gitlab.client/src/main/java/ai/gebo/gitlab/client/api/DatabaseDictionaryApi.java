package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesDictionaryTable;

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
public class DatabaseDictionaryApi {
    private ApiClient apiClient;

    public DatabaseDictionaryApi() {
        this(new ApiClient());
    }

    public DatabaseDictionaryApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve dictionary details
     * Provides table schema and column information from the GitLab database dictionary.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param databaseName Name of the database.
     * @param tableName Name of the table.
     * @return APIEntitiesDictionaryTable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminDatabasesDatabaseNameDictionaryTablesTableNameRequestCreation(@jakarta.annotation.Nonnull String databaseName, @jakarta.annotation.Nonnull String tableName) throws RestClientResponseException {
        // verify the required parameter 'databaseName' is set
        if (databaseName == null) {
            throw new RestClientResponseException("Missing the required parameter 'databaseName' when calling getApiV4AdminDatabasesDatabaseNameDictionaryTablesTableName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tableName' is set
        if (tableName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tableName' when calling getApiV4AdminDatabasesDatabaseNameDictionaryTablesTableName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("database_name", databaseName);
        pathParams.put("table_name", tableName);

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

        ParameterizedTypeReference<APIEntitiesDictionaryTable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/databases/{database_name}/dictionary/tables/{table_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve dictionary details
     * Provides table schema and column information from the GitLab database dictionary.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param databaseName Name of the database.
     * @param tableName Name of the table.
     * @return APIEntitiesDictionaryTable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDictionaryTable getApiV4AdminDatabasesDatabaseNameDictionaryTablesTableName(@jakarta.annotation.Nonnull String databaseName, @jakarta.annotation.Nonnull String tableName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDictionaryTable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminDatabasesDatabaseNameDictionaryTablesTableNameRequestCreation(databaseName, tableName).body(localVarReturnType);
    }

    /**
     * Retrieve dictionary details
     * Provides table schema and column information from the GitLab database dictionary.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param databaseName Name of the database.
     * @param tableName Name of the table.
     * @return ResponseEntity&lt;APIEntitiesDictionaryTable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDictionaryTable> getApiV4AdminDatabasesDatabaseNameDictionaryTablesTableNameWithHttpInfo(@jakarta.annotation.Nonnull String databaseName, @jakarta.annotation.Nonnull String tableName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDictionaryTable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminDatabasesDatabaseNameDictionaryTablesTableNameRequestCreation(databaseName, tableName).toEntity(localVarReturnType);
    }

    /**
     * Retrieve dictionary details
     * Provides table schema and column information from the GitLab database dictionary.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param databaseName Name of the database.
     * @param tableName Name of the table.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminDatabasesDatabaseNameDictionaryTablesTableNameWithResponseSpec(@jakarta.annotation.Nonnull String databaseName, @jakarta.annotation.Nonnull String tableName) throws RestClientResponseException {
        return getApiV4AdminDatabasesDatabaseNameDictionaryTablesTableNameRequestCreation(databaseName, tableName);
    }

    /**
     * List dictionary tables
     * Returns database dictionary tables filtered by database and optional table size
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param databaseName Name of the database.
     * @param tableSize Filter by table size classification.
     * @return List&lt;APIEntitiesDictionaryTable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4DatabasesDatabaseNameDictionaryTablesRequestCreation(@jakarta.annotation.Nonnull String databaseName, @jakarta.annotation.Nullable String tableSize) throws RestClientResponseException {
        // verify the required parameter 'databaseName' is set
        if (databaseName == null) {
            throw new RestClientResponseException("Missing the required parameter 'databaseName' when calling getApiV4DatabasesDatabaseNameDictionaryTables", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("database_name", databaseName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "table_size", tableSize));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesDictionaryTable>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/databases/{database_name}/dictionary/tables", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List dictionary tables
     * Returns database dictionary tables filtered by database and optional table size
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param databaseName Name of the database.
     * @param tableSize Filter by table size classification.
     * @return List&lt;APIEntitiesDictionaryTable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDictionaryTable> getApiV4DatabasesDatabaseNameDictionaryTables(@jakarta.annotation.Nonnull String databaseName, @jakarta.annotation.Nullable String tableSize) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDictionaryTable>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4DatabasesDatabaseNameDictionaryTablesRequestCreation(databaseName, tableSize).body(localVarReturnType);
    }

    /**
     * List dictionary tables
     * Returns database dictionary tables filtered by database and optional table size
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param databaseName Name of the database.
     * @param tableSize Filter by table size classification.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDictionaryTable&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDictionaryTable>> getApiV4DatabasesDatabaseNameDictionaryTablesWithHttpInfo(@jakarta.annotation.Nonnull String databaseName, @jakarta.annotation.Nullable String tableSize) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDictionaryTable>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4DatabasesDatabaseNameDictionaryTablesRequestCreation(databaseName, tableSize).toEntity(localVarReturnType);
    }

    /**
     * List dictionary tables
     * Returns database dictionary tables filtered by database and optional table size
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param databaseName Name of the database.
     * @param tableSize Filter by table size classification.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4DatabasesDatabaseNameDictionaryTablesWithResponseSpec(@jakarta.annotation.Nonnull String databaseName, @jakarta.annotation.Nullable String tableSize) throws RestClientResponseException {
        return getApiV4DatabasesDatabaseNameDictionaryTablesRequestCreation(databaseName, tableSize);
    }
}
