package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCiPipeline;
import ai.gebo.gitlab.client.model.APIEntitiesCiPipelineBasic;
import ai.gebo.gitlab.client.model.APIEntitiesCommit;
import ai.gebo.gitlab.client.model.APIEntitiesCommitWithLink;
import ai.gebo.gitlab.client.model.APIEntitiesDiff;
import ai.gebo.gitlab.client.model.APIEntitiesIssuableTimeStats;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequest;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestBasic;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestChanges;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestDependency;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestDiff;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestDiffFull;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestReviewer;
import ai.gebo.gitlab.client.model.APIEntitiesUserBasic;
import ai.gebo.gitlab.client.model.GetApiV4MergeRequestsApproverIdsParameter;
import ai.gebo.gitlab.client.model.GetApiV4MergeRequestsAssigneeIdParameter;
import ai.gebo.gitlab.client.model.GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdMergeRequestsMergeRequestIidRequest;

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
public class MergeRequestsApi {
    private ApiClient apiClient;

    public MergeRequestsApi() {
        this(new ApiClient());
    }

    public MergeRequestsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a merge request
     * Deletes a specified merge request for a project. Administrators and project Owners only.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition failed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdMergeRequestsMergeRequestIidRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a merge request
     * Deletes a specified merge request for a project. Administrators and project Owners only.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition failed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdMergeRequestsMergeRequestIid(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdMergeRequestsMergeRequestIidRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Delete a merge request
     * Deletes a specified merge request for a project. Administrators and project Owners only.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition failed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdMergeRequestsMergeRequestIidWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMergeRequestsMergeRequestIidRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Delete a merge request
     * Deletes a specified merge request for a project. Administrators and project Owners only.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition failed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdMergeRequestsMergeRequestIidWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return deleteApiV4ProjectsIdMergeRequestsMergeRequestIidRequestCreation(id, mergeRequestIid);
    }

