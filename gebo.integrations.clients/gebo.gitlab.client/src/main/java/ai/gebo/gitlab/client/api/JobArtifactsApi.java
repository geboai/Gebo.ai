package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCiJob;
import ai.gebo.gitlab.client.model.APIEntitiesCiJobArtifactEntry;
import java.io.File;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;

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
public class JobArtifactsApi {
    private ApiClient apiClient;

    public JobArtifactsApi() {
        this(new ApiClient());
    }

    public JobArtifactsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete all job artifacts in a project
     * Deletes job artifacts that are eligible for deletion from all jobs in a specified project. By default, artifacts from the most recently completed pipeline and the most recent successful pipeline for each ref are kept, regardless of expiration. The request sets the expiry of all deletable job artifacts to the current time. The files are then deleted by the regular cleanup of expired job artifacts, which runs asynchronously on a schedule, so deletion might take a short time. Job logs are never deleted. Artifacts cannot be recovered after deletion. You must have the Maintainer or Owner role for the project.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdArtifactsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdArtifacts", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/artifacts", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete all job artifacts in a project
     * Deletes job artifacts that are eligible for deletion from all jobs in a specified project. By default, artifacts from the most recently completed pipeline and the most recent successful pipeline for each ref are kept, regardless of expiration. The request sets the expiry of all deletable job artifacts to the current time. The files are then deleted by the regular cleanup of expired job artifacts, which runs asynchronously on a schedule, so deletion might take a short time. Job logs are never deleted. Artifacts cannot be recovered after deletion. You must have the Maintainer or Owner role for the project.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdArtifacts(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdArtifactsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete all job artifacts in a project
     * Deletes job artifacts that are eligible for deletion from all jobs in a specified project. By default, artifacts from the most recently completed pipeline and the most recent successful pipeline for each ref are kept, regardless of expiration. The request sets the expiry of all deletable job artifacts to the current time. The files are then deleted by the regular cleanup of expired job artifacts, which runs asynchronously on a schedule, so deletion might take a short time. Job logs are never deleted. Artifacts cannot be recovered after deletion. You must have the Maintainer or Owner role for the project.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdArtifactsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdArtifactsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete all job artifacts in a project
     * Deletes job artifacts that are eligible for deletion from all jobs in a specified project. By default, artifacts from the most recently completed pipeline and the most recent successful pipeline for each ref are kept, regardless of expiration. The request sets the expiry of all deletable job artifacts to the current time. The files are then deleted by the regular cleanup of expired job artifacts, which runs asynchronously on a schedule, so deletion might take a short time. Job logs are never deleted. Artifacts cannot be recovered after deletion. You must have the Maintainer or Owner role for the project.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdArtifactsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return deleteApiV4ProjectsIdArtifactsRequestCreation(id);
    }

