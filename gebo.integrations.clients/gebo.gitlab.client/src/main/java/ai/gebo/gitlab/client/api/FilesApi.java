package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBlameRange;
import java.io.File;
import ai.gebo.gitlab.client.model.HeadApiV4ProjectsIdRepositoryFilesFilePathBlameRequest;
import ai.gebo.gitlab.client.model.HeadApiV4ProjectsIdRepositoryFilesFilePathRequest;

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
public class FilesApi {
    private ApiClient apiClient;

    public FilesApi() {
        this(new ApiClient());
    }

    public FilesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a file in a repository
     * Deletes a specified file in a repository. Use the Commits API to delete multiple files with a single request.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param branch Name of the branch to commit the deletion into. Combine with &#x60;start_branch&#x60; to create a new branch.
     * @param commitMessage Commit message.
     * @param startBranch Name of the base branch to create the branch from.
     * @param authorEmail Email address of the commit author.
     * @param authorName Name of the commit author.
     * @param lastCommitId Last known file commit ID.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String branch, @jakarta.annotation.Nonnull String commitMessage, @jakarta.annotation.Nullable String startBranch, @jakarta.annotation.Nullable String authorEmail, @jakarta.annotation.Nullable String authorName, @jakarta.annotation.Nullable String lastCommitId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'filePath' is set
        if (filePath == null) {
            throw new RestClientResponseException("Missing the required parameter 'filePath' when calling deleteApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'branch' is set
        if (branch == null) {
            throw new RestClientResponseException("Missing the required parameter 'branch' when calling deleteApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'commitMessage' is set
        if (commitMessage == null) {
            throw new RestClientResponseException("Missing the required parameter 'commitMessage' when calling deleteApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_path", filePath);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "branch", branch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "commit_message", commitMessage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start_branch", startBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_email", authorEmail));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_name", authorName));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "last_commit_id", lastCommitId));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/files/{file_path}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a file in a repository
     * Deletes a specified file in a repository. Use the Commits API to delete multiple files with a single request.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param branch Name of the branch to commit the deletion into. Combine with &#x60;start_branch&#x60; to create a new branch.
     * @param commitMessage Commit message.
     * @param startBranch Name of the base branch to create the branch from.
     * @param authorEmail Email address of the commit author.
     * @param authorName Name of the commit author.
     * @param lastCommitId Last known file commit ID.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdRepositoryFilesFilePath(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String branch, @jakarta.annotation.Nonnull String commitMessage, @jakarta.annotation.Nullable String startBranch, @jakarta.annotation.Nullable String authorEmail, @jakarta.annotation.Nullable String authorName, @jakarta.annotation.Nullable String lastCommitId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(id, filePath, branch, commitMessage, startBranch, authorEmail, authorName, lastCommitId).body(localVarReturnType);
    }

    /**
     * Delete a file in a repository
     * Deletes a specified file in a repository. Use the Commits API to delete multiple files with a single request.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param branch Name of the branch to commit the deletion into. Combine with &#x60;start_branch&#x60; to create a new branch.
     * @param commitMessage Commit message.
     * @param startBranch Name of the base branch to create the branch from.
     * @param authorEmail Email address of the commit author.
     * @param authorName Name of the commit author.
     * @param lastCommitId Last known file commit ID.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdRepositoryFilesFilePathWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String branch, @jakarta.annotation.Nonnull String commitMessage, @jakarta.annotation.Nullable String startBranch, @jakarta.annotation.Nullable String authorEmail, @jakarta.annotation.Nullable String authorName, @jakarta.annotation.Nullable String lastCommitId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(id, filePath, branch, commitMessage, startBranch, authorEmail, authorName, lastCommitId).toEntity(localVarReturnType);
    }

    /**
     * Delete a file in a repository
     * Deletes a specified file in a repository. Use the Commits API to delete multiple files with a single request.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param branch Name of the branch to commit the deletion into. Combine with &#x60;start_branch&#x60; to create a new branch.
     * @param commitMessage Commit message.
     * @param startBranch Name of the base branch to create the branch from.
     * @param authorEmail Email address of the commit author.
     * @param authorName Name of the commit author.
     * @param lastCommitId Last known file commit ID.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdRepositoryFilesFilePathWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String branch, @jakarta.annotation.Nonnull String commitMessage, @jakarta.annotation.Nullable String startBranch, @jakarta.annotation.Nullable String authorEmail, @jakarta.annotation.Nullable String authorName, @jakarta.annotation.Nullable String lastCommitId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(id, filePath, branch, commitMessage, startBranch, authorEmail, authorName, lastCommitId);
    }

    /**
     * Retrieve a file from a repository
     * Retrieves information about a specified file in a repository. This includes information like the name, size, and the file contents. File content is Base64 encoded. You can access this endpoint without authentication if the repository is publicly accessible. For blobs larger than 10 MB, this endpoint has a rate limit of 5 requests every minute for each project. For text files not stored as valid UTF-8 in Git, &#x60;content&#x60; is converted to UTF-8 before Base64 encoding. In that case, &#x60;size&#x60; is the raw blob size, which might differ from the length of the decoded content, and &#x60;content_sha256&#x60; is the hash of the converted content, not the raw blob. To retrieve the exact bytes stored in Git, use the raw file endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param ref Commit SHA or name of a repository branch or tag. Use &#x60;HEAD&#x60; to use the default branch.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String ref) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'filePath' is set
        if (filePath == null) {
            throw new RestClientResponseException("Missing the required parameter 'filePath' when calling getApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'ref' is set
        if (ref == null) {
            throw new RestClientResponseException("Missing the required parameter 'ref' when calling getApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_path", filePath);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ref", ref));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/files/{file_path}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a file from a repository
     * Retrieves information about a specified file in a repository. This includes information like the name, size, and the file contents. File content is Base64 encoded. You can access this endpoint without authentication if the repository is publicly accessible. For blobs larger than 10 MB, this endpoint has a rate limit of 5 requests every minute for each project. For text files not stored as valid UTF-8 in Git, &#x60;content&#x60; is converted to UTF-8 before Base64 encoding. In that case, &#x60;size&#x60; is the raw blob size, which might differ from the length of the decoded content, and &#x60;content_sha256&#x60; is the hash of the converted content, not the raw blob. To retrieve the exact bytes stored in Git, use the raw file endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param ref Commit SHA or name of a repository branch or tag. Use &#x60;HEAD&#x60; to use the default branch.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdRepositoryFilesFilePath(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String ref) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(id, filePath, ref).body(localVarReturnType);
    }

    /**
     * Retrieve a file from a repository
     * Retrieves information about a specified file in a repository. This includes information like the name, size, and the file contents. File content is Base64 encoded. You can access this endpoint without authentication if the repository is publicly accessible. For blobs larger than 10 MB, this endpoint has a rate limit of 5 requests every minute for each project. For text files not stored as valid UTF-8 in Git, &#x60;content&#x60; is converted to UTF-8 before Base64 encoding. In that case, &#x60;size&#x60; is the raw blob size, which might differ from the length of the decoded content, and &#x60;content_sha256&#x60; is the hash of the converted content, not the raw blob. To retrieve the exact bytes stored in Git, use the raw file endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param ref Commit SHA or name of a repository branch or tag. Use &#x60;HEAD&#x60; to use the default branch.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdRepositoryFilesFilePathWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String ref) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(id, filePath, ref).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a file from a repository
     * Retrieves information about a specified file in a repository. This includes information like the name, size, and the file contents. File content is Base64 encoded. You can access this endpoint without authentication if the repository is publicly accessible. For blobs larger than 10 MB, this endpoint has a rate limit of 5 requests every minute for each project. For text files not stored as valid UTF-8 in Git, &#x60;content&#x60; is converted to UTF-8 before Base64 encoding. In that case, &#x60;size&#x60; is the raw blob size, which might differ from the length of the decoded content, and &#x60;content_sha256&#x60; is the hash of the converted content, not the raw blob. To retrieve the exact bytes stored in Git, use the raw file endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param ref Commit SHA or name of a repository branch or tag. Use &#x60;HEAD&#x60; to use the default branch.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryFilesFilePathWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String ref) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(id, filePath, ref);
    }

    /**
     * Retrieve file blame history from a repository
     * Retrieves blame history for a specified file in a repository. Each blame range contains lines and their corresponding commit information. This endpoint is rate-limited for each authenticated user for a given project, and for each IP address for unauthenticated requests. On GitLab Self-Managed and GitLab Dedicated, an administrator can change these limits in the application settings. The default limits are 400 requests every minute for each user and project, set by &#x60;project_repositories_blame_limit&#x60;, and 400 requests every minute for each IP address, set by &#x60;project_repositories_blame_unauthenticated_limit&#x60;. A limit of &#x60;0&#x60; disables it. &#x60;HEAD&#x60; requests do not count toward these limits. The GraphQL &#x60;blame&#x60; field on &#x60;RepositoryBlob&#x60; shares these limits.
     * <p><b>200</b> - OK
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param ref Commit SHA or name of a repository branch or tag. Use &#x60;HEAD&#x60; to use the default branch.
     * @param rangeStart First line number of the range to blame.
     * @param rangeEnd Last line number of the range to blame.
     * @param range Range of lines to return blame information for.
     * @return List&lt;APIEntitiesBlameRange&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryFilesFilePathBlameRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull Integer rangeStart, @jakarta.annotation.Nonnull Integer rangeEnd, @jakarta.annotation.Nullable Object range) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryFilesFilePathBlame", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'filePath' is set
        if (filePath == null) {
            throw new RestClientResponseException("Missing the required parameter 'filePath' when calling getApiV4ProjectsIdRepositoryFilesFilePathBlame", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'ref' is set
        if (ref == null) {
            throw new RestClientResponseException("Missing the required parameter 'ref' when calling getApiV4ProjectsIdRepositoryFilesFilePathBlame", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'rangeStart' is set
        if (rangeStart == null) {
            throw new RestClientResponseException("Missing the required parameter 'rangeStart' when calling getApiV4ProjectsIdRepositoryFilesFilePathBlame", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'rangeEnd' is set
        if (rangeEnd == null) {
            throw new RestClientResponseException("Missing the required parameter 'rangeEnd' when calling getApiV4ProjectsIdRepositoryFilesFilePathBlame", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_path", filePath);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ref", ref));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "range", range));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "range[start]", rangeStart));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "range[end]", rangeEnd));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesBlameRange>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/files/{file_path}/blame", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve file blame history from a repository
     * Retrieves blame history for a specified file in a repository. Each blame range contains lines and their corresponding commit information. This endpoint is rate-limited for each authenticated user for a given project, and for each IP address for unauthenticated requests. On GitLab Self-Managed and GitLab Dedicated, an administrator can change these limits in the application settings. The default limits are 400 requests every minute for each user and project, set by &#x60;project_repositories_blame_limit&#x60;, and 400 requests every minute for each IP address, set by &#x60;project_repositories_blame_unauthenticated_limit&#x60;. A limit of &#x60;0&#x60; disables it. &#x60;HEAD&#x60; requests do not count toward these limits. The GraphQL &#x60;blame&#x60; field on &#x60;RepositoryBlob&#x60; shares these limits.
     * <p><b>200</b> - OK
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param ref Commit SHA or name of a repository branch or tag. Use &#x60;HEAD&#x60; to use the default branch.
     * @param rangeStart First line number of the range to blame.
     * @param rangeEnd Last line number of the range to blame.
     * @param range Range of lines to return blame information for.
     * @return List&lt;APIEntitiesBlameRange&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBlameRange> getApiV4ProjectsIdRepositoryFilesFilePathBlame(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull Integer rangeStart, @jakarta.annotation.Nonnull Integer rangeEnd, @jakarta.annotation.Nullable Object range) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBlameRange>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryFilesFilePathBlameRequestCreation(id, filePath, ref, rangeStart, rangeEnd, range).body(localVarReturnType);
    }

    /**
     * Retrieve file blame history from a repository
     * Retrieves blame history for a specified file in a repository. Each blame range contains lines and their corresponding commit information. This endpoint is rate-limited for each authenticated user for a given project, and for each IP address for unauthenticated requests. On GitLab Self-Managed and GitLab Dedicated, an administrator can change these limits in the application settings. The default limits are 400 requests every minute for each user and project, set by &#x60;project_repositories_blame_limit&#x60;, and 400 requests every minute for each IP address, set by &#x60;project_repositories_blame_unauthenticated_limit&#x60;. A limit of &#x60;0&#x60; disables it. &#x60;HEAD&#x60; requests do not count toward these limits. The GraphQL &#x60;blame&#x60; field on &#x60;RepositoryBlob&#x60; shares these limits.
     * <p><b>200</b> - OK
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param ref Commit SHA or name of a repository branch or tag. Use &#x60;HEAD&#x60; to use the default branch.
     * @param rangeStart First line number of the range to blame.
     * @param rangeEnd Last line number of the range to blame.
     * @param range Range of lines to return blame information for.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBlameRange&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBlameRange>> getApiV4ProjectsIdRepositoryFilesFilePathBlameWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull Integer rangeStart, @jakarta.annotation.Nonnull Integer rangeEnd, @jakarta.annotation.Nullable Object range) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBlameRange>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryFilesFilePathBlameRequestCreation(id, filePath, ref, rangeStart, rangeEnd, range).toEntity(localVarReturnType);
    }

    /**
     * Retrieve file blame history from a repository
     * Retrieves blame history for a specified file in a repository. Each blame range contains lines and their corresponding commit information. This endpoint is rate-limited for each authenticated user for a given project, and for each IP address for unauthenticated requests. On GitLab Self-Managed and GitLab Dedicated, an administrator can change these limits in the application settings. The default limits are 400 requests every minute for each user and project, set by &#x60;project_repositories_blame_limit&#x60;, and 400 requests every minute for each IP address, set by &#x60;project_repositories_blame_unauthenticated_limit&#x60;. A limit of &#x60;0&#x60; disables it. &#x60;HEAD&#x60; requests do not count toward these limits. The GraphQL &#x60;blame&#x60; field on &#x60;RepositoryBlob&#x60; shares these limits.
     * <p><b>200</b> - OK
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param ref Commit SHA or name of a repository branch or tag. Use &#x60;HEAD&#x60; to use the default branch.
     * @param rangeStart First line number of the range to blame.
     * @param rangeEnd Last line number of the range to blame.
     * @param range Range of lines to return blame information for.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryFilesFilePathBlameWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull Integer rangeStart, @jakarta.annotation.Nonnull Integer rangeEnd, @jakarta.annotation.Nullable Object range) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryFilesFilePathBlameRequestCreation(id, filePath, ref, rangeStart, rangeEnd, range);
    }

    /**
     * Retrieve a raw file from a repository
     * Retrieves the raw file contents for a specified file in a repository. To get only the file metadata, use the &#x60;HEAD&#x60; method on the file endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param ref Commit SHA or name of a repository branch or tag. Default is the &#x60;HEAD&#x60; of the project.
     * @param lfs If &#x60;true&#x60;, returns the Git LFS file contents instead of the pointer. Ignored if the file isn&#39;t tracked by Git LFS.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryFilesFilePathRawRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable Boolean lfs) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryFilesFilePathRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'filePath' is set
        if (filePath == null) {
            throw new RestClientResponseException("Missing the required parameter 'filePath' when calling getApiV4ProjectsIdRepositoryFilesFilePathRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_path", filePath);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ref", ref));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "lfs", lfs));

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/files/{file_path}/raw", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a raw file from a repository
     * Retrieves the raw file contents for a specified file in a repository. To get only the file metadata, use the &#x60;HEAD&#x60; method on the file endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param ref Commit SHA or name of a repository branch or tag. Default is the &#x60;HEAD&#x60; of the project.
     * @param lfs If &#x60;true&#x60;, returns the Git LFS file contents instead of the pointer. Ignored if the file isn&#39;t tracked by Git LFS.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4ProjectsIdRepositoryFilesFilePathRaw(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable Boolean lfs) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryFilesFilePathRawRequestCreation(id, filePath, ref, lfs).body(localVarReturnType);
    }

    /**
     * Retrieve a raw file from a repository
     * Retrieves the raw file contents for a specified file in a repository. To get only the file metadata, use the &#x60;HEAD&#x60; method on the file endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param ref Commit SHA or name of a repository branch or tag. Default is the &#x60;HEAD&#x60; of the project.
     * @param lfs If &#x60;true&#x60;, returns the Git LFS file contents instead of the pointer. Ignored if the file isn&#39;t tracked by Git LFS.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4ProjectsIdRepositoryFilesFilePathRawWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable Boolean lfs) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryFilesFilePathRawRequestCreation(id, filePath, ref, lfs).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a raw file from a repository
     * Retrieves the raw file contents for a specified file in a repository. To get only the file metadata, use the &#x60;HEAD&#x60; method on the file endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param ref Commit SHA or name of a repository branch or tag. Default is the &#x60;HEAD&#x60; of the project.
     * @param lfs If &#x60;true&#x60;, returns the Git LFS file contents instead of the pointer. Ignored if the file isn&#39;t tracked by Git LFS.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryFilesFilePathRawWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable Boolean lfs) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryFilesFilePathRawRequestCreation(id, filePath, ref, lfs);
    }

    /**
     * Retrieve file metadata
     * Retrieves metadata for a specified file in a repository. Uses the &#x60;HEAD&#x60; method and returns the metadata in &#x60;X-Gitlab-*&#x60; response headers, such as &#x60;X-Gitlab-Blob-Id&#x60;, &#x60;X-Gitlab-Commit-Id&#x60;, &#x60;X-Gitlab-Content-Sha256&#x60;, &#x60;X-Gitlab-File-Name&#x60;, &#x60;X-Gitlab-Last-Commit-Id&#x60;, &#x60;X-Gitlab-Ref&#x60;, and &#x60;X-Gitlab-Size&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param headApiV4ProjectsIdRepositoryFilesFilePathRequest The headApiV4ProjectsIdRepositoryFilesFilePathRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec headApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull HeadApiV4ProjectsIdRepositoryFilesFilePathRequest headApiV4ProjectsIdRepositoryFilesFilePathRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling headApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'filePath' is set
        if (filePath == null) {
            throw new RestClientResponseException("Missing the required parameter 'filePath' when calling headApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'headApiV4ProjectsIdRepositoryFilesFilePathRequest' is set
        if (headApiV4ProjectsIdRepositoryFilesFilePathRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'headApiV4ProjectsIdRepositoryFilesFilePathRequest' when calling headApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_path", filePath);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/files/{file_path}", HttpMethod.HEAD, pathParams, localVarQueryParams, headApiV4ProjectsIdRepositoryFilesFilePathRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve file metadata
     * Retrieves metadata for a specified file in a repository. Uses the &#x60;HEAD&#x60; method and returns the metadata in &#x60;X-Gitlab-*&#x60; response headers, such as &#x60;X-Gitlab-Blob-Id&#x60;, &#x60;X-Gitlab-Commit-Id&#x60;, &#x60;X-Gitlab-Content-Sha256&#x60;, &#x60;X-Gitlab-File-Name&#x60;, &#x60;X-Gitlab-Last-Commit-Id&#x60;, &#x60;X-Gitlab-Ref&#x60;, and &#x60;X-Gitlab-Size&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param headApiV4ProjectsIdRepositoryFilesFilePathRequest The headApiV4ProjectsIdRepositoryFilesFilePathRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void headApiV4ProjectsIdRepositoryFilesFilePath(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull HeadApiV4ProjectsIdRepositoryFilesFilePathRequest headApiV4ProjectsIdRepositoryFilesFilePathRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        headApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(id, filePath, headApiV4ProjectsIdRepositoryFilesFilePathRequest).body(localVarReturnType);
    }

    /**
     * Retrieve file metadata
     * Retrieves metadata for a specified file in a repository. Uses the &#x60;HEAD&#x60; method and returns the metadata in &#x60;X-Gitlab-*&#x60; response headers, such as &#x60;X-Gitlab-Blob-Id&#x60;, &#x60;X-Gitlab-Commit-Id&#x60;, &#x60;X-Gitlab-Content-Sha256&#x60;, &#x60;X-Gitlab-File-Name&#x60;, &#x60;X-Gitlab-Last-Commit-Id&#x60;, &#x60;X-Gitlab-Ref&#x60;, and &#x60;X-Gitlab-Size&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param headApiV4ProjectsIdRepositoryFilesFilePathRequest The headApiV4ProjectsIdRepositoryFilesFilePathRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> headApiV4ProjectsIdRepositoryFilesFilePathWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull HeadApiV4ProjectsIdRepositoryFilesFilePathRequest headApiV4ProjectsIdRepositoryFilesFilePathRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return headApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(id, filePath, headApiV4ProjectsIdRepositoryFilesFilePathRequest).toEntity(localVarReturnType);
    }

    /**
     * Retrieve file metadata
     * Retrieves metadata for a specified file in a repository. Uses the &#x60;HEAD&#x60; method and returns the metadata in &#x60;X-Gitlab-*&#x60; response headers, such as &#x60;X-Gitlab-Blob-Id&#x60;, &#x60;X-Gitlab-Commit-Id&#x60;, &#x60;X-Gitlab-Content-Sha256&#x60;, &#x60;X-Gitlab-File-Name&#x60;, &#x60;X-Gitlab-Last-Commit-Id&#x60;, &#x60;X-Gitlab-Ref&#x60;, and &#x60;X-Gitlab-Size&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param headApiV4ProjectsIdRepositoryFilesFilePathRequest The headApiV4ProjectsIdRepositoryFilesFilePathRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec headApiV4ProjectsIdRepositoryFilesFilePathWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull HeadApiV4ProjectsIdRepositoryFilesFilePathRequest headApiV4ProjectsIdRepositoryFilesFilePathRequest) throws RestClientResponseException {
        return headApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(id, filePath, headApiV4ProjectsIdRepositoryFilesFilePathRequest);
    }

    /**
     * Retrieve file blame metadata
     * Retrieves blame metadata for lines in a specified file. Uses the &#x60;HEAD&#x60; method and returns the metadata in &#x60;X-Gitlab-*&#x60; response headers, such as &#x60;X-Gitlab-Blob-Id&#x60;, &#x60;X-Gitlab-Commit-Id&#x60;, &#x60;X-Gitlab-Content-Sha256&#x60;, &#x60;X-Gitlab-File-Name&#x60;, &#x60;X-Gitlab-Last-Commit-Id&#x60;, &#x60;X-Gitlab-Ref&#x60;, and &#x60;X-Gitlab-Size&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest The headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec headApiV4ProjectsIdRepositoryFilesFilePathBlameRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull HeadApiV4ProjectsIdRepositoryFilesFilePathBlameRequest headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling headApiV4ProjectsIdRepositoryFilesFilePathBlame", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'filePath' is set
        if (filePath == null) {
            throw new RestClientResponseException("Missing the required parameter 'filePath' when calling headApiV4ProjectsIdRepositoryFilesFilePathBlame", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest' is set
        if (headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest' when calling headApiV4ProjectsIdRepositoryFilesFilePathBlame", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_path", filePath);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/files/{file_path}/blame", HttpMethod.HEAD, pathParams, localVarQueryParams, headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve file blame metadata
     * Retrieves blame metadata for lines in a specified file. Uses the &#x60;HEAD&#x60; method and returns the metadata in &#x60;X-Gitlab-*&#x60; response headers, such as &#x60;X-Gitlab-Blob-Id&#x60;, &#x60;X-Gitlab-Commit-Id&#x60;, &#x60;X-Gitlab-Content-Sha256&#x60;, &#x60;X-Gitlab-File-Name&#x60;, &#x60;X-Gitlab-Last-Commit-Id&#x60;, &#x60;X-Gitlab-Ref&#x60;, and &#x60;X-Gitlab-Size&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest The headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void headApiV4ProjectsIdRepositoryFilesFilePathBlame(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull HeadApiV4ProjectsIdRepositoryFilesFilePathBlameRequest headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        headApiV4ProjectsIdRepositoryFilesFilePathBlameRequestCreation(id, filePath, headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest).body(localVarReturnType);
    }

    /**
     * Retrieve file blame metadata
     * Retrieves blame metadata for lines in a specified file. Uses the &#x60;HEAD&#x60; method and returns the metadata in &#x60;X-Gitlab-*&#x60; response headers, such as &#x60;X-Gitlab-Blob-Id&#x60;, &#x60;X-Gitlab-Commit-Id&#x60;, &#x60;X-Gitlab-Content-Sha256&#x60;, &#x60;X-Gitlab-File-Name&#x60;, &#x60;X-Gitlab-Last-Commit-Id&#x60;, &#x60;X-Gitlab-Ref&#x60;, and &#x60;X-Gitlab-Size&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest The headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> headApiV4ProjectsIdRepositoryFilesFilePathBlameWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull HeadApiV4ProjectsIdRepositoryFilesFilePathBlameRequest headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return headApiV4ProjectsIdRepositoryFilesFilePathBlameRequestCreation(id, filePath, headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest).toEntity(localVarReturnType);
    }

    /**
     * Retrieve file blame metadata
     * Retrieves blame metadata for lines in a specified file. Uses the &#x60;HEAD&#x60; method and returns the metadata in &#x60;X-Gitlab-*&#x60; response headers, such as &#x60;X-Gitlab-Blob-Id&#x60;, &#x60;X-Gitlab-Commit-Id&#x60;, &#x60;X-Gitlab-Content-Sha256&#x60;, &#x60;X-Gitlab-File-Name&#x60;, &#x60;X-Gitlab-Last-Commit-Id&#x60;, &#x60;X-Gitlab-Ref&#x60;, and &#x60;X-Gitlab-Size&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest The headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec headApiV4ProjectsIdRepositoryFilesFilePathBlameWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull HeadApiV4ProjectsIdRepositoryFilesFilePathBlameRequest headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest) throws RestClientResponseException {
        return headApiV4ProjectsIdRepositoryFilesFilePathBlameRequestCreation(id, filePath, headApiV4ProjectsIdRepositoryFilesFilePathBlameRequest);
    }

    /**
     * Create a file in a repository
     * Creates a file in a specified repository. Use the Commits API to create multiple files with a single request. Requests larger than 300 MB by default are rejected. Requests larger than 20 MB are rate-limited to 3 requests every 30 seconds for each user.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param _file The file content to be created (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'filePath' is set
        if (filePath == null) {
            throw new RestClientResponseException("Missing the required parameter 'filePath' when calling postApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_path", filePath);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/files/{file_path}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a file in a repository
     * Creates a file in a specified repository. Use the Commits API to create multiple files with a single request. Requests larger than 300 MB by default are rejected. Requests larger than 20 MB are rate-limited to 3 requests every 30 seconds for each user.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param _file The file content to be created (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdRepositoryFilesFilePath(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(id, filePath, _file).body(localVarReturnType);
    }

    /**
     * Create a file in a repository
     * Creates a file in a specified repository. Use the Commits API to create multiple files with a single request. Requests larger than 300 MB by default are rejected. Requests larger than 20 MB are rate-limited to 3 requests every 30 seconds for each user.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param _file The file content to be created (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdRepositoryFilesFilePathWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(id, filePath, _file).toEntity(localVarReturnType);
    }

    /**
     * Create a file in a repository
     * Creates a file in a specified repository. Use the Commits API to create multiple files with a single request. Requests larger than 300 MB by default are rejected. Requests larger than 20 MB are rate-limited to 3 requests every 30 seconds for each user.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param _file The file content to be created (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRepositoryFilesFilePathWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return postApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(id, filePath, _file);
    }

    /**
     * Update a file in a repository
     * Updates a specified file in a repository. Use the Commits API to update multiple files with a single request. Requests larger than 300 MB by default are rejected. Requests larger than 20 MB are rate-limited to 3 requests every 30 seconds for each user. If the commit fails, returns &#x60;400 Bad Request&#x60; with an error message. Possible causes are a &#x60;file_path&#x60; that contains &#x60;/../&#x60;, new file contents that are identical to the current contents, or a &#x60;git push&#x60; to the branch while the edit was in progress.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param _file The file content to be updated (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'filePath' is set
        if (filePath == null) {
            throw new RestClientResponseException("Missing the required parameter 'filePath' when calling putApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling putApiV4ProjectsIdRepositoryFilesFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_path", filePath);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/files/{file_path}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a file in a repository
     * Updates a specified file in a repository. Use the Commits API to update multiple files with a single request. Requests larger than 300 MB by default are rejected. Requests larger than 20 MB are rate-limited to 3 requests every 30 seconds for each user. If the commit fails, returns &#x60;400 Bad Request&#x60; with an error message. Possible causes are a &#x60;file_path&#x60; that contains &#x60;/../&#x60;, new file contents that are identical to the current contents, or a &#x60;git push&#x60; to the branch while the edit was in progress.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param _file The file content to be updated (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdRepositoryFilesFilePath(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(id, filePath, _file).body(localVarReturnType);
    }

    /**
     * Update a file in a repository
     * Updates a specified file in a repository. Use the Commits API to update multiple files with a single request. Requests larger than 300 MB by default are rejected. Requests larger than 20 MB are rate-limited to 3 requests every 30 seconds for each user. If the commit fails, returns &#x60;400 Bad Request&#x60; with an error message. Possible causes are a &#x60;file_path&#x60; that contains &#x60;/../&#x60;, new file contents that are identical to the current contents, or a &#x60;git push&#x60; to the branch while the edit was in progress.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param _file The file content to be updated (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdRepositoryFilesFilePathWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(id, filePath, _file).toEntity(localVarReturnType);
    }

    /**
     * Update a file in a repository
     * Updates a specified file in a repository. Use the Commits API to update multiple files with a single request. Requests larger than 300 MB by default are rejected. Requests larger than 20 MB are rate-limited to 3 requests every 30 seconds for each user. If the commit fails, returns &#x60;400 Bad Request&#x60; with an error message. Possible causes are a &#x60;file_path&#x60; that contains &#x60;/../&#x60;, new file contents that are identical to the current contents, or a &#x60;git push&#x60; to the branch while the edit was in progress.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filePath URL-encoded full path to the file, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param _file The file content to be updated (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdRepositoryFilesFilePathWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return putApiV4ProjectsIdRepositoryFilesFilePathRequestCreation(id, filePath, _file);
    }
}
