package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesProjectExportStatus;
import ai.gebo.gitlab.client.model.APIEntitiesProjectImportStatus;
import ai.gebo.gitlab.client.model.APIEntitiesRelationImportTracker;
import java.io.File;
import ai.gebo.gitlab.client.model.GetApiV4ProjectsIdExportRelationsStatus200Response;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ImportBitbucketRequest;
import ai.gebo.gitlab.client.model.PostApiV4ImportBitbucketServerRequest;
import ai.gebo.gitlab.client.model.PostApiV4ImportGithubCancelRequest;
import ai.gebo.gitlab.client.model.PostApiV4ImportGithubRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdExportRelationsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdExportRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdImportGitRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdImportGithubSyncRestoreRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsImportRequestOverrideParams;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsRemoteImportS3RequestOverrideParams;
import ai.gebo.gitlab.client.model.ProjectEntity;
import ai.gebo.gitlab.client.model.ProjectImportEntity;

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
public class ProjectImportApi {
    private ApiClient apiClient;

    public ProjectImportApi() {
        this(new ApiClient());
    }

    public ProjectImportApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve the status of a project export
     * Retrieves the status of the most recent export for a specified project. The &#x60;export_status&#x60; is one of: &#x60;none&#x60; when no exports are queued, started, finished, or being regenerated; &#x60;queued&#x60; when the export request is received and is in the queue to be processed; &#x60;started&#x60; when the export is in progress, including actions on the resulting file such as sending the notification email or uploading it to a web server; &#x60;finished&#x60; when the export is complete and the user was notified; &#x60;failed&#x60; when the export failed; or &#x60;regeneration_in_progress&#x60; when an export file is available to download and a new export is in progress. The &#x60;_links&#x60; object is returned only when the export is finished. The &#x60;created_at&#x60; attribute is the project creation time, not the export start time.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesProjectExportStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdExportRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdExport", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectExportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/export", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve the status of a project export
     * Retrieves the status of the most recent export for a specified project. The &#x60;export_status&#x60; is one of: &#x60;none&#x60; when no exports are queued, started, finished, or being regenerated; &#x60;queued&#x60; when the export request is received and is in the queue to be processed; &#x60;started&#x60; when the export is in progress, including actions on the resulting file such as sending the notification email or uploading it to a web server; &#x60;finished&#x60; when the export is complete and the user was notified; &#x60;failed&#x60; when the export failed; or &#x60;regeneration_in_progress&#x60; when an export file is available to download and a new export is in progress. The &#x60;_links&#x60; object is returned only when the export is finished. The &#x60;created_at&#x60; attribute is the project creation time, not the export start time.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesProjectExportStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectExportStatus getApiV4ProjectsIdExport(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectExportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdExportRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve the status of a project export
     * Retrieves the status of the most recent export for a specified project. The &#x60;export_status&#x60; is one of: &#x60;none&#x60; when no exports are queued, started, finished, or being regenerated; &#x60;queued&#x60; when the export request is received and is in the queue to be processed; &#x60;started&#x60; when the export is in progress, including actions on the resulting file such as sending the notification email or uploading it to a web server; &#x60;finished&#x60; when the export is complete and the user was notified; &#x60;failed&#x60; when the export failed; or &#x60;regeneration_in_progress&#x60; when an export file is available to download and a new export is in progress. The &#x60;_links&#x60; object is returned only when the export is finished. The &#x60;created_at&#x60; attribute is the project creation time, not the export start time.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesProjectExportStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectExportStatus> getApiV4ProjectsIdExportWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectExportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdExportRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve the status of a project export
     * Retrieves the status of the most recent export for a specified project. The &#x60;export_status&#x60; is one of: &#x60;none&#x60; when no exports are queued, started, finished, or being regenerated; &#x60;queued&#x60; when the export request is received and is in the queue to be processed; &#x60;started&#x60; when the export is in progress, including actions on the resulting file such as sending the notification email or uploading it to a web server; &#x60;finished&#x60; when the export is complete and the user was notified; &#x60;failed&#x60; when the export failed; or &#x60;regeneration_in_progress&#x60; when an export file is available to download and a new export is in progress. The &#x60;_links&#x60; object is returned only when the export is finished. The &#x60;created_at&#x60; attribute is the project creation time, not the export start time.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdExportWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdExportRequestCreation(id);
    }

    /**
     * Download a project export
     * Downloads the most recent export of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdExportDownloadRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdExportDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/export/download", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a project export
     * Downloads the most recent export of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4ProjectsIdExportDownload(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdExportDownloadRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Download a project export
     * Downloads the most recent export of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4ProjectsIdExportDownloadWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdExportDownloadRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Download a project export
     * Downloads the most recent export of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdExportDownloadWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdExportDownloadRequestCreation(id);
    }

    /**
     * Download a relations export for a project
     * Downloads a project relations export file. By default, this endpoint is limited to 100 requests per minute for each user and project. The instance must meet the direct transfer prerequisites, including the &#x60;bulk_import_enabled&#x60; application setting. If the setting is disabled, a &#x60;404&#x60; is returned. This endpoint cannot be used with the file-based group import and export API.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>500</b> - Internal Server Error
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param relation Name of the project top-level relation to download or view.
     * @param batched If &#x60;true&#x60;, downloads the batch specified by &#x60;batch_number&#x60;. Set to &#x60;true&#x60; only for batched exports. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @param batchNumber Number of the export batch to download. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdExportRelationsDownloadRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String relation, @jakarta.annotation.Nullable Boolean batched, @jakarta.annotation.Nullable Integer batchNumber) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdExportRelationsDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'relation' is set
        if (relation == null) {
            throw new RestClientResponseException("Missing the required parameter 'relation' when calling getApiV4ProjectsIdExportRelationsDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
            "application/octet-stream", "application/gzip"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/export_relations/download", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a relations export for a project
     * Downloads a project relations export file. By default, this endpoint is limited to 100 requests per minute for each user and project. The instance must meet the direct transfer prerequisites, including the &#x60;bulk_import_enabled&#x60; application setting. If the setting is disabled, a &#x60;404&#x60; is returned. This endpoint cannot be used with the file-based group import and export API.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>500</b> - Internal Server Error
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param relation Name of the project top-level relation to download or view.
     * @param batched If &#x60;true&#x60;, downloads the batch specified by &#x60;batch_number&#x60;. Set to &#x60;true&#x60; only for batched exports. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @param batchNumber Number of the export batch to download. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4ProjectsIdExportRelationsDownload(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String relation, @jakarta.annotation.Nullable Boolean batched, @jakarta.annotation.Nullable Integer batchNumber) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdExportRelationsDownloadRequestCreation(id, relation, batched, batchNumber).body(localVarReturnType);
    }

    /**
     * Download a relations export for a project
     * Downloads a project relations export file. By default, this endpoint is limited to 100 requests per minute for each user and project. The instance must meet the direct transfer prerequisites, including the &#x60;bulk_import_enabled&#x60; application setting. If the setting is disabled, a &#x60;404&#x60; is returned. This endpoint cannot be used with the file-based group import and export API.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>500</b> - Internal Server Error
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param relation Name of the project top-level relation to download or view.
     * @param batched If &#x60;true&#x60;, downloads the batch specified by &#x60;batch_number&#x60;. Set to &#x60;true&#x60; only for batched exports. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @param batchNumber Number of the export batch to download. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4ProjectsIdExportRelationsDownloadWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String relation, @jakarta.annotation.Nullable Boolean batched, @jakarta.annotation.Nullable Integer batchNumber) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdExportRelationsDownloadRequestCreation(id, relation, batched, batchNumber).toEntity(localVarReturnType);
    }

    /**
     * Download a relations export for a project
     * Downloads a project relations export file. By default, this endpoint is limited to 100 requests per minute for each user and project. The instance must meet the direct transfer prerequisites, including the &#x60;bulk_import_enabled&#x60; application setting. If the setting is disabled, a &#x60;404&#x60; is returned. This endpoint cannot be used with the file-based group import and export API.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>500</b> - Internal Server Error
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param relation Name of the project top-level relation to download or view.
     * @param batched If &#x60;true&#x60;, downloads the batch specified by &#x60;batch_number&#x60;. Set to &#x60;true&#x60; only for batched exports. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @param batchNumber Number of the export batch to download. All or none of &#x60;batched&#x60;, &#x60;batch_number&#x60; must be provided.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdExportRelationsDownloadWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String relation, @jakarta.annotation.Nullable Boolean batched, @jakarta.annotation.Nullable Integer batchNumber) throws RestClientResponseException {
        return getApiV4ProjectsIdExportRelationsDownloadRequestCreation(id, relation, batched, batchNumber);
    }

    /**
     * Retrieve the status of an relations export for a project
     * Retrieves the status of a relations export for a project. The &#x60;status&#x60; is one of &#x60;-2&#x60; (pending), &#x60;0&#x60; (started), &#x60;1&#x60; (finished), or &#x60;-1&#x60; (failed). The instance must meet the direct transfer prerequisites, including the &#x60;bulk_import_enabled&#x60; application setting. If the setting is disabled, a &#x60;404&#x60; is returned. This endpoint cannot be used with the file-based group import and export API. Without &#x60;relation&#x60;, returns a list with the status of each relation. With &#x60;relation&#x60;, returns the status of that relation as a single object.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param relation Name of the project top-level relation to download or view.
     * @return GetApiV4ProjectsIdExportRelationsStatus200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdExportRelationsStatusRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String relation) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdExportRelationsStatus", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/export_relations/status", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve the status of an relations export for a project
     * Retrieves the status of a relations export for a project. The &#x60;status&#x60; is one of &#x60;-2&#x60; (pending), &#x60;0&#x60; (started), &#x60;1&#x60; (finished), or &#x60;-1&#x60; (failed). The instance must meet the direct transfer prerequisites, including the &#x60;bulk_import_enabled&#x60; application setting. If the setting is disabled, a &#x60;404&#x60; is returned. This endpoint cannot be used with the file-based group import and export API. Without &#x60;relation&#x60;, returns a list with the status of each relation. With &#x60;relation&#x60;, returns the status of that relation as a single object.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param relation Name of the project top-level relation to download or view.
     * @return GetApiV4ProjectsIdExportRelationsStatus200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GetApiV4ProjectsIdExportRelationsStatus200Response getApiV4ProjectsIdExportRelationsStatus(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String relation) throws RestClientResponseException {
        ParameterizedTypeReference<GetApiV4ProjectsIdExportRelationsStatus200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdExportRelationsStatusRequestCreation(id, relation).body(localVarReturnType);
    }

    /**
     * Retrieve the status of an relations export for a project
     * Retrieves the status of a relations export for a project. The &#x60;status&#x60; is one of &#x60;-2&#x60; (pending), &#x60;0&#x60; (started), &#x60;1&#x60; (finished), or &#x60;-1&#x60; (failed). The instance must meet the direct transfer prerequisites, including the &#x60;bulk_import_enabled&#x60; application setting. If the setting is disabled, a &#x60;404&#x60; is returned. This endpoint cannot be used with the file-based group import and export API. Without &#x60;relation&#x60;, returns a list with the status of each relation. With &#x60;relation&#x60;, returns the status of that relation as a single object.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param relation Name of the project top-level relation to download or view.
     * @return ResponseEntity&lt;GetApiV4ProjectsIdExportRelationsStatus200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GetApiV4ProjectsIdExportRelationsStatus200Response> getApiV4ProjectsIdExportRelationsStatusWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String relation) throws RestClientResponseException {
        ParameterizedTypeReference<GetApiV4ProjectsIdExportRelationsStatus200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdExportRelationsStatusRequestCreation(id, relation).toEntity(localVarReturnType);
    }

    /**
     * Retrieve the status of an relations export for a project
     * Retrieves the status of a relations export for a project. The &#x60;status&#x60; is one of &#x60;-2&#x60; (pending), &#x60;0&#x60; (started), &#x60;1&#x60; (finished), or &#x60;-1&#x60; (failed). The instance must meet the direct transfer prerequisites, including the &#x60;bulk_import_enabled&#x60; application setting. If the setting is disabled, a &#x60;404&#x60; is returned. This endpoint cannot be used with the file-based group import and export API. Without &#x60;relation&#x60;, returns a list with the status of each relation. With &#x60;relation&#x60;, returns the status of that relation as a single object.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param relation Name of the project top-level relation to download or view.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdExportRelationsStatusWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String relation) throws RestClientResponseException {
        return getApiV4ProjectsIdExportRelationsStatusRequestCreation(id, relation);
    }

    /**
     * Retrieve the status of a project import
     * Retrieves the status of the most recent import for a specified project. The &#x60;import_status&#x60; is one of &#x60;none&#x60;, &#x60;scheduled&#x60;, &#x60;started&#x60;, &#x60;finished&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60;. If the status is &#x60;failed&#x60;, the response includes the import error message in &#x60;import_error&#x60;. If the status is &#x60;failed&#x60;, &#x60;started&#x60;, or &#x60;finished&#x60;, the &#x60;failed_relations&#x60; array might list relations that failed to import because of unrecoverable errors or because retries were exhausted, for example after query timeouts. The &#x60;id&#x60; of each &#x60;failed_relations&#x60; element references the failure record, not the relation. The &#x60;failed_relations&#x60; array is capped at 100 items. For imports from GitHub, a &#x60;stats&#x60; field lists how many objects were already fetched from GitHub and how many were already imported.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesProjectImportStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdImportRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdImport", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectImportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/import", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve the status of a project import
     * Retrieves the status of the most recent import for a specified project. The &#x60;import_status&#x60; is one of &#x60;none&#x60;, &#x60;scheduled&#x60;, &#x60;started&#x60;, &#x60;finished&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60;. If the status is &#x60;failed&#x60;, the response includes the import error message in &#x60;import_error&#x60;. If the status is &#x60;failed&#x60;, &#x60;started&#x60;, or &#x60;finished&#x60;, the &#x60;failed_relations&#x60; array might list relations that failed to import because of unrecoverable errors or because retries were exhausted, for example after query timeouts. The &#x60;id&#x60; of each &#x60;failed_relations&#x60; element references the failure record, not the relation. The &#x60;failed_relations&#x60; array is capped at 100 items. For imports from GitHub, a &#x60;stats&#x60; field lists how many objects were already fetched from GitHub and how many were already imported.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesProjectImportStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectImportStatus getApiV4ProjectsIdImport(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectImportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdImportRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve the status of a project import
     * Retrieves the status of the most recent import for a specified project. The &#x60;import_status&#x60; is one of &#x60;none&#x60;, &#x60;scheduled&#x60;, &#x60;started&#x60;, &#x60;finished&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60;. If the status is &#x60;failed&#x60;, the response includes the import error message in &#x60;import_error&#x60;. If the status is &#x60;failed&#x60;, &#x60;started&#x60;, or &#x60;finished&#x60;, the &#x60;failed_relations&#x60; array might list relations that failed to import because of unrecoverable errors or because retries were exhausted, for example after query timeouts. The &#x60;id&#x60; of each &#x60;failed_relations&#x60; element references the failure record, not the relation. The &#x60;failed_relations&#x60; array is capped at 100 items. For imports from GitHub, a &#x60;stats&#x60; field lists how many objects were already fetched from GitHub and how many were already imported.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesProjectImportStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectImportStatus> getApiV4ProjectsIdImportWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectImportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdImportRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve the status of a project import
     * Retrieves the status of the most recent import for a specified project. The &#x60;import_status&#x60; is one of &#x60;none&#x60;, &#x60;scheduled&#x60;, &#x60;started&#x60;, &#x60;finished&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60;. If the status is &#x60;failed&#x60;, the response includes the import error message in &#x60;import_error&#x60;. If the status is &#x60;failed&#x60;, &#x60;started&#x60;, or &#x60;finished&#x60;, the &#x60;failed_relations&#x60; array might list relations that failed to import because of unrecoverable errors or because retries were exhausted, for example after query timeouts. The &#x60;id&#x60; of each &#x60;failed_relations&#x60; element references the failure record, not the relation. The &#x60;failed_relations&#x60; array is capped at 100 items. For imports from GitHub, a &#x60;stats&#x60; field lists how many objects were already fetched from GitHub and how many were already imported.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdImportWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdImportRequestCreation(id);
    }

    /**
     * Retrieve the status of a project resource import
     * Retrieves the status of the most recent relation import for a specified project. Because only one relation import can be scheduled at a time, you can use this endpoint to check whether the previous import completed successfully. The &#x60;status&#x60; is one of &#x60;created&#x60; when the import is scheduled but not started, &#x60;started&#x60; when the import is being processed, &#x60;finished&#x60; when the import is complete, or &#x60;failed&#x60; when the import could not be completed. This feature was introduced in GitLab 16.11.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @return List&lt;APIEntitiesRelationImportTracker&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRelationImportsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRelationImports", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesRelationImportTracker>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/relation-imports", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve the status of a project resource import
     * Retrieves the status of the most recent relation import for a specified project. Because only one relation import can be scheduled at a time, you can use this endpoint to check whether the previous import completed successfully. The &#x60;status&#x60; is one of &#x60;created&#x60; when the import is scheduled but not started, &#x60;started&#x60; when the import is being processed, &#x60;finished&#x60; when the import is complete, or &#x60;failed&#x60; when the import could not be completed. This feature was introduced in GitLab 16.11.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @return List&lt;APIEntitiesRelationImportTracker&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesRelationImportTracker> getApiV4ProjectsIdRelationImports(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesRelationImportTracker>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRelationImportsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve the status of a project resource import
     * Retrieves the status of the most recent relation import for a specified project. Because only one relation import can be scheduled at a time, you can use this endpoint to check whether the previous import completed successfully. The &#x60;status&#x60; is one of &#x60;created&#x60; when the import is scheduled but not started, &#x60;started&#x60; when the import is being processed, &#x60;finished&#x60; when the import is complete, or &#x60;failed&#x60; when the import could not be completed. This feature was introduced in GitLab 16.11.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;List&lt;APIEntitiesRelationImportTracker&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesRelationImportTracker>> getApiV4ProjectsIdRelationImportsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesRelationImportTracker>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRelationImportsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve the status of a project resource import
     * Retrieves the status of the most recent relation import for a specified project. Because only one relation import can be scheduled at a time, you can use this endpoint to check whether the previous import completed successfully. The &#x60;status&#x60; is one of &#x60;created&#x60; when the import is scheduled but not started, &#x60;started&#x60; when the import is being processed, &#x60;finished&#x60; when the import is complete, or &#x60;failed&#x60; when the import could not be completed. This feature was introduced in GitLab 16.11.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRelationImportsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdRelationImportsRequestCreation(id);
    }

    /**
     * Import repository from Bitbucket Cloud
     * Imports a repository from Bitbucket Cloud to GitLab. Prerequisites: - The prerequisites for Bitbucket Cloud importer. You must have a Bitbucket Cloud API token with the &#x60;read:repository:bitbucket&#x60; and &#x60;read:pullrequest:bitbucket&#x60; scopes. This endpoint shares a rate limit with starting imports from the UI and with file-based project imports. By default, the limit is six requests per minute for each user. An administrator can change this limit in the application settings. This feature was introduced in GitLab 17.0.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportBitbucketRequest The postApiV4ImportBitbucketRequest parameter
     * @return ProjectImportEntity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ImportBitbucketRequestCreation(@jakarta.annotation.Nonnull PostApiV4ImportBitbucketRequest postApiV4ImportBitbucketRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4ImportBitbucketRequest' is set
        if (postApiV4ImportBitbucketRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ImportBitbucketRequest' when calling postApiV4ImportBitbucket", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ProjectImportEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/import/bitbucket", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ImportBitbucketRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Import repository from Bitbucket Cloud
     * Imports a repository from Bitbucket Cloud to GitLab. Prerequisites: - The prerequisites for Bitbucket Cloud importer. You must have a Bitbucket Cloud API token with the &#x60;read:repository:bitbucket&#x60; and &#x60;read:pullrequest:bitbucket&#x60; scopes. This endpoint shares a rate limit with starting imports from the UI and with file-based project imports. By default, the limit is six requests per minute for each user. An administrator can change this limit in the application settings. This feature was introduced in GitLab 17.0.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportBitbucketRequest The postApiV4ImportBitbucketRequest parameter
     * @return ProjectImportEntity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProjectImportEntity postApiV4ImportBitbucket(@jakarta.annotation.Nonnull PostApiV4ImportBitbucketRequest postApiV4ImportBitbucketRequest) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectImportEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ImportBitbucketRequestCreation(postApiV4ImportBitbucketRequest).body(localVarReturnType);
    }

    /**
     * Import repository from Bitbucket Cloud
     * Imports a repository from Bitbucket Cloud to GitLab. Prerequisites: - The prerequisites for Bitbucket Cloud importer. You must have a Bitbucket Cloud API token with the &#x60;read:repository:bitbucket&#x60; and &#x60;read:pullrequest:bitbucket&#x60; scopes. This endpoint shares a rate limit with starting imports from the UI and with file-based project imports. By default, the limit is six requests per minute for each user. An administrator can change this limit in the application settings. This feature was introduced in GitLab 17.0.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportBitbucketRequest The postApiV4ImportBitbucketRequest parameter
     * @return ResponseEntity&lt;ProjectImportEntity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectImportEntity> postApiV4ImportBitbucketWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4ImportBitbucketRequest postApiV4ImportBitbucketRequest) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectImportEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ImportBitbucketRequestCreation(postApiV4ImportBitbucketRequest).toEntity(localVarReturnType);
    }

    /**
     * Import repository from Bitbucket Cloud
     * Imports a repository from Bitbucket Cloud to GitLab. Prerequisites: - The prerequisites for Bitbucket Cloud importer. You must have a Bitbucket Cloud API token with the &#x60;read:repository:bitbucket&#x60; and &#x60;read:pullrequest:bitbucket&#x60; scopes. This endpoint shares a rate limit with starting imports from the UI and with file-based project imports. By default, the limit is six requests per minute for each user. An administrator can change this limit in the application settings. This feature was introduced in GitLab 17.0.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportBitbucketRequest The postApiV4ImportBitbucketRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ImportBitbucketWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4ImportBitbucketRequest postApiV4ImportBitbucketRequest) throws RestClientResponseException {
        return postApiV4ImportBitbucketRequestCreation(postApiV4ImportBitbucketRequest);
    }

    /**
     * Import repository from Bitbucket Server
     * Imports a repository from Bitbucket Server to GitLab. The Bitbucket Project Key is only used for finding the repository in Bitbucket. You must specify a &#x60;new_namespace&#x60; if you want to import the repository to a GitLab group. If you do not specify &#x60;new_namespace&#x60;, the project imports to your personal user namespace. This endpoint shares a rate limit with starting imports from the UI and with file-based project imports. By default, the limit is six requests per minute for each user. An administrator can change this limit in the application settings.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportBitbucketServerRequest The postApiV4ImportBitbucketServerRequest parameter
     * @return ProjectEntity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ImportBitbucketServerRequestCreation(@jakarta.annotation.Nonnull PostApiV4ImportBitbucketServerRequest postApiV4ImportBitbucketServerRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4ImportBitbucketServerRequest' is set
        if (postApiV4ImportBitbucketServerRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ImportBitbucketServerRequest' when calling postApiV4ImportBitbucketServer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ProjectEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/import/bitbucket_server", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ImportBitbucketServerRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Import repository from Bitbucket Server
     * Imports a repository from Bitbucket Server to GitLab. The Bitbucket Project Key is only used for finding the repository in Bitbucket. You must specify a &#x60;new_namespace&#x60; if you want to import the repository to a GitLab group. If you do not specify &#x60;new_namespace&#x60;, the project imports to your personal user namespace. This endpoint shares a rate limit with starting imports from the UI and with file-based project imports. By default, the limit is six requests per minute for each user. An administrator can change this limit in the application settings.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportBitbucketServerRequest The postApiV4ImportBitbucketServerRequest parameter
     * @return ProjectEntity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProjectEntity postApiV4ImportBitbucketServer(@jakarta.annotation.Nonnull PostApiV4ImportBitbucketServerRequest postApiV4ImportBitbucketServerRequest) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ImportBitbucketServerRequestCreation(postApiV4ImportBitbucketServerRequest).body(localVarReturnType);
    }

    /**
     * Import repository from Bitbucket Server
     * Imports a repository from Bitbucket Server to GitLab. The Bitbucket Project Key is only used for finding the repository in Bitbucket. You must specify a &#x60;new_namespace&#x60; if you want to import the repository to a GitLab group. If you do not specify &#x60;new_namespace&#x60;, the project imports to your personal user namespace. This endpoint shares a rate limit with starting imports from the UI and with file-based project imports. By default, the limit is six requests per minute for each user. An administrator can change this limit in the application settings.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportBitbucketServerRequest The postApiV4ImportBitbucketServerRequest parameter
     * @return ResponseEntity&lt;ProjectEntity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectEntity> postApiV4ImportBitbucketServerWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4ImportBitbucketServerRequest postApiV4ImportBitbucketServerRequest) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ImportBitbucketServerRequestCreation(postApiV4ImportBitbucketServerRequest).toEntity(localVarReturnType);
    }

    /**
     * Import repository from Bitbucket Server
     * Imports a repository from Bitbucket Server to GitLab. The Bitbucket Project Key is only used for finding the repository in Bitbucket. You must specify a &#x60;new_namespace&#x60; if you want to import the repository to a GitLab group. If you do not specify &#x60;new_namespace&#x60;, the project imports to your personal user namespace. This endpoint shares a rate limit with starting imports from the UI and with file-based project imports. By default, the limit is six requests per minute for each user. An administrator can change this limit in the application settings.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportBitbucketServerRequest The postApiV4ImportBitbucketServerRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ImportBitbucketServerWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4ImportBitbucketServerRequest postApiV4ImportBitbucketServerRequest) throws RestClientResponseException {
        return postApiV4ImportBitbucketServerRequestCreation(postApiV4ImportBitbucketServerRequest);
    }

    /**
     * Import a repository from GitHub
     * Imports a repository from GitHub to GitLab. This endpoint shares a rate limit with starting imports from the UI and with file-based project imports. By default, the limit is six requests per minute for each user. An administrator can change this limit in the application settings. The namespace set in &#x60;target_namespace&#x60; must exist, and must be your user namespace or a group you have the Maintainer or Owner role for. The &#x60;optional_stages&#x60; hash accepts the keys &#x60;attachments_import&#x60;, to import Markdown attachments, &#x60;collaborators_import&#x60;, to import direct repository collaborators who are not outside collaborators, and &#x60;continuous_sync&#x60;, to keep the project updated from GitHub after the import. &#x60;continuous_sync&#x60; is only available for github.com. A private GitHub repository is imported as a private project. A public GitHub repository takes the visibility of the target namespace. If that visibility level is restricted, the project uses the default project visibility instead.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportGithubRequest The postApiV4ImportGithubRequest parameter
     * @return ProjectEntity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ImportGithubRequestCreation(@jakarta.annotation.Nonnull PostApiV4ImportGithubRequest postApiV4ImportGithubRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4ImportGithubRequest' is set
        if (postApiV4ImportGithubRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ImportGithubRequest' when calling postApiV4ImportGithub", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ProjectEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/import/github", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ImportGithubRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Import a repository from GitHub
     * Imports a repository from GitHub to GitLab. This endpoint shares a rate limit with starting imports from the UI and with file-based project imports. By default, the limit is six requests per minute for each user. An administrator can change this limit in the application settings. The namespace set in &#x60;target_namespace&#x60; must exist, and must be your user namespace or a group you have the Maintainer or Owner role for. The &#x60;optional_stages&#x60; hash accepts the keys &#x60;attachments_import&#x60;, to import Markdown attachments, &#x60;collaborators_import&#x60;, to import direct repository collaborators who are not outside collaborators, and &#x60;continuous_sync&#x60;, to keep the project updated from GitHub after the import. &#x60;continuous_sync&#x60; is only available for github.com. A private GitHub repository is imported as a private project. A public GitHub repository takes the visibility of the target namespace. If that visibility level is restricted, the project uses the default project visibility instead.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportGithubRequest The postApiV4ImportGithubRequest parameter
     * @return ProjectEntity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProjectEntity postApiV4ImportGithub(@jakarta.annotation.Nonnull PostApiV4ImportGithubRequest postApiV4ImportGithubRequest) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ImportGithubRequestCreation(postApiV4ImportGithubRequest).body(localVarReturnType);
    }

    /**
     * Import a repository from GitHub
     * Imports a repository from GitHub to GitLab. This endpoint shares a rate limit with starting imports from the UI and with file-based project imports. By default, the limit is six requests per minute for each user. An administrator can change this limit in the application settings. The namespace set in &#x60;target_namespace&#x60; must exist, and must be your user namespace or a group you have the Maintainer or Owner role for. The &#x60;optional_stages&#x60; hash accepts the keys &#x60;attachments_import&#x60;, to import Markdown attachments, &#x60;collaborators_import&#x60;, to import direct repository collaborators who are not outside collaborators, and &#x60;continuous_sync&#x60;, to keep the project updated from GitHub after the import. &#x60;continuous_sync&#x60; is only available for github.com. A private GitHub repository is imported as a private project. A public GitHub repository takes the visibility of the target namespace. If that visibility level is restricted, the project uses the default project visibility instead.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportGithubRequest The postApiV4ImportGithubRequest parameter
     * @return ResponseEntity&lt;ProjectEntity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectEntity> postApiV4ImportGithubWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4ImportGithubRequest postApiV4ImportGithubRequest) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ImportGithubRequestCreation(postApiV4ImportGithubRequest).toEntity(localVarReturnType);
    }

    /**
     * Import a repository from GitHub
     * Imports a repository from GitHub to GitLab. This endpoint shares a rate limit with starting imports from the UI and with file-based project imports. By default, the limit is six requests per minute for each user. An administrator can change this limit in the application settings. The namespace set in &#x60;target_namespace&#x60; must exist, and must be your user namespace or a group you have the Maintainer or Owner role for. The &#x60;optional_stages&#x60; hash accepts the keys &#x60;attachments_import&#x60;, to import Markdown attachments, &#x60;collaborators_import&#x60;, to import direct repository collaborators who are not outside collaborators, and &#x60;continuous_sync&#x60;, to keep the project updated from GitHub after the import. &#x60;continuous_sync&#x60; is only available for github.com. A private GitHub repository is imported as a private project. A public GitHub repository takes the visibility of the target namespace. If that visibility level is restricted, the project uses the default project visibility instead.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportGithubRequest The postApiV4ImportGithubRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ImportGithubWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4ImportGithubRequest postApiV4ImportGithubRequest) throws RestClientResponseException {
        return postApiV4ImportGithubRequestCreation(postApiV4ImportGithubRequest);
    }

    /**
     * Cancel a GitHub project import
     * Cancels an in-progress import of a GitHub project to GitLab.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportGithubCancelRequest The postApiV4ImportGithubCancelRequest parameter
     * @return ProjectImportEntity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ImportGithubCancelRequestCreation(@jakarta.annotation.Nonnull PostApiV4ImportGithubCancelRequest postApiV4ImportGithubCancelRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4ImportGithubCancelRequest' is set
        if (postApiV4ImportGithubCancelRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ImportGithubCancelRequest' when calling postApiV4ImportGithubCancel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ProjectImportEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/import/github/cancel", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ImportGithubCancelRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Cancel a GitHub project import
     * Cancels an in-progress import of a GitHub project to GitLab.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportGithubCancelRequest The postApiV4ImportGithubCancelRequest parameter
     * @return ProjectImportEntity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProjectImportEntity postApiV4ImportGithubCancel(@jakarta.annotation.Nonnull PostApiV4ImportGithubCancelRequest postApiV4ImportGithubCancelRequest) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectImportEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ImportGithubCancelRequestCreation(postApiV4ImportGithubCancelRequest).body(localVarReturnType);
    }

    /**
     * Cancel a GitHub project import
     * Cancels an in-progress import of a GitHub project to GitLab.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportGithubCancelRequest The postApiV4ImportGithubCancelRequest parameter
     * @return ResponseEntity&lt;ProjectImportEntity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectImportEntity> postApiV4ImportGithubCancelWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4ImportGithubCancelRequest postApiV4ImportGithubCancelRequest) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectImportEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ImportGithubCancelRequestCreation(postApiV4ImportGithubCancelRequest).toEntity(localVarReturnType);
    }

    /**
     * Cancel a GitHub project import
     * Cancels an in-progress import of a GitHub project to GitLab.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param postApiV4ImportGithubCancelRequest The postApiV4ImportGithubCancelRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ImportGithubCancelWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4ImportGithubCancelRequest postApiV4ImportGithubCancelRequest) throws RestClientResponseException {
        return postApiV4ImportGithubCancelRequestCreation(postApiV4ImportGithubCancelRequest);
    }

    /**
     * Export a project
     * Exports a project. Use the &#x60;upload&#x60; hash parameter to upload the exported project to a web server or any S3-compatible platform. Uploads support only binary data and are sent with the &#x60;Content-Type: application/gzip&#x60; header, so a pre-signed upload URL must include this header in its signature. The export can take some time, so the upload URL must not have a short expiration time. Because of a known issue, uploads to Amazon S3 cannot be larger than 5 GB. The maximum export file size is set by an administrator with the &#x60;max_export_size&#x60; application setting. Default is &#x60;0&#x60; (unlimited). Excluded &#x60;lfs_objects&#x60; and &#x60;uploads&#x60; relations do not count toward this limit.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdExportRequest The postApiV4ProjectsIdExportRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdExportRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdExportRequest postApiV4ProjectsIdExportRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdExport", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/export", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdExportRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Export a project
     * Exports a project. Use the &#x60;upload&#x60; hash parameter to upload the exported project to a web server or any S3-compatible platform. Uploads support only binary data and are sent with the &#x60;Content-Type: application/gzip&#x60; header, so a pre-signed upload URL must include this header in its signature. The export can take some time, so the upload URL must not have a short expiration time. Because of a known issue, uploads to Amazon S3 cannot be larger than 5 GB. The maximum export file size is set by an administrator with the &#x60;max_export_size&#x60; application setting. Default is &#x60;0&#x60; (unlimited). Excluded &#x60;lfs_objects&#x60; and &#x60;uploads&#x60; relations do not count toward this limit.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdExportRequest The postApiV4ProjectsIdExportRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdExport(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdExportRequest postApiV4ProjectsIdExportRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdExportRequestCreation(id, postApiV4ProjectsIdExportRequest).body(localVarReturnType);
    }

    /**
     * Export a project
     * Exports a project. Use the &#x60;upload&#x60; hash parameter to upload the exported project to a web server or any S3-compatible platform. Uploads support only binary data and are sent with the &#x60;Content-Type: application/gzip&#x60; header, so a pre-signed upload URL must include this header in its signature. The export can take some time, so the upload URL must not have a short expiration time. Because of a known issue, uploads to Amazon S3 cannot be larger than 5 GB. The maximum export file size is set by an administrator with the &#x60;max_export_size&#x60; application setting. Default is &#x60;0&#x60; (unlimited). Excluded &#x60;lfs_objects&#x60; and &#x60;uploads&#x60; relations do not count toward this limit.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdExportRequest The postApiV4ProjectsIdExportRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdExportWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdExportRequest postApiV4ProjectsIdExportRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdExportRequestCreation(id, postApiV4ProjectsIdExportRequest).toEntity(localVarReturnType);
    }

    /**
     * Export a project
     * Exports a project. Use the &#x60;upload&#x60; hash parameter to upload the exported project to a web server or any S3-compatible platform. Uploads support only binary data and are sent with the &#x60;Content-Type: application/gzip&#x60; header, so a pre-signed upload URL must include this header in its signature. The export can take some time, so the upload URL must not have a short expiration time. Because of a known issue, uploads to Amazon S3 cannot be larger than 5 GB. The maximum export file size is set by an administrator with the &#x60;max_export_size&#x60; application setting. Default is &#x60;0&#x60; (unlimited). Excluded &#x60;lfs_objects&#x60; and &#x60;uploads&#x60; relations do not count toward this limit.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdExportRequest The postApiV4ProjectsIdExportRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdExportWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdExportRequest postApiV4ProjectsIdExportRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdExportRequestCreation(id, postApiV4ProjectsIdExportRequest);
    }

    /**
     * Schedule a relations export for a project
     * Schedules a relations export for a specified project. By default, this endpoint is limited to 6 requests per minute for each user and project. The instance must meet the direct transfer prerequisites, including the &#x60;bulk_import_enabled&#x60; application setting. If the setting is disabled, a &#x60;404&#x60; is returned. This endpoint cannot be used with the file-based group import and export API.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdExportRelationsRequest The postApiV4ProjectsIdExportRelationsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdExportRelationsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdExportRelationsRequest postApiV4ProjectsIdExportRelationsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdExportRelations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/export_relations", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdExportRelationsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Schedule a relations export for a project
     * Schedules a relations export for a specified project. By default, this endpoint is limited to 6 requests per minute for each user and project. The instance must meet the direct transfer prerequisites, including the &#x60;bulk_import_enabled&#x60; application setting. If the setting is disabled, a &#x60;404&#x60; is returned. This endpoint cannot be used with the file-based group import and export API.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdExportRelationsRequest The postApiV4ProjectsIdExportRelationsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdExportRelations(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdExportRelationsRequest postApiV4ProjectsIdExportRelationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdExportRelationsRequestCreation(id, postApiV4ProjectsIdExportRelationsRequest).body(localVarReturnType);
    }

    /**
     * Schedule a relations export for a project
     * Schedules a relations export for a specified project. By default, this endpoint is limited to 6 requests per minute for each user and project. The instance must meet the direct transfer prerequisites, including the &#x60;bulk_import_enabled&#x60; application setting. If the setting is disabled, a &#x60;404&#x60; is returned. This endpoint cannot be used with the file-based group import and export API.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdExportRelationsRequest The postApiV4ProjectsIdExportRelationsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdExportRelationsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdExportRelationsRequest postApiV4ProjectsIdExportRelationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdExportRelationsRequestCreation(id, postApiV4ProjectsIdExportRelationsRequest).toEntity(localVarReturnType);
    }

    /**
     * Schedule a relations export for a project
     * Schedules a relations export for a specified project. By default, this endpoint is limited to 6 requests per minute for each user and project. The instance must meet the direct transfer prerequisites, including the &#x60;bulk_import_enabled&#x60; application setting. If the setting is disabled, a &#x60;404&#x60; is returned. This endpoint cannot be used with the file-based group import and export API.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdExportRelationsRequest The postApiV4ProjectsIdExportRelationsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdExportRelationsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdExportRelationsRequest postApiV4ProjectsIdExportRelationsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdExportRelationsRequestCreation(id, postApiV4ProjectsIdExportRelationsRequest);
    }

    /**
     * Receive a GitHub webhook delivery for an OAuth-connected repository
     * Verifies the webhook signature and, once available, triggers repository reconciliation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the project
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdGithubWebhooksRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdGithubWebhooks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/github/webhooks", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Receive a GitHub webhook delivery for an OAuth-connected repository
     * Verifies the webhook signature and, once available, triggers repository reconciliation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the project
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdGithubWebhooks(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdGithubWebhooksRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Receive a GitHub webhook delivery for an OAuth-connected repository
     * Verifies the webhook signature and, once available, triggers repository reconciliation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the project
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdGithubWebhooksWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdGithubWebhooksRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Receive a GitHub webhook delivery for an OAuth-connected repository
     * Verifies the webhook signature and, once available, triggers repository reconciliation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id The ID of the project
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdGithubWebhooksWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4ProjectsIdGithubWebhooksRequestCreation(id);
    }

    /**
     * Import a project from a Git URL
     * Imports a project from a specified Git URL. This feature was introduced in GitLab 18.10.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable Entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdImportGitRequest The postApiV4ProjectsIdImportGitRequest parameter
     * @return APIEntitiesProjectImportStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdImportGitRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdImportGitRequest postApiV4ProjectsIdImportGitRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdImportGit", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdImportGitRequest' is set
        if (postApiV4ProjectsIdImportGitRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdImportGitRequest' when calling postApiV4ProjectsIdImportGit", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectImportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/import/git", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdImportGitRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Import a project from a Git URL
     * Imports a project from a specified Git URL. This feature was introduced in GitLab 18.10.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable Entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdImportGitRequest The postApiV4ProjectsIdImportGitRequest parameter
     * @return APIEntitiesProjectImportStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectImportStatus postApiV4ProjectsIdImportGit(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdImportGitRequest postApiV4ProjectsIdImportGitRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectImportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdImportGitRequestCreation(id, postApiV4ProjectsIdImportGitRequest).body(localVarReturnType);
    }

    /**
     * Import a project from a Git URL
     * Imports a project from a specified Git URL. This feature was introduced in GitLab 18.10.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable Entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdImportGitRequest The postApiV4ProjectsIdImportGitRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectImportStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectImportStatus> postApiV4ProjectsIdImportGitWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdImportGitRequest postApiV4ProjectsIdImportGitRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectImportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdImportGitRequestCreation(id, postApiV4ProjectsIdImportGitRequest).toEntity(localVarReturnType);
    }

    /**
     * Import a project from a Git URL
     * Imports a project from a specified Git URL. This feature was introduced in GitLab 18.10.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable Entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdImportGitRequest The postApiV4ProjectsIdImportGitRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdImportGitWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdImportGitRequest postApiV4ProjectsIdImportGitRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdImportGitRequestCreation(id, postApiV4ProjectsIdImportGitRequest);
    }

    /**
     * Restore GitHub updates with a new personal access token
     * Reconnects a project that was imported from GitHub with a personal access token and with continuous sync, after GitHub updates were disconnected because the token was revoked or expired. The new token replaces the stored one. It must be able to read the GitHub repository the project was imported from. If the token cannot manage repository webhooks, GitHub updates are restored without a webhook and the response has a &#x60;warning&#x60;. Changes are then checked on a schedule. This endpoint shares a rate limit with starting imports. Requires the Maintainer role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdImportGithubSyncRestoreRequest The postApiV4ProjectsIdImportGithubSyncRestoreRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdImportGithubSyncRestoreRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdImportGithubSyncRestoreRequest postApiV4ProjectsIdImportGithubSyncRestoreRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdImportGithubSyncRestore", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdImportGithubSyncRestoreRequest' is set
        if (postApiV4ProjectsIdImportGithubSyncRestoreRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdImportGithubSyncRestoreRequest' when calling postApiV4ProjectsIdImportGithubSyncRestore", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/import/github/sync/restore", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdImportGithubSyncRestoreRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Restore GitHub updates with a new personal access token
     * Reconnects a project that was imported from GitHub with a personal access token and with continuous sync, after GitHub updates were disconnected because the token was revoked or expired. The new token replaces the stored one. It must be able to read the GitHub repository the project was imported from. If the token cannot manage repository webhooks, GitHub updates are restored without a webhook and the response has a &#x60;warning&#x60;. Changes are then checked on a schedule. This endpoint shares a rate limit with starting imports. Requires the Maintainer role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdImportGithubSyncRestoreRequest The postApiV4ProjectsIdImportGithubSyncRestoreRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdImportGithubSyncRestore(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdImportGithubSyncRestoreRequest postApiV4ProjectsIdImportGithubSyncRestoreRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdImportGithubSyncRestoreRequestCreation(id, postApiV4ProjectsIdImportGithubSyncRestoreRequest).body(localVarReturnType);
    }

    /**
     * Restore GitHub updates with a new personal access token
     * Reconnects a project that was imported from GitHub with a personal access token and with continuous sync, after GitHub updates were disconnected because the token was revoked or expired. The new token replaces the stored one. It must be able to read the GitHub repository the project was imported from. If the token cannot manage repository webhooks, GitHub updates are restored without a webhook and the response has a &#x60;warning&#x60;. Changes are then checked on a schedule. This endpoint shares a rate limit with starting imports. Requires the Maintainer role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdImportGithubSyncRestoreRequest The postApiV4ProjectsIdImportGithubSyncRestoreRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdImportGithubSyncRestoreWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdImportGithubSyncRestoreRequest postApiV4ProjectsIdImportGithubSyncRestoreRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdImportGithubSyncRestoreRequestCreation(id, postApiV4ProjectsIdImportGithubSyncRestoreRequest).toEntity(localVarReturnType);
    }

    /**
     * Restore GitHub updates with a new personal access token
     * Reconnects a project that was imported from GitHub with a personal access token and with continuous sync, after GitHub updates were disconnected because the token was revoked or expired. The new token replaces the stored one. It must be able to read the GitHub repository the project was imported from. If the token cannot manage repository webhooks, GitHub updates are restored without a webhook and the response has a &#x60;warning&#x60;. Changes are then checked on a schedule. This endpoint shares a rate limit with starting imports. Requires the Maintainer role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdImportGithubSyncRestoreRequest The postApiV4ProjectsIdImportGithubSyncRestoreRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdImportGithubSyncRestoreWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdImportGithubSyncRestoreRequest postApiV4ProjectsIdImportGithubSyncRestoreRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdImportGithubSyncRestoreRequestCreation(id, postApiV4ProjectsIdImportGithubSyncRestoreRequest);
    }

    /**
     * Import a project from a local archive
     * Imports a project from a local archive. The maximum import file size is set by an administrator with the &#x60;max_import_size&#x60; application setting. Default is &#x60;0&#x60; (unlimited).
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param path The new project path and name
     * @param _file The project export file to be imported
     * @param name The name of the project to be imported. Defaults to the path of the project if not provided.
     * @param namespace (deprecated) The ID or path of the namespace to import the project to. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. Use &#x60;namespace_id&#x60; or &#x60;namespace_path&#x60; instead. Mutually exclusive with &#x60;namespace_id&#x60;, &#x60;namespace_path&#x60;.
     * @param namespaceId The ID of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_path&#x60;.
     * @param namespacePath The path of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_id&#x60;.
     * @param overwrite If there is a project in the same namespace and with the same name overwrite it
     * @param overrideParams The overrideParams parameter
     * @param filePath Path to locally stored body (generated by Workhorse)
     * @param fileName Real filename as send in Content-Disposition (generated by Workhorse)
     * @param fileType Real content type as send in Content-Type (generated by Workhorse)
     * @param fileSize Real size of file (generated by Workhorse)
     * @param fileMd5 MD5 checksum of the file (generated by Workhorse)
     * @param fileSha1 SHA1 checksum of the file (generated by Workhorse)
     * @param fileSha256 SHA256 checksum of the file (generated by Workhorse)
     * @param fileEtag Etag of the file (generated by Workhorse)
     * @param fileRemoteId Remote_id of the file (generated by Workhorse)
     * @param fileRemoteUrl Remote_url of the file (generated by Workhorse)
     * @return APIEntitiesProjectImportStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsImportRequestCreation(@jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String namespace, @jakarta.annotation.Nullable Integer namespaceId, @jakarta.annotation.Nullable String namespacePath, @jakarta.annotation.Nullable Boolean overwrite, @jakarta.annotation.Nullable PostApiV4ProjectsImportRequestOverrideParams overrideParams, @jakarta.annotation.Nullable String filePath, @jakarta.annotation.Nullable String fileName, @jakarta.annotation.Nullable String fileType, @jakarta.annotation.Nullable Integer fileSize, @jakarta.annotation.Nullable String fileMd5, @jakarta.annotation.Nullable String fileSha1, @jakarta.annotation.Nullable String fileSha256, @jakarta.annotation.Nullable String fileEtag, @jakarta.annotation.Nullable String fileRemoteId, @jakarta.annotation.Nullable String fileRemoteUrl) throws RestClientResponseException {
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling postApiV4ProjectsImport", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4ProjectsImport", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("path", path);
        formParams.add("file", new FileSystemResource(_file));
        if (name != null) {
            formParams.add("name", name);
        }
        if (namespace != null) {
            formParams.add("namespace", namespace);
        }
        if (namespaceId != null) {
            formParams.add("namespace_id", namespaceId);
        }
        if (namespacePath != null) {
            formParams.add("namespace_path", namespacePath);
        }
        if (overwrite != null) {
            formParams.add("overwrite", overwrite);
        }
        if (overrideParams != null) {
            formParams.add("override_params", overrideParams);
        }
        if (filePath != null) {
            formParams.add("file.path", filePath);
        }
        if (fileName != null) {
            formParams.add("file.name", fileName);
        }
        if (fileType != null) {
            formParams.add("file.type", fileType);
        }
        if (fileSize != null) {
            formParams.add("file.size", fileSize);
        }
        if (fileMd5 != null) {
            formParams.add("file.md5", fileMd5);
        }
        if (fileSha1 != null) {
            formParams.add("file.sha1", fileSha1);
        }
        if (fileSha256 != null) {
            formParams.add("file.sha256", fileSha256);
        }
        if (fileEtag != null) {
            formParams.add("file.etag", fileEtag);
        }
        if (fileRemoteId != null) {
            formParams.add("file.remote_id", fileRemoteId);
        }
        if (fileRemoteUrl != null) {
            formParams.add("file.remote_url", fileRemoteUrl);
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesProjectImportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/import", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Import a project from a local archive
     * Imports a project from a local archive. The maximum import file size is set by an administrator with the &#x60;max_import_size&#x60; application setting. Default is &#x60;0&#x60; (unlimited).
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param path The new project path and name
     * @param _file The project export file to be imported
     * @param name The name of the project to be imported. Defaults to the path of the project if not provided.
     * @param namespace (deprecated) The ID or path of the namespace to import the project to. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. Use &#x60;namespace_id&#x60; or &#x60;namespace_path&#x60; instead. Mutually exclusive with &#x60;namespace_id&#x60;, &#x60;namespace_path&#x60;.
     * @param namespaceId The ID of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_path&#x60;.
     * @param namespacePath The path of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_id&#x60;.
     * @param overwrite If there is a project in the same namespace and with the same name overwrite it
     * @param overrideParams The overrideParams parameter
     * @param filePath Path to locally stored body (generated by Workhorse)
     * @param fileName Real filename as send in Content-Disposition (generated by Workhorse)
     * @param fileType Real content type as send in Content-Type (generated by Workhorse)
     * @param fileSize Real size of file (generated by Workhorse)
     * @param fileMd5 MD5 checksum of the file (generated by Workhorse)
     * @param fileSha1 SHA1 checksum of the file (generated by Workhorse)
     * @param fileSha256 SHA256 checksum of the file (generated by Workhorse)
     * @param fileEtag Etag of the file (generated by Workhorse)
     * @param fileRemoteId Remote_id of the file (generated by Workhorse)
     * @param fileRemoteUrl Remote_url of the file (generated by Workhorse)
     * @return APIEntitiesProjectImportStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectImportStatus postApiV4ProjectsImport(@jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String namespace, @jakarta.annotation.Nullable Integer namespaceId, @jakarta.annotation.Nullable String namespacePath, @jakarta.annotation.Nullable Boolean overwrite, @jakarta.annotation.Nullable PostApiV4ProjectsImportRequestOverrideParams overrideParams, @jakarta.annotation.Nullable String filePath, @jakarta.annotation.Nullable String fileName, @jakarta.annotation.Nullable String fileType, @jakarta.annotation.Nullable Integer fileSize, @jakarta.annotation.Nullable String fileMd5, @jakarta.annotation.Nullable String fileSha1, @jakarta.annotation.Nullable String fileSha256, @jakarta.annotation.Nullable String fileEtag, @jakarta.annotation.Nullable String fileRemoteId, @jakarta.annotation.Nullable String fileRemoteUrl) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectImportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsImportRequestCreation(path, _file, name, namespace, namespaceId, namespacePath, overwrite, overrideParams, filePath, fileName, fileType, fileSize, fileMd5, fileSha1, fileSha256, fileEtag, fileRemoteId, fileRemoteUrl).body(localVarReturnType);
    }

    /**
     * Import a project from a local archive
     * Imports a project from a local archive. The maximum import file size is set by an administrator with the &#x60;max_import_size&#x60; application setting. Default is &#x60;0&#x60; (unlimited).
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param path The new project path and name
     * @param _file The project export file to be imported
     * @param name The name of the project to be imported. Defaults to the path of the project if not provided.
     * @param namespace (deprecated) The ID or path of the namespace to import the project to. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. Use &#x60;namespace_id&#x60; or &#x60;namespace_path&#x60; instead. Mutually exclusive with &#x60;namespace_id&#x60;, &#x60;namespace_path&#x60;.
     * @param namespaceId The ID of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_path&#x60;.
     * @param namespacePath The path of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_id&#x60;.
     * @param overwrite If there is a project in the same namespace and with the same name overwrite it
     * @param overrideParams The overrideParams parameter
     * @param filePath Path to locally stored body (generated by Workhorse)
     * @param fileName Real filename as send in Content-Disposition (generated by Workhorse)
     * @param fileType Real content type as send in Content-Type (generated by Workhorse)
     * @param fileSize Real size of file (generated by Workhorse)
     * @param fileMd5 MD5 checksum of the file (generated by Workhorse)
     * @param fileSha1 SHA1 checksum of the file (generated by Workhorse)
     * @param fileSha256 SHA256 checksum of the file (generated by Workhorse)
     * @param fileEtag Etag of the file (generated by Workhorse)
     * @param fileRemoteId Remote_id of the file (generated by Workhorse)
     * @param fileRemoteUrl Remote_url of the file (generated by Workhorse)
     * @return ResponseEntity&lt;APIEntitiesProjectImportStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectImportStatus> postApiV4ProjectsImportWithHttpInfo(@jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String namespace, @jakarta.annotation.Nullable Integer namespaceId, @jakarta.annotation.Nullable String namespacePath, @jakarta.annotation.Nullable Boolean overwrite, @jakarta.annotation.Nullable PostApiV4ProjectsImportRequestOverrideParams overrideParams, @jakarta.annotation.Nullable String filePath, @jakarta.annotation.Nullable String fileName, @jakarta.annotation.Nullable String fileType, @jakarta.annotation.Nullable Integer fileSize, @jakarta.annotation.Nullable String fileMd5, @jakarta.annotation.Nullable String fileSha1, @jakarta.annotation.Nullable String fileSha256, @jakarta.annotation.Nullable String fileEtag, @jakarta.annotation.Nullable String fileRemoteId, @jakarta.annotation.Nullable String fileRemoteUrl) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectImportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsImportRequestCreation(path, _file, name, namespace, namespaceId, namespacePath, overwrite, overrideParams, filePath, fileName, fileType, fileSize, fileMd5, fileSha1, fileSha256, fileEtag, fileRemoteId, fileRemoteUrl).toEntity(localVarReturnType);
    }

    /**
     * Import a project from a local archive
     * Imports a project from a local archive. The maximum import file size is set by an administrator with the &#x60;max_import_size&#x60; application setting. Default is &#x60;0&#x60; (unlimited).
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param path The new project path and name
     * @param _file The project export file to be imported
     * @param name The name of the project to be imported. Defaults to the path of the project if not provided.
     * @param namespace (deprecated) The ID or path of the namespace to import the project to. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. Use &#x60;namespace_id&#x60; or &#x60;namespace_path&#x60; instead. Mutually exclusive with &#x60;namespace_id&#x60;, &#x60;namespace_path&#x60;.
     * @param namespaceId The ID of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_path&#x60;.
     * @param namespacePath The path of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_id&#x60;.
     * @param overwrite If there is a project in the same namespace and with the same name overwrite it
     * @param overrideParams The overrideParams parameter
     * @param filePath Path to locally stored body (generated by Workhorse)
     * @param fileName Real filename as send in Content-Disposition (generated by Workhorse)
     * @param fileType Real content type as send in Content-Type (generated by Workhorse)
     * @param fileSize Real size of file (generated by Workhorse)
     * @param fileMd5 MD5 checksum of the file (generated by Workhorse)
     * @param fileSha1 SHA1 checksum of the file (generated by Workhorse)
     * @param fileSha256 SHA256 checksum of the file (generated by Workhorse)
     * @param fileEtag Etag of the file (generated by Workhorse)
     * @param fileRemoteId Remote_id of the file (generated by Workhorse)
     * @param fileRemoteUrl Remote_url of the file (generated by Workhorse)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsImportWithResponseSpec(@jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String namespace, @jakarta.annotation.Nullable Integer namespaceId, @jakarta.annotation.Nullable String namespacePath, @jakarta.annotation.Nullable Boolean overwrite, @jakarta.annotation.Nullable PostApiV4ProjectsImportRequestOverrideParams overrideParams, @jakarta.annotation.Nullable String filePath, @jakarta.annotation.Nullable String fileName, @jakarta.annotation.Nullable String fileType, @jakarta.annotation.Nullable Integer fileSize, @jakarta.annotation.Nullable String fileMd5, @jakarta.annotation.Nullable String fileSha1, @jakarta.annotation.Nullable String fileSha256, @jakarta.annotation.Nullable String fileEtag, @jakarta.annotation.Nullable String fileRemoteId, @jakarta.annotation.Nullable String fileRemoteUrl) throws RestClientResponseException {
        return postApiV4ProjectsImportRequestCreation(path, _file, name, namespace, namespaceId, namespacePath, overwrite, overrideParams, filePath, fileName, fileType, fileSize, fileMd5, fileSha1, fileSha256, fileEtag, fileRemoteId, fileRemoteUrl);
    }

    /**
     * Workhorse authorize the project import upload
     * This feature was introduced in GitLab 12.9
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsImportAuthorizeRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/projects/import/authorize", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize the project import upload
     * This feature was introduced in GitLab 12.9
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsImportAuthorize() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsImportAuthorizeRequestCreation().body(localVarReturnType);
    }

    /**
     * Workhorse authorize the project import upload
     * This feature was introduced in GitLab 12.9
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsImportAuthorizeWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsImportAuthorizeRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize the project import upload
     * This feature was introduced in GitLab 12.9
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsImportAuthorizeWithResponseSpec() throws RestClientResponseException {
        return postApiV4ProjectsImportAuthorizeRequestCreation();
    }

    /**
     * Import project resources
     * Imports project resources included with a project archive. The type of item to import is controlled by the &#x60;relation&#x60; attribute. Skips items that were previously imported. The extracted files must have the structure of a GitLab project export, and the archive cannot be larger than the maximum import file size set by an administrator. This feature was introduced in GitLab 16.11.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param path The project path and name
     * @param _file The project export file from which to extract the relation.
     * @param relation The relation to import.
     * @param filePath Path to locally stored body (generated by Workhorse)
     * @param fileName Real filename as sent in Content-Disposition (generated by Workhorse)
     * @param fileType Real content type as send in Content-Type (generated by Workhorse)
     * @param fileSize Real size of file (generated by Workhorse)
     * @param fileMd5 MD5 checksum of the file (generated by Workhorse)
     * @param fileSha1 SHA1 checksum of the file (generated by Workhorse)
     * @param fileSha256 SHA256 checksum of the file (generated by Workhorse)
     * @param fileEtag Etag of the file (generated by Workhorse)
     * @param fileRemoteId Remote_id of the file (generated by Workhorse)
     * @param fileRemoteUrl Remote_url of the file (generated by Workhorse)
     * @return APIEntitiesRelationImportTracker
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsImportRelationRequestCreation(@jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nonnull String relation, @jakarta.annotation.Nullable String filePath, @jakarta.annotation.Nullable String fileName, @jakarta.annotation.Nullable String fileType, @jakarta.annotation.Nullable Integer fileSize, @jakarta.annotation.Nullable String fileMd5, @jakarta.annotation.Nullable String fileSha1, @jakarta.annotation.Nullable String fileSha256, @jakarta.annotation.Nullable String fileEtag, @jakarta.annotation.Nullable String fileRemoteId, @jakarta.annotation.Nullable String fileRemoteUrl) throws RestClientResponseException {
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling postApiV4ProjectsImportRelation", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4ProjectsImportRelation", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'relation' is set
        if (relation == null) {
            throw new RestClientResponseException("Missing the required parameter 'relation' when calling postApiV4ProjectsImportRelation", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("path", path);
        formParams.add("file", new FileSystemResource(_file));
        formParams.add("relation", relation);
        if (filePath != null) {
            formParams.add("file.path", filePath);
        }
        if (fileName != null) {
            formParams.add("file.name", fileName);
        }
        if (fileType != null) {
            formParams.add("file.type", fileType);
        }
        if (fileSize != null) {
            formParams.add("file.size", fileSize);
        }
        if (fileMd5 != null) {
            formParams.add("file.md5", fileMd5);
        }
        if (fileSha1 != null) {
            formParams.add("file.sha1", fileSha1);
        }
        if (fileSha256 != null) {
            formParams.add("file.sha256", fileSha256);
        }
        if (fileEtag != null) {
            formParams.add("file.etag", fileEtag);
        }
        if (fileRemoteId != null) {
            formParams.add("file.remote_id", fileRemoteId);
        }
        if (fileRemoteUrl != null) {
            formParams.add("file.remote_url", fileRemoteUrl);
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesRelationImportTracker> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/import-relation", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Import project resources
     * Imports project resources included with a project archive. The type of item to import is controlled by the &#x60;relation&#x60; attribute. Skips items that were previously imported. The extracted files must have the structure of a GitLab project export, and the archive cannot be larger than the maximum import file size set by an administrator. This feature was introduced in GitLab 16.11.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param path The project path and name
     * @param _file The project export file from which to extract the relation.
     * @param relation The relation to import.
     * @param filePath Path to locally stored body (generated by Workhorse)
     * @param fileName Real filename as sent in Content-Disposition (generated by Workhorse)
     * @param fileType Real content type as send in Content-Type (generated by Workhorse)
     * @param fileSize Real size of file (generated by Workhorse)
     * @param fileMd5 MD5 checksum of the file (generated by Workhorse)
     * @param fileSha1 SHA1 checksum of the file (generated by Workhorse)
     * @param fileSha256 SHA256 checksum of the file (generated by Workhorse)
     * @param fileEtag Etag of the file (generated by Workhorse)
     * @param fileRemoteId Remote_id of the file (generated by Workhorse)
     * @param fileRemoteUrl Remote_url of the file (generated by Workhorse)
     * @return APIEntitiesRelationImportTracker
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesRelationImportTracker postApiV4ProjectsImportRelation(@jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nonnull String relation, @jakarta.annotation.Nullable String filePath, @jakarta.annotation.Nullable String fileName, @jakarta.annotation.Nullable String fileType, @jakarta.annotation.Nullable Integer fileSize, @jakarta.annotation.Nullable String fileMd5, @jakarta.annotation.Nullable String fileSha1, @jakarta.annotation.Nullable String fileSha256, @jakarta.annotation.Nullable String fileEtag, @jakarta.annotation.Nullable String fileRemoteId, @jakarta.annotation.Nullable String fileRemoteUrl) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelationImportTracker> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsImportRelationRequestCreation(path, _file, relation, filePath, fileName, fileType, fileSize, fileMd5, fileSha1, fileSha256, fileEtag, fileRemoteId, fileRemoteUrl).body(localVarReturnType);
    }

    /**
     * Import project resources
     * Imports project resources included with a project archive. The type of item to import is controlled by the &#x60;relation&#x60; attribute. Skips items that were previously imported. The extracted files must have the structure of a GitLab project export, and the archive cannot be larger than the maximum import file size set by an administrator. This feature was introduced in GitLab 16.11.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param path The project path and name
     * @param _file The project export file from which to extract the relation.
     * @param relation The relation to import.
     * @param filePath Path to locally stored body (generated by Workhorse)
     * @param fileName Real filename as sent in Content-Disposition (generated by Workhorse)
     * @param fileType Real content type as send in Content-Type (generated by Workhorse)
     * @param fileSize Real size of file (generated by Workhorse)
     * @param fileMd5 MD5 checksum of the file (generated by Workhorse)
     * @param fileSha1 SHA1 checksum of the file (generated by Workhorse)
     * @param fileSha256 SHA256 checksum of the file (generated by Workhorse)
     * @param fileEtag Etag of the file (generated by Workhorse)
     * @param fileRemoteId Remote_id of the file (generated by Workhorse)
     * @param fileRemoteUrl Remote_url of the file (generated by Workhorse)
     * @return ResponseEntity&lt;APIEntitiesRelationImportTracker&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesRelationImportTracker> postApiV4ProjectsImportRelationWithHttpInfo(@jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nonnull String relation, @jakarta.annotation.Nullable String filePath, @jakarta.annotation.Nullable String fileName, @jakarta.annotation.Nullable String fileType, @jakarta.annotation.Nullable Integer fileSize, @jakarta.annotation.Nullable String fileMd5, @jakarta.annotation.Nullable String fileSha1, @jakarta.annotation.Nullable String fileSha256, @jakarta.annotation.Nullable String fileEtag, @jakarta.annotation.Nullable String fileRemoteId, @jakarta.annotation.Nullable String fileRemoteUrl) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelationImportTracker> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsImportRelationRequestCreation(path, _file, relation, filePath, fileName, fileType, fileSize, fileMd5, fileSha1, fileSha256, fileEtag, fileRemoteId, fileRemoteUrl).toEntity(localVarReturnType);
    }

    /**
     * Import project resources
     * Imports project resources included with a project archive. The type of item to import is controlled by the &#x60;relation&#x60; attribute. Skips items that were previously imported. The extracted files must have the structure of a GitLab project export, and the archive cannot be larger than the maximum import file size set by an administrator. This feature was introduced in GitLab 16.11.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>503</b> - Service unavailable
     * @param path The project path and name
     * @param _file The project export file from which to extract the relation.
     * @param relation The relation to import.
     * @param filePath Path to locally stored body (generated by Workhorse)
     * @param fileName Real filename as sent in Content-Disposition (generated by Workhorse)
     * @param fileType Real content type as send in Content-Type (generated by Workhorse)
     * @param fileSize Real size of file (generated by Workhorse)
     * @param fileMd5 MD5 checksum of the file (generated by Workhorse)
     * @param fileSha1 SHA1 checksum of the file (generated by Workhorse)
     * @param fileSha256 SHA256 checksum of the file (generated by Workhorse)
     * @param fileEtag Etag of the file (generated by Workhorse)
     * @param fileRemoteId Remote_id of the file (generated by Workhorse)
     * @param fileRemoteUrl Remote_url of the file (generated by Workhorse)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsImportRelationWithResponseSpec(@jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nonnull String relation, @jakarta.annotation.Nullable String filePath, @jakarta.annotation.Nullable String fileName, @jakarta.annotation.Nullable String fileType, @jakarta.annotation.Nullable Integer fileSize, @jakarta.annotation.Nullable String fileMd5, @jakarta.annotation.Nullable String fileSha1, @jakarta.annotation.Nullable String fileSha256, @jakarta.annotation.Nullable String fileEtag, @jakarta.annotation.Nullable String fileRemoteId, @jakarta.annotation.Nullable String fileRemoteUrl) throws RestClientResponseException {
        return postApiV4ProjectsImportRelationRequestCreation(path, _file, relation, filePath, fileName, fileType, fileSize, fileMd5, fileSha1, fileSha256, fileEtag, fileRemoteId, fileRemoteUrl);
    }

    /**
     * Authorize project relation import
     * Authorizes uploading a project relation import file. This feature was introduced in GitLab 16.11.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsImportRelationAuthorizeRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/projects/import-relation/authorize", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Authorize project relation import
     * Authorizes uploading a project relation import file. This feature was introduced in GitLab 16.11.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsImportRelationAuthorize() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsImportRelationAuthorizeRequestCreation().body(localVarReturnType);
    }

    /**
     * Authorize project relation import
     * Authorizes uploading a project relation import file. This feature was introduced in GitLab 16.11.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsImportRelationAuthorizeWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsImportRelationAuthorizeRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Authorize project relation import
     * Authorizes uploading a project relation import file. This feature was introduced in GitLab 16.11.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsImportRelationAuthorizeWithResponseSpec() throws RestClientResponseException {
        return postApiV4ProjectsImportRelationAuthorizeRequestCreation();
    }

    /**
     * Import a project from a remote archive
     * Imports a project from a remote archive. The &#x60;Content-Length&#x60; header of the remote file must be a valid number, and the file cannot be larger than the &#x60;max_import_remote_file_size&#x60; application setting, which defaults to 10 GB. The &#x60;Content-Type&#x60; header must be &#x60;application/gzip&#x60;, &#x60;application/x-gzip&#x60;, or &#x60;application/x-tar&#x60;.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param url The URL for the file.
     * @param path The new project path and name
     * @param name The name of the project to be imported. Defaults to the path of the project if not provided.
     * @param namespace (deprecated) The ID or path of the namespace to import the project to. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. Use &#x60;namespace_id&#x60; or &#x60;namespace_path&#x60; instead. Mutually exclusive with &#x60;namespace_id&#x60;, &#x60;namespace_path&#x60;.
     * @param namespaceId The ID of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_path&#x60;.
     * @param namespacePath The path of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_id&#x60;.
     * @param overwrite If there is a project in the same namespace and with the same name overwrite it
     * @param overrideParams The overrideParams parameter
     * @return APIEntitiesProjectImportStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsRemoteImportRequestCreation(@jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String namespace, @jakarta.annotation.Nullable Integer namespaceId, @jakarta.annotation.Nullable String namespacePath, @jakarta.annotation.Nullable Boolean overwrite, @jakarta.annotation.Nullable PostApiV4ProjectsImportRequestOverrideParams overrideParams) throws RestClientResponseException {
        // verify the required parameter 'url' is set
        if (url == null) {
            throw new RestClientResponseException("Missing the required parameter 'url' when calling postApiV4ProjectsRemoteImport", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling postApiV4ProjectsRemoteImport", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("url", url);
        formParams.add("path", path);
        if (name != null) {
            formParams.add("name", name);
        }
        if (namespace != null) {
            formParams.add("namespace", namespace);
        }
        if (namespaceId != null) {
            formParams.add("namespace_id", namespaceId);
        }
        if (namespacePath != null) {
            formParams.add("namespace_path", namespacePath);
        }
        if (overwrite != null) {
            formParams.add("overwrite", overwrite);
        }
        if (overrideParams != null) {
            formParams.add("override_params", overrideParams);
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesProjectImportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/remote-import", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Import a project from a remote archive
     * Imports a project from a remote archive. The &#x60;Content-Length&#x60; header of the remote file must be a valid number, and the file cannot be larger than the &#x60;max_import_remote_file_size&#x60; application setting, which defaults to 10 GB. The &#x60;Content-Type&#x60; header must be &#x60;application/gzip&#x60;, &#x60;application/x-gzip&#x60;, or &#x60;application/x-tar&#x60;.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param url The URL for the file.
     * @param path The new project path and name
     * @param name The name of the project to be imported. Defaults to the path of the project if not provided.
     * @param namespace (deprecated) The ID or path of the namespace to import the project to. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. Use &#x60;namespace_id&#x60; or &#x60;namespace_path&#x60; instead. Mutually exclusive with &#x60;namespace_id&#x60;, &#x60;namespace_path&#x60;.
     * @param namespaceId The ID of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_path&#x60;.
     * @param namespacePath The path of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_id&#x60;.
     * @param overwrite If there is a project in the same namespace and with the same name overwrite it
     * @param overrideParams The overrideParams parameter
     * @return APIEntitiesProjectImportStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectImportStatus postApiV4ProjectsRemoteImport(@jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String namespace, @jakarta.annotation.Nullable Integer namespaceId, @jakarta.annotation.Nullable String namespacePath, @jakarta.annotation.Nullable Boolean overwrite, @jakarta.annotation.Nullable PostApiV4ProjectsImportRequestOverrideParams overrideParams) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectImportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsRemoteImportRequestCreation(url, path, name, namespace, namespaceId, namespacePath, overwrite, overrideParams).body(localVarReturnType);
    }

    /**
     * Import a project from a remote archive
     * Imports a project from a remote archive. The &#x60;Content-Length&#x60; header of the remote file must be a valid number, and the file cannot be larger than the &#x60;max_import_remote_file_size&#x60; application setting, which defaults to 10 GB. The &#x60;Content-Type&#x60; header must be &#x60;application/gzip&#x60;, &#x60;application/x-gzip&#x60;, or &#x60;application/x-tar&#x60;.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param url The URL for the file.
     * @param path The new project path and name
     * @param name The name of the project to be imported. Defaults to the path of the project if not provided.
     * @param namespace (deprecated) The ID or path of the namespace to import the project to. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. Use &#x60;namespace_id&#x60; or &#x60;namespace_path&#x60; instead. Mutually exclusive with &#x60;namespace_id&#x60;, &#x60;namespace_path&#x60;.
     * @param namespaceId The ID of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_path&#x60;.
     * @param namespacePath The path of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_id&#x60;.
     * @param overwrite If there is a project in the same namespace and with the same name overwrite it
     * @param overrideParams The overrideParams parameter
     * @return ResponseEntity&lt;APIEntitiesProjectImportStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectImportStatus> postApiV4ProjectsRemoteImportWithHttpInfo(@jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String namespace, @jakarta.annotation.Nullable Integer namespaceId, @jakarta.annotation.Nullable String namespacePath, @jakarta.annotation.Nullable Boolean overwrite, @jakarta.annotation.Nullable PostApiV4ProjectsImportRequestOverrideParams overrideParams) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectImportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsRemoteImportRequestCreation(url, path, name, namespace, namespaceId, namespacePath, overwrite, overrideParams).toEntity(localVarReturnType);
    }

    /**
     * Import a project from a remote archive
     * Imports a project from a remote archive. The &#x60;Content-Length&#x60; header of the remote file must be a valid number, and the file cannot be larger than the &#x60;max_import_remote_file_size&#x60; application setting, which defaults to 10 GB. The &#x60;Content-Type&#x60; header must be &#x60;application/gzip&#x60;, &#x60;application/x-gzip&#x60;, or &#x60;application/x-tar&#x60;.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param url The URL for the file.
     * @param path The new project path and name
     * @param name The name of the project to be imported. Defaults to the path of the project if not provided.
     * @param namespace (deprecated) The ID or path of the namespace to import the project to. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. Use &#x60;namespace_id&#x60; or &#x60;namespace_path&#x60; instead. Mutually exclusive with &#x60;namespace_id&#x60;, &#x60;namespace_path&#x60;.
     * @param namespaceId The ID of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_path&#x60;.
     * @param namespacePath The path of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_id&#x60;.
     * @param overwrite If there is a project in the same namespace and with the same name overwrite it
     * @param overrideParams The overrideParams parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsRemoteImportWithResponseSpec(@jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String namespace, @jakarta.annotation.Nullable Integer namespaceId, @jakarta.annotation.Nullable String namespacePath, @jakarta.annotation.Nullable Boolean overwrite, @jakarta.annotation.Nullable PostApiV4ProjectsImportRequestOverrideParams overrideParams) throws RestClientResponseException {
        return postApiV4ProjectsRemoteImportRequestCreation(url, path, name, namespace, namespaceId, namespacePath, overwrite, overrideParams);
    }

    /**
     * Import a project from an AWS S3 bucket
     * Imports a project from an archive stored in a specified AWS S3 bucket.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param region AWS region
     * @param bucketName Bucket name
     * @param fileKey File key
     * @param accessKeyId Access key id
     * @param secretAccessKey Secret access key
     * @param path The new project path and name
     * @param name The name of the project to be imported. Defaults to the path of the project if not provided.
     * @param namespace (deprecated) The ID or path of the namespace to import the project to. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. Use &#x60;namespace_id&#x60; or &#x60;namespace_path&#x60; instead. Mutually exclusive with &#x60;namespace_id&#x60;, &#x60;namespace_path&#x60;.
     * @param namespaceId The ID of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_path&#x60;.
     * @param namespacePath The path of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_id&#x60;.
     * @param overwrite If there is a project in the same namespace and with the same name overwrite it
     * @param overrideParams The overrideParams parameter
     * @return APIEntitiesProjectImportStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsRemoteImportS3RequestCreation(@jakarta.annotation.Nullable String region, @jakarta.annotation.Nullable String bucketName, @jakarta.annotation.Nullable String fileKey, @jakarta.annotation.Nullable String accessKeyId, @jakarta.annotation.Nullable String secretAccessKey, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String namespace, @jakarta.annotation.Nullable Integer namespaceId, @jakarta.annotation.Nullable String namespacePath, @jakarta.annotation.Nullable Boolean overwrite, @jakarta.annotation.Nullable PostApiV4ProjectsRemoteImportS3RequestOverrideParams overrideParams) throws RestClientResponseException {
        // verify the required parameter 'region' is set
        if (region == null) {
            throw new RestClientResponseException("Missing the required parameter 'region' when calling postApiV4ProjectsRemoteImportS3", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'bucketName' is set
        if (bucketName == null) {
            throw new RestClientResponseException("Missing the required parameter 'bucketName' when calling postApiV4ProjectsRemoteImportS3", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileKey' is set
        if (fileKey == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileKey' when calling postApiV4ProjectsRemoteImportS3", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'accessKeyId' is set
        if (accessKeyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'accessKeyId' when calling postApiV4ProjectsRemoteImportS3", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'secretAccessKey' is set
        if (secretAccessKey == null) {
            throw new RestClientResponseException("Missing the required parameter 'secretAccessKey' when calling postApiV4ProjectsRemoteImportS3", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling postApiV4ProjectsRemoteImportS3", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("region", region);
        formParams.add("bucket_name", bucketName);
        formParams.add("file_key", fileKey);
        formParams.add("access_key_id", accessKeyId);
        formParams.add("secret_access_key", secretAccessKey);
        formParams.add("path", path);
        if (name != null) {
            formParams.add("name", name);
        }
        if (namespace != null) {
            formParams.add("namespace", namespace);
        }
        if (namespaceId != null) {
            formParams.add("namespace_id", namespaceId);
        }
        if (namespacePath != null) {
            formParams.add("namespace_path", namespacePath);
        }
        if (overwrite != null) {
            formParams.add("overwrite", overwrite);
        }
        if (overrideParams != null) {
            formParams.add("override_params", overrideParams);
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesProjectImportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/remote-import-s3", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Import a project from an AWS S3 bucket
     * Imports a project from an archive stored in a specified AWS S3 bucket.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param region AWS region
     * @param bucketName Bucket name
     * @param fileKey File key
     * @param accessKeyId Access key id
     * @param secretAccessKey Secret access key
     * @param path The new project path and name
     * @param name The name of the project to be imported. Defaults to the path of the project if not provided.
     * @param namespace (deprecated) The ID or path of the namespace to import the project to. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. Use &#x60;namespace_id&#x60; or &#x60;namespace_path&#x60; instead. Mutually exclusive with &#x60;namespace_id&#x60;, &#x60;namespace_path&#x60;.
     * @param namespaceId The ID of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_path&#x60;.
     * @param namespacePath The path of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_id&#x60;.
     * @param overwrite If there is a project in the same namespace and with the same name overwrite it
     * @param overrideParams The overrideParams parameter
     * @return APIEntitiesProjectImportStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectImportStatus postApiV4ProjectsRemoteImportS3(@jakarta.annotation.Nullable String region, @jakarta.annotation.Nullable String bucketName, @jakarta.annotation.Nullable String fileKey, @jakarta.annotation.Nullable String accessKeyId, @jakarta.annotation.Nullable String secretAccessKey, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String namespace, @jakarta.annotation.Nullable Integer namespaceId, @jakarta.annotation.Nullable String namespacePath, @jakarta.annotation.Nullable Boolean overwrite, @jakarta.annotation.Nullable PostApiV4ProjectsRemoteImportS3RequestOverrideParams overrideParams) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectImportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsRemoteImportS3RequestCreation(region, bucketName, fileKey, accessKeyId, secretAccessKey, path, name, namespace, namespaceId, namespacePath, overwrite, overrideParams).body(localVarReturnType);
    }

    /**
     * Import a project from an AWS S3 bucket
     * Imports a project from an archive stored in a specified AWS S3 bucket.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param region AWS region
     * @param bucketName Bucket name
     * @param fileKey File key
     * @param accessKeyId Access key id
     * @param secretAccessKey Secret access key
     * @param path The new project path and name
     * @param name The name of the project to be imported. Defaults to the path of the project if not provided.
     * @param namespace (deprecated) The ID or path of the namespace to import the project to. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. Use &#x60;namespace_id&#x60; or &#x60;namespace_path&#x60; instead. Mutually exclusive with &#x60;namespace_id&#x60;, &#x60;namespace_path&#x60;.
     * @param namespaceId The ID of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_path&#x60;.
     * @param namespacePath The path of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_id&#x60;.
     * @param overwrite If there is a project in the same namespace and with the same name overwrite it
     * @param overrideParams The overrideParams parameter
     * @return ResponseEntity&lt;APIEntitiesProjectImportStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectImportStatus> postApiV4ProjectsRemoteImportS3WithHttpInfo(@jakarta.annotation.Nullable String region, @jakarta.annotation.Nullable String bucketName, @jakarta.annotation.Nullable String fileKey, @jakarta.annotation.Nullable String accessKeyId, @jakarta.annotation.Nullable String secretAccessKey, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String namespace, @jakarta.annotation.Nullable Integer namespaceId, @jakarta.annotation.Nullable String namespacePath, @jakarta.annotation.Nullable Boolean overwrite, @jakarta.annotation.Nullable PostApiV4ProjectsRemoteImportS3RequestOverrideParams overrideParams) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectImportStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsRemoteImportS3RequestCreation(region, bucketName, fileKey, accessKeyId, secretAccessKey, path, name, namespace, namespaceId, namespacePath, overwrite, overrideParams).toEntity(localVarReturnType);
    }

    /**
     * Import a project from an AWS S3 bucket
     * Imports a project from an archive stored in a specified AWS S3 bucket.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * @param region AWS region
     * @param bucketName Bucket name
     * @param fileKey File key
     * @param accessKeyId Access key id
     * @param secretAccessKey Secret access key
     * @param path The new project path and name
     * @param name The name of the project to be imported. Defaults to the path of the project if not provided.
     * @param namespace (deprecated) The ID or path of the namespace to import the project to. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. Use &#x60;namespace_id&#x60; or &#x60;namespace_path&#x60; instead. Mutually exclusive with &#x60;namespace_id&#x60;, &#x60;namespace_path&#x60;.
     * @param namespaceId The ID of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_path&#x60;.
     * @param namespacePath The path of the namespace that the project will be imported into. Defaults to the current user&#39;s namespace. Requires the Maintainer or Owner role on the destination group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/511053) in GitLab 18.7. Mutually exclusive with &#x60;namespace&#x60;, &#x60;namespace_id&#x60;.
     * @param overwrite If there is a project in the same namespace and with the same name overwrite it
     * @param overrideParams The overrideParams parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsRemoteImportS3WithResponseSpec(@jakarta.annotation.Nullable String region, @jakarta.annotation.Nullable String bucketName, @jakarta.annotation.Nullable String fileKey, @jakarta.annotation.Nullable String accessKeyId, @jakarta.annotation.Nullable String secretAccessKey, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String namespace, @jakarta.annotation.Nullable Integer namespaceId, @jakarta.annotation.Nullable String namespacePath, @jakarta.annotation.Nullable Boolean overwrite, @jakarta.annotation.Nullable PostApiV4ProjectsRemoteImportS3RequestOverrideParams overrideParams) throws RestClientResponseException {
        return postApiV4ProjectsRemoteImportS3RequestCreation(region, bucketName, fileKey, accessKeyId, secretAccessKey, path, name, namespace, namespaceId, namespacePath, overwrite, overrideParams);
    }
}
