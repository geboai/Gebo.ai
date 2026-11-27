package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBulkImport;
import ai.gebo.gitlab.client.model.APIEntitiesImportOfflineExport;
import ai.gebo.gitlab.client.model.PostApiV4OfflineExportsRequest;
import ai.gebo.gitlab.client.model.PostApiV4OfflineImportsRequest;

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
public class OfflineTransfersApi {
    private ApiClient apiClient;

    public OfflineTransfersApi() {
        this(new ApiClient());
    }

    public OfflineTransfersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all offline transfer exports
     * Lists all offline transfer exports. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param status Return offline transfer exports with the specified status.
     * @return List&lt;APIEntitiesImportOfflineExport&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4OfflineExportsRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
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

        ParameterizedTypeReference<List<APIEntitiesImportOfflineExport>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/offline_exports", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all offline transfer exports
     * Lists all offline transfer exports. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param status Return offline transfer exports with the specified status.
     * @return List&lt;APIEntitiesImportOfflineExport&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesImportOfflineExport> getApiV4OfflineExports(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesImportOfflineExport>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4OfflineExportsRequestCreation(page, perPage, sort, status).body(localVarReturnType);
    }

    /**
     * List all offline transfer exports
     * Lists all offline transfer exports. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param status Return offline transfer exports with the specified status.
     * @return ResponseEntity&lt;List&lt;APIEntitiesImportOfflineExport&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesImportOfflineExport>> getApiV4OfflineExportsWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesImportOfflineExport>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4OfflineExportsRequestCreation(page, perPage, sort, status).toEntity(localVarReturnType);
    }

    /**
     * List all offline transfer exports
     * Lists all offline transfer exports. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param sort Sort results in ascending or descending order by creation date.
     * @param status Return offline transfer exports with the specified status.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4OfflineExportsWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        return getApiV4OfflineExportsRequestCreation(page, perPage, sort, status);
    }

    /**
     * Get offline transfer export details
     * Retrieves details of an offline transfer export. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the offline transfer export.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4OfflineExportsIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4OfflineExportsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/offline_exports/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get offline transfer export details
     * Retrieves details of an offline transfer export. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the offline transfer export.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4OfflineExportsId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4OfflineExportsIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get offline transfer export details
     * Retrieves details of an offline transfer export. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the offline transfer export.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4OfflineExportsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4OfflineExportsIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get offline transfer export details
     * Retrieves details of an offline transfer export. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the offline transfer export.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4OfflineExportsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4OfflineExportsIdRequestCreation(id);
    }

    /**
     * Start a new offline transfer export
     * Initiates a new offline transfer export. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4OfflineExportsRequest The postApiV4OfflineExportsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4OfflineExportsRequestCreation(@jakarta.annotation.Nonnull PostApiV4OfflineExportsRequest postApiV4OfflineExportsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4OfflineExportsRequest' is set
        if (postApiV4OfflineExportsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4OfflineExportsRequest' when calling postApiV4OfflineExports", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/offline_exports", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4OfflineExportsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Start a new offline transfer export
     * Initiates a new offline transfer export. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4OfflineExportsRequest The postApiV4OfflineExportsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4OfflineExports(@jakarta.annotation.Nonnull PostApiV4OfflineExportsRequest postApiV4OfflineExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4OfflineExportsRequestCreation(postApiV4OfflineExportsRequest).body(localVarReturnType);
    }

    /**
     * Start a new offline transfer export
     * Initiates a new offline transfer export. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4OfflineExportsRequest The postApiV4OfflineExportsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4OfflineExportsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4OfflineExportsRequest postApiV4OfflineExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OfflineExportsRequestCreation(postApiV4OfflineExportsRequest).toEntity(localVarReturnType);
    }

    /**
     * Start a new offline transfer export
     * Initiates a new offline transfer export. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4OfflineExportsRequest The postApiV4OfflineExportsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4OfflineExportsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4OfflineExportsRequest postApiV4OfflineExportsRequest) throws RestClientResponseException {
        return postApiV4OfflineExportsRequestCreation(postApiV4OfflineExportsRequest);
    }

    /**
     * Start a new offline transfer import
     * Initiates a new offline transfer import from object storage. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4OfflineImportsRequest The postApiV4OfflineImportsRequest parameter
     * @return APIEntitiesBulkImport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4OfflineImportsRequestCreation(@jakarta.annotation.Nonnull PostApiV4OfflineImportsRequest postApiV4OfflineImportsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4OfflineImportsRequest' is set
        if (postApiV4OfflineImportsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4OfflineImportsRequest' when calling postApiV4OfflineImports", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesBulkImport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/offline_imports", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4OfflineImportsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Start a new offline transfer import
     * Initiates a new offline transfer import from object storage. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4OfflineImportsRequest The postApiV4OfflineImportsRequest parameter
     * @return APIEntitiesBulkImport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBulkImport postApiV4OfflineImports(@jakarta.annotation.Nonnull PostApiV4OfflineImportsRequest postApiV4OfflineImportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBulkImport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OfflineImportsRequestCreation(postApiV4OfflineImportsRequest).body(localVarReturnType);
    }

    /**
     * Start a new offline transfer import
     * Initiates a new offline transfer import from object storage. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4OfflineImportsRequest The postApiV4OfflineImportsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBulkImport&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBulkImport> postApiV4OfflineImportsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4OfflineImportsRequest postApiV4OfflineImportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBulkImport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OfflineImportsRequestCreation(postApiV4OfflineImportsRequest).toEntity(localVarReturnType);
    }

    /**
     * Start a new offline transfer import
     * Initiates a new offline transfer import from object storage. For more information, see https://docs.gitlab.com/user/group/import/offline_transfer_migrations/
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4OfflineImportsRequest The postApiV4OfflineImportsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4OfflineImportsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4OfflineImportsRequest postApiV4OfflineImportsRequest) throws RestClientResponseException {
        return postApiV4OfflineImportsRequestCreation(postApiV4OfflineImportsRequest);
    }
}
