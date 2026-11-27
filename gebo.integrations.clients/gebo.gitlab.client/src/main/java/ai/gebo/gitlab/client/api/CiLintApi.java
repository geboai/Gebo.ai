package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCiLintResult;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdCiLintRequest;

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
public class CiLintApi {
    private ApiClient apiClient;

    public CiLintApi() {
        this(new ApiClient());
    }

    public CiLintApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Validate existing CI/CD configuration
     * Validates the existing &#x60;.gitlab-ci.yml&#x60; configuration for a specified project. The validation runs in the context of the project, using the CI/CD variables of the project and searching the files of the project for &#x60;include:local&#x60; entries. If the &#x60;ci_lint_api_require_authentication&#x60; setting is enabled, this endpoint requires authentication, including for public projects. This endpoint does not accept CI/CD job tokens. Use a personal, project, or group access token instead.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA, branch, or tag that the CI/CD configuration content is taken from. Defaults to the head of the project&#39;s default branch. Deprecated. Use &#x60;content_ref&#x60; instead. Mutually exclusive with &#x60;content_ref&#x60;.
     * @param contentRef Commit SHA, branch, or tag that the CI/CD configuration content is taken from. Defaults to the head of the project&#39;s default branch. Mutually exclusive with &#x60;sha&#x60;.
     * @param dryRun If &#x60;true&#x60;, runs a [pipeline creation simulation](https://docs.gitlab.com/ci/yaml/lint/#simulate-a-pipeline). If &#x60;false&#x60;, runs only a static check.
     * @param includeJobs If &#x60;true&#x60;, includes the list of jobs that would exist in a static check or pipeline simulation.
     * @param ref If &#x60;dry_run&#x60; is &#x60;true&#x60;, sets the branch or tag to use to validate the CI/CD YAML configuration. Defaults to the project&#39;s default branch. Deprecated. Use &#x60;dry_run_ref&#x60; instead. Mutually exclusive with &#x60;dry_run_ref&#x60;.
     * @param dryRunRef If &#x60;dry_run&#x60; is &#x60;true&#x60;, sets the branch or tag to use to validate the CI/CD YAML configuration. Defaults to the project&#39;s default branch. Mutually exclusive with &#x60;ref&#x60;.
     * @return APIEntitiesCiLintResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdCiLintRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sha, @jakarta.annotation.Nullable String contentRef, @jakarta.annotation.Nullable Boolean dryRun, @jakarta.annotation.Nullable Boolean includeJobs, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String dryRunRef) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdCiLint", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sha", sha));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "content_ref", contentRef));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "dry_run", dryRun));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_jobs", includeJobs));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ref", ref));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "dry_run_ref", dryRunRef));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesCiLintResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ci/lint", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Validate existing CI/CD configuration
     * Validates the existing &#x60;.gitlab-ci.yml&#x60; configuration for a specified project. The validation runs in the context of the project, using the CI/CD variables of the project and searching the files of the project for &#x60;include:local&#x60; entries. If the &#x60;ci_lint_api_require_authentication&#x60; setting is enabled, this endpoint requires authentication, including for public projects. This endpoint does not accept CI/CD job tokens. Use a personal, project, or group access token instead.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA, branch, or tag that the CI/CD configuration content is taken from. Defaults to the head of the project&#39;s default branch. Deprecated. Use &#x60;content_ref&#x60; instead. Mutually exclusive with &#x60;content_ref&#x60;.
     * @param contentRef Commit SHA, branch, or tag that the CI/CD configuration content is taken from. Defaults to the head of the project&#39;s default branch. Mutually exclusive with &#x60;sha&#x60;.
     * @param dryRun If &#x60;true&#x60;, runs a [pipeline creation simulation](https://docs.gitlab.com/ci/yaml/lint/#simulate-a-pipeline). If &#x60;false&#x60;, runs only a static check.
     * @param includeJobs If &#x60;true&#x60;, includes the list of jobs that would exist in a static check or pipeline simulation.
     * @param ref If &#x60;dry_run&#x60; is &#x60;true&#x60;, sets the branch or tag to use to validate the CI/CD YAML configuration. Defaults to the project&#39;s default branch. Deprecated. Use &#x60;dry_run_ref&#x60; instead. Mutually exclusive with &#x60;dry_run_ref&#x60;.
     * @param dryRunRef If &#x60;dry_run&#x60; is &#x60;true&#x60;, sets the branch or tag to use to validate the CI/CD YAML configuration. Defaults to the project&#39;s default branch. Mutually exclusive with &#x60;ref&#x60;.
     * @return APIEntitiesCiLintResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiLintResult getApiV4ProjectsIdCiLint(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sha, @jakarta.annotation.Nullable String contentRef, @jakarta.annotation.Nullable Boolean dryRun, @jakarta.annotation.Nullable Boolean includeJobs, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String dryRunRef) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiLintResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdCiLintRequestCreation(id, sha, contentRef, dryRun, includeJobs, ref, dryRunRef).body(localVarReturnType);
    }

    /**
     * Validate existing CI/CD configuration
     * Validates the existing &#x60;.gitlab-ci.yml&#x60; configuration for a specified project. The validation runs in the context of the project, using the CI/CD variables of the project and searching the files of the project for &#x60;include:local&#x60; entries. If the &#x60;ci_lint_api_require_authentication&#x60; setting is enabled, this endpoint requires authentication, including for public projects. This endpoint does not accept CI/CD job tokens. Use a personal, project, or group access token instead.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA, branch, or tag that the CI/CD configuration content is taken from. Defaults to the head of the project&#39;s default branch. Deprecated. Use &#x60;content_ref&#x60; instead. Mutually exclusive with &#x60;content_ref&#x60;.
     * @param contentRef Commit SHA, branch, or tag that the CI/CD configuration content is taken from. Defaults to the head of the project&#39;s default branch. Mutually exclusive with &#x60;sha&#x60;.
     * @param dryRun If &#x60;true&#x60;, runs a [pipeline creation simulation](https://docs.gitlab.com/ci/yaml/lint/#simulate-a-pipeline). If &#x60;false&#x60;, runs only a static check.
     * @param includeJobs If &#x60;true&#x60;, includes the list of jobs that would exist in a static check or pipeline simulation.
     * @param ref If &#x60;dry_run&#x60; is &#x60;true&#x60;, sets the branch or tag to use to validate the CI/CD YAML configuration. Defaults to the project&#39;s default branch. Deprecated. Use &#x60;dry_run_ref&#x60; instead. Mutually exclusive with &#x60;dry_run_ref&#x60;.
     * @param dryRunRef If &#x60;dry_run&#x60; is &#x60;true&#x60;, sets the branch or tag to use to validate the CI/CD YAML configuration. Defaults to the project&#39;s default branch. Mutually exclusive with &#x60;ref&#x60;.
     * @return ResponseEntity&lt;APIEntitiesCiLintResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiLintResult> getApiV4ProjectsIdCiLintWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sha, @jakarta.annotation.Nullable String contentRef, @jakarta.annotation.Nullable Boolean dryRun, @jakarta.annotation.Nullable Boolean includeJobs, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String dryRunRef) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiLintResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdCiLintRequestCreation(id, sha, contentRef, dryRun, includeJobs, ref, dryRunRef).toEntity(localVarReturnType);
    }

    /**
     * Validate existing CI/CD configuration
     * Validates the existing &#x60;.gitlab-ci.yml&#x60; configuration for a specified project. The validation runs in the context of the project, using the CI/CD variables of the project and searching the files of the project for &#x60;include:local&#x60; entries. If the &#x60;ci_lint_api_require_authentication&#x60; setting is enabled, this endpoint requires authentication, including for public projects. This endpoint does not accept CI/CD job tokens. Use a personal, project, or group access token instead.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA, branch, or tag that the CI/CD configuration content is taken from. Defaults to the head of the project&#39;s default branch. Deprecated. Use &#x60;content_ref&#x60; instead. Mutually exclusive with &#x60;content_ref&#x60;.
     * @param contentRef Commit SHA, branch, or tag that the CI/CD configuration content is taken from. Defaults to the head of the project&#39;s default branch. Mutually exclusive with &#x60;sha&#x60;.
     * @param dryRun If &#x60;true&#x60;, runs a [pipeline creation simulation](https://docs.gitlab.com/ci/yaml/lint/#simulate-a-pipeline). If &#x60;false&#x60;, runs only a static check.
     * @param includeJobs If &#x60;true&#x60;, includes the list of jobs that would exist in a static check or pipeline simulation.
     * @param ref If &#x60;dry_run&#x60; is &#x60;true&#x60;, sets the branch or tag to use to validate the CI/CD YAML configuration. Defaults to the project&#39;s default branch. Deprecated. Use &#x60;dry_run_ref&#x60; instead. Mutually exclusive with &#x60;dry_run_ref&#x60;.
     * @param dryRunRef If &#x60;dry_run&#x60; is &#x60;true&#x60;, sets the branch or tag to use to validate the CI/CD YAML configuration. Defaults to the project&#39;s default branch. Mutually exclusive with &#x60;ref&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdCiLintWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sha, @jakarta.annotation.Nullable String contentRef, @jakarta.annotation.Nullable Boolean dryRun, @jakarta.annotation.Nullable Boolean includeJobs, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String dryRunRef) throws RestClientResponseException {
        return getApiV4ProjectsIdCiLintRequestCreation(id, sha, contentRef, dryRun, includeJobs, ref, dryRunRef);
    }

    /**
     * Validate a CI/CD configuration
     * Validates a provided CI/CD configuration in the context of a specified project. The validation uses the CI/CD variables of the project and searches the files of the project for &#x60;include:local&#x60; entries.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdCiLintRequest The postApiV4ProjectsIdCiLintRequest parameter
     * @return APIEntitiesCiLintResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdCiLintRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdCiLintRequest postApiV4ProjectsIdCiLintRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdCiLint", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdCiLintRequest' is set
        if (postApiV4ProjectsIdCiLintRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdCiLintRequest' when calling postApiV4ProjectsIdCiLint", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiLintResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ci/lint", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdCiLintRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Validate a CI/CD configuration
     * Validates a provided CI/CD configuration in the context of a specified project. The validation uses the CI/CD variables of the project and searches the files of the project for &#x60;include:local&#x60; entries.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdCiLintRequest The postApiV4ProjectsIdCiLintRequest parameter
     * @return APIEntitiesCiLintResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiLintResult postApiV4ProjectsIdCiLint(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdCiLintRequest postApiV4ProjectsIdCiLintRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiLintResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdCiLintRequestCreation(id, postApiV4ProjectsIdCiLintRequest).body(localVarReturnType);
    }

    /**
     * Validate a CI/CD configuration
     * Validates a provided CI/CD configuration in the context of a specified project. The validation uses the CI/CD variables of the project and searches the files of the project for &#x60;include:local&#x60; entries.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdCiLintRequest The postApiV4ProjectsIdCiLintRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiLintResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiLintResult> postApiV4ProjectsIdCiLintWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdCiLintRequest postApiV4ProjectsIdCiLintRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiLintResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdCiLintRequestCreation(id, postApiV4ProjectsIdCiLintRequest).toEntity(localVarReturnType);
    }

    /**
     * Validate a CI/CD configuration
     * Validates a provided CI/CD configuration in the context of a specified project. The validation uses the CI/CD variables of the project and searches the files of the project for &#x60;include:local&#x60; entries.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdCiLintRequest The postApiV4ProjectsIdCiLintRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdCiLintWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdCiLintRequest postApiV4ProjectsIdCiLintRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdCiLintRequestCreation(id, postApiV4ProjectsIdCiLintRequest);
    }
}
