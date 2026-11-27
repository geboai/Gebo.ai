package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCiJobBasicWithProject;
import ai.gebo.gitlab.client.model.APIEntitiesCiJobRequestResponse;
import ai.gebo.gitlab.client.model.APIEntitiesCiRuntimeEnvironmentKey;
import java.io.File;
import ai.gebo.gitlab.client.model.PatchApiV4JobsIdTraceRequest;
import ai.gebo.gitlab.client.model.PostApiV4JobsIdArtifactsAuthorizeRequest;
import ai.gebo.gitlab.client.model.PostApiV4JobsRequestRequest;
import ai.gebo.gitlab.client.model.PutApiV4JobsIdRequest;

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
public class JobsApi {
    private ApiClient apiClient;

    public JobsApi() {
        this(new ApiClient());
    }

    public JobsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Download job artifacts
     * Downloads artifacts for a specified job.
     * <p><b>200</b> - Download allowed
     * <p><b>302</b> - Found
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Artifact not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param token Authentication token of the job.
     * @param directDownload If &#x60;true&#x60;, performs a direct download of artifacts from remote storage instead of proxying them.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4JobsIdArtifactsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String token, @jakarta.annotation.Nullable Boolean directDownload, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4JobsIdArtifacts", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "token", token));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "direct_download", directDownload));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "download_mode", downloadMode));

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/jobs/{id}/artifacts", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download job artifacts
     * Downloads artifacts for a specified job.
     * <p><b>200</b> - Download allowed
     * <p><b>302</b> - Found
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Artifact not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param token Authentication token of the job.
     * @param directDownload If &#x60;true&#x60;, performs a direct download of artifacts from remote storage instead of proxying them.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4JobsIdArtifacts(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String token, @jakarta.annotation.Nullable Boolean directDownload, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4JobsIdArtifactsRequestCreation(id, token, directDownload, downloadMode).body(localVarReturnType);
    }

    /**
     * Download job artifacts
     * Downloads artifacts for a specified job.
     * <p><b>200</b> - Download allowed
     * <p><b>302</b> - Found
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Artifact not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param token Authentication token of the job.
     * @param directDownload If &#x60;true&#x60;, performs a direct download of artifacts from remote storage instead of proxying them.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4JobsIdArtifactsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String token, @jakarta.annotation.Nullable Boolean directDownload, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4JobsIdArtifactsRequestCreation(id, token, directDownload, downloadMode).toEntity(localVarReturnType);
    }

    /**
     * Download job artifacts
     * Downloads artifacts for a specified job.
     * <p><b>200</b> - Download allowed
     * <p><b>302</b> - Found
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Artifact not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param token Authentication token of the job.
     * @param directDownload If &#x60;true&#x60;, performs a direct download of artifacts from remote storage instead of proxying them.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4JobsIdArtifactsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String token, @jakarta.annotation.Nullable Boolean directDownload, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        return getApiV4JobsIdArtifactsRequestCreation(id, token, directDownload, downloadMode);
    }

    /**
     * Get the runtime environment key for a job
     * Retrieves the runtime environment key linked to a job, if the job is resuming a suspended environment.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param token Authentication token of the job.
     * @return APIEntitiesCiRuntimeEnvironmentKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4JobsIdRuntimeEnvironmentKeyRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String token) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4JobsIdRuntimeEnvironmentKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "token", token));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesCiRuntimeEnvironmentKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/jobs/{id}/runtime_environment_key", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get the runtime environment key for a job
     * Retrieves the runtime environment key linked to a job, if the job is resuming a suspended environment.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param token Authentication token of the job.
     * @return APIEntitiesCiRuntimeEnvironmentKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRuntimeEnvironmentKey getApiV4JobsIdRuntimeEnvironmentKey(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String token) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRuntimeEnvironmentKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4JobsIdRuntimeEnvironmentKeyRequestCreation(id, token).body(localVarReturnType);
    }

    /**
     * Get the runtime environment key for a job
     * Retrieves the runtime environment key linked to a job, if the job is resuming a suspended environment.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param token Authentication token of the job.
     * @return ResponseEntity&lt;APIEntitiesCiRuntimeEnvironmentKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRuntimeEnvironmentKey> getApiV4JobsIdRuntimeEnvironmentKeyWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String token) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRuntimeEnvironmentKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4JobsIdRuntimeEnvironmentKeyRequestCreation(id, token).toEntity(localVarReturnType);
    }

    /**
     * Get the runtime environment key for a job
     * Retrieves the runtime environment key linked to a job, if the job is resuming a suspended environment.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param token Authentication token of the job.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4JobsIdRuntimeEnvironmentKeyWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String token) throws RestClientResponseException {
        return getApiV4JobsIdRuntimeEnvironmentKeyRequestCreation(id, token);
    }

    /**
     * List all jobs processed by a runner
     * Lists all jobs that are being processed or were processed by a specified runner. The list of jobs is limited to projects where the user has the Reporter, Developer, Maintainer, or Owner role. When the &#x60;ci_rate_limit_rest_runner_job_list_completed_statuses&#x60; feature flag is enabled, requests filtered by &#x60;success&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60; are rate limited per user (&#x60;runner_job_list_completed_statuses_limit_per_user&#x60; application setting), and statuses a runner&#39;s jobs cannot have, such as &#x60;pending&#x60;, match no jobs. Until the &#x60;ci_enforce_runner_job_list_completed_statuses_limit&#x60; feature flag is enabled, requests over this limit are logged but not blocked. Requests that list only jobs from the user&#39;s projects, which is every user except administrators in Admin Mode and auditors, are rate limited per user (&#x60;runner_job_list_project_filtered_limit_per_user&#x60; application setting). Until the &#x60;ci_rate_limit_rest_runner_job_list_project_filtered&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param systemId System ID associated with the runner manager.
     * @param status Status of the job.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order. Specify &#x60;order_by&#x60; as well, including for &#x60;id&#x60;.
     * @param cursor Cursor for obtaining the next set of records.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiJobBasicWithProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4RunnersIdJobsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String systemId, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4RunnersIdJobs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "system_id", systemId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "cursor", cursor));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCiJobBasicWithProject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners/{id}/jobs", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all jobs processed by a runner
     * Lists all jobs that are being processed or were processed by a specified runner. The list of jobs is limited to projects where the user has the Reporter, Developer, Maintainer, or Owner role. When the &#x60;ci_rate_limit_rest_runner_job_list_completed_statuses&#x60; feature flag is enabled, requests filtered by &#x60;success&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60; are rate limited per user (&#x60;runner_job_list_completed_statuses_limit_per_user&#x60; application setting), and statuses a runner&#39;s jobs cannot have, such as &#x60;pending&#x60;, match no jobs. Until the &#x60;ci_enforce_runner_job_list_completed_statuses_limit&#x60; feature flag is enabled, requests over this limit are logged but not blocked. Requests that list only jobs from the user&#39;s projects, which is every user except administrators in Admin Mode and auditors, are rate limited per user (&#x60;runner_job_list_project_filtered_limit_per_user&#x60; application setting). Until the &#x60;ci_rate_limit_rest_runner_job_list_project_filtered&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param systemId System ID associated with the runner manager.
     * @param status Status of the job.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order. Specify &#x60;order_by&#x60; as well, including for &#x60;id&#x60;.
     * @param cursor Cursor for obtaining the next set of records.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiJobBasicWithProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiJobBasicWithProject> getApiV4RunnersIdJobs(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String systemId, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiJobBasicWithProject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersIdJobsRequestCreation(id, systemId, status, orderBy, sort, cursor, page, perPage).body(localVarReturnType);
    }

    /**
     * List all jobs processed by a runner
     * Lists all jobs that are being processed or were processed by a specified runner. The list of jobs is limited to projects where the user has the Reporter, Developer, Maintainer, or Owner role. When the &#x60;ci_rate_limit_rest_runner_job_list_completed_statuses&#x60; feature flag is enabled, requests filtered by &#x60;success&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60; are rate limited per user (&#x60;runner_job_list_completed_statuses_limit_per_user&#x60; application setting), and statuses a runner&#39;s jobs cannot have, such as &#x60;pending&#x60;, match no jobs. Until the &#x60;ci_enforce_runner_job_list_completed_statuses_limit&#x60; feature flag is enabled, requests over this limit are logged but not blocked. Requests that list only jobs from the user&#39;s projects, which is every user except administrators in Admin Mode and auditors, are rate limited per user (&#x60;runner_job_list_project_filtered_limit_per_user&#x60; application setting). Until the &#x60;ci_rate_limit_rest_runner_job_list_project_filtered&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param systemId System ID associated with the runner manager.
     * @param status Status of the job.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order. Specify &#x60;order_by&#x60; as well, including for &#x60;id&#x60;.
     * @param cursor Cursor for obtaining the next set of records.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiJobBasicWithProject&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiJobBasicWithProject>> getApiV4RunnersIdJobsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String systemId, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiJobBasicWithProject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersIdJobsRequestCreation(id, systemId, status, orderBy, sort, cursor, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all jobs processed by a runner
     * Lists all jobs that are being processed or were processed by a specified runner. The list of jobs is limited to projects where the user has the Reporter, Developer, Maintainer, or Owner role. When the &#x60;ci_rate_limit_rest_runner_job_list_completed_statuses&#x60; feature flag is enabled, requests filtered by &#x60;success&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60; are rate limited per user (&#x60;runner_job_list_completed_statuses_limit_per_user&#x60; application setting), and statuses a runner&#39;s jobs cannot have, such as &#x60;pending&#x60;, match no jobs. Until the &#x60;ci_enforce_runner_job_list_completed_statuses_limit&#x60; feature flag is enabled, requests over this limit are logged but not blocked. Requests that list only jobs from the user&#39;s projects, which is every user except administrators in Admin Mode and auditors, are rate limited per user (&#x60;runner_job_list_project_filtered_limit_per_user&#x60; application setting). Until the &#x60;ci_rate_limit_rest_runner_job_list_project_filtered&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param systemId System ID associated with the runner manager.
     * @param status Status of the job.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order. Specify &#x60;order_by&#x60; as well, including for &#x60;id&#x60;.
     * @param cursor Cursor for obtaining the next set of records.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4RunnersIdJobsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String systemId, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4RunnersIdJobsRequestCreation(id, systemId, status, orderBy, sort, cursor, page, perPage);
    }

    /**
     * Update the trace of a job
     * Updates the trace of a job by appending a chunk of log output. Used by runners while executing a job.
     * <p><b>202</b> - Trace was patched
     * <p><b>400</b> - Missing Content-Range header
     * <p><b>403</b> - Forbidden
     * <p><b>416</b> - Range not satisfiable
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param patchApiV4JobsIdTraceRequest The patchApiV4JobsIdTraceRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4JobsIdTraceRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PatchApiV4JobsIdTraceRequest patchApiV4JobsIdTraceRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4JobsIdTrace", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/jobs/{id}/trace", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4JobsIdTraceRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update the trace of a job
     * Updates the trace of a job by appending a chunk of log output. Used by runners while executing a job.
     * <p><b>202</b> - Trace was patched
     * <p><b>400</b> - Missing Content-Range header
     * <p><b>403</b> - Forbidden
     * <p><b>416</b> - Range not satisfiable
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param patchApiV4JobsIdTraceRequest The patchApiV4JobsIdTraceRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void patchApiV4JobsIdTrace(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PatchApiV4JobsIdTraceRequest patchApiV4JobsIdTraceRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        patchApiV4JobsIdTraceRequestCreation(id, patchApiV4JobsIdTraceRequest).body(localVarReturnType);
    }

    /**
     * Update the trace of a job
     * Updates the trace of a job by appending a chunk of log output. Used by runners while executing a job.
     * <p><b>202</b> - Trace was patched
     * <p><b>400</b> - Missing Content-Range header
     * <p><b>403</b> - Forbidden
     * <p><b>416</b> - Range not satisfiable
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param patchApiV4JobsIdTraceRequest The patchApiV4JobsIdTraceRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> patchApiV4JobsIdTraceWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PatchApiV4JobsIdTraceRequest patchApiV4JobsIdTraceRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4JobsIdTraceRequestCreation(id, patchApiV4JobsIdTraceRequest).toEntity(localVarReturnType);
    }

    /**
     * Update the trace of a job
     * Updates the trace of a job by appending a chunk of log output. Used by runners while executing a job.
     * <p><b>202</b> - Trace was patched
     * <p><b>400</b> - Missing Content-Range header
     * <p><b>403</b> - Forbidden
     * <p><b>416</b> - Range not satisfiable
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param patchApiV4JobsIdTraceRequest The patchApiV4JobsIdTraceRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4JobsIdTraceWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PatchApiV4JobsIdTraceRequest patchApiV4JobsIdTraceRequest) throws RestClientResponseException {
        return patchApiV4JobsIdTraceRequestCreation(id, patchApiV4JobsIdTraceRequest);
    }

    /**
     * Upload job artifacts
     * Uploads artifacts for a specified job.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Artifacts support not enabled
     * <p><b>409</b> - Conflict
     * <p><b>413</b> - File too large
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param _file The artifact file to store (generated by Multipart middleware)
     * @param token Authentication token of the job.
     * @param expireIn Specify when artifact should expire
     * @param artifactType Type of artifact.
     * @param artifactFormat The format of artifact
     * @param metadata The artifact metadata to store (generated by Multipart middleware)
     * @param accessibility Specify accessibility level of artifact private/public
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4JobsIdArtifactsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String token, @jakarta.annotation.Nullable String expireIn, @jakarta.annotation.Nullable String artifactType, @jakarta.annotation.Nullable String artifactFormat, @jakarta.annotation.Nullable File metadata, @jakarta.annotation.Nullable String accessibility) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4JobsIdArtifacts", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4JobsIdArtifacts", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("file", new FileSystemResource(_file));
        if (token != null) {
            formParams.add("token", token);
        }
        if (expireIn != null) {
            formParams.add("expire_in", expireIn);
        }
        if (artifactType != null) {
            formParams.add("artifact_type", artifactType);
        }
        if (artifactFormat != null) {
            formParams.add("artifact_format", artifactFormat);
        }
        if (metadata != null) {
            formParams.add("metadata", new FileSystemResource(metadata));
        }
        if (accessibility != null) {
            formParams.add("accessibility", accessibility);
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/jobs/{id}/artifacts", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload job artifacts
     * Uploads artifacts for a specified job.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Artifacts support not enabled
     * <p><b>409</b> - Conflict
     * <p><b>413</b> - File too large
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param _file The artifact file to store (generated by Multipart middleware)
     * @param token Authentication token of the job.
     * @param expireIn Specify when artifact should expire
     * @param artifactType Type of artifact.
     * @param artifactFormat The format of artifact
     * @param metadata The artifact metadata to store (generated by Multipart middleware)
     * @param accessibility Specify accessibility level of artifact private/public
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4JobsIdArtifacts(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String token, @jakarta.annotation.Nullable String expireIn, @jakarta.annotation.Nullable String artifactType, @jakarta.annotation.Nullable String artifactFormat, @jakarta.annotation.Nullable File metadata, @jakarta.annotation.Nullable String accessibility) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4JobsIdArtifactsRequestCreation(id, _file, token, expireIn, artifactType, artifactFormat, metadata, accessibility).body(localVarReturnType);
    }

    /**
     * Upload job artifacts
     * Uploads artifacts for a specified job.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Artifacts support not enabled
     * <p><b>409</b> - Conflict
     * <p><b>413</b> - File too large
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param _file The artifact file to store (generated by Multipart middleware)
     * @param token Authentication token of the job.
     * @param expireIn Specify when artifact should expire
     * @param artifactType Type of artifact.
     * @param artifactFormat The format of artifact
     * @param metadata The artifact metadata to store (generated by Multipart middleware)
     * @param accessibility Specify accessibility level of artifact private/public
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4JobsIdArtifactsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String token, @jakarta.annotation.Nullable String expireIn, @jakarta.annotation.Nullable String artifactType, @jakarta.annotation.Nullable String artifactFormat, @jakarta.annotation.Nullable File metadata, @jakarta.annotation.Nullable String accessibility) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4JobsIdArtifactsRequestCreation(id, _file, token, expireIn, artifactType, artifactFormat, metadata, accessibility).toEntity(localVarReturnType);
    }

    /**
     * Upload job artifacts
     * Uploads artifacts for a specified job.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Artifacts support not enabled
     * <p><b>409</b> - Conflict
     * <p><b>413</b> - File too large
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param _file The artifact file to store (generated by Multipart middleware)
     * @param token Authentication token of the job.
     * @param expireIn Specify when artifact should expire
     * @param artifactType Type of artifact.
     * @param artifactFormat The format of artifact
     * @param metadata The artifact metadata to store (generated by Multipart middleware)
     * @param accessibility Specify accessibility level of artifact private/public
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4JobsIdArtifactsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String token, @jakarta.annotation.Nullable String expireIn, @jakarta.annotation.Nullable String artifactType, @jakarta.annotation.Nullable String artifactFormat, @jakarta.annotation.Nullable File metadata, @jakarta.annotation.Nullable String accessibility) throws RestClientResponseException {
        return postApiV4JobsIdArtifactsRequestCreation(id, _file, token, expireIn, artifactType, artifactFormat, metadata, accessibility);
    }

    /**
     * Authorize artifacts upload
     * Authorizes uploading artifacts for a specified job.
     * <p><b>200</b> - Upload allowed
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Artifacts support not enabled
     * <p><b>413</b> - File too large
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param postApiV4JobsIdArtifactsAuthorizeRequest The postApiV4JobsIdArtifactsAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4JobsIdArtifactsAuthorizeRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4JobsIdArtifactsAuthorizeRequest postApiV4JobsIdArtifactsAuthorizeRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4JobsIdArtifactsAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/jobs/{id}/artifacts/authorize", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4JobsIdArtifactsAuthorizeRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Authorize artifacts upload
     * Authorizes uploading artifacts for a specified job.
     * <p><b>200</b> - Upload allowed
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Artifacts support not enabled
     * <p><b>413</b> - File too large
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param postApiV4JobsIdArtifactsAuthorizeRequest The postApiV4JobsIdArtifactsAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4JobsIdArtifactsAuthorize(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4JobsIdArtifactsAuthorizeRequest postApiV4JobsIdArtifactsAuthorizeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4JobsIdArtifactsAuthorizeRequestCreation(id, postApiV4JobsIdArtifactsAuthorizeRequest).body(localVarReturnType);
    }

    /**
     * Authorize artifacts upload
     * Authorizes uploading artifacts for a specified job.
     * <p><b>200</b> - Upload allowed
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Artifacts support not enabled
     * <p><b>413</b> - File too large
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param postApiV4JobsIdArtifactsAuthorizeRequest The postApiV4JobsIdArtifactsAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4JobsIdArtifactsAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4JobsIdArtifactsAuthorizeRequest postApiV4JobsIdArtifactsAuthorizeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4JobsIdArtifactsAuthorizeRequestCreation(id, postApiV4JobsIdArtifactsAuthorizeRequest).toEntity(localVarReturnType);
    }

    /**
     * Authorize artifacts upload
     * Authorizes uploading artifacts for a specified job.
     * <p><b>200</b> - Upload allowed
     * <p><b>403</b> - Forbidden
     * <p><b>405</b> - Artifacts support not enabled
     * <p><b>413</b> - File too large
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param postApiV4JobsIdArtifactsAuthorizeRequest The postApiV4JobsIdArtifactsAuthorizeRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4JobsIdArtifactsAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4JobsIdArtifactsAuthorizeRequest postApiV4JobsIdArtifactsAuthorizeRequest) throws RestClientResponseException {
        return postApiV4JobsIdArtifactsAuthorizeRequestCreation(id, postApiV4JobsIdArtifactsAuthorizeRequest);
    }

    /**
     * Request a job
     * Requests a job for a runner to execute.
     * <p><b>201</b> - Job was scheduled
     * <p><b>204</b> - No job for Runner
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Runner is orphaned
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4JobsRequestRequest The postApiV4JobsRequestRequest parameter
     * @return APIEntitiesCiJobRequestResponse
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4JobsRequestRequestCreation(@jakarta.annotation.Nonnull PostApiV4JobsRequestRequest postApiV4JobsRequestRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4JobsRequestRequest' is set
        if (postApiV4JobsRequestRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4JobsRequestRequest' when calling postApiV4JobsRequest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiJobRequestResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/jobs/request", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4JobsRequestRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Request a job
     * Requests a job for a runner to execute.
     * <p><b>201</b> - Job was scheduled
     * <p><b>204</b> - No job for Runner
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Runner is orphaned
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4JobsRequestRequest The postApiV4JobsRequestRequest parameter
     * @return APIEntitiesCiJobRequestResponse
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiJobRequestResponse postApiV4JobsRequest(@jakarta.annotation.Nonnull PostApiV4JobsRequestRequest postApiV4JobsRequestRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiJobRequestResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4JobsRequestRequestCreation(postApiV4JobsRequestRequest).body(localVarReturnType);
    }

    /**
     * Request a job
     * Requests a job for a runner to execute.
     * <p><b>201</b> - Job was scheduled
     * <p><b>204</b> - No job for Runner
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Runner is orphaned
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4JobsRequestRequest The postApiV4JobsRequestRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiJobRequestResponse&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiJobRequestResponse> postApiV4JobsRequestWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4JobsRequestRequest postApiV4JobsRequestRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiJobRequestResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4JobsRequestRequestCreation(postApiV4JobsRequestRequest).toEntity(localVarReturnType);
    }

    /**
     * Request a job
     * Requests a job for a runner to execute.
     * <p><b>201</b> - Job was scheduled
     * <p><b>204</b> - No job for Runner
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Runner is orphaned
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4JobsRequestRequest The postApiV4JobsRequestRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4JobsRequestWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4JobsRequestRequest postApiV4JobsRequestRequest) throws RestClientResponseException {
        return postApiV4JobsRequestRequestCreation(postApiV4JobsRequestRequest);
    }

    /**
     * Update a job
     * Updates the state and other attributes of a job. Used by runners while executing a job.
     * <p><b>200</b> - Job was updated
     * <p><b>202</b> - Update accepted
     * <p><b>400</b> - Unknown parameters
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param putApiV4JobsIdRequest The putApiV4JobsIdRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4JobsIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PutApiV4JobsIdRequest putApiV4JobsIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4JobsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4JobsIdRequest' is set
        if (putApiV4JobsIdRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4JobsIdRequest' when calling putApiV4JobsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/jobs/{id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4JobsIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a job
     * Updates the state and other attributes of a job. Used by runners while executing a job.
     * <p><b>200</b> - Job was updated
     * <p><b>202</b> - Update accepted
     * <p><b>400</b> - Unknown parameters
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param putApiV4JobsIdRequest The putApiV4JobsIdRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4JobsId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PutApiV4JobsIdRequest putApiV4JobsIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4JobsIdRequestCreation(id, putApiV4JobsIdRequest).body(localVarReturnType);
    }

    /**
     * Update a job
     * Updates the state and other attributes of a job. Used by runners while executing a job.
     * <p><b>200</b> - Job was updated
     * <p><b>202</b> - Update accepted
     * <p><b>400</b> - Unknown parameters
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param putApiV4JobsIdRequest The putApiV4JobsIdRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4JobsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PutApiV4JobsIdRequest putApiV4JobsIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4JobsIdRequestCreation(id, putApiV4JobsIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a job
     * Updates the state and other attributes of a job. Used by runners while executing a job.
     * <p><b>200</b> - Job was updated
     * <p><b>202</b> - Update accepted
     * <p><b>400</b> - Unknown parameters
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param putApiV4JobsIdRequest The putApiV4JobsIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4JobsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PutApiV4JobsIdRequest putApiV4JobsIdRequest) throws RestClientResponseException {
        return putApiV4JobsIdRequestCreation(id, putApiV4JobsIdRequest);
    }
}
