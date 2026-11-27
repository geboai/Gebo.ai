package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBulkImport;
import ai.gebo.gitlab.client.model.APIEntitiesBulkImportsEntity;
import ai.gebo.gitlab.client.model.APIEntitiesBulkImportsEntityFailure;
import ai.gebo.gitlab.client.model.PostApiV4BulkImportsRequestConfiguration;
import ai.gebo.gitlab.client.model.PostApiV4BulkImportsRequestEntitiesInner;
import ai.gebo.gitlab.client.model.PostApiV4ImportGithubGistsRequest;

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
public class ImportsApi {
    private ApiClient apiClient;

    public ImportsApi() {
        this(new ApiClient());
    }

    public ImportsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all group or project migrations
     * Lists all group or project migrations.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param status Filter migrations by import status.
     * @return List&lt;APIEntitiesBulkImport&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4BulkImportsRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesBulkImport>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/bulk_imports", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group or project migrations
     * Lists all group or project migrations.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param status Filter migrations by import status.
     * @return List&lt;APIEntitiesBulkImport&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBulkImport> getApiV4BulkImports(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBulkImport>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BulkImportsRequestCreation(page, perPage, sort, status).body(localVarReturnType);
    }

    /**
     * List all group or project migrations
     * Lists all group or project migrations.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param status Filter migrations by import status.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBulkImport&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBulkImport>> getApiV4BulkImportsWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBulkImport>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BulkImportsRequestCreation(page, perPage, sort, status).toEntity(localVarReturnType);
    }

    /**
     * List all group or project migrations
     * Lists all group or project migrations.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param status Filter migrations by import status.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4BulkImportsWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        return getApiV4BulkImportsRequestCreation(page, perPage, sort, status);
    }

    /**
     * List all group or project migration entities
     * Lists all group or project migration entities.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param status Filter entities by import status.
     * @return List&lt;APIEntitiesBulkImportsEntity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4BulkImportsEntitiesRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesBulkImportsEntity>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/bulk_imports/entities", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group or project migration entities
     * Lists all group or project migration entities.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param status Filter entities by import status.
     * @return List&lt;APIEntitiesBulkImportsEntity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBulkImportsEntity> getApiV4BulkImportsEntities(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBulkImportsEntity>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BulkImportsEntitiesRequestCreation(page, perPage, sort, status).body(localVarReturnType);
    }

    /**
     * List all group or project migration entities
     * Lists all group or project migration entities.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param status Filter entities by import status.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBulkImportsEntity&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBulkImportsEntity>> getApiV4BulkImportsEntitiesWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBulkImportsEntity>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BulkImportsEntitiesRequestCreation(page, perPage, sort, status).toEntity(localVarReturnType);
    }

    /**
     * List all group or project migration entities
     * Lists all group or project migration entities.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param status Filter entities by import status.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4BulkImportsEntitiesWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        return getApiV4BulkImportsEntitiesRequestCreation(page, perPage, sort, status);
    }

    /**
     * Retrieve a group or project migration
     * Retrieves details of a group or project migration.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @return APIEntitiesBulkImport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4BulkImportsImportIdRequestCreation(@jakarta.annotation.Nonnull Integer importId) throws RestClientResponseException {
        // verify the required parameter 'importId' is set
        if (importId == null) {
            throw new RestClientResponseException("Missing the required parameter 'importId' when calling getApiV4BulkImportsImportId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("import_id", importId);

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

        ParameterizedTypeReference<APIEntitiesBulkImport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/bulk_imports/{import_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group or project migration
     * Retrieves details of a group or project migration.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @return APIEntitiesBulkImport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBulkImport getApiV4BulkImportsImportId(@jakarta.annotation.Nonnull Integer importId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBulkImport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BulkImportsImportIdRequestCreation(importId).body(localVarReturnType);
    }

    /**
     * Retrieve a group or project migration
     * Retrieves details of a group or project migration.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @return ResponseEntity&lt;APIEntitiesBulkImport&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBulkImport> getApiV4BulkImportsImportIdWithHttpInfo(@jakarta.annotation.Nonnull Integer importId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBulkImport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BulkImportsImportIdRequestCreation(importId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group or project migration
     * Retrieves details of a group or project migration.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4BulkImportsImportIdWithResponseSpec(@jakarta.annotation.Nonnull Integer importId) throws RestClientResponseException {
        return getApiV4BulkImportsImportIdRequestCreation(importId);
    }

    /**
     * List all group or project migration entities
     * Lists all group or project migration entities for a specified migration.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @param status Filter entities by import status.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBulkImportsEntity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4BulkImportsImportIdEntitiesRequestCreation(@jakarta.annotation.Nonnull Integer importId, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'importId' is set
        if (importId == null) {
            throw new RestClientResponseException("Missing the required parameter 'importId' when calling getApiV4BulkImportsImportIdEntities", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("import_id", importId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesBulkImportsEntity>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/bulk_imports/{import_id}/entities", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group or project migration entities
     * Lists all group or project migration entities for a specified migration.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @param status Filter entities by import status.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBulkImportsEntity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBulkImportsEntity> getApiV4BulkImportsImportIdEntities(@jakarta.annotation.Nonnull Integer importId, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBulkImportsEntity>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BulkImportsImportIdEntitiesRequestCreation(importId, status, page, perPage).body(localVarReturnType);
    }

    /**
     * List all group or project migration entities
     * Lists all group or project migration entities for a specified migration.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @param status Filter entities by import status.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBulkImportsEntity&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBulkImportsEntity>> getApiV4BulkImportsImportIdEntitiesWithHttpInfo(@jakarta.annotation.Nonnull Integer importId, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBulkImportsEntity>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BulkImportsImportIdEntitiesRequestCreation(importId, status, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group or project migration entities
     * Lists all group or project migration entities for a specified migration.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @param status Filter entities by import status.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4BulkImportsImportIdEntitiesWithResponseSpec(@jakarta.annotation.Nonnull Integer importId, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4BulkImportsImportIdEntitiesRequestCreation(importId, status, page, perPage);
    }

    /**
     * Retrieve a group or project migration entity
     * Retrieves details of a group or project migration entity.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @param entityId ID of the GitLab Migration entity.
     * @return APIEntitiesBulkImportsEntity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4BulkImportsImportIdEntitiesEntityIdRequestCreation(@jakarta.annotation.Nonnull Integer importId, @jakarta.annotation.Nonnull Integer entityId) throws RestClientResponseException {
        // verify the required parameter 'importId' is set
        if (importId == null) {
            throw new RestClientResponseException("Missing the required parameter 'importId' when calling getApiV4BulkImportsImportIdEntitiesEntityId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new RestClientResponseException("Missing the required parameter 'entityId' when calling getApiV4BulkImportsImportIdEntitiesEntityId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("import_id", importId);
        pathParams.put("entity_id", entityId);

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

        ParameterizedTypeReference<APIEntitiesBulkImportsEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/bulk_imports/{import_id}/entities/{entity_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group or project migration entity
     * Retrieves details of a group or project migration entity.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @param entityId ID of the GitLab Migration entity.
     * @return APIEntitiesBulkImportsEntity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBulkImportsEntity getApiV4BulkImportsImportIdEntitiesEntityId(@jakarta.annotation.Nonnull Integer importId, @jakarta.annotation.Nonnull Integer entityId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBulkImportsEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BulkImportsImportIdEntitiesEntityIdRequestCreation(importId, entityId).body(localVarReturnType);
    }

    /**
     * Retrieve a group or project migration entity
     * Retrieves details of a group or project migration entity.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @param entityId ID of the GitLab Migration entity.
     * @return ResponseEntity&lt;APIEntitiesBulkImportsEntity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBulkImportsEntity> getApiV4BulkImportsImportIdEntitiesEntityIdWithHttpInfo(@jakarta.annotation.Nonnull Integer importId, @jakarta.annotation.Nonnull Integer entityId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBulkImportsEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BulkImportsImportIdEntitiesEntityIdRequestCreation(importId, entityId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group or project migration entity
     * Retrieves details of a group or project migration entity.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @param entityId ID of the GitLab Migration entity.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4BulkImportsImportIdEntitiesEntityIdWithResponseSpec(@jakarta.annotation.Nonnull Integer importId, @jakarta.annotation.Nonnull Integer entityId) throws RestClientResponseException {
        return getApiV4BulkImportsImportIdEntitiesEntityIdRequestCreation(importId, entityId);
    }

    /**
     * List all failed import records for a migration entity
     * Lists all failed import records for a group or project migration entity. This feature was introduced in GitLab 16.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @param entityId ID of the GitLab Migration entity.
     * @return List&lt;APIEntitiesBulkImportsEntityFailure&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4BulkImportsImportIdEntitiesEntityIdFailuresRequestCreation(@jakarta.annotation.Nonnull Integer importId, @jakarta.annotation.Nonnull Integer entityId) throws RestClientResponseException {
        // verify the required parameter 'importId' is set
        if (importId == null) {
            throw new RestClientResponseException("Missing the required parameter 'importId' when calling getApiV4BulkImportsImportIdEntitiesEntityIdFailures", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new RestClientResponseException("Missing the required parameter 'entityId' when calling getApiV4BulkImportsImportIdEntitiesEntityIdFailures", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("import_id", importId);
        pathParams.put("entity_id", entityId);

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

        ParameterizedTypeReference<List<APIEntitiesBulkImportsEntityFailure>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/bulk_imports/{import_id}/entities/{entity_id}/failures", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all failed import records for a migration entity
     * Lists all failed import records for a group or project migration entity. This feature was introduced in GitLab 16.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @param entityId ID of the GitLab Migration entity.
     * @return List&lt;APIEntitiesBulkImportsEntityFailure&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBulkImportsEntityFailure> getApiV4BulkImportsImportIdEntitiesEntityIdFailures(@jakarta.annotation.Nonnull Integer importId, @jakarta.annotation.Nonnull Integer entityId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBulkImportsEntityFailure>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BulkImportsImportIdEntitiesEntityIdFailuresRequestCreation(importId, entityId).body(localVarReturnType);
    }

    /**
     * List all failed import records for a migration entity
     * Lists all failed import records for a group or project migration entity. This feature was introduced in GitLab 16.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @param entityId ID of the GitLab Migration entity.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBulkImportsEntityFailure&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBulkImportsEntityFailure>> getApiV4BulkImportsImportIdEntitiesEntityIdFailuresWithHttpInfo(@jakarta.annotation.Nonnull Integer importId, @jakarta.annotation.Nonnull Integer entityId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBulkImportsEntityFailure>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BulkImportsImportIdEntitiesEntityIdFailuresRequestCreation(importId, entityId).toEntity(localVarReturnType);
    }

    /**
     * List all failed import records for a migration entity
     * Lists all failed import records for a group or project migration entity. This feature was introduced in GitLab 16.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @param entityId ID of the GitLab Migration entity.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4BulkImportsImportIdEntitiesEntityIdFailuresWithResponseSpec(@jakarta.annotation.Nonnull Integer importId, @jakarta.annotation.Nonnull Integer entityId) throws RestClientResponseException {
        return getApiV4BulkImportsImportIdEntitiesEntityIdFailuresRequestCreation(importId, entityId);
    }

    /**
     * Start a group or project migration
     * Starts a group or project migration. To migrate a project, specify &#x60;entities[project_entity]&#x60;.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>503</b> - Service unavailable
     * @param _configuration The _configuration parameter
     * @param entities List of entities to import
     * @return APIEntitiesBulkImport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4BulkImportsRequestCreation(@jakarta.annotation.Nullable PostApiV4BulkImportsRequestConfiguration _configuration, @jakarta.annotation.Nullable List<PostApiV4BulkImportsRequestEntitiesInner> entities) throws RestClientResponseException {
        // verify the required parameter '_configuration' is set
        if (_configuration == null) {
            throw new RestClientResponseException("Missing the required parameter '_configuration' when calling postApiV4BulkImports", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'entities' is set
        if (entities == null) {
            throw new RestClientResponseException("Missing the required parameter 'entities' when calling postApiV4BulkImports", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("configuration", _configuration);
        formParams.addAll("entities", entities);

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/x-www-form-urlencoded"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesBulkImport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/bulk_imports", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Start a group or project migration
     * Starts a group or project migration. To migrate a project, specify &#x60;entities[project_entity]&#x60;.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>503</b> - Service unavailable
     * @param _configuration The _configuration parameter
     * @param entities List of entities to import
     * @return APIEntitiesBulkImport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBulkImport postApiV4BulkImports(@jakarta.annotation.Nullable PostApiV4BulkImportsRequestConfiguration _configuration, @jakarta.annotation.Nullable List<PostApiV4BulkImportsRequestEntitiesInner> entities) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBulkImport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4BulkImportsRequestCreation(_configuration, entities).body(localVarReturnType);
    }

    /**
     * Start a group or project migration
     * Starts a group or project migration. To migrate a project, specify &#x60;entities[project_entity]&#x60;.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>503</b> - Service unavailable
     * @param _configuration The _configuration parameter
     * @param entities List of entities to import
     * @return ResponseEntity&lt;APIEntitiesBulkImport&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBulkImport> postApiV4BulkImportsWithHttpInfo(@jakarta.annotation.Nullable PostApiV4BulkImportsRequestConfiguration _configuration, @jakarta.annotation.Nullable List<PostApiV4BulkImportsRequestEntitiesInner> entities) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBulkImport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4BulkImportsRequestCreation(_configuration, entities).toEntity(localVarReturnType);
    }

    /**
     * Start a group or project migration
     * Starts a group or project migration. To migrate a project, specify &#x60;entities[project_entity]&#x60;.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>503</b> - Service unavailable
     * @param _configuration The _configuration parameter
     * @param entities List of entities to import
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4BulkImportsWithResponseSpec(@jakarta.annotation.Nullable PostApiV4BulkImportsRequestConfiguration _configuration, @jakarta.annotation.Nullable List<PostApiV4BulkImportsRequestEntitiesInner> entities) throws RestClientResponseException {
        return postApiV4BulkImportsRequestCreation(_configuration, entities);
    }

    /**
     * Cancel a migration
     * Cancels a direct transfer or offline transfer migration. Migrations that already finished, failed, timed out, or were canceled keep their status, but any entities still running are canceled. This feature was introduced in GitLab 17.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @return APIEntitiesBulkImport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4BulkImportsImportIdCancelRequestCreation(@jakarta.annotation.Nonnull Integer importId) throws RestClientResponseException {
        // verify the required parameter 'importId' is set
        if (importId == null) {
            throw new RestClientResponseException("Missing the required parameter 'importId' when calling postApiV4BulkImportsImportIdCancel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("import_id", importId);

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

        ParameterizedTypeReference<APIEntitiesBulkImport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/bulk_imports/{import_id}/cancel", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Cancel a migration
     * Cancels a direct transfer or offline transfer migration. Migrations that already finished, failed, timed out, or were canceled keep their status, but any entities still running are canceled. This feature was introduced in GitLab 17.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @return APIEntitiesBulkImport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBulkImport postApiV4BulkImportsImportIdCancel(@jakarta.annotation.Nonnull Integer importId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBulkImport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4BulkImportsImportIdCancelRequestCreation(importId).body(localVarReturnType);
    }

    /**
     * Cancel a migration
     * Cancels a direct transfer or offline transfer migration. Migrations that already finished, failed, timed out, or were canceled keep their status, but any entities still running are canceled. This feature was introduced in GitLab 17.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @return ResponseEntity&lt;APIEntitiesBulkImport&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBulkImport> postApiV4BulkImportsImportIdCancelWithHttpInfo(@jakarta.annotation.Nonnull Integer importId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBulkImport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4BulkImportsImportIdCancelRequestCreation(importId).toEntity(localVarReturnType);
    }

    /**
     * Cancel a migration
     * Cancels a direct transfer or offline transfer migration. Migrations that already finished, failed, timed out, or were canceled keep their status, but any entities still running are canceled. This feature was introduced in GitLab 17.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param importId ID of the GitLab Migration.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4BulkImportsImportIdCancelWithResponseSpec(@jakarta.annotation.Nonnull Integer importId) throws RestClientResponseException {
        return postApiV4BulkImportsImportIdCancelRequestCreation(importId);
    }

    /**
     * Import GitHub gists into GitLab snippets
     * Imports personal GitHub gists into GitLab snippets. You can import gists with up to 10 files. GitHub gists with more than 10 files are skipped. You should manually migrate these GitHub gists. If any gists cannot be imported, an email is sent with a list of gists that were not imported. This endpoint has a rate limit of six requests per minute for each user. An administrator can change this limit in the application settings.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4ImportGithubGistsRequest The postApiV4ImportGithubGistsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ImportGithubGistsRequestCreation(@jakarta.annotation.Nonnull PostApiV4ImportGithubGistsRequest postApiV4ImportGithubGistsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4ImportGithubGistsRequest' is set
        if (postApiV4ImportGithubGistsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ImportGithubGistsRequest' when calling postApiV4ImportGithubGists", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/import/github/gists", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ImportGithubGistsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Import GitHub gists into GitLab snippets
     * Imports personal GitHub gists into GitLab snippets. You can import gists with up to 10 files. GitHub gists with more than 10 files are skipped. You should manually migrate these GitHub gists. If any gists cannot be imported, an email is sent with a list of gists that were not imported. This endpoint has a rate limit of six requests per minute for each user. An administrator can change this limit in the application settings.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4ImportGithubGistsRequest The postApiV4ImportGithubGistsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ImportGithubGists(@jakarta.annotation.Nonnull PostApiV4ImportGithubGistsRequest postApiV4ImportGithubGistsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ImportGithubGistsRequestCreation(postApiV4ImportGithubGistsRequest).body(localVarReturnType);
    }

    /**
     * Import GitHub gists into GitLab snippets
     * Imports personal GitHub gists into GitLab snippets. You can import gists with up to 10 files. GitHub gists with more than 10 files are skipped. You should manually migrate these GitHub gists. If any gists cannot be imported, an email is sent with a list of gists that were not imported. This endpoint has a rate limit of six requests per minute for each user. An administrator can change this limit in the application settings.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4ImportGithubGistsRequest The postApiV4ImportGithubGistsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ImportGithubGistsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4ImportGithubGistsRequest postApiV4ImportGithubGistsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ImportGithubGistsRequestCreation(postApiV4ImportGithubGistsRequest).toEntity(localVarReturnType);
    }

    /**
     * Import GitHub gists into GitLab snippets
     * Imports personal GitHub gists into GitLab snippets. You can import gists with up to 10 files. GitHub gists with more than 10 files are skipped. You should manually migrate these GitHub gists. If any gists cannot be imported, an email is sent with a list of gists that were not imported. This endpoint has a rate limit of six requests per minute for each user. An administrator can change this limit in the application settings.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4ImportGithubGistsRequest The postApiV4ImportGithubGistsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ImportGithubGistsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4ImportGithubGistsRequest postApiV4ImportGithubGistsRequest) throws RestClientResponseException {
        return postApiV4ImportGithubGistsRequestCreation(postApiV4ImportGithubGistsRequest);
    }
}
