package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import java.io.File;
import ai.gebo.gitlab.client.model.GetApiV4ProjectsIdExportRelationsStatus200Response;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdExportRelationsRequest;

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
public class GroupImportAndExportApi {
    private ApiClient apiClient;

    public GroupImportAndExportApi() {
        this(new ApiClient());
    }

    public GroupImportAndExportApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve a group export download
     * Retrieves the exported archive for a specified group. Time spent on exporting a group might vary depending on the size of the group. This endpoint returns the exported archive when it is available, or a &#x60;404&#x60; message otherwise.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the group.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdExportDownloadRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdExportDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/export/download", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group export download
     * Retrieves the exported archive for a specified group. Time spent on exporting a group might vary depending on the size of the group. This endpoint returns the exported archive when it is available, or a &#x60;404&#x60; message otherwise.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the group.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4GroupsIdExportDownload(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdExportDownloadRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve a group export download
     * Retrieves the exported archive for a specified group. Time spent on exporting a group might vary depending on the size of the group. This endpoint returns the exported archive when it is available, or a &#x60;404&#x60; message otherwise.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4GroupsIdExportDownloadWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdExportDownloadRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group export download
     * Retrieves the exported archive for a specified group. Time spent on exporting a group might vary depending on the size of the group. This endpoint returns the exported archive when it is available, or a &#x60;404&#x60; message otherwise.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdExportDownloadWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getApiV4GroupsIdExportDownloadRequestCreation(id);
    }

    /**
     * Download a relations export for a group
     * Downloads a group relations export file. By default, this endpoint has a rate limit of 100 requests per minute for each user and group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relation Name of the group top-level relation to download or view.
     * @param batched If &#x60;true&#x60;, downloads the export in batches. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @param batchNumber Number of the export batch to download. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdExportRelationsDownloadRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String relation, @jakarta.annotation.Nullable Boolean batched, @jakarta.annotation.Nullable Integer batchNumber) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdExportRelationsDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'relation' is set
        if (relation == null) {
            throw new RestClientResponseException("Missing the required parameter 'relation' when calling getApiV4GroupsIdExportRelationsDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "relation", relation));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "batched", batched));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "batch_number", batchNumber));

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/export_relations/download", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a relations export for a group
     * Downloads a group relations export file. By default, this endpoint has a rate limit of 100 requests per minute for each user and group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relation Name of the group top-level relation to download or view.
     * @param batched If &#x60;true&#x60;, downloads the export in batches. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @param batchNumber Number of the export batch to download. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4GroupsIdExportRelationsDownload(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String relation, @jakarta.annotation.Nullable Boolean batched, @jakarta.annotation.Nullable Integer batchNumber) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdExportRelationsDownloadRequestCreation(id, relation, batched, batchNumber).body(localVarReturnType);
    }

    /**
     * Download a relations export for a group
     * Downloads a group relations export file. By default, this endpoint has a rate limit of 100 requests per minute for each user and group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relation Name of the group top-level relation to download or view.
     * @param batched If &#x60;true&#x60;, downloads the export in batches. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @param batchNumber Number of the export batch to download. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4GroupsIdExportRelationsDownloadWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String relation, @jakarta.annotation.Nullable Boolean batched, @jakarta.annotation.Nullable Integer batchNumber) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdExportRelationsDownloadRequestCreation(id, relation, batched, batchNumber).toEntity(localVarReturnType);
    }

    /**
     * Download a relations export for a group
     * Downloads a group relations export file. By default, this endpoint has a rate limit of 100 requests per minute for each user and group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relation Name of the group top-level relation to download or view.
     * @param batched If &#x60;true&#x60;, downloads the export in batches. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @param batchNumber Number of the export batch to download. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdExportRelationsDownloadWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String relation, @jakarta.annotation.Nullable Boolean batched, @jakarta.annotation.Nullable Integer batchNumber) throws RestClientResponseException {
        return getApiV4GroupsIdExportRelationsDownloadRequestCreation(id, relation, batched, batchNumber);
    }

    /**
     * Retrieve the status of an relations export for a group
     * Retrieves the status of a relations export for a group. The status is one of &#x60;0&#x60; (started), &#x60;1&#x60; (finished), or &#x60;-1&#x60; (failed). Without &#x60;relation&#x60;, returns a list with the status of each relation. With &#x60;relation&#x60;, returns the status of that relation as a single object.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relation Name of the group top-level relation to download or view.
     * @return GetApiV4ProjectsIdExportRelationsStatus200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdExportRelationsStatusRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String relation) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdExportRelationsStatus", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "relation", relation));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<GetApiV4ProjectsIdExportRelationsStatus200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/export_relations/status", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve the status of an relations export for a group
     * Retrieves the status of a relations export for a group. The status is one of &#x60;0&#x60; (started), &#x60;1&#x60; (finished), or &#x60;-1&#x60; (failed). Without &#x60;relation&#x60;, returns a list with the status of each relation. With &#x60;relation&#x60;, returns the status of that relation as a single object.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relation Name of the group top-level relation to download or view.
     * @return GetApiV4ProjectsIdExportRelationsStatus200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GetApiV4ProjectsIdExportRelationsStatus200Response getApiV4GroupsIdExportRelationsStatus(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String relation) throws RestClientResponseException {
        ParameterizedTypeReference<GetApiV4ProjectsIdExportRelationsStatus200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdExportRelationsStatusRequestCreation(id, relation).body(localVarReturnType);
    }

    /**
     * Retrieve the status of an relations export for a group
     * Retrieves the status of a relations export for a group. The status is one of &#x60;0&#x60; (started), &#x60;1&#x60; (finished), or &#x60;-1&#x60; (failed). Without &#x60;relation&#x60;, returns a list with the status of each relation. With &#x60;relation&#x60;, returns the status of that relation as a single object.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relation Name of the group top-level relation to download or view.
     * @return ResponseEntity&lt;GetApiV4ProjectsIdExportRelationsStatus200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GetApiV4ProjectsIdExportRelationsStatus200Response> getApiV4GroupsIdExportRelationsStatusWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String relation) throws RestClientResponseException {
        ParameterizedTypeReference<GetApiV4ProjectsIdExportRelationsStatus200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdExportRelationsStatusRequestCreation(id, relation).toEntity(localVarReturnType);
    }

    /**
     * Retrieve the status of an relations export for a group
     * Retrieves the status of a relations export for a group. The status is one of &#x60;0&#x60; (started), &#x60;1&#x60; (finished), or &#x60;-1&#x60; (failed). Without &#x60;relation&#x60;, returns a list with the status of each relation. With &#x60;relation&#x60;, returns the status of that relation as a single object.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relation Name of the group top-level relation to download or view.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdExportRelationsStatusWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String relation) throws RestClientResponseException {
        return getApiV4GroupsIdExportRelationsStatusRequestCreation(id, relation);
    }

    /**
     * Create a group export
     * Creates a group export for a specified group.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdExportRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdExport", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/export", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group export
     * Creates a group export for a specified group.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsIdExport(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsIdExportRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Create a group export
     * Creates a group export for a specified group.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsIdExportWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdExportRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Create a group export
     * Creates a group export for a specified group.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdExportWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return postApiV4GroupsIdExportRequestCreation(id);
    }

    /**
     * Schedule a relations export for a group
     * Schedules a relations export for a specified group. By default, this endpoint has a rate limit of 6 requests per minute for each user and group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdExportRelationsRequest The postApiV4GroupsIdExportRelationsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdExportRelationsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdExportRelationsRequest postApiV4GroupsIdExportRelationsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdExportRelations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/export_relations", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdExportRelationsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Schedule a relations export for a group
     * Schedules a relations export for a specified group. By default, this endpoint has a rate limit of 6 requests per minute for each user and group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdExportRelationsRequest The postApiV4GroupsIdExportRelationsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsIdExportRelations(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdExportRelationsRequest postApiV4GroupsIdExportRelationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsIdExportRelationsRequestCreation(id, postApiV4GroupsIdExportRelationsRequest).body(localVarReturnType);
    }

    /**
     * Schedule a relations export for a group
     * Schedules a relations export for a specified group. By default, this endpoint has a rate limit of 6 requests per minute for each user and group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdExportRelationsRequest The postApiV4GroupsIdExportRelationsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsIdExportRelationsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdExportRelationsRequest postApiV4GroupsIdExportRelationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdExportRelationsRequestCreation(id, postApiV4GroupsIdExportRelationsRequest).toEntity(localVarReturnType);
    }

    /**
     * Schedule a relations export for a group
     * Schedules a relations export for a specified group. By default, this endpoint has a rate limit of 6 requests per minute for each user and group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdExportRelationsRequest The postApiV4GroupsIdExportRelationsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdExportRelationsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdExportRelationsRequest postApiV4GroupsIdExportRelationsRequest) throws RestClientResponseException {
        return postApiV4GroupsIdExportRelationsRequestCreation(id, postApiV4GroupsIdExportRelationsRequest);
    }

    /**
     * Create a group import
     * Creates a group import. The maximum import file size can be set by the Administrator on GitLab Self-Managed (defaults to &#x60;0&#x60; (unlimited)). Administrators can set it in the Admin area or with the &#x60;max_import_size&#x60; application setting.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param path Group path
     * @param name Group name
     * @param _file The group export file to be imported
     * @param parentId The ID of the parent group that the group will be imported into. Defaults to the current user&#39;s namespace.
     * @param organizationId The ID of the organization that the group will be part of. Defaults to the organization of the parent group if &#x60;parent_id&#x60; is provided, otherwise the current organization.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsImportRequestCreation(@jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable Integer parentId, @jakarta.annotation.Nullable Integer organizationId) throws RestClientResponseException {
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling postApiV4GroupsImport", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling postApiV4GroupsImport", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4GroupsImport", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("path", path);
        formParams.add("name", name);
        formParams.add("file", new FileSystemResource(_file));
        if (parentId != null) {
            formParams.add("parent_id", parentId);
        }
        if (organizationId != null) {
            formParams.add("organization_id", organizationId);
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/import", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group import
     * Creates a group import. The maximum import file size can be set by the Administrator on GitLab Self-Managed (defaults to &#x60;0&#x60; (unlimited)). Administrators can set it in the Admin area or with the &#x60;max_import_size&#x60; application setting.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param path Group path
     * @param name Group name
     * @param _file The group export file to be imported
     * @param parentId The ID of the parent group that the group will be imported into. Defaults to the current user&#39;s namespace.
     * @param organizationId The ID of the organization that the group will be part of. Defaults to the organization of the parent group if &#x60;parent_id&#x60; is provided, otherwise the current organization.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsImport(@jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable Integer parentId, @jakarta.annotation.Nullable Integer organizationId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsImportRequestCreation(path, name, _file, parentId, organizationId).body(localVarReturnType);
    }

    /**
     * Create a group import
     * Creates a group import. The maximum import file size can be set by the Administrator on GitLab Self-Managed (defaults to &#x60;0&#x60; (unlimited)). Administrators can set it in the Admin area or with the &#x60;max_import_size&#x60; application setting.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param path Group path
     * @param name Group name
     * @param _file The group export file to be imported
     * @param parentId The ID of the parent group that the group will be imported into. Defaults to the current user&#39;s namespace.
     * @param organizationId The ID of the organization that the group will be part of. Defaults to the organization of the parent group if &#x60;parent_id&#x60; is provided, otherwise the current organization.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsImportWithHttpInfo(@jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable Integer parentId, @jakarta.annotation.Nullable Integer organizationId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsImportRequestCreation(path, name, _file, parentId, organizationId).toEntity(localVarReturnType);
    }

    /**
     * Create a group import
     * Creates a group import. The maximum import file size can be set by the Administrator on GitLab Self-Managed (defaults to &#x60;0&#x60; (unlimited)). Administrators can set it in the Admin area or with the &#x60;max_import_size&#x60; application setting.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param path Group path
     * @param name Group name
     * @param _file The group export file to be imported
     * @param parentId The ID of the parent group that the group will be imported into. Defaults to the current user&#39;s namespace.
     * @param organizationId The ID of the organization that the group will be part of. Defaults to the organization of the parent group if &#x60;parent_id&#x60; is provided, otherwise the current organization.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsImportWithResponseSpec(@jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable Integer parentId, @jakarta.annotation.Nullable Integer organizationId) throws RestClientResponseException {
        return postApiV4GroupsImportRequestCreation(path, name, _file, parentId, organizationId);
    }

    /**
     * Workhorse authorize the group import upload
     * This feature was introduced in GitLab 12.8
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsImportAuthorizeRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/groups/import/authorize", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize the group import upload
     * This feature was introduced in GitLab 12.8
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsImportAuthorize() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsImportAuthorizeRequestCreation().body(localVarReturnType);
    }

    /**
     * Workhorse authorize the group import upload
     * This feature was introduced in GitLab 12.8
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsImportAuthorizeWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsImportAuthorizeRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize the group import upload
     * This feature was introduced in GitLab 12.8
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsImportAuthorizeWithResponseSpec() throws RestClientResponseException {
        return postApiV4GroupsImportAuthorizeRequestCreation();
    }
}
