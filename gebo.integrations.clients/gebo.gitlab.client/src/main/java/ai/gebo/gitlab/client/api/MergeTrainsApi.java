package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesMergeTrainsCar;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest;

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
public class MergeTrainsApi {
    private ApiClient apiClient;

    public MergeTrainsApi() {
        this(new ApiClient());
    }

    public MergeTrainsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all merge trains for a project
     * Lists all merge trains for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param scope Filter merge trains by scope. &#x60;active&#x60; returns merge trains to be merged. &#x60;complete&#x60; returns merge trains that have been merged.
     * @param sort Sort results in ascending or descending order by &#x60;id&#x60;. A lower &#x60;id&#x60; value means the merge request was added to the train earlier.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeTrainsCar&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeTrainsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeTrains", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
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

        ParameterizedTypeReference<List<APIEntitiesMergeTrainsCar>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_trains", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all merge trains for a project
     * Lists all merge trains for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param scope Filter merge trains by scope. &#x60;active&#x60; returns merge trains to be merged. &#x60;complete&#x60; returns merge trains that have been merged.
     * @param sort Sort results in ascending or descending order by &#x60;id&#x60;. A lower &#x60;id&#x60; value means the merge request was added to the train earlier.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeTrainsCar&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeTrainsCar> getApiV4ProjectsIdMergeTrains(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeTrainsCar>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeTrainsRequestCreation(id, scope, sort, page, perPage).body(localVarReturnType);
    }

    /**
     * List all merge trains for a project
     * Lists all merge trains for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param scope Filter merge trains by scope. &#x60;active&#x60; returns merge trains to be merged. &#x60;complete&#x60; returns merge trains that have been merged.
     * @param sort Sort results in ascending or descending order by &#x60;id&#x60;. A lower &#x60;id&#x60; value means the merge request was added to the train earlier.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeTrainsCar&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeTrainsCar>> getApiV4ProjectsIdMergeTrainsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeTrainsCar>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeTrainsRequestCreation(id, scope, sort, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all merge trains for a project
     * Lists all merge trains for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param scope Filter merge trains by scope. &#x60;active&#x60; returns merge trains to be merged. &#x60;complete&#x60; returns merge trains that have been merged.
     * @param sort Sort results in ascending or descending order by &#x60;id&#x60;. A lower &#x60;id&#x60; value means the merge request was added to the train earlier.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeTrainsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeTrainsRequestCreation(id, scope, sort, page, perPage);
    }

    /**
     * Retrieve merge train status
     * Retrieves the merge train status of a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return APIEntitiesMergeTrainsCar
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMergeTrainsCar> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_trains/merge_requests/{merge_request_iid}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve merge train status
     * Retrieves the merge train status of a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return APIEntitiesMergeTrainsCar
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeTrainsCar getApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIid(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeTrainsCar> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Retrieve merge train status
     * Retrieves the merge train status of a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseEntity&lt;APIEntitiesMergeTrainsCar&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeTrainsCar> getApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeTrainsCar> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve merge train status
     * Retrieves the merge train status of a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequestCreation(id, mergeRequestIid);
    }

    /**
     * List all merge requests in a merge train
     * Lists all merge requests in a merge train for a target branch.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param targetBranch Target branch of the merge train.
     * @param scope Filter merge trains by scope. &#x60;active&#x60; returns merge trains to be merged. &#x60;complete&#x60; returns merge trains that have been merged.
     * @param sort Sort results in ascending or descending order by &#x60;id&#x60;. A lower &#x60;id&#x60; value means the merge request was added to the train earlier.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeTrainsCar&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeTrainsTargetBranchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String targetBranch, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeTrainsTargetBranch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'targetBranch' is set
        if (targetBranch == null) {
            throw new RestClientResponseException("Missing the required parameter 'targetBranch' when calling getApiV4ProjectsIdMergeTrainsTargetBranch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("target_branch", targetBranch);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
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

        ParameterizedTypeReference<List<APIEntitiesMergeTrainsCar>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_trains/{target_branch}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all merge requests in a merge train
     * Lists all merge requests in a merge train for a target branch.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param targetBranch Target branch of the merge train.
     * @param scope Filter merge trains by scope. &#x60;active&#x60; returns merge trains to be merged. &#x60;complete&#x60; returns merge trains that have been merged.
     * @param sort Sort results in ascending or descending order by &#x60;id&#x60;. A lower &#x60;id&#x60; value means the merge request was added to the train earlier.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeTrainsCar&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeTrainsCar> getApiV4ProjectsIdMergeTrainsTargetBranch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String targetBranch, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeTrainsCar>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeTrainsTargetBranchRequestCreation(id, targetBranch, scope, sort, page, perPage).body(localVarReturnType);
    }

    /**
     * List all merge requests in a merge train
     * Lists all merge requests in a merge train for a target branch.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param targetBranch Target branch of the merge train.
     * @param scope Filter merge trains by scope. &#x60;active&#x60; returns merge trains to be merged. &#x60;complete&#x60; returns merge trains that have been merged.
     * @param sort Sort results in ascending or descending order by &#x60;id&#x60;. A lower &#x60;id&#x60; value means the merge request was added to the train earlier.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeTrainsCar&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeTrainsCar>> getApiV4ProjectsIdMergeTrainsTargetBranchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String targetBranch, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeTrainsCar>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeTrainsTargetBranchRequestCreation(id, targetBranch, scope, sort, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all merge requests in a merge train
     * Lists all merge requests in a merge train for a target branch.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param targetBranch Target branch of the merge train.
     * @param scope Filter merge trains by scope. &#x60;active&#x60; returns merge trains to be merged. &#x60;complete&#x60; returns merge trains that have been merged.
     * @param sort Sort results in ascending or descending order by &#x60;id&#x60;. A lower &#x60;id&#x60; value means the merge request was added to the train earlier.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeTrainsTargetBranchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String targetBranch, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeTrainsTargetBranchRequestCreation(id, targetBranch, scope, sort, page, perPage);
    }

    /**
     * Add a merge request to a merge train
     * Adds a specified merge request to a merge train.
     * <p><b>201</b> - Created
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Failed to merge
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest The postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest parameter
     * @return List&lt;APIEntitiesMergeTrainsCar&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesMergeTrainsCar>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_trains/merge_requests/{merge_request_iid}", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a merge request to a merge train
     * Adds a specified merge request to a merge train.
     * <p><b>201</b> - Created
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Failed to merge
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest The postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest parameter
     * @return List&lt;APIEntitiesMergeTrainsCar&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeTrainsCar> postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIid(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeTrainsCar>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest).body(localVarReturnType);
    }

    /**
     * Add a merge request to a merge train
     * Adds a specified merge request to a merge train.
     * <p><b>201</b> - Created
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Failed to merge
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest The postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest parameter
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeTrainsCar&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeTrainsCar>> postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeTrainsCar>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a merge request to a merge train
     * Adds a specified merge request to a merge train.
     * <p><b>201</b> - Created
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Failed to merge
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest The postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeTrainsMergeRequestsMergeRequestIidRequest);
    }
}
