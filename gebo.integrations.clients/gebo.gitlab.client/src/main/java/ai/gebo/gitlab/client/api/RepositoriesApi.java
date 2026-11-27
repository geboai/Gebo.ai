package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBatchBlob;
import ai.gebo.gitlab.client.model.APIEntitiesChangedPath;
import ai.gebo.gitlab.client.model.APIEntitiesChangelog;
import ai.gebo.gitlab.client.model.APIEntitiesCommit;
import ai.gebo.gitlab.client.model.APIEntitiesCompare;
import ai.gebo.gitlab.client.model.APIEntitiesContributor;
import ai.gebo.gitlab.client.model.APIEntitiesDiffStat;
import ai.gebo.gitlab.client.model.APIEntitiesDivergingCommitCount;
import ai.gebo.gitlab.client.model.APIEntitiesRepositoryHealth;
import ai.gebo.gitlab.client.model.APIEntitiesTreeObject;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdRepositoryBlobsBatchRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdRepositoryChangelogRequest;

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
public class RepositoriesApi {
    private ApiClient apiClient;

    public RepositoriesApi() {
        this(new ApiClient());
    }

    public RepositoriesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve file archive from a repository
     * Retrieves the file archive of a specified repository. This endpoint can be accessed without authentication if the repository is publicly accessible. For GitLab.com users, this endpoint has a rate limit threshold of 5 requests per minute.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA to download. Accepts a tag, branch reference, or SHA. If omitted, defaults to the tip of the default branch.
     * @param refType Type of ref in &#x60;sha&#x60;. Use to select the correct ref when a branch and tag share a name.
     * @param format Archive format, added as a suffix to the path, for example &#x60;archive.zip&#x60;. One of &#x60;bz2&#x60;, &#x60;gz&#x60;, &#x60;tar&#x60;, &#x60;tar.bz2&#x60;, &#x60;tar.gz&#x60;, &#x60;tb2&#x60;, &#x60;tbz&#x60;, &#x60;tbz2&#x60;, &#x60;tgz&#x60;, or &#x60;zip&#x60;. Default is &#x60;tar.gz&#x60;.
     * @param path Subpath of the repository to download. An empty string returns the whole repository.
     * @param includeLfsBlobs If &#x60;true&#x60;, LFS objects are included in the archive. When set to &#x60;false&#x60;, LFS objects are excluded.
     * @param excludePaths Comma-separated list of paths to exclude from the archive.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryArchiveRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sha, @jakarta.annotation.Nullable String refType, @jakarta.annotation.Nullable String format, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean includeLfsBlobs, @jakarta.annotation.Nullable List<String> excludePaths) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryArchive", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sha", sha));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ref_type", refType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "format", format));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "path", path));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_lfs_blobs", includeLfsBlobs));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "exclude_paths", excludePaths));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/archive", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve file archive from a repository
     * Retrieves the file archive of a specified repository. This endpoint can be accessed without authentication if the repository is publicly accessible. For GitLab.com users, this endpoint has a rate limit threshold of 5 requests per minute.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA to download. Accepts a tag, branch reference, or SHA. If omitted, defaults to the tip of the default branch.
     * @param refType Type of ref in &#x60;sha&#x60;. Use to select the correct ref when a branch and tag share a name.
     * @param format Archive format, added as a suffix to the path, for example &#x60;archive.zip&#x60;. One of &#x60;bz2&#x60;, &#x60;gz&#x60;, &#x60;tar&#x60;, &#x60;tar.bz2&#x60;, &#x60;tar.gz&#x60;, &#x60;tb2&#x60;, &#x60;tbz&#x60;, &#x60;tbz2&#x60;, &#x60;tgz&#x60;, or &#x60;zip&#x60;. Default is &#x60;tar.gz&#x60;.
     * @param path Subpath of the repository to download. An empty string returns the whole repository.
     * @param includeLfsBlobs If &#x60;true&#x60;, LFS objects are included in the archive. When set to &#x60;false&#x60;, LFS objects are excluded.
     * @param excludePaths Comma-separated list of paths to exclude from the archive.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdRepositoryArchive(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sha, @jakarta.annotation.Nullable String refType, @jakarta.annotation.Nullable String format, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean includeLfsBlobs, @jakarta.annotation.Nullable List<String> excludePaths) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdRepositoryArchiveRequestCreation(id, sha, refType, format, path, includeLfsBlobs, excludePaths).body(localVarReturnType);
    }

    /**
     * Retrieve file archive from a repository
     * Retrieves the file archive of a specified repository. This endpoint can be accessed without authentication if the repository is publicly accessible. For GitLab.com users, this endpoint has a rate limit threshold of 5 requests per minute.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA to download. Accepts a tag, branch reference, or SHA. If omitted, defaults to the tip of the default branch.
     * @param refType Type of ref in &#x60;sha&#x60;. Use to select the correct ref when a branch and tag share a name.
     * @param format Archive format, added as a suffix to the path, for example &#x60;archive.zip&#x60;. One of &#x60;bz2&#x60;, &#x60;gz&#x60;, &#x60;tar&#x60;, &#x60;tar.bz2&#x60;, &#x60;tar.gz&#x60;, &#x60;tb2&#x60;, &#x60;tbz&#x60;, &#x60;tbz2&#x60;, &#x60;tgz&#x60;, or &#x60;zip&#x60;. Default is &#x60;tar.gz&#x60;.
     * @param path Subpath of the repository to download. An empty string returns the whole repository.
     * @param includeLfsBlobs If &#x60;true&#x60;, LFS objects are included in the archive. When set to &#x60;false&#x60;, LFS objects are excluded.
     * @param excludePaths Comma-separated list of paths to exclude from the archive.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdRepositoryArchiveWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sha, @jakarta.annotation.Nullable String refType, @jakarta.annotation.Nullable String format, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean includeLfsBlobs, @jakarta.annotation.Nullable List<String> excludePaths) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryArchiveRequestCreation(id, sha, refType, format, path, includeLfsBlobs, excludePaths).toEntity(localVarReturnType);
    }

    /**
     * Retrieve file archive from a repository
     * Retrieves the file archive of a specified repository. This endpoint can be accessed without authentication if the repository is publicly accessible. For GitLab.com users, this endpoint has a rate limit threshold of 5 requests per minute.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha Commit SHA to download. Accepts a tag, branch reference, or SHA. If omitted, defaults to the tip of the default branch.
     * @param refType Type of ref in &#x60;sha&#x60;. Use to select the correct ref when a branch and tag share a name.
     * @param format Archive format, added as a suffix to the path, for example &#x60;archive.zip&#x60;. One of &#x60;bz2&#x60;, &#x60;gz&#x60;, &#x60;tar&#x60;, &#x60;tar.bz2&#x60;, &#x60;tar.gz&#x60;, &#x60;tb2&#x60;, &#x60;tbz&#x60;, &#x60;tbz2&#x60;, &#x60;tgz&#x60;, or &#x60;zip&#x60;. Default is &#x60;tar.gz&#x60;.
     * @param path Subpath of the repository to download. An empty string returns the whole repository.
     * @param includeLfsBlobs If &#x60;true&#x60;, LFS objects are included in the archive. When set to &#x60;false&#x60;, LFS objects are excluded.
     * @param excludePaths Comma-separated list of paths to exclude from the archive.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryArchiveWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sha, @jakarta.annotation.Nullable String refType, @jakarta.annotation.Nullable String format, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean includeLfsBlobs, @jakarta.annotation.Nullable List<String> excludePaths) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryArchiveRequestCreation(id, sha, refType, format, path, includeLfsBlobs, excludePaths);
    }

    /**
     * Retrieve a blob from a repository
     * Retrieves information, such as size and content, about blobs in a repository. Blob content is Base64 encoded. This endpoint can be accessed without authentication, if the repository is publicly accessible. For blobs larger than 10 MB, this endpoint has a rate limit of 5 requests per minute. For text files not stored as valid UTF-8 in Git, &#x60;content&#x60; is converted to UTF-8 before Base64 encoding, and &#x60;size&#x60; is the raw blob size, which might differ from the length of the decoded content.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha SHA of the blob.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryBlobsShaRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryBlobsSha", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha' is set
        if (sha == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha' when calling getApiV4ProjectsIdRepositoryBlobsSha", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("sha", sha);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/blobs/{sha}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a blob from a repository
     * Retrieves information, such as size and content, about blobs in a repository. Blob content is Base64 encoded. This endpoint can be accessed without authentication, if the repository is publicly accessible. For blobs larger than 10 MB, this endpoint has a rate limit of 5 requests per minute. For text files not stored as valid UTF-8 in Git, &#x60;content&#x60; is converted to UTF-8 before Base64 encoding, and &#x60;size&#x60; is the raw blob size, which might differ from the length of the decoded content.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha SHA of the blob.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdRepositoryBlobsSha(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdRepositoryBlobsShaRequestCreation(id, sha).body(localVarReturnType);
    }

    /**
     * Retrieve a blob from a repository
     * Retrieves information, such as size and content, about blobs in a repository. Blob content is Base64 encoded. This endpoint can be accessed without authentication, if the repository is publicly accessible. For blobs larger than 10 MB, this endpoint has a rate limit of 5 requests per minute. For text files not stored as valid UTF-8 in Git, &#x60;content&#x60; is converted to UTF-8 before Base64 encoding, and &#x60;size&#x60; is the raw blob size, which might differ from the length of the decoded content.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha SHA of the blob.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdRepositoryBlobsShaWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryBlobsShaRequestCreation(id, sha).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a blob from a repository
     * Retrieves information, such as size and content, about blobs in a repository. Blob content is Base64 encoded. This endpoint can be accessed without authentication, if the repository is publicly accessible. For blobs larger than 10 MB, this endpoint has a rate limit of 5 requests per minute. For text files not stored as valid UTF-8 in Git, &#x60;content&#x60; is converted to UTF-8 before Base64 encoding, and &#x60;size&#x60; is the raw blob size, which might differ from the length of the decoded content.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha SHA of the blob.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryBlobsShaWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryBlobsShaRequestCreation(id, sha);
    }

    /**
     * Retrieve raw blob content
     * Retrieves the raw file contents for a blob, by blob SHA. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha SHA of the blob.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryBlobsShaRawRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryBlobsShaRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha' is set
        if (sha == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha' when calling getApiV4ProjectsIdRepositoryBlobsShaRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("sha", sha);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/blobs/{sha}/raw", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve raw blob content
     * Retrieves the raw file contents for a blob, by blob SHA. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha SHA of the blob.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdRepositoryBlobsShaRaw(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdRepositoryBlobsShaRawRequestCreation(id, sha).body(localVarReturnType);
    }

    /**
     * Retrieve raw blob content
     * Retrieves the raw file contents for a blob, by blob SHA. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha SHA of the blob.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdRepositoryBlobsShaRawWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryBlobsShaRawRequestCreation(id, sha).toEntity(localVarReturnType);
    }

    /**
     * Retrieve raw blob content
     * Retrieves the raw file contents for a blob, by blob SHA. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sha SHA of the blob.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryBlobsShaRawWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sha) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryBlobsShaRawRequestCreation(id, sha);
    }

    /**
     * List files changed between two commits
     * Returns the path, change status, and file modes for every path that differs between two refs. This endpoint requires authentication. This endpoint is behind the &#x60;repository_changed_paths_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param from The commit SHA or branch/tag to compare from
     * @param to The commit SHA or branch/tag to compare to
     * @param findRenames If true, detect file renames
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesChangedPath&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryChangedPathsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String from, @jakarta.annotation.Nonnull String to, @jakarta.annotation.Nullable Boolean findRenames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryChangedPaths", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'from' is set
        if (from == null) {
            throw new RestClientResponseException("Missing the required parameter 'from' when calling getApiV4ProjectsIdRepositoryChangedPaths", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'to' is set
        if (to == null) {
            throw new RestClientResponseException("Missing the required parameter 'to' when calling getApiV4ProjectsIdRepositoryChangedPaths", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "from", from));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "to", to));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "find_renames", findRenames));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesChangedPath>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/changed_paths", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List files changed between two commits
     * Returns the path, change status, and file modes for every path that differs between two refs. This endpoint requires authentication. This endpoint is behind the &#x60;repository_changed_paths_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param from The commit SHA or branch/tag to compare from
     * @param to The commit SHA or branch/tag to compare to
     * @param findRenames If true, detect file renames
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesChangedPath&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesChangedPath> getApiV4ProjectsIdRepositoryChangedPaths(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String from, @jakarta.annotation.Nonnull String to, @jakarta.annotation.Nullable Boolean findRenames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesChangedPath>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryChangedPathsRequestCreation(id, from, to, findRenames, page, perPage).body(localVarReturnType);
    }

    /**
     * List files changed between two commits
     * Returns the path, change status, and file modes for every path that differs between two refs. This endpoint requires authentication. This endpoint is behind the &#x60;repository_changed_paths_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param from The commit SHA or branch/tag to compare from
     * @param to The commit SHA or branch/tag to compare to
     * @param findRenames If true, detect file renames
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesChangedPath&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesChangedPath>> getApiV4ProjectsIdRepositoryChangedPathsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String from, @jakarta.annotation.Nonnull String to, @jakarta.annotation.Nullable Boolean findRenames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesChangedPath>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryChangedPathsRequestCreation(id, from, to, findRenames, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List files changed between two commits
     * Returns the path, change status, and file modes for every path that differs between two refs. This endpoint requires authentication. This endpoint is behind the &#x60;repository_changed_paths_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param from The commit SHA or branch/tag to compare from
     * @param to The commit SHA or branch/tag to compare to
     * @param findRenames If true, detect file renames
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryChangedPathsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String from, @jakarta.annotation.Nonnull String to, @jakarta.annotation.Nullable Boolean findRenames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryChangedPathsRequestCreation(id, from, to, findRenames, page, perPage);
    }

    /**
     * Generate changelog data
     * Generates changelog data based on commits in a repository, without committing them to a changelog file. Works exactly like &#x60;POST /projects/:id/repository/changelog&#x60;, except the changelog data is not committed to any changelog file. Add a &#x60;.txt&#x60; suffix to the path, as in &#x60;GET /projects/:id/repository/changelog.txt&#x60;, to return the changelog as plain text Markdown instead of JSON.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param version Version to generate the changelog for. Must follow [semantic versioning](https://semver.org/) format. Can include or omit the &#x60;v&#x60; prefix: &#x60;1.0.0&#x60; and &#x60;v1.0.0&#x60; give the same result.
     * @param from First commit in the range of commits, as a SHA, to use for the changelog. This commit is not included in the changelog. If omitted, the most recent stable version tag before &#x60;version&#x60; is used. Tags in &#x60;X.Y.Z&#x60; or &#x60;vX.Y.Z&#x60; format are recognized, and pre-release tags such as &#x60;v1.0.0-pre1&#x60; are ignored. If no such tag exists, an error is returned and you must set &#x60;from&#x60;.
     * @param to Last commit in the range of commits, as a SHA or a branch or tag name, to use for the changelog. This commit is included in the changelog. If omitted, uses the latest commit on the default branch. For the Add changelog data to file operation, uses the latest commit on &#x60;branch&#x60; instead. The range can contain at most 15,000 commits.
     * @param date Date and time of the release. Defaults to the current time.
     * @param trailer Git trailer to use for including commits. Case-sensitive: &#x60;Example&#x60; does not match &#x60;example&#x60; or &#x60;eXaMpLE&#x60;.
     * @param configFile Path to the changelog configuration file in the project&#39;s Git repository. If omitted, uses &#x60;.gitlab/changelog_config.yml&#x60;.
     * @param configFileRef Git reference, for example a branch, where the changelog configuration file is defined. Defaults to the default branch of the repository. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/426108) in GitLab 18.2.
     * @return APIEntitiesChangelog
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryChangelogRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String version, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable OffsetDateTime date, @jakarta.annotation.Nullable String trailer, @jakarta.annotation.Nullable String configFile, @jakarta.annotation.Nullable String configFileRef) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryChangelog", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'version' is set
        if (version == null) {
            throw new RestClientResponseException("Missing the required parameter 'version' when calling getApiV4ProjectsIdRepositoryChangelog", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version", version));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "from", from));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "to", to));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "date", date));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "trailer", trailer));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "config_file", configFile));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "config_file_ref", configFileRef));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesChangelog> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/changelog", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Generate changelog data
     * Generates changelog data based on commits in a repository, without committing them to a changelog file. Works exactly like &#x60;POST /projects/:id/repository/changelog&#x60;, except the changelog data is not committed to any changelog file. Add a &#x60;.txt&#x60; suffix to the path, as in &#x60;GET /projects/:id/repository/changelog.txt&#x60;, to return the changelog as plain text Markdown instead of JSON.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param version Version to generate the changelog for. Must follow [semantic versioning](https://semver.org/) format. Can include or omit the &#x60;v&#x60; prefix: &#x60;1.0.0&#x60; and &#x60;v1.0.0&#x60; give the same result.
     * @param from First commit in the range of commits, as a SHA, to use for the changelog. This commit is not included in the changelog. If omitted, the most recent stable version tag before &#x60;version&#x60; is used. Tags in &#x60;X.Y.Z&#x60; or &#x60;vX.Y.Z&#x60; format are recognized, and pre-release tags such as &#x60;v1.0.0-pre1&#x60; are ignored. If no such tag exists, an error is returned and you must set &#x60;from&#x60;.
     * @param to Last commit in the range of commits, as a SHA or a branch or tag name, to use for the changelog. This commit is included in the changelog. If omitted, uses the latest commit on the default branch. For the Add changelog data to file operation, uses the latest commit on &#x60;branch&#x60; instead. The range can contain at most 15,000 commits.
     * @param date Date and time of the release. Defaults to the current time.
     * @param trailer Git trailer to use for including commits. Case-sensitive: &#x60;Example&#x60; does not match &#x60;example&#x60; or &#x60;eXaMpLE&#x60;.
     * @param configFile Path to the changelog configuration file in the project&#39;s Git repository. If omitted, uses &#x60;.gitlab/changelog_config.yml&#x60;.
     * @param configFileRef Git reference, for example a branch, where the changelog configuration file is defined. Defaults to the default branch of the repository. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/426108) in GitLab 18.2.
     * @return APIEntitiesChangelog
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesChangelog getApiV4ProjectsIdRepositoryChangelog(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String version, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable OffsetDateTime date, @jakarta.annotation.Nullable String trailer, @jakarta.annotation.Nullable String configFile, @jakarta.annotation.Nullable String configFileRef) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesChangelog> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryChangelogRequestCreation(id, version, from, to, date, trailer, configFile, configFileRef).body(localVarReturnType);
    }

    /**
     * Generate changelog data
     * Generates changelog data based on commits in a repository, without committing them to a changelog file. Works exactly like &#x60;POST /projects/:id/repository/changelog&#x60;, except the changelog data is not committed to any changelog file. Add a &#x60;.txt&#x60; suffix to the path, as in &#x60;GET /projects/:id/repository/changelog.txt&#x60;, to return the changelog as plain text Markdown instead of JSON.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param version Version to generate the changelog for. Must follow [semantic versioning](https://semver.org/) format. Can include or omit the &#x60;v&#x60; prefix: &#x60;1.0.0&#x60; and &#x60;v1.0.0&#x60; give the same result.
     * @param from First commit in the range of commits, as a SHA, to use for the changelog. This commit is not included in the changelog. If omitted, the most recent stable version tag before &#x60;version&#x60; is used. Tags in &#x60;X.Y.Z&#x60; or &#x60;vX.Y.Z&#x60; format are recognized, and pre-release tags such as &#x60;v1.0.0-pre1&#x60; are ignored. If no such tag exists, an error is returned and you must set &#x60;from&#x60;.
     * @param to Last commit in the range of commits, as a SHA or a branch or tag name, to use for the changelog. This commit is included in the changelog. If omitted, uses the latest commit on the default branch. For the Add changelog data to file operation, uses the latest commit on &#x60;branch&#x60; instead. The range can contain at most 15,000 commits.
     * @param date Date and time of the release. Defaults to the current time.
     * @param trailer Git trailer to use for including commits. Case-sensitive: &#x60;Example&#x60; does not match &#x60;example&#x60; or &#x60;eXaMpLE&#x60;.
     * @param configFile Path to the changelog configuration file in the project&#39;s Git repository. If omitted, uses &#x60;.gitlab/changelog_config.yml&#x60;.
     * @param configFileRef Git reference, for example a branch, where the changelog configuration file is defined. Defaults to the default branch of the repository. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/426108) in GitLab 18.2.
     * @return ResponseEntity&lt;APIEntitiesChangelog&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesChangelog> getApiV4ProjectsIdRepositoryChangelogWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String version, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable OffsetDateTime date, @jakarta.annotation.Nullable String trailer, @jakarta.annotation.Nullable String configFile, @jakarta.annotation.Nullable String configFileRef) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesChangelog> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryChangelogRequestCreation(id, version, from, to, date, trailer, configFile, configFileRef).toEntity(localVarReturnType);
    }

    /**
     * Generate changelog data
     * Generates changelog data based on commits in a repository, without committing them to a changelog file. Works exactly like &#x60;POST /projects/:id/repository/changelog&#x60;, except the changelog data is not committed to any changelog file. Add a &#x60;.txt&#x60; suffix to the path, as in &#x60;GET /projects/:id/repository/changelog.txt&#x60;, to return the changelog as plain text Markdown instead of JSON.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param version Version to generate the changelog for. Must follow [semantic versioning](https://semver.org/) format. Can include or omit the &#x60;v&#x60; prefix: &#x60;1.0.0&#x60; and &#x60;v1.0.0&#x60; give the same result.
     * @param from First commit in the range of commits, as a SHA, to use for the changelog. This commit is not included in the changelog. If omitted, the most recent stable version tag before &#x60;version&#x60; is used. Tags in &#x60;X.Y.Z&#x60; or &#x60;vX.Y.Z&#x60; format are recognized, and pre-release tags such as &#x60;v1.0.0-pre1&#x60; are ignored. If no such tag exists, an error is returned and you must set &#x60;from&#x60;.
     * @param to Last commit in the range of commits, as a SHA or a branch or tag name, to use for the changelog. This commit is included in the changelog. If omitted, uses the latest commit on the default branch. For the Add changelog data to file operation, uses the latest commit on &#x60;branch&#x60; instead. The range can contain at most 15,000 commits.
     * @param date Date and time of the release. Defaults to the current time.
     * @param trailer Git trailer to use for including commits. Case-sensitive: &#x60;Example&#x60; does not match &#x60;example&#x60; or &#x60;eXaMpLE&#x60;.
     * @param configFile Path to the changelog configuration file in the project&#39;s Git repository. If omitted, uses &#x60;.gitlab/changelog_config.yml&#x60;.
     * @param configFileRef Git reference, for example a branch, where the changelog configuration file is defined. Defaults to the default branch of the repository. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/426108) in GitLab 18.2.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryChangelogWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String version, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable OffsetDateTime date, @jakarta.annotation.Nullable String trailer, @jakarta.annotation.Nullable String configFile, @jakarta.annotation.Nullable String configFileRef) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryChangelogRequestCreation(id, version, from, to, date, trailer, configFile, configFileRef);
    }

    /**
     * Compare branches, tags, or commits
     * Compares branches, tags, or commits. Retrieves the differences between two branches, tags, or commits in a specified project. This endpoint can be accessed without authentication if the repository is publicly accessible. When &#x60;compare_timeout&#x60; is &#x60;true&#x60;, the comparison exceeded size limits: the &#x60;commits&#x60; array is still complete, but the &#x60;diffs&#x60; array might be incomplete, and individual diff objects might have empty &#x60;diff&#x60; strings. This endpoint is rate-limited for each authenticated user, and for each IP address for unauthenticated requests. On GitLab Self-Managed and GitLab Dedicated, an administrator can change these limits in the application settings.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>429</b> - Too Many Requests
     * <p><b>503</b> - Service Unavailable
     * @param id ID or URL-encoded path of the project.
     * @param from Ref to compare from. Accepts a commit SHA, branch name, or tag name.
     * @param to Ref to compare to. Accepts a commit SHA, branch name, or tag name.
     * @param fromProjectId ID of the project to compare from.
     * @param straight If &#x60;true&#x60;, comparison method is direct comparison between &#x60;from&#x60; and &#x60;to&#x60; (&#x60;from&#x60;..&#x60;to&#x60;). If &#x60;false&#x60;, compares using merge base (&#x60;from&#x60;...&#x60;to&#x60;).
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return APIEntitiesCompare
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryCompareRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable Integer fromProjectId, @jakarta.annotation.Nullable Boolean straight, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryCompare", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'from' is set
        if (from == null) {
            throw new RestClientResponseException("Missing the required parameter 'from' when calling getApiV4ProjectsIdRepositoryCompare", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'to' is set
        if (to == null) {
            throw new RestClientResponseException("Missing the required parameter 'to' when calling getApiV4ProjectsIdRepositoryCompare", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "from", from));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "to", to));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "from_project_id", fromProjectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "straight", straight));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "unidiff", unidiff));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesCompare> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/compare", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Compare branches, tags, or commits
     * Compares branches, tags, or commits. Retrieves the differences between two branches, tags, or commits in a specified project. This endpoint can be accessed without authentication if the repository is publicly accessible. When &#x60;compare_timeout&#x60; is &#x60;true&#x60;, the comparison exceeded size limits: the &#x60;commits&#x60; array is still complete, but the &#x60;diffs&#x60; array might be incomplete, and individual diff objects might have empty &#x60;diff&#x60; strings. This endpoint is rate-limited for each authenticated user, and for each IP address for unauthenticated requests. On GitLab Self-Managed and GitLab Dedicated, an administrator can change these limits in the application settings.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>429</b> - Too Many Requests
     * <p><b>503</b> - Service Unavailable
     * @param id ID or URL-encoded path of the project.
     * @param from Ref to compare from. Accepts a commit SHA, branch name, or tag name.
     * @param to Ref to compare to. Accepts a commit SHA, branch name, or tag name.
     * @param fromProjectId ID of the project to compare from.
     * @param straight If &#x60;true&#x60;, comparison method is direct comparison between &#x60;from&#x60; and &#x60;to&#x60; (&#x60;from&#x60;..&#x60;to&#x60;). If &#x60;false&#x60;, compares using merge base (&#x60;from&#x60;...&#x60;to&#x60;).
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return APIEntitiesCompare
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCompare getApiV4ProjectsIdRepositoryCompare(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable Integer fromProjectId, @jakarta.annotation.Nullable Boolean straight, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCompare> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCompareRequestCreation(id, from, to, fromProjectId, straight, unidiff).body(localVarReturnType);
    }

    /**
     * Compare branches, tags, or commits
     * Compares branches, tags, or commits. Retrieves the differences between two branches, tags, or commits in a specified project. This endpoint can be accessed without authentication if the repository is publicly accessible. When &#x60;compare_timeout&#x60; is &#x60;true&#x60;, the comparison exceeded size limits: the &#x60;commits&#x60; array is still complete, but the &#x60;diffs&#x60; array might be incomplete, and individual diff objects might have empty &#x60;diff&#x60; strings. This endpoint is rate-limited for each authenticated user, and for each IP address for unauthenticated requests. On GitLab Self-Managed and GitLab Dedicated, an administrator can change these limits in the application settings.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>429</b> - Too Many Requests
     * <p><b>503</b> - Service Unavailable
     * @param id ID or URL-encoded path of the project.
     * @param from Ref to compare from. Accepts a commit SHA, branch name, or tag name.
     * @param to Ref to compare to. Accepts a commit SHA, branch name, or tag name.
     * @param fromProjectId ID of the project to compare from.
     * @param straight If &#x60;true&#x60;, comparison method is direct comparison between &#x60;from&#x60; and &#x60;to&#x60; (&#x60;from&#x60;..&#x60;to&#x60;). If &#x60;false&#x60;, compares using merge base (&#x60;from&#x60;...&#x60;to&#x60;).
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return ResponseEntity&lt;APIEntitiesCompare&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCompare> getApiV4ProjectsIdRepositoryCompareWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable Integer fromProjectId, @jakarta.annotation.Nullable Boolean straight, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCompare> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCompareRequestCreation(id, from, to, fromProjectId, straight, unidiff).toEntity(localVarReturnType);
    }

    /**
     * Compare branches, tags, or commits
     * Compares branches, tags, or commits. Retrieves the differences between two branches, tags, or commits in a specified project. This endpoint can be accessed without authentication if the repository is publicly accessible. When &#x60;compare_timeout&#x60; is &#x60;true&#x60;, the comparison exceeded size limits: the &#x60;commits&#x60; array is still complete, but the &#x60;diffs&#x60; array might be incomplete, and individual diff objects might have empty &#x60;diff&#x60; strings. This endpoint is rate-limited for each authenticated user, and for each IP address for unauthenticated requests. On GitLab Self-Managed and GitLab Dedicated, an administrator can change these limits in the application settings.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>429</b> - Too Many Requests
     * <p><b>503</b> - Service Unavailable
     * @param id ID or URL-encoded path of the project.
     * @param from Ref to compare from. Accepts a commit SHA, branch name, or tag name.
     * @param to Ref to compare to. Accepts a commit SHA, branch name, or tag name.
     * @param fromProjectId ID of the project to compare from.
     * @param straight If &#x60;true&#x60;, comparison method is direct comparison between &#x60;from&#x60; and &#x60;to&#x60; (&#x60;from&#x60;..&#x60;to&#x60;). If &#x60;false&#x60;, compares using merge base (&#x60;from&#x60;...&#x60;to&#x60;).
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryCompareWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable Integer fromProjectId, @jakarta.annotation.Nullable Boolean straight, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryCompareRequestCreation(id, from, to, fromProjectId, straight, unidiff);
    }

    /**
     * Retrieve contributors metrics
     * Retrieves a list of contributors to a specified repository. This endpoint can be accessed without authentication if the repository is publicly accessible. The commit count does not include merge commits.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param ref Name of a repository branch or tag. If omitted, uses the default branch. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/156852) in GitLab 17.4.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return List&lt;APIEntitiesContributor&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryContributorsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryContributors", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ref", ref));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesContributor>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/contributors", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve contributors metrics
     * Retrieves a list of contributors to a specified repository. This endpoint can be accessed without authentication if the repository is publicly accessible. The commit count does not include merge commits.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param ref Name of a repository branch or tag. If omitted, uses the default branch. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/156852) in GitLab 17.4.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return List&lt;APIEntitiesContributor&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesContributor> getApiV4ProjectsIdRepositoryContributors(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesContributor>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryContributorsRequestCreation(id, page, perPage, ref, orderBy, sort).body(localVarReturnType);
    }

    /**
     * Retrieve contributors metrics
     * Retrieves a list of contributors to a specified repository. This endpoint can be accessed without authentication if the repository is publicly accessible. The commit count does not include merge commits.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param ref Name of a repository branch or tag. If omitted, uses the default branch. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/156852) in GitLab 17.4.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return ResponseEntity&lt;List&lt;APIEntitiesContributor&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesContributor>> getApiV4ProjectsIdRepositoryContributorsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesContributor>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryContributorsRequestCreation(id, page, perPage, ref, orderBy, sort).toEntity(localVarReturnType);
    }

    /**
     * Retrieve contributors metrics
     * Retrieves a list of contributors to a specified repository. This endpoint can be accessed without authentication if the repository is publicly accessible. The commit count does not include merge commits.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param ref Name of a repository branch or tag. If omitted, uses the default branch. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/156852) in GitLab 17.4.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryContributorsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryContributorsRequestCreation(id, page, perPage, ref, orderBy, sort);
    }

    /**
     * Get diff statistics between two commits
     * Returns the number of added and deleted lines for every file that differs between two refs. This endpoint requires authentication. This endpoint is behind the &#x60;repository_diff_stats_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param from The commit SHA or branch/tag to compare from
     * @param to The commit SHA or branch/tag to compare to
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDiffStat&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryDiffStatsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryDiffStats", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'from' is set
        if (from == null) {
            throw new RestClientResponseException("Missing the required parameter 'from' when calling getApiV4ProjectsIdRepositoryDiffStats", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'to' is set
        if (to == null) {
            throw new RestClientResponseException("Missing the required parameter 'to' when calling getApiV4ProjectsIdRepositoryDiffStats", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "from", from));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "to", to));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesDiffStat>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/diff_stats", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get diff statistics between two commits
     * Returns the number of added and deleted lines for every file that differs between two refs. This endpoint requires authentication. This endpoint is behind the &#x60;repository_diff_stats_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param from The commit SHA or branch/tag to compare from
     * @param to The commit SHA or branch/tag to compare to
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDiffStat&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDiffStat> getApiV4ProjectsIdRepositoryDiffStats(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiffStat>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryDiffStatsRequestCreation(id, from, to, page, perPage).body(localVarReturnType);
    }

    /**
     * Get diff statistics between two commits
     * Returns the number of added and deleted lines for every file that differs between two refs. This endpoint requires authentication. This endpoint is behind the &#x60;repository_diff_stats_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param from The commit SHA or branch/tag to compare from
     * @param to The commit SHA or branch/tag to compare to
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDiffStat&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDiffStat>> getApiV4ProjectsIdRepositoryDiffStatsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiffStat>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryDiffStatsRequestCreation(id, from, to, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Get diff statistics between two commits
     * Returns the number of added and deleted lines for every file that differs between two refs. This endpoint requires authentication. This endpoint is behind the &#x60;repository_diff_stats_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param from The commit SHA or branch/tag to compare from
     * @param to The commit SHA or branch/tag to compare to
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryDiffStatsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryDiffStatsRequestCreation(id, from, to, page, perPage);
    }

    /**
     * Retrieve diverging commit counts between two refs
     * Retrieves the number of commits by which the &#x60;to&#x60; ref is ahead of and behind the &#x60;from&#x60; ref. &#x60;behind&#x60; is the number of commits in &#x60;from&#x60; that are not in &#x60;to&#x60;, and &#x60;ahead&#x60; is the number of commits in &#x60;to&#x60; that are not in &#x60;from&#x60;. This endpoint requires authentication. This endpoint is behind the &#x60;repository_diverging_commits_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param from Ref to compare from. Accepts a commit SHA, branch name, or tag name.
     * @param to Ref to compare to. Accepts a commit SHA, branch name, or tag name.
     * @param maxCount Maximum number of commits to count. Use &#x60;0&#x60; for unlimited.
     * @return APIEntitiesDivergingCommitCount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryDivergingCommitsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable Integer maxCount) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryDivergingCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'from' is set
        if (from == null) {
            throw new RestClientResponseException("Missing the required parameter 'from' when calling getApiV4ProjectsIdRepositoryDivergingCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'to' is set
        if (to == null) {
            throw new RestClientResponseException("Missing the required parameter 'to' when calling getApiV4ProjectsIdRepositoryDivergingCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "from", from));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "to", to));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "max_count", maxCount));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesDivergingCommitCount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/diverging_commits", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve diverging commit counts between two refs
     * Retrieves the number of commits by which the &#x60;to&#x60; ref is ahead of and behind the &#x60;from&#x60; ref. &#x60;behind&#x60; is the number of commits in &#x60;from&#x60; that are not in &#x60;to&#x60;, and &#x60;ahead&#x60; is the number of commits in &#x60;to&#x60; that are not in &#x60;from&#x60;. This endpoint requires authentication. This endpoint is behind the &#x60;repository_diverging_commits_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param from Ref to compare from. Accepts a commit SHA, branch name, or tag name.
     * @param to Ref to compare to. Accepts a commit SHA, branch name, or tag name.
     * @param maxCount Maximum number of commits to count. Use &#x60;0&#x60; for unlimited.
     * @return APIEntitiesDivergingCommitCount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDivergingCommitCount getApiV4ProjectsIdRepositoryDivergingCommits(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable Integer maxCount) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDivergingCommitCount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryDivergingCommitsRequestCreation(id, from, to, maxCount).body(localVarReturnType);
    }

    /**
     * Retrieve diverging commit counts between two refs
     * Retrieves the number of commits by which the &#x60;to&#x60; ref is ahead of and behind the &#x60;from&#x60; ref. &#x60;behind&#x60; is the number of commits in &#x60;from&#x60; that are not in &#x60;to&#x60;, and &#x60;ahead&#x60; is the number of commits in &#x60;to&#x60; that are not in &#x60;from&#x60;. This endpoint requires authentication. This endpoint is behind the &#x60;repository_diverging_commits_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param from Ref to compare from. Accepts a commit SHA, branch name, or tag name.
     * @param to Ref to compare to. Accepts a commit SHA, branch name, or tag name.
     * @param maxCount Maximum number of commits to count. Use &#x60;0&#x60; for unlimited.
     * @return ResponseEntity&lt;APIEntitiesDivergingCommitCount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDivergingCommitCount> getApiV4ProjectsIdRepositoryDivergingCommitsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable Integer maxCount) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDivergingCommitCount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryDivergingCommitsRequestCreation(id, from, to, maxCount).toEntity(localVarReturnType);
    }

    /**
     * Retrieve diverging commit counts between two refs
     * Retrieves the number of commits by which the &#x60;to&#x60; ref is ahead of and behind the &#x60;from&#x60; ref. &#x60;behind&#x60; is the number of commits in &#x60;from&#x60; that are not in &#x60;to&#x60;, and &#x60;ahead&#x60; is the number of commits in &#x60;to&#x60; that are not in &#x60;from&#x60;. This endpoint requires authentication. This endpoint is behind the &#x60;repository_diverging_commits_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param from Ref to compare from. Accepts a commit SHA, branch name, or tag name.
     * @param to Ref to compare to. Accepts a commit SHA, branch name, or tag name.
     * @param maxCount Maximum number of commits to count. Use &#x60;0&#x60; for unlimited.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryDivergingCommitsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String to, @jakarta.annotation.Nullable Integer maxCount) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryDivergingCommitsRequestCreation(id, from, to, maxCount);
    }

    /**
     * Retrieve repository health statistics
     * Retrieves statistics related to the health of a project repository. This endpoint is rate-limited to 5 requests/hour per project when &#x60;generate&#x60; is &#x60;true&#x60;. Available only to users with push access to the repository. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param generate If &#x60;true&#x60;, generates a new health report. Set this if the endpoint returns &#x60;404&#x60;.
     * @return APIEntitiesRepositoryHealth
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryHealthRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean generate) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryHealth", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "generate", generate));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesRepositoryHealth> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/health", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve repository health statistics
     * Retrieves statistics related to the health of a project repository. This endpoint is rate-limited to 5 requests/hour per project when &#x60;generate&#x60; is &#x60;true&#x60;. Available only to users with push access to the repository. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param generate If &#x60;true&#x60;, generates a new health report. Set this if the endpoint returns &#x60;404&#x60;.
     * @return APIEntitiesRepositoryHealth
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesRepositoryHealth getApiV4ProjectsIdRepositoryHealth(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean generate) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRepositoryHealth> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryHealthRequestCreation(id, generate).body(localVarReturnType);
    }

    /**
     * Retrieve repository health statistics
     * Retrieves statistics related to the health of a project repository. This endpoint is rate-limited to 5 requests/hour per project when &#x60;generate&#x60; is &#x60;true&#x60;. Available only to users with push access to the repository. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param generate If &#x60;true&#x60;, generates a new health report. Set this if the endpoint returns &#x60;404&#x60;.
     * @return ResponseEntity&lt;APIEntitiesRepositoryHealth&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesRepositoryHealth> getApiV4ProjectsIdRepositoryHealthWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean generate) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRepositoryHealth> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryHealthRequestCreation(id, generate).toEntity(localVarReturnType);
    }

    /**
     * Retrieve repository health statistics
     * Retrieves statistics related to the health of a project repository. This endpoint is rate-limited to 5 requests/hour per project when &#x60;generate&#x60; is &#x60;true&#x60;. Available only to users with push access to the repository. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param generate If &#x60;true&#x60;, generates a new health report. Set this if the endpoint returns &#x60;404&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryHealthWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean generate) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryHealthRequestCreation(id, generate);
    }

    /**
     * Retrieve a merge base
     * Retrieves the merge base, the common ancestor, of two or more refs, such as commit SHAs, branch names, or tags.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param refs Refs to find the common ancestor of. Accepts multiple refs.
     * @return APIEntitiesCommit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryMergeBaseRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable List<String> refs) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryMergeBase", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'refs' is set
        if (refs == null) {
            throw new RestClientResponseException("Missing the required parameter 'refs' when calling getApiV4ProjectsIdRepositoryMergeBase", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "refs", refs));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesCommit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/merge_base", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a merge base
     * Retrieves the merge base, the common ancestor, of two or more refs, such as commit SHAs, branch names, or tags.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param refs Refs to find the common ancestor of. Accepts multiple refs.
     * @return APIEntitiesCommit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCommit getApiV4ProjectsIdRepositoryMergeBase(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable List<String> refs) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryMergeBaseRequestCreation(id, refs).body(localVarReturnType);
    }

    /**
     * Retrieve a merge base
     * Retrieves the merge base, the common ancestor, of two or more refs, such as commit SHAs, branch names, or tags.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param refs Refs to find the common ancestor of. Accepts multiple refs.
     * @return ResponseEntity&lt;APIEntitiesCommit&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCommit> getApiV4ProjectsIdRepositoryMergeBaseWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable List<String> refs) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryMergeBaseRequestCreation(id, refs).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a merge base
     * Retrieves the merge base, the common ancestor, of two or more refs, such as commit SHAs, branch names, or tags.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param refs Refs to find the common ancestor of. Accepts multiple refs.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryMergeBaseWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable List<String> refs) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryMergeBaseRequestCreation(id, refs);
    }

    /**
     * List all repository trees in a project
     * Lists all repository files and directories in a specified project. This endpoint can be accessed without authentication if the repository is publicly accessible. This command provides essentially the same features as the &#x60;git ls-tree&#x60; command. Use &#x60;with_last_commit&#x60; to include the last commit that changed each entry. &#x60;with_last_commit&#x60; cannot be combined with &#x60;recursive&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - 404 Project Not Found
     * @param id ID or URL-encoded path of the project.
     * @param ref Name of a repository branch or tag. If omitted, uses the default branch.
     * @param path Path inside the repository, used to get the contents of subdirectories.
     * @param recursive If &#x60;true&#x60;, returns a recursive tree.
     * @param withLastCommit If &#x60;true&#x60;, includes the last commit for each tree entry. Cannot be combined with &#x60;recursive&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234455) in GitLab 19.3.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param pagination Pagination method. &#x60;keyset&#x60; uses the [keyset-based pagination method](https://docs.gitlab.com/api/rest/#keyset-based-pagination). &#x60;none&#x60; is valid only if &#x60;recursive&#x60; is &#x60;true&#x60;.
     * @param pageToken Tree record ID at which to fetch the next page. Used only with keyset pagination.
     * @return List&lt;APIEntitiesTreeObject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryTreeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean recursive, @jakarta.annotation.Nullable Boolean withLastCommit, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String pagination, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryTree", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ref", ref));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "path", path));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "recursive", recursive));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_last_commit", withLastCommit));
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

        ParameterizedTypeReference<List<APIEntitiesTreeObject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/tree", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all repository trees in a project
     * Lists all repository files and directories in a specified project. This endpoint can be accessed without authentication if the repository is publicly accessible. This command provides essentially the same features as the &#x60;git ls-tree&#x60; command. Use &#x60;with_last_commit&#x60; to include the last commit that changed each entry. &#x60;with_last_commit&#x60; cannot be combined with &#x60;recursive&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - 404 Project Not Found
     * @param id ID or URL-encoded path of the project.
     * @param ref Name of a repository branch or tag. If omitted, uses the default branch.
     * @param path Path inside the repository, used to get the contents of subdirectories.
     * @param recursive If &#x60;true&#x60;, returns a recursive tree.
     * @param withLastCommit If &#x60;true&#x60;, includes the last commit for each tree entry. Cannot be combined with &#x60;recursive&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234455) in GitLab 19.3.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param pagination Pagination method. &#x60;keyset&#x60; uses the [keyset-based pagination method](https://docs.gitlab.com/api/rest/#keyset-based-pagination). &#x60;none&#x60; is valid only if &#x60;recursive&#x60; is &#x60;true&#x60;.
     * @param pageToken Tree record ID at which to fetch the next page. Used only with keyset pagination.
     * @return List&lt;APIEntitiesTreeObject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesTreeObject> getApiV4ProjectsIdRepositoryTree(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean recursive, @jakarta.annotation.Nullable Boolean withLastCommit, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String pagination, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTreeObject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryTreeRequestCreation(id, ref, path, recursive, withLastCommit, page, perPage, pagination, pageToken).body(localVarReturnType);
    }

    /**
     * List all repository trees in a project
     * Lists all repository files and directories in a specified project. This endpoint can be accessed without authentication if the repository is publicly accessible. This command provides essentially the same features as the &#x60;git ls-tree&#x60; command. Use &#x60;with_last_commit&#x60; to include the last commit that changed each entry. &#x60;with_last_commit&#x60; cannot be combined with &#x60;recursive&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - 404 Project Not Found
     * @param id ID or URL-encoded path of the project.
     * @param ref Name of a repository branch or tag. If omitted, uses the default branch.
     * @param path Path inside the repository, used to get the contents of subdirectories.
     * @param recursive If &#x60;true&#x60;, returns a recursive tree.
     * @param withLastCommit If &#x60;true&#x60;, includes the last commit for each tree entry. Cannot be combined with &#x60;recursive&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234455) in GitLab 19.3.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param pagination Pagination method. &#x60;keyset&#x60; uses the [keyset-based pagination method](https://docs.gitlab.com/api/rest/#keyset-based-pagination). &#x60;none&#x60; is valid only if &#x60;recursive&#x60; is &#x60;true&#x60;.
     * @param pageToken Tree record ID at which to fetch the next page. Used only with keyset pagination.
     * @return ResponseEntity&lt;List&lt;APIEntitiesTreeObject&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesTreeObject>> getApiV4ProjectsIdRepositoryTreeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean recursive, @jakarta.annotation.Nullable Boolean withLastCommit, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String pagination, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTreeObject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryTreeRequestCreation(id, ref, path, recursive, withLastCommit, page, perPage, pagination, pageToken).toEntity(localVarReturnType);
    }

    /**
     * List all repository trees in a project
     * Lists all repository files and directories in a specified project. This endpoint can be accessed without authentication if the repository is publicly accessible. This command provides essentially the same features as the &#x60;git ls-tree&#x60; command. Use &#x60;with_last_commit&#x60; to include the last commit that changed each entry. &#x60;with_last_commit&#x60; cannot be combined with &#x60;recursive&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - 404 Project Not Found
     * @param id ID or URL-encoded path of the project.
     * @param ref Name of a repository branch or tag. If omitted, uses the default branch.
     * @param path Path inside the repository, used to get the contents of subdirectories.
     * @param recursive If &#x60;true&#x60;, returns a recursive tree.
     * @param withLastCommit If &#x60;true&#x60;, includes the last commit for each tree entry. Cannot be combined with &#x60;recursive&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234455) in GitLab 19.3.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param pagination Pagination method. &#x60;keyset&#x60; uses the [keyset-based pagination method](https://docs.gitlab.com/api/rest/#keyset-based-pagination). &#x60;none&#x60; is valid only if &#x60;recursive&#x60; is &#x60;true&#x60;.
     * @param pageToken Tree record ID at which to fetch the next page. Used only with keyset pagination.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryTreeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Boolean recursive, @jakarta.annotation.Nullable Boolean withLastCommit, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String pagination, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryTreeRequestCreation(id, ref, path, recursive, withLastCommit, page, perPage, pagination, pageToken);
    }

    /**
     * Get contents of multiple files in a single request
     * Blob content is Base64 encoded. Each blob is truncated to the first 1 MB; the &#x60;truncated&#x60; field indicates when this happens. &#x60;size&#x60; is the full blob size in bytes. Files that do not exist at the requested reference are omitted from the response. This endpoint requires authentication. It is rate limited per user and project, to 5 requests per minute by default, set by the &#x60;project_repositories_blobs_batch_limit&#x60; application setting. This endpoint is behind the &#x60;repository_blobs_batch_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryBlobsBatchRequest The postApiV4ProjectsIdRepositoryBlobsBatchRequest parameter
     * @return List&lt;APIEntitiesBatchBlob&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRepositoryBlobsBatchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryBlobsBatchRequest postApiV4ProjectsIdRepositoryBlobsBatchRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRepositoryBlobsBatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdRepositoryBlobsBatchRequest' is set
        if (postApiV4ProjectsIdRepositoryBlobsBatchRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdRepositoryBlobsBatchRequest' when calling postApiV4ProjectsIdRepositoryBlobsBatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesBatchBlob>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/blobs/batch", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdRepositoryBlobsBatchRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get contents of multiple files in a single request
     * Blob content is Base64 encoded. Each blob is truncated to the first 1 MB; the &#x60;truncated&#x60; field indicates when this happens. &#x60;size&#x60; is the full blob size in bytes. Files that do not exist at the requested reference are omitted from the response. This endpoint requires authentication. It is rate limited per user and project, to 5 requests per minute by default, set by the &#x60;project_repositories_blobs_batch_limit&#x60; application setting. This endpoint is behind the &#x60;repository_blobs_batch_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryBlobsBatchRequest The postApiV4ProjectsIdRepositoryBlobsBatchRequest parameter
     * @return List&lt;APIEntitiesBatchBlob&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBatchBlob> postApiV4ProjectsIdRepositoryBlobsBatch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryBlobsBatchRequest postApiV4ProjectsIdRepositoryBlobsBatchRequest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBatchBlob>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryBlobsBatchRequestCreation(id, postApiV4ProjectsIdRepositoryBlobsBatchRequest).body(localVarReturnType);
    }

    /**
     * Get contents of multiple files in a single request
     * Blob content is Base64 encoded. Each blob is truncated to the first 1 MB; the &#x60;truncated&#x60; field indicates when this happens. &#x60;size&#x60; is the full blob size in bytes. Files that do not exist at the requested reference are omitted from the response. This endpoint requires authentication. It is rate limited per user and project, to 5 requests per minute by default, set by the &#x60;project_repositories_blobs_batch_limit&#x60; application setting. This endpoint is behind the &#x60;repository_blobs_batch_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryBlobsBatchRequest The postApiV4ProjectsIdRepositoryBlobsBatchRequest parameter
     * @return ResponseEntity&lt;List&lt;APIEntitiesBatchBlob&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBatchBlob>> postApiV4ProjectsIdRepositoryBlobsBatchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryBlobsBatchRequest postApiV4ProjectsIdRepositoryBlobsBatchRequest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBatchBlob>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryBlobsBatchRequestCreation(id, postApiV4ProjectsIdRepositoryBlobsBatchRequest).toEntity(localVarReturnType);
    }

    /**
     * Get contents of multiple files in a single request
     * Blob content is Base64 encoded. Each blob is truncated to the first 1 MB; the &#x60;truncated&#x60; field indicates when this happens. &#x60;size&#x60; is the full blob size in bytes. Files that do not exist at the requested reference are omitted from the response. This endpoint requires authentication. It is rate limited per user and project, to 5 requests per minute by default, set by the &#x60;project_repositories_blobs_batch_limit&#x60; application setting. This endpoint is behind the &#x60;repository_blobs_batch_api&#x60; feature flag, disabled by default. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryBlobsBatchRequest The postApiV4ProjectsIdRepositoryBlobsBatchRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRepositoryBlobsBatchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryBlobsBatchRequest postApiV4ProjectsIdRepositoryBlobsBatchRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdRepositoryBlobsBatchRequestCreation(id, postApiV4ProjectsIdRepositoryBlobsBatchRequest);
    }

    /**
     * Add changelog data to file
     * Generates changelog data based on commits in a repository and commits it to a changelog file. Given a semantic version and a range of commits, GitLab generates a changelog for all commits that use a specific Git trailer, and adds a new Markdown section to the changelog file. The output format can be customized. Parsing the changelog configuration is limited to 2 seconds. If the request times out, reduce the size of the changelog configuration file.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryChangelogRequest The postApiV4ProjectsIdRepositoryChangelogRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRepositoryChangelogRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdRepositoryChangelogRequest postApiV4ProjectsIdRepositoryChangelogRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRepositoryChangelog", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/changelog", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdRepositoryChangelogRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add changelog data to file
     * Generates changelog data based on commits in a repository and commits it to a changelog file. Given a semantic version and a range of commits, GitLab generates a changelog for all commits that use a specific Git trailer, and adds a new Markdown section to the changelog file. The output format can be customized. Parsing the changelog configuration is limited to 2 seconds. If the request times out, reduce the size of the changelog configuration file.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryChangelogRequest The postApiV4ProjectsIdRepositoryChangelogRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdRepositoryChangelog(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdRepositoryChangelogRequest postApiV4ProjectsIdRepositoryChangelogRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdRepositoryChangelogRequestCreation(id, postApiV4ProjectsIdRepositoryChangelogRequest).body(localVarReturnType);
    }

    /**
     * Add changelog data to file
     * Generates changelog data based on commits in a repository and commits it to a changelog file. Given a semantic version and a range of commits, GitLab generates a changelog for all commits that use a specific Git trailer, and adds a new Markdown section to the changelog file. The output format can be customized. Parsing the changelog configuration is limited to 2 seconds. If the request times out, reduce the size of the changelog configuration file.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryChangelogRequest The postApiV4ProjectsIdRepositoryChangelogRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdRepositoryChangelogWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdRepositoryChangelogRequest postApiV4ProjectsIdRepositoryChangelogRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryChangelogRequestCreation(id, postApiV4ProjectsIdRepositoryChangelogRequest).toEntity(localVarReturnType);
    }

    /**
     * Add changelog data to file
     * Generates changelog data based on commits in a repository and commits it to a changelog file. Given a semantic version and a range of commits, GitLab generates a changelog for all commits that use a specific Git trailer, and adds a new Markdown section to the changelog file. The output format can be customized. Parsing the changelog configuration is limited to 2 seconds. If the request times out, reduce the size of the changelog configuration file.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryChangelogRequest The postApiV4ProjectsIdRepositoryChangelogRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRepositoryChangelogWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdRepositoryChangelogRequest postApiV4ProjectsIdRepositoryChangelogRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdRepositoryChangelogRequestCreation(id, postApiV4ProjectsIdRepositoryChangelogRequest);
    }
}