    /**
     * Remove a merge request dependency
     * Removes a merge request dependency.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param blockId ID of the merge request block.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer blockId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'blockId' is set
        if (blockId == null) {
            throw new RestClientResponseException("Missing the required parameter 'blockId' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);
        pathParams.put("block_id", blockId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/blocks/{block_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove a merge request dependency
     * Removes a merge request dependency.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param blockId ID of the merge request block.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer blockId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockIdRequestCreation(id, mergeRequestIid, blockId).body(localVarReturnType);
    }

    /**
     * Remove a merge request dependency
     * Removes a merge request dependency.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param blockId ID of the merge request block.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer blockId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockIdRequestCreation(id, mergeRequestIid, blockId).toEntity(localVarReturnType);
    }

    /**
     * Remove a merge request dependency
     * Removes a merge request dependency.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param blockId ID of the merge request block.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer blockId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockIdRequestCreation(id, mergeRequestIid, blockId);
    }

    /**
     * Delete context commits from a merge request
     * Deletes specified context commits from a merge request. This endpoint has a rate limit of 10 requests per minute for each user. Requests to create context commits count toward the same limit. Exceeding the limit returns &#x60;429 Too Many Requests&#x60;. The rate limit is behind a feature flag named &#x60;rate_limit_context_commits_write&#x60;, disabled by default.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param commits Context commits&#39; SHAs.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull List<String> commits, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'commits' is set
        if (commits == null) {
            throw new RestClientResponseException("Missing the required parameter 'commits' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "commits", commits));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/context_commits", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete context commits from a merge request
     * Deletes specified context commits from a merge request. This endpoint has a rate limit of 10 requests per minute for each user. Requests to create context commits count toward the same limit. Exceeding the limit returns &#x60;429 Too Many Requests&#x60;. The rate limit is behind a feature flag named &#x60;rate_limit_context_commits_write&#x60;, disabled by default.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param commits Context commits&#39; SHAs.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommits(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull List<String> commits, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequestCreation(id, commits, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Delete context commits from a merge request
     * Deletes specified context commits from a merge request. This endpoint has a rate limit of 10 requests per minute for each user. Requests to create context commits count toward the same limit. Exceeding the limit returns &#x60;429 Too Many Requests&#x60;. The rate limit is behind a feature flag named &#x60;rate_limit_context_commits_write&#x60;, disabled by default.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param commits Context commits&#39; SHAs.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull List<String> commits, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequestCreation(id, commits, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Delete context commits from a merge request
     * Deletes specified context commits from a merge request. This endpoint has a rate limit of 10 requests per minute for each user. Requests to create context commits count toward the same limit. Exceeding the limit returns &#x60;429 Too Many Requests&#x60;. The rate limit is behind a feature flag named &#x60;rate_limit_context_commits_write&#x60;, disabled by default.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param commits Context commits&#39; SHAs.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull List<String> commits, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return deleteApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequestCreation(id, commits, mergeRequestIid);
    }

    /**
     * List all group merge requests
     * Lists all merge requests for a specified group and any subgroups.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer. Default is &#x60;all&#x60;.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @param approverIds Return merge requests that have all specified users as eligible approvers according to the approval rules. &#x60;None&#x60; returns merge requests with no eligible approvers, and &#x60;Any&#x60; returns merge requests with at least one eligible approver. Premium and Ultimate only.
     * @param approvedByIds Return merge requests approved by all the users with the given &#x60;id&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_usernames&#x60;.
     * @param approvedByUsernames Return merge requests approved by all the users with the given &#x60;username&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_ids&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns merge requests from non-archived projects only.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdMergeRequestsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approverIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByUsernames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_id", authorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_username", authorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "assignee_id", assigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "assignee_username", assigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "reviewer_username", reviewerUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "labels", labels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "milestone", milestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "my_reaction_emoji", myReactionEmoji));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "reviewer_id", reviewerId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_labels_details", withLabelsDetails));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_merge_status_recheck", withMergeStatusRecheck));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merged_after", mergedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merged_before", mergedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "view", view));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "source_branch", sourceBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "source_project_id", sourceProjectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "target_branch", targetBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "in", in));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "wip", wip));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "draft", draft));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not", not));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_id]", notAuthorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_username]", notAuthorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[assignee_id]", notAssigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[assignee_username]", notAssigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[reviewer_username]", notReviewerUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[labels]", notLabels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[milestone]", notMilestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[my_reaction_emoji]", notMyReactionEmoji));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[reviewer_id]", notReviewerId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "deployed_before", deployedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "deployed_after", deployedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "environment", environment));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merge_user_id", mergeUserId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merge_user_username", mergeUserUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "approver_ids", approverIds));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "approved_by_ids", approvedByIds));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "approved_by_usernames", approvedByUsernames));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "non_archived", nonArchived));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/merge_requests", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group merge requests
     * Lists all merge requests for a specified group and any subgroups.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer. Default is &#x60;all&#x60;.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @param approverIds Return merge requests that have all specified users as eligible approvers according to the approval rules. &#x60;None&#x60; returns merge requests with no eligible approvers, and &#x60;Any&#x60; returns merge requests with at least one eligible approver. Premium and Ultimate only.
     * @param approvedByIds Return merge requests approved by all the users with the given &#x60;id&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_usernames&#x60;.
     * @param approvedByUsernames Return merge requests approved by all the users with the given &#x60;username&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_ids&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns merge requests from non-archived projects only.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeRequestBasic> getApiV4GroupsIdMergeRequests(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approverIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByUsernames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMergeRequestsRequestCreation(id, authorId, authorUsername, assigneeId, assigneeUsername, reviewerUsername, labels, milestone, myReactionEmoji, reviewerId, state, orderBy, sort, withLabelsDetails, withMergeStatusRecheck, createdAfter, createdBefore, updatedAfter, updatedBefore, mergedAfter, mergedBefore, view, scope, sourceBranch, sourceProjectId, targetBranch, search, in, wip, draft, not, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notReviewerUsername, notLabels, notMilestone, notMyReactionEmoji, notReviewerId, deployedBefore, deployedAfter, environment, mergeUserId, mergeUserUsername, approverIds, approvedByIds, approvedByUsernames, page, perPage, nonArchived).body(localVarReturnType);
    }

    /**
     * List all group merge requests
     * Lists all merge requests for a specified group and any subgroups.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer. Default is &#x60;all&#x60;.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @param approverIds Return merge requests that have all specified users as eligible approvers according to the approval rules. &#x60;None&#x60; returns merge requests with no eligible approvers, and &#x60;Any&#x60; returns merge requests with at least one eligible approver. Premium and Ultimate only.
     * @param approvedByIds Return merge requests approved by all the users with the given &#x60;id&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_usernames&#x60;.
     * @param approvedByUsernames Return merge requests approved by all the users with the given &#x60;username&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_ids&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns merge requests from non-archived projects only.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeRequestBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeRequestBasic>> getApiV4GroupsIdMergeRequestsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approverIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByUsernames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMergeRequestsRequestCreation(id, authorId, authorUsername, assigneeId, assigneeUsername, reviewerUsername, labels, milestone, myReactionEmoji, reviewerId, state, orderBy, sort, withLabelsDetails, withMergeStatusRecheck, createdAfter, createdBefore, updatedAfter, updatedBefore, mergedAfter, mergedBefore, view, scope, sourceBranch, sourceProjectId, targetBranch, search, in, wip, draft, not, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notReviewerUsername, notLabels, notMilestone, notMyReactionEmoji, notReviewerId, deployedBefore, deployedAfter, environment, mergeUserId, mergeUserUsername, approverIds, approvedByIds, approvedByUsernames, page, perPage, nonArchived).toEntity(localVarReturnType);
    }

    /**
     * List all group merge requests
     * Lists all merge requests for a specified group and any subgroups.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer. Default is &#x60;all&#x60;.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @param approverIds Return merge requests that have all specified users as eligible approvers according to the approval rules. &#x60;None&#x60; returns merge requests with no eligible approvers, and &#x60;Any&#x60; returns merge requests with at least one eligible approver. Premium and Ultimate only.
     * @param approvedByIds Return merge requests approved by all the users with the given &#x60;id&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_usernames&#x60;.
     * @param approvedByUsernames Return merge requests approved by all the users with the given &#x60;username&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_ids&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns merge requests from non-archived projects only.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdMergeRequestsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approverIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByUsernames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        return getApiV4GroupsIdMergeRequestsRequestCreation(id, authorId, authorUsername, assigneeId, assigneeUsername, reviewerUsername, labels, milestone, myReactionEmoji, reviewerId, state, orderBy, sort, withLabelsDetails, withMergeStatusRecheck, createdAfter, createdBefore, updatedAfter, updatedBefore, mergedAfter, mergedBefore, view, scope, sourceBranch, sourceProjectId, targetBranch, search, in, wip, draft, not, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notReviewerUsername, notLabels, notMilestone, notMyReactionEmoji, notReviewerId, deployedBefore, deployedAfter, environment, mergeUserId, mergeUserUsername, approverIds, approvedByIds, approvedByUsernames, page, perPage, nonArchived);
    }

    /**
     * List all merge requests
     * Lists all merge requests accessible to the authenticated user. By default, returns only merge requests created by the current user. Use &#x60;scope&#x3D;all&#x60; to get all merge requests.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @param approverIds Return merge requests that have all specified users as eligible approvers according to the approval rules. &#x60;None&#x60; returns merge requests with no eligible approvers, and &#x60;Any&#x60; returns merge requests with at least one eligible approver. Premium and Ultimate only.
     * @param approvedByIds Return merge requests approved by all the users with the given &#x60;id&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_usernames&#x60;.
     * @param approvedByUsernames Return merge requests approved by all the users with the given &#x60;username&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_ids&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns merge requests from non-archived projects only.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4MergeRequestsRequestCreation(@jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approverIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByUsernames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_id", authorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_username", authorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "assignee_id", assigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "assignee_username", assigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "reviewer_username", reviewerUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "labels", labels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "milestone", milestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "my_reaction_emoji", myReactionEmoji));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "reviewer_id", reviewerId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_labels_details", withLabelsDetails));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_merge_status_recheck", withMergeStatusRecheck));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merged_after", mergedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merged_before", mergedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "view", view));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "source_branch", sourceBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "source_project_id", sourceProjectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "target_branch", targetBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "in", in));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "wip", wip));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "draft", draft));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not", not));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_id]", notAuthorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_username]", notAuthorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[assignee_id]", notAssigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[assignee_username]", notAssigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[reviewer_username]", notReviewerUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[labels]", notLabels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[milestone]", notMilestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[my_reaction_emoji]", notMyReactionEmoji));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[reviewer_id]", notReviewerId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "deployed_before", deployedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "deployed_after", deployedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "environment", environment));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merge_user_id", mergeUserId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merge_user_username", mergeUserUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "approver_ids", approverIds));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "approved_by_ids", approvedByIds));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "approved_by_usernames", approvedByUsernames));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "non_archived", nonArchived));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/merge_requests", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all merge requests
     * Lists all merge requests accessible to the authenticated user. By default, returns only merge requests created by the current user. Use &#x60;scope&#x3D;all&#x60; to get all merge requests.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @param approverIds Return merge requests that have all specified users as eligible approvers according to the approval rules. &#x60;None&#x60; returns merge requests with no eligible approvers, and &#x60;Any&#x60; returns merge requests with at least one eligible approver. Premium and Ultimate only.
     * @param approvedByIds Return merge requests approved by all the users with the given &#x60;id&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_usernames&#x60;.
     * @param approvedByUsernames Return merge requests approved by all the users with the given &#x60;username&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_ids&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns merge requests from non-archived projects only.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeRequestBasic> getApiV4MergeRequests(@jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approverIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByUsernames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4MergeRequestsRequestCreation(authorId, authorUsername, assigneeId, assigneeUsername, reviewerUsername, labels, milestone, myReactionEmoji, reviewerId, state, orderBy, sort, withLabelsDetails, withMergeStatusRecheck, createdAfter, createdBefore, updatedAfter, updatedBefore, mergedAfter, mergedBefore, view, scope, sourceBranch, sourceProjectId, targetBranch, search, in, wip, draft, not, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notReviewerUsername, notLabels, notMilestone, notMyReactionEmoji, notReviewerId, deployedBefore, deployedAfter, environment, mergeUserId, mergeUserUsername, approverIds, approvedByIds, approvedByUsernames, page, perPage, nonArchived).body(localVarReturnType);
    }

    /**
     * List all merge requests
     * Lists all merge requests accessible to the authenticated user. By default, returns only merge requests created by the current user. Use &#x60;scope&#x3D;all&#x60; to get all merge requests.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @param approverIds Return merge requests that have all specified users as eligible approvers according to the approval rules. &#x60;None&#x60; returns merge requests with no eligible approvers, and &#x60;Any&#x60; returns merge requests with at least one eligible approver. Premium and Ultimate only.
     * @param approvedByIds Return merge requests approved by all the users with the given &#x60;id&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_usernames&#x60;.
     * @param approvedByUsernames Return merge requests approved by all the users with the given &#x60;username&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_ids&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns merge requests from non-archived projects only.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeRequestBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeRequestBasic>> getApiV4MergeRequestsWithHttpInfo(@jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approverIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByUsernames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4MergeRequestsRequestCreation(authorId, authorUsername, assigneeId, assigneeUsername, reviewerUsername, labels, milestone, myReactionEmoji, reviewerId, state, orderBy, sort, withLabelsDetails, withMergeStatusRecheck, createdAfter, createdBefore, updatedAfter, updatedBefore, mergedAfter, mergedBefore, view, scope, sourceBranch, sourceProjectId, targetBranch, search, in, wip, draft, not, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notReviewerUsername, notLabels, notMilestone, notMyReactionEmoji, notReviewerId, deployedBefore, deployedAfter, environment, mergeUserId, mergeUserUsername, approverIds, approvedByIds, approvedByUsernames, page, perPage, nonArchived).toEntity(localVarReturnType);
    }

    /**
     * List all merge requests
     * Lists all merge requests accessible to the authenticated user. By default, returns only merge requests created by the current user. Use &#x60;scope&#x3D;all&#x60; to get all merge requests.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @param approverIds Return merge requests that have all specified users as eligible approvers according to the approval rules. &#x60;None&#x60; returns merge requests with no eligible approvers, and &#x60;Any&#x60; returns merge requests with at least one eligible approver. Premium and Ultimate only.
     * @param approvedByIds Return merge requests approved by all the users with the given &#x60;id&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_usernames&#x60;.
     * @param approvedByUsernames Return merge requests approved by all the users with the given &#x60;username&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_ids&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns merge requests from non-archived projects only.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4MergeRequestsWithResponseSpec(@jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approverIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByUsernames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        return getApiV4MergeRequestsRequestCreation(authorId, authorUsername, assigneeId, assigneeUsername, reviewerUsername, labels, milestone, myReactionEmoji, reviewerId, state, orderBy, sort, withLabelsDetails, withMergeStatusRecheck, createdAfter, createdBefore, updatedAfter, updatedBefore, mergedAfter, mergedBefore, view, scope, sourceBranch, sourceProjectId, targetBranch, search, in, wip, draft, not, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notReviewerUsername, notLabels, notMilestone, notMyReactionEmoji, notReviewerId, deployedBefore, deployedAfter, environment, mergeUserId, mergeUserUsername, approverIds, approvedByIds, approvedByUsernames, page, perPage, nonArchived);
    }

    /**
     * List all project merge requests
     * Lists all project merge requests.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer. Default is &#x60;all&#x60;.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @param approverIds Return merge requests that have all specified users as eligible approvers according to the approval rules. &#x60;None&#x60; returns merge requests with no eligible approvers, and &#x60;Any&#x60; returns merge requests with at least one eligible approver. Premium and Ultimate only.
     * @param approvedByIds Return merge requests approved by all the users with the given &#x60;id&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_usernames&#x60;.
     * @param approvedByUsernames Return merge requests approved by all the users with the given &#x60;username&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_ids&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param iids Return merge requests matching the provided internal IDs.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approverIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByUsernames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable List<Integer> iids) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_id", authorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_username", authorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "assignee_id", assigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "assignee_username", assigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "reviewer_username", reviewerUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "labels", labels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "milestone", milestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "my_reaction_emoji", myReactionEmoji));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "reviewer_id", reviewerId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_labels_details", withLabelsDetails));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_merge_status_recheck", withMergeStatusRecheck));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merged_after", mergedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merged_before", mergedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "view", view));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "source_branch", sourceBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "source_project_id", sourceProjectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "target_branch", targetBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "in", in));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "wip", wip));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "draft", draft));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not", not));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_id]", notAuthorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_username]", notAuthorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[assignee_id]", notAssigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[assignee_username]", notAssigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[reviewer_username]", notReviewerUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[labels]", notLabels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[milestone]", notMilestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[my_reaction_emoji]", notMyReactionEmoji));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[reviewer_id]", notReviewerId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "deployed_before", deployedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "deployed_after", deployedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "environment", environment));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merge_user_id", mergeUserId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merge_user_username", mergeUserUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "approver_ids", approverIds));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "approved_by_ids", approvedByIds));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "approved_by_usernames", approvedByUsernames));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "iids", iids));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project merge requests
     * Lists all project merge requests.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer. Default is &#x60;all&#x60;.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @param approverIds Return merge requests that have all specified users as eligible approvers according to the approval rules. &#x60;None&#x60; returns merge requests with no eligible approvers, and &#x60;Any&#x60; returns merge requests with at least one eligible approver. Premium and Ultimate only.
     * @param approvedByIds Return merge requests approved by all the users with the given &#x60;id&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_usernames&#x60;.
     * @param approvedByUsernames Return merge requests approved by all the users with the given &#x60;username&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_ids&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param iids Return merge requests matching the provided internal IDs.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeRequestBasic> getApiV4ProjectsIdMergeRequests(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approverIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByUsernames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable List<Integer> iids) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsRequestCreation(id, authorId, authorUsername, assigneeId, assigneeUsername, reviewerUsername, labels, milestone, myReactionEmoji, reviewerId, state, orderBy, sort, withLabelsDetails, withMergeStatusRecheck, createdAfter, createdBefore, updatedAfter, updatedBefore, mergedAfter, mergedBefore, view, scope, sourceBranch, sourceProjectId, targetBranch, search, in, wip, draft, not, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notReviewerUsername, notLabels, notMilestone, notMyReactionEmoji, notReviewerId, deployedBefore, deployedAfter, environment, mergeUserId, mergeUserUsername, approverIds, approvedByIds, approvedByUsernames, page, perPage, iids).body(localVarReturnType);
    }

    /**
     * List all project merge requests
     * Lists all project merge requests.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer. Default is &#x60;all&#x60;.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @param approverIds Return merge requests that have all specified users as eligible approvers according to the approval rules. &#x60;None&#x60; returns merge requests with no eligible approvers, and &#x60;Any&#x60; returns merge requests with at least one eligible approver. Premium and Ultimate only.
     * @param approvedByIds Return merge requests approved by all the users with the given &#x60;id&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_usernames&#x60;.
     * @param approvedByUsernames Return merge requests approved by all the users with the given &#x60;username&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_ids&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param iids Return merge requests matching the provided internal IDs.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeRequestBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeRequestBasic>> getApiV4ProjectsIdMergeRequestsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approverIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByUsernames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable List<Integer> iids) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsRequestCreation(id, authorId, authorUsername, assigneeId, assigneeUsername, reviewerUsername, labels, milestone, myReactionEmoji, reviewerId, state, orderBy, sort, withLabelsDetails, withMergeStatusRecheck, createdAfter, createdBefore, updatedAfter, updatedBefore, mergedAfter, mergedBefore, view, scope, sourceBranch, sourceProjectId, targetBranch, search, in, wip, draft, not, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notReviewerUsername, notLabels, notMilestone, notMyReactionEmoji, notReviewerId, deployedBefore, deployedAfter, environment, mergeUserId, mergeUserUsername, approverIds, approvedByIds, approvedByUsernames, page, perPage, iids).toEntity(localVarReturnType);
    }

    /**
     * List all project merge requests
     * Lists all project merge requests.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer. Default is &#x60;all&#x60;.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @param approverIds Return merge requests that have all specified users as eligible approvers according to the approval rules. &#x60;None&#x60; returns merge requests with no eligible approvers, and &#x60;Any&#x60; returns merge requests with at least one eligible approver. Premium and Ultimate only.
     * @param approvedByIds Return merge requests approved by all the users with the given &#x60;id&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_usernames&#x60;.
     * @param approvedByUsernames Return merge requests approved by all the users with the given &#x60;username&#x60;, up to 5 users. &#x60;None&#x60; returns merge requests with no approvals, and &#x60;Any&#x60; returns merge requests with an approval. Mutually exclusive with &#x60;approved_by_ids&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param iids Return merge requests matching the provided internal IDs.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approverIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByIds, @jakarta.annotation.Nullable GetApiV4MergeRequestsApproverIdsParameter approvedByUsernames, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable List<Integer> iids) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsRequestCreation(id, authorId, authorUsername, assigneeId, assigneeUsername, reviewerUsername, labels, milestone, myReactionEmoji, reviewerId, state, orderBy, sort, withLabelsDetails, withMergeStatusRecheck, createdAfter, createdBefore, updatedAfter, updatedBefore, mergedAfter, mergedBefore, view, scope, sourceBranch, sourceProjectId, targetBranch, search, in, wip, draft, not, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notReviewerUsername, notLabels, notMilestone, notMyReactionEmoji, notReviewerId, deployedBefore, deployedAfter, environment, mergeUserId, mergeUserUsername, approverIds, approvedByIds, approvedByUsernames, page, perPage, iids);
    }

    /**
     * Retrieve a merge request
     * Retrieves a merge request for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param renderHtml If &#x60;true&#x60;, the response includes rendered HTML for title and description.
     * @param includeDivergedCommitsCount If &#x60;true&#x60;, the response includes the commits behind the target branch.
     * @param includeRebaseInProgress If &#x60;true&#x60;, the response includes whether a rebase operation is in progress.
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Boolean renderHtml, @jakarta.annotation.Nullable Boolean includeDivergedCommitsCount, @jakarta.annotation.Nullable Boolean includeRebaseInProgress) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "render_html", renderHtml));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_diverged_commits_count", includeDivergedCommitsCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_rebase_in_progress", includeRebaseInProgress));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a merge request
     * Retrieves a merge request for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param renderHtml If &#x60;true&#x60;, the response includes rendered HTML for title and description.
     * @param includeDivergedCommitsCount If &#x60;true&#x60;, the response includes the commits behind the target branch.
     * @param includeRebaseInProgress If &#x60;true&#x60;, the response includes whether a rebase operation is in progress.
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequest getApiV4ProjectsIdMergeRequestsMergeRequestIid(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Boolean renderHtml, @jakarta.annotation.Nullable Boolean includeDivergedCommitsCount, @jakarta.annotation.Nullable Boolean includeRebaseInProgress) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidRequestCreation(id, mergeRequestIid, renderHtml, includeDivergedCommitsCount, includeRebaseInProgress).body(localVarReturnType);
    }

    /**
     * Retrieve a merge request
     * Retrieves a merge request for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param renderHtml If &#x60;true&#x60;, the response includes rendered HTML for title and description.
     * @param includeDivergedCommitsCount If &#x60;true&#x60;, the response includes the commits behind the target branch.
     * @param includeRebaseInProgress If &#x60;true&#x60;, the response includes whether a rebase operation is in progress.
     * @return ResponseEntity&lt;APIEntitiesMergeRequest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequest> getApiV4ProjectsIdMergeRequestsMergeRequestIidWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Boolean renderHtml, @jakarta.annotation.Nullable Boolean includeDivergedCommitsCount, @jakarta.annotation.Nullable Boolean includeRebaseInProgress) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidRequestCreation(id, mergeRequestIid, renderHtml, includeDivergedCommitsCount, includeRebaseInProgress).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a merge request
     * Retrieves a merge request for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param renderHtml If &#x60;true&#x60;, the response includes rendered HTML for title and description.
     * @param includeDivergedCommitsCount If &#x60;true&#x60;, the response includes the commits behind the target branch.
     * @param includeRebaseInProgress If &#x60;true&#x60;, the response includes whether a rebase operation is in progress.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Boolean renderHtml, @jakarta.annotation.Nullable Boolean includeDivergedCommitsCount, @jakarta.annotation.Nullable Boolean includeRebaseInProgress) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidRequestCreation(id, mergeRequestIid, renderHtml, includeDivergedCommitsCount, includeRebaseInProgress);
    }

    /**
     * Retrieve blocked merge requests
     * Retrieves merge requests blocked by a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeRequestDependency&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidBlockeesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidBlockees", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidBlockees", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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

        ParameterizedTypeReference<List<APIEntitiesMergeRequestDependency>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/blockees", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve blocked merge requests
     * Retrieves merge requests blocked by a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeRequestDependency&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeRequestDependency> getApiV4ProjectsIdMergeRequestsMergeRequestIidBlockees(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestDependency>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidBlockeesRequestCreation(id, mergeRequestIid, page, perPage).body(localVarReturnType);
    }

    /**
     * Retrieve blocked merge requests
     * Retrieves merge requests blocked by a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeRequestDependency&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeRequestDependency>> getApiV4ProjectsIdMergeRequestsMergeRequestIidBlockeesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestDependency>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidBlockeesRequestCreation(id, mergeRequestIid, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Retrieve blocked merge requests
     * Retrieves merge requests blocked by a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidBlockeesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidBlockeesRequestCreation(id, mergeRequestIid, page, perPage);
    }

    /**
     * List all dependencies for a merge request
     * List all merge request dependencies for a specified merge request. Dependencies must be resolved before the merge request can merge. If the user does not have access to a blocking merge request, the &#x60;blocking_merge_request&#x60; attribute is not returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeRequestDependency&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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

        ParameterizedTypeReference<List<APIEntitiesMergeRequestDependency>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/blocks", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all dependencies for a merge request
     * List all merge request dependencies for a specified merge request. Dependencies must be resolved before the merge request can merge. If the user does not have access to a blocking merge request, the &#x60;blocking_merge_request&#x60; attribute is not returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeRequestDependency&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeRequestDependency> getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestDependency>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequestCreation(id, mergeRequestIid, page, perPage).body(localVarReturnType);
    }

    /**
     * List all dependencies for a merge request
     * List all merge request dependencies for a specified merge request. Dependencies must be resolved before the merge request can merge. If the user does not have access to a blocking merge request, the &#x60;blocking_merge_request&#x60; attribute is not returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeRequestDependency&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeRequestDependency>> getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestDependency>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequestCreation(id, mergeRequestIid, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all dependencies for a merge request
     * List all merge request dependencies for a specified merge request. Dependencies must be resolved before the merge request can merge. If the user does not have access to a blocking merge request, the &#x60;blocking_merge_request&#x60; attribute is not returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequestCreation(id, mergeRequestIid, page, perPage);
    }

    /**
     * Retrieve a merge request dependency
     * Retrieves a specified merge request dependency.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param blockId ID of the merge request block.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer blockId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'blockId' is set
        if (blockId == null) {
            throw new RestClientResponseException("Missing the required parameter 'blockId' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);
        pathParams.put("block_id", blockId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/blocks/{block_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a merge request dependency
     * Retrieves a specified merge request dependency.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param blockId ID of the merge request block.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer blockId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockIdRequestCreation(id, mergeRequestIid, blockId).body(localVarReturnType);
    }

    /**
     * Retrieve a merge request dependency
     * Retrieves a specified merge request dependency.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param blockId ID of the merge request block.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer blockId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockIdRequestCreation(id, mergeRequestIid, blockId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a merge request dependency
     * Retrieves a specified merge request dependency.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param blockId ID of the merge request block.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer blockId) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksBlockIdRequestCreation(id, mergeRequestIid, blockId);
    }

    /**
     * Retrieve merge request changes
     * Retrieves changes for a specified merge request. The diffs have the same size limits as other diffs returned by the API or shown in the UI. When the limits affect the results, the &#x60;overflow&#x60; field is &#x60;true&#x60;. Deprecated in GitLab 15.7 and scheduled for removal in API v5. Use GET /projects/:id/merge_requests/:merge_request_iid/diffs instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return APIEntitiesMergeRequestChanges
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidChangesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidChanges", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidChanges", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "unidiff", unidiff));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMergeRequestChanges> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/changes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve merge request changes
     * Retrieves changes for a specified merge request. The diffs have the same size limits as other diffs returned by the API or shown in the UI. When the limits affect the results, the &#x60;overflow&#x60; field is &#x60;true&#x60;. Deprecated in GitLab 15.7 and scheduled for removal in API v5. Use GET /projects/:id/merge_requests/:merge_request_iid/diffs instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return APIEntitiesMergeRequestChanges
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public APIEntitiesMergeRequestChanges getApiV4ProjectsIdMergeRequestsMergeRequestIidChanges(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestChanges> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidChangesRequestCreation(id, mergeRequestIid, unidiff).body(localVarReturnType);
    }

    /**
     * Retrieve merge request changes
     * Retrieves changes for a specified merge request. The diffs have the same size limits as other diffs returned by the API or shown in the UI. When the limits affect the results, the &#x60;overflow&#x60; field is &#x60;true&#x60;. Deprecated in GitLab 15.7 and scheduled for removal in API v5. Use GET /projects/:id/merge_requests/:merge_request_iid/diffs instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return ResponseEntity&lt;APIEntitiesMergeRequestChanges&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<APIEntitiesMergeRequestChanges> getApiV4ProjectsIdMergeRequestsMergeRequestIidChangesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestChanges> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidChangesRequestCreation(id, mergeRequestIid, unidiff).toEntity(localVarReturnType);
    }

    /**
     * Retrieve merge request changes
     * Retrieves changes for a specified merge request. The diffs have the same size limits as other diffs returned by the API or shown in the UI. When the limits affect the results, the &#x60;overflow&#x60; field is &#x60;true&#x60;. Deprecated in GitLab 15.7 and scheduled for removal in API v5. Use GET /projects/:id/merge_requests/:merge_request_iid/diffs instead.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidChangesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidChangesRequestCreation(id, mergeRequestIid, unidiff);
    }

    /**
     * List all issues that close on merge
     * Lists all issues that close on merge.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssuesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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

        ParameterizedTypeReference<List<GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/closes_issues", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all issues that close on merge
     * Lists all issues that close on merge.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner> getApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssuesRequestCreation(id, mergeRequestIid, page, perPage).body(localVarReturnType);
    }

    /**
     * List all issues that close on merge
     * Lists all issues that close on merge.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner>> getApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssuesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssuesRequestCreation(id, mergeRequestIid, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all issues that close on merge
     * Lists all issues that close on merge.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssuesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssuesRequestCreation(id, mergeRequestIid, page, perPage);
    }

    /**
     * Retrieve merge request commits
     * Retrieves commits for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCommit&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidCommitsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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

        ParameterizedTypeReference<List<APIEntitiesCommit>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/commits", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve merge request commits
     * Retrieves commits for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCommit&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCommit> getApiV4ProjectsIdMergeRequestsMergeRequestIidCommits(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCommit>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidCommitsRequestCreation(id, mergeRequestIid, page, perPage).body(localVarReturnType);
    }

    /**
     * Retrieve merge request commits
     * Retrieves commits for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCommit&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCommit>> getApiV4ProjectsIdMergeRequestsMergeRequestIidCommitsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCommit>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidCommitsRequestCreation(id, mergeRequestIid, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Retrieve merge request commits
     * Retrieves commits for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidCommitsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidCommitsRequestCreation(id, mergeRequestIid, page, perPage);
    }

    /**
     * List all context commits for a merge request
     * Lists all context commits for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return List&lt;APIEntitiesCommitWithLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesCommitWithLink>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/context_commits", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all context commits for a merge request
     * Lists all context commits for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return List&lt;APIEntitiesCommitWithLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCommitWithLink> getApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommits(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCommitWithLink>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * List all context commits for a merge request
     * Lists all context commits for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCommitWithLink&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCommitWithLink>> getApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCommitWithLink>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * List all context commits for a merge request
     * Lists all context commits for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequestCreation(id, mergeRequestIid);
    }

    /**
     * List all merge request diffs
     * Lists all merge request diffs. This endpoint is subject to merge request diff limits. Merge requests that exceed the diff limits return limited results.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return List&lt;APIEntitiesDiff&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidDiffsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidDiffs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidDiffs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/diffs", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all merge request diffs
     * Lists all merge request diffs. This endpoint is subject to merge request diff limits. Merge requests that exceed the diff limits return limited results.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return List&lt;APIEntitiesDiff&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDiff> getApiV4ProjectsIdMergeRequestsMergeRequestIidDiffs(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiff>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidDiffsRequestCreation(id, mergeRequestIid, page, perPage, unidiff).body(localVarReturnType);
    }

    /**
     * List all merge request diffs
     * Lists all merge request diffs. This endpoint is subject to merge request diff limits. Merge requests that exceed the diff limits return limited results.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDiff&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDiff>> getApiV4ProjectsIdMergeRequestsMergeRequestIidDiffsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiff>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidDiffsRequestCreation(id, mergeRequestIid, page, perPage, unidiff).toEntity(localVarReturnType);
    }

    /**
     * List all merge request diffs
     * Lists all merge request diffs. This endpoint is subject to merge request diff limits. Merge requests that exceed the diff limits return limited results.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidDiffsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidDiffsRequestCreation(id, mergeRequestIid, page, perPage, unidiff);
    }

    /**
     * Merge to default merge ref path
     * Merges the changes between the merge request source and target branches into the &#x60;refs/merge-requests/:iid/merge&#x60; ref, of the target project repository, if possible. This ref has the state the target branch would have if a regular merge action was taken. The ref is not always overwritten, but it is kept at the latest possible state. Requests are rate limited for each user or IP address (&#x60;merge_requests_merge_ref_limit&#x60; application setting).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRefRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRef", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRef", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/merge_ref", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Merge to default merge ref path
     * Merges the changes between the merge request source and target branches into the &#x60;refs/merge-requests/:iid/merge&#x60; ref, of the target project repository, if possible. This ref has the state the target branch would have if a regular merge action was taken. The ref is not always overwritten, but it is kept at the latest possible state. Requests are rate limited for each user or IP address (&#x60;merge_requests_merge_ref_limit&#x60; application setting).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRef(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRefRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Merge to default merge ref path
     * Merges the changes between the merge request source and target branches into the &#x60;refs/merge-requests/:iid/merge&#x60; ref, of the target project repository, if possible. This ref has the state the target branch would have if a regular merge action was taken. The ref is not always overwritten, but it is kept at the latest possible state. Requests are rate limited for each user or IP address (&#x60;merge_requests_merge_ref_limit&#x60; application setting).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRefWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRefRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Merge to default merge ref path
     * Merges the changes between the merge request source and target branches into the &#x60;refs/merge-requests/:iid/merge&#x60; ref, of the target project repository, if possible. This ref has the state the target branch would have if a regular merge action was taken. The ref is not always overwritten, but it is kept at the latest possible state. Requests are rate limited for each user or IP address (&#x60;merge_requests_merge_ref_limit&#x60; application setting).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRefWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRefRequestCreation(id, mergeRequestIid);
    }

    /**
     * Retrieve merge request participants
     * Retrieves participants for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return List&lt;APIEntitiesUserBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidParticipantsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidParticipants", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidParticipants", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesUserBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/participants", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve merge request participants
     * Retrieves participants for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return List&lt;APIEntitiesUserBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesUserBasic> getApiV4ProjectsIdMergeRequestsMergeRequestIidParticipants(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidParticipantsRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Retrieve merge request participants
     * Retrieves participants for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseEntity&lt;List&lt;APIEntitiesUserBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesUserBasic>> getApiV4ProjectsIdMergeRequestsMergeRequestIidParticipantsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidParticipantsRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve merge request participants
     * Retrieves participants for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidParticipantsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidParticipantsRequestCreation(id, mergeRequestIid);
    }

    /**
     * List all merge request pipelines
     * Lists all merge request pipelines.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return List&lt;APIEntitiesCiPipelineBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidPipelines", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidPipelines", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesCiPipelineBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/pipelines", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all merge request pipelines
     * Lists all merge request pipelines.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return List&lt;APIEntitiesCiPipelineBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiPipelineBasic> getApiV4ProjectsIdMergeRequestsMergeRequestIidPipelines(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiPipelineBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * List all merge request pipelines
     * Lists all merge request pipelines.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiPipelineBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiPipelineBasic>> getApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiPipelineBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * List all merge request pipelines
     * Lists all merge request pipelines.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequestCreation(id, mergeRequestIid);
    }

    /**
     * Retrieve merge request raw diffs
     * Retrieves the raw diffs of the files changed in a merge request.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidRawDiffsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidRawDiffs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidRawDiffs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/raw_diffs", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve merge request raw diffs
     * Retrieves the raw diffs of the files changed in a merge request.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdMergeRequestsMergeRequestIidRawDiffs(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdMergeRequestsMergeRequestIidRawDiffsRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Retrieve merge request raw diffs
     * Retrieves the raw diffs of the files changed in a merge request.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdMergeRequestsMergeRequestIidRawDiffsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidRawDiffsRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve merge request raw diffs
     * Retrieves the raw diffs of the files changed in a merge request.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidRawDiffsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidRawDiffsRequestCreation(id, mergeRequestIid);
    }

    /**
     * List all issues related to the merge request
     * Lists all issues related to the merge request. Related issues are the issues referenced in the title, description, commit messages, comments, and discussions of the merge request.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidRelatedIssuesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidRelatedIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidRelatedIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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

        ParameterizedTypeReference<List<GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/related_issues", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all issues related to the merge request
     * Lists all issues related to the merge request. Related issues are the issues referenced in the title, description, commit messages, comments, and discussions of the merge request.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner> getApiV4ProjectsIdMergeRequestsMergeRequestIidRelatedIssues(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidRelatedIssuesRequestCreation(id, mergeRequestIid, page, perPage).body(localVarReturnType);
    }

    /**
     * List all issues related to the merge request
     * Lists all issues related to the merge request. Related issues are the issues referenced in the title, description, commit messages, comments, and discussions of the merge request.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner>> getApiV4ProjectsIdMergeRequestsMergeRequestIidRelatedIssuesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<GetApiV4ProjectsIdMergeRequestsMergeRequestIidClosesIssues200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidRelatedIssuesRequestCreation(id, mergeRequestIid, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all issues related to the merge request
     * Lists all issues related to the merge request. Related issues are the issues referenced in the title, description, commit messages, comments, and discussions of the merge request.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidRelatedIssuesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidRelatedIssuesRequestCreation(id, mergeRequestIid, page, perPage);
    }

    /**
     * Retrieve merge request reviewers
     * Retrieves reviewers for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return List&lt;APIEntitiesMergeRequestReviewer&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidReviewersRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidReviewers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidReviewers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesMergeRequestReviewer>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/reviewers", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve merge request reviewers
     * Retrieves reviewers for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return List&lt;APIEntitiesMergeRequestReviewer&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeRequestReviewer> getApiV4ProjectsIdMergeRequestsMergeRequestIidReviewers(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestReviewer>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidReviewersRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Retrieve merge request reviewers
     * Retrieves reviewers for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeRequestReviewer&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeRequestReviewer>> getApiV4ProjectsIdMergeRequestsMergeRequestIidReviewersWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestReviewer>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidReviewersRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve merge request reviewers
     * Retrieves reviewers for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidReviewersWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidReviewersRequestCreation(id, mergeRequestIid);
    }

    /**
     * Retrieve time tracking stats for a merge request
     * Retrieves time tracking stats for a specified merge request, including time estimate and time spent in seconds and human-readable format (for example, &#x60;1h 30m&#x60;).
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidTimeStatsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidTimeStats", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidTimeStats", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/time_stats", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve time tracking stats for a merge request
     * Retrieves time tracking stats for a specified merge request, including time estimate and time spent in seconds and human-readable format (for example, &#x60;1h 30m&#x60;).
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssuableTimeStats getApiV4ProjectsIdMergeRequestsMergeRequestIidTimeStats(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidTimeStatsRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Retrieve time tracking stats for a merge request
     * Retrieves time tracking stats for a specified merge request, including time estimate and time spent in seconds and human-readable format (for example, &#x60;1h 30m&#x60;).
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request
     * @return ResponseEntity&lt;APIEntitiesIssuableTimeStats&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssuableTimeStats> getApiV4ProjectsIdMergeRequestsMergeRequestIidTimeStatsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidTimeStatsRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve time tracking stats for a merge request
     * Retrieves time tracking stats for a specified merge request, including time estimate and time spent in seconds and human-readable format (for example, &#x60;1h 30m&#x60;).
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidTimeStatsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidTimeStatsRequestCreation(id, mergeRequestIid);
    }

    /**
     * Retrieve merge request diff versions
     * Retrieves merge request diff versions.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeRequestDiff&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidVersions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidVersions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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

        ParameterizedTypeReference<List<APIEntitiesMergeRequestDiff>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/versions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve merge request diff versions
     * Retrieves merge request diff versions.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeRequestDiff&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeRequestDiff> getApiV4ProjectsIdMergeRequestsMergeRequestIidVersions(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestDiff>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsRequestCreation(id, mergeRequestIid, page, perPage).body(localVarReturnType);
    }

    /**
     * Retrieve merge request diff versions
     * Retrieves merge request diff versions.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeRequestDiff&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeRequestDiff>> getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestDiff>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsRequestCreation(id, mergeRequestIid, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Retrieve merge request diff versions
     * Retrieves merge request diff versions.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsRequestCreation(id, mergeRequestIid, page, perPage);
    }

    /**
     * Retrieve a merge request diff version
     * Retrieves a merge request diff version.
     * <p><b>200</b> - OK
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param versionId ID of the merge request diff version.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return APIEntitiesMergeRequestDiffFull
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsVersionIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer versionId, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsVersionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsVersionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'versionId' is set
        if (versionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'versionId' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsVersionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);
        pathParams.put("version_id", versionId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "unidiff", unidiff));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMergeRequestDiffFull> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/versions/{version_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a merge request diff version
     * Retrieves a merge request diff version.
     * <p><b>200</b> - OK
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param versionId ID of the merge request diff version.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return APIEntitiesMergeRequestDiffFull
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequestDiffFull getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsVersionId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer versionId, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestDiffFull> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsVersionIdRequestCreation(id, mergeRequestIid, versionId, unidiff).body(localVarReturnType);
    }

    /**
     * Retrieve a merge request diff version
     * Retrieves a merge request diff version.
     * <p><b>200</b> - OK
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param versionId ID of the merge request diff version.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return ResponseEntity&lt;APIEntitiesMergeRequestDiffFull&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequestDiffFull> getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsVersionIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer versionId, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestDiffFull> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsVersionIdRequestCreation(id, mergeRequestIid, versionId, unidiff).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a merge request diff version
     * Retrieves a merge request diff version.
     * <p><b>200</b> - OK
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param versionId ID of the merge request diff version.
     * @param unidiff If &#x60;true&#x60;, presents diffs in the [unified diff](https://www.gnu.org/software/diffutils/manual/html_node/Detailed-Unified.html) format.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsVersionIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer versionId, @jakarta.annotation.Nullable Boolean unidiff) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidVersionsVersionIdRequestCreation(id, mergeRequestIid, versionId, unidiff);
    }

    /**
     * Create a CI configuration merge request
     * Creates a merge request to add CI configuration to a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdCreateCiConfigRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdCreateCiConfig", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/create_ci_config", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a CI configuration merge request
     * Creates a merge request to add CI configuration to a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequest postApiV4ProjectsIdCreateCiConfig(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdCreateCiConfigRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Create a CI configuration merge request
     * Creates a merge request to add CI configuration to a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesMergeRequest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequest> postApiV4ProjectsIdCreateCiConfigWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdCreateCiConfigRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Create a CI configuration merge request
     * Creates a merge request to add CI configuration to a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdCreateCiConfigWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return postApiV4ProjectsIdCreateCiConfigRequestCreation(id);
    }

    /**
     * Create a merge request
     * Creates a merge request for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMergeRequestsRequest The postApiV4ProjectsIdMergeRequestsRequest parameter
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsRequest postApiV4ProjectsIdMergeRequestsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMergeRequestsRequest' is set
        if (postApiV4ProjectsIdMergeRequestsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMergeRequestsRequest' when calling postApiV4ProjectsIdMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a merge request
     * Creates a merge request for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMergeRequestsRequest The postApiV4ProjectsIdMergeRequestsRequest parameter
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequest postApiV4ProjectsIdMergeRequests(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsRequest postApiV4ProjectsIdMergeRequestsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsRequestCreation(id, postApiV4ProjectsIdMergeRequestsRequest).body(localVarReturnType);
    }

    /**
     * Create a merge request
     * Creates a merge request for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMergeRequestsRequest The postApiV4ProjectsIdMergeRequestsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMergeRequest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequest> postApiV4ProjectsIdMergeRequestsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsRequest postApiV4ProjectsIdMergeRequestsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsRequestCreation(id, postApiV4ProjectsIdMergeRequestsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a merge request
     * Creates a merge request for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMergeRequestsRequest The postApiV4ProjectsIdMergeRequestsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsRequest postApiV4ProjectsIdMergeRequestsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsRequestCreation(id, postApiV4ProjectsIdMergeRequestsRequest);
    }

    /**
     * Add spent time for a merge request
     * Adds spent time for a specified merge request.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest parameter
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest' is set
        if (postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/add_spent_time", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add spent time for a merge request
     * Adds spent time for a specified merge request.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest parameter
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssuableTimeStats postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTime(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest).body(localVarReturnType);
    }

    /**
     * Add spent time for a merge request
     * Adds spent time for a specified merge request.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest parameter
     * @return ResponseEntity&lt;APIEntitiesIssuableTimeStats&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssuableTimeStats> postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest).toEntity(localVarReturnType);
    }

    /**
     * Add spent time for a merge request
     * Adds spent time for a specified merge request.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidAddSpentTimeRequest);
    }

    /**
     * Create a merge request dependency
     * Creates a merge request dependency between two merge requests. The merge request is blocked until the dependency merges.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request to be blocked.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/blocks", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a merge request dependency
     * Creates a merge request dependency between two merge requests. The merge request is blocked until the dependency merges.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request to be blocked.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest).body(localVarReturnType);
    }

    /**
     * Create a merge request dependency
     * Creates a merge request dependency between two merge requests. The merge request is blocked until the dependency merges.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request to be blocked.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a merge request dependency
     * Creates a merge request dependency between two merge requests. The merge request is blocked until the dependency merges.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request to be blocked.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidBlocksRequest);
    }

    /**
     * Cancel auto merge
     * Cancels an automatic merge for a merge request that has been set to merge automatically. If the merge request is on a merge train, it is also removed from the train. Use this endpoint instead of &#x60;cancel_merge_when_pipeline_succeeds&#x60;, which returns a status hash rather than the merge request. This feature was introduced in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelAutoMergeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelAutoMerge", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelAutoMerge", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/cancel_auto_merge", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Cancel auto merge
     * Cancels an automatic merge for a merge request that has been set to merge automatically. If the merge request is on a merge train, it is also removed from the train. Use this endpoint instead of &#x60;cancel_merge_when_pipeline_succeeds&#x60;, which returns a status hash rather than the merge request. This feature was introduced in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelAutoMerge(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelAutoMergeRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Cancel auto merge
     * Cancels an automatic merge for a merge request that has been set to merge automatically. If the merge request is on a merge train, it is also removed from the train. Use this endpoint instead of &#x60;cancel_merge_when_pipeline_succeeds&#x60;, which returns a status hash rather than the merge request. This feature was introduced in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseEntity&lt;APIEntitiesMergeRequest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequest> postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelAutoMergeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelAutoMergeRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Cancel auto merge
     * Cancels an automatic merge for a merge request that has been set to merge automatically. If the merge request is on a merge train, it is also removed from the train. Use this endpoint instead of &#x60;cancel_merge_when_pipeline_succeeds&#x60;, which returns a status hash rather than the merge request. This feature was introduced in GitLab 19.5.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelAutoMergeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelAutoMergeRequestCreation(id, mergeRequestIid);
    }

    /**
     * Cancel merge when pipeline succeeds
     * Cancels an automatic merge for a merge request that has been set to merge when the pipeline succeeds. If the merge request is on a merge train, it is also removed from the train. This endpoint does not return the merge request. When the merge request is not set to auto-merge, the response has &#x60;status&#x60; &#x60;error&#x60; and an &#x60;http_status&#x60; field that is not the status code of the response. Deprecated in GitLab 19.5. Use POST /projects/:id/merge_requests/:merge_request_iid/cancel_auto_merge instead.
     * <p><b>201</b> - The status field of the response body is success when an auto-merge was canceled, and error when the merge request had none set
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelMergeWhenPipelineSucceedsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelMergeWhenPipelineSucceeds", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelMergeWhenPipelineSucceeds", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/cancel_merge_when_pipeline_succeeds", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Cancel merge when pipeline succeeds
     * Cancels an automatic merge for a merge request that has been set to merge when the pipeline succeeds. If the merge request is on a merge train, it is also removed from the train. This endpoint does not return the merge request. When the merge request is not set to auto-merge, the response has &#x60;status&#x60; &#x60;error&#x60; and an &#x60;http_status&#x60; field that is not the status code of the response. Deprecated in GitLab 19.5. Use POST /projects/:id/merge_requests/:merge_request_iid/cancel_auto_merge instead.
     * <p><b>201</b> - The status field of the response body is success when an auto-merge was canceled, and error when the merge request had none set
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public void postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelMergeWhenPipelineSucceeds(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelMergeWhenPipelineSucceedsRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Cancel merge when pipeline succeeds
     * Cancels an automatic merge for a merge request that has been set to merge when the pipeline succeeds. If the merge request is on a merge train, it is also removed from the train. This endpoint does not return the merge request. When the merge request is not set to auto-merge, the response has &#x60;status&#x60; &#x60;error&#x60; and an &#x60;http_status&#x60; field that is not the status code of the response. Deprecated in GitLab 19.5. Use POST /projects/:id/merge_requests/:merge_request_iid/cancel_auto_merge instead.
     * <p><b>201</b> - The status field of the response body is success when an auto-merge was canceled, and error when the merge request had none set
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<Void> postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelMergeWhenPipelineSucceedsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelMergeWhenPipelineSucceedsRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Cancel merge when pipeline succeeds
     * Cancels an automatic merge for a merge request that has been set to merge when the pipeline succeeds. If the merge request is on a merge train, it is also removed from the train. This endpoint does not return the merge request. When the merge request is not set to auto-merge, the response has &#x60;status&#x60; &#x60;error&#x60; and an &#x60;http_status&#x60; field that is not the status code of the response. Deprecated in GitLab 19.5. Use POST /projects/:id/merge_requests/:merge_request_iid/cancel_auto_merge instead.
     * <p><b>201</b> - The status field of the response body is success when an auto-merge was canceled, and error when the merge request had none set
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelMergeWhenPipelineSucceedsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidCancelMergeWhenPipelineSucceedsRequestCreation(id, mergeRequestIid);
    }

    /**
     * Create context commits for a merge request
     * Creates context commits for a specified merge request. This endpoint has a rate limit of 10 requests per minute for each user. Requests to delete context commits count toward the same limit. Exceeding the limit returns &#x60;429 Too Many Requests&#x60;. The rate limit is behind a feature flag named &#x60;rate_limit_context_commits_write&#x60;, disabled by default.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest parameter
     * @return List&lt;APIEntitiesCommit&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest' is set
        if (postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesCommit>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/context_commits", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create context commits for a merge request
     * Creates context commits for a specified merge request. This endpoint has a rate limit of 10 requests per minute for each user. Requests to delete context commits count toward the same limit. Exceeding the limit returns &#x60;429 Too Many Requests&#x60;. The rate limit is behind a feature flag named &#x60;rate_limit_context_commits_write&#x60;, disabled by default.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest parameter
     * @return List&lt;APIEntitiesCommit&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCommit> postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommits(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCommit>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest).body(localVarReturnType);
    }

    /**
     * Create context commits for a merge request
     * Creates context commits for a specified merge request. This endpoint has a rate limit of 10 requests per minute for each user. Requests to delete context commits count toward the same limit. Exceeding the limit returns &#x60;429 Too Many Requests&#x60;. The rate limit is behind a feature flag named &#x60;rate_limit_context_commits_write&#x60;, disabled by default.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest parameter
     * @return ResponseEntity&lt;List&lt;APIEntitiesCommit&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCommit>> postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCommit>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create context commits for a merge request
     * Creates context commits for a specified merge request. This endpoint has a rate limit of 10 requests per minute for each user. Requests to delete context commits count toward the same limit. Exceeding the limit returns &#x60;429 Too Many Requests&#x60;. The rate limit is behind a feature flag named &#x60;rate_limit_context_commits_write&#x60;, disabled by default.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidContextCommitsRequest);
    }

    /**
     * Create a merge request pipeline
     * Creates a merge request pipeline. Pipelines created with this operation must configure &#x60;.gitlab-ci.yml&#x60; with &#x60;only: [merge_requests]&#x60; to create jobs. The pipeline does not run a regular branch or tag pipeline. The new pipeline is a detached merge request pipeline, or a merged results pipeline if merged results pipelines are enabled for the project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest parameter
     * @return APIEntitiesCiPipeline
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelines", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelines", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/pipelines", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a merge request pipeline
     * Creates a merge request pipeline. Pipelines created with this operation must configure &#x60;.gitlab-ci.yml&#x60; with &#x60;only: [merge_requests]&#x60; to create jobs. The pipeline does not run a regular branch or tag pipeline. The new pipeline is a detached merge request pipeline, or a merged results pipeline if merged results pipelines are enabled for the project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest parameter
     * @return APIEntitiesCiPipeline
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiPipeline postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelines(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest).body(localVarReturnType);
    }

    /**
     * Create a merge request pipeline
     * Creates a merge request pipeline. Pipelines created with this operation must configure &#x60;.gitlab-ci.yml&#x60; with &#x60;only: [merge_requests]&#x60; to create jobs. The pipeline does not run a regular branch or tag pipeline. The new pipeline is a detached merge request pipeline, or a merged results pipeline if merged results pipelines are enabled for the project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiPipeline&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiPipeline> postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a merge request pipeline
     * Creates a merge request pipeline. Pipelines created with this operation must configure &#x60;.gitlab-ci.yml&#x60; with &#x60;only: [merge_requests]&#x60; to create jobs. The pipeline does not run a regular branch or tag pipeline. The new pipeline is a detached merge request pipeline, or a merged results pipeline if merged results pipelines are enabled for the project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidPipelinesRequest);
    }

    /**
     * Reset spent time for a merge request
     * Resets the total spent time for a specified merge request to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidResetSpentTimeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidResetSpentTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidResetSpentTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/reset_spent_time", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reset spent time for a merge request
     * Resets the total spent time for a specified merge request to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssuableTimeStats postApiV4ProjectsIdMergeRequestsMergeRequestIidResetSpentTime(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidResetSpentTimeRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Reset spent time for a merge request
     * Resets the total spent time for a specified merge request to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request
     * @return ResponseEntity&lt;APIEntitiesIssuableTimeStats&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssuableTimeStats> postApiV4ProjectsIdMergeRequestsMergeRequestIidResetSpentTimeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidResetSpentTimeRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Reset spent time for a merge request
     * Resets the total spent time for a specified merge request to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidResetSpentTimeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidResetSpentTimeRequestCreation(id, mergeRequestIid);
    }

    /**
     * Reset the estimated time for a merge request
     * Resets the estimated time for a specified merge request to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request.
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidResetTimeEstimateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidResetTimeEstimate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidResetTimeEstimate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/reset_time_estimate", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reset the estimated time for a merge request
     * Resets the estimated time for a specified merge request to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request.
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssuableTimeStats postApiV4ProjectsIdMergeRequestsMergeRequestIidResetTimeEstimate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidResetTimeEstimateRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Reset the estimated time for a merge request
     * Resets the estimated time for a specified merge request to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request.
     * @return ResponseEntity&lt;APIEntitiesIssuableTimeStats&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssuableTimeStats> postApiV4ProjectsIdMergeRequestsMergeRequestIidResetTimeEstimateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidResetTimeEstimateRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Reset the estimated time for a merge request
     * Resets the estimated time for a specified merge request to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidResetTimeEstimateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidResetTimeEstimateRequestCreation(id, mergeRequestIid);
    }

    /**
     * Set the estimated time for a merge request
     * Sets an estimated time of work for a specified merge request.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest parameter
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest' is set
        if (postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/time_estimate", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Set the estimated time for a merge request
     * Sets an estimated time of work for a specified merge request.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest parameter
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssuableTimeStats postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest).body(localVarReturnType);
    }

    /**
     * Set the estimated time for a merge request
     * Sets an estimated time of work for a specified merge request.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesIssuableTimeStats&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssuableTimeStats> postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest).toEntity(localVarReturnType);
    }

    /**
     * Set the estimated time for a merge request
     * Sets an estimated time of work for a specified merge request.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid The internal ID of the merge_request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidTimeEstimateRequest);
    }

    /**
     * Update a merge request
     * Updates a merge request for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest parameter
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdMergeRequestsMergeRequestIidRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a merge request
     * Updates a merge request for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest parameter
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequest putApiV4ProjectsIdMergeRequestsMergeRequestIid(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidRequestCreation(id, mergeRequestIid, putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest).body(localVarReturnType);
    }

    /**
     * Update a merge request
     * Updates a merge request for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMergeRequest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequest> putApiV4ProjectsIdMergeRequestsMergeRequestIidWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidRequestCreation(id, mergeRequestIid, putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a merge request
     * Updates a merge request for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdMergeRequestsMergeRequestIidWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidRequestCreation(id, mergeRequestIid, putApiV4ProjectsIdMergeRequestsMergeRequestIidRequest);
    }

    /**
     * Merge a merge request
     * Merges a merge request. Accepts and merges changes submitted with the merge request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest parameter
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIidMerge", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIidMerge", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/merge", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Merge a merge request
     * Merges a merge request. Accepts and merges changes submitted with the merge request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest parameter
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidMerge(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequestCreation(id, mergeRequestIid, putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest).body(localVarReturnType);
    }

    /**
     * Merge a merge request
     * Merges a merge request. Accepts and merges changes submitted with the merge request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMergeRequest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequest> putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequestCreation(id, mergeRequestIid, putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest).toEntity(localVarReturnType);
    }

    /**
     * Merge a merge request
     * Merges a merge request. Accepts and merges changes submitted with the merge request.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>405</b> - Method not allowed
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequestCreation(id, mergeRequestIid, putApiV4ProjectsIdMergeRequestsMergeRequestIidMergeRequest);
    }

    /**
     * Rebase a merge request
     * Rebases a merge request. Automatically rebases the &#x60;source_branch&#x60; of the merge request against its &#x60;target_branch&#x60;. The rebase runs asynchronously. To check its status, poll the retrieve merge request endpoint with &#x60;include_rebase_in_progress&#x60; set to &#x60;true&#x60;. While the rebase runs, &#x60;rebase_in_progress&#x60; is &#x60;true&#x60;. When it ends, &#x60;rebase_in_progress&#x60; is &#x60;false&#x60;, and &#x60;merge_error&#x60; holds the error if the rebase failed.
     * <p><b>202</b> - Accepted
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIidRebase", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIidRebase", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/rebase", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rebase a merge request
     * Rebases a merge request. Automatically rebases the &#x60;source_branch&#x60; of the merge request against its &#x60;target_branch&#x60;. The rebase runs asynchronously. To check its status, poll the retrieve merge request endpoint with &#x60;include_rebase_in_progress&#x60; set to &#x60;true&#x60;. While the rebase runs, &#x60;rebase_in_progress&#x60; is &#x60;true&#x60;. When it ends, &#x60;rebase_in_progress&#x60; is &#x60;false&#x60;, and &#x60;merge_error&#x60; holds the error if the rebase failed.
     * <p><b>202</b> - Accepted
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdMergeRequestsMergeRequestIidRebase(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequestCreation(id, mergeRequestIid, putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest).body(localVarReturnType);
    }

    /**
     * Rebase a merge request
     * Rebases a merge request. Automatically rebases the &#x60;source_branch&#x60; of the merge request against its &#x60;target_branch&#x60;. The rebase runs asynchronously. To check its status, poll the retrieve merge request endpoint with &#x60;include_rebase_in_progress&#x60; set to &#x60;true&#x60;. While the rebase runs, &#x60;rebase_in_progress&#x60; is &#x60;true&#x60;. When it ends, &#x60;rebase_in_progress&#x60; is &#x60;false&#x60;, and &#x60;merge_error&#x60; holds the error if the rebase failed.
     * <p><b>202</b> - Accepted
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequestCreation(id, mergeRequestIid, putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest).toEntity(localVarReturnType);
    }

    /**
     * Rebase a merge request
     * Rebases a merge request. Automatically rebases the &#x60;source_branch&#x60; of the merge request against its &#x60;target_branch&#x60;. The rebase runs asynchronously. To check its status, poll the retrieve merge request endpoint with &#x60;include_rebase_in_progress&#x60; set to &#x60;true&#x60;. While the rebase runs, &#x60;rebase_in_progress&#x60; is &#x60;true&#x60;. When it ends, &#x60;rebase_in_progress&#x60; is &#x60;false&#x60;, and &#x60;merge_error&#x60; holds the error if the rebase failed.
     * <p><b>202</b> - Accepted
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequestCreation(id, mergeRequestIid, putApiV4ProjectsIdMergeRequestsMergeRequestIidRebaseRequest);
    }
}
