package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAdminModel;
import ai.gebo.gitlab.client.model.GetApiV4AdminDataManagementModelNameIdentifiersParameter;
import ai.gebo.gitlab.client.model.GetApiV4DependencyListExportsExportIdExportIdParameter;
import ai.gebo.gitlab.client.model.PutApiV4AdminDataManagementModelNameChecksumRequest;

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
public class DataManagementApi {
    private ApiClient apiClient;

    public DataManagementApi() {
        this(new ApiClient());
    }

    public DataManagementApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve model information
     * Retrieves information about a data model in an instance. Only available to administrators. Supports keyset pagination on the primary key of the model. To use keyset pagination, set &#x60;pagination&#x3D;keyset&#x60;. By default, keyset pagination returns 20 records per page in ascending order. Use &#x60;sort&#x60; to set the order and &#x60;per_page&#x60; to set the number of records per page.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param identifiers Filter results with an array of unique identifiers of the requested model, which can be integers or base64 encoded strings.
     * @param checksumState Filter by checksum status.
     * @param cursor Cursor for obtaining the next set of records.
     * @param sort Sort results in ascending or descending order.
     * @return List&lt;APIEntitiesAdminModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminDataManagementModelNameRequestCreation(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable GetApiV4AdminDataManagementModelNameIdentifiersParameter identifiers, @jakarta.annotation.Nullable String checksumState, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // verify the required parameter 'modelName' is set
        if (modelName == null) {
            throw new RestClientResponseException("Missing the required parameter 'modelName' when calling getApiV4AdminDataManagementModelName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("model_name", modelName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "identifiers", identifiers));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "checksum_state", checksumState));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "cursor", cursor));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesAdminModel>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/data_management/{model_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve model information
     * Retrieves information about a data model in an instance. Only available to administrators. Supports keyset pagination on the primary key of the model. To use keyset pagination, set &#x60;pagination&#x3D;keyset&#x60;. By default, keyset pagination returns 20 records per page in ascending order. Use &#x60;sort&#x60; to set the order and &#x60;per_page&#x60; to set the number of records per page.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param identifiers Filter results with an array of unique identifiers of the requested model, which can be integers or base64 encoded strings.
     * @param checksumState Filter by checksum status.
     * @param cursor Cursor for obtaining the next set of records.
     * @param sort Sort results in ascending or descending order.
     * @return List&lt;APIEntitiesAdminModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAdminModel> getApiV4AdminDataManagementModelName(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable GetApiV4AdminDataManagementModelNameIdentifiersParameter identifiers, @jakarta.annotation.Nullable String checksumState, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAdminModel>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminDataManagementModelNameRequestCreation(modelName, page, perPage, identifiers, checksumState, cursor, sort).body(localVarReturnType);
    }

    /**
     * Retrieve model information
     * Retrieves information about a data model in an instance. Only available to administrators. Supports keyset pagination on the primary key of the model. To use keyset pagination, set &#x60;pagination&#x3D;keyset&#x60;. By default, keyset pagination returns 20 records per page in ascending order. Use &#x60;sort&#x60; to set the order and &#x60;per_page&#x60; to set the number of records per page.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param identifiers Filter results with an array of unique identifiers of the requested model, which can be integers or base64 encoded strings.
     * @param checksumState Filter by checksum status.
     * @param cursor Cursor for obtaining the next set of records.
     * @param sort Sort results in ascending or descending order.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAdminModel&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAdminModel>> getApiV4AdminDataManagementModelNameWithHttpInfo(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable GetApiV4AdminDataManagementModelNameIdentifiersParameter identifiers, @jakarta.annotation.Nullable String checksumState, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAdminModel>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminDataManagementModelNameRequestCreation(modelName, page, perPage, identifiers, checksumState, cursor, sort).toEntity(localVarReturnType);
    }

    /**
     * Retrieve model information
     * Retrieves information about a data model in an instance. Only available to administrators. Supports keyset pagination on the primary key of the model. To use keyset pagination, set &#x60;pagination&#x3D;keyset&#x60;. By default, keyset pagination returns 20 records per page in ascending order. Use &#x60;sort&#x60; to set the order and &#x60;per_page&#x60; to set the number of records per page.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param identifiers Filter results with an array of unique identifiers of the requested model, which can be integers or base64 encoded strings.
     * @param checksumState Filter by checksum status.
     * @param cursor Cursor for obtaining the next set of records.
     * @param sort Sort results in ascending or descending order.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminDataManagementModelNameWithResponseSpec(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable GetApiV4AdminDataManagementModelNameIdentifiersParameter identifiers, @jakarta.annotation.Nullable String checksumState, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getApiV4AdminDataManagementModelNameRequestCreation(modelName, page, perPage, identifiers, checksumState, cursor, sort);
    }

    /**
     * Retrieve information about a model record
     * Retrieves information about a specified model record. Only available to administrators.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param recordIdentifier Unique identifier of the record. Can be an integer or a Base64-encoded string, as returned by the list endpoint.
     * @return APIEntitiesAdminModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminDataManagementModelNameRecordIdentifierRequestCreation(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter recordIdentifier) throws RestClientResponseException {
        // verify the required parameter 'modelName' is set
        if (modelName == null) {
            throw new RestClientResponseException("Missing the required parameter 'modelName' when calling getApiV4AdminDataManagementModelNameRecordIdentifier", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recordIdentifier' is set
        if (recordIdentifier == null) {
            throw new RestClientResponseException("Missing the required parameter 'recordIdentifier' when calling getApiV4AdminDataManagementModelNameRecordIdentifier", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("model_name", modelName);
        pathParams.put("record_identifier", recordIdentifier);

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

        ParameterizedTypeReference<APIEntitiesAdminModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/data_management/{model_name}/{record_identifier}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve information about a model record
     * Retrieves information about a specified model record. Only available to administrators.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param recordIdentifier Unique identifier of the record. Can be an integer or a Base64-encoded string, as returned by the list endpoint.
     * @return APIEntitiesAdminModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAdminModel getApiV4AdminDataManagementModelNameRecordIdentifier(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter recordIdentifier) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAdminModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminDataManagementModelNameRecordIdentifierRequestCreation(modelName, recordIdentifier).body(localVarReturnType);
    }

    /**
     * Retrieve information about a model record
     * Retrieves information about a specified model record. Only available to administrators.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param recordIdentifier Unique identifier of the record. Can be an integer or a Base64-encoded string, as returned by the list endpoint.
     * @return ResponseEntity&lt;APIEntitiesAdminModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAdminModel> getApiV4AdminDataManagementModelNameRecordIdentifierWithHttpInfo(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter recordIdentifier) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAdminModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminDataManagementModelNameRecordIdentifierRequestCreation(modelName, recordIdentifier).toEntity(localVarReturnType);
    }

    /**
     * Retrieve information about a model record
     * Retrieves information about a specified model record. Only available to administrators.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param recordIdentifier Unique identifier of the record. Can be an integer or a Base64-encoded string, as returned by the list endpoint.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminDataManagementModelNameRecordIdentifierWithResponseSpec(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter recordIdentifier) throws RestClientResponseException {
        return getApiV4AdminDataManagementModelNameRecordIdentifierRequestCreation(modelName, recordIdentifier);
    }

    /**
     * Recalculate checksums for model records
     * Recalculates checksums for selected records of a specified model, filtered by &#x60;checksum_state&#x60; and &#x60;identifiers&#x60; parameters if provided. The request enqueues a background job to perform the recalculation. Only available to administrators on primary Geo sites. If no records match &#x60;identifiers&#x60;, returns &#x60;status&#x60; &#x60;success&#x60; with the message &#x60;No records matched the given identifiers.&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param putApiV4AdminDataManagementModelNameChecksumRequest The putApiV4AdminDataManagementModelNameChecksumRequest parameter
     * @return APIEntitiesAdminModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminDataManagementModelNameChecksumRequestCreation(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nullable PutApiV4AdminDataManagementModelNameChecksumRequest putApiV4AdminDataManagementModelNameChecksumRequest) throws RestClientResponseException {
        // verify the required parameter 'modelName' is set
        if (modelName == null) {
            throw new RestClientResponseException("Missing the required parameter 'modelName' when calling putApiV4AdminDataManagementModelNameChecksum", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("model_name", modelName);

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

        ParameterizedTypeReference<APIEntitiesAdminModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/data_management/{model_name}/checksum", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4AdminDataManagementModelNameChecksumRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Recalculate checksums for model records
     * Recalculates checksums for selected records of a specified model, filtered by &#x60;checksum_state&#x60; and &#x60;identifiers&#x60; parameters if provided. The request enqueues a background job to perform the recalculation. Only available to administrators on primary Geo sites. If no records match &#x60;identifiers&#x60;, returns &#x60;status&#x60; &#x60;success&#x60; with the message &#x60;No records matched the given identifiers.&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param putApiV4AdminDataManagementModelNameChecksumRequest The putApiV4AdminDataManagementModelNameChecksumRequest parameter
     * @return APIEntitiesAdminModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAdminModel putApiV4AdminDataManagementModelNameChecksum(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nullable PutApiV4AdminDataManagementModelNameChecksumRequest putApiV4AdminDataManagementModelNameChecksumRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAdminModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminDataManagementModelNameChecksumRequestCreation(modelName, putApiV4AdminDataManagementModelNameChecksumRequest).body(localVarReturnType);
    }

    /**
     * Recalculate checksums for model records
     * Recalculates checksums for selected records of a specified model, filtered by &#x60;checksum_state&#x60; and &#x60;identifiers&#x60; parameters if provided. The request enqueues a background job to perform the recalculation. Only available to administrators on primary Geo sites. If no records match &#x60;identifiers&#x60;, returns &#x60;status&#x60; &#x60;success&#x60; with the message &#x60;No records matched the given identifiers.&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param putApiV4AdminDataManagementModelNameChecksumRequest The putApiV4AdminDataManagementModelNameChecksumRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAdminModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAdminModel> putApiV4AdminDataManagementModelNameChecksumWithHttpInfo(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nullable PutApiV4AdminDataManagementModelNameChecksumRequest putApiV4AdminDataManagementModelNameChecksumRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAdminModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminDataManagementModelNameChecksumRequestCreation(modelName, putApiV4AdminDataManagementModelNameChecksumRequest).toEntity(localVarReturnType);
    }

    /**
     * Recalculate checksums for model records
     * Recalculates checksums for selected records of a specified model, filtered by &#x60;checksum_state&#x60; and &#x60;identifiers&#x60; parameters if provided. The request enqueues a background job to perform the recalculation. Only available to administrators on primary Geo sites. If no records match &#x60;identifiers&#x60;, returns &#x60;status&#x60; &#x60;success&#x60; with the message &#x60;No records matched the given identifiers.&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param putApiV4AdminDataManagementModelNameChecksumRequest The putApiV4AdminDataManagementModelNameChecksumRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminDataManagementModelNameChecksumWithResponseSpec(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nullable PutApiV4AdminDataManagementModelNameChecksumRequest putApiV4AdminDataManagementModelNameChecksumRequest) throws RestClientResponseException {
        return putApiV4AdminDataManagementModelNameChecksumRequestCreation(modelName, putApiV4AdminDataManagementModelNameChecksumRequest);
    }

    /**
     * Recalculate the checksum of a model record
     * Recalculates the checksum of a specified model record. The checksum value is a representation of the queried model hashed with the md5 or sha256 algorithm. Only available to administrators on primary Geo sites.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param recordIdentifier Unique identifier of the record, as returned by the list endpoint.
     * @return APIEntitiesAdminModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminDataManagementModelNameRecordIdentifierChecksumRequestCreation(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nonnull Integer recordIdentifier) throws RestClientResponseException {
        // verify the required parameter 'modelName' is set
        if (modelName == null) {
            throw new RestClientResponseException("Missing the required parameter 'modelName' when calling putApiV4AdminDataManagementModelNameRecordIdentifierChecksum", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recordIdentifier' is set
        if (recordIdentifier == null) {
            throw new RestClientResponseException("Missing the required parameter 'recordIdentifier' when calling putApiV4AdminDataManagementModelNameRecordIdentifierChecksum", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("model_name", modelName);
        pathParams.put("record_identifier", recordIdentifier);

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

        ParameterizedTypeReference<APIEntitiesAdminModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/data_management/{model_name}/{record_identifier}/checksum", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Recalculate the checksum of a model record
     * Recalculates the checksum of a specified model record. The checksum value is a representation of the queried model hashed with the md5 or sha256 algorithm. Only available to administrators on primary Geo sites.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param recordIdentifier Unique identifier of the record, as returned by the list endpoint.
     * @return APIEntitiesAdminModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAdminModel putApiV4AdminDataManagementModelNameRecordIdentifierChecksum(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nonnull Integer recordIdentifier) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAdminModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminDataManagementModelNameRecordIdentifierChecksumRequestCreation(modelName, recordIdentifier).body(localVarReturnType);
    }

    /**
     * Recalculate the checksum of a model record
     * Recalculates the checksum of a specified model record. The checksum value is a representation of the queried model hashed with the md5 or sha256 algorithm. Only available to administrators on primary Geo sites.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param recordIdentifier Unique identifier of the record, as returned by the list endpoint.
     * @return ResponseEntity&lt;APIEntitiesAdminModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAdminModel> putApiV4AdminDataManagementModelNameRecordIdentifierChecksumWithHttpInfo(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nonnull Integer recordIdentifier) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAdminModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminDataManagementModelNameRecordIdentifierChecksumRequestCreation(modelName, recordIdentifier).toEntity(localVarReturnType);
    }

    /**
     * Recalculate the checksum of a model record
     * Recalculates the checksum of a specified model record. The checksum value is a representation of the queried model hashed with the md5 or sha256 algorithm. Only available to administrators on primary Geo sites.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Model Not Found
     * @param modelName See https://docs.gitlab.com/administration/admin_area/#data-management for the list of allowed values
     * @param recordIdentifier Unique identifier of the record, as returned by the list endpoint.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminDataManagementModelNameRecordIdentifierChecksumWithResponseSpec(@jakarta.annotation.Nonnull String modelName, @jakarta.annotation.Nonnull Integer recordIdentifier) throws RestClientResponseException {
        return putApiV4AdminDataManagementModelNameRecordIdentifierChecksumRequestCreation(modelName, recordIdentifier);
    }
}