    /**
     * Delete job artifacts
     * Deletes job artifacts from a specified job in a project. This includes archived files, metadata, and report artifacts such as JUnit reports. The job log is not deleted. Artifacts cannot be recovered after deletion. You must have the Maintainer or Owner role for the project.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdJobsJobIdArtifactsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdJobsJobIdArtifacts", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'jobId' is set
        if (jobId == null) {
            throw new RestClientResponseException("Missing the required parameter 'jobId' when calling deleteApiV4ProjectsIdJobsJobIdArtifacts", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("job_id", jobId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/jobs/{job_id}/artifacts", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete job artifacts
     * Deletes job artifacts from a specified job in a project. This includes archived files, metadata, and report artifacts such as JUnit reports. The job log is not deleted. Artifacts cannot be recovered after deletion. You must have the Maintainer or Owner role for the project.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdJobsJobIdArtifacts(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdJobsJobIdArtifactsRequestCreation(id, jobId).body(localVarReturnType);
    }

    /**
     * Delete job artifacts
     * Deletes job artifacts from a specified job in a project. This includes archived files, metadata, and report artifacts such as JUnit reports. The job log is not deleted. Artifacts cannot be recovered after deletion. You must have the Maintainer or Owner role for the project.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdJobsJobIdArtifactsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdJobsJobIdArtifactsRequestCreation(id, jobId).toEntity(localVarReturnType);
    }

    /**
     * Delete job artifacts
     * Deletes job artifacts from a specified job in a project. This includes archived files, metadata, and report artifacts such as JUnit reports. The job log is not deleted. Artifacts cannot be recovered after deletion. You must have the Maintainer or Owner role for the project.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdJobsJobIdArtifactsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdJobsJobIdArtifactsRequestCreation(id, jobId);
    }

    /**
     * Retrieve job artifacts
     * Retrieves the artifacts archive for the latest successful job on a specified branch or tag. The latest successful pipeline is determined by its creation time. The start or end time of individual jobs does not affect which pipeline is the latest. When &#x60;search_recent_successful_pipelines&#x60; is &#x60;true&#x60;, the search includes up to 100 recent successful pipelines for the ref. Otherwise, for parent and child pipelines, artifacts are searched in hierarchical order from parent to child. If both parent and child pipelines have a job with the same name, the artifact from the parent pipeline is returned. The pipeline must have completed with a &#x60;success&#x60; status, so any manual jobs in it must either complete successfully or have &#x60;allow_failure: true&#x60; set. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param refName Branch or tag name in the repository. &#x60;HEAD&#x60; or SHA references are not supported. For merge request pipelines, use &#x60;refs/merge-requests/:iid/head&#x60; instead of the branch name.
     * @param job Name of the job.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param searchRecentSuccessfulPipelines If &#x60;true&#x60;, searches across recent successful pipelines instead of just the latest one. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/515864) in GitLab 18.7.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdJobsArtifactsRefNameDownloadRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String refName, @jakarta.annotation.Nullable String job, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable Boolean searchRecentSuccessfulPipelines, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdJobsArtifactsRefNameDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'refName' is set
        if (refName == null) {
            throw new RestClientResponseException("Missing the required parameter 'refName' when calling getApiV4ProjectsIdJobsArtifactsRefNameDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'job' is set
        if (job == null) {
            throw new RestClientResponseException("Missing the required parameter 'job' when calling getApiV4ProjectsIdJobsArtifactsRefNameDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("ref_name", refName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "job", job));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "job_token", jobToken));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search_recent_successful_pipelines", searchRecentSuccessfulPipelines));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "download_mode", downloadMode));

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/jobs/artifacts/{ref_name}/download", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve job artifacts
     * Retrieves the artifacts archive for the latest successful job on a specified branch or tag. The latest successful pipeline is determined by its creation time. The start or end time of individual jobs does not affect which pipeline is the latest. When &#x60;search_recent_successful_pipelines&#x60; is &#x60;true&#x60;, the search includes up to 100 recent successful pipelines for the ref. Otherwise, for parent and child pipelines, artifacts are searched in hierarchical order from parent to child. If both parent and child pipelines have a job with the same name, the artifact from the parent pipeline is returned. The pipeline must have completed with a &#x60;success&#x60; status, so any manual jobs in it must either complete successfully or have &#x60;allow_failure: true&#x60; set. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param refName Branch or tag name in the repository. &#x60;HEAD&#x60; or SHA references are not supported. For merge request pipelines, use &#x60;refs/merge-requests/:iid/head&#x60; instead of the branch name.
     * @param job Name of the job.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param searchRecentSuccessfulPipelines If &#x60;true&#x60;, searches across recent successful pipelines instead of just the latest one. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/515864) in GitLab 18.7.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4ProjectsIdJobsArtifactsRefNameDownload(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String refName, @jakarta.annotation.Nullable String job, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable Boolean searchRecentSuccessfulPipelines, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdJobsArtifactsRefNameDownloadRequestCreation(id, refName, job, jobToken, searchRecentSuccessfulPipelines, downloadMode).body(localVarReturnType);
    }

    /**
     * Retrieve job artifacts
     * Retrieves the artifacts archive for the latest successful job on a specified branch or tag. The latest successful pipeline is determined by its creation time. The start or end time of individual jobs does not affect which pipeline is the latest. When &#x60;search_recent_successful_pipelines&#x60; is &#x60;true&#x60;, the search includes up to 100 recent successful pipelines for the ref. Otherwise, for parent and child pipelines, artifacts are searched in hierarchical order from parent to child. If both parent and child pipelines have a job with the same name, the artifact from the parent pipeline is returned. The pipeline must have completed with a &#x60;success&#x60; status, so any manual jobs in it must either complete successfully or have &#x60;allow_failure: true&#x60; set. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param refName Branch or tag name in the repository. &#x60;HEAD&#x60; or SHA references are not supported. For merge request pipelines, use &#x60;refs/merge-requests/:iid/head&#x60; instead of the branch name.
     * @param job Name of the job.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param searchRecentSuccessfulPipelines If &#x60;true&#x60;, searches across recent successful pipelines instead of just the latest one. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/515864) in GitLab 18.7.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4ProjectsIdJobsArtifactsRefNameDownloadWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String refName, @jakarta.annotation.Nullable String job, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable Boolean searchRecentSuccessfulPipelines, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdJobsArtifactsRefNameDownloadRequestCreation(id, refName, job, jobToken, searchRecentSuccessfulPipelines, downloadMode).toEntity(localVarReturnType);
    }

    /**
     * Retrieve job artifacts
     * Retrieves the artifacts archive for the latest successful job on a specified branch or tag. The latest successful pipeline is determined by its creation time. The start or end time of individual jobs does not affect which pipeline is the latest. When &#x60;search_recent_successful_pipelines&#x60; is &#x60;true&#x60;, the search includes up to 100 recent successful pipelines for the ref. Otherwise, for parent and child pipelines, artifacts are searched in hierarchical order from parent to child. If both parent and child pipelines have a job with the same name, the artifact from the parent pipeline is returned. The pipeline must have completed with a &#x60;success&#x60; status, so any manual jobs in it must either complete successfully or have &#x60;allow_failure: true&#x60; set. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param refName Branch or tag name in the repository. &#x60;HEAD&#x60; or SHA references are not supported. For merge request pipelines, use &#x60;refs/merge-requests/:iid/head&#x60; instead of the branch name.
     * @param job Name of the job.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param searchRecentSuccessfulPipelines If &#x60;true&#x60;, searches across recent successful pipelines instead of just the latest one. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/515864) in GitLab 18.7.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdJobsArtifactsRefNameDownloadWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String refName, @jakarta.annotation.Nullable String job, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable Boolean searchRecentSuccessfulPipelines, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        return getApiV4ProjectsIdJobsArtifactsRefNameDownloadRequestCreation(id, refName, job, jobToken, searchRecentSuccessfulPipelines, downloadMode);
    }

    /**
     * Download a specific file from artifacts archive from a ref
     * Downloads a single file from the artifacts of the latest successful job on a specified branch or tag. The file is extracted from the archive and streamed to the client. When &#x60;search_recent_successful_pipelines&#x60; is &#x60;true&#x60;, the search includes up to 100 recent successful pipelines for the ref. Otherwise, for parent and child pipelines, artifacts are searched in hierarchical order from parent to child. If both parent and child pipelines have a job with the same name, the artifact from the parent pipeline is returned. The pipeline must have completed with a &#x60;success&#x60; status, so any manual jobs in it must either complete successfully or have &#x60;allow_failure: true&#x60; set. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option. This feature was introduced in GitLab 11.5
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param refName Branch or tag name in the repository. &#x60;HEAD&#x60; or SHA references are not supported. For merge request pipelines, use &#x60;refs/merge-requests/:iid/head&#x60; instead of the branch name.
     * @param job Name of the job.
     * @param artifactPath Path to a file inside the artifacts archive.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param searchRecentSuccessfulPipelines If &#x60;true&#x60;, searches across recent successful pipelines instead of just the latest one. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/515864) in GitLab 18.9.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdJobsArtifactsRefNameRawArtifactPathRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String refName, @jakarta.annotation.Nullable String job, @jakarta.annotation.Nonnull String artifactPath, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable Boolean searchRecentSuccessfulPipelines) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdJobsArtifactsRefNameRawArtifactPath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'refName' is set
        if (refName == null) {
            throw new RestClientResponseException("Missing the required parameter 'refName' when calling getApiV4ProjectsIdJobsArtifactsRefNameRawArtifactPath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'job' is set
        if (job == null) {
            throw new RestClientResponseException("Missing the required parameter 'job' when calling getApiV4ProjectsIdJobsArtifactsRefNameRawArtifactPath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'artifactPath' is set
        if (artifactPath == null) {
            throw new RestClientResponseException("Missing the required parameter 'artifactPath' when calling getApiV4ProjectsIdJobsArtifactsRefNameRawArtifactPath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("ref_name", refName);
        pathParams.put("artifact_path", artifactPath);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "job", job));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "job_token", jobToken));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search_recent_successful_pipelines", searchRecentSuccessfulPipelines));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/jobs/artifacts/{ref_name}/raw/{artifact_path}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a specific file from artifacts archive from a ref
     * Downloads a single file from the artifacts of the latest successful job on a specified branch or tag. The file is extracted from the archive and streamed to the client. When &#x60;search_recent_successful_pipelines&#x60; is &#x60;true&#x60;, the search includes up to 100 recent successful pipelines for the ref. Otherwise, for parent and child pipelines, artifacts are searched in hierarchical order from parent to child. If both parent and child pipelines have a job with the same name, the artifact from the parent pipeline is returned. The pipeline must have completed with a &#x60;success&#x60; status, so any manual jobs in it must either complete successfully or have &#x60;allow_failure: true&#x60; set. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option. This feature was introduced in GitLab 11.5
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param refName Branch or tag name in the repository. &#x60;HEAD&#x60; or SHA references are not supported. For merge request pipelines, use &#x60;refs/merge-requests/:iid/head&#x60; instead of the branch name.
     * @param job Name of the job.
     * @param artifactPath Path to a file inside the artifacts archive.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param searchRecentSuccessfulPipelines If &#x60;true&#x60;, searches across recent successful pipelines instead of just the latest one. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/515864) in GitLab 18.9.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdJobsArtifactsRefNameRawArtifactPath(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String refName, @jakarta.annotation.Nullable String job, @jakarta.annotation.Nonnull String artifactPath, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable Boolean searchRecentSuccessfulPipelines) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdJobsArtifactsRefNameRawArtifactPathRequestCreation(id, refName, job, artifactPath, jobToken, searchRecentSuccessfulPipelines).body(localVarReturnType);
    }

    /**
     * Download a specific file from artifacts archive from a ref
     * Downloads a single file from the artifacts of the latest successful job on a specified branch or tag. The file is extracted from the archive and streamed to the client. When &#x60;search_recent_successful_pipelines&#x60; is &#x60;true&#x60;, the search includes up to 100 recent successful pipelines for the ref. Otherwise, for parent and child pipelines, artifacts are searched in hierarchical order from parent to child. If both parent and child pipelines have a job with the same name, the artifact from the parent pipeline is returned. The pipeline must have completed with a &#x60;success&#x60; status, so any manual jobs in it must either complete successfully or have &#x60;allow_failure: true&#x60; set. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option. This feature was introduced in GitLab 11.5
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param refName Branch or tag name in the repository. &#x60;HEAD&#x60; or SHA references are not supported. For merge request pipelines, use &#x60;refs/merge-requests/:iid/head&#x60; instead of the branch name.
     * @param job Name of the job.
     * @param artifactPath Path to a file inside the artifacts archive.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param searchRecentSuccessfulPipelines If &#x60;true&#x60;, searches across recent successful pipelines instead of just the latest one. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/515864) in GitLab 18.9.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdJobsArtifactsRefNameRawArtifactPathWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String refName, @jakarta.annotation.Nullable String job, @jakarta.annotation.Nonnull String artifactPath, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable Boolean searchRecentSuccessfulPipelines) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdJobsArtifactsRefNameRawArtifactPathRequestCreation(id, refName, job, artifactPath, jobToken, searchRecentSuccessfulPipelines).toEntity(localVarReturnType);
    }

    /**
     * Download a specific file from artifacts archive from a ref
     * Downloads a single file from the artifacts of the latest successful job on a specified branch or tag. The file is extracted from the archive and streamed to the client. When &#x60;search_recent_successful_pipelines&#x60; is &#x60;true&#x60;, the search includes up to 100 recent successful pipelines for the ref. Otherwise, for parent and child pipelines, artifacts are searched in hierarchical order from parent to child. If both parent and child pipelines have a job with the same name, the artifact from the parent pipeline is returned. The pipeline must have completed with a &#x60;success&#x60; status, so any manual jobs in it must either complete successfully or have &#x60;allow_failure: true&#x60; set. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option. This feature was introduced in GitLab 11.5
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param refName Branch or tag name in the repository. &#x60;HEAD&#x60; or SHA references are not supported. For merge request pipelines, use &#x60;refs/merge-requests/:iid/head&#x60; instead of the branch name.
     * @param job Name of the job.
     * @param artifactPath Path to a file inside the artifacts archive.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param searchRecentSuccessfulPipelines If &#x60;true&#x60;, searches across recent successful pipelines instead of just the latest one. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/515864) in GitLab 18.9.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdJobsArtifactsRefNameRawArtifactPathWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String refName, @jakarta.annotation.Nullable String job, @jakarta.annotation.Nonnull String artifactPath, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable Boolean searchRecentSuccessfulPipelines) throws RestClientResponseException {
        return getApiV4ProjectsIdJobsArtifactsRefNameRawArtifactPathRequestCreation(id, refName, job, artifactPath, jobToken, searchRecentSuccessfulPipelines);
    }

    /**
     * Download an artifact from a job
     * Downloads the artifacts archive of a specified job. To download a specific report file, such as JUnit test results, use the &#x60;file_type&#x60; attribute. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option. This feature was introduced in GitLab 8.5. The &#x60;file_type&#x60; attribute was added in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @param fileType Type of artifact to download. Set a specific report type to download that report file directly instead of the full artifacts archive. &#x60;performance&#x60; and &#x60;browser_performance&#x60; share a report group. If the job uploaded both, the requested type is returned, and if it uploaded only one, that artifact is returned whichever type was requested. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/35805) in GitLab 19.4.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdJobsJobIdArtifactsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId, @jakarta.annotation.Nullable String fileType, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdJobsJobIdArtifacts", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'jobId' is set
        if (jobId == null) {
            throw new RestClientResponseException("Missing the required parameter 'jobId' when calling getApiV4ProjectsIdJobsJobIdArtifacts", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("job_id", jobId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "file_type", fileType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "job_token", jobToken));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "download_mode", downloadMode));

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/jobs/{job_id}/artifacts", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download an artifact from a job
     * Downloads the artifacts archive of a specified job. To download a specific report file, such as JUnit test results, use the &#x60;file_type&#x60; attribute. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option. This feature was introduced in GitLab 8.5. The &#x60;file_type&#x60; attribute was added in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @param fileType Type of artifact to download. Set a specific report type to download that report file directly instead of the full artifacts archive. &#x60;performance&#x60; and &#x60;browser_performance&#x60; share a report group. If the job uploaded both, the requested type is returned, and if it uploaded only one, that artifact is returned whichever type was requested. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/35805) in GitLab 19.4.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4ProjectsIdJobsJobIdArtifacts(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId, @jakarta.annotation.Nullable String fileType, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdJobsJobIdArtifactsRequestCreation(id, jobId, fileType, jobToken, downloadMode).body(localVarReturnType);
    }

    /**
     * Download an artifact from a job
     * Downloads the artifacts archive of a specified job. To download a specific report file, such as JUnit test results, use the &#x60;file_type&#x60; attribute. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option. This feature was introduced in GitLab 8.5. The &#x60;file_type&#x60; attribute was added in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @param fileType Type of artifact to download. Set a specific report type to download that report file directly instead of the full artifacts archive. &#x60;performance&#x60; and &#x60;browser_performance&#x60; share a report group. If the job uploaded both, the requested type is returned, and if it uploaded only one, that artifact is returned whichever type was requested. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/35805) in GitLab 19.4.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4ProjectsIdJobsJobIdArtifactsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId, @jakarta.annotation.Nullable String fileType, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdJobsJobIdArtifactsRequestCreation(id, jobId, fileType, jobToken, downloadMode).toEntity(localVarReturnType);
    }

    /**
     * Download an artifact from a job
     * Downloads the artifacts archive of a specified job. To download a specific report file, such as JUnit test results, use the &#x60;file_type&#x60; attribute. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option. This feature was introduced in GitLab 8.5. The &#x60;file_type&#x60; attribute was added in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @param fileType Type of artifact to download. Set a specific report type to download that report file directly instead of the full artifacts archive. &#x60;performance&#x60; and &#x60;browser_performance&#x60; share a report group. If the job uploaded both, the requested type is returned, and if it uploaded only one, that artifact is returned whichever type was requested. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/35805) in GitLab 19.4.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdJobsJobIdArtifactsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId, @jakarta.annotation.Nullable String fileType, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        return getApiV4ProjectsIdJobsJobIdArtifactsRequestCreation(id, jobId, fileType, jobToken, downloadMode);
    }

    /**
     * Download a specific file from artifacts archive
     * Downloads a single file from the artifacts of a specified job. The file is extracted from the archive and streamed to the client. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option. This feature was introduced in GitLab 10.0
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @param artifactPath Path to a file inside the artifacts archive.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdJobsJobIdArtifactsArtifactPathRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId, @jakarta.annotation.Nonnull String artifactPath, @jakarta.annotation.Nullable String jobToken) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdJobsJobIdArtifactsArtifactPath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'jobId' is set
        if (jobId == null) {
            throw new RestClientResponseException("Missing the required parameter 'jobId' when calling getApiV4ProjectsIdJobsJobIdArtifactsArtifactPath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'artifactPath' is set
        if (artifactPath == null) {
            throw new RestClientResponseException("Missing the required parameter 'artifactPath' when calling getApiV4ProjectsIdJobsJobIdArtifactsArtifactPath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("job_id", jobId);
        pathParams.put("artifact_path", artifactPath);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "job_token", jobToken));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/jobs/{job_id}/artifacts/{artifact_path}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a specific file from artifacts archive
     * Downloads a single file from the artifacts of a specified job. The file is extracted from the archive and streamed to the client. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option. This feature was introduced in GitLab 10.0
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @param artifactPath Path to a file inside the artifacts archive.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdJobsJobIdArtifactsArtifactPath(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId, @jakarta.annotation.Nonnull String artifactPath, @jakarta.annotation.Nullable String jobToken) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdJobsJobIdArtifactsArtifactPathRequestCreation(id, jobId, artifactPath, jobToken).body(localVarReturnType);
    }

    /**
     * Download a specific file from artifacts archive
     * Downloads a single file from the artifacts of a specified job. The file is extracted from the archive and streamed to the client. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option. This feature was introduced in GitLab 10.0
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @param artifactPath Path to a file inside the artifacts archive.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdJobsJobIdArtifactsArtifactPathWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId, @jakarta.annotation.Nonnull String artifactPath, @jakarta.annotation.Nullable String jobToken) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdJobsJobIdArtifactsArtifactPathRequestCreation(id, jobId, artifactPath, jobToken).toEntity(localVarReturnType);
    }

    /**
     * Download a specific file from artifacts archive
     * Downloads a single file from the artifacts of a specified job. The file is extracted from the archive and streamed to the client. On GitLab.com, the request might redirect through a CDN, so clients must follow redirects, for example with the cURL &#x60;--location&#x60; option. This feature was introduced in GitLab 10.0
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @param artifactPath Path to a file inside the artifacts archive.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdJobsJobIdArtifactsArtifactPathWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId, @jakarta.annotation.Nonnull String artifactPath, @jakarta.annotation.Nullable String jobToken) throws RestClientResponseException {
        return getApiV4ProjectsIdJobsJobIdArtifactsArtifactPathRequestCreation(id, jobId, artifactPath, jobToken);
    }

    /**
     * List all files in an artifacts archive
     * Lists all files and directories in a specified artifacts archive. It reads the artifact metadata without extracting the archive. This feature was introduced in GitLab 18.8.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @param path Path to browse in the artifacts archive. Defaults to the root directory.
     * @param recursive If &#x60;true&#x60;, returns all entries recursively.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiJobArtifactEntry&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdJobsJobIdArtifactsTreeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean recursive, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdJobsJobIdArtifactsTree", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'jobId' is set
        if (jobId == null) {
            throw new RestClientResponseException("Missing the required parameter 'jobId' when calling getApiV4ProjectsIdJobsJobIdArtifactsTree", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("job_id", jobId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "path", path));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "recursive", recursive));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "job_token", jobToken));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCiJobArtifactEntry>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/jobs/{job_id}/artifacts/tree", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all files in an artifacts archive
     * Lists all files and directories in a specified artifacts archive. It reads the artifact metadata without extracting the archive. This feature was introduced in GitLab 18.8.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @param path Path to browse in the artifacts archive. Defaults to the root directory.
     * @param recursive If &#x60;true&#x60;, returns all entries recursively.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiJobArtifactEntry&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiJobArtifactEntry> getApiV4ProjectsIdJobsJobIdArtifactsTree(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean recursive, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiJobArtifactEntry>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdJobsJobIdArtifactsTreeRequestCreation(id, jobId, path, recursive, jobToken, page, perPage).body(localVarReturnType);
    }

    /**
     * List all files in an artifacts archive
     * Lists all files and directories in a specified artifacts archive. It reads the artifact metadata without extracting the archive. This feature was introduced in GitLab 18.8.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @param path Path to browse in the artifacts archive. Defaults to the root directory.
     * @param recursive If &#x60;true&#x60;, returns all entries recursively.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiJobArtifactEntry&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiJobArtifactEntry>> getApiV4ProjectsIdJobsJobIdArtifactsTreeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean recursive, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiJobArtifactEntry>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdJobsJobIdArtifactsTreeRequestCreation(id, jobId, path, recursive, jobToken, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all files in an artifacts archive
     * Lists all files and directories in a specified artifacts archive. It reads the artifact metadata without extracting the archive. This feature was introduced in GitLab 18.8.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @param path Path to browse in the artifacts archive. Defaults to the root directory.
     * @param recursive If &#x60;true&#x60;, returns all entries recursively.
     * @param jobToken CI/CD job token for multi-project pipelines. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdJobsJobIdArtifactsTreeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean recursive, @jakarta.annotation.Nullable String jobToken, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdJobsJobIdArtifactsTreeRequestCreation(id, jobId, path, recursive, jobToken, page, perPage);
    }

    /**
     * Retain job artifacts
     * Retains job artifacts. Prevents artifacts for a job from being automatically deleted when they reach their expiration date.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @return APIEntitiesCiJob
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdJobsJobIdArtifactsKeepRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdJobsJobIdArtifactsKeep", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'jobId' is set
        if (jobId == null) {
            throw new RestClientResponseException("Missing the required parameter 'jobId' when calling postApiV4ProjectsIdJobsJobIdArtifactsKeep", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("job_id", jobId);

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

        ParameterizedTypeReference<APIEntitiesCiJob> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/jobs/{job_id}/artifacts/keep", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retain job artifacts
     * Retains job artifacts. Prevents artifacts for a job from being automatically deleted when they reach their expiration date.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @return APIEntitiesCiJob
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiJob postApiV4ProjectsIdJobsJobIdArtifactsKeep(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiJob> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdJobsJobIdArtifactsKeepRequestCreation(id, jobId).body(localVarReturnType);
    }

    /**
     * Retain job artifacts
     * Retains job artifacts. Prevents artifacts for a job from being automatically deleted when they reach their expiration date.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @return ResponseEntity&lt;APIEntitiesCiJob&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiJob> postApiV4ProjectsIdJobsJobIdArtifactsKeepWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiJob> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdJobsJobIdArtifactsKeepRequestCreation(id, jobId).toEntity(localVarReturnType);
    }

    /**
     * Retain job artifacts
     * Retains job artifacts. Prevents artifacts for a job from being automatically deleted when they reach their expiration date.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param jobId ID of the job.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdJobsJobIdArtifactsKeepWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer jobId) throws RestClientResponseException {
        return postApiV4ProjectsIdJobsJobIdArtifactsKeepRequestCreation(id, jobId);
    }
}
