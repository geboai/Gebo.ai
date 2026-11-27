package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBranch;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdRepositoryBranchesRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdRepositoryBranchesBranchProtectRequest;

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
public class BranchesApi {
    private ApiClient apiClient;

    public BranchesApi() {
        this(new ApiClient());
    }

    public BranchesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a repository branch
     * Deletes a specified branch from the repository. If an error occurs, the response includes a message that explains it. Deleting a branch does not completely erase all related data. Some information persists to maintain project history and to support recovery processes.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Branch Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch URL-encoded name of the branch. Cannot be the default branch. Only users with the Maintainer or Owner role can delete a protected branch.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdRepositoryBranchesBranchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdRepositoryBranchesBranch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'branch' is set
        if (branch == null) {
            throw new RestClientResponseException("Missing the required parameter 'branch' when calling deleteApiV4ProjectsIdRepositoryBranchesBranch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("branch", branch);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/branches/{branch}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a repository branch
     * Deletes a specified branch from the repository. If an error occurs, the response includes a message that explains it. Deleting a branch does not completely erase all related data. Some information persists to maintain project history and to support recovery processes.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Branch Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch URL-encoded name of the branch. Cannot be the default branch. Only users with the Maintainer or Owner role can delete a protected branch.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdRepositoryBranchesBranch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdRepositoryBranchesBranchRequestCreation(id, branch).body(localVarReturnType);
    }

    /**
     * Delete a repository branch
     * Deletes a specified branch from the repository. If an error occurs, the response includes a message that explains it. Deleting a branch does not completely erase all related data. Some information persists to maintain project history and to support recovery processes.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Branch Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch URL-encoded name of the branch. Cannot be the default branch. Only users with the Maintainer or Owner role can delete a protected branch.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdRepositoryBranchesBranchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdRepositoryBranchesBranchRequestCreation(id, branch).toEntity(localVarReturnType);
    }

    /**
     * Delete a repository branch
     * Deletes a specified branch from the repository. If an error occurs, the response includes a message that explains it. Deleting a branch does not completely erase all related data. Some information persists to maintain project history and to support recovery processes.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Branch Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch URL-encoded name of the branch. Cannot be the default branch. Only users with the Maintainer or Owner role can delete a protected branch.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdRepositoryBranchesBranchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        return deleteApiV4ProjectsIdRepositoryBranchesBranchRequestCreation(id, branch);
    }

    /**
     * Delete all merged branches
     * Deletes all branches that are merged into the default branch for a project. Protected branches are not deleted.
     * <p><b>202</b> - 202 Accepted
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>429</b> - 429 Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdRepositoryMergedBranchesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdRepositoryMergedBranches", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/merged_branches", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete all merged branches
     * Deletes all branches that are merged into the default branch for a project. Protected branches are not deleted.
     * <p><b>202</b> - 202 Accepted
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>429</b> - 429 Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdRepositoryMergedBranches(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdRepositoryMergedBranchesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete all merged branches
     * Deletes all branches that are merged into the default branch for a project. Protected branches are not deleted.
     * <p><b>202</b> - 202 Accepted
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>429</b> - 429 Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdRepositoryMergedBranchesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdRepositoryMergedBranchesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete all merged branches
     * Deletes all branches that are merged into the default branch for a project. Protected branches are not deleted.
     * <p><b>202</b> - 202 Accepted
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>429</b> - 429 Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdRepositoryMergedBranchesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return deleteApiV4ProjectsIdRepositoryMergedBranchesRequestCreation(id);
    }

    /**
     * List all repository branches
     * Lists all repository branches from a specified project, sorted alphabetically by name. Search by name, or use regular expressions to find specific branch patterns. Returns detailed information about the branch, including its protection status, merge status, and commit details. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Return branches containing the search string. Use &#x60;^term&#x60; to find branches that begin with &#x60;term&#x60;, and &#x60;term$&#x60; to find branches that end with &#x60;term&#x60;.
     * @param regex Return branches with names matching an [RE2](https://github.com/google/re2/wiki/Syntax) regular expression. Cannot be used together with &#x60;search&#x60;.
     * @param sort Sort results by the specified field and direction.
     * @param pageToken Token for the next page of results, from the &#x60;Link&#x60; header of the previous response. Do not create or edit this token. Used for keyset pagination.
     * @return List&lt;APIEntitiesBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryBranchesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String regex, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryBranches", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "regex", regex));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page_token", pageToken));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesBranch>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/branches", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all repository branches
     * Lists all repository branches from a specified project, sorted alphabetically by name. Search by name, or use regular expressions to find specific branch patterns. Returns detailed information about the branch, including its protection status, merge status, and commit details. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Return branches containing the search string. Use &#x60;^term&#x60; to find branches that begin with &#x60;term&#x60;, and &#x60;term$&#x60; to find branches that end with &#x60;term&#x60;.
     * @param regex Return branches with names matching an [RE2](https://github.com/google/re2/wiki/Syntax) regular expression. Cannot be used together with &#x60;search&#x60;.
     * @param sort Sort results by the specified field and direction.
     * @param pageToken Token for the next page of results, from the &#x60;Link&#x60; header of the previous response. Do not create or edit this token. Used for keyset pagination.
     * @return List&lt;APIEntitiesBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBranch> getApiV4ProjectsIdRepositoryBranches(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String regex, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBranch>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryBranchesRequestCreation(id, page, perPage, search, regex, sort, pageToken).body(localVarReturnType);
    }

    /**
     * List all repository branches
     * Lists all repository branches from a specified project, sorted alphabetically by name. Search by name, or use regular expressions to find specific branch patterns. Returns detailed information about the branch, including its protection status, merge status, and commit details. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Return branches containing the search string. Use &#x60;^term&#x60; to find branches that begin with &#x60;term&#x60;, and &#x60;term$&#x60; to find branches that end with &#x60;term&#x60;.
     * @param regex Return branches with names matching an [RE2](https://github.com/google/re2/wiki/Syntax) regular expression. Cannot be used together with &#x60;search&#x60;.
     * @param sort Sort results by the specified field and direction.
     * @param pageToken Token for the next page of results, from the &#x60;Link&#x60; header of the previous response. Do not create or edit this token. Used for keyset pagination.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBranch&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBranch>> getApiV4ProjectsIdRepositoryBranchesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String regex, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBranch>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryBranchesRequestCreation(id, page, perPage, search, regex, sort, pageToken).toEntity(localVarReturnType);
    }

    /**
     * List all repository branches
     * Lists all repository branches from a specified project, sorted alphabetically by name. Search by name, or use regular expressions to find specific branch patterns. Returns detailed information about the branch, including its protection status, merge status, and commit details. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Return branches containing the search string. Use &#x60;^term&#x60; to find branches that begin with &#x60;term&#x60;, and &#x60;term$&#x60; to find branches that end with &#x60;term&#x60;.
     * @param regex Return branches with names matching an [RE2](https://github.com/google/re2/wiki/Syntax) regular expression. Cannot be used together with &#x60;search&#x60;.
     * @param sort Sort results by the specified field and direction.
     * @param pageToken Token for the next page of results, from the &#x60;Link&#x60; header of the previous response. Do not create or edit this token. Used for keyset pagination.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryBranchesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String regex, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryBranchesRequestCreation(id, page, perPage, search, regex, sort, pageToken);
    }

    /**
     * Retrieve a repository branch
     * Retrieves a specified project repository branch. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Project Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch URL-encoded name of the branch.
     * @return APIEntitiesBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryBranchesBranchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryBranchesBranch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'branch' is set
        if (branch == null) {
            throw new RestClientResponseException("Missing the required parameter 'branch' when calling getApiV4ProjectsIdRepositoryBranchesBranch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("branch", branch);

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

        ParameterizedTypeReference<APIEntitiesBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/branches/{branch}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a repository branch
     * Retrieves a specified project repository branch. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Project Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch URL-encoded name of the branch.
     * @return APIEntitiesBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBranch getApiV4ProjectsIdRepositoryBranchesBranch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryBranchesBranchRequestCreation(id, branch).body(localVarReturnType);
    }

    /**
     * Retrieve a repository branch
     * Retrieves a specified project repository branch. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Project Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch URL-encoded name of the branch.
     * @return ResponseEntity&lt;APIEntitiesBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBranch> getApiV4ProjectsIdRepositoryBranchesBranchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryBranchesBranchRequestCreation(id, branch).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a repository branch
     * Retrieves a specified project repository branch. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Project Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch URL-encoded name of the branch.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryBranchesBranchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryBranchesBranchRequestCreation(id, branch);
    }

    /**
     * Check if a branch exists
     * Checks whether a branch exists in the repository, without returning the branch.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch URL-encoded name of the branch.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec headApiV4ProjectsIdRepositoryBranchesBranchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling headApiV4ProjectsIdRepositoryBranchesBranch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'branch' is set
        if (branch == null) {
            throw new RestClientResponseException("Missing the required parameter 'branch' when calling headApiV4ProjectsIdRepositoryBranchesBranch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("branch", branch);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/branches/{branch}", HttpMethod.HEAD, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Check if a branch exists
     * Checks whether a branch exists in the repository, without returning the branch.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch URL-encoded name of the branch.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void headApiV4ProjectsIdRepositoryBranchesBranch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        headApiV4ProjectsIdRepositoryBranchesBranchRequestCreation(id, branch).body(localVarReturnType);
    }

    /**
     * Check if a branch exists
     * Checks whether a branch exists in the repository, without returning the branch.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch URL-encoded name of the branch.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> headApiV4ProjectsIdRepositoryBranchesBranchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return headApiV4ProjectsIdRepositoryBranchesBranchRequestCreation(id, branch).toEntity(localVarReturnType);
    }

    /**
     * Check if a branch exists
     * Checks whether a branch exists in the repository, without returning the branch.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch URL-encoded name of the branch.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec headApiV4ProjectsIdRepositoryBranchesBranchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        return headApiV4ProjectsIdRepositoryBranchesBranchRequestCreation(id, branch);
    }

    /**
     * Create a repository branch
     * Creates a branch in the repository.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Branch already exists
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryBranchesRequest The postApiV4ProjectsIdRepositoryBranchesRequest parameter
     * @return APIEntitiesBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRepositoryBranchesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryBranchesRequest postApiV4ProjectsIdRepositoryBranchesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRepositoryBranches", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdRepositoryBranchesRequest' is set
        if (postApiV4ProjectsIdRepositoryBranchesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdRepositoryBranchesRequest' when calling postApiV4ProjectsIdRepositoryBranches", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/branches", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdRepositoryBranchesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a repository branch
     * Creates a branch in the repository.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Branch already exists
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryBranchesRequest The postApiV4ProjectsIdRepositoryBranchesRequest parameter
     * @return APIEntitiesBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBranch postApiV4ProjectsIdRepositoryBranches(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryBranchesRequest postApiV4ProjectsIdRepositoryBranchesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryBranchesRequestCreation(id, postApiV4ProjectsIdRepositoryBranchesRequest).body(localVarReturnType);
    }

    /**
     * Create a repository branch
     * Creates a branch in the repository.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Branch already exists
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryBranchesRequest The postApiV4ProjectsIdRepositoryBranchesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBranch> postApiV4ProjectsIdRepositoryBranchesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryBranchesRequest postApiV4ProjectsIdRepositoryBranchesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryBranchesRequestCreation(id, postApiV4ProjectsIdRepositoryBranchesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a repository branch
     * Creates a branch in the repository.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Branch already exists
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryBranchesRequest The postApiV4ProjectsIdRepositoryBranchesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRepositoryBranchesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryBranchesRequest postApiV4ProjectsIdRepositoryBranchesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdRepositoryBranchesRequestCreation(id, postApiV4ProjectsIdRepositoryBranchesRequest);
    }

    /**
     * Protect a single branch
     * Protects a single branch, or updates its protection if it is already protected. Only users with the Maintainer role can push or merge unless &#x60;developers_can_push&#x60; or &#x60;developers_can_merge&#x60; is &#x60;true&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Branch Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch Name of the branch.
     * @param putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest The putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest parameter
     * @return APIEntitiesBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdRepositoryBranchesBranchProtectRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch, @jakarta.annotation.Nullable PutApiV4ProjectsIdRepositoryBranchesBranchProtectRequest putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdRepositoryBranchesBranchProtect", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'branch' is set
        if (branch == null) {
            throw new RestClientResponseException("Missing the required parameter 'branch' when calling putApiV4ProjectsIdRepositoryBranchesBranchProtect", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("branch", branch);

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

        ParameterizedTypeReference<APIEntitiesBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/branches/{branch}/protect", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Protect a single branch
     * Protects a single branch, or updates its protection if it is already protected. Only users with the Maintainer role can push or merge unless &#x60;developers_can_push&#x60; or &#x60;developers_can_merge&#x60; is &#x60;true&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Branch Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch Name of the branch.
     * @param putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest The putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest parameter
     * @return APIEntitiesBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBranch putApiV4ProjectsIdRepositoryBranchesBranchProtect(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch, @jakarta.annotation.Nullable PutApiV4ProjectsIdRepositoryBranchesBranchProtectRequest putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdRepositoryBranchesBranchProtectRequestCreation(id, branch, putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest).body(localVarReturnType);
    }

    /**
     * Protect a single branch
     * Protects a single branch, or updates its protection if it is already protected. Only users with the Maintainer role can push or merge unless &#x60;developers_can_push&#x60; or &#x60;developers_can_merge&#x60; is &#x60;true&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Branch Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch Name of the branch.
     * @param putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest The putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBranch> putApiV4ProjectsIdRepositoryBranchesBranchProtectWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch, @jakarta.annotation.Nullable PutApiV4ProjectsIdRepositoryBranchesBranchProtectRequest putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdRepositoryBranchesBranchProtectRequestCreation(id, branch, putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest).toEntity(localVarReturnType);
    }

    /**
     * Protect a single branch
     * Protects a single branch, or updates its protection if it is already protected. Only users with the Maintainer role can push or merge unless &#x60;developers_can_push&#x60; or &#x60;developers_can_merge&#x60; is &#x60;true&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Branch Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch Name of the branch.
     * @param putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest The putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdRepositoryBranchesBranchProtectWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch, @jakarta.annotation.Nullable PutApiV4ProjectsIdRepositoryBranchesBranchProtectRequest putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdRepositoryBranchesBranchProtectRequestCreation(id, branch, putApiV4ProjectsIdRepositoryBranchesBranchProtectRequest);
    }

    /**
     * Unprotect a single branch
     * Removes the protection from a single protected branch.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Branch Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch Name of the branch.
     * @return APIEntitiesBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdRepositoryBranchesBranchUnprotectRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdRepositoryBranchesBranchUnprotect", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'branch' is set
        if (branch == null) {
            throw new RestClientResponseException("Missing the required parameter 'branch' when calling putApiV4ProjectsIdRepositoryBranchesBranchUnprotect", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("branch", branch);

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

        ParameterizedTypeReference<APIEntitiesBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/branches/{branch}/unprotect", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unprotect a single branch
     * Removes the protection from a single protected branch.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Branch Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch Name of the branch.
     * @return APIEntitiesBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBranch putApiV4ProjectsIdRepositoryBranchesBranchUnprotect(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdRepositoryBranchesBranchUnprotectRequestCreation(id, branch).body(localVarReturnType);
    }

    /**
     * Unprotect a single branch
     * Removes the protection from a single protected branch.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Branch Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch Name of the branch.
     * @return ResponseEntity&lt;APIEntitiesBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBranch> putApiV4ProjectsIdRepositoryBranchesBranchUnprotectWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdRepositoryBranchesBranchUnprotectRequestCreation(id, branch).toEntity(localVarReturnType);
    }

    /**
     * Unprotect a single branch
     * Removes the protection from a single protected branch.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Branch Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param branch Name of the branch.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdRepositoryBranchesBranchUnprotectWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String branch) throws RestClientResponseException {
        return putApiV4ProjectsIdRepositoryBranchesBranchUnprotectRequestCreation(id, branch);
    }
}
