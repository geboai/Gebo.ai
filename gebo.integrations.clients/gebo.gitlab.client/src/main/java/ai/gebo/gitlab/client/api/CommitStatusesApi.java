package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCommitStatus;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdStatusesShaRequest;

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
public class CommitStatusesApi {
    private ApiClient apiClient;

    public CommitStatusesApi() {
        this(new ApiClient());
    }

    public CommitStatusesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all commit statuses
     * Lists all commit statuses for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param ref Name of the branch or tag. Defaults to the default branch.
     * @param stage Filter statuses by [build stage](https://docs.gitlab.com/ci/yaml/#stages). For example, &#x60;test&#x60;.
     * @param name Filter statuses by [job name](https://docs.gitlab.com/ci/yaml/#job-keywords).
     * @param pipelineId Filter statuses by pipeline ID. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/176142) in GitLab 17.9.
     * @param all If &#x60;true&#x60;, includes all statuses instead of only the latest.
     * @param orderBy Sort results by the specified field. With &#x60;pipeline_id&#x60;, statuses of the same pipeline are also sorted by &#x60;id&#x60; in the &#x60;sort&#x60; direction. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/176142) in GitLab 17.9.
     * @param sort Sort statuses in ascending or descending order. Defaults to &#x60;asc&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/176142) in GitLab 17.9.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCommitStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaStatusesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String stage, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable Integer pipelineId, @jakarta.annotation.Nullable Boolean all, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryCommitsShaStatuses", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha' is set
        if (sha == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha' when calling getApiV4ProjectsIdRepositoryCommitsShaStatuses", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("sha", sha);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ref", ref));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "stage", stage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pipeline_id", pipelineId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "all", all));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCommitStatus>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{sha}/statuses", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all commit statuses
     * Lists all commit statuses for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param ref Name of the branch or tag. Defaults to the default branch.
     * @param stage Filter statuses by [build stage](https://docs.gitlab.com/ci/yaml/#stages). For example, &#x60;test&#x60;.
     * @param name Filter statuses by [job name](https://docs.gitlab.com/ci/yaml/#job-keywords).
     * @param pipelineId Filter statuses by pipeline ID. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/176142) in GitLab 17.9.
     * @param all If &#x60;true&#x60;, includes all statuses instead of only the latest.
     * @param orderBy Sort results by the specified field. With &#x60;pipeline_id&#x60;, statuses of the same pipeline are also sorted by &#x60;id&#x60; in the &#x60;sort&#x60; direction. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/176142) in GitLab 17.9.
     * @param sort Sort statuses in ascending or descending order. Defaults to &#x60;asc&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/176142) in GitLab 17.9.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCommitStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCommitStatus> getApiV4ProjectsIdRepositoryCommitsShaStatuses(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String stage, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable Integer pipelineId, @jakarta.annotation.Nullable Boolean all, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCommitStatus>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaStatusesRequestCreation(id, sha, ref, stage, name, pipelineId, all, orderBy, sort, page, perPage).body(localVarReturnType);
    }

    /**
     * List all commit statuses
     * Lists all commit statuses for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param ref Name of the branch or tag. Defaults to the default branch.
     * @param stage Filter statuses by [build stage](https://docs.gitlab.com/ci/yaml/#stages). For example, &#x60;test&#x60;.
     * @param name Filter statuses by [job name](https://docs.gitlab.com/ci/yaml/#job-keywords).
     * @param pipelineId Filter statuses by pipeline ID. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/176142) in GitLab 17.9.
     * @param all If &#x60;true&#x60;, includes all statuses instead of only the latest.
     * @param orderBy Sort results by the specified field. With &#x60;pipeline_id&#x60;, statuses of the same pipeline are also sorted by &#x60;id&#x60; in the &#x60;sort&#x60; direction. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/176142) in GitLab 17.9.
     * @param sort Sort statuses in ascending or descending order. Defaults to &#x60;asc&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/176142) in GitLab 17.9.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCommitStatus&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCommitStatus>> getApiV4ProjectsIdRepositoryCommitsShaStatusesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String stage, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable Integer pipelineId, @jakarta.annotation.Nullable Boolean all, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCommitStatus>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaStatusesRequestCreation(id, sha, ref, stage, name, pipelineId, all, orderBy, sort, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all commit statuses
     * Lists all commit statuses for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param ref Name of the branch or tag. Defaults to the default branch.
     * @param stage Filter statuses by [build stage](https://docs.gitlab.com/ci/yaml/#stages). For example, &#x60;test&#x60;.
     * @param name Filter statuses by [job name](https://docs.gitlab.com/ci/yaml/#job-keywords).
     * @param pipelineId Filter statuses by pipeline ID. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/176142) in GitLab 17.9.
     * @param all If &#x60;true&#x60;, includes all statuses instead of only the latest.
     * @param orderBy Sort results by the specified field. With &#x60;pipeline_id&#x60;, statuses of the same pipeline are also sorted by &#x60;id&#x60; in the &#x60;sort&#x60; direction. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/176142) in GitLab 17.9.
     * @param sort Sort statuses in ascending or descending order. Defaults to &#x60;asc&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/176142) in GitLab 17.9.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaStatusesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String stage, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable Integer pipelineId, @jakarta.annotation.Nullable Boolean all, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryCommitsShaStatusesRequestCreation(id, sha, ref, stage, name, pipelineId, all, orderBy, sort, page, perPage);
    }

    /**
     * Create or update a commit pipeline status
     * Creates or updates the status of a commit represented by a job in an &#x60;external&#x60; stage. If the commit is associated with a merge request, target the commit in the merge request source branch. The job is added to the newest matching pipeline for the commit. If no matching pipeline exists, a new pipeline is created with &#x60;CI_PIPELINE_SOURCE: external&#x60;. When duplicate pipelines exist for the same commit, the newest one receives the status. If the matching pipeline exceeds the maximum number of jobs in a pipeline, a &#x60;422&#x60; error is returned when &#x60;pipeline_id&#x60; is specified. Otherwise, a new pipeline is created. If an update is already in progress for the same SHA and ref, a &#x60;409&#x60; error is returned, and the request can be retried.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Another update to this commit status is in progress
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param postApiV4ProjectsIdStatusesShaRequest The postApiV4ProjectsIdStatusesShaRequest parameter
     * @return APIEntitiesCommitStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdStatusesShaRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdStatusesShaRequest postApiV4ProjectsIdStatusesShaRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdStatusesSha", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha' is set
        if (sha == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha' when calling postApiV4ProjectsIdStatusesSha", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdStatusesShaRequest' is set
        if (postApiV4ProjectsIdStatusesShaRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdStatusesShaRequest' when calling postApiV4ProjectsIdStatusesSha", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("sha", sha);

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

        ParameterizedTypeReference<APIEntitiesCommitStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/statuses/{sha}", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdStatusesShaRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create or update a commit pipeline status
     * Creates or updates the status of a commit represented by a job in an &#x60;external&#x60; stage. If the commit is associated with a merge request, target the commit in the merge request source branch. The job is added to the newest matching pipeline for the commit. If no matching pipeline exists, a new pipeline is created with &#x60;CI_PIPELINE_SOURCE: external&#x60;. When duplicate pipelines exist for the same commit, the newest one receives the status. If the matching pipeline exceeds the maximum number of jobs in a pipeline, a &#x60;422&#x60; error is returned when &#x60;pipeline_id&#x60; is specified. Otherwise, a new pipeline is created. If an update is already in progress for the same SHA and ref, a &#x60;409&#x60; error is returned, and the request can be retried.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Another update to this commit status is in progress
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param postApiV4ProjectsIdStatusesShaRequest The postApiV4ProjectsIdStatusesShaRequest parameter
     * @return APIEntitiesCommitStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCommitStatus postApiV4ProjectsIdStatusesSha(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdStatusesShaRequest postApiV4ProjectsIdStatusesShaRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommitStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdStatusesShaRequestCreation(id, sha, postApiV4ProjectsIdStatusesShaRequest).body(localVarReturnType);
    }

    /**
     * Create or update a commit pipeline status
     * Creates or updates the status of a commit represented by a job in an &#x60;external&#x60; stage. If the commit is associated with a merge request, target the commit in the merge request source branch. The job is added to the newest matching pipeline for the commit. If no matching pipeline exists, a new pipeline is created with &#x60;CI_PIPELINE_SOURCE: external&#x60;. When duplicate pipelines exist for the same commit, the newest one receives the status. If the matching pipeline exceeds the maximum number of jobs in a pipeline, a &#x60;422&#x60; error is returned when &#x60;pipeline_id&#x60; is specified. Otherwise, a new pipeline is created. If an update is already in progress for the same SHA and ref, a &#x60;409&#x60; error is returned, and the request can be retried.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Another update to this commit status is in progress
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param postApiV4ProjectsIdStatusesShaRequest The postApiV4ProjectsIdStatusesShaRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCommitStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCommitStatus> postApiV4ProjectsIdStatusesShaWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdStatusesShaRequest postApiV4ProjectsIdStatusesShaRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommitStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdStatusesShaRequestCreation(id, sha, postApiV4ProjectsIdStatusesShaRequest).toEntity(localVarReturnType);
    }

    /**
     * Create or update a commit pipeline status
     * Creates or updates the status of a commit represented by a job in an &#x60;external&#x60; stage. If the commit is associated with a merge request, target the commit in the merge request source branch. The job is added to the newest matching pipeline for the commit. If no matching pipeline exists, a new pipeline is created with &#x60;CI_PIPELINE_SOURCE: external&#x60;. When duplicate pipelines exist for the same commit, the newest one receives the status. If the matching pipeline exceeds the maximum number of jobs in a pipeline, a &#x60;422&#x60; error is returned when &#x60;pipeline_id&#x60; is specified. Otherwise, a new pipeline is created. If an update is already in progress for the same SHA and ref, a &#x60;409&#x60; error is returned, and the request can be retried.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Another update to this commit status is in progress
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param postApiV4ProjectsIdStatusesShaRequest The postApiV4ProjectsIdStatusesShaRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdStatusesShaWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdStatusesShaRequest postApiV4ProjectsIdStatusesShaRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdStatusesShaRequestCreation(id, sha, postApiV4ProjectsIdStatusesShaRequest);
    }
}
