package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBasicRef;
import ai.gebo.gitlab.client.model.APIEntitiesCommit;
import ai.gebo.gitlab.client.model.APIEntitiesCommitDetail;
import ai.gebo.gitlab.client.model.APIEntitiesCommitNote;
import ai.gebo.gitlab.client.model.APIEntitiesCommitSequence;
import ai.gebo.gitlab.client.model.APIEntitiesCommitSignature;
import ai.gebo.gitlab.client.model.APIEntitiesDiff;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestBasic;
import java.io.File;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdRepositoryCommitsShaCommentsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdRepositoryCommitsShaRevertRequest;

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
public class CommitsApi {
    private ApiClient apiClient;

    public CommitsApi() {
        this(new ApiClient());
    }

    public CommitsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all repository commits
     * Lists all commits for a specified project repository.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param refName Name of a repository branch, tag, or revision range. Uses the default branch if not given.
     * @param since Only commits after or on this date are returned.
     * @param until Only commits before or on this date are returned.
     * @param path File path relative to the repository.
     * @param follow If &#x60;true&#x60;, follows file renames when filtering commits by &#x60;path&#x60; and returns commits for the file even if it was renamed. If &#x60;false&#x60;, returns only commits where the file existed at its current path. Used only when &#x60;path&#x60; specifies a single file. Default is &#x60;true&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/225733) in GitLab 18.10.
     * @param author Filter commits by commit author.
     * @param all If &#x60;true&#x60;, retrieves every commit from the repository and ignores the &#x60;ref_name&#x60; parameter.
     * @param withStats If &#x60;true&#x60;, includes statistics for each commit in the response.
     * @param firstParent If &#x60;true&#x60;, follows only the first parent commit upon seeing a merge commit.
     * @param order Order in which to list commits, either reverse chronological or [topological](https://git-scm.com/docs/git-log#Documentation/git-log.txt---topo-order).
     * @param trailers If &#x60;true&#x60;, parses and includes [Git trailers](https://git-scm.com/docs/git-interpret-trailers) for every commit.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param pagination Specify the pagination method.
     * @param pageToken Record from which to start the keyset pagination.
     * @return List&lt;APIEntitiesCommit&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryCommitsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String refName, @jakarta.annotation.Nullable OffsetDateTime since, @jakarta.annotation.Nullable OffsetDateTime until, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean follow, @jakarta.annotation.Nullable String author, @jakarta.annotation.Nullable Boolean all, @jakarta.annotation.Nullable Boolean withStats, @jakarta.annotation.Nullable Boolean firstParent, @jakarta.annotation.Nullable String order, @jakarta.annotation.Nullable Boolean trailers, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String pagination, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ref_name", refName));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "since", since));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "until", until));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "path", path));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "follow", follow));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author", author));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "all", all));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_stats", withStats));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "first_parent", firstParent));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order", order));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "trailers", trailers));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pagination", pagination));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page_token", pageToken));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCommit>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all repository commits
     * Lists all commits for a specified project repository.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param refName Name of a repository branch, tag, or revision range. Uses the default branch if not given.
     * @param since Only commits after or on this date are returned.
     * @param until Only commits before or on this date are returned.
     * @param path File path relative to the repository.
     * @param follow If &#x60;true&#x60;, follows file renames when filtering commits by &#x60;path&#x60; and returns commits for the file even if it was renamed. If &#x60;false&#x60;, returns only commits where the file existed at its current path. Used only when &#x60;path&#x60; specifies a single file. Default is &#x60;true&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/225733) in GitLab 18.10.
     * @param author Filter commits by commit author.
     * @param all If &#x60;true&#x60;, retrieves every commit from the repository and ignores the &#x60;ref_name&#x60; parameter.
     * @param withStats If &#x60;true&#x60;, includes statistics for each commit in the response.
     * @param firstParent If &#x60;true&#x60;, follows only the first parent commit upon seeing a merge commit.
     * @param order Order in which to list commits, either reverse chronological or [topological](https://git-scm.com/docs/git-log#Documentation/git-log.txt---topo-order).
     * @param trailers If &#x60;true&#x60;, parses and includes [Git trailers](https://git-scm.com/docs/git-interpret-trailers) for every commit.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param pagination Specify the pagination method.
     * @param pageToken Record from which to start the keyset pagination.
     * @return List&lt;APIEntitiesCommit&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCommit> getApiV4ProjectsIdRepositoryCommits(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String refName, @jakarta.annotation.Nullable OffsetDateTime since, @jakarta.annotation.Nullable OffsetDateTime until, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean follow, @jakarta.annotation.Nullable String author, @jakarta.annotation.Nullable Boolean all, @jakarta.annotation.Nullable Boolean withStats, @jakarta.annotation.Nullable Boolean firstParent, @jakarta.annotation.Nullable String order, @jakarta.annotation.Nullable Boolean trailers, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String pagination, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCommit>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsRequestCreation(id, refName, since, until, path, follow, author, all, withStats, firstParent, order, trailers, page, perPage, pagination, pageToken).body(localVarReturnType);
    }

    /**
     * List all repository commits
     * Lists all commits for a specified project repository.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param refName Name of a repository branch, tag, or revision range. Uses the default branch if not given.
     * @param since Only commits after or on this date are returned.
     * @param until Only commits before or on this date are returned.
     * @param path File path relative to the repository.
     * @param follow If &#x60;true&#x60;, follows file renames when filtering commits by &#x60;path&#x60; and returns commits for the file even if it was renamed. If &#x60;false&#x60;, returns only commits where the file existed at its current path. Used only when &#x60;path&#x60; specifies a single file. Default is &#x60;true&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/225733) in GitLab 18.10.
     * @param author Filter commits by commit author.
     * @param all If &#x60;true&#x60;, retrieves every commit from the repository and ignores the &#x60;ref_name&#x60; parameter.
     * @param withStats If &#x60;true&#x60;, includes statistics for each commit in the response.
     * @param firstParent If &#x60;true&#x60;, follows only the first parent commit upon seeing a merge commit.
     * @param order Order in which to list commits, either reverse chronological or [topological](https://git-scm.com/docs/git-log#Documentation/git-log.txt---topo-order).
     * @param trailers If &#x60;true&#x60;, parses and includes [Git trailers](https://git-scm.com/docs/git-interpret-trailers) for every commit.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param pagination Specify the pagination method.
     * @param pageToken Record from which to start the keyset pagination.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCommit&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCommit>> getApiV4ProjectsIdRepositoryCommitsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String refName, @jakarta.annotation.Nullable OffsetDateTime since, @jakarta.annotation.Nullable OffsetDateTime until, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean follow, @jakarta.annotation.Nullable String author, @jakarta.annotation.Nullable Boolean all, @jakarta.annotation.Nullable Boolean withStats, @jakarta.annotation.Nullable Boolean firstParent, @jakarta.annotation.Nullable String order, @jakarta.annotation.Nullable Boolean trailers, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String pagination, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCommit>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsRequestCreation(id, refName, since, until, path, follow, author, all, withStats, firstParent, order, trailers, page, perPage, pagination, pageToken).toEntity(localVarReturnType);
    }

    /**
     * List all repository commits
     * Lists all commits for a specified project repository.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param refName Name of a repository branch, tag, or revision range. Uses the default branch if not given.
     * @param since Only commits after or on this date are returned.
     * @param until Only commits before or on this date are returned.
     * @param path File path relative to the repository.
     * @param follow If &#x60;true&#x60;, follows file renames when filtering commits by &#x60;path&#x60; and returns commits for the file even if it was renamed. If &#x60;false&#x60;, returns only commits where the file existed at its current path. Used only when &#x60;path&#x60; specifies a single file. Default is &#x60;true&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/225733) in GitLab 18.10.
     * @param author Filter commits by commit author.
     * @param all If &#x60;true&#x60;, retrieves every commit from the repository and ignores the &#x60;ref_name&#x60; parameter.
     * @param withStats If &#x60;true&#x60;, includes statistics for each commit in the response.
     * @param firstParent If &#x60;true&#x60;, follows only the first parent commit upon seeing a merge commit.
     * @param order Order in which to list commits, either reverse chronological or [topological](https://git-scm.com/docs/git-log#Documentation/git-log.txt---topo-order).
     * @param trailers If &#x60;true&#x60;, parses and includes [Git trailers](https://git-scm.com/docs/git-interpret-trailers) for every commit.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param pagination Specify the pagination method.
     * @param pageToken Record from which to start the keyset pagination.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryCommitsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String refName, @jakarta.annotation.Nullable OffsetDateTime since, @jakarta.annotation.Nullable OffsetDateTime until, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean follow, @jakarta.annotation.Nullable String author, @jakarta.annotation.Nullable Boolean all, @jakarta.annotation.Nullable Boolean withStats, @jakarta.annotation.Nullable Boolean firstParent, @jakarta.annotation.Nullable String order, @jakarta.annotation.Nullable Boolean trailers, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String pagination, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryCommitsRequestCreation(id, refName, since, until, path, follow, author, all, withStats, firstParent, order, trailers, page, perPage, pagination, pageToken);
    }

    /**
     * Retrieve a commit
     * Retrieves a specified commit identified by the commit hash or name of a branch or tag.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param stats If &#x60;true&#x60;, includes commit statistics in the response.
     * @return APIEntitiesCommitDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Boolean stats) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryCommitsSha", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha' is set
        if (sha == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha' when calling getApiV4ProjectsIdRepositoryCommitsSha", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("sha", sha);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "stats", stats));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesCommitDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{sha}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a commit
     * Retrieves a specified commit identified by the commit hash or name of a branch or tag.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param stats If &#x60;true&#x60;, includes commit statistics in the response.
     * @return APIEntitiesCommitDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCommitDetail getApiV4ProjectsIdRepositoryCommitsSha(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Boolean stats) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommitDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaRequestCreation(id, sha, stats).body(localVarReturnType);
    }

    /**
     * Retrieve a commit
     * Retrieves a specified commit identified by the commit hash or name of a branch or tag.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param stats If &#x60;true&#x60;, includes commit statistics in the response.
     * @return ResponseEntity&lt;APIEntitiesCommitDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCommitDetail> getApiV4ProjectsIdRepositoryCommitsShaWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Boolean stats) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommitDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaRequestCreation(id, sha, stats).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a commit
     * Retrieves a specified commit identified by the commit hash or name of a branch or tag.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param stats If &#x60;true&#x60;, includes commit statistics in the response.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Boolean stats) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryCommitsShaRequestCreation(id, sha, stats);
    }

    /**
     * List all commit comments
     * Lists all the comments of a commit in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCommitNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaCommentsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryCommitsShaComments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha' is set
        if (sha == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha' when calling getApiV4ProjectsIdRepositoryCommitsShaComments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("sha", sha);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCommitNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{sha}/comments", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all commit comments
     * Lists all the comments of a commit in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCommitNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCommitNote> getApiV4ProjectsIdRepositoryCommitsShaComments(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCommitNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaCommentsRequestCreation(id, sha, page, perPage).body(localVarReturnType);
    }

    /**
     * List all commit comments
     * Lists all the comments of a commit in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCommitNote&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCommitNote>> getApiV4ProjectsIdRepositoryCommitsShaCommentsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCommitNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaCommentsRequestCreation(id, sha, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all commit comments
     * Lists all the comments of a commit in a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaCommentsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryCommitsShaCommentsRequestCreation(id, sha, page, perPage);
    }

    /**
     * Retrieve a commit diff
     * Retrieves the diff of a commit in a project. Diff limits apply. When a commit exceeds the maximum number of files, pagination stops and no files beyond the limit are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return List&lt;APIEntitiesDiff&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaDiffRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryCommitsShaDiff", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha' is set
        if (sha == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha' when calling getApiV4ProjectsIdRepositoryCommitsShaDiff", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("sha", sha);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "unidiff", unidiff));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesDiff>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{sha}/diff", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a commit diff
     * Retrieves the diff of a commit in a project. Diff limits apply. When a commit exceeds the maximum number of files, pagination stops and no files beyond the limit are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return List&lt;APIEntitiesDiff&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDiff> getApiV4ProjectsIdRepositoryCommitsShaDiff(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiff>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaDiffRequestCreation(id, sha, page, perPage, unidiff).body(localVarReturnType);
    }

    /**
     * Retrieve a commit diff
     * Retrieves the diff of a commit in a project. Diff limits apply. When a commit exceeds the maximum number of files, pagination stops and no files beyond the limit are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDiff&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDiff>> getApiV4ProjectsIdRepositoryCommitsShaDiffWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiff>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaDiffRequestCreation(id, sha, page, perPage, unidiff).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a commit diff
     * Retrieves the diff of a commit in a project. Diff limits apply. When a commit exceeds the maximum number of files, pagination stops and no files beyond the limit are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaDiffWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryCommitsShaDiffRequestCreation(id, sha, page, perPage, unidiff);
    }

    /**
     * List all merge requests associated with a commit
     * Lists all merge requests associated with a specified commit.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param state Filter merge requests by state: &#x60;opened&#x60;, &#x60;closed&#x60;, &#x60;locked&#x60;, or &#x60;merged&#x60;. If omitted, merge requests of all states are returned. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/191169) in GitLab 18.2.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaMergeRequestsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryCommitsShaMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha' is set
        if (sha == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha' when calling getApiV4ProjectsIdRepositoryCommitsShaMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("sha", sha);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{sha}/merge_requests", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all merge requests associated with a commit
     * Lists all merge requests associated with a specified commit.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param state Filter merge requests by state: &#x60;opened&#x60;, &#x60;closed&#x60;, &#x60;locked&#x60;, or &#x60;merged&#x60;. If omitted, merge requests of all states are returned. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/191169) in GitLab 18.2.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeRequestBasic> getApiV4ProjectsIdRepositoryCommitsShaMergeRequests(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaMergeRequestsRequestCreation(id, sha, state, page, perPage).body(localVarReturnType);
    }

    /**
     * List all merge requests associated with a commit
     * Lists all merge requests associated with a specified commit.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param state Filter merge requests by state: &#x60;opened&#x60;, &#x60;closed&#x60;, &#x60;locked&#x60;, or &#x60;merged&#x60;. If omitted, merge requests of all states are returned. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/191169) in GitLab 18.2.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeRequestBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeRequestBasic>> getApiV4ProjectsIdRepositoryCommitsShaMergeRequestsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaMergeRequestsRequestCreation(id, sha, state, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all merge requests associated with a commit
     * Lists all merge requests associated with a specified commit.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param state Filter merge requests by state: &#x60;opened&#x60;, &#x60;closed&#x60;, &#x60;locked&#x60;, or &#x60;merged&#x60;. If omitted, merge requests of all states are returned. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/191169) in GitLab 18.2.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaMergeRequestsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryCommitsShaMergeRequestsRequestCreation(id, sha, state, page, perPage);
    }

    /**
     * List all references a commit is pushed to
     * Lists all references (from branches or tags) a commit is pushed to. The pagination parameters &#x60;page&#x60; and &#x60;per_page&#x60; can be used to restrict the list of references.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param type Type of references to return for the commit.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBasicRef&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaRefsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryCommitsShaRefs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha' is set
        if (sha == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha' when calling getApiV4ProjectsIdRepositoryCommitsShaRefs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("sha", sha);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "type", type));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesBasicRef>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{sha}/refs", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all references a commit is pushed to
     * Lists all references (from branches or tags) a commit is pushed to. The pagination parameters &#x60;page&#x60; and &#x60;per_page&#x60; can be used to restrict the list of references.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param type Type of references to return for the commit.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBasicRef&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBasicRef> getApiV4ProjectsIdRepositoryCommitsShaRefs(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBasicRef>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaRefsRequestCreation(id, sha, type, page, perPage).body(localVarReturnType);
    }

    /**
     * List all references a commit is pushed to
     * Lists all references (from branches or tags) a commit is pushed to. The pagination parameters &#x60;page&#x60; and &#x60;per_page&#x60; can be used to restrict the list of references.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param type Type of references to return for the commit.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBasicRef&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBasicRef>> getApiV4ProjectsIdRepositoryCommitsShaRefsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBasicRef>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaRefsRequestCreation(id, sha, type, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all references a commit is pushed to
     * Lists all references (from branches or tags) a commit is pushed to. The pagination parameters &#x60;page&#x60; and &#x60;per_page&#x60; can be used to restrict the list of references.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param type Type of references to return for the commit.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaRefsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryCommitsShaRefsRequestCreation(id, sha, type, page, perPage);
    }

    /**
     * Retrieve a commit sequence
     * Retrieves the sequence number of a specified commit, found by following the parent links from the commit. The result is the same as &#x60;git rev-list --count&#x60; for the commit SHA.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param firstParent If &#x60;true&#x60;, follows only the first parent commit upon seeing a merge commit.
     * @return APIEntitiesCommitSequence
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaSequenceRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Boolean firstParent) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryCommitsShaSequence", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha' is set
        if (sha == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha' when calling getApiV4ProjectsIdRepositoryCommitsShaSequence", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("sha", sha);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "first_parent", firstParent));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesCommitSequence> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{sha}/sequence", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a commit sequence
     * Retrieves the sequence number of a specified commit, found by following the parent links from the commit. The result is the same as &#x60;git rev-list --count&#x60; for the commit SHA.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param firstParent If &#x60;true&#x60;, follows only the first parent commit upon seeing a merge commit.
     * @return APIEntitiesCommitSequence
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCommitSequence getApiV4ProjectsIdRepositoryCommitsShaSequence(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Boolean firstParent) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommitSequence> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaSequenceRequestCreation(id, sha, firstParent).body(localVarReturnType);
    }

    /**
     * Retrieve a commit sequence
     * Retrieves the sequence number of a specified commit, found by following the parent links from the commit. The result is the same as &#x60;git rev-list --count&#x60; for the commit SHA.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param firstParent If &#x60;true&#x60;, follows only the first parent commit upon seeing a merge commit.
     * @return ResponseEntity&lt;APIEntitiesCommitSequence&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCommitSequence> getApiV4ProjectsIdRepositoryCommitsShaSequenceWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Boolean firstParent) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommitSequence> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaSequenceRequestCreation(id, sha, firstParent).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a commit sequence
     * Retrieves the sequence number of a specified commit, found by following the parent links from the commit. The result is the same as &#x60;git rev-list --count&#x60; for the commit SHA.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA.
     * @param firstParent If &#x60;true&#x60;, follows only the first parent commit upon seeing a merge commit.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaSequenceWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nullable Boolean firstParent) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryCommitsShaSequenceRequestCreation(id, sha, firstParent);
    }

    /**
     * Retrieve a commit signature
     * Retrieves the signature from a commit, if it is signed. For unsigned commits, it results in a 404 response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @return APIEntitiesCommitSignature
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaSignatureRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryCommitsShaSignature", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha' is set
        if (sha == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha' when calling getApiV4ProjectsIdRepositoryCommitsShaSignature", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesCommitSignature> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{sha}/signature", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a commit signature
     * Retrieves the signature from a commit, if it is signed. For unsigned commits, it results in a 404 response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @return APIEntitiesCommitSignature
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCommitSignature getApiV4ProjectsIdRepositoryCommitsShaSignature(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommitSignature> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaSignatureRequestCreation(id, sha).body(localVarReturnType);
    }

    /**
     * Retrieve a commit signature
     * Retrieves the signature from a commit, if it is signed. For unsigned commits, it results in a 404 response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @return ResponseEntity&lt;APIEntitiesCommitSignature&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCommitSignature> getApiV4ProjectsIdRepositoryCommitsShaSignatureWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommitSignature> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsShaSignatureRequestCreation(id, sha).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a commit signature
     * Retrieves the signature from a commit, if it is signed. For unsigned commits, it results in a 404 response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryCommitsShaSignatureWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryCommitsShaSignatureRequestCreation(id, sha);
    }

    /**
     * Create a commit
     * Creates a commit in the specified project from a JSON payload. The request body can also be form-encoded, with each action sent as &#x60;actions[][&lt;attribute&gt;]&#x60; fields. By default, requests larger than 300 MB are rejected. Requests larger than 20 MB are rate-limited to 3 requests every 30 seconds for each user. This feature was introduced in GitLab 8.13
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param _file The commit content to be created (generated by Multipart middleware)
     * @return APIEntitiesCommitDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRepositoryCommitsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRepositoryCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4ProjectsIdRepositoryCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesCommitDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a commit
     * Creates a commit in the specified project from a JSON payload. The request body can also be form-encoded, with each action sent as &#x60;actions[][&lt;attribute&gt;]&#x60; fields. By default, requests larger than 300 MB are rejected. Requests larger than 20 MB are rate-limited to 3 requests every 30 seconds for each user. This feature was introduced in GitLab 8.13
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param _file The commit content to be created (generated by Multipart middleware)
     * @return APIEntitiesCommitDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCommitDetail postApiV4ProjectsIdRepositoryCommits(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommitDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryCommitsRequestCreation(id, _file).body(localVarReturnType);
    }

    /**
     * Create a commit
     * Creates a commit in the specified project from a JSON payload. The request body can also be form-encoded, with each action sent as &#x60;actions[][&lt;attribute&gt;]&#x60; fields. By default, requests larger than 300 MB are rejected. Requests larger than 20 MB are rate-limited to 3 requests every 30 seconds for each user. This feature was introduced in GitLab 8.13
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param _file The commit content to be created (generated by Multipart middleware)
     * @return ResponseEntity&lt;APIEntitiesCommitDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCommitDetail> postApiV4ProjectsIdRepositoryCommitsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommitDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryCommitsRequestCreation(id, _file).toEntity(localVarReturnType);
    }

    /**
     * Create a commit
     * Creates a commit in the specified project from a JSON payload. The request body can also be form-encoded, with each action sent as &#x60;actions[][&lt;attribute&gt;]&#x60; fields. By default, requests larger than 300 MB are rejected. Requests larger than 20 MB are rate-limited to 3 requests every 30 seconds for each user. This feature was introduced in GitLab 8.13
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param _file The commit content to be created (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRepositoryCommitsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return postApiV4ProjectsIdRepositoryCommitsRequestCreation(id, _file);
    }

    /**
     * Cherry-pick a commit
     * Cherry-picks a commit to a specified branch. If the cherry-pick fails, the &#x60;400&#x60; response contains a &#x60;message&#x60; and an &#x60;error_code&#x60;. The &#x60;error_code&#x60; is &#x60;empty&#x60; when the changeset is empty, which usually means the commit already exists in the target branch, or &#x60;conflict&#x60; when there is a merge conflict. If &#x60;dry_run&#x60; is &#x60;true&#x60;, a successful check returns &#x60;{\&quot;dry_run\&quot;: \&quot;success\&quot;}&#x60;, and a failed check returns the same error response with &#x60;\&quot;dry_run\&quot;: \&quot;error\&quot;&#x60; added.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA, or name of a repository branch or tag, to cherry-pick.
     * @param postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest The postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest parameter
     * @return APIEntitiesCommit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRepositoryCommitsShaCherryPick", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha' is set
        if (sha == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha' when calling postApiV4ProjectsIdRepositoryCommitsShaCherryPick", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest' is set
        if (postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest' when calling postApiV4ProjectsIdRepositoryCommitsShaCherryPick", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCommit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{sha}/cherry_pick", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Cherry-pick a commit
     * Cherry-picks a commit to a specified branch. If the cherry-pick fails, the &#x60;400&#x60; response contains a &#x60;message&#x60; and an &#x60;error_code&#x60;. The &#x60;error_code&#x60; is &#x60;empty&#x60; when the changeset is empty, which usually means the commit already exists in the target branch, or &#x60;conflict&#x60; when there is a merge conflict. If &#x60;dry_run&#x60; is &#x60;true&#x60;, a successful check returns &#x60;{\&quot;dry_run\&quot;: \&quot;success\&quot;}&#x60;, and a failed check returns the same error response with &#x60;\&quot;dry_run\&quot;: \&quot;error\&quot;&#x60; added.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA, or name of a repository branch or tag, to cherry-pick.
     * @param postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest The postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest parameter
     * @return APIEntitiesCommit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCommit postApiV4ProjectsIdRepositoryCommitsShaCherryPick(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequestCreation(id, sha, postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest).body(localVarReturnType);
    }

    /**
     * Cherry-pick a commit
     * Cherry-picks a commit to a specified branch. If the cherry-pick fails, the &#x60;400&#x60; response contains a &#x60;message&#x60; and an &#x60;error_code&#x60;. The &#x60;error_code&#x60; is &#x60;empty&#x60; when the changeset is empty, which usually means the commit already exists in the target branch, or &#x60;conflict&#x60; when there is a merge conflict. If &#x60;dry_run&#x60; is &#x60;true&#x60;, a successful check returns &#x60;{\&quot;dry_run\&quot;: \&quot;success\&quot;}&#x60;, and a failed check returns the same error response with &#x60;\&quot;dry_run\&quot;: \&quot;error\&quot;&#x60; added.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA, or name of a repository branch or tag, to cherry-pick.
     * @param postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest The postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCommit&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCommit> postApiV4ProjectsIdRepositoryCommitsShaCherryPickWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequestCreation(id, sha, postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest).toEntity(localVarReturnType);
    }

    /**
     * Cherry-pick a commit
     * Cherry-picks a commit to a specified branch. If the cherry-pick fails, the &#x60;400&#x60; response contains a &#x60;message&#x60; and an &#x60;error_code&#x60;. The &#x60;error_code&#x60; is &#x60;empty&#x60; when the changeset is empty, which usually means the commit already exists in the target branch, or &#x60;conflict&#x60; when there is a merge conflict. If &#x60;dry_run&#x60; is &#x60;true&#x60;, a successful check returns &#x60;{\&quot;dry_run\&quot;: \&quot;success\&quot;}&#x60;, and a failed check returns the same error response with &#x60;\&quot;dry_run\&quot;: \&quot;error\&quot;&#x60; added.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA, or name of a repository branch or tag, to cherry-pick.
     * @param postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest The postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRepositoryCommitsShaCherryPickWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequestCreation(id, sha, postApiV4ProjectsIdRepositoryCommitsShaCherryPickRequest);
    }

    /**
     * Create a comment on a commit
     * Creates a comment on a commit. To comment on a specific line in a file, specify the full commit SHA, &#x60;path&#x60;, &#x60;line&#x60;, and set &#x60;line_type&#x60; to &#x60;new&#x60;. If &#x60;path&#x60; or &#x60;line&#x60; does not exist in the commit diff, the comment is added to the commit without a line position, and &#x60;line&#x60;, &#x60;line_type&#x60;, and &#x60;path&#x60; are &#x60;null&#x60; in the response. GitLab might change the comment body before saving it, for example by running and removing quick actions.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest The postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest parameter
     * @return APIEntitiesCommitNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRepositoryCommitsShaCommentsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsShaCommentsRequest postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRepositoryCommitsShaComments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha' is set
        if (sha == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha' when calling postApiV4ProjectsIdRepositoryCommitsShaComments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest' is set
        if (postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest' when calling postApiV4ProjectsIdRepositoryCommitsShaComments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCommitNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{sha}/comments", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a comment on a commit
     * Creates a comment on a commit. To comment on a specific line in a file, specify the full commit SHA, &#x60;path&#x60;, &#x60;line&#x60;, and set &#x60;line_type&#x60; to &#x60;new&#x60;. If &#x60;path&#x60; or &#x60;line&#x60; does not exist in the commit diff, the comment is added to the commit without a line position, and &#x60;line&#x60;, &#x60;line_type&#x60;, and &#x60;path&#x60; are &#x60;null&#x60; in the response. GitLab might change the comment body before saving it, for example by running and removing quick actions.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest The postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest parameter
     * @return APIEntitiesCommitNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCommitNote postApiV4ProjectsIdRepositoryCommitsShaComments(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsShaCommentsRequest postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommitNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryCommitsShaCommentsRequestCreation(id, sha, postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest).body(localVarReturnType);
    }

    /**
     * Create a comment on a commit
     * Creates a comment on a commit. To comment on a specific line in a file, specify the full commit SHA, &#x60;path&#x60;, &#x60;line&#x60;, and set &#x60;line_type&#x60; to &#x60;new&#x60;. If &#x60;path&#x60; or &#x60;line&#x60; does not exist in the commit diff, the comment is added to the commit without a line position, and &#x60;line&#x60;, &#x60;line_type&#x60;, and &#x60;path&#x60; are &#x60;null&#x60; in the response. GitLab might change the comment body before saving it, for example by running and removing quick actions.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest The postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCommitNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCommitNote> postApiV4ProjectsIdRepositoryCommitsShaCommentsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsShaCommentsRequest postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommitNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryCommitsShaCommentsRequestCreation(id, sha, postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a comment on a commit
     * Creates a comment on a commit. To comment on a specific line in a file, specify the full commit SHA, &#x60;path&#x60;, &#x60;line&#x60;, and set &#x60;line_type&#x60; to &#x60;new&#x60;. If &#x60;path&#x60; or &#x60;line&#x60; does not exist in the commit diff, the comment is added to the commit without a line position, and &#x60;line&#x60;, &#x60;line_type&#x60;, and &#x60;path&#x60; are &#x60;null&#x60; in the response. GitLab might change the comment body before saving it, for example by running and removing quick actions.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA or name of a repository branch or tag.
     * @param postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest The postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRepositoryCommitsShaCommentsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsShaCommentsRequest postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdRepositoryCommitsShaCommentsRequestCreation(id, sha, postApiV4ProjectsIdRepositoryCommitsShaCommentsRequest);
    }

    /**
     * Revert a commit
     * Reverts a commit in a specified branch. If the revert fails, the &#x60;400&#x60; response contains a &#x60;message&#x60; and an &#x60;error_code&#x60;. The &#x60;error_code&#x60; is &#x60;conflict&#x60; when the revert causes a merge conflict, or &#x60;empty&#x60; when the revert changes nothing on the branch, for example because the changes were already reverted or were never on the branch. If &#x60;dry_run&#x60; is &#x60;true&#x60;, a successful check returns &#x60;{\&quot;dry_run\&quot;: \&quot;success\&quot;}&#x60;, and a failed check returns the same error response with &#x60;\&quot;dry_run\&quot;: \&quot;error\&quot;&#x60; added.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA to revert.
     * @param postApiV4ProjectsIdRepositoryCommitsShaRevertRequest The postApiV4ProjectsIdRepositoryCommitsShaRevertRequest parameter
     * @return APIEntitiesCommit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRepositoryCommitsShaRevertRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsShaRevertRequest postApiV4ProjectsIdRepositoryCommitsShaRevertRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRepositoryCommitsShaRevert", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha' is set
        if (sha == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha' when calling postApiV4ProjectsIdRepositoryCommitsShaRevert", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdRepositoryCommitsShaRevertRequest' is set
        if (postApiV4ProjectsIdRepositoryCommitsShaRevertRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdRepositoryCommitsShaRevertRequest' when calling postApiV4ProjectsIdRepositoryCommitsShaRevert", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCommit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{sha}/revert", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdRepositoryCommitsShaRevertRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revert a commit
     * Reverts a commit in a specified branch. If the revert fails, the &#x60;400&#x60; response contains a &#x60;message&#x60; and an &#x60;error_code&#x60;. The &#x60;error_code&#x60; is &#x60;conflict&#x60; when the revert causes a merge conflict, or &#x60;empty&#x60; when the revert changes nothing on the branch, for example because the changes were already reverted or were never on the branch. If &#x60;dry_run&#x60; is &#x60;true&#x60;, a successful check returns &#x60;{\&quot;dry_run\&quot;: \&quot;success\&quot;}&#x60;, and a failed check returns the same error response with &#x60;\&quot;dry_run\&quot;: \&quot;error\&quot;&#x60; added.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA to revert.
     * @param postApiV4ProjectsIdRepositoryCommitsShaRevertRequest The postApiV4ProjectsIdRepositoryCommitsShaRevertRequest parameter
     * @return APIEntitiesCommit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCommit postApiV4ProjectsIdRepositoryCommitsShaRevert(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsShaRevertRequest postApiV4ProjectsIdRepositoryCommitsShaRevertRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryCommitsShaRevertRequestCreation(id, sha, postApiV4ProjectsIdRepositoryCommitsShaRevertRequest).body(localVarReturnType);
    }

    /**
     * Revert a commit
     * Reverts a commit in a specified branch. If the revert fails, the &#x60;400&#x60; response contains a &#x60;message&#x60; and an &#x60;error_code&#x60;. The &#x60;error_code&#x60; is &#x60;conflict&#x60; when the revert causes a merge conflict, or &#x60;empty&#x60; when the revert changes nothing on the branch, for example because the changes were already reverted or were never on the branch. If &#x60;dry_run&#x60; is &#x60;true&#x60;, a successful check returns &#x60;{\&quot;dry_run\&quot;: \&quot;success\&quot;}&#x60;, and a failed check returns the same error response with &#x60;\&quot;dry_run\&quot;: \&quot;error\&quot;&#x60; added.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA to revert.
     * @param postApiV4ProjectsIdRepositoryCommitsShaRevertRequest The postApiV4ProjectsIdRepositoryCommitsShaRevertRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCommit&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCommit> postApiV4ProjectsIdRepositoryCommitsShaRevertWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsShaRevertRequest postApiV4ProjectsIdRepositoryCommitsShaRevertRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryCommitsShaRevertRequestCreation(id, sha, postApiV4ProjectsIdRepositoryCommitsShaRevertRequest).toEntity(localVarReturnType);
    }

    /**
     * Revert a commit
     * Reverts a commit in a specified branch. If the revert fails, the &#x60;400&#x60; response contains a &#x60;message&#x60; and an &#x60;error_code&#x60;. The &#x60;error_code&#x60; is &#x60;conflict&#x60; when the revert causes a merge conflict, or &#x60;empty&#x60; when the revert changes nothing on the branch, for example because the changes were already reverted or were never on the branch. If &#x60;dry_run&#x60; is &#x60;true&#x60;, a successful check returns &#x60;{\&quot;dry_run\&quot;: \&quot;success\&quot;}&#x60;, and a failed check returns the same error response with &#x60;\&quot;dry_run\&quot;: \&quot;error\&quot;&#x60; added.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA to revert.
     * @param postApiV4ProjectsIdRepositoryCommitsShaRevertRequest The postApiV4ProjectsIdRepositoryCommitsShaRevertRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRepositoryCommitsShaRevertWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsShaRevertRequest postApiV4ProjectsIdRepositoryCommitsShaRevertRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdRepositoryCommitsShaRevertRequestCreation(id, sha, postApiV4ProjectsIdRepositoryCommitsShaRevertRequest);
    }
}
