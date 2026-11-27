package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.RepositoryBranchCollectionModel;
import ai.gebo.openproject.client.model.RepositoryBranchModel;
import ai.gebo.openproject.client.model.RepositoryCommitCollectionModel;
import ai.gebo.openproject.client.model.RepositoryCommitModel;
import ai.gebo.openproject.client.model.RepositoryIssueCollectionModel;
import ai.gebo.openproject.client.model.RepositoryIssueModel;
import ai.gebo.openproject.client.model.RepositoryMergeRequestCollectionModel;
import ai.gebo.openproject.client.model.RepositoryMergeRequestModel;
import ai.gebo.openproject.client.model.RepositoryPipelineCollectionModel;
import ai.gebo.openproject.client.model.RepositoryPipelineModel;

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
     * View repository branch
     * Returns the repository branch with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the branch does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on the work package linked to the branch)
     * @param id Repository branch id
     * @return RepositoryBranchModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getRepositoryBranchRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getRepositoryBranch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RepositoryBranchModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/repository_branches/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View repository branch
     * Returns the repository branch with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the branch does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on the work package linked to the branch)
     * @param id Repository branch id
     * @return RepositoryBranchModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RepositoryBranchModel getRepositoryBranch(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryBranchModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRepositoryBranchRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View repository branch
     * Returns the repository branch with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the branch does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on the work package linked to the branch)
     * @param id Repository branch id
     * @return ResponseEntity&lt;RepositoryBranchModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RepositoryBranchModel> getRepositoryBranchWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryBranchModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRepositoryBranchRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View repository branch
     * Returns the repository branch with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the branch does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on the work package linked to the branch)
     * @param id Repository branch id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getRepositoryBranchWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getRepositoryBranchRequestCreation(id);
    }

    /**
     * View repository commit
     * Returns the repository commit with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the commit does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the commit)
     * @param id Repository commit id
     * @return RepositoryCommitModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getRepositoryCommitRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getRepositoryCommit", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RepositoryCommitModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/repository_commits/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View repository commit
     * Returns the repository commit with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the commit does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the commit)
     * @param id Repository commit id
     * @return RepositoryCommitModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RepositoryCommitModel getRepositoryCommit(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryCommitModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRepositoryCommitRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View repository commit
     * Returns the repository commit with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the commit does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the commit)
     * @param id Repository commit id
     * @return ResponseEntity&lt;RepositoryCommitModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RepositoryCommitModel> getRepositoryCommitWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryCommitModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRepositoryCommitRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View repository commit
     * Returns the repository commit with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the commit does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the commit)
     * @param id Repository commit id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getRepositoryCommitWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getRepositoryCommitRequestCreation(id);
    }

    /**
     * View repository issue
     * Returns the repository issue with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the issue does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the issue)
     * @param id Repository issue id
     * @return RepositoryIssueModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getRepositoryIssueRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getRepositoryIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RepositoryIssueModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/repository_issues/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View repository issue
     * Returns the repository issue with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the issue does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the issue)
     * @param id Repository issue id
     * @return RepositoryIssueModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RepositoryIssueModel getRepositoryIssue(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryIssueModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRepositoryIssueRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View repository issue
     * Returns the repository issue with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the issue does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the issue)
     * @param id Repository issue id
     * @return ResponseEntity&lt;RepositoryIssueModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RepositoryIssueModel> getRepositoryIssueWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryIssueModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRepositoryIssueRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View repository issue
     * Returns the repository issue with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the issue does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the issue)
     * @param id Repository issue id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getRepositoryIssueWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getRepositoryIssueRequestCreation(id);
    }

    /**
     * View repository merge request
     * Returns the repository merge request with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the merge request does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the merge request)
     * @param id Repository merge request id
     * @return RepositoryMergeRequestModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getRepositoryMergeRequestRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getRepositoryMergeRequest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RepositoryMergeRequestModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/repository_merge_requests/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View repository merge request
     * Returns the repository merge request with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the merge request does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the merge request)
     * @param id Repository merge request id
     * @return RepositoryMergeRequestModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RepositoryMergeRequestModel getRepositoryMergeRequest(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryMergeRequestModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRepositoryMergeRequestRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View repository merge request
     * Returns the repository merge request with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the merge request does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the merge request)
     * @param id Repository merge request id
     * @return ResponseEntity&lt;RepositoryMergeRequestModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RepositoryMergeRequestModel> getRepositoryMergeRequestWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryMergeRequestModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRepositoryMergeRequestRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View repository merge request
     * Returns the repository merge request with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the merge request does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the merge request)
     * @param id Repository merge request id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getRepositoryMergeRequestWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getRepositoryMergeRequestRequestCreation(id);
    }

    /**
     * View repository pipeline
     * Returns the repository pipeline with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the pipeline does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the pipeline&#39;s merge request)
     * @param id Repository pipeline id
     * @return RepositoryPipelineModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getRepositoryPipelineRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getRepositoryPipeline", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RepositoryPipelineModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/repository_pipelines/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View repository pipeline
     * Returns the repository pipeline with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the pipeline does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the pipeline&#39;s merge request)
     * @param id Repository pipeline id
     * @return RepositoryPipelineModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RepositoryPipelineModel getRepositoryPipeline(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryPipelineModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRepositoryPipelineRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View repository pipeline
     * Returns the repository pipeline with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the pipeline does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the pipeline&#39;s merge request)
     * @param id Repository pipeline id
     * @return ResponseEntity&lt;RepositoryPipelineModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RepositoryPipelineModel> getRepositoryPipelineWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryPipelineModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRepositoryPipelineRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View repository pipeline
     * Returns the repository pipeline with the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the pipeline does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the pipeline&#39;s merge request)
     * @param id Repository pipeline id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getRepositoryPipelineWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getRepositoryPipelineRequestCreation(id);
    }

    /**
     * List pipelines of a repository merge request
     * Returns a paginated collection of the CI pipelines run for the given merge request, ordered from the oldest to the latest one. Pipelines that have not started yet come last.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of pipelines.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the merge request does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the merge request)
     * @param id Repository merge request id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryPipelineCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listRepositoryMergeRequestPipelinesRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listRepositoryMergeRequestPipelines", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RepositoryPipelineCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/repository_merge_requests/{id}/repository_pipelines", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List pipelines of a repository merge request
     * Returns a paginated collection of the CI pipelines run for the given merge request, ordered from the oldest to the latest one. Pipelines that have not started yet come last.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of pipelines.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the merge request does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the merge request)
     * @param id Repository merge request id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryPipelineCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RepositoryPipelineCollectionModel listRepositoryMergeRequestPipelines(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryPipelineCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRepositoryMergeRequestPipelinesRequestCreation(id, offset, pageSize).body(localVarReturnType);
    }

    /**
     * List pipelines of a repository merge request
     * Returns a paginated collection of the CI pipelines run for the given merge request, ordered from the oldest to the latest one. Pipelines that have not started yet come last.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of pipelines.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the merge request does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the merge request)
     * @param id Repository merge request id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseEntity&lt;RepositoryPipelineCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RepositoryPipelineCollectionModel> listRepositoryMergeRequestPipelinesWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryPipelineCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRepositoryMergeRequestPipelinesRequestCreation(id, offset, pageSize).toEntity(localVarReturnType);
    }

    /**
     * List pipelines of a repository merge request
     * Returns a paginated collection of the CI pipelines run for the given merge request, ordered from the oldest to the latest one. Pipelines that have not started yet come last.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of pipelines.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the merge request does not exist or the client does not have sufficient permissions to see it.  **Required permission:** show repository content (on at least one work package linked to the merge request)
     * @param id Repository merge request id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listRepositoryMergeRequestPipelinesWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        return listRepositoryMergeRequestPipelinesRequestCreation(id, offset, pageSize);
    }

    /**
     * List repository branches of a work package
     * Returns a paginated collection of the repository branches linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of branches.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryBranchCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageRepositoryBranchesRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageRepositoryBranches", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RepositoryBranchCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/repository_branches", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List repository branches of a work package
     * Returns a paginated collection of the repository branches linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of branches.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryBranchCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RepositoryBranchCollectionModel listWorkPackageRepositoryBranches(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryBranchCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryBranchesRequestCreation(id, offset, pageSize).body(localVarReturnType);
    }

    /**
     * List repository branches of a work package
     * Returns a paginated collection of the repository branches linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of branches.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseEntity&lt;RepositoryBranchCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RepositoryBranchCollectionModel> listWorkPackageRepositoryBranchesWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryBranchCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryBranchesRequestCreation(id, offset, pageSize).toEntity(localVarReturnType);
    }

    /**
     * List repository branches of a work package
     * Returns a paginated collection of the repository branches linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of branches.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageRepositoryBranchesWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        return listWorkPackageRepositoryBranchesRequestCreation(id, offset, pageSize);
    }

    /**
     * List repository commits of a work package
     * Returns a paginated collection of the repository commits linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of commits.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryCommitCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageRepositoryCommitsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageRepositoryCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RepositoryCommitCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/repository_commits", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List repository commits of a work package
     * Returns a paginated collection of the repository commits linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of commits.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryCommitCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RepositoryCommitCollectionModel listWorkPackageRepositoryCommits(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryCommitCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryCommitsRequestCreation(id, offset, pageSize).body(localVarReturnType);
    }

    /**
     * List repository commits of a work package
     * Returns a paginated collection of the repository commits linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of commits.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseEntity&lt;RepositoryCommitCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RepositoryCommitCollectionModel> listWorkPackageRepositoryCommitsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryCommitCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryCommitsRequestCreation(id, offset, pageSize).toEntity(localVarReturnType);
    }

    /**
     * List repository commits of a work package
     * Returns a paginated collection of the repository commits linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of commits.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageRepositoryCommitsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        return listWorkPackageRepositoryCommitsRequestCreation(id, offset, pageSize);
    }

    /**
     * List repository issues of a work package
     * Returns a paginated collection of the repository issues linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of issues.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryIssueCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageRepositoryIssuesRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageRepositoryIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RepositoryIssueCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/repository_issues", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List repository issues of a work package
     * Returns a paginated collection of the repository issues linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of issues.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryIssueCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RepositoryIssueCollectionModel listWorkPackageRepositoryIssues(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryIssueCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryIssuesRequestCreation(id, offset, pageSize).body(localVarReturnType);
    }

    /**
     * List repository issues of a work package
     * Returns a paginated collection of the repository issues linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of issues.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseEntity&lt;RepositoryIssueCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RepositoryIssueCollectionModel> listWorkPackageRepositoryIssuesWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryIssueCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryIssuesRequestCreation(id, offset, pageSize).toEntity(localVarReturnType);
    }

    /**
     * List repository issues of a work package
     * Returns a paginated collection of the repository issues linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of issues.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageRepositoryIssuesWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        return listWorkPackageRepositoryIssuesRequestCreation(id, offset, pageSize);
    }

    /**
     * List repository merge requests of a work package
     * Returns a paginated collection of the repository merge requests linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of merge requests.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryMergeRequestCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageRepositoryMergeRequestsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageRepositoryMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RepositoryMergeRequestCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/repository_merge_requests", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List repository merge requests of a work package
     * Returns a paginated collection of the repository merge requests linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of merge requests.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryMergeRequestCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RepositoryMergeRequestCollectionModel listWorkPackageRepositoryMergeRequests(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryMergeRequestCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryMergeRequestsRequestCreation(id, offset, pageSize).body(localVarReturnType);
    }

    /**
     * List repository merge requests of a work package
     * Returns a paginated collection of the repository merge requests linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of merge requests.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseEntity&lt;RepositoryMergeRequestCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RepositoryMergeRequestCollectionModel> listWorkPackageRepositoryMergeRequestsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryMergeRequestCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryMergeRequestsRequestCreation(id, offset, pageSize).toEntity(localVarReturnType);
    }

    /**
     * List repository merge requests of a work package
     * Returns a paginated collection of the repository merge requests linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of merge requests.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageRepositoryMergeRequestsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        return listWorkPackageRepositoryMergeRequestsRequestCreation(id, offset, pageSize);
    }
}
