package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesExternalStatusCheck;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestsStatusCheck;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestsStatusCheckResponse;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdExternalStatusChecksRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdExternalStatusChecksCheckIdRequest;

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
public class ExternalStatusChecksApi {
    private ApiClient apiClient;

    public ExternalStatusChecksApi() {
        this(new ApiClient());
    }

    public ExternalStatusChecksApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete external status check service
     * Deletes a specified external status check service for a project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param checkId ID of the external status check.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdExternalStatusChecksCheckIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer checkId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdExternalStatusChecksCheckId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'checkId' is set
        if (checkId == null) {
            throw new RestClientResponseException("Missing the required parameter 'checkId' when calling deleteApiV4ProjectsIdExternalStatusChecksCheckId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("check_id", checkId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/external_status_checks/{check_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete external status check service
     * Deletes a specified external status check service for a project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param checkId ID of the external status check.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdExternalStatusChecksCheckId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer checkId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdExternalStatusChecksCheckIdRequestCreation(id, checkId).body(localVarReturnType);
    }

    /**
     * Delete external status check service
     * Deletes a specified external status check service for a project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param checkId ID of the external status check.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdExternalStatusChecksCheckIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer checkId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdExternalStatusChecksCheckIdRequestCreation(id, checkId).toEntity(localVarReturnType);
    }

    /**
     * Delete external status check service
     * Deletes a specified external status check service for a project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param checkId ID of the external status check.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdExternalStatusChecksCheckIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer checkId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdExternalStatusChecksCheckIdRequestCreation(id, checkId);
    }

    /**
     * Retrieve project external status check services
     * Retrieves information on external status check services for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesExternalStatusCheck&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdExternalStatusChecksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdExternalStatusChecks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesExternalStatusCheck>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/external_status_checks", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve project external status check services
     * Retrieves information on external status check services for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesExternalStatusCheck&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesExternalStatusCheck> getApiV4ProjectsIdExternalStatusChecks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesExternalStatusCheck>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdExternalStatusChecksRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * Retrieve project external status check services
     * Retrieves information on external status check services for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesExternalStatusCheck&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesExternalStatusCheck>> getApiV4ProjectsIdExternalStatusChecksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesExternalStatusCheck>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdExternalStatusChecksRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Retrieve project external status check services
     * Retrieves information on external status check services for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdExternalStatusChecksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdExternalStatusChecksRequestCreation(id, page, perPage);
    }

    /**
     * List all status checks for a merge request
     * Lists all external status check services for a specified merge request and their status.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return List&lt;APIEntitiesMergeRequestsStatusCheck&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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

        ParameterizedTypeReference<List<APIEntitiesMergeRequestsStatusCheck>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/status_checks", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all status checks for a merge request
     * Lists all external status check services for a specified merge request and their status.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return List&lt;APIEntitiesMergeRequestsStatusCheck&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeRequestsStatusCheck> getApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestsStatusCheck>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * List all status checks for a merge request
     * Lists all external status check services for a specified merge request and their status.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeRequestsStatusCheck&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeRequestsStatusCheck>> getApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestsStatusCheck>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * List all status checks for a merge request
     * Lists all external status check services for a specified merge request and their status.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksRequestCreation(id, mergeRequestIid);
    }

    /**
     * Create external status check service
     * Creates an external status check service for a specified project. External status checks send information about all applicable merge requests to the defined external service, including confidential merge requests.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdExternalStatusChecksRequest The postApiV4ProjectsIdExternalStatusChecksRequest parameter
     * @return APIEntitiesExternalStatusCheck
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdExternalStatusChecksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdExternalStatusChecksRequest postApiV4ProjectsIdExternalStatusChecksRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdExternalStatusChecks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdExternalStatusChecksRequest' is set
        if (postApiV4ProjectsIdExternalStatusChecksRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdExternalStatusChecksRequest' when calling postApiV4ProjectsIdExternalStatusChecks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesExternalStatusCheck> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/external_status_checks", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdExternalStatusChecksRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create external status check service
     * Creates an external status check service for a specified project. External status checks send information about all applicable merge requests to the defined external service, including confidential merge requests.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdExternalStatusChecksRequest The postApiV4ProjectsIdExternalStatusChecksRequest parameter
     * @return APIEntitiesExternalStatusCheck
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesExternalStatusCheck postApiV4ProjectsIdExternalStatusChecks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdExternalStatusChecksRequest postApiV4ProjectsIdExternalStatusChecksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesExternalStatusCheck> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdExternalStatusChecksRequestCreation(id, postApiV4ProjectsIdExternalStatusChecksRequest).body(localVarReturnType);
    }

    /**
     * Create external status check service
     * Creates an external status check service for a specified project. External status checks send information about all applicable merge requests to the defined external service, including confidential merge requests.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdExternalStatusChecksRequest The postApiV4ProjectsIdExternalStatusChecksRequest parameter
     * @return ResponseEntity&lt;APIEntitiesExternalStatusCheck&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesExternalStatusCheck> postApiV4ProjectsIdExternalStatusChecksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdExternalStatusChecksRequest postApiV4ProjectsIdExternalStatusChecksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesExternalStatusCheck> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdExternalStatusChecksRequestCreation(id, postApiV4ProjectsIdExternalStatusChecksRequest).toEntity(localVarReturnType);
    }

    /**
     * Create external status check service
     * Creates an external status check service for a specified project. External status checks send information about all applicable merge requests to the defined external service, including confidential merge requests.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdExternalStatusChecksRequest The postApiV4ProjectsIdExternalStatusChecksRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdExternalStatusChecksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdExternalStatusChecksRequest postApiV4ProjectsIdExternalStatusChecksRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdExternalStatusChecksRequestCreation(id, postApiV4ProjectsIdExternalStatusChecksRequest);
    }

    /**
     * Update status of an external status check
     * Updates the status of an external status check for a specified merge request, informing GitLab that a merge request has passed a check by an external service. To set the status of an external check, the personal access token used must belong to a user with the Developer, Maintainer, or Owner role on the target project of the merge request. Any user with permission to approve the merge request can use this operation.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest parameter
     * @return APIEntitiesMergeRequestsStatusCheckResponse
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponses", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponses", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest' is set
        if (postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponses", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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

        ParameterizedTypeReference<APIEntitiesMergeRequestsStatusCheckResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/status_check_responses", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update status of an external status check
     * Updates the status of an external status check for a specified merge request, informing GitLab that a merge request has passed a check by an external service. To set the status of an external check, the personal access token used must belong to a user with the Developer, Maintainer, or Owner role on the target project of the merge request. Any user with permission to approve the merge request can use this operation.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest parameter
     * @return APIEntitiesMergeRequestsStatusCheckResponse
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequestsStatusCheckResponse postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponses(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestsStatusCheckResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest).body(localVarReturnType);
    }

    /**
     * Update status of an external status check
     * Updates the status of an external status check for a specified merge request, informing GitLab that a merge request has passed a check by an external service. To set the status of an external check, the personal access token used must belong to a user with the Developer, Maintainer, or Owner role on the target project of the merge request. Any user with permission to approve the merge request can use this operation.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMergeRequestsStatusCheckResponse&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequestsStatusCheckResponse> postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestsStatusCheckResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest).toEntity(localVarReturnType);
    }

    /**
     * Update status of an external status check
     * Updates the status of an external status check for a specified merge request, informing GitLab that a merge request has passed a check by an external service. To set the status of an external check, the personal access token used must belong to a user with the Developer, Maintainer, or Owner role on the target project of the merge request. Any user with permission to approve the merge request can use this operation.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusCheckResponsesRequest);
    }

    /**
     * Retry failed status check for a merge request
     * Retries a specified failed external status check for a merge request. Even though the merge request has not changed, this endpoint resends the current state of merge request to the defined external service.
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param externalStatusCheckId ID of the failed external status check.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksExternalStatusCheckIdRetryRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer externalStatusCheckId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksExternalStatusCheckIdRetry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksExternalStatusCheckIdRetry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'externalStatusCheckId' is set
        if (externalStatusCheckId == null) {
            throw new RestClientResponseException("Missing the required parameter 'externalStatusCheckId' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksExternalStatusCheckIdRetry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);
        pathParams.put("external_status_check_id", externalStatusCheckId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/status_checks/{external_status_check_id}/retry", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retry failed status check for a merge request
     * Retries a specified failed external status check for a merge request. Even though the merge request has not changed, this endpoint resends the current state of merge request to the defined external service.
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param externalStatusCheckId ID of the failed external status check.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksExternalStatusCheckIdRetry(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer externalStatusCheckId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksExternalStatusCheckIdRetryRequestCreation(id, mergeRequestIid, externalStatusCheckId).body(localVarReturnType);
    }

    /**
     * Retry failed status check for a merge request
     * Retries a specified failed external status check for a merge request. Even though the merge request has not changed, this endpoint resends the current state of merge request to the defined external service.
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param externalStatusCheckId ID of the failed external status check.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksExternalStatusCheckIdRetryWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer externalStatusCheckId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksExternalStatusCheckIdRetryRequestCreation(id, mergeRequestIid, externalStatusCheckId).toEntity(localVarReturnType);
    }

    /**
     * Retry failed status check for a merge request
     * Retries a specified failed external status check for a merge request. Even though the merge request has not changed, this endpoint resends the current state of merge request to the defined external service.
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param externalStatusCheckId ID of the failed external status check.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksExternalStatusCheckIdRetryWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer externalStatusCheckId) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidStatusChecksExternalStatusCheckIdRetryRequestCreation(id, mergeRequestIid, externalStatusCheckId);
    }

    /**
     * Update external status check service
     * Updates an existing external status check for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param checkId ID of the external status check.
     * @param putApiV4ProjectsIdExternalStatusChecksCheckIdRequest The putApiV4ProjectsIdExternalStatusChecksCheckIdRequest parameter
     * @return APIEntitiesExternalStatusCheck
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdExternalStatusChecksCheckIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer checkId, @jakarta.annotation.Nullable PutApiV4ProjectsIdExternalStatusChecksCheckIdRequest putApiV4ProjectsIdExternalStatusChecksCheckIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdExternalStatusChecksCheckId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'checkId' is set
        if (checkId == null) {
            throw new RestClientResponseException("Missing the required parameter 'checkId' when calling putApiV4ProjectsIdExternalStatusChecksCheckId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("check_id", checkId);

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

        ParameterizedTypeReference<APIEntitiesExternalStatusCheck> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/external_status_checks/{check_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdExternalStatusChecksCheckIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update external status check service
     * Updates an existing external status check for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param checkId ID of the external status check.
     * @param putApiV4ProjectsIdExternalStatusChecksCheckIdRequest The putApiV4ProjectsIdExternalStatusChecksCheckIdRequest parameter
     * @return APIEntitiesExternalStatusCheck
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesExternalStatusCheck putApiV4ProjectsIdExternalStatusChecksCheckId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer checkId, @jakarta.annotation.Nullable PutApiV4ProjectsIdExternalStatusChecksCheckIdRequest putApiV4ProjectsIdExternalStatusChecksCheckIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesExternalStatusCheck> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdExternalStatusChecksCheckIdRequestCreation(id, checkId, putApiV4ProjectsIdExternalStatusChecksCheckIdRequest).body(localVarReturnType);
    }

    /**
     * Update external status check service
     * Updates an existing external status check for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param checkId ID of the external status check.
     * @param putApiV4ProjectsIdExternalStatusChecksCheckIdRequest The putApiV4ProjectsIdExternalStatusChecksCheckIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesExternalStatusCheck&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesExternalStatusCheck> putApiV4ProjectsIdExternalStatusChecksCheckIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer checkId, @jakarta.annotation.Nullable PutApiV4ProjectsIdExternalStatusChecksCheckIdRequest putApiV4ProjectsIdExternalStatusChecksCheckIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesExternalStatusCheck> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdExternalStatusChecksCheckIdRequestCreation(id, checkId, putApiV4ProjectsIdExternalStatusChecksCheckIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update external status check service
     * Updates an existing external status check for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param checkId ID of the external status check.
     * @param putApiV4ProjectsIdExternalStatusChecksCheckIdRequest The putApiV4ProjectsIdExternalStatusChecksCheckIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdExternalStatusChecksCheckIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer checkId, @jakarta.annotation.Nullable PutApiV4ProjectsIdExternalStatusChecksCheckIdRequest putApiV4ProjectsIdExternalStatusChecksCheckIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdExternalStatusChecksCheckIdRequestCreation(id, checkId, putApiV4ProjectsIdExternalStatusChecksCheckIdRequest);
    }
}
